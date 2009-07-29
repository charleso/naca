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
// D:\dev\AdSpiderDriving\Logging\StdError.java
// STModuleGen generated JLib log class

package jlib.log.stdEvents;

import jlib.log.Log;
import jlib.log.LogEvent;
import jlib.log.LogEventType;
import jlib.log.LogFlowStd;
import jlib.log.LogLevel;

/**
 * @deprecated Use {@link EventWarning} or {@link EventError} instead. 
 * @author PJD
 */
public class StdError extends LogEvent
{
	public StdError(String csProduct)
	{
		super(LogEventType.Error, LogFlowStd.Trace, LogLevel.Normal, csProduct);
	}
	
	public static LogEvent log(String csChannel, String csContext, String csMessage)
	{
		return StdError.log(csChannel, null, csContext, csMessage);
	}

	public static LogEvent log(String csChannel, String csProduct, String csContext, String csMessage)
	{
		StdError event = new StdError(csProduct);
		event.fillMember("Context", csContext);
		Log.log(csChannel, event, csMessage);
		return event;
	}
}
