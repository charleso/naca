/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.varEx;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: FileDescriptorAutoCloseManager.java,v 1.1 2006/05/19 06:24:25 cvsadmin Exp $
 */
public class FileDescriptorAutoCloseManager
{
	public FileDescriptorAutoCloseManager()
	{		
	}
	
	public void reportFileDescriptorStatus(FileDescriptor fileDescriptor, FileDescriptorOpenStatus status)
	{
		if(m_hashFileDescriptor != null && !m_bIsInAutoClose)
		{
			m_hashFileDescriptor.remove(fileDescriptor);
			m_hashFileDescriptor.put(fileDescriptor, status);
		}
	}
	
	public void registerFileDescriptor(FileDescriptor fileDescriptor)
	{
		if(m_hashFileDescriptor == null)
			m_hashFileDescriptor = new Hashtable<FileDescriptor, FileDescriptorOpenStatus>();
		m_hashFileDescriptor.put(fileDescriptor, FileDescriptorOpenStatus.CLOSE);
	}
	
	public void autoClose()
	{
		if(m_hashFileDescriptor != null)
		{
			m_bIsInAutoClose = true;
			Set<Entry<FileDescriptor, FileDescriptorOpenStatus> > entries = m_hashFileDescriptor.entrySet();
			Iterator<Entry<FileDescriptor, FileDescriptorOpenStatus> > iter = entries.iterator();
			while (iter.hasNext())
			{
				Entry<FileDescriptor, FileDescriptorOpenStatus> entry = iter.next();
				if(entry.getValue() == FileDescriptorOpenStatus.OPEN)
				{
					FileDescriptor fileDescriptor = entry.getKey();
					fileDescriptor.close();
				}
			}
			m_hashFileDescriptor.clear();
			m_bIsInAutoClose = false;
		}		
	}
	
	private Hashtable<FileDescriptor, FileDescriptorOpenStatus> m_hashFileDescriptor  = null;
	private boolean m_bIsInAutoClose = false;
}
