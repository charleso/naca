/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.misc;

import jlib.log.*;


public class LogDisplay extends LogEvent
{
	public LogDisplay()
	{
		super(LogEventType.Start, LogFlowCustomNacaRT.Display, LogLevel.Normal, null);
	}

	public static LogEvent log(String csMessage)
	{
		LogDisplay event = new LogDisplay();
		Log.log("NacaRT", event, csMessage);
		return event;
	}
}
