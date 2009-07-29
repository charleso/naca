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
package nacaLib.sqlSupport;

import java.sql.ResultSet;
import java.sql.SQLException;

import jlib.log.Log;
import jlib.misc.DBIOAccounting;
import jlib.misc.DBIOAccountingType;
import jlib.sql.DbPreparedStatement;
import nacaLib.base.CJMapObject;
import nacaLib.base.JmxGeneralStat;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.exceptions.SQLErrorException;
import nacaLib.misc.SemanticContextDef;
import oracle.jdbc.OraclePreparedStatement;
import oracle.sql.ROWID;

public abstract class CSQLPreparedStatement extends DbPreparedStatement
{
	public abstract void setVarParamValue(SQL sql, String csSharpName, int nMarkerIndex, CSQLItem param, PreparedStmtColumnTypeManager preparedStmtColumnTypeManager);
	//private boolean m_bLiteralStatement = false;
	
	SemanticContextDef m_semanticContextDef = null;
	//PreparedStmtColumnTypeManager m_preparedStmtColumnTypeManager = null;
		
	CSQLPreparedStatement()
	{
		super();
		JmxGeneralStat.incNbPreparedStatement(1);
	}
	
//	public void setLiteralStatement(boolean b)
//	{
//		m_bLiteralStatement = b;
//	}
//	
//	public boolean getLiteralStatement()
//	{
//		return m_bLiteralStatement;
//	}
	
	public void finalize()
	{
		JmxGeneralStat.decNbNonFinalizedPreparedStatement(1);
	}
	
	public boolean close()
	{
		JmxGeneralStat.decNbActivePreparedStatement(1);
		return doClose();
	}
	
	public boolean setRowId(SQL sql, ROWID rowIdValue)
	{
		if(m_PreparedStatement != null)
		{
			try
			{
				((OraclePreparedStatement)m_PreparedStatement).setROWID(1, rowIdValue);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				sql.m_sqlStatus.setSQLCode("setRowId", e, m_csQueryString/*, m_csSourceFileLine*/, sql);
				return false;
			}
		}
		return true;
	}
	
	protected int getPositionQestionMark(int nParamIndex1Based)
	{
		int nPos = 0;
		while(nPos >= 0 && nParamIndex1Based > 0)
		{
			nPos = m_csQueryString.indexOf('?', nPos);
			if(nPos >= 0)
				nParamIndex1Based--;
			if(nParamIndex1Based > 0)
				nPos++;
			
		}
		return nPos;
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
		if(CJMapObject.isLogSql)
			Log.logDebug("CSQLPreparedStatement::executeQuery:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				//JmxGeneralStat.incNbSelect(1);
				
				sql.startDbIO();
				DBIOAccounting.startDBIO(DBIOAccountingType.Select);
				ResultSet r = m_PreparedStatement.executeQuery();
				DBIOAccounting.endDBIO();
				sql.endDbIO();
				
				if(r != null)
				{
					CSQLResultSet rs = new CSQLResultSet(r, m_semanticContextDef, sql);
					return rs ;
				}
				else
				{
					sql.m_sqlStatus.setSQLCode(SQLCode.SQL_NOT_FOUND.getMainCode()) ;
				}
			}
			catch (SQLException e)
			{
				DBIOAccounting.endDBIO();
				sql.endDbIO();
				manageSQLException("executeQuery", e, sql);
			}
		}
		return null;
	}
		
	public CSQLResultSet executeQueryCursor(SQL sql)
	{
		if(CJMapObject.isLogSql)
			Log.logDebug("CSQLPreparedStatement::executeQueryCursor:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				sql.startDbIO();
				DBIOAccounting.startDBIO(DBIOAccountingType.OpenCursor);
				ResultSet r = m_PreparedStatement.executeQuery();
				DBIOAccounting.endDBIO();
				if(r != null)
				{
					CSQLResultSet rs = new CSQLResultSet(r, m_semanticContextDef, sql);
					sql.m_sqlStatus.setSQLCode(SQLCode.SQL_OK.getMainCode()) ;
					sql.endDbIO();
					return rs ;
				}
				else
				{
					sql.m_sqlStatus.setSQLCode(SQLCode.SQL_NOT_FOUND.getMainCode()) ;
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
		if(BaseResourceManager.ms_bBreakOnAllSQLExceptions)
		{
//			LiteralStmtManager literalStmtManager = new LiteralStmtManager(sql);
//			literalStmtManager.tryExecuteLiteralStmt();
//			
			SQLErrorException eSQLErrorException = new SQLErrorException(csMethod, e, sql); 
			throw(eSQLErrorException);
		}
	}

	public int executeDelete(SQL sql)
	{
		sql.m_sqlStatus.setLastNbRecordUpdatedInsertedDeleted(0);
		if(CJMapObject.isLogSql)
			Log.logDebug("CSQLPreparedStatement::executeDelete:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				//JmxGeneralStat.incNbDelete(1);
				DBIOAccounting.startDBIO(DBIOAccountingType.Delete);
				int n = m_PreparedStatement.executeUpdate();
				DBIOAccounting.endDBIO();
				if (n > 0)
				{
					sql.m_sqlStatus.setSQLCode(SQLCode.SQL_OK.getMainCode()) ;
				}
				else
				{
					sql.m_sqlStatus.setSQLCode(SQLCode.SQL_NOT_FOUND.getMainCode()) ;
				}
				sql.m_sqlStatus.setLastNbRecordUpdatedInsertedDeleted(n);
				return n;
			}
			catch (SQLException e)
			{
				DBIOAccounting.endDBIO();
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_ERROR.getMainCode()) ;
				manageSQLException("executeDelete", e, sql);
			}
		}
		return -1;		
	}
	
	public int executeUpdate(SQL sql)
	{
		sql.m_sqlStatus.setLastNbRecordUpdatedInsertedDeleted(0);
		if(CJMapObject.isLogSql)
			Log.logDebug("CSQLPreparedStatement::executeUpdate:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				//JmxGeneralStat.incNbUpdate(1);
				DBIOAccounting.startDBIO(DBIOAccountingType.Update);
				int n = m_PreparedStatement.executeUpdate();
				DBIOAccounting.endDBIO();
				if (n > 0)
				{
					sql.m_sqlStatus.setSQLCode(SQLCode.SQL_OK.getMainCode()) ;
				}
				else
				{
					sql.m_sqlStatus.setSQLCode(SQLCode.SQL_NOT_FOUND.getMainCode()) ;
				}
				sql.m_sqlStatus.setLastNbRecordUpdatedInsertedDeleted(n);
				return n;
			}
			catch (SQLException e)
			{
				DBIOAccounting.endDBIO();
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_ERROR.getMainCode()) ;
				manageSQLException("executeUpdate", e, sql);
			}
		}
		return -1;		
	}
		
	public int executeInsert(SQL sql)
	{
		sql.m_sqlStatus.setLastNbRecordUpdatedInsertedDeleted(0);
		if(CJMapObject.isLogSql)
			Log.logDebug("CSQLPreparedStatement::executeInsert:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				//JmxGeneralStat.incNbInsert(1);
				DBIOAccounting.startDBIO(DBIOAccountingType.Update);
				int n = m_PreparedStatement.executeUpdate();
				DBIOAccounting.endDBIO();
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_OK.getMainCode()) ;
				sql.m_sqlStatus.setLastNbRecordUpdatedInsertedDeleted(n);
			}
			catch (SQLException e)
			{
				DBIOAccounting.endDBIO();
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_ERROR.getMainCode()) ;
				manageSQLException("executeInsert", e, sql);
			}
		}
		return -1;		
	}
	
	public int executeLock(SQL sql)
	{
		if(CJMapObject.isLogSql)
			Log.logDebug("CSQLPreparedStatement::executeLock:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				DBIOAccounting.startDBIO(DBIOAccountingType.Lock);
				m_PreparedStatement.execute();
				DBIOAccounting.endDBIO();
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_OK.getMainCode()) ;
				return 0;
			}
			catch (SQLException e)
			{
				DBIOAccounting.endDBIO();
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_ERROR.getMainCode()) ;
				manageSQLException("execute", e, sql);
			}
		}
		return -1;
	}
	
	public int executeCreateTable(SQL sql)
	{
		if(CJMapObject.isLogSql)
			Log.logDebug("CSQLPreparedStatement::executeCreateTable:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				DBIOAccounting.startDBIO(DBIOAccountingType.CreateTable);
				m_PreparedStatement.execute();
				DBIOAccounting.endDBIO();
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_OK.getMainCode()) ;
				return 0;
			}
			catch (SQLException e)
			{
				DBIOAccounting.endDBIO();
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_ERROR.getMainCode()) ;
				manageSQLException("execute", e, sql);
			}
		}
		return -1;
	}
	
	public int executeDropTable(SQL sql)
	{
		if(CJMapObject.isLogSql)
			Log.logDebug("CSQLPreparedStatement::executeDropTable:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				DBIOAccounting.startDBIO(DBIOAccountingType.DropTable);
				m_PreparedStatement.execute();
				DBIOAccounting.endDBIO();
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_OK.getMainCode()) ;
				return 0;
			}
			catch (SQLException e)
			{
				DBIOAccounting.endDBIO();
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_ERROR.getMainCode()) ;
				manageSQLException("execute", e, sql);
			}
		}
		return -1;
	}
	
	public int executeDeclareOrder(SQL sql)
	{
		if(CJMapObject.isLogSql)
			Log.logDebug("CSQLPreparedStatement::executeDeclareOrder:"+m_csQueryString);
		if(m_PreparedStatement != null)
		{
			try
			{
				DBIOAccounting.startDBIO(DBIOAccountingType.Declare);
				boolean b = m_PreparedStatement.execute();
				DBIOAccounting.endDBIO();
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_OK.getMainCode()) ;
				return 0;
			}
			catch (SQLException e)
			{
				DBIOAccounting.endDBIO();
				sql.m_sqlStatus.setSQLCode(SQLCode.SQL_ERROR.getMainCode()) ;
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
	
//	boolean isLogSql()
//	{
//		return false;
//	}
	
//	void setColumnTypeManager(PreparedStmtColumnTypeManager preparedStmtColumnTypeManager)
//	{
//		m_preparedStmtColumnTypeManager = preparedStmtColumnTypeManager;
//	}
//	
//	PreparedStmtColumnTypeManager getColumnTypeManager()
//	{
//		return m_preparedStmtColumnTypeManager;
//	}
}
