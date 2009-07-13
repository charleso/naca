/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.basePrgEnv;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: FileManager.java,v 1.6 2007/06/04 14:40:21 u930bm Exp $
 */
public class FileManager
{
	FileManager()
	{
		m_hashFileManagerEntry = new Hashtable<String, FileManagerEntry>();
	}
	
	FileManagerEntry createFileManagerEntry(String csLogicalName)
	{
		FileManagerEntry entry = new FileManagerEntry();
		m_hashFileManagerEntry.put(csLogicalName, entry);
		return entry;
	}
	
	public FileManagerEntry getFileManagerEntry(String csLogicalName)
	{
		FileManagerEntry entry = m_hashFileManagerEntry.get(csLogicalName);
		if(entry == null)
			entry = createFileManagerEntry(csLogicalName);
		return entry;
	}
	
	public void autoCloseOpenFile()
	{
		if(m_hashFileManagerEntry != null)
		{
			Collection<FileManagerEntry> col = m_hashFileManagerEntry.values();
			Iterator<FileManagerEntry> iter = col.iterator();
			while(iter.hasNext())
			{
				FileManagerEntry fileManagerEntry = iter.next();
				String cs = fileManagerEntry.dumpRWStat();
				System.out.println(cs);
				fileManagerEntry.autoClose();
			}
			m_hashFileManagerEntry.clear();
		}
	}
	
	
	public void autoFlushOpenFile()
	{
		if(m_hashFileManagerEntry != null)
		{
			Collection<FileManagerEntry> col = m_hashFileManagerEntry.values();
			Iterator<FileManagerEntry> iter = col.iterator();
			while(iter.hasNext())
			{
				FileManagerEntry fileManagerEntry = iter.next();
				fileManagerEntry.autoFlush();
			}
		}
	}

	private Hashtable<String, FileManagerEntry> m_hashFileManagerEntry = null;  
}
