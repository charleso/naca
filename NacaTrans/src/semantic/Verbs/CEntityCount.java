/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 6 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.Verbs;

import java.util.Vector;

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
public abstract class CEntityCount extends CBaseActionEntity
{

	/* (non-Javadoc)
	 * @see semantic.CBaseActionEntity#ReplaceVariable(semantic.CDataEntity, semantic.CDataEntity)
	 */
	@Override
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_Variable == field)
		{
			m_Variable = var ;
			field.UnRegisterReadingAction(this)  ;
			var.RegisterReadingAction(this) ;
			return true ;
		}
		return false ;
	}

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityCount(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	public void SetCount(CDataEntity var)
	{
		m_Variable = var ;
	}

	public void SetToVar(CDataEntity var)
	{
		m_ToVariable = var ;
	}
	
	protected CDataEntity m_Variable = null ;
	protected CDataEntity m_ToVariable = null ;
	protected Vector<CDataEntity> m_arrCountLeadingToken = new Vector<CDataEntity>() ; 
	protected Vector<CDataEntity> m_arrCountAllToken = new Vector<CDataEntity>() ; 
	protected Vector<CDataEntity> m_arrCountAfterToken = new Vector<CDataEntity>() ; 
	protected Vector<CDataEntity> m_arrCountBeforeToken = new Vector<CDataEntity>() ; 
	public void Clear()
	{
		super.Clear() ;
		m_arrCountAfterToken.clear() ;
		m_arrCountAllToken.clear() ;
		m_arrCountBeforeToken.clear() ;
		m_Variable = null ;
		m_ToVariable = null ;
	}

	public void CountBefore(CDataEntity entity)
	{
		m_arrCountBeforeToken.add(entity) ;
	}

	public void CountAll(CDataEntity entity)
	{
		m_arrCountAllToken.add(entity) ;
	}
	
	public void CountLeading(CDataEntity entity)
	{
		m_arrCountLeadingToken.add(entity) ;
	}

	public void CountAfter(CDataEntity entity)
	{
		m_arrCountAfterToken.add(entity) ;
	}
	public boolean ignore()
	{
		return m_Variable.ignore();
	}
}
