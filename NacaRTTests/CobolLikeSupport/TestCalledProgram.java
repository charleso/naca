/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 11 janv. 05
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
import nacaLib.varEx.*;
import nacaLib.callPrg.CalledProgram;
import nacaLib.program.*;

public class TestCalledProgram extends CalledProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
		Var TTTT = declare.level(1).var();
			Var TTTT02 = declare.level(2).pic9(7).var();
			Var WCounter = declare.level(2).pic9(2).value(1).var();
			
//	TestCallAndLinkCopy copy = TestCallAndLinkCopy.Copy(this);
		
	DataSection LinkageSection = declare.linkageSection();
			

		Var LS = declare.level(1).var();
			Var LSNumResult = declare.level(2).pic9(4).var();
			Var LSNum1 = declare.level(2).pic9(2).var();
			Var LSNum2 = declare.level(2).pic9(3).var();
		
			
		Var LSVoid2 = declare.level(1).pic9(50).var();
		Var LSDivider = declare.level(1).pic9(2).var();
		Var LSLen = declare.level(1).pic9(4).var();
		
		Var varRedefLS = declare.level(1).redefines(LS).var();

		Var LSDFHCOMMAREA = declare.level(1).var();
			Var LSCommand = declare.level(2).pic9(2).var();
				Var LSJJ = declare.level(2).pic9(2).var();
				Var LSMM = declare.level(2).pic9(2).var();
				Var LSAAAA = declare.level(2).pic9(4).var();
				Var LSOutputDateJJMMAAAA = declare.level(2).picX(10).var();
				Var LSerreur_Zone = declare.level(2).var() ;                              // (152) 02 ERREUR-ZONE.
					Var LSerr_Cdproj = declare.level(5).picX(2).var() ;                   // (153) 05 ERR-CDPROJ         PIC  XX.
					Var LSerr_Cdstini = declare.level(5).picX(3).var() ;                  // (154) 05 ERR-CDSTINI        PIC  XXX.
					Var LSerr_Cdappli = declare.level(5).picX(2).var() ;                  // (155) 05 ERR-CDAPPLI        PIC  XX.
		
		Var varArray = declare.level(1).var();
			Var varItem = declare.level(5).occurs(10).var();
				Var varEntry = declare.level(10).picX(10).var();
				
	ParamDeclaration param = declare.using(LSDFHCOMMAREA).using(LS).using(LSDivider).using(LSLen);	// Call parameters declaration
	//ParamDeclaration param = declare.using(LS);	// Call parameters declaration
	
	Paragraph Main = new Paragraph(this){public void run(){Main();}};void Main()
	{
		//String cs = varEntry.getAt(50).getString();	// out of bound exception
		
//		move(10, copy.X1);
//		move(12, copy.X2);
//		TestStaticExt.exchange(copy);
//		assertIfFalse(copy.X1.equals(12));
//		assertIfFalse(copy.X2.equals(10));

		
		// Check implicitly used commarea
		long lId = Thread.currentThread().getId();
		if((lId % 2) == 0)
		{
			assertIfFalse(isEqual(LSCommand, 40));
			assertIfFalse(isEqual(LSOutputDateJJMMAAAA, "11/11/1963"));

			move("12/12/1964", LSOutputDateJJMMAAAA);
			move(41, LSCommand);
		}
		else
		{
			assertIfFalse(isEqual(LSCommand, 42));
			assertIfFalse(isEqual(LSOutputDateJJMMAAAA, "26/04/1966"));

			move("27/05/1967", LSOutputDateJJMMAAAA);
			move(43, LSCommand);
		}
		
		
		assertIfFalse(isEqual(LSLen, 4));
		inc(LSLen);
		assertIfFalse(isEqual(LSLen, 5));
		
		assertIfFalse(isEqual(LSDivider, 2));
		inc(LSDivider);	// Unchanged on caller, as it is passed by value, not by reference
		assertIfFalse(isEqual(LSDivider, 3));
		
		compute(multiply(LSNum1, LSNum2), LSNumResult);			// LSNumResult = LSNum1 * LSNum2 		
		compute(divide(LSNumResult, LSDivider), LSNumResult); 	// LSNumResult = LSNumResult / LSDivider
		compute(add(LSNumResult, LSLen), LSNumResult);		// LSNumResult = LSNumResult + WCounter
				
		inc(LSNum1);	// Changed on caller, as it is passed by reference
		inc(LSNum2);	// Changed on caller, as it is passed by reference
		
		exitProgram();	// Exit as we are in a called program
		
		// Dead code: Never executed
		assertIfFalse(false);
		
		inc(LSNum1);
		inc(LSNum2);
	}
	
}

