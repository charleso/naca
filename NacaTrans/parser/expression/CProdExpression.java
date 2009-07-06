/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jul 28, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.expression;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


import semantic.CBaseEntityFactory;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CEntityExprProd;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CProdExpression extends CExpression
{
	public static class CProdType
	{
		public String Text = "" ;
		protected CProdType(String t)
		{
			Text = t ;
		} 
		public static CProdType PROD = new CProdType("MULT") ;
		public static CProdType DIVIDE = new CProdType("DIVID") ;
	}
	public CProdExpression(int line, CExpression op1, CExpression op2, CProdType t)
	{
		super(line) ;
		m_Op1 = op1 ;
		m_Op2 = op2 ;
		m_Type = t ;
	}
	protected CExpression m_Op1 = null ;
	protected CExpression m_Op2 = null ;
	protected CProdType m_Type = null ;
	
	protected boolean CheckMembersBeforeExport()
	{
		boolean b = CheckMemberNotNull(m_Op1);
		b &= CheckMemberNotNull(m_Op2);
		return b;
	}

	
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#Export(org.w3c.dom.Document)
	 */
	public Element DoExport(Document root)
	{
		Element e = root.createElement(m_Type.Text) ;
		Element e1 = m_Op1.Export(root) ;
		e.appendChild(e1) ;
		Element e2 = m_Op2.Export(root) ;
		e.appendChild(e2) ;
		return e;
	}
	public CProdType GetType()
	{
		return m_Type ;
	}
	public CBaseEntityExpression AnalyseExpression(CBaseEntityFactory factory)
	{
		CEntityExprProd eProd = factory.NewEntityExprProd();
		CBaseEntityExpression op1 = m_Op1.AnalyseExpression(factory) ;
		CBaseEntityExpression op2 = m_Op2.AnalyseExpression(factory) ;
		eProd.SetProdExpression(op1, op2, m_Type) ;
		return eProd;
	}
	public CBaseEntityCondition AnalyseCondition(CBaseEntityFactory factory, CDefaultConditionManager condMaster)
	{
		return null;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#GetFirstOperand()
	 */
	public CExpression GetFirstConditionOperand()
	{
		return this;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#GetSimilarExpression(parser.expression.CExpression)
	 */
	public CExpression GetSimilarExpression(CExpression operand)
	{
		ASSERT();
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
		if (m_Type == CProdType.PROD)
		{
			return "MULT("+m_Op1.toString()+", "+m_Op2.toString()+")" ;
		}
		else
		{
			return "DIV("+m_Op1.toString()+", "+m_Op2.toString()+")" ;
		}
	}
	public CExpression getMasterBinaryCondition()
	{
		return null ;
	}
	@Override
	public CExpression GetFirstCalculOperand()
	{
		return m_Op1.GetFirstCalculOperand() ;
	}
}
