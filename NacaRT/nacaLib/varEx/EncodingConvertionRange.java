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
package nacaLib.varEx;

import jlib.misc.AsciiEbcdicConverter;
import jlib.misc.LineRead;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class EncodingConvertionRange
{
	private static final byte BLANK_EBCDIC = (byte)0x40;
	private static final byte BLANK_ASCII = (byte)0x20;
	
	private int m_nPosition = 0;
	private int m_nLength = 0;
	private boolean m_bConvertOnlyIfBlank = false;
	private boolean m_bConvertPrint = false;
	
	public int set(int nPosition, int nLength)
	{
		m_nPosition = nPosition;
		m_nLength = nLength;
		return m_nPosition + m_nLength;  
	}
	
	public void setConvertOnlyIfBlank(boolean bConvertOnlyIfBlank)
	{
		m_bConvertOnlyIfBlank = bConvertOnlyIfBlank;
	}
	
	public void setConvertPrint(boolean bConvertPrint)
	{
		m_bConvertPrint = bConvertPrint;
	}
	
	boolean endsJustBefore(int nPosition)
	{
		if(m_nPosition + m_nLength == nPosition)
			return true;
		return false;
	}
	
	public int append(int nLength)
	{
		m_nLength += nLength;
		return m_nPosition + m_nLength;
	}
	
	public void convertEbcdicToAscii(VarBase varDest, int nLastPosToConvert)
	{
		int nLength = m_nLength;
		int nLastPos = m_nPosition + m_nLength -1;
		if(nLastPos > nLastPosToConvert)
			nLength = nLastPosToConvert - m_nPosition; 
		if(nLength > 0)
			varDest.m_bufferPos.convertEbcdicToAscii(m_nPosition, nLength);
	}
	
	public void convertEbcdicToAscii(byte tbyDest[], int nOffsetDest, int nMaxLengthDest)
	{	
		int nLength = Math.min(nMaxLengthDest, m_nLength);
		swapByteEbcdicToAscii(tbyDest, m_nPosition-nOffsetDest, nLength);	
	}
	public void convertAsciiToEbcdic(byte tbyDest[], int nOffsetDest, int nMaxLengthDest)
	{
		int nLength = Math.min(nMaxLengthDest, m_nLength);
		swapByteAsciiToEbcdic(tbyDest, m_nPosition-nOffsetDest, nLength);	
	}
	
	public void convertEbcdicToAscii(LineRead lineRead)
	{
		int nLength = Math.min(lineRead.getTotalLength() - m_nPosition, m_nLength);
		swapByteEbcdicToAscii(lineRead.getBuffer(), lineRead.getOffset()+m_nPosition, nLength);	
	}
	public void convertAsciiToEbcdic(LineRead lineRead)
	{
		int nLength = Math.min(lineRead.getTotalLength() - m_nPosition, m_nLength);
		swapByteAsciiToEbcdic(lineRead.getBuffer(), lineRead.getOffset()+m_nPosition, nLength);	
	}
	
	public int getPosition()
	{
		return m_nPosition;
	}
	
	public boolean isConvertOnlyIfBlank()
	{
		return m_bConvertOnlyIfBlank;
	}
	
	public boolean isConvertPrint()
	{
		return m_bConvertPrint;
	}
	
	private void swapByteEbcdicToAscii(byte tBytesData[], int nOffset, int nLength)
	{
		if (m_bConvertOnlyIfBlank)
			if (!isAll(tBytesData, nOffset, nLength, BLANK_EBCDIC)) return;
		if (m_bConvertPrint)
			AsciiEbcdicConverter.swapByteEbcdicToAsciiPrintAFP(tBytesData, nOffset, nLength);
		else
			AsciiEbcdicConverter.swapByteEbcdicToAscii(tBytesData, nOffset, nLength);
	}

	private void swapByteAsciiToEbcdic(byte tBytesData[], int nOffset, int nLength)
	{
		if (m_bConvertOnlyIfBlank)
			if (!isAll(tBytesData, nOffset, nLength, BLANK_ASCII)) return;
		if (m_bConvertPrint)
			AsciiEbcdicConverter.swapByteAsciiToEbcdicPrintAFP(tBytesData, nOffset, nLength);
		else
			AsciiEbcdicConverter.swapByteAsciiToEbcdic(tBytesData, nOffset, nLength);
	}

	private boolean isAll(byte tBytesData[], int nOffset, int nLength, byte byPattern)
	{
		for(int n=0; n<nLength; n++)
		{
			if (tBytesData[n+nOffset] != byPattern)
				return false; 
		}
		return true;
	}
}
