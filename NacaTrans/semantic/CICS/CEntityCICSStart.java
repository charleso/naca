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
public abstract class CEntityCICSStart extends CBaseActionEntity
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityCICSStart(int line, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity TID)
	{
		super(line, cat, out);
		m_TransID = TID ;
		cat.SendNotifRequest(new NotifDeclareUseCICSPreprocessor()) ;
	}
	
	public void SetInterval(CDataEntity inter)
	{
		m_Interval = inter ;
	}
	public void SetTime(CDataEntity time)
	{
		m_Time = time ;
	}
	public void SetDataFrom(CDataEntity from, CDataEntity len)
	{
		m_DataFrom = from ;
		m_DataLength = len ;
	}
	public void SetSysID(CDataEntity sys)
	{
		m_SysID = sys ;
	}
	public void SetTermID(CDataEntity term)
	{
		m_TermID = term ;
	}
	
	protected CDataEntity m_TransID = null ;
	protected CDataEntity m_TermID = null ;
	protected CDataEntity m_SysID = null ;
	
	protected CDataEntity m_Interval = null ;
	protected CDataEntity m_Time = null ;
	
	protected CDataEntity m_DataFrom = null ;
	protected CDataEntity m_DataLength = null ;
	public void Clear()
	{
		super.Clear();
		m_TransID = null ;
		m_TermID = null ;
		m_SysID = null ;
		m_Interval = null ;
		m_Time = null ;
		m_DataFrom = null ;
		m_DataLength = null ;
	}
	public boolean ignore()
	{
		return false; 
	}

	/**
	 * @param checked
	 */
	public void setVerified(boolean checked)
	{
		m_bVerified = checked ;
	}
	protected boolean m_bVerified = false ;
}
