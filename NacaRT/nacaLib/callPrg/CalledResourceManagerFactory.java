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


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: CalledResourceManagerFactory.java,v 1.1 2007/09/18 08:22:28 u930di Exp $
 */
public class CalledResourceManagerFactory
{
	private static CalledResourceManager ms_instance = null;

	public static CalledResourceManager GetInstance(String csIniFilePath)
	{
		if(ms_instance == null) 
		{
			ms_instance = new CalledResourceManager();
			ms_instance.initialize(csIniFilePath);
		}
		return ms_instance;  
	}
	
	public static CalledResourceManager GetInstance(String csIniFilePath, String csDBParameterPrefix)
	{
		if(ms_instance == null) 
		{
			ms_instance = new CalledResourceManager();
			ms_instance.initialize(csIniFilePath, csDBParameterPrefix);
		}
		return ms_instance;  
	}
}
