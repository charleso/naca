/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 2 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java;

import generate.*;
import semantic.*;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaClass extends CEntityClass
{

	/**
	 * @param name
	 * @param cat
	 */
	public CJavaClass(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, name, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseSemanticEntity#DoExport()
	 */
	protected void DoExport()
	{
		String name = GetName().replace('-', '_').toUpperCase();
		//WriteLine("package example.output ;") ;
		WriteEOL() ;
		for (int i=0; i<m_ProgramCatalog.getNbImportDeclaration(); i++)
		{
			String cs = m_ProgramCatalog.getImportDeclaration(i) ;
			if (cs.equals("MAP"))
			{
				WriteLine("import nacaLib.mapSupport.* ;", 0) ;
			}
			else if (cs.equals("SQL"))
			{
				WriteLine("import nacaLib.sqlSupport.* ;", 0) ;
			}
			else if (cs.equals("KEYPRESSED"))
			{
				WriteLine("import nacaLib.misc.KeyPressed;", 0) ;
			}
		}
		WriteLine("import nacaLib.program.* ;", 0) ;
		WriteLine("import nacaLib.varEx.* ;", 0) ;
		WriteEOL() ;
		
		CTransApplicationGroup.EProgramType eProgType = m_ProgramCatalog.getProgramType() ;
		String csProgType = "" ;
		switch(eProgType) 
		{
			case TYPE_BATCH:
				WriteLine("import nacaLib.batchPrgEnv.BatchProgram;", 0) ;
				csProgType = "BatchProgram" ;
				break;
			case TYPE_CALLED:
				WriteLine("import nacaLib.callPrg.CalledProgram;", 0) ;
				csProgType = "CalledProgram" ;
				break;
			case TYPE_INCLUDED:
				csProgType = "Copy" ;
				break ;
			case TYPE_MAP:
				csProgType = "Map" ;
				break ;
			case TYPE_ONLINE:
				WriteLine("import idea.onlinePrgEnv.OnlineProgram;", 0) ;
				csProgType = "OnlineProgram" ;
				break;
		}
		String line = "public class " + name + " extends " + csProgType ;
		WriteLine(line);
		WriteLine("{") ;
		StartOutputBloc();
//		WriteLine("public " + name + "(ProgramArgument programArgument)") ;
//		WriteLine("{") ;
//		StartOutputBloc();
//		WriteLine("super(programArgument);") ;
//		EndOutputBloc();
//		WriteLine("}") ;

		// children
		ExportChildren() ;

		EndOutputBloc();
		WriteLine("}") ;
		
	}

}
