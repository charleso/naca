/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 14 janv. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.SQL;

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
public abstract class CEntityCondIsSQLCode extends CUnitaryEntityCondition
{
	protected boolean m_bIsEqual = true ;
	protected int m_nValue = 0 ;
	
	public void setIsEqual(int n)
	{
		m_bIsEqual = true ;
		m_nValue = n ;
	}
	public void setIsNotEqual(int n)
	{
		m_bIsEqual = false ;
		m_nValue = n ;
	}
	
	
	public int GetPriorityLevel()
	{
		return 7;
	}

	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#GetSpecialCondition(java.lang.String, semantic.CBaseEntityFactory)
	 */
	public CBaseEntityCondition GetSpecialConditionReplacing(String val, CBaseEntityFactory fact, CDataEntity replace)
	{
		// nothing
		return null;
	}

	public boolean ignore()
	{
		return false;
	}
	public boolean isBinaryCondition()
	{
		return true;
	}
}
