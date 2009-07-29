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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleResultSet;
import oracle.sql.ROWID;

import jlib.log.Log;
import jlib.misc.ArrayFixDyn;
import jlib.misc.DBIOAccounting;
import jlib.misc.DBIOAccountingType;
import jlib.misc.IntegerRef;
import jlib.sql.LogSQLException;
import nacaLib.base.CJMapObject;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.misc.SemanticContextDef;
import nacaLib.tempCache.TempCacheLocator;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarAndEdit;
import nacaLib.varEx.VarBase;

// PJD ROWID Support: import oracle.sql.ROWID;

public class CSQLResultSet extends CJMapObject
{
	private SQL m_sql = null;
	private String m_csQuery = null;
	private String m_csProgramName = null;
	
	public CSQLResultSet(ResultSet r, SemanticContextDef semanticContextDef, SQL sql)
	{
		m_sql = sql;
		m_csQuery = sql.m_csQuery;
		m_csProgramName = sql.getProgram();
		m_r = r;
		m_arrColSelectType = sql.m_arrColSelectType;
		m_sqlStatus = sql.m_sqlStatus;
	}
	
	protected CSQLStatus m_sqlStatus = null ;
	
	public boolean next()
	{
		if(m_r != null)
		{	
			try
			{
				if (m_r.next())
				{	
					return true;
				}	
				else
				{	
					if (m_sqlStatus != null)
						m_sqlStatus.setSQLCode(SQLCode.SQL_NOT_FOUND.getMainCode());
				}
			}
			catch (SQLException e)
			{
				if (e.getErrorCode() == -99999)
				{
					LogSQLException.log(e);
					BaseProgramLoader.logMail(m_csProgramName + " - JDBC warning", "Warning while executing CSQLResultSet::next() on result set for program="+m_csProgramName + ", clause="+m_csQuery, e);
					if (m_sqlStatus != null)
						m_sqlStatus.setSQLCode(SQLCode.SQL_NOT_FOUND.getMainCode());
				}
				else
				{
					manageSQLException(e);
				}
			}
		}
		return false;
	}
	
	public boolean isTheOnlyOne()
	{
		try
		{
			boolean bHasNext = m_r.next();
			if(bHasNext == false)
			{
				return true;
			}
			else
				return false;
		}
		catch (SQLException e)
		{
			LogSQLException.log(e);
		}
		return true;
	}
	
	private String getColName(int nColSourceIndex)
	{
		try
		{
			ResultSetMetaData rsMetaData = m_r.getMetaData();
			String csColName = rsMetaData.getColumnName(nColSourceIndex);
			return csColName;
		}
		catch (SQLException e)
		{
			LogSQLException.log(e);
		}
		return "";
	}
	
	private String getTableColName(int nColSourceIndex)
	{
		// DB2 JDBC Driver supports rsMetaData.getTableName(nColSourceIndex); See http://publib.boulder.ibm.com/infocenter/db2help/index.jsp?topic=/com.ibm.db2.udb.doc/ad/rjvjdapi.htm
		try
		{
			ResultSetMetaData rsMetaData = m_r.getMetaData();
			String csTableName = rsMetaData.getTableName(nColSourceIndex);
			String csColName = rsMetaData.getColumnName(nColSourceIndex);
			String csTableColName = SemanticContextDef.getTableColName(csTableName, csColName);
			return csTableColName;
		}
		catch (SQLException e)
		{
			LogSQLException.log(e);
		}
		return "";
	}
	
	private boolean setIntoRowIdGenerated(CSQLIntoItem sqlIntoItem, SQLRecordSetVarFiller sqlRecordSetVarFiller)
	{
		try
		{
			ROWID rowId = ((OracleResultSet)m_r).getROWID(1);
			sqlIntoItem.setRowIdValue(rowId);
			return true;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private void setInto(int nColSource, CSQLIntoItem sqlIntoItem, SQLRecordSetVarFiller sqlRecordSetVarFiller)
	{
		VarAndEdit varInto = sqlIntoItem.getVarInto();
		if(varInto == null)	// null for entries created by missingFetchVariables()
			return ;
		
		if(sqlRecordSetVarFiller != null)
			sqlRecordSetVarFiller.addLinkColDestination(nColSource, varInto, sqlIntoItem.getVarIndicator());
		
		boolean bNull = fillColValue(nColSource, varInto, sqlRecordSetVarFiller.getRecordSetCacheColTypeType());
		sqlIntoItem.setColValueNull(bNull);
		if (bNull && sqlIntoItem.getVarIndicator() == null)
		{
			m_bNullError = true;
		}
	}
	
	boolean m_bNullError = false;
	
	boolean fillColValue(int nColSourceIndex0Based, VarBase varInto, RecordSetCacheColTypeType recordSetCacheColTypeType)
	{
		int nColSourceIndex = nColSourceIndex0Based +1; 
		RecordColTypeManagerBase baseRecordColTypeManager = recordSetCacheColTypeType.getRecordColTypeManager(nColSourceIndex0Based);
		if(baseRecordColTypeManager != null)
		{
			return baseRecordColTypeManager.fillColValue(m_r, varInto);
		}		
		else
		{			
			try
			{
				ResultSetMetaData rsMetaData = m_r.getMetaData();
				String csColTypeName = rsMetaData.getColumnTypeName(nColSourceIndex);
				if(csColTypeName.equals("CHAR"))
				{
					baseRecordColTypeManager = new RecordColTypeManagerChar(nColSourceIndex);
					recordSetCacheColTypeType.set(nColSourceIndex0Based, baseRecordColTypeManager);
				}
				else if(csColTypeName.equals("DECIMAL"))
				{
					
					int nPrecision = rsMetaData.getPrecision(nColSourceIndex);
					int nScale = rsMetaData.getScale(nColSourceIndex);
					if(nScale == 0)	// No digits behind comma (integer value)
					{
						if(nPrecision <= 8)	// Fits within an int
						{
							baseRecordColTypeManager = new RecordColTypeManagerDecimalInt(nColSourceIndex);
							recordSetCacheColTypeType.set(nColSourceIndex0Based, baseRecordColTypeManager);
						}
						else	// A long is needed
						{
							baseRecordColTypeManager = new RecordColTypeManagerDecimalLong(nColSourceIndex);
							recordSetCacheColTypeType.set(nColSourceIndex0Based, baseRecordColTypeManager);
						}
					}
					else	// Digits are behind the comma
					{
						baseRecordColTypeManager = new RecordColTypeManagerDecimal(nColSourceIndex);
						recordSetCacheColTypeType.set(nColSourceIndex0Based, baseRecordColTypeManager);
					}
				}
				else if(csColTypeName.equals("INTEGER"))
				{
					int nPrecision = rsMetaData.getPrecision(nColSourceIndex);
					if(nPrecision <= 8)	// Fits within an int
					{
						baseRecordColTypeManager = new RecordColTypeManagerDecimalInt(nColSourceIndex);
						recordSetCacheColTypeType.set(nColSourceIndex0Based, baseRecordColTypeManager);
					}
					else	// A long is needed
					{
						baseRecordColTypeManager = new RecordColTypeManagerDecimalLong(nColSourceIndex);
						recordSetCacheColTypeType.set(nColSourceIndex0Based, baseRecordColTypeManager);
					}
//
//					baseRecordColTypeManager = new RecordColTypeManagerDecimal(nColSourceIndex);
//					recordSetCacheColTypeType.set(nColSourceIndex0Based, baseRecordColTypeManager);
				}
				else if(csColTypeName.equals("TIMESTAMP"))
				{
					baseRecordColTypeManager = new RecordColTypeManagerTimestamp(nColSourceIndex);
					recordSetCacheColTypeType.set(nColSourceIndex0Based, baseRecordColTypeManager);
				}
				else if(csColTypeName.equals("TIMESTAMPTZ") || csColTypeName.equals("TIMESTAMP WITH TIME ZONE"))
				{
					baseRecordColTypeManager = new RecordColTypeManagerTimestamp(nColSourceIndex);
					recordSetCacheColTypeType.set(nColSourceIndex0Based, baseRecordColTypeManager);
				}
				else if(csColTypeName.equals("VARCHAR") || csColTypeName.equals("LONG VARCHAR") || csColTypeName.equals("LONG"))	// LONG is for ORACLE Support
				{
					baseRecordColTypeManager = new RecordColTypeManagerVarchar(nColSourceIndex);
					recordSetCacheColTypeType.set(nColSourceIndex0Based, baseRecordColTypeManager);
				}
				else if(csColTypeName.equals("DATE"))
				{
					baseRecordColTypeManager = new RecordColTypeManagerDate(nColSourceIndex);
					recordSetCacheColTypeType.set(nColSourceIndex0Based, baseRecordColTypeManager);
				}
				else if(csColTypeName.equals("SMALLINT"))
				{
					baseRecordColTypeManager = new RecordColTypeManagerDecimalInt(nColSourceIndex);
					recordSetCacheColTypeType.set(nColSourceIndex0Based, baseRecordColTypeManager);
				}
				else if(csColTypeName.equals("BLOB"))
				{
					baseRecordColTypeManager = new RecordColTypeManagerOther(nColSourceIndex);
					recordSetCacheColTypeType.set(nColSourceIndex0Based, baseRecordColTypeManager);
				}
				else
				{
					baseRecordColTypeManager = new RecordColTypeManagerOther(nColSourceIndex);
					recordSetCacheColTypeType.set(nColSourceIndex0Based, baseRecordColTypeManager);
				}
			}
			catch (SQLException e)
			{
				LogSQLException.log(e);	// Unkown col type ! 
			}
			return baseRecordColTypeManager.fillColValue(m_r, varInto);
		}
	}	

	public ResultSet getResultSet()
	{
		return m_r ;
	}
	
	private boolean isSelectStar(int nColDest)	// Select * From ...
	{
		if(m_arrColSelectType != null)
		{
			for(int n=0; n<m_arrColSelectType.size(); n++)
			{
				Integer iColId = m_arrColSelectType.get(n);
				if(iColId.intValue() == nColDest)
					return true;
			}
		}
		return false;
	}
	
	private ArrayFixDyn<Integer> m_arrColSelectType = null;	// hash table of boolean, indexed by col id, indexed based 0
	//private SemanticContextDef m_semanticContextDef = null;
	
	private int getRecordSetColumnCount()
	{
		try
		{
			return m_r.getMetaData().getColumnCount();
		}
		catch(SQLException e)
		{
			LogSQLException.log(e);
			return 0;
		}
	}

	public void fillIntoValues(SQL sql, boolean bCursor, boolean bRowIdGenerated, int nNbFetch)
	{		
		if(BaseResourceManager.ms_bUseVarFillCache)
		{
			long lIntoHash = sql.getIntoAllVarsUniqueHashedId();
			
			boolean b = false;
			SQLRecordSetVarFiller sqlRecordSetVarFiller = sql.getCachedRecordSetVarFiller(lIntoHash);
			if(sqlRecordSetVarFiller != null)
			{
				if(nNbFetch == 0)	// Check number of columns only at 1st fetch execution
				{
					int nNbColCached = sqlRecordSetVarFiller.getNbCol();						
					int nNbColResultSet = getRecordSetColumnCount();
					if(nNbColResultSet == nNbColCached)
						b = true;
				}
				else
					b = true;
			}
			if(b)
			{	
				sqlRecordSetVarFiller.apply(this);
				
				if(bRowIdGenerated)	// 1st col is the rowid
				{
					if(sql.m_arrIntoItems.size() > 0)
					{
						CSQLIntoItem sqlIntoItem = sql.m_arrIntoItems.get(0);
						setIntoRowIdGenerated(sqlIntoItem, sqlRecordSetVarFiller);
					}
				}
				
				manageSQLCode(bCursor);
			}
			else
			{
				sqlRecordSetVarFiller = null;
				sqlRecordSetVarFiller = new SQLRecordSetVarFiller();
				doFillIntoValues(sql, bCursor, bRowIdGenerated, sqlRecordSetVarFiller);
				sql.saveCachedRecordSetVarFiller(lIntoHash, sqlRecordSetVarFiller);
			}
		}
		else
		{
			doFillIntoValues(sql, bCursor, bRowIdGenerated, null);
		}
	}
	
	private void manageSQLCode(boolean bCursor)
	{
		if (m_bNullError)
		{
			m_sqlStatus.setSQLCode(SQLCode.SQL_VALUE_NULL.getMainCode());
		}
		else if (bCursor)
		{
			m_sqlStatus.setSQLCode(SQLCode.SQL_OK.getMainCode());
		}
		else
		{
			if(isTheOnlyOne())
				m_sqlStatus.setSQLCode(SQLCode.SQL_OK.getMainCode()) ;
			else
				m_sqlStatus.setSQLCode(SQLCode.SQL_MORE_THAN_ONE_ROW.getMainCode()) ;
		}
	}
	
	private void doFillIntoValues(SQL sql, boolean bCursor, boolean bRowIdGenerated, SQLRecordSetVarFiller sqlRecordSetVarFiller)
	{
		BaseProgramManager programManager = TempCacheLocator.getTLSTempCache().getProgramManager();
		
		int nNbColDest = sql.m_arrIntoItems.size();
		
		// Consume leading and ending unitary columns; a select with * must follow the syntax: Select [col]*, [*]*, [col]* from ...
		// There cannot be unique cols between stars: that is select toto, *, titi, *, tutu is illegal.
		// There can be select toto, A.*, B.*, c from ...
		boolean bSkippedStar = false;
		int nNbcolUnitaryLeft = 0;
		int nNbcolUnitaryRight = 0;
		
		int nNbColInRecordSet = getRecordSetColumnCount();
		if(sqlRecordSetVarFiller == null)
			return ;
		sqlRecordSetVarFiller.setNbCol(nNbColInRecordSet);
		
		if(!sql.getOneStarOnlyMode())	// we do not a select * from ...
		{
			if(m_arrColSelectType != null && m_arrColSelectType.size() > 0)	// We have at least a star
			{
				for(int nColDest = 0; nColDest<nNbColDest; nColDest++)
				{
					if(isSelectStar(nColDest))	// The nth col is a star (Select * From ...)
						bSkippedStar = true;
					else
					{	
						if(bSkippedStar)
							nNbcolUnitaryRight++;
						else
							nNbcolUnitaryLeft++;
					}
				}
			}
			else	//	No star
			{
				nNbcolUnitaryLeft = nNbColDest;
			}
		}			
		else
		{
			bSkippedStar = true;
		}

		if(bRowIdGenerated)	// 1st col is the rowid
		{
			if(sql.m_arrIntoItems.size() > 0)
			{
				CSQLIntoItem sqlIntoItem = sql.m_arrIntoItems.get(0);
				setIntoRowIdGenerated(sqlIntoItem, sqlRecordSetVarFiller);
			}
		}
		
		// Unitary cols on the left
		//Var2 varIntoDest = null;
		for(int nColDest=0; nColDest<nNbcolUnitaryLeft; nColDest++)
		{
			CSQLIntoItem sqlIntoItem = sql.m_arrIntoItems.get(nColDest);
			setInto(nColDest, sqlIntoItem, sqlRecordSetVarFiller); 
		}

		// Unitary cols on the right
		int nNbColsDest = sql.m_arrIntoItems.size();
		int nColRecordSetCurrent = nNbColInRecordSet-1;
		for(int nColDest=nNbColsDest-1; nColDest>=nNbColsDest-nNbcolUnitaryRight; nColDest--)
		{
			CSQLIntoItem sqlIntoItem = sql.m_arrIntoItems.get(nColDest);
			setInto(nColRecordSetCurrent, sqlIntoItem, sqlRecordSetVarFiller);
			nColRecordSetCurrent--;
		}	
		
		RecordSetCacheColTypeType recordSetCacheColTypeType = null;
		if(sqlRecordSetVarFiller != null)
			recordSetCacheColTypeType = sqlRecordSetVarFiller.getRecordSetCacheColTypeType();
		
		if(bSkippedStar)
		{
			ArrayList<VarBase> arrChildrenFilled = new ArrayList<VarBase>();
			//int nDestinationNumber = 1;
			IntegerRef rnChildIndex = new IntegerRef();
			for(int nColRecordSet=nNbcolUnitaryLeft; nColRecordSet<nNbColInRecordSet-nNbcolUnitaryRight; nColRecordSet++)	// enum all varing length col form the record set
			{
				rnChildIndex.set(-1);
				String csColName = getColName(nColRecordSet+1);
				for(int nColDest=nNbcolUnitaryLeft; nColDest<nNbColsDest-nNbcolUnitaryRight; nColDest++)	// Enum all groups
				{					
					CSQLIntoItem sqlIntoItem = sql.m_arrIntoItems.get(nColDest);
					VarAndEdit varDestParent = sqlIntoItem.getVarInto();
					if(varDestParent == null)	// null for entries created by missingFetchVariables()
						continue ;
						
					Var varDestIndicatorParent = sqlIntoItem.getVarIndicator();
					 
					VarBase varChild = varDestParent.getUnprefixNamedVarChild(programManager, csColName, rnChildIndex);
					if(varChild == null)
					{
						String csPrefixedColName = csColName;
						varChild = varDestParent.getUnDollarUnprefixNamedChild(programManager, csPrefixedColName, rnChildIndex);
					}
					if(varChild != null)
					{
						boolean bChildAlreadyFilled = isChilddAlreadyFilled(varChild, arrChildrenFilled);	// Fill a child only once
						if(!bChildAlreadyFilled)
						{
							Var varIndicator = null;
							if(varDestIndicatorParent != null)
							{
								int nDestinationNumber = rnChildIndex.get();
								if(nDestinationNumber >= 0)	// found the index of the destination column; it's the same as the var indicator  
									varIndicator = varDestIndicatorParent.getAt(nDestinationNumber+1);	// 1 based
							}

//							String csValue = getColValueAsString(nColRecordSet, recordSetCacheColTypeType);
//							varChild.set(csValue);
							fillColValue(nColRecordSet, varChild, recordSetCacheColTypeType);
							
							//System.out.println("varChild filled="+varChild.toString());
							
//							if(m_semanticContextDef != null)
//							{
//								String csSemanticContext = m_semanticContextDef.getSemanticContextValueDefinition(csTableColName);
//								varChild.setSemanticContextValue(csSemanticContext);
//							}
							
							arrChildrenFilled.add(varChild);
							
							if(sqlRecordSetVarFiller != null)
								sqlRecordSetVarFiller.addLinkColDestination(nColRecordSet, varChild, varIndicator);
							
							if(isLogSql)
								Log.logDebug("sql into filling var="+varChild.getLoggableValue());
							break;
						}
					}
				}
			}
		}		
		manageSQLCode(bCursor);
		recordSetCacheColTypeType.compress();
		sqlRecordSetVarFiller.compress();
	}
	
	private boolean isChilddAlreadyFilled(VarBase varChild, ArrayList arrChildrenFilled)
	{
		int nNbChildren = arrChildrenFilled.size();
		for(int n=0; n<nNbChildren; n++)
		{
			VarBase var = (VarBase)arrChildrenFilled.get(n);
			if(var == varChild)
				return true;
		}
		return false;
	}
	
	String getCursorName()
	{
		try
		{
			if(m_r != null)
				return m_r.getCursorName();
		}
		catch(SQLException e)
		{
			LogSQLException.log(e);
		}
		return null;
	}
		
	private ResultSet m_r = null;	

	public String getString(String string)
	{
		try
		{
			return m_r.getString(string);
		}
		catch (SQLException e)
		{
			LogSQLException.log(e);
			return  null ;
		}
	}

	public int getInt(int i)
	{
		try
		{
			return m_r.getInt(i);
		}
		catch (SQLException e)
		{
			LogSQLException.log(e);
			return  0 ;
		}
	}

	public int getInt(String string)
	{
		try
		{
			return m_r.getInt(string);
		}
		catch (SQLException e)
		{
			LogSQLException.log(e);
			return  0 ;
		}
	}

	public void close()
	{
		if(m_r == null)
			return ;
		try
		{
			DBIOAccounting.startDBIO(DBIOAccountingType.CloseResultset);
			m_r.close() ;
			DBIOAccounting.endDBIO();
			m_r = null;
		} 
		catch (SQLException e)
		{
			DBIOAccounting.endDBIO();
			LogSQLException.log(e);
			e.printStackTrace();
		}
	}
	
	private void manageSQLException(SQLException e)
	{
		if(m_sqlStatus != null)
		{
			m_sqlStatus.setSQLCode("next", e, m_csQuery, m_sql) ;
			m_sqlStatus.fillLastSQLCodeErrorText();
		}
	}
}
