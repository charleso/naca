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

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;

public abstract class CEntitySQLOpenStatement extends CBaseActionEntity
{
	public CEntitySQLOpenStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out, CEntitySQLCursor cur)
	{
		super(line, cat, out);
		m_cursor = cur ;
	}
	protected CEntitySQLCursor m_cursor = null ;
	protected CDataEntity m_VariableStatement = null ;
	public boolean ignore()
	{
		return false ;
	}
	/**
	 * @param var
	 */
	public void setVariableStatement(CDataEntity var)
	{
		m_VariableStatement  = var ;
	}
}