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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		for (CDataEntity value : m_Values)
		{
			if (value == field)
			{
				field.UnRegisterReadingAction(this) ;
				var.RegisterReadingAction(this) ;
				bRes = true ;
			}
		}
		for (CDataEntity value : m_Destination)
		{
			if (value == field)
			{
				field.UnRegisterWritingAction(this) ;
				var.RegisterWritingAction(this) ;
				bRes = true ;
			}
		}
		if (m_Destination.isEmpty())
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
		SetSubstract(var, Arrays.asList(val), Arrays.asList(dest));
	}
	
	public void SetSubstract(CDataEntity var, List<CDataEntity> val, List<CDataEntity> dest)
	{
		m_Variable = var ;
		m_Values.addAll(val);
		m_Destination.addAll(dest) ;
	}
	
	protected CDataEntity m_Variable ;
	protected final List<CDataEntity> m_Values = new ArrayList<CDataEntity>();
	protected final List<CDataEntity> m_Destination = new ArrayList<CDataEntity>();
	public void Clear()
	{
		super.Clear() ;
		m_Variable = null ;
		m_Values.clear();
		m_Destination.clear() ;
	}
	public boolean ignore()
	{
		boolean ignore = m_Variable.ignore() ;
		for (CDataEntity value : m_Values)
		{
			ignore |= value.ignore();
		}
		for (CDataEntity value : m_Destination)
		{
			ignore |= value.ignore();
		}
		return ignore;
	}
}

