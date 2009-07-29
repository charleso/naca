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
/**
 * 
 */
package jlib.polling;

import java.io.File;
import java.util.ArrayList;

import jlib.threads.Timer;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class PollingTimer extends Timer
{
	private ArrayList<BaseDirectoryPoller> m_arrDirsPollers = null;
	
	synchronized public void addDirectoryPoller(BaseDirectoryPoller dirPoller)
	{
		if(m_arrDirsPollers == null)
			m_arrDirsPollers = new ArrayList<BaseDirectoryPoller>();
		m_arrDirsPollers.add(dirPoller);			
	}
	
	public boolean PollAtLoadTime()
	{
		return doPulse();
	}
	
	protected boolean pulse()
	{
		return doPulse();
	}
	
	synchronized private boolean doPulse()
	{
		if(m_arrDirsPollers != null)
		{
			for(int n=0; n<m_arrDirsPollers.size(); n++)
			{
				BaseDirectoryPoller dirPoller = m_arrDirsPollers.get(n);
				dirPoller.poll();
			}
		}
		return true;
	}
}
