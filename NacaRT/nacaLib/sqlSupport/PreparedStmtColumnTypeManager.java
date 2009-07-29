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
import jlib.sql.BaseDbColDefinition;
import jlib.sql.BaseDbColDefinitionFactory;
import jlib.sql.DbConnectionBase;
import jlib.sql.OracleColumnDefinition;
import jlib.sql.SQLColumnType;

public abstract class PreparedStmtColumnTypeManager
{
	protected String m_csQueryUpper;
	
	PreparedStmtColumnTypeManager(String csQueryUpper)
	{		
		m_csQueryUpper = csQueryUpper;
	}
	
	abstract boolean analyse(ArrayFixDyn<String> arrMarkerNames);
	
	abstract public OracleColumnDefinition getOracleColumnDefinition(DbConnectionBase dbConnection, String csSharpName);
	
	protected ArrayList<String> splitParameters(String csValues, char cSeparator)
	{
		ArrayList<String> arr = new ArrayList<String>(); 
		int nParenthesisDepth = 0;
		int nPosStart = 0;
		for(int nPos=0; nPos<csValues.length(); nPos++)
		{
			char c = csValues.charAt(nPos);
			if(c == cSeparator)
			{
				if(nParenthesisDepth == 0)
				{
					String csChunk = csValues.substring(nPosStart, nPos);
					arr.add(csChunk.trim());
					nPosStart = nPos + 1;
				}
			}
			if(c == '(')
				nParenthesisDepth++;
			if(c == ')')
				nParenthesisDepth--;
		}
		if(nPosStart < csValues.length())
		{
			String csChunk = csValues.substring(nPosStart);
			arr.add(csChunk.trim());
		}
		return arr;
	}

}
	

