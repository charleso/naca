/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.varEx;

import jlib.log.StackStraceSupport;
import jlib.misc.BasePic9Comp3BufferSupport;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.batchOOApi.WriteBufferExt;
import nacaLib.tempCache.TempCacheLocator;

public class Pic9Comp3BufferSupport extends BasePic9Comp3BufferSupport 
{
	static public void setFromRightToLeftUnsigned(VarBufferPos buffer, int nNbDigitInteger, int nTotalSize, int lValue)
	{		
		//buffer.resetLastChecksum();
		char c;
		int nDigit;
		
		int nBytePos = nNbDigitInteger / 2;
		if(lValue < 0)
			lValue = -lValue;
		nDigit = lValue % 10;
		c = ms_tEncodeByteComp3Unsigned[nDigit];
		lValue /= 10;
		
		int nDestPos = buffer.m_nAbsolutePosition+nBytePos;
		
		buffer.m_acBuffer[nDestPos] = c;
		//buffer.setCharAtOffset(nOffset + nBytePos, c);
		nBytePos--;
		nDestPos--;
		while (nBytePos >= 0)
		{
			if(lValue != 0)
			{
				int nDigits = (int)(lValue % 100);
				lValue /= 100;
				c = ms_tEncodeByteComp3[nDigits];
				buffer.m_acBuffer[nDestPos--] = c;
				//buffer.setCharAtOffset(nOffset + nBytePos, c);
				nBytePos--;
				continue;
			}
			else
			{
				buffer.fillBlankComp3AtOffset(nBytePos, 0);
				break;
			}
		}
	}
	
	static public void setFromRightToLeftSigned(VarBufferPos buffer, int nNbDigitInteger, int nTotalSize, int lValue)
	{		
		//buffer.resetLastChecksum();
		char c;
		int nDigit;
		
		int nBytePos = nNbDigitInteger / 2;
		if(lValue < 0)
		{
			lValue = -lValue;
			nDigit = lValue % 10;
			c = ms_tEncodeByteComp3Negative[nDigit];
		}
		else
		{
			nDigit = lValue % 10;
			c = ms_tEncodeByteComp3Positive[nDigit];
		}		
		
		lValue /= 10;
		
		int nDestPos = buffer.m_nAbsolutePosition+nBytePos;
		
		buffer.m_acBuffer[nDestPos] = c;
		//buffer.setCharAtOffset(nOffset + nBytePos, c);
		nBytePos--;
		nDestPos--;
		while (nBytePos >= 0)
		{
			if(lValue != 0)
			{
				int nDigits = (int)(lValue % 100);
				lValue /= 100;
				c = ms_tEncodeByteComp3[nDigits];
				buffer.m_acBuffer[nDestPos--] = c;
				//buffer.setCharAtOffset(nOffset + nBytePos, c);
				nBytePos--;
				continue;
			}
			else
			{
				buffer.fillBlankComp3AtOffset(nBytePos, 0);
				break;
			}
		}
	}
	
	static public void setFromRightToLeftSignedLong(VarBufferPos buffer, int nNbDigitInteger, int nTotalSize, long lValue)
	{		
		//buffer.resetLastChecksum();
		char c;
		int nDigit;
		
		int nBytePos = nNbDigitInteger / 2;
		if(lValue < 0)
		{
			lValue = -lValue;
			nDigit = (int)(lValue % 10);
			c = ms_tEncodeByteComp3Negative[nDigit];
		}
		else
		{
			nDigit = (int)(lValue % 10);
			c = ms_tEncodeByteComp3Positive[nDigit];
		}		
		
		lValue /= 10;
		
		int nDestPos = buffer.m_nAbsolutePosition+nBytePos;
		
		buffer.m_acBuffer[nDestPos] = c;
		//buffer.setCharAtOffset(nOffset + nBytePos, c);
		nBytePos--;
		nDestPos--;
		while (nBytePos >= 0)
		{
			if(lValue != 0)
			{
				int nDigits = (int)(lValue % 100);
				lValue /= 100;
				c = ms_tEncodeByteComp3[nDigits];
				buffer.m_acBuffer[nDestPos--] = c;
				//buffer.setCharAtOffset(nOffset + nBytePos, c);
				nBytePos--;
				continue;
			}
			else
			{
				buffer.fillBlankComp3AtOffset(nBytePos, 0);
				break;
			}
		}
	}

	static public void setFromRightToLeftOffsetUnsignedLong(VarBufferPos buffer, int nNbDigitInteger, int nTotalSize, int nOffset, long lValue)
	{		
		char c;
		int nDigit;
		
		int nBytePos = nNbDigitInteger / 2;
		if(lValue < 0)
			lValue = -lValue;
		nDigit = (int)(lValue % 10);
		c = ms_tEncodeByteComp3Unsigned[nDigit];
		lValue /= 10;
		
		int nDestPos = buffer.m_nAbsolutePosition+nOffset+nBytePos;
		buffer.m_acBuffer[nDestPos] = c;
		//buffer.setCharAtOffset(nOffset + nBytePos, c);
		nBytePos--;
		nDestPos--;
		while (nBytePos >= 0)
		{
			if(lValue != 0)
			{
				int nDigits = (int)(lValue % 100);
				lValue /= 100;
				c = ms_tEncodeByteComp3[nDigits];
				buffer.m_acBuffer[nDestPos--] = c;
				//buffer.setCharAtOffset(nOffset + nBytePos, c);
				nBytePos--;
				continue;
			}
			else
			{
				buffer.fillBlankComp3AtOffset(nBytePos, nOffset);
				break;
			}
		}
	}
	
	static public void setFromRightToLeftUnsignedLong(VarBufferPos buffer, int nNbDigitInteger, int nTotalSize, long lValue)
	{		
		char c;
		int nDigit;
		
		int nBytePos = nNbDigitInteger / 2;
		if(lValue < 0)
			lValue = -lValue;
		nDigit = (int)(lValue % 10);
		c = ms_tEncodeByteComp3Unsigned[nDigit];
		lValue /= 10;
		
		int nDestPos = buffer.m_nAbsolutePosition+nBytePos;
		buffer.m_acBuffer[nDestPos] = c;
		//buffer.setCharAtOffset(nOffset + nBytePos, c);
		nBytePos--;
		nDestPos--;
		while (nBytePos >= 0)
		{
			if(lValue != 0)
			{
				int nDigits = (int)(lValue % 100);
				lValue /= 100;
				c = ms_tEncodeByteComp3[nDigits];
				buffer.m_acBuffer[nDestPos--] = c;
				//buffer.setCharAtOffset(nOffset + nBytePos, c);
				nBytePos--;
				continue;
			}
			else
			{
				buffer.fillBlankComp3AtOffset(nBytePos, 0);
				break;
			}
		}
	}

	static public void setFromRightToLeftOffsetSignedLong(VarBufferPos buffer, int nNbDigitInteger, int nTotalSize, int nOffset, long lValue)
	{		
		char c;
		int nDigit;
		
		int nBytePos = nNbDigitInteger / 2;
		if(lValue < 0)
		{
			lValue = -lValue;
			nDigit = (int)(lValue % 10);
			c = ms_tEncodeByteComp3Negative[nDigit];
		}
		else
		{
			nDigit = (int)(lValue % 10);
			c = ms_tEncodeByteComp3Positive[nDigit];
		}				
		lValue /= 10;
		
		int nDestPos = buffer.m_nAbsolutePosition+nOffset+nBytePos;
		buffer.m_acBuffer[nDestPos] = c;
		//buffer.setCharAtOffset(nOffset + nBytePos, c);
		nBytePos--;
		nDestPos--;
		while (nBytePos >= 0)
		{
			if(lValue != 0)
			{
				int nDigits = (int)(lValue % 100);
				lValue /= 100;
				c = ms_tEncodeByteComp3[nDigits];
				buffer.m_acBuffer[nDestPos--] = c;
				//buffer.setCharAtOffset(nOffset + nBytePos, c);
				nBytePos--;
				continue;
			}
			else
			{
				buffer.fillBlankComp3AtOffset(nBytePos, nOffset);
				break;
			}
		}
	}
	
	static public void setFromRightToLeft(VarBufferPos buffer, int nNbDigitInteger, int nTotalSize, int nOffset, boolean bSigned, long lValue)
	{		
		char c;
		int nDigit;
		
		int nBytePos = nNbDigitInteger / 2;
		if(bSigned)
		{
			if(lValue < 0)
			{
				lValue = -lValue;
				nDigit = (int)(lValue % 10);
				c = ms_tEncodeByteComp3Negative[nDigit];
			}
			else
			{
				nDigit = (int)(lValue % 10);
				c = ms_tEncodeByteComp3Positive[nDigit];
			}				
		}
		else
		{
			if(lValue < 0)
				lValue = -lValue;
			nDigit = (int)(lValue % 10);
			c = ms_tEncodeByteComp3Unsigned[nDigit];
		}
		lValue /= 10;
		
		int nDestPos = buffer.m_nAbsolutePosition+nOffset+nBytePos;
		buffer.m_acBuffer[nDestPos] = c;
		//buffer.setCharAtOffset(nOffset + nBytePos, c);
		nBytePos--;
		nDestPos--;
		while (nBytePos >= 0)
		{
			if(lValue != 0)
			{
				int nDigits = (int)(lValue % 100);
				lValue /= 100;
				c = ms_tEncodeByteComp3[nDigits];
				buffer.m_acBuffer[nDestPos--] = c;
				//buffer.setCharAtOffset(nOffset + nBytePos, c);
				nBytePos--;
				continue;
			}
			else
			{
				buffer.fillBlankComp3AtOffset(nBytePos, nOffset);
				break;
			}
		}
	}
	
	static long keepRightMostDigits(VarDefBase varDef, long lOriginalValue, int nNbDigitsToKeep)
	{
		long lPower10 = ms_tModulo[nNbDigitsToKeep];
		if(lOriginalValue < 0)
		{
			long lValue = -lOriginalValue;			
			if(lValue > lPower10)	// 1234 > 1000, when we want to keep only 3 digits for n, then returning only 234
			{				
				long lLeftDigits = (lValue / lPower10) * lPower10;
				lValue = lValue - lLeftDigits;
				mailLogNumberTruncationError(varDef, lOriginalValue, lValue);
				return -lValue;
			}
			return lOriginalValue;
		}
		if(lOriginalValue >= lPower10)	// 1234 >= 1000, when we want to keep only 3 digits for n, then returning only 234
		{
			long lLeftDigits = (lOriginalValue / lPower10) * lPower10;
			long lValue = lOriginalValue - lLeftDigits;
			mailLogNumberTruncationError(varDef, lOriginalValue, lValue);
			return lValue;
		}
		return lOriginalValue;		
	}
	
	private static void mailLogNumberTruncationError(VarDefBase varDef, long lOriginalValue, long lValue)
	{
		String csSimpleName = TempCacheLocator.getTLSTempCache().getProgramManager().getProgramName();
		StringBuilder sb = new StringBuilder();
		sb.append("NacaRT: Number left-digit truncated\r\n");
		sb.append("In program " + csSimpleName + "\r\n");
		sb.append("Variable definition " + varDef.toString() + "\r\n");
		sb.append("Original value=" +lOriginalValue + "\r\n");
		sb.append("Truncated value=" +lValue + "\r\n");
		sb.append("\r\n");
		sb.append("Call Stack is\r\n");
		sb.append(StackStraceSupport.getCallStackAsString());
		String csBodyText = sb.toString();
				
		BaseProgramLoader.logMail(csSimpleName + " - NacaRT: Number left-digit truncated", csBodyText);		
	}
	
	static int getAsIntWithMaxNbdigits(VarDefBase varDef, VarBufferPos buffer, int nNbDigitInteger, int nTotalSize)
	{
		long lValue = getAsInt(buffer, nNbDigitInteger, nTotalSize);
		lValue = keepRightMostDigits(varDef, lValue, nNbDigitInteger);
		return (int)lValue;
	}
	
	public static int getAsInt(VarBufferPos buffer, int nNbDigitInteger)
	{
		int nTotalSize = nNbDigitInteger / 2;
		if((nNbDigitInteger % 2) == 0)
			nTotalSize++;
		return getAsInt(buffer, nNbDigitInteger, nTotalSize);
	}
	
	static int getAsInt(VarBufferPos buffer, int nNbDigitInteger, int nTotalSize)
	{
		int nValue = 0;
		int nNbChars = nTotalSize;
		int nEncodedByte = 0;
		int nPosSource = buffer.m_nAbsolutePosition;
		for(int n=0; n<nNbChars-1; n++)
		{
			nEncodedByte = buffer.m_acBuffer[nPosSource++];
			if(nValue != 0)
				nValue *= 100;
			nValue += ms_tDecodeByteComp3[nEncodedByte];
		}
		
		// Last byte
		nEncodedByte = buffer.m_acBuffer[buffer.m_nAbsolutePosition+nNbChars-1];
		//nEncodedByte = buffer.getCharAtOffset(nNbChars-1);
		nValue *= 10;
		nValue += ms_tDecodeLastByteDigitComp3[nEncodedByte];

		boolean bNegative = ms_tDecodeLastByteNegativeComp3[nEncodedByte];
		if(bNegative)
			nValue = -nValue;
		return nValue;
	}
	
	public static int getAsUnsignedInt(VarBufferPos buffer, int nNbDigitInteger)
	{
		int nTotalSize = nNbDigitInteger / 2;
		if((nNbDigitInteger % 2) == 0)
			nTotalSize++;
		return getAsUnsignedInt(buffer, nNbDigitInteger, nTotalSize);
	}
	
	static int getAsUnsignedInt(VarBufferPos buffer, int nNbDigitInteger, int nTotalSize)
	{
		int nValue = 0;
		int nNbChars = nTotalSize;
		int nEncodedByte = 0;
		int nPosSource = buffer.m_nAbsolutePosition;
		for(int n=0; n<nNbChars-1; n++)
		{
			nEncodedByte = buffer.m_acBuffer[nPosSource++];
			//int nEncodedByte = buffer.getCharAtOffset(n);
			if(nValue != 0)
				nValue *= 100;
			nValue += ms_tDecodeByteComp3[nEncodedByte];
		}
		
		// Last byte
		nEncodedByte = buffer.m_acBuffer[buffer.m_nAbsolutePosition+nNbChars-1];
		//nEncodedByte = buffer.getCharAtOffset(nNbChars-1);
		nValue *= 10;
		nValue += ms_tDecodeLastByteDigitComp3[nEncodedByte];
//
//		boolean bNegative = ms_tDecodeLastByteNegativeComp3[nEncodedByte];
//		if(bNegative)
//			nValue = -nValue;
		return nValue;
	}
	
	
	static long getAsLongWithMaxNbdigits(VarDefBase varDef, VarBufferPos buffer, int nNbDigitInteger, int nTotalSize)
	{
		long lValue = getAsInt(buffer, nNbDigitInteger, nTotalSize);
		lValue = keepRightMostDigits(varDef, lValue, nNbDigitInteger);
		return lValue;
	}
		
	static long getAsLong(VarBufferPos buffer, int nNbDigitInteger, int nTotalSize)
	{
		long lValue = 0;
		int nNbChars = nTotalSize;
		int nEncodedByte = 0;
		int nPosSource = buffer.m_nAbsolutePosition;
		for(int n=0; n<nNbChars-1; n++)
		{
			nEncodedByte = buffer.m_acBuffer[nPosSource++];
			if(lValue != 0)
				lValue *= 100;
			lValue += ms_tDecodeByteComp3[nEncodedByte];
		}
		
		// Last byte
		nEncodedByte = buffer.m_acBuffer[buffer.m_nAbsolutePosition+nNbChars-1];
		lValue *= 10;
		lValue += ms_tDecodeLastByteDigitComp3[nEncodedByte];

		boolean bNegative = ms_tDecodeLastByteNegativeComp3[nEncodedByte];
		if(bNegative)
			lValue = -lValue;
		return lValue;
	}
	
	static long getAsUnsignedLong(VarBufferPos buffer, int nNbDigitInteger, int nTotalSize)
	{
		long lValue = 0;
		int nNbChars = nTotalSize;
		int nEncodedByte = 0;
		int nPosSource = buffer.m_nAbsolutePosition;
		for(int n=0; n<nNbChars-1; n++)
		{
			nEncodedByte = buffer.m_acBuffer[nPosSource++];
//			int nEncodedByte = buffer.getCharAtOffset(m_nAbsolutePosition+n);
			if(lValue != 0)
				lValue *= 100;
			lValue += ms_tDecodeByteComp3[nEncodedByte];
		}
		
		// Last byte
		nEncodedByte = buffer.m_acBuffer[buffer.m_nAbsolutePosition+nNbChars-1];
		//int nEncodedByte = buffer.getCharAtOffset(nNbChars-1);
		lValue *= 10;
		lValue += ms_tDecodeLastByteDigitComp3[nEncodedByte];

		return lValue;
	}
		
	
	// PJD: Not opptimized
	public static String getAsSignedString(VarBufferPos buffer, int nNbDigitInteger, int nTotalSize)
	{
		boolean bEvenNumberOfDigits = false;
		if((nNbDigitInteger % 2) == 0)
			bEvenNumberOfDigits = true;
		
		String csOut = new String();
		char c;
		int nNbChars = nTotalSize;
		int nSourcePos = buffer.m_nAbsolutePosition;
		for(int n=0; n<nNbChars; n++)
		{
			int nByte = buffer.m_acBuffer[nSourcePos++];
//			int nByte = buffer.getCharAtOffset(n);
			int nHigh = (nByte & 0x00F0) >> 4;
			c = (char)(nHigh + '0');
			csOut += c;
			
			int nLow = nByte & 0x000F;
			if(nLow < 10)
			{
				c = (char)(nLow + '0');
				csOut += c;
			}
			else	// Sign
			{
				if(nLow == COMP3_SIGN_MINUS)
					csOut += "-";
				else if(nLow == COMP3_SIGN_PLUS)
					csOut += "+";
			}
		}
		if(bEvenNumberOfDigits)
		{
			// Remove leading 0 that was there as a placeholder due to the even number of digits + sign -> implies an odd number of nibbles; the leading compensated that odd number
			csOut = csOut.substring(1);	//sOut = sOut.substring(1, nNbDigitInteger+1);			
		}
		return csOut;		
	}
	
	static public void setDec(VarBufferPos buffer, int nNbDigitInteger, int nNbDigitDecimal, int nTotalSize, int nOffset, boolean bSigned, Dec decValue)
	{		
		if(nNbDigitDecimal > 0)
		{
			// Build a number form int and dec part, with correct alignment

			long lUnsignedIntValue = decValue.getUnsignedLong();
			lUnsignedIntValue = lUnsignedIntValue % ms_tModulo[nNbDigitInteger];	// Keep only rightmost digits of the int part
			
			int nUnsignedDecValue = decValue.getLeftMostDigitOfDecPartAsInt(nNbDigitDecimal);
			long lSignedValue = (lUnsignedIntValue * ms_tModulo[nNbDigitDecimal]) + nUnsignedDecValue; 
			
			if(decValue.isNegative())
				lSignedValue = -lSignedValue;
			setFromRightToLeft(buffer, nNbDigitInteger+nNbDigitDecimal, nTotalSize, nOffset, bSigned, lSignedValue);
		}
		else
		{
			long lSignedIntValue = decValue.getSignedLong();
			setFromRightToLeft(buffer, nNbDigitInteger, nTotalSize, nOffset, bSigned, lSignedIntValue);
		}
	}
	
	static public Dec getAsDecSigned(VarBufferPos buffer, int nNbDigitInteger, int nNbDigitDecimal, int nTotalSize)
	{
		long lIntDec = getAsLong(buffer, nNbDigitInteger+nNbDigitDecimal, nTotalSize);
		boolean bNegative = false; 
		if(lIntDec < 0)
		{
			bNegative = true;
			lIntDec = -lIntDec;
		}		
		long lInt = lIntDec / ms_tModulo[nNbDigitDecimal];
		if(nNbDigitDecimal > 0)
		{
			long lDec = ms_tModulo[nNbDigitDecimal] + (lIntDec % ms_tModulo[nNbDigitDecimal]);
//			if(lDec != 0)
//			{
//				long lDecMaxValue = ms_tModulo[nNbDigitDecimal-1];
//				while(lDec < lDecMaxValue)
//					lDec *= 10;
//			}
			String cs = String.valueOf(lDec);
			String csRight = cs.substring(1); 
			Dec dec = new Dec(lInt, csRight);
			if(bNegative)
				dec.setNegativeForced();
			return dec;
		}

		Dec dec = new Dec(lInt, "0");
		if(bNegative)
			dec.setNegativeForced();
		return dec;
	}
	
	static public Dec getAsDecUnsigned(VarBufferPos buffer, int nNbDigitInteger, int nNbDigitDecimal, int nTotalSize)
	{
		long lIntDec = getAsLong(buffer, nNbDigitInteger+nNbDigitDecimal, nTotalSize);
		if(lIntDec < 0)
			lIntDec = -lIntDec;
		long lInt = lIntDec / ms_tModulo[nNbDigitDecimal];
		if(nNbDigitDecimal > 0)
		{
			long lDec = ms_tModulo[nNbDigitDecimal] + (lIntDec % ms_tModulo[nNbDigitDecimal]);
			String cs = String.valueOf(lDec);
			String csRight = cs.substring(1); 
			Dec dec = new Dec(lInt, csRight);
			return dec;
		}

		Dec dec = new Dec(lInt, "0");
		return dec;
	}
}

