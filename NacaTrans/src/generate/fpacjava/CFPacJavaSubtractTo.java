/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.Verbs.CEntitySubtractTo;
import utils.CObjectCatalog;

public class CFPacJavaSubtractTo extends CEntitySubtractTo
{

	public CFPacJavaSubtractTo(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	@Override
	protected void DoExport() {
		WriteWord("subtract(") ;
		WriteWord(this.m_Variable.ExportReference(getLine())) ;
		for(CDataEntity m_Value : m_Values)
		{
			WriteWord(", ") ;
			WriteWord(m_Value.ExportReference(getLine())) ;
		}
		WriteWord(").to(") ;
		WriteWord(this.m_Destination.ExportReference(getLine())) ;		
		WriteWord(") ;") ;
		WriteEOL() ;
	}
}
