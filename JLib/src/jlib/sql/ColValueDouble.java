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

public class ColValueDouble extends ColValue
{
	public ColValueDouble(String csName, double dValue)
	{
		super(csName);
		m_dValue = dValue;
	}
	
	public ColValue duplicate()
	{
		return new ColValueDouble(m_csName, m_dValue);
	}
	
	public void setParamSQLClause(SQLClause clause)
	{
		clause.param(m_dValue);
	}
	
	public void doFillWithResurltSetCol(ResultSet resultSet, int nCol)
		throws SQLException
	{
		m_dValue = resultSet.getDouble(nCol);
	}

	public String getValueAsString()
	{
		return String.valueOf(m_dValue);
	}
	
	String getDumpValueAsString()
	{
		return "(Double):'"+String.valueOf(m_dValue)+"'";
	}
	
	public int getValueAsInt()
	{
		return (int)m_dValue;
	}
	
	double getValueAsDouble()
	{
		return m_dValue;
	}
	
	String getType()
	{
		return "Double";
	}
	
	int getSQLType()
	{
		return Types.DOUBLE;
	}
	
	Object getValue()
	{
		return String.valueOf(Double.valueOf(m_dValue));
	}

	
	double m_dValue = 0.0;
}
