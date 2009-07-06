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

public class TestUnstring extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	// See http://publibz.boulder.ibm.com/cgi-bin/bookmgr_OS390/BOOKS/IGYPG205/1.6.2.1?DT=20000925141919
	// and http://publibz.boulder.ibm.com/cgi-bin/bookmgr_OS390/BOOKS/IGYPG205/1.6.2.1.1?SHELF=&DT=20000925141919&CASE=
	
    // Record to be acted on by the UNSTRING statement:
      Var INV_RCD = declare.level(1).var();
          Var CONTROL_CHARS = declare.level(5).picX(2).var();
          Var ITEM_INDENT = declare.level(5).picX(20).var();
          Var FILLER$01 = declare.level(5).picX().var();
          Var INV_CODE = declare.level(5).picX(10).var();
          Var FILLER$02 = declare.level(5).picX().var();
          Var NO_UNITS = declare.level(5).picX(6).var();
          Var FILLER$03 = declare.level(5).picX().var();
          Var PRICE_PER_M = declare.level(5).pic9(5).var();
          Var FILLER$04 = declare.level(5).picX().var();
          Var RTL_AMT = declare.level(5).pic9(6, 2).var();
     
     //  UNSTRING receiving field for printed output:
      Var DISPLAY_REC = declare.level(1).var();
          Var INV_NO = declare.level(5).picX(6).var();
          Var FILLER$05 = declare.level(5).picX().valueSpaces().var();
          Var ITEM_NAME = declare.level(5).picX(20).var();
          Var FILLER$06 = declare.level(5).picX().valueSpaces().var();
          Var DISPLAY_DOLS = declare.level(5).pic9(6).var();
     
     // UNSTRING receiving field for further processing:
      Var WORK_REC = declare.level(1).var();
      	Var M_UNITS = declare.level(5).pic9(6).var();
      	Var FIELD_A = declare.level(5).pic9(6).var();
      	Var WK_PRICE = declare.level(5).redefines(FIELD_A).pic9(4, 2).var();
      	Var INV_CLASS = declare.level(5).picX(3).var();
     
     //  UNSTRING statement control fields
     Var DBY_1 = declare.level(77).picX().var();
     Var CTR_1 = declare.level(77).picS9(3).var();
     Var CTR_2 = declare.level(77).picS9(3).var();
     Var CTR_3 = declare.level(77).picS9(3).var();
     Var CTR_4 = declare.level(77).picS9(3).var();
     Var DLTR_1 = declare.level(77).picX().var();
     Var DLTR_2 = declare.level(77).picX().var();
     Var CHAR_CT = declare.level(77).picS9(3).var();
     Var FLDS_FILLED = declare.level(77).picS9(3).var();
	
     Var INMESSAGE = declare.level(01).picX(80).var();
     Var THEDATE = declare.level(01).picX(6).var();
     	Var THEYEAR = declare.level(5).pic9(2).var();	
     	Var THEMONTH = declare.level(5).pic9(2).var();
     	Var THEDAY = declare.level(5).pic9(2).var();
     Var HOLD_DELIM = declare.level(01).picX(80).var();
     
     Var source = declare.level(01).picX(8).var();
     Var c1 = declare.level(01).picX(3).var();
     Var c2 = declare.level(01).picX(3).var();
     Var tallyingCount = declare.level(01).pic9(2).var();
     Var pointer = declare.level(01).pic9(3).var();
     
 	Var sourceCharsItems = declare.level(01).redefines(source).var();
 		Var sourceCharsOccurs = declare.level(5).occurs(8).var();
 			Var sourceCharsItem = declare.level(10).picX(1).var();
     	

     Var ID_INFO = declare.level(01).picX(35).var();
     Var EMPLOYEE_TABLE = declare.level(01).var();
     	Var EMPLOYEE_STATS = declare.level(02).occurs(30).var();
     		Var NAME = declare.level(03).picX(40).var();
     		Var BIRTH_DATE = declare.level(03).picX(6).var();
     		Var HAIR_COLOR = declare.level(03).picX(6).var();
     		Var EYE_COLOR = declare.level(03).picX(12).var();
     		Var HEIGHT = declare.level(03).picX(2).var();

     Var SUBSCRIPTOR = declare.level(01).picX(1).var();
     Var SUBSCRIPT = declare.level(01).pic9(2).value(1).var();
     Var INCREMENT = declare.level(01).picX(1).value(";").var();
     Var CHARS = declare.level(01).picS9(4).comp().var();
     Var COMPLETE_INFO = declare.level(01).picS9(4).comp().var();
     
	Paragraph Paragraph1 = new Paragraph(this){public void run(){Paragraph1();}};void Paragraph1()
	{
		setAssertActive(true);
		
		t1();
		t2();
		t3();
		t4();
		t5();
		t6();
		t7();
		
		//keyboard();
		Test1();
		
		
		CESM.returnTrans();
	}
	
	void t1()
	{
		move("ABC*DEF*", source);
		unstring(source).delimitedBy("*").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "ABC");
		assertIfDifferent(c2.getString(), "DEF");		
		
		move("ABCDE*FG", source);
		unstring(source).delimitedBy("*").to(c1).to(c2) ;		
		assertIfDifferent(c1.getString(), "ABC");
		assertIfDifferent(c2.getString(), "FG ");		
		
		move("A*B*****", source);
		unstring(source).delimitedBy("*").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "A  ");
		assertIfDifferent(c2.getString(), "B  ");		

		
		move("*AB*CD**", source);
		unstring(source).delimitedBy("*").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "   ");
		assertIfDifferent(c2.getString(), "AB ");	
		
		move("**ABCDEEF", source);
		unstring(source).delimitedBy("*").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "   ");
		assertIfDifferent(c2.getString(), "   ");		
		
		move("A*BCDEFG", source);
		unstring(source).delimitedBy("*").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "A  ");
		assertIfDifferent(c2.getString(), "BCD");
		
		move("ABC**DEF", source);
		unstring(source).delimitedBy("*").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "ABC");
		assertIfDifferent(c2.getString(), "   ");		
		
		move("A******B", source);
		unstring(source).delimitedBy("*").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "A  ");
		assertIfDifferent(c2.getString(), "   ");		
	}
	
	void t2()
	{
		move("ABC*DEF*", source);
		unstring(source).delimitedByAll("*").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "ABC");
		assertIfDifferent(c2.getString(), "DEF");	

		move("ABC**DEF", source);
		unstring(source).delimitedByAll("*").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "ABC");
		assertIfDifferent(c2.getString(), "DEF");	

		move("A******F", source);
		unstring(source).delimitedByAll("*").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "A  ");
		assertIfDifferent(c2.getString(), "F  ");	
		
		move("A*F*****", source);
		unstring(source).delimitedByAll("*").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "A  ");
		assertIfDifferent(c2.getString(), "F  ");	
		
		move("A*CDEFG", source);
		unstring(source).delimitedByAll("*").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "A  ");
		assertIfDifferent(c2.getString(), "CDE");	
	}
	
	void t3()
	{
		move("ABC**DEF", source);
		unstring(source).delimitedByAll("**").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "ABC");
		assertIfDifferent(c2.getString(), "DEF");	

		move("AB**DE**", source);
		unstring(source).delimitedByAll("**").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "AB ");
		assertIfDifferent(c2.getString(), "DE ");	

		move("A***D***", source);
		unstring(source).delimitedByAll("**").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "A  ");
		assertIfDifferent(c2.getString(), "*D ");	

		move("A*******", source);
		unstring(source).delimitedByAll("**").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "A  ");
		assertIfDifferent(c2.getString(), "*  ");	
	}
	
	void t4()
	{
		move(0, tallyingCount);
		move("ABCDEFGH", source);
		move("xxx", c1);
		move("yyy", c2);
		unstring(source).delimitedByAll("*").tallying(tallyingCount).to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "ABC");
		assertIfDifferent(c2.getString(), "yyy");
		assertIfFalse(tallyingCount.getInt() == 1);	

		move(0, tallyingCount);
		move("AB*DEFGH", source);
		unstring(source).delimitedByAll("*").tallying(tallyingCount).to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "AB ");
		assertIfDifferent(c2.getString(), "DEF");
		assertIfFalse(tallyingCount.getInt() == 2);

		move(0, tallyingCount);
		move("AB*DEFG*", source);
		unstring(source).delimitedByAll("*").tallying(tallyingCount).to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "AB ");
		assertIfDifferent(c2.getString(), "DEF");
		assertIfFalse(tallyingCount.getInt() == 2);

		move(0, tallyingCount);
		move("AB*DE*FG", source);
		unstring(source).delimitedByAll("*").tallying(tallyingCount).to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "AB ");
		assertIfDifferent(c2.getString(), "DE ");	
		assertIfFalse(tallyingCount.getInt() == 2);		
	}
	

	void t5()
	{
//      DELIMITED BY "," OR INCREMENT
//      INTO NAME (SUBSCRIPT)
//            BIRTH-DATE (SUBSCRIPT)
//            HAIR-COLOR (SUBSCRIPT)
//            EYE-COLOR (SUBSCRIPT)
//            HEIGHT (SUBSCRIPT)
//      DELIMITER IN SUBSCRIPTOR
//      WITH POINTER CHARS
//      TALLYING IN COMPLETE-INFO
//      ON OVERFLOW PERFORM FIND-CAUSE.

		move("WILSON JAMES,030250,BLONDE,BLUE,59;", ID_INFO);
		move(1, SUBSCRIPT);
        move(1, CHARS);
        move(0, COMPLETE_INFO);
        
        unstring(ID_INFO).delimitedBy(",").delimitedBy(";")
	        .withPointer(CHARS)
	        .tallying(COMPLETE_INFO)
	        .to(NAME.getAt(SUBSCRIPT))
	        .to(BIRTH_DATE.getAt(SUBSCRIPT))
	        .to(HAIR_COLOR.getAt(SUBSCRIPT))
	        .to(EYE_COLOR.getAt(SUBSCRIPT))
	        .to(HEIGHT.getAt(SUBSCRIPT));

        assertIfDifferent(NAME.getString(), "WILSON JAMES                            ");
        assertIfDifferent(BIRTH_DATE.getString(), "030250");
        assertIfDifferent(HAIR_COLOR.getString(), "BLONDE");
        assertIfDifferent(EYE_COLOR.getString(), "BLUE        ");
        assertIfDifferent(HEIGHT.getString(), "59");
        assertIfFalse(CHARS.getInt() == 36);
        assertIfFalse(COMPLETE_INFO.getInt() == 5);
 	}
	
	Paragraph Test1 = new Paragraph(this){public void run(){Test1();}};void Test1()
	{
		move(3, CHAR_CT);
		move(".", DBY_1); 
		move(0, FLDS_FILLED);
		//    000000000111111111122222222223333333333444444444455555555556
		//    123456789012345678901234567890123456789012345678901234567890
		move("ZYFOUR-PENNY-NAILS     707890/BBA 475120 00122 000379.50", INV_RCD);

		/* Move subfields of INV-RCD to the subfields of DISPLAY-REC and WORK-REC:
		  UNSTRING INV-RCD
		    DELIMITED BY ALL SPACES OR "/" OR DBY-1
		    INTO ITEM-NAME  COUNT IN CTR-1
		       INV-NO  DELIMITER IN DLTR-1  COUNT IN CTR-2
		       INV-CLASS
		       M-UNITS  COUNT IN CTR-3
		       FIELD-A
		       DISPLAY-DOLS  DELIMITER IN DLTR-2  COUNT IN CTR-4
		    WITH POINTER CHAR-CT
		    TALLYING IN FLDS-FILLED
		    ON OVERFLOW GO TO UNSTRING-COMPLETE.
		 */
		 
		if(unstring(INV_RCD)
			.delimitedByAll(" ").delimitedBy("/").delimitedBy(DBY_1)
			.tallying(FLDS_FILLED)
			.withPointer(CHAR_CT)
			.to(ITEM_NAME, null, CTR_1)				// ZYFOUR-PENNY-NAILS
			.to(INV_NO, DLTR_1, CTR_2)
			.to(INV_CLASS, null, null)
			.to(M_UNITS, null, CTR_3)
			.to(FIELD_A, null, null)
			.to(DISPLAY_DOLS, DLTR_2, CTR_4)
			.failed())
		{
			
		}			
         
		assertIfDifferent(ITEM_NAME.getString(), "FOUR-PENNY-NAILS    ");	// With padding added
		assertIfDifferent(CTR_1.getInt(), 16);	// padding
		 
		assertIfDifferent(INV_NO.getString(), "707890");
		assertIfDifferent(DLTR_1.getString(), "/");
		assertIfDifferent(CTR_2.getInt(), 6);
		 
		assertIfDifferent(INV_CLASS.getString(), "BBA");
		 
		assertIfDifferent(M_UNITS.getString(), "475120");
		assertIfDifferent(CTR_3.getInt(), 6);
		 
		assertIfDifferent(FIELD_A.getString(), "000122");
         
		assertIfDifferent(DISPLAY_DOLS.getString(), "000379");
		assertIfDifferent(DLTR_2.getString(), ".");
		assertIfDifferent(CTR_4.getInt(), 6); 
         
		// assertIfDifferent(DISPLAY_REC.getString(), "707890 FOUR-PENNY-NAILS            000379");
		assertIfDifferent(WORK_REC.getString(), "475120000122BBA");
		//assertIfDifferent(CHAR_CT.getInt(), 55); 	// PJD: should work !!!!
		assertIfDifferent(FLDS_FILLED.getInt(), 6); 
	}
	
	void t6()
	{
		move("6/13/87", INMESSAGE);
		if(	unstring(INMESSAGE).delimitedBy("-").delimitedBy("/").delimitedByAll(" ")
			.to(THEMONTH, HOLD_DELIM, null)
			.to(THEDAY, HOLD_DELIM, null)
			.to(THEYEAR, HOLD_DELIM, null)
			.failed())
		{
			moveAll("0", THEDATE);
		}
		inspectReplacing(THEDATE).after(" ").by("0");
		assertIfDifferent(THEDATE.getString(), "870613");
			
		move("6-13-87", INMESSAGE);
		if(	unstring(INMESSAGE).delimitedBy("-").delimitedBy("/").delimitedByAll(" ")
			.to(THEMONTH, HOLD_DELIM, null)
			.to(THEDAY, HOLD_DELIM, null)
			.to(THEYEAR, HOLD_DELIM, null)
			.failed())
		{
			moveAll("0", THEDATE);
		}
		inspectReplacing(THEDATE).after(" ").by("0");
		assertIfDifferent(THEDATE.getString(), "870613");

		move("6-13 87", INMESSAGE);
		if(	unstring(INMESSAGE).delimitedBy("-").delimitedBy("/").delimitedByAll(" ")
			.to(THEMONTH, HOLD_DELIM, null)
			.to(THEDAY, HOLD_DELIM, null)
			.to(THEYEAR, HOLD_DELIM, null)
			.failed())
		{
			moveAll("0", THEDATE);
		}
		inspectReplacing(THEDATE).after(" ").by("0");
		assertIfDifferent(THEDATE.getString(), "870613");

		move("6-13/87/2", INMESSAGE);
		if(	unstring(INMESSAGE).delimitedBy("-").delimitedBy("/").delimitedByAll(" ")
			.to(THEMONTH, HOLD_DELIM, null)
			.to(THEDAY, HOLD_DELIM, null)
			.to(THEYEAR, HOLD_DELIM, null)
			.failed())
		{
			moveAll("0", THEDATE);
		}
		inspectReplacing(THEDATE).after(" ").by("0");
		assertIfDifferent(THEDATE.getString(), "000000");

		move("1-2-3", INMESSAGE);
		if(	unstring(INMESSAGE).delimitedBy("-").delimitedBy("/").delimitedByAll(" ")
			.to(THEMONTH, HOLD_DELIM, null)
			.to(THEDAY, HOLD_DELIM, null)
			.to(THEYEAR, HOLD_DELIM, null)
			.failed())
		{
			moveAll("0", THEDATE);
		}
		inspectReplacing(THEDATE).after(" ").by("0");
		assertIfDifferent(THEDATE.getString(), "030102");
	}
	
	void t7()
	{
//	     Var sourceCharsGroup = declare.level(01).var();
//	     	Var sourceChars = declare.level(05).picX(8).var();
//	     	Var sourceCharsItems = declare.level(05).redefines(sourceChars).var();
//	     		Var sourceCharsOccurs = declare.level(10).occurs(8).var();
//	     			Var sourceCharsItem = declare.level(15).picX(1).var();
		move("xxx", c1);
		move("yyy", c2);
		move("ABC*DEF*", source);
		move("\u00a2", sourceCharsItem.getAt(4));
		unstring(source).delimitedBy("*").delimitedBy("\u00a2").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "ABC");
		assertIfDifferent(c2.getString(), "DEF");	

		move("\u00a2", sourceCharsItem.getAt(5));
		unstring(source).delimitedBy("*").delimitedByAll("\u00a2").to(c1).to(c2) ;
		assertIfDifferent(c1.getString(), "ABC");
		assertIfDifferent(c2.getString(), "EF ");	

	}
	
	void keyboard()
	{
		for(int n=0; n<10; n++)
		{
			display("Enter a date: ");
			accept(INMESSAGE); 
			if(	unstring(INMESSAGE).delimitedBy("-").delimitedBy("/").delimitedByAll(" ")
			.to(THEMONTH, HOLD_DELIM, null)
			.to(THEDAY, HOLD_DELIM, null)
			.to(THEYEAR, HOLD_DELIM, null)
			.failed())
			{
				moveAll("0", THEDATE);
			}
			inspectReplacing(THEDATE).after(" ").by("0");
			display("Date after unstring="+THEDATE.getDottedSignedString());			
		}
		
//    UNSTRING INMESSAGE 
//      DELIMITED BY "-" OR "/" OR ALL " " 
//        INTO THEMONTH DELIMITER IN HOLD-DELIM 
//             THEDAY DELIMITER IN HOLD-DELIM 
//             THEYEAR DELIMITER IN HOLD-DELIM 
//      ON OVERFLOW MOVE ALL "0" TO THEDATE. 
//    INSPECT THEDATE REPLACING ALL " " BY "0". 
//    DISPLAY THEDATE.
	}
}
