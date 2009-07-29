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
package nacaLib.basePrgEnv;

import jlib.jmxMBean.BaseCloseMBean;
import nacaLib.base.JmxGeneralStat;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class TransThreadMBean extends BaseCloseMBean
{
	private BaseEnvironment m_env = null;

	TransThreadMBean(BaseEnvironment env)
	{
		super();
		
		m_env = env;		
		if(JmxGeneralStat.showTransThreadBeans())
		{
			create();
			//TransThreadManager.registerTransBean(this);
		}
	}
	
	void setEnvClosed()
	{
		m_env = null;
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
		if(m_env != null)
		{
			String cs = "00000" + m_env.getEnvId();
			cs = "Trans." + cs.substring(cs.length()-6);
			createMBean(cs, cs);
		}
	}
	
	protected void buildDynamicMBeanInfo()
	{
		addAttribute("User", getClass(), "A_User", String.class);
		addAttribute("LDapUser", getClass(), "A_LDapUser", String.class);
		addAttribute("Terminal", getClass(), "A_Terminal", String.class);
		addAttribute("EnvironmentCreationTime", getClass(), "B_EnvironmentCreationTime", String.class);
		addAttribute("LastTransactionName", getClass(), "C_LastTransactionName", String.class);
		addAttribute("TransactionStatus", getClass(), "C_TransactionStatus", String.class);
		addAttribute("LastTransactionStartTime", getClass(), "D0_LastTransactionStartTime", String.class);
		addAttribute("LastTransactionEndTime", getClass(), "D1_LastTransactionEndTime", String.class);
		addAttribute("LastTransactionExecTime_ms", getClass(), "D2_LastTransactionExecTime_ms", String.class);
		addAttribute("SumTransactionsExecTime_ms", getClass(), "E0_SumTransactionsExecTime_ms", String.class);
		addAttribute("NbTransactionsExecuted", getClass(), "E1_NbTransactionsExecuted", int.class);
		addOperation("StopProcessing", getClass(), "StopProcessing");
	}
	
	public String getA_User()
	{
		if(m_env != null)
			return m_env.getUserId();
		return "";
	}
	
	public String getA_LDapUser()
	{
		if(m_env != null)
			return m_env.getUserLdapId();
		return "";
	}
	
	public String getA_Terminal()
	{
		if(m_env != null)
			return m_env.getTerminalID();
		return "";
	}
	
	public String getB_EnvironmentCreationTime()
	{
		if(m_env != null)
			return m_env.getCreationDateInfo().getDisplayableDateTime();
		return "";
	}
	
	public String getC_LastTransactionName()
	{
		if(m_env != null)
			return m_env.m_csCurrentTransaction;
		return "";
	}
	
	
	public String getC_TransactionStatus()
	{
		if(m_env != null)
		{
			return m_env.getStatusAsString();
		}
		return "Deleted: Must refresh";					
	}
	
	public String getD0_LastTransactionStartTime()
	{
		if(m_env != null)
		{
			return m_env.getStartRunTime().getDisplayableDateTime();
		}
		return "Obsolete entry";
	}
	
	public String getD1_LastTransactionEndTime()
	{
		if(m_env != null)
		{
			if(m_env.isRunning())
			{
				return "Execution in way";
			}
			return m_env.getEndRunTime().getDisplayableDateTime();
		}
		return "";
	}
	
	public String getD2_LastTransactionExecTime_ms()
	{
		if(m_env != null)
		{
			return "" + m_env.getLastTransactionExecTime_ms();
		}
		return "";		
	}
	
	public String getE0_SumTransactionsExecTime_ms()
	{
		if(m_env != null)
		{
			return "" + m_env.getSumTransactionsExecTime_ms();
		}
		return "";
	}
	
	public int getE1_NbTransactionsExecuted()
	{
		if(m_env != null)
		{
			return m_env.getNbTransactionsExecuted();
		}
		return 0; 
	}
	
	public int getLastTransactionExecTime_s()
	{
		if(m_env != null)
		{
			if(!m_env.isRunning())
				return (int)m_env.getStartRunTime().getTimeOffset_ms(m_env.getEndRunTime()) / 1000;
			return (int)m_env.getStartRunTime().getTimeOffsetFromNow_ms() / 1000;
		}
		return 0;		
	}
	
	public void StopProcessing()
	{
		if(m_env != null)
			m_env.requestStopProcessing();
	}
}
