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
package generate.java.CICS;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.CICS.CEntityCICSAssign;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSAssign extends CEntityCICSAssign
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaCICSAssign(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	protected void DoExport()
	{
		String disp = "CESM.assign()";
		for (int i=0; i<m_arrParameters.size(); i++)
		{
			String cs = m_arrParameters.elementAt(i);
			CDataEntity e = m_arrVariables.elementAt(i);
			disp += "." + cs + "(" + e.ExportReference(getLine()) + ")";
			WriteWord(disp);
			disp = "" ;			
		}
		WriteWord(" ;");
		WriteEOL() ;			
	}

}
