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
 * Created on Aug 10, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements.SQL;


import java.util.ListIterator;
import java.util.NoSuchElementException;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.Cobol.CCobolElement;
import parser.Cobol.elements.CWorkingEntry;

import semantic.CBaseEntityFactory;
import semantic.CBaseExternalEntity;
import semantic.CBaseLanguageEntity;
import semantic.CEntityInline;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecSQLInclude extends CBaseExecSQLAction
{
	public CExecSQLInclude(int l, String reference)
	{
		super(l);
		m_ref = reference;
	}
	public String m_ref = "" ;
	public Element ExportCustom(Document root)
	{
		Element e = root.createElement("SQLInclude") ;
		e.setAttribute("Reference", m_ref) ;
		return e ;
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CGlobalEntityCounter.GetInstance().RegisterCopy(parent.GetProgramName(), m_ref) ;
		CBaseExternalEntity e = factory.m_ProgramCatalog.GetExternalDataReference(m_ref, factory) ;
		if (e == null)
		{
			CGlobalEntityCounter.GetInstance().RegisterMissingCopy(parent.GetProgramName(), m_ref) ;
			return null ;
		}
		CBaseLanguageEntity ent = parent.FindLastEntityAvailableForLevel(e.GetInternalLevel());
		if (ent == null)
		{
			ent = parent ;	
		}
		e.InitDependences(factory) ;
		CEntityInline eil = factory.NewEntityInline(getLine(), e) ; 
		ent.AddChild(eil) ;
		e.SetParent(eil);

		ListIterator i = m_children.listIterator() ;
		CCobolElement le = null ;
		try
		{	
			le = (CCobolElement)i.next() ;
		}
		catch (NoSuchElementException ex)
		{
			//ex.printStackTrace();
		}
		while (le != null)
		{
			CBaseLanguageEntity eSub = le.DoSemanticAnalysis(null, factory) ;
			int level = eSub.GetInternalLevel() ;
			CBaseLanguageEntity newParent = parent.FindLastEntityAvailableForLevel(level);
			if (newParent != null)
			{
				//eil.ReplaceParentForChild(eSub, newParent);
				newParent.AddChild(eSub) ;
			}
			else
			{
				eil.AddChild(eSub) ;
			}
			try
			{	
				le = (CCobolElement)i.next() ;
			}
			catch (NoSuchElementException exp)
			{
				//exp.printStackTrace();
				le = null ;
			}
		}
		m_bAnalysisDoneForChildren = true ;
		return eil ;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		while (tok.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			tok = GetNext() ;
		}
		return true;
	}
	public boolean ParseContent()
	{
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tokEntry = GetCurrentToken();
			if (tokEntry.GetType()==CTokenType.NUMBER)
			{
				int level = tokEntry.GetIntValue();
				if (level > 1)
				{
					CCobolElement eEntry = new CWorkingEntry(tokEntry.getLine()) ;
					if (!Parse(eEntry))
					{
						Transcoder.logError(getLine(), "Error while parsing working entry") ;
						return false ;
					}
					AddChild(eEntry) ;
				}
				else
				{
					bDone = true ; // this entry is a top-level entry
				}
			}
			else
			{
				bDone = true ;	// this token is not parsed by this function, go back to caller
			}
		}
		return true ;
	}
} 
