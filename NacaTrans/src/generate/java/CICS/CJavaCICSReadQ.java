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
import semantic.CICS.CEntityCICSReadQ;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSReadQ extends CEntityCICSReadQ
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 * @param bPersistant
	 */
	public CJavaCICSReadQ(int line, CObjectCatalog cat, CBaseLanguageExporter out, boolean bPersistant)
	{
		super(line, cat, out, bPersistant);
	}

	protected void DoExport()
	{
		String title = "CESM.read" ;
//		if (m_bReadNext)
//		{
//			title += "Next" ;
//		}
		if (m_bPesistant)
		{
			title += "TransiantQueue(" ; 
		}
		else
		{
			title += "TempQueue(" ;
		}
		title += m_QueueName.ExportReference(getLine()) + ")";
		WriteWord(title) ;
		if (m_DataRef != null)
		{
			String cs = "" ;
			if (m_Item != null)
			{
				cs = ".itemInto(" + m_Item.ExportReference(getLine()) +", " ;
			}
			else
			{
				cs = ".nextInto(";
			}
			cs += m_DataRef.ExportReference(getLine()) ;
			if (m_DataLength != null)
			{
				cs += ", " + m_DataLength.ExportReference(getLine());
			}
			WriteWord(cs + ")") ;
		}
		if (m_NumItem != null)
		{
			WriteWord(".numItem(" + m_NumItem.ExportReference(getLine()) + ")");
		}
		WriteWord(" ;");
		WriteEOL() ;
	}

}
