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
 * Created on Oct 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.SQL;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntitySQLCursor extends CDataEntity
{
	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntitySQLCursor(String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(0, name, cat, out);
		m_ProgramCatalog.RegisterSQLCursor(this);
		if (!name.equals(this.GetName()))
		{
			m_ProgramCatalog.RegisterSQLCursor(name, this);
		}
	}

	public boolean HasAccessors()
	{
		return false;
	}
	public String ExportWriteAccessorTo(String value)
	{
		return "" ;
	}

	public void SetSelect(CEntitySQLCursorSelectStatement eSQL)
	{
		m_Select = eSQL ;
	}
	
	protected CEntitySQLCursorSelectStatement m_Select = null ;
	protected CDataEntity m_VariableStatement = null ;

	public int GetNbColumns()
	{
		if (m_Select == null)
			return 0 ;
		return m_Select.GetNbColumns() ;
	}
	public boolean ignore()
	{
		return false ;
	}
	public String GetConstantValue()
	{
		return "" ;
	}

	/**
	 * 
	 */
	public CEntitySQLCursorSelectStatement getSelect()
	{
		return m_Select ;
	}

	/**
	 * @param var
	 */
	public void setVariableStatement(CDataEntity var)
	{
		m_VariableStatement = var ;
	}
	public CDataEntity getVariableStatement()
	{
		return m_VariableStatement ;
	}
}
