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
import java.sql.Timestamp;
import java.sql.Types;

import jlib.log.Log;
import jlib.misc.CurrentDateInfo;
import jlib.misc.StringUtil;
import jlib.sql.LogSQLException;
import jlib.sql.OracleColumnDefinition;
import jlib.sql.SQLColumnType;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.exceptions.AbortSessionException;
import oracle.jdbc.OracleParameterMetaData;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.NUMBER;
import oracle.sql.ROWID;

public class CSQLPreparedStatementOracle extends CSQLPreparedStatement
{
	CSQLPreparedStatementOracle()
	{
		super();		
	}
	
//	public void setVarParamValue(SQL sql, int nMarkerIndex, CSQLItem param)
//	{
//		int ggg = 0;
//		try
//		{
//			//m_PreparedStatement.setObject(nMarkerIndex+1, "52973357", OracleTypes.FIXED_CHAR);
//			m_PreparedStatement.setObject(nMarkerIndex+1, , OracleTypes.FIXED_CHAR);
////			if(nMarkerIndex == 0)
////				m_PreparedStatement.setInt(nMarkerIndex+1, 		);
////			else
////				m_PreparedStatement.setInt(nMarkerIndex+1, 801);
//		}
//		catch (SQLException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
//	private void setTypedSQLColumnValue(SQL sql, int nMarkerIndex, CSQLItem param)
//	{
//		OraclePreparedStatement oPreparedStatement = (OraclePreparedStatement)m_PreparedStatement;
//		try
//		{
//			if(param.isSQLNull())
//			{
//				if(nMarkerIndex == 0 && param.isRowIdContainer())
//				{
//					ROWID rowId = param.getRowIdValue();
//					oPreparedStatement.setROWID(1, rowId);
//					return ;
//				}		
//				
//				SQLColumnType sqlColumnType = param.getSQLColumnType();
//				if(sqlColumnType == SQLColumnType.Char)
//					oPreparedStatement.setNull(nMarkerIndex+1, Types.CHAR);
//				else if(sqlColumnType == SQLColumnType.Number)
//					oPreparedStatement.setNull(nMarkerIndex+1, Types.NUMERIC);
//				else if(sqlColumnType == SQLColumnType.Date)
//					oPreparedStatement.setNull(nMarkerIndex+1, Types.DATE);
//				else if(sqlColumnType == SQLColumnType.Timestamp6)
//					oPreparedStatement.setNull(nMarkerIndex+1, Types.TIMESTAMP);
//				else if(sqlColumnType == SQLColumnType.Varchar2)
//					oPreparedStatement.setNull(nMarkerIndex+1, Types.VARCHAR);
//			}
//			else
//			{
//				String sTrimmed = param.getValue();
//				if(BaseResourceManager.isUpdateCodeJavaToDb())
//					sTrimmed = BaseResourceManager.updateCodeJavaToDb(sTrimmed);
//				
//				
//				oPreparedStatement.setObject(nMarkerIndex+1, sTrimmed, OracleTypes.FIXED_CHAR);
//				
//			}
//		}
//		catch (SQLException e)
//		{
//			LogSQLException.log(e);
//			sql.m_sqlStatus.setSQLCode("setTypedSQLColumnValue", e, m_csQueryString, sql);
//		}
//	}
	
	private void abort(String cs)
	{
		Log.logCritical(cs);
		
		AbortSessionException abortSessionException = new AbortSessionException();
		throw(abortSessionException);
	}
	
	private void setVarParamValueForColumnType(SQL sql, int nColIndex, CSQLItem param, OraclePreparedStatement oPreparedStatement, OracleColumnDefinition oracleColumnDefinition)
	{
		SQLColumnType sqlColumnType = oracleColumnDefinition.getColumnType();
		if(param.isSQLNull())	// Manages colums declared as SQL NULL
		{
			int nOracleType = sqlColumnType.getOracleType();
			try
			{
				oPreparedStatement.setNull(nColIndex, nOracleType);
			}
			catch(Exception e)
			{
				abort("Cannot set SQL NULL value for oracle type " + nOracleType + " into column index " + nColIndex + " For statement " + sql.toString());
			}
			return ;
		}
		else
		{
			String sTrimmed = param.getValue();
			if(StringUtil.isEmpty(sTrimmed))
				abort("Cannot bind NULL String value into column index " + nColIndex + " For statement " + sql.toString());
							
			if(BaseResourceManager.isUpdateCodeJavaToDb())
				sTrimmed = BaseResourceManager.updateCodeJavaToDb(sTrimmed);
	
			if(sqlColumnType == SQLColumnType.Char)
			{
				// No management of SQL NULL Values
				// Keep left most chars
				int nColumLength = oracleColumnDefinition.getLength();
				if(sTrimmed.length() > nColumLength)
				{
					Log.logImportant("Severe warning: Char value too large for SQL Column; Only leftmost chars are kept; sql=" + sql.toString());						
					sTrimmed = sTrimmed.substring(0, nColumLength);
				}
				try
				{
					oPreparedStatement.setObject(nColIndex, sTrimmed, SQLColumnType.Char.getOracleType());
				}
				catch(Exception e)
				{
					abort("Cannot bind CHAR Value " + sTrimmed + " into column index " + nColIndex + " For statement " + sql.toString());
				}
				return ;
			}
			else if(sqlColumnType == SQLColumnType.Number)
			{
				// No management of SQL NULL Values
				// Also keep left most chars
				int nColumLength = oracleColumnDefinition.getLength();
				if(sTrimmed.length() > nColumLength)
				{
					sTrimmed = sTrimmed.substring(0, nColumLength);
					Log.logImportant("Severe warning: Number value too large for SQL Column; sql=" + sql.toString());
				}
				//int nStep = 0;
				try
				{
					int nScale = oracleColumnDefinition.getScale();
					//oPreparedStatement.setObject(nColIndex, sTrimmed, SQLColumnType.Number.getOracleType());	// Doesn't wor is sTtrimmed == '2' on SodiFRance Oracle server. Works on Consultas' one ... 
					// Try by converting explicitly the value as an Oracle propriary type NUMBER	
					NUMBER number = new NUMBER(sTrimmed, nScale); 	// See http://www.exciton.cs.rice.edu/javaresources/Oracle/oracle/sql/NUMBER.html#NUMBER(java.lang.String,%20int)
//					nStep = 1;	// Debug ...
					oPreparedStatement.setNUMBER(nColIndex, number);
//					nStep = 2;
//					String cs = number.stringValue();
//					nStep = 3;
//					if(cs == null)
//						Log.logCritical("Warning while Binding NUMBER Value, String representation is NULL: sTrimmed=" + sTrimmed + "; number=NULL into column index " + nColIndex + " For statement " + sql.toString() + "; Step="+nStep);							
//					else if(!cs.equalsIgnoreCase(sTrimmed))
//						Log.logCritical("Warning while Binding NUMBER Value, String representation changes: sTrimmed=" + sTrimmed + "; number="+cs + "; into column index " + nColIndex + " For statement " + sql.toString() + "; Step="+nStep);							
//					nStep = 4;
				}
				catch(Exception e)
				{
					abort("Cannot bind NUMBER Value " + sTrimmed + " into column index " + nColIndex + " For statement " + sql.toString());	// + "; Step="+nStep);
				}
				return ;
			}
			else if(sqlColumnType == SQLColumnType.Date)
			{
				if(StringUtil.isEmptyOrOnlyWhitespaces(sTrimmed))	// Date filled with spaces: Set the column to SQL NULL
				{
					try
					{
						oPreparedStatement.setNull(nColIndex, SQLColumnType.Date.getOracleType());	
					}
					catch(Exception e)
					{
						abort("Cannot bind SQL NULL Date value into column index " + nColIndex + " For statement " + sql.toString());
					}
				}
				else
				{
					if(!isWrappedByFunction(nColIndex, "TO_DATE"))
					{
						CurrentDateInfo cd = new CurrentDateInfo();					
						cd.setDateYYYY_MM_DD(sTrimmed);
						long lValue = cd.getTimeInMillis();				
						Date date = new Date(lValue);
						try
						{
							oPreparedStatement.setDate(nColIndex, date);
						}
						catch(Exception e)
						{
							abort("Cannot bind Date value " + sTrimmed + " into column index " + nColIndex + " For statement " + sql.toString());
						}
					}
					else
					{
						try
						{
							oPreparedStatement.setObject(nColIndex, sTrimmed, SQLColumnType.Char.getOracleType());	
						}
						catch(Exception e)
						{
							abort("Cannot bind Char Value (as TO_DATE argument) " + sTrimmed + " into column index " + nColIndex + " For statement " + sql.toString());
						}
					}
				}
				return ;
			}
			else if(sqlColumnType == SQLColumnType.Timestamp6)
			{
				if(StringUtil.isEmptyOrOnlyWhitespaces(sTrimmed))	// Timestamp filled with spaces: Set the column to SQL NULL
				{
					try
					{
						oPreparedStatement.setNull(nColIndex, SQLColumnType.Timestamp6.getOracleType());
					}
					catch(Exception e)
					{
						abort("Cannot bind SQL NULL Timestamp6 value into column index " + nColIndex + " For statement " + sql.toString());
					}
				}
				else
				{
					CurrentDateInfo cd = new CurrentDateInfo();
					Timestamp ts = cd.fillTimestamp(sTrimmed);
					try
					{
						oPreparedStatement.setTimestamp(nColIndex, ts);
					}
					catch(Exception e)
					{
						abort("Cannot bind Timestamp6 value" + sTrimmed + " into column index " + nColIndex + " For statement " + sql.toString());
					}
				}
				return ;
			}
			
			// Normal case
			int nOracleType = sqlColumnType.getOracleType();
			try
			{
				oPreparedStatement.setObject(nColIndex, sTrimmed, nOracleType);
			}
			catch(Exception e)
			{
				abort("Cannot bind Value (as oracle type (" + nOracleType + ")) " + sTrimmed + " into column index " + nColIndex + " For statement " + sql.toString());
			}
		}
	}
	
	public void setVarParamValue(SQL sql, String csSharpName, int nMarkerIndex, CSQLItem param, PreparedStmtColumnTypeManager preparedStmtColumnTypeManager)
	{	
		if(m_PreparedStatement == null)
			return ;
//		if(param.isKnownSQLColumnType())
//		{
//			setTypedSQLColumnValue(sql, nMarkerIndex, param);
//			return ;
//		}
		
		int nColIndex = nMarkerIndex+1;
		
		try
		{
			OraclePreparedStatement oPreparedStatement = (OraclePreparedStatement)m_PreparedStatement;
			//String tcs[] = oPreparedStatement.getRegisteredTableNames();
			//OracleParameterMetaData parameterMetaData = (OracleParameterMetaData )oPreparedStatement.getParameterMetaData();
			//int n = parameterMetaData.getParameterCount();
			//String csName = parameterMetaData.getParameterClassName(nColIndex);
			//String cs = parameterMetaData.getParameterTypeName(nColIndex);
			//int nColSQLType = parameterMetaData.getParameterType(nColIndex);
			
			if(param.isRowIdContainer())
			{
				ROWID rowId = param.getRowIdValue();
				oPreparedStatement.setROWID(nColIndex, rowId);
				return ;
			}
			
//			OracleColumnDefinition oracleColumnDefinition = null; 
//			PreparedStmtColumnTypeManager preparedStmtColumnTypeManager = getColumnTypeManager();
//			if(preparedStmtColumnTypeManager != null)
//				oracleColumnDefinition = preparedStmtColumnTypeManager.getOracleColumnDefinition(sql.getConnection(), csSharpName);

			OracleColumnDefinition oracleColumnDefinition = null;
			if(preparedStmtColumnTypeManager != null)
				oracleColumnDefinition = preparedStmtColumnTypeManager.getOracleColumnDefinition(sql.getConnection(), csSharpName); 
			
			if(oracleColumnDefinition != null)   
			{
				setVarParamValueForColumnType(sql, nColIndex, param, oPreparedStatement, oracleColumnDefinition);
			}
			else	// Unknown column's type; Should never be run when every statement is parsed
			{
				if(param.isSQLNull())
				{
					oPreparedStatement.setNull(nColIndex, OracleTypes.FIXED_CHAR);	// Default value; may be wrong 
				}
				else
				{
					String sTrimmed = param.getValue();
					if(!StringUtil.isEmpty(sTrimmed))
					{
						if(BaseResourceManager.isUpdateCodeJavaToDb())
							sTrimmed = BaseResourceManager.updateCodeJavaToDb(sTrimmed);
					
						if(sTrimmed.length() == 26)
						{
							if(isTimeStamp(sTrimmed))
							{
								if(!isWrappedByFunction(nColIndex, "TO_TIMESTAMP"))
								{
									CurrentDateInfo cd = new CurrentDateInfo();
									Timestamp ts = cd.fillTimestamp(sTrimmed);
									oPreparedStatement.setTimestamp(nColIndex, ts);
									return ;
								}
							}
						}
						else if(sTrimmed.length() == 10)
						{
							if(isDate(sTrimmed))
							{
								if(!isWrappedByFunction(nColIndex, "TO_DATE"))
								{
									CurrentDateInfo cd = new CurrentDateInfo();
									cd.setDateYYYY_MM_DD(sTrimmed);
									long lValue = cd.getTimeInMillis();				
									Date date = new Date(lValue);	
									oPreparedStatement.setDate(nColIndex, date);
									return ;
								}
							}
						}
						oPreparedStatement.setObject(nColIndex, sTrimmed, OracleTypes.FIXED_CHAR);
					}
					else
						abort("Error: Cannot bind NULL String into parameter "+nColIndex + "; Sql="+sql.toString());					
				}
			}
		}
		catch (SQLException e)
		{
			LogSQLException.log(e);
			sql.m_sqlStatus.setSQLCode("setVarParamValue", e, m_csQueryString/*, m_csSourceFileLine*/, sql);
		}
	}
	
	private boolean isTimeStamp(String cs)
	{
//		OracleParameterMetaData parameterMetaData = (OracleParameterMetaData)m_PreparedStatement.getParameterMetaData();
//		String csColTypeName = parameterMetaData.getParameterClassName(1);
//		int n = parameterMetaData.getParameterCount();
//		int nColSQLType = parameterMetaData.getParameterType(2);	//nMarkerIndex+1);
//		if(nColSQLType == Types.TIMESTAMP) 
//		{
//			CurrentDateInfo cd = new CurrentDateInfo();
//			Timestamp ts = cd.fillTimestamp(sTrimmed);
//			m_PreparedStatement.setTimestamp(nMarkerIndex+1, ts);
//		}
//		else
//			m_PreparedStatement.setObject(nMarkerIndex+1, sTrimmed);
		// time stamp is formatted like YYYY-MM-DD-HH.SS-MM.FFFFFF
		if(cs.charAt(4) == '-' && cs.charAt(7) == '-' && cs.charAt(10) == '-' && cs.charAt(13) == '.' && cs.charAt(16) == '.' && cs.charAt(19) == '.')
		{
			char c = cs.charAt(0);
			if(c < '1' || c > '2')
				return false;
			c = cs.charAt(1);
			if(c < '0' || c > '9')
				return false;
			c = cs.charAt(2);
			if(c < '0' || c > '9')
				return false;
			c = cs.charAt(3);
			if(c < '0' || c > '9')
				return false;
			c = cs.charAt(5);	// M1
			if(c < '0' || c > '1')
				return false;
			c = cs.charAt(6);	// M2
			if(c < '0' || c > '9')
				return false;
			c = cs.charAt(8);	// D1
			if(c < '0' || c > '3')
				return false;
			c = cs.charAt(9);	// D2
			if(c < '0' || c > '9')
				return false;
			
			c = cs.charAt(11);	// H1
			if(c < '0' || c > '2')
				return false;
			c = cs.charAt(12);	// H2
			if(c < '0' || c > '9')
				return false;
			c = cs.charAt(14);	// M1
			if(c < '0' || c > '6')
				return false;
			c = cs.charAt(15);	// M2
			if(c < '0' || c > '9')
				return false;
			c = cs.charAt(17);	// S1
			if(c < '0' || c > '6')
				return false;
			c = cs.charAt(18);	// S2
			if(c < '0' || c > '9')
				return false;
			return true;
			}
		return false;		
	}
	
	// Date are YYYY-MM-DD
	private boolean isDate(String cs)
	{
		if(cs.charAt(4) == '-' && cs.charAt(7) == '-')
		{
			char c = cs.charAt(0);
			if(c < '1' || c > '2')
				return false;
			c = cs.charAt(1);
			if(c < '0' || c > '9')
				return false;
			c = cs.charAt(2);
			if(c < '0' || c > '9')
				return false;
			c = cs.charAt(3);
			if(c < '0' || c > '9')
				return false;
			c = cs.charAt(5);	// M1
			if(c < '0' || c > '1')
				return false;
			c = cs.charAt(6);	// M2
			if(c < '0' || c > '9')
				return false;
			c = cs.charAt(8);	// D1
			if(c < '0' || c > '3')
				return false;
			c = cs.charAt(9);	// D2
			if(c < '0' || c > '9')
				return false;
			return true;
		}
		return false;		
	}
	
	private boolean isWrappedByFunction(int nMarkerIndex1Based, String csFuntion)
	{
		int nPos = getPositionQestionMark(nMarkerIndex1Based);
		if(nPos >= 0)
		{
			String csLeft = m_csQueryString.substring(0, nPos);
			csLeft = csLeft.trim();
			if(csLeft.endsWith("("))
			{
				csLeft = csLeft.substring(0, csLeft.length()-1);
				csLeft = csLeft.trim().toUpperCase();
				if(csLeft.endsWith(csFuntion))
					return true;
			}
		}
		return false;
	}
}
