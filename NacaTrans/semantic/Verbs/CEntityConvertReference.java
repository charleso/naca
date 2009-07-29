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
package semantic.Verbs;

import generate.CBaseLanguageExporter;
import semantic.CBaseDataReference;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id$
 */
public abstract class CEntityConvertReference extends CBaseDataReference
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityConvertReference(CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(0, "", cat, out);
	}

	/**
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	@Override
	protected void DoExport()
	{
		// unused
	}

	/**
	 * @see semantic.CDataEntity#GetDataType()
	 */
	@Override
	public CDataEntityType GetDataType()
	{
		return m_Reference.GetDataType() ;
	}
	

	/**
	 * @see semantic.CDataEntity#isValNeeded()
	 */
	@Override
	public boolean isValNeeded()
	{
		return false;
	}

	/**
	 * @see semantic.CDataEntity#GetConstantValue()
	 */
	@Override
	public String GetConstantValue()
	{
		return null;
	}

	/**
	 * @param buffer
	 */
	public void convertToPacked(CDataEntity buffer)
	{
		m_bConvertToPacked = true ;
		m_bConvertToAlphaNum = false ;
		m_Reference = buffer ;
	}	
	protected boolean m_bConvertToPacked = false ;
	protected boolean m_bConvertToAlphaNum = false ;
	
	public void convertToAlphaNum(CDataEntity working) {
		m_bConvertToAlphaNum = true ;
		m_bConvertToPacked = false ;
		m_Reference = working ;
	}
}
