/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.batchPrgEnv;

import nacaLib.basePrgEnv.BaseCESMManager;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.fpacPrgEnv.FPacProgram;

public class BatchProgram extends FPacProgram
{
	public BatchProgram()
	{
		super();
	}
	
	public BaseCESMManager CESM = null;
	
	void prepareRunMain(BaseEnvironment e)
	{
		CESM = e.createCESMManager();
	}
	
	protected BaseCESMManager getCESM()
	{
		return CESM;
	}

	protected int first()
	{
		return NEXT;
	}

	protected int normal()
	{
		return NEXT;
	}

	protected int last()
	{
		return NEXT;
	}
}
