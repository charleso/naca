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
package nacaLib.basePrgEnv;

import jlib.jmxMBean.BaseCloseMBean;
import jlib.misc.DateUtil;
import jlib.misc.StopWatch;
import nacaLib.base.JmxGeneralStat;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class AsyncThreadMBean extends BaseCloseMBean
{
	private String m_csThreadName = null;
	private String m_csThreadId = null;
	private boolean m_bWaiting = false;
	private String m_csLastWaitEvent = ""; 
	private String m_csProgram = "";
	private String m_csProgramParent = "";
	private int m_nDelaySeconds = 0;
	private StopWatch m_sw = null;

	AsyncThreadMBean(String csThreadId, String csThreadName)
	{
		super();
		
		m_csThreadName = csThreadName;
		m_csThreadId = csThreadId;
		m_sw = new StopWatch();
		if(JmxGeneralStat.showAsyncThreadBeans())
		{
			create();
		}
	}
	
	void setAsyncThreadClosed()
	{
		unregisterMBean();
	}
	
	void showBean(boolean bToShow)
	{
		if(bToShow && !isBeanCreated())
			create();
		else if(!bToShow && isBeanCreated())
			unregisterMBean();
	}
	
	private void create()
	{
		String cs = getAsyncThreadMBeanId(m_csThreadName, m_csThreadId);
		createMBean(cs, cs);
	}
	
	void setWait(boolean bWaiting)
	{
		if(m_bWaiting != bWaiting)	// Changing state
			m_sw.Reset();
		m_bWaiting = bWaiting;
		m_csLastWaitEvent = DateUtil.getDisplayTimeStamp();
	}
	
	void setProgram(String csProgram)
	{
		m_csProgram = csProgram;
	}
	
	void setProgramParent(String csProgramParent)
	{
		m_csProgramParent = csProgramParent;
	}
	
	void setDelaySeconds(int nDelaySeconds)
	{
		m_nDelaySeconds = nDelaySeconds;
	}
	
	private static String getAsyncThreadMBeanId(String csThreadId, String csThreadName)
	{
		return "AsyncThread." + csThreadName + "." + csThreadId;
	}
	
	protected void buildDynamicMBeanInfo()
	{
		addAttribute("ThreadName", getClass(), "A_ThreadName", String.class);
		addAttribute("ThreadId", getClass(), "B_ThreadId", String.class);
		addAttribute("Program", getClass(), "C_Program", String.class);
		addAttribute("ProgramParent", getClass(), "D_ProgramParent", String.class);
		addAttribute("WaitStatus", getClass(), "E_WaitStatus", String.class);
		addAttribute("DelaySecond", getClass(), "F_DelaySeconds", int.class);
	}

	public String getA_ThreadName()
	{
		return m_csThreadName;
	}
	
	public String getB_ThreadId()
	{
		return m_csThreadId;
	}
	
	public String getC_Program()
	{
		return m_csProgram;
	}
	
	public String getD_ProgramParent()
	{
		return m_csProgramParent;
	}
	
	public String getE_WaitStatus()
	{
		String cs;
		if(m_bWaiting)
			cs = "Waiting since " + m_csLastWaitEvent;
		else
			cs = "Running since " + m_csLastWaitEvent;
		long lElapsedTime_s = m_sw.getElapsedTime() / 1000;
		cs = cs + " (" + lElapsedTime_s + " s)";
		return cs;
	}
	
	public int getF_DelaySeconds()
	{
		return m_nDelaySeconds;
	}
}
