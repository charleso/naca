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

import java.io.InputStream;

import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.varEx.*;
import nacaLib.batchPrgEnv.BatchProgram;
import nacaLib.program.*;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestCall extends BatchProgram 
{
	nacaLib.varEx.DataSection WorkingStorage = declare.workingStorageSection();
	
	// For called program
	Var WFill = declare.level(1).picX(10).var();
	Var WNumbers = declare.level(1).var();
		TestCalledCopy testCalledCopy = TestCalledCopy.Copy(this, replacing(5, 10).replacing(10, 15)) ;
		Var WInternal = declare.level(11).picX(10).var();

	TestMapScreen ScreenDefFile = TestMapScreen.Copy(this);
		
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
//			Edit editZ = declare.level(5).picX(5).edit();
//			Edit editNotZ = declare.level(5).picX(5).edit();

		
	public void procedureDivision()
	{
		setAssertActive(true);
				
		call("TestCalledWithString")
			.usingValue("toto")
			.executeCall();
		perform(TestsCall);
		perform(TestsCall2);
		//perform(TestCallWithEdit);
		
		
		
		CESM.returnTrans();
	}
	
	Paragraph TestCallWithEdit = new Paragraph(this){public void run(){TestCallWithEdit();}};void TestCallWithEdit()
	{
		move("12345678901234567890", editNom);
		
		call(TestCalledWithEdit.class)
			.using(editNom)		// Explicitly used in the called program !
			.executeCall();
		
		assertIfFalse(isEqual(editNom, "98765432109876543210"));

	}

	Paragraph TestsCall = new Paragraph(this){public void run(){TestsCall();}};void TestsCall()
	{
		assertIfDifferent(0, testCalledCopy.WNumResult);
		move(10, testCalledCopy.WNum1);
		move(21, testCalledCopy.WNum2);
		call(TestCalled.class)
			.using(testCalledCopy.WRoot)		// Explicitly used in the called program !
			.executeCall();
		
		assertIfFalse(isEqual(testCalledCopy.WNumResult, 31));	// ((10*20)/3) + 5
	}
	
	Paragraph TestsCall2 = new Paragraph(this){public void run(){TestsCall2();}};void TestsCall2()
	{
		move(10, testCalledCopy.WNum1);
		move(21, testCalledCopy.WNum2);
		call(TestCalled.class)
			.using(testCalledCopy.WRoot)		// Explicitly used in the called program !
			.executeCall();
		
		assertIfFalse(isEqual(testCalledCopy.WNumResult, 31));	// ((10*20)/3) + 5
	}
}
