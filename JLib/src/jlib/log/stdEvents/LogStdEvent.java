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

import jlib.log.LogEvent;
import jlib.log.LogEventType;
import jlib.log.LogFlowStd;
import jlib.log.LogLevel;

/**
 * @deprecated Use one of {@link EventStart}, {@link EventFinish}, {@link EventAbort},
 * {@link EventWarning}, {@link EventError}, {@link EventProgress},
 * {@link EventReport}, {@link EventRemark}. These predefined events have
 * a built-in policy to relate event types ({@link LogEventType}), 
 * event flows ({@link jlib.log.LogFlow})and event levels ({@link jlib.log.LogLevel}).
 */
public class LogStdEvent extends LogEvent
{
	public static LogStdEvent ComputerStart = new LogStdEvent(LogLevel.Critical);
	public static LogStdEvent ComputerStopping = new LogStdEvent(LogLevel.Critical);
	public static LogStdEvent ComputerAlive = new LogStdEvent(LogLevel.Normal);
	public static LogStdEvent ProcessStarted = new LogStdEvent(LogLevel.Critical);
	public static LogStdEvent ProcessStopping = new LogStdEvent(LogLevel.Critical);
	public static LogStdEvent ProcessAlive = new LogStdEvent(LogLevel.Normal);
	public static LogStdEvent ProcessProgress = new LogStdEvent(LogLevel.Normal);
	public static LogStdEvent Launch = new LogStdEvent(LogLevel.Normal);
	public static LogStdEvent Rem = new LogStdEvent(LogLevel.Normal);
	

	public LogStdEvent(LogLevel logLevel)
	{
		super(LogEventType.Remark, LogFlowStd.Any, logLevel);
	}
	
	public String getAsString()
	{
		return "";
	}
}
