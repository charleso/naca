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
import utils.CObjectCatalog;

public abstract class CEntitySQLCloseStatement extends CBaseActionEntity
{
	public CEntitySQLCloseStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out, CEntitySQLCursor cursor)
	{
		super(line, cat, out);
		m_Cursor = cursor ;
	}
	protected CEntitySQLCursor m_Cursor = null ;
	public boolean ignore()
	{
		return false ;
	}
}
