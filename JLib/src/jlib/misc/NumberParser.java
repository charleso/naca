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
package jlib.misc;


/*
 * Created on 17 déc. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author PJD
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NumberParser
{
	public static short getAsShort(String s)
	{
		if(s == null)
			return 0;
		else 
		{
			int nLength = s.length();
			if(nLength == 0)
				return 0;
			
			short sValue = 0;			
			int n = 0;
			boolean bNegative = false;
	
			while(n < nLength)
			{
				char c = s.charAt(n);
				if(c >= '0' && c <= '9')
					sValue = (short)(10 * sValue + (c - '0'));
				else if(c == '-')
					bNegative = true;
				else if(c == '.')
					break;
				n++;
			}
			if(bNegative)
				return (short)-sValue;
			return sValue;	
		}
	}
	
	public static int getAsInt(char c)
	{
		int nValue = 0; 
		if(c >= '0' && c <= '9')
			nValue = c - '0';
		return nValue; 
	}
	
	public static int getAsUnsignedInt(char c)
	{
		int nValue = 0; 
		if(c >= '0' && c <= '9')
			nValue = c - '0';
		return nValue; 
	}
	
	public static boolean getAsBoolean(String cs)
	{
		if(cs != null)
			if(cs.equalsIgnoreCase("true") || cs.equalsIgnoreCase("yes") || cs.equalsIgnoreCase("on") || cs.equalsIgnoreCase("1"))
				return true;
		return false;
	}
	
	public static int getAsInt(String s)
	{
		if(s == null)
			return 0;
		
		int nLength = s.length();
		int nValue = 0;
		int n = 0;
		boolean bNegative = false;
		while(n < nLength)
		{
			char c = s.charAt(n);
			if(c >= '0' && c <= '9')	// Character.isDigit(c)
			{
				nValue = 10 * nValue + (c - '0');
				n++;
				continue;
			}
			else if(c == '-')
			{
				bNegative = true;
				n++;
				continue;
			}
			else if(c == ' ');
			else if(c == '\'');
			else if(c == '+');					
			else if(c == '.')
				break;
			else if (n==0) // first char is not a digit
				return 0 ;
			n++;
		}
		if(bNegative)
			return -nValue;
		return nValue;	
	}
	
	public static int getAsUnsignedInt(String s)
	{
		if(s == null)
			return 0;
		
		int nLength = s.length();
		int nValue = 0;
		int n = 0;
		while(n < nLength)
		{
			char c = s.charAt(n);
			if(c >= '0' && c <= '9')	// Character.isDigit(c)
			{
				nValue = 10 * nValue + (c - '0');
				n++;
				continue;
			}
			else if(c == '-')
			{
				n++;
				continue;
			}
			else if(c == ' ');					
			else if(c == '+');					
			else if(c == '.')
				break;
			else if (n==0) // first char is not a digit
				return 0 ;
			n++;
		}
		return nValue;	
	}
	
	public static long getAsLong(String s)
	{
		if(s == null)
			return 0;
		else 
		{
			int nLength = s.length();
			if(nLength == 0)
				return 0;
	
			long lValue = 0;
			int n = 0;
			boolean bNegative = false;
			while(n < nLength)
			{
				char c = s.charAt(n);
				if(c >= '0' && c <= '9')
					lValue = 10 * lValue + (c - '0');
				else if(c == '-')
					bNegative = true;
				else if(c == '.')
					break;
				n++;
			}
			if(bNegative)
				return -lValue;
			return lValue;	
		}
	}
	
	
	public static long getAsUnsignedLong(String s)
	{
		if(s == null)
			return 0;
		else 
		{
			int nLength = s.length();
			if(nLength == 0)
				return 0;
	
			long lValue = 0;
			int n = 0;
			while(n < nLength)
			{
				char c = s.charAt(n);
				if(c >= '0' && c <= '9')
					lValue = 10 * lValue + (c - '0');
				else if(c == '.')
					break;
				n++;
			}
			return lValue;	
		}
	}
	
	public static double getAsDouble(String s)
	{
		if(s == null)
			return 0;
		else 
		{
			int nLength = s.length();
			if(nLength == 0)
				return 0.0;
			
			double nChunk[] = {0.0, 0.0};
			int n = 0;
			boolean bNegative = false;
			int nPart = 0;
			int nNbDecimals = 0;
			while(n < nLength)
			{
				char c = s.charAt(n);
				if(c >= '0' && c <= '9')
				{
					if(nPart == 1)	// Decimal part
						nNbDecimals++;
					nChunk[nPart] = 10 * nChunk[nPart] + (c - '0');
				}
				else if(c == '-')
					bNegative = true;
				else if(c == '.')
					nPart++;
				n++;
			}
			double dDec = nChunk[1];
			dDec = dDec / Math.pow(10, nNbDecimals);
			 
			double d = (nChunk[0]) + dDec;
			
			// Trunc at 6 digits below decimal point
			d *= 100000.0;
			d /= 100000.0; 
			if(bNegative)
				return -d;
			return d;	
		}
	}
}
