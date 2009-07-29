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

import nacaLib.varEx.VarDefBase;

public class OccursOverflowException extends NacaRTException
{
	private static final long serialVersionUID = 1L;
	private String m_csVarDefBase = null;
	private int m_nIndexRequestedBase1 = 0;
	private int m_nIndexMaxValueBase1 = 0;
	private String m_csIndexName = null;
	
	public OccursOverflowException(VarDefBase varDefBase, int nIndexRequestedBase0, int nIndexMaxValue, String csIndexName)
	{
		m_csVarDefBase = varDefBase.toString();
		m_nIndexRequestedBase1 = nIndexRequestedBase0+1;
		m_nIndexMaxValueBase1 = nIndexMaxValue;
		m_csIndexName = csIndexName;
	}
	
	public String getMessage()
	{
		String cs = "OccursOverflowException: Index " + m_csIndexName + " value requested/Max:" + m_nIndexRequestedBase1 + "/" + m_nIndexMaxValueBase1 + "; VarDef:" + m_csVarDefBase; // + "; Stack="+m_csStack;
		return cs;
	}
}
