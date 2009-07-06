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
 * @version $Id: CGenericDataEntityReference.java,v 1.1 2006/03/24 15:53:54 U930CV Exp $
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
