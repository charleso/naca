/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 12 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
import semantic.Verbs.CEntitySubtractTo;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CSubtract extends CCobolElement
{

	/**
	 * @param line
	 */
	public CSubtract(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		List<CDataEntity> eValues = new ArrayList<CDataEntity>(m_Value.size());
		for (CTerminal value : m_Value)
		{
			eValues.add(value.GetDataEntity(getLine(), factory));
		}
		for (int i=0; i<m_arrVariables.size(); i++)
		{
			CEntitySubtractTo eSub = factory.NewEntitySubtractTo(getLine());
			parent.AddChild(eSub) ;

			CTerminal variable = m_arrVariables.get(i) ;
			CDataEntity eVar = variable.GetDataEntity(getLine(), factory);
			eVar.RegisterReadingAction(eSub) ;
			for (CDataEntity eValue : eValues)
			{
				eValue.RegisterReadingAction(eSub) ;
			}
			List<CDataEntity> eRess = new ArrayList<CDataEntity>() ;
			for (CIdentifier idRes : m_arrResult)
			{
				CDataEntity eRes = idRes.GetDataReference(getLine(), factory);
				eRes.RegisterWritingAction(eSub) ;
				eRess.add(eRes);
			}
			eVar.RegisterWritingAction(eSub) ;
			eSub.SetSubstract(eVar, eValues, eRess);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.SUBTRACT)
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		tok = GetNext() ;
		while (tok.GetType() == CTokenType.NUMBER || tok.GetType() == CTokenType.IDENTIFIER)
		{
			m_Value.add(ReadTerminal());
			tok = GetCurrentToken();
		}
		if (tok.GetKeyword() != CCobolKeywordList.FROM)
		{
			Transcoder.logError(tok.getLine(), "Expecting FROM") ;
			return false ;
		}
		tok = GetNext();
		CTerminal term = ReadTerminal() ;
		while (term != null)
		{
			m_arrVariables.add(term); 
			tok = GetCurrentToken() ;
			if (tok.GetType() == CTokenType.COMMA)
			{
				tok = GetNext() ;
			}
			term = ReadTerminal() ;
		}
		tok = GetCurrentToken();
		if (tok.GetKeyword() == CCobolKeywordList.GIVING)
		{
			tok = GetNext();
			CIdentifier variable = ReadIdentifier() ;
			while (variable != null)
			{
				m_arrResult.add(variable); 
				variable = ReadIdentifier() ;
			}
		}
		tok =GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.ROUNDED)
		{
			tok = GetNext() ;
			m_bRounded = true ;
		}
		if(tok.GetKeyword() == CCobolKeywordList.END_SUBTRACT)
		{
			tok = GetNext() ;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element e = root.createElement("Substract") ;
		for (CTerminal value : m_Value)
		{
			value.ExportTo(e, root) ;
		}
		for (int i=0; i<m_arrVariables.size(); i++)
		{
			Element eTo = root.createElement("From") ;
			CTerminal variable = m_arrVariables.get(i) ;
			variable.ExportTo(eTo, root) ;
			e.appendChild(eTo) ;
			if (m_arrResult.size() == m_arrVariables.size())
			{
				Element eToOther = root.createElement("To") ;
				CIdentifier variableOther = m_arrResult.get(i) ;
				variableOther.ExportTo(eToOther, root) ;
				eTo.appendChild(eToOther) ;
			}
		}
		return e ;
	}
	
	protected List<CTerminal> m_Value = new ArrayList<CTerminal>();
	protected boolean m_bRounded ;
	protected Vector<CTerminal> m_arrVariables = new Vector<CTerminal>() ;
	protected Vector<CIdentifier> m_arrResult = new Vector<CIdentifier>() ;
}
