/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ColValue
{
	public ColValue(String csName, String csReplacement)
	{
		m_csName = csName;
		m_csReplacement = csReplacement;
	}
	
	public ColValue(String csName)
	{
		m_csName = csName;
		m_csReplacement = "?";
	}
	
	abstract String getDumpValueAsString();
	public abstract String getValueAsString();
	public abstract int getValueAsInt();
	abstract double getValueAsDouble();
	abstract Object getValue();
	abstract String getType();
	abstract int getSQLType();
	public abstract ColValue duplicate();
	public abstract void setParamSQLClause(SQLClause clause);
	public abstract void doFillWithResurltSetCol(ResultSet resultSet, int nCol) throws SQLException;
	
//	void setIntoObject(Object oMember)
//	{
//		Class memberClass = oMember.getClass(); 
//		if(memberClass.isInstance(Integer.class))
//		{
//			int n = getValueAsInt();
//			oMember.
//			((Integer)oMember).getInteger(nm, val) = n;
//			return true;
//		}
//	}
	
	public void fillWithResurltSetCol(ResultSet resultSet, int nCol)
		throws SQLException
	{
		doFillWithResurltSetCol(resultSet, nCol);
	}
	
	boolean hasName(String csKey)
	{
		if(m_csName.equalsIgnoreCase(csKey))
			return true;
		return false;
	}
	
	boolean isOrder(int nOrder)
	{
		if(m_nOrder == nOrder)
			return true;
		return false;
	}
	
	void setOrder(int n)
	{
		m_nOrder = n;
	}
	
	public String toString()
	{
		return "[" + getType() + "] " + m_csName + "='" + getValueAsString() + "'";
	}
	
	public String getName()
	{
		return m_csName;
	}
	
	public String getNameUppercase()
	{
		return m_csName.toUpperCase();
	}

	public String getReplacement()
	{
		return m_csReplacement;
	}
	
	public boolean canSetColParam()
	{
		return false; 
	}
	
	public boolean setParamIntoStmt(PreparedStatement stmt, int nCol)
	{
		return false;
	}
	
	String m_csName = null;
	String m_csReplacement=null;
	int m_nOrder = 0;
}
