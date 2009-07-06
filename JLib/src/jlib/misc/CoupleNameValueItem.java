/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.misc;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: CoupleNameValueItem.java,v 1.1 2007/10/11 10:13:03 u930di Exp $
 */
public class CoupleNameValueItem
{
	private String m_csName = null;
	private String m_csValue = null;
	
	public CoupleNameValueItem(String csName, String csValue)
	{
		m_csName = csName; 
		m_csValue = csValue;
	}
	
	public String getName()
	{
		return m_csName;
	}
	
	public int getNameAsInt()
	{
		return NumberParser.getAsInt(m_csName);
	}
	
	public String getValue()
	{
		return m_csValue;
	}
}
