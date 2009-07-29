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
import semantic.CDataEntity;
import semantic.CICS.CEntityCICSReceiveMap;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSReceiveMap extends CEntityCICSReceiveMap
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 * @param name
	 */
	public CJavaCICSReceiveMap(int line, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity name)
	{
		super(line, cat, out, name);
	}

	protected void DoExport()
	{
		WriteWord("CESM.receiveMap("+ m_MapName.ExportReference(getLine()) + ")");
		WriteWord(".mapSet(" + m_MapSetName.ExportReference(getLine()) + ")");
		WriteWord(".into(" + m_DataInto.ExportReference(getLine()) + ") ;");
		WriteEOL();
	}
}
