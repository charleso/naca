import nacaLib.program.* ;
import nacaLib.varEx.* ;
import nacaLib.callPrg.CalledProgram;
public class Ifaai extends CalledProgram                                        // (1)  IDENTIFICATION DIVISION.
{
	                                                                            // (2)  PROGRAM-ID.           IFAAI .  
	                                                                            // (3)  ENVIRONMENT DIVISION.
	                                                                            // (4)  CONFIGURATION SECTION.
	                                                                            // (5) 
	                                                                            // (6)  DATA DIVISION.
	                                                                            // (7)  FILE SECTION.
	                                                                            // (8) 
	DataSection workingstoragesection = declare.workingStorageSection() ;       // (9)  WORKING-STORAGE SECTION.
	Var wx51_Lost_Prod_Match = declare.level(1).picX(10).value("A  B*C D E")    // (10)  01  WX51-LOST-PROD-MATCH             PIC X(10) VALUE 'A  B*C D E'
		.var() ;
	Var w40_Counter = declare.level(1).picS9(8).comp().var() ;                  // (11)  01  W40-COUNTER                      PIC S9(8) COMP-5.
	                                                                            // (12) 
	                                                                            // (13)  LINKAGE SECTION.
	                                                                            // (14) 
	                                                                            // (15)  PROCEDURE DIVISION.
	                                                                            // (16) 
	Section a000_Main = new Section(this, false);                               // (17)  A000-MAIN SECTION.
	Paragraph a000 = new Paragraph(this);                                       // (18)  A000.
	public void a000() {
		                                                                        // (19) 
		moveZero(w40_Counter) ;                                                 // (20)      MOVE ZERO                      TO W40-COUNTER.
		inspectTallying(wx51_Lost_Prod_Match).countAll("*").to(w40_Counter);    // (21)      INSPECT WX51-LOST-PROD-MATCH
		                                                                        // (22)              TALLYING W40-COUNTER
		                                                                        // (23)              FOR ALL '*'.
		display("Count: " + val(w40_Counter)) ;                                 // (24)      DISPLAY 'Count: ', W40-COUNTER
		                                                                        // (25) 
		moveZero(w40_Counter) ;                                                 // (26)      MOVE ZERO                      TO W40-COUNTER.
		inspectTallying(wx51_Lost_Prod_Match).forChars().after("*")             // (27)      INSPECT WX51-LOST-PROD-MATCH
			.to(w40_Counter);
		                                                                        // (28)              TALLYING W40-COUNTER
		                                                                        // (29)              FOR CHARACTERS
		                                                                        // (30)              AFTER INITIAL '*'
		display("Count: " + val(w40_Counter)) ;                                 // (31)      DISPLAY 'Count: ', W40-COUNTER
		                                                                        // (32) 
		moveZero(w40_Counter) ;                                                 // (33)      MOVE ZERO                      TO W40-COUNTER.
		inspectTallying(wx51_Lost_Prod_Match).countAll(" ")                     // (34)      INSPECT WX51-LOST-PROD-MATCH
			.after("*").to(w40_Counter);
		                                                                        // (35)              TALLYING W40-COUNTER
		                                                                        // (36)              FOR ALL SPACES
		                                                                        // (37)              AFTER INITIAL '*'
		display("Count: " + val(w40_Counter)) ;                                 // (38)      DISPLAY 'Count: ', W40-COUNTER
		                                                                        // (39) 
		stopRun();                                                              // (40)      STOP RUN RETURNING 0.
	}
	Paragraph a000_Exit = new Paragraph(this);                                  // (41)  A000-EXIT.
	public void a000_Exit() {
	}
}
                                                                                // (42)      EXIT.
