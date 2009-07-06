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

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CCommentContainer;
import parser.expression.CExpression;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.CEntityBloc;
import semantic.CEntityCondition;
import semantic.expression.CBaseEntityCondition;
import utils.CGlobalEntityCounter;
import utils.LevelKeywordStackManager;
import utils.LevelKeywords;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CIfStatement extends CCommentContainer
{
	/**
	 * @param line
	 */
	public CIfStatement(int line) {
		super(line);
	}
	
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing(CFlag fCheckForNextSentence)
	{
		CBaseToken tokIf = GetCurrentToken() ;
		if (tokIf.GetKeyword()!=CCobolKeywordList.IF)
		{
			Transcoder.logError(getLine(), "Expecting 'IF' keyword") ;
			return false ; 
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tokIf.GetKeyword().m_Name) ;
		GetNext() ;
		
		if (!ReadCondition())
		{
			Transcoder.logError(getLine(), "Failure while reading condition") ;
			return false ;
		} 
		
		if (!ReadThenStatement(fCheckForNextSentence))
		{
			Transcoder.logError(getLine(), "Failure while reading the THEN BLOC") ;
			return false ;
		}			
		return true ;
	}
	
	protected boolean ReadCondition()
	{
		m_Condition = ReadConditionalStatement() ;
		if (m_Condition == null)
		{
			return false ;
		}
		return true ;
	}
	protected boolean ReadThenStatement(CFlag fCheckForNextSentence)
	{
		m_ThenBloc = new CThenBloc(GetCurrentToken().getLine()) ;
		if (!Parse(m_ThenBloc, fCheckForNextSentence))
		{
			Transcoder.logError(getLine(), "Failure while parsing THEN bloc") ;
			return false ;
		}
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.ELSE)
		{
			CFlag f = new CFlag() ;
			if (!ReadElseStatement(f))
			{
				return false ;
			}
			if (f.ISSet())
			{
				fCheckForNextSentence.Set() ;
			}
			return true ;
		}
		else if (tok.GetKeyword() == CCobolKeywordList.END_IF)
		{
			StepNext();
			m_ThenBloc.SetEndLine(tok.getLine()) ;
			return true ;
		}
		else if (tok.GetType() == CTokenType.DOT)
		{
			m_ThenBloc.SetEndLine(tok.getLine()) ;
			return true ;
		}
		else
		{
			return true ; // any unmatched word is valid to close a IF statement...
		}
	}
	protected boolean ReadElseStatement(CFlag fCheckForNextSentence)
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.IsKeyword() && tok.GetKeyword() == CCobolKeywordList.ELSE)
		{
			m_ElseBloc = new CElseBloc(tok.getLine()) ;
			if (!Parse(m_ElseBloc, fCheckForNextSentence))
			{
				Transcoder.logError(getLine(), "Failure while parsing ELSE bloc") ;
				return false ;
			}
			return true ;
		}
		else if (tok.IsKeyword() && tok.GetKeyword() == CCobolKeywordList.END_IF)
		{
			m_ElseBloc.SetEndLine(tok.getLine()) ;
			StepNext() ;
			return true ;
		}
		else if (tok.GetType() == CTokenType.DOT)
		{
			m_ElseBloc.SetEndLine(tok.getLine()) ;
			return true ;
		}
		else
		{
			Transcoder.logError(getLine(), "Unexpecting token : " + tok.GetValue()) ;
			return false ;
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		if (m_Condition == null || m_ThenBloc == null)
		{
			Element e = root.createElement("UnparsedIF") ;
			return e ;
		}
		Element eIf = root.createElement("IF") ;
		Element eCond = root.createElement("Condition") ;
		eIf.appendChild(eCond) ;
		eCond.appendChild(m_Condition.Export(root)) ;

		ExportChildren(root, eIf) ;
		Element eThen = m_ThenBloc.Export(root) ;
		if (eThen == null)
		{
			int n = 0 ;
		}
		eIf.appendChild(eThen) ;
		if (m_ElseBloc != null)
		{
			Element eElse = m_ElseBloc.Export(root) ;
			if (eElse == null)
			{
				int n = 0 ;
			}
			eIf.appendChild(eElse) ;
		}
		return eIf;
	}
	
	protected CExpression m_Condition = null ;
	protected CElseBloc m_ElseBloc = null ;
	protected CThenBloc m_ThenBloc = null ;
	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCondition eIf = factory.NewEntityCondition(getLine()) ;
		parent.AddChild(eIf) ;
		
		CEntityBloc blocThen = null ;
		if (m_ThenBloc != null)
		{
			blocThen = (CEntityBloc)m_ThenBloc.DoSemanticAnalysis(eIf, factory);
			eIf.AddChild(blocThen) ;
		}
		CEntityBloc blocElse = null ;
		if (m_ElseBloc != null)
		{
			blocElse = (CEntityBloc)m_ElseBloc.DoSemanticAnalysis(eIf, factory);
			eIf.AddChild(blocElse) ;
		}
		CBaseEntityCondition eCond = m_Condition.AnalyseCondition(factory);
		eIf.SetCondition(eCond, blocThen, blocElse) ;
		return eIf;
	}
}
