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

public class ColValueBoolean extends ColValue
{
	public ColValueBoolean(String csName, boolean bValue)
	{
		super(csName);
		m_bValue = bValue;
	}
	
	public ColValue duplicate()
	{
		return new ColValueBoolean(m_csName, m_bValue);
	}
	
	public void setParamSQLClause(SQLClause clause)
	{
		clause.param(m_bValue);
	}	
	
	public void doFillWithResurltSetCol(ResultSet resultSet, int nCol)
		throws SQLException
	{
		m_bValue = resultSet.getBoolean(nCol);
	}

	public String getValueAsString()
	{
		return String.valueOf(m_bValue);
	}
	
	String getDumpValueAsString()
	{
		return "(Boolean):'"+String.valueOf(m_bValue)+"'";
	}
	
	public int getValueAsInt()
	{
		if(m_bValue)
			return 1;
		return 0;
	}
	
	double getValueAsDouble()
	{
		if(m_bValue)
			return 1.0;
		return 0.0;
	}
	
	String getType()
	{
		return "Boolean";
	}
	
	int getSQLType()
	{
		return Types.BOOLEAN;
	}
	
	Object getValue()
	{
		return String.valueOf(Boolean.valueOf(m_bValue));
	}
	
	boolean m_bValue = false;
}
