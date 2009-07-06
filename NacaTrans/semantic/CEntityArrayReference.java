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

import java.util.Vector;

import parser.expression.CTerminal;

import semantic.Verbs.*;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CUnitaryEntityCondition;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityArrayReference extends CBaseDataReference
{

	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntityArrayReference(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, "", cat, out);
	}
	
	public void SetReference(CDataEntity e)
	{
		m_Reference = e ;
	}
	public void AddIndex(CBaseEntityExpression e)
	{
		m_arrIndexes.add(e);
	}
	protected Vector<CBaseEntityExpression> m_arrIndexes = new Vector<CBaseEntityExpression>() ;
//	protected CDataEntity m_Reference = null ;
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
			CEntityArrayReference eArray = factory.NewEntityArrayReference(getLine()) ;
			eArray.m_arrIndexes = m_arrIndexes ;
			eArray.m_Reference = eData ;
			eArray.RegisterVarTesting(eCond) ;
			eCond.SetConditonReference(eArray);
			return eCond;
		}
	}
	public CUnitaryEntityCondition GetAssociatedCondition(CBaseEntityFactory factory)
	{
		CUnitaryEntityCondition eCond = m_Reference.GetAssociatedCondition(factory);
		if (eCond == null)
		{
			return null ;
		}
		else
		{
			CDataEntity eData = eCond.GetConditionReference() ;
			CEntityArrayReference eArray = factory.NewEntityArrayReference(getLine()) ;
			eArray.m_arrIndexes = m_arrIndexes ;
			eArray.m_Reference = eData ;
			eArray.RegisterVarTesting(eCond) ;
			eCond.SetConditonReference(eArray);
			return eCond;
		}
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetSpecialAssignment(parser.expression.CTerminal, semantic.CBaseEntityFactory, int)
	 */
	public CBaseActionEntity GetSpecialAssignment(CTerminal term, CBaseEntityFactory factory, int l)
	{
		String value = term.GetValue() ;
		CEntitySetConstant eAssign = factory.NewEntitySetConstant(l) ;
		if (value.equals("ZERO") || value.equals("ZEROS") || value.equals("ZEROES"))
		{
			eAssign.SetToZero(this) ;
		}
		else if (value.equals("SPACE") || value.equals("SPACES"))
		{
			eAssign.SetToSpace(this) ;
		}
		else if (value.equals("LOW-VALUE"))
		{
			eAssign.SetToLowValue(this) ;
		}
		else if (value.equals("HIGH-VALUE"))
		{
			eAssign.SetToHighValue(this) ;
		}
		else
		{
			return null ;
		}
		RegisterWritingAction(eAssign) ;
		return eAssign ;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#ignore()
	 */
	public boolean ignore()
	{
		return m_Reference.ignore() ;
	}
	public String GetConstantValue()
	{
		return "" ;
	} 	 
	public CDataEntity GetSubStringReference(CBaseEntityExpression start, CBaseEntityExpression length, CBaseEntityFactory factory) 
	{
		CSubStringAttributReference ref = factory.NewEntitySubString(getLine()) ;
		ref.SetReference(this, start, length) ;
		return ref ;
	};

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#Clear()
	 */
	public void Clear()
	{
		super.Clear();
		m_arrIndexes.clear() ;
	}
}
