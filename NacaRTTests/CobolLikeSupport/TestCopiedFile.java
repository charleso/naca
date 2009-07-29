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
 
public class TestCopiedFile extends Copy
{
	static TestCopiedFile Copy(BaseProgram program)
	{
		return new TestCopiedFile(program); 
	}

	TestCopiedFile(BaseProgram program)
	{
		super(program, null);
	}
		
	Var IncludeV2 = declare.level(5).picX(10).var();
} 
