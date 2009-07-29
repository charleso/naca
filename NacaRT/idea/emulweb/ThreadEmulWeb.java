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
package idea.emulweb;

import jlib.misc.ThreadSafeCounter;

public class ThreadEmulWeb extends Thread
{
	public ThreadEmulWeb(ThreadSafeCounter counter, EmulWebThreadedRun emulWebRun)
	{
		m_counter = counter;
		m_emulWebRun = emulWebRun;
	}
	
	public void run()
	{
		m_emulWebRun.run();
		m_counter.dec();
	}
	
	public void requestStop()
	{
		interrupt();
	}
	
	private EmulWebThreadedRun m_emulWebRun = null;
	private ThreadSafeCounter m_counter = null;
}