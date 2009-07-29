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
import nacaLib.program.*;

public class TestWorkingLevel extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();

//	Var B7 = declare.level(1).var();
//		Var B71 = declare.level(2).picX(4).value("tutu").var();
//
//	Var B8 = declare.level(1).picX(10).var();
//		Var B81 = declare.level(2).picX(4).value("0123").var();
		
	Var TUAZONE = declare.level(1).var();
	Var TUAZONE_A = declare.level(5).picX(1).var();
	Var TUAZONE_B = declare.level(5).picX(255).var();
	Var TUAZONE_B_Redef0 = declare.level(5).redefines(TUAZONE_B).picX(10).value("0123456789").var();
		Var TUAZONE_B_Redef0_Item0 = declare.level(10).picX(1).var();
		Var TUAZONE_B_Redef0_Item1 = declare.level(10).picX(1).var();
	
	Var TUAZONE_B_Redef1 = declare.level(5).redefines(TUAZONE_B).var();
	
	Var TUAZONE_Redef1 = declare.level(1).redefines(TUAZONE).var();

	Var B7 = declare.level(1).var();
		Var B71 = declare.level(2).picX(4).value("tutu").var();

	Var B8 = declare.level(1).picX(10).var();
		Var B81 = declare.level(2).picX(4).value("0123").var();

	
	TestWorkingLevelForm Screen = TestWorkingLevelForm.Copy(this);
		MapRedefine MainFormRedefined = declare.level(1).redefinesMap(Screen.MainForm);
			Edit fGroup1Redefined = declare.level(5).edit();
			Var vGroupItem1 = declare.level(10).picX(5).var();
			Var vGroupItem2 = declare.level(10).var();
				Var vGroupItem21 = declare.level(15).picX(2).var();
				Var vGroupItem22 = declare.level(15).picX(3).var();
				
			Edit fGroup2Redefined = declare.level(5).edit();

	
	public void procedureDivision()
	{
		setAssertActive(true);
		perform(Test1);
		perform(Test2);
		perform(TestInternalMap);
		perform(TestInternalMapRedefine);
		
		CESM.returnTrans();
	}

	Paragraph Test1 = new Paragraph(this){public void run(){Test1();}};void Test1()
	{
		assertIfFalse(B7.getLength() == 4);
		assertIfFalse(B7.equals("tutu"));		
		
		assertIfFalse(B71.getLength() == 4);
		assertIfFalse(B71.equals("tutu"));		

		assertIfFalse(B81.getLength() == 4);	
		assertIfFalse(B81.equals("0123"));			

		assertIfFalse(B8.getLength() == 10);	
		assertIfFalse(subString(B8, 1, 4).equals("0123"));		
	}

	Paragraph Test2 = new Paragraph(this){public void run(){Test2();}};void Test2()
	{
		assertIfFalse(TUAZONE.getLength() == 256);	
		assertIfFalse(TUAZONE_A.getLength() == 1);
		assertIfFalse(TUAZONE_B.getLength() == 255);
		assertIfFalse(TUAZONE_B_Redef0.getLength() == 10);
		assertIfFalse(TUAZONE_B_Redef0_Item0.getLength() == 1);
		assertIfFalse(TUAZONE_B_Redef0_Item1.getLength() == 1);
		
		assertIfFalse(TUAZONE_B_Redef1.getLength() == 255);
		assertIfFalse(TUAZONE_Redef1.getLength() == 256);
	}

	Paragraph TestInternalMap = new Paragraph(this){public void run(){TestInternalMap();}};void TestInternalMap()
	{
		assertIfFalse(Screen.fGroup1.getLength() == 10);
		//assertIfFalse(fGroup1.m_VarManager.m_nAbsoluteStartPosition == 282);

		assertIfFalse(vGroupItem1.getLength() == 5);
		//assertIfFalse(vGroupItem1.m_VarManager.m_nAbsoluteStartPosition == 289);
		
		assertIfFalse(vGroupItem2.getLength() == 5);
		//assertIfFalse(vGroupItem2.m_VarManager.m_nAbsoluteStartPosition == 294);

		assertIfFalse(vGroupItem21.getLength() == 2);
		//assertIfFalse(vGroupItem21.m_VarManager.m_nAbsoluteStartPosition == 294);

		assertIfFalse(vGroupItem22.getLength() == 3);
		//assertIfFalse(vGroupItem22.m_VarManager.m_nAbsoluteStartPosition == 296);
		
		move("1234567890", Screen.fGroup1);
		assertIfFalse(Screen.fGroup1.getString().equals("1234567890"));
		assertIfFalse(vGroupItem1.getString().equals("12345"));
		assertIfFalse(vGroupItem2.getString().equals("67890"));
		
		move("abcde", vGroupItem2);
		assertIfFalse(Screen.fGroup1.getString().equals("12345abcde"));
	}
	
	Paragraph TestInternalMapRedefine = new Paragraph(this){public void run(){TestInternalMapRedefine();}};void TestInternalMapRedefine()
	{
		move("ABCDEFGHIJ", fGroup1Redefined);
		assertIfFalse(Screen.fGroup1.getString().equals("ABCDEFGHIJ"));

		move("12", vGroupItem21);
		assertIfFalse(Screen.fGroup1.getString().equals("ABCDE12HIJ"));

		move("a", vGroupItem2);
		assertIfFalse(Screen.fGroup1.getString().equals("ABCDEa    "));	// Space padded on right, as we wrote in a var, not an edit

		move("qw", fGroup1Redefined);
		assertIfFalse(Screen.fGroup1.getString().equals("qw        "));
		assertIfFalse(vGroupItem1.getString().equals("qw   "));

		move("1234567890abcdef", fGroup1Redefined);
		assertIfFalse(Screen.fGroup1.getString().equals("1234567890"));
		assertIfFalse(vGroupItem1.getString().equals("12345"));
		assertIfFalse(vGroupItem21.getString().equals("67"));
		assertIfFalse(vGroupItem22.getString().equals("890"));

				
		moveSpace(Screen.fGroup2);			// No space padded on the right, as we set the edit
		move("Group", Screen.fGroup2);			// No space padded on the right, as we set the edit 
		assertIfFalse(fGroup2Redefined.getString().equals("Group     "));

		move("TOTO", fGroup2Redefined);			// No space padded on the right, as we set the edit 
		assertIfFalse(Screen.fGroup2.getString().equals("TOTO      "));
		
	}
}
