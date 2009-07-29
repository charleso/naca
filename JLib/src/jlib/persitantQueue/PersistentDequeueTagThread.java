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
package jlib.persitantQueue;
import jlib.threads.BaseThread;
import jlib.threads.Threadutil;
import jlib.xml.Tag;

public abstract class PersistentDequeueTagThread extends BaseThread
{
	private PersistantQueue m_persistantQueue = null;
	private int m_nLoopWait_ms = 0;

	protected PersistentDequeueTagThread(PersistantQueue persistantQueue, int nLoopWait_ms)
	{
		m_persistantQueue = persistantQueue;
		m_nLoopWait_ms = nLoopWait_ms;
	}

	public void run()
	{
		boolean bContinue = true;
		while(bContinue)
		{   
			Tag tagItem = m_persistantQueue.getFirstAsTag();
			if(tagItem == null)
			{
				bContinue = Threadutil.wait(m_nLoopWait_ms);   
			}
			else
			{
				bContinue = handleObject(tagItem);
			}
		}
	}
	
	protected abstract boolean handleObject(Tag tagItem);
}
