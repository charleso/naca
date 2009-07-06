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
package semantic;

import java.util.Vector;


import generate.CBaseLanguageExporter;
import semantic.SQL.CEntitySQLCursor;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntitySQLCursorSection extends CEntityDataSection
{
	/**
	 * @param line
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntitySQLCursorSection(CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(0, "SQLCursorSection", cat, out);
	}
	
	protected Vector m_arrCursors = null ;
	public void SetCursors(Vector v)
	{
		m_arrCursors = v ;
	}
	public CEntityProcedureSection getSectionContainer()
	{
		return null ;
	} 
	public void Clear()
	{
		super.Clear();
		for (int i=0; i<m_arrCursors.size(); i++)
		{
			CEntitySQLCursor cur = (CEntitySQLCursor)m_arrCursors.get(i);
			cur.Clear() ;
		}
		m_arrCursors.clear() ;
	}
	/* (non-Javadoc)
	 * @see semantic.CEntityDataSection#ignore()
	 */
	@Override
	public boolean ignore()
	{
		return m_arrCursors.isEmpty() ;
	}
}
