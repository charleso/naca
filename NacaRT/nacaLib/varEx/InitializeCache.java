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

import jlib.misc.ArrayDyn;
import jlib.misc.ArrayFix;
import jlib.misc.ArrayFixDyn;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class InitializeCache
{
	public InitializeCache()
	{
		m_arr = new ArrayDyn<InitializeCachedItem>();
	}
	
	void setFilledAndcompress(int nBaseAbsolutePosition)
	{
		m_bFilled = true;
		m_nBaseAbsolutePosition = nBaseAbsolutePosition;

		if(m_arr != null)
		{	
			// Swap the type inside m_arrRedefinition 
			if(m_arr.isDyn())
			{
				int nSize = m_arr.size();
				InitializeCachedItem arr[] = new InitializeCachedItem[nSize];
				m_arr.transferInto(arr);
				
				ArrayFix<InitializeCachedItem> arrInitializeCachedItemFix = new ArrayFix<InitializeCachedItem>(arr);
				m_arr = arrInitializeCachedItemFix;	// replace by a fix one (uning less memory)
			}
		}
	}
	
	void setNotManaged()
	{
		m_bManaged = false;
	}
	
	boolean isFilled()
	{
		return m_bFilled;
	}
	
	public boolean isManaged()
	{
		return m_bManaged;
	}
	
	void addItem(char cPad, int nPosition, int nNbChars)
	{
		InitializeCachedItem initializeCachedItem = new InitializeCachedItemRepeatingChar(cPad, nPosition, nNbChars);
		m_arr.add(initializeCachedItem);
	}
	
	void addItem(VarBufferPos buffer, int nOffset, int nNbChars)
	{
		char tChars[] = buffer.getAsCharArray(nOffset, nNbChars);
		int nPos = buffer.m_nAbsolutePosition+nOffset;
		doAddItem(tChars, nPos);
	}
	
	void addItemForBody(VarBufferPos buffer, int nBodyAbsolutePosition, int nOffset, int nNbChars)
	{
		char tChars[] = buffer.getAsCharArray(nOffset, nNbChars);
		int nPos = nBodyAbsolutePosition+nOffset;
		doAddItem(tChars, nPos);
	}
	
	private void doAddItem(char tChars[], int nPos)
	{		
		InitializeCachedItem initializeCachedItem = new InitializeCachedItemCharsArray(tChars, nPos);
		m_arr.add(initializeCachedItem);
	}
	
	void applyItems(VarBufferPos varBufferPos, int nCurrentAbsolutePosition)	//, int nOffset)
	{
		int nSize = m_arr.size();
		for(int n=0; n<nSize; n++)
		{
			InitializeCachedItem initializeCachedItem = m_arr.get(n);
			initializeCachedItem.apply(m_nBaseAbsolutePosition, varBufferPos, nCurrentAbsolutePosition);	//, nOffset);			
		}
	}
	
	private boolean m_bFilled = false;
	private boolean m_bManaged = true;
	private ArrayFixDyn<InitializeCachedItem> m_arr = null;
	private int m_nBaseAbsolutePosition = 0;
}
