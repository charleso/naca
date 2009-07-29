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
public class GenericValueString extends GenericValue
{
	GenericValueString(String cs)
	{
		m_cs = cs;
	}
	
	String getAsRawString()
	{
		return m_cs;
	}
	
	String getAsString()
	{
		return String.valueOf(m_cs);
	}

	
	int getAsInt()
	{
		int n = NumberParser.getAsInt(m_cs);
		return n;
	}
	
	int getAsUnsignedInt()
	{
		int n = getAsInt();
		if(n < 0)
			return -n;
		return n;
	}
		
	
	Dec getAsDec()
	{
		long l = NumberParser.getAsLong(m_cs);
		Dec dec = new Dec(l, "");
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
		double d = NumberParser.getAsDouble(m_cs);
		return d;
	}
	
	private String m_cs = null;
}
