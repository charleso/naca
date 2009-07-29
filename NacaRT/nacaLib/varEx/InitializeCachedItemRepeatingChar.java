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
package nacaLib.varEx;

import nacaLib.debug.BufferSpy;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class InitializeCachedItemRepeatingChar extends InitializeCachedItem
{
	InitializeCachedItemRepeatingChar(char cPad, int nPosition, int nNbchars)
	{
		m_cPad = cPad;
		m_nNbchars = nNbchars;
		m_nTemplatePosition = nPosition;
	}
	
	void apply(int nBaseAbsolutePosition, VarBufferPos varBufferPos, int nCurrentAbsolutePosition)	//, int nOffset)
	{
		int nOffsetOrigin = m_nTemplatePosition - nBaseAbsolutePosition;
		nCurrentAbsolutePosition += nOffsetOrigin;
		
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(varBufferPos.m_acBuffer, nCurrentAbsolutePosition, m_nNbchars);
		for(int n=0; n<m_nNbchars; n++)
		{
			varBufferPos.m_acBuffer[nCurrentAbsolutePosition++] = m_cPad;
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
	}
	
	private char m_cPad;
	private int m_nTemplatePosition = 0;
	private int m_nNbchars = 0;
}