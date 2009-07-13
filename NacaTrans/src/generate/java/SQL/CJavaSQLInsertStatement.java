/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*/*
 * Created on 20 août 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.SQL;
import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.SQL.CEntitySQLInsertStatement;
import utils.CObjectCatalog;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSQLInsertStatement extends CEntitySQLInsertStatement
{
	public CJavaSQLInsertStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	
	protected void DoExport()
	{
		String statement = "INSERT INTO " ;
		if (m_bSessionTable)
		{
			statement += "SESSION.";
		}
		if (m_table != null)
		{
			statement += m_table.GetTableName() + " (" + m_table.ExportColReferences() + ")";
		}
		else
		{
			statement += m_csTable;
			if (m_arrCollumns != null)
			{
				statement += " (" ; 
				for (int i=0; i<m_arrCollumns.size(); i++)
				{
					if (i>0)
					{
						statement += ", " ;
					}
					statement += m_arrCollumns.elementAt(i) ;
				}
				statement += ")" ;
			}
		}
		if (m_SelectClause.equals(""))
		{
			statement += " VALUES (" ;
			for (int i=0; i<m_arrValues.size(); i++)
			{
				CDataEntity e = (CDataEntity)m_arrValues.get(i);
				if (i>0)
				{
					statement += ", " ;
				}
				if (e.GetDataType() == CDataEntity.CDataEntityType.NUMBER || e.GetDataType() == CDataEntity.CDataEntityType.STRING)
				{
					statement += e.ExportReference(getLine()).replace('"', '\'') ; 
				}
				else
				{ 
					statement += "#"+(i+1) ;
				}
			}
			statement += ")" ;
		}
		else
		{
			statement += " " + m_SelectClause ;
		}
		WriteWord("sql(");
		WriteLongString(statement);
		WriteWord(")");
		if (m_arrValues != null)
		{
			for (int i=0; i<m_arrValues.size(); i++)
			{
				CDataEntity e = (CDataEntity)m_arrValues.get(i);
				if (e.GetDataType() == CDataEntity.CDataEntityType.NUMBER || e.GetDataType() == CDataEntity.CDataEntityType.STRING)
				{
				}
				else
				{
					WriteEOL();
					WriteWord(".value("+(i+1)+", "+ e.ExportReference(getLine()) +")");
				}
			}
		}
		if (m_arrSelectParameters != null)
		{
			for(int i=0; i<m_arrSelectParameters.size(); i++)
			{
				CDataEntity cs = (CDataEntity)m_arrSelectParameters.elementAt(i);
				if (cs != null)
				{
					WriteEOL();
					WriteWord(".value("+ (i+1) + ", " + cs.ExportReference(getLine()) + ")");
				}
			}
		}
		String csSQLErrorWarningStatement = m_ProgramCatalog.getSQLWarningErrorStatement();
		if(csSQLErrorWarningStatement != null)
		{
			WriteEOL();
			WriteWord(csSQLErrorWarningStatement);
		}
		WriteWord(" ;");
		WriteEOL();
	}
}
