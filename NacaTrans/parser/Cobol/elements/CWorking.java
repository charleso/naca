/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jul 16, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;


import java.util.ListIterator;
import java.util.NoSuchElementException;

import lexer.*;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.*;

import parser.*;
import parser.Cobol.CCobolElement;
import parser.Cobol.elements.SQL.*;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.CEntityDataSection;
import utils.LevelKeywordStackManager;
import utils.LevelKeywords;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CWorking extends CCommentContainer
{
//	private Vector<CCobolElement> m_arrVariables = new Vector<CCobolElement>() ;
	/**
	 * @param line
	 */
	public CWorking(int line)
	{
		super(line);
	}
	
	private void beginParseWorking()
	{	
		LevelKeywords levelKeywords = LevelKeywordStackManager.getAndPushNewLevelKeywords();
		levelKeywords.registerManagedKeyword(CCobolKeywordList.EJECT);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.SKIP2);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.SKIP3);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.WORKING_STORAGE);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.FILE);
		levelKeywords.registerManagedKeyword(CCobolKeywordList.LINKAGE);
	}

	private void endParseWorking()
	{		
		LevelKeywordStackManager.popLevelKeywords();
	}
	
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		boolean b = false;
		beginParseWorking();
		boolean bLoop = true;
		while(bLoop)
		{		
			bLoop = false;
			b = internalDoParsing();
			CBaseToken tokEntry = GetCurrentToken();
			if(!LevelKeywordStackManager.isTokenManagedByAnyParents(tokEntry))
			{
				Transcoder.logError(tokEntry.getLine(), "Consuming token " + tokEntry.toString());
				GetNext();
				bLoop = true;
			}
			else
				endParseWorking();
		}
		return b;
	}
	
	private boolean internalDoParsing()
	{
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tokEntry = GetCurrentToken();
			
			if (tokEntry == null)
			{
				return true ;
			}
			if (tokEntry.GetType() == CTokenType.KEYWORD)
			{
				int gg = 0;
			}
			if (tokEntry.GetType() == CTokenType.NUMBER)
			{
				int n = tokEntry.GetIntValue();
				if (n >= 1 && n <= 49)
				{
					CCobolElement eEntry = new CWorkingEntry(tokEntry.getLine()) ;
					AddChild(eEntry) ;
					if (!Parse(eEntry))
					{
						return false ;
					}
				}
				else if (n == 77)
				{
					CCobolElement eEntry = new CWorkingEntry(tokEntry.getLine()) ;
//					m_arrVariables.add(eEntry) ;
					AddChild(eEntry) ;
					if (!Parse(eEntry))
					{
						return false ;
					}
				}
				else if (n == 88)
				{
					CCobolElement eEntry = new CWorkingValueEntry(tokEntry.getLine()) ;
					AddChild(eEntry) ;
					if (!Parse(eEntry))
					{
						return false ;
					}
				}
				else
				{
					Transcoder.logError(tokEntry.getLine(), "Unexpecting token : " + tokEntry.GetValue()) ;
					return false ;
				}
			}
			else if (tokEntry.GetKeyword()==CCobolKeywordList.COPY)
			{
				CCobolElement eCopy = new CCopyInWorking(tokEntry.getLine()) ;
				AddChild(eCopy) ;
				if (!Parse(eCopy))
				{
					return false ;
				}
			}
			else if (tokEntry.GetKeyword()==CCobolKeywordList.COPYREC)
			{
				CCobolElement eCopy = new CCopyRec(tokEntry.getLine()) ;
				AddChild(eCopy) ;
				if (!Parse(eCopy))
				{
					return false ;
				}
			}
			else if (tokEntry.GetKeyword()==CCobolKeywordList.EJECT)
			{
				GetNext();
			}
			else if (tokEntry.GetType()==CTokenType.KEYWORD && tokEntry.GetKeyword()==CCobolKeywordList.EXEC)
			{
				CBaseToken tokType = GetNext() ;
				CCobolElement eExec = null ;
				if (tokType.GetKeyword() == CCobolKeywordList.SQL)
				{
					eExec = new CExecSQL(tokEntry.getLine()) ;
				}
//				else if (tokType.GetKeyword() == CCobolKeywordList.CICS)
//				{
//					eExec = new CExecCICS(tokVerb.m_line) ;
//				}
				else
				{
					eExec = new CExecStatement(tokEntry.getLine()) ;
				}
				AddChild(eExec) ;
				if (!Parse(eExec))
				{
					return false ;
				}
			}
			else if (tokEntry.GetType()==CTokenType.DOT)
			{
				StepNext() ;//ConsumeEndLineWithDot();
			}
			else
			{
				bDone = true ; // this kind of token is not parsed by current function, go back to caller
			}
		}
		return true ;
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eWorking = root.createElement(GetType()) ;
		return eWorking ;
	}
	/* 
	 * unused (CV : 25/03/2006)
	 */
//	protected CBaseLanguageEntity DoSemanticAnalysisForVariables(CBaseLanguageEntity parent, CBaseEntityFactory factory)
//	{
//		m_eVariableSection = factory.NewEntityDataSection(0, "VariableSection");
//		if (parent != null)
//		{
//			parent.AddChild(m_eVariableSection);
//		}
//
//		for (int i=0; i<m_arrVariables.size();i++)
//		{
//			CCobolElement le = m_arrVariables.get(i) ;
//			CBaseLanguageEntity e = le.DoSemanticAnalysis(m_eVariableSection, factory) ;
//		}
//		return m_eVariableSection ;
//	}
//	private CEntityDataSection m_eVariableSection = null ;
	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityDataSection eSection = factory.NewEntityDataSection(getLine(), GetType());
		parent.AddChild(eSection);
	
//		if (m_eVariableSection == null)
//		{
//			for (int i=0; i<m_arrVariables.size();i++)
//			{
//				CCobolElement le = m_arrVariables.get(i) ;
//				CBaseLanguageEntity e = le.DoSemanticAnalysis(eSection, factory) ;
//			}
//		}

		ListIterator i = m_children.listIterator() ;
		CBaseLanguageEntity eLast = null;
		
		CCobolElement le = null ;
		try
		{	
			le = (CCobolElement)i.next() ;
		}
		catch (NoSuchElementException e)
		{
		}
		while (le != null)
		{
			CBaseLanguageEntity e = le.DoSemanticAnalysis(eSection, factory) ;
			if (e != null)
			{
				int level = e.GetInternalLevel() ;
				if (level == 1)
				{
					eLast = e ;
				}
				else if (level == 0)
				{
					 // nothing
				}
				else
				{
					CBaseLanguageEntity eNew = eLast.FindLastEntityAvailableForLevel(level);
//					if (eNew != null)
//					{
//						eNew.AddChild(e);
//					}
				}
			}
			try
			{	
				le = (CCobolElement)i.next() ;
			}
			catch (NoSuchElementException ee)
			{
				le = null ;
			}
		}
		m_bAnalysisDoneForChildren = true ;
	
		return eSection ;
	}
	
	protected String GetType()
	{
		return "WorkingStorageSection" ; 
	}
}
