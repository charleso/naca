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
public abstract class CEntityCondIsKindOf extends CUnitaryEntityCondition
{
	public void SetIsNumeric(CDataEntity data)
	{
		SetConditonReference(data) ;
		m_bIsNumeric = true ;
		m_bIsAlphabetic = false ;
		m_bIsLower = false ;
		m_bIsUpper = false ;
	}
	public void SetIsAlphabetic(CDataEntity data)
	{
		SetConditonReference(data) ;
		m_bIsNumeric = false ;
		m_bIsAlphabetic = true ;
		m_bIsLower = false ;
		m_bIsUpper = false ;
	}
	public void SetIsLower(CDataEntity data)
	{
		SetConditonReference(data) ;
		m_bIsNumeric = false ;
		m_bIsAlphabetic = false ;
		m_bIsLower = true ;
		m_bIsUpper = false ;
	}
	public void SetIsUpper(CDataEntity data)
	{
		SetConditonReference(data) ;
		m_bIsNumeric = false ;
		m_bIsAlphabetic = false ;
		m_bIsLower = false ;
		m_bIsUpper = true ;
	}

	protected boolean m_bIsNumeric = false ;
	protected boolean m_bIsLower = false ;
	protected boolean m_bIsUpper = false ;
	protected boolean m_bIsAlphabetic = false ;
	protected boolean m_bOpposite = false ;
	public boolean ignore()
	{
		return m_Reference.ignore();
	}
	public CBaseEntityCondition GetSpecialConditionReplacing(String val, CBaseEntityFactory fact, CDataEntity replace)
	{
		return null;
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
		return false ;
	}
	public void setOpposite()
	{
		m_bOpposite = !m_bOpposite ;
	}
	public boolean isBinaryCondition()
	{
		return false;
	}
}
