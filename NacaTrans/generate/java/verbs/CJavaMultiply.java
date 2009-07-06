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
import semantic.Verbs.CEntityMultiply;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaMultiply extends CEntityMultiply
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaMultiply(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	protected void DoExport()
	{
		String cs = "" ;
		cs = "multiply(" ;
		cs += m_What.ExportReference(getLine()) + ", " + m_By.ExportReference(getLine()) + ")" ;
		WriteWord(cs);
		if (m_To != null)
		{
			WriteWord(".to");
			if (m_bIsRounded)
			{
				WriteWord("Rounded");
			}
			
			WriteWord("(" + m_To.ExportReference(getLine()) + ")" );
		}
		WriteWord(";");
		WriteEOL();
	}
}
