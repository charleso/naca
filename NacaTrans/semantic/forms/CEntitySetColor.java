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
public abstract class CEntitySetColor extends CBaseActionEntity
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntitySetColor(int line, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity field)
	{
		super(line, cat, out);
		m_Field = field ;
	}
	
	public void SetColor(CEntityFieldColor.CFieldColor c)
	{
		m_Color = c ;
	}
	protected CEntityFieldColor.CFieldColor m_Color = null ;
	protected CDataEntity m_Field = null ;
	public void Clear()
	{
		super.Clear();
		m_Field = null ;
	}
	public boolean ignore()
	{
		if (m_Field == null || m_Field.ignore())
		{
			return true ;
		}
		else if (m_ColorVariable != null && m_ColorVariable.ignore())
		{
			return true ;
		}
		return false ;
	}
	public boolean IgnoreVariable(CDataEntity data)
	{
		if (data == m_Field)
		{
			m_Field = null ;
			data.UnRegisterWritingAction(this) ;
			return true ;
		}
		return false ;
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_Field == field)
		{
			m_Field = var ;
			field.UnRegisterWritingAction(this) ;
			var.RegisterWritingAction(this) ;
			return true ;
		}
		return false ;
	}

	/**
	 * @param term
	 */
	public void SetColor(CDataEntity term)
	{
		m_Color = null ;
		m_ColorVariable = term ;		
	}
	protected CDataEntity m_ColorVariable = null ;
}
