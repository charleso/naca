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
import semantic.CICS.CEntityCICSWriteQ;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSWriteQ extends CEntityCICSWriteQ
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 * @param bPersistant
	 */
	public CJavaCICSWriteQ(int line,CObjectCatalog cat, CBaseLanguageExporter out, boolean bPersistant)
	{
		super(line, cat, out, bPersistant);
	}

	protected void DoExport()
	{
		String cs = "CESM.write" ;
		if (m_bPersistant)
		{
			cs += "TransiantQueue(" ; 
		}
		else
		{
			cs += "TempQueue(" ;
		}
		//WriteWord(title) ;
		cs += m_QueueName.ExportReference(getLine());
		if (m_bRewrite && m_Item != null)
		{
			cs += ", " + m_Item.ExportReference(getLine()) ;
		}
		cs += ")" ;
		WriteWord(cs) ;
		if (m_DataRef != null)
		{
			cs = ".from(" + m_DataRef.ExportReference(getLine());
			if (m_DataLength != null)
			{
				cs += ", " + m_DataLength.ExportReference(getLine()) ;
			}
			cs += ")" ;
			WriteWord(cs);
			if (m_Item != null && !m_bRewrite)
			{
				WriteWord(".item(" + m_Item.ExportReference(getLine()) + ")");
			}
		}
//		if (m_NumItem != null)
//		{
//			WriteWord(".writeNumItem(" + m_NumItem.ExportReference(getLine()) + ")");
//		}
//		if (m_bAuxiliary)
//		{
//			WriteWord(".auxiliary()");
//		}
//		if (m_bMain)
//		{
//			WriteWord(".main()");
//		}
//		if (m_bRewrite)
//		{
//			WriteWord(".rewrite()");
//		}
		WriteWord(" ;");
		WriteEOL() ;
	}

}
