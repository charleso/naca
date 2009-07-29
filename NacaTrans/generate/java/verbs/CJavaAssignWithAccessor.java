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
 * Created on 11 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityAssignWithAccessor;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaAssignWithAccessor extends CEntityAssignWithAccessor
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaAssignWithAccessor(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	protected void DoExport()
	{
		String val = "" ;
		if (m_Value != null)
		{
			val = m_Value.ExportReference(getLine()) ;
		}
		String out = m_Reference.ExportWriteAccessorTo(val) ;
		if (m_bFillAll)
		{
			out = out.replaceFirst("([^\\(]*)(\\(.*)", "$1All$2") ;
		}
		WriteLine(out);

	}
}
