/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import nacaLib.varEx.*;


import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.program.*;

public class TestWorking2Copy2 extends Copy
{
	public static TestWorking2Copy2 Copy(BaseProgram program)
	{
		return new TestWorking2Copy2(program); 
	}

	public TestWorking2Copy2(BaseProgram program)
	{
		super(program, null);
	}
	
	public Var v1 = declare.level(1).var();
		public Var v21a = declare.level(5).picX(1).var();
		public Var v22a = declare.level(5).picX(2).var();
		public Var v23b = declare.level(5).picX(3).var();
		public Var v24a = declare.level(5).picX(4).var();
		public Var v25b = declare.level(5).picX(5).var();
		public Var v26a$v1 = declare.level(5).picX(6).var();
		public Var v28a = declare.level(5).picX(8).var();
} 