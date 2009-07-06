/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.sqlMapper;

import java.sql.ResultSet;
import java.util.ArrayList;

import jlib.exception.TechnicalException;
import jlib.sql.ColValue;
import jlib.sql.ColValueCollection;
import jlib.sql.DbAccessor;
import jlib.sql.SQLClause;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: SqlMapperRecordsCollection.java,v 1.2 2007/12/04 14:00:23 u930di Exp $
 */
public class SqlMapperRecordsCollection
{
	ArrayList<SqlMapperManagedRecord> m_arr = null;
	
	SqlMapperRecordsCollection()
	{
		m_arr = new ArrayList<SqlMapperManagedRecord>();
	}
	
	void add(SqlMapperManagedRecord recordCols)
	{
		m_arr.add(recordCols);
	}
	
	public SqlMapperManagedRecord getFirstRecord()
	{
		if(m_arr.size() > 0)
			return m_arr.get(0);
		return null;
	}
	
	public SqlMapperManagedRecord getRecordAt(int nIndex)
	{
		return m_arr.get(nIndex);
	}

	public int getNbRecords()
	{
		return m_arr.size();
	}
	
	public void clearValues()
	{
		int nNbRecords = getNbRecords();
		for(int n=0; n<nNbRecords; n++)
		{
			SqlMapperManagedRecord record = getRecordAt(n);
			record.clearValues();
		}
	}
	
	boolean executeSelect(DbAccessor dbAccessor, String csTableName, RecordId recordId)
		throws TechnicalException
	{
		SQLClause clause = new SQLClause(dbAccessor);
		try
		{
			// Execute a select statement with the where parameter defined by recordId
			
			// Create the select statement
//			StringBuilder sbClause = new StringBuilder("Select * from ");
//			sbClause.append(csTableName);
//			String csWhere = recordId.buildClause(csTableName, clause);
//			sbClause.append(csWhere);
//			clause.set(sbClause.toString());
			
			StringBuilder sbClause = new StringBuilder("Select * from ");
			sbClause.append(csTableName);

			
			recordId.buildWhereClauseAndMapParams(sbClause, clause);
			
//			// Set the where parameters 
//			for(int nCol=0; nCol<recordId.getNbColValues(); nCol++)	// Enum all cols of the record
//			{
//				ColValue colValue = recordId.getColValueAtIndex(nCol);
//				clause.param(colValue);			
//			}
	
			clause.prepareAndExecute();	// Execute the statement
			
			SqlMapperManagedRecord recordColsTypeMaster = null;	// Just to identify the types of all columns; It's done only for the 1st record of the result set.
			// A performance enhancement would be to have this recordColsTypeMaster created for only the 1st record of all select sharing the same statement 
			while(clause.next())	// Enum all records
			{
				ResultSet resultSet = clause.getResultSet();
				SqlMapperManagedRecord record = new SqlMapperManagedRecord();	// Record to fill
				
				if(recordColsTypeMaster == null)	// We have not yet discovered the type of the columns
				{				
					recordColsTypeMaster = new SqlMapperManagedRecord();	
					recordColsTypeMaster.handleColsType(clause, resultSet);
				}
				if(record.fillColValues(clause, resultSet, recordColsTypeMaster))	// Fill the recordCols with the column's value, with typing management
					m_arr.add(record);
			}
			
			// Callback to call to inform application code of the records read; it can filter; It must return only 1 record
	//		RecordsCollection arrRecordsSelected = sqlMapperSelectFilterIntf.filterRecordsSelected(arrRecordsRead);
	//		if(arrRecordsSelected != null)
	//			replaceInternalContainer(arrRecordsSelected);	// "this" contains now only the selected record; it can be null if the filter decided so
			
			return true;
		}
		catch (TechnicalException e)
		{
			if(clause != null)
				clause.forceCloseOnExceptionCatched();
			throw e;
		}
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		int nNbRecords = getNbRecords();
		for(int n=0; n<nNbRecords; n++)
		{
			SqlMapperManagedRecord record = getRecordAt(n);
			String cs = record.toString();
			sb.append(cs);
			sb.append("\r");
		}
		sb.append("\r");
		return sb.toString();
	}
	
}
