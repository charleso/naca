/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jul 28, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import java.util.Vector;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolConstantList;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.Cobol.CCobolElement;
import parser.expression.CTerminal;
import semantic.CDataEntity;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.CEntityNamedCondition;
import semantic.expression.CEntityConstant;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CWorkingValueEntry extends CCobolElement
{
	/**
	 * @param line
	 */
	public CWorkingValueEntry(int line) 
	{
		super(line);
		//m_Reference = ref ;
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok88 = GetCurrentToken() ;
		if (tok88.GetType() != CTokenType.NUMBER || !tok88.GetValue().equals("88"))
		{
			Transcoder.logError(getLine(), "Expecting '88' keyword");
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb("NAMED_CONDITION") ;
		
		CBaseToken tok = GetNext() ;
		if (tok.GetType() != CTokenType.IDENTIFIER)
		{
			Transcoder.logError(getLine(), "Expecting an identifier after '88' keyword");
			return false ;
		} 
		m_csIdentifier = tok.GetValue() ;
		
		tok = GetNext() ;
		if (tok.GetKeyword() != CCobolKeywordList.VALUE && tok.GetKeyword() != CCobolKeywordList.VALUES)
		{
			Transcoder.logError(getLine(), "Expecting 'VALUE' keyword");
			return false ;
		}
		
		tok = GetNext();
		if (tok.GetKeyword() == CCobolKeywordList.IS || tok.GetKeyword() == CCobolKeywordList.ARE)
		{
			tok = GetNext();
		}
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tokVal = GetCurrentToken();
			CTerminal val ; 
			if (tokVal.GetType() == CTokenType.STRING || tokVal.GetType() == CTokenType.NUMBER || tokVal.GetType() == CTokenType.CONSTANT || tokVal.GetType() == CTokenType.MINUS)
			{
				val = ReadTerminal();
				m_arrValues.addElement(val) ;
				
				CBaseToken tokNext = GetCurrentToken();
				if (tokNext.GetType() == CTokenType.COMMA)
				{
					m_arrValues.addElement(val) ; // values are intervals, so for a single value, it is added twice, as an interval of one single value
					GetNext(); // consume ","
				} 
				else if (tokNext.GetType() == CTokenType.STRING || tokNext.GetType() == CTokenType.NUMBER || tokNext.GetType() == CTokenType.CONSTANT)
				{
					m_arrValues.addElement(val) ; // values are intervals, so for a single value, it is added twice, as an interval of one single value
				}
				else if (tokNext.GetKeyword() == CCobolKeywordList.THROUGH || tokNext.GetKeyword() == CCobolKeywordList.THRU)
				{
					tokNext = GetNext();
					if (tokNext.GetType() == CTokenType.STRING || tokNext.GetType() == CTokenType.NUMBER || tokNext.GetType() == CTokenType.CONSTANT)
					{
						val = ReadTerminal();
						m_arrValues.addElement(val) ;
					}
					else
					{
						Transcoder.logError(tokNext.getLine(), "Unexpecting token : "+tokNext.GetValue()) ;
						return false ;
					}
				}
				else
				{
					m_arrValues.addElement(val) ; // values are intervals, so for a single value, it is added twice, as an interval of one single value
					bDone = true ;
				} 
			}
			else
			{
				bDone = true  ;
			}
		} 
		tok = GetCurrentToken() ;
		if (tok.GetType() == CTokenType.DOT)
		{
			GetNext(); // consume DOT at the end of the statement
		}
		if (m_arrValues.size()>0)
		{
			return true ;
		}
		else
		{
			return false ;
		}
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element e = root.createElement("ConditionName") ;
		for (int i=0; i<m_arrValues.size(); i+=2)
		{
			CTerminal term1 = m_arrValues.get(i) ;
			CTerminal term2 = m_arrValues.get(i+1) ;
			if (term1.GetValue().equals(term2.GetValue()))
			{
				Element eval = root.createElement("Value") ;
				term2.ExportTo(eval, root) ;
				e.appendChild(eval) ; 
			}
			else
			{
				Element eval = root.createElement("Interval") ;
				term1.ExportTo(eval, root) ;
				e.appendChild(eval) ; 
				Element eThrough = root.createElement("Through") ;
				term2.ExportTo(eThrough, root) ;
				eval.appendChild(eThrough) ; 
			}
		}
		return e;
	}
	
	protected String m_csIdentifier = "" ;
	protected Vector<CTerminal> m_arrValues = new Vector<CTerminal>() ; // maybe several values
	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityNamedCondition eCond = factory.NewEntityNamedCondition(getLine(), m_csIdentifier) ;
		parent.AddChild(eCond);

		for (int i=0; i<m_arrValues.size(); i+=2)
		{
			CTerminal term1 = m_arrValues.get(i) ;
			CTerminal term2 = m_arrValues.get(i+1) ;
			if (term1.GetValue().equals(term2.GetValue()))
			{
				CDataEntity eVal = term1.GetDataEntity(getLine(), factory);
				if (eVal == null && !term1.IsReference())
				{
					String cs = term1.GetValue() ;
					if (cs.equals(CCobolConstantList.HIGH_VALUE.m_Name) || cs.equals(CCobolConstantList.HIGH_VALUES.m_Name))
					{
						eVal = factory.NewEntityConstant(CEntityConstant.Value.HIGH_VALUE) ;
					}
					else
					{
						eVal = factory.NewEntityUnknownReference(getLine(), term1.toString()) ;
					}
				}
				eCond.AddValue(eVal) ;
			}
			else
			{
				CDataEntity eVal1 = term1.GetDataEntity(getLine(), factory);
				CDataEntity eVal2 = term2.GetDataEntity(getLine(), factory);
				eCond.AddInterval(eVal1, eVal2) ;
			}
		}
		return eCond ;
	} 
}
