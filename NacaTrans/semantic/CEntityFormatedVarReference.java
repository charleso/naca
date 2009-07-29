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
package semantic;

import generate.CBaseLanguageExporter;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id$
 */
public abstract class CEntityFormatedVarReference extends CBaseDataReference
{
	
	protected String m_csFormat = "" ;

	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntityFormatedVarReference(CDataEntity object, CObjectCatalog cat, CBaseLanguageExporter out, String format)
	{
		super(0, "", cat, out);
		m_csFormat = format ;
		m_Reference = object ;
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


}
