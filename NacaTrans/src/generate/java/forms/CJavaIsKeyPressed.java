/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Oct 18, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.forms;

import semantic.expression.CBaseEntityCondition;
import semantic.forms.CEntityIsKeyPressed;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaIsKeyPressed extends CEntityIsKeyPressed
{
	public int GetPriorityLevel()
	{
		return 7;
	}

	public CBaseEntityCondition GetOppositeCondition()
	{
		CJavaIsKeyPressed is = new CJavaIsKeyPressed();
		is.m_bIsNot = !m_bIsNot ;
		is.m_KeyPressed = m_KeyPressed ;
		m_KeyPressed.RegisterValueAccess(is) ;
		return is ;
	}

	public String Export()
	{
		String cs = "is" ;
		if (m_bIsNot)
		{
			cs += "Not" ;
		}
		cs += "KeyPressed(" + m_KeyPressed.ExportReference(getLine()) + ")" ;
		return cs ;
	}

}
