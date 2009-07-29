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

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSafeCounter
{
	public ThreadSafeCounter()
	{
		m_nCount.set(0);
	}
	
	public ThreadSafeCounter(int n)
	{
		m_nCount.set(n);
	}
	
	public int reset()
	{
		m_nCount.set(0);
		return 0; 
	}
	
	public int inc(int n)
	{ 
		return m_nCount.addAndGet(n);
	}
	
	public int get()
	{
		return m_nCount.get();
	}
	
	public int inc()
	{
		return m_nCount.incrementAndGet();
	}

	public int dec()
	{
		return m_nCount.decrementAndGet();
	}
	
	private AtomicInteger m_nCount = new AtomicInteger(0); // count starts at zero
}
