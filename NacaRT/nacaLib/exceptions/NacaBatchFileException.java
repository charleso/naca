/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.exceptions;

import jlib.misc.LogicalFileDescriptor;

public class NacaBatchFileException extends NacaRTException
 {
	private static final long serialVersionUID = 1L;
 	private String m_csFileName = null;
 	private String m_csLogicalFileDescriptor = null;
 	private String m_csExceptionName = null;
 	
 	public NacaBatchFileException(String csExceptionName, String csFileName, LogicalFileDescriptor logicalFileDescriptor)
 	{
 		m_csExceptionName = csExceptionName;
 		m_csFileName = csFileName;
 		if(logicalFileDescriptor != null)
 			m_csLogicalFileDescriptor = logicalFileDescriptor.toString();
 		else
 			m_csLogicalFileDescriptor = "<EMPTY>";
 	}
 	
 	public String getMessage()
 	{
 		String cs = m_csExceptionName + "; LogicalName=" + m_csFileName + "; PhysicalDescription=" + m_csLogicalFileDescriptor;
 		return cs;
 	}
}
