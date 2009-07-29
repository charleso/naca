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
/**
 * 
 */
package parser.expression;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import semantic.CBaseEntityFactory;
import semantic.CDataEntity;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CEntityExprLengthOf;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class CLengthOfExpression extends CExpression
{
	protected CExpression m_Expression = null ;
	 
	public CLengthOfExpression(int line, CExpression term)
	{
		super(line) ;
		m_Expression = term ;
	}
	public boolean IsReference()
	{
		return false;
	}

	public CBaseEntityExpression AnalyseExpression(CBaseEntityFactory factory)
	{
		CDataEntity dataEntity = m_Expression.GetReference(factory);
		CEntityExprLengthOf e = factory.NewEntityExprLengthOf(getLine()) ;
		e.setVariable(dataEntity);
		return e;
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#AnalyseCondition(semantic.CBaseEntityFactory)
	 */
	public CBaseEntityCondition AnalyseCondition(CBaseEntityFactory factory, CDefaultConditionManager condMaster)
	{
		return null;
	}
	
	protected boolean CheckMembersBeforeExport()
	{
		return CheckMemberNotNull(m_Expression);
	}
	
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#DoExport(org.w3c.dom.Document)
	 */
	public Element DoExport(Document root)
	{
		Element eop = root.createElement("LengthOf");
		eop.appendChild(m_Expression.Export(root));
		return eop ;		
	}
	/* (non-Javadoc)
	 * @see parser.expression.CExpression#GetSimilarExpression(parser.expression.CExpression)
	 */
	public CExpression GetSimilarExpression(CExpression operand)
	{
		ASSERT() ;
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
		return this;
	}
	public String toString()
	{
		return "LengthOf("+m_Expression.toString()+")" ;
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
