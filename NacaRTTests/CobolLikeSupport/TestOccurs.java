/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.varEx.*;
import nacaLib.program.*;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestOccurs extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();

	Var from_Zone = declare.level(1).var() ;                                    // (1)  01  FROM-ZONE.
		Var from_Switch = declare.level(5).picX(1).valueSpaces().var() ;        // (2)      05 FROM-SWITCH          PIC X             VALUE SPACE.
		Var from_Passage = declare.level(5).picX(150).valueSpaces().var() ;     // (3)      05 FROM-PASSAGE         PIC X(150)        VALUE SPACE.
		Var from_Help = declare.level(5).var() ;                                // (4)      05 FROM-HELP.
			Var help_Pocsr = declare.level(10).picS9(7).comp3().valueZero()     // (5)         10 HELP-POCSR        PIC S9(7) COMP-3  VALUE ZERO.
				.var() ;
			Var help_Noerr = declare.level(10).picX(4).valueSpaces().var() ;    // (6)         10 HELP-NOERR        PIC X(4)          VALUE SPACE.
			Var help_Nomasqs = declare.level(10).picX(7).valueSpaces().var() ;  // (7)         10 HELP-NOMASQS      PIC X(7)          VALUE SPACE.
		Var from_Masque = declare.level(5).var() ;                              // (8)      05 FROM-MASQUE.
			Var filler$2053 = declare.level(10).picX(12).valueSpaces().var() ;  // (9)         10 FILLER            PIC X(12)         VALUE SPACE.
			Var filler$2054 = declare.level(10).picX(7).valueSpaces().var() ;   // (10)         10 FILLER            PIC X(7)          VALUE SPACE.
			Var help_Idtrt = declare.level(10).picX(6).valueSpaces().var() ;    // (11)         10 HELP-IDTRT        PIC X(6)          VALUE SPACE.
			Var filler$2055 = declare.level(10).picX(73).valueSpaces().var() ;  // (12)         10 FILLER            PIC X(73)         VALUE SPACE.
			Var help_Cdtrans = declare.level(10).picX(4).valueSpaces().var() ;  // (13)         10 HELP-CDTRANS      PIC X(4)          VALUE SPACE.

		
			Var filler$241 = declare.level(10).picX(2299).var() ;                   // (364)         10 FILLER                PIC X(2299).                     
			Var pos_Cursor = declare.level(5).picS9(4).comp().var() ;               // (365)       05 POS-CURSOR              PIC S9(4) COMP.                  
			Var res_Annul = declare.level(5).picX(1).var() ;                        // (366)       05 RES-ANNUL               PIC X.                           
			Var Array = declare.level(5).occurs(16).var() ;                      // (367)       05 RES-ZONE OCCURS 16.                                      
				Var A = declare.level(10).picX(4).var() ;                      // (368)         10 IMPDOC                PIC X(4).                        
				Var B = declare.level(10).picX(3).var() ;                    // (372)         10 IMPDEST2              PIC X(3).                        
				Var C = declare.level(10).var() ;                             // (373)         10 IMPCRIT.                                               
					Var C1 = declare.level(15).picX(1).var() ;                 // (374)           15 IMPPAGM             PIC X.                           
					Var C2 = declare.level(15).picX(4).var() ;                   // (375)           15 IMPLU               PIC X(4).                        
					Var C3 = declare.level(15).picX(3).var() ;                 // (376)           15 IMPCOPN             PIC X(3).                        
 
	Var v1 = declare.level(1).var() ;                                // (348)      05 SV-ADRNOS.
		Var sv_Adrnos = declare.level(5).var() ;                                // (348)      05 SV-ADRNOS.
		   Var sv_Adrnol = declare.level(10).occurs(3).var() ;                 // (349)         10 SV-ADRNOL OCCURS 3.
		    Var sv_Adrno00 = declare.level(15).occurs(7).picX(3).var() ; 
					
			
	public void procedureDivision()
	{
		setAssertActive(true);
		perform(TestsOccurs);
		
		CESM.returnTrans();
	}

	
	Paragraph TestsOccurs = new Paragraph(this){public void run(){TestsOccurs();}};void TestsOccurs()
	{
		String cs= "abc";
		move(cs, sv_Adrno00.getAt(3, 5));
		for(int i=1; i<=3; i++)
		{
			for(int j=1; j<=7; j++)
			{
				cs= "" + i + "," + j;				
				move(cs, sv_Adrno00.getAt(i, j));
			}
		}
		
		 cs = help_Pocsr.toString();
		int n1 =0 ;
		int nC = C.DEBUGgetBodyAbsolutePosition();
		int nC1 = C1.DEBUGgetBodyAbsolutePosition();
		assertIfFalse(nC == nC1);
		
		Var vVC1100 = C1.getAt(1);	// should not be address of C
		int nVC1100 = vVC1100.DEBUGgetBodyAbsolutePosition();
		//assertIfFalse(nC == nVC11);
		
		
		Var vVC11 = C1.getAt(1);	// should not be address of C
		int nVC11 = vVC11.DEBUGgetBodyAbsolutePosition();
		assertIfFalse(nC == nVC11);
		
		Var vVC21 = C2.getAt(1);	// should not be address of C
		int nVC21 = vVC21.DEBUGgetBodyAbsolutePosition();
		assertIfFalse(nC+1 == nVC21);
		
		Var vVC31 = C3.getAt(1);	// should not be address of C
		int nVC31 = vVC31.DEBUGgetBodyAbsolutePosition();
		assertIfFalse(nC+1+4 == nVC31);

		Var vVC12 = C1.getAt(2);	// should not be address of C
		int nVC12 = vVC12.DEBUGgetBodyAbsolutePosition();
		assertIfFalse(nC+1*(4+3+1+4+3) == nVC12);

		Var vVC22 = C2.getAt(2);	// should not be address of C
		int nVC22 = vVC22.DEBUGgetBodyAbsolutePosition();
		assertIfFalse(nC+1*(4+3+1+4+3)+1 == nVC22);
		
		Var vVC32 = C3.getAt(2);	// should not be address of C
		int nVC32 = vVC32.DEBUGgetBodyAbsolutePosition();
		assertIfFalse(nC+1*(4+3+1+4+3)+1+4 == nVC32);

		move("toto", C1.getAt(1));
		String cs2 = C2.getAt(1).getString();
		
		int n=0;
		
	}
					
}
