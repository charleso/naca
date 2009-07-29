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

import jlib.misc.LittleEndingSignBinaryBufferStorage;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class BtreeKeySegmentSignBinary extends BtreeKeySegment
{
	public BtreeKeySegmentSignBinary(int nKeyPositionInData, int nKeyPositionInKey, int nKeyLength, boolean bAscending)
	{
		super(nKeyPositionInData, nKeyPositionInKey, nKeyLength, bAscending);
	}
	
	int compare(byte tby1[], byte tby2[])
	{
		if(m_nKeyLength == 4)
		{
			int n1 = LittleEndingSignBinaryBufferStorage.readInt(tby1, m_nKeyPosition);
			int n2 = LittleEndingSignBinaryBufferStorage.readInt(tby2, m_nKeyPosition);
			if(n1 == n2)
				return 0;
			if(n1 < n2)
			{
		    	if(m_bAscending)
		    		return -1;
		    	return 1;
			}
	    	if(m_bAscending)
	    		return 1;
	    	return -1;
		}
		else if(m_nKeyLength == 8)
		{
			long l1 = LittleEndingSignBinaryBufferStorage.readLong(tby1, m_nKeyPosition);
			long l2 = LittleEndingSignBinaryBufferStorage.readLong(tby2, m_nKeyPosition);
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
		else if(m_nKeyLength == 2)
		{
			short s1 = LittleEndingSignBinaryBufferStorage.readShort(tby1, m_nKeyPosition);
			short s2 = LittleEndingSignBinaryBufferStorage.readShort(tby2, m_nKeyPosition);
			if(s1 == s2)
				return 0;
			if(s1 < s2)
			{
		    	if(m_bAscending)
		    		return -1;
		    	return 1;
			}
	    	if(m_bAscending)
	    		return 1;
	    	return -1;
		}		
		else if(m_nKeyLength == 1)
		{
			byte b1 = LittleEndingSignBinaryBufferStorage.readByte(tby1, m_nKeyPosition);
			byte b2 = LittleEndingSignBinaryBufferStorage.readByte(tby2, m_nKeyPosition);
			if(b1 == b2)
				return 0;
			if(b1 < b2)
			{
		    	if(m_bAscending)
		    		return -1;
		    	return 1;
			}
	    	if(m_bAscending)
	    		return 1;
	    	return -1;
		}
		// Unmannaged length
		return 0;
	}
}
