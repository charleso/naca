/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 18 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.expression;

import parser.expression.CSumExpression;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityExprSum extends CBaseEntityExpression
{
	public void SetSumExpression(CBaseEntityExpression Op1, CBaseEntityExpression Op2, CSumExpression.CSumType Type)
	{
		m_Op1 = Op1 ;
		m_Op2 = Op2 ;
		m_Type = Type ;
	} 
	protected CSumExpression.CSumType m_Type = null ;
	protected CBaseEntityExpression m_Op1 = null ;
	protected CBaseEntityExpression m_Op2 = null ;
	public void Clear()
	{
		super.Clear() ;
		m_Op1.Clear() ;
		m_Op1 = null ;
		m_Op2.Clear() ;
		m_Op2 = null ;
	}
	public boolean ignore()
	{
		return m_Op1.ignore() || m_Op2.ignore();
	}

	@Override
	public CEntityExpressionType getExpressionType()
	{
		return CEntityExpressionType.MATH;
	}


}
