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

import java.sql.*;

import jlib.misc.AsciiEbcdicConverter;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class DbColDefinitionChar extends BaseDbColDefinition
{
	private int m_nLength = 0;
	
	DbColDefinitionChar(ColDescriptionInfo colDescription)
	{
		super(colDescription);
		m_nLength = colDescription.getPrecision();
	}
	
	public byte[] getByteValue(ResultSet resultSet, int nCol1Based, boolean bEbcdicOutput)
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
	
//	public int setByteValue(byte arrByteValue[], int nSourceOffset, boolean bEbcdicInput, ColValueGeneric colValueGenericDest)
//	{
//		if(bEbcdicInput)	// Must outout in ebcdic
//			AsciiEbcdicConverter.swapByteEbcdicToAscii(arrByteValue, nSourceOffset, m_nLength);	
//		String cs = new String(arrByteValue, nSourceOffset, m_nLength);
//		colValueGenericDest.setValue(cs);
//		
//		return m_nLength;
//	}
	
	public int setByteValueInStmtCol(DbColDefErrorManager dbColDefErrorManager, DbPreparedStatement stmt, int nCol, byte arrByteValue[], int nSourceOffset, boolean bEbcdicInput)
	{
		if(bEbcdicInput)	// Must outout in ebcdic
			AsciiEbcdicConverter.swapByteEbcdicToAscii(arrByteValue, nSourceOffset, m_nLength);	
		String csValue = new String(arrByteValue, nSourceOffset, m_nLength);
		stmt.setColParam(nCol, csValue);
		
		return m_nLength;
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
