/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jul 27, 2004
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
import parser.expression.CExpression;
import parser.expression.CTerminal;
import semantic.CDataEntity;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.Verbs.CEntityCallFunction;
import semantic.Verbs.CEntityLoopIter;
import semantic.expression.CBaseEntityCondition;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CPerformVarying extends CBlocElement
{
	protected CIdentifier m_Reference = null ;
	protected CIdentifier m_RefThru = null ;
	protected CIdentifier m_Variable = null ;
	protected CTerminal m_varFromValue = null ;
	protected CTerminal m_varByValue = null ;
	protected CExpression m_condUntil = null ;
	protected boolean m_bTestBefore = true ;

	protected CIdentifier m_VariableAfter = null ;
	protected CTerminal m_varFromValueAfter = null ;
	protected CTerminal m_varByValueAfter = null ;
	protected CExpression m_condUntilAfter = null ;
	
	public CPerformVarying(CIdentifier Ref, CIdentifier refThru, int line, boolean bBefore)
	{
		super(line);
		m_Reference = Ref ;
		m_RefThru = refThru ;
		m_bTestBefore = bBefore ;
	}
	
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CGlobalEntityCounter.GetInstance().CountCobolVerb("PERFORM_VARYING") ;
		CBaseToken tokVary = GetCurrentToken() ;
		if (tokVary.GetKeyword() != CCobolKeywordList.VARYING)
		{
			Transcoder.logError(getLine(), "Expecting 'VARYING' keyword") ;
			return false ;
		}
		
		CBaseToken tokVar = GetNext() ;
		if (tokVar.GetType() != CTokenType.IDENTIFIER)
		{
			Transcoder.logError(getLine(), "Expecting an identifier as varying variable") ;
			return false ;
		}
		m_Variable = ReadIdentifier() ;
		
		CBaseToken tokFrom = GetCurrentToken() ;
		if (tokFrom.GetKeyword() != CCobolKeywordList.FROM)
		{
			Transcoder.logError(getLine(), "Expecting 'FROM' keyword") ;
			return false ;
		}
		
		CBaseToken tokValFrom = GetNext() ;
		m_varFromValue = ReadTerminal() ;
		
		CBaseToken tokBy = GetCurrentToken();
		if (tokBy.GetKeyword() == CCobolKeywordList.BY)
		{
			tokBy = GetNext() ;
			m_varByValue = ReadTerminal() ;
		} 
		
		CBaseToken tokUntil = GetCurrentToken() ;
		if (tokUntil.GetKeyword() == CCobolKeywordList.UNTIL)
		{
			GetNext() ;
			m_condUntil = ReadConditionalStatement() ;
		}
		else
		{
			Transcoder.logError(getLine(), "Expecting 'UNTIL' keyword") ;
			return false ;
		} 
		
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.AFTER)
		{
			tok = GetNext() ;
			m_VariableAfter = ReadIdentifier();
			tok = GetCurrentToken() ;
			if (tok.GetKeyword() != CCobolKeywordList.FROM)
			{
				Transcoder.logError(getLine(), "Unexpecting situation") ;
				return false ;
			}
			tok = GetNext() ;
			m_varFromValueAfter = ReadTerminal() ;
			tok = GetCurrentToken() ;
			if (tok.GetKeyword() != CCobolKeywordList.BY)
			{
				Transcoder.logError(getLine(), "Unexpecting situation") ;
				return false ;
			}
			tok = GetNext() ;
			m_varByValueAfter = ReadTerminal() ;
			tok = GetCurrentToken() ;
			if (tok.GetKeyword() != CCobolKeywordList.UNTIL)
			{
				Transcoder.logError(getLine(), "Unexpecting situation") ;
				return false ;
			} 
			tok = GetNext() ;
			m_condUntilAfter = ReadConditionalStatement() ;
		} 
		
		if (m_Reference == null)
		{	// there is no reference to paragraph, the perform must run code inside him.
			if (!super.DoParsing())
			{
				Transcoder.logError(getLine(), "Failure while parsing PERFORM bloc") ;
				return false ;
			}
			CBaseToken tokEnd = GetCurrentToken() ;
			if (tokEnd.GetKeyword() != CCobolKeywordList.END_PERFORM)
			{
				Transcoder.logError(getLine(), "Expecting 'END-PERFORM' keyword") ;
				return false ;
			}
			m_nEndLine = tok.getLine() ;
			GetNext();
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element ePerf = root.createElement("PerfomVarying") ;
		Element eVar = root.createElement("Variable") ;
		ePerf.appendChild(eVar) ;
		m_Variable.ExportTo(eVar, root) ;
		Element eFrom = root.createElement("From") ;
		ePerf.appendChild(eFrom) ;
		m_varFromValue.ExportTo(eFrom, root) ;
		if (m_varByValue != null)
		{
			Element eBy = root.createElement("By") ;
			ePerf.appendChild(eBy) ;
			m_varByValue.ExportTo(eBy, root) ;
		}
		if (m_Reference != null)
		{
			ePerf.setAttribute("Reference", m_Reference.GetName()) ;
		}
		if (m_RefThru != null)
		{
			ePerf.setAttribute("Thru", m_RefThru.GetName()) ;
		}
		if (m_condUntil != null)
		{
			Element eUntil = root.createElement("UntilCondition") ;
			ePerf.appendChild(eUntil) ;
			Element eCond = m_condUntil.Export(root) ;
			eUntil.appendChild(eCond) ;
		}
		return ePerf ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityLoopIter eLoop = factory.NewEntityLoopIter(getLine()) ;
		CDataEntity eVar = m_Variable.GetDataReference(getLine(), factory) ; 
		eVar.RegisterWritingAction(eLoop) ;
		CDataEntity eFrom = m_varFromValue.GetDataEntity(getLine(), factory);
		if (!m_varByValue.IsReference())
		{
			if (m_varByValue.GetValue().equals("1"))
			{
				eLoop.SetLoopIterInc(eVar, eFrom);
			}
			else if (m_varByValue.GetValue().equals("-1"))
			{
				eLoop.SetLoopIterDec(eVar, eFrom);
			}
			else
			{
				CDataEntity eBy = m_varByValue.GetDataEntity(getLine(), factory);
				eLoop.SetLoopIter(eVar, eFrom, eBy);
			}
		}
		else
		{
			CDataEntity eBy = m_varByValue.GetDataEntity(getLine(), factory);
			eLoop.SetLoopIter(eVar, eFrom, eBy);
		}
		CBaseEntityCondition condUntil = m_condUntil.AnalyseCondition(factory); 
		eLoop.SetUntilCondition(condUntil, m_bTestBefore) ;
		parent.AddChild(eLoop) ;
		
		if (m_RefThru != null)
		{
			CEntityCallFunction e = factory.NewEntityCallFunction(getLine(), m_Reference.GetName(), m_RefThru.GetName(), parent.getSectionContainer()) ;
			factory.m_ProgramCatalog.RegisterPerformThrough(e) ;
			eLoop.AddChild(e) ;
			return e;
		}
		else if (m_Reference != null)
		{
			CEntityCallFunction e = factory.NewEntityCallFunction(getLine(), m_Reference.GetName(), "", eLoop.getSectionContainer()) ;
			eLoop.AddChild(e) ;
			return e;
		}
		return eLoop;
	}

	/* (non-Javadoc)
	 * @see parser.elements.CBlocElement#isTopLevelBloc()
	 */
	protected boolean isTopLevelBloc()
	{
		return false;
	}
}
