import nacaLib.program.* ;
import nacaLib.varEx.* ;
import nacaLib.basePrgEnv.* ;
public class TUAZONE extends Copy {
	public static TUAZONE Copy(BaseProgram program) {
		return new TUAZONE(program, null);
	}
	public static TUAZONE Copy(BaseProgram program, CopyReplacing copyReplacing) {
		return new TUAZONE(program, copyReplacing);
	}
	public TUAZONE(BaseProgram program, CopyReplacing copyReplacing) {
		super(program, copyReplacing);
	}
	Var tua_Zone = declare.level(1).var() ;                                     // (1)  01  TUA-ZONE.
		Var tua_I_Travail = declare.level(5).var() ;                            // (2)      05 TUA-I-TRAVAIL.
			Var tua_I_Dtjour = declare.level(10).picS9(8).comp3().var() ;       // (3)         10 TUA-I-DTJOUR              PIC S9(8) COMP-3.
			Var tua_I_Dtjourf = declare.level(10).picX(8).var() ;               // (4)         10 TUA-I-DTJOURF             PIC X(8).
}
