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
package jlib.log;


public class LogExceptionEvent extends LogEvent
{
	protected LogExceptionEvent(LogEventType type, LogFlow flow, LogLevel level)
	{
		super(type, flow, level);
	}
	
	protected void fillExceptionMembers(Exception e)
	{
		e.fillInStackTrace();
		StackTraceElement stack[] = e.getStackTrace();
		String csStack = StackStraceSupport.getAsString(stack);
		
		fillMember("Message", e.getMessage());
		fillMember("Stack", csStack);
	}
}
