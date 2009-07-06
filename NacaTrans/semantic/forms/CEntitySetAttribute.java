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

import parser.expression.CStringTerminal;
import parser.expression.CTerminal;
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
public abstract class CEntitySetAttribute extends CBaseActionEntity
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntitySetAttribute(int line, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity field)
	{
		super(line, cat, out);
		m_RefField = field ;
	}

	protected CDataEntity m_RefField = null ;

	public void SetBright()
	{
		m_bBright = true ;		
	}
	protected boolean m_bBright = false ;

	public void SetModified()
	{
		m_bModified = true ;		
	}
	protected boolean m_bModified = false ; 

	public void SetNumeric()
	{
		m_bNumeric = true ; 		
	}
	protected boolean m_bNumeric = false ;

	public void SetProtected()
	{
		m_bProtected = true ;		
	}
	protected boolean m_bProtected = false ;

	public void SetUnprotected()
	{
		m_bUnProtected = true ;		
	}
	protected boolean m_bUnProtected = false ;

	public void SetAutoSkip()
	{
		m_bAutoSkip = true ;		
	}
	protected boolean m_bAutoSkip = false ;

	public void SetNormal()
	{
		m_bNormal = true ;		
	}
	protected boolean m_bNormal = false ;

	public void SetUnmodified()
	{
		m_bUnmodified = true;
	}
	protected boolean m_bUnmodified = false ;

	public void SetDark()
	{
		m_bDark = true ;
	}
	protected boolean m_bDark = false ;

	public void SetAttribute(CDataEntity entity)
	{
		m_AttributeValue = entity ;		
	}
	protected CDataEntity m_AttributeValue = null ; 
	public void Clear()
	{
		super.Clear();
		m_AttributeValue = null ;
		m_RefField = null ;
	}
	public boolean ignore()
	{
		if (m_RefField == null || m_RefField.ignore())
		{
			return true ;
		}
		else if (m_AttributeValue != null && m_AttributeValue.ignore())
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
		if (m_RefField != null)
		{
			CTerminal term = new CStringTerminal(val) ;
			CBaseActionEntity act = CEntityFieldAttribute.intGetSpecialAssignment(m_RefField, term, factory, getLine()) ;
			return act ;
		}
		return null;
	}
}
