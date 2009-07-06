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
package parser.Cobol.elements;

import org.w3c.dom.*;

import parser.Cobol.CCobolElement;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.CEntityComment;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CComment extends CCobolElement
{
	/**
	 * @param line
	 */
	public CComment(int line, String cs) {
		super(line);
		m_CommentText = cs ;
	}

	// ParseComment
	// expected : Any string in the Comment token
	protected boolean DoParsing()
	{
//		CBaseToken tok = GetCurrentToken();
//		m_CommentText = tok.GetValue() ;
//		tok = GetNext() ; // consume COMMENT token
		return true ;
	}

	public Element ExportCustom(Document rootdoc)
	{
		Element e = rootdoc.createElement("Comment") ;
		e.setAttribute("Text", m_CommentText) ;
		return e ;
	}

	String m_CommentText = "" ;

	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityComment eCom = factory.NewEntityComment(getLine(), m_CommentText) ;
		if (parent != null)
		{
			parent.AddChild(eCom);
		}
		return eCom;
	} 
}
