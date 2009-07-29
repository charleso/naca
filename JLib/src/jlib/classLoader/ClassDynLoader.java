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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import jlib.log.Log;

public class ClassDynLoader extends ClassLoader
{
	protected ClassLoader m_DefaultClassLoader = null ;
	protected static Hashtable<String, CoupleCodeLoader> ms_hashByName = new Hashtable<String, CoupleCodeLoader>();
	private static int 	ms_nActive = 0;
	protected ArrayList<String> m_arrPaths = null;
	protected JarEntries m_jarEntries = null;
	protected boolean m_bCanLoadJar = false;
	protected boolean m_bCanLoadClass = false;
    protected String m_csCurrentClassName = null;
    
	public ClassDynLoader()
	{
		super();
		m_DefaultClassLoader = getClass().getClassLoader() ;
		Log.logDebug("ClassDynLoader created: " +toString());
		ms_nActive++;
	}
	
	public void finalize()
	{
		Log.logDebug("ClassDynLoader finalized: " +toString());
		ms_nActive--;
	}
	
	public ClassDynLoader(ArrayList<String> arrPaths, JarEntries jarEntries, boolean bCanLoadClass, boolean bCanLoadJar)
	{
		m_arrPaths = arrPaths;
		m_DefaultClassLoader = getClass().getClassLoader() ;
		addJarEntry(jarEntries, bCanLoadClass, bCanLoadJar);
		Log.logDebug("ClassDynLoader created: " +toString());
		
		ms_nActive++;
	}
	
	public void addPathURL(String csSourcePath)
	{
		if(m_arrPaths == null)
			m_arrPaths = new ArrayList<String>();
		m_arrPaths.add(csSourcePath);
	}

	public void addPathURL(ArrayList<String> arrSourcePath)
	{
		if(arrSourcePath != null)
		{
			if(m_arrPaths == null)
				m_arrPaths = new ArrayList<String>();
			for(int n=0; n<arrSourcePath.size(); n++)
			{
				String csPath = arrSourcePath.get(n);
				m_arrPaths.add(csPath);
			}
		}
	}
	
	public void addJarEntry(JarEntries jarEntries, boolean bCanLoadClass, boolean bCanLoadJar)
	{
		m_jarEntries = jarEntries;
		m_bCanLoadClass = bCanLoadClass;
		m_bCanLoadJar = bCanLoadJar;
	}
	
	protected byte[] getClassFileBytes(String className) 
	{
		byte result[] = null;
    	
		if(m_bCanLoadClass)
		{
			String clpack = className.replace('.', '/') ;
	 	    for(int n=0; n<m_arrPaths.size(); n++)
	    	{
	 	    	String csPath = m_arrPaths.get(n);
		    	try 
		    	{
		    		FileInputStream fi = new FileInputStream(csPath + clpack + ".class");
		    	    result = new byte[fi.available()];
		    	    fi.read(result);
		    	    fi.close() ;
		    	    return result;
		    	} 
		    	catch (Exception e) 
		    	{	
		    	}
	    	}
		}

		if(m_bCanLoadJar && m_jarEntries != null)
		{
			result = m_jarEntries.loadJarEntry(className);
		}
		return result;
	}
	
	public synchronized Class doLoadClass(String csClassName)
	{
		inMakeNewInstance(csClassName);
		Class cls = loadClass(csClassName);
		
		return cls;
	}
	
	protected Class tryLoadWithPrimordialClassLoader(String csClassName)
	{
		try	// Check with java runtime primordial class loader 
	    {
//			if(csClassName.equals("RS01M10"))		// && !csClassName.equals("RS01A10S"))
//			{
//				int gg = 0;
//			}
//			if(!csClassName.equals("RS01A10"))		// && !csClassName.equals("RS01A10S"))
//		    {
				Class classCode = m_DefaultClassLoader.loadClass(csClassName);
		    	return classCode;
//		    }
//			else
//			{
//				int gg = 0;
//			}		    
	    } 
	    catch (ClassNotFoundException e) 
	    {
	    	int gg = 0;
	    }
	    catch (IllegalAccessError e)
	    {
	    	int gg = 0;
	    }
	    catch (Exception e)
	    {
	    	int gg = 0;
	    }
	    return null;
	}

    // This is the required version of loadClass which is called both from loadClass above and from the internal function FindClassFromClass.
	@SuppressWarnings("unchecked")
	public Class loadClass(String csClassName)
    {
		Class classCode = null;
		
		// Try to get code from cache
    	CoupleCodeLoader couple = ms_hashByName.get(csClassName);
		if(couple != null)
		{
			classCode = couple.getClassCode();
			return classCode;
		}

		// Try to load with priomordial loader
		classCode = tryLoadWithPrimordialClassLoader(csClassName);
   		if(classCode != null)
   			return classCode; 
        
        // Try to load it from our paths 
        byte  arrbyteClassData[] = getClassFileBytes(csClassName);
        if (arrbyteClassData == null)
        {
            return null;	// Class not found
        }

        // Define it (parse the class file) 
        classCode = defineClass(csClassName, arrbyteClassData, 0, arrbyteClassData.length);
        if (classCode == null)
        {
            throw new ClassFormatError();
        }

		resolveClass(classCode);
		
		if(classCode != null)
		{
			couple = new CoupleCodeLoader(classCode, this);
			register(csClassName, couple);
		}
		
		return classCode;
    }
        
    protected void inMakeNewInstance(String csCurrentClassName)
    {
    	m_csCurrentClassName = csCurrentClassName;
    }
    
    protected void outMakeNewInstance()
    {
    	m_csCurrentClassName = null;
    }

	Object makeNewInstance(String csClassName, Class classCode)
	{
		Object obj = null;
		try
		{
			inMakeNewInstance(csClassName);
			obj = classCode.newInstance();
			if(obj != null)
			{
				CoupleCodeLoader couple = ms_hashByName.get(csClassName);
				couple.addInstance(obj);
			}
			outMakeNewInstance();
			return obj;
		}
		catch (InstantiationException e)
		{
			Log.logNormal("Could not instanciates " + csClassName + "; error=" + e.toString());
			return null;
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NoClassDefFoundError e)
		{
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return null;		
	}
	
	static void removeAllInstances(String csName)
	{
		CoupleCodeLoader couple = ms_hashByName.get(csName);
		if(couple != null)
		{
			ms_hashByName.remove(csName);
			couple.removeAllInstances();
			couple = null;
			Log.logDebug("removeAllInstances: ms_hashByName contains: "+ms_nActive + " items");
		}
	}
	
	protected void register(String csClassName, CoupleCodeLoader couple)
	{
		ms_hashByName.put(csClassName, couple);
		Log.logDebug("register: ms_hashByName contains: " + ms_nActive + " items");
	}
	
	protected JarEntries preloadJarEntries(String csJarFile)
	{		
		JarEntries jarEntries = new JarEntries();
		jarEntries.open(csJarFile, m_arrPaths);
		return jarEntries;
	}
	
	protected Hashtable<String, byte[]> preloadJarData(String csJarFile)
	{
		Hashtable<String, Integer> hashFileSize = new Hashtable<String, Integer>(); 

 	    for(int n=0; n<m_arrPaths.size(); n++)
    	{
 	    	String csPath = m_arrPaths.get(n);
	    	try 
	    	{
	    		String csFullPathJarFile = csPath +  csJarFile;
			    ZipFile zipFile = new ZipFile(csFullPathJarFile);
			    Enumeration e = zipFile.entries();
			    while (e.hasMoreElements())
				{
			    	ZipEntry entry = (ZipEntry)e.nextElement();
			    	hashFileSize.put(entry.getName(), new Integer((int)entry.getSize()));
				}
				zipFile.close();
				Hashtable<String, byte[]> hashFileData = loadJarFileData(csFullPathJarFile, hashFileSize);
				return hashFileData;
	    	}
			catch (FileNotFoundException e)
			{
			}
			catch (IOException e1)
			{
			}
    	}
 	    return null;
	}
	
	private Hashtable<String, byte[]> loadJarFileData(String csJarFile, Hashtable<String, Integer> hashFileSize)
	{		
		Hashtable<String, byte[]> hashFileData = new Hashtable<String, byte[]>();  
		try
		{
		    FileInputStream fis = new FileInputStream(csJarFile);
		    BufferedInputStream bis = new BufferedInputStream(fis);
		    ZipInputStream zis = new ZipInputStream(bis);
		    
		    ZipEntry entry = null;
		    while((entry = zis.getNextEntry()) != null)
			{
		    	if (entry.isDirectory())
			    {
		    		continue;
			    }
		    	
		    	String csEntryName = entry.getName();
		    	if(csEntryName.toLowerCase().endsWith(".class"))
		    	{		
			    	int nSize= (int) entry.getSize();	// -1 means unknown size.
			    	if (nSize == -1)
				    {
			    		nSize = hashFileSize.get(entry.getName()).intValue();
				    }
			
			    	byte[] tb = new byte[nSize];
			    	int rb = 0;
			    	int nChunk = 0;
			    	while ((nSize - rb) > 0)
			    	{
						nChunk = zis.read(tb, rb, nSize - rb);
			    		if (nChunk == -1)
			    		{
			    			break;
			    		}
			    		rb += nChunk;
			    	}
		
			    	// add to internal resource hashtable
			    	hashFileData.put(csEntryName, tb);
		    	}
		    }
		}
		catch (IOException e2)
		{
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return hashFileData;
	}
	
}


