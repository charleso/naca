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
 * Created on Sep 27, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.CICS;

import generate.CBaseLanguageExporter;
import semantic.CICS.CEntityCICSXctl;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSXctl extends CEntityCICSXctl
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaCICSXctl(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		String name = m_refProgram.ExportReference(getLine());
		if (name.startsWith("\"") && m_bChecked)
		{
			name = name.subSequence(1, name.length()-1) + ".class";	
		}
		WriteWord("CESM.xctl(" + name + ")");
		if (m_refCommArea != null)
		{
			String cs = ".commarea(" + m_refCommArea.ExportReference(getLine());
			if (m_CommAreaLength != null)
			{
				cs += ", " + m_CommAreaLength.ExportReference(getLine()); 
			}
			WriteWord(cs + ")");
		}
		else
		{
			WriteWord(".go()");
		}
		WriteWord(" ;");
		WriteEOL();
	}
}
