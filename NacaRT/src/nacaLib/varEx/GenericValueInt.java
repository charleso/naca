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



/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GenericValueInt extends GenericValue
{
	GenericValueInt(int n)
	{
		m_n = n;
	}
	
	String getAsRawString()
	{
		return String.valueOf(m_n);
	}
	
	String getAsString()
	{
		return String.valueOf(m_n);
	}

	
	int getAsInt()
	{
		return m_n;
	}
	
	int getAsUnsignedInt()
	{
		if(m_n < 0)
			return -m_n;
		return m_n;
	}
	
	Dec getAsDec()
	{
		Dec dec = new Dec(m_n, "");
		return dec;
	}
	
	Dec getAsUnsignedDec()
	{
		Dec dec = getAsDec();
		dec.setPositive(true);
		return dec;
	}
	
	double getAsDouble()
	{
		return m_n;
	}	
	
	private int m_n;
}
