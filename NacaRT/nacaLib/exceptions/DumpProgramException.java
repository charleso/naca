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
package nacaLib.exceptions;

import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.varEx.Var;

public class DumpProgramException extends NacaRTException
{
	private static final long serialVersionUID = 1L;
	private String m_csProgramName = "";
	private String m_csVar1 = "";
	private String m_csVar2 = "";
	
	public DumpProgramException(BaseProgramManager programManager, Var var1, Var var2)
	{
		m_csProgramName = programManager.getProgramName();
		if (var1 != null)
			m_csVar1 = var1.toString();
		if (var2 != null)
			m_csVar2 = var2.toString();
	}
	
	public String getMessage()
	{
		String cs = "DumpProgramException: Program:" + m_csProgramName + "; Var1:" + m_csVar1 + "; Var2:" + m_csVar2; // + " Stack="+m_csStack;
		return cs;
	}
}
