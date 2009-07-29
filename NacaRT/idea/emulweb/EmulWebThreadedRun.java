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

import jlib.log.Log;
import jlib.misc.StopWatch;
import idea.onlinePrgEnv.OnlineResourceManager;
import idea.onlinePrgEnv.OnlineSession;

public class EmulWebThreadedRun
{
	EmulWebThreadedRun(EmulWebRunner emulWebRunner, OnlineResourceManager resourceManager, int nbLoops, boolean bCheckScenario, boolean bOutputExport)
	{
		m_emulWebRunner = emulWebRunner;
		m_resourceManager = resourceManager;
		m_nbLoops = nbLoops;
		m_bCheckScenario = bCheckScenario;
		m_bOutputExport = bOutputExport;
	}
	
	void run()
	{	
		OnlineSession session = new OnlineSession(false) ;
		session.setCheckScenario(m_bCheckScenario);
		for (int i=0; i<m_nbLoops; i++)
		{
			StopWatch sw = new StopWatch();
			EmulWebRunner.PlayScenario(session, m_resourceManager, m_bOutputExport) ;
			Log.logCritical("Scneario loop executed in " + sw.getElapsedTimeReset() + " ms");
			waitUntilNextLoopEnabled(i);
			session.reset();
		}
		Log.logCritical("EmulWebRun finished");
	}
	
	private void waitUntilNextLoopEnabled(int i)
	{	
//		if(!m_bEnableRemainingLoops)
//		{
//			Log.logCritical("EmulWeb Loop " + i + " Done; waiting to be enabled by jmx ...");	
//			while(!m_bNextLoopEnabled)
//			{
//				try
//				{
//					Thread.sleep(1000L);
//				} 
//				catch (InterruptedException e)
//				{
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			m_bNextLoopEnabled= false;
//		}
	}
	
	int m_nbLoops = 0;
	boolean m_bCheckScenario = false;
	boolean m_bOutputExport = false;
	OnlineResourceManager m_resourceManager = null;
	EmulWebRunner m_emulWebRunner = null;
}
