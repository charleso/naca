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
public abstract class CEntityCondEquals extends CBinaryEntityCondition
{
	
	public void SetEqualCondition(CBaseEntityExpression op1, CBaseEntityExpression op2)
	{
		ASSERT(op1);
		ASSERT(op2);
		m_op1 = op1; 
		m_op2 = op2 ;
		m_bIsDifferent = false ;
	}
	public void SetDifferentCondition(CBaseEntityExpression op1, CBaseEntityExpression op2) 
	{
		ASSERT(op1);
		ASSERT(op2);
		m_op1 = op1;
		m_op2 = op2 ;
		m_bIsDifferent = true ;
	}
	protected boolean m_bIsDifferent = false ;
	protected CBaseEntityExpression m_op1 = null ;
	protected CBaseEntityExpression m_op2 = null;
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
//	public CBaseEntityCondition getSimilarCondition(CBaseEntityFactory factory, CTerminal term)
//	{
//		CEntityCondEquals eq = factory.NewEntityCondEquals() ;
//		eq.m_op1 = m_op1 ;
//		if (term.IsReference())
//		{
//			CDataEntity e = term.GetDataEntity(factory);
//			eq.m_op2 = factory.NewEntityExprTerminal(e);
//			eq.m_bIsDifferent = m_bIsDifferent ;
//			return eq;
//		}
//		else
//		{
//			CDataEntity eOP = m_op1.GetSingleOperator() ;
//			if (eOP != null)
//			{
//				CBaseEntityCondition.ConditionType type = CBaseEntityCondition.ConditionType.IS_EQUAL ;
//				if (m_bIsDifferent)
//				{
//					type = CBaseEntityCondition.ConditionType.IS_DIFFERENT ;
//				} 
//				CBaseEntityCondition eCond = eOP.GetSpecialCondition(term.GetValue(), type, factory);
//				if (eCond != null)
//				{
//					return eCond ;
//				}
//			}
//			// else
//			CDataEntity e = term.GetDataEntity(factory);
//			eq.m_op2 = factory.NewEntityExprTerminal(e);
//			eq.m_bIsDifferent = m_bIsDifferent ;
//			return eq;
//		}
//	}
	public CBaseEntityCondition GetSpecialConditionReplacing(String val, CBaseEntityFactory fact, CDataEntity replace)
	{
		EConditionType type = null ;
		if (m_bIsDifferent)
		{
			type = EConditionType.IS_DIFFERENT ; 
		}
		else
		{
			type = EConditionType.IS_EQUAL ; 
		}
		CDataEntity op1 = m_op1.GetSingleOperator() ;
		CDataEntity op2 = m_op2.GetSingleOperator() ;
		if (op1 != null && (replace==null || replace==op2))
		{
			CBaseEntityCondition cond = op1.GetSpecialCondition(getLine(), val, type, fact) ;
			if (cond != null)
			{
				return cond ;
			}
		}
		else if (op2 != null && (replace==null || replace==op1) 
				&& op2.GetDataType() != CDataEntity.CDataEntityType.NUMBER
				&& op2.GetDataType() != CDataEntity.CDataEntityType.STRING
				&& op2.GetDataType() != CDataEntity.CDataEntityType.CONSTANT)
		{
			CBaseEntityCondition cond = op2.GetSpecialCondition(getLine(), val, type, fact) ;
			if (cond != null)
			{
				return cond ;
			}
		}
		else
		{
			return  null ;
		}
		return null ;
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		boolean b1 = m_op1.ReplaceVariable(field, var) ;
		boolean b2 = m_op2.ReplaceVariable(field, var) ;
		return b1 || b2 ;
	}

}
