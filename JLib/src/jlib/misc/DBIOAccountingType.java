/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

import jlib.log.Log;

public class DBIOAccountingType
{
	public static DBIOAccountingType Prepare = new DBIOAccountingType("Prepare");
	public static DBIOAccountingType OpenCursor = new DBIOAccountingType("OpenCursor");
	public static DBIOAccountingType CloseResultset = new DBIOAccountingType("CloseResultset");
	public static DBIOAccountingType FetchCursor = new DBIOAccountingType("FetchCursor");
	public static DBIOAccountingType Select = new DBIOAccountingType("Select");
	public static DBIOAccountingType Insert = new DBIOAccountingType("Insert");
	public static DBIOAccountingType Update = new DBIOAccountingType("Update");
	public static DBIOAccountingType Delete = new DBIOAccountingType("Delete");
	public static DBIOAccountingType CreateTable = new DBIOAccountingType("CreateTable");
	public static DBIOAccountingType DropTable = new DBIOAccountingType("DropTable");
	public static DBIOAccountingType Declare = new DBIOAccountingType("Declare");
	public static DBIOAccountingType Lock = new DBIOAccountingType("Lock");
	
	
	private String m_csName = null;
	private int m_nNbAccesses = 0;
	private long m_lTimeAccesses_ns = 0;
	private long m_lMaxTimeAccess_ns = 0;
	
	DBIOAccountingType(String csName)
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
