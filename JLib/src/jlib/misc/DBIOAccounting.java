/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

public class DBIOAccounting
{
	private static StopWatchNano ms_swnDBTimeIO = new StopWatchNano();
	private static DBIOAccountingType ms_lastDBIOAccountingType = null;
	private static long ms_lSumDBTimeIO_ns = 0; 
	
	public static void startDBIO(DBIOAccountingType type)
	{
		ms_lastDBIOAccountingType = type;
		ms_swnDBTimeIO.reset();
	}
	
	public static void endDBIO()
	{
		if(ms_lastDBIOAccountingType != null)
		{
			long lDBTimeIO_ns = ms_swnDBTimeIO.getElapsedTimeReset();		
			ms_lSumDBTimeIO_ns += lDBTimeIO_ns;
			ms_lastDBIOAccountingType.incAccessCount(lDBTimeIO_ns);
		}
	}
	
	public static String getSumAllDBIO()
	{
		long lms = ms_lSumDBTimeIO_ns / 1000000;
		double ds = (double)lms / 1000.0;
		double dm = (double)lms / 1000.0 / 60.0;
		return "Sum of all DB IO times: " + lms + " ms (or " + ds + " seconds or " + dm + " minutes)";
	}
}
