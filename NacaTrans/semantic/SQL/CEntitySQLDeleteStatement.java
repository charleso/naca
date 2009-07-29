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
 * Created on 20 août 04
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

public abstract class CEntitySQLDeleteStatement extends CBaseActionEntity
{
	public CEntitySQLDeleteStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out, String csStatement, Vector<CDataEntity> arrParameters)
	{
		super(line, cat, out);
		m_csStatement = csStatement ;
		m_arrParameters = arrParameters;
	}
	protected String m_csStatement = "" ;
	protected Vector<CDataEntity> m_arrParameters = null;
	public void Clear()
	{
		super.Clear();
		m_arrParameters.clear();
	}
	public boolean ignore()
	{
		return false ;
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		int n = m_arrParameters.indexOf(field);
		if (n>=0)
		{
			m_arrParameters.get(n).UnRegisterReadingAction(this) ;
			m_arrParameters.set(n, var);
			var.RegisterReadingAction(this) ;
			return true ;
		}
		return false ;
	}
	/**
	 * @param cursor
	 */
	public void setCursor(CEntitySQLCursor cursor)
	{
		m_Cursor = cursor ;
	}
	protected CEntitySQLCursor m_Cursor = null ;
}