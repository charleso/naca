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
 * Created on 20 août 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.SQL;

import java.util.Vector;

import generate.CBaseLanguageExporter;
import generate.SQLDumper;
import semantic.CDataEntity;
import semantic.SQL.CEntitySQLDeleteStatement;
import utils.CObjectCatalog;
/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSQLDeleteStatement extends CEntitySQLDeleteStatement
{
	public CJavaSQLDeleteStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out, String csStatement, Vector<CDataEntity> arrParameters)
	{
		super(line, cat, out, csStatement, arrParameters);
	}
	
	protected void DoExport()
	{
		exportExtractedSQL();
	
		if (m_Cursor == null)
		{
			WriteWord("sql(");
			WriteLongString(m_csStatement.trim());
			WriteWord(")");
		}
		else
		{
			WriteWord("cursorDeleteCurrent(");
			WriteWord(m_Cursor.ExportReference(getLine())+ ", ");
			WriteLongString(m_csStatement.trim());
			WriteWord(")");
		}
		for(int i=0; i<m_arrParameters.size(); i++)
		{
			CDataEntity e = m_arrParameters.elementAt(i);
			if (e != null)
			{
				WriteEOL();
				WriteWord(".param("+(i+1)+", " + e.ExportReference(getLine())+ ")");
			}
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
	
	private void exportExtractedSQL()
	{
		SQLDumper sqlDumper = getSQLDumper();
		if(sqlDumper == null)
			return ;
		
		sqlDumper.startStatement(getLine());
		sqlDumper.incNbDelete();

		if (m_Cursor == null)
		{
			sqlDumper.append("m_sql = sql(\"");
			sqlDumper.append(m_csStatement.trim());
			sqlDumper.append("\")");
		}
		else
		{
			sqlDumper.append("cursorDeleteCurrent(");
			sqlDumper.append(m_Cursor.ExportReference(getLine())+ ", ");
			sqlDumper.append(m_csStatement.trim());
			sqlDumper.append(")");
		}
		sqlDumper.setNbParam(m_arrParameters.size());
		for(int i=0; i<m_arrParameters.size(); i++)
		{
			CDataEntity e = m_arrParameters.elementAt(i);
			if (e != null)
			{
				sqlDumper.appendLineFeed();
				sqlDumper.append("\t.param("+(i+1)+", " + "varParam" + i + ")");
			}
		}
		String csSQLErrorWarningStatement = m_ProgramCatalog.getSQLWarningErrorStatement();
		if(csSQLErrorWarningStatement != null)
		{
			sqlDumper.appendLineFeed();
			sqlDumper.append(csSQLErrorWarningStatement);
		}
		sqlDumper.append(" ;") ;
		sqlDumper.append("\n\t\thandleSqlStatus();") ;
		sqlDumper.appendLineFeed();
	}	
	


}
