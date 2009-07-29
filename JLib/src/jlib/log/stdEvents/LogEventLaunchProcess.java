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
package jlib.log.stdEvents;

import jlib.log.Log;
import jlib.log.LogEvent;
import jlib.log.LogEventType;
import jlib.log.LogFlowStd;
import jlib.log.LogLevel;
/**
 * Class used to log a {@link LogEventType#Launch} event type.
 * The class can be used in two ways:
 * <ul>
 * 	<li>Either create a new instance, and then send it to the {@link Log}
 * 	object:
 * 		<pre>
 * 			LogEvent launchEvent=new LogEventLaunchProcess("newProcess", "productName");
 * 			Log.log(launchEvent);
 * 		</pre>
 * 	</li>
 * 	<li>Either use directly one of the overloads of the {@link #log} method:
 * 		<pre>
 * 			LogEventLaunchProcess.log("newProcess","productName");
 * 		</pre>
 * 	</li>
 * </ul> 
 *
 * @deprecated Use {@link EventProgress} instead.
 * @author PJD
 */
public class LogEventLaunchProcess extends LogEvent
{
	public LogEventLaunchProcess(String csProduct)
	{
		super(LogEventType.Launch, LogFlowStd.Any, LogLevel.Normal, csProduct);
	}

	public static LogEvent log(String csChannel, String csLaunchedProcessName, String csMessage)
	{
		return LogEventLaunchProcess.log(csChannel, null, csLaunchedProcessName, csMessage);
	}
	
	public static LogEvent log(String csChannel, String csProduct, String csLaunchedProcessName, String csMessage)
	{
		LogEventLaunchProcess event = new LogEventLaunchProcess(csProduct);
		event.fillMember("childProcess", csLaunchedProcessName);
		Log.log(csChannel, event, csMessage);
		return event;
	}
	
	public static LogEvent log(String csChannel, String csProduct, String csLaunchedProcessName, String csMessage, String csRunId, String csRuntimeId)
	{
		LogEventLaunchProcess event = new LogEventLaunchProcess(csProduct);
		event.fillMember("childProcess", csLaunchedProcessName);
		Log.log(csChannel, event, csMessage, csRunId, csRuntimeId);
		return event;
	}
}
