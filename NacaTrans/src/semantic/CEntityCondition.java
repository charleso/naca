/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 3 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic;

import java.util.Vector;

import generate.*;
import semantic.expression.CBaseEntityCondition;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityCondition extends CBaseActionEntity
{

	/**
	 * @param cat
	 * @param out
	 */
	public CEntityCondition(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}
	
	public void SetCondition(CBaseEntityCondition exp, CEntityBloc ifyes, CEntityBloc ifnot)
	{
		m_Condition = exp ;
		if (exp != null)
			m_Condition.SetParent(this);
		m_ElseBloc = ifnot ;
		m_ThenBloc = ifyes ;
	} 
	protected CBaseEntityCondition m_Condition = null ;
	protected CEntityBloc m_ElseBloc = null ;
	protected CEntityBloc m_ThenBloc = null ;
	protected boolean m_bAlternativeCondition = false ;
	
	public boolean ignore()
	{
		return m_Condition == null || m_Condition.ignore() || ((m_ElseBloc == null || m_ElseBloc.ignore()) && m_ThenBloc.ignore()) ;
	}
	public void UpdateCondition(CBaseEntityCondition condition, CBaseEntityCondition newCond)
	{
		if (m_Condition == condition)
		{
			m_Condition = newCond ;
		}
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#Clear()
	 */
	public void Clear()
	{
		super.Clear();
		if (m_Condition != null)
			m_Condition.Clear() ;
		if (m_ElseBloc != null)
		{
			m_ElseBloc.Clear() ;
		}
		m_ThenBloc.Clear() ;
		m_Condition = null ;
		m_ElseBloc = null ;
		m_ThenBloc = null ;
	}
	public boolean hasExplicitGetOut()
	{
		boolean bExplicit = m_ThenBloc.hasExplicitGetOut() ;
		bExplicit &= m_ElseBloc != null && m_ElseBloc.hasExplicitGetOut() ;
		return bExplicit ;
	}

	/**
	 * @param exp
	 * @param blocthen
	 */
	public void SetAlternativeCondition(CBaseEntityCondition exp, CEntityBloc blocthen)
	{
		m_Condition = exp ;
		if (exp != null)
			m_Condition.SetParent(this);
		m_ElseBloc = null ;
		m_ThenBloc = blocthen ;
		m_bAlternativeCondition  = true ;
	}

	/**
	 * @param e
	 */
	public void addAlternativeCondition(CBaseLanguageEntity e)
	{
		if (m_arrAlternativeConditions == null)
			m_arrAlternativeConditions = new Vector<CBaseLanguageEntity>() ;
		m_arrAlternativeConditions.add(e) ;
	}
	protected Vector<CBaseLanguageEntity> m_arrAlternativeConditions = null;

}
