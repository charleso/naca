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
public abstract class CGenericDataEntityReference extends CDataEntity
{

	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CGenericDataEntityReference(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, name, cat, out) ;
	}

	public abstract boolean ReplaceVariable(CDataEntity field, CDataEntity var, boolean bRead) ;
		
}
