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
package jlib.misc;

public class JVMReturnCodeManager
{
	private static int ms_nExitCode = 0;
	
	public static void exitJVM(int nExitCode)
	{
		System.exit(nExitCode);
	}

	public static void exitJVM()
	{
		System.exit(ms_nExitCode);
	}
	
	public static int getExitCode()
	{
		return ms_nExitCode;
	}

	public static void setExitCode(int nExitCode)
	{
		ms_nExitCode = nExitCode;
	}
}
