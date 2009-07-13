/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package semantic.Verbs;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.CEntityFileDescriptor;
import utils.CObjectCatalog;

public abstract class CEntityReadFile extends CBaseActionEntity
{

	public CEntityReadFile(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	public void setFileDescriptor(CEntityFileDescriptor efd, CDataEntity data)
	{
		m_eFileDescriptor = efd ;
		m_eDataInto = data ;
	}
	protected CEntityFileDescriptor m_eFileDescriptor = null  ;
	protected CDataEntity m_eDataInto = null ;
	
	public void SetAtEndBloc(CBaseLanguageEntity bloc)
	{
		m_eAtEndBloc = bloc ;
	}
	protected CBaseLanguageEntity m_eAtEndBloc = null ;
	protected CBaseLanguageEntity m_eNotAtEndBloc = null ;

	public void SetNotAtEndBloc(CBaseLanguageEntity bloc)
	{
		m_eNotAtEndBloc = bloc ;
	}


}
