/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package idea.entryPoint;


import nacaLib.accounting.CriteriaEndRunMain;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.exceptions.CGotoException;

import jlib.log.Log;
import jlib.misc.StringArray;
import idea.onlinePrgEnv.OnlineProgramLoader;
import idea.onlinePrgEnv.OnlineResourceManager;
import idea.onlinePrgEnv.OnlineSession;

public class CJMapThreadedRun
{
	CJMapThreadedRun(OnlineResourceManager resourceManager, int nNbLoops, String csPrgClassName, StringArray arrPath)
	{
		m_resourceManager = resourceManager;
		m_nNbLoops = nNbLoops;
		m_csPrgClassName = csPrgClassName;
		m_arrPath = arrPath;
	}
	
	void run()
	{
		OnlineSession session = new OnlineSession(false) ;
		BaseProgramLoader loader = OnlineProgramLoader.GetProgramLoaderInstance() ;
		BaseEnvironment env = loader.GetEnvironment(session, null, null) ;
		try
		{	
			env.startRunTransaction();
			
			loader.setPaths(m_arrPath);
			env.setNextProgramToLoad(m_csPrgClassName);
			
			//StopWatch sw = new StopWatch(); 
			for(int n =0; n<m_nNbLoops; n++)
			{
				env.setNextProgramToLoad(m_csPrgClassName);
				loader.runTopProgram(env, null);
			}
			env.endRunTransaction(CriteriaEndRunMain.Normal);
			
			Log.logImportant("Finished; Waiting 10 minutes");
		}
		catch (CGotoException e)
		{
			env.endRunTransaction(CriteriaEndRunMain.GotoInAsyncStart);
		}
	}	
	
	OnlineResourceManager m_resourceManager = null;
	int m_nNbLoops = 0;
	String m_csPrgClassName = null;
	StringArray m_arrPath = null;
}
