/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Oct 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java;

import generate.CBaseLanguageExporter;
import semantic.CEntityIndex;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaIndex extends CEntityIndex
{
	/**
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CJavaIndex(String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(name, cat, out);
	}

	public String ExportReference(int nLine)
	{
		String cs = "" ;
		if (m_Of != null)
		{
			cs = m_Of.ExportReference(getLine()) +".";
		}
		cs += FormatIdentifier(GetName());
		return cs ;
	}
	public boolean isValNeeded()
	{
		return false;
	}


	protected void DoExport()
	{
		WriteLine("Var " + FormatIdentifier(GetName()) + " = declare.index() ;");
	}
}
