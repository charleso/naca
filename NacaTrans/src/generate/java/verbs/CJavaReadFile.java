/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityReadFile;
import utils.CObjectCatalog;

public class CJavaReadFile extends CEntityReadFile
{

	public CJavaReadFile(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	@Override
	protected void DoExport()
	{
		//String cs = m_eFileDescriptor.ExportReference(getLine()) + ".read" ;
		String cs = "";
		if (m_eDataInto != null)
			cs = "readInto(" + m_eFileDescriptor.ExportReference(getLine()) + ", " + m_eDataInto.ExportReference(getLine()) + ")";
		else
			cs = "read(" + m_eFileDescriptor.ExportReference(getLine()) + ")";
		//WriteLine(cs) ;
		if (m_eAtEndBloc != null)
		{
			WriteLine("if (" + cs + ".atEnd()) {");
			DoExport(m_eAtEndBloc) ;
			if (m_eNotAtEndBloc != null)
			{
				WriteLine("} else {", m_eNotAtEndBloc.getLine()-1) ;
				DoExport(m_eNotAtEndBloc) ;
				WriteLine("}") ;
			}
			else
			{
				WriteLine("}") ;
			}
		}
		else if (m_eNotAtEndBloc != null)
		{
			WriteLine("if (!" + cs + ".atEnd()) {") ;
			DoExport(m_eNotAtEndBloc) ;
			WriteLine("}") ;
		}
		else
			WriteLine(cs + " ;");
	}

}
