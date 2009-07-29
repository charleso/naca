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
 * Created on 13 déc. 04
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

public class TestMapRedefines extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();

	TestMapRedefinesMap map = TestMapRedefinesMap.Copy(this) ;
			
			
	TestMapRedefinesMap mapDest = TestMapRedefinesMap.Copy(this) ;
	MapRedefine MapRedefined2 = declare.level(1).redefinesMap(mapDest.MainForm);              // (206) 01  RS0501OCCI REDEFINES RS0501DI.
		Edit MapRedefined2$arr = declare.level(5).editOccurs(4, "row");
			Edit edit2A1 = declare.level(10).pic("ZZZZZ").edit();		// <-> E0
//			Edit edit2E1 = declare.level(5).edit();		// <-> E1 skipped
//			Edit edit2E2 = declare.level(5).edit();		// <-> E2
//			Edit Skip2E3 = declare.level(5).edit();		// E3 skipped
		
		Edit MapRedefined2$arr1 = declare.level(5).editOccurs(2, "row");
			Edit MapRedefined2$E40 = declare.level(6).editSkip();			//E40 / E41 skipped
			Edit MapRedefined2$IJ = declare.level(6).editOccurs(4, "col");
				Edit MapRedefined2$itemI = declare.level(7).edit();		//Edit item1 = _07().edit("I");		// 0: fNom1; 1: fNom2
				Edit MapRedefined2$itemJ = declare.level(7).edit();		// 0: fNom1; 1: fNom2
			Edit MapRedefined2$itemK = declare.level(6).edit();		// 0: fNom1; 1: fNom2
		Edit MapRedefined2$L = declare.level(5).edit();		// 0: fNom1; 1: fNom2
		


	Var varTemp = declare.level(77).picX(480).var();
	Var varGroup = declare.level(1).var();
		Var varJJ = declare.level(2).picX(2).var();
		Var varMM = declare.level(2).pic9(2).var();
		Var varAA = declare.level(2).pic9(2).var();
		
	TestMapRedefinesMap mapDest2 = TestMapRedefinesMap.Copy(this) ;
	
	Var vBuffer = declare.variable().picX(16).var();
	
	Var vSource = declare.level(1).var();
		Var vSource1 = declare.level(5).picX(10).var();
		Var vSource2 = declare.level(5).picX(10).var();
	
	Var vRedefA = declare.level(1).redefines(vSource).var();
		Var vRedefA1 = declare.level(5).picX(12).var();
	
	Var vRedefB = declare.level(1).redefines(vSource).var();
		Var vRedefB1 = declare.level(5).picX(12).var();
		Var vRedefB2 = declare.level(5).picX(5).var();
	
	Var vRedefC = declare.level(1).redefines(vSource).var();
		Var vRedefC1 = declare.level(5).picX(8).var();

	Var vNext  = declare.level(1).picX(5).var();
	Var vNumber  = declare.level(1).picS9(5).comp3().var();
	Var vNumber7  = declare.level(1).picS9(7).comp3().var();
	Var vNumberZ = declare.level(1).pic("ZZZZZ").var();

	
	MapRedefine MapRedefined1 = declare.level(1).redefinesMap(map.MainForm);              // (206) 01  RS0501OCCI REDEFINES RS0501DI.
		//Edit Fake = declare.level(5).edit();		// <-> E0
		Edit editA1 = declare.level(5).edit();		// <-> E0	// pic("ZZZZ").
		Edit Skip01 = declare.level(5).editSkip(1);		// <-> E1 skipped
		Edit editB = declare.level(5).edit();		// <-> E2
		Edit Skip02 = declare.level(5).editSkip(1);		// E3 skipped
		
		Edit arr1 = declare.level(5).editOccurs(2, "row");
			Edit SkipItem0 = declare.level(6).editSkip();			//E40 / E41 skipped
			Edit arr2 = declare.level(6).editOccurs(4, "col");
				Edit itemI = declare.level(7).editSkip();		//Edit item1 = _07().edit("I");		// 0: fNom1; 1: fNom2
				Edit itemJ = declare.level(7).edit();		// 0: fNom1; 1: fNom2
			Edit itemK = declare.level(6).editSkip();		// 0: fNom1; 1: fNom2
		Edit L = declare.level(5).edit();		// 0: fNom1; 1: fNom2
		Edit fGroup = declare.level(5).edit();	
			Var vGroupItem1 = declare.level(6).picX(5).var();
			Var vGroupItem2 = declare.level(6).picX(5).var();
	
	public void procedureDivision()
	{
		setAssertActive(true);

		move(vNumber, varJJ);
		
		move(vNumber, editA1);
		
		Edit e1 = edit2A1.getAt(1);
		Edit e2 = edit2A1.getAt(2);
		String cs2 = e2.toString();
		String cs1 = e1.toString();

		
		cs2 = e2.getString();
		cs1 = e1.getString();
		
		move(789, vNumber7);
		move(vNumber7, vNumberZ);
		move(vNumber7, e2);
		
		cs2= e2.getString();
		cs1 = e1.getString();
		
		moveZero(vNumberZ);
		assertIfFalse(vNumberZ.getString().equals("     "));
		moveZero(e2);

		moveSpace(vNumberZ);
		assertIfFalse(vNumberZ.getString().equals("     "));
		moveSpace(e2);

		moveLowValue(vNumberZ);
		//assertIfFalse(vNumberZ.getString().equals("     "));
		moveLowValue(e2);
		
		moveHighValue(vNumberZ);
		//assertIfFalse(vNumberZ.getString().equals("     "));
		moveHighValue(e2);

		move(123, vNumber);		
		move(vNumber, vNumberZ);
		
		
		move(vNumber, edit2A1.getAt(1));

		e2 = edit2A1.getAt(2);	
		
		move("toto", editA1);
		move("tutu", map.E0);
		
		move("var", vNext);
		move(vNext, editA1);
		
		move(editA1, vNext);
		move(map.E0, vNext);
		
		move(12, editA1);
		move(13, map.E0);
		move(12, editA1);
		
		perform(Test1);
		perform(TestEditAsGroup);
		perform(TestEditToVarToEdit);
		perform(TestFormToVarToForm);
		perform(TestFormToForm);

		//perform(TestWithSkip);
		//perform(TestWithoutSkip);
		
		CESM.returnTrans();
	}
	
	Paragraph Test1 = new Paragraph(this){public void run(){Test1();}};void Test1()
	{
		move("editA1", editA1);
		String cs = map.MainForm.toString();
		
		assertIfFalse(editA1.getString().startsWith("editA"));
		assertIfFalse(map.E0.getString().startsWith("editA"));
	}

	Paragraph TestEditAsGroup = new Paragraph(this){public void run(){TestEditAsGroup();}};void TestEditAsGroup()
	{
		// map.E0;
		move("0123456789", map.fGroup);
		assertIfDifferent(vGroupItem1.getString(), "01234");
		assertIfDifferent(vGroupItem2.getString(), "56789");
		
		//move("abcde", map.vGroupItem2);
		//assertIfDifferent(map.fGroup.getString(), "01234abcde");
	}
	
	
	Paragraph TestEditToVarToEdit = new Paragraph(this){public void run(){TestEditToVarToEdit();}};void TestEditToVarToEdit()
	{
		// Fill group through it's members
		// Partial fill
		move("12345", varGroup);
		assertIfFalse(varGroup.getString().startsWith("12345"));
		assertIfDifferent(varJJ.getString(), "12");
		assertIfDifferent(varMM.getString(), "34");
		assertIfFalse(varAA.getString().startsWith("5"));


		move("18", varJJ);
		move("08", varMM);
		move("86", varAA);
		assertIfDifferent(varGroup.getString(), "180886");
		moveSpace(editA1);
		move(varGroup, editA1);
		assertIfDifferent(editA1.getValue(), "18088");
		
		// Move from Edit to Var and Var to Edit 
		
		// Edit to Var
		move("1234567890", editA1);	// Enconding a small length value
		String cs = editA1.toString();
		assertIfDifferent(editA1.getValue(), "12345");
		
		// debug
		move("0123456789", map.fGroup);
		cs = map.fGroup.toString();
		assertIfDifferent(map.fGroup.getValue(), "0123456789");
		// end debug
		
		move(map.E0, varTemp);
		assertIfDifferent("12345                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           ", varTemp);
		
		// Erase edit's old value
		move("9876543210", editA1);
				
		// Var to another Edit
		move(varTemp, mapDest.E0);
		assertIfDifferent("12345", mapDest.E0); 
		
		// Group to Edit
		// Edit to Group
		move("", varGroup);
//		assertIfDifferent(varJJ.getString(), "  ");
//		assertIfDifferent(varMM.getString(), "  ");
//		assertIfDifferent(varAA.getString(), "  ");
		move(editA1, varGroup);
		assertIfDifferent(varGroup.getString(), "98765 ");	
		assertIfDifferent(varJJ.getString(), "98");
		assertIfDifferent(varMM.getString(), "76");
		assertIfDifferent(varAA.getString(), "5 ");

		// empty group
		move("", varGroup);
		assertIfDifferent(varGroup.getString(), "      ");
		move(varGroup, editA1);
		assertIfDifferent(editA1.getValue(), "     ");	
	}

	Paragraph TestFormToVarToForm = new Paragraph(this){public void run(){TestFormToVarToForm();}};void TestFormToVarToForm()
	{
		String cs = map.MainForm.toString();
		
		// Test the Form to Var to Form moves
		move("abcdefghijklmnopqrstuvwxyz", editA1);		// Fill a map redefined edit with a too long length value
		assertIfDifferent(editA1.getValue(), "abcde");
		
		move("E1 in copy", map.E1);	// Fill a map field with a too short length value
		assertIfDifferent(map.E1.getValue(), "E1 in");
		
		// Move the map in a var
		String s = map.MainForm.getString();
		move(MapRedefined2, varTemp);
		move(map.MainForm, varTemp);
		move(varTemp, MapRedefined2);
		move(varTemp, map.MainForm);
		
		
		// Erase intial fields
		moveSpace(editA1);
		moveSpace(map.E1);
		assertIfDifferent(editA1.getValue(), "     ");
		assertIfDifferent(map.E1.getValue(), "     ");
		
		// Move the var in another map intance
		moveSpace(mapDest.MainForm);
		move(varTemp, mapDest.MainForm);	
			   	
		// Check the values
		assertIfDifferent(mapDest.E0.getValue(), "abcde");	// Keep only the 16th first chars, as the value was too long
		//assertIfDifferent(mapDest.E1.getValue(), "E1 in copy");		// string is trucated on right
	}
	
	Paragraph TestFormToForm = new Paragraph(this){public void run(){TestFormToForm();}};void TestFormToForm()
	{
		// Form to Form
		assertIfDifferent(mapDest.E0.getValue(), "abcde");	// Keep only the 16th first chars, as the value was too long
		move(mapDest.MainForm, mapDest2.MainForm);
		assertIfDifferent(mapDest2.E0.getValue(), "abcde");	// Keep only the 16th first chars, as the value was too long
		
		// Edit to Edit
		move("1234567890123456", mapDest.E0);
		move(mapDest.E0, mapDest2.E0);
		assertIfDifferent(mapDest.E0.getValue(), "12345");
		
		// Var to Edit
		move("ABCDEFGHIJKLMNOP", vBuffer);
		move(vBuffer, mapDest.E0);
		assertIfDifferent(mapDest.E0.getValue(), "ABCDE");	

		// Edit to Var
		moveSpace(vBuffer);
		move(mapDest.E0, vBuffer);
		assertIfDifferent(vBuffer.getString(), "ABCDE           ");	
	}



	Paragraph TestWithSkip = new Paragraph(this){public void run(){TestWithSkip();}};void TestWithSkip()
	{
		Edit e = null;				
	
		assertIfNotNull(SkipItem0);
		assertIfNotNull(Skip02);
		assertIfNotNull(itemI);
		assertIfNotNull(itemK);

		e = itemJ.getAt(1, 1);
//		assertIfDifferent(e.getFullName(), "J11");
		moveSpace(e);
		e.set("j11");
		assertIfFalse(isEqual(e, "j11       "));
		 
		e = itemJ.getAt(1, 2);
//		assertIfDifferent(e.getFullName(), "J12");

		e = itemJ.getAt(1, 3);
//		assertIfDifferent(e.getFullName(), "J13");
		
		e = itemJ.getAt(1, 4);
//		assertIfDifferent(e.getFullName(), "J14");
		
		e = itemJ.getAt(2, 1);
//		assertIfDifferent(e.getFullName(), "J21");
		
		e = itemJ.getAt(2, 2);		
//		assertIfDifferent(e.getFullName(), "J22");
		
		e = itemJ.getAt(2, 3);
//		assertIfDifferent(e.getFullName(), "J23");
		
		e = itemJ.getAt(2, 4);
//		assertIfDifferent(e.getFullName(), "J24");
	}
	
	Paragraph TestWithoutSkip = new Paragraph(this){public void run(){TestWithoutSkip();}};void TestWithoutSkip()
	{
		Edit e = null;
		
		assertIfNull(MapRedefined2$arr1);
		
		e = MapRedefined2$itemI.getAt(1, 1);
//		assertIfDifferent(e.getFullName(), "I11");
		
		e = MapRedefined2$itemI.getAt(1, 2);
//		assertIfDifferent(e.getFullName(), "I12");
		
//		e = MapRedefined2$itemI.getAt(1, 3);
//		assertIfDifferent(e.getFullName(), "I13");
//		
//		e = MapRedefined2$itemI.getAt(1, 4);
//		assertIfDifferent(e.getFullName(), "I14");
//		
//		e = MapRedefined2$itemI.getAt(2, 1);
//		assertIfDifferent(e.getFullName(), "I21");
//		
//		e = MapRedefined2$itemI.getAt(2, 2);
//		assertIfDifferent(e.getFullName(), "I22");
//		
//		e = MapRedefined2$itemI.getAt(2, 3);
//		assertIfDifferent(e.getFullName(), "I23");
//		
//		e = MapRedefined2$itemI.getAt(2, 4);
//		assertIfDifferent(e.getFullName(), "I24");
//	
//	
//	
//		e = MapRedefined2$itemJ.getAt(1, 1);
//		assertIfDifferent(e.getFullName(), "J11");
//		//assertIfDifferent(e.getValue(), "j11");
//		 
//		e = MapRedefined2$itemJ.getAt(1, 2);
//		assertIfDifferent(e.getFullName(), "J12");
//
//		e = MapRedefined2$itemJ.getAt(1, 3);
//		assertIfDifferent(e.getFullName(), "J13");
//		
//		e = MapRedefined2$itemJ.getAt(1, 4);
//		assertIfDifferent(e.getFullName(), "J14");
//		
//		e = MapRedefined2$itemJ.getAt(2, 1);
//		assertIfDifferent(e.getFullName(), "J21");
//		
//		e = MapRedefined2$itemJ.getAt(2, 2);
//		assertIfDifferent(e.getFullName(), "J22");
//		
//		e = MapRedefined2$itemJ.getAt(2, 3);
//		assertIfDifferent(e.getFullName(), "J23");
//		
//		e = MapRedefined2$itemJ.getAt(2, 4);
//		assertIfDifferent(e.getFullName(), "J24");
//		
//		
//		e = MapRedefined2$itemK.getAt(1);
//		assertIfDifferent(e.getFullName(), "fNom10");
//
//		e = MapRedefined2$itemK.getAt(2);
//		assertIfDifferent(e.getFullName(), "fNom19");
		
		
//		assertIfDifferent(MapRedefined2$L.getFullName(), "fNom20");
	}
}                                                                                                                                                 
