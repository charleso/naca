/*
 * NacaRTTests - Naca Tests for NacaRT support v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.varEx.*;
import nacaLib.mapSupport.MapFieldAttrFill;
import nacaLib.mapSupport.MapFieldAttrJustify;
import nacaLib.program.*;


/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestMap8 extends OnlineProgram
{
	DataSection WS = declare.workingStorageSection() ;

	Var impcopn = declare.level(11).picS9(3).var() ; 
	
	TestMap8Map testMap8Map = TestMap8Map.Copy(this) ;                                      // (255)      COPY RS31A01            SUPPRESS.
                                                                             // (256) 
 	MapRedefine mapRedefine = declare.level(1).redefinesMap(testMap8Map.rs3101f) ;    // (257)  01  RRS3101IO               REDEFINES RS3101FI.
 		Edit e = declare.level(03).edit() ;                       // (272)          05 RFACJR1L         PIC S9(4) COMP.

  		Edit editOcc = declare.level(03).editOccurs(4, "filler$31") ;         // (260)      03 FILLER     OCCURS 8.
	    	Edit edit1 = declare.level(05).pic("Z").edit() ;                       // (272)          05 RFACJR1L         PIC S9(4) COMP.
  
   	Paragraph Main = new Paragraph(this){public void run(){Main();}};void Main()
	{
   		move(1, impcopn); 
   		move(impcopn, testMap8Map.simpcopn01);
   		
   		Edit edit11 = edit1.getAt(1);
   		Edit edit12 = edit1.getAt(2);
   		Edit edit21 = edit1.getAt(3);
   		Edit edit22 = edit1.getAt(4);
   		
   		move("1", edit1.getAt(1));
   		move("2", edit1.getAt(2));
   		move("3", edit1.getAt(3));
   		move("4", edit1.getAt(4));

   		assertIfFalse(edit1.getAt(1).getString().endsWith("1"));
   		assertIfDifferent("1", testMap8Map.facjr121);
   		
   		assertIfFalse(edit1.getAt(2).getString().endsWith("2"));
   		assertIfDifferent("2", testMap8Map.facjr221);
   		
   		assertIfFalse(edit1.getAt(3).getString().endsWith("3"));
   		assertIfDifferent("3", testMap8Map.facjr122);
   		
   		assertIfFalse(edit1.getAt(4).getString().endsWith("4"));
   		assertIfDifferent("4", testMap8Map.facjr222);
	}
}
