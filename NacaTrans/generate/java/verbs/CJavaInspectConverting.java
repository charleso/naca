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
/**
 * 
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityInspectConverting;
import utils.CObjectCatalog;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class CJavaInspectConverting extends CEntityInspectConverting
{
	public CJavaInspectConverting(int nLine, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(nLine, cat, out);
	}
	
	protected void DoExport()
	{
		String cs = "inspectConverting(";
		cs += m_variable.ExportReference(getLine());
		cs += ", ";
		cs += m_from.ExportReference(getLine());
		cs += ", ";
		cs += m_to.ExportReference(getLine());
		cs += ");";		

		WriteWord(cs);
		WriteEOL();
	}
}

