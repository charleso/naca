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
 * Created on Oct 11, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic;

import java.util.Vector;

import generate.CBaseLanguageExporter;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityProcedureDivision extends CBaseLanguageEntity
{
	/**
	 * @param line
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntityProcedureDivision(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, "", cat, out);
		cat.RegisterProcedureDivision(this) ;
	}

	protected void RegisterMySelfToCatalog()
	{
		// nothing
	}

	protected Vector<CDataEntity> m_arrCallParameters = new Vector<CDataEntity>();
	public void AddCallParameter(CDataEntity e)
	{
		m_arrCallParameters.add(e) ;
	}
	public Vector<CDataEntity> getCallParameters()
	{
		return m_arrCallParameters ;
	}
	
	protected CEntityBloc m_ProcedureBloc =null ;
	public void SetProcedureBloc(CEntityBloc b)
	{
		m_ProcedureBloc = b ;
	}
	public CEntityBloc getProcedureBloc()
	{
		return m_ProcedureBloc ;
	}
	public CEntityProcedureSection getSectionContainer()
	{
		return null ;
	} 
	public boolean ignore()
	{
		return false ;
	}
	public void Clear()
	{
		super.Clear();
		m_arrCallParameters.clear() ;
		if (m_ProcedureBloc!=null)
		{
			m_ProcedureBloc.Clear() ;
		}
		m_ProcedureBloc = null ;
	}

	/**
	 * @return
	 */
	public boolean hasExplicitGetout()
	{
		if (m_ProcedureBloc == null)
		{
			return false;
		}
		else
		{
			return m_ProcedureBloc.hasExplicitGetOut() ;
		}
	}
}