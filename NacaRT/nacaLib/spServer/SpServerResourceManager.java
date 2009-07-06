/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.spServer;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: SpServerResourceManager.java,v 1.4 2007/06/25 10:01:11 u930bm Exp $
 */

import jlib.log.Log;
import jlib.misc.BasicLogger;
import jlib.xml.Tag;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.misc.LogFlowCustomNacaRT;

public class SpServerResourceManager extends BaseResourceManager
{
	private Tag m_tagRoot = null;
	
	SpServerResourceManager()
	{
		super(false);
		BasicLogger.log("SpServerResourceManager() ended");
	}
	
	public void initialize(String csINIFilePath)
	{
		BasicLogger.log("SpServerResourceManager::initialize 0");
		m_tagRoot = setXMLConfigFilePath(csINIFilePath) ;
		BasicLogger.log("SpServerResourceManager::initialize 1");
		initSequenceur("");
		BasicLogger.log("SpServerResourceManager::initialize 2");
	}

	protected void LoadConfigFromFile(Tag tagRoot)
	{
		BasicLogger.log("LoadConfigFromFile");
		if(tagRoot != null)
		{
			BasicLogger.log("LoadConfigFromFile 1");
			String csLogCfg = tagRoot.getVal("LogSettingsPathFile");
			BasicLogger.log("LoadConfigFromFile 2");
			
			LogFlowCustomNacaRT.declare();
			BasicLogger.log("LoadConfigFromFile 3; csLogCfg="+csLogCfg);
			Tag tagLogSettings = Log.open("NacaRT", csLogCfg);
			BasicLogger.log("LoadConfigFromFile 4");
			if (tagLogSettings != null)
			{
				BasicLogger.log("LoadConfigFromFile 5");
				Tag tagSettings = tagLogSettings.getChild("Settings");
				BasicLogger.log("LoadConfigFromFile 6");
				if(tagSettings != null)
				{
					BasicLogger.log("LoadConfigFromFile 7");
//					isLogCESM = tagSettings.getValAsBoolean("CESM"); 
//					isLogFlow = tagSettings.getValAsBoolean("Flow");
//					isLogSql = tagSettings.getValAsBoolean("Sql");
//					IsSTCheck = tagSettings.getValAsBoolean("STCheck");
				}
			}
		}
		BasicLogger.log("LoadConfigFromFile done");
	}
	
	String getSpDbEnvironment()
	{
		if(m_tagRoot != null)
		{
			Tag tagSQLConfig = m_tagRoot.getChild("SQLConfig");
			if(tagSQLConfig != null)
			{
				String csDbEnvironment = tagSQLConfig.getVal("dbenvironment");
				return csDbEnvironment;
			}
		}
		return "";
	}
	
	String getSpDbPackage()
	{
		if(m_tagRoot != null)
		{
			Tag tagSQLConfig = m_tagRoot.getChild("SQLConfig");
			if(tagSQLConfig != null)
			{
				String csDbPackage = tagSQLConfig.getVal("dbpackage");
				return csDbPackage;
			}
		}
		return "";
	}
	
	protected void initSequenceur(String csDBParameterPrefix)
	{
		BasicLogger.log("SpServerResourceManager::initSequenceur() 0");
		baseInitSequenceur(csDBParameterPrefix);
		BasicLogger.log("SpServerResourceManager::initSequenceur() 1");
	}
	
	public void doRemoveResourceCache(String csForm)
	{
	}
}
