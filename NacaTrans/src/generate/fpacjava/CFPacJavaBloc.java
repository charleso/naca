/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.CEntityBloc;
import utils.CObjectCatalog;

public class CFPacJavaBloc extends CEntityBloc
{

	public CFPacJavaBloc(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}

	@Override
	protected void DoExport()
	{
		StartOutputBloc() ;
		ExportChildren();
		EndOutputBloc() ;
	}

}
