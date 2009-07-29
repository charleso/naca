/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
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
 * @version $Id$
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