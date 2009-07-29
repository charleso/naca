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
/*
 * Created on 6 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package utils;

import java.util.Vector;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class COriginalLisiting
{
	public void RegisterNewOriginalLine(String line)
	{
		m_arrLines.add(line) ;
	}
	public String GetOriginalLine(int n)
	{
		if (n>0 && n<=m_arrLines.size())
		{
			String cs = m_arrLines.get(n-1) ;
			if(cs.indexOf("VALUE 'RS0333D'") >= 0)
			{
				if(cs.indexOf("\r") >= 0)
				{
					int gg = 0;
				}
			}

			cs = cs.replaceAll("\n", "0x000A") ;
			cs = cs.replaceAll("\r", "0x000D") ;
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
