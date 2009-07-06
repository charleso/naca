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
public abstract class CEntityExprTerminal extends CBaseEntityExpression
{

	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondExpr#GetDataType()
	 */
	@Override
	public CDataEntityType GetDataType()
	{
		return m_Term.GetDataType();
	}
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityExprTerminal(CDataEntity term)
	{
		ASSERT(term);
		m_Term = term ;
	}
	protected CDataEntity m_Term = null ;
	public void Clear()
	{
		super.Clear() ;
		m_Term = null ;
	}
	public CDataEntity GetSingleOperator()
	{
		return m_Term ;
	}
	public boolean ignore()
	{
		return m_Term.ignore() ;
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_Term == field)
		{
			m_Term = var ;
			return true ;
		}
		return false ;
	}

	@Override
	public CEntityExpressionType getExpressionType()
	{
		CDataEntityType type = m_Term.GetDataType() ;
		if (type == null)
		{
			return null ;
		}
		switch (type)
		{
			case FIELD_ATTRIBUTE:
				return CEntityExpressionType.ATTRIBUTE ;
			case NUMBER:
			case NUMERIC_VAR:
				return CEntityExpressionType.NUMERIC ;
			case CONSOLE_KEY:
			case CONSTANT :
			case STRING:
				return CEntityExpressionType.STRING ;
			case CONDITION:
			case FIELD:
			case EXTERNAL_REFERENCE:
			case FORM:
			case VAR:
			case VIRTUAL_FORM:
				return CEntityExpressionType.VARIABLE ;
			case IGNORE:
				return null ;
			case EXPRESSION:
				return CEntityExpressionType.MATH ;
			case ADDRESS:
				return CEntityExpressionType.ADDRESS ;
		}
		return null ;
	}

	
	@Override
	public String GetConstantValue()
	{
		return m_Term.GetConstantValue() ;
	}
}
