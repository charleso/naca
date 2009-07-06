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

import java.sql.SQLException;

import jlib.exception.TechnicalException;


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: SQLClauseSPParam.java,v 1.1 2007/10/16 09:47:08 u930di Exp $
 */
public abstract class SQLClauseSPParam
{
	private SQLClauseSPParamWay m_wayInOut = null;
	
	protected SQLClauseSPParam(SQLClauseSPParamWay wayInOut)
	{
		m_wayInOut = wayInOut;		
	}
	
	void registerIntoCallableStatement(int nParamId, DbPreparedCallableStatement callableStatement, SQLClauseSPParamDesc paramDesc)
		throws TechnicalException
	{
		nParamId++;	// 1 based
		String csError = null;
		try
		{
			if(m_wayInOut == SQLClauseSPParamWay.In)
			{
				if(paramDesc != null && paramDesc.isColIn())
				{
					csError = TechnicalException.STORED_PROC_CALL_IN_PARAM_SET_ERROR;
					setInValueWithException(nParamId, callableStatement);
					return ;
				}
			}

			if(m_wayInOut == SQLClauseSPParamWay.Out)
			{
				if(paramDesc != null && paramDesc.isColOut())
				{
					csError = TechnicalException.STORED_PROC_CALL_OUT_PARAM_SET_ERROR;
					registerOutParameterWithException(nParamId, callableStatement);
					return ;
				}	
			}
			
			if(m_wayInOut == SQLClauseSPParamWay.InOut)
			{
				if(paramDesc != null && paramDesc.isColInOut())
				{
					csError = TechnicalException.STORED_PROC_CALL_INOUT_PARAM_SET_ERROR;
					registerOutParameterWithException(nParamId, callableStatement);
					setInValueWithException(nParamId, callableStatement);
					return ;
				}
			}
			
			TechnicalException.throwException(TechnicalException.STORED_PROC_CALL_INOUT_PARAM_WAY_NOT_MATCHING_DEF, "ParameterId (1based): "+nParamId);
			
		}
		catch (SQLException e)
		{
			TechnicalException.throwException(csError, "ParameterId (1based): "+nParamId, e);
		}			
	}
	
	public String toString()
	{
		return m_wayInOut.toString();
	}
	
	public String toString(SQLClauseSPParamDesc paramDesc)
	{
		if(paramDesc != null)
			return toString() + " "+ paramDesc.toString();
		return toString(); 
	}
	
	
	
	protected abstract void setInValueWithException(int nParamId, DbPreparedCallableStatement stmt) throws SQLException;
	protected abstract void registerOutParameterWithException(int nParamId, DbPreparedCallableStatement stmt) throws SQLException;
	protected abstract void retrieveOutValuesWithException(int nParamId, DbPreparedCallableStatement stmt) throws SQLException;
}
