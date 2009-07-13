/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 6 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic;

import generate.*;
import lexer.Cobol.CCobolConstantList;
import parser.expression.CTerminal;
import semantic.Verbs.*;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CBaseEntityExpression;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CSubStringAttributReference extends CBaseDataReference
{

	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CSubStringAttributReference(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, "", cat, out);
	}
	public void SetReference(CDataEntity ref, CBaseEntityExpression start, CBaseEntityExpression length)
	{
		m_Reference = ref ;
		m_Start = start ;
		m_Length = length;
	}
	
	protected CBaseEntityExpression m_Start = null ;
	protected CBaseEntityExpression m_Length = null ;

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetSpecialAssignment(parser.expression.CTerminal)
	 */
	public CBaseActionEntity GetSpecialAssignment(CTerminal term, CBaseEntityFactory factory, int l)
	{
		String value = term.GetValue() ;
		CEntitySetConstant eAssign = factory.NewEntitySetConstant(l) ;
		eAssign.SetSubStringRef(m_Start, m_Length);
		if (value.equals(CCobolConstantList.ZERO.m_Name) || value.equals(CCobolConstantList.ZEROS.m_Name) || value.equals(CCobolConstantList.ZEROES.m_Name))
		{
			eAssign.SetToZero(m_Reference) ;
		}
		else if (value.equals(CCobolConstantList.SPACE.m_Name) || value.equals(CCobolConstantList.SPACES.m_Name))
		{
			eAssign.SetToSpace(m_Reference) ;
		}
		else if (value.equals(CCobolConstantList.LOW_VALUE.m_Name) || value.equals(CCobolConstantList.LOW_VALUES.m_Name))
		{
			eAssign.SetToLowValue(m_Reference) ;
		}
		else if (value.equals(CCobolConstantList.HIGH_VALUE.m_Name) || value.equals(CCobolConstantList.HIGH_VALUES.m_Name))
		{
			eAssign.SetToHighValue(m_Reference) ;
		}
		else
		{
			return null ;
		}
		m_Reference.RegisterWritingAction(eAssign) ;
		//RegisterWritingAction(eAssign) ;
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
		CBaseEntityCondition eCond = m_Reference.GetSpecialCondition(getLine(), value, type, factory);
		if (eCond == null)
		{
			return null ;
		}
		else
		{
			CDataEntity eData = eCond.GetConditionReference() ;
			CSubStringAttributReference eSubStr = factory.NewEntitySubString(getLine()) ;
			eSubStr.m_Length = m_Length ;
			eSubStr.m_Start = m_Start ;
			eSubStr.m_Reference = eData ;
			eCond.SetConditonReference(eSubStr);
			eSubStr.RegisterVarTesting(eCond) ;
			return eCond;
		}
	}
	public boolean ignore()
	{
		if (m_Reference == null)
			return true ;
		return m_Reference.ignore() ;
	}
	public String GetConstantValue()
	{
		return "" ;
	} 	 
	public void Clear()
	{
		super.Clear();
		m_Length.Clear();
		m_Length = null ;
		m_Start.Clear() ;
		m_Start = null ;
	}
	
}
