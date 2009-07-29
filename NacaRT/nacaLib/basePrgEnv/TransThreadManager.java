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
package nacaLib.basePrgEnv;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class TransThreadManager
{
	TransThreadManager()
	{
	}
	
	public static synchronized void view(int nMinRunTime_s)
	{
		show(false, 0);
		show(true, nMinRunTime_s);
	}
	
//	static void registerTransBean(TransThreadMBean transThreadMBean)
//	{
//		ms_arrShownTransThreadMBean.add(transThreadMBean);
//	}
	
	public static synchronized void hide()
	{
		show(false, 0);
//		for(int n=0; n<ms_arrShownTransThreadMBean.size(); n++)
//		{
//			TransThreadMBean transThreadMBean = ms_arrShownTransThreadMBean.get(n);
//			transThreadMBean.showBean(false);
//		}
//		ms_arrShownTransThreadMBean.clear();
	}

	private static void show(boolean bShow, int nMinRunTime_s)
	{
		Set<Entry<Integer, TransThreadMBean> > entries =  m_hashTrans.entrySet();
		Iterator<Entry<Integer, TransThreadMBean> > iter = entries.iterator();
		while (iter.hasNext())
		{
			Entry<Integer, TransThreadMBean> entry = iter.next();
			TransThreadMBean transThreadMBean = entry.getValue();
			if(!bShow)
				transThreadMBean.showBean(bShow);
			else 
			{
				if(transThreadMBean.getLastTransactionExecTime_s() * 1000 >= nMinRunTime_s)
					transThreadMBean.showBean(bShow);
			}				
		}
	}
	

	static synchronized void startTransaction(BaseEnvironment env)
	{
		if(env != null && env.canManageThreadMBean())
		{
			Integer iEnvId = env.getEnvId();
			TransThreadMBean transThreadMBean = m_hashTrans.get(iEnvId);
			if(transThreadMBean == null)
			{
				transThreadMBean = new TransThreadMBean(env);
				m_hashTrans.put(iEnvId, transThreadMBean);
			}
		}
	}
	
	static synchronized void endTransaction(BaseEnvironment env)
	{
		if(env != null && env.canManageThreadMBean())
		{
			Integer iEnvId = env.getEnvId();
			TransThreadMBean transThreadMBean = m_hashTrans.get(iEnvId);
			if(transThreadMBean != null)
			{
				if(BaseResourceManager.getUsingJmx())
				{
					transThreadMBean.setEnvClosed();
				}
				m_hashTrans.remove(iEnvId);
			}
		}
	}
	
	private static Hashtable<Integer, TransThreadMBean> m_hashTrans = new Hashtable<Integer, TransThreadMBean>();
	//private static ArrayList<TransThreadMBean> ms_arrShownTransThreadMBean = new ArrayList<TransThreadMBean>();;
}
