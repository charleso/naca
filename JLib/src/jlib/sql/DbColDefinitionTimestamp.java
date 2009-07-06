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
import java.sql.Timestamp;

import jlib.misc.AsciiEbcdicConverter;
import jlib.misc.CurrentDateInfo;
import jlib.misc.DateUtil;
import jlib.misc.StringUtil;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: DbColDefinitionTimestamp.java,v 1.6 2007/03/16 08:41:50 u930di Exp $
 */
public class DbColDefinitionTimestamp extends BaseDbColDefinition
{
	DbColDefinitionTimestamp(ColDescriptionInfo colDescription)
	{
		super(colDescription);
	}

	public byte[] getByteValue(ResultSet resultSet, int nCol1Based, boolean bEbcdicOutput)
	{
		try
		{
			Timestamp ts = resultSet.getTimestamp(nCol1Based);
			String csValue = new DateUtil("yyyy-MM-dd-HH.mm.ss.", new java.util.Date(ts.getTime())).toString();

			Integer intNanos = new Integer(ts.getNanos()/1000);
			String csNano = intNanos.toString();
			String csNanoPadded = StringUtil.leftPad(csNano, 6, '0');

			csValue += csNanoPadded;
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
//			AsciiEbcdicConverter.swapByteEbcdicToAscii(arrByteValue, nSourceOffset, 26);	
//		String cs = new String(arrByteValue, nSourceOffset, 26);
//		colValueGenericDest.setValue(cs);
//		return 26;
//	}
	
	public int setByteValueInStmtCol(DbColDefErrorManager dbColDefErrorManager, DbPreparedStatement stmt, int nCol, byte arrByteValue[], int nSourceOffset, boolean bEbcdicInput)
	{
		if(bEbcdicInput)	// Must outout in ebcdic
			AsciiEbcdicConverter.swapByteEbcdicToAscii(arrByteValue, nSourceOffset, 26);	
		String cs = new String(arrByteValue, nSourceOffset, 26);
		stmt.setColParam(nCol, cs);
		return 26;
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
			Timestamp ts = resultSet.getTimestamp(nCol1Based);
			String csValue = new DateUtil("yyyy-MM-dd-HH.mm.ss.", new java.util.Date(ts.getTime())).toString();

			Integer intNanos = new Integer(ts.getNanos()/1000);
			String csNano = intNanos.toString();
			String csNanoPadded = StringUtil.leftPad(csNano, 6, '0');
			csValue += csNanoPadded;			
			csValue = "\"" + csValue + "\"";
			byte[] aBytes = csValue.getBytes();	// 26 bytes
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