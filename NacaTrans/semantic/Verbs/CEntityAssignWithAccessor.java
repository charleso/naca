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
 * Created on 11 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.Verbs;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityAssignWithAccessor extends CBaseActionEntity
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityAssignWithAccessor(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	
	public void SetAssign(CDataEntity e, CDataEntity val)
	{
		m_Reference = e ;
		m_Value = val ;
	}
	protected CDataEntity m_Reference = null ;
	protected CDataEntity m_Value = null ;
	protected boolean m_bFillAll = false ;
	public void Clear()
	{
		super.Clear();
		m_Reference= null ;
		m_Value = null ;
	}
	public boolean ignore()
	{
		if (m_Reference == null || m_Value == null)
		{
			return true ;
		}
		return m_Reference.ignore() || m_Value.ignore() ;
	}
	public boolean IgnoreVariable(CDataEntity data)
	{
		if (m_Reference == data)
		{
			m_Reference = null ;
			data.UnRegisterWritingAction(this) ;
			return true ;
		}
		else if (m_Value == data)
		{
			m_Value = null ;
			data.UnRegisterReadingAction(this) ;
			return true ;
		}
		return false ;
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_Reference == field)
		{
			m_Reference = var ;
			field.UnRegisterWritingAction(this) ;
			var.RegisterWritingAction(this) ;
			return true ;
		}
		if (m_Value == field)
		{
			m_Value = var ;
			field.UnRegisterReadingAction(this) ;
			var.RegisterReadingAction(this) ;
			return true ;
		} 
		return false ;
	}

	/**
	 * @param e1
	 */
	public void SetValue(CDataEntity e1)
	{
		m_Value = e1 ;
	}

	/**
	 * @param e2
	 */
	public void SetRefTo(CDataEntity e2)
	{
		m_Reference = e2 ;
	}

	/**
	 * @param fillAll
	 */
	public void SetFillAll(boolean fillAll)
	{
		m_bFillAll = fillAll;
	}

}
