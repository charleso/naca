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

import java.util.ArrayList;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: StringArray.java,v 1.1 2006/09/07 13:28:18 u930di Exp $
 */
public class StringArray
{
	private ArrayList<String> m_arr = null;
	
	public StringArray()
	{
		m_arr = new ArrayList<String>();
	}
	
	public void add(String csElem)
	{
		m_arr.add(csElem);
	}
	
	public int size()
	{
		return m_arr.size(); 
	}
	
	public String get(int n)
	{
		return m_arr.get(n);
	}
}
