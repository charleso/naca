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
 * Created on 13 août 2004
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
import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.Verbs.CEntityDivide;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CDivide extends CCobolElement
{

	/**
	 * @param line
	 */
	public CDivide(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityDivide eDivide = factory.NewEntityDivide(getLine());
		parent.AddChild(eDivide);
		CDataEntity eWhat = m_DivideWhat.GetDataEntity(getLine(), factory);
		eWhat.RegisterReadingAction(eDivide) ;
		CDataEntity eBy = m_DivideBy.GetDataEntity(getLine(), factory);
		eBy.RegisterReadingAction(eDivide) ;
		if (m_Result != null)
		{
			CDataEntity eResult = m_Result.GetDataReference(getLine(), factory) ;
			eResult.RegisterWritingAction(eDivide) ;
			eDivide.SetDivide(eWhat, eBy, eResult,m_bIsRounded);
		}
		else
		{
			eDivide.SetDivide(eWhat, eBy, m_bIsRounded);
		}
		
		if (m_Remainder != null)
		{
			CDataEntity eRem = m_Remainder.GetDataReference(getLine(), factory);
			eRem.RegisterWritingAction(eDivide) ;
			eDivide.SetRemainder(eRem);
		}
		return eDivide;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.DIVIDE)
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;

		tok = GetNext();
		m_DivideWhat = ReadTerminal();
		
		tok = GetCurrentToken();
		if (tok.GetKeyword() == CCobolKeywordList.INTO)
		{
			GetNext();
			m_DivideBy = m_DivideWhat ;
			m_DivideWhat = ReadTerminal();
		}
		else if (tok.GetKeyword() == CCobolKeywordList.BY)
		{
			GetNext();
			m_DivideBy = ReadTerminal();
		}
		else
		{
			Transcoder.logError(tok.getLine(), "Unexpecting token : " + tok.GetValue()) ;
			return false ;
		}
		
		tok = GetCurrentToken();
		if (tok.GetKeyword() == CCobolKeywordList.ROUNDED)
		{
			m_bIsRounded = true ;
			tok = GetNext();
		}
		else if (tok.GetKeyword() == CCobolKeywordList.GIVING)
		{
			GetNext() ;
			m_Result = ReadIdentifier();
			
			tok = GetCurrentToken();
			if (tok.GetKeyword() == CCobolKeywordList.ROUNDED)
			{
				m_bIsRounded = true ;
				tok = GetNext();
			}
			if (tok.GetType() == CTokenType.COMMA)
			{
				tok = GetNext();
			}
			if (tok.GetKeyword() == CCobolKeywordList.REMAINDER)
			{
				GetNext();
				m_Remainder = ReadIdentifier();
			}
		}
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.END_DIVIDE)
		{
			GetNext() ;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eDiv = root.createElement("Divide") ;
		if (m_bIsRounded)
		{
			eDiv.setAttribute("Rounded", "true");
		}
		Element eWhat = root.createElement("Divide") ;
		eDiv.appendChild(eWhat);
		m_DivideWhat.ExportTo(eWhat, root);
		Element eBy = root.createElement("By") ;
		eDiv.appendChild(eBy) ;
		m_DivideBy.ExportTo(eBy, root) ;
		if (m_Result != null)
		{
			Element eTo = root.createElement("To") ;
			eDiv.appendChild(eTo) ;
			m_Result.ExportTo(eTo, root) ;
		}
		if (m_Remainder != null)
		{
			Element eRem = root.createElement("Remainder") ;
			eDiv.appendChild(eRem) ;
			m_Remainder.ExportTo(eRem, root) ;
		}
		return eDiv;
	}

	protected CTerminal m_DivideWhat = null ;
	protected CTerminal m_DivideBy = null ; 
	protected CIdentifier m_Result = null ;
	protected CIdentifier m_Remainder = null ;
	protected boolean m_bIsRounded = false ;
}
