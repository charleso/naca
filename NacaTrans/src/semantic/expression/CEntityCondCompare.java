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
public abstract class CEntityCondCompare extends CBinaryEntityCondition
{
	
	public void SetLessThan(CBaseEntityExpression op1, CBaseEntityExpression op2)
	{
		m_op1 = op1 ; 
		m_op2 = op2 ;
		m_bIsOrEquals = false ;
		m_bIsGreater = false ;
	}
	public void SetLessOrEqualThan(CBaseEntityExpression op1, CBaseEntityExpression op2)
	{
		m_op1 = op1 ; 
		m_op2 = op2 ;
		m_bIsOrEquals = true ;
		m_bIsGreater = false ;
	}
	public void SetGreaterThan(CBaseEntityExpression op1, CBaseEntityExpression op2)
	{
		m_op1 = op1 ; 
		m_op2 = op2 ;
		m_bIsOrEquals = false ;
		m_bIsGreater = true ;
	}
	public void SetGreaterOrEqualsThan(CBaseEntityExpression op1, CBaseEntityExpression op2)
	{
		m_op1 = op1 ; 
		m_op2 = op2 ;
		m_bIsOrEquals = true ;
		m_bIsGreater = true ;
	}
	
	protected CBaseEntityExpression m_op1 ;
	protected CBaseEntityExpression m_op2 ;
	protected boolean m_bIsGreater = false ; // true : >/>=, false : </<=
	protected boolean m_bIsOrEquals = false ;// true : <=/>=, false : </>
	public void Clear()
	{
		super.Clear() ;
		m_op1.Clear() ;
		m_op1 = null ;
		m_op2.Clear() ;
		m_op2 = null ;
	}

	public boolean ignore()
	{
		return m_op1.ignore() || m_op2.ignore();  
	}
	public CBaseEntityCondition GetSpecialConditionReplacing(String val, CBaseEntityFactory fact, CDataEntity replace)
	{
		//CBaseEntityCondition cond = m_op1.GetSpecialCondition(val, type, fact) ;
		CDataEntity op = m_op1.GetSingleOperator() ;
		if (op != null)
		{
			EConditionType type = GetType() ;
			CBaseEntityCondition cond = op.GetSpecialCondition(getLine(), val, type, fact) ;
			if (cond != null)
			{
				return cond ;
			}
		}
		return null;
	}
	public CBaseEntityCondition.EConditionType GetType()
	{
		EConditionType type = null ;
		if (m_bIsGreater && m_bIsOrEquals)
		{
			type = EConditionType.IS_GREATER_THAN_OR_EQUAL ; 
		}
		else if (m_bIsGreater && !m_bIsOrEquals)
		{
			type = EConditionType.IS_GREATER_THAN ; 
		}
		else if (!m_bIsGreater && m_bIsOrEquals)
		{
			type = EConditionType.IS_LESS_THAN_OR_EQUAL ; 
		}
		else if (!m_bIsGreater && !m_bIsOrEquals)
		{
			type = EConditionType.IS_LESS_THAN ; 
		}
		return type ;
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		boolean b1 = m_op1.ReplaceVariable(field, var) ;
		boolean b2 = m_op2.ReplaceVariable(field, var) ;
		return b1 || b2 ;
	}
}
