/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
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
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
// Originated as StoredProcParamDesc from nacaRT
public class SQLClauseSPParamDesc extends StoredProcParamDescBase
{
//	public void retrieveOutValues(int nParamId, PreparedCallableStatement callableStatement, CSQLStatus sqlStatus)
//	{
//		nParamId++;	// 1 based
//		if(m_sColType == DatabaseMetaData.procedureColumnOut || m_sColType == DatabaseMetaData.procedureColumnInOut)
//		{
//			try
//			{
//				String csOutLang = callableStatement.getOutValueString(nParamId);				
//				if(m_varInOut != null)
//					m_varInOut.set(csOutLang);
//			}
//			catch (SQLException e)
//			{
//				String csState = e.getSQLState();
//				String csReason = e.getMessage();
//				Log.logImportant("Catched SQLException from stored procedure retrieveOutValues: "+csReason + " State="+csState);
//				sqlStatus.setSQLCode("StoredProc", e.getErrorCode(), csReason, csState);
//				
//				sqlStatus.setSQLCode(e);
//			}
//			catch (Exception e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}	
	
	public boolean fillInValue(int nParamId, DbPreparedCallableStatement callableStatement)
	{
//		if(m_varInOut != null)
//		{			
//			BaseDbColDefinition def = m_colDescriptionInfo.makeDbColDefinition();
//			return def.fillCallableStatementParam(nParamId, this, callableStatement);
//		}
		return false;
	}
	
	public String getInValueAsString()
	{
		return null;
	}
	
	public double getInValueAsDouble()
	{
		return 0.0;
	}
	
	public int getInValueAsInt()
	{
		return 0;
	}
	
	public short getInValueAsShort()
	{
		return (short)0;
	}

}
