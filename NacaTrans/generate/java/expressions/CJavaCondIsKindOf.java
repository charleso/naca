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
 * Created on 18 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.expressions;

import semantic.expression.CBaseEntityCondition;
import semantic.expression.CEntityCondIsKindOf;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCondIsKindOf extends CEntityCondIsKindOf
{

	public int GetPriorityLevel()
	{
		return 7;
	}
	public CBaseEntityCondition GetOppositeCondition()
	{
		CJavaCondIsKindOf not = new CJavaCondIsKindOf() ;
		not.m_bIsAlphabetic = m_bIsAlphabetic ;
		not.m_bIsLower = m_bIsLower ;
		not.m_bIsNumeric = m_bIsNumeric ;
		not.m_bIsUpper = m_bIsUpper ;
		not.m_bOpposite = ! m_bOpposite ;
		not.m_Reference = m_Reference ;
		return not;
	}
	public String Export()
	{
		String cs = "is" ;
		if (m_bOpposite)
		{
			cs += "Not" ;
		}
		if (m_bIsNumeric)
		{
			cs += "Numeric(";
		}
		else if (m_bIsAlphabetic)
		{
			cs += "Alphabetic(";
		}
		if (m_Reference != null)
		{
			cs += m_Reference.ExportReference(getLine());
		}
		else
		{
			cs += "[UNDEFINED]" ;
		}
		cs += ")";
		return cs ;
	}
	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#GetSpecialCondition(java.lang.String)
	 */

}
