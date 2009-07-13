/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityWriteFile;
import utils.CObjectCatalog;

public class CFPacJavaWriteFile extends CEntityWriteFile
{

	public CFPacJavaWriteFile(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	@Override
	protected void DoExport()
	{
		String cs = m_eFileDescriptor.ExportReference(getLine()) + ".write() ;" ;
		WriteLine(cs) ;
	}

}
