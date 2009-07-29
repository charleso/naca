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
package nacaLib.programPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import jlib.log.Log;
import jlib.misc.StopWatch;

import nacaLib.base.JmxGeneralStat;
import nacaLib.basePrgEnv.BaseProgram;

public class ProgramPoolManager //extends BaseOpenMBean
{
	private Hashtable<String, ProgramInstancesPool> m_hashProgramInstancesPool = null;
	
	public ProgramPoolManager(boolean bUseJmx)
	{
		m_hashProgramInstancesPool = new Hashtable<String, ProgramInstancesPool>();	// hash table of ProgramInstancePool, indexed by program name
		if(bUseJmx)
			JmxGeneralStat.addProgramPoolManager(this);
	}
	
	public void setShowProgramBeans(boolean b)
	{
		Collection<ProgramInstancesPool> col = m_hashProgramInstancesPool.values();
		Iterator<ProgramInstancesPool> iter = col.iterator();
		while(iter.hasNext())
		{
			ProgramInstancesPool p = iter.next();
			p.showBean(b);
		}
	}
	
	public BaseProgram loadPooledProgramInstance(String csProgramName)
	{
		ProgramInstancesPool programInstancesPool = m_hashProgramInstancesPool.get(csProgramName);
		if(programInstancesPool == null)	// new program pool: this prg has never been loaded
		{
			programInstancesPool = createProgramInstancesPool(csProgramName);
		}
		
		BaseProgram program = programInstancesPool.getOrCreateUnusedInstance();
		// if programInstance != null then we are inside a read lock, else no read lock set
		
		// Double check programInstancesPool, as it may have been destroyed in jmx thread (double check pattern)
		programInstancesPool = m_hashProgramInstancesPool.get(csProgramName);
		if(programInstancesPool == null)	// new program pool: this prg has never been loaded
		{
			programInstancesPool = createProgramInstancesPool(csProgramName);
		}

		return program;
	}	
	
	public BaseProgram preloadSecondInstanceProgram(String csProgramName)
	{
		ProgramInstancesPool programInstancesPool = m_hashProgramInstancesPool.get(csProgramName);
		if(programInstancesPool != null)	// The prg pool must exists
		{
			BaseProgram program = programInstancesPool.preloadSecondInstance();
			return program;
		}
		return null;
	}	
	
	public void unloadAllPrograms(boolean bDoGCAfterEachProgramUnload)
	{
		Collection<ProgramInstancesPool> colProgramInstancesPool = m_hashProgramInstancesPool.values();
		if(colProgramInstancesPool != null)
		{
			// Create another ProgramInstancesPool container, as m_hashProgramInstancesPool will be structurally modified in ProgramInstancesPool::unloadProgram() call
			int nNbEntries = 0;
			ArrayList<ProgramInstancesPool> arrProgramInstancesPool = new ArrayList<ProgramInstancesPool>();
			Iterator<ProgramInstancesPool> iter = colProgramInstancesPool.iterator();
			while(iter.hasNext())
			{
				ProgramInstancesPool programInstancesPool = iter.next();
				arrProgramInstancesPool.add(programInstancesPool);
				nNbEntries++;
			}
			colProgramInstancesPool = null; 
			
			
			StopWatch sw = new StopWatch(); 
			for(int n=0; n<nNbEntries; n++)
			{
				ProgramInstancesPool programInstancesPool = arrProgramInstancesPool.get(n);
				programInstancesPool.unloadProgram();
				if(bDoGCAfterEachProgramUnload)
					System.gc();
				programInstancesPool = null;
			}
			arrProgramInstancesPool = null;
			
			Log.logNormal("Unload time="+sw.getElapsedTimeReset());
			System.gc();
			Log.logNormal("GC 1 after Unload time="+sw.getElapsedTimeReset());
			System.gc();
			Log.logNormal("GC 2 after Unload time="+sw.getElapsedTimeReset());
			System.gc();
			Log.logNormal("GC 3 after Unload time="+sw.getElapsedTimeReset());
		}		
	}
	
	public ProgramInstancesPool getProgramPool(String csProgramName)
	{
		ProgramInstancesPool programInstancesPool = m_hashProgramInstancesPool.get(csProgramName);
		return programInstancesPool;
	}
	
	private ProgramInstancesPool createProgramInstancesPool(String csProgramName)
	{
		// create a program pool, and register it into the hash table
		ProgramInstancesPool programPool = new ProgramInstancesPool(this, csProgramName);
		m_hashProgramInstancesPool.put(csProgramName, programPool);
		return programPool;
	}
	
	public void removeProgramInstancesPool(String csProgramName)
	{
		m_hashProgramInstancesPool.remove(csProgramName);
	}

	
	public void returnProgramInstanceToPool(BaseProgram program)
	{
		String csProgramName = program.getProgramManager().getProgramName();
		ProgramInstancesPool programInstancesPool = m_hashProgramInstancesPool.get(csProgramName);
		if(programInstancesPool != null)
		{
			programInstancesPool.returnProgram(program);
		}		
	}
	
	public int getNbProgramStacked()
	{
		int n = 0;
		Collection<ProgramInstancesPool> col = m_hashProgramInstancesPool.values();
		Iterator<ProgramInstancesPool> iter = col.iterator();
		while(iter.hasNext())
		{
			ProgramInstancesPool p = iter.next();
			n += p.getNbInstancesStacked();
		}
		return n;
	}
}
