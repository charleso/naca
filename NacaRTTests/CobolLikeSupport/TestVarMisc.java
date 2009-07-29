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
import nacaLib.program.*;
import nacaLib.varEx.*;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class TestVarMisc extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	Var vCond = declare.level(5).picX(1).value("2").var();
		Cond condA = declare.condition().value("1", "5").value(9).var();
	
	Var a1 = declare.level(1).var();
		Var b1 = declare.level(5).picX(1000).var();
//		Var b2 = declare.level(5).picX(21).var();
//		Var b2a = declare.level(5).redefines(b2).var();
//			Var b2a1 = declare.level(10).picX(10).var();
//			Var b2a2 = declare.level(10).picX(11).var();
//		Var b2b = declare.level(5).redefines(b2).occurs(3).picX(5).var();
//		Var b3 = declare.level(5).picX(50).var();
	Var a1a = declare.level(1).occurs(4).redefines(a1).var();
		Var a1a1 = declare.level(5).picX(5).var();
	Var a1b = declare.level(1).redefines(a1).occurs(4).var();
		Var a1b1 = declare.level(5).pic9(5).var();
			
	Var a2 = declare.level(1).picX(8).var();

		Var v = declare.level(1).var();
			Var B60 = declare.level(5).picX(1000).var();
			Var B60a = declare.level(5).picX(500).var();
			Var B61 = declare.level(5).occurs(7).var();
				Var B610 = declare.level(10).picX(1).var();
				Var B611 = declare.level(10).picX(2).var();
			Var B62 = declare.level(5).redefines(B61).picX(21).value("DO RE MI FA SOLLA SI ").var();
			Var B62a = declare.level(5).redefines(B61).picX(2).value("do ").var();
			Var B62b = declare.level(5).redefines(B61).var();
				Var B62b1 = declare.level(6).picX(1).var();
				Var B62b2 = declare.level(6).picX(1).var();
				
		Var B7 = declare.level(1).var();
			Var B71 = declare.level(2).picX(4).value("tutu").var();

		Var B8 = declare.level(1).picX(10).var();
			Var B81 = declare.level(2).picX(4).value("0123").var();
			
		
		Var B9 = declare.level(1).var();
	
			Var ArrayLetters = declare.level(1).occurs(5).var();
			Var Letter = declare.level(2).picX(2).var();
		Var LettersDef = declare.level(1).redefines(ArrayLetters).picX(10).value("AABBCCDDEEFF").var();
		
		
		Var TUAZONE = declare.level(1).var();
			Var TUAZONE_A = declare.level(5).picX(1).var();
			Var TUAZONE_B = declare.level(5).picX(255).var();
			Var TUAZONE_B_Redef = declare.level(5).redefines(TUAZONE_B).picX(10).value("0123456789").var();
				Var TUAZONE_B_Redef0 = declare.level(10).picX(1).var();
				Var TUAZONE_B_Redef1 = declare.level(10).picX(1).var();
			
			
//		Var ArrayOk1 = declare.level(1).occursDepending(10, getCommAreaLength()).var();
//			Var Item1 = declare.level(10).picX().var();
//		
//		Var NbItems = declare.level(1).pic9(2).var();
//		Var ArrayOk2 = declare.level(1).occursDepending(10, NbItems).var();
//			Var Item2 = declare.level(10).picX().var();
		

		Var Source = declare.level(1).picX(50).var();
		Var FROM_ZONE = declare.level(1).var();
    		Var FROM_MASQUE = declare.level(5).var();
    			Var FILLER = declare.level(10).picX(10).var();
        	Var F_WORKING = declare.level(5).var();
        		Var XXX = declare.level(10).picX(5).var();
        		
	Var vNumber = declare.level(1).pic9(5).value(12345).var();
	Var vSNumber = declare.level(1).picS9(5).value(-12345).var();
	Var vSLNumber = declare.level(1).picS9(5).signLeadingSeparated().value(-12345).var();
	Var vSTNumber = declare.level(1).picS9(5).signTrailingSeparated().value(-12345).var();
	
	Var vDec = declare.level(1).pic9(5, 2).value(-12345).var();
	Var vSDec = declare.level(1).picS9(5, 2).value(-12345).var();
	Var vSLDec = declare.level(1).picS9(5, 2).signLeadingSeparated().value(-3.14).var();
	Var vSTDec = declare.level(1).picS9(5, 2).signTrailingSeparated().value(-3.14).var();

	Var vComp3 = declare.level(1).pic9(5, 2).signTrailingSeparated().value(3.14).comp3().var();
	Var vSComp3 = declare.level(1).picS9(5, 2).signTrailingSeparated().value(-3.14).comp3().var();

	Var vComp4 = declare.level(1).pic9(5, 2).signTrailingSeparated().value(3.14).comp().var();
	Var vSComp4 = declare.level(1).picS9(5, 2).signTrailingSeparated().value(-3.14).comp().var();

	Var tnommap = declare.level(1).var() ;                                      // (102) 01  TNOMMAP.
        Var filler$98 = declare.level(5).picX(8).value("RS7800D ").var() ;      // (103) 05  FILLER              PIC X(8) VALUE 'RS7800D '.
        Var filler$99 = declare.level(5).picX(8).value("RS7800F ").var() ;      // (104) 05  FILLER              PIC X(8) VALUE 'RS7800F '.
        Var filler$100 = declare.level(5).picX(8).value("RS7800G ").var() ;      // (104) 05  FILLER              PIC X(8) VALUE 'RS7800F '.
        Var filler$101 = declare.level(5).picX(8).value("RS7800I ").var() ;      // (104) 05  FILLER              PIC X(8) VALUE 'RS7800F '.
  	Var filler$102 = declare.level(1).redefines(tnommap).var() ;                // (107) 01  FILLER REDEFINES TNOMMAP.
        Var maplang = declare.level(5).occurs(4).picX(8).var() ;                // (108) 05  MAPLANG             PIC X(8) OCCURS 4 TIMES

	public void procedureDivision()
	{
		setAssertActive(true);
		
		perform(TestOccursInRedefine);
				
		perform(Test1);
		perform(TestAddressOf);
		
		perform(moveTooLarge);
	
		CESM.returnTrans();
	}
	
	Paragraph moveTooLarge = new Paragraph(this){public void run(){moveTooLarge();}};void moveTooLarge()
	{
		move("abcdefghijklmnopqrstuvwxyz", Source);
		move(568, vSNumber);
		move(-123, vSNumber);
		move(568, vNumber);
		String cs2 = vNumber.getLoggableValue();
		cs2 = vSNumber.getLoggableValue();
		move(vNumber, FROM_MASQUE);
		
		move(12345, vSLNumber);
		move(-12345, vSLNumber);

		move(8965, vSTNumber);
		move(-8965, vSTNumber);
		
		move(vSTNumber, vSLNumber);
		
		move(vSNumber, FROM_MASQUE);
		//move(314, FROM_MASQUE);
		move(FROM_MASQUE, vNumber);
		
		move(Source, b1);
		move(Source, FROM_MASQUE);
		String cs = FROM_MASQUE.getLoggableValue();
		
		move(3.14, vDec);
		move(vDec, Source);

		move(3.14, vSDec);
		cs2 = vSDec.getLoggableValue();
		move(vSDec, Source);
		
		move(-1.235, vSDec);
		cs2 = vSDec.getLoggableValue();
		move(vSDec, Source);

		move(-12.56, vSDec);
		move(vSDec, Source);

		move(1.235, vSLDec);
		move(vSLDec, Source);

		move(-12.56, vSLDec);
		move(vSLDec, Source);

		move(1.235, vSTDec);
		move(vSTDec, Source);

		move(-12.56, vSTDec);
		move(vSTDec, Source);
		
		
		move(12.56, vComp3);
		move(vComp3, Source);
		
		move(12.56, vSComp3);
		move(vSComp3, Source);

		
		move(12.56, vComp4);
		move(vComp4, Source);
		
		move(-12.56, vSComp4);
		move(vSComp4, Source);
		
		assertIfFalse(FROM_MASQUE.equals("abcdefghij"));
		assertIfFalse(!F_WORKING.equals("klmno"));
	
		move("ABCDEFGHIJKLMNOPQRSTUVWXYZ", FROM_MASQUE);
		assertIfFalse(FROM_MASQUE.equals("ABCDEFGHIJ"));
		assertIfFalse(!F_WORKING.equals("KLMNO"));
	}
	
	
	Paragraph Test1 = new Paragraph(this){public void run(){Test1();}};void Test1()
	{
		assertIfFalse(B7.equals("tutu"));
		assertIfFalse(B7.getLength() == 4);
		
		assertIfFalse(B71.equals("tutu"));
		assertIfFalse(B71.getLength() == 4);

		assertIfFalse(B81.equals("0123"));
		assertIfFalse(B81.getLength() == 4);		

		assertIfFalse(subString(B8, 1, 4).equals("0123"));
		assertIfFalse(B8.getLength() == 10);		
		
		assertIfFalse(B62.equals("do RE MI FA SOLLA SI "));
		
//		move(B62, include1.IncludeV2);
//		assertIfDifferent(include1.IncludeV2.getString(), "do RE MI F");
//		assertIfEquals(include2.IncludeV2.getString(), "A SOLLA SI");		
	}

	Paragraph TestAddressOf = new Paragraph(this){public void run(){TestAddressOf();}};void TestAddressOf()
	{
		StringBuffer buf = new StringBuffer("ABCDEF");
		char [] acBuffer = new char [256];
		acBuffer[0] = 'A';
		acBuffer[1] = 'B';
		acBuffer[2] = 'C';
		acBuffer[3] = 'D';
		acBuffer[4] = 'E';
		acBuffer[5] = 'F';

		TUAZONE.setCustomBuffer(acBuffer);	// was Var2LinkageSection.set(CallParam.m_Var2)
		assertIfDifferent(subString(TUAZONE, 1, 6), "ABCDEF");
		assertIfDifferent(TUAZONE_A.getString(), "A");
		assertIfDifferent(subString(TUAZONE_B, 1, 5), "BCDEF");
		assertIfDifferent(subString(TUAZONE_B_Redef, 1, 5), "BCDEF");
		assertIfDifferent(subString(TUAZONE_B_Redef0, 1, 1), "B");
		assertIfDifferent(subString(TUAZONE_B_Redef1, 1, 1), "C");
	}
	    
	Paragraph TestOccursInRedefine = new Paragraph(this){public void run(){TestOccursInRedefine();}};void TestOccursInRedefine()
	{
		String cs = maplang.getAt(1).getString();
		assertIfFalse(cs.equals("RS7800D "));
		cs = maplang.getAt(2).getString();
		assertIfFalse(cs.equals("RS7800F "));
		cs = maplang.getAt(3).getString();
		assertIfFalse(cs.equals("RS7800G "));
		cs = maplang.getAt(4).getString();
		assertIfFalse(cs.equals("RS7800I "));
	}	
}
