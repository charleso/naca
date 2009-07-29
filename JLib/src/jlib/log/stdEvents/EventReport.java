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
 * Generates an event to inform that the application has finished to
 * process all information elements.
 * During execution, applications should log the following event types:
 * <ul>
 * 	<li>One (and only one) {@link EventStart} when the application Reports.</li>
 * 	<li>Any number of {@link EventReport} events, to log various informations the
 * 	developers found worthwhile to log.</i>
 * 	<li>During processing, regularly log {@link EventProgress} events, to inform about
 * 	the process progression.</li>
 * 	<li>When finished processing, log a {@link EventReport} event, to summarize all
 * 	previous <i>EventProgress</i> events.</li>
 * 	<li>One (and only one) {@link EventFinish} when the application finishes.</li>
 * 	<li>If the application encounters an irrecoverable exception, it should log
 * 	one {@link EventError} event, and shortly after a {@link EventAbort} event.</li>
 * 	<li>If the application encounters an error that will not force it to abort,
 * 	it should log a {@link EventWarning} event.</li>
 * </ul>
 * This class allows to log a simple "Report". 
 * @author jmgonet
 */

public class EventReport extends LogEvent {
/**
 * Logs a "Report" event, and allows to specify custom <i>RunId</i> and
 * <i>RuntimeId</i>. 
 * For more details about the <i>Product</i>, <i>Process</i>, <i>RunId</i>
 * and <i>RuntimeId</i> identifiers, read the {@link LogCenterDbFlat} overview.
 * @param csChannel The name of the channel where the event is to be sent. If 
 * left <i>null</i> the event is broadcasted to all open channels.
 * @param csProcess The name of the process where the "Report" has been done. If 
 * left <i>null</i> the {@link LogCenter} accepting the event will assume 
 * the Report is coming from its channel default process. To set the
 * default process of a channel use {@link Log#setProcess}. 
 * @param csProduct The name of the product (brand, client, source, etc.) to which the Report 
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
 * @param nProcessedItems The number of processed information elements (including
 * elements that could not be processed because of some error).
 * @param nSuccessfullyProcessedItems The number of information elements processed,
 * not counting the elements where errors have been found.
 * @param csRemark An additional comment. Leave it <i>null</i> if not needed.
 */
	public static void log(String csChannel, String csProcess, String csProduct, String csName, String csRunId, String csRuntimeId, int nProcessedItems, int nSuccessfullyProcessedItems, String csRemark) 
	{
		EventReport e=new EventReport(csProcess, csProduct, csName, nProcessedItems, nSuccessfullyProcessedItems);
		Log.log(csChannel, e, csRemark, csRunId, csRuntimeId);		
	}

/**
 * Logs a "Report" event. 
 * For more details about the <i>Product</i> and <i>Process</i> identifiers, 
 * read the {@link LogCenterDbFlat} overview.
 * @param csChannel The name of the channel where the event is to be sent. If 
 * left <i>null</i> the event is broadcasted to all open channels.
 * @param csProcess The name of the process where the "Report" has been done. If 
 * left <i>null</i> the {@link LogCenter} accepting the event will assume 
 * the Report is coming from its channel default process. To set the
 * default process of a channel use {@link Log#setProcess}. 
 * @param csProduct The name of the product (brand, client, source, etc.) to which the Report 
 * refers. If left <i>null</i> the {@link LogCenter} accepting the event will 
 * assume the event refers to its channel default product. To set the 
 * default product of a channel use {@link Log#setProduct}.   
 * @param csName The desired name of the event. If left <i>null</i>, the default
 * value is the event class name: <i>jlib.log.stdEvents.EventXXX</i>.
 * @param nProcessedItems The number of processed information elements (including
 * elements that could not be processed because of some error).
 * @param nSuccessfullyProcessedItems The number of information elements processed,
 * not counting the elements where errors have been found.
 * @param csRemark An additional comment. Leave it <i>null</i> if not needed.
 */
	public static void log(String csChannel, String csProcess, String csProduct, String csName, int nProcessedItems, int nSuccessfullyProcessedItems, String csRemark) 
	{
		EventReport e=new EventReport(csProcess, csProduct, csName, nProcessedItems, nSuccessfullyProcessedItems);
		Log.log(csChannel, e, csRemark);		
	}

/**
 * Creates a "Report" event. 
 * @param csProcess The name of the process where the "Report" has been done.
 * @param csProduct The name of the product (brand, client, source, etc.) to which the Report refers.
 * @param csName The desired name of the event. If left <i>null</i>, the default
 * value is the event class name: <i>jlib.log.stdEvents.EventXXX</i>.
 * @param nProcessedItems The number of processed information elements (including
 * elements that could not be processed because of some error).
 * @param nSuccessfullyProcessedItems The number of information elements processed,
 * not counting the elements where errors have been found.
 */
	public EventReport(String csProcess, String csProduct, String csName, int nProcessedItems, int nSuccessfullyProcessedItems) 
	{
		super(LogEventType.Report, LogFlowStd.Monitoring, LogLevel.Normal, csProduct, csProcess, csName);
		fillMember("processedItems",nProcessedItems);
		fillMember("successfullyProcessedItems",nSuccessfullyProcessedItems);
	}
}
