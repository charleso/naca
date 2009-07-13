/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 1 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityDivide;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaDivide extends CEntityDivide
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaDivide(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	protected void DoExport()
	{
		String cs = "" ;
		cs = "divide(" ;
		cs += m_What.ExportReference(getLine()) + ", ";
		cs += m_By.ExportReference(getLine()) + ")" ;
		WriteWord(cs);
		if (m_bIsRounded)
		{
			cs = ".toRounded(" + m_Result.ExportReference(getLine()) ;
		}
		else
		{
			cs = ".to(" + m_Result.ExportReference(getLine()) ;
		}
		if (m_Remainder != null)
		{
			cs += ", " + m_Remainder.ExportReference(getLine());
		}
		cs += (") ;");
		WriteWord(cs);
		WriteEOL();
	}

}
