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
/*/*
 * Created on 20 août 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.SQL;
import java.util.ArrayList;

import parser.Cobol.elements.SQL.CSQLTableColDescriptor;

import jlib.misc.StringUtil;
import generate.CBaseLanguageExporter;
import generate.SQLDumper;
import semantic.CDataEntity;
import semantic.CDataEntity.CDataEntityType;
import semantic.SQL.CEntitySQLInsertStatement;
import utils.CObjectCatalog;
import utils.Transcoder;

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
		exportExtractedSQL();
		
//		ArrayList<String> arrColNames = new ArrayList<String>();
//		String csTableName = null;
		
		String statement = "INSERT INTO " ;
		if (m_bSessionTable)
		{
			statement += "SESSION.";
		}
		if (m_table != null)
		{
			//csTableName = m_table.GetTableName();
			statement += m_table.GetTableName() + " (" + m_table.ExportColReferences() + ")";
			//m_table.fillArrayColNames(arrColNames);
		}
		else
		{
			//csTableName = m_csTable;
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
					String csColName = m_arrCollumns.elementAt(i);					
					statement += csColName;
					//arrColNames.add(csColName);
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
				CDataEntityType dataType = e.GetDataType();
				if (dataType == CDataEntity.CDataEntityType.NUMBER || dataType == CDataEntity.CDataEntityType.STRING)
				{
					statement += e.ExportReference(getLine()).replace('"', '\'') ; 
				}
				else if(dataType == CDataEntity.CDataEntityType.SQL_FUNCTION)
				{
					String cs = e.ExportReference(getLine()) ;
					statement += cs;
				}
				else if (dataType == CDataEntity.CDataEntityType.SQL_FUNCTION_WITH_PARAMETER)
				{	
					String cs = e.ExportReference(getLine()) ;
					String csMarker = "#"+(i+1) ;
					cs = StringUtil.replace(cs, "?", csMarker, true);
					statement += cs;
				}
				else
				{ 
					String csMarker = "#"+(i+1);
//					String csColName = arrColNames.get(i);
//					CSQLTableColDescriptor colDescriptor = Transcoder.getTableColumnDescription(csTableName, csColName);
//					if(colDescriptor != null)
//					{
//						String csType = colDescriptor.GetType();
//						csType = csType.toUpperCase();
//						if(csType.equals("DATE"))
//							csMarker = "TO_DATE(" + csMarker + ", 'YYYY.MM.DD')";
//						else if(csType.startsWith("TIMESTAMP"))
//							csMarker = "TO_TIMESTAMP(" + csMarker + ", 'YYYY-MM-DD-HH24.MI.SS.FF6')";
//					}
					statement += csMarker;
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
				CDataEntityType dataType = e.GetDataType();
				if (dataType == CDataEntity.CDataEntityType.NUMBER || dataType == CDataEntity.CDataEntityType.STRING  || dataType == CDataEntity.CDataEntityType.SQL_FUNCTION)
				{
				}
				else if (dataType == CDataEntity.CDataEntityType.SQL_FUNCTION_WITH_PARAMETER)
				{
					WriteEOL();
					String csName = e.GetConstantValue();
					String cs = ".value("+(i+1)+", "+ csName + ")";
					WriteWord(cs);
				}
				else
				{
					WriteEOL();
					String cs = ".value("+(i+1)+", "+ e.ExportReference(getLine());
					if(m_arrIndicators != null)
					{
						if(m_bSingleIndicatorInOccurs)
						{
							CDataEntity eIndicator = (CDataEntity)m_arrIndicators.get(0);
							if(eIndicator != null)
							{
								int i1Based = i + 1;
								cs += ", " + eIndicator.ExportReference(getLine()) + ".getAt(" + i1Based + ")";
							}
						}
						else if(i < m_arrIndicators.size())
						{
							CDataEntity eIndicator = (CDataEntity)m_arrIndicators.get(i);
							if(eIndicator != null)
							{
								cs += ", " + eIndicator.ExportReference(getLine());
							}
						}
					}
					
					cs += ")";
					WriteWord(cs);

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
	
	private void exportExtractedSQL()
	{
		SQLDumper sqlDumper = getSQLDumper();
		if(sqlDumper == null)
			return ;
		
		sqlDumper.startStatement(getLine());
		sqlDumper.incNbInsert();
		
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
				if(e == null)
				{
					int gg = 0;
				}
				CDataEntityType dataType = e.GetDataType();
				if (dataType == CDataEntity.CDataEntityType.NUMBER || dataType == CDataEntity.CDataEntityType.STRING)
				{
					statement += e.ExportReference(getLine()).replace('"', '\'') ; 
				}
				else if (dataType == CDataEntity.CDataEntityType.SQL_FUNCTION)
				{
					String cs = e.ExportReference(getLine()) ;
					statement += cs;
				}
				else if (dataType == CDataEntity.CDataEntityType.SQL_FUNCTION_WITH_PARAMETER)
				{	
					String cs = e.ExportReference(getLine()) ;
					String csMarker = "#"+(i+1) ;
					cs = StringUtil.replace(cs, "?", csMarker, true);
					statement += cs;
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
		
		sqlDumper.append("m_sql = sql(\"");
		sqlDumper.append(statement);
		sqlDumper.append("\")");
		if (m_arrValues != null)
		{
			sqlDumper.setNbValues(m_arrValues.size());
			for (int i=0; i<m_arrValues.size(); i++)
			{
				CDataEntity e = (CDataEntity)m_arrValues.get(i);
				CDataEntityType dataType = e.GetDataType();
				if (dataType == CDataEntity.CDataEntityType.NUMBER || dataType == CDataEntity.CDataEntityType.STRING || dataType == CDataEntity.CDataEntityType.SQL_FUNCTION)
				{
				}
				else
				{
					sqlDumper.appendLineFeed();
					sqlDumper.append("\t.value("+(i+1)+", "+ "varValue" + i +")");
				}
			}
		}
		if (m_arrSelectParameters != null)
		{
			sqlDumper.setNbValues(m_arrSelectParameters.size());
			for(int i=0; i<m_arrSelectParameters.size(); i++)
			{
				CDataEntity cs = (CDataEntity)m_arrSelectParameters.elementAt(i);
				if (cs != null)
				{
					sqlDumper.appendLineFeed();
					sqlDumper.append("\t.value("+ (i+1) + ", " + "varValue" + i + ")");
				}
			}
		}
		sqlDumper.append(" ;");
		sqlDumper.append("\n\t\thandleSqlStatus();") ;
		sqlDumper.appendLineFeed();
	}

}
