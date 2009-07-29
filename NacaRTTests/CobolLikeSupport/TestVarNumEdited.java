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

public class TestVarNumEdited extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	// For TestNumericEdited
	
	Var vZ5 = declare.level(77).pic("ZZZZZ").var();
	Var vS97C3 = declare.level(77).picS9(7).comp3().var();
	Var v999999 = declare.level(77).pic("999999").var();
	Var v999V99 = declare.level(77).pic("999V99").var();
	Var v9999V99 = declare.level(77).pic("9999V99").var();
	Var p999Comma999 = declare.level(77).pic("999,999").var();
	Var p999Dot999 = declare.level(77).pic("999.999").var();
	Var pZZZCommaZZZ = declare.level(77).pic("ZZZ,ZZZ").var();
	Var pSSSCommaSSS = declare.level(77).pic("***,***").var();
	Var p99B99Bl99 = declare.level(77).pic("99B99B99").var();
	Var p99sl99sl99 = declare.level(77).pic("99/99/99").var();
	Var p9900Comma99 = declare.level(77).pic("9900,99").var();
	Var p$$Comma$$9Dot99 = declare.level(77).pic("$$,$$9.99").var();
	Var p$$$Comma$$9 = declare.level(77).pic("$$$,$$9").var();
	Var p$$$$Comma$99Dot99 = declare.level(77).pic("$$$$,$99.99").var();
	Var p$$Comma$$9	= declare.level(77).pic("$$,$$9").var();
	Var pPPPP9	= declare.level(77).pic("++++9").var();
	Var pMMMM9	= declare.level(77).pic("----9").var();
	
	Var pTest	= declare.level(77).pic("ZZZBZZZBZZ9.99-").var();
	Var X = declare.level(77).picX(10).var();
	Var Num = declare.level(77).picS9(9).var();
	

	Var comj1tx = declare.level(10).picS9(3,3).var(); 
	Var scomj1tx = declare.level(10).picS9(3,3).var();
	Var vc23_Result = declare.level(1).pic9("999.999").var() ;                   // (262)  01  VC23-RESULT            PIC 999.999.                          
 	Var filler$48 = declare.level(1).redefines(vc23_Result).var() ;             // (263)  01  FILLER                 REDEFINES VC23-RESULT.                
  		Var vc23_Result_X = declare.level(5).picX(7).var() ;              

  	Var number = declare.level(1).picS9(5).comp3().var() ;             // (263)  01  FILLER                 REDEFINES VC23-RESULT.
	Var vOcc = declare.level(1).occurs(5).var();
		Var vZ5Item = declare.level(5).pic("ZZZZZ").var();
		
	Var wz_Nombre = declare.level(1).pic("Z.ZZZ.ZZZ.ZZ9").valueZero().var() ;

	Paragraph Paragraph1 = new Paragraph(this){public void run(){Paragraph1();}};
	void Paragraph1()
	{
		setAssertActive(true);
		move(12345, wz_Nombre);
		
		move(123, number);
		move(number, vZ5Item.getAt(2));
		
		Var v = vZ5Item.getAt(2);
		
		move(-5, scomj1tx);
     	move(scomj1tx, vc23_Result_X);                     // (2792)               MOVE SCOMJ1TXI TO VC23-RESULT-X                     
//              005.000                  005.000  

     	move(vc23_Result, comj1tx);                        // (2793)               MOVE VC23-RESULT TO COMJ1TX OF DVRS6102F
//              005.000               -0001.000  
		
		//move(-12345.67, pTest);
		//move(pTest, X);
		//move(pTest, Num);
		
		move(14, vS97C3);
		move(vS97C3, vZ5);
		
		perform(TestWith0);
		perform(TestWith1);
		perform(TestWith2);
		perform(TestWith3);
		perform(TestWith4);
		perform(TestWith5);
		perform(TestWith6);
		perform(TestWith7);
		
		CESM.returnTrans();
	}

	Paragraph TestWith0 = new Paragraph(this){public void run(){TestWith0();}};
	void TestWith0()
	{
		// Source: 0	
		move(0, v999999);						
		assertIfFalse(v999999.equals("000000"));
		
		move(v999999, p999Comma999);			
		assertIfFalse(p999Comma999.equals("000,000"));
		
		move(v999999, p999Dot999);				
		assertIfFalse(p999Dot999.equals("000.000"));
		
		move(v999999, pZZZCommaZZZ);			
		assertIfFalse(pZZZCommaZZZ.equals("       "));
		
		move(v999999, pSSSCommaSSS);			
		assertIfFalse(pSSSCommaSSS.equals("       "));
		
		move(v999999, p$$Comma$$9Dot99);		
		assertIfFalse(p$$Comma$$9Dot99.equals("    $0.00"));
	}
	
	Paragraph TestWith1 = new Paragraph(this){public void run(){TestWith1();}};
	void TestWith1()
	{
		move(1, v999999);						
		assertIfFalse(v999999.equals("000001"));
		
		move(v999999, p999Comma999);			
		assertIfFalse(p999Comma999.equals("000,001"));
		
		move(v999999, p999Dot999);				
		assertIfFalse(p999Dot999.equals("001.000"));
		
		move(v999999, pZZZCommaZZZ);			
		assertIfFalse(pZZZCommaZZZ.equals("      1"));
		
		move(v999999, pSSSCommaSSS);			
		assertIfFalse(pSSSCommaSSS.equals("******1"));
		
		move(v999999, p$$Comma$$9Dot99);		
		assertIfFalse(p$$Comma$$9Dot99.equals("    $1.00"));
	}
	
	Paragraph TestWith2= new Paragraph(this){public void run(){TestWith2();}};
	void TestWith2()
	{
		move(12, v999999);						
		assertIfFalse(v999999.equals("000012"));
		
		move(v999999, p999Comma999);			
		assertIfFalse(p999Comma999.equals("000,012"));
		
		move(v999999, p999Dot999);				
		assertIfFalse(p999Dot999.equals("012.000"));
		
		move(v999999, pZZZCommaZZZ);			
		assertIfFalse(pZZZCommaZZZ.equals("     12"));
		
		move(v999999, pSSSCommaSSS);			
		assertIfFalse(pSSSCommaSSS.equals("*****12"));
		
		move(v999999, p$$Comma$$9Dot99);		
		assertIfFalse(p$$Comma$$9Dot99.equals("   $12.00"));
	}

	Paragraph TestWith3= new Paragraph(this){public void run(){TestWith3();}};
	void TestWith3()
	{
		move(123, v999999);						
		assertIfFalse(v999999.equals("000123"));
		
		move(v999999, p999Comma999);			
		assertIfFalse(p999Comma999.equals("000,123"));
		
		move(v999999, p999Dot999);				
		assertIfFalse(p999Dot999.equals("123.000"));
		
		move(v999999, pZZZCommaZZZ);			
		assertIfFalse(pZZZCommaZZZ.equals("    123"));
		
		move(v999999, pSSSCommaSSS);			
		assertIfFalse(pSSSCommaSSS.equals("****123"));
		
		move(v999999, p$$Comma$$9Dot99);		
		assertIfFalse(p$$Comma$$9Dot99.equals("  $123.00"));
	}
	
	Paragraph TestWith4 = new Paragraph(this){public void run(){TestWith4();}};
	void TestWith4()
	{
		move(1234, v999999);						
		assertIfFalse(v999999.equals("001234"));
		
		move(v999999, p999Comma999);			
		assertIfFalse(p999Comma999.equals("001,234"));
		
		move(v999999, p999Dot999);				
		assertIfFalse(p999Dot999.equals("234.000"));
		
		
		move(v999999, pZZZCommaZZZ);			
		assertIfFalse(pZZZCommaZZZ.equals("  1,234"));
		
		move(v999999, pSSSCommaSSS);			
		assertIfFalse(pSSSCommaSSS.equals("**1,234"));
		
		move(v999999, p$$Comma$$9Dot99);		
		assertIfFalse(p$$Comma$$9Dot99.equals("$1,234.00"));
	}
	
	
	Paragraph TestWith5 = new Paragraph(this){public void run(){TestWith5();}};
	void TestWith5()
	{
		move(12345, v999999);						
		assertIfFalse(v999999.equals("012345"));
		
		move(v999999, p999Comma999);			
		assertIfFalse(p999Comma999.equals("012,345"));
		
		move(v999999, p999Dot999);				
		assertIfFalse(p999Dot999.equals("345.000"));
		
		
		move(v999999, pZZZCommaZZZ);			
		assertIfFalse(pZZZCommaZZZ.equals(" 12,345"));
		
		move(v999999, pSSSCommaSSS);			
		assertIfFalse(pSSSCommaSSS.equals("*12,345"));
		
		move(v999999, p$$Comma$$9Dot99);		
		assertIfFalse(p$$Comma$$9Dot99.equals("$2,345.00"));
	}
	
	Paragraph TestWith6 = new Paragraph(this){public void run(){TestWith6();}};
	void TestWith6()
	{
		move(123456, v999999);						
		assertIfFalse(v999999.equals("123456"));
		
		move(v999999, p999Comma999);			
		assertIfFalse(p999Comma999.equals("123,456"));
		
		move(v999999, p999Dot999);				
		assertIfFalse(p999Dot999.equals("456.000"));
		
		
		move(v999999, pZZZCommaZZZ);			
		assertIfFalse(pZZZCommaZZZ.equals("123,456"));
		
		move(v999999, pSSSCommaSSS);			
		assertIfFalse(pSSSCommaSSS.equals("123,456"));
		
		move(v999999, p$$Comma$$9Dot99);		
		assertIfFalse(p$$Comma$$9Dot99.equals("$3,456.00"));
	}	
	
	Paragraph TestWith7 = new Paragraph(this){public void run(){TestWith7();}};
	void TestWith7()
	{
		move(1234567, v999999);						
		assertIfFalse(v999999.equals("234567"));
		
		move(v999999, p999Comma999);			
		assertIfFalse(p999Comma999.equals("234,567"));
		
		move(v999999, p999Dot999);				
		assertIfFalse(p999Dot999.equals("567.000"));
		
		
		move(v999999, pZZZCommaZZZ);			
		assertIfFalse(pZZZCommaZZZ.equals("234,567"));
		
		move(v999999, pSSSCommaSSS);			
		assertIfFalse(pSSSCommaSSS.equals("234,567"));
		
		move(v999999, p$$Comma$$9Dot99);		
		assertIfFalse(p$$Comma$$9Dot99.equals("$4,567.00"));
	}	
	
	
	
		
/*
		// 1 digit
		move(123, v999999);
		assertIfFalse(v999999.equals("000001"));
		
		move(v999999, p999Comma999);			
		assertIfFalse(p999Comma999.equals("000,000"));
		
		move(v999999, p999Dot999);				
		assertIfFalse(p999Dot999.equals("000.000"));
		
		move(v999999, pZZZCommaZZZ);			
		assertIfFalse(pZZZCommaZZZ.equals("       "));
		
		move(v999999, pSSSCommaSSS);			
		assertIfFalse(pSSSCommaSSS.equals("*******"));
		
		move(v999999, p$$Comma$$9Dot99);		
		assertIfFalse(p$$Comma$$9Dot99.equals("    $0.00"));
		
		
		
		move(v999999, p$$Comma$$9Dot99);
		assertIfFalse(p$$Comma$$9Dot99.equals("  $123.00"));
		
		move(1, v999999);
		move(v999999, p999Comma999);			assertIfFalse(p999Comma999.equals("000,001"));
		move(v999999, p999Dot999);				assertIfFalse(p999Dot999.equals("001.000"));
		move(v999999, pZZZCommaZZZ);			assertIfFalse(pZZZCommaZZZ.equals("      1"));
		move(v999999, pSSSCommaSSS);			assertIfFalse(pSSSCommaSSS.equals("******1"));

		move(12, v999999);
		move(v999999, p999Comma999);	assertIfFalse(p999Comma999.equals("000,012"));
		move(v999999, p999Dot999);		assertIfFalse(p999Dot999.equals("012.000"));
		move(v999999, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("     12"));
		move(v999999, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("*****12"));

		move(123, v999999);
		move(v999999, p999Comma999);	assertIfFalse(p999Comma999.equals("000,123"));
		move(v999999, p999Dot999);		assertIfFalse(p999Dot999.equals("123.000"));
		move(v999999, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("    123"));
		move(v999999, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("****123"));

		move(1234, v999999);
		move(v999999, p999Comma999);	assertIfFalse(p999Comma999.equals("001,234"));
		move(v999999, p999Dot999);		assertIfFalse(p999Dot999.equals("234.000"));
		move(v999999, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("  1,234"));
		move(v999999, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("**1,234"));
		move(v999999, p$$$Comma$$9);	assertIfFalse(p$$$Comma$$9.equals(" $1,234"));
		move(v999999, p$$$$Comma$99Dot99);	assertIfFalse(p$$$$Comma$99Dot99.equals("  $1,234.00"));

		move(57397, v999999);
		move(v999999, p$$Comma$$9);		assertIfFalse(p$$Comma$$9.equals("$7,397"));

		move(12345, v999999);
		move(v999999, p999Comma999);	assertIfFalse(p999Comma999.equals("012,345"));
		move(v999999, p999Dot999);		assertIfFalse(p999Dot999.equals("345.000"));
		move(v999999, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals(" 12,345"));
		move(v999999, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("*12,345"));

		move(123456, v999999);
		move(v999999, p999Comma999);	assertIfFalse(p999Comma999.equals("123,456"));
		move(v999999, p999Dot999);		assertIfFalse(p999Dot999.equals("456.000"));
		move(v999999, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("123,456"));
		move(v999999, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("123,456"));
		
		move(1234567, v999999);
		move(v999999, p999Comma999);	assertIfFalse(p999Comma999.equals("234,567"));
		move(v999999, p999Dot999);		assertIfFalse(p999Dot999.equals("567.000"));
		move(v999999, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("234,567"));
		move(v999999, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("234,567"));

		move(0, v999V99);
		move(v999V99, p999Comma999);	assertIfFalse(p999Comma999.equals("000,000"));
		move(v999V99, p999Dot999);		assertIfFalse(p999Dot999.equals("000.000"));
		move(v999V99, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("       "));
		move(v999V99, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("*******"));

		move(0.1, v999V99);
		move(v999V99, p999Comma999);	assertIfFalse(p999Comma999.equals("000,000"));
		move(v999V99, p999Dot999);		assertIfFalse(p999Dot999.equals("000.100"));
		move(v999V99, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("       "));
		move(v999V99, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("*******"));

		move(0.12, v999V99);
		move(v999V99, p999Comma999);	assertIfFalse(p999Comma999.equals("000,000"));
		move(v999V99, p999Dot999);		assertIfFalse(p999Dot999.equals("000.120"));
		move(v999V99, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("       "));
		move(v999V99, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("*******"));

		move(0.123, v999V99);
		move(v999V99, p999Comma999);	assertIfFalse(p999Comma999.equals("000,000"));
		move(v999V99, p999Dot999);		assertIfFalse(p999Dot999.equals("000.123"));
		move(v999V99, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("       "));
		move(v999V99, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("*******"));

		move(1, v999V99);
		move(v999V99, p999Comma999);	assertIfFalse(p999Comma999.equals("000,001"));
		move(v999V99, p999Dot999);		assertIfFalse(p999Dot999.equals("001.000"));
		move(v999V99, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("      1"));
		move(v999V99, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("******1"));

		move(12, v999V99);
		move(v999V99, p999Comma999);	assertIfFalse(p999Comma999.equals("000,012"));
		move(v999V99, p999Dot999);		assertIfFalse(p999Dot999.equals("012.000"));
		move(v999V99, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("     12"));
		move(v999V99, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("*****12"));

		move(12.3, v999V99);
		move(v999V99, p999Comma999);	assertIfFalse(p999Comma999.equals("000,012"));
		move(v999V99, p999Dot999);		assertIfFalse(p999Dot999.equals("012.300"));
		move(v999V99, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("     12"));
		move(v999V99, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("*****12"));

		move(12.34, v999V99);
		move(v999V99, p999Comma999);	assertIfFalse(p999Comma999.equals("000,012"));
		move(v999V99, p999Dot999);		assertIfFalse(p999Dot999.equals("012.340"));
		move(v999V99, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("     12"));
		move(v999V99, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("*****12"));

		move(12.345, v999V99);
		move(v999V99, p999Comma999);	assertIfFalse(p999Comma999.equals("000,012"));
		move(v999V99, p999Dot999);		assertIfFalse(p999Dot999.equals("012.345"));
		move(v999V99, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("     12"));
		move(v999V99, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("*****12"));

		move(123, v999V99);
		move(v999V99, p999Comma999);	assertIfFalse(p999Comma999.equals("000,123"));
		move(v999V99, p999Dot999);		assertIfFalse(p999Dot999.equals("123.000"));
		move(v999V99, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("    123"));
		move(v999V99, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("****123"));

		move(1234, v999V99);
		move(v999V99, p999Comma999);	assertIfFalse(p999Comma999.equals("000,234"));
		move(v999V99, p999Dot999);		assertIfFalse(p999Dot999.equals("234.000"));
		move(v999V99, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("    234"));
		move(v999V99, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("****234"));

		move(1234, v9999V99);
		move(v9999V99, p999Comma999);	assertIfFalse(p999Comma999.equals("001,234"));
		move(v9999V99, p999Dot999);		assertIfFalse(p999Dot999.equals("234.000"));
		move(v9999V99, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("  1,234"));
		move(v9999V99, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("**1,234"));

		move(1234.5, v9999V99);
		move(v9999V99, p999Comma999);	assertIfFalse(p999Comma999.equals("001,234"));
		move(v9999V99, p999Dot999);		assertIfFalse(p999Dot999.equals("234.500"));
		move(v9999V99, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("  1,234"));
		move(v9999V99, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("**1,234"));

		move(1234.56, v9999V99);
		move(v9999V99, p999Comma999);	assertIfFalse(p999Comma999.equals("001,234"));
		move(v9999V99, p999Dot999);		assertIfFalse(p999Dot999.equals("234.560"));
		move(v9999V99, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("  1,234"));
		move(v9999V99, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("**1,234"));

		move(1234.567, v9999V99);
		move(v9999V99, p999Comma999);	assertIfFalse(p999Comma999.equals("001,234"));
		move(v9999V99, p999Dot999);		assertIfFalse(p999Dot999.equals("234.567"));
		move(v9999V99, pZZZCommaZZZ);	assertIfFalse(pZZZCommaZZZ.equals("  1,234"));
		move(v9999V99, pSSSCommaSSS);	assertIfFalse(pSSSCommaSSS.equals("**1,234"));
		
		move(120183, v999999);

		move(v999999, p99B99Bl99);
		move(v999999, p99sl99sl99);
						
		move(v999999, p99B99Bl99);		assertIfFalse(p99B99Bl99.equals("12 01 83"));
		move(v999999, p99sl99sl99);		assertIfFalse(p99sl99sl99.equals("12/01/83"));	
		
		*/
	//
}
