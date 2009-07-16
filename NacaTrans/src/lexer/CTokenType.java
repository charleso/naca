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
public class CTokenType
{
	protected CTokenType()
	{
		m_bSourceValueDefined = false;
	}
	protected CTokenType(String val)
	{
		m_Value = val ;
		m_bSourceValueDefined = false;
	}
	
	protected CTokenType(String val, String csSourceValue)
	{
		m_Value = val ;
		m_csSourceValue = csSourceValue;
		m_bSourceValueDefined = true;
	}
	
	public String GetSourceValue()
	{
		if(m_bSourceValueDefined)
			return m_csSourceValue;
		
		// Should assert
		return "";
	}

	public boolean HasSourceValue()
	{
		return m_bSourceValueDefined;
	}
	
	public static CTokenType IDENTIFIER = new CTokenType() ; 
	public static CTokenType KEYWORD = new CTokenType() ; 
	public static CTokenType COMMENTS = new CTokenType() ; 
	public static CTokenType NUMBER = new CTokenType() ; 
	public static CTokenType STRING = new CTokenType() ; 
	public static CTokenType CONSTANT = new CTokenType() ; 
	public static CTokenType COMMA = new CTokenType("COMMA", ",") ; 
	public static CTokenType DOLLAR = new CTokenType("DOLLAR", "$") ; 
	public static CTokenType LEFT_SQUARE_BRACKET = new CTokenType("LEFT_SQUARE_BRACKET", "[") ; 
	public static CTokenType RIGHT_SQUARE_BRACKET = new CTokenType("RIGHT_SQUARE_BRACKET", "]") ; 
	public static CTokenType LEFT_BRACKET = new CTokenType("LEFT_BRACKET", "(") ; 
	public static CTokenType RIGHT_BRACKET = new CTokenType("RIGHT_BRACKET", ")") ; 
	public static CTokenType STAR = new CTokenType("STAR", "* ") ; 
//	public static CTokenType SHARP = new CTokenType("SHARP", "#") ; 
	public static CTokenType STAR_STAR = new CTokenType("STAR_STAR", "**") ; 
	public static CTokenType PLUS = new CTokenType("PLUS", "+") ; 
	public static CTokenType MINUS = new CTokenType("MINUS", "-") ; 
	public static CTokenType DOT = new CTokenType("DOT", ".") ; 
	public static CTokenType SLASH = new CTokenType("SLASH", "/") ; 
	public static CTokenType COLON = new CTokenType("COLON", ":") ; 
	public static CTokenType SEMI_COLON = new CTokenType("SEMI-COLON", ";") ; 
	public static CTokenType LESS_THAN = new CTokenType("LESS", "<") ; 
	public static CTokenType EQUALS = new CTokenType("EQUALS", "=") ; 
	public static CTokenType GREATER_THAN = new CTokenType("GREATER", ">") ; 
	public static CTokenType GREATER_OR_EQUALS = new CTokenType("GREATER_OR_EQUALS", ">=") ; 
	public static CTokenType LESS_OR_EQUALS = new CTokenType("LESS_OR_EQUALS", "<=") ; 
	public static CTokenType WHITESPACE = new CTokenType("WHITE_SPACE", " ") ; 
	public static CTokenType NEWLINE = new CTokenType("NEW_LINE") ;
	public static CTokenType UNRECOGNIZED = new CTokenType() ;

	public static CTokenType EXCLAMATION = new CTokenType("EXCLAMATION", "!") ;
	public static CTokenType CIRCUMFLEX = new CTokenType("CIRCUMFLEX", "^") ;
	
	public static CTokenType END_OF_BLOCK = new CTokenType("END_OF_BLOCK") ;

	//public static CTokenType  = new CTokenType("") ;
		
	public String m_Value = "" ; 
	protected String m_csSourceValue = "" ;
	protected boolean m_bSourceValueDefined = false;	
}
