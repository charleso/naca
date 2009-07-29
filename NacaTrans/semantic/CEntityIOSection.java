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
 * Created on Aug 26, 2004
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
public abstract class CEntityIOSection extends CBaseLanguageEntity
{
	/**
	 * @param line
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntityIOSection(int line, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, name, cat, out);
		cat.RegisterIOSection(this) ;
	}
	protected void RegisterMySelfToCatalog()
	{
		// nothing
	}
	
	public int GetInternalLevel()
	{
		return 0 ;
	}
	public CEntityProcedureSection getSectionContainer()
	{
		return null ;
	} 
	public boolean ignore()
	{
		return m_lstChildren.isEmpty() ;
	}

}
