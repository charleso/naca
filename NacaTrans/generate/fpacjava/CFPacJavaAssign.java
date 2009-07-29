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
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.Verbs.CEntityAssign;
import utils.CObjectCatalog;

public class CFPacJavaAssign extends CEntityAssign
{

	public CFPacJavaAssign(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}

	@Override
	protected void DoExport()
	{
		for (int i=0; i<GetNbRefTo(); i++)
		{
			CDataEntity e = GetRefTo(i) ;
			String cs = "move(" + m_Value.ExportReference(getLine()) + ", " + e.ExportReference(getLine()) + ") ;" ;
			WriteLine(cs) ;
		}		
	}

}
