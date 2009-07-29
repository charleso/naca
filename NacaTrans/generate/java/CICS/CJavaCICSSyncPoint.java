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
import semantic.CICS.CEntityCICSSyncPoint;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSSyncPoint extends CEntityCICSSyncPoint
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 * @param bRollback
	 */
	public CJavaCICSSyncPoint(int line, CObjectCatalog cat, CBaseLanguageExporter out, boolean bRollback)
	{
		super(line, cat, out, bRollback);
	}

	protected void DoExport()
	{
		if (m_bRollback)
		{
			WriteLine("CESM.syncPointRollback() ;");
		}
		else
		{
			WriteLine("CESM.syncPointCommit() ;");
		}
	}
}
