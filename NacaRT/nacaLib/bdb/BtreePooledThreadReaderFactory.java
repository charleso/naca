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

import jlib.threads.BasePooledThreadFactory;
import jlib.threads.PoolOfThreads;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: BtreePooledThreadReaderFactory.java,v 1.1 2006/11/29 09:31:30 u930di Exp $
 */
public class BtreePooledThreadReaderFactory extends BasePooledThreadFactory
{
	private BtreeFile m_btreeFile = null;
	public BtreePooledThreadReaderFactory(BtreeFile btreeFile)
	{
		m_btreeFile = btreeFile;
	}
	
	public BtreePooledReaderThread make(PoolOfThreads owningPool)
	{
		BtreePooledReaderThread thread = new BtreePooledReaderThread(owningPool);
		thread.setBtreeFile(m_btreeFile);
		
		return thread;
	}
}