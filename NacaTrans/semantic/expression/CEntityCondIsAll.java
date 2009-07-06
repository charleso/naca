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

import semantic.CBaseEntityFactory;
import semantic.CDataEntity;


/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityCondIsAll extends CBaseEntityCondition
{

	public void SetCondition(CBaseEntityExpression data, CBaseEntityExpression tok)
	{
		m_exprData = data ;
		m_exprToken = tok ;
	}
	
	protected CBaseEntityExpression m_exprData = null ;
	protected CBaseEntityExpression m_exprToken = null ;
	protected boolean m_bIsOpposite = false ;
	public void setOpposite()
	{
		m_bIsOpposite = ! m_bIsOpposite ;
	}
	public void Clear()
	{
		super.Clear() ;
		m_exprData.Clear() ;
		m_exprToken.Clear() ;
		m_exprData = null ;
		m_exprToken = null ;
	}
	public boolean ignore()
	{
		return m_exprData.ignore() ; 
	}
	public CBaseEntityCondition GetSpecialConditionReplacing(String val, CBaseEntityFactory fact, CDataEntity replace)
	{
		return null;
	}
	public boolean isBinaryCondition()
	{
		return false;
	}
	/**
	 * @see semantic.expression.CBaseEntityCondition#GetConditionReference()
	 */
	@Override
	public CDataEntity GetConditionReference()
	{
		return null;
	}
	public void SetConditonReference(CDataEntity e)
	{
		ASSERT(null) ;
	}
	
}
