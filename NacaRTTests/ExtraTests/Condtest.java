import nacaLib.program.* ;
import nacaLib.varEx.* ;
import nacaLib.callPrg.CalledProgram;
public class Condtest extends CalledProgram                                     // (1)  IDENTIFICATION DIVISION.
{
	                                                                            // (2)  PROGRAM-ID.           CONDTEST .  
	                                                                            // (3)  ENVIRONMENT DIVISION.
	                                                                            // (4)  CONFIGURATION SECTION.
	                                                                            // (5) 
	                                                                            // (6)  DATA DIVISION.
	                                                                            // (7)  FILE SECTION.
	                                                                            // (8) 
	DataSection workingstoragesection = declare.workingStorageSection() ;       // (9)  WORKING-STORAGE SECTION.
	Var mssros_Month = declare.level(1).occurs(2).var() ;                       // (10)  01  MSSROS-MONTH                     OCCURS 2.
		Var mssros_Day = declare.level(3).occurs(31).var() ;                    // (11)      03  MSSROS-DAY                   OCCURS 31.
			Var mssros_Emp_Present = declare.level(5).picX(1).var() ;           // (12)          05  MSSROS-EMP-PRESENT       PIC X(01).
			Var mssros_Pub_Hol_Yn = declare.level(5).picX(1).var() ;            // (13)          05  MSSROS-PUB-HOL-YN        PIC X(01).
				Cond mssros_Pub_Hol = declare.condition().value("Y").var() ;    // (14)              88  MSSROS-PUB-HOL       VALUE 'Y'.
				Cond mssros_Not_Pub_Hol = declare.condition().value("N").var() ;	// (15)              88  MSSROS-NOT-PUB-HOL   VALUE 'N'.
	                                                                            // (16) 
	                                                                            // (17)  LINKAGE SECTION.
	                                                                            // (18) 
	                                                                            // (19)  PROCEDURE DIVISION.
	                                                                            // (20) 
	Section a000_Main = new Section(this, false);                               // (21)  A000-MAIN SECTION.
	Paragraph a000 = new Paragraph(this);                                       // (22)  A000.
	public void a000() {
		                                                                        // (23) 
		move("X", mssros_Emp_Present.getAt(1, 10));                             // (24)      MOVE "X" TO MSSROS-EMP-PRESENT (1, 10)
		move("Y", mssros_Pub_Hol_Yn.getAt(1, 10));                              // (25)      MOVE "Y" TO MSSROS-PUB-HOL-YN (1, 10)
		display(mssros_Emp_Present.getAt(1, 10)) ;                              // (26)      DISPLAY MSSROS-EMP-PRESENT (1, 10)
		if (is(mssros_Pub_Hol.getAt(1, 10))) {                                  // (27)      IF MSSROS-PUB-HOL (1, 10)
			display("Y") ;                                                      // (28)         DISPLAY "Y".
		}
		if (is(mssros_Not_Pub_Hol.getAt(1, 10))) {                              // (29)      IF MSSROS-NOT-PUB-HOL (1, 10)
			display("N") ;                                                      // (30)         DISPLAY "N".
		}
		                                                                        // (31)      
		stopRun();                                                              // (32)      STOP RUN RETURNING 0.
	}
	Paragraph a000_Exit = new Paragraph(this);                                  // (33)  A000-EXIT.
	public void a000_Exit() {
	}
}
                                                                                // (34)      EXIT.
