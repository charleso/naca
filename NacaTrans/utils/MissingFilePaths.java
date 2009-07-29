/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaTrans - Naca Transcoder v1.2.0.beta.1
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package utils;

import java.util.ArrayList;

public class MissingFilePaths
{
	private String m_csName = null;
	private ArrayList<String> m_arrPaths = null;
	
	public MissingFilePaths(String csName)
	{
		m_csName = csName;
	}
	
	public void addPath(String csPath)
	{
		if(m_arrPaths == null)
			m_arrPaths = new ArrayList<String>();
		m_arrPaths.add(csPath);
	}
	
	public String toString()
	{
		if(m_arrPaths == null)
			return "";
		String cs = "Missing file: " + m_csName + " Searched as: ";
		for(int n=0; n<m_arrPaths.size(); n++)
		{
			if(n > 0)
				cs += "; ";
			cs += m_arrPaths.get(n);
		}
		return cs;
	}
	
}
