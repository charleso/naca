/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.dbUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.bdb.BtreePooledThreadWriterFactory;
import nacaLib.sqlSupport.SQLConnectionManager;
import jlib.log.Log;
import jlib.misc.DBIOAccounting;
import jlib.misc.DBIOAccountingType;
import jlib.misc.StringUtil;
import jlib.sql.DbConnectionBase;
import jlib.sql.DbConnectionException;
import jlib.sql.DbConnectionPool;
import jlib.sql.DbPreparedStatement;
import jlib.threads.PoolOfThreads;
import jlib.threads.SimpleThreadPool;
import jlib.xml.Tag;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class DbTransferDesc
{
	private int m_nBatchSize = 0;
	private int m_nCommitEveryBatch = 0;
	private int m_nThreadsQuantity = 0;
	private String m_csDefinitionTable;
	private SQLConnectionManager m_connectionManager = null;
	private ArrayList<TableToTransfer> m_arrTableToTransfer = new ArrayList<TableToTransfer>();
	private PoolOfThreads m_threadsPool = null;
	private boolean m_bTransferGlobalStatus = true; 
	
	boolean load(Tag tagDbTransfer)
	{
		boolean b = true;
		
		if(tagDbTransfer != null)
		{
			Tag tagParameters = tagDbTransfer.getChild("Parameters");
			if(tagParameters != null)
			{
				m_nBatchSize = tagParameters.getValAsInt("BatchSize");
				m_nCommitEveryBatch = tagParameters.getValAsInt("CommitEveryBatch");
				m_nThreadsQuantity = tagParameters.getValAsInt("ThreadsQuantity");
				m_csDefinitionTable = tagParameters.getVal("DefinitionTable");
			}
			else b = false;
			
			Tag tagSQLConfig = tagDbTransfer.getChild("SQLConfig");
			if(tagSQLConfig != null)
			{
				m_connectionManager = new SQLConnectionManager();
				DbConnectionPool dbConnectionPool = m_connectionManager.init("", tagSQLConfig);
				BaseResourceManager.addDbConnectionPool(dbConnectionPool);
			}
			else b = false;
		}
		else
		{
			Log.logCritical("No Accounting tag in .cfg file: Accouting is disabled");
			b = false;
		}
		return b;
	}
	
	boolean getTablesList(BaseEnvironment env)
	{
		String csUpdateClause = getUpdateStatementString();
		
		String csClause = "Select TName, Replace, LastWrite, NbRead, NbWrite, SQLError From " + m_csDefinitionTable + " order by TName asc";
		DbConnectionBase dbConnectionSource = env.getSQLConnection();
		DBIOAccounting.startDBIO(DBIOAccountingType.Prepare);
		DbPreparedStatement st = dbConnectionSource.prepareStatement(csClause, 0, false);
		DBIOAccounting.endDBIO();
		if(st != null)
		{
			ResultSet resultSet = st.executeSelect();
			if(resultSet != null)
			{
				try
				{
					while(resultSet.next())
					{
						String csTableName = resultSet.getString(1);
						String csReplace = resultSet.getString(2);
						String cs = resultSet.getString(3);
						cs = resultSet.getString(4);
						cs = resultSet.getString(5);
						cs = resultSet.getString(6);
						
						TableToTransfer tableToTransfer = new TableToTransfer(csTableName, csReplace, csUpdateClause);
						m_arrTableToTransfer.add(tableToTransfer);
					}
					st.close();
					return true;
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	private String getUpdateStatementString()
	{
		StringBuilder sbUpdate = new StringBuilder("update ");
		sbUpdate.append(m_csDefinitionTable);
		sbUpdate.append(" set LASTWRITE=?, NBREAD=?, NBWRITE=?, SQLERROR=? Where TNAME=");
		String csUpdate = sbUpdate.toString();
		return csUpdate;
	}
	
	DbConnectionBase getNewDestinationConnection()
	{
		if(m_connectionManager != null)
		{
			try
			{
				DbConnectionBase dbConnection = m_connectionManager.getConnection("DBTR", false);	// get a new db connection
				return dbConnection;
			}
			catch (DbConnectionException e)
			{
				Log.logCritical("Could not get DB connection for accounting !");
			}
		}
		return null;
	}

	private DbConnectionBase getNewSourceConnection(BaseEnvironment env)
	{
		DbConnectionBase dbConnectionSource = env.getNewSQLConnection();
		return dbConnectionSource;
	}
		
	boolean doTransfers(BaseEnvironment env)
	{		
		int nNbTables = m_arrTableToTransfer.size();
		
		PooledThreadDbTransferFactory pooledThreadDbTransferFactory = new PooledThreadDbTransferFactory(this, env);
		
		m_threadsPool = new PoolOfThreads(pooledThreadDbTransferFactory, m_nThreadsQuantity, nNbTables);
		m_threadsPool.startAllThreads();
		for(int n=0; n<nNbTables; n++)
		{
			TableToTransfer tableToTransfer = m_arrTableToTransfer.get(n);
			m_threadsPool.enqueue(tableToTransfer);
		}
		
		m_threadsPool.stop();
		return m_bTransferGlobalStatus;
	}	
	
	synchronized void setTransferGlobalFailure()
	{
		m_bTransferGlobalStatus = false;;
	}
	
	int getCommitEveryBatch()
	{
		return m_nCommitEveryBatch;
	}
	
	int getBatchSize()
	{
		return m_nBatchSize;
	}
}

