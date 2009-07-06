/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 10 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import lexer.CBaseToken;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.Cobol.CCobolElement;
import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class COnElse extends CCobolElement
{

	/**
	 * @param line
	 */
	public COnElse(int line)
	{
		super(line);
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		Transcoder.logError(getLine(), "No semantic analysis for On ... ELSE");
		return null;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.ON)
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		tok = GetNext();
		m_Value = ReadTerminal();
		
		tok = GetCurrentToken();
		m_ThenBloc = new CThenBloc(tok.getLine());
		if (!Parse(m_ThenBloc))
		{
			return false ;
		}

		tok = GetCurrentToken();
		if (tok.GetKeyword() == CCobolKeywordList.ELSE)
		{
			tok = GetNext();
			m_ElseBloc = new CElseBloc(tok.getLine()) ;
			if (!Parse(m_ElseBloc))
			{
				return false ;
			}
		}

		return true;
	}
	protected Element ExportCustom(Document root)
	{
		Element eOn = root.createElement("On") ;
		if (m_Value != null)
		{
			Element eVal = root.createElement("Value");
			m_Value.ExportTo(eVal, root);
			eOn.appendChild(eVal);
		}
		Element eDo = m_ThenBloc.Export(root);
		eOn.appendChild(eDo);
		if (m_ElseBloc != null)
		{
			Element eElse = m_ElseBloc.Export(root);
			eOn.appendChild(eElse) ;
		}
		return eOn;
	}

	protected CTerminal m_Value = null ;
	protected CThenBloc m_ThenBloc = null ;
	protected CElseBloc m_ElseBloc = null ;
}
