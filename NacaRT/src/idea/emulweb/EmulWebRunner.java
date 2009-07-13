/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package idea.emulweb;

import idea.action.ActionCompat;
import idea.onlinePrgEnv.OnlineResourceManager;
import idea.onlinePrgEnv.OnlineResourceManagerFactory;
import idea.onlinePrgEnv.OnlineSession;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import jlib.jmxMBean.BaseCloseMBean;
import jlib.xml.XMLUtil;
import jlib.xml.XSLTransformer;

import nacaLib.exceptions.AbortSessionException;
import nacaLib.tempCache.TempCacheLocator;

import org.w3c.dom.Document;

import idea.action.ActionScenarioList;

import java.io.File;
import java.util.ArrayList;

import jlib.log.Log;
import jlib.misc.StopWatch;
import jlib.misc.ThreadSafeCounter;

import org.w3c.dom.Element;

public class EmulWebRunner extends BaseCloseMBean
{
	EmulWebRunner()
	{
		super("EmulWebRunner", "EmulWebRunner executor");
	}
	
	void run(String[] args)
	{
		String csINIFilePath = "NacaRT.cfg" ;
		if (args.length > 0)
		{
			csINIFilePath = args[0] ;
		}
		
		ReadParams(args) ;
		
		OnlineResourceManager resourceManager = OnlineResourceManagerFactory.GetInstance(csINIFilePath) ;
				
//		if (args.length > 1)
//		{
//			String csAppliPath = args[1] ;
//			if (!csAppliPath.endsWith("\\") && !csAppliPath.endsWith("/"))
//			{
//				csAppliPath += "/" ;
//			}
//			ResourceManager.setApplicationRootPath(csAppliPath) ;
//		}
		
		if (ms_bPlayAllScenario)
		{
			File dir = new File(resourceManager.getScenarioDir()) ;
			File lst[] = dir.listFiles(new ActionScenarioList.XMLFilter()) ;
			if (lst != null)
			{
				for (int i=0; i<lst.length; i++)
				{
					File file = lst[i] ;
					if (file.isFile())
					{
						Document test = XMLUtil.LoadXML(file) ;
						if (test != null)
						{
							Element e = test.getDocumentElement() ;
							String name = e.getNodeName() ;
							if (name.equalsIgnoreCase("ST3270Catch") || name.equalsIgnoreCase("datarecord"))
							{
								String filename = file.getName() ;
								System.out.println("Starting scenarion "+filename+"...") ;
								String scepath = resourceManager.getScenarioDir() + "/" + filename ;
								OnlineSession appSession = new OnlineSession(false) ;
								appSession.SetScenario(scepath) ;
								appSession.setCheckScenario(ms_bCheckScenario);
								PlayScenario(appSession, resourceManager, ms_bOutputExport) ;
								System.out.println("Completed.") ;
							}
						}
					}
				}
			}
		}
		else
		{			
			ArrayList<ThreadEmulWeb> arrThreads = new ArrayList<ThreadEmulWeb>(); 
			// Creates threads
			ThreadSafeCounter counter = new ThreadSafeCounter(ms_nNbThreads); 
			for(int n=0; n<ms_nNbThreads; n++)
			{
				EmulWebThreadedRun emulWebThreadedRun = new EmulWebThreadedRun(this, resourceManager, ms_nbLoops, ms_bCheckScenario, ms_bOutputExport);  
				ThreadEmulWeb threadEmulWeb = new ThreadEmulWeb(counter, emulWebThreadedRun);
				arrThreads.add(threadEmulWeb);
			}
			
			StopWatch sw = new StopWatch();
			// Starts threads
			for(int n=0; n<ms_nNbThreads; n++)
			{
				ThreadEmulWeb thread = arrThreads.get(n);
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
			
//			
//			CSession session = new CSession() ;
//			session.setCheckScenario(ms_bCheckScenario);
//			for (int i=0; i<ms_nbLoops; i++)
//			{
//				StopWatch sw = new StopWatch();
//				PlayScenario(session, resourceManager, ms_bOutputExport) ;
//				Log.logCritical("Scneario loop executed in " + sw.getElapsedTimeReset() + " ms");
//				waitUntilNextLoopEnabled(i);
//				session.reset();
//			}
//			
			Log.logCritical("" + ms_nbLoops + "Scenarios loops executed in by " + ms_nNbThreads + " in " + sw.getElapsedTimeReset() + " ms");
			Log.logCritical("EmulWeb finished");
			

		}
//		else
//		{
//			CEmulMapFieldLoader loader = new CEmulMapFieldLoader() ;
//			session.setInputWrapper(loader);
//			ActionCompat action = new ActionCompat() ;
//
//			action.RunClientRequest(session);
//			Document xmlOutput = session.getXMLOutput();	
//			XMLUtil.ExportXML(xmlOutput, "D:\\Dev\\NacaRunTime\\idea\\web\\1-login.xml") ;
//			renderOutput(xmlOutput, "D:\\Dev\\NacaRunTime\\idea\\web\\1-login.html") ;
//
//			loader.reset() ;
//			loader.setIDPage("rs01a10");
//			loader.setFieldValue("cdstini", "P10", false);
//			loader.setFieldValue("cdcenpi", "930", false);
//			loader.setFieldValue("recoll", "BM", true);
//			loader.setFieldValue("passcol", "toto", true);
////			loader.setFieldValue("passcol", "DOM", true);
//			loader.setFieldValue("newpass", "", false);
//			loader.setKeyPressed(KeyPressed.ENTER);
//			action.RunClientRequest(session);
//			xmlOutput = session.getXMLOutput() ;	
//			XMLUtil.ExportXML(xmlOutput, "D:\\Dev\\NacaRunTime\\idea\\web\\2-menu.xml") ;
//			renderOutput(xmlOutput, "D:\\Dev\\NacaRunTime\\idea\\web\\2-menu.html") ;
//
//			loader.reset() ;
//			loader.setIDPage("rs01a11");
//			loader.setFieldValue("mapchoi", "78", true);
//			loader.setKeyPressed(KeyPressed.ENTER);
//			action.RunClientRequest(session);
//			xmlOutput = session.getXMLOutput() ;	
//			XMLUtil.ExportXML(xmlOutput, "D:\\Dev\\NacaRunTime\\idea\\web\\3-rs78m00.xml") ;
//			renderOutput(xmlOutput, "D:\\Dev\\NacaRunTime\\idea\\web\\3-rs78m00.html") ;
//
//			loader.reset() ;
//			loader.setIDPage("rs78a00");
//			loader.setFieldValue("mapchoi", "04", true);
//			loader.setKeyPressed(KeyPressed.ENTER);
//			action.RunClientRequest(session);
//			xmlOutput = session.getXMLOutput() ;	
//			XMLUtil.ExportXML(xmlOutput, "D:\\Dev\\NacaRunTime\\idea\\web\\4-rs78m02.xml") ;
//			renderOutput(xmlOutput, "D:\\Dev\\NacaRunTime\\idea\\web\\4-rs78m02.html") ;
//		}		
	}

	/**
	 * @param args
	 */
	private static void ReadParams(String[] args)
	{
		for (int i=1; i<args.length; i++)
		{
			String cs = args[i] ;
			if (cs.startsWith("-loop="))
			{
				int posEq = cs.indexOf('=') ;
				String val = cs.substring(posEq+1);
				ms_nbLoops = Integer.parseInt(val) ;
			}
			if (cs.startsWith("-nbThreads="))
			{
				int posEq = cs.indexOf('=') ;
				String val = cs.substring(posEq+1);
				ms_nNbThreads = Integer.parseInt(val) ;
			}
			if (cs.equalsIgnoreCase("-playall"))
			{
				ms_bPlayAllScenario = true ;
			}
			if(cs.equalsIgnoreCase("-noOutputExport"))
			{
				ms_bOutputExport = false;
			}
			if(cs.equalsIgnoreCase("-noCheckScenario"))
				ms_bCheckScenario = false;
		}
		
	}
	protected static int ms_nbLoops = 1 ;
	protected static int ms_nNbThreads = 1;
	protected static boolean ms_bPlayAllScenario = false ;
	protected static boolean ms_bDeclareSemanticContext = true;
	protected static boolean ms_bOutputExport = true;
	protected static boolean ms_bCheckScenario = true;

	/**
	 * @param docScenario
	 */
	public static void PlayScenario(OnlineSession session, OnlineResourceManager resourceManager, boolean bExportOutput)
	{
		try
		{
			CScenarioPlayer player = session.getScenarioPlayer() ;
			if (player == null)
			{
				return  ;
			}

			System.out.println("Start") ;
			player.rewindScenario();

			ActionCompat action = new ActionCompat() ;
			CEmulMapFieldLoader loader = new CEmulMapFieldLoader() ;
			session.setInputWrapper(loader);
			
			action.runClientRequestWithRender(resourceManager, player, session, bExportOutput);
			
//			ProgramSequencer prgseq = ProgramSequencer.GetInstance() ;			
//			prgseq.ResetSession(session);
//			action.RunClientRequest(session) ;
//			Document xmlOutput = session.getXMLOutput();
//			String page = player.getPageNameFromXMLOutput(xmlOutput) ;
//			String csDir = resourceManager.getScenarioDir() ;
//			String csDirOut = resourceManager.getOutputDir() ;
//			if (bExportOutput)
//			{
//				String filePattern = csDir + "/"+0+"-"+page ;
//				String filePatternOut = csDirOut + "/"+0+"-"+page ;
//				XMLUtil.ExportXML(xmlOutput, filePattern+".xml") ;
//				renderOutput(xmlOutput, filePatternOut+".html") ;
//				renderOutput(xmlOutput, csDirOut + "/output.html") ;
//				System.out.println("Current page : " + page) ;
//			}
			
			
			int i=0 ;
			String csDirOut = resourceManager.getOutputDir() ;
			while (player.isPlayingScenario())
			{
				//Document xmlData = session.getXMLData() ;
				//player.CheckOutput(xmlOutput) ;
				
				Document data = player.getCurrentPage() ;

				i=player.nextPage() ;
				action.RunClientRequest(session, data) ;

				Document xmlOutput = session.getXMLOutput();
				if (bExportOutput)
				{
					String page = player.getPageNameFromXMLOutput(xmlOutput) ;
					String filePatternOut = csDirOut + "/"+i+"-" +page ;
					XMLUtil.ExportXML(xmlOutput, filePatternOut+".xml") ;
					renderOutput(xmlOutput, filePatternOut+".html") ;
					renderOutput(xmlOutput, csDirOut + "/output.html") ;
					System.out.println("Current page : " + page) ;
				}
			}
		} 
		catch (AbortSessionException e)
		{
			e.printStackTrace();
			TempCacheLocator.getTLSTempCache().popCurrentProgram();
			return ;
		}

	}

	/**
	 * @param eForm
	 * @param eCycle
	 */

	/**
	 * @param eCycle
	 * @return
	 */

	/**
	 * @param xmlOutput
	 * @return
	 */


	private static void renderOutput(Document xmlOutput, String filename)
	{
		try
		{
			FileOutputStream file = new FileOutputStream(filename);
			if (xmlOutput == null)
			{
			}
			else
			{
				OnlineResourceManager resource = OnlineResourceManagerFactory.GetInstance() ;
				XSLTransformer xformer = resource.getXSLTransformer() ;
				if (!xformer.doTransform(xmlOutput, file))
				{
				}
				//XMLUtil.ExportXML(xmlOutput, "output.xml") ;
//				resource.returnXSLTransformer(xformer);
			}
			file.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}	
	}
	
    protected void buildDynamicMBeanInfo() 
    {
    	addAttribute("EnableNextLoop", getClass(), "EnableNextLoop", Boolean.class);
    	addAttribute("EnableRemainingLoops", getClass(), "EnableRemainingLoops", Boolean.class);
//    	addOperation("Set or reset Enable", getClass(), "setEnable", Boolean.class);	//Boolean.TYPE);
//        addOperation("Set critical level", getClass(), "setCritical");
//        addOperation("Set Important level", getClass(), "setImportant");
//        addOperation("Set Normal level", getClass(), "setNormal");
//        addOperation("Set Verbose level", getClass(), "setVerbose");
//        addOperation("Set Debug level", getClass(), "setDebug");
//        addOperation("Set Fine Debug level", getClass(), "setFineDebug");
		
    }
    
	public Boolean getEnableNextLoop()
	{
		return m_bNextLoopEnabled;
	}
	
	public void setEnableNextLoop(Boolean b)
	{
		m_bNextLoopEnabled = b;
	}
	
	public void setEnableRemainingLoops(Boolean b)
	{
		m_bEnableRemainingLoops = b;
	}
	
	public Boolean getEnableRemainingLoops()
	{
		return m_bEnableRemainingLoops;
	}
//	
//	private void waitUntilNextLoopEnabled(int i)
//	{	
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
//	}
	
	private boolean m_bNextLoopEnabled = false;
	private boolean m_bEnableRemainingLoops = false;
}