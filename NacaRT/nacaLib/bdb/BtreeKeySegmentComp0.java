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

import nacaLib.varEx.Pic9Comp0BufferSupport;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class BtreeKeySegmentComp0 extends BtreeKeySegment
{
	public BtreeKeySegmentComp0(int nKeyPositionInData, int nKeyPositionInKey, int nKeyLength, boolean bAscending)
	{
		super(nKeyPositionInData, nKeyPositionInKey, nKeyLength, bAscending);
	}
	
	int compare(byte tby1[], byte tby2[])
	{
		long l1, l2;
		
		if(m_bFileInEbcdic)
		{
			l1 = Pic9Comp0BufferSupport.getAsLongFromEbcdicBuffer(tby1, m_nKeyPosition, m_nKeyLength);
			l2 = Pic9Comp0BufferSupport.getAsLongFromEbcdicBuffer(tby2, m_nKeyPosition, m_nKeyLength);
		}
		else
		{
			l1 = Pic9Comp0BufferSupport.getAsLong(tby1, m_nKeyPosition, m_nKeyLength);
			l2 = Pic9Comp0BufferSupport.getAsLong(tby2, m_nKeyPosition, m_nKeyLength);
		}
		
		if(l1 == l2)
			return 0;
		if(l1 < l2)
		{
	    	if(m_bAscending)
	    		return -1;
	    	return 1;
		}
    	if(m_bAscending)
    		return 1;
    	return -1;
	}
}
