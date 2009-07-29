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
/*
 * Created on 7 juil. 2005
 */
package jlib.log;

/**
 * Contains the list of valid event types.
 * @author PJD
 */
public class LogEventType
{
/**
 * Events of this type should be used for signaling any debug
 * information useful to the developer.
 * To rise a Remark event, use the {@link jlib.log.stdEvents.EventRemark} class.
 */
	public static LogEventType Remark = new LogEventType("R");

/**
 * Events of this type should be used for signaling serious application errors.
 * An error is 'serious' when it will force the application to stop. To signal
 * errors that will prevent the application to process the current element, but
 * probably not the next element, use the {@link #Warning} type.
 * To rise an Error event, use the {@link jlib.log.stdEvents.EventError} class.
 */
	public static LogEventType Error = new LogEventType("E");

/**
 * Events of this type should be used for signaling errors that prevent
 * the application to process the current element, but probably not the next.
 * If the error is so 'serious' that the application will be forced to close,
 * use the {@link #Error} type.
 * To rise an Warning event, use the {@link jlib.log.stdEvents.EventWarning} class.
 */
	public static LogEventType Warning = new LogEventType("W");

/**
 * Applications should signal <i>Start</i> event during start up.
 * To rise an <i>Start</i> event, use the {@link jlib.log.stdEvents.EventStart} class.
 */
	public static LogEventType Start = new LogEventType("S");

/**
 * Applications should signal an <i>Abort</i> event if it is closing
 * prematurely after an event of type {@link #Error}.
 * To rise an <i>Abort</i> event, use the {@link jlib.log.stdEvents.EventAbort} class.
 */
	public static LogEventType Abort = new LogEventType("A");

/**
 * Applications should signal a <i>Finish</i> event when they are finishing
 * normally. If they are finishing prematurely, they should signal an event
 * of type {@link #Abort}.
 * To rise an <i>Finish</i> event, use the {@link jlib.log.stdEvents.EventFinish} class.
 * @deprecated Use {@link #Finish} instead.
 */
	public static LogEventType Stop = new LogEventType("F");

/**
 * Applications should signal a <i>Finish</i> event when they are finishing
 * normally. If they are finishing prematurely, they should signal an event
 * of type {@link #Abort}.
 * To rise an <i>Finish</i> event, use the {@link jlib.log.stdEvents.EventFinish} class.
 */
	public static LogEventType Finish = new LogEventType("F");

/**
 * An application should signal a <i>Launch</i> event just before starting
 * another application.
 * To rise a <i>Launch</i> event, use the {@link jlib.log.stdEvents.EventLaunch} class.
 */
	public static LogEventType Launch = new LogEventType("L");

/**
 * Applications should signal <i>Progress</i> events each time they process
 * a certain amount of elements.
 * To rise a <i>Progress</i> event, use the {@link jlib.log.stdEvents.EventProgress} class.
 */
	public static LogEventType Progress = new LogEventType("p");
/**
 * <i>Report</i> events are meant to summarize all previous {@link #Progress}
 * events.
 * To rise a <i>Report</i> event, use the {@link jlib.log.stdEvents.EventReport} class.
 */
	public static LogEventType Report = new LogEventType("r");

/**
 * Applications should signal a <i>Finish</i> event when they are finishing
 * normally. If they are finishing prematurely, they should signal an event
 * of type {@link #Abort}.
 * To rise an <i>Finish</i> event, use the {@link jlib.log.stdEvents.EventFinish} class.
 * @deprecated Use {@link #Finish} instead. The COP/Log application 
 * (which reads the events reported with a {@link LogCenterDbFlat} 
 * log center) wont be able to read events of type <i>Completed</i>. 
 */
	public static LogEventType Completed = new LogEventType("C");

/**
 * Class constructor
 * @param csType The event type should be one of
 * {@link #Start}, {@link #Finish}, {@link #Abort}, 
 * {@link #Error}, {@link #Warning}, {@link #Remark},
 * {@link #Progress}, {@link #Report}.  
 */
	LogEventType(String csType)
	{
		m_csType = csType;
	}

/**
 * Returns the event type as a string.
 * Types are:
 * <ul>
 * 	<li><i>S</i>: event of type {@link #Start}.</li>
 * 	<li><i>F</i>: event of type {@link #Finish}.</li>
 * 	<li><i>A</i>: event of type {@link #Abort}.</li>
 * 	<li><i>W</i>: event of type {@link #Warning}.</li>
 * 	<li><i>E</i>: event of type {@link #Error}.</li>
 * 	<li><i>R</i>: event of type {@link #Remark}.</li>
 * 	<li><i>p</i>: event of type {@link #Progress}.</li>
 * 	<li><i>r</i>: event of type {@link #Report}.</li>
 * </ul>
 * @return The event type as a string.
 */	
	public String getType()
	{
		return m_csType;
	}
	
	private String m_csType = null;
}
