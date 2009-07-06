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

import nacaLib.tempCache.TempCacheLocator;
import jlib.threads.PooledThread;
import jlib.threads.PoolOfThreads;
import jlib.threads.BasePooledThreadFactory;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: BtreePooledThreadWriterFactory.java,v 1.1 2006/11/29 09:31:30 u930di Exp $
 */
public class BtreePooledThreadWriterFactory extends BasePooledThreadFactory
{
	public BtreePooledThreadWriterFactory()
	{
	}
	
	public BtreePooledWriterThread make(PoolOfThreads owningPool)
	{
		// Key description is stored in the TLS of the current thread (which creates the pool of threads) 
		BtreeKeyDescription keyDescription = TempCacheLocator.getTLSTempCache().getBtreeKeyDescription();
		
		//setBtreeKeyDescription(m_keyDescription);
		BtreePooledWriterThread thread = new BtreePooledWriterThread(owningPool);
		thread.setBtreeKeyDescription(keyDescription);
		return thread;
	}
}
