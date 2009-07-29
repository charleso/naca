/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.program;

import java.util.Enumeration;
import java.util.Hashtable;

import jlib.log.Log;
import jlib.sql.DbPreparedStatement;

import nacaLib.basePrgEnv.BaseProgram;

public class StatCoverage
{
	private static Hashtable<String, CoverageStatByPrg> ms_statPerPrg = new Hashtable<String, CoverageStatByPrg>();	// Key: Program
	
	public static void logStatCoverage(StatCoverageType type, BaseProgram program, String csName)
	{
		String csProgramName = program.getSimpleName();
		CoverageStatByPrg statByPrg = ms_statPerPrg.get(csProgramName);
		if(statByPrg == null)
		{
			statByPrg = new CoverageStatByPrg(); 
			ms_statPerPrg.put(csProgramName, statByPrg);
		}
		statByPrg.add(type, csName);
	}
	
	public static void logStatCoverageSubProgram(StatCoverageType type, String csSubProgramName)
	{
		int gg = 0;
	}
	
	public static void logResults()
	{
		String cs = "";
		Enumeration<String> eKeys = ms_statPerPrg.keys();
		while(eKeys.hasMoreElements())
		{
			String csProgramName = eKeys.nextElement();
			cs = "Program: "+csProgramName+"\n";
			CoverageStatByPrg coverageStatByPrg = ms_statPerPrg.get(csProgramName);
			cs += coverageStatByPrg.toString();
		}		
		
		Log.logCritical(cs);
	}
	
	
}
