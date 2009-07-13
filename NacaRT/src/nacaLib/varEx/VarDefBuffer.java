/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 18 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import jlib.log.Log;
import jlib.misc.BaseDataFile;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.misc.StringAsciiEbcdicUtil;
import nacaLib.programPool.SharedProgramInstanceData;
import nacaLib.sqlSupport.CSQLItemType;
import nacaLib.tempCache.CStr;
import nacaLib.tempCache.CStrNumber;
import nacaLib.tempCache.CStrString;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class VarDefBuffer extends VarDefBase
{
	protected VarDefBuffer()
	{
		super();
	}
	
	VarDefBuffer(VarDefBase varDefParent, VarLevel varLevel)
	{
		super(varDefParent, varLevel);
	}
			
	protected void copyBytesFromSource(VarBufferPos bufferDest, int nPosition, InternalCharBuffer charBuffer)
	{
		bufferDest.copyBytesFromSource(nPosition, charBuffer);
	}
	
	void dumpToSTCheck(BaseProgramManager programManager)
	{		
		dump(programManager);
		int nNbChildren = getNbChildren();
		for(int nChild=0; nChild<nNbChildren; nChild++)
		{
			VarDefBuffer varDefChild = getChild(nChild);			
			varDefChild.dumpToSTCheck(programManager);
		}
	}

	private void dump(BaseProgramManager programManager)
	{
		VarBase varChild = programManager.getVarFullName(this);
		String cs = varChild.getSTCheckValue();
		Log.logFineDebug("dumpSTCheck:" + cs);
	}
	
	int copySourceChunk(VarBufferPos bufferDest, VarBase varSource, int nPosDest, int nPosSource, int nDestLength)
	{
		int nPositionDest = getBodyAbsolutePosition(bufferDest)+ nPosDest;
		int nPositionSource = varSource.getBodyAbsolutePosition() + nPosSource;
		bufferDest.copyBytesFromSource(nPositionDest, varSource.m_bufferPos, nPositionSource, nDestLength);
		
		return nPosSource + nDestLength;
	}
	
	int copySourceChunk(VarBufferPos bufferDest, InternalCharBuffer charBufferSource, int nPosDest, int nPosSource, int nDestLength)
	{
		int nPositionDest = getBodyAbsolutePosition(bufferDest)+ nPosDest;
		int nPositionSource = nPosSource;
		bufferDest.copyBytesFromSource(nPositionDest, charBufferSource, nPositionSource, nDestLength);
		
		return nPosSource + nDestLength;
	}
	
	protected int internalWriteRightPadding(VarBufferPos buffer, int nPosition, int nTotalSize, String cs, char cPad)
	{
		int nLength = 0;
		if(cs != null)
		{
			nLength = cs.length();
			if(nTotalSize < nLength)
				nLength = nTotalSize;
			cs.getChars(0, nLength, buffer.m_acBuffer, nPosition);
			nPosition += nLength;
		}
		if(nLength < nTotalSize)	// Padding with BLANK on the right
		{
			int nNbChars = nTotalSize-nLength;
			nPosition = buffer.writeRepeatingCharAt(nPosition, cPad, nNbChars);
		}
		return nPosition;
	}
	
	protected void internalWriteRightPaddingSpace(VarBufferPos buffer, int nPosition, int nTotalSize, String cs)
	{
		int nLength = 0;
		if(cs != null)
		{
			nLength = cs.length();
			if(nTotalSize < nLength)
				nLength = nTotalSize;
			cs.getChars(0, nLength, buffer.m_acBuffer, nPosition);
			nPosition += nLength;
		}
		if(nLength < nTotalSize)	// Padding with BLANK on the right
		{
			int nNbChars = nTotalSize-nLength;
			for(int n=0; n<nNbChars; n++)
				buffer.m_acBuffer[nPosition++] = ' ';
		}
	}
	

	
	protected int writeEditRightPaddingBlankInit(VarBufferPos buffer, int nOffset, InitializeCache initializeCache)
	{
		int nBodyPosStart = getBodyAbsolutePosition(buffer);
		int nBodyLength = getBodyLength();
		return internalWriteRightPaddingBlankInitSpace(buffer, nBodyPosStart+nOffset, nBodyLength, initializeCache);
	}
	
	protected int internalWriteRightPaddingBlankInitSpace(VarBufferPos buffer, int nPosition, int nTotalSize, InitializeCache initializeCache)
	{
		if(nTotalSize >= 1)
		{
			if(initializeCache != null)
				initializeCache.addItem(' ', nPosition, nTotalSize);
			nPosition = buffer.writeRepeatingCharAt(nPosition, ' ', nTotalSize);
		}
		return nPosition;
	}

	protected int internalWriteRightPaddingBlankInit0(VarBufferPos buffer, int nPosition, int nTotalSize, InitializeCache initializeCache)
	{	
		if(nTotalSize >= 1)
		{
			if(initializeCache != null)
				initializeCache.addItem('0', nPosition, nTotalSize);
			nPosition = buffer.writeRepeatingCharAt(nPosition, '0', nTotalSize);
		}
		return nPosition;
	}
	
	protected int internalWriteRightPadding(VarBufferPos buffer, int nPosition, int nTotalSize, CStr cs, char cPad)
	{
		int nLength = 0;
		if(cs != null)
		{
			nLength = Math.min(cs.length(), nTotalSize);
			nPosition = buffer.setStringAt(nPosition, cs, nLength);
		}
		if(nLength < nTotalSize)	// Padding with BLANK on the right
		{
			int nNbChars = nTotalSize-nLength;
			nPosition = buffer.writeRepeatingCharAt(nPosition, cPad, nNbChars);
		}
		return nPosition;
	}
	
	protected void internalWriteJustifyRightPadding(VarBufferPos buffer, int nPosition, int nDestLength, String cs, char cPad)
	{
		/*
		http://docs.hp.com/cgi-bin/doc3k/B3150090013.11820/52
		
		Data is moved from a sending data item to the right justified receiving
		data item starting with the rightmost character of the sending data item.
		The rightmost character is placed in the rightmost character of the
		receiving data item.  The next rightmost data item of the sending data
		item is then moved to the next rightmost character of the receiving data
		item.  This process continues until either all of the sending data item
		has been moved, or the receiving data item is full.  Note that a space in
		the sending data item is considered a valid character, no matter where it
		is within the sending data item.  That is, spaces are not stripped from
		the sending item, even if they are in the rightmost positions of the
		sending item.
		
		When a receiving data item is described using this clause, and the
		sending data item is larger than the receiving item, the leftmost
		characters are truncated.
		
		When the receiving data item is longer than a sending item, the data is
		aligned at the rightmost character position in the receiving field, and
		unused characters to the left are filled with spaces
		*/
		
		if(cs != null)
		{
			int nSourceLength = cs.length();
			if(nSourceLength > nDestLength)					// truncate leftmosts char of source
			{
				cs = cs.substring(nSourceLength - nDestLength);
				nSourceLength = nDestLength;
			}
			int nPosStart = nDestLength - nSourceLength;
			buffer.setStringAt(nPosition+nPosStart, cs, nSourceLength);
			nPosition = buffer.writeRepeatingCharAt(nPosition, cPad, nPosStart);
		}
	}
	
	protected void internalWriteJustifyRightPadding(VarBufferPos buffer, int nPosition, int nDestLength, CStr cs, char cPad)
	{
		/*
		http://docs.hp.com/cgi-bin/doc3k/B3150090013.11820/52
		
		Data is moved from a sending data item to the right justified receiving
		data item starting with the rightmost character of the sending data item.
		The rightmost character is placed in the rightmost character of the
		receiving data item.  The next rightmost data item of the sending data
		item is then moved to the next rightmost character of the receiving data
		item.  This process continues until either all of the sending data item
		has been moved, or the receiving data item is full.  Note that a space in
		the sending data item is considered a valid character, no matter where it
		is within the sending data item.  That is, spaces are not stripped from
		the sending item, even if they are in the rightmost positions of the
		sending item.
		
		When a receiving data item is described using this clause, and the
		sending data item is larger than the receiving item, the leftmost
		characters are truncated.
		
		When the receiving data item is longer than a sending item, the data is
		aligned at the rightmost character position in the receiving field, and
		unused characters to the left are filled with spaces
		*/
		
		if(cs != null)
		{
			int nSourceLength = cs.length();
			if(nSourceLength > nDestLength)					// truncate leftmosts char of source
			{
				cs.selfSubstring(nSourceLength - nDestLength);
				nSourceLength = nDestLength;
			}
			int nPosStart = nDestLength - nSourceLength;
			buffer.setStringAt(nPosition+nPosStart, cs, nSourceLength);
			nPosition = buffer.writeRepeatingCharAt(nPosition, cPad, nPosStart);
		}
	}
	
	protected int internalWriteNoPadding(VarBufferPos buffer, int nPosition, int nTotalSize, String cs)
	{
		if(cs != null)
		{
			int nLength = Math.min(cs.length(), nTotalSize);
			nPosition = buffer.setStringAt(nPosition, cs, nLength);
		}
		return nPosition;
	}
		
	protected CStr getCStrRightPadded(int n, int nNbCharDest)
	{
		CStrNumber cs = TempCacheLocator.getTLSTempCache().getCStrNumber();
		cs.setAbsoluteValueRightPadded(n, nNbCharDest);
		return cs;
	}
	
	protected CStr getCStrRightPadded(CStr csIn, char cPad, int nNbCharDest)
	{
		CStrString csOut = TempCacheLocator.getTLSTempCache().getCStrString();
		csOut.set(csIn, cPad, nNbCharDest);
		return csOut;
	}
		
//	protected CStr getCStrRightPadded(long l, int nNbCharDest)
//	{
//		CStrNumber cs = TempCacheLocator.getTLSTempCache().getCStrNumber();
//		cs.setAbsoluteValueRightPadded(l, nNbCharDest);
//		return cs;
//	}
	
//	protected String getStringRightPadded(int n, char cPad, int nNbCharDest)
//	{
//		String cs = String.valueOf(n);
//		return getStringRightPadded(cs, cPad, nNbCharDest);
//	}
	
//	protected String getStringRightPadded(long l, char cPad, int nNbCharDest)
//	{
//		String cs = String.valueOf(l);
//		return getStringRightPadded(cs, cPad, nNbCharDest);
//	}
	
//	protected String getStringRightPadded(String cs, char cPad, int nNbCharDest)
//	{	
//		StringBuffer buf = new StringBuffer();
//			
//		int nLength = 0;
//		if(cs != null)
//		{
//			nLength = Math.min(cs.length(), nNbCharDest);
//			String csLeftPart = cs.substring(0, nLength);
//			buf.append(csLeftPart);
//		}
//		while(nLength < nNbCharDest)	// Pad on the right
//		{
//			buf.append(cPad);
//			nLength++;
//		}			
//		return buf.toString();
//	}
	
	protected void internalPhysicalWrite(VarBufferPos bufferDest, VarDefBuffer varSource, VarBufferPos bufferSource)
	{
		int nNbCharSource = varSource.getBodyLength();
		int nNbCharDest = getBodyLength();
		int nNbCharToCopy = Math.min(nNbCharSource, nNbCharDest);
		bufferDest.copyBytesFromSource(getBodyAbsolutePosition(bufferDest), bufferSource, varSource.getBodyAbsolutePosition(bufferSource), nNbCharToCopy);
	}
	
//	protected void internalPhysicalWrite(VarBufferPos bufferDest, String csSource)
//	{
//		int nNbCharSource = csSource.length();
//		int nNbCharDest = getBodyLength();
//		int nNbCharToCopy = Math.min(nNbCharSource, nNbCharDest);
//		
//		int nPositionDest = getBodyAbsolutePosition(bufferDest);
//		for(int n=0; n<nNbCharToCopy; n++, nPositionDest++)
//		{
//			char cSource = csSource.charAt(n);
//			bufferDest.m_acBuffer[nPositionDest] = cSource;
//		}
//	}
	
//	protected void internalPhysicalWrite(VarBufferPos bufferDest, CStr csSource)
//	{
//		int nNbCharSource = csSource.length();
//		int nNbCharDest = getBodyLength();
//		int nNbCharToCopy = Math.min(nNbCharSource, nNbCharDest);
//		
//		int nPositionDest = getBodyAbsolutePosition(bufferDest);
//		for(int n=0; n<nNbCharToCopy; n++, nPositionDest++)
//		{
//			char cSource = csSource.charAt(n);
//			bufferDest.m_acBuffer[nPositionDest] = cSource;
//		}
//	}
		
	void fillInitialValueAndClearUnusedMembers(TempCache cache, SharedProgramInstanceData sharedProgramInstanceData, VarBuffer buffer)
	{
		if(m_arrChildren == null)	// Final node
		{	
			int nNbDim = getNbDim();
			if(nNbDim == 0)
			{
				setInitialValueAndClearUnusedMembers(sharedProgramInstanceData, buffer);
			}
			else if(nNbDim == 1)
			{
				int nNbX = getMaxIndexAtDim(0);
				for(int x=0; x<nNbX; x++)
				{					
					VarDefBuffer varDefItem = getCachedGetAt(cache, x+1);
					if(varDefItem != null)
						varDefItem.setInitialValueAndClearUnusedMembers(sharedProgramInstanceData, buffer);
					if(cache != null)
						cache.resetTempVarIndex(varDefItem.getTypeId());
				}
			}
			else if(nNbDim == 2)
			{
				int nNbY = getMaxIndexAtDim(1);
				int nNbX  = getMaxIndexAtDim(0);
				for(int y=0; y<nNbY; y++)
				{
					for(int x=0; x<nNbX; x++)
					{
						//VarDefBuffer varDefItemOld = getAt(y+1, x+1);
						VarDefBuffer varDefItem = getCachedGetAt(cache, y+1, x+1);
						if(varDefItem != null)
							varDefItem.setInitialValueAndClearUnusedMembers(sharedProgramInstanceData, buffer);
						if(cache != null)
							cache.resetTempVarIndex(varDefItem.getTypeId());
					}
				}
			}
			else if(nNbDim == 3)
			{
				int nNbZ = getMaxIndexAtDim(2);
				int nNbY = getMaxIndexAtDim(1);
				int nNbX  = getMaxIndexAtDim(0);
				for(int z=0; z<nNbZ; z++)
				{
					for(int y=0; y<nNbY; y++)
					{
						for(int x=0; x<nNbX; x++)
						{
							VarDefBuffer varDefItemOld = getAt(z+1, y+1, x+1);
							VarDefBuffer varDefItem = getCachedGetAt(cache, z+1, y+1, x+1);
							if(varDefItem != null)
								varDefItem.setInitialValueAndClearUnusedMembers(sharedProgramInstanceData, buffer);
							if(cache != null)
								cache.resetTempVarIndex(varDefItem.getTypeId());
						}
					}
				}
			}
		}
		else
		{
			int nNbChildren = m_arrChildren.size();
			for(int nChild=0; nChild<nNbChildren; nChild++)
			{
				VarDefBuffer varDefChild = getChild(nChild);
				if(varDefChild != null)
					varDefChild.fillInitialValueAndClearUnusedMembers(cache, sharedProgramInstanceData, buffer);
			}
			setInitialValueAndClearUnusedMembers(sharedProgramInstanceData, buffer);
		}
	}	
	
	private void setInitialValueAndClearUnusedMembers(SharedProgramInstanceData sharedProgramInstanceData, VarBuffer buffer)
	{
		VarBufferPos bufferPos = new VarBufferPos(buffer, m_nDefaultAbsolutePosition); 
		CInitialValue initialValue = sharedProgramInstanceData.getInitialValue(getId());
		if(initialValue != null)
		{
			if(initialValue.m_bFill)
			{
				char c = 0;
				String cs = initialValue.m_genericValue.getAsString();
				if(cs.length() > 0)
					c = cs.charAt(0); 
				writeRepeatingchar(bufferPos, c);
			}
			else
			{
				String cs = initialValue.m_genericValue.getAsString();
				write(bufferPos, cs);
			}			
		}
	}
	
	
	
//	void initializeItemAndChildren(ProgramManager programManager, InitializeManager initializeManager)
//	{
//		if(m_arrChildren == null)	// Final node
//		{	
//			int nNbTotalItems = getNbTotalItemsInAllDim();
//			for(int n=0; n<nNbTotalItems; n++)
//			{
//				VarDefBuffer varDefItem = getAtAllDim(n);
//				if(varDefItem != null)
//					varDefItem.tryInitialize(programManager, initializeManager);
//			}
//		}
//		else
//		{
//			int nNbChildren = m_arrChildren.size();
//			for(int nChild=0; nChild<nNbChildren; nChild++)
//			{
//				VarDefBuffer varDefChild = getChild(nChild);
//				if(varDefChild != null)
//					varDefChild.initializeItemAndChildren(programManager, initializeManager);
//			}
//		}
//	}
	
	void moveCorrespondingItemAndChildren(MoveCorrespondingEntryManager manager, SharedProgramInstanceData sharedProgramInstanceData, BaseProgramManager programManager, VarDefBase varDefDestGroup, int nSourceOffset, int nDestOffset)
	{
		if(m_arrChildren == null)	// Final node
		{	
			String csSourceName = getUnprefixedUnindexedName(sharedProgramInstanceData).toUpperCase();
			// Find in destination the child with the same name, if it exists
			VarDefBase varDefItemDest = varDefDestGroup.getNamedChild(sharedProgramInstanceData, csSourceName);
			//if(varDefItemDest != null && !csSourceName.equals("FILLER"))	// move if found
			if(varDefItemDest != null && !varDefItemDest.getFiller())
			{
				if(manager != null)
				{
					MoveCorrespondingEntry moveCorrespondingEntry = new MoveCorrespondingEntry(this, varDefItemDest);
					manager.addEntry(moveCorrespondingEntry);
				}
				
				VarBase varSource = programManager.getVarFullName(this);
				VarBase varDest = programManager.getVarFullName(varDefItemDest);
				varSource.m_bufferPos.m_nAbsolutePosition += nSourceOffset;
				varDest.m_bufferPos.m_nAbsolutePosition += nDestOffset;
				varDest.set(varSource);
				varSource.m_bufferPos.m_nAbsolutePosition -= nSourceOffset;
				varDest.m_bufferPos.m_nAbsolutePosition -= nDestOffset;
			}
		}
		else
		{
			int nNbChildren = m_arrChildren.size();
			for(int nChild=0; nChild<nNbChildren; nChild++)
			{
				VarDefBuffer varDefChild = getChild(nChild);
				if(varDefChild != null)
					varDefChild.moveCorrespondingItemAndChildren(manager, sharedProgramInstanceData, programManager, varDefDestGroup, nSourceOffset, nDestOffset);
			}
		}
	}
	
//	void initializeUsingCache(VarBufferPos varBufferPos, InitializeCache initializeCache)	//, int nOffset)
//	{
//		initializeCache.applyItems(varBufferPos, varBufferPos.m_nAbsolutePosition);	//, nOffset);
//	}	
	 
//	void initializeItemAndChildren(VarBufferPos varBufferPos, InitializeManager initializeManager, int nOffset)
//	{
//		initializeItemAndChildren(varBufferPos, initializeManager, nOffset, null);		
//	}
	
	void initializeItemAndChildren(VarBufferPos varBufferPos, InitializeManager initializeManager, int nOffset, InitializeCache initializeCache)
	{
		int nOldAbsolutePosition = varBufferPos.m_nAbsolutePosition;
		//char acOldBuffer[] = varBufferPos.getCharArray();
		//boolean bOldShared = varBufferPos.isShared();
		
		TempCache cache = TempCacheLocator.getTLSTempCache();
		initializeItemAndChildren(cache, varBufferPos, initializeManager, nOffset, getTempNbDim(), initializeCache);
		
		varBufferPos.restore(nOldAbsolutePosition, varBufferPos.m_acBuffer);	//, varBufferPos.isShared());
	}
	
	private void initializeItemAndChildren(TempCache cache, VarBufferPos varBufferPos, InitializeManager initializeManager, int nOffset, int nNbDimUsed, InitializeCache initializeCache)
	{		
		if(m_arrChildren == null)	// Final node
		{	
			//int nNbDim = getNbDim();
			int nNbDim = getNbDim(); 
			int nNbDimRemaining = nNbDim - nNbDimUsed;
			
			if(nNbDimRemaining == 0)
			{
				tryInitialize(varBufferPos, initializeManager, nOffset, initializeCache);
			}
			else if(nNbDimRemaining == 1)
			{
				int nNbX = getMaxIndexAtDim(0);	//getNbTotalItemsInAllDim();
				for(int x=0; x<nNbX; x++)
				{
					CoupleVar coupleVar = getCoupleCachedGetAt(cache, x+1);
					VarDefBuffer varDefItem = getCachedGetAt(cache, x+1);
					if(varDefItem != null)
						varDefItem.tryInitialize(varBufferPos, initializeManager, nOffset, initializeCache);
					if(cache != null)
						cache.resetTempVarIndex(varDefItem.getTypeId());					
				}
			}
			else if(nNbDimRemaining == 2)
			{
				int nNbY = getMaxIndexAtDim(1);
				int nNbX  = getMaxIndexAtDim(0);
				for(int y=0; y<nNbY; y++)
				{
					for(int x=0; x<nNbX; x++)
					{
						//VarDefBuffer varDefItem = getAt(y+1, x+1);
						VarDefBuffer varDefItem = getCachedGetAt(cache, y+1, x+1);
						if(varDefItem != null)
							varDefItem.tryInitialize(varBufferPos, initializeManager, nOffset, initializeCache);
						if(cache != null)
							cache.resetTempVarIndex(varDefItem.getTypeId());
					}
				}
			}
			else if(nNbDimRemaining == 3)
			{
				int nNbZ = getMaxIndexAtDim(2);
				int nNbY = getMaxIndexAtDim(1);
				int nNbX  = getMaxIndexAtDim(0);
				for(int z=0; z<nNbZ; z++)
				{
					for(int y=0; y<nNbY; y++)
					{
						for(int x=0; x<nNbX; x++)
						{
							//VarDefBuffer varDefItem = getAt(z+1, y+1, x+1);
							VarDefBuffer varDefItem = getCachedGetAt(cache, z+1, y+1, x+1);
							if(varDefItem != null)
								varDefItem.tryInitialize(varBufferPos, initializeManager, nOffset, initializeCache);
							if(cache != null)
								cache.resetTempVarIndex(varDefItem.getTypeId());
						}
					}
				}
			}
			else if(nNbDimRemaining < 0)	// Indexed item initializied (x.getAt(n)); the offset is alreday managed
			{				
				tryInitialize(varBufferPos, initializeManager, 0, initializeCache);
			}
		}
		else
		{
			int nNbChildren = m_arrChildren.size();
			for(int nChild=0; nChild<nNbChildren; nChild++)
			{
				VarDefBuffer varDefChild = getChild(nChild);
				if(varDefChild != null)
					if(!varDefChild.isARedefine()) 
						varDefChild.initializeItemAndChildren(cache, varBufferPos, initializeManager, nOffset, nNbDimUsed, initializeCache);
			}
		}
	}

	private void tryInitialize(VarBufferPos varBufferPos, InitializeManager initializeManager, int nOffset, InitializeCache initializeCache)
	{
		if(m_varDefRedefinOrigin == null && !getFiller())
		{
			BaseProgramManager programManager = TempCacheLocator.getTLSTempCache().getProgramManager();
			programManager.getBufferPosOfVarDef(this, varBufferPos);
			//boolean b = programManager.getBufferPosOfVarDef(this, varBufferPos);
//			assertIfFalse(b);
			//if(b)
				initializeManager.initialize(varBufferPos, this, nOffset, initializeCache);
			
			//initializeManager.initialize(varBufferPos, this, nOffset);
		}
	}
	
//	private void tryInitialize(ProgramManager programManager, InitializeManager initializeManager)
//	{
//		if(m_varDefRedefinOrigin == null && !m_bFiller)
//		{
//			VarBase varChild = programManager.getVarFullName(this);
//			initializeManager.initialize(varChild.m_bufferPos, this);
//		}
//	}
	
	protected int writeRepeatingchar(VarBufferPos buffer, char c)
	{
		return buffer.writeRepeatingCharAt(buffer.m_nAbsolutePosition, c, m_nTotalSize);
	}	
	
	protected int writeRepeatingcharAtOffset(VarBufferPos buffer, int nOffset, char c)
	{
		return buffer.writeRepeatingCharAt(buffer.m_nAbsolutePosition+nOffset, c, m_nTotalSize);
	}
	
	protected int writeRepeatingcharAtOffsetWithLength(VarBufferPos buffer, int nOffset, char c, int nNbBytes)
	{
		return buffer.writeRepeatingCharAt(buffer.m_nAbsolutePosition+nOffset, c, nNbBytes);
	}	
	
	protected void writeRepeatingCharUpToEnd(VarBufferPos buffer, char c, int nOffsetPosition, int nNbChar)
	{
		int nMaxCharOnRight = m_nTotalSize - nOffsetPosition;
		int nNbCharsToWrite = Math.min(nMaxCharOnRight, nNbChar);
		buffer.writeRepeatingCharAt(buffer.m_nAbsolutePosition+nOffsetPosition, c, nNbCharsToWrite);
	}
	
	protected int internalWriteAtOffsetPosition(VarBufferPos buffer, String csValue, int nOffset, int nNbChar, char cPad)
	{
		int nMaxCharOnRight = m_nTotalSize - nOffset;
		int nNbCharsToWrite = Math.min(nMaxCharOnRight, nNbChar);
		return internalWriteRightPadding(buffer, buffer.m_nAbsolutePosition+nOffset, nNbCharsToWrite, csValue, cPad);
	}	
	
	protected int internalWriteSubstringComp0(VarBufferPos buffer, String csValue, int nOffset, int nNbChar)
	{
		int nMaxCharOnRight = m_nTotalSize - nOffset;
		int nNbCharsToWrite = Math.min(nMaxCharOnRight, nNbChar);
		return internalWriteNoPadding(buffer, buffer.m_nAbsolutePosition+nOffset, nNbCharsToWrite, csValue);
	}	
	
	public double getDouble(VarBufferPos buffer)
	{
		Dec dec = getAsDecodedDec(buffer);
		return dec.getAsDouble();
	}
	
	public long getUnsignedLong(VarBufferPos buffer)
	{
		long l = getAsDecodedLong(buffer);
		if(l < 0)
			return -l;
		return l;
	}
	
	public int getUnsignedInt(VarBufferPos buffer)
	{
		int n = getAsDecodedUnsignedInt(buffer);
		return n;
	}
	
	public Dec getUnsignedDec(VarBufferPos buffer)
	{
		Dec dec = getAsDecodedDec(buffer);
		dec.setUnsigned();
		return dec;
	}

	abstract CSQLItemType getSQLType();
	abstract int getAsDecodedInt(VarBufferPos buffer);
	abstract int getAsDecodedUnsignedInt(VarBufferPos buffer);
	abstract long getAsDecodedLong(VarBufferPos buffer);
	abstract Dec getAsDecodedDec(VarBufferPos buffer);
	abstract CStr getDottedSignedString(VarBufferPos buffer);
	abstract CStr getDottedSignedStringAsSQLCol(VarBufferPos buffer);
	abstract CStr getAsDecodedString(VarBufferPos buffer);
	abstract CStr getAsAlphaNumString(VarBufferPos bufferSource);
	
	//abstract VarDefBuffer deepDuplicate();
	
	abstract public void inc(VarBufferPos buffer, int n);
	abstract public void inc(VarBufferPos buffer, BigDecimal bdStep);

	abstract void write(VarBufferPos buffer, CobolConstantZero cst);
	abstract void write(VarBufferPos buffer, CobolConstantSpace cst);
	abstract void write(VarBufferPos buffer, CobolConstantLowValue cst);
	abstract void write(VarBufferPos buffer, CobolConstantHighValue cst);
	
	abstract void write(VarBufferPos buffer, CobolConstantZero cst, int nOffsetPosition, int nNbChar);
	abstract void write(VarBufferPos buffer, CobolConstantSpace cst, int nOffsetPosition, int nNbChar);
	abstract void write(VarBufferPos buffer, CobolConstantLowValue cst, int nOffsetPosition, int nNbChar);
	abstract void write(VarBufferPos buffer, CobolConstantHighValue cst, int nOffsetPosition, int nNbChar);
		
	abstract void write(VarBufferPos buffer, char c);
	public abstract void write(VarBufferPos buffer, int n);
	public abstract void write(VarBufferPos buffer, long l);
	abstract void write(VarBufferPos buffer, double d);
	public abstract void write(VarBufferPos buffer, String cs);
	abstract void write(VarBufferPos buffer, Dec dec);
	public abstract void write(VarBufferPos buffer, BigDecimal bigDecimal);
	
	abstract void write(VarBufferPos buffer, String csValue, int nOffsetPosition, int nNbChar);
	
	abstract void writeAndFill(VarBufferPos buffer, char c);

	abstract public void moveIntoSameType(VarBufferPos bufferPosDest, VarDefBuffer varDefSource, VarBufferPos bufferSource);
	
	abstract void transfer(VarBufferPos bufferSource, VarAndEdit Dest);	

	abstract void write(VarBufferPos buffer, VarDefG varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefX varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumDecComp0 varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumDecComp3 varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumDecComp4 varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumDecSignComp4 varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumDecSignComp0 varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumDecSignComp3 varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumDecSignLeadingComp0 varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumDecSignTrailingComp0 varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumIntComp0 varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumIntComp0Long varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumIntComp3 varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumIntComp3Long varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumIntComp4 varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumIntComp4Long varSource, VarBufferPos bufferSource);	
	abstract void write(VarBufferPos buffer, VarDefNumIntSignComp0 varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumIntSignComp0Long varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumIntSignComp3 varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumIntSignComp3Long varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumIntSignComp4 varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumIntSignComp4Long varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumIntSignLeadingComp0 varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumIntSignLeadingComp0Long varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumIntSignTrailingComp0 varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumIntSignTrailingComp0Long varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefEditInMap varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefEditInMapRedefine varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefEditInMapRedefineNum varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefEditInMapRedefineNumEdited varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefNumEdited varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefFPacNumIntSignComp3 varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefFPacAlphaNum varSource, VarBufferPos bufferSource);
	abstract void write(VarBufferPos buffer, VarDefFPacRaw varSource, VarBufferPos bufferSource);
	
//	abstract void initialize(VarBufferPos buffer);
//	abstract void initialize(VarBufferPos buffer, String cs);
//	abstract void initialize(VarBufferPos buffer, int n);
	
	//abstract void initializeEdited(VarBufferPos buffer, String cs);
	//abstract void initializeEdited(VarBufferPos buffer, int n);

	abstract void initializeAtOffset(VarBufferPos buffer, int nOffset, InitializeCache initializeCache);
	abstract void initializeAtOffset(VarBufferPos buffer, int nOffset, int n);
	abstract void initializeAtOffset(VarBufferPos buffer, int nOffset, String cs);
	
	abstract void initializeEditedAtOffset(VarBufferPos buffer, int nOffset, int nValue);
	abstract void initializeEditedAtOffset(VarBufferPos buffer, int nOffset, double dValue);
	
	abstract boolean isConvertibleInEbcdic();
	
	abstract int compare(ComparisonMode mode, VarBufferPos bufferSource, VarAndEdit var2);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefG varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefX varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp0 varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp3 varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecComp4 varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp4 varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp0 varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignComp3 varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignLeadingComp0 varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumDecSignTrailingComp0 varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp0 varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp0Long varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp3 varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp3Long varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp4 varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntComp4Long varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp0 varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp0Long varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp3 varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacNumIntSignComp3 varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp3Long varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp4 varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignComp4Long varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignLeadingComp0 varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignLeadingComp0Long varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignTrailingComp0 varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumIntSignTrailingComp0Long varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefNumEdited varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacAlphaNum varSource, VarBufferPos buffer1);
	abstract int compare(ComparisonMode mode, VarBufferPos buffer2, VarDefFPacRaw varSource, VarBufferPos buffer1);
	
	abstract boolean isNumeric(VarBufferPos buffer);
	abstract boolean isAlphabetic(VarBufferPos buffer);
	
	
	abstract public String digits(VarBufferPos buffer);
	
	public boolean isEqualWithSameTypeTo(VarBufferPos buffer1, VarDefBuffer varDefBuffer2, VarBufferPos buffer2)
	{
		// Same length
		int nPosition1 = buffer1.m_nAbsolutePosition;
		int nPosition2 = buffer2.m_nAbsolutePosition;
		for(int n=0; n<m_nTotalSize; n++)
		{
			if(buffer1.m_acBuffer[nPosition1++] != buffer2.m_acBuffer[nPosition2++])
				return false;
		}
		return true;
	}
	
	protected int internalCompare(int n1, int n2)
	{
		if(n1 == n2)
			return 0;
		if(n1 < n2)
			return -1;
		return 1;
	}
	
	protected int internalCompare(long l1, long l2)
	{
		if(l1 == l2)
			return 0;
		if(l1 < l2)
			return -1;
		return 1;
	}

	protected int internalCompare(int n1, Dec d2)
	{
		int n = d2.compare(n1);
		if(n == 0)	// d2 == n1
			return 0;
		if(n < 0)	// d2 < n1
			return 1;
		return -1;		
	}
	
	protected int internalCompare(long l1, Dec d2)
	{
		int n = d2.compare(l1);
		if(n == 0)	// d2 == n1
			return 0;
		if(n < 0)	// d2 < n1
			return 1;
		return -1;		
	}
	
	protected int internalCompare(Dec d1, int n2)
	{
		return d1.compare(n2);
	}
	
	protected int internalCompare(Dec d1, long l2)
	{
		return d1.compare(l2);
	}
	
	protected int internalCompare(Dec d1, Dec d2)
	{
		return d1.compare(d2);
	}
	
	protected int internalCompare(ComparisonMode mode, String cs1, String cs2)
	{
		return StringAsciiEbcdicUtil.compare(mode, cs1, cs2);
	}
	
	protected int internalCompare(ComparisonMode mode, CStr cs1, CStr cs2)
	{
		return StringAsciiEbcdicUtil.compare(mode, cs1, cs2);
	}
	
	protected boolean internalIsRawStringNumeric(VarBufferPos buffer)
	{
		CStr cs = buffer.getBodyCStr(this);
		boolean b = cs.isOnlyNumeric();
		return b;
//		String cs = getRawStringExcludingHeader(buffer);
//		
//		int nLg = cs.length();
//		for(int n=0; n<nLg; n++)
//		{
//			char c = cs.charAt(n);
//			if(!((c >= '0' && c <= '9') || c == '+' || c == '-' ))
//				return false;  
//		}
//		return true;
	}
	
	protected boolean internalIsRawStringAlphabetic(VarBufferPos buffer)
	{
		CStr cs = buffer.getBodyCStr(this);
		return cs.isOnlyAlphabetic();
//		
//		String cs = getRawStringExcludingHeader(buffer);
//		
//		int nLg = cs.length();
//		for(int n=0; n<nLg; n++)
//		{
//			char c = cs.charAt(n);
//			if(!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == ' '))
//				return false;  
//		}
//		return true;
	}
	
	static int getDecodedEditAttributes(InternalCharBuffer buffer, int nPos)
	{
		// Header is:
		// 4 char for attributes
		// 1 char for Flag
		// 2 char at 0 for filling
		// Body is:
		// csText
		int nAttributes = buffer.getIntAt(nPos);
		return nAttributes;
	}
	
	static char getDecodedEditFlag(InternalCharBuffer buffer, int nPos) 
	{
		// Header is:
		// 4 char for attributes
		// 1 char for Flag
		// 2 char as a short for Text length
		// variable length csText
		char cFlag = buffer.m_acBuffer[nPos + 4];
		//char cFlag = buffer.getCharAt(nPos + 4);
		return cFlag;
	}

//	void shiftOnceAbsolutePosition(VarBase var, int nShift, int nShiftGeneration)
//	{
//		int nCurrentShiftGeneration = getShiftGeneration();
//		if(nCurrentShiftGeneration != nShiftGeneration)
//		{
//			setShiftGeneration(nShiftGeneration);
//			var.m_bufferPos.m_nAbsolutePosition += nShift;
//		}
//		else
//		{
//			int gg = 0;
//		}
//	}
	
	void shiftAbsolutePosition(VarBase var, int nShift, ArrayList<VarDefBase> arrVarShifted)
	{		
		boolean bThisInArray = isThisInArray(arrVarShifted);
		if(!bThisInArray)
		{
			arrVarShifted.add(this);
			var.m_bufferPos.m_nAbsolutePosition += nShift;
			Log.logCritical("var shifted At pos"+var.m_bufferPos.m_nAbsolutePosition);
		}
	}
	
	private boolean isThisInArray(ArrayList<VarDefBase> arrVarShifted)
	{
		int nNbItems = arrVarShifted.size();
		for(int n=0; n<nNbItems; n++)
		{
			if(arrVarShifted.get(n) == this)
				return true;
		}
		return false;
	}
		
//	boolean DEBUGCheckRangeWithinToParentRange()
//	{
//		if(m_varDefParent != null)
//		{
//			int nStartPosParent = m_varDefParent.m_nDefaultAbsolutePosition;
//			int nStartPos = m_nDefaultAbsolutePosition;
//			if(nStartPos < nStartPosParent)
//			{
//				Assert("Child: '" + toString() + "' starts before it's parent variable: " + m_varDefParent.toString() + "'");	// We are not inside parent range
//				return false;
//			}
//			
//			int nLastPosParent = nStartPosParent + m_varDefParent.getHeaderLength()+m_varDefParent.getBodyLength() - 1;
//			int nLastPos = nStartPos + getHeaderLength()+getBodyLength() - 1;
//			if(nLastPos > nLastPosParent)
//			{
//				Assert("Child: '" + toString() + "' ends after it's parent variable ending: '" + m_varDefParent.toString() + "'");	// We are not inside parent range
//				return false;
//			}
//		}
//		return true;		
//	}
	
//	boolean DEBUGCheckParentage()
//	{
//		if(m_varDefParent != null)
//		{
//			boolean b = m_varDefParent.isChildKnown(this);	// Check if our parent known us 
//			if(!b)
//				Assert("Child: '" + toString() + "' isn't correctly parented by: '" + m_varDefParent.toString() + "'");	// We are not inside parent range
//			b = DEBUGCheckRangeWithinToParentRange();	// Chck if our range is withinparent range
//			return b;
//		}
//		return true;		
//	}
	
	VarDefBuffer getVarDefEditInMapOrigin()
	{
		return null;
	}
	
//	void restoreLinkageOriginalPosition(ProgramManager programManager)
//	{
//		if(m_arrChildren == null)	// Final node
//		{	
//			int nNbTotalItems = getNbTotalItemsInAllDim();
//			for(int n=0; n<nNbTotalItems; n++)
//			{
//				VarDefBuffer varDefItem = getAtAllDim(n);
//				if(varDefItem != null && varDefItem != this)
//					varDefItem.restoreLinkageOriginalPosition(programManager);
//			}
//		}
//		else
//		{
//			int nNbChildren = m_arrChildren.size();
//			for(int nChild=0; nChild<nNbChildren; nChild++)
//			{
//				VarDefBuffer varDefChild = getChild(nChild);
//				if(varDefChild != null)
//					varDefChild.restoreLinkageOriginalPosition(programManager);
//			}
//		}	
//		restoreLinkageVarOriginalPosition(programManager);
//	}
	
//	private void restoreLinkageVarOriginalPosition(ProgramManager programManager)
//	{
//		VarBase varChild = programManager.getVarFullName(this);
//		varChild.m_bufferPos.m_nAbsolutePosition = m_nDefaultAbsolutePosition;
//		int n = 0;
//	}
	
	public boolean isLongVarCharVarStructure()
	{
		if(m_arrChildren != null)
		{
			if(getNbChildren() == 2)	// 2 children
			{
				VarDefBuffer varDefChildLength = getChild(0);
				if(varDefChildLength.isTypedLongVarCharLength())
				{
					VarDefBuffer varDefChildText = getChild(1);
					if(varDefChildText.isTypedLongVarCharText())
						return true;
				}
			}
		}
		return false;
	}
	
	boolean isTypedLongVarCharLength()
	{
		return false;
	}
	
	boolean isTypedLongVarCharText()
	{
		return false;
	}
	
	public int getNbDigitDecimal()
	{
		return -1;
	}
	
	public void writeToFile(BaseProgramManager programManager, BaseDataFile dataFile, VarBufferPos bufferPos, boolean bConvertUnicodeToEbcdic)
	{		
		if(m_arrChildren == null)	// Final node
		{
			VarBase var = programManager.getVarFullName(this);
			byte[] tBytes = null;
			if(bConvertUnicodeToEbcdic)
				tBytes = var.getAsEbcdicByteArray();
			else
				tBytes = var.getAsByteArray();
			dataFile.write(tBytes);
			dataFile.flush();
		}
		else
		{
			int nNbChildren = m_arrChildren.size();
			for(int nChild=0; nChild<nNbChildren; nChild++)
			{
				VarDefBuffer varDefChild = getChild(nChild);
				if(varDefChild != null)
					varDefChild.writeToFile(programManager, dataFile, bufferPos, bConvertUnicodeToEbcdic);
			}
		}
	}
	
//	public boolean readFromFile(ProgramManager programManager, DataFile dataFile, VarBufferPos bufferPos, boolean bConvertEbcdicToUnicode)
//	{		
//		// Read the line
//		VarBase var = programManager.getVarFullName(this);
//		int nSize = var.getTotalSize();
//		dataFile.readLine(nSize);
//		
//		if(m_arrChildren == null)	// Final node
//		{
//			VarBase var = programManager.getVarFullName(this);
//			int nSize = var.getTotalSize();
//			byte[] tBytes = dataFile.read(nSize);
//			if(bConvertEbcdicToUnicode)
//				var.setFromEbcdicByteArray(tBytes);
//			else
//				var.setFromByteArray(tBytes);
//		}
//		else
//		{
//			boolean bEOF = false;
//			int nNbChildren = m_arrChildren.size();
//			for(int nChild=0; nChild<nNbChildren && !bEOF; nChild++)
//			{
//				VarDefBuffer varDefChild = getChild(nChild);
//				if(varDefChild != null)
//					bEOF = varDefChild.readFromFile(programManager, dataFile, bufferPos, bConvertEbcdicToUnicode);
//			}
//		}
//		return dataFile.isEOF();
//	}
	
	public int getOffsetFromLevel01()
	{
		VarDefBase varDefLevel01 = getParentAtLevel01();
		if(varDefLevel01 != null)
		{
			int nPos = m_nDefaultAbsolutePosition;
			int nLevel01Pos = varDefLevel01.m_nDefaultAbsolutePosition;
			return nPos - nLevel01Pos;
		}
		return 0;
	}	
	

	Var getRecordDependingVar()
	{
		if(m_OccursDef != null)
			return m_OccursDef.getRecordDependingVar();
		return null;
	}
	
	int getRecordDependingLength(VarBufferPos buffer)
	{
		Var varRecordDependingLength = getRecordDependingVar();
		if(varRecordDependingLength != null)
		{
			int n = varRecordDependingLength.getInt();
			return n;
		}
		return getLength();
	}
	
	public void setTotalSize(int n)
	{
		m_nTotalSize = n;
	}
	
}
