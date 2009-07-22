/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 6 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package utils;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class COriginalLisiting
{
	private static final Matcher nP = Pattern.compile("0x000A").matcher("\n");
	private static final Matcher rP = Pattern.compile("0x000D").matcher("\r");
	
	public void RegisterNewOriginalLine(String line)
	{
		m_arrLines.add(line) ;
	}
	public String GetOriginalLine(int n)
	{
		if (n>0 && n<=m_arrLines.size())
		{
			String cs = m_arrLines.get(n-1) ;
			cs = nP.replaceAll(cs);
			cs = rP.replaceAll(cs);
			return cs ;
		}
		else
		{
			return null ;
		}
	}
	protected Vector<String> m_arrLines  = new Vector<String>();

	public void Clear()
	{
		m_arrLines.clear() ;
	}
	/**
	 * @param csCurrentLine
	 */
	public void ReplaceCurrentOriginalLine(String csCurrentLine)
	{
		m_arrLines.set(m_arrLines.size()-1, csCurrentLine) ;
	}

}
