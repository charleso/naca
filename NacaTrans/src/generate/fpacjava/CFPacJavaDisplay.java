/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.Verbs.CEntityDisplay;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id: CFPacJavaDisplay.java,v 1.2 2007/06/28 06:19:46 u930bm Exp $
 */
public class CFPacJavaDisplay extends CEntityDisplay
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 * @param b
	 */
	public CFPacJavaDisplay(int line, CObjectCatalog cat, CBaseLanguageExporter out, Upon t)
	{
		super(line, cat, out, t);
	}

	/**
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	@Override
	protected void DoExport()
	{
		for (CDataEntity e : m_arrItemsToDisplay)
		{
			WriteLine("wto.display(" + e.ExportReference(getLine()) + ") ;") ;
		}
	}

}
