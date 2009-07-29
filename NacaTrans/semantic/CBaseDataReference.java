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
 * Created on 24 nov. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic;

import generate.CBaseLanguageExporter;
import semantic.expression.CBaseEntityCondExpr;
import semantic.expression.CBaseEntityCondition;
import semantic.forms.CEntityResourceForm;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CBaseDataReference extends CGenericDataEntityReference
{
	public CBaseDataReference(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, name, cat, out);
	}
	protected CDataEntity m_Reference = null ;
	
	public int getNbDimOccurs()
	{
		return m_Reference.getNbDimOccurs();
	}
	
	public void RegisterReadingAction(CBaseActionEntity act)
	{
		m_Reference.RegisterReadReference(this) ;
		super.RegisterReadingAction(act);
	}
	public void RegisterValueAccess(CBaseEntityCondExpr cond)
	{
		super.RegisterValueAccess(cond) ;
		m_Reference.RegisterReadReference(this) ;
	}
	public void RegisterVarTesting(CBaseEntityCondition cond)
	{
		super.RegisterVarTesting(cond) ;
		m_Reference.RegisterReadReference(this);
	}
	public void RegisterWritingAction(CBaseActionEntity act)
	{
		m_Reference.RegisterWriteReference(this);
		super.RegisterWritingAction(act);
	}

	public void IgnoreReadingActions(CDataEntity field)
	{
		if (field == m_Reference)
		{
			for (int i=0; i<m_arrActionsReading.size(); i++)
			{
				CBaseActionEntity act = m_arrActionsReading.get(i);
				act.IgnoreVariable(this);
			}
		}
	}

	public void IgnoreWritingActions(CDataEntity field)
	{
		if (field == m_Reference)
		{
			for (int i=0; i<m_arrActionsWriting.size(); i++)
			{
				CBaseActionEntity act = m_arrActionsWriting.get(i);
				act.IgnoreVariable(this);
			}
		}		
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var, boolean bRead)
	{
		if (field == m_Reference)
		{
			m_Reference = var ;
			if (bRead)
			{
				field.UnRegisterReadReference(this) ;
				var.RegisterReadReference(this) ;
			}
			else
			{
				field.UnRegisterWriteReference(this) ;
				var.RegisterWriteReference(this) ;
			}
			return true ;
		}		
		return false ;
	}
	public boolean IgnoreVariable(CEntityResourceForm sav)
	{
		if (sav == m_Reference)
		{
			m_Reference = null ;
			sav.UnRegisterReadReference(this) ;
			sav.UnRegisterWriteReference(this) ;
			return true ;
		}
		return false ;
	}
	public boolean ignore()
	{
		return m_Reference == null ;
	} 
	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#Clear()
	 */
	public void Clear()
	{
		super.Clear();
		if (m_Reference != null)
			m_Reference.Clear() ;
		m_Reference = null ;
	}
	

//	/**
//	 * @see semantic.CBaseLanguageEntity#GetHierarchy()
//	 */
//	@Override
//	public CEntityHierarchy GetHierarchy()
//	{
//		if (m_Reference != null)
//		{
//			return m_Reference.GetHierarchy();
//		}
//		return null ;
//	}	
}
