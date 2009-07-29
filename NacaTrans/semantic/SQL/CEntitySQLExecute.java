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
package semantic.SQL;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id$
 */
public abstract class CEntitySQLExecute extends CBaseActionEntity
{
	
	protected CDataEntity m_eVariable = null  ;
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntitySQLExecute(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	/**
	 * @param var
	 */
	public void setVar(CDataEntity var)
	{
		m_eVariable = var ;
	}
}
