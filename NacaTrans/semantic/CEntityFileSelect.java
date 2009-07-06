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
	public void setFileName(String string)
	{
		m_csFileName = string ;		
	}
	protected String m_csFileName = "" ;
	public String GetFileName()
	{
		return m_csFileName ;
	}

}
