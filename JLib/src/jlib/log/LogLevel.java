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

/**
 * Class enumerating all existing log levels.
 * Log levels are used to decide if a particular {@link LogEvent} has enough
 * importance to be accepted by {@link LogCenter}.
 * <ul>
 * 	<lh>There is basically two kinds of events:</lh>
 * 	<li>Events raised to signal errors during the application/service
 * 	activity.</li>
 * 	<li>Events raised to signal particular actions during the
 * 	normal application/service activity. Examples of normal actions are:
 * 	starting the application/service, processing one element of information,
 * 	logging in or out a user...</li> 
 * </ul>
 * Additionally to their type (see {@link LogEventType}), events can have
 * different importance level. Assigning a level to an event
 * is usually done by extending the {@link LogEvent} class. Thus, deciding
 * which level give to an event is a decision taken by the developped using the
 * <i>jlib.log</i> package. Here are some suggestions:
 * <ul>
 * 	<li><i>{@link #Critical}</i> This is the highest level of importance.
 * 	It should be used only for exceptions signaling complete catastrophes
 * 	like: out of memory, disk full, unreachable database. A critical
 * 	event should be raised if the application/service is about to stop.</li>
 * 	<li><i>{@link #Important}</i> The second highest level of importance should
 * 	be used for exceptions that don't oblige the application to stop (for example,
 * 	an application processing a hundred data elements should raise a <i>Important</i>
 *  level event if one of the elements could not be processed, but this doesn't
 *  compromise the next elements). Also, for non-exception events, the 
 *  <i>Important</i> level should be reserved for high-level informations
 *  like the number of datas processed by an application.</li>
 * 	<li><i>{@link #Normal}</i> Normal events usually signal normal application
 * 	activity, such as those described above.</li>
 * 	<li><i>{@link #Verbose}</i> This level (as <i>Debug</i> and <i>FineDebug</i>) 
 *  should be used to signal particular circumstances detected during a 
 *  process. If those circumstances are normal, they can be starting or
 *  finishing a phase in the information processing. If those circumstances
 *  are suspicious, they may not have immediat impact on the result, but often
 *  a higher level event will shortly be raised.</li>
 * 	<li><i>{@link #Debug}</i>This level (as <i>Verbose</i> and <i>FineDebug</i>) 
 *  should be used to signal particular circumstances detected during a 
 *  process. If those circumstances are normal, they can be calling a
 *  delicate method, or switching between two main decisions during the information 
 *  processing. If those circumstances are suspicious, they may not have impact on the 
 *  result, but can explain a higher level event being raised shortly after.</li>
 * 	<li><i>{@link #FineDebug}</i>This level (as <i>Verbose</i> and <i>Debug</i>) 
 *  should be used to signal any debug circumtance happening during normal
 *  application/activity (as entering a method, number of data retrieved from
 *  a sql request...)</li>
 * </ul>
 * 
 * @see LogEventType
 *  
 * @author u930di
 *
 */
public class LogLevel
{
/**
 * Constant for <i>Critical</i> level.
 * This is the highest level of importance.
 * It should be used only for events signaling complete catastrophes
 * like: out of memory, disk full, unreachable database. A critical
 * event should be raised if the application/service is about to stop.
 */
	public static LogLevel Critical = new LogLevel(5, "Critical");
/**
 * Constant for <i>Important</i> level.
 * The second highest level of importance should
 * be used for exceptions that don't oblige the application to stop (for example,
 * an application processing a hundred data elements should raise a <i>Important</i>
 * level event if one of the elements could not be processed, but this doesn't
 * compromise the next elements). Also, for non-exception events, the 
 * <i>Important</i> level should be reserved for high-level informations
 * like the number of datas processed by an application.
 */
	public static LogLevel Important = new LogLevel(4, "Important");
/**
 * Constant for <i>Normal</i> level.
 * Normal events usually signal normal application
 * activity, such as: starting the application/service, processing 
 * one element of information, logging in or out a user...
 */
	public static LogLevel Normal = new LogLevel(3, "Normal");
/**
 * Constant for <i>Verbose</i> level.
 * This level (as {@link #Debug} and {@link #FineDebug}) 
 * should be used to signal suspicious circumstances detected during a 
 * process, that may not have immediat impact on the result. Very 
 * often a <i>Verbose</i> event is raised shortly before another higher 
 * level event.
 */
	public static LogLevel Verbose = new LogLevel(2, "Verbose");
/**
 * Constant for <i>Debug</i> level.
 * This level (as {@link #Verbose} and {@link #FineDebug}) 
 * should be used to signal particular circumstances detected during a 
 * process. If those circumstances are normal, they can be calling a
 * delicate method, or switching between two main decisions during the information 
 * processing. If those circumstances are suspicious, they may not have impact on the 
 * result, but can explain a higher level event being raised shortly after.
 */
	public static LogLevel Debug = new LogLevel(1, "Debug");
/**
 * Constant for <i>FineDebug</i> level.
 * This level (as {@link #Verbose} and {@link #Debug}) 
 * should be used to signal any debug circumtance happening during normal
 * application/activity (as entering a method, number of data retrieved from
 * a sql request...)
 */
	public static LogLevel FineDebug = new LogLevel(0, "FineDebug");

/**
 * Class constructor.
 * Initializes a new <i>LogLevel</i> with the same level
 * as the specified <i>LogLevel</i>. The constructor should be used
 * to <p/>
 * A typical way to initialize a <i>LogLevel</i> object with the desired
 * log level is the following:
 * <pre>
 * 	LogLevel ll=new LogLevel(LogLevel.Critic);
 * </pre>
 * Anothre way, based on the {@link getLevel} usage:
 * <pre>
 * 	String s="Critic";
 * 	LogLevel ll=new LogLevel(LogLevel.getLevel(s));
 * </pre>
 * @param logLevel An existing <i>LogLevel</i> instance. The new <i>LogLevel</i>
 * will have the same level. 
 */
	LogLevel(LogLevel logLevel)
	{
		m_nLevel = logLevel.m_nLevel;
		m_csName = logLevel.m_csName;
	}
	
	private LogLevel(int nLevel, String csName)
	{
		m_nLevel = nLevel;
		m_csName = csName;
	}
	
	private boolean hasName(String cs)
	{
		return cs.equalsIgnoreCase(m_csName);
	}
/**
 * Translates a string into a LogLevel. Translation are case insensitive.
 * <ul>
 * 	<lh>Valid values for the string are:</lh>
 * 	<li><i>Critical</i> translates to a {@link #Critical} level.</li>
 * 	<li><i>Important</i> translates to a {@link #Important} level.</li>
 * 	<li><i>Normal</i> translates to a {@link #Normal} level.</li>
 * 	<li><i>Verbose</i> translates to a {@link #Verbose} level.</li>
 * 	<li><i>Debug</i> translates to a {@link #Debug} level.</li>
 * 	<li><i>FineDebug</i> translates to a {@link #FineDebug} level.</li>
 * </ul>
 * This method should be used together with the class constructor to
 * create new <i>LogLevel</i> instances.
 * @param csLevel The string to translate. 
 * @return The level corresponding to the specified string.
 */
	static LogLevel getLevel(String csLevel)
	{
		if(Critical.hasName(csLevel))
			return Critical;
		else if(Important.hasName(csLevel))
			return Important;
		else if(Normal.hasName(csLevel))
			return Normal;
		else if(Verbose.hasName(csLevel))
			return Verbose;
		else if(Debug.hasName(csLevel))
			return Debug;
		else if(FineDebug.hasName(csLevel))
			return FineDebug;
		return Normal;
	}
/**
 * Helper to remember which {@link String} values are legal for using as
 * <i>LogLevel</i>.
 * @return A string with all different <i>LogLevel</i> translated to strings.
 */	
	static String getListTexts()
	{
		return "Critical|Important|Normal|Verbose|Debug|FineDebug";
	}

/**
 * Sets the log level to the one specified in string.
 * @param cs String specifying to which log level set the <i>LogLevel</i> instance.
 * @see getLevel 
 */
	void set(String cs)
	{
		LogLevel l = getLevel(cs);
		m_nLevel = l.m_nLevel;
		m_csName = l.m_csName;
	}
/**
 * Retrieves the current log level as a string.
 * @return A string containing the current log level.
 * @see getLevel
 */		
	String getAsString()
	{
		return m_csName;
	}

/**
 * Compares the current log level to the specified. 
 * @param minLogLevel A <i>LogLevel</i> instance to compare with the
 * current one.
 * @return <i>true</i> if the level of the specified instance is
 * greater or equal to the level of the current instance.
 */
	boolean isGreaterOrEqual(LogLevel minLogLevel)
	{
		if(m_nLevel >= minLogLevel.m_nLevel)
			return true;
		return false;			
	}
	
	private int m_nLevel = 0;
	private String m_csName = null;
}
