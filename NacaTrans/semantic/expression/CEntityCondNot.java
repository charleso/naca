/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 18 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.expression;

import semantic.CBaseEntityFactory;
import semantic.CDataEntity;



/**
 * @author sly
 *
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityCondNot extends CBaseEntityCondition
{
	
	protected CBaseEntityCondition m_Cond ;
	public void Clear()
	{
		super.Clear() ;
		m_Cond.Clear() ;
		m_Cond = null ;
	}

	public void SetCondition(CBaseEntityCondition cond)
	{
		ASSERT(cond);
		m_Cond = cond ;
		m_Cond.SetParent(this);
	}
	public boolean ignore()
	{
		return m_Cond.ignore();
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		return m_Cond.ReplaceVariable(field, var) ;
	}
	public CBaseEntityCondition GetSpecialConditionReplacing(String val, CBaseEntityFactory fact, CDataEntity replace)
	{
		CBaseEntityCondition cond = m_Cond.GetSpecialConditionReplacing(val, fact, replace);
		CBaseEntityCondition notcond = cond.GetOppositeCondition() ;
		if (notcond == null)
		{
			CEntityCondNot notCond = fact.NewEntityCondNot() ;
			notCond.SetCondition(cond);
		}
		return notcond ;
	}
//	public CBaseEntityCondition getSimilarCondition(CBaseEntityFactory factory, CTerminal term)
//	{
//		CEntityCondNot not = factory.NewEntityCondNot() ;
//		CBaseEntityCondition cond = m_Cond.getSimilarCondition(factory, term) ;
//		not.SetCondition(cond);
//		return not ;
//	}
	public void UpdateCondition(CBaseEntityCondition condition, CBaseEntityCondition newCond)
	{
		if (m_Cond == condition)
		{
			m_Cond = newCond ;
		}
	}
	public boolean isBinaryCondition()
	{
		return m_Cond.isBinaryCondition() ;
	}
	/**
	 * @see semantic.expression.CBaseEntityCondition#GetConditionReference()
	 */
	@Override
	public CDataEntity GetConditionReference()
	{
		return null;
	}
	public void SetConditonReference(CDataEntity e)
	{
		ASSERT(null) ;
	}
}
