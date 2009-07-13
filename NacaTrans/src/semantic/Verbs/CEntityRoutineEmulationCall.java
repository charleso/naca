/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 17 janv. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.Verbs;

import java.util.Vector;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityRoutineEmulationCall extends CBaseActionEntity
{

	/* (non-Javadoc)
	 * @see semantic.CBaseActionEntity#ReplaceVariable(semantic.CDataEntity, semantic.CDataEntity)
	 */
	@Override
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		int pos = m_arrParameters.indexOf(field) ;
		if (pos >= 0)
		{
			field.UnRegisterReadingAction(this) ;
			var.RegisterReadingAction(this) ;
			m_arrParameters.set(pos, var) ;
			return true ;
		}
		return false ;
	}
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityRoutineEmulationCall(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	protected String m_csDisplay = "" ;
	protected Vector<CDataEntity> m_arrParameters = new Vector<CDataEntity>() ;
	public void Clear()
	{
		super.Clear() ;
		m_arrParameters.clear() ;
	}
	public void SetDisplay(String disp)
	{
		m_csDisplay = disp ;
	}
	public void AddParameter(CDataEntity e)
	{
		m_arrParameters.add(e) ;
	}
}
