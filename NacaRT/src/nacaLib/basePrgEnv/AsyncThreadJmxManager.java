/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.basePrgEnv;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: AsyncThreadJmxManager.java,v 1.2 2006/12/20 13:59:53 u930bm Exp $
 */
public class AsyncThreadJmxManager
{
	AsyncThreadJmxManager()
	{
	}
	
	public static synchronized void view()
	{
		show(false);
		show(true);
	}
	
	public static synchronized void hide()
	{
		show(false);
	}

	private static void show(boolean bShow)
	{
		Set<Entry<String, AsyncThreadMBean> > entries =  m_hashSyncThread.entrySet();
		Iterator<Entry<String, AsyncThreadMBean> > iter = entries.iterator();
		while (iter.hasNext())
		{
			Entry<String, AsyncThreadMBean> entry = iter.next();
			AsyncThreadMBean asyncThreadMBean = entry.getValue();
			asyncThreadMBean.showBean(bShow);
		}
	}
	

	public static synchronized void startAsyncProgram(String csThreadId, String csThreadName, String csProgram, String csProgramParent, int nDelaySeconds)
	{
		String csId = getAsyncThreadMBeanId(csThreadId, csThreadName);
		AsyncThreadMBean asyncThreadMBean = m_hashSyncThread.get(csId);
		if(asyncThreadMBean == null)
		{
			asyncThreadMBean = new AsyncThreadMBean(csThreadId, csThreadName);
			asyncThreadMBean.setProgram(csProgram);
			asyncThreadMBean.setProgramParent(csProgramParent);
			asyncThreadMBean.setWait(true);
			asyncThreadMBean.setDelaySeconds(nDelaySeconds);
			m_hashSyncThread.put(csId, asyncThreadMBean);
		}
	}
	
	public static synchronized void setRunningAsyncProgram(String csThreadId, String csThreadName)
	{
		String csId = getAsyncThreadMBeanId(csThreadId, csThreadName);
		AsyncThreadMBean asyncThreadMBean = m_hashSyncThread.get(csId);
		if(asyncThreadMBean != null)
		{
			asyncThreadMBean.setWait(false);			
		}
	}
	
	public static synchronized void endAsyncProgram(String csThreadId, String csThreadName)
	{
		String csId = getAsyncThreadMBeanId(csThreadId, csThreadName);
		AsyncThreadMBean asyncThreadMBean = m_hashSyncThread.get(csId);
		if(asyncThreadMBean != null)
		{
			asyncThreadMBean.setAsyncThreadClosed();
			m_hashSyncThread.remove(csId);
		}
	}
	
	private static String getAsyncThreadMBeanId(String csThreadId, String csThreadName)
	{
		return csThreadId + "_" + csThreadName; 
	}
	
	private static Hashtable<String, AsyncThreadMBean> m_hashSyncThread = new Hashtable<String, AsyncThreadMBean>();
}