/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 8 juil. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jlib.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import jlib.exception.ProgrammingException;
import jlib.exception.TechnicalException;
import jlib.log.Log;
import jlib.misc.BaseJmxGeneralStat;
import jlib.misc.StopWatch;
import jlib.misc.StringUtil;
import jlib.misc.Time_ms;
import jlib.threads.Threadutil;

public abstract class DbConnectionBase //extends BaseOpenMBean
{
	private boolean m_bUseJmx = true;
	private String m_csPrefId = null;
	private String m_csEnvironment = "" ;
	protected Connection m_dbConnection = null;
	private boolean m_bUseRowId = false;	// true if must use RowId to support updates in cursors "select for update" (Oracle needs it)
	private Hashtable<String, DbPreparedStatement> m_hashStatement = new Hashtable<String, DbPreparedStatement>();	// Hsah collection of statement; Vey=int (hashed statement string), Value=Statement
	private boolean m_bUseCachedStatements = true;	// true if a cache of all met statements is kept by the current connection, false if the statement is recreated
	private StopWatch m_stopWatchLastUsage = null;
	private int m_nMaxStatementLiveTime_ms = -1;	// INFINITE by default
	private int m_nGenerationId = -1;
	public DbConnectionColl m_dbConnectionColl = null;
	private boolean m_bUseExplain = false;
	private DbDriverId m_dbDriverId = null;
	private String m_csUUID = null;
	
	public DbConnectionBase(Connection conn, String csPrefId, String csEnv, boolean bUseCachedStatements, boolean bUseJmx, DbDriverId dbDriverId)
	{
		m_dbDriverId = dbDriverId;
		//super("DbConnectionBase_"+csPrefId, "DbConnectionBase");
		m_csPrefId = csPrefId;
		m_bUseCachedStatements = bUseCachedStatements;
		m_dbConnection = conn ;
		if(csEnv.equals("OracleTest"))	// Tests have no prefixe
		{
			m_bUseRowId = true;
		}
		else
		{
			m_csEnvironment = csEnv ;
		}
		m_stopWatchLastUsage = new StopWatch();
		
		m_bUseJmx = bUseJmx;
		if(m_bUseJmx)
		{
			BaseJmxGeneralStat.incCounter(BaseJmxGeneralStat.COUNTER_INDEX_NbNonFinalizedConnection);
			BaseJmxGeneralStat.incCounter(BaseJmxGeneralStat.COUNTER_INDEX_NbActiveConnection);
		}
		
		m_nGenerationId = ConnectionGenerationManager.getGenerationId();
	}
	
	public DbDriverId getDbDriverId()
	{
		return m_dbDriverId;
	}
		
	public void finalize()
	{
		if(m_bUseJmx)
			BaseJmxGeneralStat.decCounter(BaseJmxGeneralStat.COUNTER_INDEX_NbNonFinalizedConnection);
	}
	
	public void close()
	{
		if(m_bUseJmx)
			BaseJmxGeneralStat.decCounter(BaseJmxGeneralStat.COUNTER_INDEX_NbActiveConnection);
		doClose();
	}	
	
	void setDbConnectionColl(DbConnectionColl dbConnectionColl)
	{
		m_dbConnectionColl = dbConnectionColl;
		if(m_dbConnectionColl != null)
		{
			m_nMaxStatementLiveTime_ms = m_dbConnectionColl.getMaxStatementLiveTime_ms(); 
		}
	}
		
	boolean isGenerationCurrent()
	{
		return ConnectionGenerationManager.isGenerationCurrent(m_nGenerationId);
	}
	
	public void setConnectionUnreusable()
	{
		m_nGenerationId = -1;	// This connection won't reused
	}	
	
	boolean canBeUsed(int nTimeBeforeRemoveConnection_ms, String csValidationQuery)
	{
		if(ConnectionGenerationManager.isGenerationCurrent(m_nGenerationId))
		{
			if(isValid(nTimeBeforeRemoveConnection_ms) && isOpen())
			{
				if(checkWithQuery(csValidationQuery))
					return true;
			}
		}
		return false;
	}
	
	boolean isValid(int nTimeBeforeRemoveConnection_ms)
	{
		if(m_dbConnection != null)
		{
			try
			{
				if(m_dbConnection.isClosed())
					return false;
				// Still open
				if(m_stopWatchLastUsage.isTimeElapsed(nTimeBeforeRemoveConnection_ms))	// Obsolete
					return false;				
				return true;
			} 
			catch (SQLException e)
			{
				LogSQLException.log(e);
			}
		}
		return false;
	}
	
	
	boolean isOpen()
	{
		if(m_dbConnection != null)
		{
			try
			{
				if(!m_dbConnection.isClosed())
				{
					return true;
				}
			} 
			catch (SQLException e)
			{
				LogSQLException.log(e);
			}
		}
		return false;				
	}
		
	protected void doClose()
	{
		if(m_dbConnection != null)
		{
			try
			{
				if(!m_dbConnection.isClosed())
					m_dbConnection.close();
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
	synchronized int garbageCollectorStatementsOptinalResetReservedStatement(boolean bResetReservedStatements)
	{
		if(m_hashStatement == null)
			return 0;
		
		int n = 0;
		Set<Map.Entry<String, DbPreparedStatement>> set = m_hashStatement.entrySet();
		Iterator<Map.Entry<String, DbPreparedStatement>> iterMapEntry = set.iterator();
		while(iterMapEntry.hasNext())
		{
			Map.Entry<String, DbPreparedStatement> mapEntry = iterMapEntry.next(); 
			DbPreparedStatement dbPreparedStatement = mapEntry.getValue();
			if(dbPreparedStatement.isTimeOut(m_nMaxStatementLiveTime_ms))
			{
				dbPreparedStatement.close();
				iterMapEntry.remove();
				n++;
			}
			else if(bResetReservedStatements)
				dbPreparedStatement.resetReserved();
		}
		
		return n;
	}
	
	synchronized int getNbCachedStatements()
	{
		if(m_hashStatement == null)
			return 0;
		return m_hashStatement.size();
	}
	
	synchronized void resetReservedStatements()
	{
		if(m_hashStatement == null)
			return;
		
		Set<Map.Entry<String, DbPreparedStatement>> set = m_hashStatement.entrySet();
		Iterator<Map.Entry<String, DbPreparedStatement>> iterMapEntry = set.iterator();
		while(iterMapEntry.hasNext())
		{
			Map.Entry<String, DbPreparedStatement> mapEntry = iterMapEntry.next(); 
			DbPreparedStatement dbPreparedStatement = mapEntry.getValue();
			dbPreparedStatement.resetReserved();
		}
	}
	
	synchronized void dumpListStatements(SortedMap<Long, StatementPosInPool> mapStatements)
	{
		if(m_hashStatement == null)
			return;
		
		Set<Map.Entry<String, DbPreparedStatement>> set = m_hashStatement.entrySet();
		Iterator<Map.Entry<String, DbPreparedStatement>> iterMapEntry = set.iterator();
		while(iterMapEntry.hasNext())
		{			
			Map.Entry<String, DbPreparedStatement> mapEntry = iterMapEntry.next();
			String csStatementId = mapEntry.getKey();
			DbPreparedStatement dbPreparedStatement = mapEntry.getValue(); 
			
			if(!dbPreparedStatement.isReserved())
			{
				StatementPosInPool pos = new StatementPosInPool(this, csStatementId);			
				mapStatements.put(dbPreparedStatement.getLastUsageTimeValue(), pos);
			}
		}
	}
	
	boolean checkWithQuery(String csValidationQuery)
	{
		if(StringUtil.isEmpty(csValidationQuery))
			return true;
		
		boolean b = false;
		DbPreparedStatement sqlStatement = prepareStatement(csValidationQuery, 0, false);
		if(sqlStatement != null)
		{
			ResultSet r = sqlStatement.executeSelect();			
			if(r != null)
			{
				try
				{
					r.close();
					b = true;
				}
				catch (SQLException e)
				{
				}
			}
			else
			{
				Log.logCritical("Error during check DB connection with query " + csValidationQuery);
			}
	
			if(!m_bUseCachedStatements)
				sqlStatement.close();
		}
		return b;
	}
	
	void markLastTimeUsage()
	{
		m_stopWatchLastUsage.Reset();
	}
	
	public synchronized int removeAllPreparedStatements()
	{
		if(m_hashStatement == null)
			return 0;
		
		int n = 0;
		Collection<DbPreparedStatement> col = m_hashStatement.values();
		Iterator<DbPreparedStatement> iter = col.iterator();
		while(iter.hasNext())
		{
			DbPreparedStatement statement = iter.next();
			statement.close();
			n++;
		}

		m_hashStatement = null;
		return n;
	}

	//int m_nDEBUGCount = 0;
	
	synchronized private DbPreparedStatement getCachedStatement(String csQueryHash)
	{
		if(m_hashStatement == null)
			return null;
		
		DbPreparedStatement SQLStatement = m_hashStatement.get(csQueryHash);
		if(SQLStatement != null)
			SQLStatement.setStatementUsed();
		return SQLStatement;
	}
	
	synchronized boolean forceRemoveStatement(String csStatementId)
	{
		if(m_hashStatement == null)
			return false;
		
		DbPreparedStatement dbPreparedStatement = m_hashStatement.get(csStatementId);
		boolean b = dbPreparedStatement.closeIfNotReserved();
		if(b)
			m_hashStatement.remove(csStatementId);
		return b; 
	}
	
	public int executeOperation(SQLTypeOperation typeOperation)
	{
		if(typeOperation== SQLTypeOperation.Commit)
		{
			int n = commit();
			if(n != 0)
				return -1;
			return 0;
		}
		else if(typeOperation == SQLTypeOperation.Rollback)
		{
			int n = rollBack();
			if(n != 0)
				return -1;
			return 0;
		}
		return -1;
	}
	
	public DbPreparedStatement prepareStatement(String csQuery)
	{
		return prepareStatement(csQuery, 0, false);
	}
	
	public DbPreparedStatement prepareStatement(SQLClause sqlStatement)
	{
		String csQuery = sqlStatement.getQuery();
		DbPreparedStatement preparedStatement = prepareStatement(csQuery, 0, false);
		sqlStatement.fillParameters(preparedStatement);
		
		return preparedStatement; 
	}
	
//	ResultSet prepareAndExecuteSelect(SQLClause sqlClause) 
//		throws TechnicalException
//	{
//		ResultSet resultSet = null;		
//		
//		String csQuery = sqlClause.getQuery();
//		SQLTypeOperation typeOperation = SQLTypeOperation.determineOperationType(csQuery, false);
//		if(typeOperation != SQLTypeOperation.Select)
//		{			
//			TechnicalException.throwException(TechnicalException.NOT_SELECT_STMT, csQuery);
//		}
//		
//		String csPrefixedQuery = SQLTypeOperation.addEnvironmentPrefix(getEnvironmentPrefix(), csQuery, typeOperation, "");
//		DbPreparedStatement preparedStatement = prepareStatementWithException(csPrefixedQuery, 0, false);
//		if(preparedStatement != null)
//		{
//			sqlClause.fillParameters(preparedStatement);
//			
//			resultSet = preparedStatement.executeSelectWithException();
//		}
//		return resultSet; 
//	}
	
	int prepareAndExecuteWithException(SQLClause sqlClause)
		throws TechnicalException
	{
		String csQuery = sqlClause.getQuery();

		TechnicalException.throwIfNullOrEmpty(csQuery, TechnicalException.DB_ERROR_PREPARE_STATEMENT,"Query is not set. Call 'SQLClause.set' before trying to execute the query.");
		
		SQLTypeOperation typeOperation = SQLTypeOperation.determineOperationType(csQuery, false);
		
		String csPrefixedQuery = SQLTypeOperation.addEnvironmentPrefix(getEnvironmentPrefix(), csQuery, typeOperation, "");
		DbPreparedStatement preparedStatement = prepareStatementWithException(csPrefixedQuery, 0, false);
		if(preparedStatement != null)
		{
			sqlClause.fillParameters(preparedStatement);
			if(typeOperation == SQLTypeOperation.Select || typeOperation==null)
			{
				try {
					ResultSet resultSet = preparedStatement.executeSelectWithException();
					sqlClause.setResultSetSet(resultSet);
				}
				catch (ProgrammingException e) {
					if (e.getCause() instanceof SQLException) {
						ProgrammingException.throwException(ProgrammingException.DB_ERROR_SELECT, sqlClause, (SQLException)e.getCause());
					} else
						throw e;
				}
				return 1;
			}
			else
			{
				int n = preparedStatement.executeWithException(typeOperation, sqlClause);
				return n;
			}
		}
			
		return -1; 
	}

	synchronized public DbPreparedStatement prepareStatement(String csQuery, int nSuffixeHash, boolean bHoldability)
	{
		String csQueryHash = csQuery + nSuffixeHash;
		if(m_bUseCachedStatements)
		{
			DbPreparedStatement SQLStatement = getCachedStatement(csQueryHash);
			if(SQLStatement != null)
				return SQLStatement;
		}
		
		DbPreparedStatement SQLStatement = createAndPrepare(csQuery, bHoldability);
		if(SQLStatement != null && m_hashStatement != null)
		{
			if(m_bUseCachedStatements)
				m_hashStatement.put(csQueryHash, SQLStatement);
		}
		return SQLStatement;
	}
	
	synchronized public DbPreparedStatement prepareStatementWithException(String csQuery, int nSuffixeHash, boolean bHoldability) 
		throws TechnicalException
	{
		String csQueryHash = csQuery + nSuffixeHash;
		if(m_bUseCachedStatements)
		{
			DbPreparedStatement SQLStatement = getCachedStatement(csQueryHash);
			if(SQLStatement != null)
				return SQLStatement;
		}
		
		DbPreparedStatement SQLStatement = createAndPrepareWithException(csQuery, bHoldability);
		if(SQLStatement != null && m_hashStatement != null)
		{
			if(m_bUseCachedStatements)
				m_hashStatement.put(csQueryHash, SQLStatement);
		}
		return SQLStatement;
	}
	
	public Statement create()
	{
		try
		{
			Statement statement = m_dbConnection.createStatement();
			return statement;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public abstract DbPreparedStatement createAndPrepare(String csQuery, boolean bHoldability);
	public abstract DbPreparedStatement createAndPrepareWithException(String csQuery, boolean bHoldability) throws TechnicalException;
	
	public abstract boolean prepareCallableStatement(DbPreparedCallableStatement m_preparedCallableStatement, String csStoredProcName, int nNbParamToProvide);
	
	public int rollBack()
	{
		//markLastTimeStamp();
		if (m_dbConnection != null)
		{
			try
			{
				m_dbConnection.rollback() ;
				return 0;
			}
			catch (SQLException e)
			{
				LogSQLException.log(e);
				return e.getErrorCode();
			}
		}
		return 0;
	}
	
	public int commit()
	{
		//markLastTimeStamp();
		if (m_dbConnection != null)
		{
			try
			{
				m_dbConnection.commit() ;
				return 0;
			}
			catch (SQLException e)
			{
				LogSQLException.log(e);
				return e.getErrorCode();
			}
		}
		return 0;
	}
	
	
	/** Method added by Jilali Raki for WLC stored procedures
	 * 
	 * @param autoCommit
	 * @return
	 */
	public int setAutoCommit(boolean autoCommit)
	{
		//markLastTimeStamp();
		if (m_dbConnection != null)
		{
			try
			{
				m_dbConnection.setAutoCommit(autoCommit) ;
				return 0;
			}
			catch (SQLException e)
			{
				LogSQLException.log(e);
				return e.getErrorCode();
			}
		}
		return 0;
	}
	
	
	public SQLException rollBackWithException()
	{
		if (m_dbConnection != null)
		{
			try
			{
				m_dbConnection.rollback() ;
				return null;
			}
			catch (SQLException e)
			{
				LogSQLException.log(e);
				return e;
			}
		}
		return null;
	}
	
	public SQLException commitWithException()
	{
		if (m_dbConnection != null)
		{
			try
			{
				m_dbConnection.commit() ;
				return null;
			}
			catch (SQLException e)
			{
				LogSQLException.log(e);
				return e;
			}
		}
		return null;
	}
	
	public String getEnvironmentPrefix()
	{
		return m_csEnvironment ;
	}


	public boolean supportCursorName()
	{
		if(m_bUseRowId)
			return false;
		return true;
	}

	/**
	 * @param n
	 * @return
	 */

	public void Release()
	{
		if (m_dbConnection != null)
		{
			try
			{
				m_dbConnection.close() ;
			} 
			catch (SQLException e)
			{
				LogSQLException.log(e);
				e.printStackTrace();
			}
			m_dbConnection = null ;
		}
	}
	
	public void returnConnectionToPool()
	{
		if(m_dbConnectionColl != null)
			m_dbConnectionColl.releaseConnection(this);
	}
	
	String getPrefId()
	{
		return m_csPrefId;
	}
	
	void setUseExplain(boolean bUseExplain)
	{
		m_bUseExplain = bUseExplain;
	}
	
	public boolean getUseExplain()
	{
		return m_bUseExplain;
	}
	
	public Connection getDbConnection()
	{
		return m_dbConnection;		
	}
	
	
	private DbConnectionBaseJMXBean m_dbConnectionBaseJMXBean = null;
	
	void showHideJMXBean(boolean bToShow)
	{
		doShowHideJMXBean(bToShow);
	}
	
	synchronized void doShowHideJMXBean(boolean bToShow)
	{
		if(m_bUseJmx)
		{
			if(bToShow && !isBeanCreated())
			{
				m_dbConnectionBaseJMXBean = new DbConnectionBaseJMXBean(this); 
				m_dbConnectionBaseJMXBean.createMBean("Con_"+m_csUUID, m_csUUID);
			}
			else if(!bToShow && isBeanCreated())
			{
				m_dbConnectionBaseJMXBean.unregisterMBean();
				m_dbConnectionBaseJMXBean.cleanup();
				m_dbConnectionBaseJMXBean = null;
			}
		}
	}
	
	public void dumpConnections(StringBuilder sbText)
	{
		sbText.append("-------------------------------------------------------------------------\n");
		sbText.append("Connection: Con_"+m_csUUID+"\n;");
		sbText.append("    Last usage"+m_stopWatchLastUsage.getElapsedTime()+" ms\n;");
		sbText.append("    Statements:\n");
				
		Enumeration<String> eStsmt = m_hashStatement.keys();
		while(eStsmt.hasMoreElements())
		{
			String csStmt = eStsmt.nextElement();
			DbPreparedStatement statement = m_hashStatement.get(csStmt);
			long lLastUsageTimeValue = statement.getLastUsageTimeValue();
			sbText.append("    " + lLastUsageTimeValue + ";  " + csStmt + "\n");
		}
	}
			
	private synchronized boolean isBeanCreated()
	{
		if(m_dbConnectionBaseJMXBean == null)
			return false;
		return true;
	}
	
	public void setOnceUUID(String csConnId)
	{
		if(m_csUUID == null)
			m_csUUID = csConnId + "_" + Time_ms.getCurrentTime_ms() + "_" + Threadutil.getCurrentThreadId();
	}
	
	public String getUUID()
	{
		return m_csUUID; 
	}
	
	void createStmtJMXBeans(DbConnectionBaseJMXBean JMXBeanOwner, String csName, String csDescription)
	{
		if(m_hashStatement == null)
			return;
		
		int n = 0;
		Enumeration<String> eStsmt = m_hashStatement.keys();
		while(eStsmt.hasMoreElements())
		{
			String csStmt = eStsmt.nextElement();
			DbPreparedStatement statement = m_hashStatement.get(csStmt);
			long lLastUsageTimeValue = statement.getLastUsageTimeValue();
			DbConnectionBaseStmtJMXBean dbConnectionBaseStmtJMXBean = new DbConnectionBaseStmtJMXBean(csStmt, lLastUsageTimeValue);
			dbConnectionBaseStmtJMXBean.createMBean(csName + "_" + lLastUsageTimeValue, csDescription);
			JMXBeanOwner.add(dbConnectionBaseStmtJMXBean);
			n++;
		}	
	}
}
