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

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


public class ColValueBinaryStream extends ColValue
{
	public ColValueBinaryStream(String csName, InputStream is)
	{
		super(csName);
		m_is = is;
	}
	
	public ColValue duplicate()
	{
		return new ColValueBinaryStream(m_csName, m_is);
	}
	
	public void setParamSQLClause(SQLClause clause)
	{
		clause.param(m_is);
	}
	
	public void doFillWithResurltSetCol(ResultSet resultSet, int nCol)
		throws SQLException
	{
		m_is = resultSet.getBinaryStream(nCol);
	}

	public String getValueAsString()
	{
		return "";
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
		return "(BinaryStream): not display";
	}
	
	String getType()
	{
		return "InputStream";
	}
	
	int getSQLType()
	{
		return Types.LONGVARBINARY;
	}
	
	Object getValue()
	{
		return m_is;
	}
	
	public boolean canSetColParam()
	{
		return true;
	}
	
	public boolean setParamIntoStmt(PreparedStatement stmt, int nCol)
	{
		try
		{
			int nLength = m_is.available();
			stmt.setBinaryStream(nCol+1, m_is, nLength);
		}
		catch (SQLException e)
		{
			LogSQLException.log(e);
			return false;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private InputStream m_is = null;
}
