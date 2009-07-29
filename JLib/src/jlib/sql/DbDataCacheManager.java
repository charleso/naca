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
package jlib.sql;

import java.util.Hashtable;

public class DbDataCacheManager
{
	protected Hashtable<String, DbDataCacheTable> m_tabTables = null;
	
	public DbDataCacheManager()
	{
		m_tabTables = new Hashtable<String, DbDataCacheTable>() ;	// hash collection of CSQLDataCacheTable indexed by csTableName
	}
	
	protected class DbDataCacheTable
	{
		public Hashtable<String, Object> m_tabData = new Hashtable<String, Object>() ;
		public String m_csTableName = "" ; 
	}
	
	public void RegisterData(String csTableName, String csKey, Object val)
	{
		DbDataCacheTable table = m_tabTables.get(csTableName) ;
		if (table == null)
		{
			table = new DbDataCacheTable() ;
			table.m_csTableName = csTableName ;
			m_tabTables.put(csTableName, table) ;
		}
		table.m_tabData.put(csKey, val) ;
	}
	
	public Object getData(String csTableName, String csKey)
	{
		DbDataCacheTable table = m_tabTables.get(csTableName) ;
		if (table == null)
		{
			return null ;
		}
		if (table.m_tabData.containsKey(csKey))
		{
			return table.m_tabData.get(csKey) ;
		}
		return null ;
	} 
}
