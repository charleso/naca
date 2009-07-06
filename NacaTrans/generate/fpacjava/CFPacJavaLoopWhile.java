/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityLoopWhile;
import utils.CObjectCatalog;

public class CFPacJavaLoopWhile extends CEntityLoopWhile
{

	public CFPacJavaLoopWhile(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	@Override
	protected void DoExport()
	{
		if (m_bDoBefore)
		{
			WriteLine("do {") ;
			ExportChildren() ;
			WriteLine("}");
			WriteLine("while (" + m_WhileCondition.Export() + ") ;");
		}
		else
		{
			WriteLine("while ("+ m_WhileCondition.Export() + ") {");
			ExportChildren() ;
			WriteLine("}");
		}
	}

}
