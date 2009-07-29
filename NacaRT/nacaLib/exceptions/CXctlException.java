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

import nacaLib.basePrgEnv.BaseEnvironment;

public class CXctlException extends NacaRTException
{
	private static final long serialVersionUID = 1L;
	public BaseEnvironment m_Environment = null;
	
	public CXctlException(BaseEnvironment env)
	{
		m_Environment = env ;
	}
}
