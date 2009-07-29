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
 * The <i>None</i> flow, as the <i>Any</i> flow (see {@link LogFlowAny})
 * are not intended for events (see {@link LogEvent}), but only
 * for {@link LogCenter}s.<p/>
 * {@link LogCenter}s assigned to the <i>None</i> flow accept no events.
 *  
 * @author PJD
 */
public class LogFlowNone extends LogFlow
{
/**
 * Registers the <i>None</i> flow to the {@link LogFlowRegister} singleton.
 */
	LogFlowNone()
	{
		super("None");
	}
	
/**
 * Checks if an event <i>LogFlow</i> is accepted by the current flow.
 * <i>None</i> flows accept no events at all.
 * @return Always <i>false</i>.
 */			
	public boolean isAcceptable(LogFlow logFlow)
	{
		return false;
	}	
}

