/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 27 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.forms;

import semantic.CBaseEntityFactory;
import semantic.CDataEntity;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CUnitaryEntityCondition;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityIsFieldHighlight extends CUnitaryEntityCondition
{

	public CEntityIsFieldHighlight(CDataEntity ref)
	{
		m_Reference = ref ;
	}
	
	public void IsBlink()
	{
		m_bIsBlink = true ;
	}
	public void IsReverse()
	{
		m_bIsReverse = true ;
	}
	public void IsUnderlined()
	{
		m_bIsUnderlined = true ;
	}
	//protected CFieldHighligh m_highlight = null ;
	protected boolean m_bIsBlink = false ;
	protected boolean m_bIsReverse = false ;
	protected boolean m_bIsUnderlined = false ;
	protected boolean m_bOpposite = false ;
	
	public boolean ignore()
	{
		return m_Reference.ignore() ;
	}
	public CBaseEntityCondition GetSpecialConditionReplacing(String val, CBaseEntityFactory fact, CDataEntity replace)
	{
		return m_Reference.GetSpecialCondition(getLine(), val, EConditionType.IS_EQUAL, fact);
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_Reference == field)
		{
			m_Reference = var ;
			field.UnRegisterVarTesting(this) ;
			var.RegisterVarTesting(this) ;
			return true ;
		}
		return false ;
	}

	/**
	 * 
	 */
	public void IsNormal()
	{
		m_bIsBlink = false ;
		m_bIsReverse = false ;
		m_bIsUnderlined = false ;
	}
	public boolean isBinaryCondition()
	{
		return true;
	}
	
	public void setOpposite() 
	{
		m_bOpposite = !m_bOpposite ;
	}
}
