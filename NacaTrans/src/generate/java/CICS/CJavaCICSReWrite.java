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
import semantic.CICS.CEntityCICSReWrite;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSReWrite extends CEntityCICSReWrite
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaCICSReWrite(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	protected void DoExport()
	{
		String cs = "CESM.reWrite";
		if (m_bWritetoDataSet)
		{
			cs += "DataSet(";
		}
		else if (m_bWriteToFile)
		{
			cs += "File(";
		}
		cs += m_Name.ExportReference(getLine()) + ")" ;
		WriteWord(cs);
		if (m_DataFrom != null)
		{
			cs = ".from(" + m_DataFrom.ExportReference(getLine());
			if (m_DataLength != null)
			{
				cs += ", " + m_DataLength.ExportReference(getLine());
			}
			WriteWord(cs + ")");
		}
		WriteWord(";");
		WriteEOL();		
	}

}
