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
/**
 * 
 */
package jlib.threads;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
// Usage 

/*
public class MyTimer extends Timer
{
	private Caller m_c = null;
	MyTimer(Caller c)
	{
		m_c = c;	
	}
	
	protected boolean pulse()
	{
		return m_c.onTimerPulse();
	}
}

// Caller code in class Caller 

class Caller
{
...
	void launchTimer()
	{
		...
		MyTimer timer = new MyTimer(this);
		timer.startTimer(60000);	// Pulse every minute
		...
	}
	
	public boolean onTimerPulse()	// Executed in the context of the timer thread
	{
		// Do actions ...
		return true; 
	}

*/

public abstract class Timer extends Thread
{
	private int m_nPeriodWait_ms = 1000;
	
	public Timer()
	{
	}
	
	public void startTimer(int nPeriodWait_ms)
	{
		m_nPeriodWait_ms = nPeriodWait_ms;
		start();
	}
	
	public void run()
	{
		boolean bContinue = true;
		while(bContinue)
		{
			try
			{
				Thread.sleep(m_nPeriodWait_ms);
				bContinue = pulse();
			} 
			catch (InterruptedException e)
			{
			}
		}
	}
	
	protected abstract boolean pulse();
	
	public void requestStop()
	{
		interrupt();
	}
}
