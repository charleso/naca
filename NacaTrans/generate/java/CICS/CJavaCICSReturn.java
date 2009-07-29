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
import semantic.CICS.CEntityCICSReturn;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSReturn extends CEntityCICSReturn
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaCICSReturn(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	protected void DoExport()
	{
		String cs = "CESM.returnTrans(";
		if (m_TransID != null)
		{
			String tid ;
			if (m_bChecked)
			{
				tid = m_TransID.GetConstantValue() + ".class" ;
			}
			else
			{
				tid = m_TransID.ExportReference(getLine()) ;
			}
			cs += tid ;
			if (m_CommArea != null)
			{
				cs += ", " + m_CommArea.ExportReference(getLine());
				if (m_CommLenght != null)
				{
					cs += ", " + m_CommLenght.ExportReference(getLine());
				}
			}
		}
		cs += (") ;");
		WriteLine(cs) ;
	}
}
