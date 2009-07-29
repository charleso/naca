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


package jlib.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Properties;
import java.util.SortedMap;

import jlib.blowfish.Blowfish;
//import jlib.log.Log;
import jlib.log.Log;
import jlib.misc.BaseJmxGeneralStat;
import jlib.misc.EnvironmentVar;
import jlib.misc.StopWatch;
import jlib.misc.StringUtil;
import jlib.misc.ThreadSafeCounter;
import jlib.misc.Time_ms;


/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DbConnectionColl
{
	private LinkedList<DbConnectionBase> m_collFreeConnections = null;		// Collection of the connections currently not in use
	private LinkedList<DbConnectionBase> m_collUsedConnections = null;		// Collection of the connections currently in use 
	
	private DbConnectionParam m_dbConnectionParam = null;
	private int m_nGarbageCollectorStatement_ms = 0;
	
	private int m_nNbMaxConnection = 1;	// Unlimited
	private int m_nTimeBeforeRemoveConnection_ms = 0;
	private int m_nMaxStatementLiveTime_ms = -1;
	private boolean m_bUseExplain = false;
	private StopWatch m_swLastCheckRemoveObsoleteConnections = new StopWatch();
	//private int m_nNbConnectionCreated = 0;
	private ThreadSafeCounter m_tscNbConnectionCreated = new ThreadSafeCounter(0);
	private boolean m_bInit = false;
	private String m_csName = null;
	private boolean m_bShowRunningConnections = false;
	
	DbConnectionColl(String csName, int nNbMaxConnection, int nTimeBeforeRemoveConnection_ms, int nMaxStatementLiveTime_ms, boolean bUseExplain, int nGarbageCollectorStatement_ms)
	{
		m_collFreeConnections = new LinkedList<DbConnectionBase>();
		m_collUsedConnections = new LinkedList<DbConnectionBase>();
		m_nNbMaxConnection = nNbMaxConnection;
		m_nTimeBeforeRemoveConnection_ms = nTimeBeforeRemoveConnection_ms;
		m_nMaxStatementLiveTime_ms = nMaxStatementLiveTime_ms;
		m_bUseExplain = bUseExplain;
		m_nGarbageCollectorStatement_ms = nGarbageCollectorStatement_ms;
		m_csName = csName;
	}
	
	public boolean isInit()
	{
		return m_bInit;
	}
	
	void setName(String csName)
	{
		m_csName = csName;
	}

	public String getName()
	{
		return m_csName;
	}

	void init(DbConnectionParam dbConnectionParam)
	{
		m_dbConnectionParam = dbConnectionParam;
		m_bInit = true;
	}


	private synchronized DbConnectionBase popAtIndex(int nIndex)
	{
		try
		{
			if(m_collFreeConnections.size() > 0)
			{
				DbConnectionBase connection = m_collFreeConnections.remove(nIndex);	// The connection is not free anymore
				m_collUsedConnections.add(connection);		// It's then in use
				connection.showHideJMXBean(m_bShowRunningConnections);
				return connection;
			}
		}
		catch(IndexOutOfBoundsException e)
		{
		}
		return null;
	}
	
	DbConnectionBase tryGetPooledValidConnection(String csValidationQuery, String csPoolName, boolean bUseStatementCache, DbConnectionManagerBase connectionManager)
		throws DbConnectionException
	{
		while(true)
		{
			DbConnectionBase sqlConnection = popAtIndex(0);
			while(sqlConnection != null)
			{
				if(sqlConnection.canBeUsed(m_nTimeBeforeRemoveConnection_ms, csValidationQuery))
				{
//					Log.logNormal("Re-using validated db connection from cache. "+ getNbFreeConnection()+" still available.");
					return sqlConnection;
				}
//				StringBuilder sbText = new StringBuilder();
//				sqlConnection.dumpConnections(sbText);
//				System.out.println(sbText.toString());				
				
				removeConnection(sqlConnection, "tryGetPooledValidConnection");
				sqlConnection = popAtIndex(0);
			}
			
			// No sqlConnection found in the pool: Create a new one if max limit not reached
			sqlConnection = createNewConnection(csPoolName, bUseStatementCache, connectionManager, csValidationQuery);
			
			if(sqlConnection != null)	// Could create a new connection
				return sqlConnection;
			
			BaseJmxGeneralStat.incCounter(BaseJmxGeneralStat.COUNTER_INDEX_NbWaitDuringConnectionCreate);
			waitUntilConnectionAvailableOrCreatable();
		}
	}
	
	DbConnectionBase forceNewConnection(String csValidationQuery, String csPoolName, boolean bUseStatementCache, DbConnectionManagerBase connectionManager)
		throws DbConnectionException
	{
		// No sqlConnection found in the pool: Create a new one if max limit not reached
		DbConnectionBase sqlConnection = createNewConnection(csPoolName, bUseStatementCache, connectionManager, csValidationQuery);
		//if (sqlConnection != null) // PJD 19/06/2008: commented line
			//m_tscNbConnectionCreated.dec();	// PJD 19/06/2008: commented line
		return sqlConnection;
	}
	
	private void waitUntilConnectionAvailableOrCreatable()
	{
		Time_ms.wait_ms(1000);
	}
	
	private String replaceEnvVarsByValue(String csUrl)
	{
		int nStartPos = csUrl.indexOf('%');
		while(nStartPos >= 0)
		{
			int nEndPos = csUrl.indexOf('%', nStartPos+1);
			if(nEndPos >= 0)
			{
				String csLeft = csUrl.substring(0, nStartPos);
				String csRight = csUrl.substring(nEndPos+1);
				String csToken = csUrl.substring(nStartPos+1, nEndPos);
				String csValue = EnvironmentVar.getParamValue(csToken);
				if(StringUtil.isEmpty(csValue))
					csValue = "NULL";
				csUrl = csLeft + csValue + csRight;
			}
			nStartPos = csUrl.indexOf('%');
		}
		return csUrl;
	}
	
	private DbConnectionBase createNewConnection(String csPoolName, boolean bUseStatementCache, DbConnectionManagerBase connectionManager, String csValidationQuery)
		throws DbConnectionException
	{
		
//		Log.logNormal(m_tscNbConnectionCreated.get()+" created connections, out of "+m_nNbMaxConnection+" allowed.");
		
		if(m_tscNbConnectionCreated.get() < m_nNbMaxConnection || m_nNbMaxConnection == -1)
		{
		    try
			{
		    	String csUrl = m_dbConnectionParam.m_csUrl;
		    	if(m_dbConnectionParam.m_csConnectionUrlOptionalParams != null)
		    		csUrl += m_dbConnectionParam.m_csConnectionUrlOptionalParams;
		    	csUrl = StringUtil.replace(csUrl, "$FoundPoolName", csPoolName, true);
		    	csUrl = replaceEnvVarsByValue(csUrl);
		    	
				Connection connection = null;
				String csUser = (String)m_dbConnectionParam.m_propertiesUserPassword.get("user");
				String csCryptedPassword = (String)m_dbConnectionParam.m_propertiesUserPassword.get("CryptedPassword");
				String csCryptKey = (String)m_dbConnectionParam.m_propertiesUserPassword.get("CryptKey");
				if(!StringUtil.isEmpty(csCryptedPassword) && !StringUtil.isEmpty(csCryptKey))
				{
					// Got a crypted db password
					// Code to move in a private jar; not distrobuted as a source
					Blowfish blowfish = new Blowfish(csCryptKey, true);
					String csPassword = blowfish.decrypt(csCryptedPassword);
					
					Properties propertiesUserPassword = new Properties();
					propertiesUserPassword.setProperty("user", csUser);	
					propertiesUserPassword.setProperty("password", csPassword);
					//propertiesUserPassword.setProperty("FetchTSWTSasTimestamp", "true");				
				
					connection = m_dbConnectionParam.m_driver.connect(csUrl, propertiesUserPassword);
//					if(connection != null)
//						Log.logNormal("Correctly created new DB connection with crypted user/password. "+ m_tscNbConnectionCreated.get()+" created connections, out of "+m_nNbMaxConnection+" allowed.");
					
				}
				else
				{
					connection = m_dbConnectionParam.m_driver.connect(csUrl, m_dbConnectionParam.m_propertiesUserPassword);
//					if(connection != null)
//						Log.logNormal("Correctly created new DB connection. "+ m_tscNbConnectionCreated.get()+" created connections, out of "+m_nNbMaxConnection+" allowed.");
				}
				if(connection == null)
				{
//					Log.logCritical("ERROR: Could not create new DB connection. "+ m_tscNbConnectionCreated.get()+" existing connections, out of "+m_nNbMaxConnection+" allowed.");
					throw new DbConnectionException("Could not get valid DB Connection");
				}

				if (!StringUtil.isEmpty(m_dbConnectionParam.m_csPackage))
				{
					setConnectionPackage(connection, m_dbConnectionParam.m_csPackage);
				}

		    	connection.setAutoCommit(m_dbConnectionParam.m_bAutoCommit);
		    	if(m_dbConnectionParam.m_bCloseCursorOnCommit)
		    	{
		    		connection.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
		    	}
		    	
		    	DbDriverId dbDriverId = m_dbConnectionParam.getDbDriverId();
		    	
		    	DbConnectionBase sqlConnection = connectionManager.createConnection(connection, csPoolName, m_dbConnectionParam.getEnvironment(), bUseStatementCache, true, dbDriverId);
		    	
		    	sqlConnection.setDbConnectionColl(this);
		    	
		    	sqlConnection.setUseExplain(getUseExplain());
				
				if(sqlConnection.checkWithQuery(csValidationQuery))
				{
					m_tscNbConnectionCreated.inc();
					String csPrefix = connectionManager.getPropertyPrefix();
					sqlConnection.setOnceUUID(csPrefix);
					m_collUsedConnections.add(sqlConnection);	// this connection is in use
					sqlConnection.showHideJMXBean(m_bShowRunningConnections);
					
//			    	///////////// oracle 
			    	Statement s = sqlConnection.getDbConnection().createStatement();
			    	boolean b = s.execute("alter session set nls_timestamp_format = 'YYYY-DD-MM-HH24.MI.SS.FF6'");
			    	b = s.execute("ALTER SESSION SET OPTIMIZER_MODE = FIRST_ROWS_1");

			    	
//			    	String csDefaultTablePrefix = sqlConnection.getEnvironmentPrefix();
//					
//					BaseDbColDefinitionFactory dbColDefinitionFactory = new BaseDbColDefinitionFactory();
//					Hashtable<String, ColDescription> hashDbColDef = dbColDefinitionFactory.makeHashDbColDescription(sqlConnection, null, "TINTERF");
//					ColDescription col = hashDbColDef.get("D_EFFET");
//					
//					Hashtable<String, ColDescription> hashDbColDef2 = dbColDefinitionFactory.makeHashDbColDescription(sqlConnection, null, "THINTBE");
//					ColDescription col2 = hashDbColDef2.get("DATE_MAJ");
					
			    	
//			    	//////////// end oracle
			    	
			    	
			    	

					
					
					return sqlConnection;
				}
				else
				{
					sqlConnection.close();
					sqlConnection = null;
				}
		    }
		    catch(DbConnectionException e)
			{
//		    	Log.logCritical("DbConnectionException; exception=" + e.getMessage());
		    	throw e;
			}
		    catch(SQLException e)
			{
		    	throw new DbConnectionException(e.getMessage());
			}
		    catch (Exception ex) 
			{
		    	throw new RuntimeException(ex.getMessage());
		    }
		}
		return null;
	}
	
	private void setConnectionPackage(Connection connection, String csDbPackage)
	{
		try
		{
			Statement stmt = connection.createStatement();
			stmt.execute("SET CURRENT PACKAGESET = '" + csDbPackage + "'");
			stmt.close();
		}
		catch (Exception ex)
		{
		}
	}
	
	int removeConnection(DbConnectionBase connection, String csCallerId)
	{
		if(connection != null)
		{
			int n = connection.removeAllPreparedStatements();
			connection.close();
			connection.m_dbConnectionColl = null;
			connection.clearJDBCConnection();
			m_tscNbConnectionCreated.dec();
//		Log.logNormal("Removing DB connection from pool. "+ m_tscNbConnectionCreated.get()+" existing connections, out of "+m_nNbMaxConnection+" allowed.");

		return n;
		}
		return 0;		
	}
	
	void removeConnectionFromUsed(DbConnectionBase sqlConnection)
	{
		if(m_collUsedConnections.contains(sqlConnection))
		{
			m_collUsedConnections.remove(sqlConnection);
			sqlConnection.showHideJMXBean(false);	// Hide
		}
	}
	
	synchronized void releaseConnection(DbConnectionBase sqlConnection)
	{
		removeConnectionFromUsed(sqlConnection);
		if(sqlConnection.isGenerationCurrent())	// The connection belongs to the current generation and can be kept 
		{
			// Check if the current generation matches the last generation
			sqlConnection.markLastTimeUsage();
			
			if(m_swLastCheckRemoveObsoleteConnections.isTimeElapsed(m_nGarbageCollectorStatement_ms))
			{
				removeObsoleteConnections(); // Remove connections in timeout
				sqlConnection.garbageCollectorStatementsOptinalResetReservedStatement(true);
			}
			else
				sqlConnection.resetReservedStatements();
//			Log.logNormal("Returning DB connection to pool. "+ m_tscNbConnectionCreated.get()+" existing connections, out of "+m_nNbMaxConnection+" allowed.");
			m_collFreeConnections.addFirst(sqlConnection);
		}
		else
		{
			// The connection generation has changed and connection can't be kept
//			Log.logNormal("DB Connection generation changed; DB connection is not returned to pool and removed. "+ m_tscNbConnectionCreated.get()+" existing connections, out of "+m_nNbMaxConnection+" allowed.");
			removeConnection(sqlConnection, "DbConnectionColl::releaseConnection");
			sqlConnection = null;
		}
	}
	
	synchronized private void removeObsoleteConnections()
	{	
		DbConnectionBase connection = null;
		if(m_collFreeConnections.size() > 0)
			connection = m_collFreeConnections.getLast();
		while(connection != null && !connection.isValid(m_nTimeBeforeRemoveConnection_ms))
		{
			removeConnection(connection, "DbConnectionColl::removeObsoleteConnections");
			m_collFreeConnections.removeLast();
			if(m_collFreeConnections.size() > 0)
				connection = m_collFreeConnections.getLast();
			else
				connection = null;
		}
		m_swLastCheckRemoveObsoleteConnections.Reset();
	}	
	
	synchronized int garbageCollectorStatementsOfCollection()
	{
		int nNbStatementRemoved = 0;
		int nIndex = 0;
		
		DbConnectionBase connection = popAtIndex(nIndex);
		while(connection != null)
		{			
			if(!connection.isValid(m_nTimeBeforeRemoveConnection_ms))
			{
				nNbStatementRemoved += removeConnection(connection, "DbConnectionColl::garbageCollectorStatementsOfCollection");
			}
			else
			{
				// Connection is still valid
				nNbStatementRemoved += connection.garbageCollectorStatementsOptinalResetReservedStatement(false);
				m_collFreeConnections.add(nIndex, connection);
				nIndex++;
			}
			
			connection = popAtIndex(nIndex);
		}
		return nNbStatementRemoved;		
	}
	
	synchronized void forceRemoveAllStatementsOfCollection()
	{
		int nIndex = 0;
		DbConnectionBase connection = popAtIndex(nIndex);
		while(connection != null)
		{			
			removeConnection(connection, "DbConnectionColl::forceRemoveAllStatementsOfCollection");
			connection = popAtIndex(nIndex);
		}	
	}
	
	synchronized void dumpListStatements(SortedMap<Long, StatementPosInPool> mapStatements)
	{
		for(int nConnectionId=0; nConnectionId<m_collFreeConnections.size(); nConnectionId++)
		{
			DbConnectionBase connection = m_collFreeConnections.get(nConnectionId);
			connection.dumpListStatements(mapStatements);
		}
	}
	
	int getMaxStatementLiveTime_ms()
	{
		return m_nMaxStatementLiveTime_ms;
	}
	
	private boolean getUseExplain()
	{
		return m_bUseExplain;
	}
	
	int getNbMaxConnection()
	{
		return m_nNbMaxConnection;
	}

	/**
	 * Returns the size of the collection of free connections.
	 * To be used for logging.
	 * @return The size of the collection of free connections.
	 */
	synchronized int getNbFreeConnection()
	{
		if(m_collFreeConnections != null)
			return m_collFreeConnections.size();
		return 0;
	}
	
	synchronized int getNbCachedStatementsForAccessor()
	{
		if(m_collFreeConnections == null)
			return 0;
		
		int n = 0;
		for(int nConnectionId=0; nConnectionId<m_collFreeConnections.size(); nConnectionId++)		
		{
			DbConnectionBase connection = m_collFreeConnections.get(nConnectionId);
			n += connection.getNbCachedStatements();
		}
		return n;
	}
	
	int getNbAllocConnnections()
	{
		return m_tscNbConnectionCreated.get();
	}
	
	int getNbRunningConnections()
	{
		if(m_collUsedConnections != null)
			return m_collUsedConnections.size();
		return 0;
	}

	void showHideRunningConnections(boolean bShowRunningCon)
	{
		m_bShowRunningConnections = bShowRunningCon;
		if(m_collUsedConnections != null)
		{
			for(int nConnectionId=0; nConnectionId<m_collUsedConnections.size(); nConnectionId++)		
			{
				DbConnectionBase connection = m_collUsedConnections.get(nConnectionId);
				connection.showHideJMXBean(m_bShowRunningConnections);
			}
		}		
	}
	
	public void dumpConnections(StringBuilder sbText)
	{
		if(m_collUsedConnections != null)
		{
			for(int nConnectionId=0; nConnectionId<m_collUsedConnections.size(); nConnectionId++)		
			{
				DbConnectionBase connection = m_collUsedConnections.get(nConnectionId);
				connection.dumpConnections(sbText);
			}
		}		
	}
}
