/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.misc;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: Comp3Support.java,v 1.2 2007/01/09 14:05:57 u930di Exp $
 */
public class Comp3Support
{
	public static String encodeDecComp3(DecBase decValue, int nNbDigitInteger, int nNbDigitDecimal)
	{
		long lInt = decValue.getUnsignedLong();
		
		String sAbsIntValue = String.valueOf(lInt);
		String sDecValue = decValue.getDecPart();
		
		String s = new String();

		int nNbTotalDigit = nNbDigitInteger + nNbDigitDecimal;
		if((nNbTotalDigit % 2) == 0)
			s = "0";	// Left most 0 to compensate empty leftmost nibble 
		int nStringLength = sAbsIntValue.length();
		while(nStringLength < nNbDigitInteger)
		{
			s = s + '0';
			nStringLength++;
		}
		if(nStringLength > nNbDigitInteger)	// Keeping only rightmostchar form source string
			sAbsIntValue = sAbsIntValue.substring(nStringLength - nNbDigitInteger);
		s = s + sAbsIntValue;
		
		String sDec = null;
		nStringLength = sDecValue.length();

		if(nStringLength > nNbDigitDecimal)
			sDec = sDecValue.substring(0, nNbDigitDecimal);
		else if(nStringLength == nNbDigitDecimal)
			sDec = sDecValue;
		else
		{
			sDec = sDecValue;
			while(nStringLength < nNbDigitDecimal)
			{
				sDec = sDec + '0';
				nStringLength++;
			}						
		}
		if(sDec != null && sDec.length() != 0)
			s = s + sDec; 
		return s;
	}	
	
	public static void internalWriteEncodeComp3(byte aBytes[], String cs, boolean bPositive, boolean bSigned)
	{
		int nStringLength = cs.length();
		int n = 0;
		int nByteDest = 0;
		
		char cHigh = 0; 
		int nHigh = 0;
		char cLow = 0;
		int nLow = 0;			
		while(n < nStringLength)
		{
			cHigh = cs.charAt(n);
			nHigh = cHigh - '0'; 
			n++;
			
			if(n == nStringLength)	// No more digit, but the sign
			{
				if(bSigned)
				{
					if(bPositive)
						nLow = 12;	// C is encoded sign for +
					else
						nLow = 13;	// D is encoded sign for -
				}
				else
					nLow = 15;	// F is encoded sign for usigned
			}
			else
			{
				cLow = cs.charAt(n);
				nLow = cLow - '0';
			}

			int nChar = (nHigh * 16) + nLow;
			byte by = (byte)nChar;  
			aBytes[nByteDest] = by;
						 
			n++;
			nByteDest++;
		}
	}
}
