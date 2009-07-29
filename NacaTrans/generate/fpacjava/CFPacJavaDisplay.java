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
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.Verbs.CEntityDisplay;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id$
 */
public class CFPacJavaDisplay extends CEntityDisplay
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 * @param b
	 */
	public CFPacJavaDisplay(int line, CObjectCatalog cat, CBaseLanguageExporter out, boolean b)
	{
		super(line, cat, out, b);
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
