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
 * Created on Jul 26, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package lexer.Cobol;

import lexer.CConstantList;
import lexer.CReservedConstant;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CCobolConstantList extends CConstantList
{
	public static CConstantList List = new CCobolConstantList() ;
	
	public static CReservedConstant ZERO = new CReservedConstant(List, "ZERO") ; 
	public static CReservedConstant ZEROS = new CReservedConstant(List, "ZEROS") ; 
	public static CReservedConstant ZEROES = new CReservedConstant(List, "ZEROES") ; 
	public static CReservedConstant TRUE = new CReservedConstant(List, "TRUE") ; 
	public static CReservedConstant FALSE = new CReservedConstant(List, "FALSE") ; 
	public static CReservedConstant SPACE = new CReservedConstant(List, "SPACE") ; 
	public static CReservedConstant SPACES = new CReservedConstant(List, "SPACES") ; 
	public static CReservedConstant QUOTE = new CReservedConstant(List, "QUOTE") ; 
	public static CReservedConstant QUOTES = new CReservedConstant(List, "QUOTES") ; 
	public static CReservedConstant HIGH_VALUE = new CReservedConstant(List, "HIGH-VALUE") ;
	public static CReservedConstant HIGH_VALUES = new CReservedConstant(List, "HIGH-VALUES") ;
	public static CReservedConstant LOW_VALUE = new CReservedConstant(List, "LOW-VALUE") ; 
	public static CReservedConstant LOW_VALUES = new CReservedConstant(List, "LOW-VALUES") ; 
	// public static CReservedConstant  = new CReservedConstant(List, "") ;
}
