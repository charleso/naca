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
/**
 * 
 */
package jlib.sql;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class ColValueBigDecimal extends ColValue
{
	public ColValueBigDecimal(String csName, BigDecimal bdValue)
	{
		super(csName);
		m_bdValue = bdValue;
	}
	
	public ColValue duplicate()
	{
		return new ColValueBigDecimal(m_csName, m_bdValue);
	}
	
	public void setParamSQLClause(SQLClause clause)
	{
		clause.param(m_bdValue);
	}	
	
	public void doFillWithResurltSetCol(ResultSet resultSet, int nCol)
		throws SQLException
	{
		m_bdValue = resultSet.getBigDecimal(nCol);
	}

	public String getValueAsString()
	{
		if(m_bdValue != null)
			return String.valueOf(m_bdValue);
		return null;
	}
	
	public int getValueAsInt()
	{
		return m_bdValue.intValue();
	}
	
	double getValueAsDouble()
	{
		return m_bdValue.doubleValue();
	}

	String getDumpValueAsString()
	{
		return "(BigDecimal):'"+String.valueOf(m_bdValue)+"'";
	}
	
	String getType()
	{
		return "BigDecimal";
	}
	
	int getSQLType()
	{
		return Types.DECIMAL;
	}
	
	Object getValue()
	{
		return m_bdValue;
	}
	
	BigDecimal m_bdValue = null;
}
