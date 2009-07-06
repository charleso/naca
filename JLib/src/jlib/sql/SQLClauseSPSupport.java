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
import java.sql.SQLException;
import java.util.ArrayList;

import jlib.log.Log;
import jlib.sql.DbConnectionBase;
import jlib.sql.StoredProcParamDescBase;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: SQLClauseSPSupport.java,v 1.4 2008/04/01 11:45:06 u930bm Exp $
 */
public class SQLClauseSPSupport
{
	public SQLClauseSPSupport()
	{
	}

	public ArrayList<SQLClauseSPInfo> getStoredProceduresList(DbConnectionBase dbConnection)
	{
		ArrayList<SQLClauseSPInfo> arr = new ArrayList<SQLClauseSPInfo>();
		try
		{
			DatabaseMetaData dmd = dbConnection.getDbConnection().getMetaData();
			ResultSet rsProcs = dmd.getProcedures(null, null, "%");
			boolean b = true;
			while(rsProcs.next() && b)
			{
				SQLClauseSPInfo info = new SQLClauseSPInfo();
				if(info.fill(rsProcs))
					arr.add(info);
			}
		}
		catch (SQLException e)
		{
			return null;
		}
		return arr;	
	}

	public SQLClauseSPParamsDesc getStoredProcedureParamsList(DbConnectionBase dbConnection, String csStoredProcName)
	{
		SQLClauseSPParamsDesc spParamsDesc = new SQLClauseSPParamsDesc();
				
		try
		{
			DatabaseMetaData dmd = dbConnection.getDbConnection().getMetaData();
			if (csStoredProcName.indexOf(".") != -1) // suppress the user if exists in the procedure
				csStoredProcName = csStoredProcName.substring(csStoredProcName.indexOf(".") + 1); 
			ResultSet rsParams = dmd.getProcedureColumns(null, dbConnection.getEnvironmentPrefix(), csStoredProcName.replace("_", "\\_"), "%");
			boolean b = true;
			while(rsParams.next() && b)
			{
				spParamsDesc.addAParam(rsParams);
			}
		}
		catch (SQLException e)
		{			
			return null;
		}

		return spParamsDesc;
	}

}
