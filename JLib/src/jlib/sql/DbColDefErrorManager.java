/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.sql;


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class DbColDefErrorManager
{
	private StringBuilder m_sb = null;
	private int m_nLine = 0;
	private int m_nErrors = 0;
	
	public void setLine(int nLine)
	{
		m_nLine = nLine;
	}
	
	void reportTruncationError(long lOriginalValue, long lValue, String csColName)
	{
		m_nErrors++;
		if(m_sb == null)
			m_sb = new StringBuilder();
		int nLine = m_nLine + 1; 
		m_sb.append("Truncation error at file line " + nLine + " For column="+csColName + " : Original Value="+lOriginalValue + " Truncated to="+lValue + "\r\n");
	}
	
	void reportTruncationError(String csOriginalValue, String csValue, String csColName)
	{
		m_nErrors++;
		if(m_sb == null)
			m_sb = new StringBuilder();
		int nLine = m_nLine + 1; 
		m_sb.append("Truncation error at file line " + nLine + " For column="+csColName + " : Original Value="+csOriginalValue + " Truncated to="+csValue + "\r\n");
	}
	
	public int getNbErrors()
	{
		return m_nErrors;
	}
	
	public String getErrorsText()
	{
		return m_sb.toString();
	}
}
