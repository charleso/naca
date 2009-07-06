/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */

import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.varEx.*;

public class TestWorkingOccurs extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();

	Var Broot = declare.level(1).picX(10).var();
		Var Broot81 = declare.level(2).picX(10).value("0123").var();
		
	Var W1 = declare.level(1).occurs(2).var();
		Var W2 = declare.level(3).occurs(3).var();
			Var W3 = declare.level(4).occurs(4).var();
				Var VJJ = declare.level(5).picX(2).value("j1").var();
				Var VMM = declare.level(5).picX(2).value("m2").var();
				Var VAA = declare.level(5).picX(4).value("a345").var();
	
				
	public void procedureDivision()
	{
		setAssertActive(true);
		
		assertIfFalse(Broot81.equals("0123"));
		for(int i=1; i<=2; i++)
		{
			for(int j=1; j<=3; j++)
			{
				for(int k=1; k<=3; k++)
				{
					Var vj = VJJ.getAt(i, j, k);
					assertIfFalse(vj.getString().equals("j1"));
					
					Var vm = VMM.getAt(i, j, k);
					assertIfFalse(vm.getString().equals("m2"));
					
					Var va = VAA.getAt(i, j, k);
					assertIfFalse(va.getString().equals("a345"));
					int hh =0 ;
				}
			}
		}
	}
}
