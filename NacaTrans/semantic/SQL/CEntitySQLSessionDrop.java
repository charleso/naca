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
import utils.CObjectCatalog;

public abstract class CEntitySQLSessionDrop extends CBaseActionEntity
{
	protected String m_csSql = "" ;
	
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntitySQLSessionDrop(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	/**
	 * @param string
	 */
	public void setSql(String string)
	{
		m_csSql = string ;
	}
}
