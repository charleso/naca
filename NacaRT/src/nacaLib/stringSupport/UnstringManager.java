/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.stringSupport;

import java.util.ArrayList;

import nacaLib.varEx.*;

class UnstringDelimiter
{
	UnstringDelimiter(String cs, boolean bAll)
	{
		m_cs = cs;
		m_bAll = bAll;
	}
	
	String getRemaingStringAfterSeparator(String csSource)
	{
		int nStringLength = m_cs.length();
		if(m_bAll)
		{
			while(csSource.startsWith(m_cs))
			{
				csSource = csSource.substring(nStringLength);
			}
		}
		else
		{
			csSource = csSource.substring(nStringLength);
		}
		return csSource;
	}
	
	int removeDelimiterString(String csSource, int nPosStart)
	{
		if(m_bAll)
		{
			int nLength = m_cs.length();
			boolean bContinue = true;
			while(bContinue)
			{
				bContinue = false;
				if(csSource.length() >= nPosStart + nLength)
				{
					String csChunk = csSource.substring(nPosStart, nPosStart + nLength);
					if(csChunk.equals(m_cs))
					{
						nPosStart += nLength;
						bContinue = true;
					}
				}
			}
		}
		else
		{
			int nLength = m_cs.length();
			nPosStart += nLength; 
		}
		return nPosStart;
	}
	
	String m_cs = null;
	boolean m_bAll = false;
}

class UnstringManager
{
	String m_csCurrentSource;
	ArrayList<UnstringDelimiter> m_arrDelimiters = new ArrayList<UnstringDelimiter>(); 	// Array of UnstringDelimiter
	boolean m_bFailed = false; 
	int m_nCount = 0;
	Var m_varPointer = null;
	Var m_varTallying = null;
	int m_nTallying = 0;
	int m_nPointer1Based = 1;
	
	
	public UnstringManager(VarAndEdit varSource)
	{
		m_csCurrentSource = varSource.getString();
	}
	
	public void withPointer(Var varPointer)
	{
		m_varPointer = varPointer;
	}
	
	public void tallying(Var varTallying)
	{
		m_varTallying = varTallying;
	}
	
	private boolean checkIfRemainingUnfilledChunks()
	{
		int nPointer0Based = m_nPointer1Based - 1;	// Must be 0 based
		for(int nDelimiter=0; nDelimiter<m_arrDelimiters.size(); nDelimiter++)	// Try all delimiters
		{
			UnstringDelimiter delimiter = m_arrDelimiters.get(nDelimiter);
			int nPositionEndChunk = m_csCurrentSource.indexOf(delimiter.m_cs, nPointer0Based);
			if(nPositionEndChunk >= 0)
			{
				return true;
			}
		}
		return false;
	}
	
	void doInto(Var varDelimiterDest, Var varDelimiterIn, Var varCountDest)
	{
		if(!m_bFailed)
		{
			if(m_varTallying != null)
				m_nTallying = m_varTallying.getInt(); 
			
			UnstringDelimiter delimiterUsed = null;
			
			// find separator to use
			int nPositionEndSepartorUsed = -1;
			if(m_varPointer != null)
				m_nPointer1Based = m_varPointer.getInt();
			
			int nPointer0Based = m_nPointer1Based - 1;	// Must be 0 based
			if(nPointer0Based < 0 || nPointer0Based >= m_csCurrentSource.length()) // Check position
			{
				if (nPointer0Based < 0)
					m_bFailed = true;
				return ;
			}
			
			if(m_arrDelimiters.isEmpty())
			{
				if (m_csCurrentSource.length() == 0)
					return;
				varDelimiterDest.set(m_csCurrentSource);
				int i = Math.min(varDelimiterDest.getLength(),
						m_csCurrentSource.length() - 1);
				m_csCurrentSource = m_csCurrentSource.substring(i);
				return;
			}
			
			for(int nDelimiter=0; nDelimiter<m_arrDelimiters.size(); nDelimiter++)	// Try all delimiters
			{
				UnstringDelimiter delimiter = m_arrDelimiters.get(nDelimiter);
				int nPositionEndChunk = m_csCurrentSource.indexOf(delimiter.m_cs, nPointer0Based);
				if(nPositionEndChunk >= 0 && (nPositionEndChunk < nPositionEndSepartorUsed || nPositionEndSepartorUsed == -1))
				{
					nPositionEndSepartorUsed = nPositionEndChunk;
					delimiterUsed = delimiter;
				}
			}
			
			if(delimiterUsed != null)	// Found 1st delimitered string
			{
				String csChunk = m_csCurrentSource.substring(nPointer0Based, nPositionEndSepartorUsed);
				fillChunk(csChunk, varDelimiterDest, varCountDest, varDelimiterIn, delimiterUsed.m_cs);
				incTallyingCount();
				nPointer0Based = delimiterUsed.removeDelimiterString(m_csCurrentSource, nPositionEndSepartorUsed);	// Remove delimiter string, optionnally managing all occurences

				fillOutPointer(nPointer0Based);
				
				return;
				
//				m_csCurrentSource = m_csCurrentSource.substring(nPosSep);
//				m_csCurrentSource = delimiterUsed.getRemaingStringAfterSeparator(m_csCurrentSource);	// Keep only right part, after all separators
			}
			
			// Maybe sone source chars remains
			String csLastChunkOnRight = m_csCurrentSource.substring(nPointer0Based);
			fillChunk(csLastChunkOnRight, varDelimiterDest, varCountDest, varDelimiterIn, "");
			incTallyingCount();
			nPointer0Based += csLastChunkOnRight.length(); 
			fillOutPointer(nPointer0Based);
			return ;
		}
		
		// Not found substring
		if(m_varPointer != null)
		{
			int nPointer = m_csCurrentSource.length() +1;	// Points after the source string's last char (1 based)
			m_varPointer.set(nPointer);
		}
	
		if(varDelimiterDest != null)
			varDelimiterDest.set("");
		
		if(varDelimiterIn != null)
			varDelimiterIn.set("");
		
		if(varCountDest != null)
			varCountDest.set(0);
	}
	
	private void fillOutPointer(int nPointer0Based)
	{
		m_nPointer1Based = nPointer0Based + 1;	// Must be 1 based on output
		if(m_varPointer != null)
			m_varPointer.set(m_nPointer1Based);
	}
	
	
	private void fillChunk(String csChunk, Var varDelimiterDest, Var varCountDest, Var varDelimiterIn, String csDelimiterUsed)
	{		
		m_nCount = csChunk.length();
		if(varCountDest != null)
			varCountDest.set(m_nCount);

		if(varDelimiterIn != null)
			varDelimiterIn.set(csDelimiterUsed);
		
		if(varDelimiterDest != null)
			varDelimiterDest.set(csChunk);
	}
	
	private void incTallyingCount()
	{
		m_nTallying++;
		if(m_varTallying != null)					
			m_varTallying.set(m_nTallying);
	}
	
	boolean failed()
	{
		if(m_bFailed)
			return m_bFailed;
		return checkIfRemainingUnfilledChunks();	// If we have some chunks left that have not been conummed by into calls(), then we have an error 		
	}
}
