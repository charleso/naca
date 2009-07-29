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
/*
 * Created on 13 janv. 05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import nacaLib.batchPrgEnv.BatchProgram;
import nacaLib.program.Paragraph;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.Var;

public class TestSubstring extends BatchProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	
	Var header = declare.level(1).picX(150).value("1234567890").var();
	Var arr = declare.level(1).var();
		Var item = declare.level(5).occurs(10).picX(10).var();
	Var RCD_01 = declare.level(1).var();
		Var CUST_NAME = declare.level(10).picX(10).value("1234567890").var();
		Var v = declare.level(10).picX(10).var();
			
	
	Var i = declare.variable().pic9(2).value(65).var();
	Var j = declare.variable().pic9(2).value(78).var();
	
	Var ws_Num_8 = declare.variable().occurs(5).pic9(8).var();
	
	Var w_Min_Tot = declare.level(1).picX(51)                                   // (265)  01  W-MIN-TOT  PIC X(51)  VALUE                                  
	.value("abcdefghijklmnopqrstuvwxyz\u00e2\u00e4\u00e0\u00e1\u00e3\u00e9\u00ea\u00eb\u00e8\u00ed\u00ee\u00ef\u00ec\u00f4\u00f6\u00f2\u00f3\u00f5\u00fb\u00fc\u00f9\u00fa\u00fd\u00e7\u00f1")
	.var() ;
	
	Var w_Maj_Tot = declare.level(1).picX(51)                                   // (263)  01  W-MAJ-TOT  PIC X(51)  VALUE                                  
	.value("ABCDEFGHIJKLMNOPQRSTUVWXYZAAAAAEEEEIIIIOOOOOUUUUYCN").var() ;
	
	Var w_Source = declare.level(1).picX(10).value("d‡ÈËÍÙ‚ryz").var();

	//Var deldat = declare.variable().occurs(5).picX(14).var();

	public void procedureDivision()
	{	
		display("Converted from "+ val(w_Source));
		inspectConverting(w_Source, w_Min_Tot, w_Maj_Tot);	// (2173)         INSPECT
		display("Converted to "+ val(w_Source)); 
		
		setAssertActive(true);
		for(int n=1; n<=10; n++)
		{
			move("abcdefghij", item.getAt(n));
		}
		perform(Test0);
		
		move("1234567890", CUST_NAME);
		perform(Test1);
		perform(Test2);
		CESM.returnTrans();
	}

	Paragraph Test0 = new Paragraph(this){public void run(){Test0();}};void Test0()
	{
		assertIfFalse(isEqual(CUST_NAME, "1234567890"));
		Var var0 = CUST_NAME.subString(1, 2);	// 12
		Var var1 = CUST_NAME.subString(2, 2);	// 23
		Var var2 = CUST_NAME.subString(3, 3);	// 345
		move("abcdef", var2);
		assertIfFalse(isEqual(CUST_NAME, "12abc67890"));
		
		Var var3 = CUST_NAME.subString(7, 2);	// 345
		
		int gg = 0;		
	}
	
	Paragraph Test1 = new Paragraph(this){public void run(){Test1();}};void Test1()
	{
		assertIfFalse(isEqual(CUST_NAME, "1234567890"));

		String cs = subString(CUST_NAME, 1, 2);
		assertIfFalse(cs.equals("12"));

		cs = subString(CUST_NAME, 1, 1);
		assertIfFalse(cs.equals("1"));

		cs = subString(CUST_NAME, 2, 3);
		assertIfFalse(cs.equals("234"));

		cs = subString(CUST_NAME, 8, 3);
		assertIfFalse(cs.equals("890"));
		
		cs = subString(CUST_NAME, 9, 3);
		assertIfFalse(cs.equals(""));	// Is ti 90 ?

		cs = subString(CUST_NAME, 12, 3);
		assertIfFalse(cs.equals(""));
	}
	
	Paragraph Test2 = new Paragraph(this){public void run(){Test2();}};void Test2()
	{		
		assertIfFalse(i.equals(65));
	
		moveSubStringZero(i, 3, 4);	// unchanged
		assertIfFalse(i.equals(65));
		assertIfFalse(j.equals(78));		

		moveSubStringZero(i, 2, 4);
		assertIfFalse(i.equals(60));
		assertIfFalse(j.equals(78));

		moveSubStringZero(i, 1, 1);
		assertIfFalse(i.equals(0));
		assertIfFalse(j.equals(78));
		
		move(98, i);
		assertIfFalse(i.equals(98));
		moveSubStringZero(i, 1, 5);
		assertIfFalse(i.equals(0));
		assertIfFalse(j.equals(78));

//		move("abcd1234567890", deldat.getAt(1));
//		assertIfFalse(deldat.getAt(1).equals("abcd1234567890"));
//		
//		move("bcde2345678901", deldat.getAt(2));
//		assertIfFalse(deldat.getAt(2).equals("bcde2345678901"));
//		
//		move("cdef3456789012", deldat.getAt(3));
//		assertIfFalse(deldat.getAt(3).equals("cdef3456789012"));
//		
//		move("defg4567890123", deldat.getAt(4));
//		assertIfFalse(deldat.getAt(4).equals("defg4567890123"));
//		
//		move("efgh5678901234", deldat.getAt(5));
//		assertIfFalse(deldat.getAt(5).equals("efgh5678901234"));
//		
//		for(move(1, i); isLess(i, 5); inc(i))
//		{
//			Var v = deldat.getAt(i);
//			String csChunk = subString(deldat.getAt(i), 5, 4);
//			setSubString(ws_Num_8.getAt(i), 10, 20, csChunk) ; // unchanged
//			setSubString(ws_Num_8.getAt(i), 1, 4, csChunk) ; // (15062)            MOVE DELDAT OF RS7APA65(I 1)(5:4) TO WS-NUM-8(1:4)
//			
//			String csWritten = ws_Num_8.getAt(i).getString();
//			String csWrittenLeft = csWritten.substring(0, 4);
//			assertIfFalse(csWrittenLeft.equals(csChunk));
//		}			
	}
}
