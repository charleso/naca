/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;

import jlib.log.Log;

public class LdapUtil
{

    //private static PropertyResourceBundle bundle = Bbundle.getBundle();
    //private static Log log = LogFactory.getLog(LdapUtil.class);  
	private ArrayList<LdapThread> m_arrThread = null;
	//Semaphore m_sem = new Semaphore(); 
    private DirContext m_ctx = null ;
    private CountDownLatch m_lock = new CountDownLatch(1); 
    private ThreadSafeCounter m_NbThreadCreated = null;
    
    /**
     * @throws NamingException
     */
    
    public LdapUtil(int nNbLdapThread)
    {
    	m_NbThreadCreated = new ThreadSafeCounter(nNbLdapThread);
    }
    
    public void addServer(int nRequestId, String csUserId, String csPassword, String csServer)
    {
    	LdapThread th = new LdapThread(nRequestId, csUserId, csPassword, csServer, m_NbThreadCreated);
    	
    	if(m_arrThread == null)
    		m_arrThread = new ArrayList<LdapThread>(); 
    	m_arrThread.add(th);
    }
    
    public void connectOnAnyServers()
    {
    	if(m_arrThread != null)
    	{
    		int nNbThreads = m_arrThread.size();
    		for(int n=0; n<nNbThreads; n++)
    		{
    			LdapThread th = m_arrThread.get(n);
    			th.setLdapThreadOwner(this);
    			th.start();    			
    		}
    	}
    	// Wait until one thread get connected
    	try
		{
			m_lock.await();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
    	// stop all threads
    }
    
    DirContext getDirContext(Hashtable<String, String> env)
    {    	
    	try
    	{
    		DirContext ctx = new InitialDirContext(env);
    		return ctx;
    	}
		catch (Exception e)
		{
			//e.printStackTrace();
			Log.logNormal("Exception catched in ldap getDirContext "+e.toString());
		}
    	return null;
    }
    
    synchronized void setOnceDirContext(DirContext dirContext)
    {
    	if(m_ctx == null)
    	{
    		m_ctx = dirContext;
    		m_lock.countDown();
    	}
    }
    
    
    public LdapUtil(String csUserId, String csPassword, String csServer)
    {
        try
		{
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://"+csServer+"/");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, csUserId);
            env.put(Context.SECURITY_CREDENTIALS, csPassword);        
            m_ctx = new InitialDirContext(env);
		}
		catch (NamingException e)
		{
			e.printStackTrace();
			m_ctx = null ;
		}
    }

//    /**
//     * @param env
//     * @throws NamingException
//     */
//    public LdapUtil(Hashtable env) throws NamingException {
//        ctx = new InitialDirContext(env);
//        logEnvironment();
//    }
    
//    /**
//     * @throws NamingException
//     */
//    public void logEnvironment() throws NamingException {
//        Hashtable env = ctx.getEnvironment();
//        Iterator iterator = env.keySet().iterator();
//        while (iterator.hasNext()) {
//            String key = (String) iterator.next();
//            log.info("ldap env "+key+"="+env.get(key));
//        }
//    }

    /**
     * @param dn
     * @param attributeName
     * @return
     * @throws NamingException
     */
    public String getOneAttribute(String dn, String attributeName)
	{
        try
		{
			Attributes attrs = m_ctx.getAttributes(dn);
			Attribute attr = attrs.get(attributeName);
			if (attr != null)
			{
				Log.logDebug("getID    : " + attr.getID());
				NamingEnumeration enumeration = attr.getAll();
				while (enumeration.hasMore()) 
				{
					Log.logDebug("getAll   : " + enumeration.next());
				}
				return (String) attr.get();
			}
		}
		catch (NamingException e)
		{
			e.printStackTrace();
			return "" ;
		}
		return "" ;
    }

    /**
     * @param dn
     * @param attributeNames
     * @return
     * @throws NamingException
     */
    public NamingEnumeration getSomeAttributes(String dn, String[] attributeNames) throws NamingException 
	{
        Attributes attrs = m_ctx.getAttributes(dn, attributeNames);
        NamingEnumeration enumSome = attrs.getAll();
        while (enumSome.hasMore()) 
        {
            Attribute a = (Attribute)enumSome.next();
            Log.logDebug(a.getID()+" = "+a.get());
        }
        return enumSome;
    }

    /**
     * @param dn
     * @return
     * @throws NamingException
     */
    public NamingEnumeration getAllAttributes(String dn) throws NamingException 
	{
        Attributes attrs = m_ctx.getAttributes(dn);
        return attrs.getAll();
    }

    /**
     * @param dn
     * @return
     * @throws NamingException
     */
    public TreeMap getAllAttributesSorted(String dn) throws NamingException 
	{
        NamingEnumeration enumAll = getAllAttributes(dn);
        TreeMap<String, Object> tree = new TreeMap<String, Object>();
        while (enumAll.hasMore()) 
        {
            Attribute a = (Attribute)enumAll.next();
            tree.put(new String(a.getID()), a.get());
        }
        for (Iterator it = tree.keySet().iterator(); it.hasNext();) 
        {
            String key = (String)it.next();
            Log.logDebug(key+" = "+tree.get(key));
        }
        return tree;
    }

    /**
     * @param dn
     * @param attributeName   
     * @param newValue
     * @throws NamingException
     */
    public void replaceAttribute(String dn, String attributeName, String newValue) throws NamingException 
	{
        Log.logDebug("attribute "+attributeName+" old value is "+getOneAttribute(dn, attributeName));
        ModificationItem[] mods = new ModificationItem[1];
        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(attributeName, newValue));
        m_ctx.modifyAttributes(dn, mods);            
        Log.logDebug("attribute "+attributeName+" new value is "+getOneAttribute(dn, attributeName));
    }

    /**
     * @param dn
     * @param filter
     * @return
     * @throws NamingException
     */
    public NamingEnumeration searchChildren(String dn, String filter) throws NamingException 
	{
        SearchControls constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        return m_ctx.search(dn, filter, constraints);
    }

    /**
     * @param dn
     * @param filter
     * @return
     * @throws NamingException
     */
    public NamingEnumeration searchOne(String dn, String filter) throws NamingException 
	{
        SearchControls constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.OBJECT_SCOPE);
        return m_ctx.search(dn, filter, constraints);
    }

    /**
     * @param dn
     * @param filter
     * @return
     * @throws NamingException
     */
    public NamingEnumeration searchSubtree(String dn, String filter)
	{
        SearchControls constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        try
		{
			return m_ctx.search(dn, filter, constraints);
		}
		catch (NamingException e)
		{
			e.printStackTrace();
			return null ;
		}
    }

	/**
	 * @return
	 */
	public boolean isValid()
	{
		return m_ctx != null ;
	}

    /*
    public Map getUserAndGroupFromConfig() throws LdapUserException {
        HashMap result = new HashMap();
        try {
            Enumeration enumKeys = bundle.getKeys();
            TreeMap tree = new TreeMap();
            while (enumKeys.hasMoreElements()) {
                String key = (String)enumKeys.nextElement();
                tree.put(new String(key), bundle.getString(key));
            }
            for (Iterator it = tree.keySet().iterator(); it.hasNext();) {
                String key = (String)it.next();
                if (key.indexOf(LdapConstants.PREFIX_LDAP_USER)==0 || key.indexOf(LdapConstants.PREFIX_LDAP_USERS)==0) {
                    if (key.indexOf(LdapConstants.SUFFIX_FILTER)==-1 && key.indexOf(LdapConstants.SUFFIX_GROUP)==-1) {
                        if (key.indexOf(LdapConstants.PREFIX_LDAP_USERS)==0) {
                            logDebug("child users "+key+" = "+tree.get(key));
                        } else {
                            logDebug("unique user "+key+" = "+tree.get(key));
                        }
                        String currentFilter = bundle.getString(key+LdapConstants.SUFFIX_FILTER);
                        if (currentFilter==null || currentFilter.equals("")) {
                            currentFilter = LdapConstants.DEFAULT_FILTER;
                            logDebug("filter not found, default one used : "+currentFilter);
                        } else {
                            logDebug("filter found : "+currentFilter);
                        }
                        String currentGroup = bundle.getString(key+LdapConstants.SUFFIX_GROUP);
                        if (currentGroup==null || currentGroup.equals("")) {
                            throw new LdapUserException("No group is specified in the properties file for : "+key);
                        } else {
                            logDebug("group found : "+currentGroup);
                        }
                        LdapPropertiesFileEntryVO entry = new LdapPropertiesFileEntryVO();
                        entry.setKey(key);
                        entry.setValue((String)tree.get(key));
                        entry.setFilter(currentFilter);
                        entry.setGroup(currentGroup);
                        result.put(new String(key), entry);
                    }
                }
            }
        } catch (Exception e) {
            throw new LdapUserException(e);
        }
        return result;
    }


    public static void main(String[] args) {

//        String ldapU930my = bundle.getString("ldap_user_u930my");
//        String ldapU930myFilter = bundle.getString("ldap_user_u930my_filter");
//        String ldapConsultasLausanne = bundle.getString("ldap_users_consultas_lausanne");
//        String ldapConsultasLausanneFilter = bundle.getString("ldap_users_consultas_lausanne_filter");
//        String ldapConsultasMartigny = bundle.getString("ldap_users_consultas_martigny");
//        String ldapConsultasMartignyFilter = bundle.getString("ldap_users_consultas_martigny_filter");
//        String ldapConsultas = bundle.getString("ldap_users_consultas");
//        String ldapConsultasFilter = bundle.getString("ldap_users_consultas_filter");
        
        try {

            // use ldap.properties
            LdapUtil ldap = new LdapUtil();

            // use hashtable
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://10.201.16.154/");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, "u_svc_test");
            env.put(Context.SECURITY_CREDENTIALS, "password");        
            //LdapUtil ldap = new LdapUtil(env);
            
            // get one attribute
            Attribute oneAttribute = ldap.getOneAttribute(ldapU930my, "cn");
            
            // get some attributes
            String[] attributeNames = {"company", "language", "mail"};
            NamingEnumeration enumSomeAttrs = ldap.getSomeAttributes(ldapU930my, attributeNames);
            
            // get all attributes
            NamingEnumeration enumAll = ldap.getAllAttributes(ldapU930my);

            // get all attributes sorted
            TreeMap enumAllSorted = ldap.getAllAttributesSorted(ldapU930my);

            // modification d'un attribut
            //ldap.replaceAttribute(ldapU930my, "extensionAttribute15", "test3");

            // search ldap child users
            NamingEnumeration children = ldap.searchChildren(ldapConsultasLausanne, ldapConsultasLausanneFilter);
            int i=0;
            while (children.hasMore()) {
                SearchResult entry = (SearchResult)children.next();
                Attributes userAttrs = entry.getAttributes();
                System.out.println("lausanne entry DN "+(++i)+" "+userAttrs.get("cn")+" "+userAttrs.get("displayName"));
            }
            children = ldap.searchChildren(ldapConsultasMartigny, ldapConsultasMartignyFilter);
            i=0;
            while (children.hasMore()) {
                SearchResult entry = (SearchResult)children.next();
                Attributes userAttrs = entry.getAttributes();
                System.out.println("martigny entry DN "+(++i)+" "+userAttrs.get("cn")+" "+userAttrs.get("displayName"));
            }
            
            // search one ldap users
            NamingEnumeration users = ldap.searchOne(ldapU930my, ldapU930myFilter);
            i=0;
            while (users.hasMore()) {
                SearchResult entry = (SearchResult)users.next();
                Attributes userAttrs = entry.getAttributes();
                System.out.println("one user entry DN "+(++i)+" "+userAttrs.get("cn")+" "+userAttrs.get("displayName"));
            }

            // search ldap users in a subtree
            NamingEnumeration subtreeUsers = ldap.searchSubtree(ldapConsultas, ldapConsultasFilter);
            i=0;
            while (subtreeUsers.hasMore()) {
                SearchResult entry = (SearchResult)subtreeUsers.next();
                Attributes userAttrs = entry.getAttributes();
                System.out.println("subtree entry DN "+(++i)+" "+userAttrs.get("cn")+" "+userAttrs.get("displayName"));
            }
            
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    */
}