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
import semantic.Verbs.CEntityGoto;

public class CFPacGoEnd extends CFPacElement
{

	public CFPacGoEnd(int line)
	{
		super(line);
	}

	@Override
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.GOEND)
		{
			tok = GetNext() ;
		}
		else if (tok.GetKeyword() == CFPacKeywordList.GOABEND)
		{
			tok = GetNext() ;
		}
		return true ;
	}

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityGoto egoto = factory.NewEntityGoto(getLine(), "END", null) ;
		parent.AddChild(egoto) ;
		return egoto ;
	}

	@Override
	protected Element ExportCustom(Document root)
	{
		Element e = root.createElement("GoEnd") ;
		return e;
	}

}
