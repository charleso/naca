/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
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

public class ColValueString extends ColValue
{
	public ColValueString(String csName, String csReplacement, String csValue)
	{
		super(csName,csReplacement);
		m_csValue = csValue;
	}
	
	public ColValueString(String csName,  String csValue)
	{
		super(csName);
		m_csValue = csValue;
	}
	
	public ColValue duplicate()
	{
		return new ColValueString(m_csName, m_csValue);
	}
	
	public void setParamSQLClause(SQLClause clause)
	{
		clause.param(m_csValue);
	}
	
	public void doFillWithResurltSetCol(ResultSet resultSet, int nCol)
		throws SQLException
	{
		m_csValue = resultSet.getString(nCol);
	}
	
	public String getValueAsString()
	{
		return m_csValue;
	}
	
	public int getValueAsInt()
	{
		return NumberParser.getAsInt(m_csValue);
	}
	
	double getValueAsDouble()
	{
		return NumberParser.getAsDouble(m_csValue);
	}

	String getDumpValueAsString()
	{
		return "(String):'"+m_csValue+"'";
	}
		
	Object getValue()
	{
		return m_csValue;
	}

	String getType()
	{
		return "String";
	}
	
	int getSQLType()
	{
		return Types.CHAR;
	}
	
	String m_csValue = null;
}
