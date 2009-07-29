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
 * Created on 1 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.CICS;

import generate.CBaseLanguageExporter;
import semantic.CICS.CEntityCICSHandleAID;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSHandleAID extends CEntityCICSHandleAID
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaCICSHandleAID(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	protected void DoExport()
	{
		if (m_arrHandledAIDs.size() == 0 && m_arrUnhandledAIDs.size() == 0)
		{
			return ;
		}
		for (int i=0;i<m_arrHandledAIDs.size();i++)
		{
			WriteWord("CESM");
			String cond = m_arrHandledAIDs.elementAt(i);
			String label = m_arrHandledAIDLabels.elementAt(i);
			String cs = ".handleAID(\"" + cond + "\", " + FormatIdentifier(label) + ")" ;
			WriteWord(cs);
			WriteWord(" ;");
			WriteEOL() ;		
		}
		for (int i=0;i<m_arrUnhandledAIDs.size();i++)
		{
			WriteWord("CESM");
			String cond = m_arrUnhandledAIDs.elementAt(i);
			String cs = ".unhandleAID(\"" + cond + "\")" ;
			WriteWord(cs);
			WriteWord(" ;");
			WriteEOL() ;		
		}
	}

}
