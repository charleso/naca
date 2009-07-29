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
 * Created on 3 janv. 05
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

public class TestInspect extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();


	Var RCD_01 = declare.level(1).var();
		Var Source = declare.level(5).picX(10).value("***0**abcd").var();
		Var Count = declare.level(5).pic9(4).var();
		Var Count2 = declare.level(5).pic9(4).var();
		
	Var From = declare.level(1).picX(10).var();
	Var To = declare.level(1).picX(10).var();
	
	Var zone_Compteur = declare.level(1).var() ;
		Var cpt = declare.level(5).picS9(4).comp().var() ;                        
		Var cpt2 = declare.level(5).picS9(4).comp().var() ;  

	Paragraph Paragraph1 = new Paragraph(this){public void run(){Paragraph1();}};void Paragraph1()
	{
		setAssertActive(true);
		
		perform(TestConverting);
		perform(TestTallying);
		perform(TestReplacing);
		perform(TestReplacingLowValue);
		
		CESM.returnTrans();
	}
	
	Paragraph TestTallying = new Paragraph(this){public void run(){TestTallying();}};void TestTallying()
	{
		// Check initial values
		move("***0**abcd", Source);
		
		move(0, Count);
		// INSPECT Source TALLYING Count FORALL "**" 
		inspectTallying(Source).countAll("**").to(Count);
		assertIfFalse(Count.getInt() == 2);

		move("AB*****D**", Source);
		move(0, Count);
		// INSPECT Source TALLYING Count FORALL "**" after B before D
		inspectTallying(Source).after("B").before("D").countAll("**").to(Count);
		assertIfFalse(Count.getInt() == 2);

		move("AB*****D**", Source);
		inspectTallying(Source).after("D").countAll("**").to(Count);
		assertIfFalse(Count.getInt() == 3);
		
		move("AB*****A**", Source);
	//	inspectTallying(Source).leading("A").to(Count);
		inspectTallying(Source).countLeading("A").to(Count);
		assertIfFalse(Count.getInt() == 4);

		move("AB*****A**", Source);
//		inspectTallying(Source).leading("B").to(Count);
		inspectTallying(Source).countLeading("B").to(Count);
		assertIfFalse(Count.getInt() == 4);
		
		
		move(0, Count);
		move("abcdef    ", Source);
		inspectTallying(functionReverse(Source))                        // (355)         INSPECT FUNCTION REVERSE(WS-ENCOURS) TALLYING LONG        
			.countLeading(" ", Count);
		assertIfFalse(Count.getInt() == 4);

		move("   bcdef .", Source);
		inspectTallying(functionReverse(Source))                        // (355)         INSPECT FUNCTION REVERSE(WS-ENCOURS) TALLYING LONG        
			.countLeading(" ", Count);
		assertIfFalse(Count.getInt() == 4);

		move(" abcdefg  ", Source);
		inspectTallying(functionReverse(Source))                        // (355)         INSPECT FUNCTION REVERSE(WS-ENCOURS) TALLYING LONG        
			.countLeading(" ", Count);
		assertIfFalse(Count.getInt() == 6);
		
		move("a@@...fghi", Source);
		inspectTallying(Source)                                 
			.countAll("@", cpt).countAll(".", cpt2);
		assertIfFalse(cpt.getInt() == 2);
		assertIfFalse(cpt2.getInt() == 3);

		move("a@@...fghi", Source);
		move(0, cpt);
		inspectTallying(Source)                                 
			.countAll("@", cpt).countAll(".", cpt);
		assertIfFalse(cpt.getInt() == 5);

		int bb = 0;
	}
	
	Paragraph TestReplacing = new Paragraph(this){public void run(){TestReplacing();}};void TestReplacing()
	{
		move("***1**abcd", Source);
		assertIfFalse(Source.equals("***1**abcd"));

		// INSPECT Source REPLACING ALL "**" BY ZEROS.
		inspectReplacing(Source).all("**").byZero();
		assertIfFalse(Source.equals("00*100abcd"));

		move("***1**abcd", Source);
		inspectReplacing(Source).all("*").byZero();
		assertIfFalse(Source.equals("000100abcd"));

		move("***1**abcd", Source);
		inspectReplacing(Source).all("***").byZero();
		assertIfFalse(Source.equals("0001**abcd"));
		
		move("***1**abcd", Source);
		inspectReplacing(Source).first("**").byZero();
		assertIfFalse(Source.equals("00*1**abcd"));
		
		move("***1**1bcd", Source);
		inspectReplacing(Source).first("*1").byZero();
		assertIfFalse(Source.equals("**00**1bcd"));

		move("   1  2bcd", Source);
		inspectReplacing(Source).leadingSpaces().by("B");
		assertIfFalse(Source.equals("BBB1  2bcd"));

		move("   1  2bcd", Source);
		inspectReplacing(Source).leadingSpaces().by("BC");
		assertIfFalse(Source.equals("BBB1  2bcd"));
		
		move("bc 1  2bcd", Source);
		inspectReplacing(Source).leading("bc").by("BC");
		assertIfFalse(Source.equals("BC 1  2BCd"));
		
		move("bc 1  2bcd", Source);
		inspectReplacing(Source).first("bc").by("BC");
		assertIfFalse(Source.equals("BC 1  2bcd"));

		move("bc 1  2bcd", Source);
		inspectReplacing(Source).all("bc").by("BC");
		assertIfFalse(Source.equals("BC 1  2BCd"));
		
		move("bc 1  2bcd", Source);
		inspectReplacing(Source).after("1").before("d").all("bc").by("BC");
		assertIfFalse(Source.equals("bc 1  2BCd"));
	}
	
	Paragraph TestReplacingLowValue = new Paragraph(this){public void run(){TestReplacingLowValue();}};void TestReplacingLowValue()
	{
		moveLowValue(Source);
		moveSubStringSpace(Source, 2, 2);
		inspectReplacing(Source).allLowValues().byZero();
		assertIfFalse(Source.equals("0  0000000"));

		
		moveLowValue(Source);
		moveSubStringSpace(Source, 2, 2);
		inspectReplacing(Source).allLowValues().by("AB");
		assertIfFalse(Source.equals("A  AAAAAAA"));
		int n = 0;		
	}
	
	Paragraph TestConverting = new Paragraph(this){public void run(){TestConverting();}};void TestConverting()
	{
		move("abcd12eagz", Source);
		move("abcdefghij", From);
		move("ABCDEFGHIJ", To);
		inspectConverting(Source, From, To); 
		assertIfFalse(Source.equals("ABCD12EAGz"));
		
		move("12XY123412", Source);
		move("123456", From);
		move("987643", To);
		inspectConverting(Source.subString(3, 6), From, To); 
		assertIfFalse(Source.equals("12XY987612"));
				
		moveLowValue(Source);
		move("A", Source.getAt(2));
		move("B", Source.getAt(3));
		inspectConverting(Source.subString(2, 4), CobolConstant.LowValue, "X");		
		
		inspectConverting(Source, CobolConstant.LowValue, " ");
		assertIfFalse(Source.equals(" ABXX     "));
		int n = 0;		
	}
}
