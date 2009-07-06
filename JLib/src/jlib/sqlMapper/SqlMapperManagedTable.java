/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.sqlMapper;

import java.util.ArrayList;
import java.util.Hashtable;

import jlib.exception.TechnicalException;
import jlib.log.Log;
import jlib.sql.DbAccessor;

public class SqlMapperManagedTable
{
	private String m_csTableName = null;
	//private Hashtable<String, RecordId> m_hashRecordIdByName = null;	// map of all recordId indexed by their names
	private Hashtable<String, SqlMapperRecordsCollection> m_hashRecords = null;	// map of all records indexed by their name, given by their RecordId
	
	private ArrayList<RecordId> m_arrRecordIds = null;	// Array of the record id filled; it's in the insertion order
	
	SqlMapperManagedTable(String csTableName)
	{
		m_csTableName = csTableName;
		checkRecordsContainers();
	}
//	
//	public SqlMapperManagedRecord set(RecordId recordId)
//	{
//		SqlMapperManagedRecord record = getOrCreateRecord(recordId);
//		return record;
//	}
//	
//	public SqlMapperManagedRecord setByName(RecordId recordId)
//	{
//		SqlMapperManagedRecord record = getOrCreateRecordByName(recordId);
//		return record;
//	}
	
//	public boolean isSet(RecordId recordId, String csColName)
//	{
//		SqlMapperManagedRecord record = getOrCreateRecordByName(recordId);
//		return record.isStored(csColName);
//	}
	
//	public String getAsString(RecordId recordId, String csColName)
//	{
//		SqlMapperManagedRecord record = getOrCreateRecordByName(recordId);
//		return record.getAsString(csColName);
//	}
//	
//	public int getAsInt(RecordId recordId, String csColName)
//	{
//		SqlMapperManagedRecord record = getOrCreateRecordByName(recordId);
//		return record.getAsInt(csColName);
//	}
//	
//	public double getAsDouble(RecordId recordId, String csColName)
//	{
//		SqlMapperManagedRecord record = getOrCreateRecordByName(recordId);
//		return record.getAsDouble(csColName);
//	}
	
//	void set(RecordId recordId, String csColName, String csValue)
//	{
//		SqlMapperManagedRecord record = getOrCreateRecordByName(recordId);
//		record.add(csColName, csValue);
//	}
//	
//	void set(RecordId recordId, String csColName, int nValue)
//	{
//		SqlMapperManagedRecord record = getOrCreateRecordByName(recordId);
//		record.add(csColName, nValue);
//	}
//	
//	void set(RecordId recordId, String csColName, double dValue)
//	{
//		SqlMapperManagedRecord record = getOrCreateRecord(recordId);
//		record.add(csColName, dValue);
//	}
//	
//	void set(RecordId recordId, String csColName, boolean bValue)
//	{
//		SqlMapperManagedRecord record = getOrCreateRecord(recordId);
//		record.add(csColName, bValue);
//	}
//	
//	void set(RecordId recordId, String csColName, BigDecimal bdValue)
//	{
//		SqlMapperManagedRecord record = getOrCreateRecord(recordId);
//		record.add(csColName, bdValue);
//	}
//	
//	void set(RecordId recordId, String csColName, Date dtValue)
//	{
//		SqlMapperManagedRecord record = getOrCreateRecord(recordId);
//		record.add(csColName, dtValue);
//	}
//	
//	void set(RecordId recordId, String csColName, Timestamp tsValue)
//	{
//		SqlMapperManagedRecord record = getOrCreateRecord(recordId);
//		record.add(csColName, tsValue);
//	}
	
	private void checkRecordsContainers()
	{
		if(m_hashRecords == null)
			m_hashRecords = new Hashtable<String, SqlMapperRecordsCollection>();
		//if(m_hashRecordIdByName == null)
		//	m_hashRecordIdByName = new Hashtable<String, RecordId>();
		if(m_arrRecordIds == null)
			m_arrRecordIds = new ArrayList<RecordId>();
	}
	
//	private synchronized SqlMapperManagedRecord getOrCreateRecordByName(RecordId recordId)
//	{
//		RecordId recordIdFoundByName = getRecordIdByName(recordId.getName());
//		if(recordIdFoundByName == null)
//			recordIdFoundByName = recordId;
//		return getOrCreateRecord(recordIdFoundByName);
//	}
	
	// Get a record by it's Id, not by the content of the record Id ! 
//	private synchronized SqlMapperManagedRecord getOrCreateRecord(RecordId recordId)
//	{
//		SqlMapperManagedRecord record = m_hashRecords.get(recordId);
//		if(record != null)	// The record existed previously
//		{
//			return record;
//		}
//		// New record
//		m_hashRecordIdByName.put(recordId.getName(), recordId);	// Identifies the recordId by it's name in a map
//		
//		record = new SqlMapperManagedRecord();
//		m_hashRecords.put(recordId, record);
//		m_arrRecordIds.add(recordId);
//		return record;	
//	}
	
//	public synchronized SqlMapperManagedRecord getRecordByRecordId(RecordId recordId)
//	{
//		if(recordId == null)
//			return null;
//		
//		SqlMapperManagedRecord record = m_hashRecords.get(recordId);
//		return record;		
//	}
	
	// Get a recordId by it's name 
//	public synchronized RecordId getRecordIdByName(String csRecordName)
//	{
//		if(StringUtil.isEmpty(csRecordName))
//			return null;
//		
//		RecordId recordId = m_hashRecordIdByName.get(csRecordName);
//		return recordId;
//	}
	
	// Get a record by it's name, not by an object reference 
//	public synchronized SqlMapperManagedRecord getRecordByName(String csRecordName)
//	{
//		RecordId recordId = getRecordIdByName(csRecordName);
//		if(recordId == null)
//			return null;
//
//		SqlMapperManagedRecord record = getRecordByRecordId(recordId);
//		return record;
//	}
	
	public synchronized SqlMapperRecordsCollection getRecords(RecordId recordId)
	{
		String csRecordName = recordId.getName();
		return getRecords(csRecordName);
	}
	
	public synchronized SqlMapperRecordsCollection getRecords(String csRecordName)
	{
		SqlMapperRecordsCollection records = m_hashRecords.get(csRecordName);
		return records;
	}
	
	public synchronized SqlMapperManagedRecord getRecordWithId(RecordId recordId)
	{
		String csRecordName = recordId.getName();
		
		SqlMapperRecordsCollection records = m_hashRecords.get(csRecordName);
		if(records == null)
		{
			return null;
		}
		
		SqlMapperManagedRecord record = records.getFirstRecord();
		if(record == null)
		{
			return null;
		}
		
		return record;
	}
//	
//	public synchronized SqlMapperManagedRecord createNewRecordWithId(RecordId recordId)
//	{
//		return getOrCreateRecordWithId(recordId);
//	}
	
	public synchronized SqlMapperManagedRecord getOrCreateRecordWithId(RecordId recordId)
	{
		String csRecordName = recordId.getName();
		
		SqlMapperRecordsCollection records = m_hashRecords.get(csRecordName);
		if(records == null)
		{
			records = new SqlMapperRecordsCollection();
			m_hashRecords.put(csRecordName, records);
			m_arrRecordIds.add(recordId);
		}
		
		SqlMapperManagedRecord record = records.getFirstRecord();
		if(record == null)
		{
			record = new SqlMapperManagedRecord();
			records.add(record);
		}
		return record;
	}	
	
	public synchronized SqlMapperManagedRecord createNewRecordWithId(RecordId recordId)
	{
		String csRecordName = recordId.getName();
		m_arrRecordIds.add(recordId);
		
		SqlMapperRecordsCollection records = m_hashRecords.get(csRecordName);
		if(records == null)
		{
			records = new SqlMapperRecordsCollection();
			m_hashRecords.put(csRecordName, records);
		}
		
		SqlMapperManagedRecord record = new SqlMapperManagedRecord();
		records.add(record);
		return record;	
	}
	
	public int executeInserts(DbAccessor dbAccessor)
	{
		for(int nRecordId=0; nRecordId<m_arrRecordIds.size(); nRecordId++)	// Write all records for this table 
		{
			RecordId recordId = m_arrRecordIds.get(nRecordId);	// Identify the record itself
			if(recordId != null)
			{
				String csName = recordId.getName();
				SqlMapperRecordsCollection records = m_hashRecords.get(csName);	// Find all records
				int nNbRecords = records.getNbRecords();
				for(int nRecord=0; nRecord<nNbRecords; nRecord++)	// We have a record to insert in the current table
				{
					SqlMapperManagedRecord record = records.getRecordAt(nRecord);
					record.executeInsert(dbAccessor, m_csTableName);
				}
			}
		}
		return 0;
	}
	
	public void executeSelects(DbAccessor dbAccessor)
	{
		for(int nRecordId=0; nRecordId<m_arrRecordIds.size(); nRecordId++)	// Read all records for this table 
		{
			RecordId recordId = m_arrRecordIds.get(nRecordId);	// Identify the record itself
			if(recordId != null)
			{
				executeSelects(dbAccessor, recordId);
			}
		}
	}
	
	public SqlMapperRecordsCollection executeSelects(DbAccessor dbAccessor, RecordId recordId)
	{
		if(recordId != null)
		{
			String csName = recordId.getName();
			SqlMapperRecordsCollection records = m_hashRecords.get(csName);	// Find all records
			if(records == null)
			{
				records = new SqlMapperRecordsCollection(); 
				m_hashRecords.put(csName, records);
				addOnceRecordId(recordId);
			}
			records.executeSelect(dbAccessor, m_csTableName, recordId);
			return records;
		}
		return null;
	}
	
	private void addOnceRecordId(RecordId recordId)
	{
		for(int n=0; n<m_arrRecordIds.size(); n++)
		{
			RecordId rec = m_arrRecordIds.get(n);
			if(rec.hasName(recordId))
				return ;
		}
		m_arrRecordIds.add(recordId);
	}
	
	public boolean executeDeletes(DbAccessor dbAccessor)
	{
		try
		{
			for(int nRecordId=0; nRecordId<m_arrRecordIds.size(); nRecordId++)	// Enum all recordids for this table 
			{
				RecordId recordId = m_arrRecordIds.get(nRecordId);	// Identify the record itself
				if(recordId != null)
				{
					String csRecordName = recordId.getName();
					SqlMapperRecordsCollection records = m_hashRecords.get(csRecordName);	// Find the values of all columns 
					if(records != null)
					{
						int nNbRecords = records.getNbRecords();
						for(int n=0; n<nNbRecords; n++)
						{
							SqlMapperManagedRecord record = records.getRecordAt(n);					
							if(record != null)	// We have a container record to fill with a select
							{
								record.executeDelete(dbAccessor, m_csTableName, recordId);
							}
						}
					}
				}
			}
			return true;
		}
		catch(TechnicalException e)
		{
			Log.logDebug("Could not SQLMApper executeDelete : " + e.getMessage() + "; " + e.getError());
		}
		return false;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("\r");
		sb.append("Table " + m_csTableName + "\r");
		sb.append("-----------------------------------------------------\r");
		for(int nRecordId=0; nRecordId<m_arrRecordIds.size(); nRecordId++)	// Enum all records for this table
		{
			RecordId recordId = m_arrRecordIds.get(nRecordId);	// Identify the record itself
			if(recordId != null)
			{
				String csRecordId = recordId.toString();
				sb.append(csRecordId+"\rValues:\r");
				String csRecordName = recordId.getName();
				SqlMapperRecordsCollection records = m_hashRecords.get(csRecordName);	// Find the values of all columns
				if(records != null)
					sb.append(records.toString());
			}				
		}
		return sb.toString();
	}
	
	public void clearValues()
	{
	//	m_lastRecord = null;
	//	m_lastRecordId = null;

		for(int nRecordId=0; nRecordId<m_arrRecordIds.size(); nRecordId++)	// Enum all recordids for this table
		{
			RecordId recordId = m_arrRecordIds.get(nRecordId);	// Identify the record itself
			if(recordId != null)
			{
				String csRecordName = recordId.getName();
				SqlMapperRecordsCollection records = m_hashRecords.get(csRecordName);	// Find the values of all columns 
				if(records != null)
					records.clearValues();
				m_hashRecords.clear();
			}
		}
		//m_arrRecordIds.clear();
		//m_hashRecords.clear();
	}
}

