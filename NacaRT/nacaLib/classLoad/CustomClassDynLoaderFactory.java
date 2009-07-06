/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.classLoad;

import jlib.classLoader.ClassDynLoader;
import jlib.classLoader.ClassDynLoaderFactory;

public class CustomClassDynLoaderFactory extends ClassDynLoaderFactory
{
	private CustomClassDynLoaderFactory()
	{
	}
	
	public static ClassDynLoaderFactory getInstance()
	{
		if(ms_instance == null)
			ms_instance = new CustomClassDynLoaderFactory();
		return ms_instance;
	}
	
	public ClassDynLoader make()
	{
		return new CustomClassDynLoader(); 
	}
}
