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
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CEntityCondCompare;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCondCompare extends CEntityCondCompare
{
	public int GetPriorityLevel()
	{
		return 7;
	}
	public CBaseEntityCondition GetOppositeCondition()
	{
		CJavaCondCompare newCond = new CJavaCondCompare();
		newCond.m_bIsGreater = !m_bIsGreater ;
		newCond.m_bIsOrEquals = !m_bIsOrEquals ;
		newCond.m_op1 = m_op1 ;
		newCond.m_op2 = m_op2 ;
		return newCond;
	}
	public String Export()
	{
		String cs = "" ;
		String ebcdic = "" ;
//		if (m_op1.getExpressionType() != CBaseEntityExpression.CEntityExpressionType.MATH 
//			&& m_op1.getExpressionType() != CBaseEntityExpression.CEntityExpressionType.NUMERIC
//			&& m_op2.getExpressionType() != CBaseEntityExpression.CEntityExpressionType.MATH 
//			&& m_op2.getExpressionType() != CBaseEntityExpression.CEntityExpressionType.NUMERIC)
//		{
//			ebcdic = "InEbcdic" ;
//		}
//		else
//		{
//			ebcdic = "" ;
//		}
		if (m_bIsGreater && m_bIsOrEquals)
		{
			cs = "isGreaterOrEqual"+ebcdic+"(" ;
		}
		else if (m_bIsGreater && !m_bIsOrEquals)
		{
			cs = "isGreater"+ebcdic+"(" ;
		}
		else if (!m_bIsGreater && m_bIsOrEquals)
		{
			cs = "isLessOrEqual"+ebcdic+"("; 
		}
		else if (!m_bIsGreater && !m_bIsOrEquals)
		{
			cs = "isLess"+ebcdic+"(" ;
		}
		cs += m_op1.Export() + ", " + m_op2.Export() + ")";
		return cs ;
	}

}
