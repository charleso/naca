/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package utils;

import org.apache.log4j.Logger;

import semantic.CBaseLanguageEntity;

import jlib.xml.Tag;

/**
 * @author S. Charton
 * @version $Id: BaseEngine.java,v 1.4 2007/12/06 07:24:07 u930bm Exp $
 */
public abstract class BaseEngine<T_Entity extends CBaseLanguageEntity>
{

	//protected Logger m_logger = Transcoder.ms_logger ;

	/**
	 * @param tagTrans
	 */
	public abstract boolean MainInit(Tag tagTrans) ;

	public abstract void doFileTranscoding(String filename, String csApplication, CTransApplicationGroup grp, boolean bResources);

	protected CRulesManager m_RulesManager = null ;
	
	protected Transcoder m_Transcoder = null ;


	/**
	 * @return Returns the ruleManager.
	 */
	public CRulesManager getRulesManager()
	{
		return m_RulesManager;
	}

	/**
	 * @param ruleManager The ruleManager to set.
	 */
	public void setRulesManager(CRulesManager ruleManager)
	{
		m_RulesManager = ruleManager;
	}

	/**
	 * @return Returns the transcoder.
	 */
	public Transcoder getTranscoder()
	{
		return m_Transcoder;
	}

	/**
	 * @param transcoder The transcoder to set.
	 */
	public void setTranscoder(Transcoder transcoder)
	{
		m_Transcoder = transcoder;
	}

	/**
	 * @param name
	 * @param grp 
	 * @return
	 */
	public abstract T_Entity doAllAnalysis(String filename, String csApplication, CTransApplicationGroup grp, boolean bResources);

	public abstract CGlobalCatalog getGlobalCatalog();



}
