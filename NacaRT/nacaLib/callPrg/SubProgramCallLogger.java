/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.callPrg;

import nacaLib.base.CJMapObject;
import jlib.log.Log;

public class SubProgramCallLogger extends CJMapObject
{
	SubProgramCallLogger()
	{		
	}
	
	public static void reportReturnToCaller(String csProgramName)
	{
		if(isLogCalls)
			Log.logNormal("Returning to program " + csProgramName);
	}
	
	public static void reportCalling(String csSubProgramName)
	{
		if(isLogCalls)
			Log.logNormal("Calling subprogram " + csSubProgramName);
	}
	
	public static void reportAbort(String csSubProgramName)
	{	
		if(isLogCalls)
			Log.logNormal("ERROR: Subprogram aborted" + csSubProgramName);
	}

	public static void reportFailedCalling(String csError, String csSubProgramName)
	{
		if(isLogCalls)
			Log.logNormal(csError + " Subprogram:" + csSubProgramName);
	}
}
