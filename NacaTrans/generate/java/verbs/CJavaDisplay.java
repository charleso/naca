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
 * Created on 4 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.Verbs.CEntityDisplay;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaDisplay extends CEntityDisplay
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 * @param b
	 */
	public CJavaDisplay(int line, CObjectCatalog cat, CBaseLanguageExporter out, boolean b)
	{
		super(line, cat, out, b);
	}
	protected void DoExport()
	{
		String cs = "" ;
		if (m_bDisplyOnConsole)
		{
			cs = "console().display(" ;
		}
		else
		{
			cs = "display(";
		}
		boolean bValNeeded = false;
		for (int i=0; i<m_arrItemsToDisplay.size(); i++)
		{
			CDataEntity e = m_arrItemsToDisplay.get(i);
			if(i != 0)
				cs += " + "; 	
			
			String cs2 = ".display(" + e.ExportReference(getLine()) + ")" ;
			if(m_arrItemsToDisplay.size() > 1 && e.isValNeeded())
				cs += "val(" + e.ExportReference(getLine()) + ")";
			else
				cs += e.ExportReference(getLine());
			//cs += e.ExportAsDisplayItem(bValNeeded) ; 
			WriteWord(cs);
			cs = "" ;
		}
		WriteWord(") ;");
		WriteEOL();
	}

}
