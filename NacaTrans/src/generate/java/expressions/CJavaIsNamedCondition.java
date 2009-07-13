/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Aug 26, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.expressions;

import semantic.CBaseEntityFactory;
import semantic.CDataEntity;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CEntityIsNamedCondition;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaIsNamedCondition extends CEntityIsNamedCondition
{
	public int GetPriorityLevel()
	{
		return 7;
	}
	public CBaseEntityCondition GetOppositeCondition()
	{
		CJavaIsNamedCondition not = new CJavaIsNamedCondition();
		not.m_bOpposite = !m_bOpposite ;
		not.m_Reference = m_Reference ;
		return not ;
	}
	public String Export()
	{
		String cs ;
		if (m_bOpposite)
		{
			cs = "isNot(" ;
		}
		else
		{
			cs = "is(" ;
		}
			
		return cs + m_Reference.ExportReference(getLine()) + ")" ;
	}
	public CBaseEntityCondition GetSpecialConditionReplacing(String val, CBaseEntityFactory fact, CDataEntity replace)
	{
		return null ;
	}
}
