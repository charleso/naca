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
import lexer.FPac.CFPacKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.FPac.CFPacElement;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.Verbs.CEntityBreak;

/**
 * @author S. Charton
 * @version $Id$
 */
public class CFPacDoQuit extends CFPacElement
{

	/**
	 * @param line
	 */
	public CFPacDoQuit(int line)
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
		if (tok.GetKeyword() == CFPacKeywordList.DOQUIT)
		{
			tok = GetNext() ;
		}
		return true ;
	}

	/**
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityBreak br = factory.NewEntityBreak(getLine()) ;
		parent.AddChild(br) ;
		return br ;
	}

	/**
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	@Override
	protected Element ExportCustom(Document root)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
