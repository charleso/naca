/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import jlib.log.Log;

public class OracleColumnDefinition
{
	private String m_csName = null;
	private String m_csTypeName = null;
	private SQLColumnType m_sqlColumnType = null; 
	private int m_nLength = 0;
	private int m_nScale = 0;
	private int m_nPrecision = 0;
	private boolean m_bNullable = false;

	OracleColumnDefinition()
	{
	}

	boolean fill(ResultSet col)	// Fill from a resiultSet of the catalog of a table; not a resultSet of the data themselves
	{
		try
		{
			m_csName = col.getString("COLUMN_NAME");
			m_csTypeName = col.getString("DATA_TYPE");
			m_sqlColumnType = SQLColumnType.getFromOracleTypeName(m_csTypeName);
			m_nLength = col.getInt("DATA_LENGTH");
			m_nScale = col.getInt("DATA_SCALE");
			m_nPrecision = col.getInt("DATA_PRECISION");
			String csNullable = col.getString("NULLABLE");
			if(csNullable.equals("Y"))
				m_bNullable = true;
			else
				m_bNullable = false;
			
			return true;
		}
		catch (SQLException e)
		{
			Log.logCritical("Exception catched While filling DB table's Column definition:" + e.toString());
		}
		return false;
	}
	
	public String getColumnName()
	{
		return m_csName;
	}
	
	public String getColumnTypeName()
	{
		return m_csTypeName;
	}
	
	public SQLColumnType getColumnType()
	{
		return m_sqlColumnType;
	}
	
	public int getLength()
	{
		return m_nLength;
	}
	
	public int getScale()
	{
		return m_nScale;
	}
}
