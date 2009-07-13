/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.CEntityClass;
import utils.CObjectCatalog;

public class CFPacJavaClass extends CEntityClass
{

	public CFPacJavaClass(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, name, cat, out);
	}

	@Override
	protected void DoExport()
	{
		String name = GetName().replace('-', '_').toUpperCase();
		WriteEOL() ;

		WriteLine("import nacaLib.fpacPrgEnv.* ;", 0) ;
//		WriteLine("import nacaLib.batchPrgEnv.* ;", 0) ;
		WriteEOL() ;
		
		String line = "public class " + name + " extends FPacProgram" ;
		WriteLine(line);
		WriteLine("{") ;
		StartOutputBloc();

//		WriteLine("public "+name+"(BatchProgramManagerFactory batchProgramManagerFactory) {");
//		StartOutputBloc() ;
//		WriteLine("super(batchProgramManagerFactory);");
//		EndOutputBloc() ;
//		WriteLine("}") ;
		
		ExportChildren() ;

		EndOutputBloc();
		WriteLine("}") ;
		
	}

}
