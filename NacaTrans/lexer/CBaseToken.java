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

import utils.Transcoder;
import jlib.misc.NumberParser;


/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CBaseToken
{
	public CBaseToken(int line, boolean newline)
	{
		setLine(line);
		m_bIsNewLine = newline;
	}
	
	public String GetValue()
	{
		return m_Value ;
	}
	public int GetIntValue()
	{
		try
		{
			return Integer.parseInt(m_Value) ;
		}
		catch (NumberFormatException e)
		{
			Transcoder.logError(getLine(), "Cannot get int value " + toString());
			return 0;
		}
	}
	public abstract String GetDisplay();
	
	public CReservedKeyword GetKeyword()
	{
		return null ;
	}
	public CReservedConstant GetConstant()
	{
		return null ;
	}
	
	public boolean IsWhiteSpace()
	{
		return GetType() == CTokenType.WHITESPACE || GetType() == CTokenType.NEWLINE || GetType()==CTokenType.END_OF_BLOCK ; 
	}
//	public boolean IsNewLine()
//	{
//		return GetType() == CTokenType.NEWLINE ; 
//	}
	
	public boolean IsKeyword()
	{
		return GetType() == CTokenType.KEYWORD;
	}
	
	public abstract CTokenType GetType() ;
	protected String m_Value = "" ;
	
	public char[] GetCharValue()
	{
		return null ;
	}
	
	public String toString()
	{
		return "[" + GetValue() + "]" ;
	}
	private int m_line = 0;
	
	public int getLine()
	{
		return m_line;
	}
	
	public void setLine(int line)
	{
		m_line = line;
		Transcoder.setLine(m_line);
	}
	
	public boolean m_bIsNewLine = false ;
}
