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
import semantic.CICS.CEntityCICSInquire;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSInquire extends CEntityCICSInquire
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaCICSInquire(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	protected void DoExport()
	{
		if (m_Transaction != null && m_Program != null)
		{
			String cs = "tools.getProgramForTransID(" + m_Transaction.ExportReference(getLine()) + 
						", " + m_Program.ExportReference(getLine()) + ") ;" ;
			WriteLine(cs) ;
		}
		else
		{
			String cs = "CESM.inquire(";
			if (m_Program != null)
			{
				cs += ").program("  + m_Program.ExportReference(getLine());
			}
			if (m_Transaction != null)
			{
				cs += ").transaction(" + m_Transaction.ExportReference(getLine());
			}
			cs += (") ;");
			WriteLine(cs);
		}
	}
}
