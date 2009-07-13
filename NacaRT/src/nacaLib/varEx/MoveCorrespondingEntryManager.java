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

import java.util.ArrayList;

import jlib.misc.ArrayDyn;
import jlib.misc.ArrayFix;
import jlib.misc.ArrayFixDyn;

import nacaLib.basePrgEnv.BaseProgramManager;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class MoveCorrespondingEntryManager
{
	public MoveCorrespondingEntryManager()
	{	
		m_arrEntries = new ArrayDyn<MoveCorrespondingEntry>();
		m_bFilled = false;
	}
	
	void addEntry(MoveCorrespondingEntry entry)
	{
		m_arrEntries.add(entry);
	}
	
	boolean isFilled()
	{
		return m_bFilled;
	}
	
	void setFilledAndCompress()
	{
		m_bFilled = true;
		if(m_arrEntries != null)
		{		
			if(m_arrEntries.isDyn())
			{
				int nSize = m_arrEntries.size();
				MoveCorrespondingEntry arr[] = new MoveCorrespondingEntry[nSize];
				m_arrEntries.transferInto(arr);
				ArrayFix<MoveCorrespondingEntry> arrVarDefFix = new ArrayFix<MoveCorrespondingEntry>(arr);
				m_arrEntries = arrVarDefFix;	// replace by a fix one (uning less memory)
			}
		}
	}
	
	void doMoves(BaseProgramManager programManager, int nSourceOffset, int nDestOffset)
	{
		if(m_arrEntries != null)
		{			
			int nNbEntries = m_arrEntries.size();
			for(int n=0; n<nNbEntries; n++)
			{
				MoveCorrespondingEntry entry = m_arrEntries.get(n);
				entry.doMove(programManager, nSourceOffset, nDestOffset);
			}
		}
	}
	
	private boolean m_bFilled = false; 
	private ArrayFixDyn<MoveCorrespondingEntry> m_arrEntries = null;
}
