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
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntitySort;
import utils.CObjectCatalog;

public class CJavaSort extends CEntitySort
{

	public CJavaSort(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	@Override
	protected void DoExport()
	{
		String cs = m_FileDescriptor.ExportReference(getLine()) ;
		WriteWord("sort("+cs+")") ;
		
		for (int i=0; i<m_arrSortKey.size(); i++) 
		{
			CEntitySortKey key = m_arrSortKey.get(i) ;
			if (key.m_bAscending)
			{
				cs = ".ascKey(" ;
			}
			else
			{
				cs = ".descKey(" ;
			}
			if (key.m_Key != null)
			{
				cs += key.m_Key.ExportReference(getLine()) ;
			}
			else
			{
				cs += "[Undefined]" ;
			}
			cs += ")" ;
			WriteWord(cs) ;
		}
		
		if (m_fdInputFile != null)
		{
			WriteWord(".using("+m_fdInputFile.ExportReference(getLine())+")") ;
		}
		else if (m_pInputProcedure != null)
		{
			WriteWord(".usingInput("+m_pInputProcedure.ExportReference(getLine())+")") ;
		}
		else if (m_csInputProcedureName != null)
		{
			m_pInputProcedure = m_ProgramCatalog.GetProcedure(m_csInputProcedureName, "") ;
			if (m_pInputProcedure != null)
			{
				WriteWord(".usingInput("+m_pInputProcedure.ExportReference(getLine())+")") ;
			}
			else
			{
				WriteWord(".usingInput(["+m_csInputProcedureName+"])") ;
			}
		}

		if (m_fdOutputFile != null)
		{
			WriteWord(".giving("+m_fdOutputFile.ExportReference(getLine())+")") ;
		}
		else if (m_pOutputProcedure != null)
		{
			WriteWord(".usingOutput("+m_pOutputProcedure.ExportReference(getLine())+")") ;
		}
		else if (m_csOutputProcedureName != null)
		{
			m_pOutputProcedure = m_ProgramCatalog.GetProcedure(m_csOutputProcedureName, "") ;
			if (m_pOutputProcedure != null)
			{
				WriteWord(".usingOutput("+m_pOutputProcedure.ExportReference(getLine())+")") ;
			}
			else
			{
				WriteWord(".usingOutput(["+m_csOutputProcedureName+"])") ;
			}
		}

		WriteWord(".exec() ;") ;
		WriteEOL() ;
	}

}
