/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
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

import java.util.Hashtable;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class DbDriverId
{
	private String m_csName = null;
	private static Hashtable<String, DbDriverId> ms_hashDriverByClass = new Hashtable<String, DbDriverId>();  
	
	public static DbDriverId SQLServer = new DbDriverId("SQLServer", "SQLServerDriver");
	public static DbDriverId UDB = new DbDriverId("UDB", "DB2Driver");
	public static DbDriverId Oracle = new DbDriverId("Oracle", "OracleDriver");
	//public static DbDriverId MySQL = new DbDriverId("MySQL", "");
	
	private DbDriverId(String csName, String csClassName)
	{
		m_csName = csName;
		put(csClassName, this);
	}
	
	private static synchronized void put(String csClassName, DbDriverId dbDriver)
	{
		ms_hashDriverByClass.put(csClassName, dbDriver);
	}
	
	public static synchronized DbDriverId getByClassName(String csFullyQualifiedClassName)
	{
		String csClassName = csFullyQualifiedClassName;
		int nIndex = csFullyQualifiedClassName.lastIndexOf(".");
		if(nIndex != -1)
			csClassName = csFullyQualifiedClassName.substring(nIndex+1);
		
		DbDriverId dbDriver = ms_hashDriverByClass.get(csClassName);
		if(dbDriver != null)
			return dbDriver;
		return UDB;
	}
	
	public String toString()
	{
		return m_csName;
	}
	
	public boolean isSameInstance(DbDriverId id)
	{
		if(this == id)
			return true;
		return false;
	}
}
