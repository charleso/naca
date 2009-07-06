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

import jlib.Helpers.PropertyLoader;
import jlib.exception.TechnicalException;
import jlib.misc.NumberParser;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @vers
 * ion $Id: DbConnectionManagerContext.java,v 1.9 2008/07/09 06:40:18 u930di Exp $
 */
public class DbConnectionManagerContext
{
	private String m_csDBProvider = null;
	private String m_csDBUrl = null;
	private String m_csDBUser = null;
	private String m_csDBPassword = null;
	private String m_csEnvironment = null;
	private int m_nNbMaxConnections = 0;
	private int m_nTimeBeforeRemoveConnection_ms = 0;
	private int m_nMaxStatementLiveTime_ms = 0;
	private boolean m_bCreated = false;	
	
	private DbConnectionManagerBase m_dbConnectionManager = null;
	
	public DbConnectionManagerContext()
	{
	}
	
	public boolean create(String csPropertyPrefix)
		throws TechnicalException
	{
		if(!csPropertyPrefix.endsWith("."))
			csPropertyPrefix += ".";

		PropertyLoader pl = new PropertyLoader();
		m_csDBProvider = pl.getProperty(csPropertyPrefix + "driver");
		m_csDBUrl = pl.getProperty(csPropertyPrefix + "connectionString");
		m_csDBUser = pl.getProperty(csPropertyPrefix + "user");
		m_csDBPassword = pl.getProperty(csPropertyPrefix + "password");
		m_csEnvironment = pl.getProperty(csPropertyPrefix + "environment", "");
					
		String cs = pl.getProperty(csPropertyPrefix + "NbMaxConnections", "2");
		m_nNbMaxConnections = NumberParser.getAsInt(cs);
		
		cs = pl.getProperty(csPropertyPrefix + "TimeBeforeRemoveConnection_ms", "600000");	// 10 minutes by defaut
		m_nTimeBeforeRemoveConnection_ms = NumberParser.getAsInt(cs);
		
		cs = pl.getProperty(csPropertyPrefix + "MaxStatementLiveTime_ms", "600000");	// 10 minutes by defaut
		m_nMaxStatementLiveTime_ms = NumberParser.getAsInt(cs);
		
		m_bCreated = doCreateConnection(csPropertyPrefix);

		return m_bCreated;
	}
	
	public boolean create(String csDBProvider, String csUrl, String csUser, String csPassword, String csEnvironment)
		throws TechnicalException
	{
		m_csDBProvider = csDBProvider;
		m_csDBUrl = csUrl;
		m_csDBUser = csUser;
		m_csDBPassword = csPassword;
		m_csEnvironment = csEnvironment;
		
		m_nNbMaxConnections = 2;		// Resonable default values; Should be parametrized ???
		m_nTimeBeforeRemoveConnection_ms = 10 * 60 * 1000; // 10 minutes
		m_nMaxStatementLiveTime_ms = 10 * 60 * 1000; 	// 10 minutes too
		
		m_bCreated = doCreateConnection("");
		return m_bCreated;
	}

	private boolean doCreateConnection(String csPropertyPrefix)
		throws TechnicalException
	{		
		m_dbConnectionManager = new DbConnectionManager();
		m_dbConnectionManager.setPropertyPrefix(csPropertyPrefix);
		try
		{
			m_bCreated = m_dbConnectionManager.create(m_csDBUser, m_csDBPassword, m_csDBUrl, m_csDBProvider, m_nNbMaxConnections, m_nTimeBeforeRemoveConnection_ms, m_nMaxStatementLiveTime_ms);
			if(m_bCreated)
				m_dbConnectionManager.setEnvironment(m_csEnvironment);
			return m_bCreated;
		}
		catch (TechnicalException e) {
			throw e;
		}
		catch (RuntimeException e)
		{
			m_bCreated = false;
			TechnicalException.throwException(TechnicalException.DB_ERROR_CONNECTION_CREATION, "Could not create DB connection", e);
		}
		return m_bCreated;
	}
	
	public boolean isOracle()
	{
		if(m_csDBProvider.equalsIgnoreCase("Oracle"))
			return true;
		return false;
	}
	
	public DbConnectionBase getConnection()
	{
		if(m_dbConnectionManager == null)
			return null;
		
		try
		{
			DbConnectionBase connection = m_dbConnectionManager.getConnection(true);
			return connection;
		}
		catch (DbConnectionException e)
		{
			TechnicalException.throwException(TechnicalException.DB_ERROR_CONNECTION_CREATION, "Could not create DB connection (getConnection())", e);
		}
		return null;
	}
	
	public boolean isCreated()
	{
		return m_bCreated;
	}
	
	/**
	 * @function getNbUnusedConnections
	 * @return Number of currently unused connection
	 */
	public int getNbUnusedConnections()
	{
		if(m_dbConnectionManager == null)
			return 0;
		return m_dbConnectionManager.getNbUnusedConnections();		
	}
	
	public int getNbRunningConnections()
	{
		if(m_dbConnectionManager == null)
			return 0;
		return m_dbConnectionManager.getNbRunningConnections();		
	}
	
	public void showHideRunningConnections(boolean bShowRunningCon)
	{
		if(m_dbConnectionManager != null)
			m_dbConnectionManager.showHideRunningConnections(bShowRunningCon);
	}
	
	public void dumpConnections(StringBuilder sbText)
	{
		if(m_dbConnectionManager != null)
			m_dbConnectionManager.dumpConnections(sbText);
	}
	
	public int getNbAllocConnnections()
	{
		if(m_dbConnectionManager == null)
			return 0;
		return m_dbConnectionManager.getNbAllocConnnections();		
	}
	
	public int getNbMaxConnection()
	{
		if(m_dbConnectionManager == null)
			return 0;
		return m_dbConnectionManager.getNbMaxConnection();
	}
	
	public int getNbCachedStatementsForAccessor()
	{
		if(m_dbConnectionManager == null)
			return 0;
		return m_dbConnectionManager.getNbCachedStatementsForAccessor();
	}
}
