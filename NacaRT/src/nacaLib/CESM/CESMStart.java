/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on Oct 26, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.CESM;

import jlib.misc.DateUtil;
import nacaLib.base.*;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;
import nacaLib.varEx.Var;

public class CESMStart extends CJMapObject
{
	public CESMStart(String cs, BaseEnvironment env)
	{
		m_csTransID = cs ;
		m_Environment = env;
	}
	private BaseEnvironment m_Environment = null ;
	public String m_csTransID = "" ;
	public String m_csTermID = "" ;
	private CESMStartData m_data = null;
	
	public CESMStart termID(String string)
	{
		m_csTermID = string ;
		return this ;
	}
	public void doStart()
	{
		if (!m_csTermID.equals(""))
		{
			assertIfFalse(m_Environment.getTerminalID().equals(m_csTermID)) ;
			m_Environment.enqueueProgram(m_csTransID, m_data) ;
		}
		else
		{
			TempCache t = TempCacheLocator.getTLSTempCache();
			String csCurrentProgram = t.getProgramManager().getProgramName();
			m_Environment.StartAsynchronousProgram(m_csTransID, csCurrentProgram, m_data, m_nIntervalTimeSeconds);
		}
	}

	public CESMStart dataFrom(Var var, Var varLength)
	{
		m_data = new CESMStartData(var, varLength);
		return this ;
	}
	
	public CESMStart dataFrom(Var var)
	{
		m_data = new CESMStartData(var, null);
		return this ;
	}
	
	/**
	 * @param trans_Time
	 * @return
	 */
	public CESMStart time(Var trans_Time)
	{
		// trans_Time uses format HHMMSS
		int nNbSecondsSinceMidnightFromNow_s = DateUtil.getNbSecondSinceMidnight();
		int nNextTime_s = DateUtil.getNbSecondsFromHour(trans_Time.getInt());
		if (nNbSecondsSinceMidnightFromNow_s < nNextTime_s)	// We are before next time 
			m_nIntervalTimeSeconds = nNextTime_s - nNbSecondsSinceMidnightFromNow_s;
		else
			m_nIntervalTimeSeconds = 0;
		return this;
	}
	/**
	 * @param interval
	 * @return
	 */
	public CESMStart interval(Var interval)
	{
		// interval uses format HHMMSS
		m_nIntervalTimeSeconds = DateUtil.getNbSecondsFromHour(interval.getInt());
		return this;
	}
	protected int m_nIntervalTimeSeconds = 0 ;
}
