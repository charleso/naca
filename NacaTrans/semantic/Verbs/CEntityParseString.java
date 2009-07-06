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

public abstract class CEntityParseString extends CBaseActionEntity
{
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		boolean bReplace = false;
		if (m_Variable == field)
		{
			m_Variable = var ;
			field.UnRegisterReadingAction(this) ;
			var.RegisterReadingAction(this) ;
			bReplace = true;
		}
		for (int i=0; i < m_arrDestinations.size(); i++)
		{
			CDataEntity[] entities = m_arrDestinations.get(i);
			for (int j=0; j < entities.length; j++)
			{
				CDataEntity entity = entities[j];
				if (entity != null && entity == field)
				{
					entity = var ;
					field.UnRegisterReadingAction(this) ;
					var.RegisterReadingAction(this) ;
					bReplace = true;
				}
			}
		}
		if (m_arrDelimitersMulti.contains(field))
		{
			int pos = m_arrDelimitersMulti.indexOf(field) ;
			m_arrDelimitersMulti.set(pos, var) ;
			field.UnRegisterWritingAction(this) ;
			var.RegisterWritingAction(this) ;
			bReplace = true ;
		}
		if (m_arrDelimitersSingle.contains(field))
		{
			int pos = m_arrDelimitersSingle.indexOf(field) ;
			m_arrDelimitersSingle.set(pos, var) ;
			field.UnRegisterWritingAction(this) ;
			var.RegisterWritingAction(this) ;
			bReplace = true ;
		}
		if (m_Tallying == field)
		{
			m_Tallying = var ;
			field.UnRegisterReadingAction(this) ;
			var.RegisterReadingAction(this) ;
			bReplace = true;
		}
		if (m_WithPointer == field)
		{
			m_WithPointer = var ;
			field.UnRegisterWritingAction(this) ;
			var.RegisterWritingAction(this) ;
			bReplace = true;
		}
		return bReplace;
	}

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityParseString(int line,	CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	public void ParseString(CDataEntity e)
	{
		m_Variable = e ;
	}
	public void AddDelimiterSingle(CDataEntity e)
	{
		m_arrDelimitersSingle.add(e);
	}
	public void AddDelimiterMulti(CDataEntity e)
	{
		m_arrDelimitersMulti.add(e);
	}
	public void AddDestination(CDataEntity[] e)
	{
		m_arrDestinations.add(e);
	}
	public void setTallying(CDataEntity e)
	{
		m_Tallying = e;
	}
	public void setWithPointer(CDataEntity e)
	{
		m_WithPointer = e;
	}
	protected CDataEntity m_Variable = null ;
	protected Vector<CDataEntity[]> m_arrDestinations = new Vector<CDataEntity[]>() ;
	protected Vector<CDataEntity> m_arrDelimitersMulti = new Vector<CDataEntity>() ;
	protected Vector<CDataEntity> m_arrDelimitersSingle = new Vector<CDataEntity>() ;
	protected CDataEntity m_Tallying = null ;
	protected CDataEntity m_WithPointer = null ;
	
	public void Clear()
	{
		super.Clear() ;
		m_Variable = null ;
		m_arrDelimitersMulti.clear() ;
		m_arrDelimitersSingle.clear() ;
		m_arrDestinations.clear();
	}
	public boolean ignore()
	{
		return m_Variable.ignore();
	}
}
