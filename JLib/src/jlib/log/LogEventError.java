/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 7 juil. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jlib.log;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LogEventError extends LogEvent
{
	LogEventError()
	{
		super(LogEventType.Error, LogFlowStd.System, LogLevel.Critical);
	}
	
	public static LogEvent info(int n)
	{
		LogEventError event = new LogEventError();
		event.fillMember("nErrorId", n);
		return event;
	}
}