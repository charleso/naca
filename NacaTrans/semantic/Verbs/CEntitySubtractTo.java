/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Aug 25, 2004
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
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntitySubtractTo extends CBaseActionEntity
{
	/* (non-Javadoc)
	 * @see semantic.CBaseActionEntity#ReplaceVariable(semantic.CDataEntity, semantic.CDataEntity)
	 */
	@Override
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		boolean bRes = false ;
		if (m_Variable == field)
		{
			m_Variable = var ;
			field.UnRegisterReadingAction(this) ;
			var.RegisterReadingAction(this) ;
			bRes = true ;
		}
		if (m_Value == field)
		{
			m_Value = var ;
			field.UnRegisterReadingAction(this) ;
			var.RegisterReadingAction(this) ;
			bRes = true ;
		}
		if (m_Destination == field)
		{
			m_Destination = var ;
			field.UnRegisterWritingAction(this) ;
			var.RegisterWritingAction(this) ;
			bRes = true ;
		}
		else if (m_Destination == null)
		{
			field.UnRegisterWritingAction(this) ;
			var.RegisterWritingAction(this) ;
			bRes = true ;
		}
		return bRes ;
	}

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntitySubtractTo(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	
	public void SetSubstract(CDataEntity var, CDataEntity val, CDataEntity dest)
	{
		m_Variable = var ;
		m_Value = val ;
		m_Destination = dest ;
	}
	
	protected CDataEntity m_Variable ;
	protected CDataEntity m_Value ;
	protected CDataEntity m_Destination ;
	public void Clear()
	{
		super.Clear() ;
		m_Variable = null ;
		m_Value = null ;
		m_Destination = null ;
	}
	public boolean ignore()
	{
		boolean ignore = m_Variable.ignore() ;
		ignore |= m_Value.ignore();
		if (m_Destination != null)
		{
			ignore |= m_Destination.ignore() ;
		}
		return ignore;
	}
}

