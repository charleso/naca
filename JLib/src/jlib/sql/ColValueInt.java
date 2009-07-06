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


public class ColValueInt extends ColValue
{
	public ColValueInt(String csName, int nValue)
	{
		super(csName);
		m_nValue = nValue;
	}
	
	public ColValue duplicate()
	{
		return new ColValueInt(m_csName, m_nValue);
	}
	
	public void setParamSQLClause(SQLClause clause)
	{
		clause.param(m_nValue);
	}
	
	public void doFillWithResurltSetCol(ResultSet resultSet, int nCol)
		throws SQLException
	{
		m_nValue = resultSet.getInt(nCol);
	}

	public String getValueAsString()
	{
		return String.valueOf(m_nValue);
	}
	
	String getDumpValueAsString()
	{
		return "(Int):'"+String.valueOf(m_nValue)+"'";
	}
	
	public int getValueAsInt()
	{
		return m_nValue;
	}
	
	double getValueAsDouble()
	{
		return (double)m_nValue;
	}
	
	String getType()
	{
		return "Int";
	}
	
	int getSQLType()
	{
		return Types.INTEGER;
	}
	
	Object getValue()
	{
		return String.valueOf(Integer.valueOf(m_nValue));
	}

	
	int m_nValue = 0;
}
