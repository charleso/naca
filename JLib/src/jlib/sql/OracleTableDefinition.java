/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

public class OracleTableDefinition
{
	private String m_csTableName = null;
	private Hashtable<String, OracleColumnDefinition> m_hashColDefByName = new Hashtable<String, OracleColumnDefinition>();
	
	public OracleTableDefinition()
	{		
	}
	
	public boolean fill(DbConnectionBase dbConnection, String csTableName)
	{
		m_csTableName = csTableName;
		Connection conn = dbConnection.getDbConnection();
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery("SELECT COLUMN_NAME, DATA_TYPE, DATA_LENGTH, DATA_SCALE, DATA_PRECISION, NULLABLE FROM USER_TAB_COLUMNS WHERE TABLE_NAME='" + csTableName + "'");
			m_hashColDefByName = new Hashtable<String, OracleColumnDefinition>();
			
			while(resultSet.next())
			{
				OracleColumnDefinition oracleColumnDefinition = new OracleColumnDefinition();
				oracleColumnDefinition.fill(resultSet);
				String cscolumnName = oracleColumnDefinition.getColumnName();
				m_hashColDefByName.put(cscolumnName, oracleColumnDefinition);
			}
			
			stmt.close();
			return true;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}	
	
	public synchronized OracleColumnDefinition getNamedColumnDefinition(String csColName)
	{
		OracleColumnDefinition oracleColumnDefinition = m_hashColDefByName.get(csColName);
		return oracleColumnDefinition;
	}
}
