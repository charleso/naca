/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.batchOOApi;

import jlib.misc.FileEndOfLine;
import nacaLib.varEx.VarBufferPos;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class WriteBufferExt extends VarBufferPos
{
	private int m_nVariableRecordWholeLength = -1;
	
	WriteBufferExt(int nSize)
	{
		super(nSize);
	}
	
	void resetCurrentPosition()
	{
		m_nAbsolutePosition = 0;
	}
	
	public void setVariableRecordWholeLength(int n)
	{
		m_nVariableRecordWholeLength = n;
	}
	
	public int getVariableRecordWholeLength()
	{
		return m_nVariableRecordWholeLength;
	}
	
	public int getRecordCurrentPosition()
	{
		return m_nAbsolutePosition;
	}
	
	void fillWriteAsPicX(String cs, int  nNbCharsToWrite)
	{
		// Fill the buffer using padding as Pic X fields 
		int nLength = 0;
		if(cs != null)
		{
			nLength = cs.length();
			if(nNbCharsToWrite < nLength)
				nLength = nNbCharsToWrite;
			cs.getChars(0, nLength, m_acBuffer, m_nAbsolutePosition);
		}
		if(nLength < nNbCharsToWrite)	// Padding with BLANK on the right
		{
			int nNbChars = nNbCharsToWrite-nLength;
			for(int n=0; n<nNbChars; n++)
				m_acBuffer[m_nAbsolutePosition + n] = ' ';
		}
	}
	
	String getString(int nSize)
	{
		if (m_nAbsolutePosition+nSize > m_acBuffer.length)
			nSize = m_nAbsolutePosition+nSize - m_acBuffer.length;
		if(m_nAbsolutePosition < m_acBuffer.length)
		{
			String cs = new String(m_acBuffer, m_nAbsolutePosition, nSize);			
			return cs ;
		}
		return "";
	}
	
	void advanceCurrentPosition(int nOffset)
	{
		m_nAbsolutePosition += nOffset;
	}
	
	public byte [] getAsByteArrayWithTrailingLF()
	{
		byte tBytes[] = new byte[m_nAbsolutePosition];  
		
		for(int n=0; n<m_nAbsolutePosition; n++)
		{
			tBytes[n] = (byte)m_acBuffer[n];
		}
		return tBytes;
	}
}
