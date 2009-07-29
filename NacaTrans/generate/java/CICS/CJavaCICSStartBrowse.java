/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 1 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.CICS;

import generate.CBaseLanguageExporter;
import semantic.CICS.CEntityCICSStartBrowse;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSStartBrowse extends CEntityCICSStartBrowse
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaCICSStartBrowse(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	protected void DoExport()
	{
		String cs = "CESM.startBrowse" ;
		if (m_DataSet != null)
		{
			cs += "DataSet("  + m_DataSet.ExportReference(getLine()) + ")";
		}
		WriteWord(cs);
		if (m_RecIDField != null)
		{
			WriteWord(".recIDField(" + m_RecIDField.ExportReference(getLine()) + ")");
		}
		if (m_bGTEQ)
		{
			WriteWord(".gTEQ()");
		}
		WriteWord(" ;");
		WriteEOL() ;
	}

}
