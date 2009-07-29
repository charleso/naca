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

public class VarFPacNumIntSignComp3LengthUndef extends VarFPacLengthUndef 
{
	public VarFPacNumIntSignComp3LengthUndef(FPacVarManager fpacVarManager, VarBuffer varBuffer, int nAbsolutePosition1Based)
	{
		super(fpacVarManager, varBuffer, nAbsolutePosition1Based);
	}
	
	public Var createVar(int nBufferSize)
	{
		Var v = m_fpacVarManager.createFPacVarNumIntSignComp3(m_varBuffer, m_nAbsolutePosition1Based, nBufferSize);
		return v;
	}
	
	public Var createVar()
	{
		Var v = m_fpacVarManager.createFPacVarNumIntSignComp3(m_varBuffer, m_nAbsolutePosition1Based, 8);
		return v;
	}
	
	int getParamLength(String cs)
	{
		return 8;
	}
	
	int getParamLength(int n)
	{
		return 8;
	}
	
	int getParamLength(Var varSource)
	{
		return 8;
	}
}


