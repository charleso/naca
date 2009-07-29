/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaTrans - Naca Transcoder v1.2.0.beta.1
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package utils;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

public class MissingFileManager
{
	private Hashtable<String, MissingFilePaths> m_arrMissingPaths = null;
	private static MissingFileManager m_missingFileManager = null;
	
	public static MissingFileManager getInstance()
	{
		if(m_missingFileManager == null)
			m_missingFileManager = new MissingFileManager(); 
		return m_missingFileManager;
	}
	
	public void addFileNotFound(String csFile, String csFullPathFileName)
	{
		if(m_arrMissingPaths == null)
			m_arrMissingPaths = new Hashtable<String, MissingFilePaths>();
		MissingFilePaths missingFilePaths = m_arrMissingPaths.get(csFile);
		if(missingFilePaths == null)
		{
			missingFilePaths = new MissingFilePaths(csFile);
			m_arrMissingPaths.put(csFile, missingFilePaths);
		}
		missingFilePaths.addPath(csFullPathFileName);
	}
	
	public void reset()
	{
		if(m_arrMissingPaths != null)
			m_arrMissingPaths.clear();
	}
	
	public void logMissingFiles()
	{
		String cs = toString();
		Transcoder.logInfo(cs);		
	}
	
	public String toString()
	{
		if(m_arrMissingPaths == null)
			return "";
		Collection<MissingFilePaths> col = m_arrMissingPaths.values();
		if(col == null)
			return "";
		
		String cs = "";
		Iterator<MissingFilePaths> iter = col.iterator();
		while(iter.hasNext())
		{
			MissingFilePaths missingFilePaths = iter.next();
			cs += missingFilePaths.toString();
			cs += "\n";
		}
		return cs;		
	}
}
