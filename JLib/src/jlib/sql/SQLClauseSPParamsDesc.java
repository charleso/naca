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

/**
 * 
 */

import java.sql.ResultSet;
import java.util.ArrayList;

import jlib.sql.DbPreparedCallableStatement;
import jlib.sql.StoredProcParamDescBase;


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: SQLClauseSPParamsDesc.java,v 1.1 2007/10/16 09:47:08 u930di Exp $
 */

// Originated as StoredProcParams from nacaRT

public class SQLClauseSPParamsDesc
{
	private ArrayList<SQLClauseSPParamDesc> m_arrParamDesc = null;
	
	public SQLClauseSPParamsDesc()
	{
		m_arrParamDesc = new ArrayList<SQLClauseSPParamDesc>();
	}
	
	public void addAParam(ResultSet rsParams)
	{
		SQLClauseSPParamDesc param = new SQLClauseSPParamDesc();
		if(param.fill(rsParams))
			m_arrParamDesc.add(param);
	}
	
	public int getNbParamToProvide()
	{
		return m_arrParamDesc.size();
	}
	
	public SQLClauseSPParamDesc get(int nParamId)
	{
		return m_arrParamDesc.get(nParamId);
	}
	
	public boolean registerInOutParameters(DbPreparedCallableStatement callableStatement)
	{
		boolean b = true;
		if(callableStatement != null)
		{
			for(int n=0; n<getNbParamToProvide() && b; n++)
			{
				StoredProcParamDescBase paramDesc = get(n);
				b = paramDesc.registerIntoCallableStatement(n, callableStatement);
			}
		}
		return b;
	}
	
//	void retrieveOutValues(PreparedCallableStatement preparedCallableStatement)
//	{
//		if(preparedCallableStatement != null)
//		{
//			for(int n=0; n<m_arrParamDesc.size(); n++)
//			{
//				SQLClauseSPParamDesc paramDesc = m_arrParamDesc.get(n);
//				paramDesc.retrieveOutValues(n, preparedCallableStatement, sqlStatus);
//			}
//		}
//	}
}
