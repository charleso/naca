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
 * Created on Sep 27, 2004
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
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityCICSWrite extends CBaseActionEntity
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityCICSWrite(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
		cat.SendNotifRequest(new NotifDeclareUseCICSPreprocessor()) ;
	}
	public void WriteFile(CDataEntity name)
	{
		m_Name = name ;
		m_bWritetoDataSet = false ;
		m_bWriteToFile = true ;
	}
	public void WriteDataSet(CDataEntity name)
	{
		m_Name = name ;
		m_bWritetoDataSet = true ;
		m_bWriteToFile = false ;
	}
	public void SetDataFrom(CDataEntity from)
	{
		m_DataFrom = from ;
	}
	public void SetRecIDField(CDataEntity rec)
	{
		m_RecIDField = rec ;
	}
	
	protected CDataEntity m_RecIDField = null ;
	protected CDataEntity m_DataFrom = null ;
	protected CDataEntity m_Name ;
	protected boolean m_bWriteToFile = false ;
	protected boolean m_bWritetoDataSet = false ;
	public void Clear()
	{
		super.Clear();
		m_RecIDField = null ;
		m_DataFrom = null ;
		m_Name = null ;
	}
	public boolean ignore()
	{
		return false; 
	}
}
