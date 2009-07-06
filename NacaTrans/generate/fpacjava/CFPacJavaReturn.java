/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityReturn;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id: CFPacJavaReturn.java,v 1.1 2006/03/03 17:26:52 U930CV Exp $
 */
public class CFPacJavaReturn extends CEntityReturn
{

	/**
	 * @param l
	 * @param cat
	 * @param out
	 */
	public CFPacJavaReturn(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}

	/**
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	@Override
	protected void DoExport()
	{
		WriteLine("return NEXT ;") ;
	}

}
