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
 * Created on 14 févr. 2005
 *
 */
package semantic.forms;

import semantic.CBaseEntityFactory;
import semantic.CDataEntity;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CUnitaryEntityCondition;

/**
 * @author sly
 *
 */
public abstract class CEntityIsFieldCursor extends CUnitaryEntityCondition
{

	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#GetSpecialCondition(java.lang.String, semantic.CBaseEntityFactory)
	 */
	public CBaseEntityCondition GetSpecialConditionReplacing(String val, CBaseEntityFactory fact, CDataEntity replace)
	{
		if (m_bHasCursor)
		{
			return m_Reference.GetSpecialCondition(getLine(), val, EConditionType.IS_EQUAL, fact);
		}
		else
		{
			return m_Reference.GetSpecialCondition(getLine(), val, EConditionType.IS_DIFFERENT, fact);
		}
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#ignore()
	 */
	public boolean ignore()
	{
		return m_Reference.ignore();
	}
	
	protected boolean m_bHasCursor = true ; 
	/**
	 * @param refField
	 */
	public void SetHasCursor(CDataEntity refField)
	{
		m_Reference = refField ;
		m_bHasCursor = true ;
	}

	/**
	 * @param refField
	 */
	public void SetHasNotCursor(CDataEntity refField)
	{
		m_Reference = refField ;
		m_bHasCursor = false ;
	}
	public boolean isBinaryCondition()
	{
		return true;
	}
	
}
