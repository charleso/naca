/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
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

import lexer.*;
import parser.Cobol.CCobolElement;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import utils.Transcoder;

import org.w3c.dom.*;


/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CUnparsedToken extends CCobolElement
{
	/**
	 * @param line
	 */
	public CUnparsedToken(int line) {
		super(line);
	}

	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken();
		m_Token = tok.GetValue() ;
		GetNext() ;
		m_Token += ReadStringUntilEOL() ;
		Transcoder.logWarn(tok.getLine(), "3. Unparsed Token : " + m_Token);
		return true ;
	}

	public Element ExportCustom(Document rootdoc)
	{
		Element e = rootdoc.createElement("UnparsedToken") ;
		e.setAttribute("Token", m_Token) ;
		return e ;
	}

	String m_Token = "" ;

	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		Transcoder.logWarn(getLine(), "No semantic analysis yet for 'UNPARSED TOKEN'") ;
		return null;
	} 
}
