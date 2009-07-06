/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.program.*;
import nacaLib.varEx.Var;

public class TestVarLong extends OnlineProgram
{	
	Var w_Tarmtl = declare.level(1).picS9(13).comp3().var();
	Var w_Tarmtl9 = declare.level(1).picS9(13).var();
	Var w_TarmtlX = declare.level(1).picX(13).var();

	Section haupt = new Section(this){public void run(){}};
	
	Paragraph haupt_A = new Paragraph(this){public void run(){haupt_A();}} ;void haupt_A()
	{	
		setAssertActive(true);
		
		move("-1234567890123", w_Tarmtl);
		assertIfFalse(w_Tarmtl.getLong() == -1234567890123L);
		
		move(w_Tarmtl, w_Tarmtl9);
		assertIfFalse(w_Tarmtl9.getLong() == -1234567890123L);
		
		move(w_Tarmtl9, w_TarmtlX);
		assertIfDifferent("1234567890123", w_TarmtlX);
		
		dec(w_Tarmtl);
		assertIfFalse(w_Tarmtl.getLong() == -1234567890124L);
		
		move(w_Tarmtl, w_TarmtlX);
		assertIfDifferent("1234567890124", w_TarmtlX);
	}
}
