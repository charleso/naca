/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 18 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.expressions;

import semantic.expression.CBaseEntityCondition;
import semantic.expression.CEntityCondIsAll;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCondIsAll extends CEntityCondIsAll
{

	public CBaseEntityCondition GetOppositeCondition()
	{
		CJavaCondIsAll not = new CJavaCondIsAll();
		not.SetCondition(m_exprData, m_exprToken) ;
		not.m_bIsOpposite = !m_bIsOpposite ;
		return not;
	}
	public String Export()
	{
		String cs = "is" ;
		if (m_bIsOpposite)
		{
			cs += "Not" ;
		}
		cs += "All(" + m_exprData.Export() + ", " + m_exprToken.Export() + ")" ;
		return cs ;
	}
	public int GetPriorityLevel()
	{
		return 7;
	}
	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#GetSpecialCondition(java.lang.String)
	 */

}
