/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 24 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jlib.log;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LogInfoMember
{	
	LogInfoMember(String csName, String csValue)
	{
		m_csName = csName;
		m_csValue = csValue;
	}
	
	LogInfoMember(String csName, int nValue)
	{
		m_csName = csName;
		m_iValue = new Integer(nValue);
	}
	
	String getAsString()
	{
		if(m_csValue != null)
			return m_csName + "=" + m_csValue;
		if(m_iValue != null)
			return m_csName + "=" + m_iValue.toString();
		return m_csName + "=?";
	}
	
	String getName()
	{
		return m_csName; 
	}
	
	String getValue()
	{
		if(m_csValue != null)
			return m_csValue;
		else if(m_iValue != null)
			return m_iValue.toString();
		return "";
	}

	
	String m_csName = null;
	String m_csValue = null;
	Integer m_iValue = null;	
}
