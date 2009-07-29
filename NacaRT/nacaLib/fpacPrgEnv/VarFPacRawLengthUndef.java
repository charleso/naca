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
package nacaLib.fpacPrgEnv;

import nacaLib.varEx.Var;
import nacaLib.varEx.VarBuffer;

public class VarFPacRawLengthUndef extends VarFPacLengthUndef
{
	public VarFPacRawLengthUndef(FPacVarManager fpacVarManager, VarBuffer varBuffer, int nAbsolutePosition1Based)
	{
		super(fpacVarManager, varBuffer, nAbsolutePosition1Based);
	}
	
	public Var createVar(int nBufferSize)
	{
		return m_fpacVarManager.createFPacVarRaw(m_varBuffer, m_nAbsolutePosition1Based, nBufferSize);
	}

	public Var createVar()
	{
		return m_fpacVarManager.createFPacVarRaw(m_varBuffer, m_nAbsolutePosition1Based, 100);
	}
	
	int getParamLength(String cs)
	{
		return cs.length();
	}
	
	int getParamLength(int n)
	{
		if(n < 0)
			n = -n;
		String cs = String.valueOf(n);
		return cs.length();
	}
	
	int getParamLength(Var varSource)
	{
		return varSource.getLength();
	}
}
