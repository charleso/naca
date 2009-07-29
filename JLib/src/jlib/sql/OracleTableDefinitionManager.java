/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.sql;

import java.util.Hashtable;

public class OracleTableDefinitionManager
{
	private static Hashtable<String, OracleTableDefinition> m_hashDefByTableName = new Hashtable<String, OracleTableDefinition>();
	
	
	public synchronized static OracleTableDefinition getOrFillDefinitionsforTable(DbConnectionBase dbConnection, String csTableName)
	{
		OracleTableDefinition oracleTableDefinition = m_hashDefByTableName.get(csTableName);
		if(oracleTableDefinition != null)
			return oracleTableDefinition;
		
		oracleTableDefinition = new OracleTableDefinition();
		boolean b = oracleTableDefinition.fill(dbConnection, csTableName);
		if(b)
		{
			m_hashDefByTableName.put(csTableName, oracleTableDefinition);
			return oracleTableDefinition;
		}
		return null;
	}
//	
//	public synchronized static OracleTableDefinition getOracleTableDefinition(String csTableName)
//	{
//		OracleTableDefinition oracleTableDefinition = m_hashDefByTableName.get(csTableName);
//		return oracleTableDefinition;
//	}
}
