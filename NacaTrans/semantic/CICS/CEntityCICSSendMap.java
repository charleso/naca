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
import semantic.forms.CEntityResourceForm;
import utils.CObjectCatalog;
import utils.CobolTranscoder.Notifs.NotifDeclareUseCICSPreprocessor;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityCICSSendMap extends CBaseActionEntity
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityCICSSendMap(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
		cat.SendNotifRequest(new NotifDeclareUseCICSPreprocessor()) ;
	}
	public void SetName(CDataEntity name)
	{
		m_MapName = name ;
	}
	public void SetMapSet(CDataEntity name)
	{
		m_MapSetName = name ;
	}
	
	public void SetDataFrom(CDataEntity from, CDataEntity len, boolean b)
	{
		if (from.GetDataType() == CDataEntity.CDataEntityType.FORM)
		{
			CEntityResourceForm form = (CEntityResourceForm)from ;
			if (form.getSaveCopy() != null)
			{
				form.UnRegisterReadingAction(this) ;
				m_DataFrom = form.getSaveCopy() ;
				m_DataFrom.RegisterReadingAction(this) ;
			}
			else
			{
				m_DataFrom = from ;
			}
		}
		else
		{
			m_DataFrom = from ;
		}
		m_DataLength = len ;
		m_bDataOnly = b ;
	}
	
	public void SetAccum(boolean b)
	{
		m_bAccum = b ;
	}
	
	public void SetAlarm(boolean b)
	{
		m_bAlarm = b ;
	}
	
	public void SetErase(boolean b)
	{
		m_bErase = b ;
	}
	
	public void SetFreeKB(boolean b)
	{
		m_bFreeKB = b ;
	}
	
	public void SetPaging(boolean b)
	{
		m_bPaging = b ;
	}
	
	public void SetWait(boolean b)
	{
		m_bWait = b ;
	}
	
	public void SetCursor(CDataEntity e)
	{
		m_bCursor = true ;
		m_CursorValue = e ;
	}
	
	protected CDataEntity m_MapName = null ;
	protected CDataEntity m_MapSetName = null ;
	protected CDataEntity m_DataFrom = null ;
	protected CDataEntity m_DataLength = null ;
	protected CDataEntity m_CursorValue = null ;
	protected boolean m_bFreeKB= false ;
	protected boolean m_bDataOnly = false ;
	protected boolean m_bCursor = false ;
	protected boolean m_bErase = false ;
	protected boolean m_bAlarm = false ; 
	protected boolean m_bWait = false ; 
	protected boolean m_bAccum = false ;
	protected boolean m_bPaging = false ;
	public void Clear()
	{
		super.Clear();
		m_MapName = null ;
		m_MapSetName = null ;
		m_DataFrom = null ;
		m_DataLength = null ;
		m_CursorValue = null ;
	}
	public boolean ignore()
	{
		return false; 
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_DataFrom == field)
		{
			field.UnRegisterReadingAction(this) ;
			m_DataFrom = var ;
			var.RegisterReadingAction(this) ;
			return true ;
		}
		return false ;
	}
	
}
