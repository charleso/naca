/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaTrans - Naca Transcoder v1.2.0.beta.1
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package semantic.Verbs;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import utils.CObjectCatalog;

public abstract class CEntityConstantReturn extends CBaseActionEntity
{
	protected String m_csConstant = "";
	/**
	 * @param cat
	 * @param out
	 */
	public CEntityConstantReturn(int l, CObjectCatalog cat, CBaseLanguageExporter out, String csConstant)
	{
		super(l, cat, out);
		m_csConstant = csConstant;
	}
	
	public boolean ignore()
	{
		return false ;
	}
	
	public boolean hasExplicitGetOut()
	{
		return true ;
	}
}
