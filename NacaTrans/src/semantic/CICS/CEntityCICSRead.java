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
public abstract class CEntityCICSRead extends CBaseActionEntity
{
	public static class CEntityCICSReadMode
	{
		public static CEntityCICSReadMode NORMAL = new CEntityCICSReadMode() ;
		public static CEntityCICSReadMode PREVIOUS = new CEntityCICSReadMode() ;
		public static CEntityCICSReadMode NEXT = new CEntityCICSReadMode() ;
	}
	public CEntityCICSRead(int line, CObjectCatalog cat, CBaseLanguageExporter out, CEntityCICSReadMode mode)
	{
		super(line, cat, out);
		m_Mode = mode ;
		cat.SendNotifRequest(new NotifDeclareUseCICSPreprocessor()) ;
	}
	public void ReadFile(CDataEntity name)
	{
		m_Name = name ;
		m_bReadtoDataSet = false ;
		m_bReadToFile = true ;
	}
	public void ReadDataSet(CDataEntity name)
	{
		m_Name = name ;
		m_bReadtoDataSet = true ;
		m_bReadToFile = false ;
	}
	public void SetDataInto(CDataEntity from, CDataEntity length)
	{
		m_DataInto = from ;
		m_DataLength = length;
	}
	public void SetRecIDField(CDataEntity rec)
	{
		m_RecIDField = rec ;
	}
	
	protected CDataEntity m_RecIDField = null ;
	protected CDataEntity m_DataInto = null ;
	protected CDataEntity m_DataLength = null ;
	protected CDataEntity m_Name ;
	protected boolean m_bReadToFile = false ;
	protected boolean m_bReadtoDataSet = false ;
	protected CEntityCICSReadMode m_Mode = null ;
	protected CDataEntity m_KeyLength = null ;
	protected boolean m_bEqual = false ;

	public void SetKeyLength(CDataEntity entity)
	{
		m_KeyLength = entity ;
	}

	public void SetEqual()
	{
		m_bEqual = true ;
	}
	public boolean ignore()
	{
		return false; 
	}
	public void Clear()
	{
		super.Clear();
		m_RecIDField = null ;
		m_DataInto = null ;
		m_DataLength = null ;
		m_Name = null ;
		m_KeyLength = null ;
		m_Mode = null ;
	}
}
