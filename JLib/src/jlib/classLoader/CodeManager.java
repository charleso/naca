/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
// Codemanager

package jlib.classLoader;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.management.NotificationEmitter;

import jlib.misc.FileSystem;
import jlib.misc.StringRef;
import jlib.misc.StringUtil;

public class CodeManager
{
	private static ArrayList<String> ms_arrPath = null;
	private static JarEntries ms_jarEntries = null;  
	private static boolean ms_bCanLoadJar = false;
	private static boolean ms_bCanLoadClass = false;
	
	static public void setPath(String csPaths)
	{
		String tcsPaths[] = new String[1];
		tcsPaths[0] = csPaths;
		
		String csPath = StringUtil.extractFirstWordWithStopList(tcsPaths, ";");
		while(!StringUtil.isEmpty(csPath))
		{
			if(ms_arrPath == null)
				ms_arrPath = new ArrayList<String>();
			csPath = csPath.trim();
			csPath = FileSystem.normalizePath(csPath);
			ms_arrPath.add(csPath);
			
			csPath = StringUtil.extractFirstWordWithStopList(tcsPaths, ";");
		}
	}
	
	static public void initLoadPossibilities(boolean bCanLoadClass, boolean bCanLoadJar)
	{
		ms_bCanLoadClass = bCanLoadClass;
		ms_bCanLoadJar = bCanLoadJar;
	}
		
	static public void preloadJar(ClassDynLoaderFactory classDynLoaderFactory, String csJarFile)
	{
		if(ms_bCanLoadJar)
		{
			ClassDynLoader classDynLoader = classDynLoaderFactory.make();
			classDynLoader.addPathURL(ms_arrPath);
			ms_jarEntries = classDynLoader.preloadJarEntries(csJarFile);
		}
	}
	
	static public Object getInstance(String csClassName, ClassDynLoaderFactory classDynLoaderFactory, Object oCTorParam1, Object oCTorParam2)
	{
		try 
		{ 
			ClassDynLoader classDynLoader = classDynLoaderFactory.make();
			classDynLoader.addPathURL(ms_arrPath);
			classDynLoader.addJarEntry(ms_jarEntries, ms_bCanLoadClass, ms_bCanLoadJar);
			Class classCode = classDynLoader.doLoadClass(csClassName);
			if(classCode != null)
			{			
				Constructor constructor = classCode.getConstructor(new Class[] {Object.class, Object.class}); 
				Object obj = constructor.newInstance(new Object[] {oCTorParam1, oCTorParam2});
				return obj;
			}
		}
		catch(Exception e) 
		{
			int n = 0;
		}
		return null;
	}

	
	static public Object getInstance(String csClassName, ClassDynLoaderFactory classDynLoaderFactory)
	{
		ClassDynLoader classDynLoader = classDynLoaderFactory.make();
		
		classDynLoader.addPathURL(ms_arrPath);
		classDynLoader.addJarEntry(ms_jarEntries, ms_bCanLoadClass, ms_bCanLoadJar);
		Class classCode = classDynLoader.doLoadClass(csClassName);
		
		if(classCode != null)
		{			
			ClassLoader clsConcreteClassLoader = classCode.getClassLoader();
			boolean b = clsConcreteClassLoader instanceof ClassDynLoader;
			if(b)
			{
				ClassDynLoader classConcreteDynLoader = (ClassDynLoader) clsConcreteClassLoader;
				Object obj = classConcreteDynLoader.makeNewInstance(csClassName, classCode);
				return obj;
			}
			else
			{
				Object obj = null;
				try
				{
					obj = classCode.newInstance();
				}
				catch (InstantiationException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				return obj;
			}
		}
		return null;
	}
		
	static public void removeAllInstances(String csName)
	{
		ClassDynLoader.removeAllInstances(csName);
	}
	
	public static void initCodeSizeLimits(int nMaxSizeMemPoolCodeCache, int nMaxSizeMemPoolPermGen)
	{
		// PJD remove ibm JMV
		List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
		for (MemoryPoolMXBean p: pools)
		{
			if(p.getType().compareTo(MemoryType.NON_HEAP) == 0)
			{
				String cs = p.getName();
				if(cs.equalsIgnoreCase("Code Cache"))
					p.setUsageThreshold((long)nMaxSizeMemPoolCodeCache * 1024L * 1024L);
				else if(cs.equalsIgnoreCase("Perm Gen"))
					p.setUsageThreshold((long)nMaxSizeMemPoolPermGen * 1024L * 1024L);
			}
		}
	}
	
	public static CodeSizeLimitEventHandler createSizeLimitEventHandler()
	{
		MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
		NotificationEmitter emitter = (NotificationEmitter) mbean;
		CodeSizeLimitEventHandler eventHandler = new CodeSizeLimitEventHandler();
		return eventHandler;
	}
}
