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
 * Created on 13 mai 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import nacaLib.debug.BufferSpy;
import jlib.misc.AsciiEbcdicConverter;
import jlib.misc.FileEndOfLine;
import jlib.misc.LineRead;


/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VarBuffer extends InternalCharBuffer
{
	public VarBuffer()
	{	
	}

	VarBuffer(VarBuffer varBufferMaster)
	{
		super();
		shareDataBufferFrom(varBufferMaster);
	}
	
	public VarBuffer(char [] acBuffer)
	{
		super(acBuffer);
	}
	
	public VarBuffer(int nSize)
	{
		super(nSize);
	}
	
//	public VarBase getVarFullName(int nId)
//	{
//		return getProgramManager().getVarFullName(nId);
//	}
	
//	public VarBase  getVarFullName(VarDefBuffer varDef)
//	{		
//		String csName = varDef.getFullName(getProgramManager().getSharedProgramInstanceData());
//		return getProgramManager().getVarFullName(csName);
		
//		int nId = varDef.getId();
//		return getProgramManager().getVarFullName(nId);
//	}
	
//	public VarBase getVarFullNameUpperCase(String csName)
//	{
//		String csNameUpperCase = csName.toUpperCase();
//		return getProgramManager().getVarFullNameUpperCase(csNameUpperCase);
//	}
	
//	public VarBase getVarFullNameUpperCase(VarDefBuffer varDef)
//	{		
//		String csNameUpperCase = varDef.getFullNameUpperCase();
//		return getProgramManager().getVarFullNameUpperCase(csNameUpperCase);
//	}
	
	
//	public void addMapAssociatedSemanticContext(Edit edit, String csSemanticContext)
//	{
//		if(m_arrMapAssociatedSemanticContext == null)
//			m_arrMapAssociatedSemanticContext = new ArrayList();
//		CEditSemanticContextMapAssoc EditSemanticContextMapAssoc = new CEditSemanticContextMapAssoc(edit, csSemanticContext);
//		m_arrMapAssociatedSemanticContext.add(EditSemanticContextMapAssoc);
//	}
	
	public String toString()
	{
		return m_acBuffer.toString();
	}
	

//	public int writeCopy(int nPositionDest, int nPositionSource, int nNbCharsToCopy)
//	{
//		for(int n=0; n<nNbCharsToCopy; n++, nPositionSource++, nPositionDest++)
//		{
//			m_acBuffer[nPositionDest] = m_acBuffer[nPositionSource];
//		}
//		return nPositionDest;
//	}
//		 	
	public int copyBytesFromSource(int nPositionDest, InternalCharBuffer Source, int nPositionSource, int nNbCharsToCopy)
	{
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nPositionDest, nNbCharsToCopy);
		for(int n=0; n<nNbCharsToCopy; n++)
		{
			m_acBuffer[nPositionDest++] = Source.m_acBuffer[nPositionSource++];
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
		return nPositionDest;
	}
	
//	public void copyInternalData(int nPositionDest, VarBufferPos Source, int nNbCharsToCopy)
//	{
//		int nPositionSource = Source.m_nAbsolutePosition;
//		for(int n=0; n<nNbCharsToCopy; n++)
//		{
//			m_acBuffer[nPositionDest++] = Source.m_acBuffer[nPositionSource++];
//		}
//	}
	
	public void copyBytesFromSource(int nPositionDest, InternalCharBuffer sourceCharBuffer)
	{
		int nNbCharsToCopy = sourceCharBuffer.m_acBuffer.length;
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nPositionDest, nNbCharsToCopy);
		for(int nSource=0; nSource<nNbCharsToCopy; nSource++, nPositionDest++)
		{
			m_acBuffer[nPositionDest] = sourceCharBuffer.m_acBuffer[nSource];
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
	}
//	
//	public void setWithNoConvertEbcdicToUnicode(byte tBytesSource[], int nLength)
//	{
//		for(int n=0; n<nLength; n++)
//		{
//			int nByte = tBytesSource[n];
//			if(nByte < 0)
//				nByte += 256; 
//			m_acBuffer[n] = (char)nByte;
//		}
//	}
	
//	public void setWithNoConvertEbcdicToUnicodeAtOffsetDest(byte tBytesSource[], int nOffsetDest, int nLength)
//	{
//		for(int n=0; n<nLength; n++)
//		{
//			int nByte = tBytesSource[n];
//			if(nByte < 0)
//				nByte += 256; 
//			m_acBuffer[nOffsetDest+n] = (char)nByte;
//		}
//	}
	
	public int setFromLineRead(LineRead lineRead, int nOffsetDest)
	{
		int nSourceOffset = lineRead.getOffset();
		int nSourceLength = lineRead.getTotalLength();
		byte bufSource[] = lineRead.getBuffer();
		
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nOffsetDest, nSourceLength);
		for(int n=0; n<nSourceLength; n++)
		{
			int nByte = bufSource[nSourceOffset + n];
			if(nByte < 0)
				nByte += 256; 		
			m_acBuffer[nOffsetDest + n] = (char) nByte;
		}		
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
		if(nSourceLength > 0)
		{
			if(m_acBuffer[nOffsetDest + nSourceLength - 1] == FileEndOfLine.LF)
				return nSourceLength-1;	// Return length excluding ending LF
		}
		return nSourceLength;
	}
	
	public int getFirstEndOfLinePosition(byte byEOL)
	{
		for(int n=0; n<m_acBuffer.length; n++)
		{
			byte b = (byte)m_acBuffer[n];
			if(b == byEOL)
				return n;
		}
		return m_acBuffer.length-1;
	}
	
//	public char [] getCharArray()
//	{
//		return m_acBuffer;
//	}
	
	
	public void dumpHexa(int nPosition, int nLength)
	{
		System.out.println("dumpHexa from position=" + nPosition + ", length="+nLength); 
		String cs = "" + nPosition + ": ";
		int n=0; 
		while(n<nLength)
		{			
			char c = m_acBuffer[n+nPosition];
			String csHexa = AsciiEbcdicConverter.getHexaValue(c);
			cs += "0x" + csHexa + " ";
			n++;
			if((n % 8) == 0)
			{
				System.out.println(cs);
				cs = "" + n + nPosition + ": ";
			}				
		}
		if((n % 8) != 0)
			System.out.println(cs);
	}
	
}
