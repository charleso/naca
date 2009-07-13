/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/**
 * 
 */
package utils;

import java.util.ArrayList;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: UnboundRefId.java,v 1.1 2007/06/28 06:19:46 u930bm Exp $
 */
public class UnboundRefId
{
	UnboundRefId(int nLine, String csFile)
	{
		m_arrLines = new ArrayList<Integer>();
		m_arrLines.add(nLine);
		m_csFile = csFile;
	}
	
	void addLineOnce(int nLine)
	{
		if(m_arrLines != null)
		{
			for(int n=0; n<m_arrLines.size(); n++)
			{
				int nVal = m_arrLines.get(n);
				if(nVal == nLine)
					return;
			}
			m_arrLines.add(nLine);
		}
	}
	
	int getFirstLine()
	{
		if(m_arrLines != null && m_arrLines.size() > 0)
			return m_arrLines.get(0);
		return 0;
	}
	
	String getAllLinesAsString()
	{
		String cs = "";
		if(m_arrLines != null && m_arrLines.size() > 0)
		{
			for(int n=1; n<m_arrLines.size(); n++)
			{
				if(n != 1)
					cs += ", ";
				cs += m_arrLines.get(n);
			}
		}
		return cs;
	}
	
	String getFile()
	{
		return m_csFile;
	}
		
	private ArrayList<Integer> m_arrLines = null;
	private String m_csFile = null;
}
