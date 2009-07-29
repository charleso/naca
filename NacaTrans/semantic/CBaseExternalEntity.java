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
 * Created on 5 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic;

import java.util.NoSuchElementException;

import generate.*;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CBaseExternalEntity extends CDataEntity
{

	/**
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CBaseExternalEntity(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, name, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#ExportReference(semantic.CBaseLanguageExporter)
	 */
	public void InitDependences(CBaseEntityFactory factory) 
	{
		int n= 0;
	}

	public CBaseLanguageEntity FindLastEntityAvailableForLevel(int level)
	{
		CBaseLanguageEntity le = null ;
		try
		{
			le = m_lstChildren.getLast() ;
		}
		catch (NoSuchElementException e)
		{
			//e.printStackTrace();
			return this ;
		}
		int nLevel = le.GetInternalLevel() ; 
		if (m_ReplaceLevel != 0 && nLevel == m_ReplaceLevel)
		{
			nLevel = m_ReplaceBy ;
		}
		if (nLevel>0 && nLevel < level)
		{
			CBaseLanguageEntity e = le.FindLastEntityAvailableForLevel(level);
			if (e != null)
			{
				return e ;
			}
			else
			{
				return le ;
			}
		}
//		else if (m_parent != null)
//		{
//			return m_parent.FindLastEntityAvailableForLevel(level) ;
//		}
		else
		{
			return null ;
		}
	}
	
	public boolean IsNeedDeclarationInClass()
	{
		return true ;
	}
	
	public abstract String GetTypeDecl() ;
	
	public void ReplaceLevel(int n1, int n2)
	{
		m_ReplaceLevel = n1 ;
		m_ReplaceBy = n2 ;
	}
	
	protected int m_ReplaceLevel = 0 ;
	protected int m_ReplaceBy = 0 ;
	public int GetReplaceItem()
	{
		return m_ReplaceLevel ;
	}
	public int GetReplaceValue()
	{
		return m_ReplaceBy ;
	}
	public String GetConstantValue()
	{
		return "" ;
	} 	 
	public void RegisterInlineAction(CEntityInline act)
	{
		m_InlineAction = act ;
	}
	protected CEntityInline m_InlineAction = null ;
	public CEntityInline GetInlineAction()
	{
		if (m_InlineAction != null)
		{
			return m_InlineAction ;
		}
		else if (m_Of != null)
		{
			return m_Of.GetInlineAction() ;
		}
		else
		{
			return null ;
		}
	}
	public void Clear()
	{
		super.Clear();
		m_InlineAction = null ;
	}


	public CBaseLanguageExporter getExporter()
	{
		return GetXMLOutput() ;
	}


}
