/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleThreadPool
{
	public SimpleThreadPool(int nNbThread)
	{
		m_pool = Executors.newFixedThreadPool(nNbThread);
	}
	
	public void enqueue(Runnable runnable)
	{
		m_pool.execute(runnable);
	}
	
	public void requestStop()
	{
		m_pool.shutdown();
	}
	
	private ExecutorService m_pool = null;
}
