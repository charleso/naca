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

import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class DbTLSStoredConnections
{
	private DbConnectionBase m_foreignDbConnectionBase = null;	// Holds a foreign db connection, that is a connection is has been provided by a caller using setForeignConnection
	private Hashtable<DbAccessor, DbConnectionBase> m_hashConById = new Hashtable<DbAccessor, DbConnectionBase>();

	DbConnectionBase getDbId(DbAccessor dbId)	// No need to synchonize: the m_hashConById is private to the current thread 
	{
		return m_hashConById.get(dbId);
	}

	void putDbId(DbAccessor dbId, DbConnectionBase connection)	// No need to synchonize: the m_hashConById is private to the current thread 
	{
		m_hashConById.put(dbId, connection);
	}

	boolean returnAllConnectionsToPool()
	{
		if(m_foreignDbConnectionBase != null)	// We have a foreign connection
		{
			m_foreignDbConnectionBase = null;	// Do not hold it anymore
		}
		
		Enumeration<DbConnectionBase> enumConnections = m_hashConById.elements();
		while(enumConnections.hasMoreElements())
		{
			DbConnectionBase connection = enumConnections.nextElement();
			connection.returnConnectionToPool();
		}
		m_hashConById.clear();
		return true;
	}
	
	void dumpConnections(StringBuilder sbText)
	{
		if(m_foreignDbConnectionBase != null)	// We have a foreign connection
		{
			sbText.append("Foreign connection:\n");
			m_foreignDbConnectionBase.dumpConnections(sbText);
		}
		sbText.append("\nTLS managed connections:\n");
		Enumeration<DbConnectionBase> enumConnections = m_hashConById.elements();
		while(enumConnections.hasMoreElements())
		{
			DbConnectionBase connection = enumConnections.nextElement();
			connection.dumpConnections(sbText);
		}
	}

	boolean returnConnectionToPool(DbAccessor dbId)
	{
		if(m_foreignDbConnectionBase != null)	// We have a foreign connection
		{
			m_foreignDbConnectionBase = null;	// Do not hold it anymore
			return true;
		}

		DbConnectionBase connection = m_hashConById.remove(dbId);
		if(connection != null)
		{
			connection.returnConnectionToPool();
			return true;
		}
		return false;
	}
	
/*	int getNbConnection(DbAccessor dbId)	// No need to synchonize: the m_hashConById is private to the current thread 
	{
		//if(m_hashConById == null)
			return 0;
	//	return m_hashConById.get(dbId);
	}
*/

	/*
	 * setForeignConnection
	 * Store a foreign connection in the TLS for usage by jlib management; the connection is not pooled ! 
	 */
	void setForeignConnection(DbConnectionBase foreignDbConnectionBase)
	{
		m_foreignDbConnectionBase = foreignDbConnectionBase;
	}

	/*
	 * Access to the foreign connection; returns null if no foerign connection used
	 */
	DbConnectionBase getForeignConnection()
	{
		return m_foreignDbConnectionBase;
	}

	/*
		commit for an accessor
		Not usable for foreign connections
	 */
	boolean commit(DbAccessor dbId)
	{
		// Foreign connections are not usable
		DbConnectionBase connection = m_hashConById.get(dbId);
		if(connection != null)
		{
			connection.commit();
			return true;
		}
		return false;
	}

	/*
	commit for an accessor
	Not usable for foreign connections
	 */
	/** Method added by Jilali Raki for WLC stored procedures
	 * 
	 * @param dbId
	 * @param autoCommit
	 * @return
	 */
	boolean setAutoCommit(DbAccessor dbId, boolean autoCommit)
	{
		// Foreign connections are not usable
		DbConnectionBase connection = m_hashConById.get(dbId);
		if(connection != null)
		{
			connection.setAutoCommit(autoCommit);
			return true;
		}
		return false;
	}	

	/*
		rollback for an accessor
		Not usable for foreign connections
	 */	
	boolean rollBack(DbAccessor dbId)
	{
		// Foreign connections are not usable
		DbConnectionBase connection = m_hashConById.get(dbId);
		if(connection != null)
		{
			connection.rollBack();
			return true;
		}
		return false;
	}
}
