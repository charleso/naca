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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class ControlerDirector
{
	protected class ControlerItemDescription
	{
		public String m_csControlerName = "" ;
		public int m_nStepId = -1 ;
	}

	private Vector<ControlerItemDescription> m_arrDesc = null ;
//	private Vector<BaseControler> m_arrControler = null ;
//	private Vector<ControlerThread> m_arrThreads = null ;
	private BaseControlerConfig m_Config = null;
	private Hashtable<String, BaseControler> m_tabControlers = null ;
	private Hashtable<String, ControlerThread> m_tabThreads = null ;
	
	public void Init(BaseControlerConfig config)
	{
		m_arrDesc = new Vector<ControlerItemDescription>() ;
//		m_arrControler = new Vector<BaseControler>() ;
//		m_arrThreads = new Vector<ControlerThread>() ;
		m_tabControlers = new Hashtable<String, BaseControler>() ;
		m_tabThreads = new Hashtable<String, ControlerThread>(); 

		m_Config = config ;
		m_Config.LoadConfig(this) ;
	}
	
//	public void launchControlers()
//	{
//	   	
//	    //Pour chaque site et pour chaque groupe présent dans le fichier de configuration on crée un thread.
//	    for (int i=0; i<m_Config.getNbTasks(); i++) 
//	    {
//	    	BaseControler ctrl = m_Factory.getControlerForTask(i) ;
//	    	BaseControlerTaskConfig confgrp = m_Config.getTaskConfig(i) ;
//	    	
//	    	
//	    	if (m_Config.isAutoStart())
//	    	{
//	    		ControlerThread th = new ControlerThread(ctrl) ;
//	    		m_arrThreads.add(th) ;
//	    		th.AutoStart() ; 
//	    	}
//	    	else
//	    	{
//		    	m_arrThreads.add(null) ;
//	    	}
//	    }
//
//	}

	private void AddControler(BaseControler ctrl, BaseControlerTaskConfig confgrp)	  
	{
		if (confgrp.isModeGroup())
    	{
	    	ControlerItemDescription descSite = new ControlerItemDescription() ;
	    	descSite.m_csControlerName = confgrp.getName() ;
	    	descSite.m_nStepId = 0 ; // single item
	    	m_arrDesc.add(descSite) ;
    	}
    	else
    	{
	    	for (int j=0; j<confgrp.getNbSteps(); j++)
	    	{
		    	BaseControlerStepConfig conf = confgrp.getStep(j) ;
		    	ControlerItemDescription descSite = new ControlerItemDescription() ;
		    	descSite.m_csControlerName = confgrp.getName() ;
		    	descSite.m_nStepId = j ; // single item
		    	m_arrDesc.add(descSite) ;
	    	}
    	}

    	m_tabControlers.put(confgrp.getName(), ctrl) ;
	}
	
	/**
	 * 
	 */
	public void StopAllControlers()
	{
		Enumeration<ControlerThread> enm = m_tabThreads.elements() ;
		while (enm.hasMoreElements())
		{
			ControlerThread th = enm.nextElement() ;
			if (th != null)
			{
				th.StopControler(false, true) ;
			}
		}
		m_tabThreads.clear() ;
	}


	public void startControler(int i, boolean bForceStart)
	{
		if (i<m_arrDesc.size())
		{
			ControlerItemDescription desc = m_arrDesc.get(i) ;
			BaseControler ctrl = m_tabControlers.get(desc.m_csControlerName) ;
			ControlerThread th = m_tabThreads.get(desc.m_csControlerName) ;
			if ((th == null || !th.isAlive()) && ctrl != null)
			{
				th = new ControlerThread(ctrl) ;
				m_tabThreads.put(desc.m_csControlerName, th) ;
				if (bForceStart)
				{
					th.StartControler(desc.m_nStepId) ;
				}
				else
				{
					th.AutoStart(desc.m_nStepId) ;
				}
			}
		}
	}

	/**
	 * @param i
	 * @param b 
	 */
	public void StopControler(int i, boolean bForce)
	{
		if (i<m_arrDesc.size())
		{
			ControlerItemDescription desc = m_arrDesc.get(i) ;
			BaseControler ctrl = m_tabControlers.get(desc.m_csControlerName) ;
			ControlerThread th = m_tabThreads.get(desc.m_csControlerName) ;
			if (th != null)
			{
				th.StopControler(bForce) ;
			}
			m_tabThreads.remove(desc.m_csControlerName) ;
		}
	}

	/**
	 * @param i
	 * @return
	 */
	public String getStatus(int i)
	{
		if (i<m_arrDesc.size())
		{
			ControlerItemDescription desc = m_arrDesc.get(i) ;
			BaseControler ctrl = m_tabControlers.get(desc.m_csControlerName) ;
			return ctrl.getStatus(desc.m_nStepId) ;
		}
		else
		{
			return "NONE." ;
		}
	}

	public int getNbControlers()
	{
		return m_arrDesc.size() ;
	}

	public void ReloadConfig()
	{
		m_Config.LoadConfig(this) ;
	}

	public String getStepName(int i)
	{
		if (i<m_arrDesc.size())
		{
			ControlerItemDescription desc = m_arrDesc.get(i) ;
			BaseControler ctrl = m_tabControlers.get(desc.m_csControlerName) ;
			String name = ctrl.getStepName(desc.m_nStepId) ;
			return name ;
		}
		else
		{
			return "(none)" ;
		}
	}

	public BaseControler getControler(int i)
	{
		if (i<m_arrDesc.size())
		{
			ControlerItemDescription desc = m_arrDesc.get(i) ;
			BaseControler ctrl = m_tabControlers.get(desc.m_csControlerName) ;
			return ctrl ;
		}
		return null ;
	}

	public ControlerThread getThread(int i)
	{
		if (i<m_arrDesc.size())
		{
			ControlerItemDescription desc = m_arrDesc.get(i) ;
			return m_tabThreads.get(desc.m_csControlerName) ;
		}
		return null ;
	}

	public void AddNewTask(BaseControlerTaskConfig grpConfig)
	{
		BaseControler ctrl = grpConfig.NewControler() ;
		AddControler(ctrl, grpConfig) ;
    	if (m_Config.isAutoStart())
    	{
    		ControlerThread th = new ControlerThread(ctrl) ;
    		m_tabThreads.put(grpConfig.getName(), th) ;
    		th.AutoStart() ; 
    	}
	}

	public void RemoveTask(BaseControlerTaskConfig conf)
	{
		String name = conf.getName();
		if (m_tabControlers.containsKey(name))
		{
			for (int i=0; i<m_arrDesc.size(); )
			{
				ControlerItemDescription desc = m_arrDesc.get(i) ;
				if (desc.m_csControlerName.equals(name))
				{
					m_arrDesc.remove(i) ;
				}
				else
				{
					i++ ;
				}
			}
			ControlerThread th = m_tabThreads.remove(name) ;
			if (th != null)
			{
				th.StopControler(true) ;
				try {
				th.join() ;
				} catch (InterruptedException e) {} 
			}
			m_tabControlers.remove(name) ;
		}
		
	}

	public void RemoveStepFromTask(BaseControlerTaskConfig cfg)
	{
		if (!cfg.isModeGroup())
		{
			String name = cfg.getName() ;
			if (m_tabControlers.containsKey(name))
			{
				int iStep = cfg.getNbSteps() ;
				for (int i=0; i<m_arrDesc.size(); )
				{
					ControlerItemDescription desc = m_arrDesc.get(i) ;
					if (desc.m_csControlerName.equals(name) && desc.m_nStepId == iStep)
					{
						m_arrDesc.remove(i) ;
					}
					else
					{
						i++ ;
					}
				}
			}
		}
	}

	public void AddStepToTask(BaseControlerTaskConfig cfg)
	{
		if (!cfg.isModeGroup())
		{
			String name = cfg.getName() ;
			BaseControler ctrl = m_tabControlers.get(name) ;
			if (ctrl != null)
			{
				int iStep = cfg.getNbSteps()-1 ;
				ControlerItemDescription desc = new ControlerItemDescription() ;
				desc.m_csControlerName = name ;
				desc.m_nStepId = iStep ;

				for (int i=0; i<m_arrDesc.size(); i++)
				{
					ControlerItemDescription d = m_arrDesc.get(i) ;
					if (d.m_csControlerName.equals(name) && d.m_nStepId == iStep-1)
					{
						m_arrDesc.insertElementAt(desc, i+1) ;
					}
				}

			}
		}
	}
}
