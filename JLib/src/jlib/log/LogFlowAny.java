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
 * Declares and registers the <i>Any</i> flow.
 * The <i>Any</i> flow, as the <i>None</i> flow (see {@link LogFlowNone})
 * are not intended for events (see {@link LogEvent}), but only
 * for {@link LogCenter}s.<p/>
 * {@link LogCenter}s assigned to the <i>Any</i> flow accept events from 
 * all flows.
 *  
 * @author PJD
 */
public class LogFlowAny extends LogFlow
{
/**
 * Registers the <i>Any</i> flow to the {@link LogFlowRegister} singleton.
 */
	LogFlowAny()
	{
		super("Any");
	}
/**
 * Checks if an event <i>LogFlow</i> is accepted by the current flow.
 * <i>Any</i> flows accept events from all flows.
 * @return Always <i>true</i>.
 */			
	public boolean isAcceptable(LogFlow logFlow)
	{
		return true;
	}	
}
