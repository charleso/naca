/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.fpacPrgEnv;

import nacaLib.varEx.Var;
import nacaLib.varEx.VarBuffer;

public abstract class VarFPacLengthUndef
{
	protected FPacVarManager m_fpacVarManager = null;
	protected int m_nAbsolutePosition1Based = 0;
	protected VarBuffer m_varBuffer = null;
	
	VarFPacLengthUndef(FPacVarManager fpacVarManager, VarBuffer varBuffer, int nAbsolutePosition1Based)
	{
		m_fpacVarManager = fpacVarManager;
		m_varBuffer = varBuffer;
		m_nAbsolutePosition1Based = nAbsolutePosition1Based;
	}
	
	public abstract Var createVar();
	public abstract Var createVar(int nBufferLength);
	abstract int getParamLength(String cs);
	abstract int getParamLength(int n);
	abstract int getParamLength(Var varSource);

	/**
	 * @return
	 */
	public int getInt()
	{
		// TODO Fake method
		return 0;
	}	
}
