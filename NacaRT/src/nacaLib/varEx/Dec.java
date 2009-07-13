/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 21 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import nacaLib.tempCache.CStr;
import nacaLib.tempCache.TempCacheLocator;
import jlib.misc.*;


/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Dec extends DecBase
{
	public Dec(Dec dec)
	{
		super();
		m_lInt = dec.m_lInt;
		m_csDec = dec.m_csDec;
		m_bPositive = dec.m_bPositive;
	}
	
	public Dec(long lInt, CStr csDec)
	{
		super();
		setLong(lInt);
		setDecPart(csDec.getAsString());
	}

	public Dec(long lInt, String csDec)
	{
		super(lInt, csDec);
	}
	
	public Dec(String csInt, String csDec)
	{
		super(csInt, csDec);
	}
	
	public void setDecPart(CStr csDec)
	{
		m_csDec = csDec.getAsString();
	}
	
	double getAsDouble()
	{
		String cs = String.valueOf(m_lInt) + "." + m_csDec;
		double d = Double.parseDouble(cs);
		if(!m_bPositive)
			return -d;
		return d;
	}
	
	int getSignedInt()
	{
		if(m_bPositive)
			return (int)m_lInt;
		return (int) -m_lInt;
	}
	
	public long getSignedLong()
	{
		if(m_bPositive)
			return m_lInt;
		return -m_lInt;
	}
	
	int getUnsignedInt()
	{
		return (int)m_lInt;
	}
	
	String getUnsignedLongAsString()
	{
		String cs = String.valueOf(m_lInt);
		return cs;
	}

//	String getDecPartAsString()
//	{
//		return getDecPart();
//	}

	String getAsString()
	{
		String cs = "";
		if(isNegative())
			cs = "-";
		long l = getUnsignedLong();
		cs += String.valueOf(l);
		if(m_csDec != "")
			cs += "." + m_csDec;
		return cs;
	}
	
	CStr getAsCStr()
	{
		String s = getAsString();
		CStr cs = TempCacheLocator.getTLSTempCache().getReusableCStr();
		cs.set(s);
		return cs;
	}
	
	public int compare(int n)
	{
		long lThis = getSignedLong();
		long l = n;
		
		if(lThis < l)
			return -1;
		if(lThis == l)
			return 0;
		return 1;
	}
	
	public int compare(long l)
	{
		long lThis = getSignedLong();
		if(lThis < l)
			return -1;
		if(lThis == l)
			return 0;
		return 1;
	}
			
	public int compare(Dec dec2)
	{
		long lSignedInt = getSignedLong();
		long lSignedInt2 = dec2.getSignedLong();
		if(lSignedInt < lSignedInt2)
			return -1;
		if(lSignedInt == lSignedInt2)
		{
			long l1 = getDecAsLong();
			long l2 = dec2.getDecAsLong();
			if(l1 < l2)
				return -1;
			if(l1 == l2)
				return 0;
		}
		return 1;
	}
	
	public long getDecAsLong()
	{
		String csDec = StringUtil.rightPad(m_csDec, 14, '0');
		long l = NumberParser.getAsLong(csDec);
		if(m_bPositive)
			return l;
		return -l;
	}
	
	public boolean isZero()
	{
		if(m_lInt == 0 && NumberParser.getAsInt(m_csDec) == 0)
			return true;
		return false;
	}
}
