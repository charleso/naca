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


import jlib.misc.DateUtil;
import jlib.misc.StringUtil;

/*
 * Created on 23 juin 2005
 */

/**
 * Class to decorate a {@link LogEvent} instance with some additional properties.
 * Additional properties are:
 * <ul>
 * 	<li>A text message.</li>
 * 	<li>The channer where the message is sent to.</li>
 * 	<li>Information about the file, the class, the method and the thread
 * 	where the event has been created.</li> 
 * </ul>
 * In some special cases it is useful to specify:
 * <ul>
 * 	<li>The <i>RunId</i> identifier.</li>
 * 	<li>The <i>RuntimeId</i> identifier.</li>
 * </ul>
 * See the {@link Log} class overview for more details about those identifiers.
 */
public class LogParams
{
/**
 * Class constructor.
 * @param csChannel The channel where the event is to be sent.
 * @param logEvent The event itself.
 * @param csMessage A text message with additional description about the event.
 */
	LogParams(String csChannel, LogEvent logEvent, String csMessage)
	{
		m_csChannel = csChannel;
		m_logEvent = logEvent;
		m_csMessage = csMessage;
		m_lStartTime = Log.getRunningTime_ms();
		Thread thread = Thread.currentThread();
		m_lThreadId = thread.getId();
		m_csThreadName = thread.getName();
		m_csTimestamp = DateUtil.getDisplayTimeStamp();
		m_csRunId = null;
		m_csRuntimeId = null;
	}
/**
 * Class constructor specifying the <i>RunId</i> and <i>RuntimeId</i> identifiers.
 * See the {@link Log} class overview for more details about these identifiers.
 * @param csChannel The channel where the event is to be sent.
 * @param logEvent The event itself.
 * @param csMessage A text message with additional description about the event.
 * @param csRunId 
 * @param csRuntimeId
 */
	LogParams(String csChannel, LogEvent logEvent, String csMessage, String csRunId, String csRuntimeId)
	{
		m_csChannel = csChannel;
		m_logEvent = logEvent;
		m_csMessage = csMessage;
		m_lStartTime = Log.getRunningTime_ms();
		Thread thread = Thread.currentThread();
		m_lThreadId = thread.getId();
		m_csThreadName = thread.getName();
		m_csTimestamp = DateUtil.getDisplayTimeStamp();
		m_csRunId = csRunId;
		m_csRuntimeId = csRuntimeId;
	}

	LogEventType getLogEventType()
	{
		return m_logEvent.getLogEventType();
	}
	
	String getDisplayTimestamp()
	{
		return m_csTimestamp;	
	}
	
	boolean isAcceptable(LogLevel minLogLevel, LogFlow logFlow)
	{
		if(logFlow.isAcceptable(m_logEvent.getLogFlow()))
		{
			if(m_logEvent.getLogLevel().isGreaterOrEqual(minLogLevel))
				return true;
		}
		return false;
	}
	
	public String toString()
	{
		String cs = m_logEvent.getShortName() + ":" ;
		if (!StringUtil.isEmpty(getMessage()))
		{
			cs += getMessage();
		} 
		cs += m_logEvent.getAsString();
		return cs;
	}
	
	public String toStringNoEvent()
	{
		if (!StringUtil.isEmpty(getMessage()))
		{
			String cs = getMessage();
			return cs;
		} 
		String cs = m_logEvent.getAsString();
		return cs;
	}
	
	public String getTextItem(int n)
	{
		return m_logEvent.getTextAsString(n);
	}
	
	public String getItemValue(int n)
	{
		return m_logEvent.getItemValue(n);
	}
	
	void fillAppCallerLocation(CallStackExclusion callStackExclusion)
	{
		Throwable th = new Throwable();
		StackTraceElement tStack[]  = th.getStackTrace(); 
		int nNbEntries = tStack.length;
		for(int n=0; n<nNbEntries; n++)
		{
			String csClassName = tStack[n].getClassName();
			if(callStackExclusion.doNotContains(csClassName))
			{
				m_caller = tStack[n];
				return;
			}
		}
		m_caller = null;
	}
	
	long getThreadId()
	{
		return m_lThreadId;
	}
	
	String getThreadName()
	{
		return m_csThreadName;
	}
	
	long getStartTime()
	{
		return m_lStartTime;
	}
	
	String getType()
	{
		return m_logEvent.getLogEventType().getType();
	}
	
	String getFile()
	{
		String fileName=null;
		if(m_caller != null)
			fileName = m_caller.getFileName();

// If application is compiled without debug information, the file name
// is not available:
		if (fileName==null)
			fileName="N/A";

		return fileName;
	}
	
	String getMethod()
	{
		String method = null;
		if(m_caller != null)
			method = m_caller.getMethodName();
		
		// If application is compiled without debug information, the method name
		// is not available:
		if (method==null)
			method="N/A";
		return method;
	}
	
	int getLine()
	{
		int lineNb = 0;
		if(m_caller != null) 
			lineNb = m_caller.getLineNumber();
			
		//  If application is compiled without debug information, the line numrer is -1.
		//  -1 is not possible in db LogCenter, force to 0    
	    if (lineNb < 0)
			lineNb = 0;
    
		return lineNb;
	}
	
	String getEventName()
	{
		return m_logEvent.getName();		
	}
	
	String getShortEventName()
	{
		String cs = m_logEvent.getName();
		int n = cs.lastIndexOf('.');
		if(n > 0)
		{
			cs = cs.substring(n+1);
		}
		return cs;
	}
	
	int getEventId()
	{
		String cs = m_logEvent.getName();
		int nNbParam = getNbParamInfoMember();
		cs += nNbParam;
		for(int n=0; n<nNbParam; n++)
		{
			LogInfoMember info = getParamInfoMember(n);
			String csParamId = info.getName();
			cs += csParamId;			
		}
		return cs.hashCode();
	}
	
	String getMessage()
	{
		if(m_csMessage != null)
			return m_csMessage;
		return "";
	}
	
	LogInfoMember getParamInfoMember(int n)
	{
		return m_logEvent.getParamInfoMember(n);
	}
	
	int getNbParamInfoMember()
	{
		int n = m_logEvent.getNbParamInfoMember();
		if(n > 10)
			n = 10;
		return n;
	}
	
	String getProduct()
	{
		String csProduct = m_logEvent.getProduct();
		return csProduct;
	}

	String m_csChannel = null;
	LogEvent m_logEvent = null;
	String m_csMessage = null;
	StackTraceElement m_caller = null;	
	String m_csThreadName;
	long m_lThreadId = 0;
	long m_lStartTime = 0;
	String m_csTimestamp = null;
	String m_csRunId;
	String m_csRuntimeId;
}
 