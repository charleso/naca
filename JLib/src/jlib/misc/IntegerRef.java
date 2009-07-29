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

public class IntegerRef
{
	public IntegerRef()
	{
	}
	
	public IntegerRef(int n)
	{
		m_n = n;
	}

	
	public int get()
	{
		return m_n;
	}
	
	public void set(int n)
	{
		m_n = n;
	}

	public void inc(int n)
	{
		m_n += n;
	}
	
	public void inc()
	{
		m_n++;
	}
	
	public void dec()
	{
		m_n--;
	}
	
	public String toString()
	{
		return "IntegerRef:" + m_n;
	}
	
	private int m_n = 0;
}
