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
 * Created on Jul 15, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package lexer;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CTokenKeyword extends CBaseToken
{
	public CTokenKeyword(CReservedKeyword kw, int line, boolean newline)
	{
		super(line, newline);
		m_kw = kw ;
		m_Value = kw.m_Name ;
	}
	
	public CReservedKeyword GetKeyword()
	{
		return m_kw ;
	}
	
	public CTokenType GetType()
	{
		return CTokenType.KEYWORD ;
	}
	
	CReservedKeyword m_kw ;

	/* (non-Javadoc)
	 * @see lexer.CBaseToken#GetDisplay()
	 */
	public String GetDisplay()
	{
		return m_kw.m_Name + " ";
	}
}
