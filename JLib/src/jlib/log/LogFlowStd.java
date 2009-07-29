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
 * Declaring and registering all existing flows (see {@link LogFlow} for
 * more details about flows.
 * The existing flows are:
 * <ul>
 * 	<li>{@link LogFlowSystem}, for <i>System</i> flows. Events reporting
 * 	exceptions, wrong data, or anomalous circumstances should belong to
 * 	the <i>System</i> flow.</li>
 * 	<li>{@link LogFlowMonitoring}, for <i>Monitoring</i> flows. Events reporting
 * 	normal application/service activity (like starting, stoping, number of processed
 * 	elements...) should belong to the <i>Monitoring</i> flow.</li>
 * 	<li>{@link LogFlowTrace}, for <i>Trace</i> flows. Events concerning developers,
 * 	like entering a method, starting or finishing a processing phase, the result
 * 	of checking a condition, should belong to <i>Trace</i> flow.</li>
 * </ul>
 * There are two more flows, but they're intended only for {@link LogCenter}s:
 * <ul>
 * 	<li>{@link LogFlowAny}, when a <i>LogCenter</i> has this flow, it will accept
 * 	events of any flow.</i>.</li>
 * 	<li>{@link LogFlowNone}, when a <i>LogCenter</i> has this flow, it won't accept
 * 	any event at all. This option can be used for closing a <i>LogCenter</i> from
 * 	the configuration file (see {@link LogCenters}).</li>
 * </ul> 
 * @author u930di
 */
public abstract class LogFlowStd
{
/**
 * Declares (and registers) the {@link LogFlowSystem} flow, which
 * is intended for reporting exceptions, wrong data, or anomalous 
 * circumstances.
 */
	public static LogFlow System = new LogFlowSystem();
/**
 * Declares (and registers) the {@link LogFlowMonitoring} flow, which
 * is intended for reporting normal application/service activity 
 * (like starting, stoping, number of processed elements...)
 */
	public static LogFlow Monitoring = new LogFlowMonitoring();
/**
 * Declares (and registers) the {@link LogFlowTrace} flow, which
 * is intended for reporting exceptions, wrong data, or anomalous 
 * circumstances.
 */
	public static LogFlow Trace = new LogFlowTrace();
/**
 * Declares (and registers) the {@link LogFlowAny} flow, which is used
 * for configuring a {@link LogCenter}s for accepting events of all flows. 
 */
	public static LogFlow Any = new LogFlowAny();
/**
 * Declares (and registers) the {@link LogFlowNone} flow, which is used
 * for configuring a {@link LogCenter}s for stop accepting events of any flow. 
 */
	public static LogFlow None = new LogFlowNone();
/**
 * Declares and registers all existing flows, which are:
 * <ul> 
 * 	<li><i>System</i></li>
 * 	<li><i>Monitor</i></li>
 * 	<li><i>Trace</i></li>
 * 	<li><i>Any</i></li>
 * 	<li><i>None</i></li>
 * </ul>
 *
 */
	static void declare()
	{
	}
/**
 * Returns a registered flow named as specified.
 * @param csName The name of the desired flow.
 * @return A registered flow named as specified.
 */
	public static LogFlow getNamedFlow(String csName)
	{
		return LogFlowRegister.getFlow(csName);
	}
}
