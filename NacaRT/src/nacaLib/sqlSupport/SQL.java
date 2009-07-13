/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.sqlSupport;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jlib.log.Log;
import jlib.misc.ArrayDyn;
import jlib.misc.ArrayFix;
import jlib.misc.ArrayFixDyn;
import jlib.misc.ThreadSafeCounter;
import jlib.sql.DbConnectionBase;
import jlib.sql.SQLTypeOperation;
import nacaLib.accounting.AccountingRecordTrans;
import nacaLib.base.CJMapObject;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.exceptions.AbortSessionException;
import nacaLib.program.Paragraph;
import nacaLib.program.Section;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarAndEdit;

public class SQL
{
	/**
	 * @param VarBuffer
	 *            Working: working storage internal buffer
	 * @param SQLConnection:
	 *            DB connection
	 * @param String
	 *            csQuery: SQL query
	 * @param bCursor:
	 *            true if a SQL cursor is concerned Internal usage only
	 */
	private AccountingRecordTrans m_accountingRecordManager = null;
	private boolean m_bArrayCompressed = false; 

	public SQL(BaseProgramManager programManager)
	{
		m_programManager = programManager;
	}
	
	public SQL(BaseProgramManager programManager, String csQuery, SQLCursor cursor/*, String csSourceFileLine*/, int nHashFileLine)
	{
		m_nSuffixeHash = nHashFileLine;
		m_errorManager = new SQLErrorManager();
		//m_csSourceFileLine = csSourceFileLine;
		if (programManager != null)
		{
			BaseEnvironment env = programManager.getEnv();
			m_accountingRecordManager = env.getAccountingRecordManager();
			DbConnectionBase SQLConnection = env.getSQLConnection();
			if(SQLConnection != null)
			{
				CSQLStatus sqlstatus = programManager.getSQLStatus();
				create(programManager, SQLConnection, csQuery, cursor, sqlstatus);
			}	
		}
		//JmxGeneralStat.incNbSQLObjects(1);
	}

//	public void finalize()
//	{
//		JmxGeneralStat.incNbSQLObjects(-1);
//	}

	public SQL(BaseEnvironment env, BaseProgramManager programManager, DbConnectionBase SQLConnection, String csQuery, SQLCursor cursor, CSQLStatus status)
	{
		m_errorManager = new SQLErrorManager();
		//JmxGeneralStat.incNbSQLObjects(1);
		m_accountingRecordManager = env.getAccountingRecordManager();
		create(programManager, SQLConnection, csQuery, cursor, status);
	}

	private void create(BaseProgramManager programManager, DbConnectionBase SQLConnection, String csQuery, SQLCursor cursor, CSQLStatus status)
	{
		m_programManager = programManager;
		if (CJMapObject.isLogSql)
		{
			if (cursor == null)
				Log.logDebug("Sql=" + csQuery);
			else
				Log.logDebug("SqlCursor=" + csQuery);
		}
		
		status.setQuery(csQuery);

		m_sqlStatus = status;
		m_arrIntoItems = new ArrayDyn<CSQLIntoItem>();
		m_hashParam = new HashMap<String, CSQLItem>();
		m_hashValue = new HashMap<String, CSQLItem>();
		m_SQLConnection = SQLConnection;
		// boolean bUseSQLMBean = BaseResourceManager.getUseSQLMBean();

		boolean bUseExplain = SQLConnection.getUseExplain();
		m_csQuery = csQuery;
		m_csQueryUpper = csQuery.toUpperCase();
		m_nSQLUniqueId = getSQLUniqueId();

		m_bRowIdGenerated = false;
		m_bOperationExecuted = false;

		boolean bCursor = false;
		if (cursor != null)
			bCursor = true;
		m_SQLTypeOperation = SQLTypeOperation.determineOperationType(m_csQueryUpper, bCursor);

		boolean bRowIdToAdd = false;

		if (cursor != null)
		{
			int nForUpdate = m_csQueryUpper.indexOf("FOR UPDATE");
			if (nForUpdate >= 0)
			{
				bUseExplain = false; // No Explain for FOR UPDATE CLAUSES
				if (!m_SQLConnection.supportCursorName())
				{
					bRowIdToAdd = true;
					cursor.setMustBeNamed(false);
				}
				else
				{
					cursor.setMustBeNamed(true);
				}
			}
			else
			{
				cursor.setMustBeNamed(false);
			}
		}

		manageOperationDeclaration(bRowIdToAdd);
		
		m_sqlStatus.setQuery(m_csQuery);
		
		attachToCursor(cursor);
		manageOperationEnding();

		if (bUseExplain)
		{
			m_csExplainQuery = "EXPLAIN PLAN SET QUERYNO=" + m_nSQLUniqueId + " FOR " + m_csQuery;
		}
	}

	private void attachToCursor(SQLCursor cursor)
	{
		m_cursor = cursor;
	}

	private SQLCursor m_cursor = null;

	public void reuse(CSQLStatus status, BaseEnvironment env, SQLCursor cursor)
	{
		//JmxGeneralStat.incNbSQLObjectsReuse(1);
		m_accountingRecordManager = env.getAccountingRecordManager();
		m_sqlStatus = status;
		m_sqlStatus.setQuery(m_csQuery);		
		resetExecuted(env);
		m_SQLCursorResultSet = null;
		m_nNbWhereParamDeclared = 0;
		m_nNbIntoParamDeclared = 0;
		m_nNbColToSetDeclared = 0;
		attachToCursor(cursor);
		manageOperationEnding();
		m_bReused = true;
		m_nNbFetch = 0;
		m_errorManager.reuse();
	}

	private void compressArrays()
	{
		m_bArrayCompressed = true;
		// Compress once array
		if (m_arrIntoItems.isDyn())
		{
			int nSize = m_arrIntoItems.size();
			CSQLIntoItem arr[] = new CSQLIntoItem[nSize];
			m_arrIntoItems.transferInto(arr);

			ArrayFix<CSQLIntoItem> arrFix = new ArrayFix<CSQLIntoItem>(arr);
			m_arrIntoItems = arrFix;
		}

		if (m_arrColSelectType != null && m_arrColSelectType.isDyn())
		{
			int nSize = m_arrColSelectType.size();
			Integer arr[] = new Integer[nSize];
			m_arrColSelectType.transferInto(arr);

			ArrayFix<Integer> arrFix = new ArrayFix<Integer>(arr);
			m_arrColSelectType = arrFix;
		}
	}

	public void resetExecuted(BaseEnvironment env)
	{
		m_bOperationExecuted = false;
		m_SQLConnection = env.getSQLConnection();
	}
	
	public void resetErrorManager()
	{
		if (m_errorManager != null)
			m_errorManager.reuse();
	}

	private void manageColStarDeclarations()
	{
		boolean bStarFound = false;
		int nNbComma = 0;
		int nNbOpenParenthesis = 0;
		int nPosFrom = m_csQueryUpper.indexOf("FROM ");
		int nPos = m_csQueryUpper.indexOf("SELECT ") + 6;
		for (; nPos < nPosFrom; nPos++)
		{
			char c = m_csQueryUpper.charAt(nPos);
			if (c == ',' && nNbOpenParenthesis == 0)
			{
				if (bStarFound)
				{
					addStarAtCol(nNbComma);
					bStarFound = false;
				}
				nNbComma++;
			}
			else if (c == '(')
			{
				nNbOpenParenthesis++;
				bStarFound = false;
			}
			else if (c == ')')
			{
				nNbOpenParenthesis--;
				bStarFound = false;
			}
			else if (c == '*')
				bStarFound = true;
			else if (!Character.isWhitespace(c) && bStarFound) // We have a non
																// whitespace
																// and ha a
																// star: it's
																// not a select
																// *
				bStarFound = false;
		}
		if (bStarFound)
			addStarAtCol(nNbComma);
	}

	private void addStarAtCol(int nColId)
	{
		if (m_arrColSelectType == null)
			m_arrColSelectType = new ArrayDyn<Integer>();
		Integer iColId = Integer.valueOf(nColId);
		m_arrColSelectType.add(iColId); // The nColId is a *
	}

	private void manageOperationDeclaration(boolean bMustAddRowId)
	{
		if (m_SQLTypeOperation == SQLTypeOperation.Select || m_SQLTypeOperation == SQLTypeOperation.CursorSelect)
		{
			m_arrMarkerNames = findAndUpdateMarkers();
			m_nNbWhereParamToProvide = m_arrMarkerNames.size();
			m_nNbWhereParamDeclared = 0;			
			m_nNbIntoParamToProvide = getNbIntoParam();
			if (m_SQLTypeOperation == SQLTypeOperation.CursorSelect && bMustAddRowId) // Add
																						// to
																						// ROWID
																						// Column
																						// at
																						// the
																						// 1st
																						// position,
																						// as
																						// it
																						// is
																						// required
																						// for
																						// "FOR
																						// UPDATE"
																						// support
			{
				int nPosSelect = m_csQueryUpper.indexOf("SELECT");
				if (nPosSelect == 0)
				{
					String csRight = m_csQuery.substring(nPosSelect + 6);
					m_csQuery = "SELECT ROWID, " + csRight;
					m_csQueryUpper = m_csQuery.toUpperCase();
					m_bRowIdGenerated = true;
					m_nNbIntoParamToProvide++;
				}
			}
			manageColStarDeclarations();
		}
		else if (m_SQLTypeOperation == SQLTypeOperation.Insert)
		{
			m_arrMarkerNames = findAndUpdateMarkers();
			m_nNbColToSetToProvide = m_arrMarkerNames.size();
		}
		else if (m_SQLTypeOperation == SQLTypeOperation.Update)
		{
			m_nNbWhereParamToProvide = getNbWhereParam();
			m_nNbWhereParamDeclared = 0;
			m_arrMarkerNames = findAndUpdateMarkers();
			m_nNbColToSetToProvide = m_arrMarkerNames.size() - m_nNbWhereParamToProvide;
		}
		else if (m_SQLTypeOperation == SQLTypeOperation.Delete)
		{
			m_arrMarkerNames = findAndUpdateMarkers();
			m_nNbWhereParamToProvide = m_arrMarkerNames.size();
			m_nNbWhereParamDeclared = 0;
		}
		if (m_SQLConnection != null)
		{
			m_csQuery = SQLTypeOperation.addEnvironmentPrefix(m_SQLConnection.getEnvironmentPrefix(), m_csQuery, m_SQLTypeOperation, "");
			m_csQueryUpper = m_csQuery.toUpperCase();
		}
	}

	private void executeOnceExplainQuery()
	{
		Statement statement = m_SQLConnection.create();
		if (statement != null)
		{
			try
			{
				statement.executeUpdate(m_csExplainQuery);
				m_csExplainQuery = null;
			}
			catch (SQLException e)
			{
				Log.logImportant("Could not execute explain query (error=" + e.getErrorCode() + ") : " + m_csExplainQuery);
			}
		}
	}

	public boolean manageOperationEnding()
	{
		//boolean bExecDone = false;

		if (m_SQLConnection == null || m_bOperationExecuted)
			return false;
		
		if (m_SQLTypeOperation == SQLTypeOperation.CursorSelect)
		{
			if (m_nNbWhereParamDeclared == m_nNbWhereParamToProvide) // All
																		// params
																		// have
																		// been
																		// filled
			{
				if (m_SQLCursorResultSet == null) // 1st step: all params
													// have been provided;
													// must now prepare the
													// statement
				{
					m_accountingRecordManager.incCursorOpen();
					//JmxGeneralStat.incOpenCursor(1);
					if (m_csExplainQuery != null)
						executeOnceExplainQuery();

					CSQLPreparedStatement SQLStatement = executePrepareSelect();

					if (SQLStatement != null)
					{
						if (m_SQLConnection.supportCursorName())
						{
							if (m_cursor != null && m_cursor.getMustNameCursor())
							{
								String csCursorName = m_cursor.getUniqueCursorName();
								SQLStatement.setCursorName(csCursorName, this);
							}
						}
						m_SQLCursorResultSet = SQLStatement.executeQueryCursor(this);
					}
				}
				if (m_nNbIntoParamToProvide == m_nNbIntoParamDeclared && m_SQLCursorResultSet != null) // All
																										// into
																										// have
																										// been
																										// specified
				{
					m_accountingRecordManager.incFetchCursor();
					//JmxGeneralStat.incFetchCursor(1);

					m_accountingRecordManager.startDbIO();
					boolean bNext = m_SQLCursorResultSet.next();
					
					m_accountingRecordManager.endDbIO();

					if (bNext)
					{
						m_SQLCursorResultSet.fillIntoValues(this, true, m_bRowIdGenerated, m_nNbFetch);
					}
					m_nNbFetch++;
					m_nNbIntoParamDeclared = 0; // no more into
					m_bOperationExecuted = true;
				}
			}
		}
		else if (m_SQLTypeOperation == SQLTypeOperation.Select)
		{
			if (m_nNbWhereParamDeclared == m_nNbWhereParamToProvide && m_nNbIntoParamDeclared == m_nNbIntoParamToProvide) // All
																															// params
																															// have
																															// been
																															// filled
			{
				m_accountingRecordManager.incSelect();
				if (m_csExplainQuery != null)
					executeOnceExplainQuery();

				CSQLPreparedStatement SQLStatement = executePrepareSelect();
				if (SQLStatement != null)
				{
					executeQueryAndFillInto(SQLStatement, m_nNbFetch);
					m_nNbFetch++;
					m_bOperationExecuted = true;
//						bExecDone = true;
				}
//					if(!m_bArrayCompressed)
//						compressArrays();
				//m_SQLConnection = null;
			}
		}
		else if (m_SQLTypeOperation == SQLTypeOperation.Insert)
		{
			if (m_nNbColToSetToProvide == m_nNbColToSetDeclared)
			{
				m_accountingRecordManager.incInsert();
				if (m_csExplainQuery != null)
					executeOnceExplainQuery();

				executeInsert();
				m_bOperationExecuted = true;
//					bExecDone = true;
//					if(!m_bArrayCompressed)
//						compressArrays();
				//m_SQLConnection = null;
			}
		}
		else if (m_SQLTypeOperation == SQLTypeOperation.Update)
		{
			if (m_nNbWhereParamDeclared == m_nNbWhereParamToProvide && m_nNbColToSetDeclared == m_nNbColToSetToProvide)
			{
				m_accountingRecordManager.incUpdate();
				if (m_csExplainQuery != null)
					executeOnceExplainQuery();

				executeUpdate();
				m_bOperationExecuted = true;
//					bExecDone = true;
//					if(!m_bArrayCompressed)
//						compressArrays();
				//m_SQLConnection = null;
			}
		}
		else if (m_SQLTypeOperation == SQLTypeOperation.Delete)
		{
			if (m_nNbWhereParamDeclared == m_nNbWhereParamToProvide)
			{
				m_accountingRecordManager.incDelete();
				if (m_csExplainQuery != null)
					executeOnceExplainQuery();

				executeDelete();
				m_bOperationExecuted = true;
//					bExecDone = true;
//					if(!m_bArrayCompressed)
//						compressArrays();
				//m_SQLConnection = null;
			}
		}
		else if (m_SQLTypeOperation == SQLTypeOperation.Lock)
		{
			executeLock();
			m_bOperationExecuted = true;
//				bExecDone = true;
//				if(!m_bArrayCompressed)
//					compressArrays();
			//m_SQLConnection = null;
		}
		else if (m_SQLTypeOperation == SQLTypeOperation.Create)
		{
			executeCreateTable();
			m_bOperationExecuted = true;
//				bExecDone = true;
//				if(!m_bArrayCompressed)
//					compressArrays();
			//m_SQLConnection = null;
		}
		else if (m_SQLTypeOperation == SQLTypeOperation.Drop)
		{
			executeDropTable();
			m_bOperationExecuted = true;
//				bExecDone = true;
//				if(!m_bArrayCompressed)
//					compressArrays();
			//m_SQLConnection = null;
		}
		else if (m_SQLTypeOperation == SQLTypeOperation.Declare)
		{
			executeDeclareOrder();
			m_bOperationExecuted = true;
//				bExecDone = true;
//				if(!m_bArrayCompressed)
//					compressArrays();
			//m_SQLConnection = null;
		}
				
		if(m_bOperationExecuted)
		{
			if(m_sqlStatus != null)
			{
				m_errorManager.manageSQLError(m_sqlStatus);
				boolean b = m_sqlStatus.isLastSQLCodeConnectionKiller();
				if(b)
				{
					m_SQLConnection.setConnectionUnreusable();	// This connection can't be used anymore
			
					AbortSessionException exp = new AbortSessionException() ;
					exp.m_Reason = new Error("Connection killer SQLCode received:"+m_sqlStatus.toString());
					exp.m_ProgramName = null;  // register current program that throws the exception.
					throw exp ;
				}
			}
			m_SQLConnection = null;
			if(!m_bArrayCompressed)
				compressArrays();
		}				
		
		return m_bOperationExecuted;
	}

	/**
	 * @param Var
	 *            varDestCol
	 * @return this Defines a destination variable that will receive a recordser
	 *         column value after a select, with cursor or not
	 */
	public SQL into(VarAndEdit varDestCol)
	{
		if(m_nNbIntoParamDeclared < m_nNbIntoParamToProvide)	// if (canFillInto())
		{
			if (CJMapObject.isLogSql)
				Log.logDebug("into " + varDestCol.getLoggableValue());
			if (/*m_bReused && */m_nNbIntoParamDeclared < m_arrIntoItems.size())
			{
				CSQLIntoItem sqlIntoItem = m_arrIntoItems.get(m_nNbIntoParamDeclared);
				sqlIntoItem.set(varDestCol, null);
			}
			else
			{
				CSQLIntoItem sqlIntoItem = new CSQLIntoItem(varDestCol, null);
				m_arrIntoItems.add(sqlIntoItem);
			}
			m_nNbIntoParamDeclared++;
			manageOperationEnding();
		}
		else
		{
			Log.logImportant("Error: Too many into set; into " + varDestCol.getLoggableValue());
			m_sqlStatus.setSQLCode("into", -1, "ERROR : too many 'into set'", m_csQuery);	///, m_csSourceFileLine);
		}
		return this;
	}

	/**
	 * @param Var
	 *            varDestCol: Destination variable
	 * @param Var
	 *            varIndicator: Destination variable
	 * @return this Defines the destination variable (varDestCol) that will
	 *         receive a recordser column value after a select, with cursor or
	 *         not The varIndicator variable will be set to -1 if the recordset
	 *         column value is SQL NULL The varIndicator variable will be set to
	 *         0 if the recordset column value is not SQL NULL These into()
	 *         methods must match exactly the column name described in the SQL
	 *         select clause
	 */
	public SQL into(VarAndEdit varDestCol, Var varIndicator)
	{
		if(m_nNbIntoParamDeclared < m_nNbIntoParamToProvide)	// if (canFillInto())	
		{
			CSQLIntoItem sqlIntoItem = null;
			if (/*m_bReused && */m_nNbIntoParamDeclared < m_arrIntoItems.size())
			{
				sqlIntoItem = m_arrIntoItems.get(m_nNbIntoParamDeclared);
				sqlIntoItem.set(varDestCol, varIndicator);
			}
			else
			{
				sqlIntoItem = new CSQLIntoItem(varDestCol, varIndicator);
				m_arrIntoItems.add(sqlIntoItem);
			}

			if (CJMapObject.isLogSql)
				Log.logDebug(sqlIntoItem.getLoggableValue());

			m_nNbIntoParamDeclared++;
			
			boolean bExecDone = manageOperationEnding();
			if (bExecDone && varIndicator != null) // Maybe we had an occurs of
													// indicator given by
													// varIndicator; the cache must
													// be be able to reuse these
													// variables, as they may have
													// been transfered to a
													// SQLRecordSetVarFiller which
													// has now the responsability of
													// these variables
			{
				TempCache cache = TempCacheLocator.getTLSTempCache();
				cache.resetTempVarIndexAndForbidReuse(varIndicator);
			}
		}
		else
		{
			CSQLIntoItem sqlIntoItemTemp = new CSQLIntoItem(varDestCol, varIndicator);	
			Log.logCritical("Error: Too many into set; " + sqlIntoItemTemp.getLoggableValue());
			m_sqlStatus.setSQLCode("into", -1, "ERROR : too many 'into set'", m_csQuery/*, m_csSourceFileLine*/);
		}
		return this;
	}

//	private boolean canFillInto()
//	{
//		if (m_nNbIntoParamDeclared < m_nNbIntoParamToProvide)
//			return true;
//		return false; // PJD: TODO: Crash due to too many into ?
//	}

	/**
	 * @param int
	 *            nName: Number identifying the value to give
	 * @param int
	 *            nValue: Value given
	 * @return this Used to provided values to a insert or update SQL clause.
	 *         The nName identifies the column set exemple (in this case, see
	 *         1st .value() call) : sql("insert into Entries (Id, Author, Text)
	 *         VALUES (#1, #2, #3)") .value(1, 2) .value(2, "toto") .value(3,
	 *         VText)
	 */
	public SQL value(int nName, int nValue)
	{
		String csName = String.valueOf(nName);
		return value(csName, nValue);
	}

	/**
	 * @param String
	 *            csName: String identifying the value to give
	 * @param int
	 *            nValue: Value given
	 * @return this Used to provided values to a insert or update SQL clause.
	 *         The nName identifies the column set exemple (in this case, see
	 *         1st .value() call) : sql("insert into Entries (Id, Author, Text)
	 *         VALUES (#a, #b, #c)") .value("a", 2) .value("b", "toto")
	 *         .value("c", VText)
	 */
	public SQL value(String csName, int nValue)
	{
		if (CJMapObject.isLogSql)
			Log.logDebug("value " + csName + "=" + nValue);
		if (m_bReused)
		{
			CSQLItem Item = m_hashValue.get(csName);
			Item.set(nValue);
		}
		else
		{
			CSQLItem Item = new CSQLItem(nValue);
			m_hashValue.put(csName, Item);
		}

		m_nNbColToSetDeclared++;
		manageOperationEnding();

		return this;
	}

	/**
	 * @param int
	 *            nName: Number identifying the value to give
	 * @param double
	 *            dValue: Value given
	 * @return this Used to provided values to a insert or update SQL clause.
	 *         The nName identifies the column set exemple (in this case, see
	 *         1st .value() call) : sql("insert into Entries (price, Author,
	 *         Text) VALUES (#1, #2, #3)") .value(1, 3.14) .value(2, "toto")
	 *         .value(3, VText)
	 */
	public SQL value(int nName, double dValue)
	{
		String csName = String.valueOf(nName);
		return value(csName, dValue);
	}

	/**
	 * @param String
	 *            csName: String identifying the value to give
	 * @param double
	 *            dValue: Value given
	 * @return this Used to provided values to a insert or update SQL clause.
	 *         The nName identifies the column set exemple (in this case, see
	 *         1st .value() call) : sql("insert into Entries (price, Author,
	 *         Text) VALUES (#a, #b, #c)") .value("a", 3.14) .value("b", "toto")
	 *         .value("c", VText)
	 */
	public SQL value(String csName, double dValue)
	{
		if (CJMapObject.isLogSql)
			Log.logDebug("value " + csName + "=" + dValue);
		if (m_bReused)
		{
			CSQLItem Item = m_hashValue.get(csName);
			Item.set(dValue);
		}
		else
		{
			CSQLItem Item = new CSQLItem(dValue);
			m_hashValue.put(csName, Item);
		}

		m_nNbColToSetDeclared++;
		manageOperationEnding();

		return this;
	}

	/**
	 * @param int
	 *            nName: Number identifying the value to give
	 * @param String
	 *            csValue: Value given
	 * @return this Used to provided values to a insert or update SQL clause.
	 *         The nName identifies the column set exemple (in this case, see
	 *         2nd .value() call) : sql("insert into Entries (price, Author,
	 *         Text) VALUES (#1, #2, #3)") .value(1, 3.14) .value(2, "toto")
	 *         .value(3, VText)
	 */
	public SQL value(int nName, String csValue)
	{
		String csName = String.valueOf(nName);
		return value(csName, csValue);
	}

	/**
	 * @param String
	 *            csName: String identifying the value to give
	 * @param String
	 *            csValue: Value given
	 * @return this Used to provided values to a insert or update SQL clause.
	 *         The nName identifies the column set exemple (in this case, see
	 *         2nd .value() call) : sql("insert into Entries (price, Author,
	 *         Text) VALUES (#a, #b, #c)") .value("a", 3.14) .value("b", "toto")
	 *         .value("c", VText)
	 */
	public SQL value(String csName, String csValue)
	{
		if (CJMapObject.isLogSql)
			Log.logDebug("value " + csName + "=" + csValue);
		if (m_bReused)
		{
			CSQLItem Item = m_hashValue.get(csName);
			Item.set(csValue);
		}
		else
		{
			CSQLItem Item = new CSQLItem(csValue);
			m_hashValue.put(csName, Item);
		}
		m_nNbColToSetDeclared++;
		manageOperationEnding();

		return this;
	}

	/**
	 * @param int
	 *            nName: Number identifying the value to give
	 * @param Var
	 *            varValue: Value given
	 * @return this Used to provided values to a insert or update SQL clause.
	 *         The nName identifies the column set exemple (in this case, see
	 *         3rd .value() call) : sql("insert into Entries (price, Author,
	 *         Text) VALUES (#1, #2, #3)") .value(1, 3.14) .value(2, "toto")
	 *         .value(3, VText)
	 */
	public SQL value(int nName, VarAndEdit varValue)
	{
		String csName = String.valueOf(nName);
		return value(csName, varValue);
	}

	/**
	 * @param String
	 *            csName: String identifying the value to give
	 * @param Var
	 *            varValue: Value given
	 * @return this Used to provided values to a insert or update SQL clause.
	 *         The nName identifies the column set exemple (in this case, see
	 *         3rd .value() call) : sql("insert into Entries (price, Author,
	 *         Text) VALUES (#a, #b, #c)") .value("a", 3.14) .value("b", "toto")
	 *         .value("c", VText)
	 */
	public SQL value(String csName, VarAndEdit varValue)
	{
		if (CJMapObject.isLogSql)
			Log.logDebug("value " + csName + "=" + varValue.getLoggableValue());
		if (m_bReused)
		{
			CSQLItem Item = m_hashValue.get(csName);
			Item.set(varValue);
		}
		else
		{
			CSQLItem Item = new CSQLItem(varValue);
			m_hashValue.put(csName, Item);
		}

		m_nNbColToSetDeclared++;
		manageOperationEnding();

		return this;
	}

	public SQL setHoldability(boolean b)
	{
		m_bHoldability = b;
		manageOperationEnding();
		return this;
	}

	/**
	 * @param int
	 *            nName: Number identifying the value to give
	 * @param Var
	 *            var: Parameter's value
	 * @return this USed to set a parameter's value in a SQL WHERE clause of a
	 *         SELECT (with cursor or not), UPDATE or DELETE exemple (:
	 *         sql("update Entries SET Text=#1, Author=#2 WHERE Id=#3 or Id=#4")
	 *         .value(1, VText) .value(2, VAuthor) .param(3, VCurrentId)
	 *         .param(4, 2);
	 */
	public SQL param(int nName, VarAndEdit var)
	{
		String csName = String.valueOf(nName);
		return param(csName, var);
	}

	/**
	 * @param String
	 *            csName: String identifying the value to give
	 * @param Var
	 *            var: Parameter's value
	 * @return this USed to set a parameter's value in a SQL WHERE clause of a
	 *         SELECT (with cursor or not), UPDATE or DELETE exemple (:
	 *         sql("update Entries SET Text=#1, Author=#2 WHERE Id=#a or Id=#b")
	 *         .value(1, VText) .value(2, VAuthor) .param("a", VCurrentId)
	 *         .param("b", 2);
	 */
	public SQL param(String csName, VarAndEdit var)
	{
		if (canFillParam())
		{
			if (CJMapObject.isLogSql)
				Log.logDebug("param " + csName + "=" + var.getLoggableValue());
			if (!m_bReused)
			{
				CSQLItem Item = new CSQLItem(var);
				m_hashParam.put(csName.toUpperCase(), Item);
			}
			else
			{
				CSQLItem Item = m_hashParam.get(csName.toUpperCase());
				Item.set(var);
			}

			m_nNbWhereParamDeclared++;
			manageOperationEnding();
		}
		else
		{
			Log.logImportant("Error: Too many param set; param " + csName + "=" + var.getLoggableValue());
		}

		return this;
	}

	/**
	 * @param int
	 *            nName: Number identifying the value to give
	 * @param int
	 *            nValue: Parameter's value
	 * @return this USed to set a parameter's value in a SQL WHERE clause of a
	 *         SELECT (with cursor or not), UPDATE or DELETE exemple (see 2nd
	 *         .param method call): sql("update Entries SET Text=#1, Author=#2
	 *         WHERE Id=#a or Id=#b") .value(1, VText) .value(2, VAuthor)
	 *         .param("a", VCurrentId) .param("b", 2);
	 */
	public SQL param(int nName, int nValue)
	{
		String csName = String.valueOf(nName);
		return param(csName, nValue);
	}

	/**
	 * @param String
	 *            csName: String identifying the value to give
	 * @param int
	 *            nValue: Parameter's value
	 * @return this USed to set a parameter's value in a SQL WHERE clause of a
	 *         SELECT (with cursor or not), UPDATE or DELETE exemple (see 2nd
	 *         .param method call): sql("update Entries SET Text=#1, Author=#2
	 *         WHERE Id=#a or Id=#b") .value(1, VText) .value(2, VAuthor)
	 *         .param("a", VCurrentId) .param("b", 2);
	 */
	public SQL param(String csName, int nValue)
	{
		if (canFillParam())
		{
			if (CJMapObject.isLogSql)
				Log.logDebug("param " + csName + "=" + nValue);
			if (!m_bReused)
			{
				CSQLItem Item = new CSQLItem(nValue);
				m_hashParam.put(csName.toUpperCase(), Item);
			}
			else
			{
				CSQLItem Item = m_hashParam.get(csName.toUpperCase());
				Item.set(nValue);
			}

			m_nNbWhereParamDeclared++;
			manageOperationEnding();
		}
		else
		{
			Log.logImportant("Error: Too many param set; param " + csName + "=" + nValue);
		}
		return this;
	}

	/**
	 * @param int
	 *            nName: Number identifying the value to give
	 * @param double
	 *            d: Parameter's value
	 * @return this USed to set a parameter's value in a SQL WHERE clause of a
	 *         SELECT (with cursor or not), UPDATE or DELETE exemple (see 2nd
	 *         .param method call): sql("update Entries SET Text=#1, Author=#2
	 *         WHERE Id=#a or Price>#b") .value(1, VText) .value(2, VAuthor)
	 *         .param("a", VCurrentId) .param("b", 5.5);
	 */
	public SQL param(int nName, double dValue)
	{
		String csName = String.valueOf(nName);
		return param(csName, dValue);
	}

	/**
	 * @param String
	 *            csName: String identifying the value to give
	 * @param double
	 *            d: Parameter's value
	 * @return this USed to set a parameter's value in a SQL WHERE clause of a
	 *         SELECT (with cursor or not), UPDATE or DELETE exemple (see 2nd
	 *         .param method call): sql("update Entries SET Text=#1, Author=#2
	 *         WHERE Id=#a or Price>#b") .value(1, VText) .value(2, VAuthor)
	 *         .param("a", VCurrentId) .param("b", 5.5);
	 */
	public SQL param(String csName, double dValue)
	{
		if (canFillParam())
		{
			if (CJMapObject.isLogSql)
				Log.logDebug("param " + csName + "=" + dValue);
			if (!m_bReused)
			{
				CSQLItem Item = new CSQLItem(dValue);
				m_hashParam.put(csName.toUpperCase(), Item);
			}
			else
			{
				CSQLItem Item = m_hashParam.get(csName.toUpperCase());
				Item.set(dValue);
			}

			m_nNbWhereParamDeclared++;
			manageOperationEnding();
		}
		else
		{
			Log.logImportant("Error: Too many param set; param " + csName + "=" + dValue);
		}
		return this;
	}

	/**
	 * @param int
	 *            nName: Number identifying the value to give
	 * @param String
	 *            csValue: Parameter's value
	 * @return this USed to set a parameter's value in a SQL WHERE clause of a
	 *         SELECT (with cursor or not), UPDATE or DELETE exemple (see 2nd
	 *         .param method call): sql("update Entries SET Text=#1, Author=#2
	 *         WHERE Id=#a or Town=#b") .value(1, VText) .value(2, VAuthor)
	 *         .param("a", VCurrentId) .param("b", "Geneva");
	 */
	public SQL param(int nName, String csValue)
	{
		String csName = String.valueOf(nName);
		return param(csName, csValue);
	}

	/**
	 * @param String
	 *            csName: String identifying the value to give
	 * @param String
	 *            csValue: Parameter's value
	 * @return this USed to set a parameter's value in a SQL WHERE clause of a
	 *         SELECT (with cursor or not), UPDATE or DELETE exemple (see 2nd
	 *         .param method call): sql("update Entries SET Text=#1, Author=#2
	 *         WHERE Id=#a or Town=#b") .value(1, VText) .value(2, VAuthor)
	 *         .param("a", VCurrentId) .param("b", "Geneva");
	 */
	public SQL param(String csName, String csValue)
	{
		if (canFillParam())
		{
			if (CJMapObject.isLogSql)
				Log.logDebug("param " + csName + "=" + csValue);

			if (!m_bReused)
			{
				CSQLItem Item = new CSQLItem(csValue);
				m_hashParam.put(csName.toUpperCase(), Item);
			}
			else
			{
				CSQLItem Item = m_hashParam.get(csName.toUpperCase());
				Item.set(csValue);
			}

			m_nNbWhereParamDeclared++;
			manageOperationEnding();
		}
		else
		{
			Log.logImportant("Error: Too many param set; param " + csName + "=" + csValue);
		}
		return this;
	}
	
	private int getNbWhereParam()
	{
		int nPosWhere = m_csQueryUpper.indexOf("WHERE");
		if (nPosWhere != -1)
		{
			int nNb = getCountOfChar('#', nPosWhere);
			return nNb;
		}
		return 0;
	}

	private boolean canFillParam()
	{
		if (m_nNbWhereParamDeclared < m_nNbWhereParamToProvide)
			return true;
		return false; // TODO: Crash due to too many param provided ?
	}

	private int getNbIntoParam()
	{
		int nNbStar = 0;
		int nNbComma = 0;
		int nNbPoint = 0;
		int nNbOpenParenthesis = 0;
		int n = m_csQueryUpper.indexOf(' '); // Skip leadin select, insert, ...
		int nPosFrom = m_csQueryUpper.indexOf("FROM");
		while (n < nPosFrom)
		{
			char c = m_csQueryUpper.charAt(n);
			if (c == ',' && nNbOpenParenthesis == 0)
				nNbComma++;
			else if (c == '(')
				nNbOpenParenthesis++;
			else if (c == ')')
				nNbOpenParenthesis--;
			else if (c == '.')
				nNbPoint++;
			else if (c == '*' && nNbOpenParenthesis == 0) // Exclude (*) for
															// case of count(*)
															// or count( *)
				nNbStar++;
			n++;
		}
		if (nNbComma == 0 && nNbStar == 1 && nNbPoint == 0)
		{
			// The number of into is the number of tables
			int nNbInto = getNbTables();
			m_bOneStarOnly = true;
			return nNbInto;
		}

		return nNbComma + 1;
	}

	private int getNbTables()
	{
		int nPosFrom = m_csQueryUpper.indexOf("FROM");
		if (nPosFrom != -1)
		{
			String csTables = null;
			int nPosWhere = m_csQueryUpper.indexOf("WHERE");
			int nPosOrder = m_csQueryUpper.indexOf("ORDER");
			int nPosEnd = SQLTypeOperation.minPositive(nPosWhere, nPosOrder);			
			int nPosForUpdate = m_csQueryUpper.indexOf("FOR UPDATE");
			nPosEnd = SQLTypeOperation.minPositive(nPosEnd, nPosForUpdate);
			if (nPosEnd != -1)
				csTables = m_csQueryUpper.substring(nPosFrom, nPosEnd).trim();
			else
				csTables = m_csQueryUpper;

			int nNbTables = 1;
			for (int n = 0; n < csTables.length(); n++)
			{
				char c = csTables.charAt(n);
				if (c == ',')
					nNbTables++;
			}
			return nNbTables;
		}
		return 0;

	}

	/**
	 * @return Internal usage only
	 */
	private int getCountOfChar(char c, int nPosStart)
	{
		return getCountOfChar(c, nPosStart, m_csQuery.length());
	}

	/**
	 * @return Internal usage only
	 */
	private int getCountOfChar(char c, int nPosStart, int nPosEnd)
	{
		int nNb = 0;
		int nIndex = m_csQuery.indexOf(c, nPosStart);
		while (nIndex >= 0 && nIndex < nPosEnd)
		{
			nNb++;
			nIndex = m_csQuery.indexOf(c, nIndex + 1);
		}
		return nNb;
	}

	/**
	 * @return Internal usage only
	 */
	CSQLItem getParam(String csItemName)
	{
		CSQLItem Item = m_hashParam.get(csItemName.toUpperCase());
		return Item;
	}

	/**
	 * @return Internal usage only
	 */
	CSQLItem getCol(String csItemName)
	{
		CSQLItem Item = m_hashValue.get(csItemName.toUpperCase());
		return Item;
	}

	/**
	 * @return Internal usage only
	 */
	private CSQLPreparedStatement executePrepareSelect()
	{
		// m_nNbPrepare++;
		m_accountingRecordManager.startDbIO();
		CSQLPreparedStatement SQLStatement = (CSQLPreparedStatement) m_SQLConnection.prepareStatement(m_csQuery, m_nSuffixeHash, m_bHoldability);
		//SQLStatement.setSourceFileLine(m_csSourceFileLine);
		m_accountingRecordManager.endDbIO();

		if (SQLStatement != null)
		{
			// Set the parameters
			int nNbItemNames = m_arrMarkerNames.size();
			for (int nItemNames = 0; nItemNames < nNbItemNames; nItemNames++)
			{
				String csItemName = m_arrMarkerNames.get(nItemNames);

				CSQLItem item = getParam(csItemName);
				SQLStatement.setVarParamValue(this, nItemNames, item);
			}
			return SQLStatement;
		}
		return null;
	}

	/**
	 * @param SQLStatement
	 * @param arrIntoItems
	 * @return Internal usage only
	 */
	protected CSQLResultSet executeQueryAndFillInto(CSQLPreparedStatement SQLStatement, int nNbFetch)
	{
		// CSQLResultSet SQLResultSet =
		// SQLStatement.executeQueryAndFillInto(this, m_sqlStatus, arrIntoItems,
		// m_arrColSelectType, m_bOneStarOnly, m_accountingRecordManager,
		// m_hashParam, m_hashValue);
		CSQLResultSet SQLResultSet = SQLStatement.executeQueryAndFillInto(this, nNbFetch);
		return SQLResultSet;
	}

	/**
	 * @return Internal usage only
	 */
	private ArrayFix<String> findAndUpdateMarkers()
	{
		ArrayFixDyn<String> arrItemNames = new ArrayDyn<String>();

		// Replace #xx placeholdersd by ?
		int nPosStart = m_csQuery.indexOf('#', 0);
		while (nPosStart != -1)
		{
			String sLeft = m_csQuery.substring(0, nPosStart);
			int n = nPosStart;
			n++; // Skip the #
			String sItemId = extractItemId(n);
			if (sItemId != null)
			{
				n += sItemId.length();
				arrItemNames.add(sItemId);
				String sRight = m_csQuery.substring(n);
				m_csQuery = sLeft + "?" + sRight;
			}

			nPosStart = m_csQuery.indexOf('#', nPosStart);
		}
		m_csQueryUpper = m_csQuery.toUpperCase();

		// Compress array
		int nSize = arrItemNames.size();
		String arr[] = new String[nSize];
		arrItemNames.transferInto(arr);

		ArrayFix<String> arrFix = new ArrayFix<String>(arr);
		return arrFix;
	}

	/**
	 * @return Internal usage only
	 */
	String extractItemId(int nPos)
	{
		int nStart = nPos;
		int nLength = m_csQuery.length();
		char c = m_csQuery.charAt(nPos);
		while (Character.isLetterOrDigit(c))
		{
			nPos++;
			if (nPos == nLength)
			{
				String s = m_csQuery.substring(nStart);
				return s;
			}

			c = m_csQuery.charAt(nPos);
		}
		String s = m_csQuery.substring(nStart, nPos);
		return s;
	}

	/**
	 * @return Internal usage only
	 */
	private void executeInsert()
	{
		// m_nNbPrepare++;
		m_accountingRecordManager.startDbIO();
		CSQLPreparedStatement SQLStatement = (CSQLPreparedStatement) m_SQLConnection.prepareStatement(m_csQuery, m_nSuffixeHash, false);
		m_accountingRecordManager.endDbIO();

		if (SQLStatement != null)
		{
			// Set the Col values
			int nNbItemNames = m_arrMarkerNames.size();
			for (int nItemNames = 0; nItemNames < nNbItemNames; nItemNames++)
			{
				String csItemName = m_arrMarkerNames.get(nItemNames);

				CSQLItem param = getCol(csItemName);
				SQLStatement.setVarParamValue(this, nItemNames, param);
			}

			m_accountingRecordManager.startDbIO();
			SQLStatement.executeInsert(this);
			m_accountingRecordManager.endDbIO();
		}
	}

	/**
	 * @return Internal usage only
	 */
	private void executeUpdate()
	{
		m_accountingRecordManager.startDbIO();
		CSQLPreparedStatement SQLStatement = (CSQLPreparedStatement) m_SQLConnection.prepareStatement(m_csQuery, m_nSuffixeHash, false);
		m_accountingRecordManager.endDbIO();

		if (SQLStatement != null)
		{
			int nNbItemNames = m_arrMarkerNames.size();
			for (int nItemNames = 0; nItemNames < nNbItemNames; nItemNames++)
			{
				String csItemName = m_arrMarkerNames.get(nItemNames);

				CSQLItem param = getCol(csItemName);
				if (param == null) // item is not a col value
					param = getParam(csItemName); // it's maybe a param
				SQLStatement.setVarParamValue(this, nItemNames, param);
			}

			m_accountingRecordManager.startDbIO();
			SQLStatement.executeUpdate(this);
			m_accountingRecordManager.endDbIO();
		}
	}

	/**
	 * @return Internal usage only
	 */
	private void executeDelete()
	{
		m_accountingRecordManager.startDbIO();
		CSQLPreparedStatement SQLStatement = (CSQLPreparedStatement) m_SQLConnection.prepareStatement(m_csQuery, m_nSuffixeHash, false);
		m_accountingRecordManager.endDbIO();

		if (SQLStatement != null)
		{
			// Set the parameters
			int nNbItemNames = m_arrMarkerNames.size();
			for (int nItemNames = 0; nItemNames < nNbItemNames; nItemNames++)
			{
				String csItemName = m_arrMarkerNames.get(nItemNames);

				CSQLItem param = getParam(csItemName);
				SQLStatement.setVarParamValue(this, nItemNames, param);
			}

			m_accountingRecordManager.startDbIO();
			SQLStatement.executeDelete(this);
			m_accountingRecordManager.endDbIO();
		}
	}

	private void executeLock()
	{
		m_accountingRecordManager.startDbIO();
		CSQLPreparedStatement SQLStatement = (CSQLPreparedStatement) m_SQLConnection.prepareStatement(m_csQuery, m_nSuffixeHash, false);

		SQLStatement.executeLock(this);
		m_accountingRecordManager.endDbIO();
	}
	
	private void executeCreateTable()
	{
		m_accountingRecordManager.startDbIO();
		CSQLPreparedStatement SQLStatement = (CSQLPreparedStatement) m_SQLConnection.prepareStatement(m_csQuery, m_nSuffixeHash, false);

		SQLStatement.executeCreateTable(this);
		m_accountingRecordManager.endDbIO();
	}
	
	private void executeDropTable()
	{
		m_accountingRecordManager.startDbIO();
		CSQLPreparedStatement SQLStatement = (CSQLPreparedStatement) m_SQLConnection.prepareStatement(m_csQuery, m_nSuffixeHash, false);

		SQLStatement.executeDropTable(this);
		m_accountingRecordManager.endDbIO();
	}
	
	private void executeDeclareOrder()
	{
		m_accountingRecordManager.startDbIO();
		CSQLPreparedStatement SQLStatement = (CSQLPreparedStatement) m_SQLConnection.prepareStatement(m_csQuery, m_nSuffixeHash, false);

		SQLStatement.executeDeclareOrder(this);
		m_accountingRecordManager.endDbIO();
	}


	/**
	 * @return Internal usage only
	 */
	public CSQLResultSet executeQuery()
	{
		if (m_SQLTypeOperation == SQLTypeOperation.CursorSelect)
		{
			if (m_nNbWhereParamDeclared == m_nNbWhereParamToProvide) // All
																		// params
																		// have
																		// been
																		// filled
			{
				if (m_SQLCursorResultSet == null) // 1st step: all params have
													// been provided; must now
													// prepare the statement
				{
					m_accountingRecordManager.incCursorOpen();
					//JmxGeneralStat.incOpenCursor(1);
					CSQLPreparedStatement SQLStatement = executePrepareSelect();
					if (SQLStatement != null)
					{
						m_SQLCursorResultSet = SQLStatement.executeQueryCursor(this);
						manageSqlError();
					}
				}
				else if (m_nNbIntoParamToProvide == m_nNbIntoParamDeclared && m_SQLCursorResultSet != null) // All
																											// into
																											// have
																											// been
																											// specified
				{
					m_accountingRecordManager.incFetchCursor();
					//JmxGeneralStat.incFetchCursor(1);
					if (m_SQLCursorResultSet.next())					
						m_SQLCursorResultSet.fillIntoValues(this, true, m_bRowIdGenerated, m_nNbFetch);
					
					m_nNbFetch++;
					m_nNbIntoParamDeclared = 0; // no more into
					manageSqlError();
				}
				m_SQLConnection = null;
			}
		}
		else if (m_SQLTypeOperation == SQLTypeOperation.Select)
		{
			if (m_nNbWhereParamDeclared == m_nNbWhereParamToProvide) // All
																		// params
																		// have
																		// been
																		// filled
			{
				m_accountingRecordManager.incSelect();
				CSQLPreparedStatement SQLStatement = executePrepareSelect();
				if (SQLStatement != null)
				{
					m_SQLCursorResultSet = SQLStatement.executeQuery(this); // m_sqlStatus,
																			// m_arrColSelectType,
																			// m_accountingRecordManager,
																			// m_hashParam,
																			// m_hashValue);
					m_SQLConnection = null;
					manageSqlError();
					return m_SQLCursorResultSet;
				}
				m_SQLConnection = null;
			}
		}
		else if (m_SQLTypeOperation == SQLTypeOperation.Insert)
		{
			if (m_nNbColToSetToProvide == m_nNbColToSetDeclared)
			{
				m_accountingRecordManager.incInsert();
				executeInsert();
				manageSqlError();
				m_SQLConnection = null;
			}
		}
		else if (m_SQLTypeOperation == SQLTypeOperation.Update)
		{
			if (m_nNbWhereParamDeclared == m_nNbWhereParamToProvide && m_nNbColToSetDeclared == m_nNbColToSetToProvide)
			{
				m_accountingRecordManager.incUpdate();
				executeUpdate();
				manageSqlError();
				m_SQLConnection = null;
			}
		}
		else if (m_SQLTypeOperation == SQLTypeOperation.Delete)
		{
			if (m_nNbWhereParamDeclared == m_nNbWhereParamToProvide)
			{
				m_accountingRecordManager.incDelete();
				executeDelete();
				manageSqlError();
				m_SQLConnection = null;
			}
		}
		return null;
	}
	
	private void manageSqlError()
	{
		if(m_sqlStatus != null)
		{
			if (m_sqlStatus.isLastSQLCodeAnError())
			{
				throw new AbortSessionException();
			}
		}
	}

	public SQL onErrorGoto(Paragraph paragraphSQGErrorGoto)
	{
		m_errorManager.manageOnErrorGoto(paragraphSQGErrorGoto, m_sqlStatus);
		return this;
	}

	public SQL onErrorGoto(Section section)
	{
		m_errorManager.manageOnErrorGoto(section, m_sqlStatus);
		return this;
	}

	public SQL onErrorContinue()
	{
		m_errorManager.manageOnErrorContinue(m_sqlStatus);
		return this;
	}

	public SQL onWarningGoto(Paragraph paragraphSQGErrorGoto)
	{
		// TODO
		return this;
	}

	public SQL onWarningGoto(Section section)
	{
		// TODO
		return this;
	}

	public SQL onWarningContinue()
	{
		// TODO
		return this;
	}

	public DbConnectionBase getConnection()
	{
		return m_SQLConnection;
	}

	/**
	 * @return Internal usage only
	 */
	public boolean hasRowIdGenerated()
	{
		return m_bRowIdGenerated;
	}

	SQLRecordSetVarFiller getCachedRecordSetVarFiller(long lHashedId)
	{
		if(m_hashSqlRecordSetVarFiller != null)
			return m_hashSqlRecordSetVarFiller.get(lHashedId);
		return null;
	}

	void saveCachedRecordSetVarFiller(long lHashedId, SQLRecordSetVarFiller sqlRecordSetVarFiller)
	{
		if(m_hashSqlRecordSetVarFiller == null)
			m_hashSqlRecordSetVarFiller = new Hashtable<Long, SQLRecordSetVarFiller>();
		m_hashSqlRecordSetVarFiller.put(lHashedId, sqlRecordSetVarFiller);
		//m_sqlRecordSetVarFiller = sqlRecordSetVarFiller;
	}

	private Hashtable<Long, SQLRecordSetVarFiller> m_hashSqlRecordSetVarFiller = null; 

	public void close()
	{
		if (m_SQLCursorResultSet != null)
			m_SQLCursorResultSet.close();
	}

	private static int getSQLUniqueId()
	{
		return ms_threadSafeCounter.inc();
	}

	public String getQuery()
	{
		return m_csQuery;
	}

	public String getProgram()
	{
		if (m_programManager != null)
			return m_programManager.m_program.m_csSimpleName;
		return "@UnknownProgram";
	}

	void startDbIO()
	{
		m_accountingRecordManager.startDbIO();
	}

	void endDbIO()
	{
		m_accountingRecordManager.endDbIO();
	}

	boolean getOneStarOnlyMode()
	{
		return m_bOneStarOnly;
	}

	String getDebugParams()
	{
		return getDebugParamValue(m_hashParam);
	}

	String getDebugValues()
	{
		return getDebugParamValue(m_hashValue);
	}

	private String getDebugParamValue(HashMap<String, CSQLItem> map)
	{
		StringBuffer csBuffer = new StringBuffer();
		if (m_hashParam != null)
		{
			int n = 0;
			Set<Map.Entry<String, CSQLItem>> set = map.entrySet();
			Iterator<Map.Entry<String, CSQLItem>> iterMapEntry = set.iterator();
			while (iterMapEntry.hasNext())
			{
				Map.Entry<String, CSQLItem> mapEntry = iterMapEntry.next();
				CSQLItem item = mapEntry.getValue();
				String csKey = mapEntry.getKey();

				if (n != 0)
					csBuffer.append(",");
				csBuffer.append("(");
				csBuffer.append(csKey);
				csBuffer.append(":");
				csBuffer.append(item.getDebugValue());
				csBuffer.append(")");
				n++;
			}
		}
		return csBuffer.toString();
	}
	
	long getIntoAllVarsUniqueHashedId()
	{
		long l = 0;
		if(m_arrIntoItems != null)
		{
			for(int n=0; n<m_arrIntoItems.size(); n++)
			{
				CSQLIntoItem intoItem = m_arrIntoItems.get(n);
				l += n * 65536;
				l += intoItem.getUniqueHashedId();
			}
		}
		return l; 
	}
	
	protected CSQLResultSet m_SQLCursorResultSet = null;

	CSQLStatus m_sqlStatus = null;

	ArrayFixDyn<Integer> m_arrColSelectType = null;

	ArrayFixDyn<CSQLIntoItem> m_arrIntoItems = null; // Array of CSQLIntoItem

	private HashMap<String, CSQLItem> m_hashParam = null; // Hash of CSQLItem;
															// indexed on name

	private SQLTypeOperation m_SQLTypeOperation = null;

	private int m_nNbWhereParamToProvide = 0;

	private int m_nNbWhereParamDeclared = 0; // Number of where param
												// declared

	private int m_nNbIntoParamToProvide = 0; // Number of column "into" for
												// select

	private int m_nNbIntoParamDeclared = 0; // Number of into() methods
											// specified

	private int m_nNbColToSetToProvide = 0; // Number of column to insert

	private int m_nNbColToSetDeclared = 0; // Number of value() methods
											// specified for insert

	private HashMap<String, CSQLItem> m_hashValue = null; // Hash of CSQLItem
															// used for item to
															// insert; indexed
															// on name

	private ArrayFixDyn<String> m_arrMarkerNames = null;

	private boolean m_bOperationExecuted = false;

	private boolean m_bRowIdGenerated = false;

	private DbConnectionBase m_SQLConnection = null;

	String m_csQuery = null;
	private String m_csQueryUpper = null;

	private int m_nSQLUniqueId = 0; // Each unique SQL clause has it's own id

	private boolean m_bOneStarOnly = false;

	protected BaseProgramManager m_programManager = null;

	private static ThreadSafeCounter ms_threadSafeCounter = new ThreadSafeCounter();

	private String m_csExplainQuery = null;

	private boolean m_bHoldability = false;

	private SQLErrorManager m_errorManager = null;

	// private SQLMBeanCursor m_SQLMBeanCursor = null; // Must exists, as it
	// holds a ref on the bean
	// private SQLMBean m_SQLMBean = null; // Must exists, as it holds a ref on
	// the bean
	private boolean m_bReused = false;

	private int m_nSuffixeHash = 0;
	
	private int m_nNbFetch = 0;

	//private String m_csSourceFileLine = null;
}
