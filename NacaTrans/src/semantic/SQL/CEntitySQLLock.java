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

/**
 * @author S. Charton
 * @version $Id: CEntitySQLLock.java,v 1.1 2006/03/01 22:47:49 U930CV Exp $
 */
public abstract class CEntitySQLLock extends CBaseActionEntity
{

	protected String m_csTableName = "" ;
	
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntitySQLLock(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	/**
	 * @param string
	 */
	public void setTable(String string)
	{
		m_csTableName = string ;
	}


}
