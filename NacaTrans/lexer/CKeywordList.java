/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jul 15, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package lexer;

import java.util.Hashtable;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CKeywordList
{
	protected CKeywordList()
	{
	}
	
	public void Register(CReservedKeyword kw)
	{
		m_tabKeyWords.put(kw.m_Name, kw) ;
	}
	
	public CReservedKeyword GetKeyword(String name)
	{
		CReservedKeyword kw = m_tabKeyWords.get(name);
		return kw ;
	}
	private Hashtable<String, CReservedKeyword> m_tabKeyWords = new Hashtable<String, CReservedKeyword>() ;
	
	
}
