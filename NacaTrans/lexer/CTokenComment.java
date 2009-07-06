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
public class CTokenComment extends CBaseToken
{
	public CTokenComment(String Comm, int line, boolean newline)
	{
		super(line, newline);
		m_Value = Comm ;
	}
	
	public CTokenType GetType()
	{
		return CTokenType.COMMENTS;
	}
	public String toString()
	{
		return "[COMMENT: " + m_Value + "]" ;
	}

	/* (non-Javadoc)
	 * @see lexer.CBaseToken#GetDisplay()
	 */
	public String GetDisplay()
	{
		return "";	
	}
}
