/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

public class FileIOAccounting
{
	private static StopWatchNano ms_swnFileTimeIO = new StopWatchNano();
	private static FileIOAccountingType ms_lastFileIOAccountingType = null;
	
	public static void startFileIO(FileIOAccountingType type)
	{
		ms_lastFileIOAccountingType = type;
		ms_swnFileTimeIO.reset();
	}
	
	public static void endFileIO()
	{
		if(ms_lastFileIOAccountingType != null)
		{
			long lFileTimeIO_ns = ms_swnFileTimeIO.getElapsedTimeReset();		
			ms_lastFileIOAccountingType.incAccessCount(lFileTimeIO_ns);
		}
	}
}
