/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.sqlSupport;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import jlib.log.Log;
import jlib.sql.DbConnectionBase;
import jlib.sql.DbPreparedCallableStatement;
import jlib.sql.SQLTypeOperation;
import jlib.sql.StoredProcParamDescBase;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.program.Paragraph;
import nacaLib.program.Section;
import nacaLib.varEx.Var;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: SQLCall.java,v 1.4 2007/01/09 15:01:07 u930di Exp $
 */
public class SQLCall 
{
	private String m_csStoredProcName = null;
	private BaseProgramManager m_programManager = null;
	private StoredProcParams m_arrStoredProcParams = null;
	private DbConnectionBase m_SQLConnection = null;
	private int m_nNbParamToProvide = -1; 	// Number of para to provide to the stored proc
	private int m_nNbParamProvided = 0;	// Number of parameters provided by application
	//private ArrayList<Var> m_arrInOutParam = null;
	private PreparedCallableStatement m_preparedCallableStatement = null;
	CSQLStatus m_sqlStatus = null;
	private SQLErrorManager m_errorManager = null;
		
	public SQLCall(BaseProgramManager programManager, String csStoredProcName)
	{
		m_programManager = programManager;
		m_csStoredProcName = csStoredProcName;
		m_errorManager = new SQLErrorManager();
		create();
	}
	
	private void create()
	{
		m_sqlStatus = new CSQLStatus();
		
		// Determine number and way of proc params
		StoredProcSupport sp = new StoredProcSupport();
		
		BaseEnvironment env = m_programManager.getEnv();
		m_SQLConnection = env.getSQLConnection();
		
		if(m_SQLConnection != null)
		{
			m_arrStoredProcParams = sp.getStoredProcedureParamsList(m_SQLConnection, m_csStoredProcName);
			if(m_arrStoredProcParams != null)
				m_nNbParamToProvide = m_arrStoredProcParams.getNbParamToProvide();
			manageOperationEnding();
		}
	}
	
	public SQLCall param(int nParamId, Var var)
	{
		nParamId--;	// 0 based
		if(m_arrStoredProcParams != null && nParamId < m_arrStoredProcParams.getNbParamToProvide())
		{
			StoredProcParamDesc storedProcParamDesc = m_arrStoredProcParams.get(nParamId);
			storedProcParamDesc.setVar(var);
		}
		m_nNbParamProvided++;
		
		manageOperationEnding();
		return this;
	}

	// Fake methods
	public SQLCall onErrorGoto(Paragraph paragraphSQGErrorGoto)
	{
		m_errorManager.manageOnErrorGoto(paragraphSQGErrorGoto, m_sqlStatus);
		return this;
	}

	public SQLCall onErrorGoto(Section section)
	{
		m_errorManager.manageOnErrorGoto(section, m_sqlStatus);
		return this;	
	}

	public SQLCall onErrorContinue()
	{
		m_errorManager.manageOnErrorContinue(m_sqlStatus);
		return this;
	}

	public SQLCall onWarningGoto(Paragraph paragraphSQGErrorGoto)
	{
		// TODO
		return this;
	}

	public SQLCall onWarningGoto(Section section)
	{
		// TODO
		return this;
	}

	public SQLCall onWarningContinue()
	{
		// TODO
		return this;
	}


	private void manageOperationEnding()
	{
		if (m_SQLConnection != null)
		{
			if (m_nNbParamToProvide == m_nNbParamProvided) // All paraqm have been provided
			{
				if(prepareCallableStatement())
				{
					execute();
					retrieveOutValues();
					// read out params and write value in destination vars
					close();
				}
			}
		}
	}

	private boolean prepareCallableStatement()
	{
		m_preparedCallableStatement = new PreparedCallableStatement(null);
		boolean bPrepared = m_SQLConnection.prepareCallableStatement(m_preparedCallableStatement, m_csStoredProcName, m_nNbParamToProvide);
		if(bPrepared)
		{
			return m_arrStoredProcParams.registerInOutParameters(m_preparedCallableStatement);
		}
		return false;
	}
	
	private void retrieveOutValues()
	{
		if(m_preparedCallableStatement != null)
		{
			m_arrStoredProcParams.retrieveOutValues(m_preparedCallableStatement, m_sqlStatus);
		}
	}
	
	private void execute()
	{
		m_sqlStatus.reset();
		try
		{
			if(m_preparedCallableStatement != null)
			{
				boolean b = m_preparedCallableStatement.execute();
			}
		}
		catch(SQLException e)
		{
			//String cs = e.getCause();
			String csState = e.getSQLState();
			String csReason = e.getMessage();
			Log.logImportant("Catched SQLException from stored procedure: "+csReason + " State="+csState);
			String csSPName = "StoredProc:" + m_csStoredProcName;
			m_sqlStatus.setSQLCode(csSPName, e.getErrorCode(), csReason, csState);
		}
	}
	
	private boolean close()
	{
		if(m_preparedCallableStatement != null)
			return m_preparedCallableStatement.close();
		return false;
	}
}
