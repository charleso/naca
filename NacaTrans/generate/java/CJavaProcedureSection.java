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
import semantic.CEntityProcedureSection;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaProcedureSection extends CEntityProcedureSection
{

	/**
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CJavaProcedureSection(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, name, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseSemanticEntity#DoExport()
	 */
	protected void DoExport()
	{
		String label = FormatIdentifier(GetName()) ;
		if (m_bReducedToProcedure)
		{
			if (m_SectionBloc != null)
			{
				String line = "Paragraph " + label + " = new Paragraph(this);" ;
				WriteLine(line) ;
				WriteLine("public void "+label+"() {") ;
				DoExport(m_SectionBloc) ;
				WriteLine("}", 0) ;
			}
		}
		else
		{
			if (m_SectionBloc != null)
			{
				String line = "Section " + label + " = new Section(this);" ;
				WriteLine(line) ;
				WriteLine("public void "+label+"() {") ;
				DoExport(m_SectionBloc) ;
				WriteLine("}", 0) ;
			}
			else
			{
				String line = "Section " + label + " = new Section(this, false);" ;
				WriteLine(line) ;
			}
		}
		ExportChildren() ;		
	}

	/* (non-Javadoc)
	 * @see semantic.CEntityProcedure#ExportReference(getLine())
	 */
	public String ExportReference(int nLine)
	{
		return FormatIdentifier(GetName()) ;
	}
}
