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
 * Created on Jul 19, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.condition;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.expression.*;
import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.CDataEntity.CDataEntityType;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CEntityCondEquals;
import utils.*;;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CCondCompStatement extends CExpression
{
	public CCondCompStatement(int line, CExpression term1, CExpression term2)
	{
		super(line) ;
		if (term1 == null || term2 == null)
		{
			int n =0; // breakpoint
		}
		m_term1 = term1 ;
		m_term2 = term2 ;
	}
	public CExpression NewCopy(int line, CExpression term1, CExpression term2)
	{
		return new CCondCompStatement(line, term1, term2);
	}
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
		Element e = root.createElement("Compare") ;
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
		return new CCondDifferentStatement(getLine(), m_term1, m_term2) ;
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
		CDataEntity eData1 = m_term1.GetReference(factory);
		CDataEntity eData2 = m_term2.GetReference(factory);
		if (m_term1.IsConstant() && m_term1.GetConstantValue().equals("TRUE"))
		{
			return m_term2.AnalyseCondition(factory);
		}
		if (m_term1.IsConstant() && m_term1.GetConstantValue().equals("FALSE"))
		{
			CBaseEntityCondition cond = m_term2.AnalyseCondition(factory);
			return cond.GetOppositeCondition() ;
		}
		if (m_term2.IsConstant() && m_term2.GetConstantValue().equals("TRUE"))
		{
			return m_term1.AnalyseCondition(factory) ;
		}
		if (m_term2.IsConstant() && m_term2.GetConstantValue().equals("FALSE"))
		{
			CBaseEntityCondition cond = m_term1.AnalyseCondition(factory) ;
			return cond.GetOppositeCondition() ;
		}
		String value = "" ;
		if (eData1 != null && eData1.GetDataType() == CDataEntityType.UNKNWON)
		{ // one expression is unknown
			CBaseEntityExpression op1 = factory.NewEntityExprTerminal(eData1) ;
			if (eData2 != null)
			{
				CBaseEntityExpression op2 = factory.NewEntityExprTerminal(eData2) ;
				CEntityCondEquals eCond = factory.NewEntityCondEquals();
				eCond.SetEqualCondition(op1, op2) ;
				return eCond  ;
			}
			else
			{
				CBaseEntityExpression op2 = m_term2.AnalyseExpression(factory);
				if (op2 == null)
				{
					op2 = factory.NewEntityExprTerminal(factory.NewEntityString(m_term2.GetConstantValue())) ;
					CEntityCondEquals eCond = factory.NewEntityCondEquals();
					eCond.SetEqualCondition(op1, op2) ;
					return eCond  ;
				}
				else
				{
					CEntityCondEquals eCond = factory.NewEntityCondEquals();
					eCond.SetEqualCondition(op1, op2) ;
					return eCond  ;
				}
			}
		}
		else if (m_term1.IsReference() && m_term2.IsConstant())
		{
			value = m_term2.GetConstantValue() ;
			if (eData1 != null)
			{
				CBaseEntityCondition eCond = eData1.GetSpecialCondition(getLine(), value, CBaseEntityCondition.EConditionType.IS_EQUAL, factory) ;
				if (eCond != null)
				{
					eData1.RegisterVarTesting(eCond);
					return eCond;
				}
			}
		}
		else if (m_term2.IsReference() && m_term1.IsConstant())
		{
			value = m_term1.GetConstantValue() ;
			if (eData2 != null)
			{
				CBaseEntityCondition eCond = eData2.GetSpecialCondition(getLine(), value, CBaseEntityCondition.EConditionType.IS_EQUAL, factory) ;
				if (eCond != null)
				{
					eData2.RegisterVarTesting(eCond);
					return eCond;
				}
			}
		} 
		else if (m_term1.IsReference() && m_term2.IsReference())
		{
			if (eData1 != null && eData2 != null)
			{
				CBaseEntityCondition eCond = eData1.GetSpecialCondition(getLine(), eData2, CBaseEntityCondition.EConditionType.IS_EQUAL, factory) ;
				if (eCond != null)
				{
					eData1.RegisterVarTesting(eCond);
					eData2.RegisterValueAccess(eCond);
					return eCond;
				}
			}
		}
		
		CBaseEntityExpression op1 = m_term1.AnalyseExpression(factory);
		if(op1 == null)
		{
			ASSERT(op1, m_term1);
			
		}
		CBaseEntityExpression op2 = m_term2.AnalyseExpression(factory);
		if (op2 == null)
		{ // maybe the op2 is a structure like 'A = (B OR C)'
//			if (op1 != null || masterCond == null)
//			{
//				masterCond = new CDefaultConditionManager(this) ;
//			}
			masterCond.SetMasterCondition(this) ;
			CBaseEntityCondition eCond = m_term2.AnalyseCondition(factory, masterCond);
			ASSERT(eCond, m_term2) ;
			return eCond ;
		}
		CEntityCondEquals eCond = factory.NewEntityCondEquals();
		eCond.SetEqualCondition(op1, op2) ;
		if (op1.GetSingleOperator() != null)
		{
			op1.GetSingleOperator().RegisterVarTesting(eCond);
		}
		if (op2.GetSingleOperator() != null)
		{
			op2.GetSingleOperator().RegisterValueAccess(eCond) ;
		}
		return eCond;
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
		return new CCondCompStatement(getLine(), m_term1, operand) ;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#IsBinaryCondition()
	 */
	public boolean IsBinaryCondition()
	{
		return true ;
	}
	public String toString()
	{
		return "compare(" + m_term1.toString() + ", " + m_term2.toString() + ")" ;
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
