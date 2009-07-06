/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.log;
/**
 * Abstract class declaring the basic interface for a <i>flow</i>.
 * The events (see {@link LogEvent}) raised by applications (by calling
 * the {@link Log#log} method) are classified under two different criteria:
 * <ul>
 * 	<li>Their level ({@link LogLevel}) indicates how important they are. More
 * 	important events may be logged to a database, while less important events
 * 	may just be logged to a local file. Event may not logged at all.</li>
 * 	<li>Their flow indicates to which category belongs the events. For example
 * 	some categories of events interest only developpers, while other categories
 * 	may interest system administrators, helpdesks, etc. See {@link LogFlowStd} 
 * 	for the list of all currently existing flows.</li>
 * </ul>
 * For a {@link LogCenter} accepting an event two conditions have to be met:
 * <ul>
 * 	<li>The event <i>LogLevel</i> has to be superior or equal to the <i>LogLevel</i>
 * 	of the <i>LogCenter</i></li>
 * 	<li>The <i>LogCenter</i> flow has to accept the event flow. {@link #isAcceptable}
 * 	is the method checking this second condition.</i> 
 * </ul>
 * @see LogFlowStd
 * @see LogFlowRegister
 */
public abstract class LogFlow
{
/**
 * Initializes a flow with the specified name, and registers it to the
 * {@link LogFlowRegister} static collection.
 * This constructor is to be called from the default constructor of 
 * the extending class:
 * <pre>
 * public class LogFlowXYZ extends LogFlow 
 * {
 * // The constructor is only used for registering the
 * // flow name:
 * 	LogFlowTrace()
 * 	{
 * 		super("XYZ");
 * 	}
 * // Usually a flow only accepts its own kind: 
 * 	public boolean isAcceptable(LogFlow logFlow)
 * 	{
 * 		return this == logFlow;
 * 	}	
 * }
 * </pre>
 * @param csName The flow name.
 */
	protected LogFlow(String csName)
	{
		m_csName = csName;
		LogFlowRegister.register(this);
	}
/**
 * Checks if the flow is named as specified.
 * @param cs The name to be compared with the flow's name.
 * @return <i>true</i> if the specified name corresponds to the
 * flow's name. The comparison is case-insensitive.
 */	
	boolean hasName(String cs)
	{
		return m_csName.equalsIgnoreCase(cs);
	}		
/**
 * Returns the name of the specified flow.
 * @param logFlow The flow to return the name of.
 * @return The name of the specified flow.
 */
	public static String getFlow(LogFlow logFlow)
	{
		return logFlow.m_csName;
	}
/**
 * Returns a reference to the registered flow named as specified.
 * The method checks the specified name against the registerd flows
 * (see {@link LogFlowRegister}).
 * @param csFlow The name of the flow to retrieve. The name is case
 * insensitive.
 * @return The reference to the flow named as specified.
 */
	public static LogFlow getNamedFlow(String csFlow)
	{
		return LogFlowRegister.getFlow(csFlow);
	}

//	private void register()
//	{
//		
//	}
	
/**
 * Should return <i>true</i> if the specified <b>logFlow</i> is accepted
 * by the current flow.
 * @param logFlow The flow to be checked.
 * @return <i>true</i> if the specified flow is accepted by the current flow.
 */
	public abstract boolean isAcceptable(LogFlow logFlow);

	private String m_csName = null;
}
