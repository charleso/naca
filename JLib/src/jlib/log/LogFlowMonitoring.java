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
 * Created on 24 juin 2005
 */
package jlib.log;

/**
 * Declares and registers the <i>Monitoring</i> flow.
 * Events reporting normal application/service activity (like starting, 
 * stoping, number of processed	elements...) should belong to 
 * the <i>Monitoring</i> flow.<p/>
 * {@link LogCenter}s assigned to the <i>Monitoring</i> flow only accept 
 * <i>Monitoring</i> events.
 *  
 * @author PJD
 */
public class LogFlowMonitoring extends LogFlow
{
/**
 * Registers the <i>Monitoring</i> flow to the {@link LogFlowRegister} singleton.
 */
	LogFlowMonitoring()
	{
		super("Monitoring");
	}
/**
 * Checks if an event <i>LogFlow</i> is accepted by the current flow.
 * <i>Monitoring</i> flows only accept <i>Monitoring</i> events.
 * @return <i>true</i> if the specified flow is a <i>Monitoring</i> flow.
 */		
	public boolean isAcceptable(LogFlow logFlow)
	{
		return this == logFlow;
	}	
}
