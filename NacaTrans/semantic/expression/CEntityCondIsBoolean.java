/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package semantic.expression;

import semantic.CBaseEntityFactory;
import semantic.CDataEntity;

public abstract class CEntityCondIsBoolean extends CUnitaryEntityCondition
{
	protected boolean m_bIsTrue = false ;
	
	public void setIsTrue(CDataEntity e)
	{
		m_Reference = e ;
		m_bIsTrue = true ;
	}
	
	public void setIsFalse(CDataEntity e)
	{
		m_Reference = e ;
		m_bIsTrue = false ;
	}
	
	
	@Override
	public boolean isBinaryCondition()
	{
		return false;
	}

	@Override
	public CBaseEntityCondition GetSpecialConditionReplacing(String val,
					CBaseEntityFactory fact, CDataEntity replace)
	{
		return null;
	}
	public boolean ignore()
	{
		return m_Reference.ignore() ;
	}

}
