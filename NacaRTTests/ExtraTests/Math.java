import nacaLib.program.* ;
import nacaLib.varEx.* ;
import nacaLib.callPrg.CalledProgram;
public class Math extends CalledProgram                                         // (1)  IDENTIFICATION DIVISION.
{
	                                                                            // (2)  PROGRAM-ID.           MATH .  
	                                                                            // (3)  ENVIRONMENT DIVISION.
	                                                                            // (4)  CONFIGURATION SECTION.
	                                                                            // (5) 
	                                                                            // (6)  DATA DIVISION.
	                                                                            // (7)  FILE SECTION.
	                                                                            // (8) 
	DataSection workingstoragesection = declare.workingStorageSection() ;       // (9)  WORKING-STORAGE SECTION.
	Var w50_Higher_Freq1 = declare.level(1).pic9(9).var() ;                     // (10)  01  W50-HIGHER-FREQ1                 PIC 9(9).
	Var w50_Sched_Freq_1_9 = declare.level(1).pic9(9).var() ;                   // (11)  01  W50-SCHED-FREQ-1-9               PIC 9(9).
	Var w50_Mult = declare.level(1).picS9(5).comp3().var() ;                    // (12)  01  W50-MULT                         PIC S9(5) COMP-3.
	Var w50_Rem = declare.level(1).picS9(8).comp3().var() ;                     // (13)  01  W50-REM                          PIC S9(8) COMP-3.
	Var w50_Meas_Value_9 = declare.level(1).picS9(7,6).comp3().var() ;          // (14)  01  W50-MEAS-VALUE-9                 PIC S9(7)V9(6) COMP-3.
	Var w50_Derived_Val_1_9 = declare.level(1).picS9(7,6).comp3().var() ;       // (15)  01  W50-DERIVED-VAL-1-9              PIC S9(7)V9(6) COMP-3.
	Var w50_Derived_Val_2_9 = declare.level(1).picS9(7,6).comp3().var() ;       // (16)  01  W50-DERIVED-VAL-2-9              PIC S9(7)V9(6) COMP-3.
	                                                                            // (17) 
	                                                                            // (18)  LINKAGE SECTION.
	                                                                            // (19) 
	                                                                            // (20)  PROCEDURE DIVISION.
	                                                                            // (21) 
	Section a000_Main = new Section(this, false);                               // (22)  A000-MAIN SECTION.
	Paragraph a000 = new Paragraph(this);                                       // (23)  A000.
	public void a000() {
		                                                                        // (24) 
		move(10, w50_Higher_Freq1);                                             // (25)      MOVE 10       TO W50-HIGHER-FREQ1.
		move(3, w50_Sched_Freq_1_9);                                            // (26)      MOVE 3        TO W50-SCHED-FREQ-1-9.
		divide(w50_Higher_Freq1, w50_Sched_Freq_1_9)                            // (27)      DIVIDE W50-HIGHER-FREQ1
			.toRounded(w50_Mult, w50_Rem) ;
		                                                                        // (28)          BY        W50-SCHED-FREQ-1-9
		                                                                        // (29)          GIVING    W50-MULT ROUNDED
		                                                                        // (30)          REMAINDER W50-REM.
		display(val(w50_Mult) + " " + val(w50_Rem)) ;                           // (31)      DISPLAY W50-MULT, ' ', W50-REM
		                                                                        // (32) 
		move("5.123", w50_Derived_Val_1_9);                                     // (33)      MOVE 5.123    TO W50-DERIVED-VAL-1-9
		move(2, w50_Derived_Val_2_9);                                           // (34)      MOVE 2        TO W50-DERIVED-VAL-2-9
		compute(pow(w50_Derived_Val_1_9,                                        // (35)      COMPUTE W50-MEAS-VALUE-9 = W50-DERIVED-VAL-1-9 **
			w50_Derived_Val_2_9), w50_Meas_Value_9); 
		                                                                        // (36)                                 W50-DERIVED-VAL-2-9
		display(w50_Meas_Value_9) ;                                             // (37)      DISPLAY W50-MEAS-VALUE-9
		                                                                        // (38) 
		compute(pow(multiply(w50_Derived_Val_1_9, 10),                          // (39)      COMPUTE W50-MEAS-VALUE-9 = W50-DERIVED-VAL-1-9 * 10 **
			w50_Derived_Val_2_9), w50_Meas_Value_9); 
		                                                                        // (40)                                 W50-DERIVED-VAL-2-9
		display(w50_Meas_Value_9) ;                                             // (41)      DISPLAY W50-MEAS-VALUE-9
		                                                                        // (42) 
		stopRun();                                                              // (43)      STOP RUN RETURNING 0.
	}
	Paragraph a000_Exit = new Paragraph(this);                                  // (44)  A000-EXIT.
	public void a000_Exit() {
	}
}
                                                                                // (45)      EXIT.
