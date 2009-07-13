/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 26 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import java.math.BigDecimal;


import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.bdb.BtreeSegmentKeyTypeFactory;
import nacaLib.sqlSupport.CSQLItemType;
import nacaLib.tempCache.CStr;
import nacaLib.tempCache.TempCacheLocator;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VarDefMapRedefine extends VarDefBuffer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VarDefMapRedefine(VarDefBase varDefParent, DeclareTypeMapRedefine declareTypeMapRedefine)
	{
		super(varDefParent, declareTypeMapRedefine.m_varLevel);
	}
			
	protected VarDefMapRedefine()
	{
		super();
	}
	
//	VarDefMapRedefine(VarDefMapRedefine varDefSource)
//	{
//		super(varDefSource);
//	}
//	
//	VarDefBuffer deepDuplicate()
//	{
//		return new VarDefMapRedefine(this);
//	}
	
	void transfer(VarBufferPos bufferSource, VarAndEdit Dest)
	{
	}
	
	CSQLItemType getSQLType()
	{
		return null;
	}
	
//	protected void assignOnOriginEdit()
//	{
//	}
	
	protected boolean isAVarDefMapRedefine()
	{
		return true;
	}
	
	protected boolean isEditInMapRedefine()
	{
		return false;
	}
	
	protected boolean isEditInMapOrigin()
	{
		return false;
	}
	
	protected boolean isVarInMapRedefine()
	{
		return false;
	}
	
	protected boolean isVarDefForm()
	{
		return false;
	}

	
//	protected void inheritOriginEditSizes(VarBase varRoot)
//	{
//	}

	public void mapOnOriginEdit()
	{
		m_varDefRedefinOrigin = m_varDefFormRedefineOrigin;
	}	


	
	protected VarDefBuffer allocCopy()
	{
		VarDefForm v = new VarDefForm();
		return v;
	}
	
	public int getBodyLength()
	{
		return m_nTotalSize;
	}
	
	protected int getHeaderLength()
	{
		return 12;
	}

	public int getSingleItemRequiredStorageSize()
	{
		if(m_varDefFormRedefineOrigin != null)
			return m_varDefFormRedefineOrigin.getTotalSize();
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
	
	CStr getAsAlphaNumString(VarBufferPos buffer)
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
	
	void write(VarBufferPos buffer, char c)
	{
	}
	
	public void write(VarBufferPos buffer, String cs)
	{
		assertIfFalse(false);
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
		assertIfFalse(false);
	}

	public void write(VarBufferPos buffer, long l)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, double d)
	{ 
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, Dec dec)
	{
		assertIfFalse(false);
	}
	
	public void write(VarBufferPos buffer, BigDecimal bigDecimal)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefG varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}

	void write(VarBufferPos bufferDest, VarDefX varDefSource, VarBufferPos bufferSource)
	{
		BaseProgramManager programManager = TempCacheLocator.getTLSTempCache().getProgramManager();
		VarBase varSource = programManager.getVarFullName(varDefSource);
		m_varDefFormRedefineOrigin.decodeFromVar(bufferDest, varSource);
	}

	void write(VarBufferPos bufferDest, VarDefFPacAlphaNum varDefSource, VarBufferPos bufferSource)
	{
		BaseProgramManager programManager = TempCacheLocator.getTLSTempCache().getProgramManager();
		VarBase varSource = programManager.getVarFullName(varDefSource);
		m_varDefFormRedefineOrigin.decodeFromVar(bufferDest, varSource);
	}
	
	void write(VarBufferPos bufferDest, VarDefFPacRaw varDefSource, VarBufferPos bufferSource)
	{
		BaseProgramManager programManager = TempCacheLocator.getTLSTempCache().getProgramManager();
		VarBase varSource = programManager.getVarFullName(varDefSource);
		m_varDefFormRedefineOrigin.decodeFromVar(bufferDest, varSource);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecComp0 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecComp3 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);	
	}
	
	void write(VarBufferPos buffer, VarDefNumDecComp4 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);	
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignComp4 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);	
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignComp0 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignComp3 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignLeadingComp0 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefNumDecSignTrailingComp0 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntComp0 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}

	void write(VarBufferPos buffer, VarDefNumIntComp0Long varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}

	void write(VarBufferPos buffer, VarDefNumIntComp3 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntComp3Long varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	
	void write(VarBufferPos buffer, VarDefNumIntComp4 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntComp4Long varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp0 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp0Long varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}

	void write(VarBufferPos buffer, VarDefNumIntSignComp3 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefFPacNumIntSignComp3 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp3Long varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);	
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp4 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
 	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignComp4Long varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignLeadingComp0 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefNumIntSignLeadingComp0Long varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}

	
	void write(VarBufferPos buffer, VarDefNumIntSignTrailingComp0 varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}

	void write(VarBufferPos buffer, VarDefNumIntSignTrailingComp0Long varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}	
	
	void write(VarBufferPos buffer, VarDefEditInMap varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefEditInMapRedefine varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}

	void write(VarBufferPos buffer, VarDefEditInMapRedefineNumEdited varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, VarDefEditInMapRedefineNum varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);
	}


	void write(VarBufferPos buffer, VarDefNumEdited varSource, VarBufferPos bufferSource)
	{
		assertIfFalse(false);	
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
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, CobolConstantSpace cst, int nOffsetPosition, int nNbChar)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, CobolConstantLowValue cst, int nOffsetPosition, int nNbChar)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, CobolConstantHighValue cst, int nOffsetPosition, int nNbChar)
	{
		assertIfFalse(false);
	}
	
	void write(VarBufferPos buffer, String csValue, int nOffsetPosition, int nNbChar)
	{		
		assertIfFalse(false);
	}
	
	void writeAndFill(VarBufferPos buffer, char c)
	{
		assertIfFalse(false);
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
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp0 varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp3 varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp4 varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp4 varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp0 varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp3 varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignLeadingComp0 varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignTrailingComp0 varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}

	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp0 varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp0Long varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp3 varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp3Long varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}

	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp4 varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp4Long varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp0 varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
		
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp0Long varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
		
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp3 varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacNumIntSignComp3 varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp3Long varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}


	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp4 varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp4Long varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}


	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignLeadingComp0 varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignLeadingComp0Long varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}	
	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignTrailingComp0 varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignTrailingComp0Long varDefNum1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}	
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefX varDef1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacAlphaNum varDef1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacRaw varDef1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
	
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefG varDef1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		return 0;
	}
		
	int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumEdited varDef1, VarBufferPos buffer1)
	{
		assertIfFalse(false);
		// TODO how to compare with num edited ?
		return 0;
	}
	
	boolean isNumeric(VarBufferPos buffer)
	{
		return false;
	}
	
	public boolean isAlphabetic(VarBufferPos buffer)
	{
		return false;
	}
	
	void assignForm(VarDefForm varDefForm)
	{
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
		return VarTypeId.VarDefMapRedefine;
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

	public void moveIntoSameType(VarBufferPos buffer, VarDefBuffer varSource, VarBufferPos bufferSource)
	{
		// never used
	}
}
