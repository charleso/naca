/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.pathManager;

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