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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import jlib.misc.NumberParser;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: ColValueGeneric.java,v 1.7 2007/12/05 09:43:28 u930bm Exp $
 */
public class ColValueGeneric extends ColValue
{
	public ColValueGeneric(String csName)
	{
		super(csName);
	}
	
	public ColValue duplicate()
	{
		return new ColValueGeneric(m_csName);
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
	
	public void setValue(String csValue)
	{
		m_csValue = csValue;
	}
	
	public void setValue(int n)
	{
		m_csValue = String.valueOf(n);
	}
	
	public void setValue(long l)
	{
		m_csValue = String.valueOf(l);
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
		return "(Generic):'"+m_csValue+"'";
	}
	
	String getType()
	{
		return "Generic";
	}
	
	int getSQLType()
	{
		return Types.CHAR;
	}
	
	Object getValue()
	{
		return m_csValue;
	}
	
	private String m_csValue = null;
}
