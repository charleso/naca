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
/**
 * 
 */
/*
 * Created on 14 déc. 04
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

public class TestVarTypes extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	Var Group1 = declare.level(1).var();								// Group
		Var X = declare.level(2).picX(5).var();							// X
		//Var AlphaNumEdited = declare.level(2).picX("XX/XX").var();	//AlphaNumEdited
		Var AlphaNumEdited = declare.level(2).picX(5).var();	//AlphaNumEdited
		Var Num = declare.level(2).pic9(5).var();					// 9
		Var NumEdited = declare.level(2).pic("$$,$$9.99").var();	// NumEdited
			

	TestMapRedefinesMap _TestMapRedefinesMap = TestMapRedefinesMap.Copy(this);
	// _TestMapRedefinesMap.E0	// Edit
				
	Var Group2 = declare.level(1).var();								// Second group
		Var X2 = declare.level(2).picX(10).var();
	
	Var Group3 = declare.level(1).var();								
		Var X4 = declare.level(2).picX(10).var();

	Paragraph Paragraph1 = new Paragraph(this){public void run(){Paragraph1();}};void Paragraph1()
	{
		setAssertActive(true);
		
		perform(moveFromGroup);
		perform(moveFromX);
		perform(moveFrom9);
		perform(moveFromEditNum);
		//perform(moveFromEditAlphaNum);
		perform(moveFromEdit);
		
		CESM.returnTrans();
	}

	Paragraph moveFromGroup = new Paragraph(this){public void run(){moveFromGroup();}};void moveFromGroup()
	{
		move("", Group1);	// Erase all fiels
		
		// AlphaNumerics
		move("abcdefg", Group2);
		assertIfDifferent("abcdefg   ", Group2);

		move(Group2, Group2);
		assertIfDifferent("abcdefg   ", Group2);
		
		move(Group2, X);
		assertIfFalse(X.getString().startsWith("abcde"));

		move(Group2, AlphaNumEdited);
		
		
		moveSpace(_TestMapRedefinesMap.E0);
		move(Group2, _TestMapRedefinesMap.E0);
		assertIfFalse(_TestMapRedefinesMap.E0.getString().startsWith("abcde"));
		
		// Numerics
		move("12345", Group2);
		assertIfDifferent("12345     ", Group2);

		move(Group2, Num);
		assertIfDifferent(12345, Num);

		move(Group2, NumEdited);
		assertIfDifferent("$2,345.00", NumEdited);	
		
		// Group
		move(Group2, Group3);
		assertIfDifferent("12345     ", Group3);
	}
	
	Paragraph moveFromX = new Paragraph(this){public void run(){moveFromX();}};void moveFromX()
	{
		move("", Group1);	// Erase all fiels
		
		// AlphaNumerics
		move("abcdefg", X);
		assertIfFalse(X.equals("abcde"));
		
		move(X, X);
		assertIfFalse(X.equals("abcde"));

		//move(X, AlphaNumEdited);
		//assertIfFalse(AlphaNumEdited.equals("ab/cd"));
		
		move(X, _TestMapRedefinesMap.E0);
		assertIfFalse(_TestMapRedefinesMap.E0.equals("abcde           "));
		
		// Numerics
		move("12345", X);
		assertIfFalse(X.equals("12345"));

		move(X, Num);
		assertIfFalse(Num.equals("12345"));

		move(X, NumEdited);
		assertIfFalse(NumEdited.equals("$2,345.00"));	
		
		// Group
		move(X, Group3);
		assertIfFalse(Group3.getString().startsWith("12345"));
	}
	
	Paragraph moveFrom9 = new Paragraph(this){public void run(){moveFrom9();}};void moveFrom9()
	{
		move("", Group1);	// Erase all fiels

		// Numerics
		move("12345", Num);
		assertIfFalse(Num.equals("12345"));

		move(Num, Num);
		assertIfFalse(Num.equals("12345"));

		move(Num, NumEdited);
		assertIfFalse(NumEdited.equals("$2,345.00"));	
		
		// AlphaNumerics
		move(Num, X);
		assertIfFalse(X.equals("12345"));
		
	//	move(Num, AlphaNumEdited);
		//assertIfFalse(AlphaNumEdited.equals("12/34"));
		
		move(Num, _TestMapRedefinesMap.E0);
		assertIfFalse(_TestMapRedefinesMap.E0.equals("12345           "));
		
		// Group
		move(Num, Group3);
		assertIfFalse(Group3.getString().startsWith("12345"));
	}	
	
	Paragraph moveFromEditNum = new Paragraph(this){public void run(){moveFromEditNum();}};void moveFromEditNum()
	{
		move("", Group1);	// Erase all fiels

		// Numerics
		move("12345", NumEdited);
		assertIfFalse(NumEdited.equals("$2,345.00"));

		move(NumEdited, NumEdited);
		assertIfFalse(NumEdited.equals("$2,345.00"));	// Unchanged

		move(NumEdited, Num);
		assertIfFalse(Num.equals("02345"));		// Unchanged
		
		// AlphaNumerics
		move(NumEdited, X);
		assertIfFalse(X.equals("$2,34"));
		
		//move(NumEdited, AlphaNumEdited);
		//assertIfFalse(AlphaNumEdited.equals("$2/,3"));
		
		move(NumEdited, _TestMapRedefinesMap.E0);
		assertIfFalse(_TestMapRedefinesMap.E0.equals("$2,34"));
		
		// Group
		move(NumEdited, Group3);
		assertIfFalse(Group3.getString().startsWith("$2,345.00"));
	}	
	
//	Paragraph moveFromEditAlphaNum = new Paragraph(this){public void run(){moveFromEditAlphaNum();}};void moveFromEditAlphaNum()
//	{
//		move("", Group1);	// Erase all fiels
//
//		// Numerics
//		move("abcde", AlphaNumEdited);
//		assertIfFalse(AlphaNumEdited.equals("ab/cd"));
//		
//		move(AlphaNumEdited, AlphaNumEdited);
//		assertIfFalse(AlphaNumEdited.equals("ab//c"));
//
//		// AlphaNumerics
//		move(AlphaNumEdited, X);
//		assertIfFalse(X.equals("ab//c"));
//		
//		move(AlphaNumEdited, _TestMapRedefinesMap.E0);
//		assertIfFalse(_TestMapRedefinesMap.E0.equals("ab//c5.00       "));
//
//		// Numeric
//		move(AlphaNumEdited, NumEdited);
//		assertIfFalse(NumEdited.equals("         "));	// Unchanged
//
//		move(AlphaNumEdited, Num);
//		assertIfFalse(Num.equals("     "));		// Unchanged
//	
//		// Group
//		move(AlphaNumEdited, Group3);
//		assertIfFalse(Group3.equals("ab//c     "));
//	}
	
	Paragraph moveFromEdit = new Paragraph(this){public void run(){moveFromEdit();}};void moveFromEdit()
	{
		move("", Group1);	// Erase all fiels

		// AlphaNumerics
		move("abcde", _TestMapRedefinesMap.E0);
		assertIfFalse(_TestMapRedefinesMap.E0.equals("abcde           "));
		
		move(_TestMapRedefinesMap.E0, _TestMapRedefinesMap.E0);
		assertIfFalse(_TestMapRedefinesMap.E0.equals("abcde           "));

		move(_TestMapRedefinesMap.E0, X);
		assertIfFalse(X.equals("abcde"));
		
		//move(_TestMapRedefinesMap.E0, AlphaNumEdited);
		//assertIfFalse(AlphaNumEdited.equals("ab/cd"));

		// Numeric
		move("12345", _TestMapRedefinesMap.E0);
		assertIfFalse(_TestMapRedefinesMap.E0.equals("12345           "));

		move(_TestMapRedefinesMap.E0, NumEdited);
		assertIfFalse(NumEdited.equals("$2,345.00"));

		move(_TestMapRedefinesMap.E0, Num);
		assertIfFalse(Num.equals("12345"));
	
		// Group
		move(_TestMapRedefinesMap.E0, Group3);
		assertIfFalse(Group3.getString().startsWith("12345"));
	}
}
