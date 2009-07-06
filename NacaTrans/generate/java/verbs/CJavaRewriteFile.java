/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityRewriteFile;
import utils.CObjectCatalog;

public class CJavaRewriteFile extends CEntityRewriteFile
{

	public CJavaRewriteFile(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	@Override
	protected void DoExport()
	{
		String cs = "";
		String csFile = "[UnknownReference]" ;
		if (m_eFileDescriptor != null)
		{
			csFile = m_eFileDescriptor.ExportReference(getLine()) ;
		}
		if (m_eDataFrom != null)
			cs = "rewriteFrom(" + csFile + ", " + m_eDataFrom.ExportReference(getLine()) + ") ;";
		else
			cs = "rewrite(" + csFile + ") ;";
		WriteLine(cs) ;
	}

}
