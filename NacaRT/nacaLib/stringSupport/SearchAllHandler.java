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
package nacaLib.stringSupport;

import nacaLib.program.CallbackSearch;
import nacaLib.program.CompareResult;
import nacaLib.program.Paragraph;
import nacaLib.varEx.Var;

public class SearchAllHandler
{
	private Var m_var = null;
	private Var m_varTableSize = null;
	private Var m_varIndex = null;
	private Var m_varKey = null;
	private boolean m_bAscending = false;
	private int m_nIndex = 0;
	
	public SearchAllHandler(Var var, Var varTableSize)
	{
		m_var = var;
		m_varTableSize = varTableSize;
	}
	
	public SearchAllHandler indexedBy(Var varIndex)
	{
		m_varIndex = varIndex;
		return this;
	}
	
	public SearchAllHandler keyAsc(Var varKey)
	{
		m_varKey = varKey;
		m_bAscending = true;
		return this;
	}
	
	public SearchAllHandler keyDesc(Var varKey)
	{
		m_varKey = varKey;
		m_bAscending = false;
		return this;
	}
	
	public boolean when(CallbackSearch callbackSearch)
	{
		boolean bFound = false;
		CompareResult compareResult = null;
		
		int nMax = m_varTableSize.getInt();
		if(nMax == 0)
			return false;
		
		if(nMax == 1)
		{
			m_varIndex.set(1);
			compareResult = callbackSearch.run();
			if(compareResult == CompareResult.Equals)
				return true;
			return false;
		}
		if(nMax == 2)
		{
			m_varIndex.set(1);
			compareResult = callbackSearch.run();
			if(compareResult == CompareResult.Equals)
				return true;
			m_varIndex.set(2);
			compareResult = callbackSearch.run();
			if(compareResult == CompareResult.Equals)
				return true;
			return false;
		}
		if(nMax == 3)
		{
			m_varIndex.set(2);
			compareResult = callbackSearch.run();
			if(compareResult == CompareResult.Equals)
				return true;
			m_varIndex.set(1);
			compareResult = callbackSearch.run();
			if(compareResult == CompareResult.Equals)
				return true;
			m_varIndex.set(3);
			compareResult = callbackSearch.run();
			if(compareResult == CompareResult.Equals)
				return true;
			return false;
		}
		
		int nMin = 0;
		
		boolean bLast = fillIndex(nMin, nMax-1);
		boolean bTerminated = false;
		while(!bTerminated)
		{
			if(bLast)
				bTerminated = true;
			// Do binary search ...
			compareResult = callbackSearch.run();
			if(compareResult == CompareResult.Equals)
				return true;
			
			if(nMin == nMax)	// Not found
				return false;
			
			if(compareResult == CompareResult.Less)
			{
				if(m_bAscending)
				{
					nMin = m_nIndex;
					bLast = fillIndex(nMin, nMax);
				}
				else
				{
					nMax = m_nIndex;
					bLast = fillIndex(nMin, nMax);
				}
			}
			if(compareResult == CompareResult.Greater)
			{
				if(m_bAscending)
				{
					nMax = m_nIndex;
					bLast = fillIndex(nMin, nMax);
				}
				else
				{
					nMin = m_nIndex;
					bLast = fillIndex(nMin, nMax);
				}
			}
		}
		return false;
	}
	
	private boolean fillIndex(int nMin, int nMax)
	{
		int nRange = nMax - nMin;
		if(nRange > 1)
		{
			int nMid = nRange / 2;
			nMid += nMin;
			m_nIndex = nMid;
			m_varIndex.set(nMid+1);
			return false;
		}
		else
		{
			if(nMax == 1 && nMin == 0)
			{
				m_nIndex = nMin;
				m_varIndex.set(nMin+1);
				nMax = nMin;
				return true;
			}
			else
			{
				m_nIndex = nMax;
				m_varIndex.set(nMax+1);
				nMax = nMin;
				return true;
			}
		}
	}
}
