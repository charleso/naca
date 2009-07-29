/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.sqlSupport;

import java.util.ArrayList;

import jlib.misc.ArrayFixDyn;
import jlib.misc.StringUtil;
import jlib.sql.DbConnectionBase;
import jlib.sql.OracleColumnDefinition;
import jlib.sql.OracleTableDefinition;
import jlib.sql.OracleTableDefinitionManager;

public class PreparedDeleteStmtColumnTypeManager extends PreparedStmtColumnTypeManager
{
	private String m_csTableName = null;
	private ArrayList<String> m_arrColNames = null;
	
	PreparedDeleteStmtColumnTypeManager(String csQueryUpper)
	{		
		super(csQueryUpper); 
	}
	
	boolean analyse(ArrayFixDyn<String> arrMarkerNames)
	{
//		m_csTableName = extractTableName();
//		if(m_csTableName != null)
//		{
//			m_arrColNames = extractColNames();
//			if(m_arrColNames != null)
//				return true;
//		}
		return false;
	}
	
	private String extractTableName()
	{
		int nPos = m_csQueryUpper.indexOf("FROM");
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
	
	private ArrayList<String> extractColNames()
	{
		ArrayList<String> arrColNames = new ArrayList<String>();
		
//		int nPosStart = m_csQueryUpper.indexOf("(");
//		int nPosEnd  = m_csQueryUpper.indexOf(")");
//		if(nPosStart >= 0 && nPosEnd >= 0 && nPosStart < nPosEnd)
//		{
//			String cs = m_csQueryUpper.substring(nPosStart+1, nPosEnd);
//			int nPos = cs.indexOf(',');
//			while(nPos >= 0)
//			{
//				String csLeft = cs.substring(0, nPos);
//				csLeft = csLeft.trim();
//				arrColNames.add(csLeft);
//				cs = cs.substring(nPos+1);
//				nPos = cs.indexOf(',');
//			}
//			if(!StringUtil.isEmpty(cs))
//			{
//				cs = cs.trim();
//				arrColNames.add(cs);
//			}
//		}
		
		return arrColNames;
	}
	
	public synchronized OracleColumnDefinition getOracleColumnDefinition(DbConnectionBase dbConnection, String csSharpName)
	{
		if(m_csTableName == null)
			return null;
		
//		OracleTableDefinition oracleTableDefinition = OracleTableDefinitionManager.getOrFillDefinitionsforTable(dbConnection, m_csTableName);
//		if(oracleTableDefinition != null)	// Found catalog definition for the table  
//		{
//			// Get the column's name
//			if(nParamIndex >= 0 && nParamIndex < m_arrColNames.size())
//			{
//				String csColName = m_arrColNames.get(nParamIndex);	
//				OracleColumnDefinition oracleColumnDefinition = oracleTableDefinition.getNamedColumnDefinition(csColName);
//				return oracleColumnDefinition;
//			}
//		}
		return null;
	}
}

