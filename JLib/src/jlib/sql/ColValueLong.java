/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import jlib.misc.NumberParser;

public class ColValueLong extends ColValue
{
	public ColValueLong(String csName, long lValue)
	{
		super(csName);
		m_lValue = lValue;
	}
	
	public ColValue duplicate()
	{
		return new ColValueLong(m_csName, m_lValue);
	}
	
	public void setParamSQLClause(SQLClause clause)
	{
		clause.param(m_lValue);
	}
	
	public void doFillWithResurltSetCol(ResultSet resultSet, int nCol)
		throws SQLException
	{
		m_lValue = resultSet.getLong(nCol);
	}

	public String getValueAsString()
	{
		return String.valueOf(m_lValue);
	}
	
	public int getValueAsInt()
	{
		return (int)m_lValue;
	}
	
	double getValueAsDouble()
	{
		return (double)m_lValue;
	}
	
	String getDumpValueAsString()
	{
		return "(long):'"+String.valueOf(m_lValue)+"'";
	}
	
	String getType()
	{
		return "long";
	}
	
	int getSQLType()
	{
		return Types.INTEGER;
	}
	
	Object getValue()
	{
		return String.valueOf(Long.valueOf(m_lValue));
	}
	
	long m_lValue = 0L;
}
