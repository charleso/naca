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

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class PosLineCol
{
	public PosLineCol()
	{
		m_nLine = 0;
		m_nCol = 0;
		m_nLength = 0;
	}
	
	public void setLine(int n)
	{
		m_nLine = n;
	}
	
	public void setCol(int n)
	{
		m_nCol = n;
	}

	public void setLength(int n)
	{
		m_nLength = n;
	}
	
	public void setLineColLength(int nLine, int nCol, int nLength)
	{
		m_nLine = nLine;
		m_nCol = nCol;
		m_nLength = nLength;
	}
	
	public int getLine()
	{
		return m_nLine;
	}
	
	public int getCol()
	{
		return m_nCol;
	}
	
	public int getLength()
	{
		return m_nLength;
	}
	
	private int m_nLine;
	private int m_nCol;
	private int m_nLength;
}
