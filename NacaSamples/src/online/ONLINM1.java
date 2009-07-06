import nacaLib.mapSupport.* ;                                                   // (1) 
import nacaLib.varEx.* ;
import nacaLib.program.* ;
import nacaLib.basePrgEnv.* ;
class ONLINM1 extends Map {
	static ONLINM1 Copy(BaseProgram program) {
		return new ONLINM1(program);
	}
	static ONLINM1 Copy(BaseProgram program, CopyReplacing rep)  {
		Assert("Unimplemented replacing for MAPs") ;
		return null ;
	}
	ONLINM1(BaseProgram program) {
		super(program);
	}
	                                                                            // (2) 
	Form onlinef = declare.form("onlinm1", 24, 80) ;                            // (3) 
		                                                                        // (9) 
		LocalizedString str_Nmmasq = declare.localizedString().text(LanguageCode.FR, "INIT01");	// (10) 
		Edit nmmasq = declare.edit("nmmasq", 6).initialValue(str_Nmmasq).justifyFill(MapFieldAttrFill.BLANK).edit() ;
		                                                                        // (19) 
		Edit dtexec = declare.edit("execdate", 8).justifyFill(MapFieldAttrFill.BLANK).edit() ;	// (20) 
		                                                                        // (24) 
		Edit hrexec = declare.edit("exechour", 8).justifyFill(MapFieldAttrFill.BLANK).edit() ;	// (25) 
		                                                                        // (40) 
		Edit recoll = declare.edit("recoll", 3).justifyFill(MapFieldAttrFill.BLANK).edit() ;	// (41) 
		                                                                        // (56) 
		Edit passcol = declare.edit("passcol", 8).justifyFill(MapFieldAttrFill.BLANK).edit() ;	// (57) 
		                                                                        // (69) 
		Edit newpass = declare.edit("newpass", 8).justifyFill(MapFieldAttrFill.BLANK).edit() ;	// (70) 
		                                                                        // (74) 
		Edit lierr = declare.edit("errormessage", 79).justifyFill(MapFieldAttrFill.BLANK).setDevelopableMark("./.").edit() ;	// (75) 
}
