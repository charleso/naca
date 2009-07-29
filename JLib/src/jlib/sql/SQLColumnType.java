/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.sql;

import oracle.jdbc.OracleTypes;

public class SQLColumnType
{
	private int m_nOracleType = 0;
	
	public final static SQLColumnType Unknown = new SQLColumnType(OracleTypes.OTHER);
	public final static SQLColumnType Char = new SQLColumnType(OracleTypes.CHAR);
	public final static SQLColumnType Number = new SQLColumnType(OracleTypes.NUMBER);
	public final static SQLColumnType Date = new SQLColumnType(OracleTypes.DATE);
	public final static SQLColumnType Timestamp6 = new SQLColumnType(OracleTypes.TIMESTAMP);
	public final static SQLColumnType Varchar2 = new SQLColumnType(OracleTypes.VARCHAR);
	
	private SQLColumnType(int nOracleType)
	{	
		m_nOracleType = nOracleType;
	}
	
	public String toString()
	{
		if(this == SQLColumnType.Unknown)
			return "SQLColumnType.Unknown";
		if(this == SQLColumnType.Char)
				return "SQLColumnType.Char";
		if(this == SQLColumnType.Number)
			return "SQLColumnType.Number";
		if(this == SQLColumnType.Date)
			return "SQLColumnType.Date";
		if(this == SQLColumnType.Timestamp6)
			return "SQLColumnType.Timestamp6";
		if(this == SQLColumnType.Varchar2)
			return "SQLColumnType.Varchar2";
		return "[undefined or null]";
	}
	
	public static SQLColumnType getFromOracleTypeName(String csOracleTypeName)
	{
		if(csOracleTypeName.equals("CHAR"))
			return SQLColumnType.Char;
		else if(csOracleTypeName.equals("NUMBER"))
			return SQLColumnType.Number;
		else if(csOracleTypeName.equals("DATE"))
			return SQLColumnType.Date;
		else if(csOracleTypeName.equals("TIMESTAMP(6)"))
			return SQLColumnType.Timestamp6;
		else if(csOracleTypeName.equals("VARCHAR2"))
			return SQLColumnType.Varchar2;
		return SQLColumnType.Unknown;
	}
	
	public int getOracleType()
	{
		return m_nOracleType;
	}
}
