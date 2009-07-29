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


/*
 * Created on 3 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import jlib.xml.*;

import org.w3c.dom.Element;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LogCenterConsole extends LogCenter
{
	public LogCenterConsole(LogCenterLoader logCenterLoader)
	{
		super(logCenterLoader);
	}
	
	public void loadSpecificsEntries(Element el)
	{
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
		System.out.println(csOut);
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
		return "LogCenterConsole";
	}
}
