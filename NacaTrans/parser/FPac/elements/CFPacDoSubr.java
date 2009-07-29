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
package parser.FPac.elements;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.FPac.CFPacKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.FPac.CFPacElement;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.Verbs.CEntityCallFunction;
import utils.Transcoder;

/**
 * @author S. Charton
 * @version $Id$
 */
public class CFPacDoSubr extends CFPacElement
{

	private CIdentifier m_idSubr;

	/**
	 * @param line
	 */
	public CFPacDoSubr(int line)
	{
		super(line);
	}

	/**
	 * @see parser.FPac.CFPacElement#DoParsing()
	 */
	@Override
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.DOSUBR)
		{
			tok = GetNext() ;
		}
		
		if (tok.GetType() == CTokenType.MINUS)
		{
			tok = GetNext() ;
		}
		else
		{
			Transcoder.logError(getLine(), "Expecting '-' after DOSUBR") ;
			return false ;
		}
		
		m_idSubr = ReadIdentifier() ;
		if (m_idSubr == null)
		{
			Transcoder.logError(getLine(), "Expecting IDENTIFIER after DOSUBR-") ;
			return false ;
		}
		return true ;
	}

	/**
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCallFunction call = factory.NewEntityCallFunction(getLine(), m_idSubr.GetName(), null, null)  ;
		parent.AddChild(call) ;
		return call ;
	}

	/**
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	@Override
	protected Element ExportCustom(Document root)
	{
		Element e = root.createElement("DoSubr") ;
		m_idSubr.ExportTo(e, root);
		return e ;
	}

}
