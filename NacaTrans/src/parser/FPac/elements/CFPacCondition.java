/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package parser.FPac.elements;

import java.util.Vector;

import lexer.CBaseToken;
import lexer.FPac.CFPacKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.FPac.CFPacElement;
import parser.expression.CExpression;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CEntityBloc;
import semantic.CEntityCondition;
import semantic.expression.CBaseEntityCondition;

public class CFPacCondition extends CFPacElement
{

	private CExpression m_expCondition;
	private CFPacCodeBloc m_ThenBloc ;
	private CFPacCodeBloc m_ElseBloc ;
	private int m_nEndLine = 0 ;
	private Vector<CFPacCondition> m_arrElseIfStatement = null ;
	private boolean m_bElseIfStatement = false ; 

	public CFPacCondition(int line)
	{
		super(line);
	}

	@Override
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken();
		if (tok.GetKeyword() == CFPacKeywordList.IF)
		{
			tok = GetNext() ;
		}
		else if (tok.GetKeyword() == CFPacKeywordList.ELSEIF)
		{
			m_bElseIfStatement  = true ;
			tok = GetNext() ;
		}
		
		CExpression exp = ReadCondition() ;
		if (exp == null)
			return false ;
		m_expCondition = exp ;
		
		tok = GetCurrentToken() ;
		if(tok.GetKeyword() == CFPacKeywordList.THEN)
		{
			tok = GetNext() ;
		}
		m_ThenBloc = new CFPacCodeBloc(tok.getLine(), "") ;
		if (!Parse(m_ThenBloc))
		{
			return false  ;
		}
		
		if (m_bElseIfStatement)
			return true ; // in case of ELSEIF statement, the ELSE and ENDIF keywords are parsed by parent.
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.ELSEIF)
		{
			m_arrElseIfStatement = new Vector<CFPacCondition>() ;
			while (tok.GetKeyword() == CFPacKeywordList.ELSEIF)
			{
				CFPacCondition elseIfStatement  = new CFPacCondition(tok.getLine()) ;
				if (!Parse(elseIfStatement))
				{
					return false ;
				}
				m_arrElseIfStatement.add(elseIfStatement) ;
				tok = GetCurrentToken() ;
			}
		}

		if (tok.GetKeyword() == CFPacKeywordList.ELSE)
		{
			m_ElseBloc = new CFPacCodeBloc(tok.getLine(), "") ;
			StepNext();
			if (!Parse(m_ElseBloc))
			{
				return false ;
			}
		}
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.IFEND)
		{
			m_nEndLine = tok.getLine() ;
			StepNext() ;
		}		
		return true ;
		
	}

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCondition cond = factory.NewEntityCondition(getLine()) ;
		parent.AddChild(cond) ;
		
		CBaseEntityCondition exp = m_expCondition.AnalyseCondition(factory) ;
		CEntityBloc blocthen = (CEntityBloc)m_ThenBloc.DoSemanticAnalysis(cond, factory) ;
		CEntityBloc blocelse = null ;
		if (m_ElseBloc != null)
		{
			blocelse = (CEntityBloc)m_ElseBloc.DoSemanticAnalysis(cond, factory) ;
			blocelse.SetEndLine(m_nEndLine) ;
		}
		else
		{
			blocthen.SetEndLine(m_nEndLine) ;
		}
		if (m_bElseIfStatement)
		{
			cond.SetAlternativeCondition(exp, blocthen) ;
		}
		else
		{
			cond.SetCondition(exp, blocthen, blocelse) ;
			if (m_arrElseIfStatement != null)
			{
				for (CFPacCondition c : m_arrElseIfStatement)
				{
					CBaseLanguageEntity e = c.DoSemanticAnalysis(cond, factory) ;
					cond.addAlternativeCondition(e) ;
				}
			}
		}
		
		return cond;
	}

	@Override
	protected Element ExportCustom(Document root)
	{
		String title = "If" ;
		if (m_bElseIfStatement)
			title = "ElseIf" ;
		Element e = root.createElement(title) ;
		Element eCond = root.createElement("Condition") ;
		e.appendChild(eCond) ;
		eCond.appendChild(m_expCondition.Export(root)) ;
		
		Element eThen = m_ThenBloc.Export(root) ;
		e.appendChild(eThen) ;
		if (m_ElseBloc != null)
		{
			Element eElse = m_ElseBloc.Export(root) ;
			e.appendChild(eElse) ;
		}
		return e ;
	}

}
