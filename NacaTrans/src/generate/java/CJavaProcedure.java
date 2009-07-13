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
import semantic.CEntityProcedure;
import semantic.CEntityProcedureSection;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaProcedure extends CEntityProcedure
{

	/**
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CJavaProcedure(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, CEntityProcedureSection section)
	{
		super(l, name, cat, out, section);
	}

	protected void DoExport()
	{
		String label = FormatIdentifier(GetName());
		String line = "Paragraph " + label + " = new Paragraph(this);";
		WriteLine(line) ;
		WriteLine("public void "+label+"() {") ;
		StartOutputBloc() ;
		ExportChildren() ;
		EndOutputBloc();
		WriteLine("}") ;		
	}

	/* (non-Javadoc)
	 * @see semantic.CEntityProcedure#ExportReference(getLine())
	 */
	public String ExportReference(int nLine)
	{
		return FormatIdentifier(GetName());
	}

}
