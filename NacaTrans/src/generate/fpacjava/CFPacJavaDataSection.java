/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.CEntityDataSection;
import utils.CObjectCatalog;

public class CFPacJavaDataSection extends CEntityDataSection
{

	public CFPacJavaDataSection(int line, String name, CObjectCatalog cat,
					CBaseLanguageExporter out)
	{
		super(line, name, cat, out);
	}

	@Override
	protected void DoExport()
	{
		ExportChildren() ;
	}

}
