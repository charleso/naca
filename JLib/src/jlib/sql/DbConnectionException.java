/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.sql;

public class DbConnectionException extends Exception
{
	private static final long serialVersionUID = "DbConnectionException".hashCode();
	
	private String m_csMessage;
	
	public DbConnectionException(String csMessage)
	{
		m_csMessage = csMessage;
	}
	
	public String toString()
	{
		return "DbConnectionException: "+m_csMessage;
	}
}
