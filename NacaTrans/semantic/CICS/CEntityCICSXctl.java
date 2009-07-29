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
public abstract class CEntityCICSXctl extends CBaseActionEntity
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityCICSXctl(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
		cat.SendNotifRequest(new NotifDeclareUseCICSPreprocessor()) ;
	}
	
	public void SetProgramName(CDataEntity prgm, boolean bChecked)
	{
		m_refProgram = prgm ;
		m_bChecked = bChecked ;
	}
	
	protected boolean m_bChecked = false ;
	protected CDataEntity m_refProgram = null ;
	protected CDataEntity m_refCommArea = null ;
	protected CDataEntity m_CommAreaLength = null ;
	public void Clear()
	{
		super.Clear();
		m_refCommArea = null ;
		m_refProgram = null ;
		m_CommAreaLength = null ;
	}
	//protected CBaseDataEntity m_CommAreaDataLength = null ;
	
	public void SetCommArea(CDataEntity eCommArea, CDataEntity eCALength)
	{
		m_refCommArea = eCommArea ;
		m_CommAreaLength = eCALength ;
		//m_CommAreaDataLength = eCADataLength ;
	}
	public boolean ignore()
	{
		return false; 
	}
	public boolean hasExplicitGetOut()
	{
		return true ;
	}
}
