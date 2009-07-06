import nacaLib.program.* ;
import nacaLib.varEx.* ;
import nacaLib.basePrgEnv.* ;
public class VTBMSGA extends Copy {
	public static VTBMSGA Copy(BaseProgram program) {
		return new VTBMSGA(program, null);
	}
	public static VTBMSGA Copy(BaseProgram program, CopyReplacing copyReplacing) {
		return new VTBMSGA(program, copyReplacing);
	}
	public VTBMSGA(BaseProgram program, CopyReplacing copyReplacing) {
		super(program, copyReplacing);
	}
	                                                                            // (1) ******************************************************************
	                                                                            // (2) * DCLGEN TABLE(VTABMSGA)                                         *
	                                                                            // (3) * ... IS THE DCLGEN COMMAND THAT MADE THE FOLLOWING STATEMENTS   *
	                                                                            // (4) ******************************************************************
	                                                                            // (5)      EXEC SQL DECLARE VTABMSGA TABLE
	                                                                            // (6)      ( NO                             CHAR(4) NOT NULL,
	                                                                            // (7)        TEXT                           CHAR(78) NOT NULL
	                                                                            // (8)      ) END-EXEC.
	                                                                            // (9) ******************************************************************
	                                                                            // (10) * COBOL DECLARATION FOR TABLE TABMSG                             *
	                                                                            // (11) ******************************************************************
	Var dvtbmsga = declare.level(1).var() ;                                     // (12)  01  DVTBMSGA.
		Var no = declare.level(10).picX(4).var() ;                              // (13)      10 NO                   PIC X(4).
		Var text = declare.level(10).picX(78).var() ;                           // (14)      10 TEXT                 PIC X(78).
}
                                                                                // (15) ******************************************************************
                                                                                // (16) * THE NUMBER OF COLUMNS DESCRIBED BY THIS DECLARATION IS 2       *
                                                                                // (17) ******************************************************************
