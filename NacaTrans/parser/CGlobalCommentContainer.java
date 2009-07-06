/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Dec 6, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser;

import java.util.Vector;

import parser.Cobol.elements.CComment;
import semantic.CBaseEntityFactory;
import semantic.CEntityComment;

import lexer.CBaseToken;
import lexer.CTokenList;
import lexer.CTokenType;


/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CGlobalCommentContainer
{

	public void RegisterComment(int line, CComment comm)
	{
		Integer in = new Integer(line) ;
		m_arrComments.add(comm) ;
	}	
	public void DoSemanticAnalysis(CBaseEntityFactory factory)
	{
		for (int i=0; i<m_arrComments.size(); i++)
		{
			CComment c = m_arrComments.get(i);
			CEntityComment comm = (CEntityComment)c.DoSemanticAnalysis(null, factory) ;
			m_arrCommentEntities.add(comm);
		}
	}
	protected Vector<CEntityComment> m_arrCommentEntities = new Vector<CEntityComment>() ;
	protected Vector<CComment> m_arrComments = new Vector<CComment>();
	protected int m_nCurrentComment = 0;
	public boolean ParseComment(CTokenList lstTokens)
	{
		CBaseToken tok = lstTokens.GetCurrentToken() ;
		if (tok.GetType() == CTokenType.COMMENTS)
		{
			CComment eComment = new CComment(tok.getLine(), tok.GetValue()) ;
//			AddChild(eComment) ;
			RegisterComment(tok.getLine(), eComment) ;
			lstTokens.GetNext();
		}
		return true ;
	}
	public CEntityComment GetCurrentComment()
	{
		CEntityComment comm = m_arrCommentEntities.get(m_nCurrentComment);
		m_nCurrentComment ++ ;
		return comm ;
	}
	public int GetCurrentCommentLine()
	{ 
		if (m_nCurrentComment < m_arrCommentEntities.size())
		{
			CEntityComment comm = m_arrCommentEntities.get(m_nCurrentComment);
			return comm.getLine() ;
		}
		else
		{
			return 0 ;
		}
	}
	public void Clear()
	{
		for (int i=0; i<m_arrCommentEntities.size(); i++)
		{
			CEntityComment comm = m_arrCommentEntities.get(i);
			comm.Clear() ;
		}
		m_arrCommentEntities.clear() ;
		m_arrComments.clear() ;
		m_nCurrentComment = 0 ;
	}

}
