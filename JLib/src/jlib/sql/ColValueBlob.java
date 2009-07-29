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
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

public class ColValueBlob extends ColValue
{
	public ColValueBlob(String csName, SerialBlob blob)
	{
		super(csName);
		m_blValue = blob;
	}
	
	public ColValue duplicate()
	{
		return new ColValueBlob(m_csName, m_blValue);
	}
	
	public void setParamSQLClause(SQLClause clause)
	{
		clause.param(m_blValue);
	}
	
	public void doFillWithResurltSetCol(ResultSet resultSet, int nCol)
		throws SQLException
	{
		Blob blob = resultSet.getBlob(nCol);
		m_blValue = new SerialBlob(blob);
	}

	public String getValueAsString()
	{
		if(m_blValue != null)
			return m_blValue.toString();
		return null;
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
		return "(Blog):'"+m_blValue.toString();
	}
	
	String getType()
	{
		return "Blob";
	}
	
	int getSQLType()
	{
		return Types.BLOB;
	}
	
	Object getValue()
	{
		return m_blValue;
	}
	
	public boolean canSetColParam()
	{
		return true;
	}
	
	public boolean setParamIntoStmt(PreparedStatement stmt, int nCol)
	{
		InputStream is;
		try
		{
			is = m_blValue.getBinaryStream();
			int nLength = is.available();
			stmt.setBinaryStream(nCol+1, is, nLength);
		}
		catch (SerialException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
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
	
	private SerialBlob m_blValue = null;
}
