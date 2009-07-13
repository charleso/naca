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
 * @version $Id: CEntitySQLExecute.java,v 1.1 2006/03/01 22:47:49 U930CV Exp $
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
