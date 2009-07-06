/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.sql;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import jlib.exception.ProgrammingException;
import jlib.exception.TechnicalException;

//*******************************************************************************
//**            Class to handle the Oralce particularities in SQL clauses.     **
//*******************************************************************************
/**
 * Handles the Oracle particularities in SQL clauses.
 */
public class OracleClause extends SQLClause {
//*******************************************************************************
//**                               Class constructor.                          **
//*******************************************************************************
/**
 * Use this constructor to specify the database accessor to use.
 * @param DbAccessor An accessor to the desired database. 
 */
	public OracleClause(DbAccessor accessor) {
		super(accessor);	// Identifies the DB that we access
	}

//*******************************************************************************
//**           Handles the types with particularities in Oracle.               **
//*******************************************************************************
	
//********************************** Booleans ***********************************
/**
 * Oracle handles booleans as <code>number(1)</code> type.
 * <ul>
 * 	<li><code>true</code> is encoded as <code>"1"</code>.</li>
 * 	<li><code>false</code> is encoded as <code>"0"</code>.</li>
 * </ul>
 * @param bVal A boolean value.
 * @return See {@link #param(String)}.
 */
	public String param(boolean bVal) {
		if (bVal)
			return super.param(1);
		else
			return super.param(0);
	}

/**
 * Oracle handles booleans as <code>number(1)</code> type.
 * <ul>
 * 	<li><code>true</code> is encoded as <code>"1"</code>.</li>
 * 	<li><code>false</code> is encoded as <code>"0"</code>.</li>
 * </ul>
 * @param bVal A boolean value.
 * @return See {@link #param(String)}.
 */
	public OracleClause paramInsert(String csName,boolean bVal) {
		if (bVal)
			super.paramInsert(csName,1);
		else
			super.paramInsert(csName,0);
		return this;
	}

/**
 * Oracle handles booleans as <code>number(1)</code> type.
 * <ul>
 * 	<li><code>true</code> is encoded as <code>"1"</code>.</li>
 * 	<li><code>false</code> is encoded as <code>"0"</code>.</li>
 * </ul>
 * @param bVal A boolean value.
 * @return See {@link #param(String)}.
 */
	public boolean getBoolean(String csColName) {
		int nVal=getInt(csColName);
		if (nVal>0)
			return true;
		return false;
	}

/**
 * Oracle handles booleans as <code>number(1)</code> type.
 * <ul>
 * 	<li><code>true</code> is encoded as <code>"1"</code>.</li>
 * 	<li><code>false</code> is encoded as <code>"0"</code>.</li>
 * </ul>
 * @param bVal A boolean value.
 * @return See {@link #param(String)}.
 */
	public boolean getBoolean(int nColNumber) {
		int nVal=this.getInt(nColNumber);
		if (nVal>0)
			return true;
		return false;
	}
	
	public Date getDate(String csColName) throws TechnicalException
	{
		if (getResultSet() != null)
		{
			try
			{
				Timestamp timestamp = getResultSet().getTimestamp(csColName);
				if (timestamp==null)
					return null;
				Date date = new Date(timestamp.getTime());
				return date;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_STRING+csColName, m_csQuery, e);
			}			
		}
		return null;
	}
	
	public Date getDate(int nColNumber)	throws TechnicalException
	{
		if (getResultSet() != null)
		{
			try
			{
				Timestamp timestamp = getResultSet().getTimestamp(nColNumber);
				if (timestamp==null)
					return null;
				Date date = new Date(timestamp.getTime());
				return date;
			}
			catch (SQLException e)
			{
				forceCloseOnExceptionCatched();
				ProgrammingException.throwException(ProgrammingException.DB_ERROR_RESULT_SET_COL_ACCESS_INT+nColNumber, m_csQuery, e);
			}			
		}
		return null;
	}

//************************************ Dates ************************************
/**
 * Oracle handles date/time using strings and a type conversion.
 * Typical type conversion is:
 * <pre>
 * 	to_date('2007/12/25 23:59:59','yyyy/mm/dd hh24:mi:ss')
 * </pre>
 */
	public String param(Date dVal) {
		String s=String.format("%1$tY/%1$tm/%1$td %1$tH:%1$tM:%1$tS",dVal);
		return "to_date("+param(s)+",'yyyy/mm/dd hh24:mi:ss')";
	}

/**
 * Oracle handles date/time using strings and a type conversion.
 * Typical type conversion is:
 * <pre>
 * 	to_date('2007/12/25 23:59:59','dd/mm/yyyy hh24:mi:ss')
 * </pre>
 */
	public OracleClause paramInsert(String csName,Date dVal) {
		String s=String.format("%1$tY/%1$tm/%1$td %1$tH:%1$tM:%1$tS",dVal);
		if(m_arrInsertParams == null)
			m_arrInsertParams = new ArrayList<ColValue>();
		ColValueString colVal = new ColValueString(csName, "to_date(?,'yyyy/mm/dd hh24:mi:ss')",s); 
		m_arrInsertParams.add(colVal);
		return this;
	}
}
