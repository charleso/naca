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
package nacaLib.varEx;

import nacaLib.debug.BufferSpy;

public class Pic9Comp0BufferSupport
{
//	public Pic9Comp0BufferSupport()
//	{
//	}
//	
//	void set(VarBufferPos buffer, int nPosition, int nNbDigitInteger, int nTotalSize)
//	{		
//		m_buffer = buffer;
//		m_nPosition = nPosition;
//		m_nTotalSize = nTotalSize;
//		m_nNbDigitInteger = nNbDigitInteger;
//	}


	static public void setFromRightToLeft(VarBufferPos buffer, int nPosition, int nNbDigitInteger, int nTotalSize, int nOffset, boolean bSigned, boolean bSignLeading, int nValue)
	{		
		int nMinIndex = nPosition+nOffset;
		if(bSigned && bSignLeading)
			nMinIndex++;
		
		char cSign = '+';
		if(nValue < 0)
		{
			nValue = -nValue;
			cSign = '-';
		}

		int nPosDigit = nPosition+nOffset+nNbDigitInteger-1;
		if(bSigned && !bSignLeading)
		{
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nPosDigit, 1);
			buffer.m_acBuffer[nPosDigit] = cSign;
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			nPosDigit--;
		}
		
		do
		{
			char cDigit = (char)((nValue % 10) + '0');
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nPosDigit, 1);
			buffer.m_acBuffer[nPosDigit] = cDigit;
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			nPosDigit--;
			nValue /= 10;
		}
		while (nValue != 0 && nPosDigit >= nMinIndex);
		
		// Fill leftmost 0
		while(nPosDigit >= nMinIndex)
		{
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nPosDigit, 1);
			buffer.m_acBuffer[nPosDigit] = '0';
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			nPosDigit--;
		}
		
		// Add optional sign		
		if(bSigned && bSignLeading)
		{
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nPosition+nOffset, 1);
			buffer.m_acBuffer[nPosition+nOffset] = cSign;
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
		}
	}
	
	static public void setFromRightToLeft(VarBufferPos buffer, int nPosition, int nNbDigitInteger, int nTotalSize, int nOffset, boolean bSigned, boolean bSignLeading, long lValue)
	{		
		int nMinIndex = nPosition+nOffset;
		if(bSigned && bSignLeading)
			nMinIndex = nPosition+nOffset+1;
		
		char cSign = '+';
		if(lValue < 0)
		{
			lValue = -lValue;
			cSign = '-';
		}

		int nPosDigit = nPosition+nOffset+nNbDigitInteger-1;
		if(bSigned && !bSignLeading)
		{
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nPosDigit, 1);
			buffer.m_acBuffer[nPosDigit] = cSign;
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			nPosDigit--;
		}
		
		do
		{
			char cDigit = (char)((lValue % 10) + '0');
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nPosDigit, 1);
			buffer.m_acBuffer[nPosDigit] = cDigit;
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			nPosDigit--;
			lValue /= 10;
		}
		while (lValue != 0 && nPosDigit >= nMinIndex);
		
		// Fill leftmost 0
		while(nPosDigit >= nMinIndex)
		{
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nPosDigit, 1);
			buffer.m_acBuffer[nPosDigit] = '0';
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			nPosDigit--;
		}
		
		// Add optional sign		
		if(bSigned && bSignLeading)
		{
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nPosition+nOffset, 1);
			buffer.m_acBuffer[nPosition+nOffset] = cSign;
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
		}
	}
	
	static public void setFromRightToLeftSignEmbedded(VarBufferPos buffer, int nPosition, int nNbDigitInteger, int nTotalSize, int nOffset, int nValue)
	{		
		// Fill the buffer with '0' on each byte
		//buffer.fillBlankComp0AtOffset(nTotalSize, nOffset);
		
		boolean bPositive = true;
		if(nValue < 0)
		{
			nValue = -nValue;
			bPositive = false;
		}

		int nNibblePos = nNbDigitInteger-1;
		int nDigitWithSign = (nValue % 10);
		if(bPositive)
			nDigitWithSign += 0xC0;
		else
			nDigitWithSign += 0xD0;
		
		int nMinPosition = nPosition + nOffset;
		int nDigitPosition = nMinPosition + nNibblePos;	
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nDigitPosition, 1);
		buffer.m_acBuffer[nDigitPosition] = (char)nDigitWithSign;
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
		//buffer.setCharAt(nDigitPosition, (char)nDigitWithSign);
		nDigitPosition--;
		nValue /= 10;		
		while (nValue != 0 && nDigitPosition >= nMinPosition)
		{
			char cDigit = (char)((nValue % 10) + '0');
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nDigitPosition, 1);
			buffer.m_acBuffer[nDigitPosition] = cDigit;
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			nDigitPosition--;
			nValue /= 10;
		}
		while(nDigitPosition >= nMinPosition)
		{
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nDigitPosition, 1);
			buffer.m_acBuffer[nDigitPosition] = '0';
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			nDigitPosition--;			
		}
	}

	static public void setFromRightToLeft(VarBufferPos buffer, int nPosition, int nNbDigitInteger, int nTotalSize, int nOffset, int nValue)
	{		
		// Fill the buffer with '0' on each byte
		buffer.fillZeroesComp0AtOffset(nTotalSize, nOffset);
		
		if(nValue < 0)
		{
			nValue = -nValue;
		}

		int nNibblePos = nNbDigitInteger-1;
		int nDestPos = nPosition+nNibblePos+nOffset;
		do
		{
			char cDigit = (char)((nValue % 10) + '0');
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nDestPos, 1);
			buffer.m_acBuffer[nDestPos] = cDigit;
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			nNibblePos--;
			nDestPos--;
			nValue /= 10;
		}
		while (nValue != 0 && nNibblePos >= 0); 
	}
	
	static public long getAsLong(byte tbyBuffer[], int nAbsolutePosition, int nTotalSize)
	{
		long lValue = getAsLong_ExceptLastByte(tbyBuffer, nAbsolutePosition, nTotalSize);
		lValue *= 10;
		
		int nDigitSign = tbyBuffer[nAbsolutePosition+nTotalSize-1];
		if(nDigitSign < 0)
			nDigitSign += 256;
		int nDigit = 0;
		if(nDigitSign >= 0xD0)
		{
			nDigit = nDigitSign - 0xD0;
			lValue += nDigit;
			lValue = -lValue;
		}
		else if(nDigitSign >= 0xC0)
		{
			nDigit = nDigitSign - 0xC0;
			lValue += nDigit;
		}
		else
		{
			nDigit = nDigitSign - '0';
			lValue += nDigit;
		}
		return lValue;
	}
	
	static public long getAsLongFromEbcdicBuffer(byte tbyBuffer[], int nAbsolutePosition, int nTotalSize)
	{
		long lValue = getAsLong_ExceptLastByte_FromEbcdicBuffer(tbyBuffer, nAbsolutePosition, nTotalSize);
		lValue *= 10;
		
		int nDigitSign = tbyBuffer[nAbsolutePosition+nTotalSize-1];
		if(nDigitSign < 0)
			nDigitSign += 256;
		int nDigit = 0;
		if(nDigitSign >= 0xD0)
		{
			nDigit = nDigitSign - 0xD0;
			lValue += nDigit;
			lValue = -lValue;
		}
		else if(nDigitSign >= 0xC0)
		{
			nDigit = nDigitSign - 0xC0;
			lValue += nDigit;
		}
		else
		{
			nDigit = nDigitSign - '0';
			lValue += nDigit;
		}
		return lValue;
	}
	
	
	static private long getAsLong_ExceptLastByte(byte tbyBuffer[], int nAbsolutePosition, int nTotalSize)
	{
		if(nTotalSize == 0)
			return 0;
		
		long lValue = 0;
		int n = nAbsolutePosition;
		int nMax = nAbsolutePosition+nTotalSize-1;
		while(n < nMax)
		{
			byte by = tbyBuffer[n];
			if(by >= '0' && by <= '9')	
				lValue = 10 * lValue + (by - '0');
			n++;
		}
		return lValue;	
	}
	
	static private long getAsLong_ExceptLastByte_FromEbcdicBuffer(byte tbyBuffer[], int nAbsolutePosition, int nTotalSize)
	{
		if(nTotalSize == 0)
			return 0;
		
		long lValue = 0;
		int n = nAbsolutePosition;
		int nMax = nAbsolutePosition+nTotalSize-1;
		while(n < nMax)
		{
			int nByte = (int) tbyBuffer[n];
			if(nByte < 0)
				nByte += 256;
			if(nByte >= 0xF0 && nByte <= 0xF9)	
				lValue = 10 * lValue + (nByte - 0xF0);
			n++;
		}
		return lValue;	
	}

//	static private void addCharRightToLeft(VarBufferPos buffer, int nPosition, int nNibblePos, int nOffset, char cDigitValue)
//	{
//		buffer.setCharAt(nPosition+nNibblePos+nOffset, cDigitValue);
//	}
	
//	private VarBufferPos m_buffer = null;
//	private int m_nNibblePos = 0;
//	private int m_nTotalSize = 0;
//	private int m_nNbDigitInteger = 0;
//	private int m_nPosition = 0;
}
