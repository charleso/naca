/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.sql;

import java.sql.Connection;
import java.util.Properties;

import jlib.exception.TechnicalException;
import jlib.misc.ListCoupleRender;
import jlib.misc.StringUtil;
import jlib.xml.Tag;

public abstract class DbConnectionManagerBase
{
	DbConnectionParam m_DbConnectionParam = null;
	private DbConnectionPool m_SQLConnectionPool = null;
	private DbDataCacheManager m_cacheManager = null;
	private String m_csPropertyPrefix = "";
	
	public DbConnectionManagerBase()
	{
		m_DbConnectionParam = new DbConnectionParam();
		m_cacheManager = new DbDataCacheManager() ;
	}
	
	public Object getCachedData(String table, String key)
	{
		return m_cacheManager.getData(table.toUpperCase(), key);
	}

	public void recordCachedData(String table, String key, Object value)
	{
		m_cacheManager.RegisterData(table.toUpperCase(), key, value);
	}
	
	public DbConnectionBase getConnection() throws DbConnectionException
	{
		return getConnection("", null, false);
	}
	
	public DbConnectionBase getConnection(boolean bUseStatementCache) throws DbConnectionException
	{
		return getConnection("", null, bUseStatementCache);
	}

	public DbConnectionBase getConnection(String csProgramId, boolean bUseStatementCache) throws DbConnectionException
	{
		return getConnection(csProgramId, null, bUseStatementCache);
	}
	
	public DbConnectionBase getConnection(String csProgramId, String csProgramParent, boolean bUseStatementCache) throws DbConnectionException
	{
		DbConnectionColl connectionColl = m_SQLConnectionPool.getConnectionCollForPref(csProgramId, csProgramParent);
		if(connectionColl != null)
		{
			if(!connectionColl.isInit())
				connectionColl.init(m_DbConnectionParam);
			String csPoolName = connectionColl.getName();
	 		DbConnectionBase sqlConnection = connectionColl.tryGetPooledValidConnection(m_csValidationQuery, csPoolName, bUseStatementCache, this);
	 		return sqlConnection;
		}
		return null;
	}
	
	public DbConnectionBase getNewConnection() throws DbConnectionException
	{
		return getNewConnection("", null, false);
	}

	public DbConnectionBase getNewConnection(boolean bUseStatementCache) throws DbConnectionException
	{
		return getNewConnection("", null, bUseStatementCache);
	}
	
	public DbConnectionBase getNewConnection(String csProgramId, boolean bUseStatementCache) throws DbConnectionException
	{
		return getNewConnection(csProgramId, null, bUseStatementCache);
	}
	
	public DbConnectionBase getNewConnection(String csProgramId, String csParentProgramId, boolean bUseStatementCache) throws DbConnectionException
	{
		DbConnectionColl connectionColl = m_SQLConnectionPool.getConnectionCollForPref(csProgramId, csParentProgramId);
		if(connectionColl != null)
		{
			if(!connectionColl.isInit())
				connectionColl.init(m_DbConnectionParam);
			String csPoolName = connectionColl.getName();
	 		DbConnectionBase sqlConnection = connectionColl.forceNewConnection(m_csValidationQuery, csPoolName, bUseStatementCache, this);
	 		return sqlConnection;
		}
		return null;
	}
	
	public abstract DbConnectionBase createConnection(Connection connection, String csPrefId, String csEnvironment, boolean bUseStatementCache, boolean bUseJmx, DbDriverId dbDriver);
	
	public void returnConnection(DbConnectionBase SQLConnection)
	{
		m_SQLConnectionPool.releaseConnection(SQLConnection);
	}
	
	public DbConnectionPool init(String csDBParameterPrefix, Tag tagSQLConfig)
	{
		m_DbConnectionParam.m_csUrl = tagSQLConfig.getVal(csDBParameterPrefix+"dburl");
		m_DbConnectionParam.setEnvironment(tagSQLConfig.getVal(csDBParameterPrefix+"dbenvironment"));
		m_DbConnectionParam.m_csPackage = tagSQLConfig.getVal(csDBParameterPrefix+"dbpackage");
		m_csValidationQuery = tagSQLConfig.getVal("validationQuery");
		m_DbConnectionParam.m_bCloseCursorOnCommit = tagSQLConfig.getValAsBoolean(csDBParameterPrefix+"CloseCursorOnCommit");
		m_DbConnectionParam.m_bAutoCommit = tagSQLConfig.getValAsBoolean("AutoCommit");
		
		String csDriverClass = tagSQLConfig.getVal(csDBParameterPrefix+"driverClass");
		String csConnectionUrlOptionalParams = tagSQLConfig.getVal(csDBParameterPrefix+"dbConnectionUrlOptionalParams");

		String csUser = tagSQLConfig.getVal(csDBParameterPrefix+"dbuser");
		String csCryptedDbPassword = tagSQLConfig.getVal(csDBParameterPrefix+"CryptedDbpassword");
		String csCryptKey = tagSQLConfig.getVal(csDBParameterPrefix+"CryptKey");
		if(!StringUtil.isEmpty(csCryptedDbPassword) && !StringUtil.isEmpty(csCryptKey))
			createDriver(csDriverClass, csUser, csCryptedDbPassword, csCryptKey, csConnectionUrlOptionalParams);
		else
		{
			String csPassword = tagSQLConfig.getVal(csDBParameterPrefix+"dbpassword");
			createDriver(csDriverClass, csUser, csPassword, csConnectionUrlOptionalParams);
		}
		
		m_SQLConnectionPool = new DbConnectionPool(tagSQLConfig);
		return m_SQLConnectionPool;
	}
	
	public boolean initDB2(String csUrl, String csUser, String csPassword, String csConnectionUrlOptionalParams, int nNbMaxConnections, int nTimeBeforeRemoveConnection_ms, int nMaxStatementLiveTime_ms, int nGarbageCollectorStatement_ms)
	{
		String csDriverClass = "com.ibm.db2.jcc.DB2Driver";
		return initDriverClass(csUrl, csUser, csPassword, csDriverClass, csConnectionUrlOptionalParams, nNbMaxConnections, nTimeBeforeRemoveConnection_ms, nMaxStatementLiveTime_ms, nGarbageCollectorStatement_ms);
	}
	
	public boolean initOracle(String csUrl, String csUser, String csPassword, String csConnectionUrlOptionalParams, int nNbMaxConnections, int nTimeBeforeRemoveConnection_ms, int nMaxStatementLiveTime_ms, int nGarbageCollectorStatement_ms)
	{
		String csDriverClass = "oracle.jdbc.driver.OracleDriver";
		return initDriverClass(csUrl, csUser, csPassword, csDriverClass, csConnectionUrlOptionalParams, nNbMaxConnections, nTimeBeforeRemoveConnection_ms, nMaxStatementLiveTime_ms, nGarbageCollectorStatement_ms);
	}
	
	public boolean initMySql(String csUrl, String csUser, String csPassword, String csConnectionUrlOptionalParams, int nNbMaxConnections, int nTimeBeforeRemoveConnection_ms, int nMaxStatementLiveTime_ms, int nGarbageCollectorStatement_ms)
	{
		String csDriverClass = "com.mysql.jdbc.Driver";
		return initDriverClass(csUrl, csUser, csPassword, csDriverClass, csConnectionUrlOptionalParams, nNbMaxConnections, nTimeBeforeRemoveConnection_ms, nMaxStatementLiveTime_ms, nGarbageCollectorStatement_ms);
	}
	
	public boolean initSqlServer(String csUrl, String csUser, String csPassword, String csConnectionUrlOptionalParams, int nNbMaxConnections, int nTimeBeforeRemoveConnection_ms, int nMaxStatementLiveTime_ms, int nGarbageCollectorStatement_ms)
	{
		String csDriverClass = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
		return initDriverClass(csUrl, csUser, csPassword, csDriverClass, csConnectionUrlOptionalParams, nNbMaxConnections, nTimeBeforeRemoveConnection_ms, nMaxStatementLiveTime_ms, nGarbageCollectorStatement_ms);
	}
	
	public boolean initDriverClass(String csUrl, String csUser, String csPassword, String csDriverClass, String csConnectionUrlOptionalParams, int nNbMaxConnections, int nTimeBeforeRemoveConnection_ms, int nMaxStatementLiveTime_ms, int nGarbageCollectorStatement_ms)
	{
		if(csDriverClass.indexOf("oracle") != -1)	// Oracle doesn't support SetCloseCursorOnCommit 
			m_bCanSetCloseCursorOnCommit = false;
		else
			m_bCanSetCloseCursorOnCommit = true;
		m_DbConnectionParam.m_csUrl = csUrl;

		boolean b = createDriver(csDriverClass, csUser, csPassword, csConnectionUrlOptionalParams);
		if(b)
			m_SQLConnectionPool = new DbConnectionPool("UnknownPoolName", nNbMaxConnections, nTimeBeforeRemoveConnection_ms, nMaxStatementLiveTime_ms, nGarbageCollectorStatement_ms);
		return b;
	}
	
	public void setAutoCommit(boolean bAutoCommit)
	{
		m_DbConnectionParam.m_bAutoCommit = bAutoCommit;
	}
	
	public void setCloseCursorOnCommit(boolean bCloseCursorOnCommit)
	{
		if(m_bCanSetCloseCursorOnCommit)
			m_bCloseCursorOnCommit = bCloseCursorOnCommit;
	}
	
	protected boolean createDriver(String csDriverClass, String csUser, String csPassword, String csConnectionUrlOptionalParams)
	{
		m_DbConnectionParam.m_propertiesUserPassword = new Properties();
		m_DbConnectionParam.m_propertiesUserPassword.setProperty("user", csUser);
		m_DbConnectionParam.m_propertiesUserPassword.setProperty("password", csPassword); 
		m_DbConnectionParam.m_csConnectionUrlOptionalParams = csConnectionUrlOptionalParams;
	    try 
		{
	    	m_DbConnectionParam.m_driver = (java.sql.Driver)Class.forName(csDriverClass).newInstance();
//	    	if(m_DbConnectionParam.m_driver != null)
//	    		Log.logNormal("Created driver " + csDriverClass + " for user " + csUser);
	    } 
	    catch (Exception e) 
		{
	    	TechnicalException.throwException(TechnicalException.DB_ERROR_DRIVER_CREATION, "Could not initialize database driver '"+csDriverClass+"'.", e);
	    }
//	    if(m_DbConnectionParam.m_driver == null)
//	    	Log.logImportant("Could not create driver " + csDriverClass + " for user " + csUser);
	    return true;
	}
	
	protected boolean createDriver(String csDriverClass, String csUser, String csCryptedPassword, String csCryptKey, String csConnectionUrlOptionalParams)
	{
		m_DbConnectionParam.m_propertiesUserPassword = new Properties();
		m_DbConnectionParam.m_propertiesUserPassword.setProperty("user", csUser);
		m_DbConnectionParam.m_propertiesUserPassword.setProperty("CryptedPassword", csCryptedPassword);
		m_DbConnectionParam.m_propertiesUserPassword.setProperty("CryptKey", csCryptKey);
		m_DbConnectionParam.m_csConnectionUrlOptionalParams = csConnectionUrlOptionalParams;
	    try 
		{
	    	m_DbConnectionParam.m_driver = (java.sql.Driver)Class.forName(csDriverClass).newInstance();
//	    	if(m_DbConnectionParam.m_driver != null)
//	    		Log.logNormal("Created driver " + csDriverClass + " for user " + csUser);
	    } 
	    catch (Exception ex) 
		{
	    	String csParams = ListCoupleRender.set("Parameters: ").set("DriverClass", csDriverClass).set("User", csUser).set("CryptedPassword", csCryptedPassword).toString();
	    	TechnicalException.throwException(TechnicalException.DB_ERROR_DRIVER_CREATION, csParams, ex);
	    }
//	    if(m_DbConnectionParam.m_driver == null)
//	    	Log.logImportant("Could not create driver " + csDriverClass + " for user " + csUser);
	    return true;
	}

	public boolean create(String csDBUser, String csDBPassword, String csDBUrl, String csDBProvider, int nNbMaxConnections, int nTimeBeforeRemoveConnection_ms, int nMaxStatementLiveTime_ms)
	{
		m_DbConnectionParam.m_csUrl = csDBUrl;
		String csDriverClass = null;
		if(csDBProvider.equalsIgnoreCase("DB2"))
			csDriverClass = "com.ibm.db2.jcc.DB2Driver"; 
		else if(csDBProvider.equalsIgnoreCase("Oracle"))
			csDriverClass = "oracle.jdbc.driver.OracleDriver"; 
		else if(csDBProvider.equalsIgnoreCase("MySQL"))
			csDriverClass = "com.mysql.jdbc.Driver";
		else if(csDBProvider.equalsIgnoreCase("SqlServer"))
			csDriverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		else 
			csDriverClass = csDBProvider;
		
		boolean b = createDriver(csDriverClass, csDBUser, csDBPassword, "");
		if(b)
		{
			//Log.logNormal("Created DB driver " + csDriverClass + " for user " + csDBUser + " on url "+csDBUrl);
			m_SQLConnectionPool = new DbConnectionPool("UnknownPoolName", nNbMaxConnections, nTimeBeforeRemoveConnection_ms, nMaxStatementLiveTime_ms, 0);
		}
//		else
//			Log.logImportant("Could not create DB driver " + csDriverClass + " for user " + csDBUser + " on url "+csDBUrl);		
		
		return b;		
	}
	
	public void setEnvironment(String csEnvironment)
	{
		m_DbConnectionParam.setEnvironment(csEnvironment);
	}
	
	public void setValidationQuery(String csValidationQuery)
	{
		m_csValidationQuery = csValidationQuery;
	}
	
	public int getNbUnusedConnections()
	{
		if(m_SQLConnectionPool == null)
			return 0;
		return m_SQLConnectionPool.getNbUnusedConnections();
	}
	
	public int getNbRunningConnections()
	{
		if(m_SQLConnectionPool == null)
			return 0;
		return m_SQLConnectionPool.getNbRunningConnections();
	}	
	
	public void showHideRunningConnections(boolean bShowRunningCon)
	{
		if(m_SQLConnectionPool != null)
			m_SQLConnectionPool.showHideRunningConnections(bShowRunningCon);
	}
	
	public void dumpConnections(StringBuilder sbText)
	{
		if(m_SQLConnectionPool != null)
			m_SQLConnectionPool.dumpConnections(sbText);
	}
	
	public int getNbCachedStatementsForAccessor()
	{
		if(m_SQLConnectionPool == null)
			return 0;
		return m_SQLConnectionPool.getNbCachedStatementsForAccessor();
	}
	
	public int getNbAllocConnnections()
	{
		if(m_SQLConnectionPool == null)
			return 0;
		return m_SQLConnectionPool.getNbAllocConnnections();
	}
	
	public int getNbMaxConnection()
	{
		if(m_SQLConnectionPool == null)
			return 0;
		return m_SQLConnectionPool.getNbMaxConnection();
	}
	
	
	
	protected int m_maxWaitTime_s = 60 ;
	protected String m_csValidationQuery = "" ;
	private boolean m_bCloseCursorOnCommit = false;
	private boolean m_bCanSetCloseCursorOnCommit = false;	// Oracle cannot set CloseCursorOnCommit, but DB2 can do it  

	/**
	 * @return the m_csPropertyName
	 */
	public String getPropertyPrefix()
	{
		return m_csPropertyPrefix;
	}

	/**
	 * @param propertyName the m_csPropertyName to set
	 */
	public void setPropertyPrefix(String csPropertyPrefix)
	{
		m_csPropertyPrefix = csPropertyPrefix;
	}
}
