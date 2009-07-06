/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 4 janv. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
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

public class TestMath extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	Var root = declare.level(1).var();
		Var A = declare.level(5).pic9(5).value(0).var();
		Var B = declare.level(5).pic9(5).value(0).var();
		Var C = declare.level(5).pic9(5).var();
		Var Total = declare.level(5).pic9(5).var();
		Var TotalSigned = declare.level(5).picS9(5).var();
		Var PI = declare.level(5).pic9(1, 2).value(3.1415).var();
		Var SignedDouble = declare.level(5).picS9(2, 6).var();
		Var SignedDouble2 = declare.level(5).picS9(2, 5).var();
		
		
	Var ws_Tarmt = declare.level(77).picS9(14, 4).value(0).var();
	Var ws_Tvatx = declare.level(77).picS9(3, 3).value(0).var();
	Var ws_Tarmti = declare.level(77).picS9(14, 4).value(0).var();
	Var w1_Mm = declare.level(77).pic9(2).var() ;

	Paragraph Paragraph1 = new Paragraph(this){public void run(){Paragraph1();}};void Paragraph1()
	{
		setAssertActive(true);
		
		perform(p_Calc_Tarmti);
		perform(TestOperations);
		perform(TestPositiveRoundings);
		perform(TestNegativeRoundings);
		perform(TestComplicatedOperations);
		perform(TestIntegerDivide);
		CESM.returnTrans();
	}
	
	Paragraph p_Calc_Tarmti = new Paragraph(this){public void run(){p_Calc_Tarmti();}} ; // (1109)  P-CALC-TARMTI.
	void p_Calc_Tarmti() 
	{
		move(0.186, ws_Tvatx);                                           
		move(123456, ws_Tarmt);
		computeRounded(multiply(divide(ws_Tarmt, 100), add(ws_Tvatx, 100)), ws_Tarmti) ;    // (1111)  COMPUTE WS-TARMTI ROUNDED =
		  
		move(11, w1_Mm);
		subtract(w1_Mm, 1).from(w1_Mm) ;
		dec(1, w1_Mm) ;
		
		add(2, w1_Mm).to(w1_Mm) ;
		inc(2, w1_Mm) ;
		int n = 0;
	}
	
	Paragraph TestOperations = new Paragraph(this){public void run(){TestOperations();}};void TestOperations()
	{
		// Check initial values
		assertIfFalse(A.getInt() == 0);
		assertIfFalse(B.getInt() == 0);
		move(0, C);
		assertIfFalse(C.getInt() == 0);
		move(0, Total);
		assertIfFalse(Total.getInt() == 0);
		assertIfFalse(PI.getDouble() == 3.14);
		
		// Inc & Dec
		inc(A);
		assertIfFalse(A.getInt() == 1);
		inc(A);
		assertIfFalse(A.getInt() == 2);
		dec(A);
		assertIfFalse(A.getInt() == 1);
		
		// Compute add
		compute(add(10, 2), B);
		assertIfFalse(B.getInt() == 12);

		compute(add(10, 2).add(A), B);
		assertIfFalse(B.getInt() == 13);

		compute(add(B, 1), C);
		assertIfFalse(C.getInt() == 14);
		
		compute(add(A, B).add(C), Total);
		assertIfFalse(Total.getInt() == 28);

		compute(add(A, B).add(C).add(PI), Total);
		assertIfFalse(Total.getInt() == 31);

		// compute subtract
		move(10, A);
		move(52, B);
		compute(subtract(A, B), Total);	// Total = A - B 
		assertIfFalse(Total.getInt() == 42);
		
		compute(subtract(A, B), TotalSigned);
		assertIfFalse(TotalSigned.getInt() == -42);
		
		compute(subtract(B, A), Total);	// Total = B - A 
		assertIfFalse(Total.getInt() == 42);	// Loose sign
		
		compute(subtract(B, A), TotalSigned);
		assertIfFalse(TotalSigned.getInt() == 42);

		// compute multiply
		move(123, A);
		move(2, B);
		compute(multiply(A, B), Total);	// Total = A * B 
		assertIfFalse(Total.getInt() == 246);

		compute(multiply(B, A), Total);	// Total = B * A 
		assertIfFalse(Total.getInt() == 246);

		move(1000, B);
		compute(multiply(A, B), Total);	// Total = A * B
		assertIfFalse(Total.getInt() == 23000);	// Loose the leading 1

		// compute divide
		move(46, A);
		move(2, B);
		compute(divide(A, B), Total);	// Total = A / B 
		assertIfFalse(Total.getInt() == 23);

		compute(divide(B, A), Total);	// Total = B / A 
		assertIfFalse(Total.getInt() == 0);
		
		compute(divide(B, A), SignedDouble);
		int nn = SignedDouble.getInt();
		assertIfFalse(nn == 0);
		double d = SignedDouble.getDouble();
		assertIfFalse(d == 0.043478);

		compute(divide(A, PI), Total);	// Total = A / PI 
		assertIfFalse(Total.getInt() == 14);
		assertIfFalse(Total.getDouble() == 14.0);

		compute(divide(A, PI), SignedDouble);	// Total = A / PI 
		assertIfFalse(SignedDouble.getInt() == 14);
		assertIfFalse(SignedDouble.getDouble() == 14.649681);	// Truncated

		//computeRounded(this.divide(A, PI), SignedDouble);	// Total = A / PI 
		//assertIfFalse(SignedDouble.getInt() == 14);
		//assertIfFalse(SignedDouble.getDouble() == 14.649682);	// Rounded
	}

	Paragraph TestPositiveRoundings = new Paragraph(this){public void run(){TestPositiveRoundings();}};void TestPositiveRoundings()
	{
		// Roudings
		// In the limit
			// Truncated
		move(3.500040, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
		assertIfFalse(SignedDouble2.getDouble() == 3.50004);	// Trucated

		move(3.500050, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
		assertIfFalse(SignedDouble2.getDouble() == 3.50005);	// Trucated

		move(3.500060, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
		assertIfFalse(SignedDouble2.getDouble() == 3.50006);	// Trucated

		move(3.5, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
		assertIfFalse(SignedDouble2.getDouble() == 3.5);	// Trucated		

		move(3.499990, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
		assertIfFalse(SignedDouble2.getDouble() == 3.49999);	// Trucated		

			// Rounded
//		move(3.500040, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
//		assertIfFalse(SignedDouble2.getDouble() == 3.50004);	// Rounded
//
//		move(3.500050, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
//		assertIfFalse(SignedDouble2.getDouble() == 3.50005);	// Trucated
//
//		move(3.500060, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
//		assertIfFalse(SignedDouble2.getDouble() == 3.50006);	// Trucated
//
//		move(3.5, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
//		assertIfFalse(SignedDouble2.getDouble() == 3.5);	// Rounded		
//
//		move(3.499990, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
//		assertIfFalse(SignedDouble2.getDouble() == 3.49999);	// Rounded		

		// Out of the limit
			// Truncated
		move(3.500004, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);
		assertIfFalse(SignedDouble2.getDouble() == 3.5);	// Trucated

		move(3.500005, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
		assertIfFalse(SignedDouble2.getDouble() == 3.5);	// Trucated

		move(3.500006, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
		assertIfFalse(SignedDouble2.getDouble() == 3.5);	// Trucated

		move(3.500000, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);
		assertIfFalse(SignedDouble2.getDouble() == 3.5);	// Trucated		

		move(3.499999, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);
		assertIfFalse(SignedDouble2.getDouble() == 3.49999);	// Trucated		

			// Rounded
//		move(3.500004, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);
//		assertIfFalse(SignedDouble2.getDouble() == 3.5);	// Rounded
//		
//		move(3.500005, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
//		assertIfFalse(SignedDouble2.getDouble() == 3.50001);	// Rounded
//
//		move(3.500006, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
//		assertIfFalse(SignedDouble2.getDouble() == 3.50001);	// Rounded
//
//		move(3.50000, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);
//		assertIfFalse(SignedDouble2.getDouble() == 3.5);	// Rounded		
//
//		move(3.499999, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);
//		assertIfFalse(SignedDouble2.getDouble() == 3.5);	// Rounded
	}
	
	Paragraph TestNegativeRoundings = new Paragraph(this){public void run(){TestNegativeRoundings();}};void TestNegativeRoundings()
	{
		// Roudings
		// In the limit
			// Truncated
		move(-3.500040, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
		assertIfFalse(SignedDouble2.getDouble() == -3.50004);	// Trucated

		move(-3.500050, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
		assertIfFalse(SignedDouble2.getDouble() == -3.50005);	// Trucated

		move(-3.500060, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
		assertIfFalse(SignedDouble2.getDouble() == -3.50006);	// Trucated

		move(-3.5, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
		assertIfFalse(SignedDouble2.getDouble() == -3.5);	// Trucated		

		move(-3.499990, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
		assertIfFalse(SignedDouble2.getDouble() == -3.49999);	// Trucated		

			// Rounded
//		move(-3.500040, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
//		assertIfFalse(SignedDouble2.getDouble() == -3.50004);	// Rounded
//
//		move(-3.500050, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
//		assertIfFalse(SignedDouble2.getDouble() == -3.50005);	// Trucated
//
//		move(-3.500060, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
//		assertIfFalse(SignedDouble2.getDouble() == -3.50006);	// Trucated
//
//		move(-3.5, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
//		assertIfFalse(SignedDouble2.getDouble() == -3.5);	// Rounded		
//
//		move(-3.499990, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
//		assertIfFalse(SignedDouble2.getDouble() == -3.49999);	// Rounded		

		// Out of the limit
			// Truncated
		move(-3.500004, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);
		assertIfFalse(SignedDouble2.getDouble() == -3.5);	// Trucated

		move(-3.500005, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
		assertIfFalse(SignedDouble2.getDouble() == -3.5);	// Trucated

		move(-3.500006, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
		assertIfFalse(SignedDouble2.getDouble() == -3.5);	// Trucated

		move(-3.500000, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);
		assertIfFalse(SignedDouble2.getDouble() == -3.5);	// Trucated		

		move(-3.499999, SignedDouble);
		compute(divide(SignedDouble, 1.0), SignedDouble2);
		assertIfFalse(SignedDouble2.getDouble() == -3.49999);	// Trucated		

			// Rounded
//		move(-3.500004, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);
//		assertIfFalse(SignedDouble2.getDouble() == -3.5);	// Rounded
//		
//		move(-3.500005, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
//		assertIfFalse(SignedDouble2.getDouble() == -3.50001);	// Rounded: shouldn't it be -3.5 ? (rounding a neagtive number is done on the opposite side ?)
//
//		move(-3.500006, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);	// Total = SignedDouble / 1.0
//		assertIfFalse(SignedDouble2.getDouble() == -3.50001);	// Rounded
//
//		move(-3.50000, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);
//		assertIfFalse(SignedDouble2.getDouble() == -3.5);	// Rounded		
//
//		move(-3.499999, SignedDouble);
//		computeRounded(divide(SignedDouble, 1.0), SignedDouble2);
//		assertIfFalse(SignedDouble2.getDouble() == -3.5);	// Rounded
	}
	
	Paragraph TestComplicatedOperations = new Paragraph(this){public void run(){TestComplicatedOperations();}};void TestComplicatedOperations()
	{
		// TotalSigned = ((A * B) + C) / PI - (100 * Total)
		move(5, A);
		move(7, B);
		move(12, C);
		move(3, Total);
		
		compute(divide(add(multiply(A, B), C), PI), TotalSigned);	// ((A * B) + C) / PI
		assertIfFalse(TotalSigned.getDouble() == 14);
		
		compute(subtract(multiply(100, Total), TotalSigned), TotalSigned); // (100 * PI) - 14
		assertIfFalse(TotalSigned.getDouble() == 286);
		
		compute(subtract(multiply(100, Total), divide(add(multiply(A, B), C), PI)), TotalSigned);	// (100 * PI) - (((A*B)+C)/PI)
		assertIfFalse(TotalSigned.getDouble() == 285.0);	// Result is different from below, as we have an intermediate result which is not an integer, and rounding occurs 
	}
	
	Paragraph TestIntegerDivide = new Paragraph(this){public void run(){TestIntegerDivide();}};void TestIntegerDivide()
	{
		move(7, A);
		move(2, B);
		
		divide(A, B).to(SignedDouble);	// Quotient / reminder
		assertIfFalse(SignedDouble.getDouble() == 3.5);
		assertIfFalse(SignedDouble.getInt() == 3);		

		divide(A, B).to(C, Total);	// Quotient / reminder
		assertIfFalse(C.getInt() == 3);
		assertIfFalse(Total.getInt() == 1);
	}

}
