/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityInspectConverting;
import utils.CObjectCatalog;

public class CJavaInspectConverting extends CEntityInspectConverting
{
	public CJavaInspectConverting(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	protected void DoExport()
	{
		WriteWord("inspectConverting(");
		WriteWord(m_Variable.ExportReference(getLine()));
		WriteWord(").to(");
		WriteWord(m_From.ExportReference(getLine()));
		WriteWord(",");
		WriteWord(m_To.ExportReference(getLine()));
		WriteWord(")");
		WriteWord(";");
		WriteEOL();		
	}

}
