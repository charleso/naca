/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.sql;

import jlib.jmxMBean.BaseCloseMBean;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: DbAccessorConnectionManagerJMX.java,v 1.1 2008/06/19 14:15:30 u930di Exp $
 */
// JMX Bean management for all DbAccessorconnectionManager
public class DbAccessorConnectionManagerJMX extends BaseCloseMBean
{
	DbAccessorConnectionManagerJMX()
	{
		super("DbAccessorConManager", "DbAccessorConManager");
	}
	
	protected void buildDynamicMBeanInfo()
	{
		addAttribute("NbConMgrContexts", getClass(), "NbConMgrContexts", int.class);
		addAttribute("NbUnusedCon", getClass(), "NbUnusedCon", int.class);
	}

	// Number of currently unused connections
	public int getNbUnusedCon()
	{
		int nNbUnusedConnections = DbAccessorConnectionManager.getNbUnusedConnections();
		return nNbUnusedConnections;
	}
	
	// Number of connection contexts
	public int getNbConMgrContexts()
	{
		int n = DbAccessorConnectionManager.getNbConnectionManagerContexts();
		return n;
	}
}
