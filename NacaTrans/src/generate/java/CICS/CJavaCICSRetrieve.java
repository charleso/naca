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
import semantic.CICS.CEntityCICSRetrieve;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSRetrieve extends CEntityCICSRetrieve
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaCICSRetrieve(int line, CObjectCatalog cat, CBaseLanguageExporter out, boolean bPointer)
	{
		super(line, cat, out, bPointer);
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		String cs = ("CESM") ;
		if (m_bPointer)
		{
			cs += ".retrieveSet(" ;
		}
		else
		{	
			cs += ".retrieveInto(" ;
		}
		cs += m_refInto.ExportReference(getLine());
		if (m_dataLength != null)
		{
			cs += ", " + m_dataLength.ExportReference(getLine());
		}
		WriteLine(cs + ") ;");
	}
}
