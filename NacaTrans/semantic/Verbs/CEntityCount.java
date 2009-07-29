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
	
	
	protected CDataEntity m_Variable = null ;
	protected Vector<CDataEntity> m_arrCountLeadingToken = new Vector<CDataEntity>() ; 
	protected Vector<CDataEntity> m_arrCountVariableLeading = new Vector<CDataEntity>() ; 
	protected Vector<CDataEntity> m_arrCountAllToken = new Vector<CDataEntity>() ; 
	protected Vector<CDataEntity> m_arrCountVariableAll = new Vector<CDataEntity>() ; 
	protected Vector<CDataEntity> m_arrCountAfterToken = new Vector<CDataEntity>() ; 
	protected Vector<CDataEntity> m_arrCountVariableAfterToken = new Vector<CDataEntity>() ; 
	protected Vector<CDataEntity> m_arrCountBeforeToken = new Vector<CDataEntity>() ; 
	protected Vector<CDataEntity> m_arrCountVariableBeforeToken = new Vector<CDataEntity>() ; 
	protected boolean m_bFunctionReverse = false;
	public void Clear()
	{
		super.Clear() ;
		m_arrCountAfterToken.clear() ;
		m_arrCountAllToken.clear() ;
		m_arrCountBeforeToken.clear() ;
		m_arrCountVariableAfterToken.clear();
		m_arrCountVariableAll.clear();
		m_arrCountVariableBeforeToken.clear() ;
		m_Variable = null ;
	}

	public void CountBefore(CDataEntity entity, CDataEntity evar)
	{
		m_arrCountBeforeToken.add(entity) ;
		m_arrCountVariableBeforeToken.add(evar) ;
	}

	public void CountAll(CDataEntity entity, CDataEntity evar)
	{
		m_arrCountAllToken.add(entity) ;
		m_arrCountVariableAll.add(evar) ;
	}
	
	public void CountLeading(CDataEntity entity, CDataEntity evar)
	{
		m_arrCountLeadingToken.add(entity) ;
		m_arrCountVariableLeading.add(evar) ;
	}

	public void CountAfter(CDataEntity entity, CDataEntity evar)
	{
		m_arrCountAfterToken.add(entity) ;
		m_arrCountVariableAfterToken.add(evar) ;
	}
	public boolean ignore()
	{
		return m_Variable.ignore();
	}
	
	public void setFunctionReverse(boolean bFunctionReverse)
	{
		m_bFunctionReverse = bFunctionReverse;
	}
}
