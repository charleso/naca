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
import semantic.CBaseEntityFactory;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CEntityCondNot;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CCondNotStatement extends CExpression
{
	public CCondNotStatement(int line, CExpression cond)
	{
		super(line) ;
		if (cond == null)
		{
			int n=0; // breakpoint 
		}
		m_cond = cond ;
	} 
//	public CExpression NewCopy(CExpression term1, CExpression term2)
//	{
//		if (m_cond == null)
//		{
//			return null ;
//		} 
//		return new CCondNotStatement(m_cond.NewCopy(term1, term2));
//	}
	protected CExpression m_cond = null ;
	
	protected boolean CheckMembersBeforeExport()
	{
		return true;
	}
	
	/* (non-Javadoc)
	 * @see parser.condition.CConditionalStatement#Export(org.w3c.dom.Document)
	 */
	public Element DoExport(Document root)
	{
		Element e = root.createElement("Not") ;
		if (m_cond != null)
		{
			e.appendChild(m_cond.Export(root)) ;
		}
		return e;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#GetOppositeCondition()
	 */
	public CExpression GetOppositeCondition()
	{
		return m_cond;
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
		CBaseEntityCondition eCond = m_cond.AnalyseCondition(factory, condMaster);
		if ((m_cond.IsConstant() || m_cond.IsReference()) && eCond.isBinaryCondition())
		{
			Transcoder.logWarn(m_cond.getLine(), "be carrefull to Abbreviated combined relation condition") ;  
		}
		CEntityCondNot eNot = factory.NewEntityCondNot();
		eNot.SetCondition(eCond) ;  
		return eNot ;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#GetFirstOperand()
	 */
	public CExpression GetFirstConditionOperand()
	{
		return m_cond.GetFirstConditionOperand() ;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#GetSimilarExpression(parser.expression.CExpression)
	 */
	public CExpression GetSimilarExpression(CExpression operand)
	{
		CCondNotStatement not = new CCondNotStatement(getLine(), m_cond.GetSimilarExpression(operand));
		return not;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#IsBinaryCondition()
	 */
	public boolean IsBinaryCondition()
	{
		return m_cond.IsBinaryCondition() ;
	}
	public String toString()
	{
		return "NOT(" + m_cond.toString() + ")" ;
	}
//	public CExpression getMasterBinaryCondition()
//	{
//		CExpression master = m_cond.getMasterBinaryCondition() ;
////		if (master != null)
////		{
////			if (m_cond.IsBinaryCondition())
////			{
////				return new CCondNotStatement(master) ;
////			}
////		}
//		return master ;
//	}
	@Override
	public CExpression GetFirstCalculOperand()
	{
		return m_cond.GetFirstCalculOperand() ;
	}
}
