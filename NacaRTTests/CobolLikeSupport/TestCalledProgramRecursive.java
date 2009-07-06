/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import nacaLib.callPrg.CalledProgram;
import nacaLib.program.Paragraph;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.ParamDeclaration;
import nacaLib.varEx.Var;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestCalledProgramRecursive extends CalledProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
		Var Count = declare.level(1).pic9(2).value(3).var();

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
			Var Broot81 = declare.level(2).picX(4).value("0123").var();
		
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
			
					
//			Var twazone = declare.level(1).var() ;                                         
//				TestWorkingTWACom twaecom = TestWorkingTWACom.Copy(this) ; 
//				
//			Var twazone_R = declare.level(1).redefines(twazone).var() ;                 
//				Var twainp1 = declare.level(2).picX(1).var() ;                         
//				Var twaout1 = declare.level(2).picX(1).var() ;  
//				Var twaout2 = declare.level(2).picX(1).var() ;    
//				Var filler$34 = declare.level(2).picX(85).var() ;
				
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
		
	DataSection LinkageSection = declare.linkageSection();
		Var LSCount = declare.level(1).pic9(2).var();
		
	ParamDeclaration param = declare.using(LSCount);	// Call parameters declaration
	
	Paragraph Main = new Paragraph(this){public void run(){Main();}};void Main()
	{
//		if(isEqual(LSCount, 0))
//			assertIfFalse(LSCount.getSemanticContextValue().equals("SEM_WDivider_ORG_0"));
//		else if(isEqual(LSCount, 1))
//			assertIfFalse(LSCount.getSemanticContextValue().equals("SEM_WDivider_ORG_1"));
//		else if(isEqual(LSCount, 2))
//			assertIfFalse(LSCount.getSemanticContextValue().equals("SEM_WDivider_ORG_2"));
//		else if(isEqual(LSCount, 3))
//			assertIfFalse(LSCount.getSemanticContextValue().equals("SEM_WDivider_ORG_3"));
		
		if(isEqual(LSCount, 0))
			move(3, LSCount);
		if(isGreater(LSCount, 1))
		{
			dec(LSCount);
//			if(isEqual(LSCount, 0))
//				setSemanticContextValue(LSCount, "SEM_WDivider_ORG_0");
//			else if(isEqual(LSCount, 1))
//				setSemanticContextValue(LSCount, "SEM_WDivider_ORG_1");
//			else if(isEqual(LSCount, 2))
//				setSemanticContextValue(LSCount, "SEM_WDivider_ORG_2");
//			else if(isEqual(LSCount, 3))
//				setSemanticContextValue(LSCount, "SEM_WDivider_ORG_3");
			
			move(LSCount, Count);
			call(TestCalledProgramRecursive.class)	// Recursive call !
				.using(Count)
				.executeCall();	
		}			
		int n = 0;
	}
}
