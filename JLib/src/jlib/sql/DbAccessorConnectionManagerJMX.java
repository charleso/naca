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
/**
 * 
 */
package jlib.sql;

import jlib.jmxMBean.BaseCloseMBean;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
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
