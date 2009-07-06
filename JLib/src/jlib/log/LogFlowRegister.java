/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.log;

import java.util.ArrayList;
/**
 * Singleton class containing the list of registered flows.
 * See {@link LogFlowStd} for the list of existing flows.
 * This class is used by {@link Log} to compare the event's flow
 * to the {@link LogCenter}'s flow and decide if a particular
 * event is to be sent to a particular log center.
 */
public class LogFlowRegister
{	
/**
 * Returns the registered flow corresponding to the specified name.
 * @param csFlow The name of the desired flow.
 * @return A reference to the registered flow corresponding to the specified name.
 * <i>null</i> if none of the registered flows correspond to the specified name.
 */
	public static LogFlow getFlow(String csFlow)
	{
		if(ms_arrFlows != null)
		{
			for(int n=0; n<ms_arrFlows.size(); n++)
			{
				LogFlow flow = ms_arrFlows.get(n);
				if(flow.hasName(csFlow))
					return flow;
			}
		}
		return null;
	}
/**
 * Registers a new flow.
 * Registered flows can be accessed later using {@link #getFlow}.
 * @param flow The flow to register.
 */
	public static void register(LogFlow flow)
	{
		if(ms_arrFlows == null)
			ms_arrFlows = new ArrayList<LogFlow>(); 
		ms_arrFlows.add(flow);
	}

	private static ArrayList<LogFlow> ms_arrFlows = null;
}
