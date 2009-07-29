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

import jlib.misc.StringUtil;


/*
 * Created on 8 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PatternLayoutRawLine extends LogPatternLayout
{
	public PatternLayoutRawLine(String csFormat)
	{
		super();
		m_csFormat = csFormat;
	}
	
	String getMessage(LogParams logParams)
	{
		String csMessage = logParams.getMessage();
		return csMessage+"\r\n";	
	}
	
	
	public String format(LogParams logParams, int n)
	{
//		if(n == 0)
//		{
		if(StringUtil.isEmpty(m_csFormat))
		{
			return logParams.toString() + "\r\n";
		}
		else
		{
			String csMessage = logParams.toString();
			String csFile = "";
			String cs = m_csFormat;
			
			if(m_csFormat.indexOf("%SourceFile") >= 0)
			{
				StackTraceElement stackElem = logParams.m_caller;
				if(stackElem != null)
				{
					csFile = stackElem.getFileName();
					if(csFile != null)
						csFile = csFile + "(" + stackElem.getLineNumber() + ")";
					cs = StringUtil.replace(cs, "%SourceFile", csFile, true);
				}
				else
					cs = StringUtil.replace(cs, "%SourceFile", "[Unknown SourceFile] ", true);
//				csClass = stackElem.getClassName();
//				csLine = Integer.toString(nLine);
//				csMethodName = stackElem.getMethodName();
			}
			
			cs = StringUtil.replace(cs, "%FullText", logParams.toString(), true);
			cs = StringUtil.replace(cs, "%Message", logParams.getMessage(), true);
			cs = StringUtil.replace(cs, "%ThreadName", logParams.getThreadName(), true);
			cs = StringUtil.replace(cs, "%ThreadId", logParams.getThreadId(), true);
			cs = StringUtil.replace(cs, "%StartTime", logParams.getStartTime(), true);
			cs = StringUtil.replace(cs, "%Timestamp", logParams.getDisplayTimestamp(), true);
			cs = StringUtil.replace(cs, "%CR", "\r", true);
			cs = StringUtil.replace(cs, "%LF", "\n", true);
			return cs;
		}
	//	}
				
	//	return null;
	}	
	
	int getNbLoop(LogParams logParams)
	{
		return 1;
	}
	
	private String m_csFormat = null;
}
