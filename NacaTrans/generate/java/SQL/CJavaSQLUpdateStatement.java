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
import semantic.CDataEntity.CDataEntityType;
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
	public CJavaSQLUpdateStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out, String csStatement, Vector<CDataEntity> arrSets, Vector<CDataEntity> arrSetsIndicators, Vector<CDataEntity> arrParameters, Vector<CDataEntity> arrParametersIndicators)
	{
		super(line, cat, out, csStatement, arrSets, arrSetsIndicators, arrParameters, arrParametersIndicators);
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
			WriteWord("cursorUpdateCurrent(");
			WriteWord(m_Cursor.ExportReference(getLine())+ ", ");
			WriteLongString(m_csStatement.trim());
			WriteWord(")");
		}
		for(int i=0; i<m_arrSets.size(); i++)
		{
			WriteEOL();
			CDataEntity e = m_arrSets.elementAt(i);
			CDataEntityType dataType = e.GetDataType();
			if (dataType == CDataEntity.CDataEntityType.NUMBER || dataType == CDataEntity.CDataEntityType.STRING)
			{
				int gg = 0;
			}
			else if(dataType == CDataEntity.CDataEntityType.SQL_FUNCTION)
			{
				int gg2 = 0;
			}
			else
			{
				String csOut = ".value("+(i+1) +", " + e.ExportReference(getLine());
				if (i<m_arrSetsIndicators.size())
				{
					CDataEntity eIndicator = m_arrSetsIndicators.elementAt(i);
					if (eIndicator != null)
					{
						csOut += ", "+eIndicator.ExportReference(getLine()) ;
					}
				}
				csOut += ")";
				WriteWord(csOut);
			}
		}
		int nIndexOffset = m_arrSets.size();
		
		for(int i=0; i<m_arrParameters.size(); i++)
		{
			CDataEntity e = m_arrParameters.elementAt(i);
			if (e != null)
			{
				CDataEntityType dataType = e.GetDataType();
				if (dataType == CDataEntity.CDataEntityType.NUMBER || dataType == CDataEntity.CDataEntityType.STRING )
				{
					int gg = 0;
				}
				else if(dataType == CDataEntity.CDataEntityType.SQL_FUNCTION)
				{
					int gg2 = 0;
				}				
				else
				{
					WriteEOL();
					String csOut = ".param("+(i+1+nIndexOffset) + ", " + e.ExportReference(getLine());
					
					if (i<m_arrParametersIndicators.size())
					{
						CDataEntity eIndicator = m_arrParametersIndicators.elementAt(i);
						if (eIndicator != null)
						{
							csOut += ", "+eIndicator.ExportReference(getLine()) ;
						}
					}
					csOut += ")";
					WriteWord(csOut);
				}
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
	
	private void exportExtractedSQL()
	{
		SQLDumper sqlDumper = getSQLDumper();
		if(sqlDumper == null)
			return ;
		
		sqlDumper.startStatement(getLine());
		sqlDumper.incNbUpdate();
		
		if (m_Cursor == null)
		{
			sqlDumper.append("m_sql = sql(\"");
			sqlDumper.append(m_csStatement.trim());
			sqlDumper.append("\")");
		}
		else
		{
			sqlDumper.append("cursorUpdateCurrent(");
			sqlDumper.append(m_Cursor.ExportReference(getLine())+ ", ");
			sqlDumper.append("\"" + m_csStatement.trim() + "\"");
			sqlDumper.append(")");
		}
		for(int i=0; i<m_arrSets.size(); i++)
		{
			sqlDumper.setNbValues(m_arrSets.size());
			sqlDumper.appendLineFeed();
			CDataEntity e = m_arrSets.elementAt(i);
			sqlDumper.append("\t.value("+(i+1) +", " + "varValue" + i + ")");
		}
		sqlDumper.setNbParam(m_arrParameters.size());
		for(int i=0; i<m_arrParameters.size(); i++)
		{
			CDataEntity e = m_arrParameters.elementAt(i);
			if (e != null)
			{
				sqlDumper.appendLineFeed();
				sqlDumper.append("\t.param("+(i+1+m_arrSets.size()) + ", " + "varParam" + i + ")");
			}
		}
		String csSQLErrorWarningStatement = m_ProgramCatalog.getSQLWarningErrorStatement();
		if(csSQLErrorWarningStatement != null)
		{
			sqlDumper.appendLineFeed();
			sqlDumper.append(csSQLErrorWarningStatement);
		}
		sqlDumper.append(";") ;
		sqlDumper.append("\n\t\thandleSqlStatus();") ;
		sqlDumper.appendLineFeed();
	}	

}

