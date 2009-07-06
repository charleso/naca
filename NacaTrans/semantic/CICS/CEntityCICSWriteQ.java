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
public abstract class CEntityCICSWriteQ extends CBaseActionEntity
{

	public CEntityCICSWriteQ(int line, CObjectCatalog cat, CBaseLanguageExporter out, boolean bPersistant)
	{
		super(line, cat, out);
		m_bPersistant = bPersistant ;
		cat.SendNotifRequest(new NotifDeclareUseCICSPreprocessor()) ;
	}

	protected boolean m_bPersistant = false ;
	protected CDataEntity m_QueueName = null ;
	protected CDataEntity m_DataRef = null ;
	protected CDataEntity m_DataLength = null ;
	protected CDataEntity m_NumItem = null ;
	protected CDataEntity m_Item = null ;
	protected boolean m_bAuxiliary = false ;
	protected boolean m_bMain = false ;
	protected boolean m_bRewrite = false ;	
	public void Clear()
	{
		super.Clear();
		m_QueueName = null ;
		m_DataLength = null ;
		m_DataRef = null ;
		m_NumItem = null ;
		m_Item = null ;
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

	public void WriteNumItem(CDataEntity entity)
	{
		m_NumItem = entity ;		
	}

	public void WriteItem(CDataEntity entity)
	{
		m_Item = entity ;
	}

	public void SetRewrite()
	{
		m_bRewrite = true ;
	}

	public void SetMain()
	{
		m_bMain = true ;		
	}

	public void SetAuxiliary()
	{
		m_bAuxiliary = true ; 		
	}
	public boolean ignore()
	{
		return false; 
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseActionEntity#ReplaceVariable(semantic.CDataEntity, semantic.CDataEntity)
	 */
	@Override
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_DataRef == field)
		{
			m_DataRef = var ;
			field.UnRegisterWritingAction(this) ;
			var.RegisterWritingAction(this) ;
			return true ;
		}
		return false ;
	}
}
