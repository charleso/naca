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
import semantic.CICS.CEntityCICSSendMap;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSSendMap extends CEntityCICSSendMap
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaCICSSendMap(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	protected void DoExport()
	{
		WriteWord("CESM.sendMap(" + m_MapName.ExportReference(getLine()) + ")");
		if (m_MapSetName != null)
		{
			WriteWord(".mapSet(" + m_MapSetName.ExportReference(getLine()) + ")");
		}
		if (m_DataFrom != null)
		{
			String cs = "";
			if (m_bDataOnly)
			{
				cs = ".dataOnlyFrom(";
			}
			else
			{
				cs = ".dataFrom(";
			}
			cs += m_DataFrom.ExportReference(getLine());
			if (m_DataLength != null)
			{
				cs += ", " + m_DataLength.ExportReference(getLine()) ;
			}
			cs += ")" ;
			WriteWord(cs);
		}
		if (m_bCursor)
		{
			String cs = ".cursor(";
			if (m_CursorValue != null)
			{
				cs += m_CursorValue.ExportReference(getLine());
			}
			WriteWord(cs+")");
		}
		if (m_bAccum)
		{
			WriteWord(".accum()");
		}
		if (m_bAlarm)
		{
			WriteWord(".alarm()");
		}
		if (m_bErase)
		{
			WriteWord(".erase()");
		}
		if (m_bFreeKB)
		{
			WriteWord(".freeKB()");
		}
		if (m_bPaging)
		{
			WriteWord(".paging()");
		}
		if (m_bWait)
		{
			WriteWord(".wait()");
		}
		WriteWord(" ;");
		WriteEOL() ;
	}
}
