/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 26 juil. 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author u930di
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.varEx;


public class CLevel
{
	CLevel(VarDefBuffer varDef, int nLevel)
	{
		m_nLevel = nLevel;
		m_varDef = varDef;
	};
	
	void setWith(CLevel levelSource)
	{
		m_nLevel = levelSource.m_nLevel;
		m_varDef = levelSource.m_varDef;
	}
	
	VarDefBuffer getVarDef()
	{
		return m_varDef;
	}
	
	boolean hasLowerLevel(int nLevel)
	{
		if(m_nLevel < nLevel)
			return true;
		return false;
	}

	int m_nLevel = 0;
	private VarDefBuffer m_varDef = null ;
}



