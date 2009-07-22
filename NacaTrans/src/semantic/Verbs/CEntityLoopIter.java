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

import java.util.ArrayList;
import java.util.List;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import semantic.expression.CBaseEntityCondition;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityLoopIter extends CBaseActionEntity
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	
	protected boolean m_bIncrementByOne = false ;
	protected boolean m_bDecrementByOne = false ;
	
	public CEntityLoopIter(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	public void SetLoopIterInc(CDataEntity v, CDataEntity init)
	{
		m_Variable = v ;
		m_Increment = null ;
		m_bIncrementByOne = true ;
		m_bDecrementByOne = false ;
		m_InitialValue = init ;
	}
	public void SetLoopIterDec(CDataEntity v, CDataEntity init)
	{
		m_Variable = v ;
		m_Increment = null ;
		m_bIncrementByOne = false ;
		m_bDecrementByOne = true ;
		m_InitialValue = init ;
	}
	public void SetLoopIter(CDataEntity v, CDataEntity init, CDataEntity inc)
	{
		m_Variable = v ;
		m_Increment = inc ;
		m_bIncrementByOne = false ;
		m_bDecrementByOne = false ;
		m_InitialValue = init ;
	}
	public void SetWhileCondition(CBaseEntityCondition cond, boolean testBefore)
	{
		m_WhileCondition = cond  ;
		m_bTestBefore = testBefore;
	}
	public void SetUntilCondition(CBaseEntityCondition cond, boolean testBefore)
	{
		m_WhileCondition = cond.GetOppositeCondition() ;
		m_bTestBefore = testBefore;
	}

	protected boolean m_bTestBefore = true ;
	protected CDataEntity m_Variable = null ;
	protected CBaseEntityCondition m_WhileCondition = null ;
	protected CDataEntity m_InitialValue = null ;
	protected CDataEntity m_Increment = null ;
	protected List<CEntityAfter> m_Afters = new ArrayList<CEntityAfter>();
	public void Clear()
	{
		super.Clear() ;
		m_Variable = null ;
		m_WhileCondition.Clear() ;
		m_WhileCondition = null ;
		m_Increment = null ;
		m_InitialValue = null ;
		m_Afters.clear();
	}
	public boolean ignore()
	{
		boolean ignore = m_Variable.ignore() ;
		ignore |= m_WhileCondition.ignore();
		ignore |= m_InitialValue.ignore() ;
		if (m_Increment != null)
		{
			ignore |= m_Increment.ignore();
		}
		//ignore |= isChildrenIgnored() ;
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
	public void AddAfter(CDataEntity after,
			CDataEntity from, CDataEntity by,
			CBaseEntityCondition until) {
		m_Afters.add(new CEntityAfter(after, from, by, until));
	}
	protected class CEntityAfter
	{
		public CDataEntity m_VariableAfter = null ;
		public CDataEntity m_varFromValueAfter = null ;
		public CDataEntity m_varByValueAfter = null ;
		public CBaseEntityCondition m_condUntilAfter = null ;
		public CEntityAfter(CDataEntity after, CDataEntity from,
				CDataEntity by, CBaseEntityCondition until)
		{
			m_VariableAfter = after;
			m_varFromValueAfter = from;
			m_varByValueAfter = by;
			m_condUntilAfter = until;
		}
	}

}
