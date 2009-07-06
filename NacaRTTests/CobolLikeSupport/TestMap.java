/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */

/**
 * @author PJD
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.varEx.*;
import nacaLib.program.*;

public class TestMap extends OnlineProgram
{
	DataSection WS = declare.workingStorageSection() ;

		Var vIndex = declare.variable().picS9(2).var();
				
		public TestMapScreen ScreenDefFile = TestMapScreen.Copy(this);
		
		MapRedefine MAP = declare.level(1).redefinesMap(ScreenDefFile.ScreenDef);
			Edit editNom = declare.level(5).edit();		// <-> editNom
			Edit SkippedCompany = declare.level(5).editSkip(1);		// editCompany skipped
			Edit editPassword = declare.level(5).edit();		// <-> editPrenom
			Edit editBirthDate = declare.level(5).edit();		// <-> editPrenom
				Var wJJ = declare.level(10).pic9(2).var();
				Var wfiller_1 = declare.level(10).picX().value("/").filler();
				Var wMM = declare.level(10).pic9(2).var();
				Var wfiller_2 = declare.level(10).picX().value("/").filler();
				Var wAAAA = declare.level(10).pic9(4).var();
			Edit editLeftBlank = declare.level(5).edit();
			Edit editRightBlank = declare.level(5).edit();
			Edit editLeftZero = declare.level(5).edit();
			Edit editRightZero = declare.level(5).edit();
//			Edit editZ = declare.level(5).pic9("ZZZZZ").edit();
//			Edit editNotZ = declare.level(5).pic9("ZZZZZ").edit();
			
		
		Var FROM_ZONE = declare.level(1).var();
    		Var tooSmallField = declare.level(5).var();
    			Var FILLER = declare.level(10).picX(2000).var();
        	Var F_WORKING = declare.level(5).var();
        		Var XXX = declare.level(10).picX(5).var();
		
		
		Var vBuffer1 = declare.variable().picX(250).var();
		Var vBuffer2 = declare.variable().picX(250).var();
		
		TestMapForm testMapForm = TestMapForm.Copy(this) ;
		TestMapForm testMapForm2 = TestMapForm.Copy(this) ; 
			
		Var vZ5 = declare.level(77).pic("ZZZZZ").var();
		Var vS97C3 = declare.level(77).picS9(7).comp3().var();
	
	Paragraph Main = new Paragraph(this){public void run(){Main();}};void Main()
	{
		move(14, vS97C3);
		move(vS97C3, vZ5);
				
		//move(vS97C3, editZ);
//		String cs = editZ.toString();
//		assertIfFalse(isEqual(editZ, "   14"));
//		assertIfFalse(isEqual(ScreenDefFile.editZ, "   14"));
//		
//		move("00000", editZ);
//		moveZero(editZ);
//		assertIfFalse(isEqual(editZ, "     "));
//		moveZero(editZ);
//		
//		move(315, vS97C3);
//		move(vS97C3, ScreenDefFile.editZ);
//		assertIfFalse(isEqual(editZ, "  315"));
//		assertIfFalse(isEqual(ScreenDefFile.editZ, "  315"));		
//
//		move(421, vS97C3);
//		move(vS97C3, ScreenDefFile.editNotZ);
//		assertIfFalse(isEqual(ScreenDefFile.editNotZ, "  421"));	// Treated as a Z because the edit redefined has a Z format
//		assertIfFalse(isEqual(editNotZ, "  421"));
//				
//		move(987, vS97C3);
//		move(vS97C3, editNotZ);
//		assertIfFalse(isEqual(ScreenDefFile.editNotZ, "  987"));
//		assertIfFalse(isEqual(editNotZ, "  987"));
//		
//		move(1, vIndex);
//		moveSpace(editNom);
//		move(vIndex, editNom);
//		assertIfFalse(isEqual(editNom, "01                  "));
		
		move("toto", vBuffer1);
		move(vBuffer1, editPassword);
		
		perform(Test1);
		perform(TestAttrEncode);		
		
		//perform(TestAttributesFillJustify);
		//perform(TestWithMapForm);
		perform(TestAttrEncode);
	}
	
		//String cs = wJJ.getString();
	Paragraph Test1 = new Paragraph(this){public void run(){Test1();}};void Test1()
	{			
		String cs  = ScreenDefFile.editNom.toString();
		moveSpace(editNom);
		moveSpace(editPassword);
		
		move("A234567890B234567890C2345678", ScreenDefFile.editNom);
		move("Password", ScreenDefFile.editPassword);
		move("C930", ScreenDefFile.editCompany);
		
		move(ScreenDefFile.ScreenDef, tooSmallField);
		int n = 0;
		
		assertIfFalse(isEqual(editNom, "A234567890B234567890"));
		assertIfFalse(isEqual(editPassword, "Password            "));
		assertIfFalse(SkippedCompany == null);
		
		move(12, ScreenDefFile.JJ);
		move(1, ScreenDefFile.MM);
		move(2005, ScreenDefFile.AAAA);
		assertIfFalse(isEqual(ScreenDefFile.editBirthDate, "12/01/2005"));
		
		move(ScreenDefFile.editRightBlank, vBuffer1);
		moveLowValue(ScreenDefFile.editRightBlank);
		move(vBuffer1, ScreenDefFile.editRightBlank);
		
		move(ScreenDefFile.ScreenDef, vBuffer1);
		move(ScreenDefFile.ScreenDef, vBuffer2);
		moveSpace(ScreenDefFile.ScreenDef);
		assertIfFalse(isAll(ScreenDefFile.ScreenDef, CobolConstant.Space));
		
		move(vBuffer1, ScreenDefFile.ScreenDef);
		moveLowValue(vBuffer1);
		assertIfFalse(isAll(vBuffer1, CobolConstant.LowValue));
		
		move(ScreenDefFile.ScreenDef, vBuffer1);
		
		String cs1 = vBuffer1.getString();
		String cs2 = vBuffer2.getString();
		assertIfFalse(cs1.equals(cs2));
	}
	
	Paragraph TestAttributesFillJustify = new Paragraph(this){public void run(){TestAttributesFillJustify();}};void TestAttributesFillJustify()
	{
		move("123456789012345", vBuffer1);
		
		move(vBuffer1, editLeftBlank);
		assertIfFalse(editLeftBlank.equals("123456789012345     "));
		assertIfFalse(ScreenDefFile.editLeftBlank.equals("123456789012345     "));

		move(vBuffer1, editRightBlank);
		assertIfFalse(editRightBlank.equals("123456789012345     "));
		assertIfFalse(ScreenDefFile.editRightBlank.equals("123456789012345     "));

		move(vBuffer1, editLeftZero);
		assertIfFalse(editLeftZero.equals("123456789012345     "));
		assertIfFalse(ScreenDefFile.editLeftZero.equals("123456789012345     "));

		move(vBuffer1, editRightZero);
		assertIfFalse(editRightZero.equals("123456789012345     "));
		assertIfFalse(ScreenDefFile.editRightZero.equals("123456789012345     "));	

		move("abcdefghjiklmno", vBuffer1);
		move(vBuffer1, editLeftBlank);
		move(vBuffer1, editRightBlank);
		move(vBuffer1, editLeftZero);
		move(vBuffer1, editRightZero);
		
		move(ScreenDefFile.ScreenDef, vBuffer1);
		move(CobolConstant.LowValue, ScreenDefFile.ScreenDef);
		move(vBuffer1, ScreenDefFile.ScreenDef);
		
		assertIfFalse(editLeftBlank.equals("abcdefghjiklmno     "));
		assertIfFalse(ScreenDefFile.editLeftBlank.equals("abcdefghjiklmno     "));

		assertIfFalse(editRightBlank.equals("abcdefghjiklmno     "));
		assertIfFalse(ScreenDefFile.editRightBlank.equals("abcdefghjiklmno     "));

		assertIfFalse(editLeftZero.equals("abcdefghjiklmno     "));
		assertIfFalse(ScreenDefFile.editLeftZero.equals("abcdefghjiklmno     "));
		
		assertIfFalse(editRightZero.equals("abcdefghjiklmno     "));
		assertIfFalse(ScreenDefFile.editRightZero.equals("abcdefghjiklmno     "));			
	}

	Paragraph TestAttrEncode = new Paragraph(this){public void run(){TestAttrEncode();}};void TestAttrEncode()
	{
		move(ScreenDefFile.ScreenDef, vBuffer1);
		move(CobolConstant.LowValue, ScreenDefFile.ScreenDef);
		move(vBuffer1, ScreenDefFile.ScreenDef);
		
		int n0 = editRightBlank.getEncodedAttr();
		editRightBlank.setEncodedAttr(n0);
		int n1 = editRightBlank.getEncodedAttr();
		assertIfFalse(n0 == n1);
		
		move(ScreenDefFile.ScreenDef, vBuffer1);
		move(vBuffer1, ScreenDefFile.ScreenDef);
		
		int n2 = editRightBlank.getEncodedAttr();
		assertIfFalse(n0 == n2);
	}

	Paragraph TestWithMapForm = new Paragraph(this){public void run(){TestWithMapForm();}};void TestWithMapForm()
	{
		move(testMapForm.TestMapFormf, vBuffer1);
		testMapForm2.TestMapFormf = testMapForm.TestMapFormf;
		int n = 0;
	}	
}

