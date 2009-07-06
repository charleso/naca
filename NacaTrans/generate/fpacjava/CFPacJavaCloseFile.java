/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityCloseFile;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id: CFPacJavaCloseFile.java,v 1.2 2007/06/28 06:19:46 u930bm Exp $
 */
public class CFPacJavaCloseFile extends CEntityCloseFile
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CFPacJavaCloseFile(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	/**
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	@Override
	protected void DoExport()
	{
		String cs = m_eFileDescriptor.ExportReference(getLine()) + ".close() ;" ;
		WriteLine(cs) ;
	}

}
