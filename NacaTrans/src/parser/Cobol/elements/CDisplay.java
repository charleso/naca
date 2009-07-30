/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 6 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import java.util.Vector;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.Cobol.CCobolElement;
import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.Verbs.CEntityDisplay;
import semantic.Verbs.CEntityDisplay.Upon;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CDisplay extends CCobolElement
{

	/**
	 * @param line
	 */
	public CDisplay(int line)
	{
		super(line);
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityDisplay eDisp = factory.NewEntityDisplay(getLine(), m_upon);
		parent.AddChild(eDisp) ;
		for (int i=0; i<m_arrToDisplay.size(); i++)
		{
			CTerminal term = m_arrToDisplay.get(i);
			CDataEntity e = term.GetDataEntity(getLine(), factory);
			e.RegisterReadingAction(eDisp) ;
			eDisp.AddItemToDisplay(e) ;
		}
		return eDisp ;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.DISPLAY)
		{
			Transcoder.logError(getLine(), "Expecting DISPLAY keyword") ;
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		
		tok = GetNext() ;
		CTerminal term = ReadTerminal();
		while (term != null)
		{
			m_arrToDisplay.add(term);
			tok = GetCurrentToken() ;
			if (tok.GetType() == CTokenType.COMMA)
			{
				GetNext() ;
			}
			term = ReadTerminal();
		}
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.UPON)
		{
			tok = GetNext() ;
			if (tok.GetKeyword()== CCobolKeywordList.CONSOLE)
			{
				GetNext() ;
				m_upon = Upon.CONSOLE ;
			}
			else if (tok.GetKeyword()== CCobolKeywordList.ENVIRONMENT_NAME)
			{
				GetNext() ;
				m_upon = Upon.ENVINONMENT ;
			}
			else
			{
				Transcoder.logError(getLine(), "Unexpecting token : "+tok.GetValue()) ;
				return false ;
			}
		}
		return m_arrToDisplay.size() > 0 ;
	}
	protected Element ExportCustom(Document root)
	{
		String name = "" ;
		if (m_upon == Upon.CONSOLE)
		{
			name = "DisplayUponConsole" ;
		}
		else
		{
			name = "Display" ;
		}
		Element eDisp = root.createElement(name);
		for (int i=0; i<m_arrToDisplay.size(); i++)
		{
			Element e = root.createElement("Data");
			eDisp.appendChild(e) ;
			CTerminal term = m_arrToDisplay.get(i);
			term.ExportTo(e, root) ;
		}
		return eDisp;
	}

	protected Vector<CTerminal> m_arrToDisplay = new Vector<CTerminal>() ;
	protected Upon m_upon = Upon.DEFAULT ; 
}
