/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.varEx;

import jlib.log.Asserter;

public class SortItemKeySegment
{
	SortItemKeySegment(boolean bAscending)
	{
		m_bAscending = bAscending;
	}
	
	void copyChars(char [] tChars, int nStart, int nLength)
	{
		m_tcKeyValue = new char [nLength];
		for(int n=0; n<nLength; n++)
		{
			m_tcKeyValue[n] = tChars[n + nStart];
		}
	}
	
	int compare(SortItemKeySegment sortItemKeySegment2)
	{
		Asserter.assertIfFalse(m_bAscending == sortItemKeySegment2.m_bAscending);
		Asserter.assertIfFalse(m_tcKeyValue.length == sortItemKeySegment2.m_tcKeyValue.length);
		
		int nLength = m_tcKeyValue.length;
		for(int n=0; n<nLength; n++)
		{
			if(m_tcKeyValue[n] < sortItemKeySegment2.m_tcKeyValue[n])
			{
				if(m_bAscending)
					return -1;
				else
					return 1;
			}
			if(m_tcKeyValue[n] > sortItemKeySegment2.m_tcKeyValue[n])
			{
				if(m_bAscending)
					return 1;
				else
					return -1;
			}
		}
		return 0;
	}
	
	boolean m_bAscending = true;
	char m_tcKeyValue[] = null;
}
