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
package nacaLib.bdb;

import jlib.misc.LittleEndingUnsignBinaryBufferStorage;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class BtreeKeySegmentUnsignBinary extends BtreeKeySegment
{
	public BtreeKeySegmentUnsignBinary(int nKeyPositionInData, int nKeyPositionInKey, int nKeyLength, boolean bAscending)
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
//		if(m_nKeyLength == 4)
//		{
//			long l1 = LittleEndingUnsignBinaryBufferStorage.readInt(tby1, m_nKeyPosition);
//			long l2 = LittleEndingUnsignBinaryBufferStorage.readInt(tby2, m_nKeyPosition);
//			
//			if(l1 == l2)
//				return 0;
//			if(l1 < l2)
//			{
//		    	if(m_bAscending)
//		    		return -1;
//		    	return 1;
//			}
//			// n1 > n2) 
//	    	if(m_bAscending)
//	    		return 1;
//	    	return -1;
//		}
//		else if(m_nKeyLength == 8)
//		{
//			long l1 = LittleEndingUnsignBinaryBufferStorage.readLong(tby1, m_nKeyPosition);
//			long l2 = LittleEndingUnsignBinaryBufferStorage.readLong(tby2, m_nKeyPosition);
//			if(l1 == l2)
//				return 0;
//			if(l1 < l2)
//			{
//		    	if(m_bAscending)
//		    		return -1;
//		    	return 1;
//			}
//	    	if(m_bAscending)
//	    		return 1;
//	    	return -1;
//		}
//		else if(m_nKeyLength == 2)
//		{
//			int n1 = LittleEndingUnsignBinaryBufferStorage.readShort(tby1, m_nKeyPosition);
//			int n2 = LittleEndingUnsignBinaryBufferStorage.readShort(tby2, m_nKeyPosition);
//			if(n1 == n2)
//				return 0;
//			if(n1 < n2)
//			{
//		    	if(m_bAscending)
//		    		return -1;
//		    	return 1;
//			}
//	    	if(m_bAscending)
//	    		return 1;
//	    	return -1;
//		}		
//		else if(m_nKeyLength == 1)
//		{
//			int n1 = LittleEndingUnsignBinaryBufferStorage.readByte(tby1, m_nKeyPosition);
//			int n2 = LittleEndingUnsignBinaryBufferStorage.readByte(tby2, m_nKeyPosition);
//			if(n1 == n2)
//				return 0;
//			if(n1 < n2)
//			{
//		    	if(m_bAscending)
//		    		return -1;
//		    	return 1;
//			}
//	    	if(m_bAscending)
//	    		return 1;
//	    	return -1;
//		}
//		// Unmannaged length
//		return 0;
	}
	
	void convertKeySegmentToAscii(byte tbyKey[], int nKeyPosition, int nKeyLength)
	{
	}
}
