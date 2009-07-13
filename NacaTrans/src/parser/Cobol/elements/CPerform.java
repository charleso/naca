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

import lexer.*;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.expression.CIdentifierTerminal;
import parser.expression.CTerminal;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.Verbs.CEntityCallFunction;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CPerform extends CBlocElement
{
	public CPerform(CIdentifier ref, int line)
	{
		super(line);
		m_Reference = ref ;
	}
	public CPerform(CTerminal ref, int line)
	{
		super(line);
		m_refRepetitions = ref ;
	}
	public CPerform(CIdentifier ref, CIdentifier refThru, int line)
	{
		super(line);
		m_Reference = ref ;
		m_RefThru = refThru ;
	}
	public CPerform(CIdentifier ref, CIdentifier refThru, CTerminal rep, int line)
	{
		super(line);
		m_refRepetitions = rep ;
		m_Reference = ref ;
		m_RefThru = refThru ;
	}


	/* (non-Javadoc)
	 * @see parser.CLanguageElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CGlobalEntityCounter.GetInstance().CountCobolVerb("PERFORM") ;
		CBaseToken tok = GetCurrentToken();
		if (tok.GetType() == CTokenType.IDENTIFIER)
		{
			CIdentifier id = ReadIdentifier() ;
			tok = GetCurrentToken() ;
			if (tok.GetKeyword() == CCobolKeywordList.TIMES)
			{
				CTerminal term = new CIdentifierTerminal(id) ; 
				m_refRepetitions = term ;
				GetNext() ;
			}
			else
			{
				Transcoder.logError(tok.getLine(), " : Unexpecting situation");
			}
		}
		if (m_Reference == null)
		{
			// no reference provided, the code is inside
			if (!super.DoParsing())
			{
				Transcoder.logError(getLine(), "Failure while parsing PERFORM bloc") ;
				return false ;
			}
			tok = GetCurrentToken() ;
			if (tok.GetKeyword() != CCobolKeywordList.END_PERFORM)
			{
				Transcoder.logError(tok.getLine(), "Expecting 'END-PERFORM' keyword") ;
				return false ;
			}
			else
			{
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
		Element e = root.createElement("Perform") ;
		if (m_Reference != null)
		{
			e.setAttribute("Reference", m_Reference.GetName()) ;
		}
		if (m_RefThru != null)
		{
			e.setAttribute("Thru", m_RefThru.GetName()) ;
		}
		return e;
	}
	
	protected CIdentifier m_Reference = null ;
	protected CIdentifier m_RefThru = null ;
	protected CTerminal m_refRepetitions = null ;
	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		if (m_RefThru != null)
		{
			CEntityCallFunction e = factory.NewEntityCallFunction(getLine(), m_Reference.GetName(), m_RefThru.GetName(), parent.getSectionContainer()) ;
			factory.m_ProgramCatalog.RegisterPerformThrough(e) ;
			if (m_refRepetitions != null)
			{
				e.SetRepetitions(m_refRepetitions.GetDataEntity(getLine(), factory)) ;
			}
			parent.AddChild(e) ;
			return e;
		}
		else if (m_Reference != null)
		{
			CEntityCallFunction e = factory.NewEntityCallFunction(getLine(), m_Reference.GetName(), "", parent.getSectionContainer()) ;
			parent.AddChild(e) ;
			if (m_refRepetitions != null)
			{
				e.SetRepetitions(m_refRepetitions.GetDataEntity(getLine(), factory)) ;
			}
			return e;
		}
		else
		{
			CEntityCallFunction e = factory.NewEntityCallFunction(getLine(), "", "", parent.getSectionContainer()) ;
			parent.AddChild(e) ;
			if (m_refRepetitions != null)
			{
				e.SetRepetitions(m_refRepetitions.GetDataEntity(getLine(), factory)) ;
			}
			return e;
		}
		
		//return null ;
	}
	/* (non-Javadoc)
	 * @see parser.elements.CBlocElement#isTopLevelBloc()
	 */
	protected boolean isTopLevelBloc()
	{
		return false;
	}
}
