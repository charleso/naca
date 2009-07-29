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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class ColValueVarBinary extends ColValue
{
	public ColValueVarBinary(String csName, VarBinary vbValue)
	{
		super(csName);
		m_vbValue = vbValue;
	}
	
	public ColValue duplicate()
	{
		return new ColValueVarBinary(m_csName, m_vbValue);
	}
	
	public void setParamSQLClause(SQLClause clause)
	{
		clause.param(m_vbValue);
	}	
	
	public void doFillWithResurltSetCol(ResultSet resultSet, int nCol)
		throws SQLException
	{
		byte tb[] = resultSet.getBytes(nCol);
		m_vbValue = new VarBinary(tb);
	}

	public String getValueAsString()
	{
		if(m_vbValue != null)
			return String.valueOf(m_vbValue);
		return null;
	}
	
	public int getValueAsInt()
	{
		//return m_vbValue.intValue();
		return 0;
	}
	
	double getValueAsDouble()
	{
		//return m_vbValue.doubleValue();
		return 0.0;
	}

	String getDumpValueAsString()
	{
		return "(VarBinary): Cannot display value";
	}
	
	String getType()
	{
		return "VarBinary";
	}
	
	int getSQLType()
	{
		return Types.VARBINARY;
	}
	
	Object getValue()
	{
		return m_vbValue;
	}
	
	public boolean canSetColParam()
	{
		return true;
	}
	
	public boolean setParamIntoStmt(PreparedStatement stmt, int nCol)
	{
		byte tb[] = m_vbValue.getBytes();
		try
		{
			stmt.setBytes(nCol+1, tb);
		}
		catch (SQLException e)
		{
			LogSQLException.log(e);
			return false;
		}
		return true;
	}
	
	VarBinary m_vbValue = null;
}
