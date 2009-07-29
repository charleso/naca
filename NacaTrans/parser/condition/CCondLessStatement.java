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
 * Created on Jul 27, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.condition;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.expression.*;
import semantic.CBaseEntityFactory;
import semantic.CDataEntity;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CEntityCondCompare;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CCondLessStatement extends CExpression
{
	public CCondLessStatement(int line, CExpression term1,CExpression term2)
	{
		super(line) ;
		m_term1 = term1 ;
		m_term2 = term2 ;
	}
	public CCondLessStatement(int line, CExpression term1,CExpression term2, boolean bOrEquals)
	{
		super(line) ;
		m_term1 = term1 ;
		m_term2 = term2 ;
		m_bOrEquals = bOrEquals ;
	}
	protected boolean m_bOrEquals = false ; 
	protected CExpression m_term1 = null ;
	protected CExpression m_term2 = null ;
	
	protected boolean CheckMembersBeforeExport()
	{
		boolean b = CheckMemberNotNull(m_term1);
		b &= CheckMemberNotNull(m_term2);
		return b;
	}

	/* (non-Javadoc)
	 * @see parser.condition.CConditionalStatement#Export(org.w3c.dom.Document)
	 */
	public Element DoExport(Document root)
	{
		Element e ;
		if (m_bOrEquals)
		{
			e = root.createElement("LessThanOrEqual") ;
		}
		else
		{
			e = root.createElement("LessThan") ;
		}
		Element e1 = m_term1.Export(root) ;
		if (e1 == null)
		{
			int n = 0 ;
		}
		e.appendChild(e1) ;
		Element e2 = m_term2.Export(root) ;
		if (e2 == null)
		{
			int n = 0 ;
		}
		e.appendChild(e2) ;
		return e;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#WriteTo(parser.expression.CBaseExpressionExporter)
	 */
	public boolean IsOrEquals()
	{
		return m_bOrEquals ;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#GetPriorityLEvel()
	 */
	public int GetPriorityLevel()
	{
		return 3;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#GetOppositeCondition()
	 */
	public CExpression GetOppositeCondition()
	{
		return new CCondGreaterStatement(getLine(), m_term1, m_term2, !m_bOrEquals) ;
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
	public CBaseEntityCondition AnalyseCondition(CBaseEntityFactory factory, CDefaultConditionManager masterCond)
	{
		masterCond.SetMasterCondition(this) ;
		String value = m_term2.GetConstantValue() ;
		if (!value.equals("") && m_term1.IsReference())
		{
			CDataEntity ref = m_term1.GetReference(factory);
			if (ref == null)
			{
				return null ;
			}
			CBaseEntityCondition.EConditionType type = CBaseEntityCondition.EConditionType.IS_LESS_THAN ;
			if (m_bOrEquals)
			{
				type = CBaseEntityCondition.EConditionType.IS_LESS_THAN_OR_EQUAL ;
			}
			CBaseEntityCondition eCond = ref.GetSpecialCondition(getLine(), value, type, factory) ;
			if (eCond != null)
			{
				return eCond ;
			}
		}
		
		value = m_term1.GetConstantValue() ;
		if (!value.equals("") && m_term2.IsReference())
		{
			CDataEntity ref = m_term2.GetReference(factory);
			CBaseEntityCondition.EConditionType type = CBaseEntityCondition.EConditionType.IS_GREATER_THAN_OR_EQUAL ;
			if (m_bOrEquals)
			{
				type = CBaseEntityCondition.EConditionType.IS_GREATER_THAN ;
			}
			CBaseEntityCondition eCond = ref.GetSpecialCondition(getLine(), value, type, factory) ;
			if (eCond != null)
			{
				return eCond ;
			}
		}
		
		CBaseEntityExpression op1 = m_term1.AnalyseExpression(factory);
		CBaseEntityExpression op2 = m_term2.AnalyseExpression(factory);
		if (op2 == null)
		{ // maybe the op2 is a structure like 'A < (B OR C)'
//			if (op1 != null || masterCond == null)
//			{
//				masterCond = new CDefaultConditionManager(this) ;
//			}
			masterCond.SetMasterCondition(this) ;
			CBaseEntityCondition eCond = m_term2.AnalyseCondition(factory, masterCond);
			ASSERT(eCond, m_term2) ;
			return eCond ;
		}
		CEntityCondCompare eCond = factory.NewEntityCondCompare() ;
		if (m_bOrEquals)
		{
			eCond.SetLessOrEqualThan(op1, op2) ;
		}
		else
		{
			eCond.SetLessThan(op1, op2);
		}
		if (op1.GetSingleOperator() != null)
		{
			op1.GetSingleOperator().RegisterVarTesting(eCond) ;
		}
		if (op2.GetSingleOperator() != null)
		{
			op2.GetSingleOperator().RegisterValueAccess(eCond) ;
		}
		return eCond;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#GetSimilarExpression(parser.expression.CExpression)
	 */
	public CExpression GetSimilarExpression(CExpression operand)
	{
		CCondLessStatement lt = new CCondLessStatement(getLine(), m_term1, operand) ;
		lt.m_bOrEquals = m_bOrEquals ;		
		return lt;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#IsBinaryCondition()
	 */
	public boolean IsBinaryCondition()
	{
		return true;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#NewCopy(parser.expression.CExpression, parser.expression.CExpression)
	 */
//	public CExpression NewCopy(CExpression term1, CExpression term2)
//	{
//		return new CCondLessStatement(term1, term2);
//	}
//	/* (non-Javadoc)
//	 * @see parser.expression.CExpression#GetFirstOperand()
//	 */
	public CExpression GetFirstConditionOperand()
	{
		return m_term1;
	}
	public String toString()
	{
		if (m_bOrEquals)
		{
			return "LESS_OR_EQUAL(" + m_term1.toString() + ", " + m_term2.toString() + ")" ;
		}
		else
		{
			return "LESS(" + m_term1.toString() + ", " + m_term2.toString() + ")" ;
		}
	}
	public CExpression getMasterBinaryCondition()
	{
		return this ;
	}
	@Override
	public CExpression GetFirstCalculOperand()
	{
		return m_term1.GetFirstCalculOperand() ;
	}
}
