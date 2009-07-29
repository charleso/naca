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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import jlib.log.Asserter;
import jlib.misc.AsciiEbcdicConverter;
import jlib.misc.LittleEndingUnsignBinaryBufferStorage;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class DbColDefinitionVarchar extends BaseDbColDefinition
{
	private int m_nLength = 0;
	
	DbColDefinitionVarchar(ColDescriptionInfo colDescription)
	{
		super(colDescription);
		m_nLength = colDescription.getPrecision();
	}
	
	public byte[] getByteValue(ResultSet resultSet, int nCol1Based, boolean bEbcdicOutput)
	{
		try
		{
			String csValue = resultSet.getString(nCol1Based);
			
			ResultSetMetaData rsMetaData = resultSet.getMetaData();

			int nColWidth = rsMetaData.getPrecision(nCol1Based);
			int nValueLength = csValue.length();
			
			byte[] aBytes = new byte[2 + nColWidth];
			Asserter.assertIfFalse(nColWidth == m_nLength);
			
			LittleEndingUnsignBinaryBufferStorage.writeUnsignedShort(aBytes, nValueLength, 0);
			
			byte[] aBytesValue = csValue.getBytes();
			if(bEbcdicOutput)	// Must outout in ebcdic
				AsciiEbcdicConverter.swapByteAsciiToEbcdic(aBytesValue, 0, nValueLength);
			
			int n=0, nDest=2;
			for(; n<aBytesValue.length; n++, nDest++)
			{
				aBytes[nDest] = aBytesValue[n];
			}
			
			while(n < nColWidth)
			{
				aBytes[nDest++] = 0;
				n++;
			}
			return aBytes;
		}
		catch (SQLException e)
		{
			return null;		
		}
	}
	
//	public int setByteValue(byte arrByteValue[], int nSourceOffset, boolean bEbcdicInput, ColValueGeneric colValueGenericDest)
//	{
//		int nLength = LittleEndingUnsignBinaryBufferStorage.readShort(arrByteValue, nSourceOffset);
//		
//		if(bEbcdicInput)	// Must outout in ebcdic
//			AsciiEbcdicConverter.swapByteEbcdicToAscii(arrByteValue, nSourceOffset, m_nLength);
//		
//		String cs = new String(arrByteValue, nSourceOffset+2, nLength);
//		colValueGenericDest.setValue(cs);
//		
//		return 2+m_nLength;
//	}
	
	public int setByteValueInStmtCol(DbColDefErrorManager dbColDefErrorManager, DbPreparedStatement stmt, int nCol, byte arrByteValue[], int nSourceOffset, boolean bEbcdicInput)
	{	
		int nLength = LittleEndingUnsignBinaryBufferStorage.readShort(arrByteValue, nSourceOffset);
		
		if(bEbcdicInput)	// Must outout in ebcdic
			AsciiEbcdicConverter.swapByteEbcdicToAscii(arrByteValue, nSourceOffset, m_nLength);
		
		String cs = new String(arrByteValue, nSourceOffset+2, nLength);
		stmt.setColParam(nCol, cs);		
		
		return 2+m_nLength;
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
			csValue = csValue.trim().replace("\"", "'");
			if (csValue.length() == 0)
				csValue = " ";
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