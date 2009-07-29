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

import java.sql.Connection;



/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class DbTLSConnectionStorage
{
	private static ThreadLocal<DbTLSStoredConnections> mtls = new ThreadLocal<DbTLSStoredConnections>();

	public static DbConnectionBase get(DbAccessor dbId)
	{
		DbTLSStoredConnections storedConnections = mtls.get();
		if(storedConnections != null)
		{
			DbConnectionBase dbConnectionBase = storedConnections.getForeignConnection();
			if(dbConnectionBase == null)	// No foreign connection specified; search for a connection bound to dbId 
				dbConnectionBase = storedConnections.getDbId(dbId);
			return dbConnectionBase;
		}
		return null;		
	}
	
	public static void set(DbAccessor dbId, DbConnectionBase dbConnectionBase)
	{
		DbTLSStoredConnections storedConnections = mtls.get();
		if(storedConnections == null)
		{
			storedConnections = new DbTLSStoredConnections();
			mtls.set(storedConnections);
		}
		storedConnections.putDbId(dbId, dbConnectionBase);
	}
	
	public static boolean returnAllConnectionsToPool()
	{
		DbTLSStoredConnections storedConnections = mtls.get();
		if(storedConnections != null)
		{
			return storedConnections.returnAllConnectionsToPool();
		}
		return false;
	}
	
	public static boolean returnConnectionToPool(DbAccessor dbId)
	{
		DbTLSStoredConnections storedConnections = mtls.get();
		if(storedConnections != null)
		{
			return storedConnections.returnConnectionToPool(dbId);
		}
		return false;
	}
	
	public static void dumpConnectionsForAllAccessors(StringBuilder sbText)
	{
		DbTLSStoredConnections storedConnections = mtls.get();
		if(storedConnections != null)
		{
			storedConnections.dumpConnections(sbText);
		}
	}
	
	public static boolean commit(DbAccessor dbId)
	{
		DbTLSStoredConnections storedConnections = mtls.get();
		if(storedConnections != null)
		{
			return storedConnections.commit(dbId);
		}
		return false;
	}	
	
	/** Method added by Jilali Raki for WLC stored procedures
	 * 
	 * @param dbId
	 * @param autoCommit
	 * @return 
	 */
	public static boolean setAutoCommit(DbAccessor dbId, boolean autoCommit)
	{
		DbTLSStoredConnections storedConnections = mtls.get();
		if(storedConnections != null)
		{
			return storedConnections.setAutoCommit(dbId, autoCommit);
		}
		return false;
	}		
	
	public static boolean rollBack(DbAccessor dbId)
	{
		DbTLSStoredConnections storedConnections = mtls.get();
		if(storedConnections != null)
		{
			return storedConnections.rollBack(dbId);
		}
		return false;
	}	

	public static void setForeignConnection(Connection connectionJDBC, String csEnv)
	{
		DbConnectionBase foreignDbConnection = new DbConnection(connectionJDBC, csEnv, false);
		setForeignConnection(foreignDbConnection);
	}
	
	public static void setForeignConnection(Connection connectionJDBC, String csEnv, boolean bUseCachedStatements)
	{
		DbConnectionBase foreignDbConnection = new DbConnection(connectionJDBC, csEnv, bUseCachedStatements);
		setForeignConnection(foreignDbConnection);
	}
	
	public static void setForeignConnection(DbConnectionBase foreignDbConnection)
	{
		DbTLSStoredConnections storedConnections = mtls.get();
		if(storedConnections == null)
		{
			storedConnections = new DbTLSStoredConnections();
			mtls.set(storedConnections);
		}
		storedConnections.setForeignConnection(foreignDbConnection);
	}
}
