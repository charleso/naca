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
public abstract class CEntityCICSLink extends CBaseActionEntity
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityCICSLink(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
		cat.SendNotifRequest(new NotifDeclareUseCICSPreprocessor()) ;
		cat.RegisterCICSLink(this);
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
	protected CDataEntity m_CommAreaDataLength = null ;
	
	public void SetCommArea(CDataEntity eCommArea, CDataEntity eCALength, CDataEntity eCADataLength)
	{
		m_refCommArea = eCommArea ;
		m_CommAreaLength = eCALength ;
		m_CommAreaDataLength = eCADataLength ;
	}
	public boolean ignore()
	{
		return false; 
	}
	public void Clear()
	{
		super.Clear();
		m_refProgram = null ;
		m_refCommArea = null ;
		m_CommAreaDataLength = null ;
		m_CommAreaLength = null ;
	}

	/**
	 * @return
	 */
	public CDataEntity GetProgramReference()
	{
		return m_refProgram ;
	}

	/**
	 * @return
	 */
	public CDataEntity GetCommareaParameter()
	{
		return m_refCommArea ;
	}

	/**
	 * @return
	 */
	public boolean isReferenceChecked()
	{
		return m_bChecked ;
	}
}
