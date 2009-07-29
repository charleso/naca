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
/*
 * Created on 10 déc. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author PJD
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.varEx.*;
import nacaLib.program.*;

public class TestVarGroup extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	

	Var v1b = declare.level(1).var();
		Var v2b = declare.level(2).picX(9).value("abcdefghi").var();

	Var v1a = declare.level(1).var();
		Var v2a = declare.level(5).picX(10).var();
		Var v2aRedefine = declare.level(5).redefines(v2a).var();
			Var Filler01 = declare.level(10).picX(1).filler();
			Var v3aReRedefine = declare.level(10).picX(9).var();

	
	Var v1c = declare.level(1).var();
		Var v2c1 = declare.level(2).picX(5).value("12345").var();
		Var v2c2 = declare.level(2).picX(5).value("67890").var();

	Var tua_Debranch = declare.level(1/*5*/).var() ;                             // (22) 05 TUA-DEBRANCH.
		Var tua_Traitec = declare.level(10).var() ;                         // (23) 10 TUA-TRAITEC.
			Var tua_D_Cdtrans1 = declare.level(15).picX(4).var() ;          // (24) 15 TUA-D-CDTRANS1         PIC X(4).
			Var tua_D_Idtrt1 = declare.level(15).picX(6).var() ;            // (25) 15 TUA-D-IDTRT1           PIC X(6).
			Var filler$30 = declare.level(15).redefines(tua_D_Idtrt1)       // (26) 15 FILLER REDEFINES TUA-D-IDTRT1.
				.var() ;
				Var filler$31 = declare.level(20).picX(2).var() ;           // (27) 20 FILLER              PIC X(2).
				Var tua_D_Idtrt1_26 = declare.level(20).picX(4).var() ;     // (28) 20 TUA-D-IDTRT1-26     PIC X(4).
		Var tua_Ptraitdef = declare.level(10).var() ;                       // (29) 10 TUA-PTRAITDEF.
			Var tua_D_Cdtrans2 = declare.level(15).picX(4).var() ;          // (30) 15 TUA-D-CDTRANS2         PIC X(4).
			Var tua_D_Idtrt2 = declare.level(15).picX(6).var() ;            // (31) 15 TUA-D-IDTRT2           PIC X(6).
			Var filler$32 = declare.level(15).redefines(tua_D_Idtrt2)       // (32) 15 FILLER REDEFINES TUA-D-IDTRT2.
				.var() ;
				Var tua_D_Idtrt2_12 = declare.level(20).picX(2).var() ;     // (33) 20 TUA-D-IDTRT2-12     PIC X(2).
				Var tua_D_Idtrt2_34 = declare.level(20).picX(2).var() ;     // (34) 20 TUA-D-IDTRT2-34     PIC X(2).
				Var tua_D_Idtrt2_56 = declare.level(20).picX(2).var() ;     // (35) 20 TUA-D-IDTRT2-56     PIC X(2).
		Var tua_Ptraitforc = declare.level(10).var() ;                      // (36) 10 TUA-PTRAITFORC.
			Var tua_D_Teibaid = declare.level(15).picX(1).var() ;           // (37) 15 TUA-D-TEIBAID          PIC X.
		Var tua_Traitanc = declare.level(10).var() ;                        // (38) 10 TUA-TRAITANC.
			Var tua_D_Cdtrans3 = declare.level(15).picX(4).var() ;          // (39) 15 TUA-D-CDTRANS3         PIC X(4).
			Var tua_D_Idtrt3 = declare.level(15).picX(6).var() ;            // (40) 15 TUA-D-IDTRT3           PIC X(6).
		Var tua_S_Mutok_2 = declare.level(10).picX(1).var() ;               // (41) 10 TUA-S-MUTOK-2             PIC X.
		Var tua_Swstr = declare.level(10).picX(1).var() ; 
	
			
	public void procedureDivision()
	{
		perform(Paragraph1);
		perform(TestMoveConstantOnGroup);
		CESM.returnTrans();
	}
		
	Paragraph Paragraph1 = new Paragraph(this){public void run(){Paragraph1();}};
	void Paragraph1()
	{
		setAssertActive(true);
		
		assertIfDifferent(v1b.getString(), "abcdefghi");
		assertIfDifferent(v2b.getString(), "abcdefghi");

		assertIfDifferent(v2c1.getString(), "12345");		
		assertIfDifferent(v2c2.getString(), "67890");
		
		move(v1c, v1b);
		assertIfDifferent(v2b.getString(), "123456789");
		
		move("ABCDEFGHIJK", v1c);
		assertIfDifferent(v2c1.getString(), "ABCDE");		
		assertIfDifferent(v2c2.getString(), "FGHIJ");
		
		move(v1c, v1b);
		assertIfDifferent(v1b.getString(), "ABCDEFGHI");
		assertIfDifferent(v2b.getString(), "ABCDEFGHI");

		
		move("Le chat ma", v1c);
		assertIfDifferent(v2c1.getString(), "Le ch");		
		assertIfDifferent(v2c2.getString(), "at ma");
		
		move(v1c, v2b);
		assertIfDifferent(v1b.getString(), "Le chat m");
		assertIfDifferent(v2b.getString(), "Le chat m");

		
		//assertIfDifferent(v1a.getString(), "");
		//assertIfDifferent(v2aRedefine.getString(), "");
		//assertIfDifferent(v3aReRedefine.getString(), "");
		move("abcdefghij", v1a);
		assertIfDifferent(v2aRedefine.getString(), "abcdefghij");
		assertIfDifferent(v3aReRedefine.getString(), "bcdefghij");
		
		move("qwertzuiopasdf", v2aRedefine);
		assertIfDifferent(v1a.getString(), "qwertzuiop");
		assertIfDifferent(v2aRedefine.getString(), "qwertzuiop");
		assertIfDifferent(v3aReRedefine.getString(), "wertzuiop");

		move("asdfghjkléà", v3aReRedefine);
		assertIfDifferent(v1a.getString(), "qasdfghjkl");
		assertIfDifferent(v2aRedefine.getString(), "qasdfghjkl");
		assertIfDifferent(v3aReRedefine.getString(), "asdfghjkl");
	}
	
	Paragraph TestMoveConstantOnGroup = new Paragraph(this){public void run(){TestMoveConstantOnGroup();}};void TestMoveConstantOnGroup()
	{
		moveSpace(tua_Debranch);
		assertIfFalse(isAll(tua_Debranch, ' '));

		moveLowValue(tua_Debranch);
		assertIfFalse(isAll(tua_Debranch, CobolConstant.LowValue));
		
		moveHighValue(tua_Debranch);
		assertIfFalse(isAll(tua_Debranch, CobolConstant.HighValue));	

		moveZero(tua_Debranch);
		assertIfFalse(isAll(tua_Debranch, '0'));	// WARNING: Treated differently due to padding rules; is it OK ?
	}
	

}
