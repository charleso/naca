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
 * Created on 19 août 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.SQL;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import java.util.Vector;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;

public abstract class CEntitySQLFetchStatement extends CBaseActionEntity
{
	/* (non-Javadoc)
	 * @see semantic.CBaseActionEntity#ReplaceVariable(semantic.CDataEntity, semantic.CDataEntity)
	 */
	@Override
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_arrInto.contains(field))
		{
			int pos = m_arrInto.indexOf(field) ;
			m_arrInto.set(pos, var) ;
			field.UnRegisterWritingAction(this) ;
			var.RegisterWritingAction(this) ;
			return true ;
		}
		return false ;
	}
	public CEntitySQLFetchStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out, CEntitySQLCursor cur)
	{
		super(line, cat, out);
		m_Cursor = cur;
		m_arrInto = new Vector<CDataEntity>() ;
		m_arrIndicators = new Vector<CDataEntity>() ;
		m_arrIgnoredInto = new Vector<CDataEntity>() ;
		m_arrIgnoredIndicators = new Vector<CDataEntity>() ;
	}
	protected CEntitySQLCursor m_Cursor = null ;
	protected Vector<CDataEntity> m_arrInto = null;
	protected Vector<CDataEntity> m_arrIndicators = null;
	protected MissingFetchVariable m_missingFetchVariable = null;
	
	protected Vector<CDataEntity> m_arrIgnoredInto = null;
	protected Vector<CDataEntity> m_arrIgnoredIndicators = null;
	
	public void Clear()
	{
		super.Clear();
		m_arrInto.clear() ;
		m_arrIndicators.clear();
		m_arrIgnoredInto.clear() ;
		m_arrIgnoredIndicators.clear() ;
		m_Cursor.Clear() ;
		m_Cursor = null ;
		m_missingFetchVariable = null;
	}
	public boolean ignore()
	{
		return false ;
	}
	public void AddFetchInto(CDataEntity e, CDataEntity eInd)
	{
		m_arrInto.add(e) ;
		m_arrIndicators.add(eInd) ;
	}
	
	public void AddIgnoredFetchInto(CDataEntity e, CDataEntity eInd)
	{
		m_arrIgnoredInto.add(e) ;
		m_arrIgnoredIndicators.add(eInd) ;
	}
	
	public void RegisterMissingFetchVariable(int nNbMissingFetchVariables)
	{
		m_missingFetchVariable = new MissingFetchVariable(nNbMissingFetchVariables);
	}
}