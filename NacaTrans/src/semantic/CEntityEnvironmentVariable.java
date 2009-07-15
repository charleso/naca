/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Aug 17, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic;

import java.util.Vector;


import generate.*;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CEntityCondIsConstant;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityEnvironmentVariable extends CDataEntity
{
	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntityEnvironmentVariable(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, String accessor, String writer, boolean bNumericVar)
	{
		super(l, name, cat, out);
		m_csAccessor = accessor ;
		m_csWriteAccessor = writer ;
		m_bNumericVariable = bNumericVar ;
	}
	
	protected String m_csAccessor = "" ;
	protected String m_csWriteAccessor = "" ;
	protected boolean m_bNumericVariable = false ;
	
	public CBaseEntityCondition GetSpecialCondition(int nLine, String value, CBaseEntityCondition.EConditionType type, CBaseEntityFactory factory)
	{
		CEntityCondIsConstant eCond = factory.NewEntityCondIsConstant() ;
		if (value.equals("ZERO") || value.equals("ZEROS") || value.equals("ZEROES"))
		{
			eCond.SetIsZero(this);
		}
		else if (value.equals("SPACE") || value.equals("SPACES"))
		{
			eCond.SetIsSpace(this);
		}
		else if (value.equals("LOW-VALUE") || value.equals("LOW-VALUES"))
		{
			eCond.SetIsLowValue(this);
		}
		else if (value.equals("HIGH-VALUE"))
		{
			eCond.SetIsHighValue(this);
		}
		else
		{
			return null ;
		}
		if (type == CBaseEntityCondition.EConditionType.IS_DIFFERENT)
		{
			eCond.SetOpposite() ;
			return eCond ;
		}
		else if (type == CBaseEntityCondition.EConditionType.IS_EQUAL)
		{
			return eCond ;
		}
		else
		{
			return null ;
		}
	}
	public boolean ignore()
	{
		return false ;
	}
	public String GetConstantValue()
	{
		return "" ;
	} 	 
	public CDataEntity GetArrayReference(Vector v, CBaseEntityFactory factory) 
	{
//		CEntityArrayReference e = factory.NewEntityArrayReference(getLine()) ;
//		e.SetReference(this) ;
//		for (int i=0; i<v.size(); i++)
//		{
//			CExpression expr = (CExpression)v.get(i);
//			CBaseEntityExpression exp = expr.AnalyseExpression(factory);
//			e.AddIndex(exp);
//		}
//		return e ;
		return this ;
	};
	public CDataEntity GetSubStringReference(CBaseEntityExpression start, CBaseEntityExpression length, CBaseEntityFactory factory) 
	{
		CSubStringAttributReference ref = factory.NewEntitySubString(getLine()) ;
		ref.SetReference(this, start, length) ;
		return ref ;
	};
	@Override
	public CDataEntityType GetDataType()
	{
		if (m_bNumericVariable)
			return CDataEntityType.NUMERIC_VAR ;
		else
			return CDataEntityType.VAR;
	}
}
