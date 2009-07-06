/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.basePrgEnv;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: CurrentUserInfo.java,v 1.2 2006/03/16 12:52:43 U930DI Exp $
 */
public class CurrentUserInfo
{
	public void set(String csLUName, String csUserLdapId)
	{
		m_csLUName = csLUName; 
		m_csUserLdapId = csUserLdapId;
	}
	
	public void reset()
	{
		m_csLUName = "";
		m_csUserLdapId = "";
	}
	
	public String m_csPub2000ProfitCenter = "";
	public String m_csPub2000UserId = "";
	
	public String m_csLUName = "";
	public String m_csUserLdapId = "";
}
