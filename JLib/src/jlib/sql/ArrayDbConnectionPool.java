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
package jlib.sql;

import java.lang.management.MemoryPoolMXBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import jlib.log.Log;
import jlib.misc.BaseJmxGeneralStat;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class ArrayDbConnectionPool
{	
	private ArrayList<DbConnectionPool> m_arrDbConnectionPool = new ArrayList<DbConnectionPool>();
	private int m_nNbTotalStatementRemoved = 0;
	
	public synchronized void addDbConnectionPool(DbConnectionPool dbConnectionPool)
	{
		m_arrDbConnectionPool.add(dbConnectionPool);
	}
	
	// Normally executed in the context of the GCThread
	synchronized void handleCleanings(MemoryPoolMXBean tenuredPool, int nNbStatementsToRemoveBeforeGC, int nNbStatementForcedRemoved, int nNbSystemGCCall)
	{
		if(m_arrDbConnectionPool != null)
		{				
			for(int n=0; n<m_arrDbConnectionPool.size(); n++)
			{
				DbConnectionPool dbConnectionPool = m_arrDbConnectionPool.get(n);
				m_nNbTotalStatementRemoved += dbConnectionPool.garbageCollectorStatementsOfAllCollections();
			}
			int nNbStatementAggressiveRemoved = 0;
			if(tenuredPool != null && tenuredPool.isUsageThresholdExceeded() && nNbStatementForcedRemoved > 0)
			{
				// Aggressivelly remove statements is heap usage is to high 
				// Collect all statements from all pools
				SortedMap<Long, StatementPosInPool> mapStatements = new TreeMap<Long, StatementPosInPool>();
				for(int n=0; n<m_arrDbConnectionPool.size(); n++)
				{
					DbConnectionPool dbConnectionPool = m_arrDbConnectionPool.get(n);
					dbConnectionPool.buildStatementOrderedList(mapStatements);
				}
				nNbStatementAggressiveRemoved = aggressiveRemoveObsoleteStatements(tenuredPool, mapStatements, nNbStatementForcedRemoved, nNbSystemGCCall);
				if(nNbStatementAggressiveRemoved != 0)
					Log.logNormal("Aggressivelly removed " + nNbStatementAggressiveRemoved + " SQL statements, because mem usage is too high");
			}
			m_nNbTotalStatementRemoved += nNbStatementAggressiveRemoved;
			if(m_nNbTotalStatementRemoved >= nNbStatementsToRemoveBeforeGC)
			{
				Log.logNormal("Forcing garbage collector");
				tryForceGC(nNbSystemGCCall);
				m_nNbTotalStatementRemoved = 0;
			}
		}				
	}
		
	private int aggressiveRemoveObsoleteStatements(MemoryPoolMXBean tenuredPool, SortedMap<Long, StatementPosInPool> mapStatements, int nNbStatementForcedRemoved, int nNbSystemGCCall)
	{		
		boolean bGCToDo = false;
		int nNbStatementRemoved = 0;
		
		Set<Map.Entry<Long, StatementPosInPool>> set = mapStatements.entrySet();
		Iterator<Map.Entry<Long, StatementPosInPool>> iterMapEntry = set.iterator();
		boolean bMemUsageTooMuch = true;
		while(iterMapEntry.hasNext() && bMemUsageTooMuch)
		{
			Map.Entry<Long, StatementPosInPool> mapEntry = iterMapEntry.next();
			StatementPosInPool statementPosInPool = mapEntry.getValue();
			
			boolean bRemoved = statementPosInPool.forceRemoveStatement();
			if(bRemoved)
			{
				bGCToDo = true;
				nNbStatementRemoved++;
				if((nNbStatementRemoved % nNbStatementForcedRemoved) == 0)
				{
					tryForceGC(nNbSystemGCCall);
					bGCToDo = false;
					bMemUsageTooMuch = tenuredPool.isUsageThresholdExceeded();
				}
			}				
		}
		if(bGCToDo)
		{
			tryForceGC(nNbSystemGCCall);
		}
		return nNbStatementRemoved;
	}
	
	private void tryForceGC(int nNbSystemGCCall)
	{
		for(int n=0; n<nNbSystemGCCall; n++)
		{
			BaseJmxGeneralStat.incCounter(BaseJmxGeneralStat.COUNTER_INDEX_NbAggressiveGC);
			System.gc();
		}
	}
	
	// Normally executed in the context of the JMX Thread
	public synchronized void forceRemoveAllDBConnections()
	{
		ConnectionGenerationManager.incCurrentGenerationId();	// Change current generation of connection 
		if(m_arrDbConnectionPool != null)
		{
			for(int n=0; n<m_arrDbConnectionPool.size(); n++)
			{
				DbConnectionPool dbConnectionPool = m_arrDbConnectionPool.get(n);
				dbConnectionPool.forceRemoveAllStatementsOfAllCollections();
			}
		}
	}
}
