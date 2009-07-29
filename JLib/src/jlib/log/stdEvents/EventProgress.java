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
 * Generates an event to inform of the progress made by the application
 * in the task of processing a certain amount of information elements.
 * During execution, applications should log the following event types:
 * <ul>
 * 	<li>One (and only one) {@link EventStart} when the application Progresss.</li>
 * 	<li>Any number of {@link EventProgress} events, to log various informations the
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
 * This class allows to log a simple "Progress". An interesting way to extend
 * this class is to allow logging less "Progress" events in a production environment
 * than in a test or develop environment. For example:
 * <pre>
 * 	class EventProgressMyApplication extends EventStart 
 * 	{
 * 		public static log(String csChannel, String csProcess, String csProduct, int nProcessedItems, int nTotalItems)
 * 		{
 * 			EventProgress e=new EventProgress(csProcess, csProduct, nProcessedItems, nTotalItems);
 * 			Log.log(csChannel, e, null);
 * 		}
 * 
 *		static m_progressCounter=0; 
 * 		public EventStartMyApplication(String csProcess, String csProduct, int nProcessedItems, int nTotalItems)
 * 		{
 * 			super(csProcess, csProduct, nProcessedItems, nTotalItems)
 *			if ((m_progressCounter++ &lt; 100) && (nProcessedItems &lt; nTotalItems))
 *				setLevel(LogLevel.Verbose);
 *			else
 *				m_progressCounter=0; 
 * 		}
 * 	}
 * </pre>
 * To complete the feature, configure the develop and test environments to accept
 * verbose events, and configure the production environment to accept only
 * normal or higher level events.
 * 
 * @author jmgonet
 */

public class EventProgress extends LogEvent {
/**
 * Logs a "Progress" event, and allows to specify custom <i>RunId</i> and
 * <i>RuntimeId</i>. 
 * For more details about the <i>Product</i>, <i>Process</i>, <i>RunId</i>
 * and <i>RuntimeId</i> identifiers, read the {@link LogCenterDbFlat} overview.
 * @param csChannel The name of the channel where the event is to be sent. If 
 * left <i>null</i> the event is broadcasted to all open channels.
 * @param csProcess The name of the process where the "Progress" has been done. If 
 * left <i>null</i> the {@link LogCenter} accepting the event will assume 
 * the Progress is coming from its channel default process. To set the
 * default process of a channel use {@link Log#setProcess}. 
 * @param csProduct The name of the product (brand, client, source, etc.) to which the Progress 
 * refers. If left <i>null</i> the {@link LogCenter} accepting the event will 
 * assume the event refers to its channel default product. To set the 
 * default product of a channel use {@link Log#setProduct}.   
 * @param csName The desired name of the event. If left <i>null</i>, the default
 * value is the event class name: <i>jlib.log.stdEvents.EventXXX</i>. Use this
 * parameter if you have several progress bars (you process items of different nature).
 * @param csRunId The run identifier to stamp the event with. If left <i>null</i> the {@link LogCenter}
 * will use the its current <i>RunId</i> identifier. To set the <i>RunId</i> identifier for a channel
 * use {@link Log#setRunId}. 
 * @param csRuntimeId The execution identifier to stamp the event with. If left <i>null</i> the {@link LogCenter}
 * will use the its current <i>RuntimeId</i> identifier. To set the <i>RuntimeId</i> identifier for a channel
 * use {@link Log#setRuntimeId}. 
 * @param nProcessedItems The number of information elements processed so far (including
 * elements that could not be processed because of some error).
 * @param nTotalItems The total number of items to process.
 */
	public static void log(String csChannel, String csProcess, String csProduct, String csName, String csRunId, String csRuntimeId, int nProcessedItems, int nTotalItems) 
	{
		EventProgress e=new EventProgress(csProcess, csProduct, csName, nProcessedItems, nTotalItems);
		Log.log(csChannel, e, null, csRunId, csRuntimeId);
	}

/**
 * Logs a "Progress" event. 
 * For more details about the <i>Product</i> and <i>Process</i> identifiers, 
 * read the {@link LogCenterDbFlat} overview.
 * @param csChannel The name of the channel where the event is to be sent. If 
 * left <i>null</i> the event is broadcasted to all open channels.
 * @param csProcess The name of the process where the "Progress" has been done. If 
 * left <i>null</i> the {@link LogCenter} accepting the event will assume 
 * the Progress is coming from its channel default process. To set the
 * default process of a channel use {@link Log#setProcess}. 
 * @param csProduct The name of the product (brand, client, source, etc.) to which the Progress 
 * refers. If left <i>null</i> the {@link LogCenter} accepting the event will 
 * assume the event refers to its channel default product. To set the 
 * default product of a channel use {@link Log#setProduct}.   
 * @param csName The desired name of the event. If left <i>null</i>, the default
 * value is the event class name: <i>jlib.log.stdEvents.EventXXX</i>. Use this
 * parameter if you have several progress bars (you process items of different nature).
 * @param nProcessedItems The number of information elements processed so far (including
 * elements that could not be processed because of some error).
 * @param nTotalItems The total number of items to process.
 */
	public static void log(String csChannel, String csProcess, String csProduct, String csName, int nProcessedItems, int nTotalItems) 
	{
		EventProgress e=new EventProgress(csProcess, csProduct, csName, nProcessedItems, nTotalItems);
		Log.log(csChannel, e, null);		
	}

/**
 * Creates a "Progress" event. 
 * @param csProcess The name of the process where the "Progress" has been done.
 * @param csProduct The name of the product (brand, client, source, etc.) to which the Progress refers.
 * @param csName The desired name of the event. If left <i>null</i>, the default
 * value is the event class name: <i>jlib.log.stdEvents.EventXXX</i>. Use this
 * parameter if you have several progress bars (you process items of different nature).
 * @param nProcessedItems The number of information elements processed so far (including
 * elements that could not be processed because of some error).
 * @param nTotalItems The total number of items to process.
 */
	public EventProgress(String csProcess, String csProduct, String csName, int nProcessedItems, int nTotalItems) 
	{
		super(LogEventType.Progress, LogFlowStd.Monitoring, LogLevel.Normal, csProduct, csProcess, csName);
		fillMember("processedItems",nProcessedItems);
		fillMember("totalItems",nTotalItems);
	}
}
