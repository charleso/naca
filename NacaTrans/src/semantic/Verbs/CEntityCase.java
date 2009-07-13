/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 5 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.Verbs;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import semantic.expression.CBaseEntityCondition;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityCase extends CBaseActionEntity
{

	/**
	 * @param cat
	 * @param out
	 */
	public CEntityCase(int l, CObjectCatalog cat, CBaseLanguageExporter out, int nEndLine)
	{
		super(l, cat, out);
		m_nEndBlocLine = nEndLine ;
	}
	public void SetCondition(CBaseEntityCondition exp)
	{
		//ASSERT(exp);
		m_Condition = exp ;
	}
	
	protected CBaseEntityCondition m_Condition = null  ; 
	protected int m_nEndBlocLine = 0 ;
	public void Clear()
	{
		super.Clear() ;
		if (m_Condition!=null)
		{
			m_Condition.Clear() ;
		}
		m_Condition = null ;
	}

	public boolean ignore()
	{
		if (m_Condition != null)
		{
			boolean ignore = m_Condition.ignore() ;
			//ignore |= isChildrenIgnored() ;
			return ignore ;
		}
		else
		{
			return isChildrenIgnored() ;
		}
	}
	public boolean IgnoreVariable(CDataEntity data)
	{
		return false ;
	}

	public boolean UpdateAction(CBaseActionEntity entity, CBaseActionEntity newCond)
	{
		for (int i=0; i<m_lstChildren.size(); i++)
		{
			CBaseActionEntity act = (CBaseActionEntity)m_lstChildren.get(i) ;
			if (act == entity)
			{
				m_lstChildren.set(i, newCond) ;
				return true ;
			}
		}
		return false ;
	}

}