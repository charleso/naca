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
 * Created on 3 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.Verbs;

import generate.CBaseLanguageExporter;

import java.util.Vector;

import semantic.CBaseActionEntity;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.expression.CBaseEntityExpression;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityCalcul extends CBaseActionEntity
{

	/**
	 * @param cat
	 * @param out
	 */
	public CEntityCalcul(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}

	public void SetCalcul(CBaseEntityExpression exp)
	{
		m_Expression = exp ;
	}
	
	public void AddDestination(CDataEntity e)
	{
		m_arrDestinations.add(e) ;
	}
	public void AddRoundedDestination(CDataEntity e)
	{
		m_arrRoundedDestinations.add(e) ;
	}
	protected CBaseEntityExpression m_Expression = null ;
	protected Vector<CDataEntity> m_arrDestinations = new Vector<CDataEntity>();
	protected Vector<CDataEntity> m_arrRoundedDestinations = new Vector<CDataEntity>();
	protected CBaseLanguageEntity m_OnErrorBloc = null ;
	public void Clear()
	{
		super.Clear();
		if (m_Expression!=null)
		{
			m_Expression.Clear() ;
		}
		m_Expression = null ;
		m_arrDestinations.clear();
		m_arrRoundedDestinations.clear() ;
		if (m_OnErrorBloc!=null)
		{
			m_OnErrorBloc.Clear() ;
		}
		m_OnErrorBloc = null ;
	}
	
	public void SetOnErrorBloc(CBaseLanguageEntity eBloc)
	{
		m_OnErrorBloc = eBloc ;
	}
	public boolean ignore()
	{
		boolean ignore = m_Expression.ignore() ;
		boolean b = true ;
		for (int i=0; i<m_arrDestinations.size(); i++)
		{
			CDataEntity e = m_arrDestinations.get(i);
			b &= e.ignore();
		}
		for (int i=0; i<m_arrRoundedDestinations.size(); i++)
		{
			CDataEntity e = m_arrRoundedDestinations.get(i);
			b &= e.ignore();
		}
		ignore |= b ;
		return ignore ;
	}
	public boolean IgnoreVariable(CDataEntity data)
	{
		if  (m_arrDestinations.contains(data) ||  m_arrRoundedDestinations.contains(data))
		{
			m_arrDestinations.remove(data);
			m_arrRoundedDestinations.remove(data) ;
			data.UnRegisterWritingAction(this) ;
			return true ;
		}
		return false ;
	}
}
