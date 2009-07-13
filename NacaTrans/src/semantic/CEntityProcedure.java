/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 2 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic;

import generate.*;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityProcedure extends CBaseLanguageEntity
{

	/**
	 * @param name
	 * @param cat
	 */
	protected CEntityProcedure(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, CEntityProcedureSection section)
	{
		super(l, "", cat, out);
		m_sectionContainer = section ;
		SetName(name);
	}
	protected CEntityProcedureSection m_sectionContainer = null ;
	/* (non-Javadoc)
	 * @see semantic.CBaseSemanticEntity#RegisterMySelfToCatalog()
	 */
	protected void RegisterMySelfToCatalog()
	{
		m_ProgramCatalog.RegisterProcedure(GetName(), this, m_sectionContainer) ;
		m_ProgramCatalog.getCallTree().RegisterProcedure(this) ;
	}

	public void setFullName()
	{
		if (m_sectionContainer == null)
		{
			String fullName = GetName() + "$" + ms_nLastProcedureIndex ;
			ms_nLastProcedureIndex ++ ;
			SetName(fullName) ;
		}
		else if (GetName().indexOf('$')>0)
		{
			String fullName = GetName() + "$" + ms_nLastProcedureIndex ;
			ms_nLastProcedureIndex ++ ;
			Rename(fullName) ;
		}
		else
		{
			String fullName = GetName() + "$" + m_sectionContainer.GetName() ;
			Rename(fullName) ;
		}
	}
	
	protected static int ms_nLastProcedureIndex = 0 ;
	public abstract String ExportReference(int nLine) ;
	/* (non-Javadoc)
	 * @see semantic.CBaseSemanticEntity#DoExport()
	 */
	public boolean ignore()
	{
		if (m_bIgnore)
		{
			int n=0;
		}
		return m_bIgnore ;
	}

	public boolean UpdateAction(CBaseActionEntity entity, CBaseActionEntity newCond)
	{
//		return m_sectionContainer.UpdateAction(entity, newCond);
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
	public void Clear()
	{
		super.Clear();
		m_sectionContainer = null ;
	}

	/**
	 * @return
	 */
	public boolean hasExplicitGetOut()
	{
		if (m_lstChildren.isEmpty())
		{
			return false ;
		}
		CBaseActionEntity le = (CBaseActionEntity)m_lstChildren.getLast() ;
		return le.hasExplicitGetOut() ;
	}

	/**
	 * 
	 */
	public void setIgnore()
	{
		m_bIgnore = true ;
	}
	/**
	 * @return
	 */
	public boolean isEmpty()
	{
		boolean bIsEmpty = true ;
		for (int i=0; i<m_lstChildren.size() && bIsEmpty; i++)
		{
			CBaseActionEntity act = (CBaseActionEntity)m_lstChildren.get(i) ;
			bIsEmpty &= act.ignore() ;
		}
		return bIsEmpty ;
		
	}

	/**
	 * @param endLine
	 */
	public void SetEndLine(int endLine)
	{
		m_nEndLine = endLine ;
	}

	protected int m_nEndLine = 0 ;

}
