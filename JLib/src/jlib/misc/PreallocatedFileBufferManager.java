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
public class PreallocatedFileBufferManager
{
	private byte[] m_tBytes = null;
	private int m_nOverheadSize = 1000;
	
	PreallocatedFileBufferManager()
	{
		m_nOverheadSize = 1000;
	}
	
	PreallocatedFileBufferManager(int nOverheadSize)
	{
		m_nOverheadSize = nOverheadSize;
	}
	
	protected byte[] checkBuffer(int nSize)
	{
		if(m_tBytes == null || m_tBytes.length < nSize)
			m_tBytes = new byte[nSize + m_nOverheadSize];
		return m_tBytes;
	}
	
	public byte[] getByteBuffer(int nSize)
	{
		checkBuffer(nSize);
		return m_tBytes;
	}
	
	byte[] getBytes()
	{
		return m_tBytes; 
	}
}
