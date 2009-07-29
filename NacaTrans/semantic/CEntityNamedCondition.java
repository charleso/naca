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
 * Created on 9 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic;

import generate.*;

import java.util.Vector;


import parser.expression.CExpression;
import parser.expression.CTerminal;
import semantic.Verbs.CEntitySetConstant;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CEntityIsNamedCondition;
import semantic.expression.CUnitaryEntityCondition;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityNamedCondition extends CDataEntity
{


	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntityNamedCondition(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, name, cat, out);
	}

//	public void SetCondition(CBaseEntityCondition cond)
//	{
//		m_Condition = cond ;
//	}
	
//	protected CBaseEntityCondition m_Condition = null ;

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetSpecialAssignment(parser.expression.CTerminal)
	 */
	public CBaseActionEntity GetSpecialAssignment(CTerminal term, CBaseEntityFactory factory, int l)
	{
		String cs = term.GetValue() ;
		if (cs.equalsIgnoreCase("TRUE"))
		{
			CEntitySetConstant eSet = factory.NewEntitySetConstant(l);
			eSet.SetCondition(this, true);
			return eSet ;
		}
		else if (cs.equalsIgnoreCase("FALSE"))
		{
			CEntitySetConstant eSet = factory.NewEntitySetConstant(l);
			eSet.SetCondition(this, false);
			return eSet ;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetSpecialAssignment(semantic.CBaseDataEntity)
	 */
	public CBaseActionEntity GetSpecialAssignment(CDataEntity term, CBaseEntityFactory factory, int l)
	{
		return null;
	}
	public CUnitaryEntityCondition GetAssociatedCondition(CBaseEntityFactory factory)
	{
		CEntityIsNamedCondition eCond = factory.NewEntityIsNamedCondition() ; 
		eCond.SetCondition(this) ;
		return eCond ;
	}
	
	public void AddInterval(CDataEntity eStart, CDataEntity eEnd)
	{
		m_arrEndIntervals.add(eEnd);
		m_arrStartIntervals.add(eStart);
	}
	public void AddValue(CDataEntity eValue)
	{
		m_arrValues.add(eValue);	
	}
	public CDataEntity GetArrayReference(Vector v, CBaseEntityFactory factory) 
	{
		CEntityArrayReference e = factory.NewEntityArrayReference(getLine()) ;
		e.SetReference(this) ;
		for (int i=0; i<v.size(); i++)
		{
			CExpression expr = (CExpression)v.get(i);
			CBaseEntityExpression exp = expr.AnalyseExpression(factory);
			e.AddIndex(exp);
		}
		return e ;
	};
	
	protected Vector<CDataEntity> m_arrStartIntervals = new Vector<CDataEntity>() ;
	protected Vector<CDataEntity> m_arrEndIntervals = new Vector<CDataEntity>() ;
	protected Vector<CDataEntity> m_arrValues = new Vector<CDataEntity>() ;
	public boolean ignore()
	{
		return m_arrStartIntervals.size() == 0 && m_arrEndIntervals.size() == 0 && m_arrValues.size() == 0 ;
	}
	public String GetConstantValue()
	{
		return "" ;
	} 	 
	public void Clear()
	{
		super.Clear();
		m_arrStartIntervals.clear() ;
		m_arrEndIntervals.clear() ;
		m_arrValues.clear() ;
	}
	
}
