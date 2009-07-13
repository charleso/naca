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
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityMultiply extends CBaseActionEntity
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityMultiply(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	protected CDataEntity m_What = null ;
	protected CDataEntity m_By = null ;
	protected CDataEntity m_To = null ;
	protected boolean m_bIsRounded = false ;
	public void Clear()
	{
		super.Clear() ;
		m_What = null ;
		m_By = null ;
		m_To = null ;
	}
	
	public void SetMultiply(CDataEntity what, CDataEntity by, CDataEntity to, boolean isRounded)
	{
		m_What = what ;
		m_By = by ;
		m_To = to ; 
		m_bIsRounded = isRounded ;
	}
	public void SetMultiply(CDataEntity what, CDataEntity by, boolean isRounded)
	{
		m_What = what ;
		m_By = by ;
		m_To = by ; 
		m_bIsRounded = isRounded ;
	}
	public boolean ignore()
	{
		boolean ignore = m_What.ignore();
		ignore |= m_By.ignore();
		ignore |= m_To.ignore() ;
		return ignore ; 
	}
}
