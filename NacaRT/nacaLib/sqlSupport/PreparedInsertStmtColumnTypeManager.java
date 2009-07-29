/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.sqlSupport;

import java.util.ArrayList;
import java.util.Hashtable;

import jlib.log.Log;
import jlib.misc.ArrayFixDyn;
import jlib.misc.StringUtil;
import jlib.sql.BaseDbColDefinition;
import jlib.sql.BaseDbColDefinitionFactory;
import jlib.sql.DbConnectionBase;
import jlib.sql.OracleColumnDefinition;
import jlib.sql.OracleTableDefinition;
import jlib.sql.OracleTableDefinitionManager;
import jlib.sql.SQLColumnType;

public class PreparedInsertStmtColumnTypeManager extends PreparedStmtColumnTypeManager
{
	private String m_csTableName = null;
	//private ArrayList<String> m_arrColNames = null;
	private Hashtable<String, String> m_hashColNames = null;
	
	PreparedInsertStmtColumnTypeManager(String csQueryUpper)
	{		
		super(csQueryUpper); 
	}
	
	boolean analyse(ArrayFixDyn<String> arrMarkerNames)
	{
		m_csTableName = extractTableName();
		if(m_csTableName != null)
		{
			m_hashColNames = extractColNames(arrMarkerNames);
			if(m_hashColNames != null)
				return true;
		}
		return false;
	}
	
	private String extractTableName()
	{
		int nPos = m_csQueryUpper.indexOf("INTO");
		if(nPos < 0)
			return null;
		
		String csRight = m_csQueryUpper.substring(nPos+4);
		nPos = csRight.indexOf("(");
		if(nPos < 0)
			return null;
		
		String csTableName = csRight.substring(0, nPos);
		csTableName = csTableName.trim();
		return csTableName;
	}
		
	private Hashtable<String, String> extractColNames(ArrayFixDyn<String> arrMarkerNames)
	{
		Hashtable<String, String> hashColNames = new Hashtable<String, String> ();
		
		int nPosStartCols = m_csQueryUpper.indexOf("(");
		int nPosEndCols  = m_csQueryUpper.indexOf(")");

		int nPosValues = m_csQueryUpper.indexOf("VALUES");
		if(nPosValues > 0)
		{
			String csValues = m_csQueryUpper.substring(nPosValues + 6);
			int nPosStartValues = csValues.indexOf("(");
			int nPosEndValues = csValues.lastIndexOf(")");
			csValues = csValues.substring(nPosStartValues+1, nPosEndValues);
			ArrayList<String> arrValues = splitParameters(csValues, ',');
			
			if(nPosStartCols >= 0 && nPosEndCols >= 0 && nPosStartCols < nPosEndCols)
			{
				int nCountQuestionMark = 0;
				String csCols = m_csQueryUpper.substring(nPosStartCols+1, nPosEndCols);
				String tCols[] = csCols.split(",");
				if(tCols.length == arrValues.size())
				{
					for(int n=0; n<tCols.length; n++)
					{
						String csQuestionMark = arrValues.get(n);
						int nPosQuestion = csQuestionMark.indexOf('?');
						while(nPosQuestion >= 0)
						{
							String csSharpName = arrMarkerNames.get(nCountQuestionMark);
							String csColName = tCols[n];
							hashColNames.put(csSharpName, csColName.trim());
							nCountQuestionMark++;
							nPosQuestion = csQuestionMark.indexOf('?', nPosQuestion+1);
						}
					}
				}
				else
					Log.logCritical("Mismatch number of columns / values for statement "+m_csQueryUpper);
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
//			Get the column's name
			String csColName = m_hashColNames.get(csSharpName);
			if(csColName != null)
			{
				OracleColumnDefinition oracleColumnDefinition = oracleTableDefinition.getNamedColumnDefinition(csColName);
				return oracleColumnDefinition;
			}
		}
		return null;
	}
}

