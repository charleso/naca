/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.persitantQueue;
import jlib.log.Log;
import jlib.threads.BaseThread;
import jlib.threads.Threadutil;

public abstract class PersistentDequeueObjectThread extends BaseThread
{
	private PersistantQueue m_persistantQueue = null;
	private int m_nLoopWait_ms = 0;
	private BaseQueueItemFactory m_baseQueueItemFactory = null;

	protected PersistentDequeueObjectThread(PersistantQueue persistantQueue, BaseQueueItemFactory baseQueueItemFactory, int nLoopWait_ms)
	{
		m_baseQueueItemFactory = baseQueueItemFactory;
		m_persistantQueue = persistantQueue;
		m_nLoopWait_ms = nLoopWait_ms;
	}

	public void run()
	{
		boolean bContinue = true;
		while(bContinue)
		{   
			Object object = m_persistantQueue.getFirst(m_baseQueueItemFactory);
			if(object == null)
			{
				bContinue = Threadutil.wait(m_nLoopWait_ms);   
			}
			else
			{
				try
				{
					bContinue = handleObject(object);
				}
				catch (Exception e)
				{
					Log.logCritical("Exception catched in handleObjet of PersistentDequeueObjectThread::run(): "+e.toString()); 
				}
			}
		}
	}
	
	protected abstract boolean handleObject(Object object);
}
