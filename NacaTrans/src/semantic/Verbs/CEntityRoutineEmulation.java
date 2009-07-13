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
	public CEntityRoutineEmulation(String alias, String display)
	{
		m_csAlias = alias ; 
		m_csDisplay = display ;
	}

	protected String m_csDisplay = "" ;
	protected String m_csAlias = "" ;
	/**
	 * @param m_line
	 * @return
	 */
	public CEntityRoutineEmulationCall NewCall(int line, CBaseEntityFactory factory)
	{
		CEntityRoutineEmulationCall call = factory.NewEntityRoutineEmulationCall(line) ;
		call.SetDisplay(m_csDisplay) ;
		return call;
	}
}
