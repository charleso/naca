/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 14 févr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package generate.java.forms;

import semantic.expression.CBaseEntityCondition;
import semantic.forms.CEntityIsFieldCursor;

/**
 * @author sly
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CJavaIsFieldCursor extends CEntityIsFieldCursor
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
		CJavaIsFieldCursor cur = new CJavaIsFieldCursor() ;
		cur.m_bHasCursor = ! m_bHasCursor ;
		cur.m_Reference = m_Reference ;
		m_Reference.RegisterVarTesting(cur) ;
		return cur;
	}

	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#Export()
	 */
	public String Export()
	{
		String cs = "is";
		if (!m_bHasCursor)
		{
			cs += "Not";
		}
		cs += "FieldHasCursor("+m_Reference.ExportReference(getLine())+")";
		return cs;
	}

}
