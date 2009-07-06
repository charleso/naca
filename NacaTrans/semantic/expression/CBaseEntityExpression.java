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

import semantic.CDataEntity;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CBaseEntityExpression extends CBaseEntityCondExpr
{

	public enum CEntityExpressionType
	{
		NUMERIC, STRING, VARIABLE, MATH, ATTRIBUTE, ADDRESS
	}
	
	/**
	 * @param line
	 * @param name
	 * @param cat
	 * @param out
	 */

	public CDataEntity GetSingleOperator()
	{
		return null;
	}
	/**
	 * @param field
	 * @param var
	 * @return TODO
	 */
	public abstract CEntityExpressionType getExpressionType() ;
}
