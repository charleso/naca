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
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.expression.CExpression;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.Verbs.CEntityCallFunction;
import semantic.Verbs.CEntityLoopWhile;
import semantic.expression.CBaseEntityCondition;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CPerformUntil extends CBlocElement
{
	
	public CPerformUntil(CIdentifier ref, CIdentifier refThru, int line, boolean bBefore)
	{
		super(line) ;
		m_Reference = ref ;
		m_RefThru = refThru ;
		m_bTestBefore = bBefore ;
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CGlobalEntityCounter.GetInstance().CountCobolVerb("PERFORM_UNTIL") ;
		CBaseToken tokUntil = GetCurrentToken() ;
		if (tokUntil.GetKeyword() != CCobolKeywordList.UNTIL)
		{
			Transcoder.logError(getLine(), "Expecting 'UNTIL' keyword") ;
			return false ;
		}
		GetNext() ;
		m_cond = ReadConditionalStatement() ;
		if (m_cond == null)
		{
			Transcoder.logError(getLine(), "No condition could be read as UNTIL condition") ;
			return false ;
		}  
		if (m_Reference == null)
		{
			// no reference provided, the code is inside
			if (!super.DoParsing())
			{
				Transcoder.logError(getLine(), "Failure while parsing PERFORM bloc") ;
				return false ;
			}
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetKeyword() != CCobolKeywordList.END_PERFORM)
			{
				Transcoder.logError(getLine(), "Expecting 'END-PERFORM' keyword") ;
				return false ;
			}
			else
			{
				m_nEndLine = tok.getLine() ;
				GetNext() ;
			}
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element ePerf = root.createElement("PerformUntil") ;
		if (m_Reference != null)
		{
			ePerf.setAttribute("Reference", m_Reference.GetName()) ;
		}
		if (m_RefThru != null)
		{
			ePerf.setAttribute("Thru", m_RefThru.GetName()) ;
		}
		Element eCond = m_cond.Export(root) ;
		ePerf.appendChild(eCond) ;
		return ePerf ;
	}
	protected CExpression m_cond = null ; 
	protected CIdentifier m_Reference = null ;
	protected CIdentifier m_RefThru = null ;
	boolean m_bTestBefore = true ;
	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityLoopWhile eLoop = factory.NewEntityLoopWhile(getLine()) ;
		parent.AddChild(eLoop) ;
		CBaseEntityCondition cond = m_cond.AnalyseCondition(factory);
		if (m_bTestBefore)
		{
			eLoop.SetUntilCondition(cond) ;
		}
		else
		{
			eLoop.SetDoUntilCondition(cond) ;
		}

		if (m_RefThru != null)
		{
			CEntityCallFunction e = factory.NewEntityCallFunction(getLine(), m_Reference.GetName(), m_RefThru.GetName(), parent.getSectionContainer()) ;
			eLoop.AddChild(e) ;
			return e;
		}
		else if (m_Reference != null)
		{
			CEntityCallFunction e = factory.NewEntityCallFunction(getLine(), m_Reference.GetName(), "", parent.getSectionContainer()) ;
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
