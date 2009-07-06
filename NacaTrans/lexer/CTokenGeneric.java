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
public class CTokenGeneric extends CBaseToken
{
	public CTokenGeneric(CTokenType type, int line, boolean newline)
	{
		super(line, newline);
		m_Type = type ;
		m_Value = type.m_Value ;
	}
	public CTokenType GetType()
	{
		return m_Type;
	}
	CTokenType m_Type = null ;
	/* (non-Javadoc)
	 * @see lexer.CBaseToken#GetDisplay()
	 */
	public String GetDisplay()
	{
		return m_Type.m_csSourceValue ;
	}
}
