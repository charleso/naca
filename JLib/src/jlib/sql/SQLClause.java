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

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;

import jlib.exception.ProgrammingException;
import jlib.exception.TechnicalException;
import jlib.log.Log;
import jlib.sqlColType.SQLColTypeDate;


/**
 * Sample call to a stored procedure:
 	// In parameters
	String strTrtCod = "DT";
	String debug = "N";
	
	// Out or In-Out parameters
	String tcsOut1[] = new String[1];
	String tcsOut2[] = new String[1];
	
	P2000Clause clause = new P2000Clause();
	clause.setCalledStoredProc("UZLFACTURE", true)
		.paramIn(strTrtCod)
		.paramIn(debug)
		.paramIn("EV")
		.paramIn("01")
		.paramOut(tcsOut1)
		.paramOut(tcsOut2);
	clause.call();		
	
	String cs = tcsOut2[0]; 
 */

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: SQLClause.java,v 1.38 2008/07/08 12:34:22 u930bm Exp $
 */
public class SQLClause
{
	protected String m_csQuery = null;
	protected ArrayList<ColValue> m_arrParams = null;
	protected ArrayList<ColValue> m_arrInsertParams = null;
	private ArrayList<ColValue> m_arrLastParams = null;	// Use for debugging only (toString())
	private ArrayList<ColValue> m_arrLastInsertParams = null;
	private SQLClauseSPCall m_spCallClause = null;

	private ResultSet m_resultSet = null;
	private DbConnectionBase m_connection = null;
	private boolean m_bAlternateconnection = false;	// An alternate connection is not managed in the TLS, but can be accessed form the outside 
	
	public SQLClause(DbAccessor dbAccessor)
	{
		if(dbAccessor != null)
			m_connection = dbAccessor.getConnection();
	}
	
	// Create a new SQLClause on an alternate DB conenction
	// dbAccessor must be valid in all cases
	// If connection == null, then a new alternate connection is established. It's not stored in the TLS
	// If connection != null, then provided connection is used for the new clause.
	public SQLClause(DbAccessor dbAccessor, DbConnectionBase connection)
	{
		if(dbAccessor != null)
		{
			if(connection == null)	// Alloc an alternate connection
			{	
				m_bAlternateconnection = true;
				m_connection = dbAccessor.getAlternateConnection();
			}
			else	// share the alternate connection 
			{
				m_connection = connection;
			}
		}
	}
	
	// Accessor method enbaling access to allocated alternate connection
	// The main connection cannot ba accessed form the outside
	public DbConnectionBase getAlternateConnection()
	{
		if(m_bAlternateconnection)
			return m_connection;
		return null; 
	}
	
/**
 * Converts the current clause into a <code>String</code> that can be executed
 * directly using a SQL client.
 * This conversion is useful for debugging.
 */
	public String toString() 
	{
		List<ColValue> arrParams;

// If the query has been constructed with 'paramInsert(...)':
		if(m_arrInsertParams != null)
			arrParams = m_arrInsertParams;
		else 
			arrParams = m_arrLastInsertParams;
		if (arrParams!=null) 
		{
			StringBuilder sbNames = new StringBuilder(m_csQuery+" (");
			StringBuilder sbValues = new StringBuilder(" (");
			
			for(int n=0; n<arrParams.size(); n++)
			{
				ColValue colValue = arrParams.get(n);
				if(n != 0)
				{
					sbNames.append(",");
					sbValues.append(",");
				}
				sbNames.append(colValue.getName());
				String value;
				if (colValue instanceof ColValueString)
					value="'"+colValue.getValue()+"'";
				else 
					value=String.valueOf(colValue.getValue());
				sbValues.append(colValue.getReplacement().replaceAll("\\?", value));
			}
			sbNames.append(") values ");
			sbValues.append(")");
			
			sbNames.append(sbValues);
			return sbNames.toString(); 			
		} 

// If the query has been constructed with 'param(...)':
		if(m_arrParams != null)
			arrParams = m_arrParams;	
		else
			arrParams = m_arrLastParams;

		StringBuilder csQuery = new StringBuilder();
		if (m_csQuery==null)
			return "";
		String[] vQuery = m_csQuery.split("\\?");
		int nNbChunks = vQuery.length;
		
		if (arrParams == null)
			return "Statement already executed: "+m_csQuery;
		
		if(arrParams.size() !=  nNbChunks-1)
			csQuery.append(" NbParams="+arrParams.size() + " Nb Question marks="+nNbChunks);
		
		int nMax = Math.min(arrParams.size(), nNbChunks);
		for(int nChunk=0; nChunk<nMax; nChunk++) 
		{
			csQuery.append(vQuery[nChunk]);
			ColValue colValue=arrParams.get(nChunk);
			
			if (colValue instanceof ColValueString)
				csQuery.append("'"+colValue.getValue()+"'");
			else 
				csQuery.append(colValue.getValue());
		}
		if(nMax <= nNbChunks)
			csQuery.append(vQuery[nNbChunks-1]);
//		
//		csQuery.append("Columns value:\n\n");
//		for(int nChunk=0; nChunk<nMax; nChunk++) 
//		{
//			ColValue colValue=arrParams.get(nChunk);
//			csQuery.append(colValue.toString());
//			csQuery.append("\n");
//		}
			
		return csQuery.toString();
	}
	
	public SQLClause set(String csQuery)
	{
		m_csQuery = csQuery;
		return this;
	}
	
	public SQLClause append(String csQuery)
	{
		m_csQuery += csQuery;
		return this;
	}
	
	public String getQuery()
	{
		completeInsertQuery();
		return m_csQuery;
	}
	
	
	public String param(ColValue colVal)
	{
		if(m_arrParams == null)
			m_arrParams = new ArrayList<ColValue>();
		m_arrParams.add(colVal);
		return "?";
	}
	
	public SQLClause paramInsert(ColValue colValue)
	{
		if(m_arrInsertParams == null)
			m_arrInsertParams = new ArrayList<ColValue>();
		m_arrInsertParams.add(colValue);
		
		return this;
	}
	
	// String	
	public String param(String csVal)
	{
		if(m_arrParams == null)
			m_arrParams = new ArrayList<ColValue>();
		ColValueString colVal = new ColValueString("", csVal); 
		m_arrParams.add(colVal);
		
		return "?";
	}
			
	public SQLClause paramInsert(String csName, String csVal)
	{
		if(m_arrInsertParams == null)
			m_arrInsertParams = new ArrayList<ColValue>();
		/*if(StringUtil.isEmpty(csVal))
			csVal = " ";*/
		ColValueString colVal = new ColValueString(csName, csVal); 
		m_arrInsertParams.add(colVal);
		
		return this;
	}
	
	public String getString(String csColName) 
		throws TechnicalException
	{		
		if(m_resultSet != null)
		{
			try
			{
				String csVal = m_resultSet.getString(csColName);
				if(csVal != null)
					csVal = csVal.trim();
				return csVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_STRING+csColName, m_csQuery, e);
			}			
		}
		return "";
	}

	public String getString(int nColNumber) 
		throws TechnicalException
	{		
		if(m_resultSet != null)
		{
			try
			{
				String csVal = m_resultSet.getString(nColNumber);
				if(csVal != null)
					csVal = csVal.trim();
				return csVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_INT+nColNumber, m_csQuery, e);
			}			
		}
		return "";
	}
	
	public String getStringWithoutTrim(String csColName) 
		throws TechnicalException
	{		
		if(m_resultSet != null)
		{
			try
			{
				String csVal = m_resultSet.getString(csColName);
				return csVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_STRING+csColName, m_csQuery, e);
			}			
		}
		return "";
	}

	public String getStringWithoutTrim(int nColNumber) 
		throws TechnicalException
	{		
		if(m_resultSet != null)
		{
			try
			{
				String csVal = m_resultSet.getString(nColNumber);
				return csVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_INT+nColNumber, m_csQuery, e);
			}			
		}
		return "";
	}
		
	// int
	public String param(int nVal)
	{
		if(m_arrParams == null)
			m_arrParams = new ArrayList<ColValue>();
		ColValueInt colVal = new ColValueInt("", nVal);
		m_arrParams.add(colVal);
		
		return "?";
	}
	
	public SQLClause paramInsert(String csName, int nVal)
	{
		if(m_arrInsertParams == null)
			m_arrInsertParams = new ArrayList<ColValue>();
		ColValueInt colVal = new ColValueInt(csName, nVal); 
		m_arrInsertParams.add(colVal);
		
		return this;
	}
	
	public int getInt(String csColName) 
		throws TechnicalException
	{
		if(m_resultSet != null)
		{
			try
			{
				int nVal = m_resultSet.getInt(csColName);
				return nVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_STRING+csColName, m_csQuery, e);
			}			
		}
		return 0;
	}
	
	public int getInt(int nColNumber) 
		throws TechnicalException
	{
		if(m_resultSet != null)
		{
			try
			{
				int nVal = m_resultSet.getInt(nColNumber);
				return nVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_INT+nColNumber, m_csQuery, e);
			}			
		}
		return 0;
	}
	
	public double getDouble(String csColName) 
		throws TechnicalException
	{
		if(m_resultSet != null)
		{
			try
			{
				double nVal = m_resultSet.getDouble(csColName);
				return nVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_STRING+csColName, m_csQuery, e);
			}			
		}
		return 0;
	}
	
	public double getDouble(int nColNumber) 
		throws TechnicalException
	{
		if(m_resultSet != null)
		{
			try
			{
				double nVal = m_resultSet.getDouble(nColNumber);
				return nVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_INT+nColNumber, m_csQuery, e);
			}			
		}
		return 0;
	}
	
	public Date getDate(String csColName) 
		throws TechnicalException
	{
		if(m_resultSet != null)
		{
			try
			{
				Date dVal = m_resultSet.getDate(csColName);
				return dVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_STRING+csColName, m_csQuery, e);
			}			
		}
		return null;
	}
		
	public Date getDate(int nColNumber) 
		throws TechnicalException
	{
		if(m_resultSet != null)
		{
			try
			{
				Date dVal = m_resultSet.getDate(nColNumber);
				return dVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_INT+nColNumber, m_csQuery, e);
			}			
		}
		return null;
	}
	
	
	// Long
	public String param(long lVal)
	{
		if(m_arrParams == null)
			m_arrParams = new ArrayList<ColValue>();
		ColValueLong colVal = new ColValueLong("", lVal); 
		m_arrParams.add(colVal);
		
		return "?";
	}
	
	public SQLClause paramInsert(String csName, long lVal)
	{
		if(m_arrInsertParams == null)
			m_arrInsertParams = new ArrayList<ColValue>();
		ColValueLong colVal = new ColValueLong(csName, lVal); 
		m_arrInsertParams.add(colVal);
		
		return this;
	}
	
	public long getLong(String csColName) 
		throws TechnicalException
	{
		if(m_resultSet != null)
		{
			try
			{
				long lVal = m_resultSet.getLong(csColName);
				return lVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_LONG+csColName, m_csQuery, e);
			}			
		}
		return 0L;
	}
	
	public long getLong(int nColNumber) 
		throws TechnicalException
	{
		if(m_resultSet != null)
		{
			try
			{
				long lVal = m_resultSet.getInt(nColNumber);
				return lVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_LONG+nColNumber, m_csQuery, e);
			}			
		}
		return 0L;
	}
	
	// boolean
	public String param(boolean bVal)
	{
		if(m_arrParams == null)
			m_arrParams = new ArrayList<ColValue>();
		ColValueBoolean colVal = new ColValueBoolean("", bVal); 
		m_arrParams.add(colVal);
		
		return "?";
	}
	
	public SQLClause paramInsert(String csName, Boolean bVal)
	{
		if(m_arrInsertParams == null)
			m_arrInsertParams = new ArrayList<ColValue>();
		boolean bNewVal = false;
		if (bVal != null)
			bNewVal = bVal;
		ColValueBoolean colVal = new ColValueBoolean(csName, bNewVal); 
		m_arrInsertParams.add(colVal);
		
		return this;
	}
	
	public SQLClause paramInsert(String csName, boolean bVal)
	{
		if(m_arrInsertParams == null)
			m_arrInsertParams = new ArrayList<ColValue>();
		ColValueBoolean colVal = new ColValueBoolean(csName, bVal); 
		m_arrInsertParams.add(colVal);
		
		return this;
	}
	
	public boolean getBoolean(String csColName) 
		throws TechnicalException
	{
		if(m_resultSet != null)
		{
			try
			{
				boolean bVal = m_resultSet.getBoolean(csColName);
				return bVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_STRING+csColName, m_csQuery, e);
			}			
		}
		return false;
	}
	
	public boolean getBoolean(int nColNumber) 
		throws TechnicalException
	{
		if(m_resultSet != null)
		{
			try
			{
				boolean bVal = m_resultSet.getBoolean(nColNumber);
				return bVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_INT+nColNumber, m_csQuery, e);
			}			
		}
		return false;
	}
	
	// BigDecimal
	public String param(BigDecimal bdVal)
	{
		if(m_arrParams == null)
			m_arrParams = new ArrayList<ColValue>();
		ColValueBigDecimal colVal = new ColValueBigDecimal("", bdVal);
		m_arrParams.add(colVal);
		
		return "?";
	}
	
	public SQLClause paramInsert(String csName, BigDecimal bdVal)
	{
		if(m_arrInsertParams == null)
			m_arrInsertParams = new ArrayList<ColValue>();
		ColValueBigDecimal colVal = new ColValueBigDecimal(csName, bdVal); 
		m_arrInsertParams.add(colVal);
		
		return this;
	}
		
		
	public BigDecimal getBigDecimal(String csColName) 
		throws TechnicalException
	{
		if(m_resultSet != null)
		{
			try
			{
				BigDecimal bdVal = m_resultSet.getBigDecimal(csColName);
				return bdVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_BIG_DECIMAL_ACCESS_STRING+csColName, m_csQuery, e);
			}			
		}
		return new BigDecimal(0);
	}
	
	public BigDecimal getBigDecimal(int nColNumber) 
		throws TechnicalException
	{
		if(m_resultSet != null)
		{
			try
			{
				BigDecimal bdVal = m_resultSet.getBigDecimal(nColNumber);
				return bdVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_BIG_DECIMAL_ACCESS_INT+nColNumber, m_csQuery, e);
			}			
		}
		return new BigDecimal(0);
	}
	
	// double
	public String param(double dVal)
	{
		if(m_arrParams == null)
			m_arrParams = new ArrayList<ColValue>();
		ColValueDouble colVal = new ColValueDouble("", dVal); 
		m_arrParams.add(colVal);
		
		return "?";
	}
	
	public SQLClause paramInsert(String csName, double dVal)
	{
		if(m_arrInsertParams == null)
			m_arrInsertParams = new ArrayList<ColValue>();
		ColValueDouble colVal = new ColValueDouble(csName, dVal); 
		m_arrInsertParams.add(colVal);
		
		return this;
	}
	
	// Date
	public String param(Date dateVal)
	{
		if(m_arrParams == null)
			m_arrParams = new ArrayList<ColValue>();
		ColValueDate colVal = new ColValueDate("", dateVal); 
		m_arrParams.add(colVal);
		
		return "?";
	}
	
	public SQLClause paramInsert(String csName, Date dateVal)
	{
		if(m_arrInsertParams == null)
			m_arrInsertParams = new ArrayList<ColValue>();
		ColValueDate colVal = new ColValueDate(csName, dateVal); 
		m_arrInsertParams.add(colVal);
		
		return this;
	}
	
	// Timestamp
	public String param(Timestamp tsVal)
	{
		if(m_arrParams == null)
			m_arrParams = new ArrayList<ColValue>();
		ColValueTimestamp colVal = new ColValueTimestamp("", tsVal); 
		m_arrParams.add(colVal);
		
		return "?";
	}
	
	public String param(SQLColTypeDate dateVal)
	{
		if(m_arrParams == null)
			m_arrParams = new ArrayList<ColValue>();
		ColValueTimestamp colVal = new ColValueTimestamp("", dateVal.getTimeStamp()); 
		m_arrParams.add(colVal);
		
		return "?";
	}
	
	public SQLClause paramInsert(String csName, Timestamp tsVal)
	{
		if(m_arrInsertParams == null)
			m_arrInsertParams = new ArrayList<ColValue>();
		ColValueTimestamp colVal = new ColValueTimestamp(csName, tsVal); 
		m_arrInsertParams.add(colVal);
		
		return this;
	}
	
	// Blob - Using implementation SerialBlob
	// Managed SQL Type: BLOB
	public String param(SerialBlob blVal)
	{
		if(m_arrParams == null)
			m_arrParams = new ArrayList<ColValue>();
		ColValue colVal = new ColValueBlob("", blVal); 
		m_arrParams.add(colVal);
		
		return "?";
	}
	
		
	public SQLClause paramInsert(String csName, SerialBlob blVal)
	{
		if(m_arrInsertParams == null)
			m_arrInsertParams = new ArrayList<ColValue>();
		ColValue colVal = new ColValueBlob(csName, blVal); 
		m_arrInsertParams.add(colVal);
		
		return this;
	}
	
	public Blob getBlob(String csColName)	throws TechnicalException
	{		
		if(m_resultSet != null)
		{
			try
			{
				Blob blVal = m_resultSet.getBlob(csColName);
				return blVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_STRING+csColName, m_csQuery, e);
			}			
		}
		return null;
	}
	
	
	public Blob getBlob(int nColNumber) throws TechnicalException
	{		
		if(m_resultSet != null)
		{
			try
			{
				Blob blVal = m_resultSet.getBlob(nColNumber);
				return blVal;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_INT+nColNumber, m_csQuery, e);
			}			
		}
		return null;
	}

	
	/** Added by Jilali Raki. Needed for ROA
	 * 
	 * @param csColName Column name
	 * @return Serial Clob data 
	 * @throws TechnicalException
	 */
	public SerialClob getClob(String csColName)	throws TechnicalException
	{		
		if(m_resultSet != null)
		{
			try
			{
				Clob blVal = m_resultSet.getClob(csColName);
				SerialClob sb = new SerialClob(blVal); 
				return sb;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_STRING+csColName, m_csQuery, e);
			}			
		}
		return null;
	}		
	
	/**Added by Jilali Raki. Needed for ROA
	 * 
	 * @param nColNumber  Column number
	 * @return Serial Clob data
	 * @throws TechnicalException
	 */
	public SerialClob getClob(int nColNumber) throws TechnicalException
	{		
		if(m_resultSet != null)
		{
			try
			{
				Clob blVal = m_resultSet.getClob(nColNumber);
				SerialClob sb = new SerialClob(blVal); 
				return sb;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_INT+nColNumber, m_csQuery, e);
			}			
		}
		return null;
	}

	
	
	// VarBinary
	// Managed SQL Type: VARBINARY
	public String param(VarBinary vbVal)
	{
		if(m_arrParams == null)
			m_arrParams = new ArrayList<ColValue>();
		ColValueVarBinary colVal = new ColValueVarBinary("", vbVal);
		m_arrParams.add(colVal);
		
		return "?";
	}
	
	public SQLClause paramInsert(String csName, VarBinary vbVal)
	{
		if(m_arrInsertParams == null)
			m_arrInsertParams = new ArrayList<ColValue>();
		ColValueVarBinary colVal = new ColValueVarBinary(csName, vbVal); 
		m_arrInsertParams.add(colVal);
		
		return this;
	}
		
		
	public VarBinary getVarBinary(String csColName) 
		throws TechnicalException
	{
		if(m_resultSet != null)
		{
			try
			{
				byte tb[] = m_resultSet.getBytes(csColName);
				VarBinary vb = new VarBinary(tb);
				return vb;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_BIG_DECIMAL_ACCESS_STRING+csColName, m_csQuery, e);
			}			
		}
		return new VarBinary();
	}
	
	public VarBinary getVarBinary(int nColNumber) throws TechnicalException
	{
		if(m_resultSet != null)
		{
			try
			{
				byte tb[] = m_resultSet.getBytes(nColNumber);
				VarBinary vb = new VarBinary(tb);
				return vb;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_BIG_DECIMAL_ACCESS_STRING+nColNumber, m_csQuery, e);
			}			
		}
		return new VarBinary();
	}
	
	// InputStream - ColValueBinaryStream
	// SQL type managed: LONGVARBINARY
	public String param(InputStream is)
	{
		if(m_arrParams == null)
			m_arrParams = new ArrayList<ColValue>();
		ColValue colVal = new ColValueBinaryStream("", is); 
		m_arrParams.add(colVal);
		
		return "?";
	}
			
	public SQLClause paramInsert(String csName, InputStream is)
	{
		if(m_arrInsertParams == null)
			m_arrInsertParams = new ArrayList<ColValue>();
		ColValue colVal = new ColValueBinaryStream(csName, is); 
		m_arrInsertParams.add(colVal);
		
		return this;
	}
	
	public InputStream getInputStream(String csColName) throws TechnicalException
	{		
		if(m_resultSet != null)
		{
			try
			{
				InputStream is = m_resultSet.getBinaryStream(csColName);
				return is;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_STRING+csColName, m_csQuery, e);
			}			
		}
		return null;
	}
	
	public InputStream getInputStream(int nColNumber) throws TechnicalException
	{		
		if(m_resultSet != null)
		{
			try
			{
				InputStream is = m_resultSet.getBinaryStream(nColNumber);
				return is;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_INT+nColNumber, m_csQuery, e);
			}			
		}
		return null;
	}

	void fillParameters(DbPreparedStatement preparedStatement)
	{
		if(preparedStatement == null)
			return ;
		
		if(m_arrParams != null)
		{
			for(int nCol=0; nCol<m_arrParams.size(); nCol++)
			{
				ColValue colVal = m_arrParams.get(nCol);
				preparedStatement.setColParam(nCol, colVal);
			}
		}
		if(m_arrInsertParams != null)
		{
			for(int nCol=0; nCol<m_arrInsertParams.size(); nCol++)
			{
				ColValue colVal = m_arrInsertParams.get(nCol);
				preparedStatement.setColParam(nCol, colVal);
			}
		}

		m_arrLastParams = m_arrParams;
		m_arrLastInsertParams = m_arrInsertParams;
		
		m_arrParams = null;
		m_arrInsertParams = null;
	}
		
	private void completeInsertQuery()
	{		
		if(m_arrInsertParams != null)
		{
			StringBuilder sbNames = new StringBuilder(" (");
			StringBuilder sbValues = new StringBuilder(" (");
			
			for(int n=0; n<m_arrInsertParams.size(); n++)
			{
				ColValue colValue = m_arrInsertParams.get(n);
				if(n != 0)
				{
					sbNames.append(",");
					sbValues.append(",");
				}
				sbValues.append(colValue.getReplacement());
				sbNames.append(colValue.getName());
			}
			sbNames.append(") values ");
			sbValues.append(") ");
			
			sbNames.append(sbValues);
			m_csQuery += sbNames.toString(); 
		}
	}
		
	public void close()
	{
		try 
        {
            if (m_resultSet != null) 
            {
            	m_resultSet.close();
            }
            else
            {
            	Log.logImportant("Resultset is null");
            }
        } 
       catch (Exception ignored) 
       {	        	   
       }
       m_resultSet = null;
	}
	
	public void forceCloseOnExceptionCatched()
	{
		close();
		if(m_connection != null)
			m_connection.returnConnectionToPool();
		m_connection = null;
	}
	
	public int prepareAndExecute() 
		throws TechnicalException
	{
		if(m_connection != null)
		{
			try
			{
				int n = m_connection.prepareAndExecuteWithException(this);
				return n;
			}
			catch (TechnicalException e)
			{
				forceCloseOnExceptionCatched();
				throw e;	// Rethrow the exception; the clause is closed
			}
		}
		return -1;
	}
	
	public int call() 
		throws TechnicalException
	{
		if(m_connection != null && m_spCallClause != null)
		{
			int n = m_spCallClause.prepareAndCallWithException(m_connection);
			return n;
		}
		return -1;
	}
	
		
	public boolean next() 
		throws TechnicalException
	{
		try 
        {
            if (m_resultSet != null) 
            {
            	return m_resultSet.next();
            }
            Log.logImportant("Resultset is null");
        } 
		catch (SQLException e) 
		{	    
			forceCloseOnExceptionCatched();
			ProgrammingException.throwException(ProgrammingException.RESULTSET_NEXT_SQL_ERROR, m_csQuery, e);
		}
		return false;
	}
	
	void setResultSetSet(ResultSet resultSet)
	{
		m_resultSet = resultSet; 
	}
	
	public ResultSet getResultSet()
	{
		return m_resultSet; 
	}

	// Stored Procedure call support
	public SQLClauseSPCall setCalledStoredProc(String csSPName, boolean bCheckParams)
	{
		m_spCallClause = new SQLClauseSPCall(csSPName, bCheckParams);
		return m_spCallClause;
	}
	
	/*
	public DbConnectionBase getConnection()
	{
		return m_connection;
	}
	*/
	public Connection getJDBCConnection()
	{
		if(m_connection != null)
			return m_connection.getDbConnection();
		return null;
	}
}







