/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaTrans - Naca Transcoder v1.2.0.beta.1
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package utils.modificationsReporter;

public class ReportModificationCount
{
	private String m_csFile = null;
	private int m_nCount = 0;
	
	ReportModificationCount(String csFile)
	{
		m_csFile = csFile;
		m_nCount = 1;
	}
}
