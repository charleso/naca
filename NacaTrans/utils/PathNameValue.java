/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package utils;

public class PathNameValue
{
	private String m_csName = null;
	private String m_csValue = null;
	
	public PathNameValue(String csName, String csValue)
	{
		m_csName = csName;
		m_csValue = csValue;
	}
	
	public String getName()
	{
		return m_csName;
	}
	
	public String getValue()
	{
		return m_csValue;
	}
}