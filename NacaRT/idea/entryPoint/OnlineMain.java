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
/*
 * Created on 27 août 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package idea.entryPoint;


import java.util.ArrayList;

import jlib.log.Log;
import jlib.misc.*;
import idea.onlinePrgEnv.OnlineProgramLoader;
import idea.onlinePrgEnv.OnlineResourceManager;
import idea.onlinePrgEnv.OnlineResourceManagerFactory;
import idea.onlinePrgEnv.OnlineSession;
//import idea.programUtil.CSession;

import nacaLib.accounting.CriteriaEndRunMain;
import nacaLib.appOpening.CalendarOpenState;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.exceptions.*;

public class OnlineMain
{
	public static void main(String[] args)
	{
		String csPath = null;
		String csPrgClassName = null;
		String csDB = "";
		String csLogCfg = null;
		String m_csConfigFile = null;
		int nNbLoops = 1;
		int nNbThreads = 1;
		int nWait = 0;

		StringArray arrPath = new StringArray();
		
		EnvironmentVar.registerCmdLineArgs(args);
		 
		if(args.length >= 2)
		{
			for(int nArg=0; nArg<args.length; nArg++)
			{
				String s = args[nArg];
				if(s.startsWith("-") || s.startsWith("/"))
				{
					String sArg = s.substring(1);
					String sArgUpper = sArg.toUpperCase();
					if(sArgUpper.startsWith("PATH="))
					{						
						csPath = sArg.substring(5);
						if(!csPath.endsWith("/"))
							csPath += "/";
						arrPath.add(csPath);
					}
						 
					if(sArgUpper.startsWith("PROGRAM="))
						csPrgClassName = sArg.substring(8); 

					if(sArgUpper.startsWith("DB="))
						csDB = sArg.substring(3); 
					
					if(sArgUpper.startsWith("HELP"))
					{
						displayHelp();
						return ;
					}
					
					if(sArgUpper.startsWith("LOG="))
						csLogCfg = sArg.substring(4);
					
					if(sArgUpper.startsWith("NBLOOPS="))
					{
						String cs = sArg.substring(8);
						nNbLoops = Integer.parseInt(cs);
					}
					if(sArgUpper.startsWith("WAIT="))
					{
						String cs = sArg.substring(5);
						nWait = Integer.parseInt(cs);
					}
					
					if(sArgUpper.startsWith("NBTHREADS="))
					{
						String cs = sArg.substring(10);
						nNbThreads = Integer.parseInt(cs);
					}
					
					if(sArgUpper.startsWith("CONFIGFILE="))
					{
						m_csConfigFile = sArg.substring(11);
					}
				}
			}
			
			if(!csPrgClassName.equals("") && arrPath.size() > 0)
			{
				if(BaseResourceManager.isInUpdateMode())
				{
					Log.logCritical("Application is in update mode");
					return;
				}
				
				OnlineResourceManager resourceManager = OnlineResourceManagerFactory.GetInstance(m_csConfigFile, csDB);
				if(BaseResourceManager.getAppOpenState() != CalendarOpenState.AppOpened)
				{
					Log.logCritical("Application is closed");
					return;
				}
					
				if(nNbThreads > 1)
				{
					ArrayList<ThreadCJMap> arrThreads = new ArrayList<ThreadCJMap>();
					ThreadSafeCounter counter = new ThreadSafeCounter(nNbThreads);
					for(int n=0; n<nNbThreads; n++)
					{
						CJMapThreadedRun cjmapThreadedRun = new CJMapThreadedRun(resourceManager, nNbLoops, csPrgClassName, arrPath);  
						ThreadCJMap threadCJMap = new ThreadCJMap(counter, cjmapThreadedRun);
						arrThreads.add(threadCJMap);
					}

					
					//StopWatch sw = new StopWatch();
					// Starts threads
					for(int n=0; n<nNbThreads; n++)
					{
						ThreadCJMap thread = arrThreads.get(n);
						thread.start();
					}
					
					// Wait until all threads are over
					while(counter.get() > 0)
					{
						try
						{
							Thread.sleep(1000L);
						} 
						catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				else	// 1 thread only (batch mode)
				{
					BaseEnvironment env = null;
					try
					{
						BaseSession session = new OnlineSession(false) ;
						BaseProgramLoader loader = OnlineProgramLoader.GetProgramLoaderInstance() ;

						env = loader.GetEnvironment(session, null, null) ;
						env.startRunTransaction();
						
						loader.setPaths(arrPath);
						env.setNextProgramToLoad(csPrgClassName);
						
						//StopWatch sw = new StopWatch(); 
						for(int n =0; n<nNbLoops; n++)
						{
							env.setNextProgramToLoad(csPrgClassName);
							try
							{
								loader.runTopProgram(env, null);
							}
							catch(AbortSessionException e)
							{
							}
							if((n % 500) == 0)
							{
								int g = 0;
							}
						}
						env.endRunTransaction(CriteriaEndRunMain.Normal);
						
						Time_ms.wait_ms(nWait);
					}
					catch (CGotoException e)
					{
					}
					catch (CStopRunException e)
					{
						env.endRunTransaction(CriteriaEndRunMain.StopRun);
						String cs = e.getMessage();
						Log.logImportant(cs);
					}
				}
			}
		}
		else
			displayHelp();
		Log.close();
		JVMReturnCodeManager.exitJVM();
	}

	static void displayHelp()
	{
		System.out.println("JCMap: CtoJ Transcoded Cobol Application runtime and executor");
		System.out.println("Command line is");
		System.out.println("        JCMap ");
		System.out.println("        	-Path=CaseSensitiveString [-Path=CaseSensitiveString]"); 
		System.out.println("        	-Program=CaseSensitiveString ");
		System.out.println("       		[-DB=Oracle|DB2] (defaulted to DB2)");
		System.out.println("        	[-Help]");
		System.out.println("        	[-ConfigFile=true|false] (Defaulted to false)");
		System.out.println("        	[-NbLoops=number] (Defaulted to 1)");
		System.out.println("        	[-Log=path and file name]");
		System.out.println("");
		System.out.println("Where - A CaseSensitiveString is a case sensitive string");
		System.out.println("      - A path can use Windows directory separators (\\)");
		System.out.println("        or, Unix one (/)");
		System.out.println("        or can be a URL");
		System.out.println("      - The Program to launch must be located one one of the specified Paths");
	}
}
