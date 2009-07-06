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

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class SQLLoadStatus
{
	public static final SQLLoadStatus loadSuccess = new SQLLoadStatus(true, false);
	public static final SQLLoadStatus loadFailure = new SQLLoadStatus(false, false);
	public static final SQLLoadStatus loadSuccessWithDuplicates = new SQLLoadStatus(true, true);
	
	private boolean m_bSuccess;
	private boolean m_bDuplicates;
	
	private SQLLoadStatus(boolean bSuccess, boolean bDuplicates)
	{
		m_bSuccess = bSuccess;
		m_bDuplicates = bDuplicates;
	}
	
	public boolean isSuccess()
	{
		return m_bSuccess;
	}
	
	public boolean hadDuplicates()
	{
		return m_bDuplicates;
	}
	
	public static SQLLoadStatus updateWithLocalStatus(SQLLoadStatus globalStatus, SQLLoadStatus status)
	{
		if(!status.m_bSuccess)
			return loadFailure;
		if(globalStatus.m_bDuplicates || status.m_bDuplicates)
			return loadSuccessWithDuplicates;
		return loadSuccess;			
	}
	
	public String toString()
	{
		String cs;
		if(m_bSuccess)
			cs = "Success";
		else
			cs = "Failure";
		
		if(m_bDuplicates)
			cs += " with duplicate keys";
		else
			cs += " without duplicate keys";
		return cs;
	}
}
