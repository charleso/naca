/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package idea.entryPoint;

import jlib.misc.ThreadSafeCounter;

public class ThreadCJMap extends Thread
{
	public ThreadCJMap(ThreadSafeCounter counter, CJMapThreadedRun cjmapRun)
	{
		m_counter = counter;
		m_cjmapRun = cjmapRun;
	}
	
	public void run()
	{
		m_cjmapRun.run();
		m_counter.dec();
	}
	
	public void requestStop()
	{
		interrupt();
	}
	
	private CJMapThreadedRun m_cjmapRun = null;
	private ThreadSafeCounter m_counter = null;
}