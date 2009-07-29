/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.sqlSupport;

import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.SQLException;

import jlib.log.Log;
import jlib.misc.CurrentDateInfo;
import jlib.sql.LogSQLException;
import nacaLib.basePrgEnv.BaseResourceManager;

public class CSQLPreparedStatementDB2 extends CSQLPreparedStatement
{
	CSQLPreparedStatementDB2()
	{
		super();		
	}
	
	public void setVarParamValue(SQL sql, String csSharpName, int nMarkerIndex, CSQLItem param, PreparedStmtColumnTypeManager preparedStmtColumnTypeManager)
	{
		if(m_PreparedStatement == null)
			return ;
		try
		{
			if(param.isSQLNull())
			{
				ParameterMetaData parameterMetaData = m_PreparedStatement.getParameterMetaData();
				int nColSQLType = parameterMetaData.getParameterType(nMarkerIndex+1);
				m_PreparedStatement.setNull(nMarkerIndex+1, nColSQLType);
			}
			else
			{
				String sTrimmed = param.getValue();
				if(BaseResourceManager.isUpdateCodeJavaToDb())
					sTrimmed = BaseResourceManager.updateCodeJavaToDb(sTrimmed);
				
				m_PreparedStatement.setObject(nMarkerIndex+1, sTrimmed);
			}
		}
		catch(IllegalArgumentException e)
		{
			// Data Time support
			String cs = param.getValue();
			if(cs.length() == 8)	// Time hh.mm.ss
			{
				CurrentDateInfo cd = new CurrentDateInfo();
				cd.setHourHHDotMMDotSS(cs);	// csValue must be of type HH.MM.SS
				long lValue = cd.getTimeInMillis();				
				Date date = new Date(lValue);							
				try
				{
					m_PreparedStatement.setDate(nMarkerIndex+1, date);
				}
				catch (SQLException e1)
				{
					LogSQLException.log(e1);
					sql.m_sqlStatus.setSQLCode("setVarParamValue with autodefined time column", e1, m_csQueryString/*, m_csSourceFileLine*/, sql);
				}
			}
			else if(cs.length() == 10)	// Date dd.mm.yyyy
			{					
				CurrentDateInfo cd = new CurrentDateInfo();
				cd.setDateDDDotMMDotYYYY(cs);	// csValue must be of type DD.MM.YYYY
				long lValue = cd.getTimeInMillis();				
				Date date = new Date(lValue);							
				try
				{
					m_PreparedStatement.setDate(nMarkerIndex+1, date);
				}
				catch (SQLException e1)
				{
					LogSQLException.log(e1);
					sql.m_sqlStatus.setSQLCode("setVarParamValue with autodefined date column", e1, m_csQueryString/*, m_csSourceFileLine*/, sql);
				}
			}
			else
			{
				Log.logImportant("setVarParamValue: Exception "+ e.toString());
				sql.m_sqlStatus.setSQLCode("setVarParamValue with autodefined date/time column", -1, e.toString(), m_csQueryString/*, m_csSourceFileLine*/);
			}
		}
		catch (SQLException e)
		{
			LogSQLException.log(e);
			sql.m_sqlStatus.setSQLCode("setVarParamValue", e, m_csQueryString/*, m_csSourceFileLine*/, sql);
		}
	}
}
