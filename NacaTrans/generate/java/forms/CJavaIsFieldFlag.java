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
 * Created on 19 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.forms;

import semantic.expression.CBaseEntityCondition;
import semantic.forms.CEntityIsFieldFlag;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaIsFieldFlag extends CEntityIsFieldFlag
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
		CJavaIsFieldFlag not = new CJavaIsFieldFlag() ;
		not.m_Reference = m_Reference ;
		not.m_Value = m_Value ;
		not.m_bOpposite = !m_bOpposite ;
		not.m_bIsSet = m_bIsSet ;
		m_Reference.RegisterVarTesting(not) ;
		return not;  
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
		if (m_bIsSet)
		{
			cs += "FieldFlagSet(" + m_Reference.ExportReference(getLine()) + ")" ;
		}
		else
		{
			cs += "FieldFlag(" + m_Reference.ExportReference(getLine()) + ", \"" + m_Value + "\")" ;
		}
		return cs ;
	}

}
