/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 6 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.CEntityProcedure;
import semantic.CEntityProcedureSection;
import semantic.Verbs.CEntityGoto;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaGoto extends CEntityGoto
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 * @param ref
	 */
	public CJavaGoto(int line, CObjectCatalog cat, CBaseLanguageExporter out, String ref, CEntityProcedureSection section)
	{
		super(line, cat, out, ref, section);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		CEntityProcedure e = m_Reference.getProcedure() ;
		String line = "goTo(" + e.ExportReference(getLine()) + ") ;" ;
		WriteLine(line) ;
	}

}
