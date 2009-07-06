/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.CEntityProcedure;
import semantic.CEntityProcedureSection;
import utils.CObjectCatalog;

public class CFPacJavaProcedure extends CEntityProcedure
{

	public CFPacJavaProcedure(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, CEntityProcedureSection section)
	{
		super(l, name, cat, out, section);
	}

	@Override
	public String ExportReference(int nLine)
	{
		return FormatIdentifier(GetName());
	}

	@Override
	protected void DoExport()
	{
		String cs = "protected int " + FormatIdentifier(GetName()) + "() {" ;
		WriteLine(cs) ;
		StartOutputBloc() ;
		
		ExportChildren() ;
		if (!this.hasExplicitGetOut())
		{
			WriteLine("return NEXT ;") ;
		}
		
		EndOutputBloc() ;
		WriteLine("}", m_nEndLine) ;
	}

}
