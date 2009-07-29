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
package nacaLib.dbUtils;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.sqlSupport.RecordColTypeManagerBase;
import nacaLib.sqlSupport.RecordColTypeManagerChar;
import nacaLib.sqlSupport.RecordColTypeManagerDate;
import nacaLib.sqlSupport.RecordColTypeManagerDecimal;
import nacaLib.sqlSupport.RecordColTypeManagerDecimalInt;
import nacaLib.sqlSupport.RecordColTypeManagerDecimalLong;
import nacaLib.sqlSupport.RecordColTypeManagerOther;
import nacaLib.sqlSupport.RecordColTypeManagerTimestamp;
import nacaLib.sqlSupport.RecordColTypeManagerVarchar;
import jlib.log.Log;
import jlib.misc.CurrentDateInfo;
import jlib.misc.DBIOAccounting;
import jlib.misc.DBIOAccountingType;
import jlib.misc.StopWatch;
import jlib.sql.BaseDbColDefinition;
import jlib.sql.BaseDbColDefinitionFactory;
import jlib.sql.DbConnectionBase;
import jlib.sql.DbPreparedStatement;
import jlib.sql.SQLLoadStatus;
import jlib.threads.ThreadPoolRequest;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class TableToTransfer extends ThreadPoolRequest
{	
	private String m_csTableName = null;
	private String m_csUpdateClause = null;
	private boolean m_bReplace = false;
	
	TableToTransfer(boolean bTerminaison)
	{
		super(bTerminaison);
	}
	
	TableToTransfer(String csTableName, String csReplace, String csUpdateClause)
	{
		super(false);
		
		m_csTableName = csTableName;
		m_csUpdateClause = csUpdateClause + "'" + csTableName + "'";
		if(csReplace.equalsIgnoreCase("y"))
			m_bReplace = true;
		else
			m_bReplace = false;
	}

	public void execute()
	{
		// Handle a table to transfer		
	}
	
	public void execute(DbConnectionBase dbConnectionSource, DbConnectionBase dbConnectionDestination, DbTransferDesc dbTransferDesc)
	{
		if(m_bReplace)
		{
			deleteRecordsOfDestinationTable(dbConnectionDestination);
		}
		doTransfers(dbConnectionSource, dbConnectionDestination, dbTransferDesc);		
	}
	
	private void deleteRecordsOfDestinationTable(DbConnectionBase dbConnectionDestination)
	{
		String csClause = "Delete from  " + dbConnectionDestination.getEnvironmentPrefix() + "." + m_csTableName;
		DBIOAccounting.startDBIO(DBIOAccountingType.Prepare);
		DbPreparedStatement st = dbConnectionDestination.prepareStatement(csClause, 0, false);
		DBIOAccounting.endDBIO();
		st.executeUpdate();
	}
	
	private String buildInsertClause(DbConnectionBase dbConnectionDestination, StringBuilder csColumnNames, StringBuilder csQuestionMarks)
	{
		StringBuilder csInsertClause = new StringBuilder("Insert into ");
		csInsertClause.append(dbConnectionDestination.getEnvironmentPrefix());
		csInsertClause.append(".");
		csInsertClause.append(m_csTableName);
		csInsertClause.append("(");
		csInsertClause.append(csColumnNames);
		csInsertClause.append(") values (");
		csInsertClause.append(csQuestionMarks);
		csInsertClause.append(")");
		
		return csInsertClause.toString();
	}
	
	private void doTransfers(DbConnectionBase dbConnectionSource, DbConnectionBase dbConnectionDestination, DbTransferDesc dbTransferDesc)
	{
		StopWatch sw = new StopWatch(); 
		int nBatchSize = dbTransferDesc.getBatchSize();
		int nCommitEveryBatch = dbTransferDesc.getCommitEveryBatch();
		int nNbRecordRead = 0;
		int nNbRecordWritten = 0;
		int nNbColumns = 0;
		int nNbBatchWritten = 0;
		ArrayList<RecordColTypeManagerBase> arrColTypes = null;
		ArrayList<String> arrColNames = new ArrayList<String>();
		StringBuilder sbSQLError = new StringBuilder(" ");
		String csPrefixedTableName = dbConnectionSource.getEnvironmentPrefix() + "." + m_csTableName;
		String csDestinationTableName = dbConnectionDestination.getEnvironmentPrefix() + "." + m_csTableName;
		
		String csClause = "Select * From " + csPrefixedTableName;
		DbPreparedStatement selectStatement = dbConnectionSource.prepareStatement(csClause, 0, false);
		DbPreparedStatement dbInsertStatement = null;
		PreparedStatement insertStatement = null;
		
		if(selectStatement != null)
		{
			ResultSet resultSet = selectStatement.executeSelect();
			if(resultSet != null)
			{
				try
				{
					boolean b = true;
					while(resultSet.next())
					{
						if(nNbRecordRead == 0)
						{
							ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
							nNbColumns = resultSetMetaData.getColumnCount();
							dbInsertStatement = getInsertStatement(dbConnectionDestination, resultSet, nNbColumns, resultSetMetaData, arrColNames);
							if(dbInsertStatement == null)
							{
								String cs = "Could not prepare insert statement for destination table=" + csDestinationTableName;  
								Log.logCritical(cs);
								sbSQLError = appendIfPossible(sbSQLError, cs);
								insertStatement = null;
								b = false;
							}
							else
								insertStatement = dbInsertStatement.getPreparedStatement(); 
														
							arrColTypes = getColumnsTypes(nNbColumns, resultSetMetaData);
						}						
						
						for(int nColumn=0; nColumn<nNbColumns; nColumn++)
						{
							RecordColTypeManagerBase recordColTypeManagerBase = arrColTypes.get(nColumn);
							boolean bCol = recordColTypeManagerBase.transfer(nColumn+1, resultSet, insertStatement);
							if(!bCol)
							{
								String cs = "Problem during transfer of a column. Source table=" + csPrefixedTableName + "; Destination table=" + csDestinationTableName + "; Column name=" + arrColNames.get(nColumn) + "; Clause="+csClause + "; Record number=" + nNbRecordRead;  
								Log.logCritical(cs);
								sbSQLError = appendIfPossible(sbSQLError, cs);
							}
							b |= bCol;
						}
			
						nNbRecordRead++;
						if(b)
						{
							dbInsertStatement.addBatch();
							if(dbInsertStatement.getBatchSize() == nBatchSize)
							{
								SQLLoadStatus status = doWriteBatchRecords(dbInsertStatement, nNbRecordRead, nBatchSize, sbSQLError, csPrefixedTableName, csDestinationTableName);
								if(status.isSuccess())
								{
									nNbRecordWritten += nBatchSize;
									nNbBatchWritten++;
									if(nNbBatchWritten % nCommitEveryBatch == 0)
										dbConnectionDestination.commit();
								}
								else
								{
									int nMin = nNbRecordRead-nBatchSize;
									String cs = "Execute batch insert error for records. Status=" + status.toString() + "; Source table=" + csPrefixedTableName + "; Destination table=" + csDestinationTableName + "; Record number range" + nMin + ", " + nNbRecordRead + "]";  
									Log.logCritical(cs);

									sbSQLError = appendIfPossible(sbSQLError, cs);
									dbTransferDesc.setTransferGlobalFailure();
								}									
							}
						}
						else
							dbTransferDesc.setTransferGlobalFailure();
					}
					// All records have bee transfered
					if(dbInsertStatement != null)
					{
						int n = dbInsertStatement.getBatchSize();
						SQLLoadStatus status = doWriteBatchRecords(dbInsertStatement, nNbRecordRead, n, sbSQLError, csPrefixedTableName, csDestinationTableName);
						if(status.isSuccess())
						{
							nNbRecordWritten += n;
							nNbBatchWritten++;
							dbConnectionDestination.commit();
						}
						else
						{
							int nMin = nNbRecordRead-n;
							String cs = "Execute batch insert error for records. Status=" + status.toString() + "Source table="+ csPrefixedTableName + "; Destination table=" + csDestinationTableName + "; Record number range" + nMin + ", " + nNbRecordRead + "]";  
							Log.logCritical(cs);
							sbSQLError = appendIfPossible(sbSQLError, cs);
							dbTransferDesc.setTransferGlobalFailure();
						}
					}
				}
				catch (SQLException e)
				{
					String cs = "Exception catched during transfer. Source table=" + csPrefixedTableName + "; Destination table=" + csDestinationTableName + "; Clause="+ csClause + "; Exception catched=" + e.getMessage();
					sbSQLError = appendIfPossible(sbSQLError, cs);
					Log.logCritical(cs);
					dbTransferDesc.setTransferGlobalFailure();
				}
				
				reportTransfer(dbConnectionSource, dbConnectionDestination, nNbRecordRead, sbSQLError, csPrefixedTableName, sw, csDestinationTableName);
			}
		}
	}
	
	private SQLLoadStatus doWriteBatchRecords(DbPreparedStatement dbInsertStatement, int nNbRecordRead, int nBatchSize, StringBuilder sbSQLError, String csPrefixedTableName, String csDestinationTableName)
	{
		int nMin = nNbRecordRead-nBatchSize;
		SQLLoadStatus status = dbInsertStatement.executeBatch(nMin);
		if(!status.isSuccess())
		{
			String cs = "Execute batch insert error for records. Status=" + status.toString() + "; Source table=" + csPrefixedTableName + "; Destination table=" + csDestinationTableName + "; Record number range" + nMin + ", " + nNbRecordRead + "]";  
			Log.logCritical(cs);
	
			sbSQLError = appendIfPossible(sbSQLError, cs);
		}
		return status;
	}
	
	private StringBuilder appendIfPossible(StringBuilder sbLastException, String cs)
	{
		if(sbLastException.length()+cs.length()+2 < 32700)
		{
			sbLastException.append(cs);
			sbLastException.append("\r\n");
		}
		return sbLastException;
	}
	
	private synchronized void reportTransfer(DbConnectionBase dbConnectionSource, DbConnectionBase dbConnectionDestination, int nNbRecordRead, StringBuilder sbSQLError, String csPrefixedTableName, StopWatch sw, String csDestinationTableName)
	{
		// clause is "update m_csDefinitionTable set LASTWRITE=?, NBREAD=?, NBWRITE=?, SQLERROR=? Where TNAME='xxx'"
		int nNbRecordWritten = getNbRecordsInTable(dbConnectionDestination);
		
		CurrentDateInfo now = new CurrentDateInfo();
		Date dateNow = new Date(now.getTimeInMillis());
		String csError = sbSQLError.toString();
		
		Log.logCritical("Finished transfer from source table " + csPrefixedTableName + " to destination table " + csDestinationTableName + "; Records read=" + nNbRecordRead + "; Records written="+nNbRecordWritten + "; Transfet Time=" + sw.getElapsedTime() + " ms; Text error="+csError);
		DbPreparedStatement updateStatement = dbConnectionSource.prepareStatement(m_csUpdateClause, 0, false);
		
		updateStatement.setDateTime(0, dateNow);
		updateStatement.setColParam(1, nNbRecordRead);
		updateStatement.setColParam(2, nNbRecordWritten);
		updateStatement.setColParamString(3, csError);
		int n = updateStatement.executeUpdate();
		if(n < 0)
		{
			Log.logCritical("Error during execution of clause " + m_csUpdateClause);
		}
		
		dbConnectionSource.commit();		
	}
	
	private DbPreparedStatement getInsertStatement(DbConnectionBase dbConnectionDestination, ResultSet resultSet, int nNbColumns, ResultSetMetaData resultSetMetaData, ArrayList<String> arrColNames)
	{
		try
		{
			StringBuilder csQuestionMarks = new StringBuilder();
			StringBuilder csColumnNames = new StringBuilder();
			for(int nColumn=1; nColumn<=nNbColumns; nColumn++)
			{
				String csColumnName = resultSetMetaData.getColumnName(nColumn);
				arrColNames.add(csColumnName);
				
				csColumnNames.append(csColumnName);
				if(nColumn == nNbColumns)
				{
					csQuestionMarks.append("?");									
				}
				else
				{
					csQuestionMarks.append("?,");
					csColumnNames.append(",");
				}
			}
			String csInsertClause = buildInsertClause(dbConnectionDestination, csColumnNames, csQuestionMarks);
			DbPreparedStatement insertStatement = dbConnectionDestination.prepareStatement(csInsertClause, 0, false);
			return insertStatement;
		}
		catch (SQLException e)
		{
			
		}
		return null;
	}
	
	private int getNbRecordsInTable(DbConnectionBase dbConnectionDestination)
	{
		int nNbRecords = 0;
		try
		{
			String csPrefix = dbConnectionDestination.getEnvironmentPrefix();
			String csQuery = "select count(*) from " + csPrefix + "." + m_csTableName;
			
			DbPreparedStatement st = dbConnectionDestination.prepareStatement(csQuery, 0, false);
			if(st != null)
			{
				ResultSet resultSet = st.executeSelect();
				if(resultSet != null)
				{
					if(resultSet.next())
					{
						nNbRecords = resultSet.getInt(1); 	
					}
				}
			}
		}
		catch (SQLException e)
		{						
		}
		return nNbRecords;
	}
	

	private ArrayList<RecordColTypeManagerBase> getColumnsTypes(int nNbColumns, ResultSetMetaData resultSetMetaData)
	{
		ArrayList<RecordColTypeManagerBase> arr = new ArrayList<RecordColTypeManagerBase>();
		
		try
		{
			RecordColTypeManagerBase baseRecordColTypeManager = null;
			for(int nColSourceIndex=1; nColSourceIndex<=nNbColumns; nColSourceIndex++)
			{
				String csColTypeName = resultSetMetaData.getColumnTypeName(nColSourceIndex);
				if(csColTypeName.equals("CHAR"))
				{
					baseRecordColTypeManager = new RecordColTypeManagerChar(nColSourceIndex);
					arr.add(baseRecordColTypeManager);
				}
				else if(csColTypeName.equals("DECIMAL"))
				{
					
					int nPrecision = resultSetMetaData.getPrecision(nColSourceIndex);
					int nScale = resultSetMetaData.getScale(nColSourceIndex);
					if(nScale == 0)	// No digits behind comma (integer value)
					{
						if(nPrecision <= 8)	// Fits within an int
						{
							baseRecordColTypeManager = new RecordColTypeManagerDecimalInt(nColSourceIndex);
							arr.add(baseRecordColTypeManager);
						}
						else	// A long is needed
						{
							baseRecordColTypeManager = new RecordColTypeManagerDecimalLong(nColSourceIndex);
							arr.add(baseRecordColTypeManager);
						}
					}
					else	// Digits are behind the comma
					{
						baseRecordColTypeManager = new RecordColTypeManagerDecimal(nColSourceIndex);
						arr.add(baseRecordColTypeManager);
					}
				}
				else if(csColTypeName.equals("INTEGER"))
				{
					int nPrecision = resultSetMetaData.getPrecision(nColSourceIndex);
					if(nPrecision <= 8)	// Fits within an int
					{
						baseRecordColTypeManager = new RecordColTypeManagerDecimalInt(nColSourceIndex);
						arr.add(baseRecordColTypeManager);
					}
					else	// A long is needed
					{
						baseRecordColTypeManager = new RecordColTypeManagerDecimalLong(nColSourceIndex);
						arr.add(baseRecordColTypeManager);
					}
				}
				else if(csColTypeName.equals("TIMESTAMP"))
				{
					baseRecordColTypeManager = new RecordColTypeManagerTimestamp(nColSourceIndex);
					arr.add(baseRecordColTypeManager);
				}
				else if(csColTypeName.equals("VARCHAR") || csColTypeName.equals("LONG VARCHAR") || csColTypeName.equals("LONG"))	// LONG is for ORACLE Support
				{
					baseRecordColTypeManager = new RecordColTypeManagerVarchar(nColSourceIndex);
					arr.add(baseRecordColTypeManager);
				}
				else if(csColTypeName.equals("DATE"))
				{
					baseRecordColTypeManager = new RecordColTypeManagerDate(nColSourceIndex);
					arr.add(baseRecordColTypeManager);
				}
				else if(csColTypeName.equals("SMALLINT"))
				{
					baseRecordColTypeManager = new RecordColTypeManagerDecimalInt(nColSourceIndex);
					arr.add(baseRecordColTypeManager);
				}
				else if(csColTypeName.equals("BLOB"))
				{
					baseRecordColTypeManager = new RecordColTypeManagerOther(nColSourceIndex);
					arr.add(baseRecordColTypeManager);
				}
				else
				{
					baseRecordColTypeManager = new RecordColTypeManagerOther(nColSourceIndex);
					arr.add(baseRecordColTypeManager);
				}
			}
		}
		catch (SQLException e)
		{
			int n = 0;
		}
		return arr;
	}
}
