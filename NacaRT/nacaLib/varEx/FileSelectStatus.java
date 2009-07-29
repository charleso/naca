/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.varEx;

public class FileSelectStatus
{
	private FileDescriptor m_fileDesc = null;

	public FileSelectStatus(FileDescriptor fileDesc, Var varStatus)
	{
		m_fileDesc = fileDesc; 
		m_fileDesc.registerStatus(varStatus);
	}
	
	public FileSelectStatus organization(FileOrganization fileOrganization)
	{
		m_fileDesc.setOrganization(fileOrganization);
		return this;
	}
}
