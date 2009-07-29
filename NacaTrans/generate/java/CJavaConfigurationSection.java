/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.java;

import generate.CBaseLanguageExporter;
import semantic.CEntityConfigurationSection;
import utils.CObjectCatalog;

public class CJavaConfigurationSection extends CEntityConfigurationSection
{
	public CJavaConfigurationSection(CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(cat, out);
	}

	@Override
	protected void DoExport()
	{
		if(m_bDecimalPointIsComma)
			WriteWord("SpecialName $$decimalPoint = declare.decimalPointIsComma() ;");
		WriteEOL();
	}
}