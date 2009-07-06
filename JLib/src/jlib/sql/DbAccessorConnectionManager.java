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

import java.util.Enumeration;
import java.util.Hashtable;

import jlib.exception.TechnicalException;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: DbAccessorConnectionManager.java,v 1.6 2008/07/09 06:40:18 u930di Exp $
 */
public class DbAccessorConnectionManager
{
	private static Hashtable<DbAccessor, DbConnectionManagerContext> ms_dbConnectionManagerContext = new Hashtable<DbAccessor, DbConnectionManagerContext>();
	private static DbAccessorConnectionManagerJMX ms_DbAccessorConnectionManagerJMX = new DbAccessorConnectionManagerJMX(); 
	
	public static boolean isOracle(DbAccessor dbId)
	{
		DbConnectionManagerContext dbConnectionManagerContext = getOrCreateConnectionManagerContext(dbId);
		if(dbConnectionManagerContext != null && dbConnectionManagerContext.isCreated())
		{
			return dbConnectionManagerContext.isOracle();
		} 
		return false;
	}
	
	public static DbConnectionBase getConnection(DbAccessor dbId) 
		throws TechnicalException
	{
		DbConnectionManagerContext dbConnectionManagerContext = getOrCreateConnectionManagerContext(dbId);
		if(dbConnectionManagerContext != null && dbConnectionManagerContext.isCreated())
		{
			DbConnectionBase connection = dbConnectionManagerContext.getConnection();
			if(connection != null)	// Connection allocated
				return connection;
			else
				TechnicalException.throwException(TechnicalException.MISSING_CONFIGURATION, "Could not allocates valid DB connection, for Db Id="+dbId.getKey());
		} 
		TechnicalException.throwException(TechnicalException.MISSING_CONFIGURATION, "Could not create DB Connection; maybe missing param in property file for Db Id="+dbId.getKey());
		return null;
	}
	
	private static synchronized DbConnectionManagerContext getOrCreateConnectionManagerContext(DbAccessor dbId)
	{
		if(ms_dbConnectionManagerContext == null)
			ms_dbConnectionManagerContext = new Hashtable<DbAccessor, DbConnectionManagerContext>();
		
		DbConnectionManagerContext dbConnectionManagerContext = ms_dbConnectionManagerContext.get(dbId);
		if(dbConnectionManagerContext == null)
		{				
			dbConnectionManagerContext = new DbConnectionManagerContext();
			ms_dbConnectionManagerContext.put(dbId, dbConnectionManagerContext);
			dbConnectionManagerContext.create(dbId.getKey());						
		}
		return dbConnectionManagerContext;
	}
	
	/**
	 * 
	 * @return Total number of unused connections, whatever the DbAccessor
	 */
	public static synchronized int getNbUnusedConnections() 
	{
		if(ms_dbConnectionManagerContext == null)
			return 0;
		
		int nNbUnusedConnections = 0;
		Enumeration<DbConnectionManagerContext> coll = ms_dbConnectionManagerContext.elements();
		while(coll.hasMoreElements())
		{
			DbConnectionManagerContext context = coll.nextElement();
			nNbUnusedConnections += context.getNbUnusedConnections();
		}
		return nNbUnusedConnections;
	}

	/**
	 * @param  dbId: DbAccesor whose number of free connections is searched 
	 * @return Number of unused connections for a given DbAccessor
	 */
	public static synchronized int getNbUnusedConnectionsForDbAccessor(DbAccessor dbId) 
	{
		if(ms_dbConnectionManagerContext == null)
			return 0;
		
		DbConnectionManagerContext context = ms_dbConnectionManagerContext.get(dbId);
		if(context != null)
		{
			int nNbUnusedConnections = context.getNbUnusedConnections();
			return nNbUnusedConnections;
		}
		return 0;		
	}
	
	public static synchronized int getNbRunningConnectionsForDbAccessor(DbAccessor dbId) 
	{
		if(ms_dbConnectionManagerContext == null)
			return 0;
		
		DbConnectionManagerContext context = ms_dbConnectionManagerContext.get(dbId);
		if(context != null)
		{
			int nNbUnusedConnections = context.getNbRunningConnections();
			return nNbUnusedConnections;
		}
		return 0;		
	}
	
	public static synchronized void showHideRunningConnections(DbAccessor dbId, boolean bShowRunningCon)
	{
		if(ms_dbConnectionManagerContext != null)
		{			
			DbConnectionManagerContext context = ms_dbConnectionManagerContext.get(dbId);
			if(context != null)
			{
				context.showHideRunningConnections(bShowRunningCon);
			}
		}
	}
	
	public static synchronized void dumpConnections(DbAccessor dbId, StringBuilder sbText)
	{
		if(ms_dbConnectionManagerContext != null)
		{			
			DbConnectionManagerContext context = ms_dbConnectionManagerContext.get(dbId);
			if(context != null)
			{
				context.dumpConnections(sbText);
			}
		}
	}
	
	public static synchronized int getNbAllocConnnectionsForAccessor(DbAccessor dbId) 
	{
		if(ms_dbConnectionManagerContext == null)
			return 0;
		
		DbConnectionManagerContext context = ms_dbConnectionManagerContext.get(dbId);
		if(context != null)
		{
			int n = context.getNbAllocConnnections();
			return n;
		}
		return 0;		
	}
		
	public static synchronized int getNbMaxConnectionForAccessor(DbAccessor dbId) 
	{
		if(ms_dbConnectionManagerContext == null)
			return 0;
		
		DbConnectionManagerContext context = ms_dbConnectionManagerContext.get(dbId);
		if(context != null)
		{
			int n = context.getNbMaxConnection();
			return n;
		}
		return 0;		
	}
	
	public static synchronized int getNbCachedStatementsForAccessor(DbAccessor dbId) 
	{
		if(ms_dbConnectionManagerContext == null)
			return 0;
		
		DbConnectionManagerContext context = ms_dbConnectionManagerContext.get(dbId);
		if(context != null)
		{
			int n = context.getNbCachedStatementsForAccessor();
			return n;
		}
		return 0;		
	}
	
	public static synchronized int getNbConnectionManagerContexts() 
	{
		if(ms_dbConnectionManagerContext == null)
			return 0;
		
		int nNbConnectionManagerContexts = ms_dbConnectionManagerContext.size();
		return nNbConnectionManagerContexts;
	}
}
