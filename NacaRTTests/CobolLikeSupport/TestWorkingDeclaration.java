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
 * Created on 10 déc. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author PJD
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.program.*;
import nacaLib.varEx.*;


public class TestWorkingDeclaration extends OnlineProgram
{
//	public TestWorkingDeclaration(ProgramArgument prgArgs)
//	{
//		super(prgArgs);
//	}
	
	DataSection WorkingStorage = declare.workingStorageSection();
	
		Var mutok = declare.level(1).picX(6).valueSpaces().var() ;         // (270)          10 MUTOK           PIC X(6)                VALUE SPACE.
		Var mutok7 = declare.level(1).picX(7).value("zorglub").var() ;         // (270)          10 MUTOK           PIC X(6)                VALUE SPACE.
	
		Var TUAZONE = declare.level(1).var();
			Var TUAZONE_A = declare.level(5).picX(1).var();
			Var TUAZONE_B = declare.level(5).picX(255).var();
			Var TUAZONE_B_Redef = declare.level(5).redefines(TUAZONE_B).picX(10).value("0123456789").var();
				Var TUAZONE_B_RedefArray = declare.level(10).occurs(10).var();
					Var TUAZONE_B_Redef1 = declare.level(15).picX(1).var();

	
	Var WOcc = declare.level(1).occurs(2).picX(10).var();
	
	Var W3 = declare.level(1).occurs(10).var();
		Var VJJ = declare.level(5).picX(2).var();
		Var VMM = declare.level(5).picX(2).var();
		Var VAA = declare.level(5).picX(4).var();
	
	Var Broot = declare.level(1).picX(10).var();
		Var Broot81 = declare.level(2).picX(10).value("0123").var();
	
	Var v = declare.level(1).var();
		Var B60 = declare.level(5).picX(1000).var();
		
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
			Var B81_filler = declare.level(2).picX(6).var();
			
		
		Var B9 = declare.level(1).var();
	
		TestCopiedFile include1 = TestCopiedFile.Copy(this);
		TestCopiedFile include2 = TestCopiedFile.Copy(this);
		
		Var ArrayLetters = declare.level(1).occurs(5).var();
			Var Letter = declare.level(2).picX(2).var();
		Var LettersDef = declare.level(1).redefines(ArrayLetters).picX(10).value("AABBCCDDEEFF").var();
		
				
//		Var twazone = declare.level(1).var() ;                                         
//			TestWorkingTWACom twaecom = TestWorkingTWACom.Copy(this) ; 
			
//		Var twazone_R = declare.level(1).redefines(twazone).var() ;                 
//			Var twainp1 = declare.level(2).picX(1).var() ;                         
//			Var twaout1 = declare.level(2).picX(1).var() ;  
//			Var twaout2 = declare.level(2).picX(1).var() ;    
//			Var filler$34 = declare.level(2).picX(85).var() ;
			
		Var ArrayOk1 = declare.level(1).occursDepending(10, getCommAreaLength()).var();
			Var Item1 = declare.level(10).picX().var();
		
		Var NbItems = declare.level(1).pic9(2).var();
		Var ArrayOk2 = declare.level(1).occursDepending(10, NbItems).var();
			Var Item2 = declare.level(10).picX().var();
		

		Var Source = declare.level(1).picX(50).var();
		Var FROM_ZONE = declare.level(1).var();
    		Var FROM_MASQUE = declare.level(5).var();
    			Var FILLER = declare.level(10).picX(10).var();
        	Var F_WORKING = declare.level(5).var();
        		Var XXX = declare.level(10).picX(5).var();

			
	Var tnommap = declare.level(1).var() ;                                      // (102) 01  TNOMMAP.
        Var filler$98 = declare.level(5).picX(8).value("RS7800D ").var() ;      // (103) 05  FILLER              PIC X(8) VALUE 'RS7800D '.
        Var filler$99 = declare.level(5).picX(8).value("RS7800F ").var() ;      // (104) 05  FILLER              PIC X(8) VALUE 'RS7800F '.
        Var filler$100 = declare.level(5).picX(8).value("RS7800G ").var() ;      // (104) 05  FILLER              PIC X(8) VALUE 'RS7800F '.
        Var filler$101 = declare.level(5).picX(8).value("RS7800I ").var() ;      // (104) 05  FILLER              PIC X(8) VALUE 'RS7800F '.
  	Var filler$102 = declare.level(1).redefines(tnommap).var() ;                // (107) 01  FILLER REDEFINES TNOMMAP.
        Var maplang = declare.level(5).occurs(4).picX(8).var() ;                // (108) 05  MAPLANG             PIC X(8) OCCURS 4 TIMES
   
        
    Var G1 = declare.level(1).var();
		Var G2 = declare.level(2).picX(6).value("tutu").var();
			Var G3a = declare.level(3).picX(3).value("abc").var();
			Var G3b = declare.level(3).picX(3).value("def").var();
	
	Var X = declare.level(1).picX(10).var();
	Var Num = declare.level(1).pic9(5).var();
	
	Var ws_Ev_Diff = declare.level(1).picS9(14,1).valueZero()       // (240)      05 WS-EV-DIFF         PIC S9(14)V9(1) COMP-3 VALUE ZERO.     
			.var() ;
	
	Var ws_Ev_Diff_Ar = declare.level(1).picS9(14).comp3().valueZero()      // (241)      05 WS-EV-DIFF-AR      PIC S9(14)      COMP-3 VALUE ZERO.     
		.var() ;
	
	public void procedureDivision()
	{
		setAssertActive(true);
		
		move(123456789012l, ws_Ev_Diff);
		move(ws_Ev_Diff, ws_Ev_Diff_Ar);
		
		assertIfFalse(Broot81.equals("0123"));
		move("abcd", Broot81);
		
		move("0", mutok);
		boolean b = isEqual(mutok, "0     ");
		assertIfFalse(b);
		
		move("0", mutok7);
		b = isEqual(mutok, mutok7);
		assertIfFalse(b);
		
		move("  abc", mutok7);
		move(" abc", mutok);
		b = isDifferent(mutok, mutok7);
		assertIfFalse(b);
		
		move("A", WOcc.getAt(1));
		move("B", WOcc.getAt(2));
		String cs = WOcc.getAt(1).getString();
		cs = WOcc.getAt(2).getString();
		
		moveSpace(X);
		moveZero(Num);
		
		moveSpace(Num);
		moveZero(X);
		
		perform(moveTooLarge);
		perform(TestOccursInRedefine);
		perform(Test1);
		perform(TestAddressOf);
		//perform(TestWorkingTWA);
		
		CESM.returnTrans();
	}

	
	Paragraph moveTooLarge = new Paragraph(this){public void run(){moveTooLarge();}};void moveTooLarge()
	{
		move("abcdefghijklmnopqrstuvwxyz", Source);
		move(Source, FROM_MASQUE);
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
		
		move(B62, include1.IncludeV2);
		assertIfDifferent(include1.IncludeV2.getString(), "do RE MI F");
		assertIfEquals(include2.IncludeV2.getString(), "A SOLLA SI");		
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

		Var v2Before = TUAZONE_B_Redef1.getAt(2);
		TUAZONE.setCustomBuffer(acBuffer);	// was varLinkageSection.set(CallParam.m_var)
		Var v2 = TUAZONE_B_Redef1.getAt(2);
		Var v1 = TUAZONE_B_Redef1.getAt(1);		
		String cs = TUAZONE.toString();
		assertIfDifferent(subString(TUAZONE, 1, 6), "ABCDEF");
		assertIfDifferent(TUAZONE_A.getString(), "A");
		assertIfDifferent(subString(TUAZONE_B, 1, 5), "BCDEF");
		assertIfDifferent(subString(TUAZONE_B_Redef, 1, 5), "BCDEF");
		//assertIfDifferent(subString(TUAZONE_B_Redef0, 1, 1), "B");
		//assertIfDifferent(subString(TUAZONE_B_Redef1, 1, 1), "C");
		
		
		assertIfDifferent(v1.getString(), "B");
		
		
		assertIfDifferent(v2.getString(), "C");
		
		Var v3 = TUAZONE_B_Redef1.getAt(3);
		assertIfDifferent(v3.getString(), "D");
		
		
	}

//	Paragraph TestWorkingTWA = new Paragraph(this){public void run(){TestWorkingTWA();}};void TestWorkingTWA()
//	{
//		CESM.getAddressOfTWA(addressOf(twazone)) ;                              // (290) EXEC CICS ADDRESS
//		
//		move("12345678", twaecom.ecompgm);
//		assertIfFalse(isEqual(twainp1, "1"));
//		assertIfFalse(isEqual(twaout1, "2"));
//		assertIfFalse(isEqual(twaout2, "3"));
//		
//		move("A", twainp1);
//		assertIfFalse(isEqual(twaecom.ecompgm, "A2345678"));
//
//		move("O", twaout1);		
//		assertIfFalse(isEqual(twaecom.ecompgm, "AO345678"));
//		
//		Var v0 = twaecom.tua_S_Secur.getAt(1);
//		for(int i=1; i<5; i++)
//		{
//			Var vSource = ArrayLetters.getAt(i);
//			if(i == 1)
//				assertIfFalse(isEqual(vSource, "AA"));
//			else if(i == 2)
//				assertIfFalse(isEqual(vSource, "BB"));
//			else if(i == 3)
//				assertIfFalse(isEqual(vSource, "CC"));
//			else if(i == 4)
//				assertIfFalse(isEqual(vSource, "DD"));
//			else if(i == 5)
//				assertIfFalse(isEqual(vSource, "EE"));
//				
//			move(vSource, twaecom.tua_S_Cdproj.getAt(i));
//			if(i == 1)
//				assertIfFalse(isEqual(twaecom.tua_S_Cdproj.getAt(i), "AA"));
//			else if(i == 2)
//				assertIfFalse(isEqual(twaecom.tua_S_Cdproj.getAt(i), "BB"));
//			else if(i == 3)
//				assertIfFalse(isEqual(twaecom.tua_S_Cdproj.getAt(i), "CC"));
//			else if(i == 4)
//				assertIfFalse(isEqual(twaecom.tua_S_Cdproj.getAt(i), "DD"));
//			else if(i == 5)
//				assertIfFalse(isEqual(twaecom.tua_S_Cdproj.getAt(i), "EE"));		
//			
//		}
//
//		for(int i=1; i<5; i++)
//		{
//			String cs1 = ArrayLetters.getAt(i).getString();
//			String cs2 = twaecom.tua_S_Cdproj.getAt(i).getString();
//			assertIfFalse(isEqual(cs1, cs2)); 
//		}
//	}
		    
	Paragraph TestOccursInRedefine = new Paragraph(this){public void run(){TestOccursInRedefine();}};void TestOccursInRedefine()
	{
		assertIfFalse(maplang.getLength() == 32);
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
