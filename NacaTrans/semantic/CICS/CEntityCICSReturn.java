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
public abstract class CEntityCICSReturn extends CBaseActionEntity
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityCICSReturn(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
		cat.SendNotifRequest(new NotifDeclareUseCICSPreprocessor()) ;
	}
	public void SetTransID(CDataEntity TID, CDataEntity comma, CDataEntity comlen, boolean bChecked)
	{
		m_TransID = TID;
		m_CommArea = comma ;
		m_CommLenght = comlen ;
		m_bChecked = bChecked ;
	}
	
	protected boolean m_bChecked = false ;
	protected CDataEntity m_TransID = null ;
	protected CDataEntity m_CommArea = null ;
	protected CDataEntity m_CommLenght = null ;
	public void Clear()
	{
		super.Clear();
		m_TransID = null ;
		m_CommArea = null ;
		m_CommLenght = null ;
	}
	public boolean ignore()
	{
		return false; 
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_TransID == field)
		{
			m_TransID = var ;
			field.UnRegisterReadingAction(this) ;
			var.RegisterReadingAction(this) ;
			return true ;
		}
		else if (m_CommArea == field)
		{
			m_CommArea = var ;
			field.UnRegisterReadingAction(this) ;
			var.RegisterReadingAction(this) ;
			return true ;
		}
		return false ;
	}
	public boolean hasExplicitGetOut()
	{
		return true ;
	}
}
