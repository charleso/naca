/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

import javax.naming.NamingEnumeration;
import javax.naming.directory.SearchResult;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */

/*
 * Sample call:
 * class ResourceManager
 * {
 * 		...
 * 		// Members variables to initialize by some meanings
 * 		private String m_csLDAPServer = "" ;		// Main LDAP server address
 * 		private String m_csLDAPServer2 = "" ;		// 2nd LDAP server address
 * 		private String m_csLDAPServer3 = "" ;		// 3rd LDAP server address
 *      private String m_csLDAPDomain = "" ;		// Domain
 * 		private String m_csLDAPRootOU = "" ;		// Root OU; e.g.: OU=FUTUR_PUBLIGROUPE,DC=Publigroupe,DC=net		
 * 		private String m_csLDAPGenericUser = "" ;	// LDap connection user
 * 		private String m_csLDAPGenericPassword = "";// LDap connection password
 * 		...
 * 		public CLDAPRequester getLDAPRequester()
 *		{
 *			return new CLDAPRequester(m_csLDAPServer, m_csLDAPServer2, m_csLDAPServer3, m_csLDAPDomain, m_csLDAPRootOU, m_csLDAPGenericUser, m_csLDAPGenericPassword) ;
 *		}
 *		...
 *	}
 *
 * Application code:
 * ...
 * LDapRequester ldapReq = m_ResourceManager.getLDAPRequester() ;	
 * String csUserDN = ldapReq.getUserLogin(m_csUserLdapId, csPassword, bLoginAuto) ;
 * boolean bLogged = !StringUtil.IsEmpty(csUserDN);
 * ...
 * // To get LDap attributs
 * m_csApplicationCredentials = ldapReq.getAttribute(csUserDN, "extensionAttribute12") ;
 * if (m_csApplicationCredentials == null)
 * {
 * 		m_csApplicationCredentials = "" ;
 * }
 * 
 * // To get user complete name 
 * String csSn = ldapReq.getAttribute(csUserDN, "sn") ;
 * if (csSn == null)
 * {
 * 		m_csUserLdapName = "";
 * }
 * else
 * {
 * 		m_csUserLdapName = csSn;
 * 		String csGivenName = ldapReq.getAttribute(csUserDN, "givenName") ;
 * 		if (csGivenName != null) 
 * 		{
 * 			m_csUserLdapName += " " + csGivenName;
 * 		}
 * }
 */

public class LdapRequester
{
	private LdapUtil m_ldap = null;		// Helper class
	private String m_csLDAPServer1 = null;	// Main LDAP server address
	private String m_csLDAPServer2 = null;	// 2nd LDAP server address
	private String m_csLDAPServer3 = null;	// 3rd LDAP server address
	private String m_csLDAPDomain = "" ;
	private String m_csLDAPRootOU = "" ;	// Root OU; e.g.: OU=FUTUR_PUBLIGROUPE,DC=Publigroupe,DC=net
	private String m_csLDAPGenericUser = "" ;	// LDap generic connection user
	private String m_csLDAPGenericPassword = "" ;	// LDap generic connection password
	private ThreadSafeCounter m_cptLdapRequestId = new ThreadSafeCounter(0);

	public LdapRequester(String csServer1, String csServer2, String csServer3, String csDomain, String ou, String csGenericUser, String csGenericPassword)
	{
		m_csLDAPServer1 = csServer1;
		m_csLDAPServer2 = csServer2;
		m_csLDAPServer3 = csServer3;
		m_csLDAPDomain = csDomain;
		m_csLDAPRootOU = ou ;
		m_csLDAPGenericUser = csGenericUser;
		m_csLDAPGenericPassword = csGenericPassword;
	}
	
	/**
	 * @param csUser: User name
	 * @param csPassword: User password
	 * @return true if ldap user/password exists, false otherwise
	 */
	public boolean validateLogin(String csUser, String csPassword)
	{		
		int nLdapRequestId = m_cptLdapRequestId.inc();
		int nNbLdapThread = 1;
		if(!StringUtil.isEmpty(m_csLDAPServer2))
			nNbLdapThread++;
		if(!StringUtil.isEmpty(m_csLDAPServer3))
			nNbLdapThread++;
		
		m_ldap = new LdapUtil(nNbLdapThread);
		m_ldap.addServer(nLdapRequestId, csUser+"@"+m_csLDAPDomain, csPassword, m_csLDAPServer1);
		if(!StringUtil.isEmpty(m_csLDAPServer2))
			m_ldap.addServer(nLdapRequestId, csUser+"@"+m_csLDAPDomain, csPassword, m_csLDAPServer2);
		if(!StringUtil.isEmpty(m_csLDAPServer3))
			m_ldap.addServer(nLdapRequestId, csUser+"@"+m_csLDAPDomain, csPassword, m_csLDAPServer3);
		m_ldap.connectOnAnyServers();
		return m_ldap.isValid() ;
	}

	/**
	 * @param csUser: specific user we want to login
	 * @param csPassword: specific password
	 * @param bUseGenericUser: true if connecting using generic user, not the specific user (csUser / csPassword); in that case csUser/csPassword is ignored   
	 * @return String UserDN; set to null or empty if user did not login correctly.
	 */
	public String getUserLogin(String csUser, String csPassword, boolean bUseGenericUser)
	{
		String csUserLogin = csUser;
		String csPasswordLogin = csPassword;
		if (bUseGenericUser)
		{
			csUserLogin = m_csLDAPGenericUser;
			csPasswordLogin = m_csLDAPGenericPassword;
		}
		
		if (!validateLogin(csUserLogin, csPasswordLogin))
		{
			return null ;
		}
		if (m_ldap == null)
		{
			return null ;
		}
		
		NamingEnumeration enumer = m_ldap.searchSubtree(m_csLDAPRootOU, "sAMAccountName="+csUser) ;
		if (enumer.hasMoreElements())
		{
			SearchResult res = (SearchResult)enumer.nextElement() ;
			String name = res.getNameInNamespace() ;
			return name ;
		}
		return null;
	}

	/**
	 * @param csUserDN: User DN whose attribute is serached
	 * @param csAttributName: Attribut name 
	 * @return String, giving the read attribut value. 
	 */	
	public String getAttribute(String csUserDN, String csAttributName)
	{
		if (m_ldap == null)
		{
			return null ;
		}
		String csAttributsValue = m_ldap.getOneAttribute(csUserDN, csAttributName) ;
		return csAttributsValue;
	}
}
