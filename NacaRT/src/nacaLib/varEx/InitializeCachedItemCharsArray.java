/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.varEx;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class InitializeCachedItemCharsArray extends InitializeCachedItem
{
	InitializeCachedItemCharsArray(char tChars[], int nPosition)
	{
		m_tChars = tChars;
		m_nTemplatePosition = nPosition;
	}
	
	void apply(int nBaseAbsolutePosition, VarBufferPos varBufferPos, int nCurrentAbsolutePosition)	//, int nOffset)
	{
		int nSize = m_tChars.length;
		//int nPosDest = nOffset + nCurrentAbsolutePosition;
		
		int nOffsetOrigin = m_nTemplatePosition - nBaseAbsolutePosition;
		nCurrentAbsolutePosition += nOffsetOrigin; 
		
		for(int n=0; n<nSize; n++, nCurrentAbsolutePosition++)
		{
			varBufferPos.m_acBuffer[nCurrentAbsolutePosition] = m_tChars[n]; 
		}
	}
	
	private char [] m_tChars;
	private int m_nTemplatePosition = 0;
}
