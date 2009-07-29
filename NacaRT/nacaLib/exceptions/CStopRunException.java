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

public class CStopRunException extends NacaRTException
{
	private static final long serialVersionUID = 1L;
	private BaseProgramManager m_programManager = null;
	
	public CStopRunException(BaseProgramManager programManager)
	{
		m_programManager = programManager;
	}
	
	public String getMessage()
	{
		String cs = "CStopRunException: Program:"+ m_programManager.getProgramName();
		return cs;
	}
}
