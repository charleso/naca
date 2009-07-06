/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.DirContext;

import jlib.log.Log;

public class LdapThread extends Thread
{
	private ThreadSafeCounter m_NbThreadCreated = null;
	
	LdapThread(int nRquestId, String csUserId, String csPassword, String csServer, ThreadSafeCounter NbThreadCreated)
	{
		m_nRequestId = nRquestId;
		m_csUserId = csUserId;
		m_csPassword = csPassword;
		m_csServer = csServer;
		m_NbThreadCreated = NbThreadCreated;
	}
	
	void setLdapThreadOwner(LdapUtil ldapUtil)
	{
		m_ldapUtil = ldapUtil;  
	}
	
	public void run()
    {
		Log.logNormal("LDap request " + m_nRequestId + ": trying to get ldap info from server " + m_csServer);
		int nNbTries = 0;
		while(nNbTries < 2)
		{
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://"+m_csServer+"/");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, m_csUserId);
            env.put(Context.SECURITY_CREDENTIALS, m_csPassword);  
            if(m_ldapUtil != null)
            {
            	DirContext dirContext = m_ldapUtil.getDirContext(env);
            	if(dirContext != null)
            	{
            		m_ldapUtil.setOnceDirContext(dirContext);
            		Log.logNormal("LDap request " + m_nRequestId + ": dir context correctly set");
            		return;
            	}
            }   
            nNbTries++;
		}
		if(m_NbThreadCreated.dec() <= 0)
		{
			m_ldapUtil.setOnceDirContext(null);
		}
		Log.logCritical("LDap request " + m_nRequestId + ": dir context NOT correctly set");
    }
	
	private LdapUtil m_ldapUtil = null;
	private String m_csUserId = null;
	private String m_csPassword = null;
	private String m_csServer = null;
	private int m_nRequestId = 0;
}
