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

import java.util.NoSuchElementException;

import generate.*;
import parser.expression.CTerminal;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityExternalDataStructure extends CBaseExternalEntity
{

		/* (non-Javadoc)
	 * @see semantic.CBaseExternalEntity#IsNeedDeclarationInClass()
	 */
	public boolean IsNeedDeclarationInClass()
	{
		return !m_bInline ;
	}
/**
	 * @param name
	 * @param cat
	 * @param out
	 */
	protected String m_csClassName = "" ;
	public CEntityExternalDataStructure(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, name, cat, out);
		m_csClassName = name ;
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetSpecialAssignment(parser.expression.CTerminal)
	 */
	public CBaseActionEntity GetSpecialAssignment(CTerminal term, CBaseEntityFactory factory, int l)
	{
		return null;
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetSpecialAssignment(semantic.CBaseDataEntity)
	 */
	public CBaseActionEntity GetSpecialAssignment(CDataEntity term, CBaseEntityFactory factory, int l)
	{
		return null;
	}
	public int GetInternalLevel()
	{
		try 
		{
			int i =0;
			int level = 0 ;
			while (i<m_lstChildren.size())
			{
				CBaseLanguageEntity child = m_lstChildren.get(i);
				int l = child.GetInternalLevel() ;
				if (l>0 && level==0)
				{
					level = l ;
				}
				else if (l>0 && l<level)
				{  // 2 or more children with the same level...
					level = l ;
				}
				i ++ ;
			}
			if (level > 0 && level == m_ReplaceLevel)
			{
				return m_ReplaceBy ;
			}
			return level ;
		}
		catch (NoSuchElementException e)
		{
			return 0 ;
		}
	}


	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#SetName(java.lang.String)
	 */
	public void Rename(String name)
	{
		super.Rename(name);
		if (m_csClassName.equals(""))
		{
			m_csClassName = name ;
		}
	}
	public boolean ignore()
	{
		return false ;
	}
	protected void RegisterMySelfToCatalog()
	{
		// nothing
	}
	
	protected boolean m_bInline = false ;
	public void SetInline(boolean bInline)
	{
		m_bInline = bInline;
	}
	/**
	 * @return
	 */
	public boolean isInlined()
	{
		return m_bInline ;
	}
	/**
	 * @return
	 */
	public int getActualSubLevel()
	{
		try
		{
			CBaseLanguageEntity[] arr = new CBaseLanguageEntity[m_lstChildren.size()] ;
			m_lstChildren.toArray(arr) ;
			for (CBaseLanguageEntity e : arr)
			{
				int n = e.GetInternalLevel() ;
				if (n > 0)
				{
					CDataEntity le = (CDataEntity)e ;
					int m = le.getActualSubLevel() ;
					return m ;
				}
			}
		} catch (NoSuchElementException e)
		{
		}
		return 0 ;
	}
	public void ApplyAliasPattern(String csRenamePattern)
	{
		ApplyAliasPatternToChildren(csRenamePattern) ;
	}



}
