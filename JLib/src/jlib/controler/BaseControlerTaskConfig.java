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
/**
 * 
 */
package jlib.controler;

import java.util.Hashtable;

import jlib.xml.Tag;


public abstract class BaseControlerTaskConfig
{
	protected BaseControlerTaskConfig(String name)
	{
		m_csName = name ;
	}
	private String m_csName = "" ;
	protected enum EGroupMode 
	{
		MODE_GROUP, MODE_SITE ;
	}
	private EGroupMode m_eMode = EGroupMode.MODE_SITE ;

	public abstract int getNbSteps() ;

	public abstract BaseControlerStepConfig getStep(int j) ;

	public boolean isModeGroup()
	{
		return m_eMode == EGroupMode.MODE_GROUP ;
	}

	void Setup(Tag tagTask)
	{
		m_nDelayBeforeStart = tagTask.getValAsInt("startdelay") ;
		m_nDelayBeforeRestart = tagTask.getValAsInt("restartdelay") ;
		String cs = tagTask.getVal("mode") ;
		if (cs.equals("group"))
		{
			m_eMode = EGroupMode.MODE_GROUP ;
			m_bActive = false ; // default value
		}
		else if (cs.equals("site"))
		{
			m_eMode = EGroupMode.MODE_SITE ;
			m_bActive = true ; // default value
		}
		cs = tagTask.getVal("status") ;
		if (cs.equalsIgnoreCase("active"))
		{
			m_bActive = true ;
		}
		else if (cs.equalsIgnoreCase("inactive"))
		{
			m_bActive = false ;
		}
		
		
		intSetup(tagTask) ;
	}
	protected abstract void intSetup(Tag tagTask);

	private int m_nDelayBeforeStart = 0 ;
	private int m_nDelayBeforeRestart = 0 ;
	private boolean m_bActive = false ;

	public String getName()
	{
		return m_csName ;
	}

	protected int getDelayBeforeStart()
	{
		return m_nDelayBeforeStart ;
	}

	protected int getDelayBeforeRestart()
	{
		return m_nDelayBeforeRestart ;
	}

	public abstract String getLogChannel() ;

	protected abstract BaseControlerStepConfig NewStepConfig(String stepName, int stepIndex) ;

	protected abstract Hashtable<String, BaseControlerStepConfig> getTabConfig() ;

	protected abstract void RemoveStepConfig(BaseControlerStepConfig conf) ;

	public abstract BaseControler NewControler() ;

//	public abstract int FindStepConfig(BaseControlerStepConfig conf) ;
	
	
//	void setCurrentControler(BaseControler ctrl)
//	{
//		m_CurrentControler = ctrl ;
//	}
//	private BaseControler m_CurrentControler = null  ;
//	void OnDeleteConfig()
//	{
//		if (m_CurrentControler != null)
//		{
//			m_CurrentControler.Stop(true) ;
//		}
//		for (int i=0; i<getNbSteps(); i++)
//		{
//			BaseControlerStepConfig step = getStep(i) ;
//			step.OnDeleteConfig() ;
//		}
//	}
}