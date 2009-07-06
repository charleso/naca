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
 * Declares and registers the <i>System</i> flow.
 * Events reporting exceptions, wrong data, or anomalous circumstances 
 * should belong to the <i>System</i> flow.<p/>
 * {@link LogCenter}s assigned to the <i>System</i> flow only accept <i>System</i>
 * events.
 *  
 * @author PJD
 */
public class LogFlowSystem extends LogFlow
{
/**
 * Registers the <i>System</i> flow to the {@link LogFlowRegister} singleton.
 */
	LogFlowSystem()
	{
		super("System");
	}
/**
 * Checks if an event <i>LogFlow</i> is accepted by the current flow.
 * <i>System</i> flows only accept <i>System</i> events.
 * @return <i>true</i> if the specified flow is a <i>System</i> flow.
 */	
	public boolean isAcceptable(LogFlow logFlow)
	{
		return this == logFlow;
	}	
}
