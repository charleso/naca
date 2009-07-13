/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.sqlSupport;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import jlib.log.Log;
import jlib.misc.CurrentDateInfo;
import jlib.sql.DbPreparedStatement;
import jlib.sql.LogSQLException;
import nacaLib.base.JmxGeneralStat;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.misc.SemanticContextDef;

public class CSQLPreparedStatement extends DbPreparedStatement
{
	SemanticContextDef m_semanticContextDef = null;
		
	CSQLPreparedStatement(/*DbConnectionBase dbConnection*/)
	{
		super(/*dbConnection*/);
		JmxGeneralStat.incNbPreparedStatement(1);
	}
	
	public void finalize()
	{
		JmxGeneralStat.decNbNonFinalizedPreparedStatement(1);
	}
	
	public boolean close()
	{
		JmxGeneralStat.decNbActivePreparedStatement(1);
		return doClose();
	}
	
	public void setVarParamValue(SQL sql, int nParamIndex, CSQLItem param)
	{
		if(m_PreparedStatement != null)
		{
			try
			{
				String sTrimmed = param.getValue();
				if(BaseResourceManager.isUpdateCodeJavaToDb())
					sTrimmed = BaseResourceManager.updateCodeJavaToDb(sTrimmed);
				m_PreparedStatement.setObject(nParamIndex+1, sTrimmed);
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
						m_PreparedStatement.setDate(nParamIndex+1, date);
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
						m_PreparedStatement.setDate(nParamIndex+1, date);
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
	
	public CSQLResultSet executeQueryAndFillInto(SQL sql, int nNbFetch)
	{
		CSQLResultSet SQLResultSet = executeQuery(sql);	//sql.m_sqlStatus, sql.m_arrColSelectType, sql.m_accountingRecordManager, sql.m_hashParam, sql.m_hashValue);
		if (SQLResultSet != null)
		{
			if(SQLResultSet.next())
			{
				SQLResultSet.fillIntoValues(sql, false, false, nNbFetch);
				SQLResultSet.close();
				return SQLResultSet;
			}
		}
		return null;
	}
	
	void setSemanticContextDef(SemanticContextDef semanticContextDef)
	{
		m_semanticContextDef = semanticContextDef; 
	}
	
	public CSQLResultSet executeQuery(SQL sql)	//CSQLStatus sqlStatus, ArrayFixDyn<Integer> arrColSelectType, AccountingRecordTrans accountingRecordManager, HashMap<String, CSQLItem> hashParam, HashMap<String, CSQLItem> hashValue)
	{
		if(isLogSql())
			Log.logDebug("CSQLPreparedStatement::executeQuery:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				//JmxGeneralStat.incNbSelect(1);
				
				sql.startDbIO();
				ResultSet r = m_PreparedStatement.executeQuery();
				sql.endDbIO();
				
				if(r != null)
				{
					CSQLResultSet rs = new CSQLResultSet(r, m_semanticContextDef, sql);
					return rs ;
				}
				else
				{
					sql.m_sqlStatus.setSQLCode(SQLCode.SQL_NOT_FOUND) ;
				}
			}
			catch (SQLException e)
			{
				sql.endDbIO();
				manageSQLException("executeQuery", e, sql);
			}
		}
		return null;
	}
		
	public CSQLResultSet executeQueryCursor(SQL sql)
	{
		if(isLogSql())
			Log.logDebug("CSQLPreparedStatement::executeQueryCursor:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				sql.startDbIO();
				ResultSet r = m_PreparedStatement.executeQuery();
				if(r != null)
				{
					CSQLResultSet rs = new CSQLResultSet(r, m_semanticContextDef, sql);
					sql.m_sqlStatus.setSQLCode(SQLCode.SQL_OK) ;
					sql.endDbIO();
					return rs ;
				}
				else
				{
					sql.m_sqlStatus.setSQLCode(SQLCode.SQL_NOT_FOUND) ;
					sql.endDbIO();
				}
			}
			catch (SQLException e)
			{
				sql.endDbIO();
				manageSQLException("executeQueryCursor", e, sql);				
			}
		}
		return null;
	}

	private void manageSQLException(String csMethod, SQLException e, SQL sql)
	{
		CSQLStatus sqlStatus = sql.m_sqlStatus;
		if(sqlStatus != null)
		{
			sqlStatus.setSQLCode(csMethod, e, m_csQueryString/*, m_csSourceFileLine*/, sql) ;
			sqlStatus.fillLastSQLCodeErrorText();
		}
		
		if(BaseResourceManager.ms_bLogAllSQLException || e.getErrorCode() == -499)
		{
			Log.logCritical("SQL EXCEPTION in " + csMethod + ": "+e.getErrorCode() + "; "+ e.getMessage() + " Clause="+getQueryString());
		}
	}
	
	public int executeDelete(SQL sql)
	{
		sql.m_sqlStatus.setLastNbRecordUpdatedInsertedDeleted(0);
		if(isLogSql())
			Log.logDebug("CSQLPreparedStatement::executeDelete:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				//JmxGeneralStat.incNbDelete(1);
				int n = m_PreparedStatement.executeUpdate();
				if (n > 0)
				{
					sql.m_sqlStatus.setSQLCode(SQLCode.SQL_OK) ;
				}
				else
				{
					sql.m_sqlStatus.setSQLCode(SQLCode.SQL_NOT_FOUND) ;
				}
				sql.m_sqlStatus.setLastNbRecordUpdatedInsertedDeleted(n);
				return n;
			}
			catch (SQLException e)
			{
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_ERROR) ;
				manageSQLException("executeDelete", e, sql);
			}
		}
		return -1;		
	}
	
	public int executeUpdate(SQL sql)
	{
		sql.m_sqlStatus.setLastNbRecordUpdatedInsertedDeleted(0);
		if(isLogSql())
			Log.logDebug("CSQLPreparedStatement::executeUpdate:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				//JmxGeneralStat.incNbUpdate(1);
				int n = m_PreparedStatement.executeUpdate();
				if (n > 0)
				{
					sql.m_sqlStatus.setSQLCode(SQLCode.SQL_OK) ;
				}
				else
				{
					sql.m_sqlStatus.setSQLCode(SQLCode.SQL_NOT_FOUND) ;
				}
				sql.m_sqlStatus.setLastNbRecordUpdatedInsertedDeleted(n);
				return n;
			}
			catch (SQLException e)
			{
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_ERROR) ;
				manageSQLException("executeUpdate", e, sql);
			}
		}
		return -1;		
	}
		
	public int executeInsert(SQL sql)
	{
		sql.m_sqlStatus.setLastNbRecordUpdatedInsertedDeleted(0);
		if(isLogSql())
			Log.logDebug("CSQLPreparedStatement::executeInsert:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				//JmxGeneralStat.incNbInsert(1);
				int n = m_PreparedStatement.executeUpdate();
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_OK) ;
				sql.m_sqlStatus.setLastNbRecordUpdatedInsertedDeleted(n);
			}
			catch (SQLException e)
			{
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_ERROR) ;
				manageSQLException("executeInsert", e, sql);
			}
		}
		return -1;		
	}
	
	public int executeLock(SQL sql)
	{
		if(isLogSql())
			Log.logDebug("CSQLPreparedStatement::executeLock:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				m_PreparedStatement.execute();
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_OK) ;
				return 0;
			}
			catch (SQLException e)
			{
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_ERROR) ;
				manageSQLException("execute", e, sql);
			}
		}
		return -1;
	}
	
	public int executeCreateTable(SQL sql)
	{
		if(isLogSql())
			Log.logDebug("CSQLPreparedStatement::executeCreateTable:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				m_PreparedStatement.execute();
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_OK) ;
				return 0;
			}
			catch (SQLException e)
			{
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_ERROR) ;
				manageSQLException("execute", e, sql);
			}
		}
		return -1;
	}
	
	public int executeDropTable(SQL sql)
	{
		if(isLogSql())
			Log.logDebug("CSQLPreparedStatement::executeDropTable:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				m_PreparedStatement.execute();
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_OK) ;
				return 0;
			}
			catch (SQLException e)
			{
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_ERROR) ;
				manageSQLException("execute", e, sql);
			}
		}
		return -1;
	}
	
	public int executeDeclareOrder(SQL sql)
	{
		if(isLogSql())
			Log.logDebug("CSQLPreparedStatement::executeDeclareOrder:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				boolean b = m_PreparedStatement.execute();
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_OK) ;
				return 0;
			}
			catch (SQLException e)
			{
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_ERROR) ;
				manageSQLException("execute", e, sql);				
			}
		}
		return -1;
	}
		
	public void setCursorName(String csName, SQL sql)
	{
		try
		{
			m_PreparedStatement.setCursorName(csName);
		}
		catch(SQLException e)
		{
			manageSQLException("setCursorName", e, sql);
		}
	}
	
	boolean isLogSql()
	{
		return true;
	}	
}
