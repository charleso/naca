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

public class TestLong extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	Var W3 = declare.level(1).occurs(10).var();
		Var V9Comp010 = declare.level(5).pic9(10).var();
		Var V9Comp014V4 = declare.level(5).pic9(14, 4).var();
	
	Var VX10 = declare.level(5).picX(10).var();
        
	public void procedureDivision()
	{
		setAssertActive(true);
		
		move("9876543210", VX10);
		assertIfDifferent("9876543210", VX10);
		
		move(VX10, V9Comp010);
		long l = V9Comp010.getLong();
		assertIfFalse(l == 9876543210L);
		
		multiply(1000, V9Comp010).to(V9Comp014V4);
		assertIfFalse(9876543210000L == V9Comp014V4.getLong());
		
		String cs = V9Comp010.toString();
		cs = V9Comp014V4.toString();
		assertIfDifferent("9876543210000.0000", V9Comp014V4);
		
		inc(V9Comp010);
		assertIfFalse(9876543211L == V9Comp010.getLong());
		
		CESM.returnTrans();
	}
}
