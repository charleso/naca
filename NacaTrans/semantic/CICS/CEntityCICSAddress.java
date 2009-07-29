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
public abstract class CEntityCICSAddress extends CBaseActionEntity
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityCICSAddress(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
		cat.SendNotifRequest(new NotifDeclareUseCICSPreprocessor()) ;
	}
	public void SetRefForCWA(CDataEntity e)
	{
		m_RefCWA = e ;
	}
	public void SetRefForTCTUA(CDataEntity e)
	{
		m_RefTCTUA = e ;
	} 
	public void SetRefForTWA(CDataEntity e)
	{
		m_RefTWA = e ;
	}
	
	protected CDataEntity m_RefCWA = null;
	protected CDataEntity m_RefTCTUA = null;
	protected CDataEntity m_RefTWA = null;
	public void Clear()
	{
		super.Clear();
		m_RefCWA = null ;
		m_RefTCTUA = null ;
		m_RefTWA = null ;
	}
}
