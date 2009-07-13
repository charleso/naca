/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jul 19, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import java.util.Vector;

import lexer.*;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.Cobol.CCobolElement;
import parser.condition.CCondAndStatement;
import parser.condition.CCondEqualsStatement;
import parser.condition.CCondGreaterStatement;
import parser.condition.CCondLessStatement;
import parser.condition.CCondNotStatement;
import parser.condition.CCondOrStatement;
import parser.expression.CConstantTerminal;
import parser.expression.CExpression;
import parser.expression.CTermExpression;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.Verbs.CEntitySwitchCase;
import utils.CGlobalEntityCounter;
import utils.Transcoder;


/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CEvaluate extends CCobolElement
{
	/**
	 * @param line
	 */
	public CEvaluate(int line) {
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{ // start parsing EVALUATE statement
		CBaseToken tokEval = GetCurrentToken() ;
		if (tokEval.GetKeyword() != CCobolKeywordList.EVALUATE)
		{
			Transcoder.logError(getLine(), "Expecting 'EVALUATE' keyword") ;
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tokEval.GetKeyword().m_Name) ;
		// read evaluate expression
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tokId = GetNext() ;
			CExpression term = ReadExpression() ;
			if (term == null)
			{
				Transcoder.logError(getLine(), "Can't read terminal to evaluate") ;
				return false ;
			}
			else
			{
				m_arrIdToEval.add(term) ;
			}
			CBaseToken tokAlso = GetCurrentToken() ;
			if (tokAlso.GetKeyword() != CCobolKeywordList.ALSO)
			{
				bDone = true ;
			}
			else
			{
				//GetNext();
			}
		}
					
		// read WHEN statements
		bDone = false ;
		while (!bDone)
		{
			CBaseToken tokWhen = GetCurrentToken();
			if (tokWhen.GetType()==CTokenType.KEYWORD && tokWhen.GetKeyword()==CCobolKeywordList.WHEN)
			{
				CExpression cond = ReadWhenCondition();
				if (cond == null)
				{
					Transcoder.logError(getLine(), "Can't read condition") ;
					return false ;
				}
				CCobolElement eWhen = new CWhenBloc(cond, tokWhen.getLine());
				AddChild(eWhen) ;
				if (!Parse(eWhen))
				{
					Transcoder.logError(getLine(), "Can't parse WHEN statement") ;
					return false ;
				}
			}
//			else if (tokWhen.GetType() == CTokenType.COMMENT)
//			{
//				if (!ParseComment())
//				{
//					return false ;
//				}
//			}
			else if (tokWhen.GetType() == CTokenType.KEYWORD && tokWhen.GetKeyword()==CCobolKeywordList.END_EVALUATE)
			{
				GetNext() ;
				bDone = true ;
			}
			else
			{
				//m_Logger.error("ERROR Line " +getLine()+ " : " + "Unexpected token : " + tokWhen.GetValue()) ;
				bDone = true ; 
			}
		}
		return true ;
	}
		
	protected CExpression ReadWhenCondition()
	{
		CExpression condGlobal = null ;
		boolean bDone = false ;
		while (!bDone)
		{ // there are maybe several 'when' clauses
			int i = 0;
			CBaseToken tokWhen = GetCurrentToken();
			CExpression cond = null;
			if (tokWhen.GetKeyword() == CCobolKeywordList.WHEN)
			{
				CBaseToken tokOther = GetNext();
				if (tokOther.GetKeyword() == CCobolKeywordList.OTHER)
				{
					GetNext() ;
					return new CTermExpression(tokOther.getLine(), new CConstantTerminal("OTHER")) ;
				}
				else
				{
					cond = ReadSingleCondition(i) ;
					i++ ;
					while (m_arrIdToEval.size() > i)
					{	// there must be some 'also' statements
						CBaseToken tok = GetCurrentToken();
						if (tok.GetKeyword() == CCobolKeywordList.ALSO)
						{
							GetNext() ;
							CExpression tempcond = ReadSingleCondition(i) ;
							i++ ;
							if (tempcond != null)
							{
								if (cond == null)
								{
									cond = tempcond;
								}
								else
								{
									cond = new CCondAndStatement(tok.getLine(), cond, tempcond) ;
								}								
							}
						}
						else
						{
							Transcoder.logError(getLine(), "Expecting 'ALSO' keyword") ;
							return null ; // there must be a 'also' keyword, because there are more than one eval expressions
						}
					}
				}
			}
			else
			{
				bDone = true ;
			}
			if (cond != null)
			{
				if (condGlobal == null)
				{
					condGlobal = cond ;
				}
				else
				{
					condGlobal = new CCondOrStatement(tokWhen.getLine(), condGlobal, cond) ;
				}
			}
		}
		return condGlobal ;
	}
	
	protected CExpression ReadSingleCondition(int i)
	{
		CExpression cond = null ;
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.ANY)
		{
			GetNext();
			return null ; // in this case the statement is trivial.
		}
		else if (tok.GetKeyword() == CCobolKeywordList.NOT)
		{
			GetNext() ; // consume NOT
			CExpression term = ReadExpression() ;
			if (term != null)
			{
				cond = new CCondEqualsStatement(tok.getLine(), m_arrIdToEval.get(i), term) ;
				cond = new CCondNotStatement(tok.getLine(), cond) ;
			}
			else
			{
				Transcoder.logError(getLine(), "Expecting terminal in expression") ;
				return null ; // a terminal must be found
			}
		}
		else
		{
			CExpression term = ReadExpression() ;
			if (term != null)
			{
				CExpression expr = m_arrIdToEval.get(i) ;
				CBaseToken tokThru = GetCurrentToken() ;
				if (tokThru.GetKeyword() == CCobolKeywordList.THRU || tokThru.GetKeyword() == CCobolKeywordList.THROUGH)
				{
					GetNext();
					CExpression term2 = ReadExpression() ;
					if (term2 != null)
					{
						CExpression e1 = new CCondGreaterStatement(tokThru.getLine(), expr, term, true) ;
						CExpression e2 = new CCondLessStatement(tokThru.getLine(), expr, term2, true) ;
						cond = new CCondAndStatement(tokThru.getLine(), e1, e2);
					}
					else
					{
						Transcoder.logError(getLine(), "Can't read expression") ;
						return null ;
					}
				}
				else
				{
					cond = new CCondEqualsStatement(tokThru.getLine(), expr, term) ;
				}
			}
			else
			{
				Transcoder.logError(getLine(), "Expecting terminal in expression") ;
				return null ; // a terminal must be found
			}
		}
		return cond ;
	} 
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element e = root.createElement("Evaluate") ;
		return e ;
	}
	
	protected Vector<CExpression> m_arrIdToEval = new Vector<CExpression>() ;

	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntitySwitchCase e = factory.NewEntitySwitchCase(getLine()) ;
		parent.AddChild(e) ;
		return e ;
	}
}
