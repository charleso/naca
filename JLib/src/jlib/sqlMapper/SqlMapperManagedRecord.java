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
package jlib.sqlMapper;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;

import jlib.exception.TechnicalException;
import jlib.sql.ColValue;
import jlib.sql.ColValueCollection;
import jlib.sql.DbAccessor;
import jlib.sql.SQLClause;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */

public class SqlMapperManagedRecord extends ColValueCollection	
{
	SqlMapperManagedRecord()
	{
		super();
	}
	
	boolean executeInsert(DbAccessor dbAccessor, String csTableName)
	{
		SQLClause clause = new SQLClause(dbAccessor);
		try
		{
			int nNbCols = getNbColValues();
			
			clause.set("INSERT INTO " + csTableName);
			for(int nCol=0; nCol<nNbCols; nCol++)	// Enum all cols of the record
			{
				ColValue colValue = getColValueAtIndex(nCol);
				clause.paramInsert(colValue);			
			}
			int nNbRecords = clause.prepareAndExecute();
			return true;
		}
		catch(TechnicalException e)
		{
			if(clause != null)
				clause.forceCloseOnExceptionCatched();
			throw e;
		}
	}
	
	boolean executeDelete(DbAccessor dbAccessor, String csTableName, RecordId recordId)
		throws TechnicalException
	{
		SQLClause clause = new SQLClause(dbAccessor);
		try
		{
			// Execute a select statement with the where parameter defined by recordId
			
			// Create the select statement
			StringBuilder sbClause = new StringBuilder("Delete from ");
			sbClause.append(csTableName);
			recordId.buildWhereClauseAndMapParams(sbClause, clause);
//			
//			String csWhere = recordId.getWhere();
//			sbClause.append(csWhere);
//			clause.set(sbClause.toString());
//			// Set the where parameters		
//			
//			for(int nCol=0; nCol<recordId.getNbColValues(); nCol++)	// Enum all cols of the record
//			{
//				ColValue colValue = recordId.getColValueAtIndex(nCol);
//				clause.param(colValue);			
//			}
//
			clause.prepareAndExecute();	// Execute the statement
			
			return true;
		}
		catch (TechnicalException e)
		{
			if(clause != null)
				clause.forceCloseOnExceptionCatched();
			throw e;
		}
	}
	
	void handleColsType(SQLClause clause, ResultSet resultSet)
	{
		ResultSetMetaData rsMetaData;
		int nCol=0;
		try
		{
			rsMetaData = resultSet.getMetaData();
			
			int nNbCols = rsMetaData.getColumnCount();
			for(nCol=1; nCol<=nNbCols; nCol++)
			{
				String csColName = rsMetaData.getColumnName(nCol);
				String csColTypeName = rsMetaData.getColumnTypeName(nCol);
				if(csColTypeName.equals("CHAR"))
				{
					add(csColName, (String)null);
				}
				else if(csColTypeName.equals("INTEGER") || csColTypeName.equals("INTEGER UNSIGNED"))
				{
					add(csColName, (int)0);
				}
				else if(csColTypeName.equals("DECIMAL"))
				{				
					int nPrecision = rsMetaData.getPrecision(nCol);
					int nScale = rsMetaData.getScale(nCol);
					if(nScale == 0)	// No digits behind comma (integer value)
					{
						if(nPrecision <= 8)	// Fits within an int
						{
							add(csColName, (int)0);
						}
						else	// A long is needed
						{
							add(csColName, (long)0);
						}
					}
					else	// Digits are behind the comma
					{
						add(csColName, (BigDecimal)null);
					}
				}
				else if(csColTypeName.equals("INTEGER"))
				{
					int nPrecision = rsMetaData.getPrecision(nCol);
					if(nPrecision <= 8)	// Fits within an int
					{
						add(csColName, (int)0);
					}
					else	// A long is needed
					{
						add(csColName, (long)0);
					}
				}
				else if(csColTypeName.equals("TIMESTAMP"))
				{
					add(csColName, (Timestamp)null);
				}
				else if(csColTypeName.equals("VARCHAR") || csColTypeName.equals("LONG VARCHAR") || csColTypeName.equals("LONG"))	// LONG is for ORACLE Support
				{
					add(csColName, (String)null);	// VARCHAR is managed as well as CHAR; Is it Ok ?
				}
				else if(csColTypeName.equals("DATE"))
				{
					add(csColName, (Date)null);
				}
				else if(csColTypeName.equals("SMALLINT"))
				{
					add(csColName, (int)0);
				}
//				else if(csColTypeName.equals("BLOB"))
//				{
//					baseRecordColTypeManager = new RecordColTypeManagerOther(nColSourceIndex);
//					recordSetCacheColTypeType.set(nColSourceIndex0Based, baseRecordColTypeManager);
//				}
				else
				{
					add(csColName, (String)null);
				}
			}
		}
		catch (SQLException e)
		{
			TechnicalException.throwException("Error while getting column type or name; 1 based column index="+nCol, clause, e);
		}
	}	
	
	boolean fillColValues(SQLClause clause, ResultSet resultSet, ColValueCollection recordColsTypeMaster)
	{
		int nCol = 0;
		try
		{
			int nNbCols = recordColsTypeMaster.getNbColValues();
			for(nCol=0; nCol<nNbCols; nCol++)
			{
				ColValue colValueMaster = recordColsTypeMaster.getColValueAtIndex(nCol);
				ColValue colValueTarget = colValueMaster.duplicate();
				colValueTarget.fillWithResurltSetCol(resultSet, nCol+1);
							
				add(colValueTarget);
			}
			return true;
		}
		catch (SQLException e)
		{
			TechnicalException.throwException("Result column get value error; 0 based col index="+nCol, clause, e);
		}
		return false;
	}
	
//	public void fillObject(Object oTarget)	// Fill the members of the object in parameter
//	{
//		Class<?> targetClass = oTarget.getClass();
//		Field fieldlist[] = targetClass.getDeclaredFields();
//		for (int i=0; i < fieldlist.length; i++) 
//		{
//			Field field = fieldlist[i];
//			field.setAccessible(true);
//			String csMember = field.getName();	// By convention, the csMember name must match column name (case insensitive)
//			Class type = field.getType();
//			Type fieldType = field.getGenericType();
//			try
//			{
//				Object oMember = field.get(oTarget);
//				if(oMember != null)
//				{
//					ColValue colValue = getColValueByNameCaseInsensitive(csMember);
//					if(colValue == null)	// Not found; maybe the member used m_<typing> prefix; try without this prefix
//					{	
//						String csUnprefixedMemberName = StringUtil.getUnprefixedMemberName(csMember, "m_", fieldType);	// 
//						colValue = getColValueByNameCaseInsensitive(csUnprefixedMemberName);
//					}
//					if(colValue != null)
//					{
//						if(fieldType.equals(Integer.TYPE))
//						{
//							int n = colValue.getValueAsInt();
//							field.set(oTarget, new Integer(n));
//						}
//						if(fieldType.equals(String.class))
//						{
//							String cs = colValue.getValueAsString();
//							field.set(oTarget, new String(cs));
//						}
//					}
//						
//				}
//			}
//			catch (IllegalArgumentException e)
//			{
//				int gg =0 ;
//				//out.set(csTypeName + " " + csName, "?");
//			}
//			catch (IllegalAccessException e)
//			{
//				int gg =0 ;
//				//out.set(csTypeName + " " + csName, "?");
//			}
//		}
//	}
}
