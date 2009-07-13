/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package idea.onlinePrgEnv;


public class OnlineResourceManagerFactory
{
	private static OnlineResourceManager ms_instance = null;
	
	public static OnlineResourceManager GetInstance()
	{
		if (ms_instance == null)
		{
			ms_instance = new OnlineResourceManager() ;
		}
		return ms_instance ;
	}
	
	public static OnlineResourceManager GetInstance(String csIniFilePath)
	{
		return GetInstance(csIniFilePath, "");
	}
		
	public static OnlineResourceManager GetInstance(String csIniFilePath, String csDBParameterPrefix)
	{
		if(ms_instance == null) 
		{
			ms_instance = new OnlineResourceManager();
			ms_instance.initialize(csIniFilePath, csDBParameterPrefix);
		}
		return ms_instance;  
	}
	
	public static OnlineResourceManager GetInstance(String csIniFilePath, boolean bLoadResources, boolean bDeclareSemanticContext)
	{
		return GetInstance(csIniFilePath, "", bLoadResources, bDeclareSemanticContext);
	}	
	
	public static OnlineResourceManager GetInstance(String csIniFilePath, String csDBParameterPrefix, boolean bLoadResources, boolean bDeclareSemanticContext)
	{
		if (ms_instance == null)
		{
			ms_instance = new OnlineResourceManager() ;
			ms_instance.initialize(csIniFilePath, csDBParameterPrefix, bLoadResources, bDeclareSemanticContext) ;
		}
		return ms_instance ;
	}
}
