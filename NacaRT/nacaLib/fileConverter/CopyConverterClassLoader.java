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
package nacaLib.fileConverter;

import java.lang.reflect.Constructor;

import jlib.classLoader.ClassDynLoader;
import jlib.classLoader.ClassDynLoaderFactory;
import jlib.classLoader.JarEntries;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.program.CopyReplacing;

public class CopyConverterClassLoader
{
	private static String ms_csPath = null;
	private static JarEntries ms_jarEntries = null;  
	private static boolean ms_bCanLoadJar = false;
	private static boolean ms_bCanLoadClass = false;
	
	static public void init(String csPath, boolean bCanLoadClass, boolean bCanLoadJar)
	{
		ms_csPath = csPath;
		ms_bCanLoadClass = bCanLoadClass;
		ms_bCanLoadJar = bCanLoadJar;
	}
	
	static public Object getInstance(String csClassName, ClassDynLoaderFactory classDynLoaderFactory, BaseProgram program)
	{
		try 
		{ 
			ClassDynLoader classDynLoader = classDynLoaderFactory.make();
			classDynLoader.addPathURL(ms_csPath);
			classDynLoader.addJarEntry(ms_jarEntries, ms_bCanLoadClass, ms_bCanLoadJar);
			Class classCode = classDynLoader.doLoadClass(csClassName);
			if(classCode != null)
			{			
				Constructor constructor = classCode.getConstructor(new Class[] {BaseProgram.class, CopyReplacing.class}); 
				Object obj = constructor.newInstance(new Object[] {program, null});
				return obj;
			}
		}
		catch(Exception e) 
		{
			int n = 0;
		}
		return null;
	}
}