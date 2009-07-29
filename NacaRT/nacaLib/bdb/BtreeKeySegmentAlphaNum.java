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

import nacaLib.basePrgEnv.BaseResourceManager;
import jlib.misc.AsciiEbcdicConverter;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class BtreeKeySegmentAlphaNum extends BtreeKeySegment
{
	private boolean m_bCompareInEbcdic = false;
	
	public BtreeKeySegmentAlphaNum(int nKeyPositionInData, int nKeyPositionInKey, int nKeyLength, boolean bAscending)
	{
		super(nKeyPositionInData, nKeyPositionInKey, nKeyLength, bAscending);
		m_bCompareInEbcdic = BaseResourceManager.getComparisonInEbcdic(); 
	}
	
	int compare(byte tby1[], byte tby2[])
	{
		byte b1, b2;
		int nPos = m_nKeyPosition;
		for (int n=0; n<m_nKeyLength; n++, nPos++)
		{
		    b1 = tby1[nPos];
		    b2 = tby2[nPos];
		    if (b1 == b2)
		    	continue;
		    else
		    {
		    	// Remember, bytes are signed, so convert to shorts so that we effectively do an unsigned byte comparison.
		    	int n1 = b1 & 0xff;
		    	int n2 = b2 & 0xff;
		    	
				if(m_bCompareInEbcdic != m_bFileInEbcdic)	// encoding is different from collating seq
				{
		    		if(!m_bFileInEbcdic)	// File is in ascii and collating seq is ecbdic
		    		{
				    	n1 = AsciiEbcdicConverter.getEbcdicChar((char)n1);
				    	n2 = AsciiEbcdicConverter.getEbcdicChar((char)n2);
		    		}
		    		else	// File is ebcdic and collating seq is ascii 
		    		{
				    	n1 = AsciiEbcdicConverter.getAsciiChar((char)n1);
				    	n2 = AsciiEbcdicConverter.getAsciiChar((char)n2);
		    		}
				}
				if(n1 < n2)					
			    	return m_bAscending ? -1 : 1;
		    	return m_bAscending ? 1 : -1;
		    }
		}
		return 0;
	}
}
