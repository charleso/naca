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
 * Created on 21 mars 2005
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
public class GenericValueDec extends GenericValue
{
	GenericValueDec(Dec dec)
	{
		m_dec = dec;
	}
	
	GenericValueDec(int nInt, String csDec)
	{
		m_dec = new Dec(nInt, csDec);
	}
	
	String getAsRawString()
	{
		String cs = m_dec.getAsString();
		return cs;
	}
	
	String getAsString()
	{
		return getAsRawString();
	}
	
	int getAsInt()
	{
		return m_dec.getSignedInt();
	}
	
	int getAsUnsignedInt()
	{
		return m_dec.getUnsignedInt();
	}

	Dec getAsDec()
	{
		return m_dec;
	}
		
	Dec getAsUnsignedDec()
	{
		if(m_dec.isNegative())
		{			
			Dec dec = new Dec(m_dec);
			dec.setPositive(true);
			return dec;
		}
		return m_dec;
	}
	
	
	double getAsDouble()
	{
		return m_dec.getAsDouble(); 
	}	

	
	private Dec m_dec = null;
}
