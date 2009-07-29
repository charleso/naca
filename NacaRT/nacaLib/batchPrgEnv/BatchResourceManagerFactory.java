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

public class BatchResourceManagerFactory
{
	private static BatchResourceManager ms_instance = null;

	public static BatchResourceManager GetInstance(String csIniFilePath)
	{
		if(ms_instance == null) 
		{
			ms_instance = new BatchResourceManager();
			ms_instance.initialize(csIniFilePath);
		}
		return ms_instance;  
	}
	
	public static BatchResourceManager GetInstance(String csIniFilePath, String csDBParameterPrefix)
	{
		if(ms_instance == null) 
		{
			ms_instance = new BatchResourceManager();
			ms_instance.initialize(csIniFilePath, csDBParameterPrefix);
			ms_instance.initCallInterceptor();
		}
		return ms_instance;  
	}
}
