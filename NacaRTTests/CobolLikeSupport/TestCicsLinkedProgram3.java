/*
 * NacaRTTests - Naca Tests for NacaRT support v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import idea.onlinePrgEnv.OnlineProgram;
import jlib.log.Log;
import nacaLib.varEx.*;

public class TestCicsLinkedProgram3 extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
		Var TTTT = declare.level(1).var();
			Var WDate = declare.level(2).picX(10).var();
		
		Var WDateFormatter = declare.level(1).var();
			Var WFormatterJJ = declare.level(2).pic9(2).var();
			Var Filler_1 = declare.level(2).picX(1).value("/").var();
			Var WFormatterMM = declare.level(2).pic9(2).var();
			Var Filler_2 = declare.level(2).picX(1).value("/").var();
			Var WFormatterAAAA = declare.level(2).pic9(4).var();

	DataSection LinkageSection = declare.linkageSection();
		Var dfhcommarea = declare.level(1).var();                                                 
			Var filler$29 = declare.level(2).occursDepending(99999, getCommAreaLength()).picX(1).filler() ;
		Var dfhcommareaRedef = declare.level(1).redefines(dfhcommarea).var();
			Var Command = declare.level(2).pic9(2).var();
			Var JJ = declare.level(2).pic9(2).var();
			Var MM = declare.level(2).pic9(2).var();
			Var AAAA = declare.level(2).pic9(4).var();
			Var OutputDateJJMMAAAA = declare.level(2).picX(10).var();
			Var erreur_Zone = declare.level(2).var() ;                              // (152) 02 ERREUR-ZONE.
				Var err_Cdproj = declare.level(5).picX(2).var() ;                   // (153) 05 ERR-CDPROJ         PIC  XX.
				Var err_Cdstini = declare.level(5).picX(3).var() ;                  // (154) 05 ERR-CDSTINI        PIC  XXX.
				Var err_Cdappli = declare.level(5).picX(2).var() ;                  // (155) 05 ERR-CDAPPLI        PIC  XX.
		
	ParamDeclaration callParameters = declare.using(dfhcommarea);               // (202)  PROCEDURE DIVISION.                                              
	public void procedureDivision() 
	{
		assertIfDifferent(12, JJ);
		assertIfDifferent(3, MM);
		assertIfDifferent(2005, AAAA);
		
		inc(Command);
	}
}

