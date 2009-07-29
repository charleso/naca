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
public abstract class CBaseEntityCondition extends CBaseEntityCondExpr
{
//	public CBaseEntityCondition(int nLine)
//	{
//		super(nLine);
//	}
	
	public enum EConditionType
	{
		IS_DIFFERENT, 
		IS_EQUAL, 
		IS_GREATER_THAN, 
		IS_GREATER_THAN_OR_EQUAL,
		IS_LESS_THAN,
		IS_LESS_THAN_OR_EQUAL,
		IS_FIELD_COLOR,
		IS_FIELD_ATTRIBUTE,
		IS_FIELD_HIGHLITING,
		IS_FIELD_MODIFIED,
		IS_FIELD_PROTECTED ;
	}
	/**
	 * @param line
	 * @param name
	 * @param cat
	 * @param out
	 */

	public abstract int GetPriorityLevel() ;
	public abstract CBaseEntityCondition GetOppositeCondition() ;
	
	public void Replace(CBaseEntityCondition newCond)
	{
		m_parent.UpdateCondition(this, newCond) ;		
	}

//	public CBaseEntityCondition getSimilarCondition(CBaseEntityFactory factory, CTerminal term)
//	{
//		return null;
//	}
	/**
	 * @return
	 */
	abstract public boolean isBinaryCondition() ;
	public CBaseEntityCondition getAsCondition()
	{
		return this;
	}

	public abstract CBaseEntityCondition GetSpecialConditionReplacing(String val, CBaseEntityFactory fact, CDataEntity replace) ;
	public abstract CDataEntity GetConditionReference() ;
	public abstract void SetConditonReference(CDataEntity e) ;
	
	protected boolean m_bForcedCompare = false;
	public void setForceCompare()
	{
		m_bForcedCompare = true;
	}
	
	public boolean getForcedCompare()
	{
		return m_bForcedCompare;
	}

}
