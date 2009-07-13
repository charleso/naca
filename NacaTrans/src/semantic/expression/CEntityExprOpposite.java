/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Sep 27, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.expression;




/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityExprOpposite extends CBaseEntityExpression
{
	
	public void SetOpposite(CBaseEntityExpression e)
	{
		m_data = e ;
	}
	protected CBaseEntityExpression m_data = null ;
	public void Clear()
	{
		super.Clear() ;
		m_data.Clear() ;
		m_data = null ;
	}
	public boolean ignore()
	{
		return m_data.ignore();
	}

	@Override
	public CEntityExpressionType getExpressionType()
	{
		return CEntityExpressionType.MATH;
	}

	
}
