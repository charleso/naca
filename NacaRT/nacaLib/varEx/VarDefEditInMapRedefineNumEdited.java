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
public class VarDefEditInMapRedefineNumEdited extends VarDefEditInMapRedefineBase
{	
	public VarDefEditInMapRedefineNumEdited(VarDefBase varDefParent, DeclareTypeEditInMapRedefineNumEdited declareTypeEditInMapRedefineNumEdited)
	{
		super(varDefParent, declareTypeEditInMapRedefineNumEdited.m_varLevel);
		m_csFormat = declareTypeEditInMapRedefineNumEdited.getNumEditedFormat();
		m_bBlankWhenZero = declareTypeEditInMapRedefineNumEdited.getBlankWhenZero();
	}
	
	VarDefEditInMapRedefineNumEdited()
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
		CStr cs = varSource.getAsDecodedString(bufferSource);
		Dec dec = NumberParserDec.getAsDec(cs);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');
	}

	void write(VarBufferPos buffer, VarDefEditInMapRedefine varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		Dec dec = NumberParserDec.getAsDec(cs);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');
	}
	
	void write(VarBufferPos buffer, VarDefEditInMapRedefineNum varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		Dec dec = NumberParserDec.getAsDec(cs);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');
	}
	
	public void moveIntoSameType(VarBufferPos buffer, VarDefBuffer varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		Dec dec = NumberParserDec.getAsDec(cs);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');
	}
	
	void write(VarBufferPos buffer, VarDefEditInMapRedefineNumEdited varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		Dec dec = NumberParserDec.getAsDec(cs);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');
	}
	
	protected VarDefBuffer allocCopy()
	{
		VarDefEditInMapRedefineNumEdited vCopy = new VarDefEditInMapRedefineNumEdited();
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
		Dec dec = NumberParserDec.getAsDec(c);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	public void write(VarBufferPos buffer, String cs)
	{
		Dec dec = NumberParserDec.getAsDec(cs);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
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
		Dec dec = NumberParserDec.getAsDec(n);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	public void write(VarBufferPos buffer, long l)
	{
		Dec dec = NumberParserDec.getAsDec(l);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	
	void write(VarBufferPos buffer, double d)
	{
		Dec dec = NumberParserDec.getAsDec(d);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	void write(VarBufferPos buffer, Dec dec)
	{
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	public void write(VarBufferPos buffer, BigDecimal bigDecimal)
	{
		Dec dec = NumberParserDec.getAsDec(bigDecimal);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');
	}	
	
	
	void write(VarBufferPos buffer, VarDefG varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
		
	
	void write(VarBufferPos buffer, VarDefX varDefSource, VarBufferPos bufferSource)
	{
		//String cs = varSource.getRawStringExcludingHeader(bufferSource);
		CStr cs = bufferSource.getBodyCStr(varDefSource);
		Dec dec = NumberParserDec.getAsDec(cs);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	void write(VarBufferPos buffer, VarDefFPacAlphaNum varDefSource, VarBufferPos bufferSource)
	{
		//String cs = varSource.getRawStringExcludingHeader(bufferSource);
		CStr cs = bufferSource.getBodyCStr(varDefSource);
		Dec dec = NumberParserDec.getAsDec(cs);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	void write(VarBufferPos buffer, VarDefFPacRaw varDefSource, VarBufferPos bufferSource)
	{
		CStr cs = bufferSource.getBodyCStr(varDefSource);
		Dec dec = NumberParserDec.getAsDec(cs);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	
	
	void write(VarBufferPos buffer, VarDefNumDecComp0 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	void write(VarBufferPos buffer, VarDefNumDecComp3 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}

	
	void write(VarBufferPos buffer, VarDefNumDecComp4 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignComp4 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}

	
	void write(VarBufferPos buffer, VarDefNumDecSignComp0 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignComp3 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignLeadingComp0 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignTrailingComp0 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	void write(VarBufferPos buffer, VarDefNumIntComp0 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}

	void write(VarBufferPos buffer, VarDefNumIntComp0Long varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}

	void write(VarBufferPos buffer, VarDefNumIntComp3 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}	
	
	void write(VarBufferPos buffer, VarDefNumIntComp3Long varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}	


	
	void write(VarBufferPos buffer, VarDefNumIntComp4 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	void write(VarBufferPos buffer, VarDefNumIntComp4Long varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp0 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp0Long varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp3 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	void write(VarBufferPos buffer, VarDefFPacNumIntSignComp3 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp3Long varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp4 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
 	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp4Long varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignLeadingComp0 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}

	void write(VarBufferPos buffer, VarDefNumIntSignLeadingComp0Long varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}

	void write(VarBufferPos buffer, VarDefNumIntSignTrailingComp0 varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}

	void write(VarBufferPos buffer, VarDefNumIntSignTrailingComp0Long varSource, VarBufferPos bufferSource)
	{
		Dec dec = varSource.getAsDecodedDec(bufferSource);
		String csFormatted = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		writeEditRightPadding(buffer, csFormatted, ' ');			
	}

	
	private int writeEditRightPadding(VarBufferPos buffer, String cs, char cPad)
	{
		int nBodyPosStart = getBodyAbsolutePosition(buffer);
		int nBodyLength = getBodyLength();
		return internalWriteRightPadding(buffer, nBodyPosStart, nBodyLength, cs, cPad);
	}
	
	private int writeEditRightPadding(VarBufferPos buffer, CStr cs, char cPad)
	{
		int nBodyPosStart = getBodyAbsolutePosition(buffer);
		int nBodyLength = getBodyLength();
		return internalWriteRightPadding(buffer, nBodyPosStart, nBodyLength, cs, cPad);
	}
	
	private int writeEditRightPadding(VarBufferPos buffer, int nOffset, String cs, char cPad)
	{
		int nBodyPosStart = getBodyAbsolutePosition(buffer);
		int nBodyLength = getBodyLength();
		return internalWriteRightPadding(buffer, nBodyPosStart+nOffset, nBodyLength, cs, cPad);
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
		Dec dec = new Dec(0L, "");
		internalFormatAndWrite(buffer, dec);	
		
		//writeEditRepeatingchar(buffer, cst.getValue());
	}
	
	private void internalFormatAndWrite(VarBufferPos buffer, Dec dec)
	{
		String cs = RWNumEdited.internalFormatAndWrite(dec, m_csFormat, m_bBlankWhenZero);
		internalWriteRightPadding(buffer, getBodyAbsolutePosition(buffer), getBodyLength(), cs, '\0');	// Padding with \0 on the right
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
//		writeEditRightPadding(buffer, " ", ' ');
//	}
	
	public void initializeAtOffset(VarBufferPos buffer, int nOffset, InitializeCache initializeCache)
	{
		//writeEditRightPadding(buffer, nOffset, " ", ' ');
		writeEditRightPaddingBlankInit(buffer, nOffset, initializeCache);
	}

	
//	void initialize(VarBufferPos buffer, String cs)
//	{
//		assertIfFalse(false) ;
//		//writeEditRightPadding(buffer, cs, ' ');
//	}
	
	void initializeAtOffset(VarBufferPos buffer, int nOffset, String cs)
	{
	}
	
	
//	void initialize(VarBufferPos buffer, int n)
//	{
//		assertIfFalse(false);
//	}
	
	void initializeAtOffset(VarBufferPos buffer, int nOffset, int n)
	{
	}

	void initializeEditedAtOffset(VarBufferPos buffer, int nOffset, int nValue)
	{
	}
	
	void initializeEditedAtOffset(VarBufferPos buffer, int nOffset, double dValue)
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
		// return var2.m_varDef.compare(var2.m_buffer, this, bufferSource);
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
	
	void transmitFormat(VarDefEditInMapRedefineNumEdited varDefDest)
	{
		varDefDest.m_csFormat = m_csFormat; 
		varDefDest.m_bBlankWhenZero = m_bBlankWhenZero;
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
		return VarTypeId.VarDefEditInMapRedefineNumEditedTypeId;
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
		VarDefEditInMapRedefineNumEdited varDefCopy = (VarDefEditInMapRedefineNumEdited)varDefBufferCopySingleItem;
		varDefCopy.m_csFormat = m_csFormat;
		varDefCopy.m_bBlankWhenZero = m_bBlankWhenZero;
	}
	
	protected void adjustCustomPropertyForCharGetAt(VarDefBuffer varDefBufferCopySingleItem)
	{
		VarDefEditInMapRedefineNumEdited varDefCopy = (VarDefEditInMapRedefineNumEdited)varDefBufferCopySingleItem;
		varDefCopy.m_csFormat = m_csFormat;
		varDefCopy.m_bBlankWhenZero = m_bBlankWhenZero;
	}

	
	private String m_csFormat = null;	
	private boolean m_bBlankWhenZero = false;
}
