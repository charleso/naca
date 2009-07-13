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

public abstract class CEntitySortReturn extends CBaseActionEntity
{

	public CEntitySortReturn(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	protected CEntityFileDescriptor m_eFileDesc = null ;
	public void setDataReference(CEntityFileDescriptor ref)
	{
		m_eFileDesc = ref ;	
	}
	protected CDataEntity m_eDataInto = null;
	public void setDataReference(CEntityFileDescriptor ref, CDataEntity into)
	{
		m_eDataInto = into ;
		m_eFileDesc = ref ;
	}
	
	protected CBaseLanguageEntity m_blocAtEnd = null ;
	protected CBaseLanguageEntity m_blocNotAtEnd = null ;
	
	public void SetAtEndBloc(CBaseLanguageEntity le)
	{
		m_blocAtEnd = le ;
	}

	public void SetNotAtEndBloc(CBaseLanguageEntity le)
	{
		m_blocNotAtEnd = le ;
	}

}
