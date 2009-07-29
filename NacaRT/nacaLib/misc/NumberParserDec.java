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
 * Created on 24 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.misc;

import java.math.BigDecimal;

import jlib.misc.NumberParser;
import nacaLib.tempCache.CStr;
import nacaLib.varEx.Dec;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NumberParserDec extends NumberParser
{
	public static Dec getAsDec(int n)
	{
		String cs = String.valueOf(n);
		Dec dec = getAsDec(cs);
		return dec;
	}
	
	public static Dec getAsDec(double d)
	{
		String cs = String.valueOf(d);
		Dec dec = getAsDec(cs);
		return dec;
	}
	
	public static Dec getAsDec(CStr cs)
	{
		String s = cs.getAsString();
		return getAsDec(s);
	}
	
	public static Dec getAsDec(String cs)
	{
		int nLength = cs.length();
		if(nLength > 0)
		{
			char c;			
			long lInt = 0;
			boolean bPositive = true;
			
			int nIndex = 0;
			// Integer part
			while (nIndex < nLength)
			{
				c = cs.charAt(nIndex);
				if (c >= '0' && c <= '9')
				{
					lInt *= 10;
					lInt += (c - '0');
				}
				else if (c == '-')
				{
					bPositive = false;
				}
				else if (c == '.')
				{
					break;
				}
				nIndex++;
			}
			
			// Decimal part
			nIndex++;
			if (nIndex < nLength)
			{	
				String csDec = "";
				while (nIndex < nLength)
				{
					c = cs.charAt(nIndex);
					if (c >= '0' && c <= '9')
					{
						csDec += c;
					}
					else if (c == '-')
					{
						bPositive = false;
					}
					nIndex++;
				}
				
				if (csDec.equals("")) csDec = "0";
					
				Dec dec = new Dec(lInt, csDec);
				dec.setPositive(bPositive);
				return dec;
			}
			else
			{
				Dec dec = new Dec(lInt, "0");
				dec.setPositive(bPositive);
				return dec;
			}
		}
		else
		{
			Dec dec = new Dec(0, "0");
			dec.setPositive(true);
			return dec;
		}
	}
	
	public static Dec getAsDec(BigDecimal bigDecimal)
	{
		// to optimize ...
		String cs = bigDecimal.toString();
		return getAsDec(cs);
//		long lInt = bigDecimal.longValue();
//		String csDecimals = bigDecimal.toString();
	}

/*
	public static Dec getAsDec(String cs)
	{
		boolean bPositive = true;
		if(cs.length() > 0 && cs.charAt(0) == '-')
		{
			cs = cs.substring(1);
			bPositive = false;
		}
		int nDot = cs.indexOf('.');
		if(nDot >= 0)
		{
			String csInt = cs.substring(0, nDot);
			String csDec = cs.substring(nDot+1);
			Dec dec = new Dec(csInt, csDec);
			dec.setPositive(bPositive);
			return dec;
		}
		else
		{
			Dec dec = new Dec(cs, "0");
			dec.setPositive(bPositive);
			return dec;
		}	
	}
*/	
	public static Dec getAsDec(char c)
	{
		String cs = String.valueOf(c);
		Dec dec = new Dec(cs, "0");
		return dec;
	}

}
