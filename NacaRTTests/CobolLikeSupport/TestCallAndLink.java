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
import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.varEx.*;
import nacaLib.program.*;

public class TestCallAndLink extends OnlineProgram
{
//	public TestCallAndLink(ProgramArgument prgArgs)
//	{
//		super(prgArgs);
//	}
	//DataSection WorkingStorage = declare.workingStorageSection();

	// For linked program
	Var vCommarea = declare.level(1).var();
		Var Command = declare.level(2).pic9(2).var();
		Var JJ = declare.level(2).pic9(2).var();
		Var MM = declare.level(2).pic9(2).var();
		Var AAAA = declare.level(2).pic9(4).var();
		Var OutputDateJJMMAAAA = declare.level(2).picX(10).var();
		Var erreur_Zone = declare.level(2).var() ;                              // (152) 02 ERREUR-ZONE.
			Var err_Cdproj = declare.level(5).picX(2).var() ;                   // (153) 05 ERR-CDPROJ         PIC  XX.
			Var err_Cdstini = declare.level(5).picX(3).var() ;                  // (154) 05 ERR-CDSTINI        PIC  XXX.
			Var err_Cdappli = declare.level(5).picX(2).var() ;                  // (155) 05 ERR-CDAPPLI        PIC  XX.
			
	Var vCommareaAlt = declare.level(1).var();
			Var CommandAlt = declare.level(2).pic9(2).var();
			Var JJAlt = declare.level(2).pic9(2).var();
			Var MMAlt = declare.level(2).pic9(2).var();
			Var AAAAAlt = declare.level(2).pic9(4).var();
			Var OutputDateJJMMAAAAAlt = declare.level(2).picX(10).var();
			Var erreur_ZoneAlt = declare.level(2).var() ;                              // (152) 02 ERREUR-ZONE.
				Var err_CdprojAlt = declare.level(5).picX(2).var() ;                   // (153) 05 ERR-CDPROJ         PIC  XX.
				Var err_CdstiniAlt = declare.level(5).picX(3).var() ;                  // (154) 05 ERR-CDSTINI        PIC  XXX.
				Var err_CdappliAlt = declare.level(5).picX(2).var() ;                  // (155) 05 ERR-CDAPPLI        PIC  XX.

	int n =0 ;
	String cs = "titi";

	// For called program
	Var WNumbers = declare.level(1).var();
		Var WResult = declare.level(2).pic9(4).var();
		Var WV1 = declare.level(2).pic9(2).var();
		Var WV2 = declare.level(2).pic9(3).var();
	Var WDivider = declare.level(1).pic9(2).var();
	
	Var vCommarea2 = declare.level(1).picX(256).var();
	
	Var X1 = declare.level(2).picX(2).var();
	Var X2 = declare.level(2).picX(2).var();
	
	Var wk_Libmess = declare.level(1).picX(80).valueSpaces().var() ;
	Var wk_Debmess = declare.level(1).picX(80).valueSpaces().var() ;            // (357)  01  WK-DEBMESS              PIC X(80)   VALUE SPACE.
	Var wk_Separ = declare.level(1).picX(1).valueSpaces().var() ;               // (361)  01  WK-SEPAR                PIC X       VALUE SPACE.
	Var longueur = declare.level(1).pic9(5).value(0).var() ;                    // (369)  01  LONGUEUR                PIC 9(5)    VALUE 0.
	Var nombre = declare.level(1).pic9(2).comp().value(0).var() ;               // (368)  01  NOMBRE                  PIC 99 COMP VALUE 0.
	Var pointeur = declare.level(1).pic9(2).comp().value(0).var() ;             // (367)  01  POINTEUR                PIC 99 COMP VALUE 0.
	
	//TestCallAndLinkCopy copy = TestCallAndLinkCopy.Copy(this, replacing(1,1));
		

	public void procedureDivision()
	{
		setAssertActive(true);
		
		//CESM.start(TestCicsLinkedProgram3.class).dataFrom(vCommarea).doStart();		
		
		move("IMMAT (%LFTPBKUP%) &ED&/&IMMRUN&/&FTPBKUP&#                  000", wk_Libmess);
		if (unstring(wk_Libmess)
			.delimitedBy("&")
			.delimitedBy("%")          // (914)         UNSTRING WK-LIBMESS DELIMITED BY '&' OR '%' OR '#'        
			.delimitedBy("#")
			.withPointer(pointeur)
			.tallying(nombre)
			.to(wk_Debmess, wk_Separ, longueur)

			
			.failed()) 
		{
							                                                            // (915)             INTO         WK-DEBMESS                               
							                                                            // (916)             DELIMITER IN WK-SEPAR                                 
							                                                            // (917)             COUNT IN     LONGUEUR                                 
							                                                            // (918)             WITH POINTER POINTEUR                                 
							                                                            // (919)             TALLYING IN  NOMBRE                                   
							int gg = 0;                                          // (920)             ON OVERFLOW  MOVE 'V' TO SW-FIN
		}
		
//		move(10, X1);
//		move(12, X2);
//		
//		TestStaticExt.exchange(X1, X2);
//		assertIfFalse(X1.equals(12));
//		assertIfFalse(X2.equals(10));
		

		//move(10, copy.X1);
//		move(12, copy.X2);
//		TestStaticExt.exchange(copy);
//		assertIfFalse(copy.X1.equals(12));
//		assertIfFalse(copy.X2.equals(10));
		
		
		//assertIfFalse(OutputDateJJMMAAAA.getSemanticContextValue() == null);
		//assertIfFalse(Command.getSemanticContextValue() == null);
		//assertIfFalse(WDivider.getSemanticContextValue() == null);
		
		//setSemanticContextValue(Command, "SEM_Command");
		//setSemanticContextValue(OutputDateJJMMAAAA, "SEM_InputDateJJMMAAAA");
		//setSemanticContextValue(WDivider, "SEM_WDivider");
		
		for(int n=0; n<2; n++)
		{
			perform(TestCall);
		}
		
		perform(TestLink);
		perform(TestCallRecursive);

		CESM.returnTrans();
	}
	
	Paragraph TestCallRecursive = new Paragraph(this)
	{
		public void run()
		{
			TestCallRecursive();
		}
	};
	void TestCallRecursive()
	{
		move(0, WDivider);
		call(TestCalledProgramRecursive.class)
		.using(WDivider)
		.executeCall();
	}


	Paragraph TestCall = new Paragraph(this)
	{
		public void run()
		{
			move(10, WV1);
			move(20, WV2);
			move(2, WDivider);
			move(10, WV1);
			move(20, WV2);
			move(2, WDivider);
			
			// Operations done in called program
			// Inc WDivider, but as passed by value, the incremented value is not returned
			// WResult = ((WV1 * WV2) / WDivider) + Length(WResult)+1;
			// Inc WV1
			// Inc WV2
			
			long lId = Thread.currentThread().getId();
			if((lId % 2) == 0)
			{
				move(40, Command);		
				move("11/11/1963", OutputDateJJMMAAAA);	// Fill the commarea
				
				call(TestCalledProgram.class)
				.using(vCommarea)		// Implicity used in the called program !
				.using(WNumbers)		// Explicitly used in the called program !
				.usingValue(WDivider)
				.usingLengthOf(WResult)
				.executeCall();
	
				// Check returned commarea
				assertIfFalse(isEqual(Command, "41"));
				assertIfFalse(isEqual(OutputDateJJMMAAAA, "12/12/1964"));	
			}
			else
			{
				move(42, CommandAlt);
				move("26/04/1966", OutputDateJJMMAAAAAlt);	// Fill the commarea
	
				call(TestCalledProgram.class)
				.using(vCommareaAlt)		// Implicity used in the called program !
				.using(WNumbers)		// Explicitly used in the called program !
				.usingValue(WDivider)
				.usingLengthOf(WResult)
				.executeCall();
	
				// Check returned commarea
				assertIfFalse(isEqual(CommandAlt, "43"));
				assertIfFalse(isEqual(OutputDateJJMMAAAAAlt, "27/05/1967"));	
			}
	
			// check other parameters
			assertIfFalse(isEqual(WResult, 71));	// ((10*20)/3) + 5
			assertIfFalse(isEqual(WDivider, 2));
			assertIfFalse(isEqual(WV1, 11));
			assertIfFalse(isEqual(WV2, 21));
		}
	};

	Paragraph TestLink = new Paragraph(this){public void run(){TestLink();}};void TestLink()
	{		
		move(12, JJ);
		move(3, MM);
		move(2005, AAAA);
		move("A", err_Cdproj);
		move("B", err_Cdstini);
		move("C", err_Cdappli);

		int n=0; 
		while(n<12)
		{
			move(n, Command);	// Format the date
			CESM.link("TestCicsLinkedProgram3").commarea(vCommarea);
			n = Command.getInt();
		}

//		CESM.link("TestCicsLinkedProgram").commarea(vCommarea);
//		
//		assertIfFalse(isEqual(OutputDateJJMMAAAA, "12/03/2005"));
//		assertIfFalse(isEqual(Command, 1));	// It is not incremented in the linked program
//
//		move("abcdefg", vCommarea2);
//		CESM.link("TestCicsLinkedProgram2").commarea(vCommarea2);
	
	}	

}
