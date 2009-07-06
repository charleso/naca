/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.xml;

import jlib.log.*;

public class LogTagError extends LogExceptionEvent
{
	public LogTagError()
	{
		super(LogEventType.Error, LogFlowStd.Any, LogLevel.Debug);
	}
	
	public static LogEvent log(Exception e)
	{
		LogTagError event = new LogTagError();
		event.fillExceptionMembers(e);
		Log.log(null, event, "Exception");
		return event;
	}
}