import nacaLib.program.* ;
import nacaLib.varEx.* ;
import nacaLib.callPrg.CalledProgram;
public class Msbtst extends CalledProgram                                       // (1)  IDENTIFICATION DIVISION.
{
	                                                                            // (2)  PROGRAM-ID.           MSBTST .
	// *  AUTHOR             Mincom.                                            // (3)
	// *  INSTALLATION       Mincom Limited                                     // (4)
	// *                     Mincom Centre                                      // (5)
	// *                     61 Wyandra Street                                  // (6)
	// *                     Teneriffe, 4005                                    // (7)
	// *                     Brisbane, QLD, Australia,                          // (8)
	// *                     Ph: +61 7 3303 3333                                // (9)
	// *                                                                        // (10)
	// *  Copyright (c) 2009, Mincom Limited, Brisbane, Australia               // (11)
	// *  All Rights Reserved.                                                  // (12)
	// *  All rights reserved                                                   // (13)
	// *                                                                        // (14)
	// *  DATE-WRITTEN       NOV-89.                                            // (15)
	// *                                                                        // (16)
	// *  PROGRAM ABSTRACT:                                                     // (17)
	// *                                                                        // (18)
	// *  This program initialises the System Control File                      // (19)
	// *  and checks certain fields to be consistent.                           // (20)
	// *                                                                        // (21)
	//                                                                          // (22)
	                                                                            // (23) 
	//                                                                          // (24)
	// ** Revision History ***                                                  // (25)
	// ** 17-Jul-09 peta                                                        // (26)
	                                                                            // (27)  ENVIRONMENT DIVISION.
	                                                                            // (28)  CONFIGURATION SECTION.
	                                                                            // (29) 
	                                                                            // (30)  DATA DIVISION.
	                                                                            // (31)  FILE SECTION.
	                                                                            // (32) 
	DataSection workingstoragesection = declare.workingStorageSection() ;       // (33)  WORKING-STORAGE SECTION.
	//  Generated Code Work Variables                                              (34) *
	Var ws_Working_Storage_Start = declare.level(1).var() ;                     // (35)  01  WS-WORKING-STORAGE-START.
		Var filler$1 = declare.level(5).picX(30)                                // (36)      05  FILLER                       PIC X(30) VALUE
			.value("*** WORKING STORAGE SECTION **").filler() ;
	                                                                            // (37)          '*** WORKING STORAGE SECTION **'.
	                                                                            // (38) 
	Var w00_Prog_Ident = declare.level(1).var() ;                               // (39)  01  W00-PROG-IDENT.
		Var w00_Prog_Name = declare.level(5).picX(8).value("MSB000").var() ;    // (40)      05  W00-PROG-NAME                PIC X(8)  VALUE 'MSB000'.
		Var w00_Module = declare.level(5).picX(4).value("3001").var() ;         // (41)      05  W00-MODULE                   PIC X(4)  VALUE '3001'.
		Var w00_Program_Version = declare.level(5).picX(4).value("ADH").var() ; // (42)      05  W00-PROGRAM-VERSION          PIC X(4)  VALUE 'ADH'.
	                                                                            // (43) 
	Var w50_Work_Fields = declare.level(1).var() ;                              // (44)  01  W50-WORK-FIELDS.
		Var filler$2 = declare.level(5).picX(30)                                // (45)      05  FILLER                        PIC X(30) VALUE
			.value("*** W50-WORK-FIELDS *********").filler() ;
		                                                                        // (46)          '*** W50-WORK-FIELDS *********'.
		Var w50_Amt_1 = declare.level(5).pic9(2).value(7).var() ;               // (47)      05  W50-AMT-1                    PIC 9(2) VALUE 7.
		Var w50_Amt_2 = declare.level(5).pic9(2).value(5).var() ;               // (48)      05  W50-AMT-2                    PIC 9(2) VALUE 5.
		Var w50_Amt_3 = declare.level(5).pic9(2).value(2).var() ;               // (49)      05  W50-AMT-3                    PIC 9(2) VALUE 2.
		Var w50_Amt_4 = declare.level(5).pic9(3).value(999).var() ;             // (50)      05  W50-AMT-4                    PIC 9(3) VALUE 999.
		Var w50_Amt_Total = declare.level(5).pic9(2).value(20).var() ;          // (51)      05  W50-AMT-TOTAL                PIC 9(2) VALUE 20.
		Var w50_Inspect_Literal = declare.level(5).picX(40)                     // (52)      05  W50-INSPECT-LITERAL          PIC X(40)
			.value("The lazy brown FOX jumped over the fence").var() ;
		                                                                        // (53)          VALUE 'The lazy brown FOX jumped over the fence'.
		Var w50_Unstring_Literal = declare.level(5).picX(10).value("1234567890")	// (54)      05  W50-UNSTRING-LITERAL         PIC X(10) 
			.var() ;
		                                                                        // (55)          VALUE '1234567890'.
		Var w50_Unstrung_Lit_1 = declare.level(5).picX(5).var() ;               // (56)      05  W50-UNSTRUNG-LIT-1           PIC X(5).
		Var w50_Unstrung_Lit_2 = declare.level(5).picX(3).var() ;               // (57)      05  W50-UNSTRUNG-LIT-2           PIC X(3).
		Var w50_Zed_Edited_Num = declare.level(5).pic("ZZZ,ZZZ,ZZZ,ZZZ,ZZ9.99") // (58)      05  W50-ZED-EDITED-NUM           PIC ZZZ,ZZZ,ZZZ,ZZZ,ZZ9.99.
			.var() ;
		Var w50_Dollar_Edited_Num = declare.level(5).pic("$$$,$$$,$$$,$$$,$$9.99")	// (59)      05  W50-DOLLAR-EDITED-NUM        PIC $$$,$$$,$$$,$$$,$$9.99.
			.var() ;
		Var w50_Array = declare.level(5).occurs(5).var() ;                      // (60)      05  W50-ARRAY                    OCCURS 5.
			Var w50_Array_Item = declare.level(7).picX(3).var() ;               // (61)          07  W50-ARRAY-ITEM           PIC X(3).
				Cond affirmative = declare.condition().value("YES").var() ;     // (62)              88  AFFIRMATIVE          VALUE 'YES'.
				Cond no_Way = declare.condition().value("NO ").var() ;          // (63)              88  NO-WAY               VALUE 'NO '.
	                                                                            // (64) 
	                                                                            // (65)  LINKAGE SECTION.
	                                                                            // (66) 
	                                                                            // (67)  PROCEDURE DIVISION.
	                                                                            // (68) 
	Section a000_Main = new Section(this, false);                               // (69)  A000-MAIN SECTION.
	Paragraph a000 = new Paragraph(this);                                       // (70)  A000.
	public void a000() {
		                                                                        // (71) 
		perform(subtract_Multi_Operands_Test) ;                                 // (72)      PERFORM SUBTRACT-MULTI-OPERANDS-TEST.
		                                                                        // (73) 
		perform(subtract_With_Size_Error_Test) ;                                // (74)      PERFORM SUBTRACT-WITH-SIZE-ERROR-TEST.
		                                                                        // (75) 
		perform(inspect_Converting_Tests) ;                                     // (76)      PERFORM INSPECT-CONVERTING-TESTS.
		                                                                        // (77) 
		perform(unstring_No_Delim_Test) ;                                       // (78)      PERFORM UNSTRING-NO-DELIM-TEST.
		                                                                        // (79) 
		perform(dollar_Edited_Test) ;                                           // (80)      PERFORM DOLLAR-EDITED-TEST.
		                                                                        // (81) 
		perform($88lvl_Indexed_Var_Test) ;                                      // (82)      PERFORM 88LVL-INDEXED-VAR-TEST.
		                                                                        // (83) 
		stopRun();                                                              // (84)      STOP RUN RETURNING 0.
	}
	                                                                            // (85) 
	Paragraph a000_Exit = new Paragraph(this);                                  // (86)  A000-EXIT.
	public void a000_Exit() {
	}
	                                                                            // (87)      EXIT.
	                                                                            // (88) 
	Section subtract_Multi_Operands_Test = new Section(this, false);            // (89)  SUBTRACT-MULTI-OPERANDS-TEST SECTION.
	Paragraph smot_Para = new Paragraph(this);                                  // (90)  SMOT-PARA.
	public void smot_Para() {
		                                                                        // (91) 
		//  Subtract multi-operands                                                (92) *
		display("Subtracting " + val(w50_Amt_1) + ", " + val(w50_Amt_2)         // (93)      DISPLAY 'Subtracting ' W50-AMT-1 ', ' W50-AMT-2 ' from '
			 + " from " + val(w50_Amt_Total)) ;
		                                                                        // (94)              W50-AMT-TOTAL.
		dec(w50_Amt_1, w50_Amt_2, w50_Amt_Total) ;                              // (95)      SUBTRACT W50-AMT-1 W50-AMT-2 FROM W50-AMT-TOTAL.
		display("Answer -> " + val(w50_Amt_Total)) ;                            // (96)      DISPLAY 'Answer -> ' W50-AMT-TOTAL.
		                                                                        // (97) 
		display(" ") ;                                                          // (98)      DISPLAY ' '.
	}
	                                                                            // (99) 
	Paragraph smot_Exit = new Paragraph(this);                                  // (100)  SMOT-EXIT.
	public void smot_Exit() {
	}
	                                                                            // (101)      EXIT.
	                                                                            // (102) 
	Section subtract_With_Size_Error_Test = new Section(this, false);           // (103)  SUBTRACT-WITH-SIZE-ERROR-TEST SECTION.
	Paragraph swet_Para = new Paragraph(this);                                  // (104)  SWET-PARA.
	public void swet_Para() {
		                                                                        // (105) 
		//  Subtract causing ON SIZE Error                                      // (106)
		                                                                        // (107) 
		display("Subtracting " + val(w50_Amt_3) + " from " + val(w50_Amt_4)     // (108)      DISPLAY 'Subtracting ' W50-AMT-3 ' from ' W50-AMT-4
			 + " into PIC 9(2) variable which should cause SIZE ERROR"
			 + " message.") ;
		                                                                        // (109)        ' into PIC 9(2) variable which should cause SIZE ERROR'
		                                                                        // (110)        ' message.'
		subtract(w50_Amt_4, w50_Amt_3).to(w50_Amt_Total) ;                      // (111)      SUBTRACT W50-AMT-3 FROM W50-AMT-4 GIVING W50-AMT-TOTAL
	}
	                                                                            // (112)         ON SIZE ERROR DISPLAY 'SIZE ERROR on SUBTRACT'.
	                                                                            // (113) 
	                                                                            // (114)      DISPLAY ' '.
	                                                                            // (115) 
	Paragraph swet_Exit = new Paragraph(this);                                  // (116)  SWET-EXIT.
	public void swet_Exit() {
	}
	                                                                            // (117)      EXIT.
	                                                                            // (118) 
	Section inspect_Converting_Tests = new Section(this, false);                // (119)  INSPECT-CONVERTING-TESTS SECTION.
	Paragraph ict_Para = new Paragraph(this);                                   // (120)  ICT-PARA.
	public void ict_Para() {
		                                                                        // (121) 
		//  INSPECT variable converting lowercase to UPPERCASE                  // (122)
		                                                                        // (123) 
		display("String before INSPECT CONVERTING:") ;                          // (124)      DISPLAY 'String before INSPECT CONVERTING:'.
		display(w50_Inspect_Literal) ;                                          // (125)      DISPLAY W50-INSPECT-LITERAL.
		inspectConverting(w50_Inspect_Literal).to("abcdefghijklmnopqrstuvwxyz", // (126)      INSPECT W50-INSPECT-LITERAL
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		                                                                        // (127)              CONVERTING
		                                                                        // (128)              'abcdefghijklmnopqrstuvwxyz'
		                                                                        // (129)           TO 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.
		display("String after INSPECT CONVERTING:") ;                           // (130)      DISPLAY 'String after INSPECT CONVERTING:'.
		display(w50_Inspect_Literal) ;                                          // (131)      DISPLAY W50-INSPECT-LITERAL.
		display(" ") ;                                                          // (132)      DISPLAY ' '.
	}
	                                                                            // (133) 
	Paragraph ict_Exit = new Paragraph(this);                                   // (134)  ICT-EXIT.
	public void ict_Exit() {
	}
	                                                                            // (135)      EXIT.
	                                                                            // (136) 
	Section unstring_No_Delim_Test = new Section(this, false);                  // (137)  UNSTRING-NO-DELIM-TEST SECTION.
	Paragraph undt_Para = new Paragraph(this);                                  // (138)  UNDT-PARA.
	public void undt_Para() {
		                                                                        // (139) 
		//  UNSTRING with no delimiter.                                            (140) *
		display("UNSTRING of 10 chars into 2 variables "                        // (141)      DISPLAY 'UNSTRING of 10 chars into 2 variables '
			 + " defined as PIC X(5) and PIC X(3).") ;
		                                                                        // (142)              ' defined as PIC X(5) and PIC X(3).'
		display(w50_Unstring_Literal) ;                                         // (143)      DISPLAY W50-UNSTRING-LITERAL
		unstring(w50_Unstring_Literal).to(w50_Unstrung_Lit_1)                   // (144)      UNSTRING
			.to(w50_Unstrung_Lit_2) ;
		                                                                        // (145)          W50-UNSTRING-LITERAL
		                                                                        // (146)          INTO
		                                                                        // (147)          W50-UNSTRUNG-LIT-1
		                                                                        // (148)          W50-UNSTRUNG-LIT-2.
		                                                                        // (149) 
		display(w50_Unstrung_Lit_1) ;                                           // (150)      DISPLAY W50-UNSTRUNG-LIT-1.
		display(w50_Unstrung_Lit_2) ;                                           // (151)      DISPLAY W50-UNSTRUNG-LIT-2.
		display(" ") ;                                                          // (152)      DISPLAY ' '.
	}
	                                                                            // (153) 
	Paragraph undt_Exit = new Paragraph(this);                                  // (154)  UNDT-EXIT.
	public void undt_Exit() {
	}
	                                                                            // (155)      EXIT.
	                                                                            // (156) 
	Section dollar_Edited_Test = new Section(this, false);                      // (157)  DOLLAR-EDITED-TEST SECTION.
	Paragraph det_Para = new Paragraph(this);                                   // (158)  DET-PARA.
	public void det_Para() {
		                                                                        // (159) 
		//  Test Z edited picture clause                                           (160) *
		display("Moving 6,199,876 to PIC ZZZ,ZZZ,ZZZ,ZZZ,ZZ9.99.") ;            // (161)      DISPLAY 'Moving 6,199,876 to PIC ZZZ,ZZZ,ZZZ,ZZZ,ZZ9.99.'
		move(6199876, w50_Zed_Edited_Num);                                      // (162)      MOVE 6199876 TO W50-ZED-EDITED-NUM.
		display(w50_Zed_Edited_Num) ;                                           // (163)      DISPLAY W50-ZED-EDITED-NUM.
		display(" ") ;                                                          // (164)      DISPLAY ' '.
		                                                                        // (165) 
		//  Test $ edited picture clause                                           (166) *
		display("Moving 6,199,876 to PIC $$$,$$$,$$$,$$$,$$9.99.") ;            // (167)      DISPLAY 'Moving 6,199,876 to PIC $$$,$$$,$$$,$$$,$$9.99.'
		move(6199876, w50_Dollar_Edited_Num);                                   // (168)      MOVE 6199876 TO W50-DOLLAR-EDITED-NUM.
		display(w50_Dollar_Edited_Num) ;                                        // (169)      DISPLAY W50-DOLLAR-EDITED-NUM.
		display(" ") ;                                                          // (170)      DISPLAY ' '.
	}
	                                                                            // (171) 
	Paragraph det_Exit = new Paragraph(this);                                   // (172)  DET-EXIT.
	public void det_Exit() {
	}
	                                                                            // (173)      EXIT.
	                                                                            // (174) 
	Section $88lvl_Indexed_Var_Test = new Section(this, false);                 // (175)  88LVL-INDEXED-VAR-TEST SECTION.
	Paragraph $8ivt_Para = new Paragraph(this);                                 // (176)  8IVT-PARA.
	public void $8ivt_Para() {
		                                                                        // (177) 
		//  Checking 88Lvl condition applied to indexed variable.                  (178) *
		display("Check 88-LVL condition on Indexed variable.") ;                // (179)      DISPLAY 'Check 88-LVL condition on Indexed variable.'
		move("YES", w50_Array_Item.getAt(1));                                   // (180)      MOVE 'YES' TO W50-ARRAY-ITEM (1).
		move("NO ", w50_Array_Item.getAt(3));                                   // (181)      MOVE 'NO ' TO W50-ARRAY-ITEM (3).
		if (is(affirmative.getAt(1))) {                                         // (182)      IF  AFFIRMATIVE (1)
			display("Correct - Array element (1) is YES") ;                     // (183)          DISPLAY 'Correct - Array element (1) is YES'
		}
		else {                                                                  // (184)      ELSE
			display("FAILURE - Array element (1) NOT YES") ;                    // (185)          DISPLAY 'FAILURE - Array element (1) NOT YES'
		}                                                                       // (186)      END-IF.
		display(" ") ;                                                          // (187)      DISPLAY ' '.
		                                                                        // (188) 
		if (is(no_Way.getAt(3))) {                                              // (189)      IF  NO-WAY (3)
			display("Correct - Array element (3) is NO") ;                      // (190)          DISPLAY 'Correct - Array element (3) is NO'
		}
		else {                                                                  // (191)      ELSE
			display("FAILURE - Array element (3) NOT NO") ;                     // (192)          DISPLAY 'FAILURE - Array element (3) NOT NO'
		}                                                                       // (193)      END-IF.
		display(" ") ;                                                          // (194)      DISPLAY ' '.
		                                                                        // (195) 
		if (is(no_Way.getAt(4)) || is(affirmative.getAt(4))) {                  // (196)      IF  NO-WAY (4) OR AFFIRMATIVE (4)
			display("FAILURE - Array element (4) should " + "not be YES or NO"  // (197)          DISPLAY 'FAILURE - Array element (4) should '
				) ;
		}                                                                       // (198)            'not be YES or NO'
		else {                                                                  // (199)      ELSE
			display("Correct - Array element (4) not YES "                      // (200)          DISPLAY 'Correct - Array element (4) not YES '
				 + " and not NO (not initialised)") ;
		                                                                        // (201)            ' and not NO (not initialised)'
		}                                                                       // (202)      END-IF.
		display(" ") ;                                                          // (203)      DISPLAY ' '.
	}
	                                                                            // (204) 
	Paragraph $8ivt_Exit = new Paragraph(this);                                 // (205)  8IVT-EXIT.
	public void $8ivt_Exit() {
	}
}
                                                                                // (206)      EXIT.
                                                                                // (207)   
                                                                                // (208) 
                                                                                // (209) 
