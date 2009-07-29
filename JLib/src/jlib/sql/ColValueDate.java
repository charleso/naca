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
import java.util.Date;

import jlib.misc.DateUtil;


public class ColValueDate extends ColValue
{
	ColValueDate(String csName, String csReplacement, Date dateValue)
	{
		super(csName, csReplacement);
		m_dateValue = dateValue;
	}
	
	ColValueDate(String csName, Date dateValue)
	{
		super(csName);
		m_dateValue = dateValue;
	}
	
	public ColValue duplicate()
	{
		return new ColValueDate(m_csName, m_dateValue);
	}
	
	public void setParamSQLClause(SQLClause clause)
	{
		clause.param(m_dateValue);
	}	
	
	public void doFillWithResurltSetCol(ResultSet resultSet, int nCol)
		throws SQLException
	{
		m_dateValue = resultSet.getDate(nCol);
	}

	public String getValueAsString()
	{	
		if(m_dateValue == null)	// Now
		{
			return null;
//			Date date = new Date();
//			return new DateUtil("yyyyMMdd HH:mm:ss", date).toString();
//			return String.valueOf(date);
		}
		return new DateUtil("yyyyMMdd HH:mm:ss", m_dateValue).toString();
//		return String.valueOf(m_dateValue);		
	}
	
	public int getValueAsInt()
	{
		return 0;
	}
	
	double getValueAsDouble()
	{
		return 0.0;
	}
	
	String getDumpValueAsString()
	{
		if(m_dateValue == null)	// Now
		{
			Date date = new Date();
			return "(Date now):'"+String.valueOf(date)+"'";
		}
		return "(Date):'"+String.valueOf(m_dateValue)+"'";		
	}	
	
	String getType()
	{
		return "Date";
	}
	
	int getSQLType()
	{
		return Types.DATE;
	}
	
	Object getValue()
	{
//		if(m_dateValue == null)	// Now
//		{
//			Date date = new Date();
//			return date;
//		}
		return m_dateValue;
	}

	
	Date m_dateValue = null;
}
