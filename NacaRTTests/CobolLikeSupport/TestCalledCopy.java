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
import nacaLib.varEx.*;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.program.*;

public class TestCalledCopy extends Copy
{
	public static TestCalledCopy Copy(BaseProgram prg)
	{
		return new TestCalledCopy(prg, null);
	}
	
	public static TestCalledCopy Copy(BaseProgram prg, CopyReplacing copyReplacing)
	{
		return new TestCalledCopy(prg, copyReplacing);
	}
		
	public TestCalledCopy(BaseProgram prg, CopyReplacing copyReplacing)
	{
		super(prg, copyReplacing);
	}
	
	public Var WRoot = declare.level(5).var() ;  
		public Var WNumResult = declare.level(10).pic9(4).var();
		public Var WNum1 = declare.level(10).pic9(3).var();
		public Var WNum2 = declare.level(10).pic9(3).var();	
}
