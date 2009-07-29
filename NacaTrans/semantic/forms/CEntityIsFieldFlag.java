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

import parser.expression.CTerminal;
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
public abstract class CEntityIsFieldFlag extends CUnitaryEntityCondition
{
	public void SetIsFlag(CDataEntity eData, String cs)
	{
		m_Value = cs ;
		m_bIsSet = false ;
		m_Reference = eData ;
	}
	protected String m_Value = "" ;
	protected boolean m_bIsSet = false ;
	//protected CDataEntity m_Reference = null ; 
	protected boolean m_bOpposite = false ;
	public void Clear()
	{
		super.Clear();
		m_Reference = null ;
		m_bIsSet = false ;
	}
	public CBaseEntityCondition getSimilarCondition(CBaseEntityFactory factory, CTerminal term)
	{
		if (term.IsReference())
		{
			ASSERT(null) ;
			return null ; 
		}
		else
		{
			CEntityIsFieldFlag eCond = factory.NewEntityIsFieldFlag();
			eCond.SetIsFlag(m_Reference, term.GetValue());
			return eCond;
		}
	}
	public void SetOpposite()
	{
		m_bOpposite = !m_bOpposite ;		
	}
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
			field.UnRegisterVarTesting(this) ;
			var.RegisterVarTesting(this) ;
			m_Reference = var ;
			return true ;
		}
		return false ;
	}
	/**
	 * @param refField
	 */
	public void SetIsFlagSet(CDataEntity refField)
	{
		m_Value = "" ;
		m_bIsSet = true ;
		m_Reference = refField ;
	}
	public boolean isBinaryCondition()
	{
		return true;
	}

}	
