/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package lexer.FPac;

import lexer.CConstantList;
import lexer.CReservedConstant;

public class CFPacConstantList extends CConstantList
{

	protected static CFPacConstantList _List_ = new CFPacConstantList() ;
	public static CReservedConstant NOLOG = new CReservedConstant(_List_, "NOLOG") ;
	public static CReservedConstant LIST = new CReservedConstant(_List_, "LIST") ;
	public static CReservedConstant NOSTMT = new CReservedConstant(_List_, "NOSTMT") ;
	public static CReservedConstant MSG = new CReservedConstant(_List_, "MSG") ;
	// public static CReservedConstant  = new CReservedConstant(_List_, "") ;
	// public static CReservedConstant  = new CReservedConstant(_List_, "") ;
	// public static CReservedConstant  = new CReservedConstant(_List_, "") ;

}
