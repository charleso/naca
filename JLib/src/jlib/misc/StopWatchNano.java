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

public class StopWatchNano
{
	public StopWatchNano()
	{
		m_lStart = System.nanoTime();
	}
	
	public long getElapsedTime()
	{
		long lStop = System.nanoTime();
		return lStop - m_lStart;
	}

	public long getElapsedTimeReset()
	{
		long lStop = System.nanoTime();
		long l = lStop - m_lStart;
		m_lStart = lStop;
		return l;		
	}
	
	public void reset()
	{
		m_lStart = System.nanoTime();
	}
	
	public static long getMicroSecond(long l)
	{
		return l / 1000;
	}
	
	public static long getMilliSecond(long l)
	{
		return l / 1000000;
	}
	
	
	private long m_lStart = 0;
}
