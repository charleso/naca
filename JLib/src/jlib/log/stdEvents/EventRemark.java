/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.log.stdEvents;

import jlib.log.*;
/**
 * Generates an event to signal that an application has found some circumstance
 * that the developer found worthwhile to "Remark".
 * During execution, applications should log the following event types:
 * <ul>
 * 	<li>One (and only one) {@link EventStart} when the application Remarks.</li>
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
 * This class allows to log a simple "Remark". Normally you won't need to
 * extend this class, as "Remark" is the most generic of the events. Use it
 * freely to report anything you want: entering in a method, checking a file,
 * testing a condition, etc.
 * @author jmgonet
 */

public class EventRemark extends LogEvent {
/**
 * Logs a "Remark" event, and allows to specify custom <i>RunId</i> and
 * <i>RuntimeId</i>. 
 * For more details about the <i>Product</i>, <i>Process</i>, <i>RunId</i>
 * and <i>RuntimeId</i> identifiers, read the {@link LogCenterDbFlat} overview.
 * @param csChannel The name of the channel where the event is to be sent. If 
 * left <i>null</i> the event is broadcasted to all open channels.
 * @param csProcess The name of the process where the "Remark" has been done. If 
 * left <i>null</i> the {@link LogCenter} accepting the event will assume 
 * the remark is coming from its channel default process. To set the
 * default process of a channel use {@link Log#setProcess}. 
 * @param csProduct The name of the product (brand, client, source, etc.) to which the remark 
 * refers. If left <i>null</i> the {@link LogCenter} accepting the event will 
 * assume the event refers to its channel default product. To set the 
 * default product of a channel use {@link Log#setProduct}.   
 * @param csName The desired name of the event. If left <i>null</i>, the default
 * value is the event class name: <i>jlib.log.stdEvents.EventXXX</i>.
 * @param csRunId The run identifier to stamp the event with. If left <i>null</i> the {@link LogCenter}
 * will use the its current <i>RunId</i> identifier. To set the <i>RunId</i> identifier for a channel
 * use {@link Log#setRunId}. 
 * @param csRuntimeId The execution identifier to stamp the event with. If left <i>null</i> the {@link LogCenter}
 * will use the its current <i>RuntimeId</i> identifier. To set the <i>RuntimeId</i> identifier for a channel
 * use {@link Log#setRuntimeId}. 
 * @param csRemark An additional free text message.
 */
	public static void log(String csChannel, String csProcess, String csProduct, String csName, String csRunId, String csRuntimeId, String csRemark) 
	{
		EventRemark e=new EventRemark(csProcess, csProduct, csName);
		Log.log(csChannel, e, csRemark, csRunId, csRuntimeId);
	}

/**
 * Logs a "Remark" event. 
 * For more details about the <i>Product</i> and <i>Process</i> identifiers, 
 * read the {@link LogCenterDbFlat} overview.
 * @param csChannel The name of the channel where the event is to be sent. If 
 * left <i>null</i> the event is broadcasted to all open channels (which is convenient
 * in most cases).
 * @param csProcess The name of the process where the "Remark" has been done. If 
 * left <i>null</i> the {@link LogCenter} accepting the event will assume 
 * the remark is coming from its channel default process. To set the
 * default process of a channel use {@link Log#setProcess}.
 * @param csProduct The name of the product (brand, client, source, etc.) to which the remark 
 * refers. If left <i>null</i> the {@link LogCenter} accepting the event will 
 * assume the event refers to its channel default product. To set the 
 * default product of a channel use {@link Log#setProduct}.
 * @param csName The desired name of the event. If left <i>null</i>, the default
 * value is the event class name: <i>jlib.log.stdEvents.EventXXX</i>.
 * @param csRemark An additional free text message.
 */
	public static void log(String csChannel, String csProcess, String csProduct, String csName, String csRemark) 
	{
		EventRemark e=new EventRemark(csProcess, csProduct, csName);
		Log.log(csChannel, e, csRemark);		
	}

/**
 * Creates a "Remark" event. 
 * @param csProcess The name of the process where the "Remark" has been done.
 * @param csProduct The name of the product (brand, client, source, etc.) to which the remark refers.
 * @param csName The desired name of the event. If left <i>null</i>, the default
 * value is the event class name: <i>jlib.log.stdEvents.EventXXX</i>.
 */
	public EventRemark(String csProcess, String csProduct, String csName) 
	{
		super(LogEventType.Remark, LogFlowStd.Trace, LogLevel.Verbose, csProduct, csProcess, csName);
	}
}
