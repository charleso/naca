/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.log;


/*
 * Created on 3 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import jlib.misc.StringUtil;
import jlib.xml.*;

import org.w3c.dom.Element;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LogCenterPluginConsole extends LogCenter
{
	private static int m_nLineId = 0;
	
	public static void resetLineCoutner()
	{
		m_nLineId = 0;
	}

	public LogCenterPluginConsole(LogCenterLoader logCenterLoader)
	{
		super(logCenterLoader);
	}
	
	public void loadSpecificsEntries(Element el)
	{
	}
	
	public static String getAndIncLine()
	{
		int n = m_nLineId;
		m_nLineId++;
		return StringUtil.FormatWithFill4LeftZero(n);
	}
			
	public void loadSpecificsEntries(Tag tagLogCenter)
	{
		m_csFormat = tagLogCenter.getVal("Format");
	}
	
	boolean open()
	{
		return true;
	}
	
	boolean closeLogCenter()
	{
		return true;
	}
	
	void preSendOutput()
	{
	}
	
	void sendOutput(LogParams logParam)
	{
		String csOut = m_patternLayout.format(logParam, 0);
		if(m_pluginMarker != null)
		{
			LogEventType logEventType = logParam.getLogEventType();
			if(logEventType == LogEventType.Error)
				m_pluginMarker.error("(0) [Error] " + getAndIncLine() + " " + m_csDecoratedFileNameSource + csOut);
			else if(logEventType == LogEventType.Warning)
				m_pluginMarker.warn("(0) [warning] " + getAndIncLine() + " " + m_csDecoratedFileNameSource + csOut);
			else
				m_pluginMarker.info("(0) [Info] " + getAndIncLine() + " " + m_csDecoratedFileNameSource + csOut);
		}
	}
	
	void postSendOutput()
	{
	}

	
	String getFormat()
	{
		return m_csFormat;
	}
	
	private String m_csFormat = null;
	
	public String getType()
	{
		return "LogCenterPluginConsole";
	}
	
	public void setPluginMarker(BasePluginMarker pluginMarker, String csFileNameSource, boolean bInfo, boolean bWarning, boolean bError)
	{
		m_pluginMarker = pluginMarker;
		m_bInfo = bInfo; 
		m_bWarning = bWarning;
		m_bError = bError;
		m_csDecoratedFileNameSource = "%" + csFileNameSource + "% ";
	}
	
	private BasePluginMarker m_pluginMarker = null;
	private String m_csDecoratedFileNameSource = null;
	boolean m_bInfo = false; 
	boolean m_bWarning = false;
	boolean m_bError = false;
}
