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
package lexer.BMS;

import lexer.CKeywordList;
import lexer.CReservedKeyword;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CBMSKeywordList extends CKeywordList
{
	public static CKeywordList List = new CBMSKeywordList() ;
	public static CReservedKeyword DFHMSD = new CReservedKeyword(List, "DFHMSD") ; 
	public static CReservedKeyword DFHMDI = new CReservedKeyword(List, "DFHMDI") ; 
	public static CReservedKeyword DFHMDF = new CReservedKeyword(List, "DFHMDF") ; 
	
	public static CReservedKeyword TYPE = new CReservedKeyword(List, "TYPE") ; 
	public static CReservedKeyword LANG = new CReservedKeyword(List, "LANG") ; 
	public static CReservedKeyword MODE = new CReservedKeyword(List, "MODE") ; 
	public static CReservedKeyword SIZE = new CReservedKeyword(List, "SIZE") ; 
	public static CReservedKeyword LINE = new CReservedKeyword(List, "LINE") ; 
	public static CReservedKeyword COLUMN = new CReservedKeyword(List, "COLUMN") ; 
	public static CReservedKeyword CTRL = new CReservedKeyword(List, "CTRL") ; 
	public static CReservedKeyword TIOAPFX = new CReservedKeyword(List, "TIOAPFX") ; 
	public static CReservedKeyword MAPATTS = new CReservedKeyword(List, "MAPATTS") ; 
	public static CReservedKeyword DSATTS = new CReservedKeyword(List, "DSATTS") ; 
	public static CReservedKeyword DATA = new CReservedKeyword(List, "DATA") ; 
	public static CReservedKeyword JUSTIFY = new CReservedKeyword(List, "JUSTIFY") ; 
	public static CReservedKeyword OBFMT = new CReservedKeyword(List, "OBFMT") ; 
	public static CReservedKeyword POS = new CReservedKeyword(List, "POS") ; 
	public static CReservedKeyword LENGTH = new CReservedKeyword(List, "LENGTH") ; 
	public static CReservedKeyword ATTRB = new CReservedKeyword(List, "ATTRB") ; 
	public static CReservedKeyword COLOR = new CReservedKeyword(List, "COLOR") ; 
	public static CReservedKeyword HILIGHT = new CReservedKeyword(List, "HILIGHT") ; 
	public static CReservedKeyword INITIAL = new CReservedKeyword(List, "INITIAL") ; 
	public static CReservedKeyword END = new CReservedKeyword(List, "END") ; 
	public static CReservedKeyword GRPNAME = new CReservedKeyword(List, "GRPNAME") ; 
	public static CReservedKeyword PICIN = new CReservedKeyword(List, "PICIN") ; 
	public static CReservedKeyword PICOUT = new CReservedKeyword(List, "PICOUT") ; 
	public static CReservedKeyword TRAILER = new CReservedKeyword(List, "TRAILER") ; 
	//public static CReservedKeyword  = new CReservedKeyword(List, "") ; 
	//public static CReservedKeyword  = new CReservedKeyword(List, "") ; 
	//public static CReservedKeyword  = new CReservedKeyword(List, "") ; 
	//public static CReservedKeyword  = new CReservedKeyword(List, "") ; 
	//public static CReservedKeyword  = new CReservedKeyword(List, "") ; 
	//public static CReservedKeyword  = new CReservedKeyword(List, "") ; 
	//public static CReservedKeyword  = new CReservedKeyword(List, "") ; 
	//public static CReservedKeyword  = new CReservedKeyword(List, "") ; 
}
