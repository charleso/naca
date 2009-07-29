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
 * Created on 8 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import jlib.misc.AsciiEbcdicConverter;
import nacaLib.base.CJMapObject;
import nacaLib.base.JmxGeneralStat;
import nacaLib.debug.BufferSpy;
import nacaLib.tempCache.CStr;
import nacaLib.tempCache.TempCacheLocator;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InternalCharBuffer extends CJMapObject
{
	public InternalCharBuffer()
	{
		m_acBuffer = null;
	}

	public InternalCharBuffer(int nBufferSize)
	{
		alloc(nBufferSize);
	}
	
	public void prepareAutoRemoval()
	{
		m_acBuffer = null;
	}
	
	public InternalCharBuffer(char [] acBuffer)
	{
		m_acBuffer = acBuffer;
	}
	
//	public InternalCharBuffer(InternalCharBuffer internalCharBufferOriginal)
//	{
//		copyFrom(internalCharBufferOriginal);
//	}
	
	public InternalCharBuffer(InternalCharBuffer internalCharBufferOriginal, int nAbsolutePosition, int nTotalSize)
	{
		copyFrom(internalCharBufferOriginal, nAbsolutePosition, nTotalSize);
	}

	public void allocBufferStorage(int nBufferSize)
	{
		alloc(nBufferSize);
		//JmxGeneralStat.incInternalCharBufferSize(nBufferSize);
		//m_hashSemantics = new Hashtable<Integer, String>(); 
	}

	private void alloc(int nNewLength)
	{
		m_acBuffer = new char [nNewLength];
		BufferSpy.alloc(m_acBuffer, nNewLength);
	}


//	public void copyFrom(InternalCharBuffer internalCharBufferOriginal)
//	{
//		if(!hasSameSize(internalCharBufferOriginal))
//		{
//			alloc(internalCharBufferOriginal.m_acBuffer.length);
//		}
//		for(int n=0; n<m_acBuffer.length; n++)
//		{
//			m_acBuffer[n] = internalCharBufferOriginal.m_acBuffer[n];
//		}
//	}
	
	public void copyFrom(InternalCharBufferCompressedBackup internalCharBufferCompressedBackup)
	{
		if(!isLargeEnough(internalCharBufferCompressedBackup.getBufferSize()))
		{
			alloc(internalCharBufferCompressedBackup.getBufferSize());
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, 0, m_acBuffer.length);
		for(int n=0; n<m_acBuffer.length; n++)
		{
			byte b = internalCharBufferCompressedBackup.m_abBuffer[n];
			if(b < 0)
				m_acBuffer[n] = (char)(b + 256);	
			else
				m_acBuffer[n] = (char)b; 
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
	}
	
	 
	public void copyFrom(InternalCharBuffer internalCharBufferOriginal, int nAbsolutePosition, int nTotalSize)
	{
		if(!isLargeEnough(internalCharBufferOriginal.getBufferSize()))
		{
			alloc(nTotalSize);
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, 0, nTotalSize);
		for(int n=0; n<nTotalSize; n++)
		{
			m_acBuffer[n] = internalCharBufferOriginal.m_acBuffer[nAbsolutePosition+n];
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
	}
	
//	public char[] getRawChars(int nPos, int nLength)
//	{
//		byte[] t = new byte[nLength];
//		
//		for(int n=0; n<nLength; n++)
//		{
//			char c = m_acBuffer[n+nPos];
//			byte b = (byte)c;
//			// Convert byte in ebcdic
//			t[n] = b;
//		}
//		return t;
//	}
//	
	
	
	void shareDataBufferFrom(InternalCharBuffer internalCharBufferOriginal)
	{
		m_acBuffer = internalCharBufferOriginal.m_acBuffer;
		//m_hashSemantics = internalCharBufferOriginal.m_hashSemantics;
	}
	
	boolean isLargeEnough(int nLength)
	{
		if(m_acBuffer == null)
			return false;
		
		if(m_acBuffer.length < nLength)
			return false;
		return true;			
	}	

//	boolean isBufferComputed()
//	{
//		if(m_acBuffer == null || m_acBuffer.length == 0)
//			return false;
//		return true;
//	}
	
	public int writeInt(int nValue, int nPos)
	{
		if(nPos+4 <= m_acBuffer.length)
		{
			setIntAt(nPos, nValue);
			nPos += 4;
			return nPos;
		}	
		return -1;
	}
	
	public int writeShort(short sValue, int nPos)
	{
		if(nPos+2 <= m_acBuffer.length)
		{
			setShortAt(nPos, sValue);
			nPos += 2;
			return nPos;
		}	
		return -1;
	}
	
	public int writeChar(char cValue, int nPos)
	{
		if(nPos < m_acBuffer.length)
		{
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nPos, 1);
			m_acBuffer[nPos] = cValue;
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			//setCharAt(nPos, cValue);
			nPos++;
			return nPos;
		}	
		return -1;
	}
		
	public int writeRepeatingCharAt(int nPosition, char c, int nNbChars)
	{
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nPosition, nNbChars);
		for(int n=0; n<nNbChars; n++, nPosition++)
			m_acBuffer[nPosition] = c;
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
		return nPosition;
	}
	
	public void copyBytes(int nPositionDest, int nNbCharsToCopy, int nPositionSource, InternalCharBuffer sourceCharBuffer)
	{
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nPositionDest, nNbCharsToCopy);
		for(int n=0; n<nNbCharsToCopy; n++, nPositionDest++, nPositionSource++)
		{
			m_acBuffer[nPositionDest] = sourceCharBuffer.m_acBuffer[nPositionSource];
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
	}
			
	public int writeString(String csValue, int nPos)
	{
		int nLength = csValue.length();
		//return writeString(csValue, nLength, nPos);
		int nNbChars = m_acBuffer.length - nPos;
		if(nNbChars < nLength)
		{
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nPos, nNbChars);
			csValue.getChars(0, nNbChars, m_acBuffer, nPos);
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			return -1;
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nPos, nLength);
		csValue.getChars(0, nLength, m_acBuffer, nPos);
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
		return nPos+nLength;
	}

//	public char getCharAt(int nPosition)
//	{
//		return m_acBuffer[nPosition];
//	}

	// Usage is discouraged as it should be inlined for perf reasons
	public void setCharAt(int nPosition, char c)
	{
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nPosition, 1);
		m_acBuffer[nPosition] = c;
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
	}
		
	public void setIntSignComp3At(VarBufferPos varBufferPos, long lValue, int nNbDigitInteger, int nTotalSize)
	{
		Pic9Comp3BufferSupport.setFromRightToLeft(varBufferPos, nNbDigitInteger, nTotalSize, 0, true, lValue);
	}

	
//	public int setStringAtWithStringLength(int nAbsoluteStartPosition, String cs, int nStringLength, int nLength)
//	{
//		char c = 0;
//		for(int n=0; n<nLength && nAbsoluteStartPosition<m_acBuffer.length; n++)
//		{			
//			if(n < nStringLength)
//				c = cs.charAt(n);
//			else
//				c = 0;
//			m_acBuffer[nAbsoluteStartPosition] = c;
//			nAbsoluteStartPosition++;
//		}
//		return nAbsoluteStartPosition;
//	}

	public int setStringAt(int nAbsoluteStartPosition, String cs, int nLength)
	{
		int nStringLength = cs.length();
		int nBufRemainingLength = m_acBuffer.length - nAbsoluteStartPosition; 
		if(nStringLength > nBufRemainingLength)
			nStringLength = nBufRemainingLength;

		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nAbsoluteStartPosition, nStringLength);
		cs.getChars(0, nStringLength, m_acBuffer, nAbsoluteStartPosition);
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();

		
//		int nStringLength = cs.length();
//		char c = 0;
//		for(int n=0; n<nLength && nAbsoluteStartPosition<m_acBuffer.length; n++)
//		{			
//			if(n < nStringLength)
//				c = cs.charAt(n);
//			else
//				c = 0;
//			m_acBuffer[nAbsoluteStartPosition] = c;
//			nAbsoluteStartPosition++;
//		}
		return nAbsoluteStartPosition + nStringLength;
	}
	
	public int writeString(String csValue, int nLength, int nPos)
	{
		int nNbChars = m_acBuffer.length - nPos;
		if(nNbChars < nLength)
		{
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nPos, nNbChars);
			csValue.getChars(0, nNbChars, m_acBuffer, nPos);
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			return -1;
		}
		else
		{
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nPos, nLength);
			csValue.getChars(0, nLength, m_acBuffer, nPos);
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			return nPos+nLength;
		}
// 		Old code			
//		int n = 0;
//		while(nPos < m_acBuffer.length && n < nLength)
//		{
//			char c = csValue.charAt(n); 
//			m_acBuffer[nPos++] = c;
//			n++;
//		}
//		if(n == nLength)	// Can copied all chars ?
//			return nPos;
//		return -1;
	}
	
	
	public int setStringAt(int nAbsoluteStartPosition, CStr cs, int nLength)
	{
		int nStringLength = cs.length();
		char c = 0;
		for(int n=0; n<nLength && nAbsoluteStartPosition<m_acBuffer.length; n++)
		{			
			if(n < nStringLength)
				c = cs.charAt(n);
			else
				c = 0;
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nAbsoluteStartPosition, 1);
			m_acBuffer[nAbsoluteStartPosition] = c;
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
			nAbsoluteStartPosition++;
		}
		return nAbsoluteStartPosition;
	}
	
	public String getString()
	{
		String cs = new String(m_acBuffer);
		return cs;
	}	
	
	public CStr getBufChunkAt(int nAbsolutePosition, int nSize)
	{
		if(nSize < 0)
			nSize = 0;
		
		int nMaxSize = m_acBuffer.length - nAbsolutePosition;
		if(nSize > nMaxSize)
			nSize = nMaxSize;
		CStr cs = TempCacheLocator.getTLSTempCache().getMappedCStr(); 
		cs.set(m_acBuffer, nAbsolutePosition, nSize);
		return cs;
	}
	
	public CStr getStringAt(int nAbsolutePosition, int nSize)
	{
		CStr cs = TempCacheLocator.getTLSTempCache().getMappedCStr();
		// Do not fill with ending \0
		int nEnd = nAbsolutePosition+nSize-1 ;

		if (nEnd < nAbsolutePosition || nEnd == -1)
		{
			cs.set(null, 0, 0);	// Erase previous buffer, as we are mapped
			return cs ;
		}
		else
		{
			if(m_acBuffer != null)
			{
				int nMaxSize = nEnd-nAbsolutePosition+1;
				if(nMaxSize > m_acBuffer.length - nAbsolutePosition)
					nMaxSize = m_acBuffer.length - nAbsolutePosition;
				if(nAbsolutePosition < m_acBuffer.length)
				{						
					cs.set(m_acBuffer, nAbsolutePosition, nMaxSize); 
					return cs ;
				}
			}						
		}
		cs.set(null, 0, 0);	// Erase previous buffer, as we are mapped
		return cs;
	}
	
	public void setShortAt(int nPosition, short s)
	{
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nPosition, 2);
		int n = s; 
		m_acBuffer[nPosition+1] = (char)(n & 255) ;
	    n = n >> 8 ;
		m_acBuffer[nPosition] = (char)(n & 255) ;
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
//		
//		//int nChecksum = 0;	// PJD Optimization
//		int nSignOffet = 0;
//		int n = s;
//		if(n < 0)
//		{
//			n = -n;
//			nSignOffet = 256;
//		}
//		
//		for(int nByte=0, nPos=1; nByte <2; nByte++, nPos--)
//		{
//			int nChar = n % 256;
//			if(nByte == 1)	// High order byte
//				nChar += nSignOffet;
//			
//			char cVal = (char)(nChar);
//			setCharAt(nPosition+nPos, cVal);
//			//nChecksum += cVal;
//			n = n / 256;
//		} 
//		//return nChecksum;
	}
	
	public short getShortAt(int nPosition)
	{
		int n = m_acBuffer[nPosition] & 0xFF;
	    n = n << 8 ;
		n += m_acBuffer[nPosition+1] & 0xFF;
		return (short)n;
//		
//		boolean bNegative = false;
//		int nVal = 0;
//		for(int nIndex=0; nIndex<2; nIndex++)
//		{
//			char cByteVal = getCharAt(nPosition+nIndex);
//			int nByteVal = cByteVal;
//			if(nIndex == 0 && nByteVal >= 256)
//			{
//				nByteVal -= 256;
//				bNegative = true;
//			}
//			nVal = (nVal * 256) + nByteVal;
//		}
//		if(bNegative)
//			nVal = -nVal;
//		short s = (short)nVal;
//		return s;
	}
	
	public void setIntAt(int nPosition, int n)
	{
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nPosition, 4);
		for (int nByte=3; nByte>=0; nByte--) 
		{
			m_acBuffer[nPosition+nByte] = (char)(n & 255) ;
		    n = n >> 8 ;
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
//		int nSignOffet = 0;
//		if(n < 0)
//		{
//			n = -n;
//			nSignOffet = 256;
//		}
//		
//		for(int nByte=0, nPos=3; nByte <4; nByte++, nPos--)
//		{
//			int nChar = n % 256;
//			if(nByte == 3)	// High order byte
//				nChar += nSignOffet;
//			
//			char cVal = (char)(nChar);
//			setCharAt(nPosition+nPos, cVal);
//			n = n / 256;
//		} 
	}	
	
	public int getIntAt(int nPosition)
	{	
		int n = m_acBuffer[nPosition++] & 0xFF;
		n <<= 8 ;
		n += m_acBuffer[nPosition++] & 0xFF;
		n <<= 8 ;
		n += m_acBuffer[nPosition++] & 0xFF;
		n <<= 8 ;
		n += m_acBuffer[nPosition++] & 0xFF;
		return n;
		
//		boolean bNegative = false;
//		int nVal = 0;
//		for(int nIndex=0; nIndex<4; nIndex++)
//		{
//			char cByteVal = getCharAt(nPosition+nIndex);
//			int nByteVal = cByteVal;
//			if(nIndex == 0 && nByteVal >= 256)
//			{
//				nByteVal -= 256;
//				bNegative = true;
//			}
//			nVal = (nVal * 256) + nByteVal;
//		}
//		if(bNegative)
//			nVal = -nVal;
//		return nVal;		
	}
		
	public void setLongAt(int nPosition, long l)
	{
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nPosition, 8);
		for (int nByte=7; nByte>=0; nByte--) 
		{
			m_acBuffer[nPosition+nByte] = (char)(l & 255) ;
		    l = l >> 8 ;
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
//		int nSignOffet = 0;
//		if(l < 0)
//		{
//			l = -l;
//			nSignOffet = 256;
//		}
//		
//		for(int nByte=0, nPos=7; nByte <8; nByte++, nPos--)
//		{
//			long lChar = l % 256;
//			if(nByte == 7)	// High order byte
//				lChar += nSignOffet;
//			
//			char cVal = (char)(lChar);
//			setCharAt(nPosition+nPos, cVal);
//			l = l / 256;
//		} 
	}	
	
	public long getLongAt(int nPosition)
	{
		long l = m_acBuffer[nPosition++] & 0xFF;
		l <<= 8 ;
		l += m_acBuffer[nPosition++] & 0xFF;
		l <<= 8 ;
		l += m_acBuffer[nPosition++] & 0xFF;
		l <<= 8 ;
		l += m_acBuffer[nPosition++] & 0xFF;
		l <<= 8 ;
		l += m_acBuffer[nPosition++] & 0xFF;
		l <<= 8 ;
		l += m_acBuffer[nPosition++] & 0xFF;
		l <<= 8 ;
		l += m_acBuffer[nPosition++] & 0xFF;
		l <<= 8 ;
		l += m_acBuffer[nPosition++] & 0xFF;
		return l;
		
//		boolean bNegative = false;
//		long lVal = 0;
//		for(int nIndex=0; nIndex<8; nIndex++)
//		{
//			char cByteVal = getCharAt(nPosition+nIndex);
//			int nByteVal = cByteVal;
//			if(nIndex == 0 && nByteVal >= 256)
//			{
//				nByteVal -= 256;
//				bNegative = true;
//			}
//			lVal = (lVal * 256) + nByteVal;
//		}
//		if(bNegative)
//			lVal = -lVal;
//		return lVal;		
	}

	
	public String toString()
	{
		return "Size="+getBufferSize()+" Value=\""+getString()+"\"";
	}
	
	public int getBufferSize()
	{
		if(m_acBuffer != null)
			return m_acBuffer.length;
		return 0;
	}
	

	void convertEbcdicToAscii(int nPosition, int nLength)
	{
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nPosition, nLength);
		for(int n=0; n<nLength; n++)
		{
			char cEbcdic = m_acBuffer[nPosition+n];
			char cAscii = AsciiEbcdicConverter.getAsciiChar(cEbcdic);
			m_acBuffer[nPosition+n] = cAscii;
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
	}
	
	void convertAsciiToEbcdic(int nPosition, int nLength)
	{
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nPosition, nLength);
		for(int n=0; n<nLength; n++)
		{
			char cAscii = m_acBuffer[nPosition+n];
			char cEbcdic = AsciiEbcdicConverter.getEbcdicChar(cAscii);
			m_acBuffer[nPosition+n] = cEbcdic;
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
	}
	
	void getConvertedBytesAsciiToEbcdic(int nPositionDest, int nLength, byte tbyDest[])
	{
		for(int n=0; n<nLength; n++)
		{
			char cAscii = m_acBuffer[nPositionDest+n];
			char cEbcdic = AsciiEbcdicConverter.getEbcdicChar(cAscii);
			tbyDest[n] = (byte)cEbcdic;
		}
	}

	
//	public boolean isShared()
//	{
//		return m_bShared;
//	}
	
//	
//	int getBufferSize()
//	{
//		return m_acBuffer.length;
//	}
	
//	public void setProgramManager(BaseProgramManager programManager)
//	{
//		m_programManager = programManager;
//	}
//
//	public BaseProgramManager getProgramManager()
//	{
//		// Check TLS 
//		BaseProgramManager tlspm = TempCacheLocator.getTLSTempCache().getProgramManager();
//		assertIfFalse(tlspm == m_programManager); 
//		return m_programManager;
//	}

//	public BaseProgramManager getProgramManager()
//	{
//		// Check TLS 
//		BaseProgramManager tlspm = TempCacheLocator.getTLSTempCache().getProgramManager();
//		return tlspm;
//	}
		
//	public void setSemanticContextValue(String csValue, int nAbsoluteStartPosition)
//	{
//		if(m_hashSemantics == null)
//			m_hashSemantics = new Hashtable<Integer, String>();
//		
//		Integer intKey = getSemanticHashIndex(nAbsoluteStartPosition);
//		boolean bSemanticExists = m_hashSemantics.containsKey(intKey);
//		if(bSemanticExists)
//			m_hashSemantics.remove(intKey);
//		if(csValue != null)
//			m_hashSemantics.put(intKey, csValue);
//	}
	
//	public String getSemanticContextValue(int nAbsoluteStartPosition)
//	{
//		if(m_hashSemantics != null)
//		{
//			Integer intKey = getSemanticHashIndex(nAbsoluteStartPosition);
//			String csValue = m_hashSemantics.get(intKey);
//			return csValue;
//		}
//		return null;
//	}

//	private Integer getSemanticHashIndex(int nAbsoluteStartPosition)
//	{
//		int nKey = nAbsoluteStartPosition;
//		Integer intKey = new Integer(nKey);
//		return intKey;
//	}
	
//	public void removeAllSemanticContext()
//	{
//		if(m_hashSemantics != null)
//		{
//			m_hashSemantics.clear();
//		}
//	}
	
//	public void inheritSemanticContext(InternalCharBuffer bufferSource, int nOffset)
//	{
//		ArrayList arrCoupleIdValue = getArrayCoupleIdValue();
//		if(arrCoupleIdValue != null)
//		{
//			//int nMinAbsoluteStartPositionSource = 0;
//			//int nLengthPositionSource = bufferSource.getBufferSize();
//			//int nMaxAbsoluteStartPositionSource = nMinAbsoluteStartPositionSource + nLengthPositionSource - 1; 
//			for(int n=0; n<arrCoupleIdValue.size(); n++)
//			{
//				CoupleIdValue couple = (CoupleIdValue)arrCoupleIdValue.get(n);
//				int nKeyDest = couple.m_nId + nOffset;
//				setSemanticContextValue(couple.m_csValue, nKeyDest);
//			}
//		}		
//	}
	
//	ArrayList getArrayCoupleIdValue()
//	{
//		ArrayList<CoupleIdValue> arr = null;
//		if(m_hashSemantics != null)
//		{
//			Enumeration eKeys = m_hashSemantics.keys();
//			while(eKeys.hasMoreElements())
//			{
//				Integer intKey = (Integer)eKeys.nextElement();
//				if( intKey != null)
//				{
//					int nKey = intKey.intValue();
//					String csValue = m_hashSemantics.get(intKey);
//					CoupleIdValue couple = new CoupleIdValue(nKey, csValue);
//					
//					if(arr == null)
//						arr = new ArrayList<CoupleIdValue>();
//					arr.add(couple);
//				}
//			}
//		}
//		return arr;	
//	}
	
	//private Hashtable<Integer, String> m_hashSemantics = null;	// Hash table of vars index by name

	public char [] m_acBuffer = null;		// Array of chars used as the buffer where data are stored
	//protected int m_nBufferSize = 0;
}
