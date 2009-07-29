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
package jlib.log.stdEvents;

import jlib.log.*;
/**
 * Generates an event to signal that an application has just started.
 * During execution, applications should log the following event types:
 * <ul>
 * 	<li>One (and only one) {@link EventStart} when the application starts.</li>
 * 	<li>Any number of {@link EventRemark} events, to log various informations the
 * 	developers found worthwhile to log.</i>
 * 	<li>During processing, regularly log {@link EventProgress} events, to log
 * 	the process progressing.</li>
 * 	<li>When finished processing, log a {@link EventReport} event, to summarize all
 * 	previous <i>EventProgress</i>.</li>
 * 	<li>One (and only one) {@link EventFinish} when the application finishes.</li>
 * 	<li>If the application encounters an irrecoverable exception, it should log
 * 	one {@link EventError} event, and shortly after a {@link EventAbort} event.</li>
 * 	<li>If the application encounters an error that will not force it to abort,
 * 	it should log a {@link EventWarning} event.</li>
 * </ul>
 * This class allows to log the simplest "Start" event. If you need to join additional
 * parameters to the event you can extend the class:
 * <pre>
 * 	class EventStartMyApplication extends EventStart 
 * 	{
 * 		public static log(String csChannel, String csProcess, String csProduct, String csParam1, int iParam2, String csMessage)
 * 		{
 * 			EventStart e=new EventStart(csProcess, csProduct, csParam1, iParam2);
 * 			Log.log(csChannel,e,csMessage);
 * 		}
 * 
 * 		public EventStartMyApplication(String csProcess, String csProduct, String csParam1, int iParam2)
 * 		{
 * 			super(csProcess, csProduct);
 * 			fillMember("param1",csParam1);
 * 			fillMember("param2",iParam2);
 * 		}
 * 	}
 * </pre>
 * @author jmgonet
 */

public class EventStart extends LogEvent {
/**
 * Logs a "Start" event, and allows to specify custom <i>RunId</i> and
 * <i>RuntimeId</i>. 
 * For more details about the <i>Product</i>, <i>Process</i>, <i>RunId</i>
 * and <i>RuntimeId</i> identifiers, read the {@link LogCenterDbFlat} overview.
 * @param csChannel The name of the channel where the event is to be sent. If 
 * left <i>null</i> the event is broadcasted to all open channels.
 * @param csProcess The name of the process creating the event. If left <i>null</i> the {@link LogCenter} 
 * accepting the event will assume the event is coming from its channel default process. To set the
 * default process of a channel use {@link Log#setProcess}. 
 * @param csProduct The name of the product being (or about to be) processed. If left <i>null</i> 
 * the {@link LogCenter} accepting the event will assume the event refers to its channel default product. 
 * To set the default product of a channel use {@link Log#setProduct}.   
 * @param csRunId The run identifier to stamp the event with. If left <i>null</i> the {@link LogCenter}
 * will use the its current <i>RunId</i> identifier. To set the <i>RunId</i> identifier for a channel
 * use {@link Log#setRunId}. 
 * @param csRuntimeId The execution identifier to stamp the event with. If left <i>null</i> the {@link LogCenter}
 * will use the its current <i>RuntimeId</i> identifier. To set the <i>RuntimeId</i> identifier for a channel
 * use {@link Log#setRuntimeId}. 
 * @param csMessage An additional free text message.
 */
	public static void log(String csChannel, String csProcess, String csProduct, String csRunId, String csRuntimeId, String csMessage) 
	{
		EventStart e=new EventStart(csProcess, csProduct);
		Log.log(csChannel, e, csMessage, csRunId, csRuntimeId);
	}

/**
 * Logs a "Start" event. 
 * For more details about the <i>Product</i> and <i>Process</i> identifiers, 
 * read the {@link LogCenterDbFlat} overview.
 * @param csChannel The name of the channel where the event is to be sent. If 
 * left <i>null</i> the event is broadcasted to all open channels.
 * @param csProcess The name of the process creating the event. If left <i>null</i> the {@link LogCenter} 
 * accepting the event will assume the event is coming from its channel default process. To set the
 * default process of a channel use {@link Log#setProcess}. 
 * @param csProduct The name of the product being (or about to be) processed. If left <i>null</i> 
 * the {@link LogCenter} accepting the event will assume the event refers to its channel default product. 
 * To set the default product of a channel use {@link Log#setProduct}.   
 * @param csMessage An additional free text message.
 */
	public static void log(String csChannel, String csProcess, String csProduct, String csMessage) 
	{
		EventStart e=new EventStart(csProcess, csProduct);
		Log.log(csChannel, e, csMessage);		
	}

/**
 * Creates a "Start" event. 
 * @param csProcess The name of the starting process.
 * @param csProduct The name of the product (brand, client, source, etc.) about to be processed.
 */
	public EventStart(String csProcess, String csProduct) 
	{
		super(LogEventType.Start, LogFlowStd.Monitoring, LogLevel.Important, csProduct, csProcess);
	}
}
