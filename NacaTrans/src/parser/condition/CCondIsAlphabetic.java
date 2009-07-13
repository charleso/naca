/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 13 août 2004
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
import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CEntityCondIsKindOf;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CCondIsAlphabetic extends CExpression
{
	public CCondIsAlphabetic(int line, CExpression term, int method, boolean bIsOpposite) // method is <0 : lower, =0 : normal, >0 upper
	{
		super(line) ;
		m_term = term ;
		m_method = 0 ;
		m_bIsOpposite = bIsOpposite ;
	}

	protected boolean m_bIsOpposite = false ;
	protected int m_method = 0 ;
	CExpression m_term = null ;
	public int GetMethod()
	{
		return m_method;
	}

	/* (non-Javadoc)
	 * @see parser.expression.CExpression#WriteTo(parser.expression.CBaseExpressionExporter)
	 */
//	public void WriteTo(CBaseExpressionExporter exporter)
//	{
//		exporter.Write(this) ;
//	} 
//	public void WriteOpTo(CBaseExpressionExporter exp)
//	{
//		m_term.WriteTo(exp) ;
//	}

	/* (non-Javadoc)
	 * @see parser.expression.CExpression#GetPriorityLevel()
	 */
	public int GetPriorityLevel()
	{
		return 7;
	}

	protected boolean CheckMembersBeforeExport()
	{
		boolean b = CheckMemberNotNull(m_term);
		return b; 
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#DoExport(org.w3c.dom.Document)
	 */
	public Element DoExport(Document root)
	{
		Element e = root.createElement("IsAlphabetic");
		e.appendChild(m_term.Export(root)) ;
		return e;
	}

	/* (non-Javadoc)
	 * @see parser.expression.CExpression#GetOppositeCondition()
	 */
	public CExpression GetOppositeCondition()
	{
		return new CCondIsAlphabetic(getLine(), m_term, m_method, m_bIsOpposite);
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
		CEntityCondIsKindOf eCond = factory.NewEntityCondIsKindOf() ;
		if (m_bIsOpposite)
		{
			eCond.setOpposite() ;
		}
		if (m_term.IsReference())
		{
			CDataEntity eData = m_term.GetReference(factory) ;
			if (m_method < 0)
			{
				eCond.SetIsLower(eData) ;
			}
			else if (m_method > 0)
			{
				eCond.SetIsUpper(eData) ;
			}
			else
			{
				eCond.SetIsAlphabetic(eData) ;
			}
			eData.RegisterVarTesting(eCond) ;
			return eCond;
		}
		else
		{
			//CBaseEntityExpression data = m_term.AnalyseExpression(factory) ;
			Transcoder.logError(getLine(), "Unexpecting situation : MUST be an identifier");
			return null ;
		}
	}

	/* (non-Javadoc)
	 * @see parser.expression.CExpression#GetFirstOperand()
	 */
	public CExpression GetFirstConditionOperand()
	{
		return m_term;
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
		return "IS_APHABETIC(" + m_term.toString() + ")" ;
	}
	public CExpression getMasterBinaryCondition()
	{
		return this ;
	}

	@Override
	public CExpression GetFirstCalculOperand()
	{
		return m_term.GetFirstCalculOperand() ;
	}

}
