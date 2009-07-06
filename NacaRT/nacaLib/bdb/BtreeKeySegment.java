/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.bdb;


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: BtreeKeySegment.java,v 1.14 2007/01/09 14:41:39 u930di Exp $
 */
public abstract class BtreeKeySegment
{
	protected int m_nKeyPositionInData = 0;
	protected int m_nKeyPosition = 0;
	protected int m_nKeyLength = 0;
	protected boolean m_bAscending = true;	// Ascending
	protected boolean m_bFileInEbcdic = false;
	
	public BtreeKeySegment(int nKeyPositionInData, int nKeyPositionInKey, int nKeyLength, boolean bAscending)
	{
		m_nKeyPositionInData = nKeyPositionInData; 
		m_nKeyPosition = nKeyPositionInKey;
		m_nKeyLength = nKeyLength;
		m_bAscending = bAscending;
	}

	public void setDescending()
	{
		m_bAscending = false;
	}
	
	public void setAscending()
	{
		m_bAscending = true;
	}
	
	int getLength()
	{
		return m_nKeyLength; 
	}
		
	protected int appendKeySegmentData(byte tbyData[], int nOffset, byte tbyKey[])	//, boolean bConvertKeyToAscii)
	{
		int nDest = m_nKeyPosition;
		int nSource = m_nKeyPositionInData + nOffset;
		for(int n=0; n<m_nKeyLength; n++, nDest++, nSource++)
		{				
			tbyKey[nDest] = tbyData[nSource];
		}
		return m_nKeyPosition + m_nKeyLength;
	}
	
	void setFileInEncoding(boolean bFileInEbcdic)
	{
		m_bFileInEbcdic = bFileInEbcdic;
	}
	
	abstract int compare(byte tby1[], byte tby2[]);
}
