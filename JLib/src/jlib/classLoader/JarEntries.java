/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.classLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import jlib.log.Log;
import jlib.misc.FileSystem;

public class JarEntries
{
	public JarEntries()
	{
	}
	
	public boolean open(String csJar, ArrayList<String> m_arrPaths)
	{
		boolean bOpened = false;
	    for(int n=0; n<m_arrPaths.size() && !bOpened; n++)
	    {
		   	String csPath = m_arrPaths.get(n);
		   	String csFullPathJarFile = FileSystem.appendFilePath(csPath, csJar);
    		bOpened = open(csFullPathJarFile, true, ".class");
	    }
	    return bOpened;
	}
	
	public boolean open(String csPath, String csJar, boolean bFilterByExtension, String csExtension)
	{
		csPath = FileSystem.normalizePath(csPath);
   		String csFullPathJarFile = csPath + csJar;
   		boolean bOpened = open(csFullPathJarFile, bFilterByExtension, csExtension);
	    return bOpened;
	}
	
	public boolean open(String csFullPathJarFile, boolean bFilterByExtension, String csExtension)
    {
		int nExtensionLength = 0;
		if(bFilterByExtension)
			nExtensionLength = csExtension.length();
		try 
		{
			Log.logNormal("Preloading JarEntries for file " + csFullPathJarFile);
		    m_zipFile = new ZipFile(csFullPathJarFile);
		    Enumeration e = m_zipFile.entries();
		    while (e.hasMoreElements())
			{
		    	ZipEntry zipEntry = (ZipEntry)e.nextElement();
		    	String csEntryName = zipEntry.getName();
		    	csEntryName = csEntryName.toLowerCase();
		    	if((bFilterByExtension && csEntryName.endsWith(csExtension)) || !bFilterByExtension)
		    	{
		    		csEntryName = csEntryName.substring(0, csEntryName.length()-nExtensionLength);	// remove extension
			    	JarItemEntry jarItemEntry = new JarItemEntry(zipEntry);   
			    	if(m_hash == null)
			    		m_hash = new Hashtable<String, JarItemEntry>();
			    	m_hash.put(csEntryName, jarItemEntry);
		    	}
			}
		    int nNbEntries = 0;
		    if(m_hash != null)
		    	nNbEntries = m_hash.size();
		    Log.logNormal("Preloaded " + nNbEntries + " entries from jar file " + csFullPathJarFile);
		    return true;
		}
		catch (FileNotFoundException e)
		{
			int n0 =0 ;
		}
		catch (IOException e1)
		{
			Log.logNormal("Could not find jar file " + csFullPathJarFile);
		}
		return false;
	}
	
	public void close()
	{
		if(m_zipFile != null)
		{
			try
			{
				m_zipFile.close();
				m_hash = null;
			}
			catch (IOException e)
			{
			}
		}
		m_zipFile = null;
	}
	
	ZipFile getZipFile()
	{
		return m_zipFile;
	}
	
	public byte[] loadJarEntry(String csClass)
	{
		if (m_hash != null)
		{
			csClass = csClass.toLowerCase();
			JarItemEntry entry = m_hash.get(csClass);
			if(entry != null)
			{
				return entry.loadBytes(m_zipFile);
			}
		}
		return null;
	}
	
	public Enumeration<String> getKeys()
	{
		if(m_hash != null)
			return m_hash.keys();
		return null;
	}
	
	public JarItemEntry getEntry(String csKey)
	{
		if(m_hash != null)
			return m_hash.get(csKey);
		return null;
	}
	
	private Hashtable<String, JarItemEntry> m_hash = null;
	private ZipFile m_zipFile = null;
}
