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


public class ColValueInteger extends ColValue	// MAnages sql null values 
{
	public ColValueInteger(String csName, Integer nValue)
	{
		super(csName);
		m_iValue = nValue;
	}
	
	public ColValue duplicate()
	{
		return new ColValueInt(m_csName, m_iValue);
	}
	
	public void setParamSQLClause(SQLClause clause)
	{
		clause.param(m_iValue);
	}
	
	public void doFillWithResurltSetCol(ResultSet resultSet, int nCol)
		throws SQLException
	{
		m_iValue = resultSet.getInt(nCol);
	}

	public String getValueAsString()
	{
		if(m_iValue != null)
			return String.valueOf(m_iValue);
		return null;		
	}
	
	String getDumpValueAsString()
	{
		if(m_iValue != null)
			return "(Int):'"+String.valueOf(m_iValue)+"'";
		return "(Int):null";
	}
	
	public int getValueAsInt()
	{
		if(m_iValue != null)
			return m_iValue;
		return 0;
	}
	
	double getValueAsDouble()
	{
		if(m_iValue != null)
			return (double)m_iValue;
		return 0.0;
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
		if(m_iValue != null)
			return String.valueOf(Integer.valueOf(m_iValue));
		return null; 
	}

	private Integer m_iValue = 0;
}
