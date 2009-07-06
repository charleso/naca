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
import semantic.CICS.CEntityCICSStart;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSStart extends CEntityCICSStart
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 * @param TID
	 */
	public CJavaCICSStart(int line, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity TID)
	{
		super(line, cat, out, TID);
	}
	protected void DoExport()
	{
		String tid ;
		if (m_bVerified)
		{
			tid = m_TransID.GetConstantValue() + ".class" ;
		}
		else
		{
			tid = m_TransID.ExportReference(getLine()) ;
		}
		WriteWord("CESM.start(" + tid + ")");
		if (m_Interval != null)
		{
			WriteWord(".interval(" + m_Interval.ExportReference(getLine()) + ")");
		}
		else if (m_Time != null)
		{
			WriteWord(".time(" + m_Time.ExportReference(getLine()) + ")");
		}
		
		if (m_TermID != null)
		{
			WriteWord(".termID(" + m_TermID.ExportReference(getLine()) + ")");
		}
		
		if (m_SysID != null)
		{
			WriteWord(".sysID(" + m_SysID.ExportReference(getLine()) + ")") ;
		}
		
		if (m_DataFrom!= null)
		{
			String cs = ".dataFrom(" + m_DataFrom.ExportReference(getLine());
			if (m_DataLength != null)
			{
				cs += ", " + m_DataLength.ExportReference(getLine());
			}
			WriteWord(cs + ")");
		}
		WriteWord(".doStart() ;");
		WriteEOL() ;
	}
}
