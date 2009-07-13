/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityReadFile;
import utils.CObjectCatalog;

public class CFPacJavaReadFile extends CEntityReadFile
{

	public CFPacJavaReadFile(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	@Override
	protected void DoExport()
	{
		String cs = m_eFileDescriptor.ExportReference(getLine()) + ".read()" ;
		if (m_eAtEndBloc != null)
		{
			WriteLine("if ("+cs+".atEnd()) {") ;
			DoExport(m_eAtEndBloc) ;
			WriteLine("}") ;
		}
		else
		{
			WriteLine(cs + " ;") ;
		}
	}

}
