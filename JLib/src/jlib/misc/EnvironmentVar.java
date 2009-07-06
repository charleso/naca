/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

import java.util.Hashtable;


public class EnvironmentVar
{
	private static Hashtable<String, String> ms_tabProgramVars = new Hashtable<String, String>();
	private static String[] ms_tArgs = null;
	
	public static void registerProgramVar(String key, String value)
	{
		if (ms_tabProgramVars.get(key) != null)
		{
			ms_tabProgramVars.remove(key);
		}
		ms_tabProgramVars.put(key, value);
	}
	
	public static void registerCmdLineArgs(String[] args)
	{
		ms_tArgs = args;
	}
	
	public static String getParamValue(String csName)
	{
		String cs = getProgramVariable(csName);
		if(cs == null || cs.length() == 0)
			cs = getEnvironmentVariable(csName);
		if(cs == null || cs.length() == 0)
			cs = getCommandArg(csName);
		if (cs == null) return "";
		return cs;
	}
	
	public static boolean getParamValueAsBoolean(String csName)
	{
		String cs = getCommandArg(csName);
		if(cs.equalsIgnoreCase("true") || cs.equalsIgnoreCase("1"))
			return true;
		return false;
	}
	
	public static int getParamValueAsInt(String csName)
	{
		String cs = getCommandArg(csName);
		return Integer.valueOf(cs).intValue();
	}
	
	private static String getProgramVariable(String csName)
	{
		return ms_tabProgramVars.get(csName);
	}
	
	public static String getEnvironmentVariable(String csName)
	{
		String cs = System.getenv(csName);	// Pb: It is deprecated in java 1.5, and System.getProperty(csName) is wrong
		return StringUtil.removeSurroundingQuotes(cs);
	}
	
	private static String getCommandArg(String csName)
	{
		String csVal = null;
		csName = csName.toUpperCase() + "=";
		if(ms_tArgs != null)
		{
			for(int n=0; n<ms_tArgs.length; n++)
			{
				String cs = ms_tArgs[n];
				if(cs.startsWith("-") || cs.startsWith("/"))
				{
					String csArg = cs.substring(1).toUpperCase();
					if(csArg.startsWith(csName))
					{						
						csVal = cs.substring(csName.length() + 1);
						return csVal;
					}
				}
			}
		}
		return csVal;
	}
		
	public static boolean isParamDefined(String csName)
	{
		csName = csName.toUpperCase();
		if(ms_tArgs != null)
		{
			for(int n=0; n<ms_tArgs.length; n++)
			{
				String cs = ms_tArgs[n];
				if(cs.startsWith("-") || cs.startsWith("/"))
				{
					String csArg = cs.substring(1).toUpperCase();
					if(csArg.startsWith(csName))
						return true;
				}
			}
		}
		return false;
	}
}