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
public abstract class CEntityCICSReceiveMap extends CBaseActionEntity
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityCICSReceiveMap(int line, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity name)
	{
		super(line, cat, out);
		m_MapName = name ;
		cat.SendNotifRequest(new NotifDeclareUseCICSPreprocessor()) ;
	}
	
	public void SetMapSet(CDataEntity name)
	{
		m_MapSetName = name ;
	}
	public void SetDataInto(CDataEntity name)
	{
		m_DataInto = name ;
	}
	
	
	protected CDataEntity m_MapName = null ;
	protected CDataEntity m_MapSetName = null ;
	protected CDataEntity m_DataInto = null ;
	public void Clear()
	{
		super.Clear();
		m_MapName = null ;
		m_MapSetName = null ;
		m_DataInto = null ;
	}
	public boolean ignore()
	{
		return false; 
	}
}
