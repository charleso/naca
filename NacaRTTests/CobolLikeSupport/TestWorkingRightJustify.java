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


public class TestWorkingRightJustify extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	TestMapRightJustifyForm form = TestMapRightJustifyForm.Copy(this) ;                                      // (356)      EXEC SQL INCLUDE RS73A20 END-EXEC.                           
	                                                                            // (357)                                                                   
	MapRedefine iers7320fs = declare.level(1).redefinesMap(form.Screen) ;   // (358)  01  IERS7320FS REDEFINES RS7320FI.                               
		Edit e1 = declare.level(03).edit() ;                        // (359)      03 FILLER                PIC X(102).
		Edit e1RJ = declare.level(03).justifyRight().edit() ;        // (360)      03 FILLER                OCCURS 14 TIMES.
		Edit e1occ = declare.level(03).editOccurs(5, "occ") ;                        // (359)      03 FILLER                PIC X(102).
			Edit e1Item = declare.level(5).justifyRight().edit() ;                        // (359)      03 FILLER                PIC X(102).
	
	
	Var ts_Zone = declare.level(1).var() ;
		Var v1 = declare.level(5).picX(5).var() ;
		Var v2 = declare.level(5).picX(12).var() ;
		Var v3 = declare.level(5).picX(10).var() ;
		Var v1RJ = declare.level(5).picX(10).justifyRight().var() ;

	public void procedureDivision()
	{
		setAssertActive(true);
		
		move("abcd", v1);
		assertIfFalse(v1.getString().equals("abcd "));

		move(v1, v2);
		assertIfFalse(v2.getString().equals("abcd        "));

		move(v1, v1RJ);
		assertIfFalse(v1RJ.getString().equals("     abcd "));
		
		move("012345678", v2);
		move(v2, v1RJ);
		assertIfFalse(v1RJ.getString().equals("2345678   "));

		move("0123456789", v2);
		move(v2, v1RJ);
		assertIfFalse(v1RJ.getString().equals("23456789  "));

		move("0123456789abc", v2);
		assertIfFalse(v2.getString().equals("0123456789ab"));
		move(v2, v1RJ);
		assertIfFalse(v1RJ.getString().equals("23456789ab"));
		
		
		move("012345678", v3);
		assertIfFalse(v3.getString().equals("012345678 "));
		move(v3, v1RJ);
		assertIfFalse(v1RJ.getString().equals("012345678 "));

		move("0123456789", v3);
		assertIfFalse(v3.getString().equals("0123456789"));
		move(v3, v1RJ);
		assertIfFalse(v1RJ.getString().equals("0123456789"));

		move("0123456789a", v3);
		assertIfFalse(v3.getString().equals("0123456789"));
		move(v3, v1RJ);
		assertIfFalse(v1RJ.getString().equals("0123456789"));
		
		// On editInMapRedefine
		move("abcd", v1);
		assertIfFalse(v1.getString().equals("abcd "));
		
		move(v1, e1RJ);
		assertIfFalse(e1RJ.getString().equals("     abcd "));
		
		move("012345678", v2);
		move(v2, e1RJ);
		assertIfFalse(e1RJ.getString().equals("2345678   "));

		move("0123456789", v2);
		move(v2, e1RJ);
		assertIfFalse(e1RJ.getString().equals("23456789  "));

		move("0123456789abc", v2);
		assertIfFalse(v2.getString().equals("0123456789ab"));
		move(v2, e1RJ);
		assertIfFalse(e1RJ.getString().equals("23456789ab"));

		for(int i=1; i<=5; i++)
		{
			Edit eRJ = e1Item.getAt(i);
			move("abcd", v1);
			assertIfFalse(v1.getString().equals("abcd "));
			
			move(v1, eRJ);
			assertIfFalse(eRJ.getString().equals("     abcd "));
			
			move("012345678", v2);
			move(v2, eRJ);
			assertIfFalse(eRJ.getString().equals("2345678   "));
	
			move("0123456789", v2);
			move(v2, eRJ);
			assertIfFalse(eRJ.getString().equals("23456789  "));
	
			move("0123456789abc", v2);
			assertIfFalse(v2.getString().equals("0123456789ab"));
			move(v2, eRJ);
			assertIfFalse(eRJ.getString().equals("23456789ab"));
		}

		CESM.returnTrans();
	}
}
