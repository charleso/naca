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

import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.varEx.*;
import nacaLib.program.*;

public class TestSubstring extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	Var RCD_01 = declare.level(1).var();
		Var CUST_NAME = declare.level(10).picX(10).value("1234567890").var();
		Var v = declare.level(10).picX(10).var();
			
	
	Var i = declare.variable().pic9(2).value(65).var();
	Var j = declare.variable().pic9(2).value(78).var();
	
	Var ws_Num_8 = declare.variable().occurs(5).pic9(8).var();
	Var deldat = declare.variable().occurs(5).picX(14).var();

	Paragraph Paragraph1 = new Paragraph(this){public void run(){Paragraph1();}};void Paragraph1()
	{
		setAssertActive(true);
		
		perform(Test1);
		perform(Test2);
		CESM.returnTrans();
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

		move("abcd1234567890", deldat.getAt(1));
		assertIfFalse(deldat.getAt(1).equals("abcd1234567890"));
		
		move("bcde2345678901", deldat.getAt(2));
		assertIfFalse(deldat.getAt(2).equals("bcde2345678901"));
		
		move("cdef3456789012", deldat.getAt(3));
		assertIfFalse(deldat.getAt(3).equals("cdef3456789012"));
		
		move("defg4567890123", deldat.getAt(4));
		assertIfFalse(deldat.getAt(4).equals("defg4567890123"));
		
		move("efgh5678901234", deldat.getAt(5));
		assertIfFalse(deldat.getAt(5).equals("efgh5678901234"));
		
		for(move(1, i); isLess(i, 5); inc(i))
		{
			Var v = deldat.getAt(i);
			String csChunk = subString(deldat.getAt(i), 5, 4);
			setSubString(ws_Num_8.getAt(i), 10, 20, csChunk) ; // unchanged
			setSubString(ws_Num_8.getAt(i), 1, 4, csChunk) ; // (15062)            MOVE DELDAT OF RS7APA65(I 1)(5:4) TO WS-NUM-8(1:4)
			
			String csWritten = ws_Num_8.getAt(i).getString();
			String csWrittenLeft = csWritten.substring(0, 4);
			assertIfFalse(csWrittenLeft.equals(csChunk));
		}			
	}
}
