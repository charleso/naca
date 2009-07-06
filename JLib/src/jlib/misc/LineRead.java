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
 * @version $Id: LineRead.java,v 1.11 2007/10/25 15:13:11 u930di Exp $
 */
public class LineRead
{
	private byte m_tbLine[] = null; 
	private int m_nTotalLength = 0;
	private int m_nBodyLength = 0;
	private int m_nOffset = 0;
	
	LineRead()
	{
	}
	
	void resetAndGaranteeBufferStorage(int nMinBufferStorageLength, int nBufferStorageLengthToAlloc)
	{
		if(m_tbLine == null || m_tbLine.length < nMinBufferStorageLength)
		{
			m_tbLine = new byte[nBufferStorageLengthToAlloc];
		}
		m_nTotalLength = 0;
		m_nBodyLength = 0;
		m_nOffset = 0;
	}
	
	int readAndConvertHeaderVHToVBMode()
	{
		// Header read in VH mode are hh ll 00 00; and the length 0xhhll includes the header itself
		// Header read in VB mode are hh xx yy ll; and the length 0xhhxxyyll does not include the header itself
		int nLength = getAsLittleEndingUnsignBinaryShort();
		nLength -= 4;	// Header in VB mode do not include header length
		LittleEndingUnsignBinaryBufferStorage.writeInt(m_tbLine, nLength, m_nOffset);
		return nLength;
	}
	
	void append(LineRead lineSource)
	{
		int nSourceOffset = lineSource.getOffset();
		int nSourceLength = lineSource.getTotalLength();
		fill(lineSource.getBuffer(), nSourceOffset, nSourceLength, m_nTotalLength);
		m_nTotalLength += nSourceLength;
		m_nBodyLength += nSourceLength;
	}
	
	private void fill(byte tReadBytes[], int nSourceOffset, int nSourceLength, int nOffsetDest)
	{
		for(int n=0; n<nSourceLength; n++)
		{
			m_tbLine[nOffsetDest++] = tReadBytes[nSourceOffset++];
		}
	}
	
	public void shiftOffset(int nShiftLength)
	{
		m_nTotalLength -= nShiftLength;
		m_nBodyLength -= nShiftLength;
		m_nOffset += nShiftLength;
	}
	
	void set(byte tReadBytesAHead[], int nBodyFirstPositionInReadAHead, int nBodyLength, int nHeaderLength)
	{
		m_tbLine = tReadBytesAHead;
		m_nOffset = nBodyFirstPositionInReadAHead - nHeaderLength;
		m_nBodyLength = nBodyLength ;		
		m_nTotalLength = nBodyLength + nHeaderLength;
		int nDest = m_nOffset; 
		for(int n=0; n<nHeaderLength; n++)
		{
			m_tbLine[nDest++] = 0;
		}
	}
	
	public String getChunkAsString()
	{
		String cs = new String(m_tbLine, m_nOffset, m_nTotalLength);
		return cs;
	}
	
	public byte [] getBuffer()
	{
		return m_tbLine;
	}
	
	public byte [] getBufferCopy()
	{
		byte by[] = new byte[m_nTotalLength];
		int nSource = m_nOffset;
		for(int n=0; n<m_nTotalLength; n++)
		{
			by[n] = m_tbLine[nSource++];
		}
		return by;
	}
	
	public int getOffset()
	{
		return m_nOffset; 
	}
	
	public int getTotalLength()
	{
		return m_nTotalLength; 
	}
	
	public int getBodyLength()
	{
		return m_nBodyLength;
	}
	
	public int getBufferLength()
	{
		return m_tbLine.length;
	}
	
	public boolean manageTrailingLF()
	{
		if(m_tbLine[m_nOffset+m_nTotalLength-1] == 0x0A)
		{
			m_nBodyLength--;	// Do not use the trailing LF; just consume it
			m_nTotalLength--;
			return true;
		}		
		return false;
	}
	
	public boolean isTrailingLF()
	{
		// PJD Next line was if(m_tbLine[m_nOffset+m_nTotalLength-1] == 0x0A); 
		// the -1 is wrong as manageTrailingLF() must have already been called previously on this LineReadObject, so m_nTotalLength was decremented.
		if(m_tbLine[m_nOffset+m_nTotalLength] == 0x0A)	 
			return true;
		return false;
	}
	
	public int getAsLittleEndingUnsignBinaryInt()
	{
		if(m_nBodyLength >= 4)
			return (int)LittleEndingUnsignBinaryBufferStorage.readInt(m_tbLine, m_nOffset);
		return -1;	// Error
	}
	
	public int getAsLittleEndingUnsignBinaryShort()
	{
		if(m_nBodyLength >= 2)
			return (int)LittleEndingUnsignBinaryBufferStorage.readShort(m_tbLine, m_nOffset);
		return -1;	// Error
	}
	
	public void setDataLengthStartingAt0(int nLength)
	{
		m_nBodyLength = nLength;
		m_nTotalLength = nLength;
		m_nOffset = 0;
	}
}
