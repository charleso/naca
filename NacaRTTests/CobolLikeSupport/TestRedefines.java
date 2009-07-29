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

/*
 * Created on 15 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestRedefines extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	Var vBuffer = declare.variable().picX(10).var();
	
	Var vSource = declare.level(1).var();
		Var vSource1 = declare.level(5).picX(10).var();
		Var vSource2 = declare.level(5).picX(10).var();
	
	Var vRedefA = declare.level(1).redefines(vSource).var();
		Var vRedefA1 = declare.level(5).picX(12).var();
	
	Var vRedefB = declare.level(1).redefines(vSource).var();
		Var vRedefB1 = declare.level(5).picX(12).var();
		Var vRedefB2 = declare.level(5).picX(5).var();
	
	Var vRedefC = declare.level(1).redefines(vSource).var();
		Var vRedefC1 = declare.level(5).var();
			Var vRedefC11 = declare.level(5).picX(2).var();
			Var vRedefC12 = declare.level(5).picX(3).var();

	Var vNext  = declare.level(1).picX(5).var();
	Var vNext2  = declare.level(1).picX(5).var();
	
	Var w_Lib = declare.level(1).var() ;                                        // (57)  01  W-LIB.                                                       
	Var w_Lib01r = declare.level(5).var() ;                                 // (58)      05 W-LIB01R.                                                 
		Var filler$152 = declare.level(7).picX(18)                          // (59)         07 FILLER           PIC X(18)      VALUE                  
			.value("   Profit-Center =").var() ;
		                                                                    // (60)              '   Profit-Center ='.                                
		Var filler$153 = declare.level(7).picX(18)                          // (61)         07 FILLER           PIC X(18)      VALUE                  
			.value("Centre de profit =").var() ;
		                                                                    // (62)              'Centre de profit ='.                                
		Var filler$154 = declare.level(7).picX(18)                          // (63)         07 FILLER           PIC X(18)      VALUE                  
			.value(" Centro di prof. =").var() ;
		                                                                    // (64)              ' Centro di prof. ='.                                
		Var filler$155 = declare.level(7).picX(18)                          // (65)         07 FILLER           PIC X(18)      VALUE                  
			.value("Centre de profit =").var() ;
	                                                                        // (66)              'Centre de profit ='.                                
	Var w_Lib01 = declare.level(5).redefines(w_Lib01r).occurs(4).picX(18)   // (67)      05 W-LIB01 REDEFINES W-LIB01R                                
		.var() ;
	Var vDest = declare.level(5).picX(18).var();
	
	Var ws_Orsparam = declare.level(1).var() ;                              // (228)  01  WS-ORSPARAM.                                                 
		Var orsp_Utiento = declare.level(5).var() ;                            // (229)      05 ORSP-UTIENTO.                                             
			Var orsp_Utiste = declare.level(15).picX(2).valueSpaces().var() ;     // (230)         15 ORSP-UTISTE       PIC X(2)              VALUE SPACES.  
			Var orsp_Uticpr = declare.level(15).picX(3).valueSpaces().var() ;     // (231)         15 ORSP-UTICPR       PIC X(3)              VALUE SPACES.  
        Var val3 = declare.level(5).picX(8).valueSpaces().var() ;              // (232)      05  VAL3                PIC X(8)              VALUE SPACES.  
        Var orsp_Trtdat = declare.level(5).redefines(val3).pic9(8).var() ;     // (233)      05  ORSP-TRTDAT REDEFINES VAL3 PIC 9(8).                     
	
	public void procedureDivision()
	{
		setAssertActive(true);
		
		boolean b9_00 = isZero(orsp_Trtdat);
		assertIfFalse(!b9_00);
		boolean bX_00 = isZero(val3);
		assertIfFalse(!bX_00);

		initialize(orsp_Trtdat);
		move(0, orsp_Trtdat);
		
		boolean b9_01 = isZero(orsp_Trtdat);
		assertIfFalse(b9_01);
		boolean bX_01 = isZero(val3);
		assertIfFalse(bX_01);
		
		Var v = w_Lib01.getAt(1);
		move(w_Lib01.getAt(1), vDest);                         // (258)        MOVE W-LIB01(I-LNG)  TO SLIB1I
		assertIfFalse(vDest.equals("   Profit-Center ="));
		
		move(w_Lib01.getAt(2), vDest);                         // (258)        MOVE W-LIB01(I-LNG)  TO SLIB1I
		assertIfFalse(vDest.equals("Centre de profit ="));
		
		move(w_Lib01.getAt(3), vDest);                         // (258)        MOVE W-LIB01(I-LNG)  TO SLIB1I
		assertIfFalse(vDest.equals(" Centro di prof. ="));
		
		move(w_Lib01.getAt(4), vDest);                         // (258)        MOVE W-LIB01(I-LNG)  TO SLIB1I
		assertIfFalse(vDest.equals("Centre de profit ="));
		
		moveSpace(vSource);
		move("12345678901234567890", vRedefA1);
				
		assertIfFalse(vRedefA1.equals("123456789012"));
		assertIfFalse(vSource1.equals("1234567890"));
		assertIfFalse(vSource2.equals("12        "));
		
		moveSpace(vNext);
		moveSpace(vNext2);
		move("abc", vNext);
		assertIfFalse(vNext.equals("abc  "));
		
		move("abcdefghji", vNext);
		assertIfFalse(vNext.equals("abcde"));
		assertIfFalse(vNext2.equals("     "));
		
		move(vNext, vSource);
	}
}


