/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 1 sept. 2004
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
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityStringConcat extends CBaseActionEntity
{

	/* (non-Javadoc)
	 * @see semantic.CBaseActionEntity#ReplaceVariable(semantic.CDataEntity, semantic.CDataEntity)
	 */
	@Override
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_eVariable == field)
		{
			m_eVariable = var ;
			field.UnRegisterWritingAction(this) ;
			var.RegisterWritingAction(this) ;
			return true ;
		}
		if (m_arrItems.contains(field))
		{
			int pos;
			while ((pos = m_arrItems.indexOf(field)) != -1)
			{
				m_arrItems.set(pos, var) ;
				field.UnRegisterReadingAction(this) ;
				var.RegisterReadingAction(this) ;
			}	
			return true ;
		}
		return false ;
	}
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityStringConcat(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	
	protected Vector<CDataEntity> m_arrItems = new Vector<CDataEntity>() ; 
	protected Vector<CDataEntity> m_arrItemsDelimiters = new Vector<CDataEntity>() ; 
	protected CDataEntity m_eVariable = null ;
	protected CDataEntity m_eStartIndex = null ;
	public void Clear()
	{
		super.Clear() ;
		m_arrItems.clear();
		m_arrItemsDelimiters.clear() ;
		m_eStartIndex = null ;
		m_eVariable = null;
	}
	public void SetVariable(CDataEntity e)
	{
		m_eVariable = e ;
	}
	public void SetVariable(CDataEntity e, CDataEntity s)
	{
		m_eVariable = e ;
		m_eStartIndex = s ;
	}
	public void AddItem(CDataEntity eItem, CDataEntity eUntil)
	{
		m_arrItems.add(eItem);
		m_arrItemsDelimiters.add(eUntil);
	}
	public void AddItem(CDataEntity eItem)
	{
		m_arrItems.add(eItem);
		m_arrItemsDelimiters.add(null);
	}
	public boolean ignore()
	{
		boolean ignore = m_eVariable.ignore();
		return ignore ;
	}
}
