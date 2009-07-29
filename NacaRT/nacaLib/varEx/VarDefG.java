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
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import java.math.BigDecimal;

import jlib.display.ResourceManager;
import jlib.misc.*;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.bdb.BtreeSegmentKeyTypeFactory;
import nacaLib.debug.BufferSpy;
import nacaLib.misc.StringAsciiEbcdicUtil;
import nacaLib.sqlSupport.CSQLItemType;
import nacaLib.tempCache.*;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VarDefG extends VarDefVariable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VarDefG(VarDefBase varDefParent, DeclareTypeG declareTypeG)
	{
		super(varDefParent, declareTypeG.m_varLevel);
	}
	
	protected VarDefG()
	{
		super();
	}
	
//	VarDefG(VarDefG varDefSource)
//	{
//		super(varDefSource);
//	}
//	
//	VarDefBuffer deepDuplicate()
//	{
//		return new VarDefG(this);
//	}
	
	void transfer(VarBufferPos bufferSource, VarAndEdit Dest)
	{
		Dest.m_varDef.write(Dest.m_bufferPos, this, bufferSource);
	}
	
	protected VarDefBuffer allocCopy()
	{
		VarDefG v = new VarDefG();
		return v;
	}
	
	CSQLItemType getSQLType()
	{
		return CSQLItemType.SQL_TYPE_STRING;
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
		return 0;
	}
	
//	String getRawString(VarBuffer buffer)
//	{
//		String cs = buffer.getStringAt(m_nAbsolutePosition, m_nTotalSize);
//		return cs;
//	}
	
	CStr getAsDecodedString(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
		return cs;
	}
	
	int getAsDecodedInt(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
		int n = cs.getAsInt();
		return n;
	}
	
	int getAsDecodedUnsignedInt(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
		int n = cs.getAsUnsignedInt();
		return n;
	}
	
	long getAsDecodedLong(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
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
		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
		return cs;
	}
	
	CStr getDottedSignedStringAsSQLCol(VarBufferPos buffer)
	{	
		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
		return cs;
	}
	
	CStr getAsAlphaNumString(VarBufferPos buffer)
	{
		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
		return cs;
	}
	
	void write(VarBufferPos buffer, char c)
	{
		String cs = String.valueOf(c);
		internalPhysicalWriteToGroup(buffer, cs);
	}
	
	public void write(VarBufferPos buffer, String cs)
	{
		internalPhysicalWriteToGroup(buffer, cs);	
	}
	
	public void inc(VarBufferPos buffer, int n)
	{
		assertIfFalse(false);
	}
	
	public void inc(VarBufferPos buffer, BigDecimal bdStep)
	{
		assertIfFalse(false);
	}

	
	public void write(VarBufferPos buffer, int n)
	{
//		String cs = NumberParser.encodeIntoString(n);
//		internalPhysicalWriteFromGroup(buffer, cs);
		assertIfFalse(false);
		// TODO
	}
	
	public void write(VarBufferPos buffer, long l)
	{
		assertIfFalse(false);
	}
	
	
	void write(VarBufferPos buffer, double d)
	{ 
//		String cs = NumberParser.encodeIntoString(n);
//		internalPhysicalWriteFromGroup(buffer, cs);
		assertIfFalse(false);
		// TODO
	}
	
	void write(VarBufferPos buffer, Dec dec)
	{
//		String cs = NumberParser.encodeIntoString(n);
//		internalPhysicalWriteFromGroup(buffer, cs);
		assertIfFalse(false);
		// TODO
	}
	
	public void write(VarBufferPos buffer, BigDecimal bigDecimal)
	{
		assertIfFalse(false);
	}
	
	public void moveIntoSameType(VarBufferPos buffer, VarDefBuffer varSource, VarBufferPos bufferSource)
	{
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}
	
	void write(VarBufferPos buffer, VarDefG varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If the sending item is a group item, and the receiving item is an elementary item, the compiler ignores the receiving item description except for the size description, in bytes, and any JUSTIFIED clause. It conducts no conversion or editing on the sending item's data
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}
	
	void write(VarBufferPos buffer, VarDefX varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}

	void write(VarBufferPos buffer, VarDefFPacAlphaNum varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}
	
	void write(VarBufferPos buffer, VarDefFPacRaw varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}
	

	void write(VarBufferPos buffer, VarDefNumDecComp0 varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecComp3 varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}

	
	void write(VarBufferPos buffer, VarDefNumDecComp4 varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}

	void write(VarBufferPos buffer, VarDefNumDecSignComp4 varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}

	void write(VarBufferPos buffer, VarDefNumDecSignComp0 varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignComp3 varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignLeadingComp0 varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignTrailingComp0 varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntComp0 varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}

	void write(VarBufferPos buffer, VarDefNumIntComp0Long varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}

	void write(VarBufferPos buffer, VarDefNumIntComp3 varSource, VarBufferPos bufferSource)
	{
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntComp3Long varSource, VarBufferPos bufferSource)
	{
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}	
	
	
	void write(VarBufferPos buffer, VarDefNumIntComp4 varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}

	void write(VarBufferPos buffer, VarDefNumIntComp4Long varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp0 varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp0Long varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp3 varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}

	void write(VarBufferPos buffer, VarDefFPacNumIntSignComp3 varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp3Long varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp4 varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
 	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp4Long varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}

	
	void write(VarBufferPos buffer, VarDefNumIntSignLeadingComp0 varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}

	void write(VarBufferPos buffer, VarDefNumIntSignLeadingComp0Long varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}

	void write(VarBufferPos buffer, VarDefNumIntSignTrailingComp0 varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignTrailingComp0Long varSource, VarBufferPos bufferSource)
	{
		// http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#alpha_elem_move_sec
		// If either the sending or receiving item is a group item, the compiler considers the move to be a group move. It treats both the sending and receiving items as if they were alphanumeric items. 
		internalPhysicalWriteToGroup(buffer, varSource, bufferSource);
	}	
	
	void write(VarBufferPos buffer, VarDefEditInMap varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		internalPhysicalWriteToGroup(buffer, cs);
	}
	
	void write(VarBufferPos buffer, VarDefEditInMapRedefine varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		internalPhysicalWriteToGroup(buffer, cs);
	}	

	void write(VarBufferPos buffer, VarDefEditInMapRedefineNumEdited varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		internalPhysicalWriteToGroup(buffer, cs);
	}
	
	void write(VarBufferPos buffer, VarDefEditInMapRedefineNum varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		internalPhysicalWriteToGroup(buffer, cs);
	}


	void write(VarBufferPos buffer, VarDefNumEdited varSource, VarBufferPos bufferSource)
	{
		CStr cs = varSource.getAsDecodedString(bufferSource);
		internalPhysicalWriteToGroup(buffer, cs);	
	}

	
	
	void write(VarBufferPos buffer, CobolConstantZero cst)
	{
		writeRepeatingchar(buffer, cst.getValue());
	}

	void write(VarBufferPos buffer, CobolConstantSpace cst)
	{
		writeRepeatingchar(buffer, cst.getValue());
	}

	void write(VarBufferPos buffer, CobolConstantLowValue cst)
	{
		writeRepeatingchar(buffer, cst.getValue());
	}

	void write(VarBufferPos buffer, CobolConstantHighValue cst)
	{
		writeRepeatingchar(buffer, cst.getValue());
	}
	
	void write(VarBufferPos buffer, CobolConstantZero cst, int nOffsetPosition, int nNbChar)
	{
		writeRepeatingCharUpToEnd(buffer, cst.getValue(), nOffsetPosition, nNbChar);
	}
	
	void write(VarBufferPos buffer, CobolConstantSpace cst, int nOffsetPosition, int nNbChar)
	{
		writeRepeatingCharUpToEnd(buffer, cst.getValue(), nOffsetPosition, nNbChar);
	}
	
	void write(VarBufferPos buffer, CobolConstantLowValue cst, int nOffsetPosition, int nNbChar)
	{
		writeRepeatingCharUpToEnd(buffer, cst.getValue(), nOffsetPosition, nNbChar);
	}
	
	void write(VarBufferPos buffer, CobolConstantHighValue cst, int nOffsetPosition, int nNbChar)
	{
		writeRepeatingCharUpToEnd(buffer, cst.getValue(), nOffsetPosition, nNbChar);
	}
	
	void write(VarBufferPos buffer, String csValue, int nOffsetPosition, int nNbChar)
	{		
		internalWriteAtOffsetPosition(buffer, csValue, nOffsetPosition, nNbChar, ' ');
	}
	
	void writeAndFill(VarBufferPos buffer, char c)
	{
		writeRepeatingchar(buffer, c);
	}
	

	
	
//	public void initialize(VarBufferPos buffer)
//	{
//	}

	public void initializeAtOffset(VarBufferPos buffer, int nOffset, InitializeCache initializeCache)
	{
	}

//	void initialize(VarBufferPos buffer, String cs)
//	{
//	}
	
	void initializeAtOffset(VarBufferPos buffer, int nOffset, String cs)
	{
	}
		
//	void initialize(VarBufferPos buffer, int n)
//	{
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
	}
	
	void initializeEdited(VarBufferPos buffer, int n)
	{
	}
	
	int compare(ComparisonMode mode, VarBufferPos bufferSource, VarAndEdit var2)
	{
		int n = var2.m_varDef.compare(mode, var2.m_bufferPos, this, bufferSource);
		return n;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		int n1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp3 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		int n1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}

	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp4 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		int n1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp4 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		int n1 = varDefNum1.getUnsignedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		int n1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp3 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		int n1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignLeadingComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		int n1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignTrailingComp0 varDefNum1, VarBufferPos buffer1)
	{
		// The compiler does not accept a comparison between a noninteger numeric operand and a nonnumeric operand. If you try to compare these two items, you receive a diagnostic message at compile time.
		// more relax here: 
		int n1 = varDefNum1.getAsDecodedInt(buffer1);
		Dec dec2 = getAsDecodedDec(buffer2);
		return internalCompare(n1, dec2);
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
		// If the nonnumeric operand is a group item, the compiler treats the numeric operand as if it had been moved into a group item the same size as the numeric operand and then compared. This is equivalent to a group move. 
		// The compiler ignores the description of the numeric item (except for length) and, therefore, includes in its length any operational sign, whether carried as a separate character or as an overpunched character. Overpunched characters are never ASCII numeric digits. They are characters ranging from A to R, left brace ({), or right brace (}). Thus, the sign and the digits, stored as ASCII bytes, take part in the comparison, and zeros are not supplied for P characters in the PICTURE character-string. 
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		
		int n2 = getAsDecodedInt(buffer2);
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
		// If the nonnumeric operand is a group item, the compiler treats the numeric operand as if it had been moved into a group item the same size as the numeric operand and then compared. This is equivalent to a group move. 
		// The compiler ignores the description of the numeric item (except for length) and, therefore, includes in its length any operational sign, whether carried as a separate character or as an overpunched character. Overpunched characters are never ASCII numeric digits. They are characters ranging from A to R, left brace ({), or right brace (}). Thus, the sign and the digits, stored as ASCII bytes, take part in the comparison, and zeros are not supplied for P characters in the PICTURE character-string. 
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getAsDecodedInt(buffer2);
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
		// If the nonnumeric operand is a group item, the compiler treats the numeric operand as if it had been moved into a group item the same size as the numeric operand and then compared. This is equivalent to a group move. 
		// The compiler ignores the description of the numeric item (except for length) and, therefore, includes in its length any operational sign, whether carried as a separate character or as an overpunched character. Overpunched characters are never ASCII numeric digits. They are characters ranging from A to R, left brace ({), or right brace (}). Thus, the sign and the digits, stored as ASCII bytes, take part in the comparison, and zeros are not supplied for P characters in the PICTURE character-string. 
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getAsDecodedInt(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(n2));
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacNumIntSignComp3 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is a group item, the compiler treats the numeric operand as if it had been moved into a group item the same size as the numeric operand and then compared. This is equivalent to a group move. 
		// The compiler ignores the description of the numeric item (except for length) and, therefore, includes in its length any operational sign, whether carried as a separate character or as an overpunched character. Overpunched characters are never ASCII numeric digits. They are characters ranging from A to R, left brace ({), or right brace (}). Thus, the sign and the digits, stored as ASCII bytes, take part in the comparison, and zeros are not supplied for P characters in the PICTURE character-string. 
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getAsDecodedInt(buffer2);
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
		// If the nonnumeric operand is a group item, the compiler treats the numeric operand as if it had been moved into a group item the same size as the numeric operand and then compared. This is equivalent to a group move. 
		// The compiler ignores the description of the numeric item (except for length) and, therefore, includes in its length any operational sign, whether carried as a separate character or as an overpunched character. Overpunched characters are never ASCII numeric digits. They are characters ranging from A to R, left brace ({), or right brace (}). Thus, the sign and the digits, stored as ASCII bytes, take part in the comparison, and zeros are not supplied for P characters in the PICTURE character-string. 
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getAsDecodedInt(buffer2);
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
		// If the nonnumeric operand is a group item, the compiler treats the numeric operand as if it had been moved into a group item the same size as the numeric operand and then compared. This is equivalent to a group move. 
		// The compiler ignores the description of the numeric item (except for length) and, therefore, includes in its length any operational sign, whether carried as a separate character or as an overpunched character. Overpunched characters are never ASCII numeric digits. They are characters ranging from A to R, left brace ({), or right brace (}). Thus, the sign and the digits, stored as ASCII bytes, take part in the comparison, and zeros are not supplied for P characters in the PICTURE character-string. 
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getAsDecodedInt(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(n2));
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignLeadingComp0Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		long l2 = getAsDecodedLong(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(l2));
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignTrailingComp0 varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is a group item, the compiler treats the numeric operand as if it had been moved into a group item the same size as the numeric operand and then compared. This is equivalent to a group move. 
		// The compiler ignores the description of the numeric item (except for length) and, therefore, includes in its length any operational sign, whether carried as a separate character or as an overpunched character. Overpunched characters are never ASCII numeric digits. They are characters ranging from A to R, left brace ({), or right brace (}). Thus, the sign and the digits, stored as ASCII bytes, take part in the comparison, and zeros are not supplied for P characters in the PICTURE character-string. 
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		int n2 = getAsDecodedInt(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(n2));
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignTrailingComp0Long varDefNum1, VarBufferPos buffer1)
	{
		// see http://h71000.www7.hp.com/DOC/73final/6297/6297_profile_010.html#group_items_sec
		// If the nonnumeric operand is an elementary item or a literal, the compiler treats the numeric operand as if it had been moved into an alphanumeric data item the same size as the numeric operand and then compared. This causes any operational sign, whether carried as a separate character or as an overpunched character, to be stripped from the numeric item so that it appears to be an unsigned quantity. 
		// In addition, if the PICTURE character-string of the numeric item contains trailing P characters, indicating that there are assumed integer positions that are not actually present, they are filled with zero digits. Thus, an item with a PICTURE character-string of S9999PPP is moved to a temporary location where it is described as 9999999. If its value is 432J (--4321), the value in the temporary location will be 4321000. The numeric digits take part in the comparison.
		//String cs1 = varDefNum1.getRawStringExcludingHeader(buffer1);
		CStr cs1 = buffer1.getBodyCStr(varDefNum1);
		long l2 = getAsDecodedLong(buffer2);
		return StringAsciiEbcdicUtil.compare(mode, cs1, String.valueOf(l2));
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefX varDef1, VarBufferPos buffer1)
	{
//		String cs1 = varDef1.getRawStringExcludingHeader(buffer1);
//		String cs2 = getRawStringExcludingHeader(buffer2);
//		
		CStr cs1 = buffer1.getBodyCStr(varDef1);
		CStr cs2 = buffer2.getBodyCStr(this);
		return internalCompare(mode, cs1, cs2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacAlphaNum varDef1, VarBufferPos buffer1)
	{
//		String cs1 = varDef1.getRawStringExcludingHeader(buffer1);
//		String cs2 = getRawStringExcludingHeader(buffer2);
//		
		CStr cs1 = buffer1.getBodyCStr(varDef1);
		CStr cs2 = buffer2.getBodyCStr(this);
		return internalCompare(mode, cs1, cs2);
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacRaw varDef1, VarBufferPos buffer1)
	{
//		String cs1 = varDef1.getRawStringExcludingHeader(buffer1);
//		String cs2 = getRawStringExcludingHeader(buffer2);
//		
		CStr cs1 = buffer1.getBodyCStr(varDef1);
		CStr cs2 = buffer2.getBodyCStr(this);
		return internalCompare(mode, cs1, cs2);
	}
	
//	private void getComparisonBufferItemAndChildren(TempCache tempCache, ComparisonMode mode, CStr csToFill, IntegerRef riAbsolutePosition, VarDefBuffer varDef, VarBufferPos buffer)
//					//TempCache cache, VarBufferPos varBufferPos, int nNbDimUsed)
//	{		
//		if(varDef.m_arrChildren == null)	// Final node
//		{	
//			//int nNbDim = getNbDim();
//			int nNbDim = varDef.getNbDim(); 
//			int nNbDimRemaining = nNbDim - nNbDimUsed;
//			
//			if(nNbDimRemaining < 0)	// Indexed item initializied (x.getAt(n)); the offset is alreday managed
//			{				
//				fillComparisionBuffer(tempCache, mode, csToFill, riAbsolutePosition, varDef, buffer);
//			}
//			else if(nNbDimRemaining == 0)
//			{
//				fillComparisionBuffer(tempCache, mode, csToFill, riAbsolutePosition, varDef, buffer);
//			}
//			else if(nNbDimRemaining == 1)
//			{
//				int nNbX = getMaxIndexAtDim(0);	//getNbTotalItemsInAllDim();
//				for(int x=0; x<nNbX; x++)
//				{
//					CoupleVar coupleVar = varDef.getCoupleCachedGetAt(tempCache, x+1);
//					VarDefBuffer varDefItem = varDef.getCachedGetAt(tempCache, x+1);
//					if(varDefItem != null)
//						fillComparisionBuffer(tempCache, mode, csToFill, riAbsolutePosition, varDefItem, buffer);
//					tempCache.resetTempVarIndex(varDefItem.getTypeId());					
//				}
//			}
//			else if(nNbDimRemaining == 2)
//			{
//				int nNbY = getMaxIndexAtDim(1);
//				int nNbX  = getMaxIndexAtDim(0);
//				for(int y=0; y<nNbY; y++)
//				{
//					for(int x=0; x<nNbX; x++)
//					{
//						VarDefBuffer varDefItem = varDef.getCachedGetAt(tempCache, y+1, x+1);
//						if(varDefItem != null)
//							fillComparisionBuffer(tempCache, mode, csToFill, riAbsolutePosition, varDefItem, buffer);
//						tempCache.resetTempVarIndex(varDefItem.getTypeId());
//					}
//				}
//			}
//			else if(nNbDimRemaining == 3)
//			{
//				int nNbZ = getMaxIndexAtDim(2);
//				int nNbY = getMaxIndexAtDim(1);
//				int nNbX  = getMaxIndexAtDim(0);
//				for(int z=0; z<nNbZ; z++)
//				{
//					for(int y=0; y<nNbY; y++)
//					{
//						for(int x=0; x<nNbX; x++)
//						{
//							VarDefBuffer varDefItem = varDef.getCachedGetAt(tempCache, z+1, y+1, x+1);
//							if(varDefItem != null)
//								fillComparisionBuffer(tempCache, mode, csToFill, riAbsolutePosition, varDefItem, buffer);
//							tempCache.resetTempVarIndex(varDefItem.getTypeId());
//						}
//					}
//				}
//			}			
//		}
//		else
//		{
//			int nNbChildren = varDef.m_arrChildren.size();
//			for(int nChild=0; nChild<nNbChildren; nChild++)
//			{
//				VarDefBuffer varDefChild = getChild(nChild);
//				if(varDefChild != null)
//					if(!varDefChild.isARedefine() && !getFiller())	// PJD: Added because redefines and filler must not be taken in account for
//						varDefChild.getComparisonBufferItemAndChildren(tempCache, mode, csToFill, riAbsolutePosition, varDefChild, buffer);
//			}
//		}
//	}

//	private void fillComparisionBuffer(TempCache tempCache, ComparisonMode mode, CStr csToFill, IntegerRef riAbsolutePosition, VarDefBuffer varDef, VarBufferPos buffer)
//	{
//		CStr cs = buffer.getBodyCStrAtAbsolutePosition(mode, riAbsolutePosition, varDef);
//		csToFill.guaranteeMinialSize(csToFill.length() + cs.length());
//		csToFill.append(cs); 
//		tempCache.rewindCStrMapped(1);
//	}
	
	// Warning: Do not manage occurs !
	private void fillChildrenBuffer(TempCache tempCache, ComparisonMode mode, CStr csToFill, IntegerRef riAbsolutePosition, VarDefBuffer varDef, VarBufferPos buffer)
	{
		int nNbChildren = varDef.getNbChildren();
		if(nNbChildren == 0)
		{
			CStr cs = buffer.getBodyCStrAtAbsolutePosition(mode, riAbsolutePosition, varDef);
			csToFill.guaranteeMinialSize(csToFill.length() + cs.length());
			csToFill.append(cs); 
			tempCache.rewindCStrMapped(1);
		}
		else
		{
			for(int n=0; n<nNbChildren; n++)
			{
				VarDefBuffer varDefChild = varDef.getChild(n);
				if(!varDefChild.isARedefine())	// Exlude redefines
					fillChildrenBuffer(tempCache, mode, csToFill, riAbsolutePosition, varDefChild, buffer);
			}
		}
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefG varDef1, VarBufferPos buffer1)
	{
// 		PJD Start Commented out comparison unification 
//		if(mode == ComparisonMode.UnicodeOrEbcdic)
//		{
//			if(BaseResourceManager.getComparisonInEbcdic())
//				mode = ComparisonMode.Ebcdic;
//			else
//				mode = ComparisonMode.Unicode;
//		}
// 		PJD End Commented out comparison unification 
		
		if(mode == ComparisonMode.Ebcdic)
		{
			TempCache tempCache = TempCacheLocator.getTLSTempCache();
			CStr cs1 = tempCache.getReusableCStr();
			cs1.resetMinimalSize(40);
			CStr cs2 = tempCache.getReusableCStr();
			cs2.resetMinimalSize(40);

			
			IntegerRef riAbsolutePosition = new IntegerRef(buffer1.m_nAbsolutePosition); 
			fillChildrenBuffer(tempCache, mode, cs1, riAbsolutePosition, varDef1, buffer1);
//			VarDefBuffer varDefChild = null;
//			int nNbChildren1 = varDef1.getNbChildren();
//			for(int n=0; n<nNbChildren1; n++)
//			{
//				varDefChild = varDef1.getChild(n);
//				CStr cs = buffer1.getBodyCStrAtAbsolutePosition(mode, riAbsolutePosition, varDefChild);
//				cs1.guaranteeMinialSize(cs1.length() + cs.length());
//				cs1.append(cs); 
//				tempCache.rewindCStrMapped(1);
//			}
			
			riAbsolutePosition.set(buffer2.m_nAbsolutePosition);
			fillChildrenBuffer(tempCache, mode, cs2, riAbsolutePosition, this, buffer2);
//			int nNbChildren2 = getNbChildren();
//			riAbsolutePosition.set(buffer2.m_nAbsolutePosition); 
//			for(int n=0; n<nNbChildren2; n++)
//			{
//				varDefChild = getChild(n);
//				CStr cs = buffer2.getBodyCStrAtAbsolutePosition(mode, riAbsolutePosition, varDefChild);
//				cs2.guaranteeMinialSize(cs1.length() + cs.length());
//				cs2.append(cs);
//				tempCache.rewindCStrMapped(1);
//			}
			return internalCompare(ComparisonMode.Unicode, cs1, cs2);
		}
		else
		{
			CStr s1 = buffer1.getBodyCStr(varDef1);
			CStr s2 = buffer2.getBodyCStr(this);
			return internalCompare(mode, s1, s2);
		}
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
	
	public String digits(VarBufferPos buffer)
	{
		return getAsAlphaNumString(buffer).getAsString();
	}
	
	boolean isConvertibleInEbcdic()
	{
		return false;
	}
	

	public int getTypeId()
	{
		return VarTypeId.VarDefGTypeId;
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
	}

	protected void adjustCustomPropertyForCharGetAt(VarDefBuffer varDefBufferCopySingleItem)
	{
	}
	
	private void internalPhysicalWriteToGroup(VarBufferPos bufferDest, VarDefBuffer varSource, VarBufferPos bufferSource)
	{
		int nNbCharSource = varSource.getBodyLength();
		int nNbCharDest = getBodyLength();
		int nNbCharToCopy = Math.min(nNbCharSource, nNbCharDest);
		int nPositionDest = bufferDest.copyBytesFromSource(getBodyAbsolutePosition(bufferDest), bufferSource, varSource.getBodyAbsolutePosition(bufferSource), nNbCharToCopy);
		if(nNbCharSource < nNbCharDest)
		{
			int nNbSpaceToAdd = nNbCharDest - nNbCharSource; 
			rightPadSpace(bufferDest, nPositionDest, nNbSpaceToAdd);
		}
	}
	
	private void internalPhysicalWriteToGroup(VarBufferPos bufferDest, String csSource)
	{
		int nNbCharSource = csSource.length();
		int nNbCharDest = getBodyLength();
		int nNbCharToCopy = Math.min(nNbCharSource, nNbCharDest);
		
		int nPositionDest = getBodyAbsolutePosition(bufferDest);
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(bufferDest.m_acBuffer, nPositionDest, nNbCharToCopy);
		for(int n=0; n<nNbCharToCopy; n++, nPositionDest++)
		{
			bufferDest.m_acBuffer[nPositionDest] = csSource.charAt(n);
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
		if(nNbCharSource < nNbCharDest)
		{
			int nNbSpaceToAdd = nNbCharDest - nNbCharSource; 
			rightPadSpace(bufferDest, nPositionDest, nNbSpaceToAdd);
		}
	}
	
	private void internalPhysicalWriteToGroup(VarBufferPos bufferDest, CStr csSource)
	{
		int nNbCharSource = csSource.length();
		int nNbCharDest = getBodyLength();
		int nNbCharToCopy = Math.min(nNbCharSource, nNbCharDest);
		
		int nPositionDest = getBodyAbsolutePosition(bufferDest);
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(bufferDest.m_acBuffer, nPositionDest, nNbCharToCopy);
		for(int n=0; n<nNbCharToCopy; n++, nPositionDest++)
		{
			bufferDest.m_acBuffer[nPositionDest] = csSource.charAt(n);
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
		if(nNbCharSource < nNbCharDest)
		{
			int nNbSpaceToAdd = nNbCharDest - nNbCharSource; 
			rightPadSpace(bufferDest, nPositionDest, nNbSpaceToAdd);
		}
	}
	
	private void rightPadSpace(VarBufferPos bufferDest, int nPositionDest, int nNbSpaceToAdd)
	{
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(bufferDest.m_acBuffer, nPositionDest, nNbSpaceToAdd);
		for(int n=0; n<nNbSpaceToAdd; n++, nPositionDest++)
		{
			bufferDest.m_acBuffer[nPositionDest] = ' ';
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
	}
}
