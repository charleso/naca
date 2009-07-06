/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.spServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jlib.log.Log;
import jlib.misc.ClassHelper;
import jlib.misc.StringUtil;
import nacaLib.accounting.CriteriaEndRunMain;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.calledPrgSupport.CalledProgramParamSupportByPosition;
import nacaLib.exceptions.AbortSessionException;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: StoredProcedure.java,v 1.29 2008/03/26 07:46:05 u930bm Exp $
 */
public class StoredProcedure extends CalledProgramParamSupportByPosition
{
	
	private String m_csProgramName = null;
	private Class m_clsProgram = null;
	private boolean m_bConnectionPackage = false;
	
	public StoredProcedure(String csProgramName)
	{		
		m_csProgramName = csProgramName;
		
	}
	
	public StoredProcedure(Class clsProgram)
	{
		m_clsProgram = clsProgram;
		m_csProgramName = ClassHelper.getLocalName(m_clsProgram);
	}

	private Connection getSpConnection(boolean bContainerSimulated) throws SQLException
	{
		if(!bContainerSimulated)	// Executed in a normal SP container (in UDB)
		{
			Connection connection = DriverManager.getConnection("jdbc:default:connection");	// get the caller's connection to the database
			return connection;
		}
		// Allocate a new connection
		return null;
	}
	
	public boolean executeContainerSimulated() 
	{
		try
		{
			return doExecute(true);
		}
		catch(SQLException e)
		{
			return false;
		}
	}
	
	public boolean execute() throws SQLException
	{	
		return doExecute(false);
	}
	
	private boolean doExecute(boolean bContainerSimulated) throws SQLException
	{
		Connection connection = getSpConnection(bContainerSimulated);
		if(connection == null)
		{
			return false;
		}
		BaseEnvironment env = null;
		
		try
		{
			// We must have a table "NacaRTSP" with 1 record of 1 column "CONFIGFILE"; It's value is the path and file name of the config file
			String csPathFileNameConfig = null;
			String csCurrentSqlid = "";
			PreparedStatement statement = connection.prepareStatement("Select CONFIGFILE, current sqlid FROM naca.NacaRTSP");
			if (statement != null)
			{
				ResultSet rs = null;
				boolean b = statement.execute();
				if (b)
					rs = statement.getResultSet();
				if (rs != null)
				{
					if(rs.next())
					{
						csPathFileNameConfig = rs.getString(1).trim();
						csCurrentSqlid = rs.getString(2).trim();
					}
					rs.close();
				}
				statement.close();
			}
			if(StringUtil.isEmpty(csPathFileNameConfig))
			{
				SQLException sqlException = new SQLException("Could not find environment variable NacaRTSP value: Cannot contine");
				throw sqlException;
			}
		
			SpServerResourceManager spServerResourceManager = SpServerResourceManagerFactory.GetInstance(csPathFileNameConfig);			
			String csSpDbEnvironment = spServerResourceManager.getSpDbEnvironment();
			SpServerSession session = new SpServerSession(connection, spServerResourceManager);
			SpServerProgramLoader loader = SpServerProgramLoader.GetProgramLoaderInstance();
			env = loader.GetEnvironment(session, m_csProgramName, null);
			
			boolean bUseStatementCache = BaseResourceManager.getUseStatementCache();
			env.fillEnvConnectionWithAllocatedConnection(connection, "SPConnection", csSpDbEnvironment, bUseStatementCache);
			
			String csSpDbPackage = spServerResourceManager.getSpDbPackage();
			setConnectionPackage(connection, csSpDbPackage);
			
			Log.logNormal("Start stored procedure:"+m_csProgramName + " for clsid:" + csCurrentSqlid);
			env.setInitialConnectDb(false);
			env.startRunTransaction();
			loader.runTopProgram(env, m_arrPublicArgs);			
			env.endRunTransaction(CriteriaEndRunMain.Normal);
			Log.logNormal("Stop stored procedure:"+m_csProgramName);
			
			resetConnectionPackage(connection);
			
			return true;
		}
		catch (AbortSessionException e)
		{
			resetConnectionPackage(connection);
			String csMessage = e.getReason();
			SQLException sqlException = new SQLException(csMessage);
			throw sqlException;
		}
		catch (Exception e)
		{
			resetConnectionPackage(connection);
			String csMessage = e.getMessage();
			SQLException sqlException = new SQLException(csMessage);
			throw sqlException;
		}
	}
	
	private void setConnectionPackage(Connection spConnection, String csSpDbPackage)
	{
		if (csSpDbPackage.equals("")) return;
		if (executeConnectionPackage(spConnection, csSpDbPackage))
			m_bConnectionPackage = true;
	}
	
	private void resetConnectionPackage(Connection spConnection)
	{
		if (!m_bConnectionPackage) return;		
		executeConnectionPackage(spConnection, "NULLID");
	}
	
	private boolean executeConnectionPackage(Connection spConnection, String csSpDbPackage)
	{
		try
		{
			COM.ibm.db2.jdbc.app.DB2Connection Db2connection = (COM.ibm.db2.jdbc.app.DB2Connection)spConnection;
			Db2connection.setConnectOption(1276, csSpDbPackage); // SQL_ATTR_CURRENT_PACKAGE_SET
			Log.logNormal("Change Package:" + csSpDbPackage);
		}
		catch (Exception ex)
		{
			Log.logNormal("Problem with change Package:" + csSpDbPackage + " ex:" + ex.getMessage());
			return false;
		}
		return true;
	}
}
