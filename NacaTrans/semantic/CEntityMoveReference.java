/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Aug 25, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic;

import generate.*;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityMoveReference extends CBaseActionEntity
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityMoveReference(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	
	public void SetMoveReference(CEntityAddressReference from, CEntityAddressReference to)
	{
		m_From = from ;
		m_To = to;
	}
	protected CEntityAddressReference m_From = null ;
	protected CEntityAddressReference m_To = null ;
	public boolean ignore()
	{
		return m_From.ignore() || m_To.ignore();
	}
	public void Clear()
	{
		super.Clear();
		m_From.Clear() ;
		m_To.Clear() ;
		m_From = null ;
		m_To = null ;
	}
}
