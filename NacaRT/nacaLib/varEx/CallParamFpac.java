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
 * @version $Id: CallParamFpac.java,v 1.1 2006/12/20 09:33:22 u930di Exp $
 */
public class CallParamFpac extends CCallParam
{
	public CallParamFpac(InternalCharBuffer charBufferSource)
	{
		int nLength = charBufferSource.getBufferSize() - 2;	// Exclude commeara length header 
		m_charBuffer = new InternalCharBuffer(nLength);
		m_charBuffer.copyBytes(0, nLength, 2, charBufferSource);
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
	
	private InternalCharBuffer m_charBuffer = null;
}
