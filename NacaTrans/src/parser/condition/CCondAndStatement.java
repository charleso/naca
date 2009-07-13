/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jul 19, 2004
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
import semantic.expression.CEntityCondAnd;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CCondAndStatement extends CExpression
{
	public CCondAndStatement(int line, CExpression st1, CExpression st2)
	{
		super(line);
		m_st1 = st1 ;
		m_st2 = st2 ;
	}
	protected CExpression m_st1 = null ;
	protected CExpression m_st2 = null ;
	/* (non-Javadoc)
	 * @see parser.condition.CConditionalStatement#Export(org.w3c.dom.Document)
	 */
	
	protected boolean CheckMembersBeforeExport()
	{
		boolean b = CheckMemberNotNull(m_st1);
		b &= CheckMemberNotNull(m_st2);
		return b;
	}
	
	public Element DoExport(Document root)
	{
		Element e = root.createElement("And") ;
		Element e1 = m_st1.Export(root) ;
		if (e1 == null)
		{
			int n = 0 ;
		}
		e.appendChild(e1) ;
		Element e2 = m_st2.Export(root) ;
		if (e2 == null)
		{
			int n = 0 ;
		}
		e.appendChild(e2) ;
		return e;
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
//	public CBaseEntityCondition AnalyseCondition(CBaseEntityFactory factory)
//	{
//		return AnalyseCondition(factory, null);
//	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#AnalyseCondition(semantic.CBaseEntityFactory)
	 */
	public CBaseEntityCondition AnalyseCondition(CBaseEntityFactory factory, CDefaultConditionManager masterCond)
	{
		CBaseEntityCondition op1 = m_st1.AnalyseCondition(factory, masterCond);
		ASSERT(op1, m_st1);
//		if (m_st1.IsBinaryCondition() && !masterCond.isSetted())
//		{
//			masterCond.SetMasterCondition(m_st1);
//		}
		CBaseEntityCondition op2 = m_st2.AnalyseCondition(factory, masterCond);
		ASSERT(op2, m_st2);
		CEntityCondAnd eAnd = factory.NewEntityCondAnd();
		eAnd.SetCondition(op1, op2) ;
		return eAnd ;
	}

	public CExpression GetFirstConditionOperand()
	{
		return m_st1.GetFirstConditionOperand() ;
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
		return "AND(" + m_st1.toString() + ", " + m_st2.toString() + ")" ;
	}
//	public CExpression getMasterBinaryCondition()
//	{
//		CExpression master = m_st2.getMasterBinaryCondition() ;
//		if (master == null)
//		{
//			master = m_st1.getMasterBinaryCondition() ;
//		}
//		return master ;
//	}
	@Override
	public CExpression GetFirstCalculOperand()
	{
		return m_st1.GetFirstCalculOperand() ;
	}
	
}
