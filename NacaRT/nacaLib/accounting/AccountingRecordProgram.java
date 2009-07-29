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
/**
 * 
 */
package nacaLib.accounting;

import java.util.Date;


import jlib.misc.StopWatchNano;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class AccountingRecordProgram
{
	public AccountingRecordProgram()
	{
		m_dateStart = new Date();
		m_stopWatchNano.reset();
	}
	
	public void beginRunProgram(String csProgramName)
	{
		m_csProgramName = csProgramName;
	}
	
	public void endRunProgram(CriteriaEndRunMain criteria)
	{
		m_nRunTime_ms = (int)StopWatchNano.getMilliSecond(m_stopWatchNano.getElapsedTime());
		m_csCriteriaEnd = criteria.getName();
	}
	
	int getRunTime_ms()
	{
		return m_nRunTime_ms;
	}
	
	long getRunTimeIO_ns()
	{
		return m_nRunTimeIO_ns;
	}
	
	long getTimeDateStart()
	{
		if(m_dateStart != null)
			return m_dateStart.getTime();
		return 0;
	}
	
	void reportDBIOTime(long lDBIOTime_ns)
	{
		m_nRunTimeIO_ns += lDBIOTime_ns;
	}
	
	String getProgramName()
	{
		return m_csProgramName;
	}
	
	String getCriteriaEnd()
	{
		return m_csCriteriaEnd;
	}
	
	private Date m_dateStart = null;
	private String m_csProgramName = "";
	private int m_nRunTime_ms = 0;
	private long m_nRunTimeIO_ns = 0;

	private String m_csCriteriaEnd = "";
	private StopWatchNano m_stopWatchNano = new StopWatchNano();	
}
