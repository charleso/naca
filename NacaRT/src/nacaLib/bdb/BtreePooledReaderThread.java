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

import jlib.threads.PoolOfThreads;
import jlib.threads.PooledThread;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: BtreePooledReaderThread.java,v 1.1 2006/11/29 09:31:30 u930di Exp $
 */
public class BtreePooledReaderThread extends PooledThread
{
	private BtreeFile m_btreeFile = null;
	
	public BtreePooledReaderThread(PoolOfThreads owningPool)
	{
		super(owningPool);
	}
	
	void setBtreeFile(BtreeFile btreeFile)
	{
		m_btreeFile = btreeFile; 
	}
	
	public void run()
	{
		// fill the queue; act as a producer; Only 1 background thread can enqueue sorted records 
		byte tbyDataWithHeader[] = m_btreeFile.syncGetFirst();
		while(tbyDataWithHeader != null)
		{
			SortedRecordReq sortedRecordReq = new SortedRecordReq(tbyDataWithHeader);
			m_owningPool.enqueue(sortedRecordReq);
			
			tbyDataWithHeader = m_btreeFile.syncGetNext();
		}
		m_owningPool.enqueueFinalRequests();	// So that the main thread knows that no more records are pending in the queue
		
		// This backgroubd thread finishes here; no need to join
	}
}
