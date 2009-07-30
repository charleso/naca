/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 5 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityReturn;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaReturn extends CEntityReturn
{

	/**
	 * @param cat
	 * @param out
	 */
	public CJavaReturn(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		if (m_bStopAllStackCalls >= 0)
		{
			WriteLine("stopRun(" + m_bStopAllStackCalls +  ");") ;
		}
		else if (m_bonlyLeaveParagraph)
		{
			WriteLine("return ;") ;
		}
		else
		{
			WriteLine("exitProgram();") ;
		}
	}

}
