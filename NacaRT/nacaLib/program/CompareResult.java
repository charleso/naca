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
package nacaLib.program;

public class CompareResult
{
	public static final CompareResult Less = new CompareResult(-1);
	public static final CompareResult Equals = new CompareResult(0);
	public static final CompareResult Greater = new CompareResult(1);	
	
	private int m_n = 0;
	
	
	private CompareResult(int n)
	{
		m_n = n;
	}
	
	public static final CompareResult get(int n)
	{
		if(n < 0)
			return Less;
		if(n == 0)
			return Equals;	
		
		return Greater;
	}
	
	public boolean isGreater()
	{
		if(m_n > 0)
			return true;
		return false;
	}
	
	public boolean isLess()
	{
		if(m_n < 0)
			return true;
		return false;
	}
	
	public boolean isEqual()
	{
		if(m_n == 0)
			return true;
		return false;
	}
	
	public String toString()
	{
		if(m_n < 0)
			return "Less";
		if(m_n == 0)
			return "Equals";
		return "Greater";
	}
}
