/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityOpenFile;
import utils.CObjectCatalog;

public class CJavaOpenFile extends CEntityOpenFile
{

	public CJavaOpenFile(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	@Override
	protected void DoExport()
	{
		String cs ;
		switch (m_eMode)
		{
			case APPEND:
				cs = ".openExtend() ;" ;
				break ;
			case INPUT:
				cs = ".openInput() ;" ;
				break ;
			case INPUT_OUTPUT:
				cs = ".openInputOutput() ;" ;
				break ;
			case OUTPUT:
				cs = ".openOutput() ;" ;
				break ;
			default:
				cs = ".open() ;" ;
			break ;
		}
		WriteLine(m_eFileDescriptor.ExportReference(getLine()) + cs) ;
	}

}
