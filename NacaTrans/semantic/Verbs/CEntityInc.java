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
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id$
 */
public abstract class CEntityInc extends CBaseActionEntity
{

	protected CDataEntity m_AddDest;
	protected CDataEntity m_AddValue;

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityInc(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	/**
	 * @param dest
	 */
	public void SetAddDest(CDataEntity dest)
	{
		m_AddDest = dest ;		
	}

	/**
	 * @param val
	 */
	public void SetAddValue(CDataEntity val)
	{
		m_AddValue = val ;
	}


}
