import nacaLib.program.* ;
import nacaLib.varEx.* ;
import nacaLib.batchPrgEnv.BatchProgram;
public class BATCH1 extends BatchProgram                                        // (1)      IDENTIFICATION DIVISION.
{
	// =================================================================        // (2)
	                                                                            // (3)  PROGRAM-ID.   BATCH1.
	                                                                            // (4)  AUTHOR.       XXXXXXXXX.
	                                                                            // (5)  DATE-WRITTEN. 2008.
	                                                                            // (6) 
	// REMARKS.                                                                 // (7)
	//                                                                          // (8)
	//  Programme demo BATCH                                                    // (9)
	//                                                                          // (10)
	//                                                                          // (11)
	                                                                            // (12)  ENVIRONMENT DIVISION. 
	// =================================================================        // (13)
	                                                                            // (14)  CONFIGURATION SECTION.
	// -----------------------------------------------------------------        // (15)
	                                                                            // (16)  SOURCE-COMPUTER. IBM-3081.
	                                                                            // (17)  OBJECT-COMPUTER. IBM-3081.
	                                                                            // (18) 
	                                                                            // (19)  INPUT-OUTPUT SECTION.
	// -----------------------------------------------------------------        // (20)
	                                                                            // (21)  FILE-CONTROL.
	                                                                            // (22)      SELECT FILEIN  ASSIGN TO UT-S-FILEIN.
	                                                                            // (23)      SELECT FILEOUT ASSIGN TO UT-S-FILEOUT.
	                                                                            // (24)  DATA DIVISION. 
	// =================================================================           (25) *
	DataSection filesection = declare.fileSection() ;                           // (26)  FILE SECTION.
	// -----------------------------------------------------------------           (27) *
	FileDescriptor filein = declare.file("FILEIN") ;                            // (28)  FD  FILEIN
	                                                                            // (29)      LABEL RECORDS STANDARD
	                                                                            // (30)      BLOCK 0
	                                                                            // (31)      RECORD 69
	                                                                            // (32)      RECORDING F.
	Var filein_Z = declare.level(1).var() ;                                     // (33)  01  FILEIN-Z.
		Var filein_Code = declare.level(5).picX(1).var() ;                      // (34)      05  FILEIN-CODE         PIC X(1).
		Var filler$2 = declare.level(5).picX(68).filler() ;                     // (35)      05  FILLER              PIC X(68).
	                                                                            // (36) 
	FileDescriptor fileout = declare.file("FILEOUT") ;                          // (37)  FD  FILEOUT
	                                                                            // (38)      LABEL RECORDS STANDARD
	                                                                            // (39)      BLOCK 0
	                                                                            // (40)      RECORD 69
	                                                                            // (41)      RECORDING F.
	Var fileout_Z = declare.level(1).picX(69).var() ;                           // (42)  01  FILEOUT-Z               PIC X(69).
	                                                                            // (43) 
	DataSection workingstoragesection = declare.workingStorageSection() ;       // (44)  WORKING-STORAGE SECTION.
	// ------------------------                                                    (45) *
	Var cpt_In = declare.level(77).picS9(7).comp3().valueZero().var() ;         // (46)  77  CPT-IN                  PIC S9(7) COMP-3   VALUE ZERO.
	Var cpt_Out = declare.level(77).picS9(7).comp3().valueZero().var() ;        // (47)  77  CPT-OUT                 PIC S9(7) COMP-3   VALUE ZERO.
	                                                                            // (48) 
	Var fin_Trait = declare.level(77).picX(1).valueSpaces().var() ;             // (49)  77  FIN-TRAIT               PIC X              VALUE SPACE.
	                                                                            // (50)  
	Var sys_Time = declare.level(1).pic9(8).valueZero().var() ;                 // (51)  01  SYS-TIME                PIC 9(8)          VALUE ZEROS.
	Var filler$1 = declare.level(1).redefines(sys_Time).filler() ;              // (52)  01  FILLER REDEFINES SYS-TIME.
		Var sys_Time1 = declare.level(3).pic9(7).var() ;                        // (53)      03 SYS-TIME1            PIC 9(7).
		Var sys_Time2 = declare.level(3).pic9(1).var() ;                        // (54)      03 SYS-TIME2            PIC 9.
	                                                                            // (55)  
	MSGZONE msgzone = MSGZONE.Copy(this) ;                                      // (56)      COPY MSGZONE.
	// Relocated INPUT-OUTPUT SECTION / FILE CONTROL
	                                                                            // (57)      
	//                                                                             (58) /
	public void procedureDivision() {                                           // (59)  PROCEDURE DIVISION.
		// =================================================================    // (60)
		                                                                        // (61) 
		openInput(filein) ;                                                     // (62)      OPEN INPUT  FILEIN
		openOutput(fileout) ;
		                                                                        // (63)           OUTPUT FILEOUT
		                                                                        // (64)           
		move(getTimeBatch(), sys_Time);                                         // (65)      ACCEPT SYS-TIME FROM TIME
		                                                                        // (66)      
		display("DEBUG - TIME : " + val(sys_Time1)) ;                           // (67)      DISPLAY 'DEBUG - TIME : ' SYS-TIME1
		                                                                        // (68)      
		perform(read_Filein) ;                                                  // (69)      PERFORM READ-FILEIN     
		                                                                        // (70)      
		while (isDifferent(fin_Trait, "F")) {                                   // (71)      PERFORM TRAITEMENT UNTIL FIN-TRAIT = 'F'
			perform(traitement) ;
		}
		                                                                        // (72) 
		console().display("STAT FILEIN  - READ RECORDS   : " + val(cpt_In)) ;   // (73)      DISPLAY 'STAT FILEIN  - READ RECORDS   : '
		                                                                        // (74)               CPT-IN           UPON CONSOLE.
		console().display("STAT FILEOUT - WRITE RECORDS  : " + val(cpt_Out)) ;  // (75)      DISPLAY 'STAT FILEOUT - WRITE RECORDS  : '
		                                                                        // (76)               CPT-OUT          UPON CONSOLE.
		                                                                        // (77)               
		close(filein);                                                          // (78)      CLOSE FILEIN
		close(fileout);
		                                                                        // (79)            FILEOUT
		                                                                        // (80)            
		stopRun();                                                              // (81)      STOP RUN.
	}
	                                                                            // (82)      
	Paragraph read_Filein = new Paragraph(this);                                // (83)  READ-FILEIN.
	public void read_Filein() {
		// -----------------------------------------------------------------       (84) *
		if (read(filein).atEnd()) {                                             // (85)      READ FILEIN
			move("F", fin_Trait);                                               // (86)           AT END MOVE 'F' TO FIN-TRAIT.    
		}
	}
	                                                                            // (87)      
	Paragraph traitement = new Paragraph(this);                                 // (88)  TRAITEMENT.
	public void traitement() {
		// -----------------------------------------------------------------       (89) *
		inc(cpt_In) ;                                                           // (90)      ADD 1 TO CPT-IN
		                                                                        // (91)      EVALUATE FILEIN-CODE
		if (isEqual(filein_Code, "1"))
		{                                                                       // (92)        WHEN '1'           
			display("DEBUG 1 - " + val(filein_Z)) ;                             // (93)          DISPLAY 'DEBUG 1 - ' FILEIN-Z
			writeFrom(fileout, filein_Z) ;                                      // (94)          WRITE FILEOUT-Z FROM FILEIN-Z
			inc(cpt_Out) ;                                                      // (95)          ADD 1 TO CPT-OUT
		}
		else if (isEqual(filein_Code, "2"))
		{                                                                       // (96)        WHEN '2'
			move("0001", msgzone.msg_No);                                       // (97)          MOVE '0001'     TO MSG-NO
			call(CALLMSG.class).using(msgzone.msg_Zone).executeCall() ;         // (98)          CALL 'CALLMSG'  USING MSG-ZONE
			display("DEBUG 2 - " + val(msgzone.msg_Text)) ;                     // (99)          DISPLAY 'DEBUG 2 - ' MSG-TEXT  
		}
		                                                                        // (100)      END-EVALUATE
		perform(read_Filein) ;                                                  // (101)      PERFORM READ-FILEIN.
	}
}
