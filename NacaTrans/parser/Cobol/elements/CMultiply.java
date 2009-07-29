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
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import parser.expression.CTerminal;
import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.Verbs.CEntityMultiply;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CMultiply extends CCobolElement
{

	/**
	 * @param line
	 */
	public CMultiply(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityMultiply eMult = factory.NewEntityMultiply(getLine()) ;
		parent.AddChild(eMult) ;
		CDataEntity eWhat = m_MultiplyWhat.GetDataEntity(getLine(), factory);
		CDataEntity eBy = m_MultiplyBy.GetDataEntity(getLine(), factory);
		if (m_Result != null)
		{
			CDataEntity eTo = m_Result.GetDataReference(getLine(), factory);
			eMult.SetMultiply(eWhat, eBy, eTo, m_bIsRounded) ;
		}
		else
		{
			eMult.SetMultiply(eWhat, eBy, m_bIsRounded) ;
		}
		return eMult;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken();
		if (tok.GetKeyword() != CCobolKeywordList.MULTIPLY)
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		tok = GetNext();
		m_MultiplyWhat = ReadTerminal();
		
		tok=GetCurrentToken();
		if (tok.GetKeyword() != CCobolKeywordList.BY)
		{
			Transcoder.logError(tok.getLine(), "Unexpecting token : " + tok.GetValue());
			return false ;
		}
		GetNext() ;
		m_MultiplyBy = ReadTerminal();
		
		tok = GetCurrentToken();
		if (tok.GetKeyword() == CCobolKeywordList.ROUNDED)
		{
			m_bIsRounded = true ;
			GetNext() ;
		} 
		else if (tok.GetKeyword() == CCobolKeywordList.GIVING)
		{
			GetNext();
			m_Result = ReadIdentifier();
			tok = GetCurrentToken();
			if (tok.GetKeyword() == CCobolKeywordList.ROUNDED)
			{
				m_bIsRounded = true ;
				GetNext() ;
			} 
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eMult = root.createElement("Multiply");
		Element eWhat = root.createElement("Multiply");
		eMult.appendChild(eWhat) ;
		m_MultiplyWhat.ExportTo(eWhat, root);
		Element eBy = root.createElement("By") ;
		eMult.appendChild(eBy);
		m_MultiplyBy.ExportTo(eBy, root);
		if (m_Result != null)
		{
			Element eResult = root.createElement("To");
			eMult.appendChild(eResult);
			m_Result.ExportTo(eResult, root);			
		}
		return eMult;
	}
	
	protected CTerminal m_MultiplyWhat = null ;
	protected CTerminal m_MultiplyBy = null ;
	protected CIdentifier m_Result = null ;
	protected boolean m_bIsRounded = false ;
}
