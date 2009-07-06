/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Aug 26, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.Verbs;

import generate.CBaseLanguageExporter;

import java.util.Vector;

import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityReplace extends CBaseActionEntity
{
	protected static class CReplaceMode
	{
		public static CReplaceMode ALL = new CReplaceMode();
		public static CReplaceMode LEADING = new CReplaceMode();
		public static CReplaceMode FIRST = new CReplaceMode();
	}
	protected static class CReplaceType
	{
		public static CReplaceType CUSTOM = new CReplaceType() ; 
		public static CReplaceType SPACES = new CReplaceType() ; 
		public static CReplaceType ZEROS = new CReplaceType() ; 
		public static CReplaceType LOW_VALUES = new CReplaceType() ; 
		public static CReplaceType HIGH_VALUES = new CReplaceType() ; 
	}
	protected class CReplaceItem
	{
		public CReplaceMode m_Mode = null ;
		public CReplaceType m_ReplaceDataType = null ;
		public CDataEntity m_ReplaceData = null ;
		public CReplaceType m_ByDataType = null ;
		public CDataEntity m_ByData = null ;
	} 
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityReplace(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	
	protected CDataEntity m_Variable = null ; 
	protected Vector<CReplaceItem> m_arrItemsToReplace = new Vector<CReplaceItem>() ;
	private CReplaceItem m_curItem = null ;
	public void Clear()
	{
		super.Clear() ;
		m_Variable = null ;
		m_arrItemsToReplace.clear() ;
		if (m_curItem != null)
		{
			m_curItem.m_ByData = null ;
			m_curItem.m_ByDataType = null ;
			m_curItem.m_ReplaceData = null ;
			m_curItem = null ;
		}
	}

	public void SetReplace(CDataEntity e)
	{
		m_Variable = e ;
	}
	public void AddReplaceLeading()
	{
		m_curItem = new CReplaceItem() ;
		m_curItem.m_Mode = CReplaceMode.LEADING;
	}
	public void AddReplaceAll()
	{
		m_curItem = new CReplaceItem() ;
		m_curItem.m_Mode = CReplaceMode.ALL;
	}
	public void AddReplaceFirst()
	{
		m_curItem = new CReplaceItem() ;
		m_curItem.m_Mode = CReplaceMode.FIRST;
	}
	public void ReplaceSpaces()
	{
		m_curItem.m_ReplaceDataType = CReplaceType.SPACES ;
		m_curItem.m_ReplaceData = null ;
	}
	public void ReplaceZeros()
	{
		m_curItem.m_ReplaceDataType = CReplaceType.ZEROS ;
		m_curItem.m_ReplaceData = null ;
	}
	public void ReplaceLowValues()
	{
		m_curItem.m_ReplaceDataType = CReplaceType.LOW_VALUES ;
		m_curItem.m_ReplaceData = null ;
	}
	public void ReplaceHighValues()
	{
		m_curItem.m_ReplaceDataType = CReplaceType.HIGH_VALUES;
		m_curItem.m_ReplaceData = null ;
	}
	public void BySpaces()
	{
		m_curItem.m_ByDataType = CReplaceType.SPACES ;
		m_curItem.m_ByData = null ;
		m_arrItemsToReplace.add(m_curItem) ;
		m_curItem = null ;
	}
	public void ByZeros()
	{
		m_curItem.m_ByDataType = CReplaceType.ZEROS ;
		m_curItem.m_ByData = null ;
		m_arrItemsToReplace.add(m_curItem) ;
		m_curItem = null ;
	}
	public void ByLowValues()
	{
		m_curItem.m_ByDataType = CReplaceType.LOW_VALUES ;
		m_curItem.m_ByData = null ;
		m_arrItemsToReplace.add(m_curItem) ;
		m_curItem = null ;
	}
	public void ByHighValues()
	{
		m_curItem.m_ByDataType = CReplaceType.HIGH_VALUES ;
		m_curItem.m_ByData = null ;
		m_arrItemsToReplace.add(m_curItem) ;
		m_curItem = null ;
	}
	public void ReplaceData(CDataEntity e)
	{
		m_curItem.m_ReplaceDataType = CReplaceType.CUSTOM ;
		m_curItem.m_ReplaceData = e ;
	}
	public void ByData(CDataEntity e)
	{
		m_curItem.m_ByDataType = CReplaceType.CUSTOM ;
		m_curItem.m_ByData = e ;
		m_arrItemsToReplace.add(m_curItem) ;
		m_curItem = null ;
	}
	public boolean ignore()
	{
		return m_Variable.ignore();
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseActionEntity#ReplaceVariable(semantic.CDataEntity, semantic.CDataEntity)
	 */
	@Override
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_Variable == field)
		{
			m_Variable = var ;
			field.UnRegisterWritingAction(this) ;
			var.RegisterWritingAction(this) ;
			return true ;
		}
		return false ;
	}
}
