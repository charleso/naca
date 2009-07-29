/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.sqlSupport;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.print.attribute.HashAttributeSet;

import jlib.misc.ArrayFixDyn;
import jlib.misc.StringUtil;
import jlib.sql.BaseDbColDefinition;
import jlib.sql.BaseDbColDefinitionFactory;
import jlib.sql.DbConnectionBase;
import jlib.sql.OracleColumnDefinition;
import jlib.sql.OracleTableDefinition;
import jlib.sql.OracleTableDefinitionManager;
import jlib.sql.SQLColumnType;

public class PreparedUpdateStmtColumnTypeManager extends PreparedStmtColumnTypeManager
{
	private String m_csTableName = null;
	private Hashtable<String, String> m_hashColNames = null;	// ColName=#x; key is x, value is ColName
	
	PreparedUpdateStmtColumnTypeManager(String csQueryUpper)
	{		
		super(csQueryUpper); 
	}
	
	boolean analyse(ArrayFixDyn<String> arrMarkerNames)
	{
		m_csTableName = extractTableName();
		if(m_csTableName != null)
		{
			m_hashColNames = extractSetNames(arrMarkerNames);
			if(m_hashColNames != null)
				return true;
		}
		return false;
	}
	
	private String extractTableName()
	{
		int nPos = m_csQueryUpper.indexOf("UPDATE");
		if(nPos < 0)
			return null;
		
		String csRight = m_csQueryUpper.substring(nPos+6);
		csRight = csRight.trim();
		nPos = csRight.indexOf(" ");
		if(nPos < 0)
			return null;

		String csTableName = csRight.substring(0, nPos);
		csTableName = csTableName.trim();
		return csTableName;
	}
	
	private Hashtable<String, String> extractSetNames(ArrayFixDyn<String> arrMarkerNames)
	{
		int nCountQuestionMark = 0;
		Hashtable<String, String> hashColNames = new Hashtable<String, String>();
		
		int nPos = m_csQueryUpper.indexOf("SET");
		if(nPos >= 0)
		{
			String cs = m_csQueryUpper.substring(nPos + 3);
			if(cs != null)
			{				
				int nPosWhere = cs.indexOf("WHERE");
				if(nPosWhere >= 0)
					cs = cs.substring(0, nPosWhere);
				ArrayList<String> arrSets = splitParameters(cs, ',');
				if(arrSets != null)
				{
					for(int n=0; n<arrSets.size(); n++)
					{
						String csSetChunk = arrSets.get(n);
						int nPosQuestion = csSetChunk.indexOf('?');
						while(nPosQuestion > 0)
						{
							int nPosSep = csSetChunk.indexOf('=');			// xxx=?
							String csColumnName = csSetChunk.substring(0, nPosSep);
							csColumnName = csColumnName.trim();
							String csSharpName = arrMarkerNames.get(nCountQuestionMark);
							hashColNames.put(csSharpName, csColumnName);
							nCountQuestionMark++;
							
							nPosQuestion = csSetChunk.indexOf('?', nPosQuestion+1);
						}
					}
				}
			}			
		}
		
		return hashColNames;
	}
	
	public synchronized OracleColumnDefinition getOracleColumnDefinition(DbConnectionBase dbConnection, String csSharpName)
	{
		if(m_csTableName == null)
			return null;
		
		OracleTableDefinition oracleTableDefinition = OracleTableDefinitionManager.getOrFillDefinitionsforTable(dbConnection, m_csTableName);
		if(oracleTableDefinition != null)	// Found catalog definition for the table  
		{
			// Get the column's name
			String csColName = m_hashColNames.get(csSharpName);
			if(csColName != null)
			{
				OracleColumnDefinition oracleColumnDefinition = oracleTableDefinition.getNamedColumnDefinition(csColName);
				return oracleColumnDefinition;
			}
			
//			if(nParamIndex >= 0 && nParamIndex < m_arrSetNames.size())
//			{
//				String csColName = m_arrSetNames.get(nParamIndex);
//				if(csColName != null)
//				{
//					OracleColumnDefinition oracleColumnDefinition = oracleTableDefinition.getNamedColumnDefinition(csColName);
//					return oracleColumnDefinition;
//				}
//			}
		}
		return null;
	}
}

