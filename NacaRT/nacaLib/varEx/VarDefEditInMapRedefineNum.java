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
 * Created on 7 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import java.math.BigDecimal;


import nacaLib.bdb.BtreeSegmentKeyTypeFactory;
import nacaLib.mathSupport.MathAdd;
import nacaLib.misc.NumberParserDec;
import nacaLib.misc.StringAsciiEbcdicUtil;
import nacaLib.sqlSupport.CSQLItemType;
import nacaLib.tempCache.CStr;


/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VarDefEditInMapRedefineNum extends VarDefEditInMapRedefineBase
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VarDefEditInMapRedefineNum(VarDefBase varDefParent, DeclareTypeEditInMapRedefineNum declareTypeEditInMapRedefineNum)
	{
		super(varDefParent, declareTypeEditInMapRedefineNum.m_varLevel);
		m_numericValue = declareTypeEditInMapRedefineNum.getNumericValue();
	}
	
	VarDefEditInMapRedefineNum()
	{
		super();
	}

	
	void transfer(VarBufferPos bufferSource, VarAndEdit Dest)
	{
		Dest.m_varDef.write(Dest.m_bufferPos, this, bufferSource);
	}
	
	CSQLItemType getSQLType()
	{
		return CSQLItemType.SQL_TYPE_STRING;
	}
	
	void write(VarBufferPos buffer, VarDefEditInMap varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getUnsignedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}

	void write(VarBufferPos buffer, VarDefEditInMapRedefine varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getUnsignedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}
	
	void write(VarBufferPos buffer, VarDefEditInMapRedefineNumEdited varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getUnsignedInt(bufferSource);
		writeEditLeftPadding(buffer, n);	
	}
	
	void write(VarBufferPos buffer, VarDefEditInMapRedefineNum varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getUnsignedInt(bufferSource);
		write(buffer, n);
	}
	
	public void moveIntoSameType(VarBufferPos buffer, VarDefBuffer varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getUnsignedInt(bufferSource);
		write(buffer, n);
	}
	
	protected VarDefBuffer allocCopy()
	{
		VarDefEditInMapRedefineNum vCopy = new VarDefEditInMapRedefineNum();
		transmitFormat(vCopy);
		return vCopy;
	}

		
	
	CStr getAsDecodedString(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(getBodyAbsolutePosition(buffer), getBodyLength());
		return cs;
	}
	
	CStr getAsAlphaNumString(VarBufferPos buffer)
	{		
		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
		return cs;		
	}

	
	int getAsDecodedInt(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(getBodyAbsolutePosition(buffer), getBodyLength());
		int n = cs.getAsInt();
		return n;
	}
	
	int getAsDecodedUnsignedInt(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(getBodyAbsolutePosition(buffer), getBodyLength());
		int n = cs.getAsUnsignedInt();
		return n;
	}
	
	long getAsDecodedLong(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(getBodyAbsolutePosition(buffer), getBodyLength());
		long l = cs.getAsLong();
		return l;
	}
	
	Dec getAsDecodedDec(VarBufferPos buffer)
	{
		long lInt = getAsDecodedLong(buffer);
		Dec dec = new Dec(lInt, "");
		return dec;
	}
	
	CStr getDottedSignedString(VarBufferPos buffer)
	{	
		CStr cs = buffer.getStringAt(getBodyAbsolutePosition(buffer), getBodyLength());
		return cs;
	}
	
	CStr getDottedSignedStringAsSQLCol(VarBufferPos buffer)
	{	
		CStr cs = buffer.getStringAt(getBodyAbsolutePosition(buffer), getBodyLength());
		return cs;
	}

	
	void write(VarBufferPos buffer, char c)
	{
		writeEditLeftPadding(buffer, c);			
	}
	
	public void write(VarBufferPos buffer, String cs)
	{
		writeEditLeftPadding(buffer, cs);			
	}
	
	public void inc(VarBufferPos buffer, int n)
	{
		int nVal = getAsDecodedInt(buffer);
		nVal += n;
		write(buffer, nVal);
	}
	
	public void inc(VarBufferPos buffer, BigDecimal bdStep)
	{
		CStr s1 = getDottedSignedString(buffer);
		Dec dec = MathAdd.inc(s1, bdStep);
		write(buffer, dec);
	}
		
	public void write(VarBufferPos buffer, int n)
	{
		writeEditLeftPadding(buffer, n);	
	}
	
	public void write(VarBufferPos buffer, long l)
	{
		writeEditLeftPadding(buffer, l);		
	}
	
	
	void write(VarBufferPos buffer, double d)
	{
		Dec dec = NumberParserDec.getAsDec(d);
		writeEditLeftPadding(buffer, dec);
	}
	
	void write(VarBufferPos buffer, Dec dec)
	{
		writeEditLeftPadding(buffer, dec);			
	}
	
	public void write(VarBufferPos buffer, BigDecimal bigDecimal)
	{
		long lValue = bigDecimal.longValue();
		if(lValue < 0)
			lValue = -lValue;
		writeEditLeftPadding(buffer, lValue);
	}
	
	
	void write(VarBufferPos buffer, VarDefG varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
		
	
	void write(VarBufferPos buffer, VarDefX varDefSource, VarBufferPos bufferSource)
	{
		//String cs = varSource.getRawStringExcludingHeader(bufferSource);
		CStr cs = bufferSource.getBodyCStr(varDefSource);
		writeEditLeftPadding(buffer, cs);
	}

	void write(VarBufferPos buffer, VarDefFPacAlphaNum varDefSource, VarBufferPos bufferSource)
	{
		//String cs = varSource.getRawStringExcludingHeader(bufferSource);
		CStr cs = bufferSource.getBodyCStr(varDefSource);
		writeEditLeftPadding(buffer, cs);
	}

	void write(VarBufferPos buffer, VarDefFPacRaw varDefSource, VarBufferPos bufferSource)
	{
		//String cs = varSource.getRawStringExcludingHeader(bufferSource);
		CStr cs = bufferSource.getBodyCStr(varDefSource);
		writeEditLeftPadding(buffer, cs);
	}

	
	void write(VarBufferPos buffer, VarDefNumDecComp0 varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecComp3 varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}

	
	void write(VarBufferPos buffer, VarDefNumDecComp4 varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignComp4 varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}

	
	void write(VarBufferPos buffer, VarDefNumDecSignComp0 varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignComp3 varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignLeadingComp0 varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignTrailingComp0 varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntComp0 varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}

	void write(VarBufferPos buffer, VarDefNumIntComp0Long varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}

	void write(VarBufferPos buffer, VarDefNumIntComp3 varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}	
	
	void write(VarBufferPos buffer, VarDefNumIntComp3Long varSource, VarBufferPos bufferSource)
	{
		long l = varSource.getAsDecodedLong(bufferSource);
		writeEditLeftPadding(buffer, l);			
	}	


	
	void write(VarBufferPos buffer, VarDefNumIntComp4 varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntComp4Long varSource, VarBufferPos bufferSource)
	{
		long l = varSource.getAsDecodedLong(bufferSource);
		writeEditLeftPadding(buffer, l);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp0 varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp0Long varSource, VarBufferPos bufferSource)
	{
		long l = varSource.getAsDecodedLong(bufferSource);
		writeEditLeftPadding(buffer, l);
		
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp3 varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}

	void write(VarBufferPos buffer, VarDefFPacNumIntSignComp3 varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp3Long varSource, VarBufferPos bufferSource)
	{
		long l = varSource.getAsDecodedLong(bufferSource);
		writeEditLeftPadding(buffer, l);
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp4 varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp4Long varSource, VarBufferPos bufferSource)
	{
		long l = varSource.getAsDecodedLong(bufferSource);
		writeEditLeftPadding(buffer, l);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignLeadingComp0 varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}

	void write(VarBufferPos buffer, VarDefNumIntSignLeadingComp0Long varSource, VarBufferPos bufferSource)
	{
		long l = varSource.getAsDecodedLong(bufferSource);
		writeEditLeftPadding(buffer, l);
	}

	void write(VarBufferPos buffer, VarDefNumIntSignTrailingComp0 varSource, VarBufferPos bufferSource)
	{
		int n = varSource.getAsDecodedInt(bufferSource);
		writeEditLeftPadding(buffer, n);
	}

	void write(VarBufferPos buffer, VarDefNumIntSignTrailingComp0Long varSource, VarBufferPos bufferSource)
	{
		long l = varSource.getAsDecodedLong(bufferSource);
		writeEditLeftPadding(buffer, l);
	}

	private int writeEditRightPadding(VarBufferPos buffer, CStr cs, char cPad)
	{
		int nBodyPosStart = getBodyAbsolutePosition(buffer);
		int nBodyLength = getBodyLength();
		return internalWriteRightPadding(buffer, nBodyPosStart, nBodyLength, cs, cPad);
	}
	
	private int writeEditRightPadding(VarBufferPos buffer, String cs, char cPad)
	{
		int nBodyPosStart = getBodyAbsolutePosition(buffer);
		int nBodyLength = getBodyLength();
		return internalWriteRightPadding(buffer, nBodyPosStart, nBodyLength, cs, cPad);
	}

//	private int writeEditRightPadding(VarBufferPos buffer, int nOffset, String cs, char cPad)
//	{
//		int nBodyPosStart = getBodyAbsolutePosition(buffer);
//		int nBodyLength = getBodyLength();
//		return internalWriteRightPadding(buffer, nBodyPosStart+nOffset, nBodyLength, cs, cPad);
//	}
	
	private int writeEditRightPaddingBlankInit0(VarBufferPos buffer, int nOffset, InitializeCache initializeCache)
	{
		int nBodyPosStart = getBodyAbsolutePosition(buffer);
		int nBodyLength = getBodyLength();
		return internalWriteRightPaddingBlankInit0(buffer, nBodyPosStart+nOffset, nBodyLength, initializeCache);
	}

	protected int writeEditRepeatingchar(VarBufferPos buffer, char c)
	{
		return buffer.writeRepeatingCharAt(buffer.m_nAbsolutePosition+getHeaderLength(), c, m_nTotalSize-getHeaderLength());
	}	
	
	protected int writeEditRepeatingchar(VarBufferPos buffer, char c, int nOffset, int nNbChars)
	{
		int nMaxCharOnRight = m_nTotalSize - getHeaderLength() - nOffset;
		int nNbCharsToWrite = Math.min(nMaxCharOnRight, nNbChars);
		return buffer.writeRepeatingCharAt(buffer.m_nAbsolutePosition+nOffset+getHeaderLength(), c, nNbCharsToWrite);
	}	
	
	void write(VarBufferPos buffer, VarDefNumEdited varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		writeEditRightPadding(buffer, cs, ' ');	
	}
	
	
	void write(VarBufferPos buffer, CobolConstantZero cst)
	{
		writeEditRepeatingchar(buffer, cst.getValue());
	}

	void write(VarBufferPos buffer, CobolConstantSpace cst)
	{
		writeEditRepeatingchar(buffer, cst.getValue());
	}

	void write(VarBufferPos buffer, CobolConstantLowValue cst)
	{
		writeEditRepeatingchar(buffer, cst.getValue());
	}

	void write(VarBufferPos buffer, CobolConstantHighValue cst)
	{
		writeEditRepeatingchar(buffer, cst.getValue());
	}
	
	void write(VarBufferPos buffer, CobolConstantZero cst, int nOffsetPosition, int nNbChar)
	{
		writeEditRepeatingchar(buffer, cst.getValue(), nOffsetPosition, nNbChar);
	}
	
	void write(VarBufferPos buffer, CobolConstantSpace cst, int nOffsetPosition, int nNbChar)
	{
		writeEditRepeatingchar(buffer, cst.getValue(), nOffsetPosition, nNbChar);
	}
	
	void write(VarBufferPos buffer, CobolConstantLowValue cst, int nOffsetPosition, int nNbChar)
	{
		writeEditRepeatingchar(buffer, cst.getValue(), nOffsetPosition, nNbChar);
	}
	
	void write(VarBufferPos buffer, CobolConstantHighValue cst, int nOffsetPosition, int nNbChar)
	{
		writeEditRepeatingchar(buffer, cst.getValue(), nOffsetPosition, nNbChar);
	}
	
	void write(VarBufferPos buffer, String csValue, int nOffsetPosition, int nNbChar)
	{		
		internalWriteAtOffsetPosition(buffer, csValue, nOffsetPosition, nNbChar, ' ');
	}
	
	void writeAndFill(VarBufferPos buffer, char c)
	{
		writeEditRepeatingchar(buffer, c);
	}
	
//	public void initialize(VarBufferPos buffer)
//	{
//		writeEditRightPadding(buffer, "0", '0');
//	}
	
	public void initializeAtOffset(VarBufferPos buffer, int nOffset, InitializeCache initializeCache)
	{
		writeEditRightPaddingBlankInit0(buffer, nOffset, initializeCache);
	}

	
//	void initialize(VarBufferPos buffer, String cs)
//	{
//		assertIfFalse(false) ;
//		//writeEditRightPadding(buffer, cs, ' ');
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
//		assertIfFalse(false);
//	}
	
	void initializeAtOffset(VarBufferPos buffer, int nOffset, int n)
	{
	}
	
	
	void initializeEdited(VarBufferPos buffer, String cs)
	{
		assertIfFalse(false);
	}
	
	void initializeEdited(VarBufferPos buffer, int n)
	{
		assertIfFalse(false);
	}
	
	int compare(ComparisonMode mode, VarBufferPos bufferSource, VarAndEdit var2)
	{
		// return var2.m_varDef.compare(var2.m_bufferPos, this, bufferSource);
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp3 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}

	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp4 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp4 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp3 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignLeadingComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignTrailingComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp0Long varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		long l1 = varDefNum1.getAsDecodedLong(buffer1);
		long l2 = getAsDecodedLong(buffer2);
		return internalCompare(l1, l2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp3 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		Dec dec1 = varDefNum1.getUnsignedDec(buffer1);
		int n2 = getUnsignedInt(buffer2);
		return internalCompare(dec1, n2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp3Long varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		long l1 = varDefNum1.getAsDecodedLong(buffer1);
		long l2 = getAsDecodedLong(buffer2);
		return internalCompare(l1, l2);
	}

	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp4 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getUnsignedInt(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(n2));
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp4Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is a group item, the compiler treats the numeric operand as if it had been moved into a group item the same size as the numeric operand and then compared. This is equivalent to a group move. 
		// The compiler ignores the description of the numeric item (except for length) and, therefore, includes in its length any operational sign, whether carried as a separate character or as an overpunched character. Overpunched characters are never ASCII numeric digits. They are characters ranging from A to R, left brace ({), or right brace (}). Thus, the sign and the digits, stored as ASCII bytes, take part in the comparison, and zeros are not supplied for P characters in the PICTURE character-string. 
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);

		long l2 = getAsDecodedLong(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(l2));
	}
	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp0 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getUnsignedInt(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(n2));
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp0Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		long l2 = getUnsignedLong(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(l2));
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp3 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getUnsignedInt(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(n2));
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacNumIntSignComp3 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getUnsignedInt(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(n2));
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp3Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		long l2 = getUnsignedLong(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(l2));
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp4 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getUnsignedInt(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(n2));
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp4Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		long l2 = getUnsignedLong(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(l2));
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignLeadingComp0 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getUnsignedInt(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(n2));
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignLeadingComp0Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		long l2 = getUnsignedLong(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(l2));
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignTrailingComp0 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getUnsignedInt(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(n2));
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignTrailingComp0Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		long l2 = getUnsignedLong(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(l2));
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefX varDef1, VarBufferPos buffer1)
	{
//		String cs1 = varDef1.getRawStringExcludingHeader(buffer1);
//		String cs2 = getRawStringExcludingHeader(buffer2);
		CStr cs1 = buffer1.getBodyCStr(varDef1);
		CStr cs2 = buffer2.getBodyCStr(this);
		return internalCompare(mode, cs1, cs2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacAlphaNum varDef1, VarBufferPos buffer1)
	{
//		String cs1 = varDef1.getRawStringExcludingHeader(buffer1);
//		String cs2 = getRawStringExcludingHeader(buffer2);
		CStr cs1 = buffer1.getBodyCStr(varDef1);
		CStr cs2 = buffer2.getBodyCStr(this);
		return internalCompare(mode, cs1, cs2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacRaw varDef1, VarBufferPos buffer1)
	{
//		String cs1 = varDef1.getRawStringExcludingHeader(buffer1);
//		String cs2 = getRawStringExcludingHeader(buffer2);
		CStr cs1 = buffer1.getBodyCStr(varDef1);
		CStr cs2 = buffer2.getBodyCStr(this);
		return internalCompare(mode, cs1, cs2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefG varDef1, VarBufferPos buffer1)
	{
		CStr cs1 = buffer1.getBodyCStr(varDef1);
		CStr cs2 = buffer2.getBodyCStr(this);
//		
//		String cs1 = varDef1.getRawStringExcludingHeader(buffer1);
//		String cs2 = getRawStringExcludingHeader(buffer2);
		return internalCompare(mode, cs1, cs2);
	}
			
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumEdited varDef1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		// TODO how to compare with num edited ?
		return 0;
	}
	
	boolean isNumeric(VarBufferPos buffer)
	{
		return internalIsRawStringNumeric(buffer);
	}
	
	public boolean isAlphabetic(VarBufferPos buffer)
	{
		return internalIsRawStringAlphabetic(buffer);
	}
	
//	private void internalEditPhysicalWrite(VarBufferPos bufferDest, VarDefBuffer varSource, VarBufferPos bufferSource)
//	{
//		int nNbCharSource = varSource.getBodyLength();
//		int nNbCharDest = getBodyLength();
//		int nNbCharToCopy = Math.min(nNbCharSource, nNbCharDest);
//		bufferDest.copyBytesFromSource(getBodyAbsolutePosition(bufferDest), bufferSource, varSource.getBodyAbsolutePosition(bufferSource), nNbCharToCopy);
//	}
	
	void transmitFormat(VarDefEditInMapRedefineNum varDefDest)
	{
		m_numericValue = new NumericValue(varDefDest.m_numericValue);
	}
	
	private void writeEditLeftPadding(VarBufferPos buffer, int nValue)
	{
		int nBodyPosStart = getBodyAbsolutePosition(buffer);
		int nBodyLength = getBodyLength();
		
		//RWNumIntComp0.internalWriteAbsoluteIntComp0(buffer, nValue, nBodyPosStart, nBodyLength);
		
		Pic9Comp0BufferSupport.setFromRightToLeft(buffer, nBodyPosStart, nBodyLength, nBodyLength, 0, nValue);
	}

	private void writeEditLeftPadding(VarBufferPos buffer, long lValue)
	{
		int nBodyPosStart = getBodyAbsolutePosition(buffer);
		int nBodyLength = getBodyLength();
		
		RWNumIntComp0.internalWriteAbsoluteIntComp0AsLong(buffer, lValue, nBodyPosStart, nBodyLength);
	}

	private void writeEditLeftPadding(VarBufferPos buffer, String csValue)
	{
		int nBodyPosStart = getBodyAbsolutePosition(buffer);
		int nBodyLength = getBodyLength();
		
		RWNumIntComp0.internalWriteAbsoluteIntComp0AsString(buffer, csValue, nBodyPosStart, nBodyLength);

	}
	
	private void writeEditLeftPadding(VarBufferPos buffer, CStr csValue)
	{
		int nBodyPosStart = getBodyAbsolutePosition(buffer);
		int nBodyLength = getBodyLength();
		
				
		RWNumIntComp0.internalWriteAbsoluteIntComp0AsString(buffer, csValue.getAsString(), nBodyPosStart, nBodyLength);

	}

	private void writeEditLeftPadding(VarBufferPos buffer, Dec dec)
	{
		int nValue = dec.getUnsignedInt();
		writeEditLeftPadding(buffer, nValue);
	}
	
	public String digits(VarBufferPos buffer)
	{
		return getAsAlphaNumString(buffer).getAsString();
	}
	
	boolean isConvertibleInEbcdic()
	{
		return true;
	}
	
	public int getTypeId()
	{
		return VarTypeId.VarDefEditInMapRedefineNumTypeId;
	}
	
	public BtreeSegmentKeyTypeFactory getSegmentKeyTypeFactory()
	{
		return VarTypeId.m_segmentKeyTypeFactoryString;
	}		

	public boolean isEbcdicAsciiConvertible()
	{
		return false;
	}
	
	protected void adjustCustomProperty(VarDefBuffer varDefBufferCopySingleItem)
	{
		VarDefEditInMapRedefineNum varDefCopy = (VarDefEditInMapRedefineNum)varDefBufferCopySingleItem;
		super.adjustCustomProperty(varDefBufferCopySingleItem);
		varDefCopy.m_numericValue = m_numericValue;
	}
	
	protected void adjustCustomPropertyForCharGetAt(VarDefBuffer varDefBufferCopySingleItem)
	{
		VarDefEditInMapRedefineNum varDefCopy = (VarDefEditInMapRedefineNum)varDefBufferCopySingleItem;
		super.adjustCustomProperty(varDefBufferCopySingleItem);
		varDefCopy.m_numericValue = m_numericValue;
	}
	
	private NumericValue m_numericValue = null;	
}
