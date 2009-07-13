/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.varEx;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import jlib.misc.ArrayFix;
import jlib.misc.LineRead;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: VarDefEncodingConvertibleManager.java,v 1.13 2007/06/09 12:04:22 u930bm Exp $
 */
public class VarDefEncodingConvertibleManager
{
	public VarDefEncodingConvertibleManager()
	{
	}
	
	public void add(VarDefBase varDefBase)
	{
		int nPosition = varDefBase.DEBUGgetDefaultAbsolutePosition();
		int nLength = varDefBase.getLength();
		int nTraliningLength = varDefBase.getTrailingLengthToNotconvert();
		nLength -= nTraliningLength;
		
		add(nPosition, nLength);
	}
	
	public void add(int nPosition, int nLength)
	{
		add(nPosition, nLength, false);
	}
	public void add(int nPosition, int nLength, boolean bConvertOnlyIfBlank)
	{
		add(nPosition, nLength, bConvertOnlyIfBlank, false);
	}
	public void add(int nPosition, int nLength, boolean bConvertOnlyIfBlank, boolean bConvertPrint)
	{
		if(m_hash == null)
			m_hash = new Hashtable<Integer, EncodingConvertionRange>();
		
		EncodingConvertionRange ePrevious = m_hash.get(nPosition);	// Find the entry whose position preceeds us
		if(ePrevious != null 
				&& ePrevious.isConvertOnlyIfBlank() == bConvertOnlyIfBlank
				&& ePrevious.isConvertPrint() == bConvertPrint)
		{
			m_hash.remove(nPosition);
			int nLastPos = ePrevious.append(nLength);
			m_hash.put(nLastPos, ePrevious);
			return;
		}

		EncodingConvertionRange e = new EncodingConvertionRange();
		int nLastPos = e.set(nPosition, nLength);
		e.setConvertOnlyIfBlank(bConvertOnlyIfBlank);
		e.setConvertPrint(bConvertPrint);
		m_hash.put(nLastPos, e);
	}
	
	public void compress()
	{
		if(m_arr == null)
		{
			int nSize = m_hash.size();
			EncodingConvertionRange t[] = new EncodingConvertionRange[nSize];
			
			Collection<EncodingConvertionRange> col = m_hash.values();
			Iterator<EncodingConvertionRange> iter = col.iterator();
			int n = 0;
			while(iter.hasNext())
			{
				EncodingConvertionRange e = iter.next();
				t[n] = e;
				n++;
			}
			m_arr = new ArrayFix<EncodingConvertionRange>(t);
			m_hash = null;
		}
	}

	public void getConvertedBytesAsciiToEbcdic(int nStartPos, byte tbyDest[], int nMaxLengthDest)
	{
		if(m_arr != null)
		{
			for(int n=0; n<m_arr.size(); n++)
			{
				EncodingConvertionRange e = m_arr.get(n);
				e.convertAsciiToEbcdic(tbyDest, nStartPos, nMaxLengthDest);				
			}
		}
	}
	
	public void getConvertedBytesAsciiToEbcdic(LineRead lineRead)
	{
		if(m_arr != null)
		{
			for(int n=0; n<m_arr.size(); n++)
			{
				EncodingConvertionRange e = m_arr.get(n);
				e.convertAsciiToEbcdic(lineRead);				
			}
		}
	}
		
	public void convertEbcdicToAscii(VarBase varDest, int nMaxLengthToConvert)
	{
		int nLastPosToConvert = 0;
		if(m_arr != null)
		{
			for(int n=0; n<m_arr.size(); n++)
			{
				EncodingConvertionRange e = m_arr.get(n);
				if(n == 0)
					nLastPosToConvert = e.getPosition() + nMaxLengthToConvert-1;
				e.convertEbcdicToAscii(varDest, nLastPosToConvert);
			}
		}
	}
	
	public void getConvertedBytesEbcdicToAscii(int nStartPos, byte tbyDest[], int nMaxLengthDest)
	{
		if(m_arr != null)
		{
			for(int n=0; n<m_arr.size(); n++)
			{
				EncodingConvertionRange e = m_arr.get(n);
				e.convertEbcdicToAscii(tbyDest, nStartPos, nMaxLengthDest);				
			}
		}
	}
	
	public void getConvertedBytesEbcdicToAscii(LineRead lineRead)
	{
		if(m_arr != null)
		{
			for(int n=0; n<m_arr.size(); n++)
			{
				EncodingConvertionRange e = m_arr.get(n);
				e.convertEbcdicToAscii(lineRead);				
			}
		}
	}	
	
	public void fillDestAndConvertIntoAscii(LineRead lineRead, VarBase varDest)
	{
		int nLength = varDest.setFromLineRead(lineRead);
		convertEbcdicToAscii(varDest, nLength);
	}

	
	private Hashtable<Integer, EncodingConvertionRange> m_hash = null;
	private ArrayFix<EncodingConvertionRange> m_arr = null;
}
