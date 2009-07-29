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
/**
 * 
 */
package nacaLib.spServer;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
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
