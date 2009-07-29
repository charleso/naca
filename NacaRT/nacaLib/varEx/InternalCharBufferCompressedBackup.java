/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
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

import nacaLib.debug.BufferSpy;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class InternalCharBufferCompressedBackup
{
	public InternalCharBufferCompressedBackup(InternalCharBuffer internalCharBufferSource)
	{
		copyFrom(internalCharBufferSource);
	}
	
	private void copyFrom(InternalCharBuffer internalCharBufferSource)
	{
		m_abBuffer = new byte[internalCharBufferSource.m_acBuffer.length];
		for(int n=0; n<m_abBuffer.length; n++)
		{
			m_abBuffer[n] = (byte)internalCharBufferSource.m_acBuffer[n];
		}
	}
	
	public int getBufferSize()
	{
		if(m_abBuffer != null)
			return m_abBuffer.length;
		return 0;
	}
	
	public void prepareAutoRemoval()
	{
		m_abBuffer = null;
	}
	
	
	public byte m_abBuffer[] = null;
}
