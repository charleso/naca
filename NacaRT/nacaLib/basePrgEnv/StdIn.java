/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.basePrgEnv;

import java.util.ArrayList;

public class StdIn
{
	private ArrayList<String> m_arrArgs = null;
	private int m_nExtractedArgIndex = 0;
	
	public StdIn()
	{
	}
	
	void fill(ArrayList<String> arr)
	{
		if(m_arrArgs == null)
			m_arrArgs = new ArrayList<String>();
		if(arr != null)
			m_arrArgs.addAll(arr);
	}
	
	String getArgOnce()
	{
		if(m_arrArgs != null)
		{
			if(m_nExtractedArgIndex < m_arrArgs.size())
			{
				String csArg = m_arrArgs.get(m_nExtractedArgIndex);
				m_nExtractedArgIndex++;
				return csArg;
			}
		}
		return null;
	}
}
