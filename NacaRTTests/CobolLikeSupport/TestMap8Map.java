/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.mapSupport.* ; 
import nacaLib.varEx.* ;
import nacaLib.program.* ;

class TestMap8Map extends Map 
{
	static TestMap8Map Copy(BaseProgram program) 
	{
		return new TestMap8Map(program);
	}
	static TestMap8Map Copy(BaseProgram program, CopyReplacing rep)  
	{
		Assert("Unimplemented replacing for MAPs") ;
		return null ;
	}
	TestMap8Map(BaseProgram program) 
	{
		super(program);
	}
 
	Form rs3101f = declare.form("rs31a01$1", 24, 80) ;                          
		Edit simpcopn01 = declare.edit("impcopn01", 3).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit() ; // (912) 

		Edit facjr121 = declare.edit("facjr121", 1).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit() ;	// (688) 
		Edit facjr221 = declare.edit("facjr221", 1).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit() ;	// (693) 
		Edit facjr122 = declare.edit("facjr122", 1).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit() ;	// (756) 
		Edit facjr222 = declare.edit("facjr222", 1).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit() ;	// (761) 

}
