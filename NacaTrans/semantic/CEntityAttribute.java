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
 * Created on 2 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic;

import generate.*;
import lexer.Cobol.CCobolConstantList;
import parser.expression.CTerminal;
import semantic.Verbs.*;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CEntityCondCompare;
import semantic.expression.CEntityCondIsConstant;
import semantic.expression.CEntityConstant;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityAttribute extends CGenericDataEntityReference implements ITypableEntity
{

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#FindFirstDataEntityAtLevel(int)
	 */
	@Override
	public CDataEntity FindFirstDataEntityAtLevel(int level)
	{
		if (level == 1 || level == 77)
		{
			return this ;
		}
		return null ;
	}
	/**
	 * @param name
	 * @param cat
	 */
	public CEntityAttribute(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, name, cat, out);
	}
	public void SetComp(String s)
	{
		m_Comp = s ;
	}
	public void SetTypeString(int length) 
	{
		m_Type = "picX" ;
		m_Length = length ;
	};
	public void SetTypeNum(int length, int dec)
	{
		m_Type = "pic9" ;
		m_Length = length ;
		m_Decimals = dec ;
	};
	public void SetTypeSigned(int length, int dec)
	{
		m_Type = "picS9" ;
		m_Length = length ;
		m_Decimals = dec ;
	};
	public void SetInitialValueSpaces()
	{
		m_bInitialValueIsSpaces = true ;
		m_bInitialValueIsZeros = false ;
		m_bInitialValueIsLowValue = false ;
		m_bInitialValueIsHighValue = false ;
		m_Value = null ;
	}
	public void SetInitialValueZeros()
	{
		m_bInitialValueIsSpaces = false ;
		m_bInitialValueIsZeros = true ;
		m_bInitialValueIsLowValue = false ;
		m_bInitialValueIsHighValue = false ;
		m_Value = null ;
	}
	public void SetInitialLowValue()
	{
		m_Value = null ;
		m_bInitialValueIsSpaces = false ;
		m_bInitialValueIsZeros = false ;
		m_bInitialValueIsLowValue = true ;
		m_bInitialValueIsHighValue = false ;
	}
	public void SetInitialHighValue()
	{
		m_Value = null ;
		m_bInitialValueIsSpaces = false ;
		m_bInitialValueIsZeros = false ;
		m_bInitialValueIsLowValue = false ;
		m_bInitialValueIsHighValue = true ;
	}
	public void SetInitialValueAll(CDataEntity s)
	{
		m_Value = s ;
		m_bFillWithValue = true ;
		m_bInitialValueIsSpaces = false ;
		m_bInitialValueIsZeros = false ;
		m_bInitialValueIsLowValue = false ;
		m_bInitialValueIsHighValue = false ;
	}
	public void SetInitialValue(CDataEntity s)
	{
		m_Value = s ;
		m_bInitialValueIsSpaces = false ;
		m_bInitialValueIsZeros = false ;
		m_bInitialValueIsLowValue = false ;
		m_bInitialValueIsHighValue = false ;
	}
	public void SetTypeEdited(String f)
	{
		m_Type = "pic" ;
		m_Length = 0;
		m_Decimals = 0;
		m_Format =f ;
	}

	public CDataEntity GetSubStringReference(CBaseEntityExpression start, CBaseEntityExpression length, CBaseEntityFactory factory) 
	{
		CSubStringAttributReference ref = factory.NewEntitySubString(getLine()) ;
		ref.SetReference(this, start, length) ;
		return ref ;
	};
	
	protected CDataEntity m_Value = null ; 
	protected boolean m_bInitialValueIsSpaces = false ;
	protected boolean m_bInitialValueIsZeros = false ;
	protected boolean m_bInitialValueIsLowValue = false ;
	protected boolean m_bInitialValueIsHighValue = false ;
	protected String m_Comp = "" ;
	protected String m_Type = "" ;
	protected int m_Length = 0 ;
	protected int m_Decimals = 0 ;
	protected String m_Format = "" ;
	protected boolean m_bSync = false ;
	protected boolean m_bFillWithValue = false ;
	public void SetSync(boolean b)
	{
		m_bSync = b ;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetSpecialAssignment(parser.expression.CTerminal)
	 */
	public CBaseActionEntity GetSpecialAssignment(CTerminal term, CBaseEntityFactory factory, int l)
	{
		String value = term.GetValue() ;
		CEntitySetConstant eAssign = factory.NewEntitySetConstant(l) ;
		if (value.equals(CCobolConstantList.ZERO.m_Name) || value.equals(CCobolConstantList.ZEROS.m_Name) || value.equals(CCobolConstantList.ZEROES.m_Name))
		{
			eAssign.SetToZero(this) ;
		}
		else if (value.equals(CCobolConstantList.SPACE.m_Name) || value.equals(CCobolConstantList.SPACES.m_Name))
		{
			eAssign.SetToSpace(this) ;
		}
		else if (value.equals(CCobolConstantList.LOW_VALUE.m_Name) || value.equals(CCobolConstantList.LOW_VALUES.m_Name))
		{
			eAssign.SetToLowValue(this) ;
		}
		else if (value.equals(CCobolConstantList.HIGH_VALUE.m_Name) || value.equals(CCobolConstantList.HIGH_VALUES.m_Name))
		{
			eAssign.SetToHighValue(this) ;
		}
		else if (term.IsNumber() && (m_Type.equals("picX") || m_Type.equals("")))
		{
			String type = m_Type ;
			if (type.equals(""))
				type = "GROUP" ;
			CEntityAssign asgn = factory.NewEntityAssign(l) ;
			asgn.SetValue(factory.NewEntityString(value)) ;
			asgn.AddRefTo(this) ;
			Transcoder.logInfo(l, "Number converted to string to move into "+type+" var ("+GetName()+"): "+value) ;
			RegisterWritingAction(asgn) ;
			return asgn ;
		}
		else
		{
			return null ;
		}
		return eAssign ;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetSpecialAssignment(semantic.CBaseDataEntity)
	 */
	public CBaseActionEntity GetSpecialAssignment(CDataEntity term, CBaseEntityFactory factory, int l)
	{
		return null;
	}

	public CBaseEntityCondition GetSpecialCondition(int nLine, String value, CBaseEntityCondition.EConditionType type, CBaseEntityFactory factory)
	{
		CEntityCondIsConstant eCond = factory.NewEntityCondIsConstant() ;
		if (value.equals("ZERO") || value.equals("ZEROS") || value.equals("ZEROES"))
		{
			eCond.SetIsZero(this);
		}
		/*else if (value.equals("SPACES") && type == CBaseEntityCondition.EConditionType.IS_GREATER_THAN)
		{
			CEntityCondCompare comp = factory.NewEntityCondCompare() ;
			comp.SetGreaterThan(factory.NewEntityExprTerminal(this), 
							factory.NewEntityExprTerminal(factory.NewEntityConstant(CEntityConstant.Value.SPACES))) ;
			RegisterVarTesting(comp) ;
			return comp ;
		}*/
		else if (value.equals("SPACE") || value.equals("SPACES"))
		{
			eCond.SetIsSpace(this);
		}
		else if (value.equals("LOW-VALUE"))
		{
			eCond.SetIsLowValue(this);
		}
		else if (value.equals("HIGH-VALUE") || value.equals("HIGH-VALUES"))
		{
			eCond.SetIsHighValue(this);
		}
//		else if (m_Type.equals("picX"))
//		{
//			try
//			{
//				int n = Integer.parseInt(value) ;
//				if (type == CBaseEntityCondition.EConditionType.IS_DIFFERENT)
//				{
//					CEntityCondEquals cond = factory.NewEntityCondEquals() ;
//					cond.SetDifferentCondition(factory.NewEntityExprTerminal(this), factory.NewEntityExprTerminal(factory.NewEntityString(value))) ;
//					m_logger.info("line "+getLine()+" : numeric value converted to string to compare with PICX var : " + value) ;
//					return cond ;
//				}
//				else if (type == CBaseEntityCondition.EConditionType.IS_EQUAL)
//				{
//					CEntityCondEquals cond = factory.NewEntityCondEquals() ;
//					cond.SetEqualCondition(factory.NewEntityExprTerminal(this), factory.NewEntityExprTerminal(factory.NewEntityString(value))) ;
//					m_logger.info("line "+getLine()+" : numeric value converted to string to compare with PICX var : " + value) ;
//					return cond ;
//				}
//				else
//				{
//					m_logger.info("line "+getLine()+" : numeric value to compare with EDIT var not managed : " + value) ;
//					return null ;
//				}
//			}
//			catch (NumberFormatException e)
//			{
//				return null ;
//			}
//		}
		else
		{
			return null ;
		}
		RegisterVarTesting(eCond) ;
		if (type == CBaseEntityCondition.EConditionType.IS_DIFFERENT)
		{
			eCond.SetOpposite() ;
			return eCond ;
		}
		else if (type == CBaseEntityCondition.EConditionType.IS_EQUAL)
		{
			return eCond ;
		}
		else if (type == CBaseEntityCondition.EConditionType.IS_LESS_THAN && value.equals("HIGH-VALUE"))
		{
			eCond.SetOpposite() ;
			return eCond ;
		}
		else if (type == CBaseEntityCondition.EConditionType.IS_GREATER_THAN && value.equals("LOW-VALUE"))
		{
			eCond.SetOpposite() ;
			return eCond ;
		}
		else
		{
			return null ;
		}
	}
	public int GetInternalLevel()
	{
		return 1 ;
	} 
	public String GetInitialValue()
	{
		if (m_Value != null)
		{
			return m_Value.GetConstantValue() ;
		}
		else
		{
			return "" ;
		}
	}
	public String GetConstantValue()
	{
		if (m_Value == null)
		{
			return "" ;
		}
		else
		{
			return m_Value.GetConstantValue() ;
		}
	} 	 
	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#Clear()
	 */
	public void Clear()
	{
		super.Clear();
		m_Value = null ;
	}
	public void SetJustifiedRight(boolean bJustifiedRight)
	{
		m_bJustifiedRight = bJustifiedRight ;
	}
	protected boolean m_bJustifiedRight = false ;
	
	public void SetBlankWhenZero(boolean blankWhenZero)
	{
		m_bBlankWhenZero = blankWhenZero ;
	}
	protected boolean m_bBlankWhenZero = false ;


	/**
	 * @see semantic.CGenericDataEntityReference#ReplaceVariable(semantic.CDataEntity, semantic.CDataEntity, boolean)
	 */
	@Override
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var, boolean bRead)
	{
		if (m_Value == field)
		{
			m_Value = var ;
			return false;
		}
		return false;
	}
	/**
	 * @return Returns the comp.
	 */
	public String getComp()
	{
		return m_Comp;
	}

}
