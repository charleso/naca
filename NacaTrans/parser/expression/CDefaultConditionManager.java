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
 * Created on 29 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package parser.expression;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CDefaultConditionManager
{
	public CDefaultConditionManager()
	{
		int n = 0;
	}
	
	public CDefaultConditionManager(CExpression exp)
	{
		if(exp == null)
		{
			int n = 0;
		}
		m_expMaster = exp ;
	}
	
	private CExpression m_expMaster = null ;
	
	public boolean isDefaultOperatorSetted()
	{
		return m_bIsDefaultOperatorSetted ;
	}
	
	protected boolean m_bIsDefaultOperatorSetted = false ;

	/**
	 * @param expression
	 * @return
	 */
	public CExpression GetSimilarExpression(CTermExpression expression)
	{
		m_bIsDefaultOperatorSetted = true ;
		if(m_expMaster == null)
			return null;	// PJD: wil crash
		return m_expMaster.GetSimilarExpression(expression);
	}

	/**
	 * @param st1
	 */
	public void SetMasterCondition(CExpression st1)
	{
		m_bIsDefaultOperatorSetted = false ;
		m_expMaster = st1 ;
	}

	/**
	 * @return
	 */
	public CExpression GetFirstOperand()
	{
		return m_expMaster.GetFirstConditionOperand() ;
	}

	/**
	 * @return
	 */
	public boolean isSetted()
	{
		return m_expMaster != null ;
	}
}
