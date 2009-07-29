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
 * Created on 6 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.Verbs;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.expression.CBaseEntityCondition;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityLoopWhile extends CBaseActionEntity
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityLoopWhile(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	public void SetWhileCondition(CBaseEntityCondition exp)
	{
		m_WhileCondition = exp ;
		m_bDoBefore = false ;
	}
	public void SetDoWhileCondition(CBaseEntityCondition exp)
	{
		m_WhileCondition = exp ;
		m_bDoBefore = true ;
	}
	public void SetDoUntilCondition(CBaseEntityCondition exp)
	{
		m_WhileCondition = exp.GetOppositeCondition() ;
		m_bDoBefore = true;
	}
	public void SetUntilCondition(CBaseEntityCondition exp)
	{
		m_WhileCondition = exp.GetOppositeCondition() ;
		m_bDoBefore = false ;
	}
	protected CBaseEntityCondition m_WhileCondition = null ;
	protected boolean m_bDoBefore = false ; // false = WHILE DO / true = DO WHILE  
	public void Clear()
	{
		super.Clear() ;
		m_WhileCondition.Clear() ;
		m_WhileCondition = null ;
	}
	public boolean ignore()
	{
		boolean ignore = m_WhileCondition.ignore() ;
//		ignore |= (isChildrenIgnored() && m_;
		return ignore ;
	}
	public boolean UpdateAction(CBaseActionEntity entity, CBaseActionEntity newCond)
	{
		for (int i=0; i<m_lstChildren.size(); i++)
		{
			CBaseActionEntity act = (CBaseActionEntity)m_lstChildren.get(i) ;
			if (act == entity)
			{
				m_lstChildren.set(i, newCond) ;
				return true ;
			}
		}
		return false ;
	}

}
