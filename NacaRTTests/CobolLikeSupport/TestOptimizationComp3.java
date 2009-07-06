/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import jlib.misc.StopWatch;
import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.Var;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class TestOptimizationComp3 extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	Var vInt = declare.level(1).var() ;
		Var v4 = declare.level(5).pic9(4).comp3().var() ;
		Var vS4 = declare.level(5).picS9(4).comp3().var() ;
		Var v5 = declare.level(5).pic9(5).comp3().var() ;
		Var vS5 = declare.level(5).picS9(5).comp3().var() ;
		
	Var vDec = declare.level(1).var() ;
		Var v52 = declare.level(5).pic9(5, 2).comp3().var() ;
		Var vS52 = declare.level(5).picS9(5, 2).comp3().var() ;

	Var vComp0 = declare.level(1).var() ;
		Var vComp05L = declare.level(5).pic9(5).signLeadingSeparated().var() ;
		Var vComp05T = declare.level(5).pic9(5).signTrailingSeparated().var() ;
		Var vSComp05 = declare.level(5).picS9(5).var() ;
		
	public void procedureDivision()
	{
		setAssertActive(true);
		
		testComp0();
		testDec();
		testInteger();
	}
	
	void testComp0()
	{
		move(12345678, vComp05L);
		assertIfDifferent("+45678", vComp05L);
		
		move(-12345678, vComp05L);
		assertIfDifferent("-45678", vComp05L);
		
		move(12345678, vComp05T);
		assertIfFalse(vComp05T.getString().equals("45678+"));

		move(-12345678, vComp05T);
		assertIfFalse(vComp05T.getString().equals("45678-"));
		
		move(0, vComp05L);
		assertIfFalse(vComp05L.getString().equals("+00000"));
		
		move(0, vComp05T);
		assertIfFalse(vComp05T.getString().equals("00000+"));
		
		move(12, vComp05L);
		assertIfFalse(vComp05L.getString().equals("+00012"));
		move(-12, vComp05L);
		assertIfFalse(vComp05L.getString().equals("-00012"));
		
		move(12, vComp05T);
		assertIfFalse(vComp05T.getString().equals("00012+"));
		move(-12, vComp05T);
		assertIfFalse(vComp05T.getString().equals("00012-"));
		
		
		move(98765, vComp05L);
		assertIfFalse(vComp05L.getString().equals("+98765"));
		
		move(-98765, vComp05L);
		assertIfFalse(vComp05L.getString().equals("-98765"));
		
		move(98765, vComp05T);
		assertIfFalse(vComp05T.getString().equals("98765+"));
		
		move(-98765, vComp05T);
		assertIfFalse(vComp05T.getString().equals("98765-"));
		
		move(12345678, vSComp05);
		assertIfDifferent(45678, vSComp05);
		move(-12345678, vSComp05);
		assertIfDifferent(-45678, vSComp05);
				
		move(0, vSComp05);
		assertIfDifferent(0, vSComp05);
		
		move(12, vSComp05);
		assertIfDifferent(12, vSComp05);
		move(-12, vSComp05);
		assertIfDifferent(-12, vSComp05);
	}
	
	void testDec()
	{
		move(1234567.98765, v52);
		assertIfFalse(34567.98 == v52.getDouble());
		
		move(1234567.98765, vS52);
		assertIfFalse(34567.98 == vS52.getDouble());

		move(-1234567.98765, v52);
		assertIfFalse(34567.98 == v52.getDouble());

		move(-1234567.98765, vS52);
		assertIfFalse(-34567.98 == vS52.getDouble());

		move(12, vS52);
		assertIfFalse(12.0 == vS52.getDouble());

		move(123.45, v52);
		assertIfFalse(123.45 == v52.getDouble());
		
		move(123.45, vS52);
		assertIfFalse(123.45 == vS52.getDouble());
		
		move(-123.45, vS52);
		assertIfFalse(-123.45 == vS52.getDouble());
	}
	
	void testInteger()
	{
		move(1234, v4);
		assertIfFalse(1234 == v4.getInt());
		
		move(1234, vS4);
		assertIfFalse(1234 == vS4.getInt());

		move(1234, vS5);
		assertIfFalse(1234 == vS5.getInt());

		move(1234, vS5);
		assertIfFalse(1234 == vS5.getInt());

	
		move(23456, v4);
		assertIfFalse(23456 == v4.getInt());
		
		move(23456, vS4);
		assertIfFalse(23456 == vS4.getInt());

		move(23456, vS5);
		assertIfFalse(23456 == vS5.getInt());

		move(23456, vS5);
		assertIfFalse(23456 == vS5.getInt());
		
		move(-1234, v4);
		assertIfFalse(1234 == v4.getInt());
		
		move(-1234, vS4);
		assertIfFalse(-1234 == vS4.getInt());

		move(-1234, v5);
		assertIfFalse(1234 == v5.getInt());

		move(-1234, vS5);
		assertIfFalse(-1234 == vS5.getInt());

	
		move(-23456, v4);
		assertIfFalse(23456 == v4.getInt());
		
		move(-23456, vS4);
		assertIfFalse(-23456 == vS4.getInt());

		move(-23456, v5);
		assertIfFalse(23456 == v5.getInt());

		move(-23456, vS5);
		assertIfFalse(-23456 == vS5.getInt());
		
		
		
		//---------
		move(345678, v4);
		assertIfFalse(45678 == v4.getInt());
		
		move(345678, vS4);
		assertIfFalse(45678 == vS4.getInt());

		move(345678, v5);
		assertIfFalse(45678 == v5.getInt());
		
		move(345678, vS5);
		assertIfFalse(45678 == vS5.getInt());
		
		
		move(-345678, v4);
		assertIfFalse(45678 == v4.getInt());
		
		move(-345678, vS4);
		assertIfFalse(-45678 == vS4.getInt());
		
		
		move(-345678, v5);
		assertIfFalse(45678 == v5.getInt());
		
		move(-345678, vS5);
		assertIfFalse(-45678 == vS5.getInt());
	}
}
