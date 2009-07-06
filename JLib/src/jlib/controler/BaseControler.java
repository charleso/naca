/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.controler;

import java.util.Date;
import java.util.Vector;

public abstract class BaseControler
{
	public BaseControler(int nbSteps)
	{
		m_arrStatus = new Vector<String>(nbSteps) ;
		m_arrDtStarts = new Vector<Date>(nbSteps)  ;
		m_arrDtEnds = new Vector<Date>(nbSteps)  ;
		for (int i=0; i<nbSteps; i++)
		{
			m_arrStatus.add("NONE") ;
			m_arrDtEnds.add(null) ;
			m_arrDtStarts.add(null) ;
		}
	}
	private Vector<String> m_arrStatus; 
	private Vector<Date> m_arrDtStarts ;
	private Vector<Date> m_arrDtEnds ;
	
	private boolean m_bIsRunning = false ;
	private int m_nCurrentStep = 0 ;
	
	public String getStatus(int stepId)
	{
		if (stepId >= m_arrStatus.size())
		{
			return "NONE" ;
		}
		String status = m_arrStatus.get(stepId) ;
		if (getTaskConfig().isModeGroup() || stepId == m_nCurrentStep)
		{
			if (status.startsWith("NONE") || status.startsWith("ERROR") || status.startsWith("STARTING"))
			{
				return status ;
			}
			else
			{
				return status + " ; " + getCurrentInternalStatus() ;
			}
		}
		else
		{
			return status ;
		}
	};

	protected abstract String getCurrentInternalStatus() ;
	
	public abstract BaseControlerTaskConfig getTaskConfig() ;

	public void setStatus(int currentSite, String string)
	{
		m_arrStatus.set(currentSite, string) ;
	}
	public void setStartDate(int currentSite, Date dt)
	{
		m_arrDtStarts.set(currentSite, dt) ;
	}

	public boolean RunStep(int currentSite)
	{
		m_bIsRunning = true ;
		m_nCurrentStep = currentSite ;
		BaseControlerTaskConfig conf = getTaskConfig() ;
		BaseControlerStepConfig step = conf.getStep(m_nCurrentStep) ;
//		step.setCurrentControler(this) ;
		
		boolean b = DoOneStep(currentSite) ;
		
		m_bIsRunning = false ;
//		step.setCurrentControler(null) ;
		return b ;
	}
	
	protected abstract boolean DoOneStep(int currentSite) ;

	public abstract void Stop(boolean force) ;

	public Date getDateGroupEnds()
	{
		return m_dtGroupEnds;
	}
	public void setDateGroupEnds()
	{
		m_dtGroupEnds = new Date() ;
	}
	private Date m_dtGroupEnds = null ;

	public Date getDateStepEnds(int currentSite)
	{
		return m_arrDtEnds.get(currentSite) ;
	}

	public String getStepName(int stepId)
	{
		return getTaskConfig().getStep(stepId).getName() ;
	}
	
	protected boolean isRunning()
	{
		return m_bIsRunning ;
	}
	protected int getCurrentStep()
	{
		return m_nCurrentStep ;
	}

}
