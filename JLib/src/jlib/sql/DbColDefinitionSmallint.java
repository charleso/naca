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

import java.sql.ResultSet;
import java.sql.SQLException;

import jlib.misc.AsciiEbcdicConverter;
import jlib.misc.BasePic9Comp3BufferSupport;
import jlib.misc.LittleEndingUnsignBinaryBufferStorage;
import jlib.misc.NumberParser;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class DbColDefinitionSmallint extends BaseDbColDefinition
{
	private int m_nNbDigits = 0;
	
	DbColDefinitionSmallint(ColDescriptionInfo colDescription)
	{
		super(colDescription);
		m_nNbDigits = colDescription.getPrecision();
	}
	
	public byte[] getByteValue(ResultSet resultSet, int nCol1Based, boolean bEbcdicOutput)
	{
		try
		{
			String csValue = resultSet.getString(nCol1Based);
			int nValue = NumberParser.getAsShort(csValue);
			byte aBytes[] = new byte[2];
			LittleEndingUnsignBinaryBufferStorage.writeUnsignedShort(aBytes, nValue, 0);
			return aBytes;
		}
		catch (SQLException e)
		{
			return null;		
		}
	}
	
//	public int setByteValue(byte arrByteValue[], int nSourceOffset, boolean bEbcdicInput, ColValueGeneric colValueGenericDest)
//	{
//		int nValue = LittleEndingUnsignBinaryBufferStorage.readShort(arrByteValue, nSourceOffset);
//		colValueGenericDest.setValue(nValue);
//		
//		return 2;
//	}
	
	public int setByteValueInStmtCol(DbColDefErrorManager dbColDefErrorManager, DbPreparedStatement stmt, int nCol, byte arrByteValue[], int nSourceOffset, boolean bEbcdicInput)
	{	
		long lOriginalValue = LittleEndingUnsignBinaryBufferStorage.readShort(arrByteValue, nSourceOffset);
		long lValue = BasePic9Comp3BufferSupport.keepRightMostDigits(lOriginalValue, m_nNbDigits);
		stmt.setColParam(nCol, (short)lValue);
		if(lOriginalValue != lValue)
			dbColDefErrorManager.reportTruncationError(lOriginalValue, lValue, getColumnName());

		return 2;
	}
	
	public boolean fillCallableStatementParam(int nParamId, StoredProcParamDescBase storedProcParamDescBase, DbPreparedCallableStatement callableStatement)
	{
		short s = storedProcParamDescBase.getInValueAsShort();
		return callableStatement.setInValue(nParamId, s);
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