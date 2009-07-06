/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.log.stdEvents;

import jlib.log.*;
/**
 * Generates an event to signal that an application has found an recoverable
 * exception; an exception that prevents the application to process an
 * information element, but probably won't prevent to process the next element.
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
 * 	<li>If the application encounters an Warning that will not force it to abort,
 * 	it should log a {@link EventWarning} event.</li>
 * </ul>
 * This class allows to log a simple "Warning". You'll probably need to extend this
 * class to adapt it to your own exception handling. Here are a couple of
 * suggestions:
 * <ul>
 * 	<li>You can add up to nine custom parameters:
 * 		<pre>
 * 			class EventWarningMyApplication extends EventWarning 
 * 			{
 * 				public static log(String csChannel, String csProcess, String csProduct, String csParam1, int iParam2, String csMessage)
 * 				{
 * 					EventStart e=new EventStart(csProcess, csProduct, csParam1, iParam2);
 * 					Log.log(csChannel,e,csMessage);
 * 				}
 * 
 * 				public EventWarningMyApplication(String csProcess, String csProduct, String csParam1, int iParam2)
 * 				{
 * 					super(csProcess, csProduct);
 * 					fillMember("param1",csParam1);
 * 					fillMember("param2",iParam2);
 * 				}
 * 			}
 * 		</pre>
 * 	</li>
 * 	<li>You can create a link between the caught exception and the name
 * 	of the "Warning" event you log:
 * 		<pre>
 * 			class EventWarningMyApplication extends EventWarning 
 * 			{
 * 				public static log(String csChannel, String csProcess, String csProduct, Exception exception)
 * 				{
 * 					EventStart e=new EventStart(csProcess, csProduct, exception);
 * 					Log.log(csChannel,e,exception.getMessage());
 * 				}
 * 
 * 				public EventWarningMyApplication(String csProcess, String csProduct, Exception exception)
 * 				{
 * 					super(csProcess, csProduct);
 * 					setName(exception.getName());
 * 				}
 * 			}
 * 		</pre>
 * 	</li>
 * </ul>
 * @author jmgonet
 */

public class EventWarning extends LogEvent {
/**
 * Logs a "Warning" event, and allows to specify custom <i>RunId</i> and
 * <i>RuntimeId</i>. 
 * For more details about the <i>Product</i>, <i>Process</i>, <i>RunId</i>
 * and <i>RuntimeId</i> identifiers, read the {@link LogCenterDbFlat} overview.
 * @param csChannel The name of the channel where the event is to be sent. If 
 * left <i>null</i> the event is broadcasted to all open channels.
 * @param csProcess The name of the process where the "Warning" has been done. If 
 * left <i>null</i> the {@link LogCenter} accepting the event will assume 
 * the Warning is coming from its channel default process. To set the
 * default process of a channel use {@link Log#setProcess}. 
 * @param csProduct The name of the product (brand, client, source, etc.) to which the Warning 
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
 * @param csWarning An additional free text message.
 */
	public static void log(String csChannel, String csProcess, String csProduct, String csName, String csRunId, String csRuntimeId, String csWarning) 
	{
		EventWarning e=new EventWarning(csProcess, csProduct, csName);
		Log.log(csChannel, e, csWarning, csRunId, csRuntimeId);				
	}

/**
 * Logs a "Warning" event. 
 * For more details about the <i>Product</i> and <i>Process</i> identifiers, 
 * read the {@link LogCenterDbFlat} overview.
 * @param csChannel The name of the channel where the event is to be sent. If 
 * left <i>null</i> the event is broadcasted to all open channels.
 * @param csProcess The name of the process where the "Warning" has been done. If 
 * left <i>null</i> the {@link LogCenter} accepting the event will assume 
 * the Warning is coming from its channel default process. To set the
 * default process of a channel use {@link Log#setProcess}. 
 * @param csProduct The name of the product (brand, client, source, etc.) to which the Warning 
 * refers. If left <i>null</i> the {@link LogCenter} accepting the event will 
 * assume the event refers to its channel default product. To set the 
 * default product of a channel use {@link Log#setProduct}.   
 * @param csName The desired name of the event. If left <i>null</i>, the default
 * value is the event class name: <i>jlib.log.stdEvents.EventXXX</i>.
 * @param csWarning An additional free text message.
 */
	public static void log(String csChannel, String csProcess, String csProduct, String csName, String csWarning) 
	{
		EventWarning e=new EventWarning(csProcess, csProduct, csName);
		Log.log(csChannel, e, csWarning);		
	}

/**
 * Creates an "Warning" event. 
 * @param csProcess The name of the process where the "Warning" has found.
 * @param csProduct The name of the product (brand, client, source, etc.) to which the Warning refers.
 * "Warning" events aren't usually associated to an information element, rather they are
 * raised after trying unsuccessfully to access some resource (database, file system).
 * Thus it is very common to leave this parameter to <i>null</i>.
 * @param csName The desired name of the event. If left <i>null</i>, the default
 * value is the event class name: <i>jlib.log.stdEvents.EventXXX</i>.
 */
	public EventWarning(String csProcess, String csProduct, String csName) 
	{
		super(LogEventType.Warning, LogFlowStd.System, LogLevel.Important,  csProduct, csProcess, csName);
	}
}
