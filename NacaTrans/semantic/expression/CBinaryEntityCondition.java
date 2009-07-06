/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package semantic.expression;

import semantic.CDataEntity;

/**
 * @author S. Charton
 * @version $Id: CBinaryEntityCondition.java,v 1.1 2006/03/14 20:46:36 U930CV Exp $
 */
public abstract class CBinaryEntityCondition extends CBaseEntityCondition
{

	/**
	 * 
	 */
	public CBinaryEntityCondition()
	{
		super();
	}
	/**
	 * @see semantic.expression.CBaseEntityCondition#isBinaryCondition()
	 */
	@Override
	public boolean isBinaryCondition()
	{
		return true ;
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
