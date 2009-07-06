/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.sql;

import java.sql.BatchUpdateException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jlib.exception.ProgrammingException;
import jlib.exception.TechnicalException;
import jlib.log.Log;
import jlib.misc.CurrentDateInfo;
import jlib.misc.StopWatch;

public class DbPreparedStatement
{	
	protected PreparedStatement m_PreparedStatement = null;
	protected String m_csQueryString = null;
	private StopWatch m_swLastTimeUsed = new StopWatch(); 
	private boolean m_bReserved = true;
	private int m_nBatchSize = 0;
		
	public DbPreparedStatement()
	{
		m_bReserved = true;
	}

	Long getLastUsageTimeValue()
	{
		return m_swLastTimeUsed.getStartValue();
	}
	
	public int getBatchSize()
	{
		return m_nBatchSize;
	}
	
	public void addBatch()
	{
		try
		{
			m_PreparedStatement.addBatch();
			m_nBatchSize++;
		}
		catch (SQLException e)
		{			
			throw new RuntimeException("Could not add batch statement: " + e.getMessage(), e);
		}
	}
	
	public SQLLoadStatus executeBatch(int nLineId)
	{
		if(m_nBatchSize != 0)
		{
			try
			{
				m_nBatchSize = 0;
				m_PreparedStatement.executeBatch();
				return SQLLoadStatus.loadSuccess;
			}
			catch (BatchUpdateException e)
			{
				int ntUpdateCounts[] = e.getUpdateCounts();
				if(ntUpdateCounts != null)
				{
					SQLException e1 = e.getNextException();
					Log.logCritical("SQL sub-exception inside executeBatch: "+e1.getMessage());
					
					if (e1.getErrorCode() < 0 && e1.getErrorCode() != -803)
					{
						return SQLLoadStatus.loadFailure;
					}
					for(int n=0; n<ntUpdateCounts.length; n++)
					{
						if(ntUpdateCounts[n] != 1)
						{
							int nVal = nLineId+n;
							Log.logCritical("BatchUpdateException on source data file line: "+nVal);
						}
					}
				}
				return SQLLoadStatus.loadSuccessWithDuplicates;
			}
			catch (SQLException e)
			{			
				Log.logCritical("SQL error executing an executeBatch: "+e.getMessage());

				SQLException e1 = e.getNextException();
				Log.logCritical("SQL sub-exception inside executeBatch: "+e1.getMessage());
				return SQLLoadStatus.loadFailure;
			}
			catch (Exception e)
			{
				Log.logCritical("Generic exception catch during execution of an executeBatch: "+e.getMessage());
				return SQLLoadStatus.loadFailure;
			}
		}
		return SQLLoadStatus.loadSuccess;
	}
	
	
	public boolean prepare(DbConnectionBase dbConnection, String csQuery, boolean bHoldability)
	{
		m_csQueryString = csQuery;
		if(dbConnection != null)
		{
			try
			{
				if(bHoldability)
					m_PreparedStatement = dbConnection.getDbConnection().prepareStatement(csQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				else
					m_PreparedStatement = dbConnection.getDbConnection().prepareStatement(csQuery);
				return true;
			}
			catch (SQLException e)
			{
				throw new RuntimeException("Could not prepare '"+csQuery+"' statement:"+e.getMessage(),e);
			}
		}
		return false; 
	}
	
	public boolean prepareWithException(DbConnectionBase dbConnection, String csQuery, boolean bHoldability) 
		throws TechnicalException
	{
		m_csQueryString = csQuery;
		if(dbConnection != null)
		{
			try
			{
				if(bHoldability)
					m_PreparedStatement = dbConnection.getDbConnection().prepareStatement(csQuery, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
				else
					m_PreparedStatement = dbConnection.getDbConnection().prepareStatement(csQuery);
				return true;
			}
			catch (SQLException e)
			{
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_PREPARE_STATEMENT, csQuery, e);
			}
		}
		return false; 
	}

	public boolean setColParam(int nCol, ColValue colValue)
	{		
		if(m_PreparedStatement != null)
		{
			if(colValue.canSetColParam())	// Some column types write themseleves directly into the statement's param 
			{
				boolean b = colValue.setParamIntoStmt(m_PreparedStatement, nCol);
				return b;
			}
			String cs = colValue.getValueAsString();
			if (cs != null)
			{	
				try
				{
					m_PreparedStatement.setObject(nCol+1, cs);
				}
				catch(IllegalArgumentException e)
				{
					// Data Time support
					if(cs.length() == 8)	// Time hh.mm.ss
					{
						CurrentDateInfo cd = new CurrentDateInfo();
						cd.setHourHHDotMMDotSS(cs);	// csValue must be of type HH.MM.SS
						long lValue = cd.getTimeInMillis();				
						Date date = new Date(lValue);							
						try
						{
							m_PreparedStatement.setDate(nCol+1, date);
						}
						catch (SQLException e1)
						{
							LogSQLException.log(e1);
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
							m_PreparedStatement.setDate(nCol+1, date);
						}
						catch (SQLException e1)
						{
							LogSQLException.log(e1);
						}
					}
					else
					{
						Log.logImportant("setVarParamValue: Exception "+ e.toString());
						return false;
					}
				}
				catch (SQLException e)
				{
					LogSQLException.log(e);
					return false;
				}
			}
			else
			{
				try
				{
					m_PreparedStatement.setNull(nCol+1, colValue.getSQLType());
				}
				catch (SQLException e)
				{
					LogSQLException.log(e);
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean setDateTime(int nCol, Date date)
	{
		try
		{
			m_PreparedStatement.setDate(nCol+1, date);
			return true;
		}
		catch (SQLException e)
		{
			return false;
		}
	}

	public void setColParam(int nCol, long lValue)
	{
		try
		{
			m_PreparedStatement.setObject(nCol+1, lValue);
		} 
		catch (SQLException e)
		{
			throw new RuntimeException("Could not set columnn "+String.valueOf(nCol)+" to value '"+String.valueOf(lValue)+"': "+e.getMessage(),e);
		}
	}
	
	public void setColParam(int nCol, short sValue)
	{
		try
		{
			m_PreparedStatement.setInt(nCol+1, sValue);
		} 
		catch (SQLException e)
		{
			throw new RuntimeException("Could not set columnn "+String.valueOf(nCol)+" to value '"+String.valueOf(sValue)+"': "+e.getMessage(),e);
		}
	}
	
	public void setColParam(int nCol, int nValue)
	{
		try
		{
			m_PreparedStatement.setInt(nCol+1, nValue);
		} 
		catch (SQLException e)
		{
			throw new RuntimeException("Could not set columnn "+String.valueOf(nCol)+" to value '"+String.valueOf(nValue)+"': "+e.getMessage(),e);
		}
	}
	
	public void setColParam(int nCol, java.util.Date dateValue)
	{
		try
		{
			m_PreparedStatement.setObject(nCol+1, dateValue);
		} 
		catch (SQLException e)
		{
			throw new RuntimeException("Could not set columnn "+String.valueOf(nCol)+" to value '"+dateValue.toString()+"': "+e.getMessage(),e);
		}
	}

	public void setColParam(int nCol, String csValue)
	{
		try
		{
			m_PreparedStatement.setObject(nCol+1, csValue);
		}
		catch (SQLException e)
		{
			throw new RuntimeException("Could not set columnn "+String.valueOf(nCol)+" to value '"+csValue+"': "+e.getMessage(),e);
		}
	}
	
	public void setColParamString(int nCol, String csValue)
	{
		try
		{
			m_PreparedStatement.setString(nCol+1, csValue);
		}
		catch (SQLException e)
		{
			throw new RuntimeException("Could not set columnn "+String.valueOf(nCol)+" to value '"+csValue+"': "+e.getMessage(),e);
		}
	}

	
	public void setColParamObject(int nCol, Object oValue)
	{
		try
		{
			m_PreparedStatement.setObject(nCol+1, oValue);
		}
		catch (SQLException e)
		{
			throw new RuntimeException("Could not set columnn "+String.valueOf(nCol)+" to value '"+oValue.toString()+"': "+e.getMessage(),e);
		}
	}
	
	public int execute(SQLTypeOperation typeOperation)
	{
		if(typeOperation == SQLTypeOperation.Insert)
			return executeInsert();
		else if(typeOperation == SQLTypeOperation.Update)
			return executeUpdate();
		else if(typeOperation == SQLTypeOperation.Delete)
			return executeUpdate();
		else if(typeOperation == SQLTypeOperation.Select) 
		{
			ResultSet rs = executeSelect();
			if (rs != null)
			{
				int i = 0;
                try
                {
                	while (rs.next())
                		i++;
                	rs.close();
                }
                catch (SQLException e) {}                
                return i;
			}	
		}
		else
			return execute();
		return -1;
	}
	
	int executeWithException(SQLTypeOperation typeOperation, SQLClause sqlClause)
		throws TechnicalException
	{
		if(typeOperation == SQLTypeOperation.Insert)
			return executeInsertWithException(sqlClause);
		else if(typeOperation == SQLTypeOperation.Update)
			return executeUpdateWithException(sqlClause);
		else if(typeOperation == SQLTypeOperation.Delete)
			return executeDeleteWithException(sqlClause);
		else if(typeOperation == SQLTypeOperation.Select) 
		{
			// Should never happen: This case should be handled only by DbConnectionBase.prepareAndExecuteWithException
			ProgrammingException.throwException(ProgrammingException.SQL_PARSING_ERROR, "Should never happen; statement: "+m_csQueryString);
		}
		else
			return executeWithException(sqlClause);
		return -1;
	}
	
	private int executeWithException(SQLClause sqlClause)
		throws TechnicalException
	{
		if(m_PreparedStatement != null)
		{
			try
			{
				boolean b = m_PreparedStatement.execute();
				if(b)
					return 1;
			}
			catch (SQLException e)
			{
				Log.logCritical("SQL execute error: "+e.getMessage());
				ProgrammingException.throwException(ProgrammingException.DB_ERROR, getDumpClauseString(sqlClause), e);
			}
		}
		return -1;		
	}

	
	private int execute()
	{
		if(m_PreparedStatement != null)
		{
			try
			{
				boolean b = m_PreparedStatement.execute();
				if(b)
					return 1;
			}
			catch (SQLException e)
			{
				Log.logCritical("SQL execute error: "+e.getMessage());
				throw new RuntimeException("SQL execute error: "+e.getMessage(),e);
			}
		}
		return -1;		
	}
	
	public int executeInsert()
	{
		if(m_PreparedStatement != null)
		{
			try
			{
				int n = m_PreparedStatement.executeUpdate();
				return n;
			}
			catch (SQLException e)
			{		
				Log.logCritical("SQL error executing an insert: "+e.getMessage());
			}
		}
		return -1;		
	}
	
	public int executeInsertWithException(SQLClause sqlClause)
		throws TechnicalException
	{
		if(m_PreparedStatement != null)
		{
			try
			{
				int n = m_PreparedStatement.executeUpdate();
				return n;
			}
			catch (SQLException e)
			{		
				Log.logCritical("SQL error executing an insert: "+e.getMessage());
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_INSERT, getDumpClauseString(sqlClause), e);
			}
		}
		return -1;		
	}
	
	public int executeUpdate()
	{
		if(m_PreparedStatement != null)
		{
			try
			{
				int n = m_PreparedStatement.executeUpdate();
				return n;
			}
			catch (SQLException e)
			{
				Log.logCritical("SQL error executing an update: "+e.getMessage());
			}
		}
		return -1;		
	}
	
	public int executeUpdateWithException(SQLClause sqlClause)
		throws TechnicalException
	{
		if(m_PreparedStatement != null)
		{
			try
			{
				int n = m_PreparedStatement.executeUpdate();
				return n;
			}
			catch (SQLException e)
			{
				Log.logCritical("SQL error executing an update: "+e.getMessage());
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_UPDATE, getDumpClauseString(sqlClause), e);
			}
		}
		return -1;		
	}
	
	private String getDumpClauseString(SQLClause sqlClause)
	{
		if(sqlClause != null)
			return m_csQueryString + "\n" + sqlClause.toString();
		return m_csQueryString;
	}
	
	public int executeDeleteWithException(SQLClause sqlClause)
		throws TechnicalException
	{
		if(m_PreparedStatement != null)
		{
			try
			{
				int n = m_PreparedStatement.executeUpdate();
				return n;
			}
			catch (SQLException e)
			{
				Log.logCritical("SQL error executing an delete: "+e.getMessage());
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_DELETE, getDumpClauseString(sqlClause), e);
			}
		}
		return -1;		
	}
	
	public ResultSet executeSelect()
	{
		if(m_PreparedStatement != null)
		{
			try
			{
				ResultSet r = m_PreparedStatement.executeQuery();
				return r;
			}
			catch (SQLException e)
			{
				Log.logCritical("SQL error executing a select: "+e.getMessage());
			}
		}
		return null;
	}

	public ResultSet executeSelectWithException() 
		throws TechnicalException
	{
		if(m_PreparedStatement != null)
		{
			try
			{
				ResultSet r = m_PreparedStatement.executeQuery();
				return r;
			}
			catch (SQLException e)
			{
				Log.logCritical("SQL error executing a select: "+e.getMessage());
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_SELECT, m_csQueryString, e);
			}
		}
		return null;
	}
		
	public boolean close()
	{
		return doClose();
	}
	
	public PreparedStatement getPreparedStatement()
	{
		return m_PreparedStatement;
	}
	
	public boolean doClose()
	{
		try
		{
			m_PreparedStatement.close();
			return true;
		}
		catch (SQLException e)
		{
			Log.logCritical("Error closing a statement: "+e.getMessage());
		}
		return false;
	}
	
	public String getQueryString()
	{
		return m_csQueryString;
	}
	
	boolean isTimeOut(int nMaxStatementLiveTime_ms)
	{
		return m_swLastTimeUsed.isTimeElapsed(nMaxStatementLiveTime_ms);
	}
	
	public boolean isReserved()
	{
		return m_bReserved;
	}
	
	synchronized public void resetReserved()
	{
		m_bReserved = false;
	}
	
	public void setStatementUsed()
	{
		m_bReserved = true;
		m_swLastTimeUsed.Reset();
	}
	
	synchronized public boolean closeIfNotReserved()
	{
		if(!m_bReserved)
		{
			close();
			return true;
		}
		return false;
	}
}
