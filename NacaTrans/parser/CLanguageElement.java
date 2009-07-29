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
package parser;

import java.util.ListIterator;
import java.util.NoSuchElementException;


import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import utils.Transcoder;

public abstract class CLanguageElement extends CBaseElement
{
	public CLanguageElement(int line)
	{
		super(line);
	}

	protected abstract CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory) ;
	protected boolean m_bAnalysisDoneForChildren = false ;
	public CBaseLanguageEntity DoSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CBaseLanguageEntity eCurrent = DoCustomSemanticAnalysis(parent, factory) ;
		if (eCurrent == null)
		{
			return null ;
		}
		if (!m_bAnalysisDoneForChildren)
		{
			DoSemanticAnalysisForChildren(eCurrent, factory) ;
		}
		return eCurrent ;
	}
	
	public void DoDeferredSemanticAnalysisForChildren(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		int nIndex = 0;
		ListIterator<CBaseElement> i = m_children.listIterator() ;
		CBaseElement le = null ;
		try
		{	
			le = i.next() ;
		}
		catch (NoSuchElementException e)
		{
			//e.printStackTrace();
		}
		while (le != null)
		{
			le.DoDeferredCustomSemanticAnalysis(parent, factory, nIndex) ;
			try
			{	
				le = i.next() ;
			}
			catch (NoSuchElementException ee)
			{
				//ee.printStackTrace();
				le = null ;
			}
			nIndex++;
		}
		m_bAnalysisDoneForChildren = true ;
	}
	
	protected void DoSemanticAnalysisForChildren(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		ListIterator<CBaseElement> i = m_children.listIterator() ;
		CBaseElement le = null ;
		try
		{	
			le = i.next() ;
		}
		catch (NoSuchElementException e)
		{
			//e.printStackTrace();
		}
		while (le != null)
		{
			CBaseLanguageEntity e = le.DoSemanticAnalysis(parent, factory) ;
			try
			{	
				le = i.next() ;
			}
			catch (NoSuchElementException ee)
			{
				//ee.printStackTrace();
				le = null ;
			}
		}
		m_bAnalysisDoneForChildren = true ;
	}
}
