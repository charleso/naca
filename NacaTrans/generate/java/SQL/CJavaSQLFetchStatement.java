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

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.SQL.CEntitySQLCursor;
import semantic.SQL.CEntitySQLFetchStatement;
import utils.CObjectCatalog;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSQLFetchStatement extends CEntitySQLFetchStatement
{
	public CJavaSQLFetchStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out, CEntitySQLCursor cur)
	{
		super(line, cat, out, cur);
	}
   
	protected void DoExport()
	{
		String s = "cursorFetch(" + m_Cursor.ExportReference(getLine()) + ")";
		WriteWord(s);
   
		for(int i=0; i<m_arrInto.size(); i++)
		{
			if(i != 0)
				WriteEOL();
			CDataEntity e = m_arrInto.elementAt(i);
			String cs = e.ExportReference(getLine());
			CDataEntity eInd = m_arrIndicators.elementAt(i) ;
			if (eInd != null)
			{
				cs += ", " + eInd.ExportReference(getLine()) ;
			}
			WriteWord(".into(" + cs + ")");
		}
		String csSQLErrorWarningStatement = m_ProgramCatalog.getSQLWarningErrorStatement();
		if(csSQLErrorWarningStatement != null)
		{
			WriteEOL();
			WriteWord(csSQLErrorWarningStatement);
		}
		WriteWord(" ;") ;
		WriteEOL();		
	}	
}
