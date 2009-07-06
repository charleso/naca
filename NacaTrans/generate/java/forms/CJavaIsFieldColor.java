/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 19 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.forms;

import semantic.expression.CBaseEntityCondition;
import semantic.forms.CEntityIsFieldColor;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaIsFieldColor extends CEntityIsFieldColor
{

	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#GetPriorityLevel()
	 */
	public int GetPriorityLevel()
	{
		return 7;
	}

	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#GetOppositeCondition()
	 */
	public CBaseEntityCondition GetOppositeCondition()
	{
//		CJavaCondNot not = new CJavaCondNot() ;
//		not.SetCondition(this) ;
//		return not;
		CJavaIsFieldColor cond = new CJavaIsFieldColor() ;
		cond.m_bOpposite =  ! m_bOpposite ;
		cond.m_IsColor = m_IsColor ;
		cond.m_Reference = m_Reference ;
		m_Reference.RegisterVarTesting(cond) ;
		return cond ;
	}

	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#ExportTo(semantic.CBaseLanguageExporter)
	 */
	public String Export()
	{
		String cs = "is" ;
		if (m_bOpposite)
		{
			cs += "Not" ;	
		}
		return cs += "FieldColored(" + m_Reference.ExportReference(getLine()) + ", MapFieldAttrColor."+ m_IsColor.m_text + ")";		
	}

}
