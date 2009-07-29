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
 * Created on 1 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.CICS;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;
import utils.CobolTranscoder.Notifs.NotifDeclareUseCICSPreprocessor;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityCICSStartBrowse extends CBaseActionEntity
{
	protected boolean m_bGTEQ = false ;
	protected CDataEntity m_DataSet = null ;
	protected CDataEntity m_RecIDField = null ;
	protected CDataEntity m_KeyLength = null ;
	public void Clear()
	{
		super.Clear();
		m_DataSet = null ;
		m_RecIDField = null ;
		m_KeyLength = null ;
	}

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityCICSStartBrowse(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
		cat.SendNotifRequest(new NotifDeclareUseCICSPreprocessor()) ;
	}
	public void SetGTEQ()
	{
		m_bGTEQ = true ;		
	}
	public void BrowseDataSet(CDataEntity entity)
	{
		m_DataSet = entity ;
	}
	public void SetRecIDField(CDataEntity entity)
	{
		m_RecIDField = entity ;
	}
	public boolean ignore()
	{
		return false; 
	}
	/**
	 * @param entity
	 */
	public void SetKeyLength(CDataEntity entity)
	{
		m_KeyLength = entity ;
	}
}
