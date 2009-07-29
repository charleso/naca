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
public abstract class CEntityDivide extends CBaseActionEntity
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityDivide(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	
	protected CDataEntity m_What = null ;
	protected CDataEntity m_By = null ;
	protected CDataEntity m_Result = null ;
	protected CDataEntity m_Remainder = null ;
	protected boolean m_bIsRounded = false ;
	public void Clear()
	{
		super.Clear() ;
		m_What = null ;
		m_By = null ;
		m_Result = null ;
		m_Remainder = null ;
	}
	
	public void SetDivide(CDataEntity what, CDataEntity by, CDataEntity result, boolean isRounded) 
	{
		m_What = what ;
		m_By = by ;
		m_bIsRounded = isRounded ;
		m_Result = result ;
	}
	public void SetDivide(CDataEntity what, CDataEntity by, boolean isRounded) 
	{
		m_What = what ;
		m_By = by ;
		m_bIsRounded = isRounded ;
		m_Result = what ;
	}
	public void SetRemainder(CDataEntity rem)
	{
		m_Remainder = rem ;
	}
	public boolean ignore()
	{
		boolean ignore = m_What.ignore() ;
		ignore |= m_By.ignore() ;
		ignore |= m_Result.ignore() ;
		if (m_Remainder != null)
		{
			ignore |= m_Remainder.ignore() ;
		}
		return ignore ;
	}
}
