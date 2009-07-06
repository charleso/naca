import nacaLib.mapSupport.* ;                                                   // (1) 
import nacaLib.varEx.* ;
import nacaLib.program.* ;
import nacaLib.basePrgEnv.* ;
class ONLINM1S extends Map {
	static ONLINM1S Copy(BaseProgram program) {
		return new ONLINM1S(program);
	}
	static ONLINM1S Copy(BaseProgram program, CopyReplacing rep)  {
		Assert("Unimplemented replacing for MAPs") ;
		return null ;
	}
	ONLINM1S(BaseProgram program) {
		super(program);
	}
	                                                                            // (2) 
	Form onlinefs = declare.form("onlinm1", 24, 80) ;                           // (3) 
		                                                                        // (9) 
		LocalizedString str_Nmmasq = declare.localizedString().text(LanguageCode.FR, "INIT01");	// (10) 
		Edit snmmasq = declare.edit("nmmasq", 6).initialValue(str_Nmmasq).justifyFill(MapFieldAttrFill.BLANK).edit() ;
		                                                                        // (19) 
		Edit sdtexec = declare.edit("execdate", 8).justifyFill(MapFieldAttrFill.BLANK).edit() ;	// (20) 
		                                                                        // (24) 
		Edit shrexec = declare.edit("exechour", 8).justifyFill(MapFieldAttrFill.BLANK).edit() ;	// (25) 
		                                                                        // (40) 
		Edit srecoll = declare.edit("recoll", 3).justifyFill(MapFieldAttrFill.BLANK).edit() ;	// (41) 
		                                                                        // (56) 
		Edit spasscol = declare.edit("passcol", 8).justifyFill(MapFieldAttrFill.BLANK).edit() ;	// (57) 
		                                                                        // (69) 
		Edit snewpass = declare.edit("newpass", 8).justifyFill(MapFieldAttrFill.BLANK).edit() ;	// (70) 
		                                                                        // (74) 
		Edit slierr = declare.edit("errormessage", 79).justifyFill(MapFieldAttrFill.BLANK).setDevelopableMark("./.").edit() ;	// (75) 
}
