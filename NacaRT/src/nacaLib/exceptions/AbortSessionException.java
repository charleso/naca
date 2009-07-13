/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.exceptions;

public class AbortSessionException extends NacaRTException
{
	private static final long serialVersionUID = 1L;
	
	public Throwable m_Reason = null ;
	public String m_ProgramName = "" ;
	
	public String getMessage()
	{
		String cs = "";
		if(m_ProgramName != null)
			cs = "AbortSessionException Prg=" + m_ProgramName;
		if(m_Reason != null && m_Reason.getMessage() != null)
			cs += " Reason=" + m_Reason.getMessage();
		return cs;		
	}
	
	public String getReason()
	{
		if(m_Reason != null && m_Reason.getMessage() != null)
		{
			String cs = m_Reason.getMessage();
			return cs;
		}
		return "AbortSessionException Prg=" + m_ProgramName;
	}
}
