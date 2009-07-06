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
 * A DbAcessor is attached to a Db 
 * It contains a Database identifier.
 * All databases must be uniquely identified by an single instance of derivated from this class
 * The m_csKey value must point to the section of an app.properties file.
 * The app.properties file section must be accessible in the classpath, and must contains 
 * the following values in the case of PUB2000Db:
 * 
 * PUB2000Db.driver=com.ibm.db2.jcc.DB2Driver
 * PUB2000Db.connectionString=jdbc:db2://db2002.consultas.ch:50002/PUB2000T
 * PUB2000Db.user=<User>
 * PUB2000Db.password=<Password>
 * PUB2000Db.environment=TEST
 * 
 * In that sample, m_csKey must be set to "PUB2000Db"
 */
public class DbAccessor extends BaseCloseMBean
{
	private String m_csKey = null;	// Gives the section name within the app.properties file. This section is used to provide DB parameters
	
	public DbAccessor(String csKey)
	{
		super("DbAccessor_" + csKey, csKey);
		m_csKey = csKey;
	}
	
	public String getKey()	// Access to the app.properties section's key 
	{
		return m_csKey;
	}
	
	/**
	 * DbConnectionBase getConnection()
	 * @return a database connection corresponding to the Db identified by DbId passed 
	 * in the constructor. 
	 * The connection is created if it is not establed yet; It's managed from the pool and
	 * cannot be accessed publicly 
	 **/
	DbConnectionBase getConnection()	
	{
		// Try to get the connection from the Thread Local Storage
		DbConnectionBase dbConnectionBase = DbTLSConnectionStorage.get(this);
		if(dbConnectionBase == null)	// The connection doesn't exist in the TLS: This is the 1st clause created within this thread since laste returnConnectionToPool 
		{
			// Establish a connection: It can be either got form the pool (of one is available) or created.
			dbConnectionBase = DbAccessorConnectionManager.getConnection(this);
			if(dbConnectionBase != null)
			{
				// The connection has been obtained from pool; store it in the TLS for futher reference 
				DbTLSConnectionStorage.set(this, dbConnectionBase);
			}
		}
		// Return current connection
		return dbConnectionBase;
	}
	
	/**
	 * DbConnectionBase getAlternateConnection()
	 * @return a database connection NOT managed in the TLS
	 * It's taken from the pool
	 **/
	public DbConnectionBase getAlternateConnection()	
	{
		// Establish a connection: It can be either got form the pool (of one is available) or created.
		// Do not act on TLS !
		DbConnectionBase dbConnectionBase = DbAccessorConnectionManager.getConnection(this);
		return dbConnectionBase;	// Return current connection
	}
	
	public boolean isOracle()
	{
		return DbAccessorConnectionManager.isOracle(this);
	}
	
	
	// JMX Bean management
	protected void buildDynamicMBeanInfo()
	{
		addOperation("ShowRunningCon", getClass(), "setShowRunningCon");
		addAttribute("NbMaxConnections", getClass(), "NbMaxConnections", int.class);
		addAttribute("NbAllocConnections", getClass(), "NbAllocConnections", int.class);
		addAttribute("NbUnusedConnections", getClass(), "NbUnusedConnections", int.class);
		addAttribute("NbRunningConnections", getClass(), "NbRunningConnections", int.class);
		addAttribute("NbUnusedCachedStmts", getClass(), "NbUnusedCachedStmts", int.class);
		addAttribute("AreConnectionsShown", getClass(), "AreConnectionsShown", boolean.class);
	}

	// Number of currently unused connections
	public int getNbUnusedConnections()
	{
		int nNbUnusedConnections = DbAccessorConnectionManager.getNbUnusedConnectionsForDbAccessor(this);
		return nNbUnusedConnections;
	}
	
	// Number of currently allocated connections
	public int getNbAllocConnections()
	{
		int nNbAllocConnections = DbAccessorConnectionManager.getNbAllocConnnectionsForAccessor(this);
		return nNbAllocConnections;
	}
	
	public int getNbRunningConnections()
	{
		int n = DbAccessorConnectionManager.getNbRunningConnectionsForDbAccessor(this);	
		return n;
	}
	
	// Max number of connections
	public int getNbMaxConnections()
	{
		int nNbUnusedConnections = DbAccessorConnectionManager.getNbMaxConnectionForAccessor(this);
		return nNbUnusedConnections;
	}
	
	public int getNbUnusedCachedStmts()
	{	
		int n = DbAccessorConnectionManager.getNbCachedStatementsForAccessor(this);
		return n;
	}
	
	private boolean m_bShowRunningCon = false;
	
	// Operation
	public void setShowRunningCon()
	{
		m_bShowRunningCon = !m_bShowRunningCon;
		DbAccessorConnectionManager.showHideRunningConnections(this, m_bShowRunningCon);		
	}
	
	public boolean getAreConnectionsShown()
	{
		return m_bShowRunningCon;
	}
	
	public static String dumpConnectionsForAllAccessors()
	{
		StringBuilder sbText = new StringBuilder();
		sbText.append("Currently running connections in the Thread Local Storage for all DB Accessors:\n");		
		DbTLSConnectionStorage.dumpConnectionsForAllAccessors(sbText);
		return sbText.toString();
	}

	public static String dumpConnections(DbAccessor accessor)
	{
		StringBuilder sbText = new StringBuilder();		
		accessor.dumpConnections(sbText);
		return sbText.toString();
	}
	
	public void dumpConnections(StringBuilder sbText)
	{
		sbText.append("DbAccessor: "+getKey()+"\n");
		sbText.append("Number unused connections: "+getNbUnusedConnections() + "\n");
		sbText.append("Number running connections: "+getNbRunningConnections() + "\n");
		sbText.append("Number max connections: "+getNbMaxConnections() + "\n");
		sbText.append("Number unused cached statements: "+getNbUnusedCachedStmts() + "\n");
		
		DbAccessorConnectionManager.dumpConnections(this, sbText);
	}
	
	public static void returnAllAccessorsConnectionsToPool()
	{
		DbTLSConnectionStorage.returnAllConnectionsToPool();
	}
}
