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
// D:\dev\AdSpiderDriving\Logging\LoadConfigStart.java
// STModuleGen generated JLib log class

package jlib.log.stdEvents;

import jlib.log.*;

public class LoadConfigStart extends LogEvent
{
	public LoadConfigStart(String csProduct)
	{
		super(LogEventType.Remark, LogFlowStd.Monitoring, LogLevel.Important, csProduct);
	}

	public static LogEvent log(String csChannel, String csName, String csMessage)
	{
		return LoadConfigStart.log(csChannel, null, csName, csMessage);
	}

	public static LogEvent log(String csChannel, String csProduct, String csName, String csMessage)
	{
		LoadConfigStart event = new LoadConfigStart(csProduct);
		event.fillMember("Name", csName);
		Log.log(csChannel, event, csMessage);
		return event;
	}
}
