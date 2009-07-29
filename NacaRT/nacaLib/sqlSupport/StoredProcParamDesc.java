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
package nacaLib.sqlSupport;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import nacaLib.varEx.Var;
import jlib.log.Log;
import jlib.sql.BaseDbColDefinition;
import jlib.sql.DbPreparedCallableStatement;
import jlib.sql.StoredProcParamDescBase;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class StoredProcParamDesc extends StoredProcParamDescBase
{
	private Var m_varInOut = null;
		
	void setVar(Var var)
	{
		m_varInOut = var;
	}
	
	public void retrieveOutValues(int nParamId, PreparedCallableStatement callableStatement, CSQLStatus sqlStatus)
	{
		nParamId++;	// 1 based
		if(m_sColType == DatabaseMetaData.procedureColumnOut || m_sColType == DatabaseMetaData.procedureColumnInOut)
		{
			try
			{
				String csOutLang = callableStatement.getOutValueString(nParamId);				
				if(m_varInOut != null)
					m_varInOut.set(csOutLang);
			} 			
			catch (SQLException e)
			{
				String csState = e.getSQLState();
				String csReason = e.getMessage();
				Log.logImportant("Catched SQLException from stored procedure retrieveOutValues: "+csReason + " State="+csState);
				sqlStatus.setSQLCode("StoredProc", e.getErrorCode(), csReason, csState);
				
				sqlStatus.setSQLCode(e);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
	
	public boolean fillInValue(int nParamId, DbPreparedCallableStatement callableStatement)
	{
		if(m_varInOut != null)
		{			
			BaseDbColDefinition def = m_colDescriptionInfo.makeDbColDefinition();
			return def.fillCallableStatementParam(nParamId, this, callableStatement);
		}
		return false;
	}
	
	public String getInValueAsString()
	{
		String cs = m_varInOut.getString();
		return cs;
	}
	
	public double getInValueAsDouble()
	{
		double d = m_varInOut.getDouble();
		return d;
	}
	
	public int getInValueAsInt()
	{
		int n = m_varInOut.getInt();
		return n;
	}
	
	public short getInValueAsShort()
	{
		int n = m_varInOut.getInt();
		return (short)n;
	}

}
