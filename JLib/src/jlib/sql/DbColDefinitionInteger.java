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

import java.sql.ResultSet;
import java.sql.SQLException;

import jlib.misc.AsciiEbcdicConverter;
import jlib.misc.BasePic9Comp3BufferSupport;
import jlib.misc.LittleEndingUnsignBinaryBufferStorage;
import jlib.misc.NumberParser;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: DbColDefinitionInteger.java,v 1.5 2007/03/16 08:41:50 u930di Exp $
 */
public class DbColDefinitionInteger extends BaseDbColDefinition
{
	private int m_nNbDigits = 0;
	
	DbColDefinitionInteger(ColDescriptionInfo colDescription)
	{
		super(colDescription);
		m_nNbDigits = colDescription.getPrecision();
	}
	
	public byte[] getByteValue(ResultSet resultSet, int nCol1Based, boolean bEbcdicOutput)
	{
		try
		{
			String csValue = resultSet.getString(nCol1Based);
			int nValue = NumberParser.getAsInt(csValue);
			byte aBytes[] = new byte[4];
			LittleEndingUnsignBinaryBufferStorage.writeInt(aBytes, nValue, 0);
			return aBytes;
		}
		catch (SQLException e)
		{
			return null;		
		}
	}
	
//	public int setByteValue(byte arrByteValue[], int nSourceOffset, boolean bEbcdicInput, ColValueGeneric colValueGenericDest)
//	{
//		long lValue = LittleEndingUnsignBinaryBufferStorage.readInt(arrByteValue, nSourceOffset);
//		colValueGenericDest.setValue(lValue);
//		
//		return 4;
//	}
	
	public int setByteValueInStmtCol(DbColDefErrorManager dbColDefErrorManager, DbPreparedStatement stmt, int nCol, byte arrByteValue[], int nSourceOffset, boolean bEbcdicInput)
	{	
		long lOriginalValue = LittleEndingUnsignBinaryBufferStorage.readInt(arrByteValue, nSourceOffset);
		long lValue = BasePic9Comp3BufferSupport.keepRightMostDigits(lOriginalValue, m_nNbDigits);
		if(lOriginalValue != lValue)
			dbColDefErrorManager.reportTruncationError(lOriginalValue, lValue, getColumnName());
		
		stmt.setColParam(nCol, (int)lValue);
		return 4;
	}
	
	public boolean fillCallableStatementParam(int nParamId, StoredProcParamDescBase storedProcParamDescBase, DbPreparedCallableStatement callableStatement)
	{
		int n = storedProcParamDescBase.getInValueAsInt();
		return callableStatement.setInValue(nParamId, n);
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