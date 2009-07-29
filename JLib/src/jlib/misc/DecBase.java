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

import java.math.BigDecimal;


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class DecBase
{
	protected String m_csDec;
	protected long m_lInt;
	protected boolean m_bPositive;
	
	public DecBase()
	{
	}
	
	public DecBase(String csInt, String csDec)
	{
		long lInt = NumberParser.getAsLong(csInt);
		setLong(lInt);
		m_csDec = csDec;
	}
	
	public DecBase(long lInt, String csDec)
	{
		setLong(lInt);
		setDecPart(csDec);
	}
	
	public void setLong(long lInt)
	{
		if(lInt >= 0)
		{
			m_lInt = lInt;
			m_bPositive = true;
		}
		else
		{
			m_lInt = -lInt;
			m_bPositive = false;
		}
	}
	
	public void setPositive(boolean bPositive)
	{
		m_bPositive = bPositive;
	}

	public void setNegativeForced()
	{
		m_bPositive = false;
	}

	public boolean isNegative()
	{
		return !m_bPositive;
	}

	public boolean isPositive()
	{
		return m_bPositive;
	}

	public void setUnsigned()
	{
		m_bPositive = true;
	}

	public void setDecPart(String csDec)
	{
		m_csDec = csDec;
	}
	
	public long getUnsignedLong()
	{
		return m_lInt;
	}
	
	public long getSignedLong()
	{
		if(m_bPositive)
			return m_lInt;
		return -m_lInt;
	}

	
	public String getDecPart()
	{
		return m_csDec;
	}
	
	public int getLeftMostDigitOfDecPartAsInt(int nNbDigits)
	{		
		if(m_csDec.length() <= 0)
			return 0;
		
		int n=0;
		int nDec = 0;
		int nDecLength = m_csDec.length();
		while(n < nDecLength && n < nNbDigits)
		{
			nDec *= 10;
			char c = m_csDec.charAt(n);
			nDec += c - '0';
			n++;
		}
		while (n < nNbDigits)
		{
			nDec *= 10;
			n++;
		}
		return nDec;
	}
	
	public static DecBase toDec(BigDecimal bd)
	{
		boolean bPositive = true;

		if(bd.signum() < 0)
			bPositive = false;
			
		String sValue = bd.abs().unscaledValue().toString();
		int nScale = bd.scale();
		if(sValue.length() > nScale)
		{
			String sInt = sValue.substring(0, sValue.length()-nScale);
			String sDec = sValue.substring(sValue.length()-nScale);
			DecBase dec = new DecBase(sInt, sDec);
			dec.setPositive(bPositive);
			return dec;
		}
		else
		{
			String sDec = new String();
			int nNbLeadingZeros = nScale - sValue.length();
			for(int n=0; n<nNbLeadingZeros; n++)
			{
				sDec = sDec + "0";
			}
			sDec = sDec + sValue;
			
			DecBase dec = new DecBase(0, sDec); 
			dec.setPositive(bPositive);
			return dec;
		}
	}
	
	public String toString()
	{
		String cs;
		if(isNegative())
			cs = "Negative; Int=";
		else
			cs = "Positive; Int=";
		cs += m_lInt;
		cs += "; Decimal="+m_csDec;
		return cs;
	}
}
