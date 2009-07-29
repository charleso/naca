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

import java.sql.ResultSet;
import java.sql.SQLException;

import jlib.misc.AsciiEbcdicConverter;
import jlib.misc.LittleEndingUnsignBinaryBufferStorage;
import jlib.misc.NumberParser;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class DbColDefinitionDouble extends BaseDbColDefinition
{
	DbColDefinitionDouble(ColDescriptionInfo colDescription)
	{
		super(colDescription);
	}
	
	public byte[] getByteValue(ResultSet resultSet, int nCol1Based, boolean bEbcdicOutput)
	{
		try
		{
			String csValue = resultSet.getString(nCol1Based);
			long lValue = NumberParser.getAsLong(csValue);
			byte aBytes[] = new byte[8];
			LittleEndingUnsignBinaryBufferStorage.writeLong(aBytes, lValue, 0);
			return aBytes;
		}
		catch (SQLException e)
		{
			return null;		
		}
	}
	
//	public int setByteValue(byte arrByteValue[], int nSourceOffset, boolean bEbcdicInput, ColValueGeneric colValueGenericDest)
//	{
//		long lValue = LittleEndingUnsignBinaryBufferStorage.readLong(arrByteValue, nSourceOffset);
//		colValueGenericDest.setValue(lValue);
//		
//		return 8;
//	}
	
	public int setByteValueInStmtCol(DbColDefErrorManager dbColDefErrorManager, DbPreparedStatement stmt, int nCol, byte arrByteValue[], int nSourceOffset, boolean bEbcdicInput)
	{	
		long lOriginalValue = LittleEndingUnsignBinaryBufferStorage.readLong(arrByteValue, nSourceOffset);
		stmt.setColParam(nCol, lOriginalValue);
		
		return 8;
	}
	
	public boolean fillCallableStatementParam(int nParamId, StoredProcParamDescBase storedProcParamDescBase, DbPreparedCallableStatement callableStatement)
	{
		double d = storedProcParamDescBase.getInValueAsDouble();
		return callableStatement.setInValue(nParamId, d);
	}
	
	public byte[] getExcelValue(ResultSet resultSet, int nCol1Based, boolean bEbcdicOutput)
	{
		try
		{
			String csValue = resultSet.getString(nCol1Based);
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