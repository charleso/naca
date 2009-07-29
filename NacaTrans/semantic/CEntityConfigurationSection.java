/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package semantic;

import generate.CBaseLanguageExporter;
import utils.CObjectCatalog;

public abstract class CEntityConfigurationSection extends CBaseLanguageEntity
{
	public CEntityConfigurationSection(CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(0, "", cat, out);
	}

	@Override
	protected void RegisterMySelfToCatalog()
	{
	}
	
	protected boolean m_bDecimalPointIsComma = false;

	public void setDecimalPointIsComma()
	{
		m_bDecimalPointIsComma = true;
	}
}