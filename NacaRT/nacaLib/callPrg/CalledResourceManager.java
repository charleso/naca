/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.callPrg;

import jlib.log.Log;
import jlib.xml.Tag;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.misc.LogFlowCustomNacaRT;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: CalledResourceManager.java,v 1.1 2007/09/18 08:22:28 u930di Exp $
 */
public class CalledResourceManager extends BaseResourceManager
{
	CalledResourceManager()
	{
		super(true);
	}
	
	public void initialize(String csINIFilePath)
	{
		setXMLConfigFilePath(csINIFilePath) ;
	}
	
	public void initialize(String csINIFilePath, String csDBParameterPrefix)
	{
		initialize(csINIFilePath);
		initSequenceur(csDBParameterPrefix);
	}
	
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
		}
	}
	
	protected void initSequenceur(String csDBParameterPrefix)
	{
		baseInitSequenceur(csDBParameterPrefix);
	}
	
	public void doRemoveResourceCache(String csForm)
	{
	}
}