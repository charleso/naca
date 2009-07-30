/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package semantic;

import generate.CBaseLanguageExporter;
import utils.CObjectCatalog;

public class CEntityFileSelect extends CBaseLanguageEntity
{
	public enum AccessMode
	{
		DYNAMIC,
		RANDOM,
		SEQUENTIAL
	}

	public enum OrganizationMode
	{
		INDEXED,
		SEQUENTIAL
	}

	public CEntityFileSelect(String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(0, name, cat, out);
	}

	@Override
	protected void RegisterMySelfToCatalog()
	{
		m_ProgramCatalog.RegisterFileSelect(this) ;
	}

	@Override
	protected void DoExport()
	{
		// nothing
	}

	public void setOrganizationMode(OrganizationMode eMode)
	{
		m_eOrganizationMode = eMode ;
	}
	protected OrganizationMode m_eOrganizationMode = null ;
	public void setAccessMode(AccessMode eMode)
	{
		m_eAccessmode = eMode ;	
	}
	protected AccessMode m_eAccessmode = null ;
	public void setFileName(CDataEntity fileName)
	{
		m_csFileName = fileName ;		
	}
	protected CDataEntity m_csFileName ;
	public CDataEntity GetFileName()
	{
		return m_csFileName ;
	}
	protected CDataEntity m_FileStatus;
	public void setFileStatus(CDataEntity fileStatus)
	{
		m_FileStatus = fileStatus;
	}
	public CDataEntity getFileStatus()
	{
		return m_FileStatus;
	}

}
