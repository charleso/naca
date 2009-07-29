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
 * Created on 19 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import jlib.misc.*;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GenericValueDouble extends GenericValue
{
	GenericValueDouble(double d)
	{
		m_d = d;
	}
	
	String getAsRawString()
	{
		return String.valueOf(m_d);
	}
	
	String getAsString()
	{
		return String.valueOf(m_d);
	}

	
	int getAsInt()
	{
		return (int)m_d;
	}
	
		
	int getAsUnsignedInt()
	{
		int n = getAsInt();
		if(n < 0)
			return -n;
		return n;
	}
	
	double getAsDouble()
	{
		return m_d;
	}
	
	Dec getAsDec()
	{
		String cs = String.valueOf(m_d);
		int nDot = cs.indexOf('.');
		if(nDot == -1)
		{
			long lInt = NumberParser.getAsLong(cs);
			Dec dec = new Dec(lInt, "");
			return dec;
		}
		else
		{
			String csInt = cs.substring(0, nDot);
			long lInt = NumberParser.getAsLong(csInt);
			
			String csDec = cs.substring(nDot+1);
			Dec dec = new Dec(lInt, csDec);
			return dec;
		}
	}
	
	Dec getAsUnsignedDec()
	{
		Dec dec = getAsDec();
		dec.setPositive(true);
		return dec;
	}
	
	private double m_d;
}
