/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.sql;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.util.List;

import jlib.misc.BaseJmxGeneralStat;
import jlib.xml.Tag;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: ThreadStatementGC.java,v 1.16 2007/03/15 06:44:38 u930di Exp $
 */
public class ThreadStatementGC extends Thread
{
	private int m_nPeriod_ms = 0;
	private ArrayDbConnectionPool m_arrayDbConnectionPool = null;
	private MemoryPoolMXBean m_tenuredPool = null;
	private int m_nNbStatementForcedRemoved = 0;
	private boolean m_bActive = false;
	private int m_nNbStatementsToRemoveBeforeGC = 0;
	private int m_nNbSystemGCCall = 0;
	private int m_nMaxPermanentHeap_Mo = 0;
	private boolean m_bMaxPermanentHeap_MoSet = false;

	public ThreadStatementGC(Tag tagGCThread, ArrayDbConnectionPool arrayDbConnectionPool)
	{				
		m_arrayDbConnectionPool = arrayDbConnectionPool;
		m_bActive = tagGCThread.getValAsBoolean("ActivateThreadGarbageCollectorStatement");
		if(m_bActive)
		{
			m_nPeriod_ms = tagGCThread.getValAsInt("GarbageCollectorStatement_ms");
			if(m_nPeriod_ms <= 30000)
				m_nPeriod_ms = 30000;	// Cannot be less than 30 seconds
			m_nNbStatementForcedRemoved = tagGCThread.getValAsInt("NbStatementForcedRemoved");
			m_nMaxPermanentHeap_Mo = tagGCThread.getValAsInt("MaxPermanentHeap_Mo");
						
			m_nNbStatementsToRemoveBeforeGC = tagGCThread.getValAsInt("NbStatementsToRemoveBeforeGC", -1);
			m_nNbSystemGCCall = tagGCThread.getValAsInt("NbSystemGCCall", 0);
			
			if(m_nMaxPermanentHeap_Mo > 0 && m_nNbStatementForcedRemoved > 0)
			{
				setMemThreshold();
			}
		}
	}

	private void setMemThreshold()
	{
		m_bMaxPermanentHeap_MoSet = false;

		List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
		for (MemoryPoolMXBean p: pools)
		{
			if(p.getType().compareTo(MemoryType.HEAP) == 0)
			{
				String cs = p.getName();
				if(cs.equalsIgnoreCase("Tenured gen"))
				{
					long l = 1024L * 1024L * (long)m_nMaxPermanentHeap_Mo;
					p.setUsageThreshold(l);
					m_tenuredPool = p;
				}				
			}
		}
	}

	public synchronized void setCurrentMaxPermanentHeap_Mo(int nMaxPermanentHeap_Mo)
	{
		m_nMaxPermanentHeap_Mo = nMaxPermanentHeap_Mo;
		m_bMaxPermanentHeap_MoSet = true;
	}
	
	public synchronized int getCurrentMaxPermanentHeap_Mo()
	{
		return m_nMaxPermanentHeap_Mo;
	}
	
//	public void addDbConnectionPool(DbConnectionPool dbConnectionPool)
//	{
//		if(m_arrDbConnectionPool == null)
//			m_arrDbConnectionPool = new ArrayList<DbConnectionPool>();
//		m_arrDbConnectionPool.add(dbConnectionPool);
//	}
	
	public void run()
    {
		while(m_bActive && waitPeriod())
		{
			BaseJmxGeneralStat.incCounter(BaseJmxGeneralStat.COUNTER_INDEX_NbRunThreadGC);
			if(m_bMaxPermanentHeap_MoSet)	// Mem threshhold has changed
			{
				setMemThreshold();
			}
			if(m_arrayDbConnectionPool != null)
				m_arrayDbConnectionPool.handleCleanings(m_tenuredPool, m_nNbStatementsToRemoveBeforeGC, m_nNbStatementForcedRemoved, m_nNbSystemGCCall);
		}
    }

//	private synchronized void doRun()
//	{
//		if(m_arrDbConnectionPool != null)
//		{				
//			for(int n=0; n<m_arrDbConnectionPool.size(); n++)
//			{
//				DbConnectionPool dbConnectionPool = m_arrDbConnectionPool.get(n);
//				m_nNbTotalStatementRemoved += dbConnectionPool.garbageCollectorStatementsOfAllCollections();
//			}
//			int nNbStatementAggressiveRemoved = 0;
//			if(m_tenuredPool != null && m_tenuredPool.isUsageThresholdExceeded() && m_nNbStatementForcedRemoved > 0)
//			{
//				// Aggressivelly remove statements is heap usage is to high 
//				// Collect all statements from all pools
//				SortedMap<Long, StatementPosInPool> mapStatements = new TreeMap<Long, StatementPosInPool>();
//				for(int n=0; n<m_arrDbConnectionPool.size(); n++)
//				{
//					DbConnectionPool dbConnectionPool = m_arrDbConnectionPool.get(n);
//					dbConnectionPool.buildStatementOrderedList(mapStatements);
//				}
//				nNbStatementAggressiveRemoved = aggressiveRemoveObsoleteStatements(mapStatements);
//				if(nNbStatementAggressiveRemoved != 0)
//					Log.logNormal("Aggressivelly removed " + nNbStatementAggressiveRemoved + " SQL statements, because mem usage is too high");
//			}
//			m_nNbTotalStatementRemoved += nNbStatementAggressiveRemoved;
//			if(m_nNbTotalStatementRemoved >= m_nNbStatementsToRemoveBeforeGC)
//			{
//				Log.logNormal("Forcing garbage collector");
//				tryForceGC();
//				m_nNbTotalStatementRemoved = 0;
//			}
//		}				
//	}
	
	private boolean waitPeriod()
	{
		try
		{			
			Thread.sleep(m_nPeriod_ms);
			return true;
		}
		catch (InterruptedException e)
		{
			return false;
		}
	}
}
