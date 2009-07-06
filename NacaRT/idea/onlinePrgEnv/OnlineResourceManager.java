/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package idea.onlinePrgEnv;


import idea.manager.PreloadProgramSettings;
import idea.manager.ProgramPreloader;
import idea.semanticContext.CMenuDef;
import idea.semanticContext.SemanticManager;
import idea.view.XMLMerger;
import idea.view.XMLMergerManager;

import java.io.File;
import java.util.ArrayList;

import jlib.classLoader.CodeManager;
import jlib.log.Log;
import jlib.misc.FileSystem;
import jlib.misc.LdapRequester;
import jlib.misc.StopWatch;
import jlib.misc.StringUtil;
import jlib.xml.Tag;
import jlib.xml.XMLUtil;
import jlib.xml.XSLTransformer;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.misc.LogFlowCustomNacaRT;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/*
 * Created on 8 déc. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class OnlineResourceManager extends BaseResourceManager
{	
	protected String m_csResourcePath = "" ; //"D:\\Dev\\CJTests\\CJTestDev\\src\\" ;
	protected String m_csAlternateResourcePath = "";
		
	protected int m_nNbInstanceToPreload = 1;
	protected boolean m_bPreLoadAllProgramFromDir = false;	// true if try to load all programs form directory
	protected boolean m_bKeepPreloadedProgramList = false;	// true if you want to register into [m_csPreLoadProgramList] the preloaded program list; usefull to build the list of program from the dir  
	protected boolean m_bPreLoadAllProgramFromList = false;	// true if load all programs indiciated in [m_csPreLoadProgramList] 
	protected String m_csPreLoadProgramList = "";	// Gives the path and name of the file indicating a program list to be loaded in mode m_bPreLoadAllProgramFromList; it is updated in mode m_bPreLoadAllProgramFromDir   
	
	protected String m_csXMLFrameFilePath = "";
	protected String m_csXMLFramePSFilePath = "";
	
	protected String m_csSemanticContextPathFile = "";
	
	
	protected String m_csJarXMLFile = "";
	protected String m_csCustomApplicationLauncherConfigFilePath = "" ;
	
	private Document m_xmlFrame = null ;
	
	private jlib.display.ResourceManager m_StdResourceManager = new jlib.display.ResourceManager() ;
	
	private OnlineResourceBeanManager m_resourceBeanManager = null;
	
		OnlineResourceManager()
	{
		super(true);
		m_resourceBeanManager = new OnlineResourceBeanManager(this);
	}
	
	public Document getXmlFrame()
	{
		return m_xmlFrame;
	}
	
	private void doInitialize(String csINIFilePath, boolean bLoadSemanticContextDef)
	{
		m_resourceBeanManager.setJarXMLFile(m_csJarXMLFile);
		m_resourceBeanManager.LoadResourceCache(ms_bCacheResourceFiles);
		if(bLoadSemanticContextDef)
		{
			// Load semantic context data dictionnary: Defines semantic context associtaed to DB columns
			loadDBSemanticContextDef();		
			
			// Load semantic context configuration file: Defines menus, options, ...
			String csSemanticContext = getSemanticContextPathFile();
			if(csSemanticContext != null && csSemanticContext.length() != 0)
			{
				SemanticManager semanticManager = SemanticManager.GetInstance();
				semanticManager.Init(csSemanticContext);
				registerSemanticManager(semanticManager);			
			}
		}
		preloadPrograms();
	}

	void initialize(String csINIFilePath, String csDBParameterPrefix, boolean bCacheResourceFiles, boolean bLoadSemanticContextDef)
	{
		setXMLConfigFilePath(csINIFilePath) ;
		initSequenceur(csDBParameterPrefix);
		doInitialize(csINIFilePath, bLoadSemanticContextDef);
	}
	
	void initialize(String csINIFilePath, String csDBParameterPrefix)//, boolean ModeBatch)
	{
		setXMLConfigFilePath(csINIFilePath) ;
		initSequenceur(csDBParameterPrefix);
		boolean bLoadSemanticContextDef = !StringUtil.isEmpty(m_csSemanticContextPathFile);
		doInitialize(csINIFilePath, bLoadSemanticContextDef);
	}	

	private void preloadPrograms()
	{
		ProgramPreloader programPreloader = null;
		ArrayList<PreloadProgramSettings> arrProgramToPreload = null;
		if(m_bPreLoadAllProgramFromDir)
		{
			programPreloader = new ProgramPreloader(); 
			if(!StringUtil.isEmpty(m_csApplicationClassPath))
				arrProgramToPreload = programPreloader.buildArrayPreloadProgramFromDir(m_csApplicationClassPath);			
		}
		else if(m_bPreLoadAllProgramFromList)
		{
			programPreloader = new ProgramPreloader();
			arrProgramToPreload = programPreloader.buildArrayPreloadProgramFromList(m_csPreLoadProgramList);
		}
		
		if(programPreloader != null && arrProgramToPreload != null)
		{
			Log.logNormal("Program preload starts");
			StopWatch sw = new StopWatch(); 
			
			String csProgramListToKeep = m_csPreLoadProgramList;
			if(!m_bKeepPreloadedProgramList)
				csProgramListToKeep = null;
			
			if(BaseResourceManager.isAsynchronousPreloadPrograms())
			{
				AsynchronousProgramPreloaderThread asynchronousProgramPreloaderThread = new AsynchronousProgramPreloaderThread(this, programPreloader, arrProgramToPreload, csProgramListToKeep);
				asynchronousProgramPreloaderThread.start();				
			}
			else
				programPreloader.preloadProgramsSynchronous(arrProgramToPreload, m_Sequencer, csProgramListToKeep);

			Log.logNormal("Program preload ends: it took " + sw.getElapsedTime() + " ms");
		}
	}
	
	public void AsynchronouslyPreloadPrograms(ArrayList<PreloadProgramSettings> arrProgramToPreload, ProgramPreloader programPreloader, String csProgramListToKeep)
	{
		programPreloader.preloadProgramsSynchronous(arrProgramToPreload, m_Sequencer, csProgramListToKeep);
	}
	
	public XSLTransformer getHelpTransformer()
	{
		return m_StdResourceManager.getXSLTransformer("IDEA_HELP") ;
	}
	public XSLTransformer getPrintScreenTransformer()
	{
		return m_StdResourceManager.getXSLTransformer("IDEA_PRINT_SCREEN") ;
	}
	
	public XSLTransformer getXSLTransformer()
	{
		return m_StdResourceManager.getXSLTransformer("IDEA") ;
	}
	
	public XSLTransformer getXSLTransformerBold()
	{
		return m_StdResourceManager.getXSLTransformer("IDEA_BOLD") ;
	}
	
	public XSLTransformer getXSLTransformerZoom()
	{
		return m_StdResourceManager.getXSLTransformer("IDEA_ZOOM") ;
	}
	
	public XSLTransformer getXSLTransformerZoomBold()
	{
		return m_StdResourceManager.getXSLTransformer("IDEA_ZOOM_BOLD") ;
	}
	
	public Document GetXMLPage(String csIdPageupperCase)
	{
		return m_resourceBeanManager.GetXMLPage(csIdPageupperCase);
	}
	
	public Document GetXMLStructure(String idPage)
	{
		return m_resourceBeanManager.GetXMLStructure(idPage);
	}			

	public Document GetXMLStructureForPrintScreen(String idPage)
	{
		if (m_csXMLFramePSFilePath == null || m_csXMLFramePSFilePath.equals(""))
		{
			return m_resourceBeanManager.GetXMLStructure(idPage);
		}
		else
		{	
			String csIdPageupperCase = idPage.toUpperCase();
			Document struct = null ;
			Document doc = GetXMLPage(csIdPageupperCase) ;
			if (doc != null)
			{
				XMLMerger merger = XMLMergerManager.get(null);	//new XMLMerger(null) ;
				NodeList lstForms = doc.getElementsByTagName("form") ;
				for (int j=0; j<lstForms.getLength(); j++)
				{
					Element eForm = (Element)lstForms.item(j);
					String name = eForm.getAttribute("name") ;
					if (name.equalsIgnoreCase(idPage))
					{
						Document xmlFramePS = XMLUtil.LoadXML(m_csXMLFramePSFilePath) ;
						struct = merger.BuildXLMStructure(xmlFramePS, eForm) ;
						XMLMergerManager.release(merger);
						return struct ;
					}
				}
				XMLMergerManager.release(merger);
			}
			return null;
		}	
	}


	protected Document m_docLogSettings = null ;
	protected String m_csScenarioFilePath = "" ;
	
	protected String m_csScenarioDir = "" ;
	protected String m_csScenarioOutputDir ="" ;
	
	protected void LoadConfigFromFile(Tag tagRoot)
	{
		if(tagRoot != null)
		{
			String csLogCfg = tagRoot.getVal("LogSettingsPathFile");
			
			LogFlowCustomNacaRT.declare();
			Tag tagLogSettings = Log.open("NacaRT", csLogCfg);
			if (tagLogSettings != null)
			{
				Tag tagSettings = tagLogSettings.getChild("Settings");
				if(tagSettings != null)
				{
//					isLogCESM = tagSettings.getValAsBoolean("CESM"); 
//					isLogFlow = tagSettings.getValAsBoolean("Flow");
//					isLogSql = tagSettings.getValAsBoolean("Sql");
//					IsSTCheck = tagSettings.getValAsBoolean("STCheck");
				}
			}
			
			
			ms_nHttpSessionMaxInactiveInterval_s = tagRoot.getValAsInt("HttpSessionMaxInactiveInterval_s");
			ms_bCacheResourceFiles = tagRoot.getValAsBoolean("CacheResourceFiles") ;
			
			String csEmulWebRootPath = tagRoot.getVal("EmulWebRootPath") ;
			OnlineResourceManager.setOnceRootPath(csEmulWebRootPath);
						
			String csXSLFilePath = tagRoot.getVal("XSLFilePath") ;
			m_StdResourceManager.setXSLFilePath("IDEA", csXSLFilePath) ;
			
			String csXSLFilePathBold = tagRoot.getVal("XSLFilePathBold") ;
			if (csXSLFilePathBold != null && !csXSLFilePathBold.equals(""))
				m_StdResourceManager.setXSLFilePath("IDEA_BOLD", csXSLFilePathBold) ;
			
			String csXSLFilePathZoom = tagRoot.getVal("XSLFilePathZoom") ;
			if (csXSLFilePathZoom != null && !csXSLFilePathZoom.equals(""))
				m_StdResourceManager.setXSLFilePath("IDEA_ZOOM", csXSLFilePathZoom) ;
			
			String csXSLFilePathZoomBold = tagRoot.getVal("XSLFilePathZoomBold") ;
			if (csXSLFilePathZoomBold != null && !csXSLFilePathZoomBold.equals(""))
				m_StdResourceManager.setXSLFilePath("IDEA_ZOOM_BOLD", csXSLFilePathZoomBold) ;

			String csXSLPSFilePath = /*getRootPath() + */tagRoot.getVal("PSXSLFilePath") ;
			if (csXSLPSFilePath != null && !csXSLPSFilePath.equals(""))
				m_StdResourceManager.setXSLFilePath("IDEA_PRINT_SCREEN", csXSLPSFilePath) ;
			
			String csXSLHelpFilePath = /*getRootPath() + */tagRoot.getVal("HelpXSLFilePath") ;
			m_StdResourceManager.setXSLFilePath("IDEA_HELP", csXSLHelpFilePath) ;
			
			m_csResourcePath = getApplicationRootPath() + tagRoot.getVal("ResourcePath") ;
			m_csResourcePath = FileSystem.normalizePath(m_csResourcePath);
			
			m_csAlternateResourcePath = getApplicationRootPath() + tagRoot.getVal("AlternateResourcePath") ;
			if(!StringUtil.isEmpty(m_csAlternateResourcePath))
				m_csAlternateResourcePath = FileSystem.normalizePath(m_csAlternateResourcePath);
			
			m_bPreLoadAllProgramFromDir = tagRoot.getValAsBoolean("PreLoadAllProgramFromDir") ;
			m_bKeepPreloadedProgramList = tagRoot.getValAsBoolean("KeepPreloadedProgramList") ;
			
//			String cs = tagRoot.getVal("NbInstanceToPreload");
//			if(cs == null)
//				m_nNbInstanceToPreload = 1;
//			else
//				m_nNbInstanceToPreload = NumberParser.getAsInt(cs);
			
			m_bPreLoadAllProgramFromList = tagRoot.getValAsBoolean("PreLoadAllProgramFromList");
			m_csPreLoadProgramList = tagRoot.getVal("PreLoadProgramList") ;
						
			
			m_csXMLFrameFilePath = tagRoot.getVal("XMLFrameFilePath") ;
			m_csXMLFramePSFilePath = tagRoot.getVal("XMLFramePSFilePath") ;
			m_csSemanticContextPathFile = /*getRootPath() + */tagRoot.getVal("SemanticContextPathFile") ;

			m_csJarXMLFile = tagRoot.getVal("JarXMLFile") ;
			
			int nMaxSizeMemPoolCodeCache_Mb = tagRoot.getValAsInt("MaxSizeMemPoolCodeCache_Mb") ;
			int nMaxSizeMemPoolPermGen_Mb = tagRoot.getValAsInt("MaxSizeMemPoolPermGen_Mb") ;
			CodeManager.initCodeSizeLimits(nMaxSizeMemPoolCodeCache_Mb, nMaxSizeMemPoolPermGen_Mb);
						
			m_csServerName = tagRoot.getVal("ServerName") ;
			m_csLDAPServer = tagRoot.getVal("LDAPServer") ;
			m_csLDAPServer2 = tagRoot.getVal("LDAPServer2") ;
			m_csLDAPServer3 = tagRoot.getVal("LDAPServer3") ;
			m_csLDAPDomain = tagRoot.getVal("LDAPDomain") ;
			m_csLDAPRootOU = tagRoot.getVal("LDAPRootOU") ;
			m_csLDAPGenericUser = tagRoot.getVal("LDAPGenericUser") ;
			m_csLDAPGenericPassword = tagRoot.getVal("LDAPGenericPassword") ;
			
			m_csScenarioFilePath = tagRoot.getVal("ScenarioFilePath") ;
			
			m_csScenarioDir = tagRoot.getVal("ScenarioDir") ;
			
			m_csScenarioOutputDir = tagRoot.getVal("ScenarioOutputDir") ;
			m_csScenarioOutputDir = FileSystem.normalizePath(m_csScenarioOutputDir);
			FileSystem.createPath(m_csScenarioOutputDir);
						
			
			
			m_csCustomApplicationLauncherConfigFilePath = tagRoot.getVal("AppLauncherConfig") ;
		}		
	}

	protected void initSequenceur(String csDBParameterPrefix)
	{
		baseInitSequenceur(csDBParameterPrefix);

		m_xmlFrame = XMLUtil.LoadXML(m_csXMLFrameFilePath) ;
		if (m_xmlFrame == null)
		{
			return ;
		}	
	}
	
	public void removeSession(OnlineSession session)
	{	
		m_Sequencer.removeSession(session);
	}
	
	/**
	 * 
	 */
	public static String getLogDir()
	{
		String cslogDir = ms_csRootPath + "log\\" ; 
		return cslogDir ;	
	}
		
	public String getSemanticContextPathFile()
	{
		return m_csSemanticContextPathFile;
	}
	
	public void registerSemanticManager(SemanticManager semanticManager)
	{
		m_semanticManager = semanticManager;
	}
	
	public CMenuDef getMenuForSemanticContext(String csScreen, String csSemanticContext)
	{
		if(m_semanticManager != null)
			return m_semanticManager.getMenuForSemanticContext(csScreen, csSemanticContext);
		return null;
	}
	
	private SemanticManager m_semanticManager = null;
	
	/**
	 * @return
	 */
	public String getScenarioFilePath()
	{
		return m_csScenarioFilePath ;
	}

	public String getScenarioDir()
	{
		if (m_csScenarioDir == null || m_csScenarioDir.equals(""))
		{
			File file = new File(m_csScenarioFilePath) ;
			String csDir = file.getParent() ;
			return csDir;
		}
		else
		{
			return m_csScenarioDir ;
		}
	}
	public String getOutputDir()
	{
		if (m_csScenarioOutputDir.equals(""))
		{
			return getScenarioDir() ;
		}
		else
		{
			return m_csScenarioOutputDir ;
		}
	}
	/**
	 * @param path
	 */
	public static void setOnceRootPath(String path)
	{
		if(ms_csRootPath.length() == 0)
		{
			ms_csRootPath = path ;
			if (!ms_csRootPath.endsWith("/") && !ms_csRootPath.endsWith("\\"))
			{
				ms_csRootPath+="/" ;
			}
			// make log dir
			File f = new File(getLogDir()) ;
			f.mkdirs() ;
		}
	}
	
	protected static String ms_csRootPath = "" ;
	public static String getRootPath()
	{
	 	return ms_csRootPath ;
	}
	/**
	 * @param csAppliRootPath
	 * @return
	 */
	
	public static void setApplicationRootPath(String csAppliRootPath) 
	{
		ms_csApplicationRootPath = csAppliRootPath ;
	}
	protected static String ms_csApplicationRootPath = "" ;
	public static String getApplicationRootPath()
	{
		return ms_csApplicationRootPath ;
	}
	/**
	 * @return
	 */
//	public Document getGoodbyeDisplay()
//	{
//		Document out = GetXMLStructure("GOODBYE") ;
//		return out ;
//	}
	/**
	 * @param string
	 * @return
	 */
	public Document getMainPage(String string)
	{
		int nPos = m_csXMLFrameFilePath.lastIndexOf('/') ;
		int nPos2 = m_csXMLFrameFilePath.lastIndexOf('\\') ;
		if (nPos2>nPos)
			nPos = nPos2 ;
		String dir = m_csXMLFrameFilePath.substring(0, nPos+1) ;
		String path = dir + string + ".xml" ;
		return XMLUtil.LoadXML(path) ;
	}
	

	public int getHttpSessionMaxInactiveInterval_s()
	{
		return ms_nHttpSessionMaxInactiveInterval_s;
	}
	
	protected static int ms_nHttpSessionMaxInactiveInterval_s = -1;	// Infinite by default
	
	protected String m_csServerName = "" ;
	public String getServerName()
	{
		return m_csServerName;
	}
	
	/**
	 * @return
	 */
	private String m_csLDAPServer = "" ;
	private String m_csLDAPServer2 = "" ;
	private String m_csLDAPServer3 = "" ;
	private String m_csLDAPDomain = "" ;
	private String m_csLDAPRootOU = "" ;
	private String m_csLDAPGenericUser = "" ;
	private String m_csLDAPGenericPassword = "" ;
	public static boolean ms_bCacheResourceFiles = false;
	
	public LdapRequester getLdapRequester()
	{
		return new LdapRequester(m_csLDAPServer, m_csLDAPServer2, m_csLDAPServer3, m_csLDAPDomain, m_csLDAPRootOU, m_csLDAPGenericUser, m_csLDAPGenericPassword) ;
	}

	public Tag getCustomApplicationLauncherConfig()
	{
		return Tag.createFromFile(m_csCustomApplicationLauncherConfigFilePath) ;
	}
	
	public void doRemoveResourceCache(String csForm)
	{
		m_resourceBeanManager.removeResourceCache(csForm);
	}
}
