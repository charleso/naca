/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.sql;

public class SqlBuilder
{
	private StringBuilder sb;
	
	public SqlBuilder()
	{
		sb = new StringBuilder();
	}
	
	public void append(String sql)
	{
		sb.append(sql + "\r\n");
	}
	
	public String toString()
	{
		return sb.toString();
	}
}
