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

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import jlib.exception.TechnicalException;
import jlib.log.Log;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: SQLClauseSPCallBase.java,v 1.1 2007/10/16 09:47:08 u930di Exp $
 */
public class SQLClauseSPCallBase
{
	private boolean m_bCheckParams = false;
	private String m_csName = null;
	private ArrayList<SQLClauseSPParam> m_arrParams = new ArrayList<SQLClauseSPParam>();
	private DbPreparedCallableStatement m_preparedCallableStatement = null; 
	
	protected SQLClauseSPParamsDesc getStoredProcedureParamsList(DbConnectionBase connection)
	{
		SQLClauseSPSupport support = new SQLClauseSPSupport();
		SQLClauseSPParamsDesc paramsDesc = support.getStoredProcedureParamsList(connection, m_csName);
		return paramsDesc;		
	}
	
	protected SQLClauseSPCallBase(String csName, boolean bCheckParams)
	{
		m_csName = csName;
		m_bCheckParams = bCheckParams;
	}
	
	protected void addParam(SQLClauseSPParam param)
	{
		m_arrParams.add(param);
	}
	
	protected int prepareAndCallWithException(DbConnectionBase connection)
		throws TechnicalException
	{
		m_preparedCallableStatement = prepareWithException(connection);
		if(m_preparedCallableStatement != null)
		{
			executeWithException(connection);
			retrieveOutValuesWithException(connection);
			closeWithException(connection);
		}
		return 0;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder("StoredProc: "+m_csName+"\n");
		int nNbParams = m_arrParams.size();
		for(int n=0; n<nNbParams; n++)
		{
			SQLClauseSPParam param = m_arrParams.get(n);
			int n1Based = n+1;
			sb.append("Parameter " + n1Based + ": " + param.toString()+"\n");
		}
		return sb.toString();		
	}
	
	public String dump(DbConnectionBase connection)
	{
		int nNbParams = m_arrParams.size();
		int nMin = nNbParams; 
		
		SQLClauseSPParamsDesc paramsDesc = getStoredProcedureParamsList(connection);
				
		StringBuilder sb = new StringBuilder("StoredProc: "+m_csName+"\n");
		if(paramsDesc == null)
			sb.append("No Description found in DB for the Stored proc !!!\n");
		else
		{
			nMin = Math.min(nNbParams, paramsDesc.getNbParamToProvide());
			if(nNbParams != paramsDesc.getNbParamToProvide())
				sb.append("Number of parameters defined in Stored proc is different form the number of parameters provided by caller\n");
		}
		
		for(int n=0; n<nMin; n++)
		{
			SQLClauseSPParam param = m_arrParams.get(n);
			int n1Based = n+1;
			sb.append("Parameter " + n1Based + ": " + param.toString());
			if(paramsDesc != null)
			{
				SQLClauseSPParamDesc paramDesc = paramsDesc.get(n);
				sb.append(paramDesc.toString() + "\n");
			}
		}
		return sb.toString();		
	}
	
	private DbPreparedCallableStatement prepareWithException(DbConnectionBase connection)
		throws TechnicalException
	{
		String csSql = "CALL " + m_csName;
		int nNbParams = m_arrParams.size();
		int n=0;
		for(; n<nNbParams; n++)
		{
			if(n == 0)
				csSql += " (?";
			else
				csSql += ",?";
		}
		if(n != 0)
			csSql += ")";
		
		SQLClauseSPParamsDesc paramsDesc = null; 
		if(m_bCheckParams)
			paramsDesc = getStoredProcedureParamsList(connection);
		
		try
		{
			CallableStatement call = connection.m_dbConnection.prepareCall(csSql);
			if(call != null)
			{
				DbPreparedCallableStatement preparedCallableStatement = new DbPreparedCallableStatement(call); 
				registerInOutParameters(preparedCallableStatement, paramsDesc);
				return preparedCallableStatement;
			}
		}
		catch (SQLException e)
		{
			TechnicalException.throwException(TechnicalException.STORED_PROC_CALL_PREPARE_ERROR, toString(), e);
		}
		return null;
	}
	
	
	private void registerInOutParameters(DbPreparedCallableStatement callableStatement, SQLClauseSPParamsDesc paramsDesc)
		throws TechnicalException
	{		
		SQLClauseSPParamDesc paramDesc = null;
		
		if(callableStatement != null)
		{
			int nNbParams = m_arrParams.size();
			int nNbParamDesc = nNbParams; 
			if(paramsDesc != null)	// If we check the parameters; check their number
				nNbParamDesc = paramsDesc.getNbParamToProvide();
			if(nNbParams == nNbParamDesc)	// Correct number of parameters
			{				
				for(int n=0; n<nNbParams; n++)
				{
					SQLClauseSPParam param = m_arrParams.get(n);
					if(paramsDesc != null)			// We have parameters to check
						paramDesc = paramsDesc.get(n);
					try
					{
						param.registerIntoCallableStatement(n, callableStatement, paramDesc);
					}
					catch (TechnicalException e)
					{
						String cs = "; Clause: "+toString();	// Append the clause details
						e.appendMessage(cs);
						throw e;
					}
				}
			}
		}
	}

	private void executeWithException(DbConnectionBase connection)
	{
		try
		{
			m_preparedCallableStatement.executeWithException();
		}
		catch(SQLException e)
		{
			TechnicalException.throwException(TechnicalException.STORED_PROC_CALL_EXECUTE_ERROR, dump(connection), e);
		}
	}
	
	private void retrieveOutValuesWithException(DbConnectionBase connection)
		throws TechnicalException
	{
		int n=0;
		try
		{
			for(; n<m_arrParams.size(); n++)	// 1 based
			{
				SQLClauseSPParam param = m_arrParams.get(n);
				int n1Based = n+1;  
				param.retrieveOutValuesWithException(n1Based, m_preparedCallableStatement);
			}
		}
		catch (SQLException e)
		{
			int n1Based = n+1;
			TechnicalException.throwException(TechnicalException.STORED_PROC_CALL_RETRIEVE_OUT_VALUES_ERROR, "Parameter:" + n1Based + "; Clause=" + dump(connection), e);
		}
	}
	
	private void closeWithException(DbConnectionBase connection)
		throws TechnicalException
	{
		try
		{
			m_preparedCallableStatement.closeWithException();
		}
		catch (SQLException e)
		{
			TechnicalException.throwException(TechnicalException.STORED_PROC_CALL_RETRIEVE_OUT_VALUES_ERROR, dump(connection), e);
		}		
	}
}
