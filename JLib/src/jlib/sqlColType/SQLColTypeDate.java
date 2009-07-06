/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.sqlColType;

import java.sql.Timestamp;

import jlib.misc.NumberParser;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: SQLColTypeDate.java,v 1.3 2007/09/26 08:41:52 u930gn Exp $
 */
public class SQLColTypeDate
{
	private Timestamp m_ts = null;
	private boolean m_bValid = false;
	
	public SQLColTypeDate(String csYYYYMMDD)
	{
		int nYYYY = NumberParser.getAsInt(csYYYYMMDD.substring(0, 4));
		int nMM = NumberParser.getAsInt(csYYYYMMDD.substring(4, 6));
		int nDD = NumberParser.getAsInt(csYYYYMMDD.substring(6, 8));
		
		m_ts = new Timestamp(nYYYY, nMM, nDD, 0, 0, 0, 0);
		m_bValid = true;
	}
	
	public SQLColTypeDate(int nYYYYMMDD)
	{
		String csYYYYMMDD = "" + nYYYYMMDD;
		int nYYYY = NumberParser.getAsInt(csYYYYMMDD.substring(0, 4));
		int nMM = NumberParser.getAsInt(csYYYYMMDD.substring(4, 6));
		int nDD = NumberParser.getAsInt(csYYYYMMDD.substring(6, 8));
		
		m_ts = new Timestamp(nYYYY, nMM, nDD, 0, 0, 0, 0);
		m_bValid = true;
	}
	
	public void setInfinite()
	{
		m_ts = new Timestamp(2038, 12, 31, 0, 0, 0, 0);
		m_bValid = true;
	}
	
	public boolean isValid()
	{
		return m_bValid;
	}
	
	public Timestamp getTimeStamp()
	{
		return m_ts;
	}
}
