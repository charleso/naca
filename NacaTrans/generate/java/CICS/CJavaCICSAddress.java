/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Sep 27, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.CICS;

import generate.CBaseLanguageExporter;
import semantic.CICS.CEntityCICSAddress;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSAddress extends CEntityCICSAddress
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaCICSAddress(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		String cs = "CESM" ;
		if (m_RefCWA != null && !m_RefCWA.ignore())
		{
			cs += ".getAddressOfCWA(" + m_RefCWA.ExportReference(getLine()) + ")";
			WriteWord(cs);
			cs = "" ;
		}
		if (m_RefTCTUA != null && !m_RefTCTUA.ignore())
		{
			cs += ".getAddressOfTCTUA(" + m_RefTCTUA.ExportReference(getLine()) + ")";
			WriteWord(cs);
			cs = "" ;
		}
		if (m_RefTWA != null && !m_RefTWA.ignore())
		{
			cs += ".getAddressOfTWA(" + m_RefTWA.ExportReference(getLine()) + ")";
			WriteWord(cs);
			cs = "" ;
		}
		WriteWord(" ;");
		WriteEOL() ;		
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#ignore()
	 */
	public boolean ignore()
	{
		boolean ignore = true ;
		if (m_RefCWA != null)
		{
			ignore &= m_RefCWA.ignore() ;
		}
		if (m_RefTCTUA != null)
		{
			ignore &= m_RefTCTUA.ignore() ;
		}
		if (m_RefTWA != null)
		{
			ignore &= m_RefTWA.ignore() ;
		}
		return ignore ;
	}
}
