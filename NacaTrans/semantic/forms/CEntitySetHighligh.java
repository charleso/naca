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
import semantic.CBaseEntityFactory;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntitySetHighligh extends CBaseActionEntity
{

//	public static class CFieldHighligh
//	{
//		protected CFieldHighligh(String s)
//		{
//			m_Text = s ;
//		}
//		public String m_Text = "" ;
//		public static CFieldHighligh NORMAL = new CFieldHighligh("Normal");
//	} 
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntitySetHighligh(int line, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity field)
	{
		super(line, cat, out);
		m_RefField = field ;
	}
	
	public void SetBlink()
	{
		m_bIsBlink = true ;
	}
	public void SetReverse()
	{
		m_bIsReverse = true ;
	}
	public void SetUnderlined()
	{
		m_bIsUnderlined = true ;
	}
	//protected CFieldHighligh m_highlight = null ;
	protected boolean m_bIsBlink = false ;
	protected boolean m_bIsReverse = false ;
	protected boolean m_bIsUnderlined = false ;
	protected boolean m_bIsNormal = false ;
	protected CDataEntity m_RefField = null ;
	protected CDataEntity m_HighLightValue = null ;
	public void Clear()
	{
		super.Clear();
		m_RefField = null ;
		m_HighLightValue = null ;
	}

	public void SetHighLight(CDataEntity entity)
	{
		m_HighLightValue = entity ;		
	}

	public void SetNormal()
	{
		m_bIsNormal = true ;		
	}
	public boolean ignore()
	{
		if (m_RefField == null || m_RefField.ignore())
		{
			return true ;
		}
		else if (m_HighLightValue != null && m_HighLightValue.ignore())
		{
			return true ;
		}
		return false ;
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
	public CBaseActionEntity GetSpecialAssignement(String val, CBaseEntityFactory factory)
	{
		return CEntityFieldHighlight.intGetSpecialAssignment(val, m_RefField, factory, getLine());
	}

	/**
	 * 
	 */
	public void Reset()
	{
		m_bIsBlink = false ;
		m_bIsNormal = false ;
		m_bIsReverse = false ;
		m_bIsUnderlined = false ;
		m_HighLightValue = null ;		
	}
}
