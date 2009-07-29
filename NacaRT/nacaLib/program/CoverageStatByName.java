/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.program;

import java.util.ArrayList;

public class CoverageStatByName
{
	private ArrayList<Integer> m_counters = new ArrayList<Integer>();
	
	CoverageStatByName()
	{
		for(int n=0; n<StatCoverageType.getNbEntries(); n++)
			m_counters.add(0);
	}
	
	void add(StatCoverageType type)
	{
		int nId = type.getId();
		Integer nCount = m_counters.get(nId);
		m_counters.set(nId, nCount+1);
	}
	
	public String toString()
	{
		String cs = "";
		for(int n=0; n<StatCoverageType.getNbEntries(); n++)
		{
			int nCount = m_counters.get(n);
			if(nCount != 0)
			{
				String csCounterName = StatCoverageType.getNameAtIndex(n);
				cs += csCounterName + ": " + nCount + "\n";
			}
		}
		
		return cs + "\n";
	}
}
