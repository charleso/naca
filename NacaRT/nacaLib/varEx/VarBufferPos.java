/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 7 juil. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author PJD
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package nacaLib.varEx;

import jlib.misc.IntegerRef;
import jlib.misc.LineRead;
import nacaLib.tempCache.CStr;
import nacaLib.tempCache.TempCacheLocator;

public class VarBufferPos extends VarBuffer
{	
	protected int m_nAbsolutePosition = 0;	
	private CStr m_cstr = null;
	
	public VarBufferPos(VarBuffer varBuffer, int nPosition)
	{	
		super(varBuffer);
		m_nAbsolutePosition = nPosition;
	}
	
	public VarBufferPos(int nSize)
	{
		super(nSize);
		m_nAbsolutePosition = 0;
	}
	
	public VarBufferPos(char [] acBuffer)
	{
		super(acBuffer);
		m_nAbsolutePosition = 0;
	}
	
	public void setAsVar(VarBase varBase)
	{	
		shareDataBufferFrom(varBase.m_bufferPos);
		m_nAbsolutePosition = varBase.m_bufferPos.m_nAbsolutePosition;
	}
	
	void reuse(VarBuffer bufferSource, int nPosition)
	{
		shareDataBufferFrom(bufferSource);
		m_nAbsolutePosition = nPosition;
	}
	
	
	public String toString()
	{
		String cs = "Buffer @" + m_nAbsolutePosition + " (Id=" + m_acBuffer.hashCode() + ")";   
		return cs;
	}
//	
//	public BufChunk getBufChunkAt(int nSize)
//	{
//		if(nSize < 0)
//			nSize = 0;
//		
//		int nMaxSize = m_acBuffer.length - m_nAbsolutePosition;
//		if(nSize > nMaxSize)
//			nSize = nMaxSize;
//		BufChunk bufChunk = new BufChunk(this, m_nAbsolutePosition, nSize);
//		return bufChunk;
//	}
	
	public CStr getBufChunkAt(int nSize)
	{
		if(nSize < 0)
			nSize = 0;
		
		int nMaxSize = m_acBuffer.length - m_nAbsolutePosition;
		if(nSize > nMaxSize)
			nSize = nMaxSize;
		CStr cs = TempCacheLocator.getTLSTempCache().getMappedCStr();
		cs.set(m_acBuffer, m_nAbsolutePosition, nSize);
		return cs;
	}
	
	public CStr getOwnCStr(int nSize)
	{
		if(m_cstr == null)
			m_cstr = new CStr();
		if(nSize < 0)
			nSize = 0;
		
		int nMaxSize = m_acBuffer.length - m_nAbsolutePosition;
		if(nSize > nMaxSize)
			nSize = nMaxSize;
		
		m_cstr.set(m_acBuffer, m_nAbsolutePosition, nSize);
		return m_cstr;
	}
		
//	public BufChunk getBodyBufChunk(VarDefBuffer varDef)
//	{
//		int nBodyLength = varDef.getBodyLength();
//			
//		int nBodyAbsolutePosition = m_nAbsolutePosition + varDef.getHeaderLength();
//				
//		int nMaxSize = m_acBuffer.length - nBodyAbsolutePosition;
//		if(nBodyLength > nMaxSize)
//			nBodyLength = nMaxSize;
//		BufChunk bufChunk = new BufChunk(this, nBodyAbsolutePosition, nBodyLength);
//		return bufChunk;
//	}
	
//	public String getBodyBufChunkAsString(VarDefBuffer varDef)
//	{
//		int nBodyLength = varDef.getBodyLength();
//			
//		int nBodyAbsolutePosition = m_nAbsolutePosition + varDef.getHeaderLength();
//				
//		int nMaxSize = m_acBuffer.length - nBodyAbsolutePosition;
//		if(nBodyLength > nMaxSize)
//			nBodyLength = nMaxSize;
//		String cs = new String(m_acBuffer, nBodyAbsolutePosition, nBodyLength);
//		return cs;
//	}
	
	public CStr getBodyCStr(VarDefBuffer varDef)
	{
		int nBodyLength = varDef.getBodyLength();
			
		int nBodyAbsolutePosition = m_nAbsolutePosition + varDef.getHeaderLength();
				
		int nMaxSize = m_acBuffer.length - nBodyAbsolutePosition;
		if(nBodyLength > nMaxSize)
			nBodyLength = nMaxSize;
		//CStr cs = new CStr();
		CStr cs = TempCacheLocator.getTLSTempCache().getMappedCStr();
		cs.set(m_acBuffer, nBodyAbsolutePosition, nBodyLength);
		return cs;
	}
	
//	public BufChunk getBodyBufChunk(ComparisonMode mode, VarDefBuffer varDef)
//	{
//		int nBodyLength = varDef.getBodyLength();
//			
//		int nBodyAbsolutePosition = m_nAbsolutePosition + varDef.getHeaderLength();
//				
//		int nMaxSize = m_acBuffer.length - nBodyAbsolutePosition;
//		if(nBodyLength > nMaxSize)
//			nBodyLength = nMaxSize;
//		BufChunk bufChunk = new BufChunk(this, nBodyAbsolutePosition, nBodyLength);
//		if(mode == ComparisonMode.Ebcdic)
//		{
//			if(varDef.isConvertibleInEbcdic())
//			{
//				bufChunk.setEbcdic();
//			}
//		}
//		return bufChunk;
//	}
	
//	public BufChunk getBodyBufChunkAtAbsolutePosition(ComparisonMode mode, IntegerRef iAbsolutePosition, VarDefBuffer varDef)
//	{
//		int nBodyLength = varDef.getBodyLength();
//		
//		int nAbsolutePosition = iAbsolutePosition.get();
//			
//		int nBodyAbsolutePosition = nAbsolutePosition + varDef.getHeaderLength();
//				
//		int nMaxSize = m_acBuffer.length - nBodyAbsolutePosition;
//		if(nBodyLength > nMaxSize)
//			nBodyLength = nMaxSize;
//		BufChunk bufChunk = new BufChunk(this, nBodyAbsolutePosition, nBodyLength, true, true);
//		if(mode == ComparisonMode.Ebcdic)
//		{
//			if(varDef.isConvertibleInEbcdic())
//			{
//				bufChunk.setEbcdic();
//			}
//		}
//		iAbsolutePosition.inc(nBodyLength);
//		return bufChunk;
//	}
	
	public CStr getBodyCStrAtAbsolutePosition(ComparisonMode mode, IntegerRef iAbsolutePosition, VarDefBuffer varDef)
	{
		int nBodyLength = varDef.getBodyLength();
		
		int nAbsolutePosition = iAbsolutePosition.get();
			
		int nBodyAbsolutePosition = nAbsolutePosition + varDef.getHeaderLength();
				
		int nMaxSize = m_acBuffer.length - nBodyAbsolutePosition;
		if(nBodyLength > nMaxSize)
			nBodyLength = nMaxSize;
		CStr cs = TempCacheLocator.getTLSTempCache().getMappedCStr();
		cs.set(m_acBuffer, nBodyAbsolutePosition, nBodyLength);
		CStr csDuplicate = cs.duplicate();
		
		
		if(mode == ComparisonMode.Ebcdic)
		{
			if(varDef.isConvertibleInEbcdic())
			{
				csDuplicate.setEbcdic();
			}
		}
		iAbsolutePosition.inc(nBodyLength);
		return csDuplicate;
	}

	char[] getByteArray(VarBase var, int nLength)
	{
		int nPosSource = m_nAbsolutePosition;
		char[] tc = new char[nLength];
		for(int nOffset=0; nOffset<nLength; nOffset++)
		{
			tc[nOffset] = m_acBuffer[nPosSource++];			
		}
		return tc;
	}
	
//	void setByteArray(byte[] tBytes)
//	{
//		if(tBytes != null)
//		{
//			int nLength = tBytes.length;
//			for(int nOffset=0; nOffset<nLength; nOffset++)
//			{
//				m_acBuffer[m_nAbsolutePosition + nOffset] = (char)tBytes[nOffset];			
//			}
//		}
//	}

	void importFromByteArray(byte[] tBySource, int nLengthDest, int nLengthSource)
	{
		int nPosDest = m_nAbsolutePosition;
		int nLength = Math.min(nLengthSource, nLengthDest);
		if(tBySource != null)
		{
			for(int nOffset=0; nOffset<nLength; nOffset++)
			{
				int n = tBySource[nOffset];
				if(n < 0)
					n += 256;
				m_acBuffer[nPosDest++] = (char)n;			
			}
		}
	}
		
	void setByteArray(byte[] tBytesSource, int nOffsetSource, int nLength)
	{	
		int nPosDest = m_nAbsolutePosition;
		if(tBytesSource != null)
		{
			for(int n=0; n<nLength; n++)
			{
				int nSource = tBytesSource[nOffsetSource + n];
				if(nSource < 0)
					nSource += 256;
				m_acBuffer[nPosDest++] = (char)nSource;
			}
		}
	}
	
	void setByteArray(byte[] tSourceBytes, int nSourceOffset, int nSourceLength, VarBufferPos buf2, int nDest2Length)
	{	
		int nPosSource = nSourceOffset;
		int nPosDest1 = m_nAbsolutePosition;
		int nPosDest2 = buf2.m_nAbsolutePosition;
		if(tSourceBytes != null)
		{
			for(int n=0; n<nSourceLength; n++)
			{
				int nByte = tSourceBytes[nPosSource++];
				if(nByte < 0)
					nByte += 256;
				
				char c = (char)nByte;
				m_acBuffer[nPosDest1++] = c;
				if(n < nDest2Length)
					buf2.m_acBuffer[nPosDest2++] = c;
			}
		}
	}
	
//	void fillNull(int nOffsetDest, int nLength)
//	{
//		int m = m_nAbsolutePosition + nOffsetDest;
//		for(int n=0; n<nLength; n++, m++)
//		{			
//			m_acBuffer[m] = 0;
//		}
//	}
	
//	void fillWithSameByteAtOffset(byte by, int nOffset, int nNbOccurences)
//	{
//		int nSource = by;
//		if(nSource < 0)
//			nSource += 256;
//		char c  = (char)nSource;
//
//		for(int n=0; n<nNbOccurences; n++)
//			m_acBuffer[m_nAbsolutePosition + nOffset + n] = c;
//	}
	
	void exportIntoByteArray(byte tByDest[], int nLengthDest, int nLengthSource)
	{
		int nLength = Math.min(nLengthSource, nLengthDest);
		int n = m_nAbsolutePosition;
		for(int nPos=0; nPos<nLength; nPos++)
		{
			tByDest[nPos] = (byte) m_acBuffer[n++];
		}
	}
	
	char [] getAsCharArray(int nOffset, int nLength)
	{
		char tcDest[] = new char [nLength];
		
		int n = nOffset + m_nAbsolutePosition;
		for(int nPos=0; nPos<nLength; nPos++)
		{
			//tcDest[nPos] = m_acBuffer[n + nPos];
			tcDest[nPos] = m_acBuffer[n++];
		}
		return tcDest;
	}
	
	void fillBlankComp3AtOffset(int nTotalSize, int nOffset)
	{
		int nPos = m_nAbsolutePosition + nOffset;
		for(int n=0; n<=nTotalSize; n++)
		{
			m_acBuffer[nPos++] = 0; 
		}
	}
		
	void fillZeroesComp0AtOffset(int nTotalSize, int nOffset)
	{
		int nPos = m_nAbsolutePosition + nOffset;
		for(int n=0; n<nTotalSize; n++)
		{
			m_acBuffer[nPos++] = '0'; 
		}
	}
	
	void fillBlankComp0AtOffset(int nTotalSize, int nOffset)
	{
		int nPos = m_nAbsolutePosition + nOffset;
		for(int n=0; n<nTotalSize; n++)
		{
			m_acBuffer[nPos++] = ' '; 
		}
	}
	
//	void setBufferByteAtOffset(int nCharPos, byte byHighValue, byte byLowValue)
//	{
//		char cChar = (char)((byHighValue * 16) + byLowValue);
//		m_acBuffer[nCharPos] = cChar;
//	}
	
//	void setBufferByteAtOffset(int nCharPos, int nByteIndex, byte byValue)
//	{
//		char c = m_acBuffer[nCharPos];
//		if(nByteIndex == 1)
//		{
//			m_acBuffer[m_nAbsolutePosition+nCharPos] &= 0xFFF0;
//			m_acBuffer[m_nAbsolutePosition+nCharPos] |= byValue;
//		}
//		else
//		{
//			m_acBuffer[m_nAbsolutePosition+nCharPos] &= 0x000F;
//			m_acBuffer[m_nAbsolutePosition+nCharPos] |= (byValue << 4);			
//		}			
//	}
	
	void addNibbleAtOffset(int nOffset, int nNibblePos, byte byValue)
	{		
		int nCharPos = nNibblePos / 2;
		int nNibbleIndex = nNibblePos % 2;
		int nIndex = m_nAbsolutePosition+nOffset+nCharPos;
		if(nNibbleIndex == 1)	// char is 16 bits in 4 nibbles: 0000 0000 nible0 nibble1
		{
			m_acBuffer[nIndex] &= 0xFFF0;
			m_acBuffer[nIndex] |= byValue;
		}
		else
		{
			m_acBuffer[nIndex] &= 0x000F;
			m_acBuffer[nIndex] |= (byValue << 4);			
		}
	}
	
//	char getCharAtOffset(int nCharPos)
//	{
//		char c = m_acBuffer[m_nAbsolutePosition+nCharPos];
//		return c;
//	}
	
//	void setCharAtOffset(int nCharPos, char c)
//	{
//		m_acBuffer[m_nAbsolutePosition+nCharPos] = c;
//	}
	
	int getAsInt(int nSize)
	{
		if(nSize < 0)
			nSize = 0;
		
		int nMaxSize = m_acBuffer.length - m_nAbsolutePosition;
		if(nSize > nMaxSize)
			nSize = nMaxSize;
		
		return getAsInt(m_nAbsolutePosition, nSize);
	}
	
	int getAsInt(int nAbsolutePosition, int nTotalSize)
	{
		if(nTotalSize == 0)
			return 0;
		int nValue = 0;
		int n = nAbsolutePosition;
		int nMax = nAbsolutePosition + nTotalSize;
		boolean bNegative = false;
		while(n < nMax)
		{
			char c = m_acBuffer[n++];
			if(c >= '0' && c <= '9')
			{
				nValue = 10 * nValue + (c - '0');
				continue;
			}
			else if(c == '-')
			{
				bNegative = true;
				continue;
			}
			else if(c == '.')
				break;
		}
		if(bNegative)
			return -nValue;
		return nValue;	
	}
	
	int getAsUnsignedInt(int nSize)
	{
		if(nSize < 0)
			nSize = 0;
		
		int nMaxSize = m_acBuffer.length - m_nAbsolutePosition;
		if(nSize > nMaxSize)
			nSize = nMaxSize;
		
		return getAsUnsignedInt(m_nAbsolutePosition, nSize);
	}
	
	int getAsUnsignedInt(int nAbsolutePosition, int nTotalSize)
	{
		if(nTotalSize == 0)
			return 0;
		int nValue = 0;
		int n = nAbsolutePosition;
		int nMax = nAbsolutePosition + nTotalSize;
		while(n < nMax)
		{
			char c = m_acBuffer[n++];
			if(c >= '0' && c <= '9')
			{
				nValue = 10 * nValue + (c - '0');
				continue;
			}
			else if(c == '.')
				break;
		}
		return nValue;	
	}
	
	long getAsLong(int nSize)
	{
		if(nSize < 0)
			nSize = 0;
		
		int nMaxSize = m_acBuffer.length - m_nAbsolutePosition;
		if(nSize > nMaxSize)
			nSize = nMaxSize;
		
		return getAsLong(m_nAbsolutePosition, nSize);
	}
	
	public long getAsLong(int nAbsolutePosition, int nTotalSize)
	{
		if(nTotalSize == 0)
			return 0;
		
		long lValue = 0;
		int n = nAbsolutePosition;
		int nMax = nAbsolutePosition+nTotalSize;
		boolean bNegative = false;
		while(n < nMax)
		{
			char c = m_acBuffer[n++];
			if(c >= '0' && c <= '9')
			{
				lValue = 10 * lValue + (c - '0');
				continue;
			}
			else if(c == '-')
			{
				bNegative = true;
				continue;
			}			
			else if(c == '.')
				break;
		}
		if(bNegative)
			return -lValue;
		return lValue;	
	}
		
	long getAsUnsignedLong(int nSize)
	{
		if(nSize < 0)
			nSize = 0;
		
		int nMaxSize = m_acBuffer.length - m_nAbsolutePosition;
		if(nSize > nMaxSize)
			nSize = nMaxSize;
		
		return getAsUnsignedLong(m_nAbsolutePosition, nSize);
	}
	
	public long getAsUnsignedLong(int nAbsolutePosition, int nTotalSize)
	{
		if(nTotalSize == 0)
			return 0;
		
		long lValue = 0;
		int n = nAbsolutePosition;
		int nMax = nAbsolutePosition+nTotalSize;
		while(n < nMax)
		{
			char c = m_acBuffer[n++];
			if(c >= '0' && c <= '9')
			{
				lValue = 10 * lValue + (c - '0');
				continue;
			}
			else if(c == '-')
			{
				continue;
			}			
			else if(c == '.')
				break;
		}
		return lValue;	
	}
	
	public void copy(int nNbCharToCopy, VarBufferPos varBufPosSource)
	{
		copyBytes(m_nAbsolutePosition, nNbCharToCopy, varBufPosSource.m_nAbsolutePosition, varBufPosSource);
	}
	
	void restore(int nOldAbsolutePosition, char acOldBuffer[])	//, boolean bShared)
	{
		m_nAbsolutePosition = nOldAbsolutePosition;
		m_acBuffer = acOldBuffer;
	}
	
	
	// Experimental performance code
	
//	private int m_nLastChecksum = 0;
//	public int m_nLastValue = 0;
//
//	public void resetLastChecksum()
//	{
//		m_nLastChecksum = 0;
//	}
//	
//	public void setChecksum(int n)
//	{
//		m_nLastChecksum = n;
//	}
//	
//	public boolean isLastChecksumValid(int nTotalSize)
//	{
//		int nChecksum = 0;
//		for(int n=0; n<nTotalSize; n++)
//		{
//			nChecksum += m_acBuffer[n+m_nAbsolutePosition];
//		}
//		if(nChecksum == m_nLastChecksum)
//			return true;
//		m_nLastChecksum = nChecksum;
//		return false;
//	}
}



class CEditSemanticContextMapAssoc
{
	CEditSemanticContextMapAssoc(Edit edit, String csSemantiContext)
	{
		m_edit = edit;
		m_csSemantiContext = csSemantiContext;
	}
	Edit m_edit = null; 
	String m_csSemantiContext = null;
}

