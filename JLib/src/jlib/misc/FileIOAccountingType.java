/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

import jlib.log.Log;

public class FileIOAccountingType
{
	public static FileIOAccountingType Open = new FileIOAccountingType("Open");
	public static FileIOAccountingType Close = new FileIOAccountingType("Close");
	public static FileIOAccountingType Flush = new FileIOAccountingType("Flush");
	public static FileIOAccountingType Read = new FileIOAccountingType("Read");
	public static FileIOAccountingType Write = new FileIOAccountingType("Write");
	public static FileIOAccountingType Rewrite = new FileIOAccountingType("Rewrite");
	public static FileIOAccountingType Position = new FileIOAccountingType("Position");
	
	private String m_csName = null;
	private int m_nNbAccesses = 0;
	private long m_lTimeAccesses_ns = 0;
	private long m_lMaxTimeAccess_ns = 0;
	
	FileIOAccountingType(String csName)
	{
		m_csName = csName; 
	}
	
	public void incAccessCount(long lTimeAccesses_ns)
	{
		m_nNbAccesses++;
		m_lTimeAccesses_ns += lTimeAccesses_ns;
		
		if(lTimeAccesses_ns > m_lMaxTimeAccess_ns)
			m_lMaxTimeAccess_ns = lTimeAccesses_ns;
	}
	
	public void logNormal()
	{
		Log.logNormal(toString());
	}
	
	public String toString()
	{
		return "Number of " + m_csName + ": " + m_nNbAccesses + "; Total time (ms): "+m_lTimeAccesses_ns/1000000 +"; Max time (ms): " + m_lMaxTimeAccess_ns/1000000;  
	}
}
