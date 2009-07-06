/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.classLoader;

public class ClassDynLoaderFactory
{
	protected static ClassDynLoaderFactory ms_instance =  null;
	
	protected ClassDynLoaderFactory()
	{
	}
	
	public static ClassDynLoaderFactory getInstance()
	{
		if(ms_instance == null)
			ms_instance = new ClassDynLoaderFactory();
		return ms_instance;
	}
	
	public ClassDynLoader make()
	{
		return new ClassDynLoader(); 
	}
	
	public void preloadJar(String csJarFile)
	{
		int n = 0;
	}
}
