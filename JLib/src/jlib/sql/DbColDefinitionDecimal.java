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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import jlib.log.Asserter;
import jlib.misc.AsciiEbcdicConverter;
import jlib.misc.BasePic9Comp3BufferSupport;
import jlib.misc.Comp3Support;
import jlib.misc.DecBase;
import jlib.misc.NumberParser;



/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: DbColDefinitionDecimal.java,v 1.7 2007/05/08 16:53:12 u930di Exp $
 */
public class DbColDefinitionDecimal extends BaseDbColDefinition
{
	private int m_nNbDigits = 0;
	private int m_nNbDecimals = 0;
	private boolean m_rbNegative[] = null;
	
	DbColDefinitionDecimal(ColDescriptionInfo colDescription)
	{
		super(colDescription);
		m_nNbDigits = colDescription.getPrecision();
		m_nNbDecimals = colDescription.getScale();
		m_rbNegative = new boolean[1];
	}
	
	public byte[] getByteValue(ResultSet resultSet, int nCol1Based, boolean bEbcdicOutput)
	{
		try
		{
			ResultSetMetaData rsMetaData = resultSet.getMetaData();

			int nNbDigits = rsMetaData.getPrecision(nCol1Based);
			int nNbDecimals = rsMetaData.getScale(nCol1Based);
			Asserter.assertIfFalse(nNbDigits == m_nNbDigits);
			Asserter.assertIfFalse(nNbDecimals == m_nNbDecimals);
	
			if((nNbDigits % 2) == 0)
				nNbDigits++;
			int nNbCharsInComp3 = (nNbDigits / 2) + 1;
			
			BigDecimal bd = resultSet.getBigDecimal(nCol1Based);
			DecBase decValue = DecBase.toDec(bd);
			
			byte [] aBytes = new byte[nNbCharsInComp3];
			
			boolean bPositive = !decValue.isNegative();
			String cs = Comp3Support.encodeDecComp3(decValue, m_nNbDigits-m_nNbDecimals, m_nNbDecimals);
			Comp3Support.internalWriteEncodeComp3(aBytes, cs, bPositive, true);
						
			//m_nPhysicalPosInRecordSet += nNbCharsInComp3;
			return aBytes;					
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
//	public int setByteValue(byte arrByteValue[], int nSourceOffset, boolean bEbcdicInput, ColValueGeneric colValueGenericDest)
//	{
//		int nSize = 1+(m_nNbDigits / 2);
//		String cs = getAsString(arrByteValue, nSourceOffset, m_nNbDigits, m_nNbDecimals, nSize);
//		colValueGenericDest.setValue(cs);
//		
//		return nSize;
//	}
	
	public int setByteValueInStmtCol(DbColDefErrorManager dbColDefErrorManager, DbPreparedStatement stmt, int nCol, byte arrByteValue[], int nSourceOffset, boolean bEbcdicInput)
	{
		int nSize = 1+(m_nNbDigits / 2);
		if(m_nNbDigits < 18) // Binary value fits in a long
		{
			if(m_nNbDecimals == 0)					
			{
				long lOriginalValue = BasePic9Comp3BufferSupport.getAsLong(arrByteValue, nSourceOffset, m_nNbDigits, nSize);
				long lValue = BasePic9Comp3BufferSupport.keepRightMostDigits(lOriginalValue, m_nNbDigits);
				if(lOriginalValue != lValue)
					dbColDefErrorManager.reportTruncationError(lOriginalValue, lValue, getColumnName());
				stmt.setColParam(nCol, lValue);
			}
			else
			{
				long lOriginalValue = BasePic9Comp3BufferSupport.getAsLong(arrByteValue, nSourceOffset, m_nNbDigits, nSize);
				long lValue = BasePic9Comp3BufferSupport.keepRightMostDigits(lOriginalValue, m_nNbDigits);
				if(lOriginalValue != lValue)
					dbColDefErrorManager.reportTruncationError(lOriginalValue, lValue, getColumnName());
				String csValue = BasePic9Comp3BufferSupport.makeDottedString(lValue, m_nNbDecimals);
//				if(csValue.startsWith("815"))
//				{
//					int n = 0;					
//				}
				stmt.setColParam(nCol, csValue);
			}
		}
		else	// Cannot use a long (64 bits is not enough ...)
		{			
			String csOriginalValue = getAsString(arrByteValue, nSourceOffset, m_nNbDigits, m_nNbDecimals, nSize, m_rbNegative);
			int nPosDot = csOriginalValue.indexOf(".");
			String csDec = "";
			String csInt;
			if(nPosDot >= 0)
			{
				csDec = csOriginalValue.substring(nPosDot);
				if(m_rbNegative[0])	// A leading sign has been added
					csInt = csOriginalValue.substring(1, nPosDot);
				else
					csInt = csOriginalValue.substring(0, nPosDot);
			}
			else
				csInt = csOriginalValue;
			int nNbDigitsInt = m_nNbDigits - m_nNbDecimals; 
			if(csInt.length() > nNbDigitsInt)	// Integer part is too long
			{
				int nNbDigitsToRemoveOnLeft = csInt.length() - nNbDigitsInt;
				String csLeft = csInt.substring(0, nNbDigitsToRemoveOnLeft);
				boolean bSignificantTruncation = false;
				if(NumberParser.getAsLong(csLeft) != 0)	// We truncates significant digits on left	
					bSignificantTruncation = true;
					
				csInt = csInt.substring(nNbDigitsToRemoveOnLeft);
				if(m_rbNegative[0])
					csInt = "-" + csInt;
				String csValue = csInt + csDec;
				if(bSignificantTruncation)
					dbColDefErrorManager.reportTruncationError(csOriginalValue, csValue, getColumnName());
				stmt.setColParam(nCol, csValue);
			}
		}
		
		return nSize;
	}
	
	private String getAsString(byte acBuffer[], int nAbsolutePosition, int nNbDigits, int nNbDecimals, int nTotalSize, boolean rbNegative[])
	{
		rbNegative[0] = false;
		StringBuilder cs = null;
		int nPosDecimalDot = -1;
		//boolean bAddLeadingDigit = true;
		int nNbDigitsInteger = nNbDigits - nNbDecimals;
		
		if(nNbDecimals == 0)
			cs = new StringBuilder(1+nNbDigits);
		else
		{
			nPosDecimalDot = nNbDigitsInteger;
			if((nNbDigits % 2) == 0)	// Even number of digits: A leading nibble has been added
				nPosDecimalDot++;
//				bAddLeadingDigit = false;
			cs = new StringBuilder(2+nNbDigits);
		}
		
		int nNbChars = nTotalSize;
		for(int n=0; n<nNbChars; n++)
		{
			int nByte = acBuffer[nAbsolutePosition + n];
			int nHigh = (nByte & 0x00F0) >> 4;
			int nLow = nByte & 0x000F;
			if(nLow < 10)
			{
//				if(!bAddLeadingDigit)
//					bAddLeadingDigit = true;	// Consume leading digit
//				else
					cs.append((char)('0' + nHigh));
				cs.append((char)('0' + nLow));
			}
			else
			{
//				if(!bAddLeadingDigit)
//					bAddLeadingDigit = true;	// Consume leading digit
//				else
					cs.append((char)('0' + nHigh));
				
				if(nLow == COMP3_SIGN_MINUS)
				{
					rbNegative[0] = true;
					if(nPosDecimalDot != -1)
						nPosDecimalDot++;	// Right shift decimal dot (if one has been set) as we have added a leading '-'
					cs.insert(0, '-');
				}
			}
		}
		if(nPosDecimalDot != -1)
			cs.insert(nPosDecimalDot, '.');
		return cs.toString();		
	}
		
//	private String getAsString(byte acBuffer[], int nAbsolutePosition, int nNbDigits, int nNbDecimals, int nTotalSize)
//	{
//		StringBuilder cs = null;
//		int nPosDecimalDot = -1;
//		boolean bAddLeadingDigit = true;
//		int nNbDigitsInteger = nNbDigits - nNbDecimals;
//		
//		if(nNbDecimals == 0)
//			cs = new StringBuilder(1+nNbDigits);
//		else
//		{
//			nPosDecimalDot = nNbDigitsInteger;
//			if((nNbDigits % 2) == 0)	// Even number of digits: A leading nibble has been added
//				bAddLeadingDigit = false;
//			cs = new StringBuilder(2+nNbDigits);
//		}
//		
//		int nNbChars = nTotalSize;
//		for(int n=0; n<nNbChars; n++)
//		{
//			int nByte = acBuffer[nAbsolutePosition + n];
//			int nHigh = (nByte & 0x00F0) >> 4;
//			int nLow = nByte & 0x000F;
//			if(nLow < 10)
//			{
//				if(!bAddLeadingDigit)
//					bAddLeadingDigit = true;	// Consume leading digit
//				else
//					cs.append((char)('0' + nHigh));
//				cs.append((char)('0' + nLow));
//			}
//			else
//			{
//				if(!bAddLeadingDigit)
//					bAddLeadingDigit = true;	// Consume leading digit
//				else
//					cs.append((char)('0' + nHigh));
//				
//				if(nLow == COMP3_SIGN_MINUS)
//				{
//					if(nPosDecimalDot != -1)
//						nPosDecimalDot++;	// Right shift decimal dot (if one has been set) as we have added a leading '-'
//					cs.insert(0, '-');
//				}
//			}
//		}
//		if(nPosDecimalDot != -1)
//			cs.insert(nPosDecimalDot, '.');
//		return cs.toString();		
//	}
	
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
	
	private final static byte COMP3_SIGN_MINUS =(byte)13;	// D is encoded sign for -
}
