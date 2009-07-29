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
 * Created on 25 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import java.math.BigDecimal;

import jlib.misc.*;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.bdb.BtreeSegmentKeyTypeFactory;
import nacaLib.sqlSupport.CSQLItemType;
import nacaLib.tempCache.CStr;
import nacaLib.tempCache.TempCacheLocator;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VarDefForm extends VarDefBuffer
{
	public VarDefForm(VarDefBase varDefParent, DeclareTypeForm declareTypeForm)
	{
		super(varDefParent, declareTypeForm.m_varLevel);
		m_arrFields = new ArrayDyn<VarDefBuffer>(); 
	}
			
	protected VarDefForm()
	{
		super();
	}
	
	CSQLItemType getSQLType()
	{
		return null;
	}
	
	protected boolean isAVarDefMapRedefine()
	{
		return false;
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
		return true;
	}
	
	void transfer(VarBufferPos bufferSource, VarAndEdit varDest)
	{
		encodeToVar(bufferSource, varDest);
	}
	
	protected VarDefBuffer allocCopy()
	{
		VarDefForm v = new VarDefForm();
		return v;
	}
	
	public int getBodyLength()
	{
		return m_nTotalSize - getHeaderLength();
	}
	
	protected int getHeaderLength()
	{
		return 12;
	}

	public int getSingleItemRequiredStorageSize()
	{
		return 0;
	}
		
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
		CStr cs = buffer.getStringAt(getBodyAbsolutePosition(buffer), getBodyLength());
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
	
	CStr getAsAlphaNumString(VarBufferPos buffer)
	{		
		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
		return cs;
	}
	
	CStr getDottedSignedString(VarBufferPos buffer)
	{	
		CStr cs = buffer.getStringAt(buffer.m_nAbsolutePosition, m_nTotalSize);
		return cs;
	}
	
	CStr getDottedSignedStringAsSQLCol(VarBufferPos buffer)
	{	
		CStr cs = buffer.getStringAt(getBodyAbsolutePosition(buffer), getBodyLength());
		return cs;
	}
	
	void write(VarBufferPos buffer, char c)
	{
	}
	
	public void write(VarBufferPos buffer, String cs)
	{
		writeRightPadding(buffer, cs, ' ');
	}
	
	private int writeRightPadding(VarBufferPos buffer, String cs, char cPad)
	{
		return internalWriteRightPadding(buffer, buffer.m_nAbsolutePosition, m_nTotalSize, cs, cPad);
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
		decodeFromVar(bufferDest, varSource);
	}
	
	void write(VarBufferPos bufferDest, VarDefFPacAlphaNum varDefSource, VarBufferPos bufferSource)
	{
		BaseProgramManager programManager = TempCacheLocator.getTLSTempCache().getProgramManager();
		VarBase varSource = programManager.getVarFullName(varDefSource);
		decodeFromVar(bufferDest, varSource);
	}
	
	void write(VarBufferPos bufferDest, VarDefFPacRaw varDefSource, VarBufferPos bufferSource)
	{
		BaseProgramManager programManager = TempCacheLocator.getTLSTempCache().getProgramManager();
		VarBase varSource = programManager.getVarFullName(varDefSource);
		decodeFromVar(bufferDest, varSource);
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
		writeRepeatingcharAt(buffer, nOffsetPosition, cst.getValue(), nNbChar);
	}
	
	void write(VarBufferPos buffer, CobolConstantSpace cst, int nOffsetPosition, int nNbChar)
	{
		writeRepeatingcharAt(buffer, nOffsetPosition, cst.getValue(), nNbChar);
	}
	
	void write(VarBufferPos buffer, CobolConstantLowValue cst, int nOffsetPosition, int nNbChar)
	{
		writeRepeatingcharAt(buffer, nOffsetPosition, cst.getValue(), nNbChar);
	}
	
	void write(VarBufferPos buffer, CobolConstantHighValue cst, int nOffsetPosition, int nNbChar)
	{
		writeRepeatingcharAt(buffer, nOffsetPosition, cst.getValue(), nNbChar);
	}
	
	void write(VarBufferPos buffer, String csValue, int nOffsetPosition, int nNbChar)
	{		
		assertIfFalse(false);
	}
	
	void writeAndFill(VarBufferPos buffer, char c)
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
	
	public void addField(VarDefBuffer varDefEdit)
	{
		m_arrFields.add(varDefEdit);		 		
	}
	
	int getNbFields()
	{
		if(m_arrFields != null)
			return m_arrFields.size();
		return 0;
	}
	
	VarDefEdit getEditAt(int nIndex)
	{
		if(m_arrFields != null)
			return (VarDefEdit) m_arrFields.get(nIndex);
		return null;
	}

	public void encodeToVar(VarBufferPos bufferSource, VarAndEdit varDest)
	{
		int nDestLength = varDest.getBodyLength();
		InternalCharBuffer tempCharBuffer = encodeToCharBuffer(nDestLength);
		varDest.copyBytesFromSourceIntoBody(tempCharBuffer);
		//int nOffset = varDest.getBodyAbsolutePosition();
		//varDest.m_bufferPos.inheritSemanticContext(tempCharBuffer, nOffset);
	}
	
	public InternalCharBuffer encodeToCharBuffer(int nDestLength)
	{	
		BaseProgramManager programManager = TempCacheLocator.getTLSTempCache().getProgramManager();
		
		InternalCharBuffer charBuffer = new InternalCharBuffer(nDestLength);
		int nPos = 0;
		nPos = charBuffer.writeString("HHHHHHHHHHHH", nPos);	// Header
		if(nPos != -1)
		{
			// Encode all chlidren except 1st, as it is the header (encoded below)
			int nNbEdits = getNbFields();
			int nNbChildren = getNbChildren();
			
			VarDefEdit varDefEdit = null;
			VarDefBuffer varDefChild = null;
			int nEdit = 0;
			if(nNbEdits > 0)
				varDefEdit = getEditAt(nEdit);
			for(int nChild=0; nChild<nNbChildren && nPos != -1; nChild++)	// Skip first child, as it is the fake header
			{
				varDefChild = getChild(nChild);
				if(varDefChild != null)
				{
					if(varDefChild == varDefEdit)	// The child is the current edit
					{
						Edit var2Edit = (Edit)programManager.getVarFullName(varDefEdit);
						
						CStr cs = var2Edit.m_bufferPos.getBodyCStr(varDefChild);
						String csText = cs.getAsString();
								
						//String csText = varDefChild.getRawStringExcludingHeader(var2Edit.m_bufferPos);	// getString
						int nEditLength = varDefEdit.getBodyLength();

//						String csSemanticContextValue = var2Edit.getSemanticContextValue();
//						charBuffer.setSemanticContextValue(csSemanticContextValue, nPos);
						
						nPos = var2Edit.encodeIntoCharBuffer(charBuffer, csText, nEditLength, nPos);
						
						nEdit++;
						if(nEdit < nNbEdits)
							varDefEdit = getEditAt(nEdit);
						else
							varDefEdit = null;
					}
					else	// The child is a var that may be interleaved with edits, or after all edits
					{					
						int nSourceLength = varDefChild.getLength();
						VarBase varChild = programManager.getVarFullName(varDefChild);
						
//						String csSemanticContextValue = varChild.getSemanticContextValue();
//						charBuffer.setSemanticContextValue(csSemanticContextValue, nPos);

						if(varDefChild.m_varDefRedefinOrigin == null)	// Not a redefine
						{	
							//String csText = varDefChild.getRawStringExcludingHeader(varChild.m_bufferPos);
							String csText = varChild.m_bufferPos.getBodyCStr(varDefChild).getAsString();							
							nPos = charBuffer.writeString(csText, nSourceLength, nPos);
						}	
					}
				}
			}
		}
		TempCacheLocator.getTLSTempCache().resetCStr();
		return charBuffer;
	}

	public void decodeFromVar(VarBufferPos bufferDest, VarBase varSource)
	{
		BaseProgramManager programManager = TempCacheLocator.getTLSTempCache().getProgramManager();
		int nPosSource = 12;
		
		int nOffset = varSource.getBodyAbsolutePosition();
		
		int nNbEdits = getNbFields();
		int nNbChildren = getNbChildren();
		
		// Ignore 1st chilst as it is the 12 chars header
		VarDefEdit varDefEdit = null;
		VarDefBuffer varDefChild = null;

		int nEdit = 0;
		if(nNbEdits > 0)
			varDefEdit = getEditAt(nEdit);
		for(int nChild=0; nChild<nNbChildren; nChild++)	// Skip fake header
		{
			varDefChild = getChild(nChild);
			if(varDefChild != null)
			{
				if(varDefChild == varDefEdit)	// The child is the current edit
				{
					int nEditLength = varDefEdit.getBodyLength();
					Edit var2Edit = (Edit)programManager.getVarFullName(varDefEdit);
					
					String csSemanticContextValue = varSource.getSemanticContextValue(nPosSource + nOffset);
					var2Edit.setSemanticContextValue(csSemanticContextValue);
					
					nPosSource = var2Edit.decodeFromVar(varSource, nPosSource, nEditLength);
					nEdit++;
					if(nEdit < nNbEdits)
						varDefEdit = getEditAt(nEdit);
					else
						varDefEdit = null;				
				}
				else	// The child is a var that may be interleaved with edits, or after all edits
				{
					//String csSemanticContextValue = varSource.getSemanticContextValue();
					// PJD: Semantic context to move 		
					if(varDefChild.m_varDefRedefinOrigin == null)	// Not a redefine
					{
						int nDestLength = varDefChild.getLength();
						nPosSource = copySourceChunk(bufferDest, varSource, nPosSource-12, nPosSource, nDestLength);
					}
				}
			}
		}
	}
		
	public void decodeFromCharBuffer(VarBufferPos bufferDest, InternalCharBuffer charBuffer)
	{
		BaseProgramManager programManager = TempCacheLocator.getTLSTempCache().getProgramManager();
		int nPosSource = 12;
		
		int nNbEdits = getNbFields();
		int nNbChildren = getNbChildren();
		
		// Ignore 1st chilst as it is the 12 chars header
		VarDefEdit varDefEdit = null;
		VarDefBuffer varDefChild = null;

		int nEdit = 0;
		if(nNbEdits > 0)
			varDefEdit = getEditAt(nEdit);
		for(int nChild=0; nChild<nNbChildren; nChild++)	// Skip fake header
		{
			varDefChild = getChild(nChild);
			if(varDefChild != null)
			{
				if(varDefChild == varDefEdit)	// The child is the current edit
				{
					int nEditLength = varDefEdit.getBodyLength();
					Edit var2Edit = (Edit)programManager.getVarFullName(varDefEdit);
					nPosSource = var2Edit.decodeFromCharBuffer(charBuffer, nPosSource, nEditLength);
					nEdit++;
					if(nEdit < nNbEdits)
						varDefEdit = getEditAt(nEdit);
					else
						varDefEdit = null;				
				}
				else	// The child is a var that may be interleaved with edits, or after all edits
				{
					int nDestLength = varDefChild.getLength();
					nPosSource = copySourceChunk(bufferDest, charBuffer, nPosSource-12, nPosSource, nDestLength);
				}
			}
		}
	}
	
	protected int writeRepeatingchar(VarBufferPos buffer, char c)
	{
		return buffer.writeRepeatingCharAt(buffer.m_nAbsolutePosition, c, m_nTotalSize);
	}
	
	protected int writeRepeatingcharAt(VarBufferPos buffer, int nPosition, char c, int nNbChars)
	{
		return buffer.writeRepeatingCharAt(nPosition, c, nNbChars);
	}	
	
	void assignForm(VarDefForm varDefForm)
	{
	}
	
	VarDefBuffer getChildAtDefaultPosition(int nAbsolutePosition)
	{
		int nNbChildren = getNbChildren();
		for(int nChild=0; nChild<nNbChildren; nChild++)	// Skip fake header
		{
			VarDefBuffer varDefChild = getChild(nChild);
			if(varDefChild.m_nDefaultAbsolutePosition == nAbsolutePosition)
				return varDefChild;
		}
		return null;
	}
	
	public String digits(VarBufferPos buffer)
	{
		return getAsAlphaNumString(buffer).getAsString();
	}
	
	public void compress()
	{
		if(m_arrFields != null)
		{
			// Swap the type inside m_arrRedefinition 
			if(m_arrFields.isDyn())
			{
				int nSize = m_arrFields.size();
				VarDefBuffer arr[] = new VarDefBuffer[nSize];
				m_arrFields.transferInto(arr);
				
				ArrayFix<VarDefBuffer> arrFix = new ArrayFix<VarDefBuffer>(arr);
				m_arrFields = arrFix;	// replace by a fix one (uning less memory)
			}
		}
		super.compress();
	}
	
	public void prepareAutoRemoval()
	{
		m_arrFields = null;
		super.prepareAutoRemoval();
	}
	
	boolean isConvertibleInEbcdic()
	{
		return false;
	}
	
	public int getTypeId()
	{
		return VarTypeId.VarDefFormTypeId;
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
		VarDefForm varDefCopy = (VarDefForm)varDefBufferCopySingleItem;
		varDefCopy.m_arrFields = m_arrFields;
	}
	
	protected void adjustCustomPropertyForCharGetAt(VarDefBuffer varDefBufferCopySingleItem)
	{
		VarDefForm varDefCopy = (VarDefForm)varDefBufferCopySingleItem;
		varDefCopy.m_arrFields = m_arrFields;
	}
	
	// Should never be called
	public void moveIntoSameType(VarBufferPos buffer, VarDefBuffer varSource, VarBufferPos bufferSource)
	{
	}
	
	protected ArrayFixDyn<VarDefBuffer> m_arrFields = null;	// Array of VarDefEdit
}
