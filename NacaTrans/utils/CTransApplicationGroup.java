/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/**
 * 
 */
package utils;

import java.util.Hashtable;
import java.util.Vector;

import jlib.xml.Tag;

public class CTransApplicationGroup
{
	public CTransApplicationGroup(BaseEngine engine)
	{
		m_Engine = engine;
	}
	
	public enum EProgramType
	{
		TYPE_ONLINE,
		TYPE_BATCH,
		TYPE_CALLED,
		TYPE_INCLUDED,
		TYPE_MAP
	};
	
	public String m_csName ;
	public Hashtable<String, Tag> m_tabApplication = new Hashtable<String, Tag>() ;
	public Vector<String> m_arrApplications = new Vector<String>() ;
	public EProgramType m_eType ;
	public String m_csInputPath = "" ;
	public String m_csOutputPath = "" ;
	public String m_csInterPath = "" ;
	
	private BaseEngine m_Engine = null ;
	
	public BaseEngine getEngine()
	{
		return m_Engine ;
	}
}