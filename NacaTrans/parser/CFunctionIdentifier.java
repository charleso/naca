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
package parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.Cobol.CCobolElement;

import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.CSubStringAttributReference;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CBaseEntityFunction;
import utils.Transcoder;
import lexer.CBaseToken;
import lexer.CReservedKeyword;
import lexer.CTokenList;
import lexer.Cobol.CCobolKeywordList;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CFunctionIdentifier extends CIdentifier
{

	/**
	 * @param s
	 */
	public CFunctionIdentifier(CTokenList lstTokens, CCobolElement owner)
	{
		super("");
		Parse(lstTokens, owner) ;
	}
	
	protected void Parse(CTokenList lstTokens, CCobolElement owner)
	{
		CBaseToken tok = lstTokens.GetCurrentToken();
		if (tok.GetKeyword() == CCobolKeywordList.FUNCTION)
		{
			tok = lstTokens.GetNext();
		}
		
		if (tok.GetKeyword() == CCobolKeywordList.CURRENT_DATE)
		{
			m_Function = tok.GetKeyword() ;
			lstTokens.GetNext() ;
		}
		else if (tok.GetKeyword() == CCobolKeywordList.LENGTH)
		{
			CBaseToken tokOf = lstTokens.GetNext() ;
			if (tokOf.GetKeyword() == CCobolKeywordList.OF)
			{ 
				m_Function = tok.GetKeyword() ;
				lstTokens.GetNext() ;
				m_Parameter = owner.ReadIdentifier();
			}
			else
			{
				Transcoder.logError(tok.getLine(), "Unexpecting situation");
			}
		}
		else if (tok.GetKeyword() == CCobolKeywordList.ADDRESS)
		{
			CBaseToken tokOf = lstTokens.GetNext() ;
			if (tokOf.GetKeyword() == CCobolKeywordList.OF)
			{ 
				m_Function = tok.GetKeyword() ;
				lstTokens.GetNext() ;
				m_Parameter = owner.ReadIdentifier();
			}
			else
			{
				Transcoder.logError(tok.getLine(), "Unexpecting situation");
			}
		}
		else
		{
			Transcoder.logError(tok.getLine(), "Unexpecting token : "+tok.GetValue());
		}
	}

	public CDataEntity GetDataReference(int nLine, CBaseEntityFactory fact)
	{
		CBaseEntityFunction f = null ;
		if (m_Function == CCobolKeywordList.LENGTH)
		{
			CDataEntity e = m_Parameter.GetDataReference(nLine, fact);
			f = fact.NewEntityLengthOf(e);
		}
		else if (m_Function == CCobolKeywordList.ADDRESS)
		{
			CDataEntity e = m_Parameter.GetDataReference(nLine, fact);
			f = fact.NewEntityAddressOf(e);
		}
		else if (m_Function == CCobolKeywordList.CURRENT_DATE)
		{
			f = fact.NewEntityCurrentDate();
			if (m_exprStringLengthReference != null & m_exprStringStartReference != null)
			{
				CSubStringAttributReference ref = fact.NewEntitySubString(nLine);
				CBaseEntityExpression start = m_exprStringStartReference.AnalyseExpression(fact) ;
				CBaseEntityExpression len = m_exprStringLengthReference.AnalyseExpression(fact) ;
				ref.SetReference(f, start, len) ;
				return ref ;
			}
		}
		else 
		{
			Transcoder.logError(nLine, "Missing semantic analysis for FUNCTIONS");
			f = null ;
		}
		return f ;
	}

	public void ExportTo(Element e, Document root)
	{
		if (m_Function == CCobolKeywordList.LENGTH)
		{
			Element eLen = root.createElement("LengthOf");
			e.appendChild(eLen);
			m_Parameter.ExportTo(eLen, root);
		}
		else if (m_Function == CCobolKeywordList.ADDRESS)
		{
			Element eLen = root.createElement("AddressOf");
			e.appendChild(eLen);
			m_Parameter.ExportTo(eLen, root);
		}
		else if (m_Function == CCobolKeywordList.CURRENT_DATE)
		{
			e.setAttribute("Function", "Current-Date") ;
		}
		else 
		{
			Element eLen = root.createElement("Undefined");
			e.appendChild(eLen);
		}
	}
	
	protected CReservedKeyword m_Function = null ;
	protected CIdentifier m_Parameter = null ; 
}
