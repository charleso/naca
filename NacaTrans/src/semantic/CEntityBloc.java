/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 3 août 2004
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
public abstract class CEntityBloc extends CBaseLanguageEntity
{
	/**
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntityBloc(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, "", cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseSemanticEntity#RegisterMySelfToCatalog()
	 */
	protected void RegisterMySelfToCatalog()
	{
		// NOTHING		
	}
	public boolean ignore()
	{
		return isChildrenIgnored();
	}

	public void SetEndLine(int n)
	{
		m_nEndLine = n ;
	}
	protected int m_nEndLine = 0 ;

	public int GetEndLine()
	{
		return m_nEndLine ;
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
}
