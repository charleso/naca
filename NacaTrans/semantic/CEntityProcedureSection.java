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
public abstract class CEntityProcedureSection extends CEntityProcedure
{

	/**
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntityProcedureSection(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, name, cat, out, null);
	}

	protected CEntityBloc m_SectionBloc =null ;
	public void SetSectionBloc(CEntityBloc b)
	{
		m_SectionBloc = b ;
	}
	protected void RegisterMySelfToCatalog()
	{
		m_ProgramCatalog.RegisterProcedure(GetName(), this, null) ;
		m_ProgramCatalog.getCallTree().RegisterSection(this) ;
	}
	public CEntityProcedureSection getSectionContainer()
	{
		return this ;
	} 
	public boolean UpdateAction(CBaseActionEntity entity, CBaseActionEntity newCond)
	{
		if (m_SectionBloc!=null && m_SectionBloc.UpdateAction(entity, newCond))
		{
			return true ;
		}
		for (int i=0; i<m_lstChildren.size(); i++)
		{
			CBaseLanguageEntity act = m_lstChildren.get(i) ;
			if (act.UpdateAction(entity, newCond))
			{
				return true ;
			}
		}
		return false ;
	}
	public void Clear()
	{
		super.Clear();
		if (m_SectionBloc != null)
		{
			m_SectionBloc.Clear() ;
		}
		m_SectionBloc = null ;
	}
	public boolean hasExplicitGetOut()
	{
		if (m_SectionBloc == null)
		{
			return false ;
		}
		return m_SectionBloc.hasExplicitGetOut() ;
	}
	/**
	 * 
	 */
	public void ReduceToProcedure()
	{
		m_bReducedToProcedure = true ;
	}	
	protected boolean m_bReducedToProcedure = false ;
	/**
	 * @return
	 */
	public CEntityBloc getSectionBloc()
	{
		return m_SectionBloc ;
	}
	public boolean ignore()
	{
		if (m_bIgnore)
		{
			return true ;
		}
		if (m_bReducedToProcedure)
		{
			if (m_SectionBloc == null)
			{
				return isChildrenIgnored() ;
			}
			else
			{
				return m_SectionBloc.ignore() && isChildrenIgnored() ;
			}
		}
		return false ;
	}
	
	public void setLabelSentence(boolean bLabelSentence)
	{
		m_bLabelSentence = bLabelSentence;
	}
	
	protected boolean m_bLabelSentence = false;
}
