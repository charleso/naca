/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityCallFunction;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id: CFPacJavaCallFunction.java,v 1.2 2007/06/28 06:19:46 u930bm Exp $
 */
public class CFPacJavaCallFunction extends CEntityCallFunction
{

	/**
	 * @param l
	 * @param cat
	 * @param out
	 * @param ref
	 * @param refThru
	 * @param sectionContainer
	 */
	public CFPacJavaCallFunction(int l, CObjectCatalog cat, CBaseLanguageExporter out, String ref)
	{
		super(l, cat, out, ref, "", null);
	}

	/**
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	@Override
	protected void DoExport()
	{
		WriteLine(m_Reference.getProcedure().ExportReference(getLine()) + "() ;") ;

	}

}
