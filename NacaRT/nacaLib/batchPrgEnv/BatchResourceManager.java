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
package nacaLib.batchPrgEnv;

import jlib.log.Log;
import jlib.xml.Tag;
import nacaLib.base.NacaRTVersion;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.misc.LogFlowCustomNacaRT;
import nacaLib.pathManager.PathsManager;

public class BatchResourceManager extends BaseResourceManager
{
	BatchResourceManager()
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
			csLogCfg = PathsManager.adjustPath(csLogCfg);
			
			LogFlowCustomNacaRT.declare();
			Tag tagLogSettings = Log.open("NacaRT", csLogCfg);
			NacaRTVersion.logVersions();
			if (tagLogSettings != null)
			{
				Tag tagSettings = tagLogSettings.getChild("Settings");
				if(tagSettings != null)
				{
					isLogCESM = tagSettings.getValAsBoolean("CESM"); 
					isLogFlow = tagSettings.getValAsBoolean("Flow");
					isLogSql = tagSettings.getValAsBoolean("Sql");
					isLogFile = tagSettings.getValAsBoolean("File");
					isLogCalls = tagSettings.getValAsBoolean("Calls");
					IsSTCheck = tagSettings.getValAsBoolean("STCheck");
					isLogStatCoverage = tagSettings.getValAsBoolean("StatCoverage");
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
