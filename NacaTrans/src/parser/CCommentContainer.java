/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 17 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser;

import parser.Cobol.CCobolElement;


/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CCommentContainer extends CCobolElement
{
	public CCommentContainer(int line)
	{
		super(line);
	}
//	public boolean ParseComment()
//	{
//		CBaseToken tok = m_lstTokens.GetCurrentToken() ;
//		if (tok.GetType() == CTokenType.COMMENTS)
//		{
//			CLanguageElement eComment = new CComment(tokEntry.getLine(), tok.GetValue()) ;
//			AddChild(eComment) ;
//			m_lstTokens.GetNext();
//		}
//		return true ;
//	}
//	protected boolean Parse(CLanguageElement e)
//	{
//		return e.Parse(m_lstTokens, this) ;
//	}
//	protected boolean Parse(CLanguageElement e, CFlag f)
//	{
//		return e.Parse(m_lstTokens, this, f) ;
//	}
//	public boolean Parse(CTokenList lstTokens)
//	{
//		return Parse(lstTokens, this);
//	}
}
