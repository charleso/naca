import nacaLib.program.* ;
import nacaLib.varEx.* ;
import nacaLib.basePrgEnv.* ;
public class MSGZONE extends Copy {
	public static MSGZONE Copy(BaseProgram program) {
		return new MSGZONE(program, null);
	}
	public static MSGZONE Copy(BaseProgram program, CopyReplacing copyReplacing) {
		return new MSGZONE(program, copyReplacing);
	}
	public MSGZONE(BaseProgram program, CopyReplacing copyReplacing) {
		super(program, copyReplacing);
	}
	Var msg_Zone = declare.level(1).var() ;                                     // (1)  01  MSG-ZONE.
		Var msg_No = declare.level(5).picX(4).valueSpaces().var() ;             // (2)      05 MSG-NO               PIC  X(4)         VALUE SPACE.
		Var msg_Text = declare.level(5).picX(78).valueSpaces().var() ;          // (3)      05 MSG-TEXT             PIC  X(78)        VALUE SPACE.
}
