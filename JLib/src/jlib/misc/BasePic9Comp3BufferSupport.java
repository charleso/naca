/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
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
 * @version $Id$
 */
public class BasePic9Comp3BufferSupport
{
	public final static byte COMP3_UNSIGNED =(byte)15;	// F is encoded sign for usigned
	public final static byte COMP3_SIGN_MINUS =(byte)13;	// D is encoded sign for -
	public static byte COMP3_SIGN_PLUS = (byte)12;	// C is encoded sign for +

	protected static char ms_tEncodeByteComp3[] = null;
	protected static char ms_tEncodeByteComp3Negative[] = null;
	protected static char ms_tEncodeByteComp3Positive[] = null;
	protected static char ms_tEncodeByteComp3Unsigned[] = null;
	
	protected static int ms_tDecodeByteComp3[] = null;
	protected static int ms_tDecodeLastByteDigitComp3[] = null;
	protected static boolean ms_tDecodeLastByteNegativeComp3[] = null;
	
	protected static long ms_tModulo[] = null;
	protected static String ms_tcs0[] = null;
	
	public static void init()
	{		
		if(ms_tModulo != null)
			return ;
		
		// arrays for fast encoding
		ms_tModulo = new long[20];
		ms_tcs0 = new String[20];
		ms_tEncodeByteComp3 = new char [100];
		ms_tEncodeByteComp3Negative = new char [10];
		ms_tEncodeByteComp3Positive = new char [10];
		ms_tEncodeByteComp3Unsigned = new char [10];
		int i=0;
		for(int n=0; n<10; n++)
		{
			for(int m=0; m<10; m++, i++)
			{
				ms_tEncodeByteComp3[i] = (char)((n*16) + m);
			}
		}
		for(int m=0; m<10; m++)
		{
			ms_tEncodeByteComp3Negative[m] = (char)((m*16) + COMP3_SIGN_MINUS);
			ms_tEncodeByteComp3Positive[m] = (char)((m*16) + COMP3_SIGN_PLUS);
			ms_tEncodeByteComp3Unsigned[m] = (char)((m*16) + COMP3_UNSIGNED);
		}
		
		// arrays for fast decoding
		ms_tDecodeByteComp3 = new int[256];
		ms_tDecodeLastByteNegativeComp3 = new boolean[256];	// Normally 160is enough, excpet that a move high value can set all bytes at FF. Thus we need a size of 255 ! 
		ms_tDecodeLastByteDigitComp3 = new int[256];
		for(int n=0; n<10; n++)
		{
			int nHighNibble = n * 16;
			int nHighDecimal = n * 10;
			for(int m=0; m<10; m++)
			{
				ms_tDecodeByteComp3[nHighNibble+m] = nHighDecimal+m;
				ms_tDecodeLastByteNegativeComp3[nHighNibble+m] = false;
			}
			ms_tDecodeLastByteNegativeComp3[nHighNibble+COMP3_SIGN_MINUS] = true;			
		}	
		
		i = 0;
		for(int n=0; n<10; n++)
		{
			for(int m=0; m<16; m++, i++)
			{
				ms_tDecodeLastByteDigitComp3[i] = n;
			}
		}
	
		for(int n=160; n<256; n++)
		{
			ms_tDecodeLastByteNegativeComp3[n] = false;
			ms_tDecodeLastByteDigitComp3[n] = 0;
		}
		
		String cs0 = "";
		long l = 1;
		for(i=0; i<20; i++)
		{
			ms_tModulo[i] = l;
			l *= 10;
			ms_tcs0[i] = cs0;			
			cs0 += "0";
		}
	}
	
	public static boolean isValidSign(int nLow)
	{
		if(nLow == COMP3_SIGN_MINUS || nLow == COMP3_SIGN_PLUS)
			return true;
		return false;
	}
	
	public static boolean isValidUnsign(int nLow)
	{
		if(nLow == COMP3_UNSIGNED)
			return true;
		return false;
	}
	
	public static boolean isNegative(byte by)
	{
		int nLow = by & 0x0F;
		if(nLow == COMP3_SIGN_MINUS)
			return true;
		return false;
	} 	
	
	public static long getAsLong(byte acBuffer[], int nAbsolutePosition, int nNbDigitInteger, int nTotalSize)
	{
		long lValue = 0;
		int nNbChars = nTotalSize;
		int nPosSource = nAbsolutePosition;
		for(int n=0; n<nNbChars-1; n++)
		{
			int nEncodedByte = acBuffer[nPosSource++];
			if( nEncodedByte < 0)
				nEncodedByte += 256;
			
			if( lValue!= 0)
				lValue *= 100;
			lValue += ms_tDecodeByteComp3[nEncodedByte];
		}
		
		// Last byte
		int nEncodedByte = acBuffer[nAbsolutePosition + nNbChars-1];
		if( nEncodedByte < 0)
			nEncodedByte += 256;
		int nDecodedByte = ms_tDecodeLastByteDigitComp3[nEncodedByte];
		lValue *= 10;
		lValue += nDecodedByte;

		boolean bNegative = ms_tDecodeLastByteNegativeComp3[nEncodedByte];
		if(bNegative)
			lValue = -lValue;
		return lValue;
	}
	
	public static long keepRightMostDigits(long lOriginalValue, int nNbDigitsToKeep)
	{
		long lPower10 = ms_tModulo[nNbDigitsToKeep];
		if(lOriginalValue < 0)
		{
			long lValue = -lOriginalValue;			
			if(lValue > lPower10)	// 1234 > 1000, when we want to keep only 3 digits for n, then returning only 234
			{				
				long lLeftDigits = (lValue / lPower10) * lPower10;
				lValue = lValue - lLeftDigits;
				return -lValue;
			}
			return lOriginalValue;
		}
		if(lOriginalValue > lPower10)	// 1234 > 1000, when we want to keep only 3 digits for n, then returning only 234
		{
			long lLeftDigits = (lOriginalValue / lPower10) * lPower10;
			long lValue = lOriginalValue - lLeftDigits;
			return lValue;
		}
		return lOriginalValue;		
	}
	
	public static String makeDottedString(long lValue, int nNbDecimals)
	{
		long lPower10 = ms_tModulo[nNbDecimals];
		long lInt = lValue / lPower10;
		
		long lAbsValue = lValue;
		if(lAbsValue < 0)
			lAbsValue = -lAbsValue;
		long lDec = lAbsValue % lPower10;
		
		String csDec = "" + lDec;
		if(csDec.length() < nNbDecimals)	// Must prefix with leading 0
		{
			int nNbLeading0 = nNbDecimals - csDec.length();
			csDec = ms_tcs0[nNbLeading0] + csDec;
		}
		
		return "" + lInt + "." + csDec; 
	}
	
}
