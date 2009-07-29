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
 * Created on 31 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import nacaLib.debug.BufferSpy;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RWNumIntComp0
{
	RWNumIntComp0()
	{
	}
	
	// PJD: To be replaced by setFromRightToLeft ...
	static int internalWriteAbsoluteIntComp0(VarBufferPos buffer, int nOffset, int nValue, int nPosition, int nTotalSize)
	{
		if(nValue < 0)
			nValue = -nValue;
		String csValue = String.valueOf(nValue);
		return internalWriteAbsoluteIntComp0AsString(buffer, nOffset, csValue, nPosition, nTotalSize);
	}

	
//	static void internalWriteAbsoluteIntComp0(VarBufferPos buffer, int nValue, int nPosition, int nTotalSize)
//	{
//		if(nValue < 0)
//			nValue = -nValue;
//		String csValue = String.valueOf(nValue);
//		return internalWriteAbsoluteIntComp0AsString(buffer, csValue, nPosition, nTotalSize);
//		
//	}
		
	static int internalWriteAbsoluteIntComp0AsLong(VarBufferPos buffer, long lValue, int nPosition, int nTotalSize)
	{
		if(lValue < 0)
			lValue = -lValue;
		String csValue = String.valueOf(lValue);
		return internalWriteAbsoluteIntComp0AsString(buffer, csValue, nPosition, nTotalSize);
	}
	
	static int internalWriteAbsoluteIntComp0AsLong(VarBufferPos buffer, int nOffset, long lValue, int nPosition, int nTotalSize)
	{
		if(lValue < 0)
			lValue = -lValue;
		String csValue = String.valueOf(lValue);
		return internalWriteAbsoluteIntComp0AsString(buffer, nOffset, csValue, nPosition, nTotalSize);
	}
	
	static int internalWriteAbsoluteIntComp0AsString(VarBufferPos buffer, int nOffset, String csValue, int nPosition, int nTotalSize)
	{
		int nStringLength = csValue.length();
		if(nTotalSize < nStringLength)	// Keeping only rightmostchar form source string
		{
			String csSourceString = csValue.substring(nStringLength - nTotalSize);
			nPosition = buffer.setStringAt(nPosition+nOffset, csSourceString, nTotalSize);	// Writing the string
		}
		else if(nStringLength < nTotalSize)	// Padding with '0' on the left
		{
			int nNbZeroToPrefix = nTotalSize - nStringLength;
			nPosition = buffer.writeRepeatingCharAt(nPosition+nOffset, '0', nNbZeroToPrefix);
			nPosition = buffer.setStringAt(nPosition, csValue, nStringLength);	// Writing the string
		}
		else	// Same size
		{
			nPosition = buffer.setStringAt(nPosition+nOffset, csValue, nStringLength);	// Writing the string
		}
		return nPosition;
	}
	
//	static int internalWriteAbsoluteIntComp0AsString(VarBufferPos buffer, int nOffset, CStr csValue, int nPosition, int nTotalSize)
//	{
//		int nStringLength = csValue.length();
//		if(nTotalSize < nStringLength)	// Keeping only rightmostchar form source string
//		{
//			String csSourceString = csValue.substring(nStringLength - nTotalSize);
//			nPosition = buffer.setStringAt(nPosition+nOffset, csSourceString, nTotalSize);	// Writing the string
//		}
//		else if(nStringLength < nTotalSize)	// Padding with '0' on the left
//		{
//			int nNbZeroToPrefix = nTotalSize - nStringLength;
//			nPosition = buffer.writeRepeatingCharAt(nPosition+nOffset, '0', nNbZeroToPrefix);
//			nPosition = buffer.setStringAt(nPosition, csValue, nStringLength);	// Writing the string
//		}
//		else	// Same size
//		{
//			nPosition = buffer.setStringAt(nPosition+nOffset, csValue, nStringLength);	// Writing the string
//		}
//		return nPosition;
//	}

	
	static int internalWriteAbsoluteIntComp0AsString(VarBufferPos buffer, String csValue, int nPosition, int nTotalSize)
	{
		int nStringLength = csValue.length();
		if(nTotalSize < nStringLength)	// Keeping only rightmostchar form source string
		{
			String csSourceString = csValue.substring(nStringLength - nTotalSize);
			nPosition = buffer.setStringAt(nPosition, csSourceString, nTotalSize);	// Writing the string
		}
		else if(nStringLength < nTotalSize)	// Padding with '0' on the left
		{
			int nNbZeroToPrefix = nTotalSize - nStringLength;
			nPosition = buffer.writeRepeatingCharAt(nPosition, '0', nNbZeroToPrefix);
			nPosition = buffer.setStringAt(nPosition, csValue, nStringLength);	// Writing the string
		}
		else	// Same size
		{
			nPosition = buffer.setStringAt(nPosition, csValue, nStringLength);	// Writing the string
		}
		return nPosition;
	}
	
//	static String getAsAbsoluteIntComp0String(int nValue, int nNbDigitInteger)
//	{
//		if(nValue < 0)
//			nValue = -nValue;
//		String csValue = String.valueOf(nValue);
//		return getAsAbsoluteIntComp0StringAsString(csValue, nNbDigitInteger);
//	}
//	
//	static String getAsAbsoluteIntComp0StringAsLong(long lValue, int nNbDigitInteger)
//	{
//		if(lValue < 0)
//			lValue = -lValue;
//		String csValue = String.valueOf(lValue);
//		return getAsAbsoluteIntComp0StringAsString(csValue, nNbDigitInteger);
//	}
//	
//	static String getAsAbsoluteIntComp0StringAsString(String csValue, int nNbDigitInteger)
//	{
//		String csOutString;
//		int nValueLength = csValue.length();
//		if(nNbDigitInteger < nValueLength)	// Keeping only rightmostchar form source string
//		{
//			csOutString = csValue.substring(nValueLength - nNbDigitInteger);
//		}
//		else if(nNbDigitInteger > nValueLength)	// Padding with '0' on the left
//		{
//			csOutString = new String();
//			int nNbZeroToPrefix = nNbDigitInteger - nValueLength;
//			for(int n0=0; n0<nNbZeroToPrefix; n0++)
//				csOutString += '0';
//			csOutString += csValue;
//		}
//		else	// Same size
//		{
//			csOutString = csValue;
//		}
//		return csOutString;
//	}
	
//	public static void fillBlankAsZero(VarBufferPos buffer, int nOffset, int nTotalSize)
//	{
//		buffer.fillBlankComp0AtOffset(nTotalSize, nOffset);
//	}
	
	public static void setFromRightToLeft(VarBufferPos buffer, int nOffset, int nValue, int nTotalSize, int nNbDigitInteger)
	{		
		// Fill the buffer with '0' on each byte
		//buffer.fillZeroesComp0AtOffset(nTotalSize, nOffset);
		
		if(nValue < 0)
			nValue = -nValue;

		int nRelativePosStart = buffer.m_nAbsolutePosition + nOffset;
		int nRelativePos = buffer.m_nAbsolutePosition + nOffset + nNbDigitInteger-1;
		do
		{
			char cDigit = (char)((nValue % 10) + '0');
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nRelativePos, 1);
			buffer.m_acBuffer[nRelativePos] = cDigit;
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			nRelativePos--;
			nValue /= 10;
		}
		while (nValue != 0 && nRelativePos >= nRelativePosStart); 
		
		while(nRelativePos >= nRelativePosStart)
		{
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nRelativePos, 1);
			buffer.m_acBuffer[nRelativePos] = '0';
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			nRelativePos--;
		}
	}
	
	public static void setFromRightToLeft(VarBufferPos buffer, int nOffset, long lValue, int nTotalSize, int nNbDigitInteger)
	{		
		// Fill the buffer with '0' on each byte
		//buffer.fillZeroesComp0AtOffset(nTotalSize, nOffset);
		
		if(lValue < 0)
			lValue = -lValue;

		int nRelativePosStart = buffer.m_nAbsolutePosition + nOffset;
		int nRelativePos = buffer.m_nAbsolutePosition + nOffset + nNbDigitInteger-1;
		do
		{
			char cDigit = (char)((lValue % 10) + '0');
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nRelativePos, 1);
			buffer.m_acBuffer[nRelativePos] = cDigit;
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			nRelativePos--;
			lValue /= 10;
		}
		while (lValue != 0 && nRelativePos >= nRelativePosStart); 
		
		while(nRelativePos >= nRelativePosStart)
		{
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nRelativePos, 1);
			buffer.m_acBuffer[nRelativePos] = '0';
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			nRelativePos--;
		}
	}

}

