/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.sql;

import java.util.ArrayList;

import jlib.jmxMBean.BaseCloseMBean;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: DbConnectionBaseJMXBean.java,v 1.1 2008/06/20 08:11:13 u930di Exp $
 */

public class DbConnectionBaseJMXBean extends BaseCloseMBean
{
	private DbConnectionBase m_dbConnectionBase = null;
	private boolean m_bShowStatements = false;
	private DbConnectionBaseStmtJMXBean m_dbConnectionBaseStmtJMXBean = null;
	private ArrayList<DbConnectionBaseStmtJMXBean> m_arrStmts = null;
	
	DbConnectionBaseJMXBean(DbConnectionBase dbConnectionBase)
	{
		 m_dbConnectionBase = dbConnectionBase;
	}
	
	void cleanup()
	{
		m_bShowStatements = false;	// Hide sttm beans
		doSetShowStatments();
		m_dbConnectionBaseStmtJMXBean = null;		
		m_dbConnectionBase = null;
	}
	
	protected void buildDynamicMBeanInfo()
	{
		addAttribute("AreStatementsShown", getClass(), "AreStatementsShown", boolean.class);
		addAttribute("NbCachedStatements", getClass(), "NbCachedStatements", int.class);		
		addOperation("ShowStatments", getClass(), "setShowStatments");
	}
	
	public int getNbCachedStatements()
	{
		if(m_dbConnectionBase != null)
			return m_dbConnectionBase.getNbCachedStatements();
		return 0;
	}
	
	public boolean getAreStatementsShown()
	{
		return m_bShowStatements;
	}
	
	public void setShowStatments()
	{
		m_bShowStatements = !m_bShowStatements;
		doSetShowStatments();
	}
	
	synchronized void doSetShowStatments()
	{
		if(m_bShowStatements)	//&& !isBeanCreated())
		{
			m_dbConnectionBase.createStmtJMXBeans(this, getMBeanName() + "_Stmt", getMBeanName());
		}
		else if(!m_bShowStatements)	// && isBeanCreated())
		{
			if(m_arrStmts != null)
			{
				for(int n=0; n<m_arrStmts.size(); n++)
				{
					DbConnectionBaseStmtJMXBean bean = m_arrStmts.get(n);
					bean.unregisterMBean();
				}
			}
		}
	}	
	
	synchronized void add(DbConnectionBaseStmtJMXBean dbConnectionBaseStmtJMXBean)
	{
		if(m_arrStmts == null)
			m_arrStmts = new ArrayList<DbConnectionBaseStmtJMXBean>(); 
		m_arrStmts.add(dbConnectionBaseStmtJMXBean);
	}
}
