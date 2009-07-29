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
/*
 * Created on 17 janv. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.Verbs;

import semantic.CBaseEntityFactory;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CEntityRoutineEmulation
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityRoutineEmulation(String alias, String display, String csRequiredToolsLib)
	{
		m_csAlias = alias ; 
		m_csDisplay = display ;
		m_csRequiredToolsLib = csRequiredToolsLib;
	}
	
	public CEntityRoutineEmulation(String alias, String display, boolean external)
	{
		m_csAlias = alias ; 
		m_csDisplay = display ;
		m_bExternal = external;
	}

	protected String m_csDisplay = "" ;
	protected String m_csAlias = "" ;
	private String m_csRequiredToolsLib = null; 
	protected boolean m_bExternal = false;
	/**
	 * @param m_line
	 * @return
	 */
	public CEntityRoutineEmulationCall NewCall(int line, CBaseEntityFactory factory)
	{
		CEntityRoutineEmulationCall call = factory.NewEntityRoutineEmulationCall(line) ;
		call.SetDisplay(m_csDisplay) ;
		call.setExternal(m_bExternal);
		return call;
	}
	
	public String getRequiredToolsLib()
	{
		return m_csRequiredToolsLib;
	}
}
