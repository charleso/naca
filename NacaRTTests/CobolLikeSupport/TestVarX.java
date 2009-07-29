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
import nacaLib.varEx.*;
import nacaLib.program.*;

public class TestVarX extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	Var W1 = declare.level(1).var();
		Var WJJMMAA = declare.level(2).picX(6).var();
			Var WJJ = declare.level(5).picX(2).var();
			Var WMM = declare.level(5).picX(2).var();
			Var WAA = declare.level(5).picX(2).var();

	Paragraph Paragraph1 = new Paragraph(this){public void run(){Paragraph1();}};void Paragraph1()
	{
		setAssertActive(true);
		
		perform(Test1);
		CESM.returnTrans();
	}

	Paragraph Test1 = new Paragraph(this){public void run(){Test1();}};void Test1()
	{
		// Full fill
		move("JJ", WJJ);
		assertIfDifferent(WJJ.getString(), "JJ");
		
		move("MM", WMM);
		assertIfDifferent(WMM.getString(), "MM");
		
		move("AA", WAA);
		assertIfDifferent(WAA.getString(), "AA");
		
		assertIfDifferent(WJJMMAA.getString(), "JJMMAA");
		
		// Partial fill with right padding
		move("j", WJJ);
		assertIfDifferent(WJJ.getString(), "j ");
		assertIfDifferent(WJJMMAA.getString(), "j MMAA");
		
		boolean b = this.isEqual(WJJ, "j ");
		assertIfFalse(b);
		
		// Too wide filling in sub item
		move("abcd", WJJ);
		assertIfDifferent(WJJMMAA.getString(), "abMMAA");

		// Too wide filling in main item
		move("12345678", WJJMMAA);	// 78 are in excess
		assertIfDifferent(WJJMMAA.getString(), "123456");
		assertIfDifferent(WJJ.getString(), "12");
		assertIfDifferent(WMM.getString(), "34");
		assertIfDifferent(WAA.getString(), "56");
	}
}
