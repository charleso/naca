/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Aug 10, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.SQL;

import generate.CBaseLanguageExporter;
import semantic.SQL.CEntitySQLSingleStatement;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSQLSingleStatement extends CEntitySQLSingleStatement
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 * @param st
	 */
	public CJavaSQLSingleStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out, String st)
	{
		super(line, cat, out, st);
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		WriteLine("getDBConnection().execSQL(\"" + m_csStatement + "\") ;") ;
	}
}
