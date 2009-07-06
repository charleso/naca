/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.display;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import jlib.jmxMBean.BaseCloseMBean;
import jlib.log.Log;
import jlib.xml.XSLTransformer;

public class ResourceManager extends BaseCloseMBean
{
	public ResourceManager()
	{
		super("_ XSLTResources", "_ XSLTResources");
	}
	
	protected void buildDynamicMBeanInfo()
	{
    	addAttribute("NbResourcesFiles", getClass(), "NbResourcesFiles", int.class);
    	addAttribute("NbResourcesCached", getClass(), "NbResourcesCached", int.class);
    	addOperation("Unload cached ressource", getClass(), "unloadCachedResources");
	}
	
	//public static ArrayList<DbPreparedStatement> m_arrDEBUG = null;	// To be removed
	
	public int getNbResourcesFiles()
	{
		int n = 0;
		m_unloadRWLock.readLock().lock();
		if(m_tabXSLFiles != null)
			n = m_tabXSLFiles.size();
		m_unloadRWLock.readLock().unlock();
		return n;
	}

	public int getNbResourcesCached()
	{
		int n = 0;
		m_unloadRWLock.readLock().lock();
		if(m_tabXSLTransformerCache != null)
			n = m_tabXSLTransformerCache.size();
		m_unloadRWLock.readLock().unlock();
		return n;
	}

	public void unloadCachedResources()
	{
		Log.logImportant("unloadCachedResources started");
		m_unloadRWLock.writeLock().lock();	// Get exclusive lock
		
		if(m_tabXSLTransformerCache != null)
			m_tabXSLTransformerCache.clear();		
		
		m_unloadRWLock.writeLock().unlock();	// Release exclusive lock; unlocking optinal thread waiting to obtain read lock in getUnusedInstance()
		Log.logImportant("unloadCachedResources ended");
	}

	public void setXSLFilePath(String ID, File filePath)
	{
		m_tabXSLFiles.put(ID, filePath) ;
	}
	
	public void setXSLFilePath(String ID, String csXSLFilePath)
	{
		setXSLFilePath(ID, new File(csXSLFilePath)) ;
	}
	
	public XSLTransformer getXSLTransformer(String ID)
	{
		m_unloadRWLock.readLock().lock();
		if (!m_tabXSLTransformerCache.containsKey(ID))
		{
			File f = m_tabXSLFiles.get(ID) ;
			if (f == null)
			{
				m_unloadRWLock.readLock().unlock();
				return null;
			}				
			XSLTransformer tr = XSLTransformer.loadFromFile(f, true) ;
			m_tabXSLTransformerCache.put(ID, tr) ;
			m_unloadRWLock.readLock().unlock();
			return tr ;
		}
		XSLTransformer tr = m_tabXSLTransformerCache.get(ID) ;
		m_unloadRWLock.readLock().unlock();
		return tr;
	}

	private Map<String, File> m_tabXSLFiles = new HashMap<String, File>() ;
	private Map<String, XSLTransformer> m_tabXSLTransformerCache = new HashMap<String, XSLTransformer>() ;
	private ReentrantReadWriteLock m_unloadRWLock = new ReentrantReadWriteLock();
}
