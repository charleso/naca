/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Oct 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.forms;

import generate.CBaseLanguageExporter;
import semantic.CBaseEntityFactory;
import semantic.CDataEntity;
import semantic.CEntityArrayReference;
import semantic.CSubStringAttributReference;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CBaseEntityExpression;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityFieldArrayReference extends CEntityArrayReference
{
	/**
	 * @param l
	 * @param cat
	 * @param out
	 */
	public CEntityFieldArrayReference(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
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
			CEntityFieldArrayReference eArray = factory.NewEntityFieldArrayReference(getLine()) ;
			eArray.m_arrIndexes = m_arrIndexes ;
			eArray.m_Reference = eData ;
			eArray.RegisterVarTesting(eCond) ;
			eCond.SetConditonReference(eArray);
			return eCond;
		}
	}
	public CDataEntity GetSubStringReference(CBaseEntityExpression start, CBaseEntityExpression length, CBaseEntityFactory factory) 
	{
		CSubStringAttributReference ref = factory.NewEntitySubString(getLine()) ;
		ref.SetReference(this, start, length) ;
		return ref ;
	}
}
