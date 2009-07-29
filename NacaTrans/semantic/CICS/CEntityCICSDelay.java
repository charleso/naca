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
public abstract class CEntityCICSDelay extends CBaseActionEntity
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityCICSDelay(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
		cat.SendNotifRequest(new NotifDeclareUseCICSPreprocessor()) ;
	}

	public void SetSeconds(CDataEntity entity)
	{
		m_Seconds = entity;		
	}

	public void SetInterval(CDataEntity entity)
	{
		m_Interval = entity ; 		
	}
	
	protected CDataEntity m_Interval = null ;
	protected CDataEntity m_Seconds = null ;

	public boolean ignore()
	{
		return false ;
	}
	public void Clear()
	{
		super.Clear();
		if (m_Interval != null)
		{
			m_Interval.Clear() ;
		}
		if (m_Seconds!=null)
		{
			m_Seconds.Clear() ;
			m_Seconds = null ;
		}
		m_Interval = null ;
	}
	
}
