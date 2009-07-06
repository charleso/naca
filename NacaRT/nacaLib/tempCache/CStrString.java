/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.tempCache;

/**
 * 
 */


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: CStrString.java,v 1.2 2007/01/09 15:54:59 u930di Exp $
 */
public class CStrString extends CStr
{
	// Char buffer is always reusable
	CStrString()
	{
		super();
	}
	
	public void set(CStr csIn, char cPad, int nNbCharDest)
	{
		if(m_acBuffer == null)
			m_acBuffer = new char[nNbCharDest];
		else if(m_acBuffer.length < nNbCharDest)
			m_acBuffer = new char[nNbCharDest];
		
		m_nLength = nNbCharDest;
		m_nStartPos = 0;
				
		int nLength = 0;
		if(csIn != null)
		{
			nLength = Math.min(csIn.length(), nNbCharDest);
			for(int n=0; n<nLength; n++)
			{
				m_acBuffer[n] = csIn.charAt(n);
			}
		}
		while(nLength < nNbCharDest)	// Pad on the right
		{
			m_acBuffer[nLength] = cPad;
			nLength++;
		}			
	}
}
