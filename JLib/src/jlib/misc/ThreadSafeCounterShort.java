/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

public class ThreadSafeCounterShort
{
	public ThreadSafeCounterShort()
	{
		m_sCount = 0;
	}
	
	public ThreadSafeCounterShort(short s)
	{
		m_sCount = s;
	}
	
	private short m_sCount = 0; // count starts at zero

	public synchronized int reset()
	{
		m_sCount = 0;
		return m_sCount; 
	}
	
	public synchronized short inc(short s)
	{ 
		m_sCount += s;
		return m_sCount; 
	}
	
	public synchronized short get()
	{
		return m_sCount;
	}
	
	public synchronized short inc()
	{
		m_sCount++;
		return m_sCount;
	}

	public synchronized short dec()
	{
		m_sCount--;
		return m_sCount;
	}
}
