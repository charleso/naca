/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.controler;


import java.util.Date;

import jlib.log.stdEvents.StdInfo;




public class ControlerThread extends Thread
{
	private BaseControler m_Controler = null ;
	private BaseControlerTaskConfig m_grpConfig =  null ;
	private int m_nCurrentSite = -1 ;
	private boolean m_bDoAllSites = false ;
	private String m_csControlerName = "" ;
	private boolean m_bForceStarting = false ;
	private boolean m_bStopASAP = false ; 	   //Un flag qui permet d'arrêter le crawl en urgence.
	
	public ControlerThread(BaseControler ctrl)
	{
		m_Controler = ctrl ;
		m_grpConfig = ctrl.getTaskConfig() ;
		m_csControlerName = m_grpConfig.getName() ;
	}

	public void AutoStart(int nStepId)
	{
		m_bForceStarting = false ;
		DoStart(nStepId) ;
	}

	public void StopControler(boolean bForce)
	{
		if (m_nCurrentSite>=0)
		{
			m_Controler.setStatus(m_nCurrentSite, "STOPPING...") ;
		}
		m_Controler.Stop(bForce) ;
		State st = this.getState() ;
		if (st == State.TIMED_WAITING || st == State.WAITING)
		{
			this.interrupt() ;
		}
		try
		{
			this.join() ;
		}
		catch (InterruptedException e)
		{
		}
		if (m_nCurrentSite>=0)
		{
			m_Controler.setStatus(m_nCurrentSite, "NONE : Interrupted") ;
		}
	}

	private void DoStart(int nStepId)
	{
		if (m_grpConfig.isModeGroup() || nStepId == -1 || (m_grpConfig.getNbSteps()==1 && nStepId==0))
		{
			m_nCurrentSite = 0 ;
			m_bDoAllSites = true ;
		}
		else
		{
			m_nCurrentSite = nStepId ;
			m_bDoAllSites = false ;
		}
		start() ;
	}
	public void StartControler(int nStepId)
	{
		m_bForceStarting = true ;
		DoStart(nStepId) ;
	}

	//	..............................................................................................................
	/*
	 * Méthode principale du thread.
	 * Cette méthode vérifie l'état dans lequel se trouve le crawling et agit en conséquence.
	 */
	public void run() 
	{
		if (m_nCurrentSite<0 && m_nCurrentSite >= m_grpConfig.getNbSteps())
		{
			return ;
		}
//		m_grpConfig.setCurrentControler(m_Controler) ;
		doRun() ;
//		m_grpConfig.setCurrentControler(null) ;
		
	}
	private void doRun()
	{
		int nSite = m_nCurrentSite ;

		boolean bAlreadyRun = false ;
		boolean bContinue = true ;
		while(bContinue)
		{
			if ((!m_bForceStarting || bAlreadyRun) && m_bDoAllSites)
			{
				Date dtGrpEnds = m_Controler.getDateGroupEnds() ;
				
				if (dtGrpEnds == null)
				{
					if (m_grpConfig.getDelayBeforeStart() > 0)
					{
						StdInfo.log(m_grpConfig.getLogChannel(), m_grpConfig.getName(), "Waiting to start") ; 
						m_Controler.setStatus(m_nCurrentSite, "NONE : Waiting to start") ;
						try
						{
							Thread.sleep(m_grpConfig.getDelayBeforeStart() * 1000) ;
						}
						catch (InterruptedException e1)
						{
							StdInfo.log(m_grpConfig.getLogChannel(), m_grpConfig.getName(), "Interrupted. Getting out") ; 
							m_Controler.setStatus(m_nCurrentSite, "NONE : Interrupted") ;
							return ;
						}
					}
				}
				else
				{
					Date now = new Date() ;
					long msec = now.getTime() - dtGrpEnds.getTime() ;
					if (m_grpConfig.getDelayBeforeRestart()*1000 > msec)
					{
						StdInfo.log(m_grpConfig.getLogChannel(), m_grpConfig.getName(), "Waiting to restart") ; 
						m_Controler.setStatus(m_nCurrentSite, "NONE : Waiting to restart") ;
						try
						{
							Thread.sleep(m_grpConfig.getDelayBeforeRestart()*1000 - msec) ;
						}
						catch (InterruptedException e1)
						{
							StdInfo.log(m_grpConfig.getLogChannel(), m_grpConfig.getName(), "Interrupted. Getting out") ; 
							m_Controler.setStatus(m_nCurrentSite, "NONE : Interrupted") ;
							return ;
						}
					}
				}
			}

			bContinue &= !m_bStopASAP ;
			while (bContinue && nSite < m_grpConfig.getNbSteps())
			{
				m_nCurrentSite = nSite ;
				BaseControlerStepConfig stepConfig = m_grpConfig.getStep(m_nCurrentSite);
				String context = m_csControlerName ;
				if (!context.equals(""))
					context += "/" ;
				context += stepConfig.getName() ;
				if (!stepConfig.isActive())
				{
					m_Controler.setStatus(m_nCurrentSite, "NONE : Inactive") ;
					StdInfo.log(m_grpConfig.getLogChannel(), context, "Site is INACTIVE") ; 
					bContinue = m_bDoAllSites ;
					nSite ++ ;
					continue ;
				}
	
				//if (!m_bForceStarting)
				//{
					Date dtStepEnds = m_Controler.getDateStepEnds(m_nCurrentSite) ;
					
					if (dtStepEnds == null)
					{
						if (stepConfig.getDelayBeforeStart() < 0)
						{
							m_Controler.setStatus(m_nCurrentSite, "NONE : Not started ") ;
							StdInfo.log(m_grpConfig.getLogChannel(), context, "Site is not Autostart") ; 
							bContinue = m_bDoAllSites ;
							nSite ++ ;
							continue ;
						}
						else if (stepConfig.getDelayBeforeStart() > 0)
						{
							StdInfo.log(m_grpConfig.getLogChannel(), context, "Waiting to start") ; 
							m_Controler.setStatus(m_nCurrentSite, "NONE : Waiting to start") ;
							try
							{
								Thread.sleep(stepConfig.getDelayBeforeStart() * 1000) ;
							}
							catch (InterruptedException e1)
							{
								StdInfo.log(m_grpConfig.getLogChannel(), context, "Interrupted. Getting out") ; 
								m_Controler.setStatus(m_nCurrentSite, "NONE : Interrupted") ;
								return ;
							}
						}
					}
					else
					{
						long msec = (new Date()).getTime() - dtStepEnds.getTime() ;
						
						if (stepConfig.getDelayBeforeRestart() < 0)
						{
							m_Controler.setStatus(m_nCurrentSite, "NONE : Not started ") ;
							StdInfo.log(m_grpConfig.getLogChannel(), context, "Site is not Autostart") ; 
							bContinue = m_bDoAllSites ;
							nSite ++ ;
							continue ;
						}
						else if (stepConfig.getDelayBeforeRestart()*1000 > msec)
						{
							StdInfo.log(m_grpConfig.getLogChannel(), context, "Waiting to start") ; 
							m_Controler.setStatus(m_nCurrentSite, "NONE : Waiting to start") ;
							
							
							try
							{
								Thread.sleep(stepConfig.getDelayBeforeRestart() * 1000 - msec) ;
							}
							catch (InterruptedException e1)
							{
								StdInfo.log(m_grpConfig.getLogChannel(), context, "Interrupted. Getting out") ; 
								m_Controler.setStatus(m_nCurrentSite, "NONE : Interrupted") ;
								return ;
							}
						}
					}
				//}
			
				boolean bRet = m_Controler.RunStep(m_nCurrentSite) ;

				if (!bRet) 
				{
					bContinue = false ;
				}
				else if (m_bStopASAP)
				{
					bContinue = false ;
				}
				else
				{
					bContinue = m_bDoAllSites ;
					nSite ++ ;
				}
				
				// Set start date for next site
				/*if (m_nCurrentSite < m_grpConfig.getNbSteps())
				{
					Date nextDate = new Date(new Date().getTime() + m_grpConfig.getStep(m_nCurrentSite + 1).getDelayBeforeRestart() * 1000);
					m_Controler.setStartDate(m_nCurrentSite + 1, nextDate);
				}*/
				
			}
			bAlreadyRun = true ;
			nSite = 0 ;
			m_Controler.setDateGroupEnds() ;
		}
	}

	public void StopControler(boolean bRestart, boolean bForce)
	{
		this.isDaemon() ;
		m_bStopASAP = !bRestart ;
		StopControler(bForce) ;
	}

	public void AutoStart()
	{
		AutoStart(-1) ;
	}

	public int getCurrentStep()
	{
		return m_nCurrentSite ;
	}	
}
