/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.bdb;

import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;
import jlib.threads.PooledThread;
import jlib.threads.PoolOfThreads;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: BtreePooledWriterThread.java,v 1.1 2006/11/29 09:31:30 u930di Exp $
 */
public class BtreePooledWriterThread extends PooledThread
{
	private BtreeKeyDescription m_keyDescription = null;
	
	public BtreePooledWriterThread(PoolOfThreads owningPool)
	{
		super(owningPool);
	}
	
	void setBtreeKeyDescription(BtreeKeyDescription keyDescription)
	{
		m_keyDescription = keyDescription;
	}
	
	public boolean preRun()
	{
		// Fill the TLS with key description
		if(m_keyDescription != null)
		{
			TempCacheLocator.setTempCache();	// Init TLS
			TempCache t = TempCacheLocator.getTLSTempCache();
			if(t != null)
			{
				t.setBtreeKeyDescription(m_keyDescription);
				return true;
			}	
		}
		return false;	// No key desc !		
	}
	
	public void postRun()
	{
		TempCacheLocator.relaseTempCache();
	}
	
}
