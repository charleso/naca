/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import nacaLib.varEx.*;


import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.program.*;

public class TestWorking2Copy extends Copy
{
	public static TestWorking2Copy Copy(BaseProgram program)
	{
		return new TestWorking2Copy(program); 
	}

	public TestWorking2Copy(BaseProgram program)
	{
		super(program, null);
	}
	
	public Var Group = declare.level(1).var() ;
		public Var Item1 = declare.level(10).picX(1).var() ;
		public Var Item2 = declare.level(10).pic9(2).var() ;
		
	public Var GOccursed = declare.level(1).var() ;
		public Var Occursed = declare.level(5).occurs(3).var() ;
			public Var GroupOccursed = declare.level(10).var() ;
				public 	Var Item1$GOccursed = declare.level(15).picX(1).var() ;
				public 	Var Item2$GOccursed = declare.level(15).pic9(2).var() ;
				
} 