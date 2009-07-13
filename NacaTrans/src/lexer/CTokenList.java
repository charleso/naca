/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jul 16, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package lexer;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CTokenList
{
	LinkedList<CBaseToken> m_lstTokens = new LinkedList<CBaseToken>() ;
	ListIterator m_iter = null ;
	CBaseToken m_curToken = null ;

	
	public void Add(CBaseToken tok)
	{
		m_lstTokens.add(tok) ;
	}
	
	public CBaseToken GetCurrentToken()
	{
		return m_curToken ;
	}
	
	public void StartIter()
	{
		try
		{
			m_iter = m_lstTokens.listIterator() ;
			m_curToken = (CBaseToken)m_iter.next() ;
		}
		catch (Exception e)
		{
			m_iter = null ;
			m_curToken = null ;
		}
	}
	public CBaseToken GetNext()
	{
		try
		{
			if (m_iter == null)
			{
				m_iter = m_lstTokens.listIterator() ;
			}
			m_curToken = (CBaseToken)m_iter.next();
			return m_curToken;
		}
		catch (Exception e)
		{
			m_curToken = null ;
			return null ;
		}
	} 
	public int GetNbTokens()
	{
		return m_lstTokens.size();
	}

	public void Clear()
	{
		m_curToken = null ;
		m_iter = null ;
		m_lstTokens.clear() ;
	}
}
