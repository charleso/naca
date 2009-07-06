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
public abstract class CEntityIsFieldAttribute extends CUnitaryEntityCondition
{
	protected CDataEntity m_VarValue = null ;
	public void Clear()
	{
		super.Clear();
		m_VarValue = null ;
	}
	
	public void IsAttribute(CDataEntity data, CDataEntity var)
	{
		m_VarValue = data ;
		m_Reference = var ;
	}
	public void IsAutoSkip()
	{
		m_bIsAutoSkip = true ;		
		m_nbConditions ++ ;		
	}
	protected boolean m_bIsAutoSkip = false ;

	public void IsBright()
	{
		m_bIsBright = true ;		
		m_nbConditions ++ ;		
	}
	protected boolean m_bIsBright = false ;

	public void IsNumeric()
	{
		m_bIsNumeric = true ;		
		m_nbConditions ++ ;		
	}
	protected boolean m_bIsNumeric = false ;

	public void IsProtected()
	{
		m_bIsProtected = true;		
		m_nbConditions ++ ;		
	}
	protected boolean m_bIsProtected = false ;

	public void IsUnprotected()
	{
		m_bIsUnprotected = true ;		
		m_nbConditions ++ ;		
	}
	protected boolean m_bIsUnprotected = false ;

	public void IsModified()
	{
		m_bIsModified = true ;		
		m_nbConditions ++ ;		
	}
	public void IsUnmodified()
	{
		m_bIsUnmodified = true ;		
		m_nbConditions ++ ;		
	}
	public void IsCleared()
	{
		m_bIsCleared = true ;		
		m_nbConditions ++ ;		
	}
	protected boolean m_bIsModified = false ;
	protected boolean m_bIsUnmodified = false ;		
	protected boolean m_bIsCleared = false ;
	
	public void IsDark()
	{
		m_bIsDark = true ;
		m_nbConditions ++ ;		
	}
	protected boolean m_bIsDark = false ;
	protected int m_nbConditions = 0;

	public void SetVariable(CDataEntity field)
	{
		m_Reference = field ;		
	}
	public boolean ignore()
	{
		return m_Reference.ignore() ;
	}

	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#GetSpecialCondition(java.lang.String, semantic.CBaseEntityFactory)
	 */
	public CBaseEntityCondition GetSpecialConditionReplacing(String val, CBaseEntityFactory fact, CDataEntity replace)
	{
		CBaseEntityCondition cond = m_Reference.GetSpecialCondition(getLine(), val, EConditionType.IS_FIELD_ATTRIBUTE, fact);
		if (m_bOpposite && cond!=null)
		{
			return cond.GetOppositeCondition() ;
		}
		return cond ;
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_Reference == field)
		{
			field.UnRegisterVarTesting(this) ;
			var.RegisterVarTesting(this) ;
			m_Reference = var ;
			return true ;
		}
		if (m_VarValue == field)
		{
			m_VarValue = var ;
			field.UnRegisterValueAccess(this) ;
			var.RegisterValueAccess(this) ;
			return true ;
		}
		return false  ;
	}
	
	protected void SetOpposite()
	{
		m_bOpposite = !m_bOpposite ;
	}
	protected boolean m_bOpposite = false ;
	public boolean isBinaryCondition()
	{
		return true;
	}

}
