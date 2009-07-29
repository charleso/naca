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
package jlib.sqlMapper;

import java.util.ArrayList;
import java.util.Hashtable;

import jlib.sql.DbAccessor;

public class SqlMapper 
{
	private Hashtable<String, SqlMapperManagedTable> m_hashTables = null; 	// Hash for get managed table by name
	private ArrayList<SqlMapperManagedTable> m_arrTables = null; 	// Array for get managed table by dependency order
	
	public SqlMapper() 
	{
	}
	
	public synchronized SqlMapperManagedTable registerTable(String csTableName) 
	{
		if(m_hashTables == null)
			m_hashTables = new Hashtable<String, SqlMapperManagedTable>();
		if(m_arrTables == null)
			 m_arrTables = new ArrayList<SqlMapperManagedTable>();
		
		SqlMapperManagedTable managedTable = m_hashTables.get(csTableName);
		if(managedTable == null)
		{
			managedTable = new SqlMapperManagedTable(csTableName);
			m_hashTables.put(csTableName, managedTable);
			m_arrTables.add(managedTable);
		}
		return managedTable;
	}

	// Get a field/key's value of a given record id
//	public String getAsString(RecordId recordId, String csColName)
//	{
//		return recordId.getAsString(csColName);
//	}
//	
//	public int getAsInt(RecordId recordId, String csColName)
//	{
//		return recordId.getAsInt(csColName);
//	}
//	
//	public double getAsDouble(RecordId recordId, String csColName)
//	{
//		return recordId.getAsDouble(csColName);
//	}
	
	// Get a column value of the given record of the given table
//	public String getAsString(SqlMapperManagedTable managedTable, RecordId recordId, String csColName)
//	{
//		return managedTable.getAsString(recordId, csColName);
//	}
//
//	public int getAsInt(SqlMapperManagedTable managedTable, RecordId recordId, String csColName)
//	{
//		return managedTable.getAsInt(recordId, csColName);
//	}
//	
//	public double getAsDouble(SqlMapperManagedTable managedTable, RecordId recordId, String csColName)
//	{
//		return managedTable.getAsDouble(recordId, csColName);
//	}
	
//	public boolean isSet(SqlMapperManagedTable managedTable, RecordId recordId, String csColName)
//	{
//		return managedTable.isSet(recordId, csColName);
//	}
//	
//	public void set(SqlMapperManagedTable managedTable, RecordId recordId, String csColName, String csValue) 
//	{		
//		managedTable.set(recordId, csColName, csValue);
//	}
//	
//	public void set(SqlMapperManagedTable managedTable, RecordId recordId, String csColName, int nValue) 
//	{
//		managedTable.set(recordId, csColName, nValue);
//	}
//	
//	public void set(SqlMapperManagedTable managedTable, RecordId recordId, String csColName, double dValue) 
//	{
//		managedTable.set(recordId, csColName, dValue);
//	}
//	
//	public void set(SqlMapperManagedTable managedTable, RecordId recordId, String csColName, boolean bValue) 
//	{
//		managedTable.set(recordId, csColName, bValue);
//	}
//	
//	public void set(SqlMapperManagedTable managedTable, RecordId recordId, String csColName, BigDecimal bdValue)
//	{
//		managedTable.set(recordId, csColName, bdValue);
//	}
//	
//	public void set(SqlMapperManagedTable managedTable, RecordId recordId, String csColName, Date dtValue)
//	{
//		managedTable.set(recordId, csColName, dtValue);
//	}
//	
//	public void set(SqlMapperManagedTable managedTable, RecordId recordId, String csColName, Timestamp tsValue)
//	{
//		managedTable.set(recordId, csColName, tsValue);
//	}
	
	public synchronized SqlMapperManagedTable getRegisteredTable(String csTableName) 
	{
		SqlMapperManagedTable managedTable = m_hashTables.get(csTableName);
		return managedTable;
	}
	
//	public SqlMapperManagedRecord getRecordByName(SqlMapperManagedTable managedTable, String csRecordName)
//	{
//		if(managedTable == null)
//			return null;
//
//		SqlMapperManagedRecord record = managedTable.getRecordByName(csRecordName);
//		return record;
//	}
//	
//	public RecordId getRecordIdByName(SqlMapperManagedTable managedTable, String csRecordName)
//	{
//		if(managedTable == null)
//			return null;
//
//		RecordId recordId = managedTable.getRecordIdByName(csRecordName);
//		return recordId;
//	}
	
//	public RecordId getRecordId(SqlMapperManagedTable table, String csRecordIdSemantic) 
//	{
//		table.getRecordIdWithSemantic(String csRecordIdSemantic)
//		SqlMapperManagedTable managedTable = m_hashTables.get(csTableName);
//		return managedTable;
//	}
//	
	// Execute all insert statements for all tables on all records
	public void executeInserts(DbAccessor dbAccessor)
	{
		// Insert records for all tables 
		for(int nTable=0; nTable<m_arrTables.size(); nTable++)	// A recordId may concern multiple tables; enum all tables that are potential concerned by a record Id
		{
			SqlMapperManagedTable managedTable = m_arrTables.get(nTable);
			managedTable.executeInserts(dbAccessor);	// Execute an insert for the record id in the table 
		}
	}
	
	// Execute all select statements for all tables on all records; They are done by keys defined in all RecordId
	public void executeSelects(DbAccessor dbAccessor)
	{
		// Select records for all tables; The select "where" is given by the values stored in the recordId
		for(int nTable=0; nTable<m_arrTables.size(); nTable++)	// A recordId may concern multiple tables; enum all tables that are potential concerned by a record Id
		{
			SqlMapperManagedTable managedTable = m_arrTables.get(nTable);
			managedTable.executeSelects(dbAccessor);	// Execute an insert for the record id in the table 
		}
	}
	
	// Execute all delete statements for all tables on all records; They are done by keys defined in all RecordId
	public void executeDeletes(DbAccessor dbAccessor)
	{
		// Delete records for all tables; The select "where" is given by the values stored in the recordId
		for(int nTable=0; nTable<m_arrTables.size(); nTable++)	// A recordId may concern multiple tables; enum all tables that are potential concerned by a record Id
		{
			SqlMapperManagedTable managedTable = m_arrTables.get(nTable);
			managedTable.executeDeletes(dbAccessor);	// Execute an insert for the record id in the table 
		}
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder(); 
		for(int nTable=0; nTable<m_arrTables.size(); nTable++)	// A recordId may concern multiple tables; enum all tables that are potential concerned by a record Id
		{
			SqlMapperManagedTable managedTable = m_arrTables.get(nTable);
			String cs = managedTable.toString();
			sb.append(cs+"\r");
		}
		return sb.toString();
	}
	
	public void clearValues()
	{
		for(int nTable=0; nTable<m_arrTables.size(); nTable++)	// A recordId may concern multiple tables; enum all tables that are potential concerned by a record Id
		{
			SqlMapperManagedTable managedTable = m_arrTables.get(nTable);
			managedTable.clearValues();
		}
	}
	
	
}
