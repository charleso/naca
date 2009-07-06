/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.program.*;
import nacaLib.varEx.Var;

public class TestWorking2CopyReplacing extends Copy
{
	public static TestWorking2CopyReplacing Copy(BaseProgram program)
	{
		return new TestWorking2CopyReplacing(program, null); 
	}
	
	public static TestWorking2CopyReplacing Copy(BaseProgram program, CopyReplacing copyReplacing)
	{
		return new TestWorking2CopyReplacing(program, copyReplacing); 
	}

	public TestWorking2CopyReplacing(BaseProgram program, CopyReplacing copyReplacing)
	{
		super(program, copyReplacing);
	}
	
	public Var RS03S99A_ZONE = declare.level(1).var() ;
		public Var RS03S99A_RESTRIC = declare.level(5).picX(1).var() ;
		public Var RS03S99A_CDSTINI1 = declare.level(5).picX(3).var() ;
		public Var RS03S99A_CDSTINI2 = declare.level(5).picX(3).var() ;
		public Var RS03S99A_CDCENPI = declare.level(5).picX(3).var() ;
		public Var RS03S99A_DICAPP = declare.level(5).picX(3).var() ;
}
