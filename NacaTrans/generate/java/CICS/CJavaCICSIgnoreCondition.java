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
 * Created on Sep 29, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.CICS;

import generate.CBaseLanguageExporter;
import semantic.CICS.CEntityCICSIgnoreCondition;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSIgnoreCondition extends CEntityCICSIgnoreCondition
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaCICSIgnoreCondition(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	protected void DoExport()
	{
		WriteWord("CESM");
		for (int i=0;i<m_arrConditions.size();i++)
		{
			String cond = m_arrConditions.elementAt(i);
			String cs = ".ignoreCondition(\"" + cond + "\")" ;
			WriteWord(cs);
		}
		WriteWord(" ;");
		WriteEOL() ;		
	}
}
