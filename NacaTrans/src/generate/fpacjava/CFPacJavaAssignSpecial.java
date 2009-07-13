/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityAssignSpecial;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id: CFPacJavaAssignSpecial.java,v 1.2 2007/06/28 06:19:46 u930bm Exp $
 */
public class CFPacJavaAssignSpecial extends CEntityAssignSpecial
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CFPacJavaAssignSpecial(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	/**
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	@Override
	protected void DoExport()
	{
		if (arithmeticAssign)
		{
			WriteLine("movePacked("+ source.ExportReference(getLine()) + ", "+destination.ExportReference(getLine())+") ;") ;
		}

	}

}
