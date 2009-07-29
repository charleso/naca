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
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityOpenFile;
import utils.CObjectCatalog;

public class CFpacJavaOpenFile extends CEntityOpenFile
{

	public CFpacJavaOpenFile(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	@Override
	protected void DoExport()
	{
		String cs = m_eFileDescriptor.ExportReference(getLine()) + ".open" ;
		switch (m_eFileDescriptor.getAccessMode())
		{
			case APPEND:
				cs += "Extend()" ;
				break;
			case INPUT:
				cs += "Input()" ;
				break;
			case INPUT_OUTPUT:
				cs += "InputOutput()" ;
				break ;
			case OUTPUT:
				cs += "Output()" ;
				break ;
		}
		if (m_eFileDescriptor.isRecordSizeVariable())
		{
			cs += ".variableLength()" ;
		}
		cs += " ;" ;
		WriteLine(cs) ;
	}

}
