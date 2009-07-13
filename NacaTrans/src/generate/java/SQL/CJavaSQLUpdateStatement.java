/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 20 août 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.SQL;
import java.util.Vector;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.SQL.CEntitySQLUpdateStatement;
import utils.CObjectCatalog;


/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSQLUpdateStatement extends CEntitySQLUpdateStatement
{
	public CJavaSQLUpdateStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out, String csStatement, Vector<CDataEntity> arrSets, Vector<CDataEntity> arrParameters)
	{
		super(line, cat, out, csStatement, arrSets, arrParameters);
	}
	
	protected void DoExport()
	{
		if (m_Cursor == null)
		{
			WriteWord("sql(");
			WriteLongString(m_csStatement.trim());
			WriteWord(")");
		}
		else
		{
			WriteWord("cursorUpdateCurrent(");
			WriteWord(m_Cursor.ExportReference(getLine())+ ", ");
			WriteLongString(m_csStatement.trim());
			WriteWord(")");
		}
		for(int i=0; i<m_arrSets.size(); i++)
		{
			WriteEOL();
			CDataEntity e = m_arrSets.elementAt(i);
			WriteWord(".value("+(i+1) +", " + e.ExportReference(getLine())+ ")");
		}
		for(int i=0; i<m_arrParameters.size(); i++)
		{
			CDataEntity e = m_arrParameters.elementAt(i);
			if (e != null)
			{
				WriteEOL();
				WriteWord(".param("+(i+1+m_arrSets.size()) + ", " + e.ExportReference(getLine())+ ")");
			}
		}
		String csSQLErrorWarningStatement = m_ProgramCatalog.getSQLWarningErrorStatement();
		if(csSQLErrorWarningStatement != null)
		{
			WriteEOL();
			WriteWord(csSQLErrorWarningStatement);
		}
		WriteWord(";") ;
		WriteEOL();
	}	

}

