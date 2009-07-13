/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 3 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java;

import generate.CBaseLanguageExporter;
import semantic.CEntityExternalDataStructure;
import utils.CObjectCatalog;
import utils.CobolNameUtil;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaExternalDataStructure extends CEntityExternalDataStructure
{

	/**
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CJavaExternalDataStructure(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, name, cat, out);
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#ExportReference(semantic.CBaseExporter)
	 */

	public String ExportReference(int nLine)
	{
		return FormatIdentifier(GetDisplayName()) ;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseSemanticEntity#DoExport()
	 */
	protected void DoExport()
	{
		if (!m_bInline)
		{
			String name = m_csClassName.replace('-', '_') ;
			name = CobolNameUtil.fixJavaName(name);
			WriteEOL() ;
			WriteLine("import nacaLib.program.* ;") ;
			WriteLine("import nacaLib.varEx.* ;") ;
			WriteLine("import nacaLib.basePrgEnv.* ;") ;
			WriteEOL() ;
			WriteLine("public class " +  name + " extends Copy {") ;
			StartOutputBloc();
	
			WriteLine("") ;
			WriteLine("public static " +  name + " Copy(BaseProgram program) {") ;
			StartOutputBloc();
			WriteLine("return new " +  name + "(program, null);") ;
			EndOutputBloc();
			WriteLine("}") ;
			WriteLine("") ;
			WriteLine("") ;
			WriteLine("public static " +  name + " Copy(BaseProgram program, CopyReplacing copyReplacing) {") ;
			StartOutputBloc();
			WriteLine("return new " +  name + "(program, copyReplacing);") ;
			EndOutputBloc();
			WriteLine("}") ;
			WriteLine("") ;
			WriteLine("public " + name + "(BaseProgram program, CopyReplacing copyReplacing) {") ;
			StartOutputBloc();
			WriteLine("super(program, copyReplacing);") ;
			EndOutputBloc();
			WriteLine("}") ;
			WriteLine("") ;
	
			ExportChildren() ;
			EndOutputBloc();
			WriteLine("}") ;
		}
		else
		{
			ExportChildren() ;
		}
	}
	public boolean HasAccessors()
	{
		return false;
	}
	public String ExportWriteAccessorTo(String value)
	{
		// unsued
		return "" ;
	}
	
	public boolean isValNeeded()
	{
		return false;
	}


	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetDataType()
	 */
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.EXTERNAL_REFERENCE ;
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseExternalEntity#GetTypeDecl()
	 */
	public String GetTypeDecl()
	{
		return m_csClassName.replace('-', '_');
	}
}
 