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

/*
 * Created on 17 déc. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author PJD
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class StopWatch
{
	public StopWatch()
	{
		m_lStart = System.currentTimeMillis();
	}
	
	public long getElapsedTime()
	{
		long lStop = System.currentTimeMillis();
		return lStop - m_lStart;
	}

	public long getElapsedTimeReset()
	{
		long lStop = System.currentTimeMillis();
		long l = lStop - m_lStart;
		m_lStart = lStop;
		return l;		
	}
	
	public void Reset()
	{
		m_lStart = System.currentTimeMillis();
	}
	
	public boolean isTimeElapsed(long lTimeOut)
	{
		long lNow = System.currentTimeMillis();
		long lElapsed = lNow - m_lStart;
		if(lElapsed >= lTimeOut)
			return true;
		return false;
	}
	
	public long getStartValue()
	{
		return m_lStart;
	}
	
	private long m_lStart = 0;
}
