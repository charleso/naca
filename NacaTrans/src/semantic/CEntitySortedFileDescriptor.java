/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package semantic;

import generate.CBaseLanguageExporter;
import utils.CObjectCatalog;

public abstract class CEntitySortedFileDescriptor extends CEntityFileDescriptor
{

	public CEntitySortedFileDescriptor(int line, String name,
					CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, name, cat, out);
	}


}
