/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
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

import java.io.IOException;
import java.io.InputStream;

import utils.CTransApplicationGroup.EProgramType;

import jlib.misc.FileSystem;
import jlib.misc.StringUtil;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class FileContentBuffer
{	
	private int m_nNbInlining = 0;
	private StringBuilder m_sb = null;
	private int m_nReadPosition = 0;
	private EProgramType m_eProgramType = null;
	
	public FileContentBuffer(EProgramType eProgramType)
	{
		m_sb = new StringBuilder();
		m_nNbInlining = 0;
		m_eProgramType = eProgramType;
	}
	
	public EProgramType getEProgramType()
	{
		return m_eProgramType;
	}
	
	public void initialLoad(StringBuilder sbToInsert)
	{
		m_sb.insert(0, sbToInsert.toString());
	}
	
	public void insertInlinedCodeAtReadPosition(StringBuilder sbToAppend)
	{
		m_nNbInlining++;
		m_sb.insert(m_nReadPosition, sbToAppend.toString());		
	}
	
	boolean loadWithFileBuffer(InputStream buf)
	{
		m_sb = new StringBuilder();	
		m_nReadPosition = 0;
		try
		{
			while(buf.available() > 0)
			{
				char cChar = (char)buf.read();
				m_sb.append(cChar);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public int available()
	{
		int nLength = m_sb.length();
		int nRemaining = nLength - m_nReadPosition;
		return nRemaining;
	}
	
	public char read()
	{
		char c = m_sb.charAt(m_nReadPosition);
		m_nReadPosition++;
		return c;
	}
	
	public void dumpIfNeeded(String csFileName)
	{
		if(m_nNbInlining > 0)
			FileSystem.writeFile(csFileName, m_sb);
	}
	
	public void updateReplacingMarkers(String csPatternSeach, String csPatternReplacement)
	{
		if(!StringUtil.isEmpty(csPatternSeach) && !StringUtil.isEmpty(csPatternReplacement))
		{
			StringUtil.replace(m_sb, csPatternSeach, csPatternReplacement);
		}
	}
	
	public void removeCobolLineFormatting()
	{
		m_nReadPosition = 0;
		StringBuilder sb = new StringBuilder(); 
		String csLine = readLine();
		while(csLine != null)
		{
			csLine = removeAfterCol72(csLine);
			csLine = removeLineHeader(csLine);
			sb.append(csLine);
			csLine = readLine();
		}
		m_nReadPosition = 0;
		m_sb = sb;
	}
	
	private String readLine()
	{		
		if(m_nReadPosition >= m_sb.length())
			return null;
		
		String cs = "";
		while(m_nReadPosition < m_sb.length())
		{
			char c = m_sb.charAt(m_nReadPosition);
			cs += c;	
			m_nReadPosition++;
			if(c == '\n')
				return cs;
		}
		return cs;
	}
	
	private String removeAfterCol72(String csLine)
	{
		if(csLine.length() > 72)
		{
			String cs = csLine.substring(0, 72);
			if(!cs.endsWith("\n"))
				 cs += "\n";
			return cs;
		}
		return csLine;
	}
	
	private String removeLineHeader(String csLine)
	{
		if(csLine.length() > 6)
			return csLine.substring(6);
		return csLine;
	}
}
