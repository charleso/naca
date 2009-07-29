/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.program;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class CoverageStatByPrg
{
	private Hashtable<String, CoverageStatByName> m_hashStatByName = new Hashtable<String, CoverageStatByName>();	// Key: Paragraph/section/sentence name
	
	CoverageStatByPrg()
	{
	}
	
	void add(StatCoverageType type, String csName)
	{
		CoverageStatByName coverageStatByName = m_hashStatByName.get(csName);
		if(coverageStatByName == null)
		{
			coverageStatByName = new CoverageStatByName();
			m_hashStatByName.put(csName, coverageStatByName);
		}
		coverageStatByName.add(type);
	}
	
	public String toString()
	{
		String cs = "";
		
		Enumeration<String> eKeys = m_hashStatByName.keys();
		while(eKeys.hasMoreElements())
		{
			String csName = eKeys.nextElement();
			cs += "Section/Paragraph/Sentence: "+csName+"\n";
			CoverageStatByName coverageStatByName = m_hashStatByName.get(csName);
			cs += coverageStatByName.toString();			
		}
		
		return cs;	
	}
}
