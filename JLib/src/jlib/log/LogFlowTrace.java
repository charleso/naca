/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 24 juin 2005
 */
package jlib.log;

/**
 * Declares and registers the <i>Trace</i> flow.
 * Events concerning developers, like entering a method, starting or 
 * finishing a processing phase, the result of checking a condition, 
 * should belong to <i>Trace</i> flow.<p/>
 * {@link LogCenter}s assigned to the <i>Trace</i> flow only accept <i>Trace</i>
 * events.
 *  
 * @author PJD
 */
public class LogFlowTrace extends LogFlow
{
/**
 * Registers the <i>Trace</i> flow to the {@link LogFlowRegister} singleton.
 */
	LogFlowTrace()
	{
		super("Trace");
	}
/**
 * Checks if an event <i>LogFlow</i> is accepted by the current flow.
 * <i>Trace</i> flows only accept <i>Trace</i> events.
 * @return <i>true</i> if the specified flow is a <i>Trace</i> flow.
 */
	public boolean isAcceptable(LogFlow logFlow)
	{
		return this == logFlow;
	}	
}
