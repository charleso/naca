/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 12 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.condition;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

//import parser.expression.CBaseExpressionExporter;
import parser.expression.CDefaultConditionManager;
import parser.expression.CExpression;
import semantic.CBaseEntityFactory;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CEntityCondIsAll;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CCondIsAll extends CExpression
{
	protected CExpression m_term1 = null ;
	protected CExpression m_term2 = null ;
	
	public CCondIsAll(int line, CExpression term1, CExpression term2)
	{
		super(line) ;
		m_term1 = term1 ;
		m_term2 = term2 ;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#WriteTo(parser.expression.CBaseExpressionExporter)
	 */

	/* (non-Javadoc)
	 * @see parser.expression.CExpression#GetPriorityLevel()
	 */
	public int GetPriorityLevel()
	{
		return 7;
	}
	
	protected boolean CheckMembersBeforeExport()
	{
		boolean b = CheckMemberNotNull(m_term1);
		b &= CheckMemberNotNull(m_term2);
		return b;
	}

	/* (non-Javadoc)
	 * @see parser.expression.CExpression#DoExport(org.w3c.dom.Document)
	 */
	public Element DoExport(Document root)
	{
		Element e = root.createElement("IsAll") ;
		e.appendChild(m_term1.Export(root)) ;
		e.appendChild(m_term2.Export(root)) ;
		return e;
	}

	/* (non-Javadoc)
	 * @see parser.expression.CExpression#GetOppositeCondition()
	 */
	public CExpression GetOppositeCondition()
	{
		return new CCondNotStatement(getLine(), this) ;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#AnalyseExpression(semantic.CBaseEntityFactory)
	 */
	public CBaseEntityExpression AnalyseExpression(CBaseEntityFactory factory)
	{
		return null;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#AnalyseCondition(semantic.CBaseEntityFactory)
	 */
	public CBaseEntityCondition AnalyseCondition(CBaseEntityFactory factory, CDefaultConditionManager condMaster)
	{
		CBaseEntityExpression op1 = m_term1.AnalyseExpression(factory);
		CBaseEntityExpression op2 = m_term2.AnalyseExpression(factory);
		CEntityCondIsAll eIsAll = factory.NewEntityCondIsAll();
		eIsAll.SetCondition(op1, op2) ;
		if (m_bIsOpposite)
		{
			eIsAll.setOpposite() ;
		}
		return eIsAll;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#GetFirstOperand()
	 */
	public CExpression GetFirstConditionOperand()
	{
		return m_term1 ;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#GetSimilarExpression(parser.expression.CExpression)
	 */
	public CExpression GetSimilarExpression(CExpression operand)
	{
		ASSERT(null, null);
		return null;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#IsBinaryCondition()
	 */
	public boolean IsBinaryCondition()
	{
		return false;
	}
	public String toString()
	{
		if (m_bIsOpposite)
		{
			return "IS_NOT_ALL(" + m_term1.toString() + ", " + m_term2.toString() + ")" ;
		}
		else
		{
			return "IS_ALL(" + m_term1.toString() + ", " + m_term2.toString() + ")" ;
		}
	}
	public CExpression getMasterBinaryCondition()
	{
		return this ;
	}
	/**
	 * 
	 */
	public void setOpposite()
	{
		m_bIsOpposite = !m_bIsOpposite ;
	}
	protected boolean m_bIsOpposite = false ;

	@Override
	public CExpression GetFirstCalculOperand()
	{
		return m_term1.GetFirstCalculOperand() ;
	}

}
