/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.sql;

import java.sql.SQLException;

import jlib.log.*;

public class LogSQLException extends LogExceptionEvent
{
	public LogSQLException()
	{
		super(LogEventType.Error, LogFlowStd.Any, LogLevel.Normal);
	}
	
	public static LogEvent log(SQLException e)
	{
		LogSQLException event = new LogSQLException();
		event.fillExceptionMembers(e);
		event.fillMember("Code", e.getErrorCode());
		event.fillMember("SQLState", e.getSQLState());
		event.fillMember("Message", e.getMessage());
		Log.log(null, event, "SQL Exception");
		return event;
	}
	
	public static LogEvent log(SQLException e, String csStatement)
	{
		LogSQLException event = new LogSQLException();
		event.fillExceptionMembers(e);
		event.fillMember("Code", e.getErrorCode());
		event.fillMember("SQLState", e.getSQLState());
		event.fillMember("Message", e.getMessage());
		event.fillMember("Statement", csStatement);
		Log.log(null, event, "SQL Exception");
		return event;
	}
}