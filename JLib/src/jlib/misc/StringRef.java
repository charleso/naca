/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

public class StringRef
{
	public StringRef()
	{
	}
	
	public StringRef(String cs)
	{
		m_cs = cs;
	}

	
	public String get()
	{
		return m_cs;
	}
	
	public void set(String cs)
	{
		m_cs = cs;
	}
	
	public String toString()
	{
		if(m_cs != null)
			return "StringRef: \""+m_cs + "\"";
		return "StringRef: \"<null>\"";
	}
	
	private String m_cs = null;
}
