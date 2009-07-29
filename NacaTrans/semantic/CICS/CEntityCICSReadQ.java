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
public abstract class CEntityCICSReadQ extends CBaseActionEntity
{

	public CEntityCICSReadQ(int line, CObjectCatalog cat, CBaseLanguageExporter out, boolean bPersistant)
	{
		super(line, cat, out);
		m_bPesistant = bPersistant ;
		cat.SendNotifRequest(new NotifDeclareUseCICSPreprocessor()) ;
	}

	protected boolean m_bPesistant = false ;
	protected CDataEntity m_QueueName = null ;
	protected CDataEntity m_DataRef = null ;
	protected CDataEntity m_DataLength = null ;
	protected boolean m_bReadNext = false ;
	protected CDataEntity m_NumItem = null ;
	protected CDataEntity m_Item = null ;

	public void Clear()
	{
		super.Clear();
		m_QueueName = null ;
		m_DataRef = null ;
		m_DataLength = null ;
		m_NumItem = null;
		m_Item = null;
	}

	public void SetName(CDataEntity entity)
	{
		m_QueueName = entity ;		
	}
	public void SetDataRef(CDataEntity entity, CDataEntity len)
	{
		m_DataRef = entity ;
		m_DataLength = len ;
	}

	public void ReadNext()
	{
		m_bReadNext = true ; 		
	}

	public void ReadNumItem(CDataEntity entity)
	{
		m_NumItem = entity ;		
	}

	public void ReadItem(CDataEntity entity)
	{
		m_Item = entity ;
	}
	public boolean ignore()
	{
		return false; 
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_DataRef == field)
		{
			m_DataRef = var ;
			field.UnRegisterReadingAction(this) ;
			var.RegisterReadingAction(this) ;
			return true ;
		}
		return false ;
	}
}
