/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package utils;

import org.apache.log4j.Logger;

import semantic.CBaseLanguageEntity;
import utils.DCLGenConverter.DCLGenConverter;
import utils.SQLSyntaxConverter.SQLSyntaxConverter;

import jlib.xml.Tag;
import lexer.CTokenList;

/**
 * @author S. Charton
 * @version $Id$
 */
public abstract class BaseEngine<T_Entity extends CBaseLanguageEntity>
{

	//protected Logger m_logger = Transcoder.ms_logger ;

	/**
	 * @param tagTrans
	 */
	public abstract boolean MainInit(Tag tagTrans) ;

	public abstract void doFileTranscoding(String filename, String csApplication, CTransApplicationGroup grp, boolean bResources);
	public abstract CTokenList doTextTranscoding(String csText, boolean bFromSource);

	protected CRulesManager m_RulesManager = null ;
	protected DCLGenConverter m_DCLGenConverter = null ;
	protected SQLSyntaxConverter m_sqlSyntaxConverter = null;
	
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
	
	public void setDCLGenConverter(DCLGenConverter dclGenConverter)
	{
		m_DCLGenConverter = dclGenConverter;
	}
	
	public void setSQLSyntaxConverter(SQLSyntaxConverter sqlSyntaxConverter)
	{
		m_sqlSyntaxConverter = sqlSyntaxConverter;
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

	public abstract String generateInputFileName(String filename) ;

}
