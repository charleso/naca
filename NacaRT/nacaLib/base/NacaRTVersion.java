/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.base;

import jlib.log.Log;
import jlib.misc.JLibVersion;

public class NacaRTVersion
{
	public static String getVersion()
	{
		return "1.2.16";
	}
	
	public static void logVersions()
	{
		String cs = "Version: NacaRT: "+getVersion();
		cs += "; JLib: "+JLibVersion.getVersion();
		
		Log.logCritical(cs);
	}
}
