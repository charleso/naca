/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 19 août 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.SQL;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import generate.CBaseLanguageExporter;
import semantic.SQL.CEntitySQLCloseStatement;
import semantic.SQL.CEntitySQLCursor;
import utils.CObjectCatalog;


/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSQLCloseStatement extends CEntitySQLCloseStatement
{
	public CJavaSQLCloseStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out, CEntitySQLCursor csCursorName)
	{
		super(line, cat, out, csCursorName);
	}
   
	protected void DoExport()
	{
		String s = "cursorClose(" + m_Cursor.ExportReference(getLine()) + ")";
		WriteWord(s);
		String csSQLErrorWarningStatement = m_ProgramCatalog.getSQLWarningErrorStatement();
		if(csSQLErrorWarningStatement != null)
		{
			WriteWord(csSQLErrorWarningStatement);
		}
		WriteWord(" ;") ;
		WriteEOL() ;
   }	
}
