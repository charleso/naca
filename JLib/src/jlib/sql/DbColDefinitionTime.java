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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import jlib.misc.AsciiEbcdicConverter;
import jlib.misc.CurrentDateInfo;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: DbColDefinitionTime.java,v 1.6 2007/03/16 08:41:50 u930di Exp $
 */
public class DbColDefinitionTime extends BaseDbColDefinition
{
	DbColDefinitionTime(ColDescriptionInfo colDescription)
	{
		super(colDescription);
	}
	
	public byte[] getByteValue(ResultSet resultSet, int nCol1Based, boolean bEbcdicOutput)
	{
		try
		{
			String csValue = resultSet.getString(nCol1Based);
			// JDBC returns date as HH:MM:SS
			String csHH = csValue.substring(0, 2);
			String csMM = csValue.substring(3, 5);
			String csSS = csValue.substring(6, 8);
			csValue = csHH + "." + csMM + "." + csSS;
			byte[] aBytes = csValue.getBytes();
			
			if(bEbcdicOutput)	// Must outout in ebcdic
				AsciiEbcdicConverter.swapByteAsciiToEbcdic(aBytes, 0, aBytes.length);
			return aBytes;
		}
		catch (SQLException e)
		{
			return null;		
		}
	}
	
//	public int setByteValue(byte arrByteValue[], int nSourceOffset, boolean bEbcdicInput, ColValueGeneric colValueGenericDest)
//	{
//		if(bEbcdicInput)	// Must outout in ebcdic
//			AsciiEbcdicConverter.swapByteEbcdicToAscii(arrByteValue, nSourceOffset, 8);
//		String cs = new String(arrByteValue, nSourceOffset, 8);
//		colValueGenericDest.setValue(cs);
//		return 8;
//	}
	
	public int setByteValueInStmtCol(DbColDefErrorManager dbColDefErrorManager, DbPreparedStatement stmt, int nCol, byte arrByteValue[], int nSourceOffset, boolean bEbcdicInput)
	{
		if(bEbcdicInput)	// Must outout in ebcdic
			AsciiEbcdicConverter.swapByteEbcdicToAscii(arrByteValue, nSourceOffset, 8);
		String cs = new String(arrByteValue, nSourceOffset, 8);
		
		CurrentDateInfo cd = new CurrentDateInfo();
		cd.setHourHHDotMMDotSS(cs);	// csValue must be of type HH.MM.SS
		long lValue = cd.getTimeInMillis();				
		Date date = new Date(lValue);							
		stmt.setDateTime(nCol, date);
		
		return 8;		
	}
	
	public boolean fillCallableStatementParam(int nParamId, StoredProcParamDescBase storedProcParamDescBase, DbPreparedCallableStatement callableStatement)
	{
		String cs = storedProcParamDescBase.getInValueAsString();
		return callableStatement.setInValue(nParamId, cs);
	}
	
	public byte[] getExcelValue(ResultSet resultSet, int nCol1Based, boolean bEbcdicOutput)
	{
		try
		{
			String csValue = resultSet.getString(nCol1Based);
			// JDBC returns date as HH:MM:SS
			String csHH = csValue.substring(0, 2);
			String csMM = csValue.substring(3, 5);
			String csSS = csValue.substring(6, 8);
			csValue = csHH + "." + csMM + "." + csSS;
			csValue = "\"" + csValue + "\"";
			byte[] aBytes = csValue.getBytes();			
			if(bEbcdicOutput)	// Must outout in ebcdic
				AsciiEbcdicConverter.swapByteAsciiToEbcdic(aBytes, 0, aBytes.length);
			return aBytes;
		}
		catch (SQLException e)
		{
			return null;
		}
	}
}