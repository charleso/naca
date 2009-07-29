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
public abstract class CEntityIsFieldColor extends CUnitaryEntityCondition
{
	
	public void IsColor(CEntityFieldColor.CFieldColor col, CDataEntity data)
	{
		m_IsColor = col ;
		m_Reference = data ;
	}
	protected CEntityFieldColor.CFieldColor m_IsColor ; 
	public void Clear()
	{
		super.Clear();
		m_Reference = null ;
	}
	public boolean ignore()
	{
		return m_Reference.ignore();
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
	public void SetOpposite()
	{
		m_bOpposite = !m_bOpposite ;
	}
	protected boolean m_bOpposite = false ;
	public boolean isBinaryCondition()
	{
		return true;
	}
}
