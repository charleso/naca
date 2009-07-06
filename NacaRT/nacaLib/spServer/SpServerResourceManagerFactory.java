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
 * @version $Id: SpServerResourceManagerFactory.java,v 1.1 2006/11/08 09:32:15 u930di Exp $
 */
public class SpServerResourceManagerFactory
{
	private static SpServerResourceManager ms_instance = null;

	public static SpServerResourceManager GetInstance(String csIniFilePath)
	{
		if(ms_instance == null) 
		{
			ms_instance = new SpServerResourceManager();
			ms_instance.initialize(csIniFilePath);
		}
		return ms_instance;  
	}
}
