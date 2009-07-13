/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 6 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.CICS;

import generate.CBaseLanguageExporter;
import semantic.CICS.CEntityCICSSetTDQueue;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSSetTDQueue extends CEntityCICSSetTDQueue
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaCICSSetTDQueue(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	protected void DoExport()
	{
		if (m_bClosed)
		{
			WriteLine("CESM.setTDQueueClosed(" + m_QueueName.ExportReference(getLine()) + ") ;");
		}
		else if (m_bOpen)
		{
			WriteLine("CESM.setTDQueueOpen(" + m_QueueName.ExportReference(getLine()) + ") ;");
		}
	}

}
