/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.varEx;

import java.math.BigDecimal;

import jlib.misc.*;
import nacaLib.bdb.BtreeSegmentKeyTypeFactory;
import nacaLib.mathSupport.MathAdd;
import nacaLib.misc.NumberParserDec;
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
public class VarDefNumDecSignComp4 extends VarDefNum
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public VarDefNumDecSignComp4(VarDefBase varDefParent, DeclareType9 declareType9, NumericValue numericValue)
	{
		super(varDefParent, declareType9.m_varLevel);
		m_nNbDigitInteger = numericValue.m_nNbDigitInteger;
		m_nNbDigitDecimal = numericValue.m_nNbDigitDecimal;
	}
	
	protected VarDefNumDecSignComp4()
	{
		super();
	}
	
	void transfer(VarBufferPos bufferSource, VarAndEdit Dest)
	{
		Dest.m_varDef.write(Dest.m_bufferPos, this, bufferSource);
	}
	
	protected VarDefBuffer allocCopy()
	{
		VarDefNumDecSignComp4 v = new VarDefNumDecSignComp4();
		v.m_nNbDigitInteger = m_nNbDigitInteger;
		v.m_nNbDigitDecimal = m_nNbDigitDecimal;
		return v;
	}
	
	CSQLItemType getSQLType()
	{
		return getDecimalSQLType(m_nNbDigitInteger, m_nNbDigitDecimal);
	}
	
	public int getBodyLength()
	{
		return m_nTotalSize;
	}
	
	protected int getHeaderLength()
	{
		return 0;
	}


	
	public int getSingleItemRequiredStorageSize()
	{
		int nNbTotalDigits = m_nNbDigitInteger + m_nNbDigitDecimal;  
		if(nNbTotalDigits <= 4)
			return 2;	// store in a short
		else if(nNbTotalDigits <= 8)	// store in a int
			return 4;
		return 8;	// store in a long
	}
	
	int getAsDecodedInt(VarBufferPos buffer)
	{
		int nValue = internalReadIntSignComp4(buffer);
		CStrNumber csNum = TempCacheLocator.getTLSTempCache().getCStrNumber();
		csNum.getAsAbsoluteIntComp0String(nValue, m_nNbDigitInteger);
		int n = csNum.getAsInt();
		return n;
	}
	
	int getAsDecodedUnsignedInt(VarBufferPos buffer)
	{
		int nValue = internalReadIntSignComp4(buffer);
		if(nValue < 0)
			nValue = -nValue;
		CStrNumber csNum = TempCacheLocator.getTLSTempCache().getCStrNumber();
		csNum.getAsAbsoluteIntComp0String(nValue, m_nNbDigitInteger);
		int n = csNum.getAsInt();
		return n;
	}
	
	long getAsDecodedLong(VarBufferPos buffer)
	{
		long lValue = internalReadIntSignComp4AsLong(buffer);
		CStrNumber csNum = TempCacheLocator.getTLSTempCache().getCStrNumber();
		csNum.getAsAbsoluteIntComp0StringAsLong(lValue, m_nNbDigitInteger);
		long l = csNum.getAsLong();
		if(lValue < 0)
			l = -l;
		return l;
	}
	
	Dec getAsDecodedDec(VarBufferPos buffer)
	{
		long lValue = internalReadIntSignComp4AsLong(buffer);
		CStrNumber csNum = TempCacheLocator.getTLSTempCache().getCStrNumber();
		csNum.getAsAbsoluteIntComp0StringAsLong(lValue, m_nNbDigitInteger+m_nNbDigitDecimal);
		String cs = csNum.getAsString();
		String csInt = cs.substring(0, m_nNbDigitInteger);
		String csDec = cs.substring(m_nNbDigitInteger);
		Dec dec = new Dec(csInt, csDec);
		if(lValue < 0)
			dec.setPositive(false);
		return dec;
	}
	
	CStr getAsAlphaNumString(VarBufferPos buffer)
	{
		int nValue = internalReadIntSignComp4(buffer);
		CStrNumber csNum = TempCacheLocator.getTLSTempCache().getCStrNumber();
		csNum.getAsAbsoluteIntComp0String(nValue, m_nNbDigitInteger);
		if(nValue < 0)
			csNum.insert(0, '-');
		return csNum;
	}
	
	CStr getDottedSignedString(VarBufferPos buffer)
	{
		Dec dec = getAsDecodedDec(buffer);
		CStr cs = dec.getAsCStr();
		return cs;
	}
	
	CStr getDottedSignedStringAsSQLCol(VarBufferPos buffer)
	{
		Dec dec = getAsDecodedDec(buffer);
		CStr cs = dec.getAsCStr();
		return cs;
	}
	
	void write(VarBufferPos buffer, char c)
	{
		int n = NumberParser.getAsUnsignedInt(c);
		Dec dec = new Dec(n, "");
		writeDecComp4(buffer, dec);
	}
	
	public void write(VarBufferPos buffer, String cs)
	{
		Dec dec = NumberParserDec.getAsDec(cs);
		writeDecComp4(buffer, dec);
	}
	
	public void inc(VarBufferPos buffer, int n)
	{
		CStr s1 = getDottedSignedString(buffer);
		Dec dec = MathAdd.inc(s1, n);
		write(buffer, dec);
	}
	
	public void inc(VarBufferPos buffer, BigDecimal bdStep)
	{
		CStr s1 = getDottedSignedString(buffer);
		Dec dec = MathAdd.inc(s1, bdStep);
		write(buffer, dec);
	}
	
	public void write(VarBufferPos buffer, int n)
	{
		Dec dec = new Dec(n, "");
		writeDecComp4(buffer, dec);
	}
	
	public void write(VarBufferPos buffer, long l)
	{
		Dec dec = new Dec(l, "");
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, double d)
	{
		Dec dec = NumberParserDec.getAsDec(d);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, Dec dec)
	{
		writeDecComp4(buffer, dec);
	}
	
	public void write(VarBufferPos buffer, BigDecimal bigDecimal)
	{
		Dec dec = NumberParserDec.getAsDec(bigDecimal);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefG varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If the sending item is a group item, and the receiving item is an elementary item, the compiler ignores the receiving item description except for the size description, in bytes, and any JUSTIFIED clause. It conducts no conversion or editing on the sending item's data
		internalPhysicalWrite(buffer, varSource, bufferSource); 
	}

	void write(VarBufferPos buffer, VarDefX varSource, VarBufferPos bufferSource)
	{
		long l = varSource.getAsDecodedLong(bufferSource);
		Dec dec = new Dec(l, "");
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefFPacAlphaNum varSource, VarBufferPos bufferSource)
	{
		long l = varSource.getAsDecodedLong(bufferSource);
		Dec dec = new Dec(l, "");
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefFPacRaw varSource, VarBufferPos bufferSource)
	{
		long l = varSource.getAsDecodedLong(bufferSource);
		Dec dec = new Dec(l, "");
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecComp0 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}

	void write(VarBufferPos buffer, VarDefNumDecComp3 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecComp4 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	public void moveIntoSameType(VarBufferPos buffer, VarDefBuffer varSource, VarBufferPos bufferSource)
	{
		if(m_nTotalSize == varSource.m_nTotalSize)	// Same type and same size: Directly copy bytes
		{
			VarDefNumDecSignComp4 varDefSourceSignComp4 = (VarDefNumDecSignComp4)varSource;
			if(m_nNbDigitDecimal == varDefSourceSignComp4.m_nNbDigitDecimal && m_nNbDigitInteger == varDefSourceSignComp4.m_nNbDigitInteger)
			{
				int nPositionDest = buffer.m_nAbsolutePosition;
				int nPositionSource = bufferSource.m_nAbsolutePosition;
				for(int n=0; n<m_nTotalSize; n++)
				{
					buffer.m_acBuffer[nPositionDest++] = bufferSource.m_acBuffer[nPositionSource++];
				}
				return ;
			}
		}

		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignComp4 varSource, VarBufferPos bufferSource)
	{
		if(m_nTotalSize == varSource.m_nTotalSize)	// Same type and same size: Directly copy bytes
		{
			int nPositionDest = buffer.m_nAbsolutePosition;
			int nPositionSource = bufferSource.m_nAbsolutePosition;
			for(int n=0; n<m_nTotalSize; n++)
			{
				buffer.m_acBuffer[nPositionDest++] = bufferSource.m_acBuffer[nPositionSource++];
			}
			return ;
		}

		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignComp0 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignComp3 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignLeadingComp0 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignTrailingComp0 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntComp0 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntComp0Long varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}

	
	void write(VarBufferPos buffer, VarDefNumIntComp3 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntComp3Long varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}

	
	void write(VarBufferPos buffer, VarDefNumIntComp4 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntComp4Long varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp0 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp0Long varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp3 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefFPacNumIntSignComp3 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp3Long varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp4 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp4Long varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignLeadingComp0 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignLeadingComp0Long varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignTrailingComp0 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignTrailingComp0Long varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}

	
	void write(VarBufferPos buffer, VarDefEditInMap varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefEditInMapRedefine varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefEditInMapRedefineNumEdited varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefEditInMapRedefineNum varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
	
	void write(VarBufferPos buffer, VarDefNumEdited varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		writeDecComp4(buffer, dec);
	}
		
		
//	public void initialize(VarBufferPos buffer)
//	{
//		Dec dec = new Dec(0L, "");
//		writeDecComp4(buffer, dec);
//	}
	
	public void initializeAtOffset(VarBufferPos buffer, int nOffset, InitializeCache initializeCache)
	{
		Dec dec = new Dec(0L, "");
		writeDecComp4(buffer, nOffset, dec);
		if(initializeCache != null)
			initializeCache.addItem(buffer, nOffset, getSingleItemRequiredStorageSize());
	}

	
//	void initialize(VarBufferPos buffer, String cs)
//	{
//	}
	
	void initializeAtOffset(VarBufferPos buffer, int nOffset, String cs)
	{
	}
	
	void initializeEditedAtOffset(VarBufferPos buffer, int nOffset, int nValue)
	{
	}
	
	void initializeEditedAtOffset(VarBufferPos buffer, int nOffset, double dValue)
	{
	}

		
//	void initialize(VarBufferPos buffer, int n)
//	{
//		Dec dec = new Dec(n, "");
//		writeDecComp4(buffer, dec);
//	}
	
	void initializeAtOffset(VarBufferPos buffer, int nOffset, int n)
	{
		Dec dec = new Dec(n, "");
		writeDecComp4(buffer, nOffset, dec);
	}
	
	void initializeEdited(VarBufferPos buffer, String cs)
	{
	}
	
	void initializeEdited(VarBufferPos buffer, int n)
	{
	}
	
	private int writeDecComp4(VarBufferPos buffer, Dec dec)
	{
		return writeDecComp4(buffer, 0, dec);
	}
		
	private int writeDecComp4(VarBufferPos buffer, int nOffset, Dec dec)
	{
		dec.setUnsigned();
		long lValue = dec.getSignedLong();
		String csInt = String.valueOf(lValue);
		
		int nNbDigitInt = csInt.length();
		if(nNbDigitInt > m_nNbDigitInteger)
		{
			// keep decimal rightmost digits of integer part
			csInt = csInt.substring(nNbDigitInt-m_nNbDigitInteger, nNbDigitInt);
		}
		
		String csDec = dec.getDecPart();
		int nNbDigitDec = csDec.length(); 
		while(nNbDigitDec < m_nNbDigitDecimal)
		{
			csDec += '0';
			nNbDigitDec++;
		}
		if(nNbDigitDec > m_nNbDigitDecimal)
		{
			// keep decimal leftmost digits of decimal part
			csDec = csDec.substring(0, m_nNbDigitDecimal);
		}
		
		
				
		String csIntDec = csInt + csDec;
		
		long l = NumberParser.getAsLong(csIntDec);
		int nBinaryNumberStorage = getSingleItemRequiredStorageSize();
		if(nBinaryNumberStorage == 4)	// 32 bits
		{
			int n = (short)l;
			buffer.setIntAt(buffer.m_nAbsolutePosition+nOffset, n);
			return buffer.m_nAbsolutePosition + 4+nOffset;
		}		
		else if(nBinaryNumberStorage == 2)	// short
		{
			short s = (short)l;
			buffer.setShortAt(buffer.m_nAbsolutePosition+nOffset, s);
			return buffer.m_nAbsolutePosition + 2+nOffset;
		}
		else			// long
		{
			buffer.setLongAt(buffer.m_nAbsolutePosition+nOffset, l);
			return buffer.m_nAbsolutePosition + 8+nOffset;
		}
	}

	void write(VarBufferPos buffer, CobolConstantZero cst)
	{
		Dec dec = new Dec(0L, "");
		writeDecComp4(buffer, dec);
	}

	void write(VarBufferPos buffer, CobolConstantSpace cst)
	{
		// Do nothing into numeric vars
	}

	void write(VarBufferPos buffer, CobolConstantLowValue cst)
	{
		// Do nothing into numeric vars
	}

	void write(VarBufferPos buffer, CobolConstantHighValue cst)
	{
		// Do nothing into numeric vars
	}
	
	void write(VarBufferPos buffer, CobolConstantZero cst, int nOffsetPosition, int nNbChar)
	{
	}

	void write(VarBufferPos buffer, CobolConstantSpace cst, int nOffsetPosition, int nNbChar)
	{
	}
	
	void write(VarBufferPos buffer, CobolConstantLowValue cst, int nOffsetPosition, int nNbChar)
	{
	}
	
	void write(VarBufferPos buffer, CobolConstantHighValue cst, int nOffsetPosition, int nNbChar)
	{
	}
	
	void write(VarBufferPos buffer, String csValue, int nOffsetPosition, int nNbChar)
	{		
	}
	
	
	int compare(ComparisonMode mode, VarBufferPos bufferSource, VarAndEdit var2)
	{
		return var2.m_varDef.compare(mode, var2.m_bufferPos, this, bufferSource);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp0 varDefNum1, VarBufferPos buffer1)
	{
		Dec dec1 = varDefNum1.getAsDecodedDec(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(dec1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp3 varDefNum1, VarBufferPos buffer1)
	{
		Dec dec1 = varDefNum1.getAsDecodedDec(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(dec1, dec2);
	}

	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp4 varDefNum1, VarBufferPos buffer1)
	{
		Dec dec1 = varDefNum1.getAsDecodedDec(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(dec1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp4 varDefNum1, VarBufferPos buffer1)
	{
		Dec dec1 = varDefNum1.getAsDecodedDec(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(dec1, dec2);
	}
	
	public boolean isEqualWithSameTypeTo(VarBufferPos buffer1, VarDefBuffer varDefBuffer2, VarBufferPos buffer2)
	{		
		VarDefNumDecSignComp4 varDefSourceComp4 = (VarDefNumDecSignComp4)varDefBuffer2;
		if(m_nNbDigitDecimal == varDefSourceComp4.m_nNbDigitDecimal && m_nNbDigitInteger == varDefSourceComp4.m_nNbDigitInteger)
		{
			// Same quantity of digits before and after dot
			int nPosition1 = buffer1.m_nAbsolutePosition;
			int nPosition2 = buffer2.m_nAbsolutePosition;
			for(int n=0; n<m_nTotalSize; n++)
			{
				if(buffer1.m_acBuffer[nPosition1++] != buffer2.m_acBuffer[nPosition2++])
					return false;
			}
			return true;
		}
		
		Dec dec2 = varDefBuffer2.getAsDecodedDec(buffer2);
		Dec dec1 = getAsDecodedDec(buffer1);
		if(dec1.compare(dec2) == 0)
			return true;
		return false;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp0 varDefNum1, VarBufferPos buffer1)
	{
		Dec dec1 = varDefNum1.getAsDecodedDec(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(dec1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp3 varDefNum1, VarBufferPos buffer1)
	{
		Dec dec1 = varDefNum1.getAsDecodedDec(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(dec1, dec2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignLeadingComp0 varDefNum1, VarBufferPos buffer1)
	{
		Dec dec1 = varDefNum1.getAsDecodedDec(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(dec1, dec2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignTrailingComp0 varDefNum1, VarBufferPos buffer1)
	{
		Dec dec1 = varDefNum1.getAsDecodedDec(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(dec1, dec2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp0 varDefNum1, VarBufferPos buffer1)
	{
		int n1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp0Long varDefNum1, VarBufferPos buffer1)
	{
		long l1 = varDefNum1.getAsDecodedLong(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(l1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp3 varDefNum1, VarBufferPos buffer1)
	{
		int n1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp3Long varDefNum1, VarBufferPos buffer1)
	{
		long l1 = varDefNum1.getAsDecodedLong(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(l1, dec2);
	}

	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp4 varDefNum1, VarBufferPos buffer1)
	{
		int n1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp4Long varDefNum1, VarBufferPos buffer1)
	{
		long l1 = varDefNum1.getAsDecodedLong(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(l1, dec2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp0 varDefNum1, VarBufferPos buffer1)
	{
		int n1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp0Long varDefNum1, VarBufferPos buffer1)
	{
		long l1 = varDefNum1.getAsDecodedLong(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(l1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp3 varDefNum1, VarBufferPos buffer1)
	{
		int n1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacNumIntSignComp3 varDefNum1, VarBufferPos buffer1)
	{
		int n1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp3Long varDefNum1, VarBufferPos buffer1)
	{
		long l1 = varDefNum1.getAsDecodedLong(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(l1, dec2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp4 varDefNum1, VarBufferPos buffer1)
	{
		int n1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp4Long varDefNum1, VarBufferPos buffer1)
	{
		long l1 = varDefNum1.getAsDecodedLong(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(l1, dec2);
	}


	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignLeadingComp0 varDefNum1, VarBufferPos buffer1)
	{
		int n1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignLeadingComp0Long varDefNum1, VarBufferPos buffer1)
	{
		long l1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(l1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignTrailingComp0 varDefNum1, VarBufferPos buffer1)
	{
		int n1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignTrailingComp0Long varDefNum1, VarBufferPos buffer1)
	{
		long l1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(l1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefX varDef1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		int n1 = varDef1.getUnsignedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacAlphaNum varDef1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		int n1 = varDef1.getUnsignedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacRaw varDef1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		int n1 = varDef1.getUnsignedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefG varDef1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		int n1 = varDef1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}
		
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumEdited varDef1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		// TODO how to compare with num edited ?
		return 0;
	}
	
	public String digits(VarBufferPos buffer)
	{
		long lValue = internalReadIntSignComp4AsLong(buffer);
		if(lValue < 0)
			lValue = -lValue;
		CStrNumber csNum = TempCacheLocator.getTLSTempCache().getCStrNumber();
		csNum.getAsAbsoluteIntComp0StringAsLong(lValue, m_nNbDigitInteger+m_nNbDigitDecimal);
		return csNum.getAsString();
	}
	
	public int getNbDigitDecimal()
	{
		return m_nNbDigitDecimal;
	}
	
	boolean isConvertibleInEbcdic()
	{
		return false;
	}

	public int getTypeId()
	{
		return VarTypeId.VarDefNumDecSignComp4;
	}
	
	public BtreeSegmentKeyTypeFactory getSegmentKeyTypeFactory()
	{
		return VarTypeId.m_segmentKeyTypeFactorySignComp4;
	}

	public boolean isEbcdicAsciiConvertible()
	{
		return false;
	}
	
	protected void adjustCustomProperty(VarDefBuffer varDefBufferCopySingleItem)
	{
		VarDefNumDecSignComp4 varDefCopy = (VarDefNumDecSignComp4)varDefBufferCopySingleItem;
		varDefCopy.m_nNbDigitInteger = m_nNbDigitInteger;
		varDefCopy.m_nNbDigitDecimal = m_nNbDigitDecimal;
	}
	
	
	protected void adjustCustomPropertyForCharGetAt(VarDefBuffer varDefBufferCopySingleItem)
	{
		VarDefNumDecSignComp4 varDefCopy = (VarDefNumDecSignComp4)varDefBufferCopySingleItem;
		varDefCopy.m_nNbDigitInteger = 1;
		varDefCopy.m_nNbDigitDecimal = 0;
	}
	
	boolean isNumeric(VarBufferPos buffer)
	{
		return true;	// All byte values are valid
	}
	
	private int m_nNbDigitInteger;
	private int m_nNbDigitDecimal;
}
