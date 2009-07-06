/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.jmxMBean;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: JmxClassPath.java,v 1.1 2007/10/04 16:46:35 u930di Exp $
 */
/**
 * Gets system path from the current JVM; Uses JMX
 */
public class JmxClassPath
{
	public static String getBootClassPath()
	{
		RuntimeMXBean r = ManagementFactory.getRuntimeMXBean();
		if(r != null)
		{
			String cs = r.getBootClassPath();
			return cs;
		}
		return "";
	}
	
	public static String getClassPath()
	{
		RuntimeMXBean r = ManagementFactory.getRuntimeMXBean();
		if(r != null)
		{
			String cs = r.getClassPath();
			return cs;
		}
		return "";
	}
	
	public static String getLibraryPath()
	{
		RuntimeMXBean r = ManagementFactory.getRuntimeMXBean();
		if(r != null)
		{
			String cs = r.getLibraryPath();
			return cs;
		}
		return "";
	}
	
	public static String getAllPaths()
	{
		String cs = "";
		RuntimeMXBean r = ManagementFactory.getRuntimeMXBean();
		if(r != null)
		{
			cs = "ClassPath="+r.getClassPath();
			cs += "; BootClassPath="+r.getBootClassPath();
			cs += "; LibraryPath="+r.getLibraryPath();
			return cs;
		}
		return "";
	}
}
