/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;

import jlib.misc.StopWatch;
import jlib.xml.Tag;

/**
 * The Log class holds a collection of {@link LogCenter}s, and broacast to them
 * the events received by the static methods {@link #log}, 
 * {@link #logCritical}, {@link #logImportant}...
 * The <i>LogCenter</i>s are loaded via the {@link #open} (and overloads) method.
 * The {@link LogEvent}s are received via the {@link #log} method, or any of its
 * overloads.<p/>
 * 
 * @author u930di
 */
public class Log
{
	private static CallStackExclusion ms_CallStackExclusion = null; 
	//private ArrayList ms_arrExclude = null;
	private static StopWatch ms_processStopWatch = new StopWatch();
//	private static Hashtable<String, LogChannelSetting> ms_hashSettings = new Hashtable<String, LogChannelSetting>();
	//private static String m_csChannel = null;
	
	Log()
	{	
	}
	
	public static long getRunningTime_ms()
	{
		return ms_processStopWatch.getElapsedTime();
	}
	
/**
 * Initializes all {@link LogCenter}s described in the specified
 * configuration file, configured to listen to the specified channel<p/>
 * @param csChannel Only <i>LogCenter</i> listening to this channel will be
 * initialized.
 * @param csConfigFile The JLib.log xml configuration file containing the description
 * for one or more <i>LogCenter</i> objects.
 * @param csRunId The default <i>RunId</i> identifier for the specified channel. This
 * value will be used any time no <i>RunId</i> is specified when receiving an
 * event through a {@link #log} call.
 * @param csProduct The default <i>Product</i> for the specified channel. This value
 * will be used when logging an {@link LogEvent} which doesn't specify a <i>Product</i>.
 * @return The <i>LogCenter</i> descriptors.
 * Use the simplest overload of this method. Use {@link #setRunId},
 * {@link #setProduct}, {@link #setProcess} to configure default values for
 * a particular channel. 
 */
	synchronized public static Tag open(String csChannel, String csConfigFile, String csRunId, String csProduct)
	{
		Tag tagConfig = Tag.createFromFile(csConfigFile);
		setProduct(csChannel, csProduct);		
		return open(csChannel, csRunId, tagConfig);
	}

/**
 * Initializes all {@link LogCenter}s described in the specified
 * configuration file, configured to listen to the specified channel<p/>
 * @param csChannel Only <i>LogCenter</i> listening to this channel will be
 * initialized.
 * @param csConfigFile The JLib.log xml configuration file containing the description
 * for one or more <i>LogCenter</i> objects.
 * @return The <i>LogCenter</i> descriptors. 
 */
	synchronized public static Tag open(String csChannel, String csConfigFile)
	{
		Tag tagConfig = Tag.createFromFile(csConfigFile);
		return open(csChannel, null, tagConfig);
	}

/**
 * Initializes all {@link LogCenter}s described in the specified
 * configuration file, configured to listen to the specified channel<p/>
 * @param csChannel Only <i>LogCenter</i> listening to this channel will be
 * initialized.
 * @param isConfigFile An input stream opened on the JLib.log xml configuration file 
 * containing the description for one or more <i>LogCenter</i> objects.
 * @return The <i>LogCenter</i> descriptors. 
 */
	synchronized public static Tag open(String csChannel, InputStream isConfigFile)
	{
		Tag tagConfig = Tag.createFromStream(isConfigFile);
		return open(csChannel, null, tagConfig);
	}
	
/**
 * Opens the specified channel on all the {@link LogCenter} described in the specified
 * configuration file.<p/>
 * @param csConfigFile The JLib.log xml configuration file containing the description
 * for one or more <i>LogCenter</i> objects.
 * @return The <i>LogCenter</i> descriptors. 
 */
	synchronized public static Tag open(String csConfigFile)
	{
		Tag tagConfig = Tag.createFromFile(csConfigFile);
		Tag tag = open(null, null, tagConfig);
		return tag;
	}

/**
 * Opens the specified channel on all the {@link LogCenter} described in the specified
 * configuration file.<p/>
 * @param isConfigFile An input stream opened on the JLib.log xml configuration file 
 * containing the description for one or more <i>LogCenter</i> objects.
 * @return The <i>LogCenter</i> descriptors. 
 */
	synchronized public static Tag open(InputStream isConfigFile)
	{
		Tag tagConfig = Tag.createFromStream(isConfigFile);
		Tag tag = open(null, null, tagConfig);
		return tag;
	}

/**
 * Sets the <i>RunId</i> identifier for the specified channel.
 * @param csChannel The name of the channel. Leave it to <i>null</i>
 * to affect all opened chanels.
 * @param csRunId The specified value is assigned as the default
 * <i>RunId</i> identifier for all <i>LogCenter</i> listening to the
 * channel. Some log centers generate a new unique value 
 * ({@link LogCenterDbFlat} when <i>null is specified. Other log centers 
 * don't use this identifier.  
 */
	synchronized public static void setRunId(String csChannel, String csRunId)
	{
		if(m_arrLogCenter != null)
		{
			int nNbLogCenter = m_arrLogCenter.size();
			for(int n=0; n<nNbLogCenter; n++)
			{
				LogCenter logCenter = m_arrLogCenter.get(n);
				if (logCenter.getChannel().equals(csChannel) || csChannel==null)
					logCenter.setRunId(csRunId);				
			}
		}
	}

/**
 * Sets the <i>RuntimeId</i> identifier for the specified channel.
 * @param csChannel The name of the channel. Leave it
 * to <i>null</i> to affect all opened channels.
 * @param csRuntimeId The specified value is assigned as the default
 * <i>RunId</i> identifier for all <i>LogCenter</i> listening to the
 * channel. Some log centers generate a new unique value 
 * ({@link LogCenterDbFlat} when <i>null is specified. Other log centers 
 * don't use this identifier.  
 */
	synchronized public static void setRuntimeId(String csChannel, String csRuntimeId)
	{
		if(m_arrLogCenter != null)
		{
			int nNbLogCenter = m_arrLogCenter.size();
			for(int n=0; n<nNbLogCenter; n++)
			{
				LogCenter logCenter = m_arrLogCenter.get(n);
				if (logCenter.getChannel().equals(csChannel) || csChannel==null)
					logCenter.setRuntimeId(csRuntimeId);				
			}
		}
	}
	
/**
 * Returns the default <i>RunId</i> identifier of the specified channel.
 * @param csChannel The channel.
 * @return The default <i>RunId</i> identifier of the specified channel.
 */		
	synchronized public static String getRunId(String csChannel)
	{
		if(m_arrLogCenter != null)
		{
			int nNbLogCenter = m_arrLogCenter.size();
			for(int n=0; n<nNbLogCenter; n++)
			{
				LogCenter logCenter = m_arrLogCenter.get(n);
				if (logCenter.getChannel().equals(csChannel))
					return logCenter.getRunId();				
			}
		}
		return null;
	}
/**
 * Returns the default <i>RuntimeId</i> identifier of the specified channel.
 * @param csChannel The channel.
 * @return The default <i>RuntimeId</i> identifier of the specified channel.
 */	
	synchronized public static String getRuntimeId(String csChannel)
	{
		if(m_arrLogCenter != null)
		{
			int nNbLogCenter = m_arrLogCenter.size();
			for(int n=0; n<nNbLogCenter; n++)
			{
				LogCenter logCenter = m_arrLogCenter.get(n);
				if (logCenter.getChannel().equals(csChannel))
					return logCenter.getRuntimeId();				
			}
		}
		return null;
	}
	
/**
 * Sets the default <i>Product</i> to the specified channel.
 * @param csChannel The channel to which set the defalut <i>Product</i>. Leave it
 * to <i>null</i> to affect all opened channels.
 * @param csProduct The default <i>Product</i> for the specified channel.
 */	
	synchronized public static void setProduct(String csChannel, String csProduct)
	{
		if(m_arrLogCenter != null)
		{
			int nNbLogCenter = m_arrLogCenter.size();
			for(int n=0; n<nNbLogCenter; n++)
			{
				LogCenter logCenter = m_arrLogCenter.get(n);
				if (logCenter.getChannel().equals(csChannel) || csChannel==null)
					logCenter.setProduct(csProduct);				
			}
		}
	}
/**
 * Returns the default <i>Product</i> for the specified channel.
 * @param csChannel The channel to retrieve the default <i>Product</i>. 
 * @return The default <i>Product</i> for the specified channel.
 */	
	synchronized public static String getProduct(String csChannel)
	{
		if(m_arrLogCenter != null)
		{
			int nNbLogCenter = m_arrLogCenter.size();
			for(int n=0; n<nNbLogCenter; n++)
			{
				LogCenter logCenter = m_arrLogCenter.get(n);
				if (logCenter.getChannel().equals(csChannel))
					return logCenter.getProduct();				
			}
		}
		return null;
	}

/**
 * Sets the default <i>Process</i> to the specified channel.
 * @param csChannel The channel to which set the default <i>Product</i>. Leave it
 * to <i>null</i> to affect all opened channels.
 * @param csProcess The default <i>Process</i> for the specified channel.
 */	
	synchronized public static void setProcess(String csChannel, String csProcess)
	{
		if(m_arrLogCenter != null)
		{
			int nNbLogCenter = m_arrLogCenter.size();
			for(int n=0; n<nNbLogCenter; n++)
			{
				LogCenter logCenter = m_arrLogCenter.get(n);
				if (logCenter.getChannel().equals(csChannel) || csChannel==null)
					logCenter.setProcess(csProcess);				
			}
		}
	}
/**
 * Returns the default <i>Process</i> for the specified channel.
 * @param csChannel The channel to retrieve the default <i>Product</i>. 
 * @return The default <i>Process</i> for the specified channel.
 */	
	synchronized public static String getProcess(String csChannel)
	{
		if(m_arrLogCenter != null)
		{
			int nNbLogCenter = m_arrLogCenter.size();
			for(int n=0; n<nNbLogCenter; n++)
			{
				LogCenter logCenter = m_arrLogCenter.get(n);
				if (logCenter.getChannel().equals(csChannel))
					return logCenter.getProcess();				
			}
		}
		return null;
	}

/**
 * Opens the {@link LogCenter}s described in the specified
 * configuration file.<p/>
 * @param csChannel If not <i>null</i>, only the <i>LogCenter</i> listening
 * to the specified channel will be initialized. The other will be ignored. Usually 
 * the channel name is equal to the application name. "Application name" is used 
 * here in the large sense: it is not the name of the executable file (as 
 * would be <i>winword.exe</i> for the "Microsoft Office Word", but the 
 * application official name (as would be "WORD").
 * @param csRunId If not <i>null</i> contains the <i>RunId</i> identifier to be used
 * by default to the specified channel. If <i>null</i>, a unique <i>RunId</i> will
 * be generated (provided that at least one the <i>LogCenter</i> is able to generate
 * <i>RunId</i> identifiers).
 * @param csConfigFile The JLib.log xml configuration file containing the description
 * for one or more <i>LogCenter</i> objects.
 * @return A reference to the <b>tagConfig</b> parameter.
 */
	synchronized private static Tag open(String csChannel, String csRunId, Tag tagConfig)
	{
		LogFlowStd.declare();
		
		if(tagConfig != null)
		{
			LogCenters logCenters = new LogCenters();
			boolean b = logCenters.loadDefinition(csChannel, tagConfig, null);
			if(b)
			{				
				Tag tagSettings = tagConfig.getChild("Settings");
				fillCallStack(tagSettings);
			}
			
			if(csChannel == null)
			{
				Vector<String> arrChannels = new Vector<String>() ;
				for (int i=0; i<logCenters.getNbLogCenterloader(); i++)
				{
					LogCenterLoader loader = logCenters.getLogCenterloader(i) ;
					String ch = loader.getChannel() ;
					if (!arrChannels.contains(ch))
					{
						arrChannels.add(ch) ;
					}
				}
				
				for (String cs : arrChannels)
				{
					setRunId(cs, csRunId);
				}
			}
			else
			{
				setRunId(csChannel, csRunId);
			}
		}
		return tagConfig;
	}
	
	synchronized public static void close()
	{
		if(m_arrLogCenter != null)
		{
			int nNbLogCenter = m_arrLogCenter.size();
			for(int n=0; n<nNbLogCenter; n++)
			{
				LogCenter LogCenter = m_arrLogCenter.get(n);
				LogCenter.close();
			}
		}			
	}
/**
 * Registers a new {@link LogCenter} in the active log centers collection.
 * Once registered, the log centers receive the log events through the
 * {@link #sendLog} method. 
 * @param logCenter The log center to register.
 */
	synchronized static void registerLogCenter(LogCenter logCenter)
	{
		boolean b = logCenter.doOpen();
		if(b)
		{
			if(m_arrLogCenter == null)
				m_arrLogCenter = new ArrayList<LogCenter>();
			
			m_arrLogCenter.add(logCenter);
		}
	}
	
	public synchronized static LogCenterPluginConsole getLogCenterPluginConsole()
	{
		if(m_arrLogCenter != null)
		{
			for(int n=0; n<m_arrLogCenter.size(); n++)
			{
				LogCenter logCenter = m_arrLogCenter.get(n);
				if(logCenter.getType().equalsIgnoreCase("LogCenterPluginConsole"))
					return (LogCenterPluginConsole)logCenter;
			}
		}
		return null;
	}
/**
 * Removes a log center from the log centers collection.
 * @param logCenter The log center to unregister.
 */	
	synchronized static void unregisterLogCenter(LogCenter logCenter)
	{
		logCenter.closeLogCenter();
	}
	
	private static ArrayList<LogCenter> m_arrLogCenter = null;
	
/**
 * Dispatches the description of to all registered log centers.
 * {@link LogCenter}s are registered with the method {@link #registerLogCenter}.
 * Each <i>LogCenter</i> will decide if it accepts the event based upon
 * three criteria:
 * <ul>
 * 	<li>The event is sent on the channel the <i>LogCenter</i> is listening to.</li>
 * 	<li>The event has a {@link LogFlow} accepted by the <i>LogCenter</i> flow (see protected
 * 	property {@link #m_logFlow}).</li>
 * 	<li>The event has a {@link LogLevel} equal or higher than the
 * 	minimal required by the <i>LogCenter</i> (see property {@link getLevel}.</li> 
 * </ul>
 * Additionally, the <i>LogCenter</i> must be enabled.
 * @param logParams A structure containing all paremeters characterizing
 * one log event.
 */
	synchronized static void sendLog(LogParams logParams)
	{
		String csOut = "";
		if(m_arrLogCenter != null)
		{
			int nNbLogCenter = m_arrLogCenter.size();
			for(int n=0; n<nNbLogCenter; n++)
			{
				LogCenter LogCenter = m_arrLogCenter.get(n);
				LogCenter.output(logParams);				
			}
		}
	}
/**
 * Loads the list of packages which will be ignored while searching for the 
 * origin of an event.
 * @param tagSettings The [Settings] tag of a [LogCenter] definition. 
 */
	static void fillCallStack(Tag tagSettings)
	{
		if(tagSettings != null)
		{			
			boolean bFillCallStack = tagSettings.getValAsBoolean("GetCallerLocation");
			
			if(bFillCallStack)
			{
				ms_CallStackExclusion = new CallStackExclusion();
				ms_CallStackExclusion.fillExcluded(tagSettings);
			}
		}		
	}
/**
 * Logs an event to the specified channel, with the specified free text message,
 * using the default <i>RunId</i> and <i>RuntimeId</i> identfiers, and the 
 * {@link LogFlow} and {@link LogLevel} of the event.
 * The specified event will be broadcasted to all registered {@link LogCenter}s.
 * <i>LogCenter</i>s are registered based on the JLib.log xml configuration file,
 * by the {@link #open} method.
 * @param csChannel The channel to send the event to.
 * @param logEvent The event to send to the specified channel.
 * @param csMessage An additional free text message. If no message is required,
 * this paremeter can be null.
 */
	public static void log(String csChannel, LogEvent logEvent, String csMessage)
	{
		LogParams logParams=new LogParams(csChannel,logEvent,csMessage);
		if(ms_CallStackExclusion != null)
			logParams.fillAppCallerLocation(ms_CallStackExclusion);
		sendLog(logParams);
	}
/**
 * Logs an event to the specified channel, with the specified free text message and
 * <i>RunId</i> and <i>RuntimeId</i>, using the {@link LogFlow} and {@link LogLevel}
 * of the event.
 * The specified event will be broadcasted to all registered {@link LogCenter}s.
 * <i>LogCenter</i>s are registered based on the JLib.log xml configuration file,
 * by the {@link #open} method.
 * @param csChannel The channel to send the event to.
 * @param logEvent The event to send to the specified channel.
 * @param csMessage An additional free text message. If no message is required,
 * this paremeter can be null.
 * @param csRunId Specifies a particular <i>RunId</i> identifier for the event.
 * If <i>null</i>, the default <i>RunId</i> of the specified channel is used.
 * @param csRuntimeId Specified a particular <i>RuntimeId</i> identifier
 * for the event. If <i>null</i>, the default <i>RuntimeId</i> of the specified
 * channel is used.
 */	
	public static void log(String csChannel, LogEvent logEvent, String csMessage, String csRunId, String csRuntimeId)
	{
		LogParams logParams=new LogParams(csChannel, logEvent, csMessage, csRunId, csRuntimeId);
		if(ms_CallStackExclusion != null)
			logParams.fillAppCallerLocation(ms_CallStackExclusion);
		sendLog(logParams);
	}

/**
 * Logs an event to the specified channel, with the specified free text message
 * and {@link LogLevel}, using the specified channel's
 * <i>RunId</i> and <i>RuntimeId</i> identifiers, and the event's {@link LogLevel}.
 * The specified event will be broadcasted to all registered {@link LogCenter}s.
 * <i>LogCenter</i>s are registered based on the JLib.log xml configuration file,
 * by the {@link #open} method.
 * @param csChannel The channel to send the event to.
 * @param logFlow If specified, the event's flow will be ignored, and this
 * will be used instead.
 * @param logEvent The event to send to the specified channel.
 * @param csMessage An additional free text message. If no message is required,
 * this paremeter can be null.
 */
	public static void log(String csChannel, LogFlow logFlow, LogEvent logEvent, String csMessage)
	{
		logEvent.setLogFlow(logFlow);
		LogParams logParams=new LogParams(csChannel, logEvent, csMessage);
		if(ms_CallStackExclusion != null)
			logParams.fillAppCallerLocation(ms_CallStackExclusion);
		sendLog(logParams);
	}
	
	public static void log(String csChannel, LogFlow logFlow, LogEvent logEvent, LogLevel logLevel, String csMessage)
	{
		logEvent.setLogFlow(logFlow);
		logEvent.setLogLevel(logLevel);
		LogParams logParams=new LogParams(csChannel, logEvent, csMessage);
		if(ms_CallStackExclusion != null)
			logParams.fillAppCallerLocation(ms_CallStackExclusion);
		sendLog(logParams);
	}
/**
 * Logs an event on the {@link LogFlowStd#System} flow to the specified channel,
 * with the specified message.
 * @param csChannel The channel to send the event to.
 * @param logEvent The event to send to the specified channel.
 * @param csMessage An additional free text message. If no message is required,
 * this paremeter can be null.
 */
	public static void logSystem(String csChannel, LogEvent logEvent, String csMessage)
	{
		logEvent.setLogFlow(LogFlowStd.System);
		LogParams logParams=new LogParams(csChannel, logEvent, csMessage);
		if(ms_CallStackExclusion != null)
			logParams.fillAppCallerLocation(ms_CallStackExclusion);
		sendLog(logParams);
	}
	
/**
 * Logs an event on the {@link LogFlowStd#Monitoring} flow to the specified channel,
 * with the specified message.
 * @param csChannel The channel to send the event to.
 * @param logEvent The event to send to the specified channel.
 * @param csMessage An additional free text message. If no message is required,
 * this paremeter can be null.
 */
	public static void logMonitoring(String csChannel, LogEvent logEvent, String csMessage)
	{
		logEvent.setLogFlow(LogFlowStd.Monitoring);
		LogParams logParams=new LogParams(csChannel, logEvent, csMessage);
		if(ms_CallStackExclusion != null)
			logParams.fillAppCallerLocation(ms_CallStackExclusion);
		sendLog(logParams);
	}
	
/**
 * Logs an event on the {@link LogFlowStd#Monitoring} flow to the specified channel,
 * with the specified message and the specified {@link LogLevel}.
 * @param csChannel The channel to send the event to.
 * @param logEvent The event to send to the specified channel.
 * @param logLevel Will be used (if specified) instead of the event's level.
 * @param csMessage An additional free text message. If no message is required,
 * this paremeter can be null.
 */
	public static void logMonitoring(String csChannel, LogEvent logEvent, LogLevel logLevel, String csMessage)
	{
		logEvent.setLogLevel(logLevel);
		logEvent.setLogFlow(LogFlowStd.Monitoring);
		LogParams logParams=new LogParams(csChannel, logEvent, csMessage);
		if(ms_CallStackExclusion != null)
			logParams.fillAppCallerLocation(ms_CallStackExclusion);
		sendLog(logParams);
	}
	
/**
 * Logs an event on the {@link LogFlowStd#Trace} flow to the specified channel,
 * with the specified message and the specified {@link LogLevel}.
 * @param csChannel The channel to send the event to.
 * @param logEvent The event to send to the specified channel.
 * @param csMessage An additional free text message. If no message is required,
 * this paremeter can be null.
 */
	public static void logTrace(String csChannel, LogEvent logEvent, String csMessage)
	{
		logEvent.setLogFlow(LogFlowStd.Trace);
		LogParams logParams=new LogParams(csChannel, logEvent, csMessage);
		if(ms_CallStackExclusion != null)
			logParams.fillAppCallerLocation(ms_CallStackExclusion);
		sendLog(logParams);
	}
	
/**
 * Logs an event on the {@link LogFlowStd#Trace} flow to the specified channel,
 * with the specified message and the specified {@link LogLevel}.
 * @param csChannel The channel to send the event to.
 * @param logEvent The event to send to the specified channel.
 * @param logLevel Will be used (if specified) instead of the event's level.
 * @param csMessage An additional free text message. If no message is required,
 * this paremeter can be null.
 */
	public static void logTrace(String csChannel, LogEvent logEvent, LogLevel logLevel, String csMessage)
	{
		logEvent.setLogLevel(logLevel);
		logEvent.setLogFlow(LogFlowStd.Trace);
		LogParams logParams=new LogParams(csChannel, logEvent, csMessage);
		if(ms_CallStackExclusion != null)
			logParams.fillAppCallerLocation(ms_CallStackExclusion);
		sendLog(logParams);
	}
	
/**
 * Simplest method to send an event with the {@link LogLevel#Critical} level.
 * The event will be sent with:
 * <ul>
 * 	<li>{@link LogLevel} set to <i>Critical</i>.</li>
 * 	<li>{@link LogFlow} set to <i>Any</i>.</li>
 * 	<li>{@link LogEventType} set to <i>Remark</i>.</li>
 * 	<li>The event will be sent to all channels.</li>
 * </ul>
 * @param csMessage An additional free text message. If no message is required,
 * this paremeter can be null.
 * With this method it is not possible to specify the event name. Use instead
 * a custom {@link LogEvent}, and send it using the standard {@link Log#log} method. 
 */	
	public static void logCritical(String csMessage)
	{
		LogEvent logEvent=new LogEvent(LogEventType.Remark, LogFlowStd.Any, LogLevel.Critical, null, null);
		logEvent.setName("CriticalEvent");
		LogParams logParams=new LogParams(null, logEvent, csMessage);
		if(ms_CallStackExclusion != null)
			logParams.fillAppCallerLocation(ms_CallStackExclusion);
		sendLog(logParams);
	}
	
/**
 * Simplest method to send an event with the {@link LogLevel#Critical} level.
 * The event will be sent with:
 * <ul>
 * 	<li>{@link LogLevel} set to <i>Critical</i>.</li>
 * 	<li>{@link LogFlow} set to <i>Any</i>.</li>
 * 	<li>{@link LogEventType} set to <i>Remark</i>.</li>
 * 	<li>The event will be sent to all channels.</li>
 * </ul>
 * @param csMessage An additional free text message. If no message is required,
 * this paremeter can be null.
 * With this method it is not possible to specify the event name. Use instead
 * a custom {@link LogEvent}, and send it using the standard {@link Log#log} method. 
 */	
	public static void logImportant(String csMessage)
	{
		LogEvent logEvent=new LogEvent(LogEventType.Remark, LogFlowStd.Any, LogLevel.Important, null, null);
		logEvent.setName("ImportantEvent");
		LogParams logParams=new LogParams(null, logEvent, csMessage);
		if(ms_CallStackExclusion != null)
			logParams.fillAppCallerLocation(ms_CallStackExclusion);
		sendLog(logParams);
	}
/**
 * Simplest method to send an event with the {@link LogLevel#Normal} level.
 * The event will be sent with:
 * <ul>
 * 	<li>{@link LogLevel} set to <i>Critical</i>.</li>
 * 	<li>{@link LogFlow} set to <i>Any</i>.</li>
 * 	<li>{@link LogEventType} set to <i>Remark</i>.</li>
 * 	<li>The event will be sent to all channels.</li>
 * </ul>
 * @param csMessage An additional free text message. If no message is required,
 * this paremeter can be null.
 * With this method it is not possible to specify the event name. Use instead
 * a custom {@link LogEvent}, and send it using the standard {@link Log#log} method. 
 */	
	public static void logNormal(String csMessage)
	{
		LogEvent logEvent=new LogEvent(LogEventType.Remark, LogFlowStd.Any, LogLevel.Normal, null, null);
		logEvent.setName("NormalEvent");
		LogParams logParams=new LogParams(null, logEvent, csMessage);
		if(ms_CallStackExclusion != null)
			logParams.fillAppCallerLocation(ms_CallStackExclusion);
		sendLog(logParams);
	}
	
/**
 * Simplest method to send an event with the {@link LogLevel#Verbose} level.
 * The event will be sent with:
 * <ul>
 * 	<li>{@link LogLevel} set to <i>Critical</i>.</li>
 * 	<li>{@link LogFlow} set to <i>Any</i>.</li>
 * 	<li>{@link LogEventType} set to <i>Remark</i>.</li>
 * 	<li>The event will be sent to all channels.</li>
 * </ul>
 * @param csMessage An additional free text message. If no message is required,
 * this paremeter can be null.
 * With this method it is not possible to specify the event name. Use instead
 * a custom {@link LogEvent}, and send it using the standard {@link Log#log} method. 
 */	
	public static void logVerbose(String csMessage)
	{
		LogEvent logEvent=new LogEvent(LogEventType.Remark, LogFlowStd.Any, LogLevel.Verbose, null, null);
		logEvent.setName("VerboseEvent");
		LogParams logParams=new LogParams(null, logEvent, csMessage);
		if(ms_CallStackExclusion != null)
			logParams.fillAppCallerLocation(ms_CallStackExclusion);
		sendLog(logParams);
	}
	
/**
 * Simplest method to send an event with the {@link LogLevel#Debug} level.
 * The event will be sent with:
 * <ul>
 * 	<li>{@link LogLevel} set to <i>Critical</i>.</li>
 * 	<li>{@link LogFlow} set to <i>Any</i>.</li>
 * 	<li>{@link LogEventType} set to <i>Remark</i>.</li>
 * 	<li>The event will be sent to all channels.</li>
 * </ul>
 * @param csMessage An additional free text message. If no message is required,
 * this paremeter can be null.
 * With this method it is not possible to specify the event name. Use instead
 * a custom {@link LogEvent}, and send it using the standard {@link Log#log} method. 
 */	
	public static void logDebug(String csMessage)
	{		
		LogEvent logEvent=new LogEvent(LogEventType.Remark, LogFlowStd.Any, LogLevel.Debug, null, null);
		logEvent.setName("DebugEvent");
		LogParams logParams=new LogParams(null, logEvent, csMessage);
		if(ms_CallStackExclusion != null)
			logParams.fillAppCallerLocation(ms_CallStackExclusion);
		sendLog(logParams);
	}
	
/**
 * Simplest method to send an event with the {@link LogLevel#FineDebug} level.
 * The event will be sent with:
 * <ul>
 * 	<li>{@link LogLevel} set to <i>Critical</i>.</li>
 * 	<li>{@link LogFlow} set to <i>Any</i>.</li>
 * 	<li>{@link LogEventType} set to <i>Remark</i>.</li>
 * 	<li>The event will be sent to all channels.</li>
 * </ul>
 * @param csMessage An additional free text message. If no message is required,
 * this paremeter can be null.
 * With this method it is not possible to specify the event name. Use instead
 * a custom {@link LogEvent}, and send it using the standard {@link Log#log} method. 
 */	
	public static void logFineDebug(String csMessage)
	{		
		LogEvent logEvent=new LogEvent(LogEventType.Remark, LogFlowStd.Any, LogLevel.FineDebug, null, null);
		logEvent.setName("FineDebugEvent");
		LogParams logParams=new LogParams(null, logEvent, csMessage);
		if(ms_CallStackExclusion != null)
			logParams.fillAppCallerLocation(ms_CallStackExclusion);
		sendLog(logParams);
	}
	
	public static int incCounter(String csName)
	{
		// TODO
		return 0;
	}
	
	public static int decCounter(String csName)
	{
		// TODO
		return 0;
	}

	public static void resetCounter(String csName)
	{
		// TODO
	}
}

