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
 * Created on 8 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import lexer.CBaseToken;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
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
public class CTransform extends CCobolElement
{

	/**
	 * @param line
	 */
	public CTransform(int line)
	{
		super(line);
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		Transcoder.logError(getLine(), "NO semantic analysis for TRANSFORM") ;
		return null;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.TRANSFORM)
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		
		tok = GetNext();
		m_Variable = ReadIdentifier();
		
		tok = GetCurrentToken();
		if (tok.GetKeyword() != CCobolKeywordList.FROM)
		{
			return false ;
		}
		tok = GetNext();
		m_ValueFrom = ReadTerminal();
		
		tok = GetCurrentToken();
		if (tok.GetKeyword() != CCobolKeywordList.TO)
		{
			return false ;
		}
		tok = GetNext();
		m_ValueTo = ReadTerminal();

		return true ;
	}
	protected Element ExportCustom(Document root)
	{
		Element eTr = root.createElement("Transform") ;
		Element eVar = root.createElement("Variable");
		eTr.appendChild(eVar);
		m_Variable.ExportTo(eVar, root);
		
		Element eFrom = root.createElement("From");
		eTr.appendChild(eFrom);
		m_ValueFrom.ExportTo(eFrom, root);
		
		Element eTo = root.createElement("To");
		eTr.appendChild(eTo);
		m_ValueTo.ExportTo(eTo, root);
		
		return eTr;
	}

	protected CIdentifier m_Variable = null ;
	protected CTerminal m_ValueFrom = null ;
	protected CTerminal m_ValueTo = null ;
}
