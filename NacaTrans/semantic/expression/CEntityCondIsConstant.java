/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 19 août 2004
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
public abstract class CEntityCondIsConstant extends CUnitaryEntityCondition
{
//	public CEntityCondIsConstant(int nLine)
//	{
//		super(nLine);
//	}
	
	public void SetIsZero(CDataEntity eData)
	{
		m_bIsZero = true ;
		m_bIsSpace = false ;
		m_bIsLowValue = false ;
		m_bIsHighValue = false ;
		SetConditonReference(eData) ;
	}

	public void SetIsSpace(CDataEntity eData)
	{
		m_bIsZero = false ;
		m_bIsSpace = true ;
		m_bIsLowValue = false ;
		m_bIsHighValue = false ;
		SetConditonReference(eData) ;
	}

	public void SetIsHighValue(CDataEntity eData)
	{
		m_bIsZero = false ;
		m_bIsSpace = false ;
		m_bIsLowValue = false ;
		m_bIsHighValue = true;
		SetConditonReference(eData) ;
	}
	public void SetIsLowValue(CDataEntity eData)
	{
		m_bIsZero = false ;
		m_bIsSpace = false ;
		m_bIsLowValue = true ;
		m_bIsHighValue = false ;
		SetConditonReference(eData) ;
	}
	public void SetOpposite()
	{
		m_bIsOpposite = true ;
	}

	protected boolean m_bIsOpposite = false ;
	protected boolean m_bIsZero = false ;
	protected boolean m_bIsSpace = false ;
	protected boolean m_bIsLowValue = false ;
	protected boolean m_bIsHighValue = false ;
	public boolean ignore()
	{
		return m_Reference.ignore() ; 
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_Reference == field)
		{
			field.UnRegisterVarTesting(this) ;
			var.RegisterVarTesting(this) ;
			m_Reference = var ;
			return true ;
		}
		return false ;
	}

//	public CBaseEntityCondition getSimilarCondition(CBaseEntityFactory factory, CTerminal term)
//	{
//		if (term.IsReference())
//		{
//			CDataEntity e = term.GetDataEntity(factory) ;
//			CEntityCondEquals eq = factory.NewEntityCondEquals() ;
//			CBaseEntityExpression op1 = factory.NewEntityExprTerminal(m_Reference);
//			CBaseEntityExpression op2 = factory.NewEntityExprTerminal(e);
//			if (m_bIsOpposite)
//			{
//				eq.SetDifferentCondition(op1, op2);
//			}
//			else
//			{
//				eq.SetEqualCondition(op1, op2);
//			}
//			return eq ;
//		}
//		else
//		{
//			CBaseEntityCondition.ConditionType type = CBaseEntityCondition.ConditionType.IS_EQUAL ;
//			if (m_bIsOpposite)
//			{
//				type = CBaseEntityCondition.ConditionType.IS_DIFFERENT ;
//			}
//			CBaseEntityCondition cond = m_Reference.GetSpecialCondition(term.GetValue(), type, factory) ;
//			if (cond == null)
//			{
//				CDataEntity e = term.GetDataEntity(factory) ;
//				CEntityCondEquals eq = factory.NewEntityCondEquals() ;
//				CBaseEntityExpression op1 = factory.NewEntityExprTerminal(m_Reference);
//				CBaseEntityExpression op2 = factory.NewEntityExprTerminal(e);
//				if (m_bIsOpposite)
//				{
//					eq.SetDifferentCondition(op1, op2);
//				}
//				else
//				{
//					eq.SetEqualCondition(op1, op2);
//				}
//				return eq ;
//			}
//			return cond ;
//		}
//	}
	public CBaseEntityCondition GetSpecialConditionReplacing(String val, CBaseEntityFactory fact, CDataEntity replace)
	{
		return null;
	}
	public boolean isBinaryCondition()
	{
		return true;
	}

}
