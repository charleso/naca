/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.sql;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import jlib.log.Log;


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: BaseDbColDefinitionFactory.java,v 1.5 2007/02/08 06:38:12 u930di Exp $
 */
public class BaseDbColDefinitionFactory
{
	public BaseDbColDefinitionFactory()
	{
	}
	
	public static String makeInsertString(String csTableFullName, ArrayList<BaseDbColDefinition> arrDbColDefinition)
	{
		String csInsert = "INSERT INTO " + csTableFullName + " VALUES (";  

		int nNbCols = arrDbColDefinition.size(); 
		for(int n=0; n<nNbCols; n++)
		{
			if(n == 0)
				csInsert += "?";
			else
				csInsert += ",?";
		}

		csInsert += ")";
		return csInsert;
	}
	
	public ArrayList<BaseDbColDefinition> makeArrayDbColDefinitions(DbConnectionBase dbConnection, String csPrefix, String csTableName)
	{
		int nDebugStep = 0;
		ArrayList<BaseDbColDefinition> arrDbColDef = new ArrayList<BaseDbColDefinition>();
		try
		{			
			ColDescription colDescription = new ColDescription();
			
			DatabaseMetaData dmd = dbConnection.getDbConnection().getMetaData();
			nDebugStep = 1;
			ResultSet cols = dmd.getColumns(null, csPrefix, csTableName, "%");
			nDebugStep = 2;
			if (cols != null)
			{		
				boolean b = true;
				while(cols.next() && b)
				{					
					b = colDescription.fill(cols);
					if(b)
					{
						//System.out.println(colDescription.toString());
						BaseDbColDefinition dbColDef = colDescription.makeDbColDefinition();
						if(dbColDef != null)
							arrDbColDef.add(dbColDef);
						else
						{
							Log.logImportant("Could create DbColDefinition for colDescription="+colDescription.toString());
						}
					}
					nDebugStep++;
				}
			}
		}
		catch (SQLException e)
		{
			String cs = csTableName;
			if(csPrefix != null)
				cs = csPrefix + "." + csTableName;
			Log.logImportant("SQL excption in makeArrayDbColDefinitions from table " + cs + " at debugStep="+nDebugStep);
			return null;
		}
		return arrDbColDef;
	}
	
	public ArrayList<BaseDbColDefinition> makeArrayDbColDefinitions(ResultSet resultSet)
	{
		ArrayList<BaseDbColDefinition> arrDbColDef = new ArrayList<BaseDbColDefinition>();
		try
		{
			ResultSetMetaData rsm = resultSet.getMetaData();
			ColDescription colDescription = new ColDescription();
			int nNbCols = rsm.getColumnCount();
			boolean b = true;
			for(int nCol=0; nCol<nNbCols && b; nCol++)
			{
				b = colDescription.fill(rsm, nCol+1);
				//System.out.println(colDescription.toString());
				if(b)
				{
					BaseDbColDefinition dbColDef = colDescription.makeDbColDefinition();
					if(dbColDef != null)
						arrDbColDef.add(dbColDef);
				}
				else
					return null;
			}
		}
		catch (SQLException e)
		{
			Log.logImportant("SQL excption in makeArrayDbColDefinitions from resultset");
			return null;
		}
		
		return arrDbColDef;
	}
}
