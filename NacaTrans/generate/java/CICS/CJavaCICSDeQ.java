/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 6 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.CICS;

import generate.CBaseLanguageExporter;
import semantic.CICS.CEntityCICSDeQ;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSDeQ extends CEntityCICSDeQ
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaCICSDeQ(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	protected void DoExport()
	{
		String cs = "CESM.deQ(" + m_Resource.ExportReference(getLine());
		if (m_Length != null)
		{
			cs += ", " + m_Length.ExportReference(getLine());
		}
		cs+= ") ;";
		WriteLine(cs) ;		
	}

}
