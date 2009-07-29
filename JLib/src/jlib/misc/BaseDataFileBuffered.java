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
/**
 * 
 */
package jlib.misc;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
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
