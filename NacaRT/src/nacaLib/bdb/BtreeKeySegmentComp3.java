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

import nacaLib.varEx.Pic9Comp3BufferSupport;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: BtreeKeySegmentComp3.java,v 1.13 2007/01/30 10:57:48 u930bm Exp $
 */
public class BtreeKeySegmentComp3 extends BtreeKeySegment
{
	private int m_nNbDigitInteger = 0;
	public BtreeKeySegmentComp3(int nKeyPositionInData, int nKeyPositionInKey, int nKeyLength, boolean bAscending)
	{
		super(nKeyPositionInData, nKeyPositionInKey, nKeyLength, bAscending);
		m_nNbDigitInteger = (m_nKeyLength-1) * 2;
	}
	
	int compare(byte tby1[], byte tby2[])
	{
    	int nPos = m_nKeyPosition + m_nKeyLength -1;
    	// Compare Sign
    	
    	boolean b1 = Pic9Comp3BufferSupport.isNegative(tby1[nPos]);
    	boolean b2 = Pic9Comp3BufferSupport.isNegative(tby2[nPos]);
    	if(b1 != b2)
    	{
    		if(b1)	// tb1 is <0 ; tb2 is then > 0
    			return (m_bAscending) ? -1 : 1;
    		return (m_bAscending) ? 1 : -1;	// tb1 is > 0, tb2 is < 0
    	}
    	// They have the same sign
    	// byte per byte comparison
    	nPos = m_nKeyPosition;
    	byte by1, by2;
    	for(int n=0; n<m_nKeyLength; n++, nPos++)
    	{
    		by1 = tby1[nPos];
    		by2 = tby2[nPos];
    		if(by1 == by2)
    			continue;

    		int n1 = by1;
    		if(n1 < 0)
    			n1 += 256;
    		
    		int n2 = by2;
    		if(n2 < 0)
    			n2 += 256;
    		
    		if(n1 < n2)
    			return (m_bAscending) ? -1 : 1;
    		return (m_bAscending) ? 1 : -1;
    	}
    	return 0;

//    	int n = 0;
//		long l1 = Pic9Comp3BufferSupport.getAsLong(tby1, m_nKeyPosition, m_nNbDigitInteger, m_nKeyLength);
//		long l2 = Pic9Comp3BufferSupport.getAsLong(tby2, m_nKeyPosition, m_nNbDigitInteger, m_nKeyLength);
//
//		if(l1 == l2)
//			n = 0;
//		else if(l1 < l2)
//		{
//	    	if(m_bAscending)
//	    		n = -1;
//	    	else
//	    		n = 1;
//		}
//		else // if(l1 > l2)
//		{
//			if(m_bAscending)
//	    		n = 1;
//	    	else
//	    		n = -1;
//		}
//
//    	return n;
	}
}
