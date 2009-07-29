/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 17 mars 2005
 *
 * TODO To change the template for this generated file go7 to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;


import jlib.misc.Comp3Support;
import nacaLib.debug.BufferSpy;
import nacaLib.sqlSupport.CSQLItemType;
import nacaLib.tempCache.CStr;
import nacaLib.tempCache.CStrNumber;
import nacaLib.tempCache.TempCacheLocator;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


/*
http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_029.htm#index_x_737
MOVES:
 Editing, De-Editing, and Data Conversion During Elementary Moves 


Editing, de-editing, or other required internal data conversions occur during elementary moves. They are controlled by the description of dest-item. 
When dest-item is alphanumeric or alphanumeric edited, alignment and space-filling occur according to the Standard Alignment Rules. 
If lit or src-item is signed numeric, the operational sign is not moved. If the operational sign occupies a separate character position: 
The sign character is not moved. 
The size of lit or src-item is considered to be one less than its actual size (in terms of Standard Data Format characters). 

If the sending operand is numeric and contains the PICTURE symbol (P), all digit positions specified with this symbol are considered to have the value zero and are counted in the size of the sending operand. 
When dest-item is numeric or numeric edited, decimal point alignment and zero-filling occur according to the Standard Alignment Rules. 
When dest-item is a signed numeric item, the sign from lit or src-item is placed in it. If the sending item is unsigned, a positive sign is placed in dest-item. 
When dest-item is an unsigned numeric item, the absolute value of lit or src-item is moved. 
When lit or src-item is alphanumeric, the move occurs as if the sending item were described as an unsigned numeric integer. 
When src-item is numeric edited, the compiler de-edits it before moving it to dest-item. Src-item can be signed. 
When dest-item is alphabetic, justification and space-filling occur according to the Standard Alignment Rules. 
Nonelementary Moves 


A nonelementary move occurs as if it were an alphanumeric-to-alphanumeric elementary move. However, there is no internal data conversion. The move is not affected by individual elementary or group items in either src-item or dest-item, except as noted in the General Rules for the OCCURS clause. 
Summary 


Table 6-13 summarizes the valid types of MOVE statements. References after slash marks show the applicable General Rule. For example, moving a numeric edited item to an alphabetic item is invalid because of General Rule 9b. 

Table 6-13 Valid MOVE Statements   Category of Receiving Data Item (dest-item)  
Category of Sending 
Data Item 
(lit or src-item)  Alphabetic  Alphanumeric Edited 
Alphanumeric  Numeric Integer 
Numeric Noninteger 
Numeric Edited  
Alphabetic  Yes/13  Yes/11  No/9a  
Alphanumeric  Yes/13  Yes/11  Yes/12  
Alphanumeric Edited  Yes/13  Yes/11  No/9a  
Numeric Integer  No/9b  Yes/11  Yes/12  
Numeric Noninteger  No/9b  No/9c  Yes/12  
Numeric Edited  No/9b  Yes/11  Yes/12  
*/

public abstract class VarDefNum extends VarDefVariable 
{
	protected VarDefNum()
	{
		super();
	}
	
	public VarDefNum(VarDefBase varDefParent, VarLevel varLevel)
	{
		super(varDefParent, varLevel);	//declareType9.m_varLevel);
	}
	
//	VarDefNum(VarDefNum varDefSource)
//	{
//		super(varDefSource);
//	}
	
	
//	CStr getAsDecodedString(VarBufferPos buffer)
//	{
//		String cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
//		return cs;
//	}
	
	CStr getAsDecodedString(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
		return cs;
	}
	
	// Comp0	
//	protected int internalWriteDecComp0(VarBufferPos buffer, Dec decValue, int nNbDigitInteger, int nNbDigitDecimal)
//	{
//		int nPosition = RWNumIntComp0.internalWriteAbsoluteIntComp0AsLong(buffer, decValue.getUnsignedLong(), buffer.m_nAbsolutePosition, nNbDigitInteger);
//		String csValueDec = decValue.getDecPart();
//		nPosition = internalWriteRightPadding(buffer, nPosition, nNbDigitDecimal, csValueDec, '0');
//		return nPosition;
//	}
	
	protected int internalWriteDecComp0(VarBufferPos buffer, int nOffset, Dec decValue, int nNbDigitInteger, int nNbDigitDecimal)
	{
		int nPosition = RWNumIntComp0.internalWriteAbsoluteIntComp0AsLong(buffer, nOffset, decValue.getUnsignedLong(), buffer.m_nAbsolutePosition, nNbDigitInteger);
		String csValueDec = decValue.getDecPart();
		nPosition = internalWriteRightPadding(buffer, nPosition, nNbDigitDecimal, csValueDec, '0');
		return nPosition;
	}
	
	protected void internalWriteEmbeddedComp0Sign(VarBufferPos buffer, boolean bPositive)
	{
		// Embebbed sign in rightmost digit
		// Cobol:
		// +123 		F1 F2 C3
		// -4321        F4 F3 F2 D1
		// CJMap:
		// +123 		'1' '2' '3'
		// -4321        '4' '3' '2' 0xF0+'1'
		int nPos = buffer.m_nAbsolutePosition+m_nTotalSize-1;
		char cRightMost = buffer.m_acBuffer[nPos];
		//char cRightMost = buffer.getCharAt(nPos);
		int nDigit = cRightMost - '0'; 
		if(bPositive)
			nDigit += 0xC0;
		else
			nDigit += 0xD0;
		
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nPos, 1);
		buffer.m_acBuffer[nPos] = (char)nDigit;
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
		//buffer.setCharAt(nPos, (char)nDigit);
	}
	
	protected void internalWriteEmbeddedComp0Sign(VarBufferPos buffer, int nOffset, boolean bPositive)
	{
		// Embebbed sign in rightmost digit
		// Cobol:
		// +123 		F1 F2 C3
		// -4321        F4 F3 F2 D1
		// CJMap:
		// +123 		'1' '2' '3'
		// -4321        '4' '3' '2' 0xF0+'1'
		int nPos = buffer.m_nAbsolutePosition+m_nTotalSize-1+nOffset;
		char cRightMost = buffer.m_acBuffer[nPos];
		int nDigit = cRightMost - '0'; 
		if(bPositive)
			nDigit += 0xC0;
		else
			nDigit += 0xD0;
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(buffer.m_acBuffer, nPos, 1);
		buffer.m_acBuffer[nPos] = (char)nDigit;
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
	}

	

	
//	protected int readIntComp0(VarBuffer buffer, int nAbsolutePosition, int nTotalSize)
//	{
//		int n = internalReadSignedIntComp0(buffer, nAbsolutePosition, nTotalSize);
//		if(n < 0)
//			n = -n;
//		return n;
//	}
	
//	protected int readSignIntComp0(VarBuffer buffer, int nAbsolutePosition, int nTotalSize)
//	{
//		String cs = buffer.getStringAt(nAbsolutePosition, nTotalSize);
//		int nValue = NumberParser.getAsInt(cs);
//		return nValue;
//	}
	


	
	protected int internalReadSignedIntComp0(VarBufferPos buffer, int nAbsolutePosition, int nTotalSize)
	{
//		String cs = buffer.getStringAt(nAbsolutePosition, nTotalSize-1);
//		int nValue = NumberParser.getAsInt(cs);
		
		int nValue = buffer.getAsInt(nAbsolutePosition, nTotalSize-1);
		nValue *= 10;
		
		char cDigitSign = buffer.m_acBuffer[nAbsolutePosition+nTotalSize-1];
		//char cDigitSign = buffer.getCharAt(nAbsolutePosition+nTotalSize-1);
		int nDigit = 0;
		if(cDigitSign >= 0xD0)
		{
			nDigit = cDigitSign - 0xD0;
			nValue += nDigit;
			nValue = -nValue;
			return nValue;
		}
		else if(cDigitSign >= 0xC0)
		{
			nDigit = cDigitSign - 0xC0;
			nValue += nDigit;
			return nValue;
		}
		nDigit = cDigitSign - '0';
		nValue += nDigit;
		return nValue;
	}
	
	protected int internalReadUnsignedIntComp0(VarBufferPos buffer, int nAbsolutePosition, int nTotalSize)
	{
//		String cs = buffer.getStringAt(nAbsolutePosition, nTotalSize-1);
//		int nValue = NumberParser.getAsInt(cs);
		
		int nValue = buffer.getAsUnsignedInt(nAbsolutePosition, nTotalSize-1);
		nValue *= 10;
		
		char cDigitSign = buffer.m_acBuffer[nAbsolutePosition+nTotalSize-1];
		//char cDigitSign = buffer.getCharAt(nAbsolutePosition+nTotalSize-1);
		int nDigit = 0;
		if(cDigitSign >= 0xD0)
		{
			nDigit = cDigitSign - 0xD0;
			nValue += nDigit;
			return nValue;
		}
		else if(cDigitSign >= 0xC0)
		{
			nDigit = cDigitSign - 0xC0;
			nValue += nDigit;
			return nValue;
		}
		nDigit = cDigitSign - '0';
		nValue += nDigit;
		return nValue;
	}
	
	protected long internalReadSignedIntComp0AsLong(VarBufferPos buffer, int nAbsolutePosition, int nTotalSize)
	{
//		String cs = buffer.getStringAt(nAbsolutePosition, nTotalSize-1);
//		long lValue = NumberParser.getAsLong(cs);
		long lValue = buffer.getAsLong(nAbsolutePosition, nTotalSize-1);
		lValue *= 10;
		
		char cDigitSign = buffer.m_acBuffer[nAbsolutePosition+nTotalSize-1];
		//char cDigitSign = buffer.getCharAt(nAbsolutePosition+nTotalSize-1);
		int nDigit = 0;
		if(cDigitSign >= 0xD0)
		{
			nDigit = cDigitSign - 0xD0;
			lValue += nDigit;
			lValue = -lValue;
			return lValue;
		}
		else if(cDigitSign >= 0xC0)
		{
			nDigit = cDigitSign - 0xC0;
			lValue += nDigit;
			return lValue;
		}
		nDigit = cDigitSign - '0';
		lValue += nDigit;
		return lValue;
	}
	
	protected long internalReadUnsignedIntComp0AsLong(VarBufferPos buffer, int nAbsolutePosition, int nTotalSize)
	{
//		String cs = buffer.getStringAt(nAbsolutePosition, nTotalSize-1);
//		long lValue = NumberParser.getAsLong(cs);
		long lValue = buffer.getAsLong(nAbsolutePosition, nTotalSize-1);
		lValue *= 10;
		
		char cDigitSign = buffer.m_acBuffer[nAbsolutePosition+nTotalSize-1];
		//char cDigitSign = buffer.getCharAt(nAbsolutePosition+nTotalSize-1);
		int nDigit = 0;
		if(cDigitSign >= 0xD0)
		{
			nDigit = cDigitSign - 0xD0;
			lValue += nDigit;
			return lValue;
		}
		else if(cDigitSign >= 0xC0)
		{
			nDigit = cDigitSign - 0xC0;
			lValue += nDigit;
			return lValue;
		}
		nDigit = cDigitSign - '0';
		lValue += nDigit;
		return lValue;
	}
	
	protected String internalReadSignedIntComp0AsString(VarBufferPos buffer, int nAbsolutePosition, int nTotalSize)
	{
		String cs = "";
		if (nTotalSize > 1)
		{ 
			cs = buffer.getStringAt(nAbsolutePosition, nTotalSize-1).getAsString();
		}
//		int nValue = NumberParser.getAsInt(cs);
//		nValue *= 10;
		
		char cDigitSign = buffer.m_acBuffer[nAbsolutePosition+nTotalSize-1];
		//char cDigitSign = buffer.getCharAt(nAbsolutePosition+nTotalSize-1);
		int nDigit = 0;
		if(cDigitSign >= 0xD0)
		{
			nDigit = cDigitSign - 0xD0 + '0';
			char cDigit = (char)nDigit;
			cs = "-" + cs + cDigit;
			return cs;
		}
		else if(cDigitSign >= 0xC0)
		{
			nDigit = cDigitSign - 0xC0 + '0';
			char cDigit = (char)nDigit;
			cs = cs + cDigit;
			return cs;
		}
		//nDigit = cDigitSign;
		//cs += nDigit;
		cs += cDigitSign;
		return cs;
	}
	
	CStr internalReadSignedIntComp0AsUnsignedString(VarBufferPos buffer, int nAbsolutePosition, int nTotalSize)
	{
		CStr cs = buffer.getStringAt(nAbsolutePosition, nTotalSize-1);
		CStrNumber csNum = TempCacheLocator.getTLSTempCache().getCStrNumber();
		csNum.set(cs, 1);
		
		char cDigitSign = buffer.m_acBuffer[nAbsolutePosition+nTotalSize-1];
		//char cDigitSign = buffer.getCharAt(nAbsolutePosition+nTotalSize-1);
		if(cDigitSign >= 0xD0)
		{
			int n = cDigitSign - 0xD0 + '0';
			char c = (char)n;
			csNum.append(c);
			return csNum;
		}
		else if(cDigitSign >= 0xC0)
		{
			int n = cDigitSign - 0xC0 + '0';
			char c = (char)n;
			csNum.append(c);
			return csNum;
		}
		else if(cDigitSign != '-' && cDigitSign != '+')  
			csNum.append(cDigitSign);
		return csNum;
	}
	
	
	// Comp3
//	public static int internalWriteEncodeComp3(VarBufferPos buffer, int nOffset, String s, boolean bPositive, boolean bSigned)
//	{
//		int nStringLength = s.length();
//		int n = 0;
//		int nCharDest = 0;
//		
//		char cHigh = 0; 
//		int nHigh = 0;
//		char cLow = 0;
//		int nLow = 0;			
//		while(n < nStringLength)
//		{
//			cHigh = s.charAt(n);
//			nHigh = cHigh - '0'; 
//			n++;
//			
//			if(n == nStringLength)	// No more digit, but the sign
//			{
//				if(bSigned)
//				{
//					if(bPositive)
//						nLow = 12;	// C is encoded sign for +
//					else
//						nLow = 13;	// D is encoded sign for -
//				}
//				else
//					nLow = 15;	// F is encoded sign for usigned
//			}
//			else
//			{
//				cLow = s.charAt(n);
//				nLow = cLow - '0';
//			}
//
//			int nChar = (nHigh * 16) + nLow;
//			char cChar = (char)nChar;  
//			buffer.setCharAt(buffer.m_nAbsolutePosition+nCharDest+nOffset, cChar);
//						 
//			n++;
//			nCharDest++;
//		}
//		return buffer.m_nAbsolutePosition+nCharDest+nOffset;
//	}
	
//	protected int internalReadIntSignComp3(VarBufferPos buffer, int nNbDigitInteger)
//	{
//		int nValue = Pic9Comp3BufferSupport.getAsInt(buffer, nNbDigitInteger, m_nTotalSize);
//		return nValue;
////		
////		String s = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
////		s = StringUtil.decodeSignComp3String(s, nNbDigitInteger);
////		int nInt = NumberParser.getAsInt(s);
////		return nInt;
//	}
	
//	protected long internalReadIntSignComp3AsLong(VarBufferPos buffer, int nNbDigits)
//	{
//		long lValue = Pic9Comp3BufferSupport.getAsLong(buffer, nNbDigits, m_nTotalSize);
//		return lValue;
////		String s = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
////		s = StringUtil.decodeSignComp3String(s, nNbDigits);
////		long l = NumberParser.getAsLong(s);
////		return l;
//	}
	
//	protected int internalReadIntComp3(VarBufferPos buffer, int nNbDigitInteger)
//	{
//		int nValue = Pic9Comp3BufferSupport.getAsInt(buffer, nNbDigitInteger, m_nTotalSize);
//		return nValue;
////		
////		String s = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
////		s = StringUtil.decodeComp3String(s, nNbDigitInteger);
////		int nInt = NumberParser.getAsInt(s);
////		return nInt;
//	}
		
//	protected long internalReadIntComp3AsLong(VarBufferPos buffer, int nNbDigitInteger)
//	{
//		long lValue = Pic9Comp3BufferSupport.getAsLong(buffer, nNbDigitInteger, m_nTotalSize);
//		return lValue;
//		
////		String s = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
////		s = StringUtil.decodeComp3String(s, nNbDigitInteger);
////		long l = NumberParser.getAsLong(s);
////		return l;
//	}
	
		
//	protected int writeIntComp3(VarBufferPos buffer, int nValue, int nNbDigitInteger)
//	{
//		if(nValue < 0)
//			nValue = -nValue;
//		
//		String sAbsIntValue = String.valueOf(nValue);
//		String s = encodeStringComp3(sAbsIntValue, nNbDigitInteger);
//		return internalWriteEncodeComp3(buffer, 0, s, true, false);
//	}
	
//	protected void writeIntComp3(VarBufferPos buffer, int nValue, int nNbDigitInteger)
//	{
//		Pic9Comp3BufferSupport.setFromRightToLeft(buffer, nNbDigitInteger, m_nTotalSize, 0, false, nValue);
//	}
	
//	protected int writeIntComp3(VarBufferPos buffer, int nOffset, int nValue, int nNbDigitInteger)
//	{
//		if(nValue < 0)
//			nValue = -nValue;
//		
//		String sAbsIntValue = String.valueOf(nValue);
//		String s = encodeStringComp3(sAbsIntValue, nNbDigitInteger);
//		return internalWriteEncodeComp3(buffer, nOffset, s, true, false);
//	}
	
//	protected void writeIntComp3(VarBufferPos buffer, int nOffset, int nValue, int nNbDigitInteger)
//	{
//		Pic9Comp3BufferSupport.setFromRightToLeft(buffer, nNbDigitInteger, m_nTotalSize, nOffset, false, nValue);
//	}
	
//	protected int writeIntComp3AsLong(VarBufferPos buffer, long lValue, int nNbDigitInteger)
//	{
//		if(lValue < 0)
//			lValue = -lValue;
//		
//		String sAbsIntValue = String.valueOf(lValue);
//		String s = encodeStringComp3(sAbsIntValue, nNbDigitInteger);
//		return internalWriteEncodeComp3(buffer, 0, s, true, false);
//	}
	
//	protected void writeIntComp3AsLong(VarBufferPos buffer, long lValue, int nNbDigitInteger)
//	{
//		Pic9Comp3BufferSupport.setFromRightToLeft(buffer, nNbDigitInteger, m_nTotalSize, 0, false, lValue);
//	}
	
//	protected int writeIntComp3AsLong(VarBufferPos buffer, int nOffset, long lValue, int nNbDigitInteger)
//	{
//		if(lValue < 0)
//			lValue = -lValue;
//		
//		String sAbsIntValue = String.valueOf(lValue);
//		String s = encodeStringComp3(sAbsIntValue, nNbDigitInteger);
//		return internalWriteEncodeComp3(buffer, nOffset, s, true, false);
//	}
	
//	protected void writeIntComp3AsLong_TOTO(VarBufferPos buffer, int nOffset, long lValue, int nNbDigitInteger)
//	{
//		Pic9Comp3BufferSupport.setFromRightToLeft(buffer, nNbDigitInteger, m_nTotalSize, nOffset, false, lValue);
//	}

		
//	protected int writeIntSignComp3(VarBufferPos buffer, int nValue, int nNbDigitInteger)
//	{
//		boolean bPositive = true;
//		if(nValue < 0)
//		{
//			nValue = -nValue;
//			bPositive = false;
//		}	
//		String sAbsIntValue = String.valueOf(nValue);
//		String s = encodeStringComp3(sAbsIntValue, nNbDigitInteger);
//		return internalWriteEncodeComp3(buffer, 0, s, bPositive, true);
//	}
	
//	protected void writeIntSignComp3(VarBufferPos buffer, int nValue, int nNbDigitInteger)
//	{
//		Pic9Comp3BufferSupport.setFromRightToLeftSigned(buffer, nNbDigitInteger, m_nTotalSize, nValue);
//	}
	
//	protected void writeIntSignComp3(VarBufferPos buffer, String csValue, int nNbDigitInteger)
//	{
//		Pic9Comp3BufferSupport.setFromRightToLeft(buffer, nNbDigitInteger, m_nTotalSize, 0, true, csValue);
//	}
//	
//	protected int writeIntSignComp3(VarBufferPos buffer, int nOffset, int nValue, int nNbDigitInteger)
//	{
//		boolean bPositive = true;
//		if(nValue < 0)
//		{
//			nValue = -nValue;
//			bPositive = false;
//		}	
//		String sAbsIntValue = String.valueOf(nValue);
//		String s = encodeStringComp3(sAbsIntValue, nNbDigitInteger);
//		return internalWriteEncodeComp3(buffer, nOffset, s, bPositive, true);
//	}
	
//	protected void writeIntSignComp3(VarBufferPos buffer, int nOffset, int nValue, int nNbDigitInteger)
//	{
//		Pic9Comp3BufferSupport.setFromRightToLeft(buffer, nNbDigitInteger, m_nTotalSize, nOffset, true, nValue);
//	}
	
//	protected int writeIntSignComp3AsLong(VarBufferPos buffer, long lValue, int nNbDigitInteger)
//	{
//		return writeIntSignComp3AsLong(buffer, 0, lValue, nNbDigitInteger);
//	}
//	
//	protected int writeIntSignComp3AsLong(VarBufferPos buffer, int nOffset, long lValue, int nNbDigitInteger)
//	{
//		boolean bPositive = true;
//		if(lValue < 0)
//		{
//			lValue = -lValue;
//			bPositive = false;
//		}	
//		
//		String sAbsIntValue = String.valueOf(lValue);
//		String s = encodeStringComp3(sAbsIntValue, nNbDigitInteger);
//		return internalWriteEncodeComp3(buffer, nOffset, s, bPositive, true);
//	}
	
	// Comp4
	int internalReadIntSignComp4(VarBufferPos buffer)
	{
		int nBinaryNumberStorage = getSingleItemRequiredStorageSize();
		if(nBinaryNumberStorage == 4)	// int
		{				
			int n = buffer.getIntAt(buffer.m_nAbsolutePosition);
			return n;
		}	
		else if(nBinaryNumberStorage == 2)	// short
		{
			short s = buffer.getShortAt(buffer.m_nAbsolutePosition);
			return s;
		}
		else // long
		{			
			long l = buffer.getLongAt(buffer.m_nAbsolutePosition);
			return (int)l;
		}
	}
	
	int internalReadIntSignComp4WithMaxDigits(VarDefBase varDef, VarBufferPos buffer, int nNbDigitsToKeep)
	{
		long lValue;
		int nBinaryNumberStorage = getSingleItemRequiredStorageSize();
		if(nBinaryNumberStorage == 4)	// int
		{				
			lValue = (long)buffer.getIntAt(buffer.m_nAbsolutePosition);
		}	
		else if(nBinaryNumberStorage == 2)	// short
		{
			lValue = (long)buffer.getShortAt(buffer.m_nAbsolutePosition);
		}
		else // long
		{			
			lValue = buffer.getLongAt(buffer.m_nAbsolutePosition);	
		}
		// lValue = Pic9Comp3BufferSupport.keepRightMostDigits(varDef, lValue, nNbDigitsToKeep);
		return (int)lValue;
	}
	
	long internalReadIntSignComp4AsLongWithMaxDigits(VarDefBase varDef, VarBufferPos buffer, int nNbDigitsToKeep)
	{
		long lValue;
		int nBinaryNumberStorage = getSingleItemRequiredStorageSize();
		if(nBinaryNumberStorage == 4)	// int
		{				
			lValue = (long)buffer.getIntAt(buffer.m_nAbsolutePosition);
		}	
		else if(nBinaryNumberStorage == 2)	// short
		{
			lValue = (long)buffer.getShortAt(buffer.m_nAbsolutePosition);
		}
		else // long
		{			
			lValue = buffer.getLongAt(buffer.m_nAbsolutePosition);	
		}
		// lValue = Pic9Comp3BufferSupport.keepRightMostDigits(varDef, lValue, nNbDigitsToKeep);
		return lValue;
	}
	
	long internalReadIntSignComp4AsLong(VarBufferPos buffer)
	{
		int nBinaryNumberStorage = getSingleItemRequiredStorageSize();
		if(nBinaryNumberStorage == 4)	// int
		{				
			int n = buffer.getIntAt(buffer.m_nAbsolutePosition);
			return n;
		}	
		else if(nBinaryNumberStorage == 2)	// short
		{
			short s = buffer.getShortAt(buffer.m_nAbsolutePosition);
			return s;
		}
		else // long
		{			
			long l = buffer.getLongAt(buffer.m_nAbsolutePosition);
			return l;
		}
	}
	

	
	void writeAndFill(VarBufferPos buffer, char c)
	{
		assertIfFalse(false);
	}

	
	public boolean isAlphabetic(VarBufferPos buffer)
	{
		return false;  
	}
	
	protected CSQLItemType getDecimalSQLType(int nNbDigitInteger, int nNbDigitDecimal)
	{
		return CSQLItemType.SQL_TYPE_DOUBLE;
	}
	
	protected CSQLItemType getIntegerSQLType(int nNbDigitInteger)
	{
		if(IntLongDeterminator.isIntEnough(nNbDigitInteger))
			return CSQLItemType.SQL_TYPE_INTEGER;
		return CSQLItemType.SQL_TYPE_LONG_INTEGER;
	}

	protected int getSingleItemRequiredStorageSizeForComp4(int nNbDigitInteger)
	{
		if(nNbDigitInteger <= 4)
			return 2;	// store in a short
		else if(nNbDigitInteger <= 9)	// store in a int
			return 4;
		return 8;	// store in a long
	}
}


