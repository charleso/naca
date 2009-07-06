/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.log;

import jlib.xml.Tag;

/**
 * Reads the <i>LogCenter</i> tag  from a JLib.log xml configuration file, 
 * creates a new instance of {@link LogCenter}, and registers it to the {@link Log}
 * static collection.
 * 
 * @author PJD
 */
public class LogCenterLoader
{
/**
 * The default class constructor, to instantiate a new {@link LogCenterLoader}.
 */
	public LogCenterLoader()
	{
	}

/**
 * Reads a <i>LogCenter</i> tag, and instantiates, initializes and registers
 * the corresponding {@link LogCenter} to the {@link Log} static collection.
 * @param tagLogCenter The JLib.log xml configuration file should be read
 * with a {@link Tag} object. Then the <i>LogCenter</i> tag should be located,
 * and provided here.
 * @return <i>true</i> if the <i>LogCenter</i> tag has been successfully
 * processed.
 */
	boolean loadDefinition(Tag tagLogCenter)
	{
		m_csChannel = tagLogCenter.getVal("Channel");
		m_bEnable = tagLogCenter.getValAsBoolean("Enable");
		
		m_nNbRequestBufferSize = tagLogCenter.getValAsInt("NbRequestBufferSize");
		m_bAsynchronous = tagLogCenter.getValAsBoolean("Asynchronous");
		
		String csLevel = tagLogCenter.getVal("Level");
		String csFlow = tagLogCenter.getVal("Flow");		
		
		m_logLevel  = LogLevel.getLevel(csLevel);
		m_logFlow = LogFlow.getNamedFlow(csFlow);
		
		LogCenter logCenter = createLogCenter(tagLogCenter);
		if(logCenter != null)
		{
			Log.registerLogCenter(logCenter);
			return true;
		}	

		return false;			
	}
	boolean saveDefinition(Tag tagLogCenter)
	{
		tagLogCenter.addVal("Name", m_csName);
		tagLogCenter.addVal("NbRequestBufferSize", m_nNbRequestBufferSize);
		tagLogCenter.addVal("Asynchronous", m_bAsynchronous);
		tagLogCenter.addVal("Enable", m_bEnable);
		tagLogCenter.addVal("Mode", m_csMode);
		tagLogCenter.addVal("Channel", m_csChannel);
		tagLogCenter.addVal("Level", m_logLevel.getAsString());
		tagLogCenter.addVal("Flow", LogFlow.getFlow(m_logFlow));
		
		return true;
	}
	
	
	private LogCenter createLogCenter(Tag tagLogCenter)
	{
		m_csName = tagLogCenter.getVal("Name");
		m_csMode = tagLogCenter.getVal("Mode");
		if(m_csMode.equalsIgnoreCase("FileST6"))
		{
			PatternLayoutST6 layout = new PatternLayoutST6();
			LogCenterFile logCenter = new LogCenterFile(this);
			logCenter.loadSpecificsEntries(tagLogCenter);
			logCenter.setPatternLayout(layout);
			return logCenter;
		}
		else if(m_csMode.equalsIgnoreCase("Console"))
		{
			LogCenterConsole logCenter = new LogCenterConsole(this);
			logCenter.loadSpecificsEntries(tagLogCenter);
			PatternLayoutConsole layout = new PatternLayoutConsole(logCenter.getFormat());
			logCenter.setPatternLayout(layout);
			return logCenter;
		}
		else if(m_csMode.equalsIgnoreCase("Db"))
		{
			PatternLayoutDb layout = new PatternLayoutDb();
			LogCenterDb logCenter = new LogCenterDb(this);
			logCenter.loadSpecificsEntries(tagLogCenter);
			logCenter.setPatternLayout(layout);
			return logCenter;
		}	
		else if(m_csMode.equalsIgnoreCase("DbFlat"))
		{
			PatternLayoutDb layout = new PatternLayoutDb();
			LogCenterDbFlat logCenter = new LogCenterDbFlat(this);
			logCenter.loadSpecificsEntries(tagLogCenter);
			logCenter.setPatternLayout(layout);
			return logCenter;
		}
		else if(m_csMode.equalsIgnoreCase("FileRawLine"))
		{
			LogCenterFile logCenter = new LogCenterFile(this);
			logCenter.loadSpecificsEntries(tagLogCenter);
			PatternLayoutRawLine layout = new PatternLayoutRawLine(logCenter.getFormat());
			logCenter.setPatternLayout(layout);
			return logCenter;
		}
		else if(m_csMode.equalsIgnoreCase("FileSTCheck"))
		{
			PatternLayoutSTCheck layout = new PatternLayoutSTCheck();
			LogCenterFile logCenter = new LogCenterFile(this);
			logCenter.loadSpecificsEntries(tagLogCenter);
			logCenter.setPatternLayout(layout);
			return logCenter;
		}
		else if(m_csMode.equalsIgnoreCase("FileChunk"))
		{
			PatternLayoutFileChunk layout = new PatternLayoutFileChunk();
			LogCenterFile logCenter = new LogCenterFile(this);
			logCenter.loadSpecificsEntries(tagLogCenter);
			logCenter.setPatternLayout(layout);
			return logCenter;
		}	
		else if(m_csMode.equalsIgnoreCase("PluginConsole"))
		{
			LogCenterPluginConsole logCenter = new LogCenterPluginConsole(this);
			logCenter.loadSpecificsEntries(tagLogCenter);
			PatternLayoutConsole layout = new PatternLayoutConsole(logCenter.getFormat());
			logCenter.setPatternLayout(layout);
			return logCenter;
		}
		return null;
	}
	
	public boolean isEnable()
	{
		return m_bEnable;
	}

	public String getChannel()
	{
		return m_csChannel;
	}

	public String getMode()
	{
		return m_csMode;
	}

	public LogLevel getLogLevel()
	{
		return m_logLevel;
	}
	public LogFlow getFlow()
	{
		return m_logFlow;
	}

	public int getNbRequestBufferSize()
	{
		return m_nNbRequestBufferSize;
	}

	public boolean getAsynchronous()
	{
		return m_bAsynchronous;
	}
	
	protected boolean m_bEnable = true;
	protected int m_nNbRequestBufferSize = 0;
	protected boolean m_bAsynchronous = false;
	protected String m_csChannel = null;			
	protected LogLevel m_logLevel = null;
	protected String m_csName = "";
	protected String m_csMode = "";
	protected LogFlow m_logFlow = null;
}
