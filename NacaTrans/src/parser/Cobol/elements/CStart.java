/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 9 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import lexer.CBaseToken;
import lexer.CTokenType;
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
public class CStart extends CCobolElement
{
	/**
	 * @param line
	 */
	public CStart(int line)
	{
		super(line);
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		Transcoder.logError(getLine(), "No Semantic analysis for START");
		return null;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken();
		if (tok.GetKeyword() != CCobolKeywordList.START)
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		
		tok = GetNext();
		m_FileDesc = ReadIdentifier();
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.KEY)
		{
			return false ;
		}
		tok = GetNext();
		if (tok.GetKeyword() == CCobolKeywordList.IS)
		{
			tok = GetNext() ;
		}
		if (tok.GetType() == CTokenType.GREATER_THAN)
		{
			m_KeyCompare = CTokenType.GREATER_THAN ;
			tok = GetNext() ;
		}
		else if (tok.GetType() == CTokenType.GREATER_OR_EQUALS)
		{
			m_KeyCompare = CTokenType.GREATER_OR_EQUALS ;
			tok = GetNext() ;
		}
		else if (tok.GetType() == CTokenType.EQUALS)
		{
			m_KeyCompare = CTokenType.EQUALS ;
			tok = GetNext() ;
		}
		else if (tok.GetKeyword() == CCobolKeywordList.GREATER)
		{
			tok = GetNext() ;
			if (tok.GetKeyword() == CCobolKeywordList.THAN)
			{
				tok = GetNext() ;
			}
			m_KeyCompare = CTokenType.GREATER_THAN ;
		}
		else if (tok.GetKeyword() == CCobolKeywordList.EQUALS)
		{
			tok = GetNext() ;
			m_KeyCompare = CTokenType.EQUALS ;
		}
		else if (tok.GetKeyword() == CCobolKeywordList.NOT)
		{
			tok = GetNext() ;
			if (tok.GetKeyword() == CCobolKeywordList.LESS)
			{
				m_KeyCompare = CTokenType.GREATER_OR_EQUALS ;
				tok = GetNext() ;
				if (tok.GetKeyword() == CCobolKeywordList.THAN)
				{
					tok = GetNext() ;
				}
			}
			else if (tok.GetType() == CTokenType.LESS_THAN)
			{
				m_KeyCompare = CTokenType.GREATER_OR_EQUALS ;
				tok = GetNext() ;
			}
			else 
			{
				return false ;
			}
		}
		else 
		{
			return false ;
		}
		
		m_Value = ReadTerminal();
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.INVALID)
		{
			tok = GetNext();
			if (tok.GetKeyword() == CCobolKeywordList.KEY)
			{
				tok = GetNext();
			}
			m_OnInvalidKey = new CGenericBloc("OnInvalidKey",  tok.getLine());
			if (!Parse(m_OnInvalidKey))
			{
				return false ;
			}
		}
		
		return true;
	}
	protected Element ExportCustom(Document root)
	{
		Element eStart = root.createElement("Start") ;
		Element eFile = root.createElement("File");
		eStart.appendChild(eFile);
		m_FileDesc.ExportTo(eFile, root);
		
		String cs = "Key" ;
		if (m_KeyCompare == CTokenType.EQUALS)
		{
			cs = "KeyEquals" ;
		}
		else if (m_KeyCompare == CTokenType.GREATER_THAN)
		{
			cs = "KeyGreaterThan" ;
		}
		else if (m_KeyCompare == CTokenType.GREATER_OR_EQUALS)
		{
			cs = "KeyGreaterThanOrEquals" ;
		}
		else
		{
			Transcoder.logError(getLine(), "Unexpecting situation");
		}
		Element eKey = root.createElement(cs);
		eStart.appendChild(eKey);
		m_Value.ExportTo(eKey, root); 
		return eStart;
	}

	protected CIdentifier m_FileDesc = null ;
	protected CTerminal m_Value = null ;
	protected CTokenType m_KeyCompare = null ;
	protected CGenericBloc m_OnInvalidKey = null ;
}
