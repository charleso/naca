/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.threads;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class FixedSizeBlockingQueue<T>
{
	public FixedSizeBlockingQueue(int nNbEntries)
	{
		m_nNbEntries = nNbEntries;
		m_arr = new Object[nNbEntries];
		m_semFilledEntries = new Semaphore(0, true);
		m_semNotFilledEntries = new Semaphore(nNbEntries, true);
	}
	
	public void enqueue(T t)
	{
		try
		{
			m_semNotFilledEntries.acquire();
		}
		catch (InterruptedException e)
		{
			return;
		}

		synchronized(m_arr)
		{
			m_arr[m_nIndexSet] = t;
			m_nIndexSet++;
			if(m_nIndexSet >= m_nNbEntries)
				m_nIndexSet  = 0;
		}
		m_semFilledEntries.release();
	}
	
	public T dequeue()
	{		
		try
		{
			m_semFilledEntries.acquire();
		}
		catch (InterruptedException e)
		{
			return null;
		}

		synchronized(m_arr)
		{
			T t = (T)m_arr[m_nIndexGet];
			m_nIndexGet++;
			if(m_nIndexGet >= m_nNbEntries)
				m_nIndexGet = 0;
			m_semNotFilledEntries.release();
			return t;
		}		
	}
	
	private int m_nNbEntries = 0;
	private Object m_arr[] = null;
	private int m_nIndexSet = 0;
	private int m_nIndexGet = 0;
	private Semaphore m_semFilledEntries = null;	// Semaphore counting the number of filled entries
	private Semaphore m_semNotFilledEntries = null;	// Semaphore counting the number of entries that can still be filled (that are not filled yet)
}
