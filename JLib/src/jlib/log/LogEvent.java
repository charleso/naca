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

import java.util.ArrayList;

/**
 * Events are raised by applications to signal any particular circumstance,
 * and are sent to the {@link Log} listener, which will broadcast the
 * event to all active {@link LogCenter} instances.
 * Events are caracterized by:
 * <ul>
 * 	<li>Its name (see {@link #getName} and {@link #getShortName}). If not specified,
 * 	the default value is the event class (either <i>LogEvent</i>, either the
 * 	name of the extending class.</li>
 * 	<li>Its type (see {@link #getLogEventType}). This characteristic is mandatory.</li>
 *  <li>Its level (see {@link #getLogLevel}). This characteristic is mandatory. </li>
 * 	<li>Its flow (see {@link #getLogFlow}). This characteristic is mandatory.
 * 	<li>The product it refers to (see {@link #getProduct}). If not specified, the
 * 	LogCenter will use its default product instead.</li>
 * 	<li>The process it refers to (see {@link #getProcess}). If not specified, the
 * 	LogCenter will use its default Process instead.</li>
 * 	<li>A variable list of parameters (see {@link #fillMember}). It can be
 * 	empty if there are no parameters associated with the event.</li>
 * </ul>
 * Different constructors allow to specify the characteristics of the event. Once
 * created, the event cannot be modified.<p/>
 * Events are accepted by the log centers under two conditions:
 * <ol>
 * 	<li>The event <i>level</i> must be high enough.</li>
 * 	<li>The log center must be interested by the event <i>flow</i>.</li>
 * <ol>
 * Additionally, the log center must be listening to the channel specified
 * by calling the {@link Log#log} method.
 * @author u930di
 */
public class LogEvent
{
	public LogEvent(LogEventType logEventType, LogFlow logFlow, LogLevel logLevel, String csProduct, String csProcess, String csName) {
		m_logEventType = logEventType;
		m_logLevel = logLevel;
		m_logFlow = logFlow;
		m_csProduct = csProduct;
		m_csProcess = csProcess;
		m_csName = csName;
	}

	public LogEvent(LogEventType logEventType, LogFlow logFlow, LogLevel logLevel, String csProduct, String csProcess) {
		m_logEventType = logEventType;
		m_logLevel = logLevel;
		m_logFlow = logFlow;
		m_csProduct = csProduct;
		m_csProcess = csProcess;
	}

	public LogEvent(LogEventType logEventType, LogFlow logFlow, LogLevel logLevel, String csProduct)
	{
		m_logEventType = logEventType;
		m_logFlow = logFlow;
		m_logLevel = logLevel;
		m_csProduct = csProduct;
	}
	
	public LogEvent(LogEventType logEventType, LogFlow logFlow, LogLevel logLevel)
	{
		m_logEventType = logEventType;
		m_logFlow = logFlow;
		m_logLevel = logLevel;
	}
	
	public LogEvent(LogFlow logFlow, LogLevel logLevel)
	{
		m_logEventType = LogEventType.Remark; 
		m_logFlow = logFlow;
		m_logLevel = logLevel;
	}

	public void setLogLevel(LogLevel logLevel)
	{
		m_logLevel = logLevel;
	}

	public LogLevel getLogLevel()
	{
		return m_logLevel;
	}
	
	public LogEventType getLogEventType()
	{
		return m_logEventType;
	}
	
	public void setLogFlow(LogFlow logFlow)
	{
		m_logFlow = logFlow;
	}

	public LogFlow getLogFlow()
	{
		return m_logFlow;
	}

	public void setName(String csName)
	{
		m_csName=csName;
	}

	public String getName()
	{
		if (m_csName==null) 
			m_csName = getClass().getName();
		return m_csName;
	}
	
	String getShortName()
	{
		if (m_csName==null) 
			m_csName = getClass().getName();
		int n = m_csName.lastIndexOf(".");
		if (n<0)
			return m_csName;
		else
			return m_csName.substring(n+1);
	}

	public void fillMember(String csName, String csValue)
	{
		if(m_arrLogInfoMembers == null)
			m_arrLogInfoMembers = new ArrayList<LogInfoMember>();
		LogInfoMember logInfomember = new LogInfoMember(csName, csValue);
		m_arrLogInfoMembers.add(logInfomember);
	}
	
	public void fillMember(String csName, int nValue)
	{
		if(m_arrLogInfoMembers == null)
			m_arrLogInfoMembers = new ArrayList<LogInfoMember>();
		LogInfoMember logInfomember = new LogInfoMember(csName, nValue);
		m_arrLogInfoMembers.add(logInfomember);
	}

	public String getAsString()
	{
		String cs = "" ;
		if (m_csProduct != null && !m_csProduct.equals(""))
		{
			cs = "Product="+m_csProduct+" ; " ;
		}
		if(m_arrLogInfoMembers != null)
		{
			int nNbMembers = m_arrLogInfoMembers.size();
			for(int n=0; n<nNbMembers; n++)
			{				
				LogInfoMember logInfoMember = m_arrLogInfoMembers.get(n);
				cs += "; "+ logInfoMember.getAsString();
			}
			return cs;
		}
		return cs ;
	}
	
	String getTextAsString(int n)
	{
		if(m_arrLogInfoMembers != null)
		{
			int nNbMembers = m_arrLogInfoMembers.size();
			if(n < nNbMembers)
			{
				LogInfoMember logInfoMember = m_arrLogInfoMembers.get(n);
				String cs = logInfoMember.getAsString();
				return cs;
			}
		}
		return null;
	}
	
	String getItemValue(int n)
	{
		if(m_arrLogInfoMembers != null)
		{
			int nNbMembers = m_arrLogInfoMembers.size();
			if(n < nNbMembers)
			{
				LogInfoMember logInfoMember = m_arrLogInfoMembers.get(n);
				String cs = logInfoMember.getValue();
				return cs;
			}
		}
		return null;
	}
	
	LogInfoMember getParamInfoMember(int n)
	{
		if(m_arrLogInfoMembers != null)
		{
			int nNbMembers = m_arrLogInfoMembers.size();
			if(n < nNbMembers)
			{
				LogInfoMember logInfoMember = m_arrLogInfoMembers.get(n);
				return logInfoMember;
			}
		}
		return null;
	}
	
	int getNbParamInfoMember()
	{
		if(m_arrLogInfoMembers != null)
		{
			int nNbMembers = m_arrLogInfoMembers.size();
			return nNbMembers;
		}
		return 0;
	}
	
	String getProduct()
	{
		return m_csProduct;
	}

	String getProcess()
	{
		return m_csProcess;
	}
	
	private String m_csName = null;
	private LogEventType m_logEventType = null;
	private LogLevel m_logLevel = null;
	private LogFlow m_logFlow = null;
	private String m_csProduct = null;
	private String m_csProcess = null;
	private ArrayList<LogInfoMember> m_arrLogInfoMembers = null;
}
