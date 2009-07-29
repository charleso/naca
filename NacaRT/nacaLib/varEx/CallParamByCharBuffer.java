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
/*
 * Created on 7 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CallParamByCharBuffer extends CCallParam
{
	public CallParamByCharBuffer(InternalCharBuffer charBuffer)
	{
		m_charBuffer = charBuffer;
	}
	
	public int getParamLength()
	{
		if(m_charBuffer != null)
			return m_charBuffer.getBufferSize();
		return 0;
	}
	
	public void MapOn(Var varLinkageSection)
	{
		int nNbCharsToCopy = m_charBuffer.getBufferSize();
		int nPositionDest = varLinkageSection.getBodyAbsolutePosition();
		int nBuffetDestSize = varLinkageSection.m_bufferPos.getBufferSize();
		//TODO Contrôle si contrôle sur longueur du buffer ou longueur de varLinkageSection
		if(nPositionDest + nNbCharsToCopy > nBuffetDestSize)
		{
			assertIfFalse(false, "Assertion: CallParamByCharBuffer.MapOn; Destination Buffer too small; Source length=" + nNbCharsToCopy + " Destination length=" + nBuffetDestSize);
		}
		
		varLinkageSection.m_bufferPos.writeRepeatingCharAt(nPositionDest, ' ', varLinkageSection.getLength());
		varLinkageSection.m_bufferPos.copyBytes(nPositionDest, nNbCharsToCopy, 0, m_charBuffer);
	}
	
	public Var getCallerSourceVar()
	{
		return null;
	}
	
	private InternalCharBuffer m_charBuffer = null;
}
