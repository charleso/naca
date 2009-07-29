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
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityCondAnd extends CBaseEntityCondition
{
	public void SetCondition(CBaseEntityCondition op1, CBaseEntityCondition op2)	{
		m_Op1 = op1 ;
		m_Op1.SetParent(this); 
		m_Op2 = op2 ; 
		m_Op2.SetParent(this) ;
	}
	protected CBaseEntityCondition m_Op1 = null ;
	protected CBaseEntityCondition m_Op2 = null ;
	public void Clear()
	{
		super.Clear() ;
		m_Op1.Clear() ;
		m_Op2.Clear() ;
		m_Op1 =null ;
		m_Op2 = null ;
	}
	protected int GetLevelPriority()
	{
		return  1;
	}
	public boolean ignore()
	{
		return m_Op1.ignore() && m_Op2.ignore() ; 
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		boolean b1 = m_Op1.ReplaceVariable(field, var) ;
		boolean b2 = m_Op2.ReplaceVariable(field, var) ;
		return b1 || b2 ;
	}
	public CBaseEntityCondition GetSpecialConditionReplacing(String val, CBaseEntityFactory fact, CDataEntity replace)
	{
		return null;
	}
	public void UpdateCondition(CBaseEntityCondition condition, CBaseEntityCondition newCond)
	{
		if (m_Op1 == condition)
		{
			m_Op1 = newCond ;
		}
		if (m_Op2 == condition)
		{
			m_Op2 = newCond ;
		} 
	}
	public boolean isBinaryCondition()
	{
		return false;
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
