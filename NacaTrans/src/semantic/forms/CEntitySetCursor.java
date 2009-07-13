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
package semantic.forms;

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
public abstract class CEntitySetCursor extends CBaseActionEntity
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntitySetCursor(int line, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity field)
	{
		super(line, cat, out);
		m_RefField = field ;
	}
	
	protected CDataEntity m_RefField = null ;
	public void Clear()
	{
		super.Clear();
		m_RefField = null ;
	}
	public boolean ignore()
	{
		return m_RefField == null || (m_ReferenceValue!=null && m_ReferenceValue.ignore());
	}
	public boolean IgnoreVariable(CDataEntity data)
	{
		if (data == m_RefField)
		{
			m_RefField = null ;
			data.UnRegisterWritingAction(this) ;
			return true ;
		}
		return false ;
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_RefField == field)
		{
			m_RefField = var ;
			field.UnRegisterWritingAction(this) ;
			var.RegisterWritingAction(this) ;
			return true ;
		}
		return false ;
	}
	/**
	 * 
	 */
	public void removeCursor()
	{
		m_bRemoveCursor = true ;		
	}
	protected boolean m_bRemoveCursor = false ;
	/**
	 * @param term
	 */
	public void SetReference(CDataEntity term)
	{
		m_ReferenceValue = term ;
	}
	protected CDataEntity m_ReferenceValue = null ;

}
