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
 * @version $Id: BtreeKeySegmentUnsignedBinaryOrPacked.java,v 1.2 2007/01/09 14:41:39 u930di Exp $
 */
public class BtreeKeySegmentUnsignedBinaryOrPacked extends BtreeKeySegment
{
	public BtreeKeySegmentUnsignedBinaryOrPacked(int nKeyPositionInData, int nKeyPositionInKey, int nKeyLength, boolean bAscending)
	{
		super(nKeyPositionInData, nKeyPositionInKey, nKeyLength, bAscending);
	}
	
	int compare(byte tby1[], byte tby2[])
	{
		int n1, n2;
		int nPos = m_nKeyPosition;
		for (int n=0; n<m_nKeyLength; n++, nPos++)
		{
		    n1 = tby1[nPos] & 0xff;	// Get unsigned
		    n2 = tby2[nPos] & 0xff;
		    if(n1 == n2)
		    	continue;
			if(n1 < n2)
		    	return m_bAscending ? -1 : 1;
	    	return m_bAscending ? 1 : -1;
		}
		return 0;
//		
//		for(int n=0; n<m_nKeyLength; n++)
//		{			
//			int n1 = tby1[m_nKeyPosition + n];
//			if(n1 < 0)
//				n1 += 256;
//			
//			int n2 = tby2[m_nKeyPosition + n];
//			if(n2 < 0)
//				n2 += 256;
//			
//			if(n1 < n2)
//				return -1;
//			if(n1 > n2)
//				return 1;
//		}
//		return 0;
	}
	
	void convertKeySegmentToAscii(byte tbyKey[], int nKeyPosition, int nKeyLength)
	{
	}
}
