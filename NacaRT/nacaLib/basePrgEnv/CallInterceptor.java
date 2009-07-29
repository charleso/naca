/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.basePrgEnv;

import java.util.ArrayList;

import nacaLib.varEx.CCallParam;
import nacaLib.varEx.Var;

public abstract class CallInterceptor
{
	private String m_csName = null;
	
	protected CallInterceptor(String csName)
	{
		m_csName = csName;
	}
	
	public abstract void run(BaseProgramManager baseProgramManager, ArrayList<CCallParam> arrCallerCallParam);
	
	protected Var getCallerSourceVar(ArrayList<CCallParam> arrCallerCallParam, int nIndex)
	{
		if(arrCallerCallParam.size() <= nIndex)
			return null;
		
		CCallParam callParam = arrCallerCallParam.get(nIndex);
		Var var = callParam.getCallerSourceVar();	// Not compatible with CallInterceptor
		return var;
	}
	
	public String getSubProgramName()
	{
		return m_csName;
	}
}
