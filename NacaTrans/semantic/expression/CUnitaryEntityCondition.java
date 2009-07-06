/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 19 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.expression;

import semantic.CDataEntity;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CUnitaryEntityCondition extends CBaseEntityCondition
{
//	public CUnitaryEntityCondition(int nLine)
//	{
//		super(nLine);
//	}
	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#ReplaceVariable(semantic.CDataEntity, semantic.CDataEntity)
	 */
	@Override
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_Reference == field)
		{
			m_Reference = var ;
			field.UnRegisterVarTesting(this) ;
			field.RegisterVarTesting(this) ;
			return true ;
		}
		return false ;
	}
	public void SetConditonReference(CDataEntity e)
	{
		ASSERT(e) ;
		m_Reference = e ;
	}
	public CDataEntity GetConditionReference()
	{
		return m_Reference ;
	}
	protected CDataEntity m_Reference = null; 
	public void Clear()
	{
		super.Clear() ;
		m_Reference = null ;
	}
}
