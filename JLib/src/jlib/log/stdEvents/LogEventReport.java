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
 * @deprecated Use {@link EventReport} instead.
 */
public class LogEventReport extends LogEvent
{
	public LogEventReport(String csProduct)
	{
		super(LogEventType.Report, LogFlowStd.Any, LogLevel.Normal, csProduct);
	}

	public static LogEvent log(String csChannel, int nNbItemsProcessed, int nNbItemsSucessfullyProcess, String csMessage)
	{
		return LogEventReport.log(csChannel, nNbItemsProcessed, nNbItemsSucessfullyProcess, csMessage);
	}
	
	public static LogEvent log(String csChannel, String csProduct, int nNbItemsProcessed, int nNbItemsSucessfullyProcess, String csMessage)
	{
		LogEventReport event = new LogEventReport(csProduct);
		event.fillMember("processedItems", nNbItemsProcessed);
		event.fillMember("sucessfullyProcessItems", nNbItemsSucessfullyProcess);
		Log.log(csChannel, event, csMessage);
		return event;
	}
}
