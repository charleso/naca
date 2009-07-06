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
public abstract class CEntityInline extends CBaseActionEntity
{


	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#GetDisplayName()
	 */
	@Override
	public String GetDisplayName()
	{
		return m_externalData.GetDisplayName();
	}
	/**
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntityInline(int l, CObjectCatalog cat, CBaseLanguageExporter out, CBaseExternalEntity e)
	{
		super(l, cat, out);
		m_externalData = e ;
		m_externalData.RegisterInlineAction(this) ;
		e.SetParent(this);
	}

	protected CBaseExternalEntity m_externalData = null;
	
	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#GetInternalLevel()
	 */
	public int GetInternalLevel()
	{
		return m_externalData.GetInternalLevel() ;
	}
	public boolean ignore()
	{
		return m_externalData.ignore() ;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#Clear()
	 */
	public void Clear()
	{
		super.Clear();
		if (m_externalData.IsNeedDeclarationInClass())
		{
			m_externalData.Clear() ;
		}
		else
		{
			m_externalData.m_InlineAction = null ;
		}
		m_externalData = null ;
	}
	/**
	 * @param sub
	 * @param newParent
	 */
	public void ReplaceParentForChild(CBaseLanguageEntity sub, CBaseLanguageEntity newParent)
	{
		CBaseLanguageEntity le = newParent.FindLastEntityAvailableForLevel(sub.GetInternalLevel()) ;
		if (le == null)
			le = newParent ;
		sub.m_parent = le ;		
	}
	public void AddChild(CBaseLanguageEntity e)
	{
		super.AddChild(e) ;
		int n = e.GetInternalLevel() ;
		int nsub = m_externalData.getActualSubLevel() ;
		if (n>0 && nsub>0 && n<nsub)
		{
			Transcoder.logWarn(e.getLine(), "WARNING : bad sub-level for structure : expecting "+nsub+" ; found "+n) ;
		}
		ReplaceParentForChild(e, m_externalData) ;  // child of INLINE entity must have the external data as parent for name
													// confict resolution, but must be child of INLINE to be exported
	}
	public CBaseLanguageEntity FindLastEntityAvailableForLevel(int level)
	{
		CBaseLanguageEntity e = m_externalData.FindLastEntityAvailableForLevel(level) ;
		CBaseLanguageEntity child = super.FindLastEntityAvailableForLevel(level) ;
		if (child == this)
		{
			child = null ;
		}
		if (child == null && e != null)
		{
			return this ;
		}
		else if (child != null && child != this)
		{
			return child ;
		}
		else if (m_parent != null)
		{
			CBaseLanguageEntity ep = m_parent.FindLastEntityAvailableForLevel(level);
			if (ep == null && m_parent.GetInternalLevel() < level && m_parent.GetInternalLevel()>0)
			{
				return m_parent ;
			}
			return ep ;
		}
		else 
		{
			return null ;
		}
	}
	@Override
	public CDataEntity FindFirstDataEntityAtLevel(int level)
	{
		CDataEntity de = m_externalData.FindFirstDataEntityAtLevel(level) ;
		if (de == null)
		{
			return super.FindFirstDataEntityAtLevel(level) ;
		}
		return de ;
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseActionEntity#ReplaceVariable(semantic.CDataEntity, semantic.CDataEntity)
	 */
	public boolean ReplaceExternalData(CBaseExternalEntity field, CBaseExternalEntity var)
	{
		if (field == m_externalData)
		{
			m_externalData = var ;
			return true ;
		}
		return false  ;
	}
	
	public CBaseExternalEntity getExternalEntity()
	{
		return m_externalData ;
	}
}
