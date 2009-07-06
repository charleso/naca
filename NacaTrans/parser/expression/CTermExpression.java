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


import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CBaseEntityExpression;
import utils.Transcoder;


/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CTermExpression extends CExpression
{
	public CTermExpression(int line, CTerminal t)
	{
		super(line) ;
		m_term = t ;
	}
	
	CTerminal m_term = null ;
	
	protected boolean CheckMembersBeforeExport()
	{
		return CheckMemberNotNull(m_term);
	}
	
	public Element DoExport(Document root)
	{
		Element e = root.createElement("Value");
		m_term.ExportTo(e, root) ;
		return e;
	}
	public CTerminal GetTerminal()
	{
		return m_term ;
	}

	public CBaseEntityExpression AnalyseExpression(CBaseEntityFactory factory)
	{
		CDataEntity eData = m_term.GetDataReference(getLine(), factory);
		if (eData == null)
		{
			eData = m_term.GetDataEntity(getLine(), factory);
		}
		if (eData == null)
		{
			return null ;
		}
		CBaseEntityExpression exp = factory.NewEntityExprTerminal(eData) ;
		eData.RegisterValueAccess(exp) ;
		return exp ;
	}
	public CBaseEntityCondition AnalyseCondition(CBaseEntityFactory factory, CDefaultConditionManager condMaster)
	{
		CDataEntity eData = m_term.GetDataEntity(getLine(), factory);
		if (eData != null)
		{
			CBaseEntityCondition eCond = eData.GetAssociatedCondition(factory) ;
			if (eCond != null)
			{
				return eCond ;
			}
			else
			{
				CExpression newCond = condMaster.GetSimilarExpression(this); 
				eCond = newCond.AnalyseCondition(factory, condMaster) ;
				return eCond ;
			}
		}
		else if (!m_term.IsReference())
		{
			if (condMaster != null)
			{
				CExpression newCond = condMaster.GetSimilarExpression(this); 
				CBaseEntityCondition eCond = newCond.AnalyseCondition(factory, condMaster) ;
				return eCond ;
			}
			else
			{
				return null ;
			}
		} 
		else
		{
			return null ;
		}
	}
	public boolean IsReference()
	{
		return m_term.IsReference() ;
	}	
	public boolean IsConstant()
	{
		return !m_term.IsReference() ;
	}
	public CDataEntity GetReference(CBaseEntityFactory factory)
	{
		CDataEntity e = m_term.GetDataReference(getLine(), factory);
		return e;
	} 
	public String GetConstantValue()
	{
		if (m_term.IsReference())
		{
			return "" ;
		}
		else
		{
			return m_term.GetValue();
		}
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
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#GetFirstOperand()
	 */
	public CExpression GetFirstConditionOperand()
	{
		//return this;
		return null ;
	}
	public String toString()
	{
		return m_term.toString() ;
	}
	public CExpression getMasterBinaryCondition()
	{
		return null ;
	}
	@Override
	public CExpression GetFirstCalculOperand()
	{
		return this ;
	}

	
}
