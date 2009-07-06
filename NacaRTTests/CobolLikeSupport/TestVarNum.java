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

public class TestVarNum extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();

	Var v = declare.level(1).var();
		Var V4S4 = declare.level(2).picS9(4).comp().value(4).var();
		Var V44 = declare.level(2).pic9(4).comp().value(4).var();
	
		// COMP-3 integer	
		Var V3S4 = declare.level(2).picS9(4).comp3().value(4).var();
		Var V34 = declare.level(2).pic9(4).comp3().value(4).var();
		
		Var V3S5 = declare.level(2).picS9(5).comp3().value(-567).var();		
		Var V3S6 = declare.level(2).picS9(6).comp3().value(-6789).var();
		
		Var V3S8 = declare.level(2).picS9(8).comp3().var();
		Var V381 = declare.level(2).pic9(8).comp3().var();
		Var V382 = declare.level(2).pic9(8).comp3().var();
		
		// COMP-3 Decimals	
		Var V3S32 = declare.level(2).picS9(3, 2).comp3().value(1.2).var();
		Var V3S33 = declare.level(2).picS9(3, 3).comp3().value(1.2).var();
		Var V3S23 = declare.level(2).picS9(2, 3).comp3().value(3.4).var();
		Var VNS41 = declare.level(2).picS9(4, 1).comp3().value(4.0).var();
		
		// Signed integer
		Var VNS4 = declare.level(2).picS9(4).value(4).var();
		Var VNS5 = declare.level(2).picS9(5).value(-5).var();
		Var VNS6 = declare.level(2).picS9(6).value(-6).var();
		
		// Signed decimals (sign embedded)
		Var VNS32 = declare.level(2).picS9(3, 2).value(1.2).var();
		Var VNS23 = declare.level(2).picS9(2, 3).value(-3.4).var();

		// Signed decimals (sign separated)
		Var VNS4sl = declare.level(2).picS9(4).signLeadingSeparated().value(1).var();
		Var VNS5st = declare.level(2).picS9(5).signTrailingSeparated().value(1).var();
		Var VNS6sl = declare.level(2).picS9(6).signLeadingSeparated().value(1).var();
		Var VNS32sl = declare.level(2).picS9(3, 2).signLeadingSeparated().value(1).var();
		Var VNS23st = declare.level(2).picS9(2, 3).signTrailingSeparated().value(1).var();

		// Unsigned
		Var VN4 = declare.level(2).pic9(4).value(4).var();
		Var VN5 = declare.level(2).pic9(5).value(5).var();
		Var VN6 = declare.level(2).pic9(6).value(6).var();
		Var VN32 = declare.level(2).pic9(3, 2).value(3.14).var();
		Var VN23 = declare.level(2).pic9(2, 3).value(1.2345).var();
		Var vTotal5 = declare.level(2).pic9(5).value(1).var();
		Var vTotal52 = declare.level(2).pic9(5, 2).value(1).var();

		// Alpha
		Var VA4 = declare.level(2).picX(4).value("10").var();
		Var VA5 = declare.level(2).picX(5).value("10").var();
		Var VA6 = declare.level(2).picX(6).value("10").var();
		Var VANull = declare.level(2).picX(5).var();	// Contains null
		Var VAEmpty = declare.level(2).picX(5).value("").var();	// Contains null
		
		// comp-4
		Var VC22 = declare.level(2).pic9(2, 2).value(12.34).comp().var();
		Var VC32 = declare.level(2).pic9(3, 2).value(234.56).comp().var();
		Var VC5_4 = declare.level(2).picS9(5).value(1234).comp().var();
		Var VC5_9 = declare.level(2).picS9(9).value(1234).comp().var();

	public void procedureDivision()
	{
		setAssertActive(true);
		
//		testV3S4();
//		testV34();
//		
//		testV4S4();
//		testV44();
		
		perform(Paragraph1);
		stopRun();
	}
	
	void testV3S4()
	{
		move(123, V3S4);
		String cs0 = V3S4.getDottedSignedString();
		assertIfDifferent(cs0, "123");
		String cs1 = V3S4.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "123");

		move(1234, V3S4);
		cs0 = V3S4.getDottedSignedString();
		assertIfDifferent(cs0, "1234");
		cs1 = V3S4.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "1234");

		move(12345, V3S4);
		cs0 = V3S4.getDottedSignedString();
		assertIfDifferent(cs0, "12345");
		cs1 = V3S4.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "2345");
		
		move(123456, V3S4);
		cs0 = V3S4.getDottedSignedString();
		assertIfDifferent(cs0, "23456");
		cs1 = V3S4.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "3456");

		move(-1234, V3S4);
		cs0 = V3S4.getDottedSignedString();
		assertIfDifferent(cs0, "-1234");
		cs1 = V3S4.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "-1234");

		move(-12345, V3S4);
		cs0 = V3S4.getDottedSignedString();
		assertIfDifferent(cs0, "-12345");
		cs1 = V3S4.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "-2345");
		
		move(-123456, V3S4);
		cs0 = V3S4.getDottedSignedString();
		assertIfDifferent(cs0, "-23456");
		cs1 = V3S4.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "-3456");

		int n = 0;
	}
	
	void testV34()
	{
		move(123, V34);
		String cs0 = V34.getDottedSignedString();
		assertIfDifferent(cs0, "123");
		String cs1 = V34.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "123");

		move(1234, V34);
		cs0 = V34.getDottedSignedString();
		assertIfDifferent(cs0, "1234");
		cs1 = V34.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "1234");

		move(12345, V34);
		cs0 = V34.getDottedSignedString();
		assertIfDifferent(cs0, "12345");
		cs1 = V34.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "2345");
		
		move(123456, V34);
		cs0 = V34.getDottedSignedString();
		assertIfDifferent(cs0, "23456");
		cs1 = V34.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "3456");

		move(-1234, V34);
		cs0 = V34.getDottedSignedString();
		assertIfDifferent(cs0, "1234");
		cs1 = V34.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "1234");

		move(-12345, V34);
		cs0 = V34.getDottedSignedString();
		assertIfDifferent(cs0, "12345");
		cs1 = V34.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "2345");
		
		move(-123456, V34);
		cs0 = V34.getDottedSignedString();
		assertIfDifferent(cs0, "23456");
		cs1 = V34.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "3456");
		
		int n = 0;
	}
	
	void testV4S4()
	{
		move(123, V4S4);
		String cs0 = V4S4.getDottedSignedString();
		assertIfDifferent(cs0, "123");
		String cs1 = V4S4.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "123");

		move(1234, V4S4);
		cs0 = V4S4.getDottedSignedString();
		assertIfDifferent(cs0, "1234");
		cs1 = V4S4.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "1234");

		move(12345, V4S4);
		cs0 = V4S4.getDottedSignedString();
		assertIfDifferent(cs0, "12345");
		cs1 = V4S4.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "2345");
		
		move(-1234, V4S4);
		cs0 = V4S4.getDottedSignedString();
		assertIfDifferent(cs0, "-1234");
		cs1 = V4S4.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "-1234");

		move(-12345, V4S4);
		cs0 = V4S4.getDottedSignedString();
		assertIfDifferent(cs0, "-12345");
		cs1 = V4S4.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "-2345");
		
		int n = 0;
	}
	
	void testV44()
	{
		move(123, V44);
		String cs0 = V44.getDottedSignedString();
		assertIfDifferent(cs0, "123");
		String cs1 = V44.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "123");

		move(1234, V44);
		cs0 = V44.getDottedSignedString();
		assertIfDifferent(cs0, "1234");
		cs1 = V44.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "1234");

		move(12345, V44);
		cs0 = V44.getDottedSignedString();
		assertIfDifferent(cs0, "12345");
		cs1 = V44.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "2345");

		move(-1234, V44);
		cs0 = V44.getDottedSignedString();
		assertIfDifferent(cs0, "1234");
		cs1 = V44.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "1234");

		move(-12345, V44);
		cs0 = V44.getDottedSignedString();
		assertIfDifferent(cs0, "12345");
		cs1 = V44.getDottedSignedStringAsSQLCol();
		assertIfDifferent(cs1, "2345");
		
		int n = 0;
	}
	
	Paragraph Paragraph1 = new Paragraph(this){public void run(){Paragraph1();}};void Paragraph1()
	{		
		setAssertActive(true);
		
//		move(32767, VC5_4);
//		int n = VC5_4.getInt();
//		move(511, VC5_4);
//		n = VC5_4.getInt();
//		move(32768, VC5_4);
//		n = VC5_4.getInt();
//		move(32769, VC5_4);
//		n = VC5_4.getInt();
//		move(-1, VC5_4);
//		n = VC5_4.getInt();
//		move(65535, VC5_4);
//		move(65536, VC5_4);
//		
//		move(0, VC5_4);
//		move(-98765432, VC5_4);
//		
//		move(123456789, VC5_9);
//		move(-123456789, VC5_9);
//		move(-987654321, VC5_9);
//		move(987654321, VC5_9);
//		move(1, VC5_9);
		
		perform(CheckInitialValues);
		
		perform(Comp3NoDecOddNumberDigits);
		perform(Comp3NoDecEvenNumberDigits);
		perform(Comp3DecOddNumberDigits);
		perform(Comp3DecEvenNumberDigits);

		
		perform(XTo9);
		//perform(NullTo9);
				
		perform(testNumDecComp4);
		perform(Comp0NoSignNoDec);
		perform(Comp0NoSignDec);
		perform(Comp0SignNoDec);
		perform(Comp0SignDec);
		
		perform(Comp0LeadingSignNoDec);
		perform(Comp0TrailingSignNoDec);
		perform(Comp0LeadingSignDec);
		perform(Comp0TrailingSignDec);
		
		
		perform(Test0);
		
		CESM.returnTrans();
	}
	
	
	Paragraph CheckInitialValues = new Paragraph(this){public void run(){CheckInitialValues();}};void CheckInitialValues()
	{
		//assertIfDifferent(V3S4.getDottedSignedString(), "+00004");
		assertIfDifferent(V3S4.getInt(), 4);
		//assertIfDifferent(V3S5.getDottedSignedString(), "-00005");
		assertIfDifferent(V3S5.getInt(), -567);
		//assertIfDifferent(V3S6.getDottedSignedString(), "-0000006");
		assertIfDifferent(V3S6.getInt(), -6789);
	
		// COMP-3 Decimals	
		//assertIfDifferent(V3S32.getDottedSignedString(), "+001.20");
		assertIfDifferent(V3S32.getDouble(), 1.20);
		//assertIfDifferent(V3S33.getDottedSignedString(), "+0001.200");
		assertIfDifferent(V3S33.getDouble(), 1.2);
		//assertIfDifferent(V3S23.getDottedSignedString(), "+03.400");
		assertIfDifferent(V3S23.getDouble(), 3.4);
		//assertIfDifferent(VNS41.getDottedSignedString(), "+0004.0");
		assertIfDifferent(VNS41.getDouble(), 4);
				
		// Signed integer
		//assertIfDifferent(VNS4.getDottedSignedString(), "+0004");
		assertIfDifferent(VNS4.getInt(), 4);
		//assertIfDifferent(VNS5.getDottedSignedString(), "-00005");
		assertIfDifferent(VNS5.getInt(), -5);
		//assertIfDifferent(VNS6.getDottedSignedString(), "-000006");
		assertIfDifferent(VNS6.getInt(), -6);
		
		// Signed decimals (sign embedded)
		//assertIfDifferent(VNS32.getDottedSignedString(), "+001.20");
		assertIfDifferent(VNS32.getDouble(), 1.2);
		//assertIfDifferent(VNS23.getDottedSignedString(), "-03.400");
		assertIfDifferent(VNS23.getDouble(), -3.4);

		// Signed decimals (sign separated)
		//assertIfDifferent(VNS4sl.getString(), "+0001");
		assertIfDifferent(VNS4sl.getInt(), 1);
		String s0 = VNS5st.getString();
		String s1 = VNS5st.getDottedSignedString();
		//assertIfDifferent(VNS5st.getDottedSignedString(), "00001+");
		assertIfDifferent(VNS5st.getString(), "00001+");
		assertIfDifferent(VNS5st.getInt(), 1);
		assertIfDifferent(VNS6sl.getString(), "+000001");
		assertIfDifferent(VNS6sl.getInt(), 1);
		assertIfDifferent(VNS32sl.getString(), "+00100");
		assertIfDifferent(VNS32sl.getDouble(), 1.0);
		assertIfDifferent(VNS23st.getString(), "01000+");
		//assertIfDifferent(VNS23st.getDouble(), 1.0);

		// Unsigned
		assertIfDifferent(VN4.getString(), "0004");
		assertIfDifferent(VN4.getInt(), 4);
		assertIfDifferent(VN5.getString(), "00005");
		assertIfDifferent(VN5.getInt(), 5);
		assertIfDifferent(VN6.getString(), "000006");
		assertIfDifferent(VN6.getInt(), 6);
		assertIfDifferent(VN32.getString(), "00314");
		assertIfDifferent(VN32.getDouble(), 3.14);
		assertIfDifferent(VN23.getString(), "01234");
		assertIfDifferent(VN23.getDouble(), 1.234);
		assertIfDifferent(vTotal5.getString(), "00001");
		assertIfDifferent(vTotal5.getInt(), 1);
		assertIfDifferent(vTotal52.getDouble(), 1.0);

		// Alpha
		assertIfDifferent(VA4.getString(), "10  ");
		assertIfDifferent(VA5.getString(), "10   ");
		assertIfDifferent(VA6.getString(), "10    ");
	}
	
	Paragraph testNumDecComp4 = new Paragraph(this){public void run(){testNumDecComp4();}};void testNumDecComp4()
	{
		move(VC22, VNS32);
		move(VC32, VNS32);
		
		move(1.2, VC22);
		move(2.3, VC32);
		
		move(VC22, VNS32);
		move(VC32, VNS32);
		
		move(12345.6789, VC22);
		move(12345.6789, VC32);
		
		move(VNS32, VC22);
		move(VNS32, VC32);
		
		
		int n = 0;
		
	}
		
	Paragraph Comp0NoSignNoDec = new Paragraph(this){public void run(){Comp0NoSignNoDec();}};void Comp0NoSignNoDec()
	{
		move(0, VN4);				
		assertIfFalse(VN4.equals("0000"));
		
		move(12.345, VN4);			
		assertIfFalse(VN4.equals("0012"));
		
		move(-12.345, VN4);			
		assertIfFalse(VN4.equals("0012"));
		
		move(56789, VN4);			
		assertIfFalse(VN4.equals("6789"));
		
		move(-56789, VN4);			
		assertIfFalse(VN4.equals("6789"));
		
		move(0.9876, VN4);			
		assertIfFalse(VN4.equals("0000"));
		
		move(-0.9876, VN4);			
		assertIfFalse(VN4.equals("0000"));
		
		move(12345.6789, VN4);		
		assertIfFalse(VN4.equals("2345"));
	
		move(VN4, V3S5);
		//assertIfFalse(V3S5.getDottedSignedString().equals("+02345"));
		
		move(VN4, V3S4);			
		//assertIfFalse(V3S4.getDottedSignedString().equals("+02345"));
		
		move(VN4, V3S32);
		//assertIfFalse(V3S32.getDottedSignedString().equals("+345.00"));
		
		move(VN4, V3S33);			
		//assertIfFalse(V3S33.getDottedSignedString().equals("+0345.000"));
		
		move(VN4, VN4);				
		assertIfFalse(VN4.equals("2345"));
		
		move(VN4, VN32);			
		assertIfFalse(VN32.getDottedSignedString().equals("345.00"));
		
		move(VN4, VNS4);			
		//assertIfFalse(VNS4.getDottedSignedString().equals("+2345"));
		
		move(VN4, VNS32);			
		//assertIfFalse(VNS32.getDottedSignedString().equals("+345.00"));
		
		move(VN4, VNS4sl);			
		//assertIfFalse(VNS4sl.getDottedSignedString().equals("+2345"));
		
		move(VN4, VNS5st);			
		//assertIfFalse(VNS5st.getDottedSignedString().equals("02345+"));
		
		move(VN4, VNS32sl);			
		//assertIfFalse(VNS32sl.getDottedSignedString().equals("+345.00"));
		
		move(VN4, VNS23st);			
		//assertIfFalse(VNS23st.getDottedSignedString().equals("45.000+"));
}

	Paragraph Comp0NoSignDec = new Paragraph(this){public void run(){Comp0NoSignDec();}};void Comp0NoSignDec()
	{
		// Comp0 No Sign Dec		
		move(0, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("000.00"));
		
		move(12.345, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("012.34"));
		
		move(-12.345, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("012.34"));
		
		move(56789, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("789.00"));
		
		move(-56789, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("789.00"));
		
		move(0.9876, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("000.98"));
		
		move(-0.9876, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("000.98"));
		
		move(12345.6789, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("345.67"));
		
		move(-12345.6789, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("345.67"));
		
		move(VN32, V3S5);
		assertIfFalse(V3S5.getDottedSignedString().equals("+00345"));
		
		move(VN32, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("+00345"));
		
		move(VN32, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("+345.67"));
		
		move(VN32, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("+0345.670"));
		
		move(VN32, VN4);
		assertIfFalse(VN4.getDottedSignedString().equals("0345"));
		
		move(VN32, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("345.67"));
		
		move(VN32, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("+0345"));
		
		move(VN32, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("+345.67"));
		
		move(VN32, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("+0345"));
		
		move(VN32, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("00345+"));
		assertIfFalse(VNS5st.getString().equals("00345+"));
		
		move(VN32, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("+345.67"));
		assertIfFalse(VNS32sl.getString().equals("+34567"));
		
		move(VN32, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("45.670+"));
	}
	
	Paragraph Comp0SignNoDec = new Paragraph(this){public void run(){Comp0SignNoDec();}};void Comp0SignNoDec()
	{		
		// Comp0 Sign (included) no dec		
		move(0, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("+0000"));
		
		move(12.345, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("+0012"));
		
		move(-12.345, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("-0012"));
		
		move(56789, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("+6789"));
		
		move(-56789, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("-6789"));
		
		move(0.9876, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("+0000"));
		
		move(-0.9876, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("-0000"));
		
		move(12345.6789, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("+2345"));
		
		move(-12345.6789, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("-2345"));
		
		move(VNS4, V3S5);
		assertIfFalse(V3S5.getDottedSignedString().equals("-02345"));
		
		move(VNS4, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("-02345"));
				
		move(VNS4, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("-345.00"));
		
		move(VNS4, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("-0345.000"));
		
		move(VNS4, VN4);
		assertIfFalse(VN4.getDottedSignedString().equals("2345"));
		
		move(VNS4, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("345.00"));
		
		move(VNS4, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("-2345"));
		
		move(VNS4, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("-345.00"));
		
		move(VNS4, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("-2345"));
						
		move(VNS4, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("02345-"));
		
		move(VNS4, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("-345.00"));
		
		move(VNS4, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("45.000-"));
	}

	Paragraph Comp0SignDec = new Paragraph(this){public void run(){Comp0SignDec();}};void Comp0SignDec()
	{
		// Comp0 Sign (included) dec		
		move(0, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("+000.00"));
		
		move(12.345, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("+012.34"));
		
		move(-12.345, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("-012.34"));
		
		move(56789, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("+789.00"));
		
		move(-56789, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("-789.00"));
		
		move(0.9876, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("+000.98"));
		
		move(-0.9876, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("-000.98"));
		
		move(12345.6789, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("+345.67"));
		
		move(-12345.6789, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("-345.67"));
		
		move(VNS32, V3S5);
		assertIfFalse(V3S5.getDottedSignedString().equals("-00345"));
		
		move(VNS32, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("-00345"));
		
		move(VNS32, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("-345.67"));
		
		move(VNS32, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("-0345.670"));
		
		move(VNS32, VN4);
		assertIfFalse(VN4.getDottedSignedString().equals("0345"));
		
		move(VNS32, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("345.67"));
		
		move(VNS32, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("-0345"));
		
		move(VNS32, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("-345.67"));

		move(VNS32, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("-0345"));
		
		move(VNS32, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("00345-"));
		
		move(VNS32, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("-345.67"));
		
		move(VNS32, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("45.670-"));
	}
		
	Paragraph Comp0LeadingSignNoDec = new Paragraph(this){public void run(){Comp0LeadingSignNoDec();}};void Comp0LeadingSignNoDec()
	{
		// Comp0 Sign separated leading, no dec
		move(0, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("+0000"));
		assertIfFalse(VNS4sl.getString().equals("+0000"));
		
		move(12.345, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("+0012"));
		assertIfFalse(VNS4sl.getString().equals("+0012"));
		
		move(-12.345, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("-0012"));
		assertIfFalse(VNS4sl.getString().equals("-0012"));
		
		move(56789, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("+6789"));
		assertIfFalse(VNS4sl.getString().equals("+6789"));
		
		move(-56789, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("-6789"));
		assertIfFalse(VNS4sl.getString().equals("-6789"));
		
		move(0.9876, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("+0000"));
		assertIfFalse(VNS4sl.getString().equals("+0000"));
		
		move(-0.9876, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("-0000"));
		assertIfFalse(VNS4sl.getString().equals("-0000"));
		
		move(12345.6789, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("+2345"));
		assertIfFalse(VNS4sl.getString().equals("+2345"));
		
		move(-12345.6789, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("-2345"));
		assertIfFalse(VNS4sl.getString().equals("-2345"));
		
		move(VNS4sl, V3S5);
		assertIfFalse(V3S5.getDottedSignedString().equals("-02345"));

		move(VNS4sl, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("-02345"));
		
		move(VNS4sl, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("-345.00"));
		
		move(VNS4sl, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("-0345.000"));
		
		move(VNS4sl, VN4);
		assertIfFalse(VN4.getDottedSignedString().equals("2345"));
		
		move(VNS4sl, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("345.00"));
		
		move(VNS4sl, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("-2345"));
		
		move(VNS4sl, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("-345.00"));
		
		move(VNS4sl, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("-2345"));
		
		move(VNS4sl, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("02345-"));
		
		move(VNS4sl, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("-345.00"));
		
		move(VNS4sl, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("45.000-"));
	}
	
	Paragraph Comp0TrailingSignNoDec = new Paragraph(this){public void run(){Comp0TrailingSignNoDec();}};void Comp0TrailingSignNoDec()
	{
		// Comp0 Sign separated trailing, no dec
		move(0, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("00000+"));
		assertIfFalse(VNS5st.getString().equals("00000+"));
		
		move(12.345, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("00012+"));
		assertIfFalse(VNS5st.getString().equals("00012+"));

		move(-12.345, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("00012-"));
		assertIfFalse(VNS5st.getString().equals("00012-"));
		
		move(56789, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("56789+"));
		assertIfFalse(VNS5st.getString().equals("56789+"));
		
		move(-56789, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("56789-"));
		assertIfFalse(VNS5st.getString().equals("56789-"));

		move(0.9876, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("00000+"));
		assertIfFalse(VNS5st.getString().equals("00000+"));

		move(-0.9876, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("00000-"));
		assertIfFalse(VNS5st.getString().equals("00000-"));

		move(12345.6789, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("12345+"));
		assertIfFalse(VNS5st.getString().equals("12345+"));
		
		move(-12345.6789, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("12345-"));
		assertIfFalse(VNS5st.getString().equals("12345-"));
		
		move(VNS5st, V3S5);
		assertIfFalse(V3S5.getDottedSignedString().equals("-12345"));
		
		move(VNS5st, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("-02345"));
		
		move(VNS5st, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("-345.00"));
		
		move(VNS5st, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("-0345.000"));
		
		move(VNS5st, VN4);
		assertIfFalse(VN4.getDottedSignedString().equals("2345"));
		
		move(VNS5st, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("345.00"));
		
		move(VNS5st, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("-2345"));
		
		move(VNS5st, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("-345.00"));
		
		move(VNS5st, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("-2345"));
		
		move(VNS5st, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("12345-"));
		
		move(VNS5st, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("-345.00"));
		
		move(VNS5st, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("45.000-"));
	}
	
	
	Paragraph Comp0LeadingSignDec = new Paragraph(this){public void run(){Comp0LeadingSignDec();}};void Comp0LeadingSignDec()
	{
		// Comp0 Sign separated leading, dec
		move(0, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("+000.00"));
		assertIfFalse(VNS32sl.getString().equals("+00000"));
		
		move(123.45, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("+123.45"));
		assertIfFalse(VNS32sl.getString().equals("+12345"));
		
		move(-123.45, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("-123.45"));
		assertIfFalse(VNS32sl.getString().equals("-12345"));

		move(56789, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("+789.00"));
		assertIfFalse(VNS32sl.getString().equals("+78900"));

		move(-56789, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("-789.00"));
		assertIfFalse(VNS32sl.getString().equals("-78900"));

		move(0.9876, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("+000.98"));
		assertIfFalse(VNS32sl.getString().equals("+00098"));

		move(-0.9876, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("-000.98"));
		assertIfFalse(VNS32sl.getString().equals("-00098"));

		move(12345.6789, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("+345.67"));
		assertIfFalse(VNS32sl.getString().equals("+34567"));

		move(-12345.6789, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("-345.67"));
		assertIfFalse(VNS32sl.getString().equals("-34567"));

		move(VNS32sl, V3S5);
		assertIfFalse(V3S5.getDottedSignedString().equals("-00345"));
		
		move(VNS32sl, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("-00345"));
		
		move(VNS32sl, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("-345.67"));
		
		move(VNS32sl, VA6);
		
		move(VNS32sl, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("-0345.670"));
		
		move(VNS32sl, VN4);
		assertIfFalse(VN4.getDottedSignedString().equals("0345"));
		
		move(VNS32sl, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("345.67"));
		
		move(VNS32sl, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("-0345"));
		
		move(VNS32sl, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("-345.67"));
		
		move(VNS32sl, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("-0345"));
		
		move(VNS32sl, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("00345-"));
		
		move(VNS32sl, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("-345.67"));
		
		move(VNS32sl, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("45.670-"));
	}
		
	Paragraph Comp0TrailingSignDec = new Paragraph(this){public void run(){Comp0TrailingSignDec();}};void Comp0TrailingSignDec()
	{
		// Comp0 Sign separated trailing, dec
		move(0, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("00.000+"));
		assertIfFalse(VNS23st.getString().equals("00000+"));
		
		move(12.345, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("12.345+"));
		assertIfFalse(VNS23st.getString().equals("12345+"));
		
		move(-12.345, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("12.345-"));
		assertIfFalse(VNS23st.getString().equals("12345-"));
		
		move(56789, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("89.000+"));
		assertIfFalse(VNS23st.getString().equals("89000+"));

		move(-56789, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("89.000-"));
		assertIfFalse(VNS23st.getString().equals("89000-"));
		
		move(0.9876, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("00.987+"));
		assertIfFalse(VNS23st.getString().equals("00987+"));
		
		move(-0.9876, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("00.987-"));
		assertIfFalse(VNS23st.getString().equals("00987-"));

		move(12345.6789, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("45.678+"));
		assertIfFalse(VNS23st.getString().equals("45678+"));
		
		move(-12345.6789, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("45.678-"));
		assertIfFalse(VNS23st.getString().equals("45678-"));

		move(VNS23st, V3S5);
		assertIfFalse(V3S5.getDottedSignedString().equals("-00045"));
		
		move(VNS23st, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("-00045"));
		
		move(VNS23st, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("-045.67"));
		
		move(VNS23st, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("-0045.678"));
		
		move(VNS23st, VN4);
		assertIfFalse(VN4.getDottedSignedString().equals("0045"));
		
		move(VNS23st, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("045.67"));
		
		move(VNS23st, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("-0045"));
		
		move(VNS23st, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("-045.67"));
		
		move(VNS23st, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("-0045"));
		
		move(VNS23st, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("00045-"));
		
		move(VNS23st, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("-045.67"));
		
		move(VNS23st, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("45.678-"));		
	}
	
	Paragraph Comp3NoDecOddNumberDigits = new Paragraph(this){public void run(){Comp3NoDecOddNumberDigits();}};void Comp3NoDecOddNumberDigits()
	{
		move(1234, V3S4);
		move(1234, V3S5);
		move(1234, V3S6);
		// Comp-3 No dec; odd number of digits
		move(0, V3S5);
		assertIfFalse(V3S5.getInt() == 0);
		
		move(12.345, V3S5);
		//assertIfFalse(V3S5.getDottedSignedString().equals("+00012"));

		move(-12.345, V3S5);
		//assertIfFalse(V3S5.getDottedSignedString().equals("-00012"));
		
		move(56789, V3S5);
		//assertIfFalse(V3S5.getDottedSignedString().equals("+56789"));
		
		move(-56789, V3S5);
		//assertIfFalse(V3S5.getDottedSignedString().equals("-56789"));
		
		move(0.9876, V3S5);
		//assertIfFalse(V3S5.getDottedSignedString().equals("+00000"));
		
		move(-0.9876, V3S5);
		//assertIfFalse(V3S5.getDottedSignedString().equals("-00000"));
		
		move(12345.6789, V3S5);
		//assertIfFalse(V3S5.getDottedSignedString().equals("+12345"));
		
		move(-12345.6789, V3S5);
		//assertIfFalse(V3S5.getDottedSignedString().equals("-12345"));
		
		move(1234567.6789, V3S5);
		//assertIfFalse(V3S5.getDottedSignedString().equals("+34567"));
		
		move(-1234567.6789, V3S5);
		//assertIfFalse(V3S5.getDottedSignedString().equals("-34567"));
		
		move(V3S5, V3S5);
		//assertIfFalse(V3S5.getDottedSignedString().equals("-34567"));
				
		move(V3S5, V3S4);
		//assertIfFalse(V3S4.getDottedSignedString().equals("-04567"));
		
		move(V3S5, V3S32);
		String ccc = V3S32.getString();
		//assertIfFalse(V3S32.getDottedSignedString().equals("-567.00"));
		
		move(V3S5, V3S33);
		//assertIfFalse(V3S33.getDottedSignedString().equals("-0567.000"));
		
		move(V3S5, VN4);
		//assertIfFalse(VN4.getDottedSignedString().equals("4567"));
		
		move(V3S5, VN32);
		//assertIfFalse(VN32.getDottedSignedString().equals("567.00"));
		
		move(V3S5, VNS4);
		//assertIfFalse(VNS4.getDottedSignedString().equals("-4567"));
		
		move(V3S5, VNS32);
		//assertIfFalse(VNS32.getDottedSignedString().equals("-567.00"));
		
		move(V3S5, VNS4sl);
		//assertIfFalse(VNS4sl.getDottedSignedString().equals("-4567"));
		
		move(V3S5, VNS5st);
		//assertIfFalse(VNS5st.getDottedSignedString().equals("34567-"));
		
		move(V3S5, VNS32sl);
		//assertIfFalse(VNS32sl.getDottedSignedString().equals("-567.00"));
		
		move(V3S5, VNS23st);
		//assertIfFalse(VNS23st.getDottedSignedString().equals("67.000-"));
	}
	
	Paragraph Comp3NoDecEvenNumberDigits = new Paragraph(this){public void run(){Comp3NoDecEvenNumberDigits();}};void Comp3NoDecEvenNumberDigits()
	{
		// Comp-3 No dec; even number of digits
		move(0, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("0"));
		
		move(12.345, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("+00012"));
		
		move(-12.345, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("-00012"));
		
		move(56789, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("+06789"));
		
		move(-56789, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("-06789"));
		
		move(0.9876, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("+00000"));
		
		move(-0.9876, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("-00000"));
		
		move(12345.6789, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("+02345"));
		
		move(-12345.6789, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("-02345"));
		
		move(V3S4, V3S5);
		assertIfFalse(V3S5.getDottedSignedString().equals("-02345"));
		
		move(V3S4, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("-02345"));
		
		move(V3S4, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("-345.00"));
		
		move(V3S4, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("-0345.000"));
		
		move(V3S4, VN4);
		assertIfFalse(VN4.getDottedSignedString().equals("2345"));
		
		move(V3S4, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("345.00"));
		
		move(V3S4, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("-2345"));
		
		move(V3S4, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("-345.00"));
		
		move(V3S4, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("-2345"));
		
		move(V3S4, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("02345-"));
		
		move(V3S4, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("-345.00"));
		
		move(V3S4, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("45.000-"));
	}
	
	Paragraph Comp3DecOddNumberDigits = new Paragraph(this){public void run(){Comp3DecOddNumberDigits();}};void Comp3DecOddNumberDigits()
	{
		// Comp-3 dec; odd number of digits
		move(0, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("+000.00"));
		
		move(12.345, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("+012.34"));
		
		move(-12.345, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("-012.34"));
		
		move(56789, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("+789.00"));
		
		move(-56789, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("-789.00"));
		
		move(0.9876, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("+000.98"));
		
		move(-0.9876, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("-000.98"));
		
		move(12345.6789, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("+345.67"));
		
		move(-12345.6789, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("-345.67"));

		move(V3S32, V3S5);
		assertIfFalse(V3S5.getDottedSignedString().equals("-00345"));
		
		move(V3S32, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("-00345"));
		
		move(V3S32, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("-345.67"));
		
		move(V3S32, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("-0345.670"));
		
		move(V3S32, VN4);
		assertIfFalse(VN4.getDottedSignedString().equals("0345"));
		
		move(V3S32, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("345.67"));
		
		move(V3S32, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("-0345"));
		
		move(V3S32, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("-345.67"));
		
		move(V3S32, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("-0345"));
		
		move(V3S32, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("00345-"));
		
		move(V3S32, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("-345.67"));
		
		move(V3S32, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("45.670-"));
	}
	
	Paragraph Comp3DecEvenNumberDigits = new Paragraph(this){public void run(){Comp3DecEvenNumberDigits();}};void Comp3DecEvenNumberDigits()
	{
		// Comp-3 dec; even number of digits
		move(0, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("+0000.000"));
		
		move(12.345, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("+0012.345"));
		
		move(-12.345, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("-0012.345"));
		
		move(56789, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("+0789.000"));
		
		move(-56789, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("-0789.000"));
		
		move(0.9876, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("+0000.987"));
		
		move(-0.9876, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("-0000.987"));
		
		move(12345.6789, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("+0345.678"));
		
		move(-12345.6789, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("-0345.678"));
		
		move(V3S33, V3S5);
		assertIfFalse(V3S5.getDottedSignedString().equals("-00345"));
		
		move(V3S33, V3S4);
		assertIfFalse(V3S4.getDottedSignedString().equals("-00345"));
		
		move(V3S33, V3S32);
		assertIfFalse(V3S32.getDottedSignedString().equals("-345.67"));
		
		move(V3S33, V3S33);
		assertIfFalse(V3S33.getDottedSignedString().equals("-0345.678"));
		
		move(V3S33, VN4);
		assertIfFalse(VN4.getDottedSignedString().equals("0345"));
		
		move(V3S33, VN32);
		assertIfFalse(VN32.getDottedSignedString().equals("345.67"));
		
		move(V3S33, VNS4);
		assertIfFalse(VNS4.getDottedSignedString().equals("-0345"));
		
		move(V3S33, VNS32);
		assertIfFalse(VNS32.getDottedSignedString().equals("-345.67"));
		
		move(V3S33, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("-0345"));
		
		move(V3S33, VNS5st);
		assertIfFalse(VNS5st.getDottedSignedString().equals("00345-"));
		
		move(V3S33, VNS32sl);
		assertIfFalse(VNS32sl.getDottedSignedString().equals("-345.67"));
		
		move(V3S33, VNS23st);
		assertIfFalse(VNS23st.getDottedSignedString().equals("45.678-"));
	}
	
	Paragraph XTo9 = new Paragraph(this){public void run(){XTo9();}};void XTo9()
	{
		// X to 9 moves
		move(VANull, VNS4sl);
		assertIfFalse(VNS4sl.getDottedSignedString().equals("+0000"));
		assertIfFalse(VNS4sl.getString().equals("+0000"));
		
		move(VANull, V3S5);
		String cs = V3S5.getString();
		//assertIfFalse(V3S5.getDottedSignedString().equals("+00000"));

		move(VANull, V3S4);
		//assertIfFalse(V3S4.getDottedSignedString().equals("+00000"));
		
		move(VANull, V3S32);
		//assertIfFalse(V3S32.getDottedSignedString().equals("+000.00"));
		
		move(VANull, V3S33);
		//assertIfFalse(V3S33.getDottedSignedString().equals("+0000.000"));
		
		move(VANull, VN4);
		//assertIfFalse(VN4.getDottedSignedString().equals("0000"));
		
		move(VANull, VN32);
		//assertIfFalse(VN32.getDottedSignedString().equals("000.00"));
		
		move(VANull, VNS4);
		//assertIfFalse(VNS4.getDottedSignedString().equals("+0000"));
		
		move(VANull, VNS32);
		//assertIfFalse(VNS32.getDottedSignedString().equals("+000.00"));
		
		move(VANull, VNS4sl);
		//assertIfFalse(VNS4sl.getDottedSignedString().equals("+0000"));
		
		move(VANull, VNS5st);
		//assertIfFalse(VNS5st.getDottedSignedString().equals("00000+"));
		
		move(VANull, VNS32sl);
		//assertIfFalse(VNS32sl.getDottedSignedString().equals("+000.00"));
		
		move(VANull, VNS23st);
		//assertIfFalse(VNS23st.getDottedSignedString().equals("00.000+"));
		
		
		move(VAEmpty, VN4);
		//assertIfFalse(VNS4sl.getDottedSignedString().equals("+0000"));
		assertIfFalse(VNS4sl.getString().equals("+0000"));
		
		move(VAEmpty, V3S5);
		//assertIfFalse(V3S5.getDottedSignedString().equals("+00000"));

		move(VAEmpty, V3S4);
		//assertIfFalse(V3S4.getDottedSignedString().equals("+00000"));
		
		move(VAEmpty, V3S32);
		//assertIfFalse(V3S32.getDottedSignedString().equals("+000.00"));
		
		move(VAEmpty, V3S33);
		//assertIfFalse(V3S33.getDottedSignedString().equals("+0000.000"));
		
		move(VAEmpty, VN4);
		//assertIfFalse(VN4.getDottedSignedString().equals("0000"));
		
		move(VAEmpty, VN32);
		//assertIfFalse(VN32.getDottedSignedString().equals("000.00"));
		
		move(VAEmpty, VNS4);
		//assertIfFalse(VNS4.getDottedSignedString().equals("+0000"));
		
		move(VAEmpty, VNS32);
		//assertIfFalse(VNS32.getDottedSignedString().equals("+000.00"));
		
		move(VAEmpty, VNS4sl);
		//assertIfFalse(VNS4sl.getDottedSignedString().equals("+0000"));
		
		move(VAEmpty, VNS5st);
		//assertIfFalse(VNS5st.getDottedSignedString().equals("00000+"));
		
		move(VAEmpty, VNS32sl);
		//assertIfFalse(VNS32sl.getDottedSignedString().equals("+000.00"));
		
		move(VAEmpty, VNS23st);
		//assertIfFalse(VNS23st.getDottedSignedString().equals("00.000+"));

	}
	
	Paragraph NullTo9 = new Paragraph(this){public void run(){NullTo9();}};void NullTo9()
	{
		move(V3S8, V381);
		move(V382, V3S8);
		
		Var varNull = null;
		move(varNull, VN4);
		//assertIfFalse(VNS4sl.getDottedSignedString().equals("+0000"));
		assertIfFalse(VNS4sl.getString().equals("+0000"));
		
		move(varNull, V3S5);
		//assertIfFalse(V3S5.getDottedSignedString().equals("+00000"));

		move(varNull, V3S4);
		//assertIfFalse(V3S4.getDottedSignedString().equals("+00000"));
		
		move(varNull, V3S32);
		//assertIfFalse(V3S32.getDottedSignedString().equals("+000.00"));
		
		move(varNull, V3S33);
		//assertIfFalse(V3S33.getDottedSignedString().equals("+0000.000"));
		
		move(varNull, VN4);
		//assertIfFalse(VN4.getDottedSignedString().equals("0000"));
		
		move(varNull, VN32);
		//assertIfFalse(VN32.getDottedSignedString().equals("000.00"));
		
		move(varNull, VNS4);
		//assertIfFalse(VNS4.getDottedSignedString().equals("+0000"));
		
		move(varNull, VNS32);
		//assertIfFalse(VNS32.getDottedSignedString().equals("+000.00"));
		
		move(varNull, VNS4sl);
		//assertIfFalse(VNS4sl.getDottedSignedString().equals("+0000"));
		
		move(varNull, VNS5st);
		//assertIfFalse(VNS5st.getDottedSignedString().equals("00000+"));
		
		move(varNull, VNS32sl);
		//assertIfFalse(VNS32sl.getDottedSignedString().equals("+000.00"));
		
		move(varNull, VNS23st);
		//assertIfFalse(VNS23st.getDottedSignedString().equals("00.000+"));
	}

	Paragraph Test0 = new Paragraph(this){public void run(){Test0();}};void Test0()
	{
		moveZero(V3S4);
		assertIfFalse(isZero(V3S4)); 
		
		moveZero(V3S5);
		assertIfFalse(isZero(V3S5));
		
		moveZero(V3S6);
		assertIfFalse(isZero(V3S6));
		
		moveZero(V3S8);
		assertIfFalse(isZero(V3S8));
		
		moveZero(V381);
		assertIfFalse(isZero(V381));
		
		moveZero(V382);
		assertIfFalse(isZero(V382));
		
		
		// COMP-3 Decimals	
		moveZero(V3S32);
		assertIfFalse(isZero(V3S32));
		
		moveZero(V3S33);
		assertIfFalse(isZero(V3S33));
		
		moveZero(V3S23);
		assertIfFalse(isZero(V3S23));
		
		moveZero(VNS41);
		assertIfFalse(isZero(V3S4));
		
		
		// Signed integer
		moveZero(VNS4);
		assertIfFalse(isZero(VNS4));
		
		moveZero(VNS5);
		assertIfFalse(isZero(VNS5));
		
		moveZero(VNS6);
		assertIfFalse(isZero(VNS6));
		
		// Signed decimals (sign embedded)
		moveZero(VNS32);
		assertIfFalse(isZero(VNS32));
		
		moveZero(VNS23);
		assertIfFalse(isZero(VNS23));

		// Signed decimals (sign separated)
		moveZero(VNS4sl);
		assertIfFalse(isZero(VNS4sl));
		
		moveZero(VNS5st);
		assertIfFalse(isZero(VNS5st));
		
		moveZero(VNS6sl);
		assertIfFalse(isZero(VNS6sl));
		
		moveZero(VNS32sl);
		assertIfFalse(isZero(VNS32sl));
		
		moveZero(VNS23st);
		assertIfFalse(isZero(VNS23st));

		// Unsigned
		moveZero(VN4);
		assertIfFalse(isZero(VN4));
		
		moveZero(VN5);
		assertIfFalse(isZero(VN5));
		
		moveZero(VN6);
		assertIfFalse(isZero(VN6));
		
		moveZero(VN32);
		assertIfFalse(isZero(VN32));
		
		moveZero(VN23);
		assertIfFalse(isZero(VN23));
		
		moveZero(vTotal5);
		assertIfFalse(isZero(vTotal5));
		
		moveZero(vTotal52);
		assertIfFalse(isZero(vTotal52));

		// Alpha
		moveZero(VA4);
		assertIfFalse(isZero(VA4));
		
		moveZero(VA5);
		assertIfFalse(isZero(VA5));
		
		moveZero(VA6);
		assertIfFalse(isZero(VA6));
		
		moveZero(VANull);	// Contains null
		assertIfFalse(isZero(VANull));
		
		moveZero(VAEmpty);	// Contains null
		assertIfFalse(isZero(VAEmpty));
		
		move(V3S32, VA4);
		int n = 0;
	}
	

}
