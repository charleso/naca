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
package nacaLib.tempCache;

import java.math.BigDecimal;

import jlib.misc.AsciiEbcdicConverter;
import nacaLib.debug.BufferSpy;
import nacaLib.varEx.Pic9Comp3BufferSupport;


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class CStr
{
	//CStrManager m_manager = null;
	
	public CStr()
	{
	}

	public void resetMinimalSize(int n)
	{
		m_nLength = 0;
		m_nStartPos = 0;
		if(m_acBuffer != null)
			if(m_acBuffer.length > n)
				return;
		set(new char[n], 0, 0);
	}
		
	public void set(char acBuffer[], int nStartPos, int nLength)
	{
		m_acBuffer = acBuffer;
		m_nStartPos = nStartPos; 
		m_nLength = nLength;
	}
	
	public void set(String cs)
	{
		if(cs != null)
		{
			m_nLength = cs.length();
			m_nStartPos = 0;
			m_acBuffer = cs.toCharArray();
		}
		else
		{
			m_nLength = 0;
			m_nStartPos = 0;
			m_acBuffer = null;
		}
	}

	public void removeLeft(int nNbChar)
	{
		m_nStartPos += +nNbChar;
		m_nLength -= nNbChar;
	}
	
	public void insert(int nPosition, char c)
	{
		int nNbCharRight = m_nLength-nPosition;
		for(int n=nNbCharRight-2; n>=0; n--)
		{
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, m_nStartPos + n+1, 1);
			m_acBuffer[m_nStartPos + n+1] = m_acBuffer[m_nStartPos + n];
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, nPosition, 1);
		m_acBuffer[nPosition] = c;
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
	}

	
	public int length()
	{
		return m_nLength;
	}
	
	public char charAt(int n)
	{
		return m_acBuffer[n + m_nStartPos]; 
	}
	
	public void setCharAt(int nPosition, char cDigit)
	{
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, m_nStartPos + nPosition, 1);
		m_acBuffer[m_nStartPos + nPosition] = cDigit;
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
	}
	
	public void setLength(int n)
	{
		m_nLength = n;
	}
	
	public void append(char c)
	{
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, m_nStartPos + m_nLength, 1);
		m_acBuffer[m_nStartPos + m_nLength] = c;
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
		m_nLength++;		
	}
	
	public void append(CStr csInt)
	{
		int nLength = csInt.length();
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, m_nStartPos + m_nLength, nLength);
		for(int n=0; n<nLength; n++)
		{
			char c = csInt.charAt(n);
			m_acBuffer[m_nStartPos + m_nLength] = c;
			m_nLength++;
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
	}
	
	public void guaranteeMinialSize(int nMinimalSize)
	{
		if(m_acBuffer.length < nMinimalSize)
		{
			char acNewBuffer[] = new char [nMinimalSize];
			for(int n=0; n<m_acBuffer.length; n++)
			{
				acNewBuffer[n] = m_acBuffer[n]; 
			}
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, 0, nMinimalSize);
			m_acBuffer = acNewBuffer;
			if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
		}
	}
	
	public void selfSubstring(int nLeftPos)
	{
		m_nStartPos += nLeftPos;
		m_nLength -= nLeftPos;
	}
	
//	public void selfTrimLeftRight()
//	{
//		int n = 0;
//		for(; n<m_nLength; n++)
//		{
//			char c = m_acBuffer[n + m_nStartPos];
//			if(!Character.isWhitespace(c))
//				break;
//		}
//		m_nStartPos += n;
//		m_nLength -= n;
//		
//		while(m_nLength >= 0)
//		{
//			char c = m_acBuffer[m_nStartPos + m_nLength - 1];
//			if(!Character.isWhitespace(c))
//				break;
//			m_nLength--;
//		}
//	}
	
	public String toString()
	{
		return "\"" + new String(m_acBuffer, m_nStartPos, m_nLength) + "\"";
	}
	
	public String getAsString()
	{
		return new String(m_acBuffer, m_nStartPos, m_nLength);
	}
	
	
	public boolean isOnlyAlphabetic()
	{		
		int nMax = m_nStartPos+m_nLength;
		for(int n=m_nStartPos; n<nMax; n++)
		{
			char c = m_acBuffer[n];
			if(!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == ' '))
				return false;  
		}
		return true;
	}
	
	public boolean isOnlyNumeric()
	{		
		int nMax = m_nStartPos+m_nLength;
		for(int n=m_nStartPos; n<nMax; n++)
		{
			char c = m_acBuffer[n];
			if(!((c >= '0' && c <= '9') || c == '+' || c == '-' ))
				return false;  
		}
		return true;
	}
	
	public boolean isOnlyNumericPicX()
	{		
		int nMax = m_nStartPos+m_nLength;
		for(int n=m_nStartPos; n<nMax; n++)
		{
			char c = m_acBuffer[n];
			if(c < '0' || c > '9')
				return false;  
		}
		return true;
	}


	public boolean isOnlyNumericComp0(boolean bSigned, boolean bDec)
	{		
		int nNbDec = 0;
		int nMax = m_nStartPos+m_nLength;
		for(int n=m_nStartPos; n<nMax-1; n++)
		{
			char c = m_acBuffer[n];
			if(c == '.')
				nNbDec++;
			else if(c < '0' || c > '9')
				return false;  
		}
		
		if((bDec && (nNbDec == 0 || nNbDec == 1)) || !bDec)	// Maximum 1 . for decimals
		{
			char c = m_acBuffer[nMax-1];
			if(bSigned)
			{
				if((c >= (char)0xC0 && c <= (char)0xC9) || (c >= (char)0xD0 && c <= (char)0xD9))   
					return true;
				return false;
			}
			else
			{
				if(c < '0' || c > '9')
					return false;
				return true;				
			}
		}
		return false;
	}
	
	public boolean isOnlyNumericComp3(boolean bSigned)
	{		
		int nMax = m_nStartPos+m_nLength;
		for(int n=m_nStartPos; n<nMax-1; n++)
		{
			int nByte = m_acBuffer[n];
			int nHigh = (nByte & 0x00F0) >> 4;
			int nLow = nByte & 0x000F;	
			if(nHigh >= 10 || nLow >= 10)
				return false;
		}
		int nByte = m_acBuffer[nMax-1];
		int nHigh = (nByte & 0x00F0) >> 4;
		int nLow = nByte & 0x000F;
		if(nHigh >= 10)
			return false;
		if(bSigned && Pic9Comp3BufferSupport.isValidSign(nLow))
			return true;
		if(!bSigned && Pic9Comp3BufferSupport.isValidUnsign(nLow))
			return true;
		return false;
	}
	
	public boolean isOnlyNumericComp0SignLeading(boolean bDec)
	{
		int nNbDec = 0;
		
		char c = m_acBuffer[m_nStartPos];
		if(c != '-' && c != '+')
			return false;

		int nMax = m_nStartPos+m_nLength;
		for(int n=m_nStartPos+1; n<nMax; n++)
		{
			c = m_acBuffer[n];
			if(c == '.')
				nNbDec++;
			else if(c < '0' || c > '9')
				return false;
		}
		if(bDec && (nNbDec == 0 || nNbDec == 1))	// Maximum 1 . for decimals
			return true;
		return false;
	}
	
	public boolean isOnlyNumericComp0SignTrailingDec()
	{
		int nNbDec = 0;
		
		int nMax = m_nStartPos+m_nLength;
		for(int n=m_nStartPos; n<nMax-1; n++)
		{
			char c = m_acBuffer[n];
			if(c == '.')
				nNbDec++;
			else if(c < '0' || c > '9')
				return false;  
		}
		
		if(nNbDec == 0 || nNbDec == 1)	// Maximum 1 . for decimals
		{
			char c = m_acBuffer[nMax-1];
			if(c != '-' && c != '+')
				return false;
			return true;
		}
		return false;
	}
	
	public boolean isOnlyNumericComp0SignTrailingInt()
	{
		int nMax = m_nStartPos+m_nLength;
		for(int n=m_nStartPos; n<nMax-1; n++)
		{
			char c = m_acBuffer[n];
			if(c < '0' || c > '9')
				return false;  
		}
		char c = m_acBuffer[nMax-1];
		if(c != '-' && c != '+')
			return false;
		return true;
	}
	
	public int getAsInt()
	{
		if(m_nLength == 0)
			return 0;
				
		int nValue = 0;
		int nSource = m_nStartPos;
		int nMax = m_nLength + m_nStartPos;
		boolean bNegative = false;
		while(nSource < nMax)
		{
			char c = m_acBuffer[nSource++];
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
			else if(c == '+');
			else if(c == ' ');
			else if(c == '.')
				break;
			else if (nSource == m_nStartPos) // first char is not a digit
				return 0 ;
		}
		if(bNegative)
			return -nValue;
		return nValue;	
	}
	
	public int getAsUnsignedInt()
	{
		if(m_nLength == 0)
			return 0;
				
		int nValue = 0;
		int nSource = m_nStartPos;
		int nMax = m_nStartPos + m_nLength;
		while(nSource < nMax)
		{
			char c = m_acBuffer[nSource++];
			if(c >= '0' && c <= '9')
			{
				nValue = 10 * nValue + (c - '0');
				continue;
			}
			else if(c == '+');
			else if(c == ' ');
			else if(c == '.')
				break;
			else if (nSource == m_nStartPos) // first char is not a digit
				return 0 ;
		}
		return nValue;	
	}
	
	public long getAsLong()
	{
		if(m_nLength == 0)
			return 0;
				
		long lValue = 0;
		boolean bNegative = false;
		int nSource = m_nStartPos;
		int nMax = m_nLength+nSource;
		while(nSource < nMax)
		{
			char c = m_acBuffer[nSource++];
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
			else if(c == '+');
			else if(c == ' ');
			else if(c == '.')
				break;
			else if (nSource == m_nStartPos) // first char is not a digit
				return 0 ;
		}
		if(bNegative)
			return -lValue;
		return lValue;	
	}
	
	public BigDecimal makeBigDecimal()
	{
		return new BigDecimal(m_acBuffer , m_nStartPos, m_nLength);
	}
	
	public CStr duplicate()
	{
		CStr csCopy = new CStr();
		int nBufferLength = m_acBuffer.length;
		csCopy.set(new char[nBufferLength], m_nStartPos, m_nLength);
		for(int n=0; n<nBufferLength; n++)
		{
			csCopy.m_acBuffer[n] = m_acBuffer[n]; 
		}
		return csCopy;		
	}
	
	public void setEbcdic()
	{
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.prewrite(m_acBuffer, m_nStartPos, m_nLength);
		for(int n=0; n<m_nLength; n++)
		{
			char cAscii = m_acBuffer[n + m_nStartPos];
			char cEbcdic1 = AsciiEbcdicConverter.getEbcdicChar(cAscii);
			m_acBuffer[m_nStartPos + n] = cEbcdic1;
		}
		if(BufferSpy.BUFFER_WRITE_DEBUG) BufferSpy.endwrite();
	}

	
	protected char [] m_acBuffer;
	protected int m_nStartPos = 0;
	protected int m_nLength = 0;
}
