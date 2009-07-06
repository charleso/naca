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

import jlib.xml.Tag;

public abstract class BaseControlerStepConfig
{

	public BaseControlerStepConfig(String csStepName)
	{
		m_csName = csStepName ;
	}
	
	private String m_csName = "" ;
	private boolean m_bActive = false ;
	private int m_nDelayBeforeStart = 0 ;
	private int m_nDelayBeforeRestart = 0 ;

	public String getName()
	{
		return m_csName ;
	}

	public boolean isActive()
	{
		return m_bActive ;
	} 

	protected int getDelayBeforeStart()
	{
		return m_nDelayBeforeStart;
	}

	protected int getDelayBeforeRestart()
	{
		return m_nDelayBeforeRestart ;
	}

	void Setup(Tag tagSite)
	{
		m_nDelayBeforeStart = tagSite.getValAsInt("startdelay") ;
		m_nDelayBeforeRestart = tagSite.getValAsInt("restartdelay") ;
		String cs = tagSite.getVal("status") ;
		if (cs.equalsIgnoreCase("active"))
		{
			m_bActive = true ;
		}
		else
		{
			m_bActive = false ;
		}
		
		intSetup(tagSite) ;
	}

	protected abstract void intSetup(Tag tagSite) ;
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
//	}
}