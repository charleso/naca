/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.misc;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: BaseDataFileBuffered.java,v 1.4 2006/07/31 11:58:46 u930di Exp $
 */
public abstract class BaseDataFileBuffered extends BaseDataFile
{
	private PreallocatedFileBufferManager m_buffer = null;
	//private PreallocatedFileBufferManager m_alternateBuffer = null;
		
	public byte[] getByteBuffer(int nSize)
	{
		if(m_buffer == null)
			m_buffer = new PreallocatedFileBufferManager();
		return m_buffer.checkBuffer(nSize);
	}
	
//	public byte[] getAlternateByteBuffer(int nSize)
//	{
//		if(m_alternateBuffer == null)
//			m_alternateBuffer = new PreallocatedFileBufferManager();
//		return m_alternateBuffer.checkBuffer(nSize);
//	}
}
