/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 11 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.forms;

import generate.*;

import java.util.Vector;

import lexer.Cobol.CCobolConstantList;
import parser.expression.CTerminal;
import semantic.*;
import semantic.expression.CBaseEntityCondition;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityFieldFlag extends CBaseEntityFieldAttribute
{

	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 * @param type
	 * @param owner
	 */
	public CEntityFieldFlag(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity owner)
	{
		super(l, name, cat, out, CEntityFieldAttributeType.FLAG, owner);
	}
	public CBaseActionEntity GetSpecialAssignment(CTerminal term, CBaseEntityFactory factory, int l)
	{
		CEntityFieldAttributeReference ref = factory.NewEntityFieldAttributeReference(m_Reference) ;
		CEntitySetFlag eSet = factory.NewEntitySetFlag(l, ref) ;
		String v = term.GetValue() ;
		if (v.equals(CCobolConstantList.LOW_VALUE.m_Name) || v.equals(CCobolConstantList.LOW_VALUES.m_Name))
		{
			eSet.ResetFlag() ;
		}
		else if (v.equals("1"))
		{
			eSet.SetFlag("1") ;
		}
		else if (v.equals("0"))
		{
			eSet.SetFlag("0") ;
		}
		else if (v.equals(CCobolConstantList.SPACE.m_Name) || v.equals(CCobolConstantList.SPACES.m_Name))
		{
			eSet.SetFlag("0") ;
		}
		else if (v.equals(CCobolConstantList.ZERO.m_Name) || v.equals(CCobolConstantList.ZEROS.m_Name) || v.equals(CCobolConstantList.ZEROES.m_Name))
		{
			eSet.SetFlag("0") ;
		}
		else
		{
			return null ;
		}
		ref.RegisterWritingAction(eSet) ;
		return eSet;
	}
	public CDataEntity GetArrayReference(Vector v, CBaseEntityFactory factory) 
	{
		CDataEntity e = m_Reference.GetArrayReference(v, factory) ;
		return factory.NewEntityFieldFlag(getLine(), "", e);
	};
	public CBaseEntityCondition GetSpecialCondition(int nLine, String value, CBaseEntityCondition.EConditionType type, CBaseEntityFactory factory)
	{
		CEntityIsFieldFlag eCond = factory.NewEntityIsFieldFlag() ;
		if (value.equals("1"))
		{
			eCond.SetIsFlag(m_Reference, "1");
		}
		else if (value.equals("LOW-VALUE") || value.equals("LOW-VALUES"))
		{
			eCond.SetIsFlagSet(m_Reference);
			eCond.SetOpposite() ;	 // if FIELD.Flag == LOW-VALUE  <=>  if flag not set
		}
		else if (value.equals("0")|| value.equals("ZERO") || value.equals("ZEROS") || value.equals("ZEROES"))
		{
			eCond.SetIsFlag(m_Reference, "0");
		}
		else
		{
			return null ;
		}
		m_Reference.RegisterVarTesting(eCond) ;
		if (type == CBaseEntityCondition.EConditionType.IS_EQUAL)
		{
			return eCond ;
		}
		else if (type == CBaseEntityCondition.EConditionType.IS_DIFFERENT)
		{
			eCond.SetOpposite() ;
			return eCond ;			
		}
		else
		{
			return null ;
		}
	}
}
