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

import jlib.misc.AsciiEbcdicConverter;
import jlib.misc.LineRead;
import nacaLib.base.CJMapObject;
import nacaLib.base.JmxGeneralStat;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.fpacPrgEnv.FPacVarManager;
import nacaLib.fpacPrgEnv.VarFPacLengthUndef;
import nacaLib.programPool.SharedProgramInstanceData;
import nacaLib.sqlSupport.CSQLItemType;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class VarBase extends CJMapObject
{
	VarBase(DeclareTypeBase declareTypeBase)
	{
		if(declareTypeBase != null)	// InternalInt have no VarDef representation
		{
			BaseProgramManager programManager = declareTypeBase.getProgramManager();
			
			SharedProgramInstanceData sharedProgramInstanceData = programManager.getSharedProgramInstanceData();   
			m_varDef = declareTypeBase.getOrCreateVarDef(sharedProgramInstanceData/*varInstancesHolder*/);
			m_varTypeId = m_varDef.getTypeId();
			
			if(m_varDef.m_varDefRedefinOrigin != null)	// We redefine another var
				m_varDef.m_varDefRedefinOrigin.addRedefinition(m_varDef);
			
			if(declareTypeBase.isVariableLengthDeclaration())
				programManager.defineVarDynLengthMarker((Var)this);

			programManager.registerVar(this);
			//JmxGeneralStat.incNbVar();
		}
	}
	
	protected VarBase()
	{
		//JmxGeneralStat.incNbVarGetAt();
	}
	
//	public void finalize()
//	{
//		if(m_varDef != null && m_varDef.getIsGetAt())
//			JmxGeneralStat.decNbVarGetAt();
//		else
//			JmxGeneralStat.decNbVar();
//	}
	
	public VarDefBuffer getVarDef()
	{
		return m_varDef;
	}
	
	public VarDefBuffer DEBUGgetVarDef()
	{
		return m_varDef;
	}	
	public VarBufferPos getBuffer()
	{
		return m_bufferPos;
	}
	
	boolean isBufferComputed()
	{
		if(m_bufferPos != null)
			return true;
		return false;
	}
	
	public String getSTCheckValue()
	{
		assertIfFalse(false);
		return "";
	}
	
	public boolean is(CobolConstantBase constant)
	{
		char cPattern = constant.getValue();
		String sValue = getString();
		return BaseProgram.isAll(sValue, cPattern);
	}
	
	public SharedProgramInstanceData getSharedProgramInstanceData()
	{
		SharedProgramInstanceData sharedProgramInstanceData = null;
		if(getProgramManager() != null)
			sharedProgramInstanceData = getProgramManager().getSharedProgramInstanceData();
		return sharedProgramInstanceData;
	}
	
	
	public String getLoggableValue()
	{
		if(m_varDef != null)
		{
			SharedProgramInstanceData sharedProgramInstanceData = getSharedProgramInstanceData();
			if(sharedProgramInstanceData != null)
			{
				String cs = m_varDef.toDump(sharedProgramInstanceData);
				int nDefaultAbsolutePosition = m_varDef.DEBUGgetDefaultAbsolutePosition();
				if(m_bufferPos != null)
				{
					int nAbsolutePosition = m_bufferPos.m_nAbsolutePosition;
					if(nDefaultAbsolutePosition != nAbsolutePosition)
					{
						cs += " (@" + nAbsolutePosition + ")";
					}
					cs += ":" + m_varDef.getDottedSignedString(m_bufferPos);
				}
				else
					cs += ":(null)";
				return cs;
			}
			return "SharedProgramInstanceData is null";
		}
		return "VarDef is null";	
	}
	
//	private void dupVarDefShiftingAbsoluteStartPosition(int nShift, ArrayList<VarDefBase> arrVarShifted)
//	{	
//		VarEnumerator e = new VarEnumerator(m_bufferPos.getProgramManager(), this); 
//		VarBase var = e.getFirstVarChild();
//		while(var != null)
//		{
//			var.dupVarDefShiftingAbsoluteStartPosition(nShift, arrVarShifted);
//			var = e.getNextVarChild();
//		}
//		// Shift occurs
//		
//		m_varDef.shiftAbsolutePosition(this, nShift, arrVarShifted);
//		
//		// Shift redefines
//		int nNbRedefinition = m_varDef.getNbRedefinition();
//		for(int n=0; n<nNbRedefinition; n++)
//		{
//			VarDefBase varDefRedefines = m_varDef.getRedefinitionAt(n);
//			VarBase varChild = m_bufferPos.getProgramManager().getVarFullName(varDefRedefines);
//			varChild.dupVarDefShiftingAbsoluteStartPosition(nShift, arrVarShifted);
//		}		
//	}
	
	public void internalAssignBufferShiftPosition(char oldBuffer[], int nStartPos, int nLength, VarBuffer bufferSource, int nShift)
	{
		if(getBuffer().m_acBuffer == oldBuffer && getBodyAbsolutePosition() >= nStartPos && getBodyAbsolutePosition() < nStartPos+nLength)
		{
			m_bufferPos = new VarBufferPos(bufferSource, m_varDef.m_nDefaultAbsolutePosition - nShift);
			getEditAttributManager();
		}
	}
	
//	void assignBuffer(BaseProgramManager programManagerDest, VarBuffer bufferSource)
//	{
//		Log.logCritical("var buffer assigned"+toString());
//		m_bufferPos = new VarBufferPos(programManagerDest, bufferSource, m_varDef.m_nDefaultAbsolutePosition);
//
//		getEditAttributManager(m_bufferPos.getProgramManager());
//
//		VarEnumerator e = new VarEnumerator(m_bufferPos.getProgramManager(), this); 
//		VarBase var = e.getFirstVarChild();
//		while(var != null)
//		{ 
//			var.assignBuffer(programManagerDest, bufferSource);
//			var = e.getNextVarChild();
//		}
//		
//		// The redefinitions must also be linked to the new buffer
//		int nNbRedefinition = m_varDef.getNbRedefinition();
//		for(int n=0; n<nNbRedefinition; n++)
//		{
//			VarDefBase varDefRedefines = m_varDef.getRedefinitionAt(n);
//			VarBase varChild = m_bufferPos.getProgramManager().getVarFullName(varDefRedefines);
//			varChild.assignBuffer(programManagerDest, bufferSource);
//		}
//	}

	public void setAtAdress(VarAndEdit varSource)
	{
		// Old Code
//		assignBuffer(m_bufferPos.getProgramManager(), varSource.m_bufferPos);
//		int nShift = varSource.getBodyAbsolutePosition() - getBodyAbsolutePosition();
//		
//		ArrayList<VarDefBase> arrVarShifted = new ArrayList<VarDefBase>(); 
//		dupVarDefShiftingAbsoluteStartPosition(nShift, arrVarShifted);
		
		// New Code
		int nStartPos = getBodyAbsolutePosition();
		char oldBuffer[] = m_bufferPos.m_acBuffer;
		int nLength = getTotalSize();
		
		int nShift = varSource.getBodyAbsolutePosition() - getBodyAbsolutePosition();
		
		BaseProgramManager pm = TempCacheLocator.getTLSTempCache().getProgramManager();
		pm.changeBufferAndShiftPosition(oldBuffer, nStartPos, nLength, varSource.getBuffer(), -nShift);
	}
	
	public BaseProgramManager getProgramManager()
	{
		BaseProgramManager pm = TempCacheLocator.getTLSTempCache().getProgramManager();
		return pm;
	}
	
	public void setCustomBuffer(char [] cBuffer)
	{		
		// Old Code
//		if(m_varDef.getLevel() != 1)	// Only level 01 can have a custom buffer; their children are also mapped the the buffer 
//			return ;
//		
//		BaseProgramManager programManager = m_bufferPos.getProgramManager();
//		
//		// Create a VarBuffer and set it as the buffer of this
//		VarBuffer varBuffer = new VarBuffer(programManager, cBuffer);		// But it has it's own private var buffer
//		int nShift = m_bufferPos.m_nAbsolutePosition;
//
//		assignBuffer(programManager, varBuffer);	// The current var and all it's children must use the current var buffer		
//		ArrayList<VarDefBase> arrVarShifted = new ArrayList<VarDefBase>();
//		dupVarDefShiftingAbsoluteStartPosition(0 - nShift, arrVarShifted);
		
		// New code
		if(m_varDef.getLevel() != 1)	// Only level 01 can have a custom buffer; their children are also mapped the the buffer 
			return ;
		
		int nStartPos = getBodyAbsolutePosition();
		char oldBuffer[] = m_bufferPos.m_acBuffer;
		int nLength = getTotalSize();
		
		VarBuffer newVarBuffer = new VarBuffer(cBuffer);
		int nShift = m_bufferPos.m_nAbsolutePosition;
		
		BaseProgramManager pm = TempCacheLocator.getTLSTempCache().getProgramManager();
		pm.changeBufferAndShiftPosition(oldBuffer, nStartPos, nLength, newVarBuffer, nShift);
	}

//	public void moveCorresponding(VarBase varDestGroup)
//	{		
//		TempCache tempCache = TempCacheLocator.getTLSTempCache();
//		
//		VarDefBuffer varDefDestGroup = varDestGroup.getVarDef();
//		int nDestOffset = varDestGroup.getInitializeReplacingOffset(tempCache); 
//		int nSourceOffset = getInitializeReplacingOffset(tempCache);
//		
//		SharedProgramInstanceData sharedProgramInstanceData = tempCache.getSharedProgramInstanceData();
//		m_varDef.moveCorrespondingItemAndChildren(sharedProgramInstanceData, tempCache.getProgramManager(), varDefDestGroup, nSourceOffset, nDestOffset);
//		//m_varDef.initializeItemAndChildren(m_bufferPos.getProgramManager(), initializeManagerManager, nOffset);
//	}

	public void moveCorresponding(MoveCorrespondingEntryManager moveCorrespondingEntryManager, VarBase varDestGroup)
	{	
		TempCache tempCache = TempCacheLocator.getTLSTempCache();
		
		VarDefBuffer varDefDestGroup = varDestGroup.getVarDef();
		int nDestOffset = varDestGroup.getInitializeReplacingOffset(tempCache); 
		int nSourceOffset = getInitializeReplacingOffset(tempCache);
		
		if(moveCorrespondingEntryManager != null && moveCorrespondingEntryManager.isFilled())
		{
			moveCorrespondingEntryManager.doMoves(tempCache.getProgramManager(), nSourceOffset, nDestOffset);
		}
		else
		{
			SharedProgramInstanceData sharedProgramInstanceData = tempCache.getSharedProgramInstanceData();
			m_varDef.moveCorrespondingItemAndChildren(moveCorrespondingEntryManager, sharedProgramInstanceData, tempCache.getProgramManager(), varDefDestGroup, nSourceOffset, nDestOffset);
			
			if(moveCorrespondingEntryManager != null)
				moveCorrespondingEntryManager.setFilledAndCompress();
		}		
	}
	
	
//	public void initialize()
//	{
//		TempCache tempCache = TempCacheLocator.getTLSTempCache();
//		InitializeManager initializeManagerManager = tempCache.getInitializeManagerNone();
//		
//		int nOffset = getInitializeReplacingOffset(tempCache);
//		m_varDef.initializeItemAndChildren(m_bufferPos, initializeManagerManager, nOffset);
//	}
	
	public void initialize(InitializeCache initializeCache)
	{
		//TempCache tempCache = TempCacheLocator.getTLSTempCache();
		//int nOffset = getInitializeReplacingOffset(tempCache);
		m_varDef.initializeAtOffset(m_bufferPos, 0, initializeCache);
	}
	
	public void initializeReplacingNum(int n)
	{
		TempCache tempCache = TempCacheLocator.getTLSTempCache();
		InitializeManager initializeManagerManager = tempCache.getInitializeManagerInt(n);
		
		int nOffset = getInitializeReplacingOffset(tempCache);
		m_varDef.initializeItemAndChildren(m_bufferPos, initializeManagerManager, nOffset, null);
	}
	
	public void initializeReplacingNum(String cs)
	{
		TempCache tempCache = TempCacheLocator.getTLSTempCache();
		InitializeManager initializeManagerManager = tempCache.getInitializeManagerDouble(cs);
		
		int nOffset = getInitializeReplacingOffset(tempCache);
		m_varDef.initializeItemAndChildren(m_bufferPos, initializeManagerManager, nOffset, null);
	}
	
	int getInitializeReplacingOffset(TempCache tempCache)
	{
		int nCatalogPos = m_varDef.m_nDefaultAbsolutePosition;
		VarDefBase varDefMaster = m_varDef.getVarDefMaster(tempCache.getSharedProgramInstanceData());
		if(varDefMaster != null)
		{
			nCatalogPos = varDefMaster.m_nDefaultAbsolutePosition;
			m_varDef.m_arrChildren = varDefMaster.m_arrChildren;
		}
		int nItemPos =  m_varDef.m_nDefaultAbsolutePosition;
		int nOffset = nItemPos - nCatalogPos;
		
		return nOffset;
	}	
	
	public void initializeReplacingAlphaNum(String cs)
	{
		TempCache tempCache = TempCacheLocator.getTLSTempCache();
		InitializeManager initializeManagerManager = tempCache.getInitializeManagerString(cs);
		int nOffset = getInitializeReplacingOffset(tempCache);
		m_varDef.initializeItemAndChildren(m_bufferPos, initializeManagerManager, nOffset, null);
	}
	
	public void initializeReplacingNumEdited(int n)
	{
		TempCache tempCache = TempCacheLocator.getTLSTempCache();
		InitializeManager initializeManagerManager = tempCache.getInitializeManagerIntEdited(n);
		int nOffset = getInitializeReplacingOffset(tempCache);
		m_varDef.initializeItemAndChildren(m_bufferPos, initializeManagerManager, nOffset, null);
	}
	
	public void initializeReplacingNumEdited(double d)
	{
		TempCache tempCache = TempCacheLocator.getTLSTempCache();
		InitializeManager initializeManagerManager = tempCache.getInitializeManagerDoubleEdited(d);
		int nOffset = getInitializeReplacingOffset(tempCache);
		m_varDef.initializeItemAndChildren(m_bufferPos, initializeManagerManager, nOffset, null);
	}

	public void initializeReplacingAlphaNumEdited(String cs)
	{
		TempCache tempCache = TempCacheLocator.getTLSTempCache();
		InitializeManager initializeManagerManager = tempCache.getInitializeManagerStringEdited();
		int nOffset = getInitializeReplacingOffset(tempCache);
		m_varDef.initializeItemAndChildren(m_bufferPos, initializeManagerManager, nOffset, null);
	}

	public String toString()
	{		
		return getLoggableValue();
	}
	
	public int getTotalSize()
	{
		return m_varDef.getTotalSize();
	}
	
	public void set(char c)
	{
		m_varDef.write(m_bufferPos, c);
	}
	
	public void set(int n)
	{
		m_varDef.write(m_bufferPos, n);
	}
	
	public void set(double d)
	{
		m_varDef.write(m_bufferPos, d);
	}
	
	public void set(long l)
	{
		m_varDef.write(m_bufferPos, l);
	}
	
	public void set(String cs)
	{
		m_varDef.write(m_bufferPos, cs);
	}
	
	public void set(Dec dec)
	{
		m_varDef.write(m_bufferPos, dec);
	}
	
	public void set(BigDecimal bigDecimal)
	{
		m_varDef.write(m_bufferPos, bigDecimal);
	}
	
	public void set(VarBase varSource)
	{
		if(varSource.isEdit())
			set(varSource);
		else
			set(varSource);
	}
	
	public void declareAsFiller()
	{
		m_varDef.setFiller(true);
	}
	
	int getBodyAbsolutePosition()
	{
		return m_varDef.getBodyAbsolutePosition(m_bufferPos);
	}
	
	public int DEBUGgetBodyAbsolutePosition()
	{
		return m_varDef.getBodyAbsolutePosition(m_bufferPos);
	}

	
	int getBodyLength()
	{
		return m_varDef.getBodyLength();
	}
	
//	public String getFullName()
//	{
//		if(m_varDef != null)
//		{
//			String cs = m_varDef.getFullName(m_bufferPos.getProgramManager().getSharedProgramInstanceData());
//			return cs;
//		}
//		return "";
//	}
	
//	public String getFullName(SharedProgramInstanceData s)
//	{
//		if(m_varDef != null)
//		{
//			String cs = m_varDef.getFullName(s);
//			return cs;
//		}
//		return "";
//	}
	
//	public String getFullNameUpperCase()
//	{
//		if(m_varDef != null)
//		{
//			String cs = m_varDef.getFullName(m_bufferPos.getProgramManager().getSharedProgramInstanceData());
//			cs = cs.toUpperCase();
//			return cs;
//		}
//		return "";
//	}
	
//	public String getUnprefixedName()
//	{
//		String csFullName = getFullName();
//		return NameManager.getUnprefixedName(csFullName);
//	}
	
//	public String getUnprefixedUnindexedName()
//	{
//		String csFullName = getFullName();
//		return NameManager.getUnprefixedUnindexedName(csFullName);
//	}
//	
//	public VarBase getUnprefixedNamedChild(String csName)
//	{		
//		csName = NameManager.getUnprefixedName(csName);
//		VarEnumerator e = new VarEnumerator(m_bufferPos.getProgramManager(), this); 
//		VarBase varChild = e.getFirstVarChild();
//		while(varChild != null)
//		{
//			String csChildName = varChild.getUnprefixedUnindexedName();
//			if(csName.equals(csChildName))
//				return varChild;
//			varChild = e.getNextVarChild();
//		}
//		return null;
//	}
	
	public InternalCharBuffer exportToCharBuffer()
	{
		int nLength = getLength();
		return exportToCharBuffer(nLength) ;
	}
	public InternalCharBuffer exportToCharBuffer(int nLength)
	{
		if (nLength == -1)
		{
			nLength = getLength() ;
		}
		InternalCharBuffer charBufferDest = new InternalCharBuffer(nLength);

		charBufferDest.copyBytes(0, nLength, m_bufferPos.m_nAbsolutePosition, m_bufferPos);
		
		return charBufferDest;
	}
	
	public char [] exportToCharArray()
	{
		int nLength = getLength() ;
		char [] arr = new char [nLength]; 
		int nPositionDest = 0;
		int nPositionSource = m_bufferPos.m_nAbsolutePosition;
		
		for(int n=0; n<nLength; n++, nPositionDest++, nPositionSource++)
		{
			char cSource = m_bufferPos.m_acBuffer[nPositionSource];
			arr[nPositionDest] = cSource;
		}
		return arr;
	}
	
	public void exportToByteArray(byte arr[], int nLength)
	{
		int nPositionDest = 0;
		int nPositionSource = m_bufferPos.m_nAbsolutePosition;
		
		for(int n=0; n<nLength; n++)
		{
			arr[nPositionDest++] = (byte)m_bufferPos.m_acBuffer[nPositionSource++];
		}
	}
		
	public void exportToByteArray(byte arr[], int nOffsetDest, int nLength)
	{
		int nPositionDest = nOffsetDest;
		int nPositionSource = m_bufferPos.m_nAbsolutePosition;
		
		for(int n=0; n<nLength; n++, nPositionDest++, nPositionSource++)
		{
			arr[nPositionDest] = (byte)m_bufferPos.m_acBuffer[nPositionSource];
		}
	}
	
	public void fill(CobolConstantBase constant)
	{
		char c = constant.getValue();
		m_varDef.writeRepeatingchar(m_bufferPos, c);		
	}
	
	public void fillEndOfRecord(int nNbRecordByteAlreadyFilled, int nRecordTotalLength, char cFillerConstant)
	{
		int nNbBytesToFill = nRecordTotalLength - nNbRecordByteAlreadyFilled;
		m_varDef.writeRepeatingcharAtOffsetWithLength(m_bufferPos, nNbRecordByteAlreadyFilled, cFillerConstant, nNbBytesToFill);
		//m_varDef.writeRepeatingchar(m_bufferPos, CobolConstant.LowValue);		
	}
	
	private void fillEndOfRecordForLength(int nNbRecordByteAlreadyFilled, int nNbBytesToFill, char cFillerConstant)
	{
		m_varDef.writeRepeatingcharAtOffsetWithLength(m_bufferPos, nNbRecordByteAlreadyFilled, cFillerConstant, nNbBytesToFill);
	}
	
	public void copyBytesFromSourceIntoBody(InternalCharBuffer charBuffer)
	{
		m_varDef.copyBytesFromSource(m_bufferPos, getBodyAbsolutePosition(), charBuffer);
	}
	
//	public void copyBytesFromSourceIntoBodyAndHeader(InternalCharBuffer charBuffer)
//	{
//		m_varDef.copyBytesFromSource(m_bufferPos, m_bufferPos.m_nAbsolutePosition, charBuffer);
//	}
	
//	public BaseProgram getProgram()
//	{
//		return m_bufferPos.getProgramManager().getProgram();
//	}
	
	public int getLength()
	{
		return m_varDef.getLength(); 
	}
	
//	public String getHexaValueFromAsciiToEbcdic()
//	{
//		String csOut = new String();
//		int nLg = getLength();
//		int nPos = getBodyAbsolutePosition();
//		for(int n=0; n<nLg; n++, nPos++)
//		{			
//			char c = m_bufferPos.m_acBuffer[nPos];
//			//char c = m_bufferPos.getCharAt(nPos);
//			int nCode = c;
//			String cs = AsciiEbcdicConverter.getEbcdicHexaValue(nCode);
//			csOut += cs;
//		}
//		
//		return csOut;
//	}
	
	public String getHexaValueInEbcdic()
	{
		String csOut = new String();
		int nLg = getLength();
		int nPos = getBodyAbsolutePosition(); 
		for(int n=0; n<nLg; n++, nPos++)
		{			
			char c = m_bufferPos.m_acBuffer[nPos];
			//char c = m_bufferPos.getCharAt(nPos);
			int nCode = c;
			String cs = AsciiEbcdicConverter.getHexaValue(nCode);
			csOut += cs;
		}
		
		return csOut;
	}
	
	public boolean DEBUGisStorageAscii()
	{
		return true;
	}
	
	public CSQLItemType getSQLType()
	{
		if(m_varDef != null)
			return m_varDef.getSQLType();
		return null;
	}
	
	public abstract void assignBufferExt(VarBuffer bufferSource);
	
	protected abstract String getAsLoggableString();
	abstract boolean isEdit();
	public abstract boolean hasType(VarTypeEnum e);
	
	abstract EditAttributManager getEditAttributManager();
	
	public void setSemanticContextValue(String csValue)
	{
//		if(m_bufferPos != null)
//			m_bufferPos.setSemanticContextValue(csValue, m_bufferPos.m_nAbsolutePosition);
	}
	
	public String getSemanticContextValue()
	{
//		if(m_bufferPos != null)
//		{
//			String csSemanticValue = m_bufferPos.getSemanticContextValue(m_bufferPos.m_nAbsolutePosition);
//			return csSemanticValue;
//		}
		return "";			
	}	
	
	public String getSemanticContextValue(int nAbsolutePosition)
	{
//		if(m_bufferPos != null)
//		{
//			String csSemanticValue = m_bufferPos.getSemanticContextValue(nAbsolutePosition);
//			return csSemanticValue;
//		}
		return "";			
	}
	
	public void restoreDefaultAbsolutePosition()
	{	
		if(m_varDef != null && m_bufferPos != null)
			m_bufferPos.m_nAbsolutePosition = m_varDef.m_nDefaultAbsolutePosition;
	}
	
	public double getDouble()
	{
		return m_varDef.getDouble(m_bufferPos);
	}
	
	public int getInt()
	{
		return m_varDef.getAsDecodedInt(m_bufferPos);
	}
	
	public long getLong()
	{
		return m_varDef.getAsDecodedLong(m_bufferPos);
	}
	
	public boolean isWSVar()
	{
		return m_varDef.getWSVar();
	}
	
	public int setFromLineRead(LineRead lineRead)
	{
		int nSourceLength = lineRead.getTotalLength();
		int nDestLength = getBodyLength();
		if(nSourceLength > nDestLength)
			nSourceLength = nDestLength;
		m_bufferPos.setByteArray(lineRead.getBuffer(), lineRead.getOffset(), nSourceLength);
		return nSourceLength;
	}
	
	public void setFromLineRead2DestWithFilling(LineRead lineRead, VarBase varDest2, char cFillerConstant)
	{
		// "this" is the variable specified by the into() method
		// varDest2 is the variable carried by the file descriptor
		
		int nSourceLength = lineRead.getTotalLength();
		int nDest1Length = getBodyLength();
		int nFillLength1 = 0;

		if(nSourceLength < nDest1Length)	// Less data than buffer: We must fill the remaining bytes of the buffer1
			nFillLength1 = nDest1Length - nSourceLength;
		
		if(nSourceLength > nDest1Length)	// More data than buffer: We must limit the data to copy in the buffer1
			nSourceLength = nDest1Length;	// Source length is limited by number of bytes of the destination1 (that is the variable specified by into() method

		
		int nFillLength2 = 0;
		int nDest2Length = varDest2.getBodyLength();
		
		if(nDest1Length < nDest2Length)	// The length of destination variable 2 (file descriptor) is longer than the length of the variable specified by into()
			nFillLength2 = nDest1Length - nDest2Length;	// We must fill the remaining bytes of varDest2
		
		if(nDest1Length > nDest2Length)		// The length of destination variable 2 (file descriptor) is shoreter than the length of the variable specified by into()
			nDest2Length = nDest1Length;
				
		m_bufferPos.setByteArray(lineRead.getBuffer(), lineRead.getOffset(), nSourceLength, varDest2.m_bufferPos, nDest2Length);
		
		if(nFillLength1 != 0)
			fillEndOfRecordForLength(nSourceLength, nFillLength1, cFillerConstant);
		
		if(nFillLength2 != 0)
			varDest2.fillEndOfRecordForLength(nSourceLength, nFillLength2, cFillerConstant);
	}
	
	public void setFromByteArray(byte[] tBytes, int nOffsetSource, int nLength)
	{
		m_bufferPos.setByteArray(tBytes, nOffsetSource, nLength);
	}
	
//	void fillWithSameByteAtOffset(byte by, int nOffset, int nNbOccurences)
//	{
//		m_bufferPos.fillWithSameByteAtOffset(by, nOffset, nNbOccurences);
//	}
	
	
	public byte[] getAsByteArray()
	{
		//int nLength = getLength();
		int nLength = m_varDef.getRecordDependingLength(m_bufferPos);
		char[] tChars = m_bufferPos.getByteArray(this, nLength);
		byte[] tBytes = AsciiEbcdicConverter.noConvertUnicodeToEbcdic(tChars);
		return tBytes;
	}

	
	public byte[] getAsEbcdicByteArray()
	{
		int nLength = getLength();
		char[] tChars = m_bufferPos.getByteArray(this, nLength);
		byte[] tBytes = convertUnicodeToEbcdic(tChars);
		return tBytes;
	}

	protected abstract byte[] convertUnicodeToEbcdic(char[] tBytes);
	protected abstract char[] convertEbcdicToUnicode(byte[] tBytes);
	
	public byte[] doConvertUnicodeToEbcdic(char[] tChars)
	{
		return AsciiEbcdicConverter.convertUnicodeToEbcdic(tChars);
	}
	
	public char[] doConvertEbcdicToUnicode(byte[] tBytes)
	{
		return AsciiEbcdicConverter.convertEbcdicToUnicode(tBytes);
	}
	
	
//	public void resetTempIndex(TempCache tempCache)
//	{
//		tempCache.resetTempVarIndex(m_varTypeId);
//	}

	public abstract String getString() ;
	public abstract String getDottedSignedString() ;
	public abstract String getDottedSignedStringAsSQLCol();
	
	
	public VarFPacLengthUndef createVarFPacUndef(FPacVarManager fpacVarManager, VarBuffer varBuffer, int nAbsolutePosition)
	{
		return null;
	}
	
	public String DEBUGgetBufferDumpHexaInEbcdic()
	{
		return getHexaValueInEbcdic();
	}
	
	public void importFromByteArray(byte tBytesSource[], int nSizeSource)
	{
		m_bufferPos.importFromByteArray(tBytesSource, m_varDef.getTotalSize(), nSizeSource);
	}
	
	public void exportIntoByteArray(byte tbyDest[], int nLengthDest)
	{
		m_bufferPos.exportIntoByteArray(tbyDest, nLengthDest, m_varDef.getTotalSize());
	}
	
	
	public int getId()
	{
		return m_varDef.getId();
	}
		
//	public int getIdSolvedDim()
//	{
//		return m_varDef.getIdSolvedDim();
//	}
	

	
	
	
	
	/*protected*/ public VarDefBuffer m_varDef = null;		// definition of variable options and memory storage (common with other vars)
	/*protected*/ public VarBufferPos m_bufferPos = null;		// physical data buffer
	public abstract VarType getVarType();
	//private String m_csSemanticContextValue = null;
	
	// Experimental optimizations
//	public void setTempVar()
//	{
//		m_bTempVar = true;
//	}
	
//	public boolean isTempVar()
//	{
//		return m_bTempVar; 
//	}
	
//	public int getTypeId()
//	{
////		if(m_varTypeId == VarTypeId.VarDefUnknownTypeId)
////			m_varTypeId = m_varDef.getTypeId();
//		return m_varTypeId;
//	}
	
	public int m_varTypeId = VarTypeId.VarDefUnknownTypeId;
	//private boolean m_bTempVar = false;

}
