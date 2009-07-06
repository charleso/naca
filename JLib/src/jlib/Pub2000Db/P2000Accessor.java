/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.Pub2000Db;

import jlib.sql.DbAccessor;
import jlib.sql.DbConnectionBase;
import jlib.sql.DbTLSConnectionStorage;

/**
 * A specific DbAccessor, for Pub2000 db.
 */
public class P2000Accessor extends DbAccessor
{
	/**
	 * Unique Identifier for Pub2000 DB
	 * All SQLClause that refers to this DB can use this accessor 
	 */
	public static DbAccessor accessor = new P2000Accessor(); 
	
	private P2000Accessor()
	{
//		Pass the key to the app.properties file where DB connection parameters are stored
		super("PUB2000Db");	
	}
	
	/**
	 * Return the connection to the pool
	 * It is not autmatically commited, not rollback !!!
	 * This call is mandatory, as a connection must always be returned to the pool after usage. 
	 */
	public static void returnConnectionToPool()
	{
		DbTLSConnectionStorage.returnConnectionToPool(accessor);
	}
	
	/**
	 * commit all modifications to the db. Warning: This is not called automatically, but must be done by application code.
	 */
	public static void commit()
	{
		DbTLSConnectionStorage.commit(accessor);
	}

	/**
	 * cancel all modifications to the db. Warning: This is not called automatically, but must be done by application code.
	 */
	public static void rollBack()
	{
		DbTLSConnectionStorage.rollBack(accessor);
	}
	
	/**
	 * Get The curretnly established connection. No allocation is done if the connection is not established
	 * @return null if no current connection, a valid connection object otherwise
	 */
	public static DbConnectionBase getCurrentConnection()
	{
		return DbTLSConnectionStorage.get(accessor);
	}
	
	public static String dumpConnections()
	{
		return DbAccessor.dumpConnections(accessor);
	}
}
