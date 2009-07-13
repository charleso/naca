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
public abstract class CEntityCICSReWrite extends CBaseActionEntity
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityCICSReWrite(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
		cat.SendNotifRequest(new NotifDeclareUseCICSPreprocessor()) ;
	}

	public void WriteFile(CDataEntity filename)
	{
		m_bWriteToFile = true ;
		m_bWritetoDataSet = false ;
		m_Name = filename;
	}

	public void WriteDataSet(CDataEntity filename)
	{
		m_bWritetoDataSet = true ;
		m_bWriteToFile = false ;
		m_Name = filename;
	}

	public void SetDataFrom(CDataEntity edata, CDataEntity eLen)
	{
		m_DataFrom = edata ;
		m_DataLength = eLen ;		
	}
	
	protected CDataEntity m_DataLength = null ;
	protected CDataEntity m_DataFrom = null ;
	protected CDataEntity m_Name ;
	protected boolean m_bWriteToFile = false ;
	protected boolean m_bWritetoDataSet = false ;
	public void Clear()
	{
		super.Clear();
		m_DataLength = null ;
		m_DataFrom = null;
		m_Name = null ;
	}
	public boolean ignore()
	{
		return false; 
	}

}
