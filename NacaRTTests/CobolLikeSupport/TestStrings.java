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
 * Created on 15 déc. 2004
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

public class TestStrings extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	

	Var RCD_01 = declare.level(1).var();
		Var CUST_INFO = declare.level(5).var();
			Var CUST_NAME = declare.level(10).picX(15).value("J.B. SMITH").var();
			Var CUST_ADDR = declare.level(10).picX(35).value("444 SPRING ST., CHICAGO, ILL.").var();
		Var BILL_INFO = declare.level(5).var();
			Var INV_NO = declare.level(10).picX(6).value("A14275").var();
			Var INV_AMT = declare.level(10).pic("$$,$$$.99").value(4736.85).var();	// Is iot OK ? I don't knoy if it's not 4736.85
			Var AMT_PAID = declare.level(10).pic("$$,$$$.99").value(2400.00).var();	// Is iot OK ? I don't knoy if it's not 2400.00 
			Var DATE_PAID = declare.level(10).picX(8).value("09/22/76").var();
			Var BAL_DUE = declare.level(10).pic("$$,$$$.99").value(2336.85).var();
			Var DATE_DUE = declare.level(10).picX(8).value("10/22/76").var();
			
			
	Var RPT_LINE = declare.level(77).picX(120).var();
	Var LINE_POS = declare.level(77).picS9(3).var();
	Var LINE_NO = declare.level(77).pic9(5).value(1).var();
	Var DEC_POINT = declare.level(77).picX().value(".").var();


	Paragraph Paragraph1 = new Paragraph(this){public void run(){Paragraph1();}};void Paragraph1()
	{
		setAssertActive(true);
		
		perform(Test1);
		CESM.returnTrans();
	}
	
	Paragraph Test1 = new Paragraph(this){public void run(){Test1();}};void Test1()
	{
		// see http://publib.boulder.ibm.com/infocenter/pdthelp/v1r1/index.jsp?topic=/com.ibm.entcobol4.doc/rpstr06e.htm
		// Check initial values
		assertIfFalse(CUST_NAME.equals("J.B. SMITH     "));
		assertIfFalse(CUST_ADDR.equals("444 SPRING ST., CHICAGO, ILL.      "));
		assertIfFalse(INV_NO.equals("A14275"));
		assertIfFalse(INV_AMT.equals("$4,736.85"));
		assertIfFalse(AMT_PAID.equals("$2,400.00"));
		assertIfFalse(DATE_PAID.equals("09/22/76"));
		assertIfFalse(BAL_DUE.equals("$2,336.85"));
		assertIfFalse(DATE_DUE.equals("10/22/76"));
		/*
	    STRING
        LINE-NO SPACE CUST-INFO INV-NO SPACE DATE-DUE SPACE
           DELIMITED BY SIZE
        BAL-DUE
           DELIMITED BY DEC-POINT
        INTO RPT-LINE
        WITH POINTER LINE-POS.
        */
        moveSpace(RPT_LINE);
        move(4, LINE_POS);
        
        /*concat("A").
        	concat("B").
        	withPointer(LINE_POS).into(RPT_LINE);

		concat("D").
        	concat("E").
        	withPointer(LINE_POS).into(RPT_LINE);
			*/
        
        concat(LINE_NO)
        	.concat(" ")
        	.concatDelimitedBySize(CUST_INFO)
        	.concat(INV_NO)
        	.concat(" ")
        	.concat(DATE_DUE)
        	.concat(" ")
        	.concatDelimitedDecimalPoint(BAL_DUE)
        	.withPointer(LINE_POS)
        	.into(RPT_LINE)
        	.failed();
        
		assertIfFalse(RPT_LINE.equals("   00001 J.B. SMITH     444 SPRING ST., CHICAGO, ILL.      A14275 10/22/76 $2,336                                       "));
        assertIfFalse(LINE_POS.getInt() == 82);
        
        concat(" Le chat mange la souris").
        withPointer(LINE_POS).into(RPT_LINE);
        assertIfFalse(RPT_LINE.equals("   00001 J.B. SMITH     444 SPRING ST., CHICAGO, ILL.      A14275 10/22/76 $2,336 Le chat mange la souris               "));
        assertIfFalse(LINE_POS.getInt() == 106);

        concat(" et ne se fait pas attraper").	// Too long !
        withPointer(LINE_POS).into(RPT_LINE);
        assertIfFalse(RPT_LINE.equals("   00001 J.B. SMITH     444 SPRING ST., CHICAGO, ILL.      A14275 10/22/76 $2,336 Le chat mange la souris et ne se fait "));
        assertIfFalse(LINE_POS.getInt() == 121);	// should be 120 ?
        
        
        
        
        
//        
//        
//        moveSpace(RPT_LINE);
//        move(4, LINE_POS);
//        
//        /*concat("A").
//        	concat("B").
//        	withPointer(LINE_POS).into(RPT_LINE);
//
//		concat("D").
//        	concat("E").
//        	withPointer(LINE_POS).into(RPT_LINE);
//			*/
//        
//        concat(LINE_NO)
//        	.concat(" ")
//        	.concatDelimitedBySize(CUST_INFO)
//        	.concat(INV_NO)
//        	.concat(" ")
//        	.concat(DATE_DUE)
//        	.concat(" ")
//        	.concatDelimitedDecimalPoint(BAL_DUE)
//        	.into(RPT_LINE)
//        	.withPointer(LINE_POS);
//        
//		assertIfFalse(RPT_LINE.equals("   00001 J.B. SMITH     444 SPRING ST., CHICAGO, ILL.      A14275 10/22/76 $2,336                                       "));
//        assertIfFalse(LINE_POS.getInt() == 82);
//        
//        concat(" Le chat mange la souris").
//        withPointer(LINE_POS).into(RPT_LINE);
//        assertIfFalse(RPT_LINE.equals("   00001 J.B. SMITH     444 SPRING ST., CHICAGO, ILL.      A14275 10/22/76 $2,336 Le chat mange la souris               "));
//        assertIfFalse(LINE_POS.getInt() == 106);
//
//        concat(" et ne se fait pas attraper").	// Too long !
//        withPointer(LINE_POS).into(RPT_LINE);
//        assertIfFalse(RPT_LINE.equals("   00001 J.B. SMITH     444 SPRING ST., CHICAGO, ILL.      A14275 10/22/76 $2,336 Le chat mange la souris et ne se fait "));
//        assertIfFalse(LINE_POS.getInt() == 121);	// should be 120 ?
//        
        
	}
}
