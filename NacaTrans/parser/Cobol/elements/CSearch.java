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
 * Created on Sep 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CBaseElement;
import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import parser.expression.CExpression;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.CEntityBloc;
import semantic.CEntityDataSection;
import semantic.CEntityStructure;
import semantic.Verbs.CEntityAssign;
import semantic.Verbs.CEntityBreak;
import semantic.Verbs.CEntityConstantReturn;
import semantic.Verbs.CEntitySearch;
import semantic.expression.CEntityBoolean;
import semantic.expression.CEntityInternalBool;
import utils.CGlobalEntityCounter;
import utils.Transcoder;
import utils.modificationsReporter.Reporter;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CSearch extends CCobolElement
{
	/**
	 * @param line
	 */
	public CSearch(int line)
	{
		super(line);
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntitySearch eSearch = factory.NewEntitySearch(getLine()) ;
		CDataEntity eVar = m_Variable.GetDataReference(getLine(), factory) ;
		CDataEntity eIndex = null ;
		if (m_Index != null)
		{
			eIndex = m_Index.GetDataReference(getLine(), factory) ;
		}
		else
		{
			CEntityStructure str = (CEntityStructure)eVar ;
			eIndex = str.getOccursIndex() ;
		}
		eSearch.setVariable(eVar, eIndex) ;
		eSearch.setAll(m_bAll);
		
		if (m_blocElse != null)
		{
			CEntityBloc eBloc = (CEntityBloc)m_blocElse.DoSemanticAnalysis(eSearch, factory) ;
			eSearch.setElseBloc(eBloc) ;
		}
		
		if(m_bAll)
		{
			CBaseLanguageEntity e = DoCustomSemanticAnalysisSearchAll(eSearch, parent, factory);
			return e;
		}

		
		CDataEntity eAtt  ;
		if (factory.m_ProgramCatalog.IsExistingDataEntity("Search-Found", ""))
		{
			eAtt = factory.m_ProgramCatalog.GetDataEntity("Search-Found", "") ;
		}
		else
		{
			CEntityInternalBool att = factory.NewEntityInternalBool("Search-Found") ;
			CEntityDataSection working = factory.m_ProgramCatalog.getWorkingSection() ;
			working.AddChild(att) ;
			eAtt = att ;
		}
		CEntityBoolean val = factory.NewEntityBoolean(true) ;
		ListIterator i = m_children.listIterator() ;
		CCobolElement le = null ;
		try
		{	
			le = (CCobolElement)i.next() ;
		}
		catch (NoSuchElementException e)
		{
			e.printStackTrace();
		}
		while (le != null)
		{
			CBaseLanguageEntity e = le.DoSemanticAnalysis(eSearch, factory) ;
			
			CEntityAssign eAss = factory.NewEntityAssign(0) ;
			eAss.SetValue(val) ;
			eAss.AddRefTo(eAtt) ;
			eAtt.RegisterWritingAction(eAss) ;
			e.AddChild(eAss) ;
			CEntityBreak eBr = factory.NewEntityBreak(0) ;
			e.AddChild(eBr) ;
			try
			{	
				le = (CCobolElement)i.next() ;
			}
			catch (NoSuchElementException ee)
			{
				//ee.printStackTrace();
				le = null ;
			}
		}

		parent.AddChild(eSearch) ;
		m_bAnalysisDoneForChildren = true ;
		return eSearch ;
	}
	
	private CBaseLanguageEntity DoCustomSemanticAnalysisSearchAll(CEntitySearch eSearch, CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
//		CDataEntity eAtt  ;
//		if (factory.m_ProgramCatalog.IsExistingDataEntity("Search-Found", ""))
//		{
//			eAtt = factory.m_ProgramCatalog.GetDataEntity("Search-Found", "") ;
//		}
//		else
//		{
//			CEntityInternalBool att = factory.NewEntityInternalBool("Search-Found") ;
//			CEntityDataSection working = factory.m_ProgramCatalog.getWorkingSection() ;
//			working.AddChild(att) ;
//			eAtt = att ;
//		}
		
		ListIterator i = m_children.listIterator() ;
		CCobolElement le = null ;
		try
		{	
			le = (CCobolElement)i.next() ;
		}
		catch (NoSuchElementException e)
		{
			e.printStackTrace();
		}
		while (le != null)
		{
			CBaseLanguageEntity e = le.DoSemanticAnalysis(eSearch, factory) ;

			CEntityConstantReturn eReturn = factory.NewEntityConstantReturn(0, "result");	// Add the return false;
			eSearch.AddChild(eReturn);
			
//			
//			
//			
//			CEntityAssign eAss = factory.NewEntityAssign(0) ;
//			eAss.SetValue(val) ;
//			eAss.AddRefTo(eAtt) ;
//			eAtt.RegisterWritingAction(eAss) ;
//			e.AddChild(eAss) ;
//			CEntityBreak eBr = factory.NewEntityBreak(0) ;
//			e.AddChild(eBr) ;
			try
			{	
				le = (CCobolElement)i.next() ;
			}
			catch (NoSuchElementException ee)
			{
				//ee.printStackTrace();
				le = null ;
			}
		}

		parent.AddChild(eSearch) ;
		m_bAnalysisDoneForChildren = true ;
		return eSearch ;
	}
	
	
	protected CIdentifier m_Variable = null ;
	protected CIdentifier m_Index = null;
	protected CGenericBloc m_blocElse = null ;
	private boolean m_bAll = false;

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		// Syntax http://publibz.boulder.ibm.com/cgi-bin/bookmgr_OS390/handheld/Connected/BOOKS/IGY3LR10/6.2.32?DT=20020920180651
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.SEARCH)
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		tok = GetNext() ;
		if (tok.GetKeyword() == CCobolKeywordList.ALL)	// PJD Added PG POC
		{
			Reporter.Add("Modif_PJ", "Search");
			m_bAll = true;
			tok = GetNext() ;
			m_Variable = ReadIdentifier() ;
			
			if (tok.GetKeyword() == CCobolKeywordList.AT)	// AT ?
			{
				tok = GetNext() ;
				if (tok.GetKeyword() == CCobolKeywordList.END)	// END
				{
					GetNext();
					m_blocElse = new CGenericBloc("AtEnd", tok.getLine()) ;
					if (!Parse(m_blocElse))
					{
						Transcoder.logError(GetCurrentToken().getLine(), "Error while parsing bloc");
						return false ;
					}
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Expecting END");
					return false ;
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.END)	// Directly END, without AT ?
			{
				GetNext();
				m_blocElse = new CGenericBloc("AtEnd", tok.getLine()) ;
				if (!Parse(m_blocElse))
				{
					Transcoder.logError(GetCurrentToken().getLine(), "Error while parsing bloc");
					return false ;
				}
			}	
		}
		else	// Not ALL
		{			
			m_Variable = ReadIdentifier() ;
		
			// VARYING ???
			tok = GetCurrentToken();
			if (tok.GetKeyword() == CCobolKeywordList.VARYING)
			{
				tok = GetNext() ;
				m_Index = ReadIdentifier();
				tok = GetCurrentToken() ;
			} 
		
			// AT END ?
			m_blocElse = null ;
			if (tok.GetKeyword() == CCobolKeywordList.AT)	// AT ?
			{
				tok = GetNext() ;
				if (tok.GetKeyword() == CCobolKeywordList.END)	// END
				{
					GetNext();
					m_blocElse = new CGenericBloc("AtEnd", tok.getLine()) ;
					if (!Parse(m_blocElse))
					{
						Transcoder.logError(GetCurrentToken().getLine(), "Error while parsing bloc");
						return false ;
					}
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Expecting END");
					return false ;
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.END)	// Directly END without AT
			{
				GetNext();
				m_blocElse = new CGenericBloc("AtEnd", tok.getLine()) ;
				if (!Parse(m_blocElse))
				{
					Transcoder.logError(GetCurrentToken().getLine(), "Error while parsing bloc");
					return false ;
				}
			}
		}
		
		tok = GetCurrentToken();		
		while (tok.GetKeyword() == CCobolKeywordList.WHEN)
		{
			tok = GetNext() ;
			CExpression cond = ReadConditionalStatement() ;
			//CExpression cond = new CCondCompStatement(condOld.getLine(), condOld.GetFirstConditionOperand(), condOld.GetFirstConditionOperand()) ;
 
			tok = GetCurrentToken() ;
			if (tok.GetType() == CTokenType.COMMA)
			{
				tok = GetNext() ;
			}
			CBaseElement bloc = null;
			if(m_bAll)
				bloc = new CWhenSearchAllBloc(cond, tok.getLine()) ;
			else
				bloc = new CWhenBloc(cond, tok.getLine()) ;
			if (!Parse(bloc))
			{
				Transcoder.logError(GetCurrentToken().getLine(), "Error while parsing bloc");
				return false ;
			} 
			AddChild(bloc);
			tok = GetCurrentToken() ;
		}
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.END_SEARCH)
		{
			GetNext();
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eSearch = root.createElement("Search");
		Element v = root.createElement("Variable");
		eSearch.appendChild(v);
		m_Variable.ExportTo(v, root);
		if (m_Index != null)
		{
			Element i = root.createElement("Index");
			eSearch.appendChild(i);
			m_Index.ExportTo(i, root) ;
		}
		ExportChildren(root, eSearch) ;
		if (m_blocElse != null)
		{
			eSearch.appendChild(m_blocElse.ExportCustom(root));
		}		
		return eSearch;
	}
}
